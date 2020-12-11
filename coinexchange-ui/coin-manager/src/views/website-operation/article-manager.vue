<template>
  <div class="common-main">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="search-container">

      <el-form-item label="标题" prop="title">
        <el-input v-model="ruleForm.title" class="form-input" clearable></el-input>
      </el-form-item>
      <el-form-item label="创建时间" prop="createTime">
        <el-date-picker
          v-model="ruleForm.dateRange"
          type="daterange"
          align="right"
          unlink-panels
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :picker-options="datePickerOptions"
          value-format="yyyy-MM-dd">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select v-model="ruleForm.status" class="form-input" placeholder="请选择" clearable>
          <el-option label="启用" value="1"></el-option>
          <el-option label="禁用" value="0"></el-option>
        </el-select>
      </el-form-item>
      <div class="operation-container">
        <el-button icon="el-icon-search" @click="submitForm('ruleForm')">搜索</el-button>
        <el-button type="primary" icon="el-icon-edit" @click="handleCreate">新建</el-button>
        <el-button type="danger" icon="el-icon-delete" @click="handleDelete">删除</el-button>
      </div>
    </el-form>


    <el-table
      ref="multipleTable"
      :data="listData"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange"
      v-loading="listLoading"
    >
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column
        prop="title"
        label="标题"
      >
      </el-table-column>
      <el-table-column
        prop="description"
        label="简介"
        class-name="article-content-class"
      >
      </el-table-column>
      <el-table-column
        prop="author"
        label="作者"
      >
      </el-table-column>
      <el-table-column
        prop="sort"
        label="排序"
        width="120"
      >
      </el-table-column>
      <el-table-column
        prop="status"
        label="状态"
        width="100"
      >
        <template slot-scope="scope">
          <el-tag :type="scope.row.status| elTagFilter">
            {{scope.row.status|statusFilter}}
          </el-tag>

        </template>
      </el-table-column>

      <el-table-column
        prop="created"
        label="创建时间"
        width="200"
      >
      </el-table-column>
      <el-table-column
        label="操作"
        width="200"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="handleEdit(scope.$index, scope.row)" icon="el-icon-edit">编辑
          </el-button>
          <el-button
            size="mini"
            v-show="scope.row.status===0"
            @click="handleChangeStatus(scope.$index, scope.row)" icon="el-icon-edit">启用
          </el-button>
          <el-button
            size="mini"
            type="danger"
            v-show="scope.row.status===1"
            @click="handleChangeStatus(scope.$index, scope.row)" icon="el-icon-edit">禁用
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      class="pagination-container"
      background
      layout="total,prev, pager, next"
      :current-page.sync="listQuery.current"
      :page-size="listQuery.size"
      :total="listQuery.total"
      @current-change="handlePageChange"
    >
    </el-pagination>

    <article-dialog ref="articleDialog" @refreshList="_getList"></article-dialog>
  </div>
</template>

<script>
  import articleDialog from './component/article-dialog'
  import {noticeApi} from "@/api/noticeApi";
  import commonMixin from '@/components/mixin/common-mixin'

  export default {
    components: {articleDialog},
    mixins: [commonMixin],
    data() {
      return {
        rules: {
          title: [
            // {required: true, message: '请输入标题', trigger: 'blur'},
          ],
        },
        ruleForm: {
          title: '',
          dateRange: '',
          status: ''
        },
      }
    },

    methods: {
      listCallback() {
        return noticeApi.getNoticeList(this.ruleForm,
          this.listQuery.current, this.listQuery.size)
      },
      deleteCallback(ids) {
        return noticeApi.deleteNotice(ids)
      },
      handleCreate() {
        this.$refs.articleDialog.showDialog(1,{});
      },
      handleEdit(index, row) {
        this.$refs.articleDialog.showDialog(2,Object.assign({}, row));
      },
      async changeStatusCallback(id, status) {
        console.log(id+"-------"+status)
        return await noticeApi.updateStatus(id,status);
      },
    }
  }
</script>

<template>
  <div class="common-main">


    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="search-container">
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
          <el-option label="未处理" :value="1"></el-option>
          <el-option label="已处理" :value="2"></el-option>
        </el-select>
      </el-form-item>
      <div class="operation-container">
        <el-button  icon="el-icon-search" @click="submitForm('ruleForm')">搜索</el-button>
      </div>
    </el-form>


    <el-table
      ref="multipleTable"
      :data="listData"
      tooltip-effect="dark"
      v-loading="listLoading"

    >

      <el-table-column
        prop="username"
        label="用户名"
      >
      </el-table-column>
      <el-table-column
        prop="realName"
        label="姓名"
      >
      </el-table-column>

      <el-table-column
        prop="question"
        label="问题内容"
      >
      </el-table-column>
      <el-table-column
        prop="created"
        label="创建时间"
        width="160"
      >
      </el-table-column>
      <el-table-column
        prop="created"
        label="回复时间"
        width="160"
      >
      </el-table-column>
      <el-table-column
        prop="status"
        label="状态"
        width="100"
      >
        <template slot-scope="scope">
          <el-tag :type="scope.row.status| elTagFilter2">
            {{scope.row.status|serviceStatusFilter}}
          </el-tag>
        </template>
      </el-table-column>
      <!--<el-table-column-->
      <!--prop="service"-->
      <!--label="客服人员"-->
      <!--&gt;-->
      <!--</el-table-column>-->
      <el-table-column
        label="操作"
        width="200"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="handleEdit(scope.$index, scope.row)" icon="el-icon-edit">回复
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
    <reply-dialog ref="replyDialog" @refreshList="_getList"></reply-dialog>
  </div>
</template>

<script>
  import commonMixin from '@/components/mixin/common-mixin'
  import replyDialog from './component/reply-dialog'
  import {serviceApi} from "../../api/serviceApi";

  export default {
    components:{replyDialog},
    mixins: [commonMixin],
    data() {
      return {
        ruleForm: {
          status:''
        },
        rules: {},
      }
    },
    methods: {
      listCallback() {
        return serviceApi.getWorkIssueList(this.ruleForm,
          this.listQuery.current, this.listQuery.size)
      },
      handleEdit(index,row){
        this.$refs.replyDialog.showDialog(2,row)
      }
    }
  }
</script>

<template>
  <div class="common-main">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="search-container">
      <el-form-item label="资源类型" prop="type">
        <el-select v-model="ruleForm.type" class="form-input" placeholder="请选择" clearable>
          <!--<el-option label="合作机构" value="1"></el-option>-->
          <el-option label="主banner图" value="WEB_BANNER"></el-option>
          <el-option label="小banner图" value="LINK_BANNER"></el-option>
        </el-select>

      </el-form-item>
      <el-form-item label="资源名称" prop="name">
        <el-input v-model="ruleForm.name" class="form-input" clearable></el-input>
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
        prop="name"
        label="资源名称"
        width="200"
      >
      </el-table-column>
      <el-table-column
        prop="type"
        label="资源类型"
        width="150"
      >
        <template slot-scope="scope">
          {{scope.row.type | resourceTypeFilter}}
        </template>
      </el-table-column>
      <el-table-column
        prop="value"
        label="图片"
      >
        <template slot-scope="scope">
          <img class="logoImg" v-bind:src="scope.row.value"/>
        </template>
      </el-table-column>
      <el-table-column
        prop="url"
        label="链接"
      >
      </el-table-column>
      <el-table-column
        prop="sort"
        label="排序"
        width="120"
      >
      </el-table-column>
      <el-table-column
        prop="created"
        label="创建时间"
        width="160"
      >
      </el-table-column>
      <el-table-column
        prop="status"
        label="状态"
        width="100"
      >
        <template slot-scope="scope">
          <el-tag :type="scope.row.status| elTagFilter">
            {{scope.row.status | statusFilter}}
          </el-tag>
        </template>
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
    <resource-dialog ref="resourceDialog" @refreshList="_getList"></resource-dialog>
  </div>
</template>

<script>

  import resourceDialog from './component/resource-dialog'
  import {resourceConfigApi} from '@/api/resourceConfigApi'
  import commonMixin from '@/components/mixin/common-mixin'

  export default {
    components: {resourceDialog},
    mixins: [commonMixin],
    data() {
      return {
        PROTOCOL: process.env.PROTOCOL,
        ruleForm: {
          state: ''
        },
        rules: {},
      }
    },
    methods: {
      listCallback() {
        return resourceConfigApi.getWebConfigList(this.ruleForm, this.listQuery.current, this.listQuery.size)
      },
      async deleteCallback(ids) {
        return await resourceConfigApi.deleteWebConfig(ids)
      },
      handleCreate() {
        this.$refs.resourceDialog.showDialog(1);
      },
      handleEdit(index, row) {
        const {url = ''} = row;
        let res = url;
        if (url) {
          const temp = this.PROTOCOL;
          const lastindex = url.lastIndexOf(temp);
          if (lastindex > -1) {
            res = url.substring(lastindex + temp.length);
          }
        }
        this.$refs.resourceDialog.showDialog(2, {
          ...row,
          url: res,
        });
      },
    }
  }
</script>

<template>
  <div class="common-main">
    <el-form :rules="rules" ref="ruleForm" :model="ruleForm" label-width="100px"
             class="search-container">
      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="ruleForm.mobile" class="form-input" clearable></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="fullname">
        <el-input v-model="ruleForm.fullname" class="form-input" clearable></el-input>
      </el-form-item>

      <div class="operation-container">
        <el-button icon="el-icon-search" @click="submitForm('ruleForm')">搜索
        </el-button>
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
        prop="username"
        label="用户名"
      >
      </el-table-column>
      <el-table-column
        prop="fullname"
        label="姓名"
      >
      </el-table-column>
      <el-table-column
        prop="mobile"
        width="250"
        label="手机号"
      >
      </el-table-column>
      <el-table-column
        prop="email"
        width="250"
        label="邮箱"
      >
      </el-table-column>
      <el-table-column
        prop="status"
        width="100"
        label="状态"
      >
        <template slot-scope="scope">
          <el-tag :type="scope.row.status | elTagFilter">
            {{scope.row.status | statusFilter}}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column
        label="操作"
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
    <employeeDialog ref="employeeDialog" @refreshList="_getList"></employeeDialog>
  </div>
</template>

<script>
  import commonMixin from '@/components/mixin/common-mixin'
  import {sysConfigApi} from '@/api/sysConfigApi'
  import employeeDialog from './component/employee-dialog'

  export default {
    components: {employeeDialog},
    mixins: [commonMixin],
    data() {
      return {
        rules: {},
        ruleForm: {}
      }
    },
    methods: {
      listCallback() {
        return sysConfigApi.getUsers(this.ruleForm,
          this.listQuery.current, this.listQuery.size)
      },
      deleteCallback(ids) {
        return sysConfigApi.deleteEmployee(ids)
      },
      getInitForm(data) {
        const {role_strings = ''} = data;
        return {
          ...data,
          role_strings_array: role_strings ? role_strings.split(',').filter(item => !!item) : []
        }
      },
      handleCreate() {
        this.$refs.employeeDialog.showDialog(1, this.getInitForm({}));
      },
      handleEdit(index, row) {
        console.log(row.role_strings)
        this.$refs.employeeDialog.showDialog(2, this.getInitForm({...row}));
      },
    }
  }
</script>

<style scoped>

</style>


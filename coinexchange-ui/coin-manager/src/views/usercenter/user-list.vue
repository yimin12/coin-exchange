<template>
  <div class="common-main">
    <el-form :model="ruleForm"
             :rules="rules"
             ref="ruleForm"
             label-width="100px"
             class="search-container">

      <el-form-item label="用户ID" prop="userId">
        <el-input v-model="ruleForm.userId" class="form-input" clearable></el-input>
      </el-form-item>

      <el-form-item label="用户名" prop="userName">
        <el-input v-model="ruleForm.userName" class="form-input" clearable></el-input>
      </el-form-item>

      <el-form-item label="姓名" prop="realName">
        <el-input v-model="ruleForm.realName" class="form-input" clearable></el-input>
      </el-form-item>

      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="ruleForm.mobile" class="form-input" clearable></el-input>
      </el-form-item>

      <el-form-item label="状态" prop="status">
        <el-select v-model="ruleForm.status" class="form-input" placeholder="请选择" clearable>
          <el-option label="启用" value="1"></el-option>
          <el-option label="禁用" value="0"></el-option>
        </el-select>
      </el-form-item>

 <!--<el-form-item label="实名认证状态" prop="status">-->
        <!--<el-select v-model="ruleForm.authStatus" class="form-input" placeholder="请选择" clearable>-->
          <!--<el-option label="未认证" value="0"></el-option>-->
          <!--<el-option label="初级" value="1"></el-option>-->
          <!--<el-option label="高级" value="2"></el-option>-->
        <!--</el-select>-->
      <!--</el-form-item>-->

      <div class="operation-container">
        <el-button icon="el-icon-search" @click="submitForm('ruleForm')">搜索</el-button>
      </div>
    </el-form>

    <el-table
      :data="listData"
      v-loading="listLoading"
    >
      <el-table-column
        prop="id"
        label="用户ID"
        width="180"
      >
      </el-table-column>

      <el-table-column
        prop="username"
        label="用户名"
        width="150"
      >
      </el-table-column>

      <el-table-column
        prop="mobile"
        label="手机号"
        width="150">
      </el-table-column>

      <el-table-column
        prop="realName"
        label="姓名">
      </el-table-column>

      <el-table-column
        prop="authStatus"
        label="实名认证状态"
        width="120">
        <template slot-scope="scope">
          <el-tag :type="authStatusType(scope.row.authStatus)">
            {{scope.row.authStatus | authStatusFilter}}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column
        prop="paypassSetting"
        label="是否设置支付密码"
        width="150">
        <template slot-scope="scope">
          <el-tag :type="scope.row.paypassSetting|elTagFilter">
            {{scope.row.paypassSetting === 0 ? '未设置' : '已设置' }}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column
        prop="inviteCode"
        label="邀请码"
        width="150">
      </el-table-column>

      <el-table-column
        prop="status"
        label="状态">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status|elTagFilter">
            {{scope.row.status | statusFilter}}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column
        prop="created"
        label="注册时间"
        width="180">
      </el-table-column>

      <el-table-column
        label="操作"
        width="280"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="viewDetail(scope.$index, scope.row)"
            icon="el-icon-search">查看
          </el-button>
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

    <user-dialog ref="userDialog" @refreshList="_getList"></user-dialog>

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
  </div>
</template>

<script>
  import commonMixin from '@/components/mixin/common-mixin'
  import {userApi} from "../../api/userApi";
  import userDialog from "./components/user-dialog";

  export default {
    mixins: [commonMixin],
    components: {userDialog},
    data() {
      return {
        ruleForm: {
          department: '',
          name: '',
          pwd: '',
          desc: '',
          state: '',
          cname: '',
          ename: '',
          email: '',
          mobile: '',
          // authStatus:""
        },
        rules: {},
      }
    },
    methods: {
      authStatusType(authStatus) {
        switch (authStatus) {
          case 0:
            return 'danger'
          case 1:
            return 'success'
          case 2:
            return ''
        }
      },
      viewDetail(index, row) {
        this.$router.push({path: `/usercenter/user-manager/user-detail/${row.id}`})
      },
      listCallback() {
        console.log(this.fuleForm)
        return userApi.getUserList(this.ruleForm,
          this.listQuery.current, this.listQuery.size)
      },
      handleEdit(index, row) {
        this.$refs.userDialog.showDialog(2, {...row});
      },
      changeStatusCallback(id, status) {
        return userApi.updateStatus(id, status)
      },
      updateCallback() {
        return userApi.updateUser(this.ruleForm,
          this.listQuery.current, this.listQuery.size)
      },
    }
  }
</script>

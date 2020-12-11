<template>
  <div class="assets common-main">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="search-container">
      <el-form-item label="ID" prop="id">
        <el-input v-model="ruleForm.id" class="form-input" clearable></el-input>
      </el-form-item>

      <el-form-item label="矿池名称" prop="name">
        <el-input v-model="ruleForm.name" class="form-input" clearable></el-input>
      </el-form-item>
      <el-form-item label="创建者" prop="createUser">
        <el-input v-model="ruleForm.createUser" class="form-input" clearable></el-input>
      </el-form-item>


      <!--<el-form-item label="时间" prop="created">-->
        <!--<el-input v-model="ruleForm.mobile" class="form-input" clearable></el-input>-->
      <!--</el-form-item>-->`

      <div class="operation-container">
        <el-button icon="el-icon-search" @click="submitForm('ruleForm')">搜索</el-button>
      </div>
    </el-form>

    <el-table
      :data="listData"
      style="width: 100%"
      v-loading="listLoading"
    >
      <el-table-column
        prop="id"
        label="ID"
        width="180"
      >
      </el-table-column>
      <el-table-column
        prop="name"
        label="矿池名称"
        width="120"
      >
      </el-table-column>
      <el-table-column
        prop="createUser"
        label="创建者"
        width="200">
      </el-table-column>
      <el-table-column
        prop="description"
        label="描述"
        width="150">
      </el-table-column>
      <!--0 待审核 1通过 2拒绝-->
      <el-table-column
        prop="authStatus"
        label="矿池审核状态"
        width="120">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status|elTagFilter">
            {{scope.row.status | poolStatusFilter}}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column
        label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="small" icon="el-icon-edit"
                     :disabled="scope.row.status!=0"
                     @click="handleVerify(scope.row.id)">审核
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



    <el-dialog width="600px" title="审核提示" :visible.sync="dialogFormVisible">
      <el-form :model="auditForm" :rules="auditRules" ref="ruleRef">
        <el-form-item label="审核备注" :label-width="'120px'" prop="remark">
          <el-input v-model="auditForm.remark"></el-input>
        </el-form-item>
        <el-form-item class="dialog-footer">
          <el-button @click="auditOperant('reject','ruleRef')">拒绝</el-button>
          <el-button type="primary" @click="auditOperant('accept','ruleRef')">通过</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

  </div>
</template>

<script>
  import commonMixin from '@/components/mixin/common-mixin'
  import {userApi} from "../../api/userApi";

  export default {
    mixins: [commonMixin],
    data() {
      return {
        dialogFormVisible:false,
        ruleForm: {
          createUser: '',
          id: '',
          name: '',
          status: ''
        },
        rules: {},
        postData: {},

        auditForm: {
          remark: '',
        },
        auditRules: {
          remark: [{required: true, message: '请输入备注原因', trigger: 'blur'}]
        }
      }
    },
    methods: {
      listCallback() {
        return userApi.minePollList(this.ruleForm,
          this.listQuery.current, this.listQuery.size)
      },
      handleVerify(id) {
        this.dialogFormVisible = true;
        this.postData.id = id;
        this.auditForm.remark = '';
        if (this.$refs['ruleRef']) {
          this.$refs['ruleRef'].resetFields();
        }
      },
      // 审核操作
      auditOperant(type, formName) {
        // 拒绝审核
        if (type == 'reject') {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              this.postData.remark = this.auditForm.remark;
              this.postData.status = 2;
              this.request();
            } else {
              return false;
            }
          })
        } else if (type == 'accept') {
          this.postData.remark = this.auditForm.remark;
          this.postData.status = 1;
          this.$refs[formName].resetFields();
          this.request();
        }
      },
      async request() {
        await userApi.verifyPool(this.postData);
        this.$notify({
          type: 'success',
          title: '提示',
          message: '操作成功！',
        });
        this.dialogFormVisible = false;
        this._getList();
      }
    }
  }
</script>

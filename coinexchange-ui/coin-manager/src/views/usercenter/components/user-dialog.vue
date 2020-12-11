<template>
  <el-dialog
    :title="dialogTitle"
    :visible.sync="dialogVisible"
    width="40%"
  >
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="dialog-form">
      <!--<el-form-item label="所在部门" prop="name">-->
      <!--<el-input v-model="ruleForm.department"></el-input>-->
      <!--</el-form-item>-->
      <!---->
      <el-form-item label="ID" prop="id">
        <el-input v-model="ruleForm.id" disabled class="form-input"></el-input>
      </el-form-item>

      <el-form-item label="用户名" prop="username">
        <el-input v-model="ruleForm.username" class="form-input" disabled></el-input>
      </el-form-item>

      <el-form-item label="国际区号" prop="countryCode">
        <el-input v-model="ruleForm.countryCode" class="form-input" disabled></el-input>
      </el-form-item>

      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="ruleForm.mobile" class="form-input" disabled></el-input>
      </el-form-item>
      <!--第二列-->
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="ruleForm.email" class="form-input" disabled></el-input>
      </el-form-item>

      <el-form-item label="姓名" prop="realName">
        <el-input v-model="ruleForm.realName" class="form-input"></el-input>
      </el-form-item>

      <el-form-item label="身份证号" prop="idCard">
        <el-input v-model="ruleForm.idCard" class="form-input" disabled></el-input>
      </el-form-item>
      <el-form-item label="实名认证状态">
        {{ruleForm.authStatus|authStatusFilter}}
      </el-form-item>
      <el-form-item label="用户状态" prop="status">
        <el-switch v-model="statusFlag" active-text="启用"
                   inactive-text="禁用"></el-switch>
      </el-form-item>
    </el-form>
       <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
       </span>
  </el-dialog>
</template>
<script>
  import {userApi} from "@/api/userApi";
  import dialogMixin from '@/components/mixin/dialog-mixin'

  export default {
    mixins: [dialogMixin],
    data() {
      return {
        dialogTitle: '编辑',
        rules: {
          username: [
            {required: true, message: '请输入用户名', trigger: 'blur'},
          ],
          mobile: [
            {required: true, message: '请输入手机号', trigger: 'blur'},
          ],
        },
        ruleForm: {

        }
      }
    },
    methods: {

      updateCallback() {
        return userApi.updateUser(this.ruleForm)
      },
      editCallback(row){
        this.statusFlag = row.status === 1
      },
    }
  }
</script>

<style scoped>

</style>



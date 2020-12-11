<template>
  <el-dialog
    :title="dialogTitle"
    :visible.sync="dialogVisible"
    width="50%"
    @close="closeDialog"
  >
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
      <el-form-item label="ID" prop="id">
        <el-input disabled v-model="ruleForm.id" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="层级" prop="level">
        <el-input disabled v-model="ruleForm.level" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="mobile">
        <el-input disabled v-model="ruleForm.mobile" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input disabled v-model="ruleForm.email" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="username">
        <el-input disabled v-model="ruleForm.username" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="身份证号" prop="idCard">
        <el-input  disabled v-model="ruleForm.idCard" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="注册时间" prop="created">
        <el-input disabled v-model="ruleForm.created" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="审核备注" prop="agentNote">
        <el-input disabled v-model="ruleForm.agentNote" class="form-input"></el-input>
      </el-form-item>

      <el-form-item label="上级代理商">
        <el-select v-model="ruleForm.directInviteid" class="form-input">
          <el-option v-for="user in userArr"
                     :label="user.realName || user.username"
                     :key="user.id"
                     :value="user.id"></el-option>
        </el-select>
      </el-form-item>

    </el-form>

    <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
      </span>
  </el-dialog>
</template>

<script>
  import {agentVerifyApi} from "@/api/agentVerifyApi";
  import dialogMixin from '@/components/mixin/dialog-mixin';

  export default {
    mixins: [dialogMixin],
    data() {
      return {
        dialogTitle: '编辑代理信息',
        rules: {},
        ruleForm: {},
        userArr: []
      };
    },
    methods: {
      async editCallback(row) {
        let that = this;
        await agentVerifyApi.getCurrentAgent(row.id).then(function (data) {
          that.userArr = data;
        });
      },
      async updateCallback() {
        let that = this;
        let ruleForm = {
          agentNote: that.ruleForm.agentNote,
          changeUserId: that.ruleForm.directInviteid,
          countryCode: that.ruleForm.countryCode,
          email: that.ruleForm.email,
          idCard: that.ruleForm.idCard,
          mobile: that.ruleForm.mobile,
          status: that.ruleForm.reviewsStatus,
          userId: that.ruleForm.id,
          username: that.ruleForm.username
        };
        await agentVerifyApi.postAgentInfo(ruleForm);
      }

    }
  };
</script>

<style scoped>

</style>

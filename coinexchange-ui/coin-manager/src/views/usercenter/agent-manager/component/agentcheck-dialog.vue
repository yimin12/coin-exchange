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
      <el-form-item label="姓名" prop="realName">
        <el-input disabled v-model="ruleForm.realName" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="身份证号" prop="idCard">
        <el-input disabled v-model="ruleForm.idCard" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="注册时间" prop="created">
        <el-input disabled v-model="ruleForm.created" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="审核备注" prop="agentNote">
        <el-input v-model="ruleForm.agentNote" class="form-input"></el-input>
      </el-form-item>

      <el-form-item label="审核状态" prop="reviewsStatus">
        <el-select v-model="ruleForm.reviewsStatus" class="form-input">
          <el-option key="'1'" label="通过" value="1"></el-option>
          <el-option key="'2'" label="拒绝" value="2"></el-option>
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
        dialogTitle: '编辑代理注册审核',
        rules: {},
        ruleForm: {},
        statusFlag: ''
      };
    },
    watch: {
      statusFlag(val) {
        if (val) {
          this.ruleForm.reviewsStatus = 1;
        } else {
          this.ruleForm.reviewsStatus = 2;
        }
      }
    },
    methods: {
      editCallback(row) {
      },
      async updateCallback() {
        let that = this;
        await agentVerifyApi.updateVerify(
          that.ruleForm.id,
          that.ruleForm.reviewsStatus,
          that.ruleForm.agentNote);
      }

    }
  };
</script>

<style scoped>

</style>



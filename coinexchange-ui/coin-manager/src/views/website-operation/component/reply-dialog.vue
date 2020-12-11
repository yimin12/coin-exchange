<template>
  <el-dialog
    title="回复客户"
    :visible.sync="dialogVisible"
    width="40%"
  >
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="dialog-container">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="ruleForm.username" disabled class="form-input"></el-input>

      </el-form-item>
      <el-form-item label="姓名" prop="realName">
        <el-input v-model="ruleForm.realName" disabled class="form-input"></el-input>

      </el-form-item>

      <el-form-item label="创建时间" prop="created">
        <el-input v-model="ruleForm.created" disabled class="form-input"></el-input>
      </el-form-item>

      <el-form-item label="问题内容" prop="question">
        <el-input
          type="textarea"
          :autosize="{ minRows: 5, maxRows: 5}"
          v-model="ruleForm.question" disabled class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="回复内容" prop="answer">
        <el-input
          type="textarea"
          :autosize="{ minRows: 10, maxRows: 10}"
          placeholder="请输入内容"
          v-model="ruleForm.answer" class="form-input">
        </el-input>
      </el-form-item>
    </el-form>

    <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
      </span>
  </el-dialog>
</template>

<script>
  import {serviceApi} from "@/api/serviceApi";
  import dialogMixin from '@/components/mixin/dialog-mixin'

  export default {
    mixins:[dialogMixin],
    data() {
      return {
        rules: {
          answer: [
            {required: true, message: '请回复内容！', trigger: 'blur'},
          ],
        },
        ruleForm: {}
      }
    },

    methods: {

      updateCallback() {
        return serviceApi.updateWorkIssue(this.ruleForm)
      },

    }
  }
</script>

<style scoped>

</style>



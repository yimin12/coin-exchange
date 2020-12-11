<template>
  <el-dialog
    :title="dialogTitle"
    :visible.sync="dialogVisible"
    width="50%"
    @close="closeDialog"
  >
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
      <el-form-item label="充值金额" prop="amount">
        <el-input v-model="ruleForm.amount" class="form-input" type="number"></el-input>
      </el-form-item>

      <el-form-item label="备注" prop="remark">
        <el-input v-model="ruleForm.remark" class="form-input"></el-input>
      </el-form-item>
    </el-form>

    <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
      </span>
  </el-dialog>
</template>

<script>
  import {assetsApi} from '@/api/assetsApi'
  import dialogMixin from '@/components/mixin/dialog-mixin'

  export default {
    mixins: [dialogMixin],
    data() {
      return {
        dialogTitle: '充值',
        rules: {
          amount: [
            {required: true, message: '请输入金额', trigger: 'blur'},
          ],
          remark: [
            {required: true, message: '请输入备注', trigger: 'blur'},
          ],
        },
        ruleForm: {
          amount: '',
          remark: ''
        }
      }
    },
    methods: {
      updateCallback() {
        return assetsApi.rechargeCoin(this.ruleForm)
      },
    }
  }
</script>

<style scoped>

</style>



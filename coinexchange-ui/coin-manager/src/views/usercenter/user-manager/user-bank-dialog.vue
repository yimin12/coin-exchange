<template>
  <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" @close="closeDialog">

    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px">

      <el-form-item label="银行卡名称" prop="remark">
        <el-input v-model="ruleForm.remark" class="form-input"></el-input>
      </el-form-item>

      <el-form-item label="开户行" prop="bank">
        <el-input v-model="ruleForm.bank" class="form-input"></el-input>
      </el-form-item>

      <el-form-item label="开户地址" prop="bankAddr">
        <el-input v-model="ruleForm.bankAddr" class="form-input"></el-input>
      </el-form-item>

      <el-form-item label="开户人" prop="realName">
        <el-input v-model="ruleForm.realName" class="form-input"></el-input>
      </el-form-item>

      <el-form-item label="开户账号" prop="bankCard">
        <el-input v-model="ruleForm.bankCard" class="form-input"></el-input>
      </el-form-item>

      <el-form-item label="开户省" prop="bankProv">
        <el-input v-model="ruleForm.bankProv" class="form-input"></el-input>
      </el-form-item>

      <!-- 0 禁用 1启用 -->
      <el-form-item label="状态" prop="status">
        <el-select v-model="ruleForm.status" placeholder="状态">
          <el-option label="禁用" :value="0"></el-option>
          <el-option label="启用" :value="1"></el-option>
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
  import dialogMixin from '@/components/mixin/dialog-mixin';
  import {userApi} from '@/api/userApi'

  export default {
    mixins: [dialogMixin],
    props: [
      'userId',
    ],
    data() {
      return {
        dialogTitle: '修改银行卡',
        rules: {
          remark:[
            {required: true, message: '请输入银行卡名称', trigger: 'blur'}
          ],
          bank: [
            {required: true, message: '请输入开户行', trigger: 'blur'}
          ],
          bankAddr: [
            {required: true, message: '请输入开户地址', trigger: 'blur'}
          ],
          bankCard: [
            {required: true, message: '请输入开户账号', trigger: 'blur'}
          ],
          bankProv: [
            {required: true, message: '请输入开户省', trigger: 'blur'}
          ],
          realName: [
            {required: true, message: '请输入真实姓名', trigger: 'blur'}
          ],
          status: [
            {required: true, message: '请选择状态', trigger: 'blur'}
          ],
        },
        ruleForm: {
          remark:'',
          bank: '',
          bankAddr: '',
          bankCard: '',
          bankProv: '',
          realName: '',
          status: '',
        }
      };
    },
    methods: {
      updateCallback() {
        const props = [
          'remark',
          'bank',
          'bankAddr',
          'bankCard',
          'bankProv',
          'realName',
          'status',
          'id'
        ];

        const data = {};
        props.map(key => data[key] = this.ruleForm[key]);
        return userApi.updateBank({
          ...data,
          userId: this.userId,
        })
      },
    }
  };
</script>

<style scoped>

</style>

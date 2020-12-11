<template>
  <el-dialog :title="dialogTitle"
             :visible.sync="dialogVisible"
             @close="closeDialog">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px">


      <el-form-item label="币种类型" prop="code">
        <el-input v-model="ruleForm.code" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input v-model="ruleForm.description" class="form-input"></el-input>
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
  import {coinConfigApi} from "@/api/coinConfigApi";
  import dialogMixin from '@/components/mixin/dialog-mixin';

  export default {
    mixins: [dialogMixin],
    data() {
      return {
        dialogTitle: '新建配置',
        rules: {
          code: [
            {required: true, message: '请输入币种类型', trigger: 'blur'}
          ],
          description: [
            {required: true, message: '请输入描述', trigger: 'blur'}
          ],
          status: [
            {required: true, message: '请输入状态', trigger: 'blur'}
          ],
        },
        ruleForm: {
          code:'',
          description:'',
          status: '',
        }
      };
    },
    watch: {
      dialogType(val) {
        if (val === 1) {
          this.dialogTitle = "新建配置";
        } else {
          this.dialogTitle = "编辑配置";
        }
      }
    },
    methods: {
      createCallback() {
        return coinConfigApi.createCoinType(this.ruleForm);
      },

      updateCallback() {
        return coinConfigApi.updateCoinType(this.ruleForm);
      },

    }
  };
</script>

<style scoped>

</style>

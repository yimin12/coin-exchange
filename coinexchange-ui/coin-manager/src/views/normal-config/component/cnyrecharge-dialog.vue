<template>
  <el-dialog
    :title="dialogTitle"
    :visible.sync="dialogVisible"
    width="50%"
    @close="closeDialog"
  >
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
      <el-form-item label="类型" prop="type">
        <!--<el-select v-model="ruleForm.type" placeholder="类型" :disabled="dialogType==2">
          <el-option label="CNY" value="CNY"></el-option>
          <el-option label="SYSTEM" value="SYSTEM"></el-option>
        </el-select>-->
        <el-input v-model="ruleForm.type" class="form-input" :disabled="dialogType==2"></el-input>
      </el-form-item>
      <el-form-item label="规则代码" prop="code">
        <el-input v-model="ruleForm.code" class="form-input" :disabled="dialogType==2"></el-input>
      </el-form-item>
      <el-form-item label="规则名称" prop="name">
        <el-input v-model="ruleForm.name" class="form-input" type="text"></el-input>
      </el-form-item>
      <el-form-item label="规则描述" prop="desc">
        <el-input v-model="ruleForm.desc" class="form-input" type="text"></el-input>
      </el-form-item>
      <el-form-item label="值" prop="value">
        <el-input v-model="ruleForm.value" class="form-input" type="text"></el-input>
      </el-form-item>
    </el-form>

    <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
      </span>
  </el-dialog>
</template>

<script>
  import {normalConfigApi} from "@/api/normalConfigApi";
  import dialogMixin      from '@/components/mixin/dialog-mixin';

  export default {
    mixins : [dialogMixin],
    data() {
      return {
        dialogTitle: '新建配置',
        coinAll    : '',
        rules      : {
          type : [
            {required: true, message: '请输入类型', trigger: 'blur'}
          ],
          code : [
            {required: true, message: '请输入规则代码', trigger: 'blur'}
          ],
          name : [
            {required: true, message: '请输入规则名称', trigger: 'blur'}
          ],
          desc : [
            {required: true, message: '请输入规则描述', trigger: 'blur'}
          ],
          value: [
            {required: true, message: '请输入值', trigger: 'blur'}
          ]
        },
        ruleForm   : {
          type: ''
        }
      };
    },
    watch  : {
      dialogType(val) {
        if (val === 1) {
          this.dialogTitle = "新建配置";
        } else {
          this.dialogTitle = "编辑配置";
        }
      }
    },
    methods: {
      async createCallback() {
        return await normalConfigApi.createConfig(this.ruleForm);
      },
      async updateCallback() {
        return await normalConfigApi.updateConfig(this.ruleForm);
      }

    }
  };
</script>

<style scoped>

</style>



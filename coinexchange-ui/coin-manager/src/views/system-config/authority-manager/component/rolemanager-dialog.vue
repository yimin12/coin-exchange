<template>
  <el-dialog
    :title="dialogTitle"
    :visible.sync="dialogVisible"
    width="50%"
    @close="closeDialog"
  >
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="180px">
      <el-form-item label="角色" prop="name">
        <el-input v-model="ruleForm.name" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="角色编码" prop="code">
        <el-input v-model="ruleForm.code" class="form-input"></el-input>
      </el-form-item>
    </el-form>

    <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
      </span>
  </el-dialog>
</template>

<script>
  import {sysConfigApi} from "@/api/sysConfigApi";
  import dialogMixin from '@/components/mixin/dialog-mixin';

  export default {
    mixins: [dialogMixin],

    data() {
      return {
        dialogTitle: '新建角色权限',
        coinAll: '',
        rules: {
          name: [
            {required: true, message: '请输入角色', trigger: 'blur'}
          ],
          code: [
            {required: true, message: '请输入角色编码', trigger: 'blur'}
          ]
        },
        ruleForm: {}
      };
    },
    watch: {
      dialogType(val) {
        if (val === 1) {
          this.dialogTitle = "新建角色权限";
        } else {
          this.dialogTitle = "编辑角色权限";
        }
      }
    },
    methods: {
      getData() {
        const data = {};
        if (this.dialogType == 2) {
          data['id'] = this.ruleForm.id;
        }
        data['name'] = this.ruleForm.name;
        data['code'] = this.ruleForm.code;
        return data;
      },
      async createCallback() {
        return await sysConfigApi.createRole(this.getData());
      }

    }
  };
</script>

<style scoped>

</style>



<template>
  <el-dialog
    :title="dialogTitle"
    :visible.sync="dialogVisible"
    width="50%"
    @close="closeDialog"
  >
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
      <el-form-item :key="par.id" :label="par.name" v-for="par in configRule">
        <el-checkbox-group v-model="checkList">
          <el-checkbox label="复选框 A"></el-checkbox>
          <el-checkbox label="复选框 B"></el-checkbox>
          <el-checkbox label="复选框 C"></el-checkbox>
          <el-checkbox label="禁用" disabled></el-checkbox>
          <el-checkbox label="选中且禁用" disabled></el-checkbox>
        </el-checkbox-group>
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
  import dialogMixin    from '@/components/mixin/dialog-mixin';

  export default {
    mixins : [dialogMixin],
    data() {
      return {
        dialogTitle: '配置角色权限',
        checkList  : ['选中且禁用', '复选框 A'],
        configRule : [],
        rules      : {
          name: [
            {required: true, message: '请输入角色', trigger: 'blur'}
          ],
          code: [
            {required: true, message: '请输入角色编码', trigger: 'blur'}
          ]
        },
        ruleForm   : {}
      };
    },
    methods: {
      async editCallback(row) {
        let that = this;
         await sysConfigApi.getRolePrivileges(row.id).then(function (data) {
          that.configRule = data;
        });
        console.log(that.configRule);

      },

      createCallback() {
        return sysConfigApi.createRole(this.ruleForm);
      },


      updateCallback() {
        // return sysConfigApi.updateRole(this.ruleForm);
      }

    }
  };
</script>



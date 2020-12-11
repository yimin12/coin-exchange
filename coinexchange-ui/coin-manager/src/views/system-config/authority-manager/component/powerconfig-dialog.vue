<template>
  <el-dialog
    :title="dialogTitle"
    :visible.sync="dialogVisible"
    width="50%"
    @close="closeDialog"
  >
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="180px">
      <el-form-item label="名称" prop="name">
        <el-input v-model="ruleForm.name" class="form-input" :disabled="dialogType==2"></el-input>
      </el-form-item>
      <el-form-item label="描述" prop="description">
        <el-input v-model="ruleForm.description" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="菜单ID" prop="topSelectedMenu" v-if="dialogType==1">
        <el-cascader
          :options="topMenuOptions"
          :show-all-levels="false"
          :props="topMenuProps"
          v-model="ruleForm.topSelectedMenu">
        </el-cascader>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
      </span>
  </el-dialog>
</template>

<script>
  import Tinymce from '@/components/Tinymce';
  import {normalConfigApi} from "@/api/normalConfigApi";
  import dialogMixin from '@/components/mixin/dialog-mixin';
  import {mapGetters} from 'vuex';

  export default {
    mixins: [dialogMixin],

    data() {
      return {
        topMenuOptions: '',
        topMenuProps: {
          value: 'id',
          label: 'title',
          children: 'children',
        },
        dialogTitle: '新建功能权限',
        coinAll: '',
        rules: {
          name: [
            {required: true, message: '请输入名称', trigger: 'blur'}
          ],
          description: [
            {required: true, message: '请输入描述', trigger: 'blur'}
          ],
          topSelectedMenu: [
            {required: true, message: '请选择菜单ID', trigger: 'blur'}
          ]
        },
        ruleForm: {
          name: '',
          description: '',
          topSelectedMenu: [],
        }
      };
    },
    computed: {
      ...mapGetters(['topMenu'])
    },
    created() {
      this.recursionMenu(this.topMenu);
      this.topMenuOptions = this.topMenu;
    },
    watch: {
      dialogType(val) {
        if (val === 1) {
          this.dialogTitle = "新建功能权限";
        } else {
          this.dialogTitle = "编辑功能权限";
        }
      }
    },
    methods: {
      // 递归设置菜单名称
      recursionMenu(data) {
        if (data.length != 0) {
          for (let i = 0; i < data.length; i++) {
            data[i].title = data[i].meta.title;
            if (data[i].children) {
              this.recursionMenu(data[i].children)
            }
          }
        }
      },
      getRequestData() {
        const props = ['id', 'name', 'description', 'menuId'];
        const data = {};
        props.map(key => this.ruleForm[key] ? data[key] = this.ruleForm[key] : '')
        const menuId =
          this.ruleForm.topSelectedMenu ?
            this.ruleForm.topSelectedMenu[this.ruleForm.topSelectedMenu.length - 1] :
            '';

        if (menuId) {
          data['menuId'] = menuId;
        }
        return data;
      },
      // 新建权限
      createCallback() {
        return normalConfigApi.createSysPrivileges(this.getRequestData());
      },
      // 修改权限
      updateCallback() {
        return normalConfigApi.editSysPrivileges(this.getRequestData());
      },
    }
  };
</script>

<style scoped>

</style>

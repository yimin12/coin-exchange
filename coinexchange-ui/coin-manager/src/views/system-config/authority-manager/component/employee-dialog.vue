<template>
  <el-dialog
    :title="dialogTitle"
    :visible.sync="dialogVisible"
    width="50%"
    @close="closeDialog"
  >
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="180px">
      <el-form-item label="用户名" prop="username">
        <el-input v-model="ruleForm.username" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="密码" prop="password">
        <el-input v-model="ruleForm.password" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="姓名" prop="fullname">
        <el-input v-model="ruleForm.fullname" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="ruleForm.mobile" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="邮箱" prop="email">
        <el-input v-model="ruleForm.email" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="角色" prop="role_strings_array">
        <el-checkbox-group v-model="ruleForm.role_strings_array">
          <el-checkbox v-for="item in roleSelect"
                       :label="item.id"
                       :key="item.id">
            {{item.name}}
          </el-checkbox>
        </el-checkbox-group>
      </el-form-item>
      <el-form-item label="状态">
        <el-switch
          v-model="statusFlag"
          active-text="开启"
          inactive-text="关闭">
        </el-switch>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
      </span>
  </el-dialog>
</template>

<script>
  import { sysConfigApi } from '@/api/sysConfigApi'
import dialogMixin from '@/components/mixin/dialog-mixin'

export default {
    mixins: [dialogMixin],

    data() {
      return {
        dialogTitle: '新建员工',
        roleSelect: [],
        statusFlag: '',
        rules: {
          username: [
            { required: true, message: '请输入用户名', trigger: 'blur' }
          ],
          password: [
            { required: true, message: '请输入密码', trigger: 'blur' }
          ],
          fullname: [
            { required: true, message: '请输入姓名', trigger: 'blur' }
          ],
          mobile: [
            { required: true, message: '请输入手机号', trigger: 'blur' }
          ],
          email: [
            { required: true, message: '请输入邮箱', trigger: 'blur' }
          ],
          role_strings_array: [
            { required: true, message: '请选择角色', trigger: 'blur' }
          ]
        },
        ruleForm: {
          username: [],
          email: [],
          password: [],
          fullname: [],
          mobile: [],
          role_strings_array: []
        }
      }
  },
    watch: {
      dialogType(val) {
        if (val === 1) {
          this.statusFlag = 0
          this.dialogTitle = '新建员工'
        } else {
          this.dialogTitle = '编辑员工'
        }
      }
    },
    async mounted() {
      const response = await sysConfigApi.getRoleList({}, 1, 100)
      const { records } = response
      this.roleSelect = records
  },
    methods: {
      editCallback(row) {
        this.statusFlag = row.status === 1
      },
      getResponseData() {

        const props = [
          'username',
          'password',
          'fullname',
          'mobile',
          'email',
          'role_strings',
          'status',
          'id'
        ]
        const data = { ...this.ruleForm }
        const roleSelect = this.roleSelect
        const { role_strings_array = [] } = data
        data.role_strings =
          role_strings_array.map(item => roleSelect.find(child => child.id === item).id).join(',')

        const requestData = {}
        // props.map(key => data[key] === null ? requestData[key] = data[key] : '')
        props.map(key => requestData[key] = data[key])
        return requestData
      },
      createCallback() {
        return sysConfigApi.createEmployee(this.getResponseData())
      },
      updateCallback() {
        return sysConfigApi.updateEmployee(this.getResponseData())
      }

    }
  }
</script>

<style scoped>

</style>



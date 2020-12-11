<template>
  <el-dialog
    :title="dialogTitle"
    :visible.sync="dialogVisible"
    width="50%"
    @close="closeDialog"
  >
    <el-form
      :model="ruleForm"
      :rules="rules"
      ref="ruleForm"
      label-width="100px">


      <el-form-item label="名称" prop="name">
        <el-input :disabled="dialogType==2" v-model="ruleForm.name" class="form-input"></el-input>
      </el-form-item>

      <el-form-item label="标题" prop="title">
        <el-input :disabled="dialogType==2" v-model="ruleForm.title" class="form-input"></el-input>
      </el-form-item>

      <el-form-item label="排序" prop="sort">
        <el-input-number v-model="ruleForm.sort" class="form-input"></el-input-number>
      </el-form-item>

      <el-form-item label="状态" prop="status">
        <el-select v-model="ruleForm.status" class="form-input">
          <el-option v-for="(value,key) in select.status"
                     :key="key"
                     :label="value"
                     :value="key"></el-option>
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
  import {forexConfigApi} from '@/api/coinConfigApi'
  import {selectOption} from '../../trade-config/common'
  import dialogMixin from '@/components/mixin/dialog-mixin'

  export default {
    mixins: [dialogMixin],
    data() {
      return {
        select: selectOption,
        dialogTitle: '新建创新交易配置',
        rules: {
          name: [
            {required: true, message: '请输入名称'},
          ],
          title: [
            {required: true, message: '请输入标题'},
          ],
          sort: [
            {required: true, message: '请输入排序'},
          ],
          status: [
            {required: true, message: '请选择状态'},
          ],
        },
        ruleForm: {
          name: '',
          title: '',
          sort: '',
          status: '',
        }
      }
    },
    watch: {
      dialogType(val) {
        if (val === 1) {
          this.dialogTitle = "新建创新交易币种配置"
        } else {
          this.dialogTitle = "编辑创新交易币种配置"
        }
      }
    },
    methods: {
      createCallback() {
        return forexConfigApi.addForexCoin(this.ruleForm)
      },
      updateCallback() {
        return forexConfigApi.editForexCoin(this.ruleForm)
      },

    }
  }
</script>

<style scoped>

</style>

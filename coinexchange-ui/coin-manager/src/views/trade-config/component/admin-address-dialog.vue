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

      <!-- <el-form-item label="钱包币种" prop="coinType">
        <el-select v-model="ruleForm.coinType" class="form-input">
          <el-option v-for="item in select.type"
                     :key="item.id"
                     :label="item.name"
                     :value="item.type"></el-option>
        </el-select>
      </el-form-item> -->

      <el-form-item label="钱包地址" prop="address">
        <el-input v-model="ruleForm.address" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="地址类型" prop="status">
        <el-select v-model="ruleForm.status" class="form-input">
          <el-option v-for="(value,key) in select.adminAddressStatus"
                     :key="key"
                     :label="value"
                     :value="key"></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="密钥" prop="keystore">
        <el-input v-model="ruleForm.keystore" class="form-input" placeholder="私钥或者keystore(eth系列币必须填写keystore,暂不支持私钥)"></el-input>
      </el-form-item>

      <el-form-item label="密码" prop="pwd">
        <el-input v-model="ruleForm.pwd" class="form-input" placeholder="只有eth,etc系列需要填写密码"></el-input>
      </el-form-item>

    </el-form>

    <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
      </span>
  </el-dialog>
</template>

<script>
  import {selectOption} from '../common'
  import {coinConfigApi} from '@/api/coinConfigApi'
  import dialogMixin from '@/components/mixin/dialog-mixin'

  export default {
    mixins: [dialogMixin],
    data() {
      return {
        select: selectOption,
        dialogTitle: '新建钱包归集地址',
        rules: {
          coinType: [
            {required: true, message: '请选择币种类型'},
          ],
          address: [
            {required: true, message: '请输入钱包地址'},
          ],
          keystore: [
          ],
          pwd: [
          ],
          status: [
            {required: true, message: '请选择状态'},
          ],
        },
        ruleForm: {
          coinId: '',
          coinType: '',
          address: '',
          keystore: '',
          pwd: '',
          status: '',
        }
      }
    },
    watch: {
      dialogType(val) {
        if (val === 1) {
          this.dialogTitle = "新建钱包归集地址"
        } else {
          this.dialogTitle = "编辑钱包归集地址"
        }
      }
    },
    methods: {
      createCallback() {
        return coinConfigApi.addAdminAddress(this.ruleForm)
      },
      updateCallback() {
        return coinConfigApi.editAdminAddress(this.ruleForm)
      },

    }
  }
</script>

<style scoped>

</style>

<template>
  <el-dialog
    :title="dialogTitle"
    :visible.sync="dialogVisible"
    width="50%"
    @close="closeDialog"
  >
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="180px">
      <el-form-item label="开户人姓名" prop="name">
        <el-input v-model="ruleForm.name" class="form-input"></el-input>

      </el-form-item>
      <el-form-item label="开户行（包含支行名称）" prop="bankName">
        <el-input v-model="ruleForm.bankName" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="卡号" prop="bankCard">
        <el-input v-model="ruleForm.bankCard" class="form-input" type="text"></el-input>
      </el-form-item>
     <!-- <el-form-item label="币种">
        <el-select v-model="ruleForm.coinId" placeholder="币种">
          <el-option :label="item.name" @change="updateCoinType(item)" :value="item.id" v-for="item in coinAll" :key="item.id"></el-option>
        </el-select>
      </el-form-item>-->
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
  import Tinymce from '@/components/Tinymce'
import { normalConfigApi } from '@/api/normalConfigApi'
import dialogMixin from '@/components/mixin/dialog-mixin'
import {
    mapGetters
  } from 'vuex'

export default {
    components: { Tinymce },
    mixins: [dialogMixin],
    computed: {
      ...mapGetters(['getCoinAll'])
    },
    mounted() {
      this.coinAll = this.getCoinAll
  },
    data() {
      return {
        dialogTitle: '新建银行卡',
        coinAll: '',
        rules: {
          name: [
            { required: true, message: '请输入开户人姓名', trigger: 'blur' }
          ],
          bankName: [
            { required: true, message: '请输入开户行名称', trigger: 'blur' }
          ],
          bankCard: [
            { required: true, message: '请输入卡号', trigger: 'blur' }
          ]
        },
        ruleForm: {
          coinId: null,
          coinName: null,
          status: null
        },
        statusFlag: ''
      }
  },
    watch: {
      dialogType(val) {
        if (val === 1) {
          this.statusFlag = 0
          this.dialogTitle = '新建银行卡'
        } else {
          this.dialogTitle = '编辑银行卡'
        }
      },
      statusFlag(val) {
        if (val) {
          this.ruleForm.status = 1
        } else {
          this.ruleForm.status = 0
        }
      }
    },
    methods: {
      editCallback(row) {
        this.ruleForm.coinId = row.coinId !== null ? row.coinId.toString() : '0'
        this.statusFlag = row.status === 1
      },

      updateCoinType(item) {
        this.ruleForm.coinId = item.id
        this.ruleForm.coinName = item.name
      },

      createCallback() {
        this.updateRuleForm()
        return normalConfigApi.createBank(this.ruleForm)
      },

      updateRuleForm() {
        this.ruleForm.status ? this.ruleForm.status = 1 : this.ruleForm.status = 0
        for (let i = 0; i < this.coinAll.length; i++) {
          if (this.ruleForm.coinId === this.coinAll[i].id) {
            this.ruleForm.coinName = this.coinAll[i].name
          }
        }
      },

      updateCallback() {
        this.updateRuleForm()
        return normalConfigApi.updateBank(this.ruleForm)
      }

    }
  }
</script>

<style scoped>

</style>

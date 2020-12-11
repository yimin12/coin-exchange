<template>
  <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" @close="closeDialog">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px">
      <el-form-item label="名称" prop="name">
        <el-input v-model="ruleForm.name" class="form-input"></el-input>
      </el-form-item>

      <el-form-item label="代码" prop="code">
        <el-input v-model="ruleForm.code" class="form-input"></el-input>
      </el-form-item>

      <el-form-item label="结算币种" prop="coinId">
        <el-select v-model="ruleForm.coinId" placeholder="结算币种">
          <el-option :label="item.name" :value="item.id" v-for="item in coinAll"
                     :key="item.id"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="资产统计标识" prop="baseCoin">
        <el-select v-model="ruleForm.baseCoin" placeholder="资产统计标识">
          <el-option label="否" value="0"></el-option>
          <el-option label="是" value="1"></el-option>
        </el-select>
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
  import {tradeAreaApi} from "@/api/tradeAreaApi";
  import {forexConfigApi,marketApi} from '@/api/coinConfigApi'
  import dialogMixin from '@/components/mixin/dialog-mixin';

  export default {
    mixins: [dialogMixin],
    data() {
      return {
        dialogTitle: '新建配置',
        coinAll: '',
        rules: {
          code: [
            {required: true, message: '请输入代码', trigger: 'blur'}
          ],
          name: [
            {required: true, message: '请输入名称', trigger: 'blur'}
          ],
          type: [
            {required: true, message: '请输入类型', trigger: 'blur'}
          ],
          baseCoin: [
            {required: true, message: '请输入资产统计标识', trigger: 'blur'}
          ],
          status: [
            {required: true, message: '请输入状态', trigger: 'blur'}
          ],
          coinId: [
            {required: true, message: '请输入结算币种', trigger: 'blur'}
          ],
        },
        ruleForm: {
          status: '',
          type: '',
        }
      };
    },
    async mounted() {
      this.coinAll = await marketApi.getCoinAll()
    },
    watch: {
      dialogType(val) {
        if (val === 1) {
          this.dialogTitle = "新建区域配置";
        } else {
          this.dialogTitle = "编辑区域配置";
        }
      }
    },
    methods: {
      createCallback() {

        const {coinId} = this.ruleForm;
        const coinName = this.coinAll.find(item => item.id == coinId).title
        return tradeAreaApi.createdInnovateTradeArea({
          ...this.ruleForm,
          coinName,
        });
      },
      updateCallback() {
        return tradeAreaApi.updateInnovateTradeArea(this.ruleForm);
      },

    }
  };
</script>

<style scoped>

</style>

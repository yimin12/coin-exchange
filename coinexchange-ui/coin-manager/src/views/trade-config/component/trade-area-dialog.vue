<template>
  <el-dialog :title="dialogTitle" :visible.sync="dialogVisible" @close="closeDialog">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="150px">
      <el-form-item label="名称" prop="name">
        <el-input v-model="ruleForm.name" class="form-input"></el-input>
      </el-form-item>

      <el-form-item label="代码" prop="code">
        <el-input v-model="ruleForm.code" class="form-input"></el-input>
      </el-form-item>

      <!-- 1 币币交易 2 创新交易 -->
      <!--<el-form-item label="类型" prop="type">-->
      <!--<el-select v-model="ruleForm.type" placeholder="类型">-->
      <!--<el-option label="币币交易" :value="1"></el-option>-->
      <!--<el-option label="创新交易" :value="2"></el-option>-->
      <!--</el-select>-->
      <!--</el-form-item>-->

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
        <el-tooltip class="item" effect="dark" content="币币交易统计用户资产的单位，一般结算币种为USDT时设置为是，结算币种不是USDT请设置为否" placement="top">
          <i style="color:#E6A23C" class="el-icon-question"></i>
        </el-tooltip>

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
  import dialogMixin from '@/components/mixin/dialog-mixin';
  import {mapGetters} from 'vuex'

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
          // type : [
          //   {required: true, message: '请输入类型', trigger: 'blur'}
          // ],
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
    computed: {
      ...mapGetters(['getCoinAll'])
    },
    created() {
      this.coinAll = this.getCoinAll;
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
        this.coinAll.forEach((i, k) => {
          if (i.id == this.ruleForm.coinId) {
            this.ruleForm.coinName = i.title;
          }
        })
        return tradeAreaApi.createdTradeArea(this.ruleForm);
      },

      updateCallback() {
        return tradeAreaApi.updateTradeArea(this.ruleForm);
      },

    }
  };
</script>

<style scoped>

</style>

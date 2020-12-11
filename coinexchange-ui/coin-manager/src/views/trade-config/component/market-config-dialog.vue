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
      label-width="150px">

      <el-form-item label="交易区域" prop="tradeAreaId">
        <el-select v-model="ruleForm.tradeAreaId" @change="changeTradeArea" class="form-input"
                   :disabled="dialogType==21">
          <el-option v-for="item in tradeAreaSelect"
                     :key="item.id"
                     :label="item.name"
                     :value="item.id"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="报价货币" prop="buyCoinId">
        <el-select v-model="ruleForm.buyCoinId" class="form-input" :disabled="dialogType==2">
          <el-option v-for="item in coinidSelect"
                     :key="item.id"
                     :label="item.name"
                     :value="item.id"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="基础货币" prop="sellCoinId">
        <el-select v-model="ruleForm.sellCoinId" class="form-input" :disabled="dialogType==2">
          <el-option v-for="item in coinidSelect"
                     :key="item.id"
                     :label="item.name"
                     :value="item.id"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="开盘价" prop="openPrice">
        <el-input-number v-model="ruleForm.openPrice" class="form-input"></el-input-number>
      </el-form-item>

      <el-form-item label="交易周期" prop="tradeWeekArray">
        <el-checkbox-group v-model="ruleForm.tradeWeekArray">
          <el-checkbox :label="item" v-for="item in tradeWeekData" :key="item.id"></el-checkbox>
        </el-checkbox-group>
      </el-form-item>

      <el-form-item label="交易时间" prop="tradeTimeArray">
        <el-time-picker
          is-range
          v-model="ruleForm.tradeTimeArray"
          range-separator="至"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
          placeholder="选择时间范围">
        </el-time-picker>
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

      <el-form-item label="买入手续费" prop="feeBuy">
        <el-input-number v-model="ruleForm.feeBuy">
        </el-input-number>
      </el-form-item>

      <el-form-item label="卖出手续费" prop="feeSell">
        <el-input-number v-model="ruleForm.feeSell">
        </el-input-number>
      </el-form-item>

      <el-form-item label="价格小数位数" prop="priceScale">
        <el-input-number v-model="ruleForm.priceScale"
                         :min="minPriceScale"
                         :max="maxPriceScale"
                         @change="changePriceScale">
        </el-input-number>
      </el-form-item>

      <el-form-item label="合并深度" prop="mergeDepthArray" v-if="this.marketType==1">
        <el-checkbox-group v-model="ruleForm.mergeDepthArray">
          <el-checkbox :label="item"
                       v-for="item in ruleForm.mergeDepthData"
                       :key="item.id"></el-checkbox>
        </el-checkbox-group>
      </el-form-item>

      <el-form-item label="数量小数位数" prop="numScale">
        <el-input-number v-model="ruleForm.numScale">
        </el-input-number>
      </el-form-item>

      <el-form-item label="最小委托" prop="numMin">
        <el-input-number v-model="ruleForm.numMin">
        </el-input-number>
      </el-form-item>

      <el-form-item label="最大委托" prop="numMax">
        <el-input-number v-model="ruleForm.numMax">
        </el-input-number>
      </el-form-item>

      <el-form-item :label="item.name"
                    :prop="item.props"
                    v-if="item.type!='string'"
                    :key="item.type"
                    v-for="item in otherFiels">
        <el-input-number v-model="ruleForm[item.props]">
        </el-input-number>
      </el-form-item>

      <el-form-item :label="item.name"
                    :prop="item.props"
                    v-if="item.type=='string'"
                    :key="item.type"
                    v-for="item in otherFiels">
        <el-input v-model="ruleForm[item.props]">
        </el-input>
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
  import {marketApi} from '@/api/coinConfigApi'
  import dialogMixin from '@/components/mixin/dialog-mixin'

  export default {
    mixins: [dialogMixin],
    props: [
      'marketType',
      'otherRules',
      'otherRuleForm',
      'otherFiels',
      'tradeAreaAPI',
      'buyCoinIdAPI',
      'coinidAPI',
    ],
    data() {
      let that = this;
      let validateDepth = function (rule, value, callback) {
        if (value.length !== 3) {
          callback(new Error('请选择3个合并深度!'));
        } else {
          callback();
        }
      };
      return {
        tradeAreaSelect: [],
        coinidSelect: [],
        select: selectOption,
        tradeWeekData: selectOption.tradeWeekData,
        dialogTitle: '新建市场',
        minPriceScale: 0,
        maxPriceScale: 8,
        rules: {
          tradeAreaId: [
            {required: true, message: '请选择交易区域'},
          ],
          buyCoinId: [
            {required: true, message: '请选择报价货币'},
          ],
          sellCoinId: [
            {required: true, message: '请选择基础货币'},
          ],
          openPrice: [
            {required: true, message: '请输入开盘价'},
          ],
          tradeWeekArray: [
            {required: true, message: '请输入交易周期'},
          ],

          tradeTimeArray: [
            {required: true, message: '请输入交易时间'},
          ],
          sort: [
            {required: true, message: '请输入排序'},
          ],
          status: [
            {required: true, message: '请选择状态'},
          ],
          mergeDepthArray: [
            {required: true, message: '请选择合并深度'},
            {validator: validateDepth, message: '请选择3个合并深度', trigger: blur}
          ],
          feeBuy: [
            {required: true, message: '请输入买入手续费'},
          ],

          feeSell: [
            {required: true, message: '请输入卖出手续费'},
          ],
          priceScale: [
            {required: true, message: '请输入价格小数位数'},
          ],
          numScale: [
            {required: true, message: '请输入数量小数位数'},
          ],
          numMin: [
            {required: true, message: '请输入最小委托量'},
          ],
          numMax: [
            {required: true, message: '请输入最大委托量'},
          ],


        },
        ruleForm: {
          tradeAreaId: '',
          buyCoinId: '',
          sellCoinId: '',
          openPrice: '',
          tradeWeekArray: [],

          tradeTimeArray: '',
          sort: '',
          status: '',
          mergeDepthArray: [],
          mergeDepthData: [],
          feeBuy: '',

          feeSell: '',
          priceScale: '',
          numScale: '',
          numMin: '',
          numMax: '',
        }
      }
    },
    async mounted() {
      this.rules = {
        ...this.rules,
        ...this.otherRules,
      }
      this.ruleForm = {
        ...this.ruleForm,
        ...this.otherRuleForm,
      }

      if (this.marketType == 1) {
        const [tradeAreaSelect, coinidSelect] = await Promise.all([
          this.tradeAreaAPI(),
          this.coinidAPI()
        ]);
        this.tradeAreaSelect = tradeAreaSelect;
        this.coinidSelect = coinidSelect;
        this.minPriceScale = 2;
        this.maxPriceScale = 8;
      }
      else {
        const tradeAreaSelect = await this.tradeAreaAPI();
        this.tradeAreaSelect = tradeAreaSelect;
        this.minPriceScale = 0;
        this.maxPriceScale = 8;
        this.changeTradeArea();
      }
    },
    watch: {
      dialogType(val) {
        if (val === 1) {
          this.dialogTitle = "新建市场"
        } else {
          this.dialogTitle = "编辑市场"
        }
      }
    },
    methods: {
      editCallback(row) {
        if (this.marketType == 2) {
          this.changeTradeArea();
        }
      },
      // 交易区域chang
      async changeTradeArea() {
        if (this.marketType == 1 || !this.ruleForm.tradeAreaId) {
          return;
        }
        const tradeId = this.ruleForm.tradeAreaId;
        const coinidSelect = await this.coinidAPI(tradeId);
        this.coinidSelect = coinidSelect;
      },
      // 价格小数位数 change 事件
      changePriceScale(val) {
        const data = selectOption.mergeDepthData;
        this.ruleForm.mergeDepthData = selectOption.createMergeDepthData(val, data);
      },
      // 获取请求的 data
      getRequestData() {
        const {tradeTimeArray, tradeWeekArray, mergeDepthArray} = this.ruleForm;
        const mergeDepthData = selectOption.mergeDepthData;
        const converToTimeStr = (date) => {
          const hours = date.getHours();
          const mins = date.getMinutes();
          const add0 = (i) => {
            return i < 10 ? `0${i}` : i;
          }
          return `${add0(hours)}:${add0(mins)}`;
        }

        const data = {
          ...this.ruleForm,
          pointValue: `0.${'0'.repeat(this.ruleForm.priceScale - 1)}1`,
          tradeWeek: tradeWeekArray.map(item => (this.tradeWeekData.indexOf(item) + 1)).join(','),
          tradeTime: `${converToTimeStr(tradeTimeArray[0])}-${converToTimeStr(tradeTimeArray[1])}`,
          mergeDepth: mergeDepthArray.map(item => (mergeDepthData.indexOf(item))).join(','),
        }

        delete data.mergeDepthData;
        delete data.mergeDepthArray;
        delete data.tradeTimeArray;
        delete data.tradeWeekArray;
        return data;
      },
      createCallback() {
        return marketApi.addMarket(this.getRequestData())
      },
      updateCallback() {
        return marketApi.editMarket(this.getRequestData())
      },

    }
  }
</script>

<style scoped>

</style>

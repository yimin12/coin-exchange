<template>
  <div class="common-main">
    <el-form
      :inline="true"
      :model="searchForm"
      ref="searchForm"
      class="demo-form-inline"
      :label-position="'right'"
      label-width="80px">

      <el-form-item label="交易区域" prop="tradeAreaId">
        <el-select v-model="searchForm.tradeAreaId" class="form-input" clearable>
          <el-option v-for="item in tradeAreaSelect"
                     :key="item.id"
                     :label="item.name"
                     :value="item.id"></el-option>
        </el-select>
      </el-form-item>

      <!--<el-form-item label="市场名称" prop="coinId">
        <el-select v-model="searchForm.coinId" class="form-input">
          <el-option v-for="item in coinSelect"
                     :key="item.id"
                     :label="item.name"
                     :value="item.id"></el-option>
        </el-select>
      </el-form-item>-->

      <el-form-item label="状态" prop="status">
        <el-select v-model="searchForm.status" class="form-input" clearable>
          <el-option v-for="(value,key) in select.status"
                     :key="key"
                     :label="value"
                     :value="key"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button icon="el-icon-search" @click="submitForm('searchForm')">搜索
        </el-button>
        <el-button type="primary" icon="el-icon-edit" @click="handleCreate">新建</el-button>
      </el-form-item>
    </el-form>

    <div class="table">
      <el-table
        :data="listData"
        style="width: 100%"
        v-loading="listLoading"
      >

        <el-table-column
          prop="id"
          label="市场ID"
          width="200">
        </el-table-column>

        <el-table-column
          prop="name"
          label="市场名称"
          width="150">
        </el-table-column>

        <el-table-column
          prop="symbol"
          label="市场标识符"
          width="150">
        </el-table-column>

        <el-table-column
          prop="title"
          label="市场标题"
          width="150">
        </el-table-column>

        <el-table-column
          prop="img"
          label="市场图标"
          width="160"
          v-if="marketType==1"
        >
          <template slot-scope="scope">
            <img class="logoImg" v-bind:src="scope.row.img"/>
          </template>
        </el-table-column>

        <el-table-column
          prop="openPrice"
          label="开盘价">
        </el-table-column>

        <el-table-column
          prop="feeBuy"
          label="买入手续费"
          width="100">
        </el-table-column>

        <el-table-column
          prop="feeSell"
          label="卖出手续费"
          width="100">
        </el-table-column>

        <el-table-column
          prop="sort"
          label="排序"
        >
        </el-table-column>

        <el-table-column
          prop="status"
          render="renderHeader"
          label="状态"
        >
          <template slot-scope="scope">
            <el-tag :type="scope.row.status|elTagFilter">
              {{select.status[scope.row.status]}}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column
          label="操作"
          width="250"

        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="primary"
              @click="handleEdit(scope.$index, scope.row)" icon="el-icon-edit">编辑
            </el-button>

            <el-button
              size="mini"
              v-show="scope.row.status===0"
              @click="handleChangeStatus(scope.$index, scope.row)" icon="el-icon-edit">启用
            </el-button>

            <el-button
              size="mini"
              type="danger"
              v-show="scope.row.status===1"
              @click="handleChangeStatus(scope.$index, scope.row)" icon="el-icon-edit">禁用
            </el-button>
          </template>
        </el-table-column>

      </el-table>
      <el-pagination
        class="pagination-container"
        background
        layout="total,prev, pager, next"
        :current-page.sync="listQuery.current"
        :page-size="listQuery.size"
        :total="listQuery.total"
        @current-change="handlePageChange"
      >
      </el-pagination>
    </div>

    <market-config-dialog ref="marketConfigDialog"
                          :marketType="marketType"
                          :otherRules="otherRules"
                          :otherRuleForm="otherRuleForm"
                          :otherFiels="otherFiels"
                          :tradeAreaAPI="tradeAreaSelectAPI"
                          :coinidAPI="coinidAPI"
                          @refreshList="_getList"></market-config-dialog>

  </div>
</template>

<script>
  import marketConfigDialog from './market-config-dialog.vue'
  import {marketApi} from "@/api/coinConfigApi";
  import commonMixin from '@/components/mixin/common-mixin'
  import {selectOption} from '../common'

  export default {
    components: {marketConfigDialog},
    mixins: [commonMixin],
    props: [
      'marketType',
      'searchListAPI',
      'tradeAreaSelectAPI',
      'coinSelectAPI',
      'otherRules',
      'otherRuleForm',
      'otherFiels',
      'coinidAPI',
    ],
    data() {
      return {
        tradeAreaSelect: [],
        coinSelect: [],
        select: selectOption,
        rules: {
          tradeAreaId: [
            {required: true, message: '请选择交易区域'},
          ],
          coinId: [
            {required: true, message: '请选择交易区域'},
          ],
          status: [
            {required: true, message: '请选择交易区域'},
          ],
        },
        searchForm: {
          tradeAreaId: '',
          coinId: '',
          status: '',
        },
      }
    },
    async mounted() {
      let [tradeAreaSelect, coinSelect] = await Promise.all([
        this.tradeAreaSelectAPI(),
        // this.coinSelectAPI(),
      ]);
      this.tradeAreaSelect = tradeAreaSelect;
      this.coinSelect = coinSelect;
    },
    methods: {
      // 启用/禁用 回调
      async changeStatusCallback(id, status) {
        return await marketApi.setStatus({
          id,
          status,
        });
      },
      // 列表 回调
      listCallback() {
        return this.searchListAPI(this.searchForm,
          this.listQuery.current, this.listQuery.size)
      },
      // 新增/编辑 赋值
      getDialogData(row) {
        const {
          tradeWeek,
          tradeTime,
          mergeDepth,

          tradeAreaId = '',
          buyCoinId = '',
          sellCoinId = '',
          status = '',
          openPrice = '1',
          sort = '1',
          feeBuy = '1',
          feeSell = '1',
          priceScale = '2',
          numScale = '1',
          numMin = '1',
          numMax = '1',
          tradeMin = '1',
          tradeMax = '1',
          contractUnit = '1',
          marginRate = '1',
          pointValue = '1',
        } = row;

        // 交易周期
        const finallyTradeWeek = tradeWeek ? tradeWeek : '1,2,3,4,5,6,7';
        const tradeWeekData = selectOption.tradeWeekData;
        const tradeWeekArray = finallyTradeWeek.split(',').map(item => tradeWeekData[item - 1]);

        // 交易时间
        let tradeTimeArray = [];
        if (tradeTime) {
          tradeTimeArray = tradeTime.split('-');
          tradeTimeArray = tradeTimeArray.map(item => {
            const temp = item.split(':');
            const add0 = (i) => (i < 10 ? `0${i}` : i);
            return new Date(2018, 10, 10, add0(temp[0]), add0(temp[1]), '00');
          })
        }
        else {
          tradeTimeArray = [new Date(2018, 10, 10, '00', '00', '00'), new Date(2018, 10, 10, 23, 59, '00')]
        }

        // 合并深度
        const allMergeDepthData = selectOption.mergeDepthData;
        const mergeDepthData = selectOption.createMergeDepthData(priceScale, allMergeDepthData);
        const finallMergeDepth = mergeDepth ? mergeDepth : '0,1,2';
        const mergeDepthArray = finallMergeDepth.split(',').map(item =>
          (allMergeDepthData[item]));

        const data = {
          ...row,
          tradeWeekArray,
          tradeTimeArray,
          openPrice,
          sort,
          feeBuy,
          feeSell,
          priceScale,
          numScale,
          numMin,
          numMax,
          tradeMin,
          tradeMax,
          mergeDepthData,
          mergeDepthArray,
          contractUnit,
          marginRate,
          pointValue,
          tradeAreaId: tradeAreaId + '',
          buyCoinId: buyCoinId + '',
          sellCoinId: sellCoinId + '',
          mergeDepth: mergeDepth ? (mergeDepth + '') : mergeDepth,
          status: status + '',
          type: this.marketType
        }
        return data;
      },
      // 新增
      handleCreate() {
        this.$refs.marketConfigDialog.showDialog(1, this.getDialogData({}));
      },
      // 编辑
      handleEdit(index, row) {
        this.$refs.marketConfigDialog.showDialog(2, this.getDialogData(row));
      },
    }
  }
</script>

<style scoped>

</style>

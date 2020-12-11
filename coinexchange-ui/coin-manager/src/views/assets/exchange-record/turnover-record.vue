<template>
  <div class="common-main">
    <el-form ref="turnoverRecordForm"
             :inline="true"
             :model="ruleForm"
             class="search-container"
             :label-position="'right'"
             label-width="80px">

      <el-form-item label="交易市场">
        <el-select v-model="ruleForm.marketId" placeholder="交易市场" class="form-input" clearable>
          <el-option label="全部" value=""></el-option>
          <el-option :label="item.name" :value="item.id" v-for="item in marketAll"
                     :key="item.id"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="交易方式">
        <el-select v-model="ruleForm.tradeType" placeholder="交易方式" class="form-input" clearable>
          <el-option label="全部" value=""></el-option>
          <el-option label="买" value="1"></el-option>
          <el-option label="卖" value="2"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="订单ID">
        <el-input v-model="ruleForm.orderId" placeholder="订单ID" class="form-input"
                  clearable></el-input>
      </el-form-item>

      <el-form-item label="用户ID" :span="8">
        <el-input v-model="ruleForm.userId" placeholder="用户ID" class="form-input"
                  clearable></el-input>
      </el-form-item>

      <el-form-item label="用户名" :span="8">
        <el-input v-model="ruleForm.userName" placeholder="用户名" class="form-input"
                  clearable></el-input>
      </el-form-item>

      <el-form-item label="手机号" :span="8">
        <el-input type="number" v-model.number="ruleForm.mobile"
                  placeholder="手机号" class="form-input" clearable></el-input>
      </el-form-item>

      <el-form-item label="时间">
        <el-date-picker v-model="ruleForm.dateRange" :editable="false"
                        value-format="yyyy-MM-dd" format="yyyy-MM-dd" type="datetimerange"
                        start-placeholder="开始日期" end-placeholder="结束日期"
                        clearable>
        </el-date-picker>
      </el-form-item>

      <el-form-item label=" ">
        <el-button icon="el-icon-search" @click="submitForm('turnoverRecordForm')">搜索</el-button>
        <el-button
          type="primary"
          icon="el-icon-edit"
          @click="exportExcel('turnoverRecordForm','/turnoverOrder/exportList','币币交易成交')"
          :disabled="listData.length == 0">导出
        </el-button>
      </el-form-item>
    </el-form>

    <div class="table">
      <el-table :data="listData" style="width: 100%" v-loading="listLoading">
        <el-table-column prop="id" label="订单ID" width="180">
        </el-table-column>

        <el-table-column prop="marketName" label="交易市场" width="150">
        </el-table-column>

        <el-table-column prop="buyOrderId" label="买方委托ID" width="180">
        </el-table-column>

        <el-table-column prop="buyUserId" label="买方用户ID" width="180">
        </el-table-column>

        <el-table-column prop="sellOrderId" label="卖方委托ID" width="180">
        </el-table-column>

        <el-table-column prop="sellUserId" label="卖方用户ID" width="180">
        </el-table-column>

        <el-table-column prop="price" label="成交价">
        </el-table-column>

        <el-table-column prop="volume" label="成交量">
        </el-table-column>

        <el-table-column prop="amount" label="成交额">
        </el-table-column>

        <el-table-column prop="dealBuyFee" label="买方手续费" width="120">
        </el-table-column>

        <el-table-column prop="dealSellFee" label="卖方手续费" width="120">
        </el-table-column>

        <el-table-column prop="tradeType" label="交易方式">
          <template slot-scope="scope">
            {{scope.row.tradeType | entrustManagerListTradtypeFilter}}
          </template>
        </el-table-column>

        <el-table-column prop="status" label="状态">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status| elTagFilter">
              {{scope.row.status | entrustManagerListStatusFilter}}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="created" label="成交时间" width="180">
        </el-table-column>

      </el-table>
    </div>

    <el-pagination class="pagination-container" background layout="total,prev, pager, next"
                   :current-page.sync="listQuery.current" :page-size="listQuery.size"
                   :total="listQuery.total" @current-change="handlePageChange">
    </el-pagination>


  </div>
</template>

<script>
  import commonMixin from '@/components/mixin/common-mixin'
  import {mapGetters} from 'vuex'
  import {assetsApi} from '@/api/assetsApi'

  export default {
    mixins: [commonMixin],
    data() {
      return {
        marketAll: '',
        ruleForm: {
          marketId: '',
          tradeType: '',
          type: 1,//1 币币交易 2 创新交易
          userId: '',
          orderId: '',
          userName: '',
          dateRange: '',
          mobile: '',
        },
      }
    },
    computed: {
      ...mapGetters(['getMarketAll'])
    },
    mounted() {
      this.marketAll = this.getMarketAll
    },
    methods: {
      // 请求数据列表
      listCallback() {
        return assetsApi.getTurnoverRecordList(this.ruleForm, this.listQuery.current, this.listQuery.size)
      },

    }
  }
</script>

<style scoped>

</style>

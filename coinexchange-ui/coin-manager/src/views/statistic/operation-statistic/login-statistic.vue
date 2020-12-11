<template>
  <div class="common-main">
    <!-- Search Form -->
    <el-form :rules="rules" :model="ruleForm" ref="ruleForm" label-width="100px" class="search-container">
      <el-form-item label="统计时间" prop="countTime">
        <el-date-picker
          v-model="ruleForm.dateRange"
          type="daterange"
          align="right"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :picker-options="datePickerOptions"
          value-format="yyyy-MM-dd">
        </el-date-picker>
      </el-form-item>
      
      <div class="operation-container">
        <el-button icon="el-icon-search" @click="submitForm('ruleForm')">搜索</el-button>
      </div>
    </el-form>
    <!-- Search Form End -->

    <!-- Table -->
    <el-table
      ref="multipleTable"
      :data="listData"
      tooltip-effect="dark"
      style="width:100%"
      @selection-change="handleSelectionChange"
      v-loading="listLoading"
    >
      <el-table-column 
        label="统计时间"
        prop="loginDate"
      >
      </el-table-column>
      <el-table-column 
        label="登录人数"
        prop="loginNum"
      >
      </el-table-column>
      <el-table-column 
        label="参与交易人数"
        prop="tradeNum"
      >
      </el-table-column>
      <el-table-column 
        label="充值人数"
        prop="rechargeNum"
      >
      </el-table-column>
      <el-table-column 
        label="提现人数"
        prop="withdrawNum"
      >
      </el-table-column>
      <el-table-column 
        label="充币人数"
        prop="rechargeCoinNum"
      >
      </el-table-column>
      <el-table-column 
        label="提币人数"
        prop="withdrawCoinNum"
      >
      </el-table-column>
    </el-table>
    <!-- Table End -->

    <!-- Page -->
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
    <!-- Page End -->
  </div>
</template>

<script>
import {countApi} from "@/api/countApi";
import commonMixin from "@/components/mixin/common-mixin"
export default {
  mixins:[commonMixin],
  data() {
    return {
      rules:{},
      ruleForm:{}
    }
  },
  methods: {
    listCallback(){
      let url = '/users/count/login';
      return countApi.getCountList(this.ruleForm,this.listQuery.current, this.listQuery.size,url);
    }
  }
}
</script>
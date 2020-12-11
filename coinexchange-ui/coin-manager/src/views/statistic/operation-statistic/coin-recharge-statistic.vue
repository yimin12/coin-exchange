<!-- 充币统计 -->
<template>
  <div class="common-main">
    <!-- Search Form -->
    <el-form :rules="rules" :model="ruleForm" ref="ruleForm" label-width="100px" class="search-container">
      <el-form-item label="统计时间" prop="countTime">
        <!-- 日期选择器 -->
        <el-date-picker
          v-model="ruleForm.dateRange"
          type="daterange"
          align="right"
          unlink-panels
          range-separator="-"
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
      style="width: 100%"
      @selection-change="handleSelectionChange"
      v-loading="listLoading"
    >
    <el-table-column
      prop="created"
      label="统计时间"
    >
    </el-table-column>

    <el-table-column
      prop="sumAmount"
      label="充值金额"
    >
    </el-table-column>

    <!--<el-table-column-->
      <!--prop="sumMum"-->
      <!--label="到账金额"-->
    <!--&gt;-->
    <!--</el-table-column>-->

    <el-table-column
      prop="counts"
      label="充值笔数"
    >
    </el-table-column>

    <!--<el-table-column-->
      <!--prop="validCounts"-->
      <!--label="成功笔数"-->
    <!--&gt;-->
    <!--</el-table-column>-->

    <el-table-column
      prop="userCounts"
      label="充值用户数"
    >
    </el-table-column>

    <el-table-column
      prop="userId"
      label="最多充值用户ID"
    >
    </el-table-column>
    </el-table>
    <!-- Table End-->

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
import commonMixin from '@/components/mixin/common-mixin'
export default {
  mixins: [commonMixin],
  data() {
    return {
      rules:{},
      ruleForm:{}
    }
  },
  methods: {
    listCallback(){
      let url = '/coinRechargeCount/getList';
      return countApi.getCountList(this.ruleForm,this.listQuery.current, this.listQuery.size,url);
    }
  }
}
</script>

<style scoped>

</style>


<template>
  <div class="assets common-main">
    <el-form ref="financialDetailsForm" :inline="true" :model="ruleForm" :rules="rules" class="search-container" :label-position="'right'" label-width="80px">

        <el-form-item label="用户ID">
          <el-input v-model="ruleForm.userId" placeholder="用户ID" class="inputauto" clearable></el-input>
        </el-form-item>

        <el-form-item label="用户名">
          <el-input v-model="ruleForm.userName" placeholder="用户名" class="inputauto"
                    clearable></el-input>
        </el-form-item>

        <el-form-item label="手机号码" :span="8">
          <el-input class="inputnumber inputauto" type="number" v-model.number="ruleForm.mobile" placeholder="手机号码" clearable></el-input>
        </el-form-item>

        <el-form-item label="币种">
          <el-select v-model="ruleForm.coinId" placeholder="币种" class="inputauto" clearable>
            <el-option label="全部" value=""></el-option>
            <el-option :label="item.name" :value="item.id" v-for="item in coinAll" :key="item.id"></el-option>
          </el-select>
        </el-form-item>

      <!-- 0-审核中；1-审核通过；2-拒绝；3-提币成功；4：撤销；5-打币中 -->
        <el-form-item label="状态">
          <el-select v-model="ruleForm.status" placeholder="状态" clearable>
            <el-option label="审核中" value="0"></el-option>
            <el-option label="转出成功" value="1"></el-option>
            <el-option label="审核拒绝" value="2"></el-option>
            <el-option label="撤销" value="3"></el-option>
            <el-option label="审核通过" value="4"></el-option>
            <el-option label="打币成功" value="5"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="提现金额">
          <el-col :span="12">
            <el-input class="inputnumber" type="number" v-model.number="ruleForm.startAmount" placeholder="" clearable></el-input>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="10">
            <el-input class="inputnumber" type="number" v-model.number="ruleForm.endAmount" placeholder="" clearable></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="时间" >
          <el-date-picker class="widthauto" v-model="ruleForm.dateRange" :editable="false" value-format="yyyy-MM-dd" format="yyyy-MM-dd" type="datetimerange" start-placeholder="开始日期" end-placeholder="结束日期" clearable>
          </el-date-picker>
        </el-form-item>

        <el-form-item label=" ">
          <el-button icon="el-icon-search" @click="submitForm('financialDetailsForm')">搜索</el-button>
          <el-button icon="el-icon-download" type="primary" @click="exportExcel('financialDetailsForm','/coinWithdraw/exportList','提币记录')" :disabled="listData.length == 0">导出</el-button>
        </el-form-item>

    </el-form>

    <div class="table">
      <el-table :data="listData" style="width: 100%" v-loading="listLoading">
        <el-table-column prop="id" label="订单ID">
        </el-table-column>

        <el-table-column prop="userName" label="用户名" width="120">
        </el-table-column>

        <el-table-column prop="coinName" label="币种名称">
        </el-table-column>

        <el-table-column prop="num" label="提现量">
        </el-table-column>

        <el-table-column prop="mum" label="实际提现">
        </el-table-column>

        <el-table-column prop="fee" label="手续费">
        </el-table-column>

        <el-table-column prop="address" label="钱包地址" width="200">
        </el-table-column>

        <el-table-column prop="txid" label="交易ID" width="200">
        </el-table-column>

        <el-table-column prop="created" label="申请时间" width="160">
        </el-table-column>

        <el-table-column prop="auditTime" label="审核时间" width="160">
        </el-table-column>

        <el-table-column prop="remark" label="审核备注">
        </el-table-column>

        <el-table-column prop="status" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status| elTagFilter">
              {{scope.row.status | coninWithdrawTypeFilter}}
            </el-tag>
          </template>
        </el-table-column>

      </el-table>
    </div>

    <el-pagination class="pagination-container" background layout="total,prev, pager, next" :current-page.sync="listQuery.current" :page-size="listQuery.size" :total="listQuery.total" @current-change="handlePageChange">
    </el-pagination>
  </div>
</template>

<script>
import commonMixin from '@/components/mixin/common-mixin'
import {mapGetters} from 'vuex'
import { financeApi } from "@/api/financeApi";
export default {
  mixins: [commonMixin],
  data() {
    return {
      coinAll:'',
      ruleForm: {
        coinId:'',
        userId: '',
        userName: '',
        mobile: '',
        status: '',
        endAmount: '',
        startAmount: '',
        dateRange: ''
      },
      rules: {
        title: [
          // {required: true, message: '请输入标题', trigger: 'blur'},
        ],
      },
    }
  },
  computed:{
    ...mapGetters(['getCoinAll'])
  },
  mounted(){
    this.coinAll = this.getCoinAll
  },
  methods: {
    // 请求数据列表
    listCallback() {
      return financeApi.getCoinWithdraw(this.ruleForm, this.listQuery.current, this.listQuery.size)
    },
  }
}
</script>

<style scoped>

</style>

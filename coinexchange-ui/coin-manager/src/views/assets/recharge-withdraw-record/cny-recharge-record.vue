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
          <el-input class="inputauto" clearable type="number" v-model.number="ruleForm.mobile" placeholder="手机号码"></el-input>
        </el-form-item>

        <el-form-item label="币种">
          <el-select v-model="ruleForm.coinId" placeholder="币种" class="inputauto" clearable>
            <el-option label="全部" value=""></el-option>
            <el-option :label="item.name" :value="item.id" v-for="item in coinAll" :key="item.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="状态">
          <el-select v-model="ruleForm.status" placeholder="状态" class="inputauto" clearable>
            <el-option label="待审核" value="0"></el-option>
            <el-option label="审核通过" value="1"></el-option>
            <el-option label="拒绝" value="2"></el-option>
            <el-option label="充值成功" value="3"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="充值金额" >
          <el-col :span="12">
            <el-input class="inputnumber" placeholder="最小充值金额" type="number" v-model.number="ruleForm.numMin" clearable></el-input>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="10">
            <el-input class="inputnumber" placeholder="最大充值金额" type="number"
                      v-model.number="ruleForm.numMax"
                      clearable></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="时间">
          <el-date-picker class="widthauto" v-model="ruleForm.dateRange" :editable="false" value-format="yyyy-MM-dd" format="yyyy-MM-dd" type="datetimerange" start-placeholder="开始日期" end-placeholder="结束日期" clearable>
          </el-date-picker>
        </el-form-item>

        <el-form-item label=" ">
          <el-button icon="el-icon-search" @click="submitForm('financialDetailsForm')">搜索</el-button>
          <el-button icon="el-icon-download" type="primary" @click="exportExcel('financialDetailsForm','/cashRecharge/exportCNYRecharge','场外交易充值')" :disabled="listData.length == 0">导出</el-button>
        </el-form-item>

    </el-form>

    <div class="table">
      <el-table :data="listData" style="width: 100%" v-loading="listLoading">
        <el-table-column prop="id" label="订单ID" width="180">
        </el-table-column>
        <el-table-column prop="userId" label="用户ID" width="180">
        </el-table-column>

        <el-table-column prop="username" label="用户名" width="100">
        </el-table-column>

        <el-table-column prop="realName" label="真实用户名" width="100">
        </el-table-column>

        <el-table-column prop="coinName" label="充值币种" width="100">
        </el-table-column>

        <el-table-column prop="num" label="充值金额(USDT)" width="150">
        </el-table-column>

        <el-table-column prop="fee" label="手续费" width="100">
        </el-table-column>

        <el-table-column prop="mum" label="到账金额(CNY)" width="150">
        </el-table-column>

        <el-table-column prop="type" label="充值方式" width="100">
          <template slot-scope="scope">
            {{scope.row.type | rechargeTypeFilter}}
          </template>
        </el-table-column>

        <el-table-column prop="tradeno" label="充值订单" width="180">
        </el-table-column>

        <el-table-column prop="remark" label="参考号" width="120">
        </el-table-column>

        <el-table-column prop="created" label="充值时间" width="180">
        </el-table-column>

        <el-table-column prop="lastTime" label="完成时间" width="200">
        </el-table-column>

        <el-table-column prop="statusStr" label="状态" width="120"> 
          <template slot-scope="scope">
            <el-tag :type="scope.row.status| elTagFilter">
              {{scope.row.status|rechargeStatusFilter}}
            </el-tag>
          </template>
        </el-table-column>
        

        <el-table-column prop="step" label="审核级数" width="80">
        </el-table-column>

        <el-table-column prop="auditRemark" label="审核备注" class="remarks">
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
        numMin: '',
        numMax: '',
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
      return financeApi.getFinanceRechargeList(this.ruleForm, this.listQuery.current, this.listQuery.size)
    },
  }
}
</script>

<style scoped>

</style>

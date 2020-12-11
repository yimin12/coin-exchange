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

        <el-form-item label="状态">
          <el-select v-model="ruleForm.status" placeholder="状态" clearable>
            <el-option label="待审核" value="0"></el-option>
            <el-option label="审核通过" value="1"></el-option>
            <el-option label="拒绝" value="2"></el-option>
            <el-option label="提现成功" value="3"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="提现金额" >
          <el-col :span="12">
            <el-input class="inputnumber" type="number" v-model.number="ruleForm.numMin"
                      placeholder="最小提现金额" clearable></el-input>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="10">
            <el-input class="inputnumber" type="number" v-model.number="ruleForm.numMax"
                      placeholder="最大提现金额" clearable></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="时间" >
          <el-date-picker class="widthauto" v-model="ruleForm.dateRange" :editable="false" value-format="yyyy-MM-dd" format="yyyy-MM-dd" type="datetimerange" start-placeholder="开始日期" end-placeholder="结束日期" clearable>
          </el-date-picker>
        </el-form-item>

        <el-form-item label=" ">
          <el-button icon="el-icon-search" @click="submitForm('financialDetailsForm')">搜索</el-button>
          <el-button icon="el-icon-download" type="primary" @click="exportExcel('financialDetailsForm','/cashWithdrawals/exportCNYWithDrawals','场外交易提现')" :disabled="listData.length == 0">导出</el-button>
          <!-- <el-button type="primary" @click="exportExcel()" :disabled="listData.length == 0">导出</el-button> -->
        </el-form-item>

    </el-form>

    <div class="table">
      <el-table :data="listData" ref="listDataTable" style="width: 100%" v-loading="listLoading">
        <el-table-column prop="id" label="订单ID" width="180">
        </el-table-column>

        <el-table-column prop="userId" label="用户ID" width="180">
        </el-table-column>

        <el-table-column prop="username" label="用户名" width="180">
        </el-table-column>

        <el-table-column prop="num" label="提现金额(USDT)" width="150">
        </el-table-column>

        <el-table-column prop="fee" label="手续费">
        </el-table-column>

        <el-table-column prop="mum" label="到账金额(CNY)" width="120">
        </el-table-column>

        <el-table-column prop="truename" label="提现开户名" width="120">
        </el-table-column>

        <el-table-column prop="bank" label="银行名称/账号" width="180">
          <template slot-scope="scope">
              {{scope.row.bank}}{{scope.row.bankCard}}
          </template>
        </el-table-column>

        <el-table-column prop="created" label="申请时间" width="180">
        </el-table-column>

        <el-table-column prop="lastTime" label="完成时间" width="180">
        </el-table-column>

        <!-- <el-table-column prop="status" label="状态" width="120" >
          <template slot-scope="scope">
            <el-tag :type="scope.row.status| elTagFilter">
              {{scope.row.statusStr}}
            </el-tag>
          </template>
        </el-table-column> -->

        <el-table-column prop="statusStr" label="状态" width="120">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status| elTagFilter">
             {{scope.row.status|withdrawStatusFilter}}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="remark" label="审核备注">
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
    this.marketAll = this.getCoinAll
    console.log(this.$refs.listDataTable);
  },
  methods: {
    // 请求数据列表
    listCallback() {
      return financeApi.getFinanceWithdrawalsList(this.ruleForm, this.listQuery.current, this.listQuery.size)
    },
    // 请求excel数据
    exportListCallback(){
      return financeApi.getFinanceWithdrawalsList(this.ruleForm, this.listQuery.current, 2000)
    },
    // // 导出excel
    // exportExcel(){
    //   let listDataTable = this.$refs.listDataTable.columns;
    //
    //   let excelOption = {
    //     tHeader   :[],
    //     filterVal :[],
    //     filename  :'场外交易提现记录'
    //   }
    //
    //   for (var variable in listDataTable) {
    //     if (listDataTable.hasOwnProperty(variable)) {
    //       excelOption.tHeader.push(listDataTable[variable].label)
    //       excelOption.filterVal.push(listDataTable[variable].property)
    //     }
    //   }
    //   this.getExportExcel(excelOption);
    // },

  }
}
</script>

<style scoped>

</style>

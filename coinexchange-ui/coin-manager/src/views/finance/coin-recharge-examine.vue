<template>
<div class="assets common-main">

  <el-form ref="financeRechargeForm" :inline="true" :model="ruleForm" class="form-top" :label-position="'right'" label-width="80px">

    <el-col :span="8">
      <el-form-item label="用户ID">
        <el-input v-model="ruleForm.userId" placeholder="用户ID"></el-input>
      </el-form-item>
    </el-col>

    <el-col :span="8">
      <el-form-item label="姓名">
        <el-input v-model="ruleForm.userName" placeholder="姓名"></el-input>
      </el-form-item>
    </el-col>

    <el-col :span="8">
      <el-form-item label="手机号码" :span="8">
        <el-input class="inputnumber" type="number" v-model.number="ruleForm.mobile" placeholder="手机号码"></el-input>
      </el-form-item>
    </el-col>


    <el-col :span="8">
      <el-form-item label="币种">
        <el-select v-model="ruleForm.coinId" placeholder="币种">
          <el-option label="全部" value=""></el-option>
          <el-option :label="item.name" :value="item.id" v-for="item in coinAll" :key="item.id"></el-option>
        </el-select>
      </el-form-item>
    </el-col>
    <!-- 0-成功；1-失败 -->
    <el-col :span="8">
      <el-form-item label="状态">
        <el-select v-model="ruleForm.status" placeholder="状态">
          <el-option label="全部" value=""></el-option>
          <el-option label="成功" value="0"></el-option>
          <el-option label="失败" value="1"></el-option>
        </el-select>
      </el-form-item>
    </el-col>

    <el-col :span="8">
      <el-form-item label="数额" class="flex">
        <el-col :span="12">
          <el-input class="inputnumber" type="number" v-model.number="ruleForm.startAmount" placeholder=""></el-input>
        </el-col>
        <el-col class="line" :span="2">-</el-col>
        <el-col :span="10">
          <el-input class="inputnumber" type="number" v-model.number="ruleForm.endAmount" placeholder=""></el-input>
        </el-col>
      </el-form-item>
    </el-col>

    <el-col :span="8">
      <el-form-item label="时间" class="flex">
        <el-date-picker class="widthauto" v-model="ruleForm.dateRange" :editable="false" value-format="yyyy-MM-dd" format="yyyy-MM-dd" type="datetimerange" start-placeholder="开始日期" end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
    </el-col>

    <el-col :span="8">
      <el-form-item label=" ">
        <el-button @click="submitForm('financeRechargeForm')">搜索</el-button>
        <el-button type="primary" @click="exportExcel('financeRechargeForm','/coinRecharge/exportCoinRecharge')" :disabled="listData.length == 0">导出</el-button>
      </el-form-item>
    </el-col>

  </el-form>

  <div class="table">
    <el-table :data="listData" style="width: 100%" v-loading="listLoading">
      <el-table-column prop="id" label="ID" width="180">
      </el-table-column>

      <el-table-column prop="userId" label="用户名ID" width="180">
      </el-table-column>

      <el-table-column prop="userName" label="用户名" width="100">
      </el-table-column>

      <el-table-column prop="coinName" label="币种名称" width="100">
      </el-table-column>

      <el-table-column prop="mum" label="充值数量" width="100">
      </el-table-column>

      <el-table-column prop="address" label="收款地址">
      </el-table-column>

      <el-table-column prop="txid" label="交易ID" width="250">
      </el-table-column>

      <el-table-column prop="created" label="充值时间" >
      </el-table-column>

      <el-table-column prop="status" label="状态" >
        <template slot-scope="scope">
          <el-tag :type="scope.row.status| elTagFilter">
            {{scope.row.status|coinRechargeTypeListFilter}}
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
import { mapGetters } from 'vuex'
import { financeApi } from "@/api/financeApi";
export default {
  mixins: [commonMixin],
  data() {
    return {
      dialogFormVisible: false,
      remark: '',
      name: '数字货币提现审核',
      coinAll: '',
      postData: {},
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
  computed: {
    ...mapGetters(['getCoinAll'])
  },
  mounted() {
    this.coinAll = this.getCoinAll;
  },
  methods: {
    // 请求数据列表
    listCallback() {
      return financeApi.getCoinRecharge(this.ruleForm, this.listQuery.current, this.listQuery.size)
    },
  }
}
</script>

<style scoped>

</style>

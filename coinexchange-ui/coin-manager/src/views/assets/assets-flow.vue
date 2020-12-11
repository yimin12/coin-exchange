<template>
  <div class="assets common-main">
    <el-form ref="assetsFlowForm" :inline="true" :model="ruleForm" :rules="rules" class="search-container"
             :label-position="'right'" label-width="80px">

        <el-form-item label="账户ID">
          <el-input v-model="ruleForm.accountId" placeholder="账户ID" clearable></el-input>
        </el-form-item>

        <el-form-item label="用户ID">
          <el-input v-model="ruleForm.userId" placeholder="用户ID" clearable></el-input>
        </el-form-item>


        <el-form-item label="用户名">
          <el-input v-model="ruleForm.userName" placeholder="用户名" clearable></el-input>
        </el-form-item>

        <el-form-item label="手机号码" :span="8">
          <el-input class="inputnumber" type="number" v-model.number="ruleForm.mobile"
                    placeholder="手机号码" clearable></el-input>
        </el-form-item>

        <el-form-item label="币种">
          <el-select v-model="ruleForm.coinId" placeholder="币种" clearable>
            <el-option label="全部" value=""></el-option>
            <el-option :label="item.name" :value="item.id" v-for="item in coinAll"
                       :key="item.id"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="金额">
          <el-col :span="12">
            <el-input class="inputnumber" type="number" v-model.number="ruleForm.amountStart"
                      placeholder="最小金额" clearable></el-input>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="10">
            <el-input class="inputnumber" type="number" v-model.number="ruleForm.amountEnd"
                      placeholder="最大金额" clearable></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="时间">
          <el-date-picker class="widthauto" v-model="ruleForm.dateRange" :editable="false"
                          value-format="yyyy-MM-dd" format="yyyy-MM-dd" type="datetimerange"
                          start-placeholder="开始日期" end-placeholder="结束日期" clearable>
          </el-date-picker>
        </el-form-item>

        <el-form-item label=" ">
          <el-button icon="el-icon-search" @click="submitForm('assetsFlowForm')">搜索</el-button>
          <el-button icon="el-icon-download" type="primary"
                     @click="exportExcel('assetsFlowForm','/finance/accountDetails/export','资金流水')"
                     :disabled="listData.length == 0">导出
          </el-button>
        </el-form-item>

    </el-form>

    <div class="table">
      <el-table :data="listData" style="width: 100%" v-loading="listLoading">

        <el-table-column prop="id" label="ID" width="180">
        </el-table-column>

        <el-table-column prop="accountId" label="账户ID" width="180">
        </el-table-column>

        <el-table-column prop="refAccountId" label="关联账户ID" width="180">
        </el-table-column>

        <el-table-column prop="username" label="用户名">
        </el-table-column>

        <el-table-column prop="coinName" label="币种名称">
          <template slot-scope="scope">
           {{  coinFilter(scope.row.coinId)}}
          </template>
        </el-table-column>

        <el-table-column prop="amount" label="金额">
        </el-table-column>

        <el-table-column prop="direction" label="收付类型" width="120">
          <template slot-scope="scope">
            {{scope.row.direction | accountFlowDirectionFilter}}
          </template>
        </el-table-column>

        <el-table-column prop="businessType" label="业务类型" width="120">
           <template slot-scope="scope">
            {{scope.row.businessType | businessTypeFilter}}
          </template>
        </el-table-column>

        <el-table-column prop="orderId" label="关联订单号" width="180">
        </el-table-column>

        <el-table-column prop="created" label="发生时间" width="180">
        </el-table-column>

        <el-table-column prop="remark" label="备注">
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
        coinAll: '',
        ruleForm: {
          userId: '',
          userName: '',
          mobile: '',
          coinId: '',
          startTime: '',
          endTime: '',
          amountStart: '',
          amountEnd: '',
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
        return assetsApi.getAccountAssetsFlowList(this.ruleForm, this.listQuery.current, this.listQuery.size)
      },
      coinFilter(coinId) {
           for (let index = 0; index < this.coinAll.length; index++) {
                const element = this.coinAll[index];
                if(element.id==coinId){
                  return element.name ;
                }
           }
        } 

    }
  }
</script>

<style scoped>

</style>

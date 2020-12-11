<template>
  <div class="assets common-main">

    <el-form ref="financeRechargeForm" :inline="true" :model="ruleForm" class="search-container"
             :label-position="'right'" label-width="80px">

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

      <!-- 0-待审核；1-审核通过；2-拒绝；3-提现成功 -->
        <el-form-item label="状态">
          <el-select v-model="ruleForm.status" placeholder="状态" clearable>
            <el-option label="全部" value=""></el-option>
            <el-option label="待审核" value="0"></el-option>
            <el-option label="审核通过" value="1"></el-option>
            <el-option label="拒绝" value="2"></el-option>
            <el-option label="提现成功" value="3"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="提现金额">
          <el-col :span="10">
            <el-input class="inputnumber" type="number" v-model.number="ruleForm.numMin"
                      placeholder="" clearable></el-input>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="10">
            <el-input class="inputnumber" type="number" v-model.number="ruleForm.numMax"
                      placeholder="" clearable></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="时间">
          <el-date-picker class="widthauto" v-model="ruleForm.dateRange" :editable="false"
                          value-format="yyyy-MM-dd" format="yyyy-MM-dd" type="datetimerange"
                          start-placeholder="开始日期" end-placeholder="结束日期" clearable>
          </el-date-picker>
        </el-form-item>

        <el-form-item label=" ">
          <el-button icon="el-icon-search" @click="submitForm('financeRechargeForm')">搜索</el-button>
          <el-button icon="el-icon-download" type="primary"
                     @click="exportExcel('financeRechargeForm','/cashWithdrawals/exportCNYWithDrawals','GCN提现审核')"
                     :disabled="listData.length == 0">导出
          </el-button>
        </el-form-item>

    </el-form>

    <div class="table">
      <el-table :data="listData" style="width: 100%" v-loading="listLoading">
        <el-table-column prop="id" label="ID" width="180">
        </el-table-column>

        <el-table-column prop="userId" label="用户ID" width="180">
        </el-table-column>

        <el-table-column prop="username" label="用户名" width="100">
        </el-table-column>

        <el-table-column prop="num" label="提现金额(USDT)" width="150">
        </el-table-column>

        <el-table-column prop="fee" label="手续费" width="100">
        </el-table-column>

        <el-table-column prop="mum" label="到账金额(CNY)" width="150">
        </el-table-column>

        <el-table-column prop="truename" label="提现开户名" width="150">
        </el-table-column>

        <el-table-column prop="bankNameAndCard" label="银行名称/账号" width="150">
         <template slot-scope="scope">
              {{scope.row.bank}}{{scope.row.bankCard}}
          </template>
        </el-table-column>

        <el-table-column prop="created" label="申请时间" width="160">
        </el-table-column>

        <el-table-column prop="lastTime" label="完成时间" width="200">
        </el-table-column>

        <el-table-column prop="status" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status| elTagFilter">
              {{scope.row.status | withdrawStatusFilter}}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="remark" label="审核备注">
        </el-table-column>

        <el-table-column prop="step" label="审核级数" width="80">
        </el-table-column>

        <el-table-column label="操作" width="100">
          <template slot-scope="scope">
            <el-button
              @click="handleAudit(scope.row)"
              type="primary"
              size="small"
              v-if="scope.row.status == 0">审核
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-dialog width="600px" title="审核提示" :visible.sync="dialogFormVisible">
      <el-form :model="auditForm" :rules="auditRules" ref="ruleRef">
        <el-form-item label="审核备注" :label-width="'120px'" prop="remark">
          <el-input v-model="auditForm.remark"></el-input>
        </el-form-item>
        <el-form-item class="dialog-footer">
          <el-button @click="auditOperant('reject','ruleRef')">拒绝</el-button>
          <el-button type="primary" @click="auditOperant('accept','ruleRef')">通过</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>

    <el-pagination class="pagination-container" background layout="total,prev, pager, next"
                   :current-page.sync="listQuery.current" :page-size="listQuery.size"
                   :total="listQuery.total" @current-change="handlePageChange">
    </el-pagination>

  </div>
</template>

<script>
  import commonMixin from '@/components/mixin/common-mixin'
  import {mapGetters} from 'vuex'
  import {financeApi} from "@/api/financeApi";

  export default {
    mixins: [commonMixin],
    data() {
      return {
        dialogFormVisible: false,
        remark: '',
        name: '场外交易提现审核',
        coinAll: '',
        postData: {},
        ruleForm: {
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
        auditForm: {
          remark: '',
        },
        auditRules: {
          remark: [{required: true, message: '请输入备注原因', trigger: 'blur'}]
        }
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
        return financeApi.getFinanceWithdrawalsList(this.ruleForm, this.listQuery.current, this.listQuery.size)
      },

      // 充值审核
      handleAudit(row) {
        this.dialogFormVisible = true;
        this.postData.id = row.id;
        this.auditForm.remark = '';
        if (this.$refs['ruleRef']) {
          this.$refs['ruleRef'].resetFields();
        }
      },

      // 审核操作
      auditOperant(type, formName) {
        this.postData.remark = this.auditForm.remark;
        // 拒绝审核
        if (type == 'reject') {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              this.postData.status = 2;
              this.request();
            } else {
              return false;
            }
          })
        } else if (type == 'accept') {
          this.postData.status = 1;
          this.$refs[formName].resetFields();
          this.request();
        }


      },

      async request() {
        await financeApi.checkFinanceWithdrawals(this.postData);
        this.$notify({
          type: 'success',
          title: '提示',
          message: '操作成功！',
        });
        this.dialogFormVisible = false;
        this._getList();
      }
    }
  }
</script>

<style scoped>

</style>

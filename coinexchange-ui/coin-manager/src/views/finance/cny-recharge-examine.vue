<template>
  <div class="assets common-main">

    <el-form
      ref="financeRechargeForm"
      :inline="true"
      :model="ruleForm"
      :rules="rules"
      :label-position="'right'"
      label-width="100px"
      class="search-container">

        <el-form-item label="用户ID">
          <el-input v-model="ruleForm.userId"
                    placeholder="用户ID"
                    class="form-input"
                    clearable></el-input>
        </el-form-item>

        <el-form-item label="用户名">
          <el-input v-model="ruleForm.userName" placeholder="用户名" clearable></el-input>
        </el-form-item>

        <el-form-item label="手机号码" :span="8">
          <el-input class="inputnumber" type="number" v-model.number="ruleForm.mobile"
                    placeholder="手机号码"></el-input>
        </el-form-item>

      <!-- 0-待审核；1-审核通过；2-拒绝；3-充值成功； -->
        <el-form-item label="充值状态">
          <el-select v-model="ruleForm.status" placeholder="充值状态" class="form-input" clearable>
            <el-option label="待审核" value="0"></el-option>
            <el-option label="审核通过" value="1"></el-option>
            <el-option label="拒绝" value="2"></el-option>
            <el-option label="充值成功" value="3"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="充值金额">
          <el-col :span="10">
            <el-input class="inputnumber" type="number" v-model.number="ruleForm.numMin"
                      placeholder=""></el-input>
          </el-col>
          <el-col class="line" :span="1">-</el-col>
          <el-col :span="10">
            <el-input class="inputnumber" type="number" v-model.number="ruleForm.numMax"
                      placeholder=""></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label="时间">
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

        <el-form-item label=" ">
          <el-button icon="el-icon-search" @click="submitForm('financeRechargeForm')">搜索</el-button>
          <el-button icon="el-icon-download" type="primary"
                     @click="exportExcel('financeRechargeForm','/cashRecharge/exportCNYRecharge','GCN充值审核')"
                     :disabled="listData.length == 0">导出
          </el-button>
        </el-form-item>

    </el-form>

    <div>
      <el-table :data="listData" style="width: 100%" v-loading="listLoading">
        <el-table-column prop="id" label="ID" width="180">
        </el-table-column>

        <el-table-column prop="userId" label="用户ID" width="180">
        </el-table-column>

        <el-table-column prop="username" label="用户名" width="120">
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

        <el-table-column prop="tradeno" label="充值订单" width="200">
        </el-table-column>

        <el-table-column prop="remark" label="参考号" width="150">
        </el-table-column>

        <el-table-column prop="created" label="充值时间" width="200">
        </el-table-column>

        <el-table-column prop="lastTime" label="完成时间" width="200">
        </el-table-column>

        <el-table-column prop="statusStr" label="状态" width="120">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status| elTagFilter">
              {{scope.row.status | rechargeStatusFilter}}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="auditRemark" label="审核备注" width="120">
        </el-table-column>

        <el-table-column prop="step" label="审核级数" width="80">
        </el-table-column>

        <el-table-column label="操作" width="100">
          <template slot-scope="scope">
            <el-button @click="handleAudit(scope.row)"
                       size="small"
                       type="primary"
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
  import {financeApi} from "@/api/financeApi";

  export default {
    mixins: [commonMixin],
    data() {
      return {
        dialogFormVisible: false,
        name: '场外交易充值审核',
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
    mounted() {
    },
    methods: {
      // 请求数据列表
      async listCallback() {
        return await financeApi.getFinanceRechargeList(this.ruleForm, this.listQuery.current,
          this.listQuery.size)
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
        // 拒绝审核
        if (type == 'reject') {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              this.postData.remark = this.auditForm.remark;
              this.postData.status = 2;
              this.request();
            } else {
              return false;
            }
          })
        } else if (type == 'accept') {
          this.postData.remark = this.auditForm.remark;
          this.postData.status = 1;
          this.$refs[formName].resetFields();
          this.request();
        }
      },
      async request() {
        await financeApi.checkFinanceRecharge(this.postData);
        this.$notify({
          type: 'success',
          title: '提示',
          message: '操作成功！',
        });
        this.dialogFormVisible = false;
        this._getList();
      }

    },

  }
</script>

<style scoped>

</style>

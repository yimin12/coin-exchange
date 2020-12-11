<template>
  <div class="assets common-main">

    <el-form ref="financeRechargeForm" :inline="true" :model="ruleForm" class="search-container"
             :label-position="'right'" label-width="80px">

      <el-form-item label="用户名">
        <el-input v-model="ruleForm.userName" placeholder="姓名" clearable></el-input>
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

      <!-- 0-审核中；1-审核通过；2-拒绝；3-提币成功；4：撤销；5-打币中； -->
      <el-form-item label="状态">
        <el-select v-model="ruleForm.status" placeholder="状态" clearable>
          <el-option label="审核中" value="0"></el-option>
          <el-option label="转出成功" value="1"></el-option>
          <el-option label="审核拒绝" value="2"></el-option>
          <el-option label="撤销" value="3"></el-option>
          <el-option label="审核通过" value="4"></el-option>
          <el-option label="打币中" value="5"></el-option>
          <el-option label="钱包异常，审核中" value="6"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="提现金额">
        <el-col :span="12">
          <el-input class="inputnumber" type="number" v-model.number="ruleForm.startAmount"
                    placeholder="" clearable></el-input>
        </el-col>
        <el-col class="line" :span="2">-</el-col>
        <el-col :span="10">
          <el-input class="inputnumber" type="number" v-model.number="ruleForm.endAmount"
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
                   @click="exportExcel('financeRechargeForm','/coinWithdraw/exportList','数字货币提现审核')"
                   :disabled="listData.length == 0">导出
        </el-button>
      </el-form-item>

    </el-form>

    <div class="table">
      <el-table :data="listData" style="width: 100%" v-loading="listLoading">
        <el-table-column prop="id" label="ID" width="80">
        </el-table-column>

        <el-table-column prop="userName" label="用户名" width="100">
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

        <el-table-column prop="remark" label="审核备注" width="150">
        </el-table-column>

        <el-table-column prop="status" label="状态" width="100">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status| elTagFilter">
              {{scope.row.status | coninWithdrawTypeFilter}}
            </el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="type" label="打款类型" width="80">
          <template slot-scope="scope">
            {{scope.row.type | withdrawType}}
          </template>
        </el-table-column>
        <el-table-column prop="step" label="审核级数" width="80">
        </el-table-column>

        <el-table-column label="操作" width="100">
          <template slot-scope="scope">
            <el-button @click="handleAudit(scope.row)"
                       type="primary"
                       size="small"
                       v-if="scope.row.status == 0">
              审核
            </el-button>
            <!--手动打款-->
            <el-button @click="handleManualWithdraw(scope.row)"
                       type="primary"
                       size="small"
                       v-if="scope.row.status == 4 && scope.row.type ==2">
              确认打款
            </el-button>

          </template>
        </el-table-column>
      </el-table>
    </div>
    <!--审核对话框-->
    <el-dialog width="600px" title="审核提示" :visible.sync="dialogFormVisible">
      <el-form :model="auditForm" :rules="auditRules" ref="ruleRef">
        <el-form-item label="审核备注" :label-width="'120px'" prop="remark">
          <el-input v-model="auditForm.remark"></el-input>
        </el-form-item>
        <el-form-item class="dialog-footer">
          <el-button @click="submitAudit('reject','ruleRef')">拒绝</el-button>
          <el-button type="primary" @click="submitAudit('accept','ruleRef')">通过</el-button>
        </el-form-item>
      </el-form>
    </el-dialog>
    <!--手动打款对话框-->
    <el-dialog width="600px" title="手动打款信息" :visible.sync="manualVisible">
      <el-form :model="manualForm" :rules="manualRules" ref="manualRef">
        <el-form-item label="转账流水号" :label-width="'120px'" prop="txid">
          <el-input v-model="manualForm.txid"></el-input>
        </el-form-item>
        <el-form-item label="链上手续费" :label-width="'120px'" prop="chainFee">
          <el-input type="number" v-model="manualForm.chainFee"></el-input>
        </el-form-item>
        <el-form-item class="dialog-footer">
          <el-button type="primary" @click="submitManual('manualRef')">确认</el-button>
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
        manualVisible:false,
        remark: '',
        name: '数字货币提现审核',
        coinAll: '',
        postData: {},
        ruleForm: {
          coinId: '',
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
        },
        manualForm:{
          txid:"",
          chainFee:""
        },
        manualRules:{
          txid: [{required: true, message: '请输入转账流水号', trigger: 'blur'}],
          chainFee: [{required: true, message: '请输入链上手续费', trigger: 'blur'}]
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
      async listCallback() {
        return await financeApi.getCoinWithdraw(this.ruleForm, this.listQuery.current,
          this.listQuery.size)
      },

      // 充值审核
      handleAudit(row) {
        this.dialogFormVisible = true;
        this.postData.id = row.id;
        this.auditForm.remark = '';
      },
      // 手动打款
      handleManualWithdraw(row){
        this.manualForm = {}
        this.manualVisible = true
        this.manualForm.id = row.id;
      },
      submitManual(formName){
        this.$refs[formName].validate((valid) => {
          if (valid) {
            this._manualWithdraw()
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },

      async _manualWithdraw(){
        await financeApi.manualWithdraw(this.manualForm);

        setTimeout(()=>{
          this.$notify({
            type: 'success',
            title: '提示',
            message: '操作成功！',
          });
          this._getList();
          this.manualVisible = false;

        },2000)
      },
      // 审核操作
      submitAudit(type, formName) {

        // 拒绝审核
        if (type == 'reject') {
          this.$refs[formName].validate((valid) => {
            if (valid) {
              this.postData.remark = this.auditForm.remark;
              this.postData.status = 2;
              this._withdrawAudit();
            } else {
              return false;
            }
          })
        } else if (type == 'accept') {
          this.postData.status = 4;
          this.$refs[formName].resetFields();
          this._withdrawAudit();
        }


      },

      async _withdrawAudit() {
        await financeApi.checkCoinWithdraw(this.postData);
        this.$notify({
          type: 'success',
          title: '提示',
          message: '操作成功！',
        });
        this._getList();
        this.dialogFormVisible = false;
      }
    }
  }
</script>

<style scoped>

</style>

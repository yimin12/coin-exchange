<template>
  <div class="assets common-main">
    <el-form ref="accountAssetsForm" :inline="true" :model="ruleForm" :rules="rules"
             class="search-container" :label-position="'right'" label-width="80px">

        <el-form-item label="用户ID">
          <el-input v-model="ruleForm.userId" placeholder="用户ID" class="inputauto"
                    clearable></el-input>
        </el-form-item>

        <el-form-item label="用户名">
          <el-input v-model="ruleForm.userName" placeholder="用户名" class="inputauto"
                    clearable></el-input>
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

        <el-form-item label="状态">
          <el-select v-model="ruleForm.status" placeholder="状态" clearable>
            <el-option label="全部" value=""></el-option>
            <el-option label="正常" value="1"></el-option>
            <el-option label="冻结" value="2"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="账户余额">
          <el-col :span="12">
            <el-input class="inputnumber" type="number" v-model.number="ruleForm.startAmount"
                      clearable placeholder=""></el-input>
          </el-col>
          <el-col class="line" :span="2">-</el-col>
          <el-col :span="10">
            <el-input class="inputnumber" type="number" v-model.number="ruleForm.endAmount"
                      clearable placeholder=""></el-input>
          </el-col>
        </el-form-item>

        <el-form-item label=" ">
          <el-button icon="el-icon-search" @click="submitForm('accountAssetsForm')">搜索</el-button>
          <el-button icon="el-icon-download" type="primary"
                     @click="exportExcel('accountAssetsForm','/account/exportList','账户资产')"
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

        <el-table-column prop="userName" label="用户名" width="120">
        </el-table-column>

        <el-table-column prop="realName" label="真实姓名" width="120">
        </el-table-column>

        <el-table-column prop="coinName" label="币种名称">
        </el-table-column>

        <el-table-column prop="mobile" label="手机号码" width="150">
        </el-table-column>

        <el-table-column prop="balanceAmount" label="账户余额">
        </el-table-column>

        <el-table-column prop="freezeAmount" label="冻结余额">
        </el-table-column>

        <el-table-column prop="recAddr" label="钱包地址">
        </el-table-column>

        <el-table-column prop="rechargeAmount" label="总充值">
        </el-table-column>

        <el-table-column prop="withdrawalsAmount" label="总提现">
        </el-table-column>

        <el-table-column prop="statusStr" label="状态">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status| elTagFilter">
              {{scope.row.status | accountAssetsListStatus}}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column label="操作" width="250">
          <template slot-scope="scope">

            <el-button
              @click="recharge(scope.row)"
              type="primary"
              size="small">充值
            </el-button>

            <el-button
              v-if="scope.row.status == 1"
              @click="handleFreeze(scope.row)"
              size="small"
              type="danger">冻结
            </el-button>

            <el-button
              v-if="scope.row.status == 2"
              @click="handleUnfreeze(scope.row)"
              size="small">解冻
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <el-pagination class="pagination-container" background layout="total,prev, pager, next"
                   :current-page.sync="listQuery.current" :page-size="listQuery.size"
                   :total="listQuery.total" @current-change="handlePageChange">
    </el-pagination>

    <account-recharge ref="recharge" @refreshList="_getList"></account-recharge>
  </div>
</template>

<script>
  import commonMixin from '@/components/mixin/common-mixin'
  import accountRecharge from './account-recharge';
  import {assetsApi} from '@/api/assetsApi'
  import {mapGetters} from 'vuex'

  export default {
    components: {accountRecharge},
    mixins: [commonMixin],
    name: 'sccount-assets',
    data() {
      return {
        dialogVisible: false,
        coinAll: '',
        ruleForm: {
          coinId: '',
          userId: '',
          userName: '',
          mobile: '',
          coinId: '',
          status: '',
          startAmount: '',
          endAmount: '',
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
    created() {
      this.coinAll = this.getCoinAll;
    },
    methods: {
      // 请求数据列表
      listCallback() {
        return assetsApi.getAccountAssetsList(this.ruleForm, this.listQuery.current, this.listQuery.size)
      },
      // 冻结
      async handleFreeze(row) {
        let res = await assetsApi.accountAssetsOpearte(row);
        if(res.errcode === 0){
          row.status = 2;
          this.$notify({
            type: 'success',
            title: '提示',
            message: '操作成功！',
          });
          this._getList();
        }
      },
      // 解冻
      async handleUnfreeze(row) {
        let res = await assetsApi.accountAssetsOpearte(row);
        if(res.errcode === 0 ){
          row.status = 1;
          this.$notify({
            type: 'success',
            title: '提示',
            message: '操作成功！',
          });
          this._getList();
        }

      },
      // 充值
      recharge(row) {
        const {coinId, userId} = row;
        this.$refs.recharge.showDialog(2, {coinId, userId});
      },
    }
  }
</script>

<style lang="scss" scoped>

</style>

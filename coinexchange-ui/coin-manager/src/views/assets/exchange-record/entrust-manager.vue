<template>
  <div class="common-main">
    <!-- <transactionRecordForm :Type="2"  @transactionRecordForm="transactionRecordForm"></transactionRecordForm> -->

    <el-form
      ref="entrustManagerForm"
      :inline="true"
      :model="ruleForm"
      class="search-container"
      :label-position="'right'"
      label-width="80px">

      <el-form-item label="交易市场">
        <el-select v-model="ruleForm.marketId" placeholder="交易市场" class="form-input" clearable>
          <el-option label="全部" value=""></el-option>
          <el-option :label="item.name" :value="item.id" v-for="item in marketAll"
                     :key="item.id"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="交易方式">
        <el-select v-model="ruleForm.type" placeholder="交易方式" class="form-input" clearable>
          <el-option label="全部" value=""></el-option>
          <el-option label="买" value="1"></el-option>
          <el-option label="卖" value="2"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="交易状态">
        <el-select v-model="ruleForm.status" placeholder="交易状态" class="form-input" clearable>
          <el-option label="全部" value=""></el-option>
          <el-option label="未成交" value="0"></el-option>
          <el-option label="已成交" value="1"></el-option>
          <el-option label="已取消" value="2"></el-option>
          <el-option label="异常单" value="4"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="订单ID">
        <el-input v-model="ruleForm.id" placeholder="订单ID" class="form-input" clearable></el-input>
      </el-form-item>

      <el-form-item label="用户ID" :span="8">
        <el-input v-model="ruleForm.userId" placeholder="用户ID" class="form-input"
                  clearable></el-input>
      </el-form-item>

      <el-form-item label="用户名" :span="8">
        <el-input v-model="ruleForm.userName" placeholder="用户名" class="form-input"
                  clearable></el-input>
      </el-form-item>

      <el-form-item label="手机号" :span="8">
        <el-input class="form-input" clearable type="number" v-model.number="ruleForm.mobile"
                  placeholder="手机号"></el-input>
      </el-form-item>

      <el-form-item label="时间" :span="16">
        <el-date-picker  clearable v-model="ruleForm.dateRange" :editable="false"
                        value-format="yyyy-MM-dd" format="yyyy-MM-dd" type="datetimerange"
                        start-placeholder="开始日期" end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>

      <el-form-item label=" ">
        <el-button icon="el-icon-search" @click="submitForm('entrustManagerForm')">搜索</el-button>
        <el-button type="primary" icon="el-icon-edit"
                   @click="exportExcel('entrustManagerForm','/entrustOrder/exportList','币币交易委托')"
                   :disabled="listData.length == 0">导出
        </el-button>
      </el-form-item>

    </el-form>

    <div class="table">
      <el-table :data="listData" v-loading="listLoading">
        <el-table-column prop="id" label="订单ID" width="170">
        </el-table-column>

        <el-table-column prop="userId" label="用户ID" width="180">
        </el-table-column>

        <el-table-column prop="userName" label="用户名">
        </el-table-column>

        <el-table-column prop="marketName" label="交易市场" width="120">
        </el-table-column>

        <el-table-column prop="price" label="委托价格" width="120">
        </el-table-column>

        <el-table-column prop="volume" label="委托数量">
        </el-table-column>

        <el-table-column prop="predictTurnoverAmount" label="预计成交额" width="150">
        </el-table-column>

        <el-table-column prop="deal" label="已成交量">
        </el-table-column>

        <el-table-column prop="fee" label="手续费" width="120">
        </el-table-column>

        <el-table-column prop="freeze" label="冻结金额" width="120">
        </el-table-column>

        <el-table-column prop="type" label="交易方式">
          <template slot-scope="scope">
            {{scope.row.type | entrustManagerListTradtypeFilter}}
          </template>
        </el-table-column>

        <el-table-column prop="status" label="状态">
          <template slot-scope="scope">
            <el-tag :type="scope.row.status| elTagFilter">
              {{scope.row.status | entrustManagerListStatusFilter}}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column prop="created" label="委托时间" width="180">
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
        marketAll: '',
        ruleForm: {
          marketId: '',
          type: '',
          userId: '',
          id: '',
          userName: '',
          dateRange: '',
          status: '',
          mobile: '',
        },

        rules: {
          title: [
            // {required: true, message: '请输入标题', trigger: 'blur'},
          ],
        },


        resourceData: [{
          name: "网站标题",
          type: "合作机构",
          code: "webtitle",
          value: "hashtoken",
          createTime: "2018-06-12",
          status: "启用",
        }],
      }
    },
    computed: {
      ...mapGetters(['getMarketAll'])
    },
    mounted() {
      this.marketAll = this.getMarketAll
    },
    methods: {
      // 请求数据列表
      listCallback() {
        return assetsApi.getEntrustManagerList(this.ruleForm, this.listQuery.current, this.listQuery.size)
      },

    }
  }
</script>

<style scoped>

</style>

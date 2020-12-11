<template>
  <el-dialog
    :title="dialogTitle"
    :visible.sync="dialogVisible"
    width="80%"
    @close="closeDialog"
  >
    <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">返 回</el-button>
    </span>
    <el-table 
      ref="multipleTable"
      :data="listData"
      tooltip-effect="dark"
      style="width: 100%"
      highlight-current-row
      @selection-change="handleSelectionChange"
      v-loading="listLoading"
    >
      <el-table-column
        prop="userId"
        label="用户ID"
      >
      </el-table-column>

      <el-table-column 
       prop="btcAmount"
       label="持有BTC"
      >
      </el-table-column>
      
      <el-table-column 
       prop="usdtAmount"
       label="持有CNY"
      >
      </el-table-column>

      <el-table-column 
       prop="ethAmount"
       label="持有ETH"
      >
      </el-table-column>

      <el-table-column 
       prop="ltcAmount"
       label="持有LTC"
      >
      </el-table-column>
    </el-table>
    <!-- Page -->
    <el-pagination
      class="pagination-container"
      background
      layout="total,prev, pager, next"
      :current-page.sync="listQuery.current"
      :page-size="listQuery.size"
      :total="listQuery.total"
      @current-change="handlePageChange"
    >
    </el-pagination>
    <!-- Page End -->
  </el-dialog>
</template>

<script>
  import Tinymce from '@/components/Tinymce'
  import {countApi} from "@/api/countApi";
  import commonMixin from '@/components/mixin/common-mixin'
  import dialogMixin from '@/components/mixin/dialog-mixin'

  export default {
    components: {Tinymce},
    mixins: [dialogMixin,commonMixin],
    data() {
      return {
        dialogTitle: '用户BTC持仓',
        rules: {}
      }
    },
    methods: {
      listCallback(){
      let url = '/trade/count/top/balance';
      return countApi.getCountList(this.ruleForm,this.listQuery.current, this.listQuery.size,url);
    },
    }
  }
</script>

<style scoped>

</style>



<template>
  <div class="common-main">
    <el-form ref="coinTypeConfigForm"
             :model="ruleForm"
             label-width="100px"
             class="search-container">

      <el-form-item label="币种类型">
        <el-input v-model="ruleForm.code" placeholder="请输入" clearable></el-input>
      </el-form-item>

      <div class="operation-container">
        <el-button icon="el-icon-search" @click="submitForm('coinTypeConfigForm')">搜索</el-button>
        <el-button type="primary" icon="el-icon-edit" @click="created('coinTypeConfigForm')">新增</el-button>
        <!--<el-button type="danger" icon="el-icon-delete" @click="handleDelete">删除</el-button>-->
      </div>

    </el-form>

    <el-table ref="multipleTable"
              :data="listData"
              tooltip-effect="dark"
              style="width: 100%"
              @selection-change="handleSelectionChange"
              v-loading="listLoading">

      <!--<el-table-column type="selection" width="55">
      </el-table-column>-->

      <el-table-column prop="code" label="币种类型">
      </el-table-column>

      <el-table-column prop="description" label="描述">
      </el-table-column>

      <el-table-column prop="status" label="状态">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status| elTagFilter">
            {{scope.row.status|statusFilter}}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column width="200" label="操作">
        <template slot-scope="scope">
          <el-button @click="handleEdit(scope.$index,scope.row)" size="small" type="primary">编辑</el-button>
          <el-button
            size="mini"
            v-show="scope.row.status===0"
            @click="handleChangeStatus(scope.$index, scope.row)" icon="el-icon-edit">启用
          </el-button>
          <el-button
            size="mini"
            type="danger"
            v-show="scope.row.status===1"
            @click="handleChangeStatus(scope.$index, scope.row)" icon="el-icon-edit">禁用
          </el-button>
        </template>
      </el-table-column>

    </el-table>

    <el-pagination class="pagination-container"
                   background
                   layout="total,prev, pager, next"
                   :current-page.sync="listQuery.current"
                   :page-size="listQuery.size"
                   :total="listQuery.total"
                   @current-change="handlePageChange">
    </el-pagination>

    <coinTypeDialog ref="coinTypeDialog" @refreshList="_getList"></coinTypeDialog>
  </div>
</template>

<script>
  import coinTypeDialog from './component/coin-type-dialog';
  import commonMixin from '@/components/mixin/common-mixin'
  import {coinConfigApi} from '@/api/coinConfigApi'

  export default {
    mixins: [commonMixin],
    components: {coinTypeDialog},
    data() {
      return {
        ruleForm: {
          code: '',
        },

      }
    },
    methods: {
      // 请求数据列表
      listCallback() {
        this.$store.dispatch("CoinTypeAll");
        return coinConfigApi.getCoinType(this.ruleForm, this.listQuery.current, this.listQuery.size)
      },
      // 新增
      created() {
        this.$refs.coinTypeDialog.showDialog(1);
      },
      // 批量删除
      deleteCallback(ids) {
        return coinConfigApi.deleteCoinType(ids)
      },
      async changeStatusCallback(id, status) {
        return await coinConfigApi.setCoinTypeStatus({
          id,
          status,
        });
      },
      handleEdit(index,row){
        this.$refs.coinTypeDialog.showDialog(2, Object.assign({}, row));

      }
    }
  }
</script>

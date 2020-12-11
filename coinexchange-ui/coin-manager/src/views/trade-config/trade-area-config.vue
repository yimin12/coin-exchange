<template>
  <div class="common-main">
    <el-form :model="ruleForm" ref="ruleForm" label-width="100px" class="search-container">
      <el-form-item label="名称">
        <el-input v-model="ruleForm.name" placeholder="" clearable></el-input>
      </el-form-item>
      <el-form-item label="状态">
        <el-select v-model="ruleForm.status" placeholder="请选择" clearable>
          <el-option label="禁用" value="0"></el-option>
          <el-option label="启用" value="1"></el-option>
        </el-select>
      </el-form-item>
      <div class="operation-container">
        <el-button icon="el-icon-search" @click="submitForm('ruleForm')">搜索</el-button>
        <el-button type="primary" icon="el-icon-edit" @click="created('ruleForm')">新增</el-button>
        <!--<el-button type="danger" icon="el-icon-delete" @click="handleDelete">删除</el-button>-->
      </div>
    </el-form>

    <el-table :data="listData" v-loading="listLoading" @selection-change="handleSelectionChange">
      <!--<el-table-column type="selection" width="55">
      </el-table-column>-->

      <el-table-column prop="id" label="ID">
      </el-table-column>

      <el-table-column prop="name" label="名称">
      </el-table-column>

      <el-table-column prop="code" label="代码">
      </el-table-column>

      <el-table-column prop="coinName" label="结算币种">
      </el-table-column>

      <el-table-column prop="baseCoin" label="资产统计标识">
        <template slot-scope="scope">
          {{scope.row.baseCoin == 0 ? '否' : '是'}}
        </template>
      </el-table-column>

      <el-table-column prop="status" label="状态">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status| elTagFilter">
            {{scope.row.status | statusFilter}}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column width="200" label="操作">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="handleEdit(scope.$index, scope.row)" icon="el-icon-edit">编辑
          </el-button>
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

    <el-pagination class="pagination-container" background layout="total,prev, pager, next"
                   :current-page.sync="listQuery.current" :page-size="listQuery.size"
                   :total="listQuery.total" @current-change="handlePageChange">
    </el-pagination>

    <tradeAreaDialog ref="tradeAreaDialog" @refreshList="_getList"></tradeAreaDialog>
  </div>
</template>

<script>
  import tradeAreaDialog from './component/trade-area-dialog';
  import commonMixin from '@/components/mixin/common-mixin'
  import {tradeAreaApi} from '@/api/tradeAreaApi'

  export default {
    mixins: [commonMixin],
    components: {tradeAreaDialog},
    data() {
      return {
        ruleForm: {
          name: '',
          code: '',
          status: ''
        },

      }
    },
    methods: {
      // 请求数据列表
      listCallback() {
        return tradeAreaApi.getTradeAreaList(this.ruleForm, this.listQuery.current, this.listQuery.size)
      },
      // 新增
      created() {
        this.$refs.tradeAreaDialog.showDialog(1);
      },
      // 修改
      handleEdit(index, row) {
        this.$refs.tradeAreaDialog.showDialog(2, Object.assign({}, row));
      },
      // 批量删除
      deleteCallback(ids) {
        return tradeAreaApi.deleteTradeArea(ids)
      },
      // 启用禁用
      async changeStatusCallback(id, status) {
        return await tradeAreaApi.setTradeAreaStats({id, status});
      },
    }
  }
</script>

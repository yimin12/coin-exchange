<template>
  <div class="common-main">
    <el-form
      :inline="true"
      :model="searchForm"
      ref="searchForm"
      class="demo-form-inline"
      :label-position="'right'"
      label-width="80px">

      <el-form-item label="状态" prop="status">
        <el-select v-model="searchForm.status" class="form-input" clearable>
          <el-option v-for="(value,key) in select.status"
                     :key="key"
                     :label="value"
                     :value="key"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button icon="el-icon-search" @click="submitForm('searchForm')">搜索
        </el-button>
        <el-button type="primary" icon="el-icon-edit" @click="handleCreate">新建</el-button>
      </el-form-item>
    </el-form>

    <div class="table">
      <el-table
        :data="listData"
        style="width: 100%">

        <el-table-column
          prop="id"
          label="ID"
          width="200">
        </el-table-column>

        <el-table-column
          prop="name"
          label="名称"
          width="150">
        </el-table-column>

        <el-table-column
          prop="title"
          label="标题"
          width="150">
        </el-table-column>

        <el-table-column
          prop="sort"
          label="排序"
          width="150">
        </el-table-column>

        <el-table-column
          prop="status"
          render="renderHeader"
          label="状态"
          width="100"
        >
          <template slot-scope="scope">
            <el-tag :type="scope.row.status|elTagFilter">
              {{select.status[scope.row.status]}}
            </el-tag>
          </template>
        </el-table-column>

        <el-table-column
          width="775"
          label="操作"
        >
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
    </div>

    <coin-config-dialog ref="coinConfigDialog" @refreshList="_getList"></coin-config-dialog>

  </div>
</template>

<script>
  import coinConfigDialog from './component/coin-config-dialog.vue'
  import {forexConfigApi} from "@/api/coinConfigApi";
  import {selectOption} from '../trade-config/common'
  import commonMixin from '@/components/mixin/common-mixin'

  export default {
    components: {coinConfigDialog},
    mixins: [commonMixin],
    data() {
      return {
        select: selectOption,
        searchForm: {
          status: '',
        },
      }
    },
    methods: {
      listCallback() {
        return forexConfigApi.getForexCoinList(this.searchForm,
          this.listQuery.current, this.listQuery.size)
      },
      async changeStatusCallback(id, status) {
        return await forexConfigApi.setStatus({
          id,
          status,
        });
      },
      handleCreate() {
        this.$refs.coinConfigDialog.showDialog(1,);
      },
      handleEdit(index, row) {
        const data = {
          ...row,
          status: row.status + ''
        }
        this.$refs.coinConfigDialog.showDialog(2, data);
      },
    }
  }
</script>

<style scoped>

</style>

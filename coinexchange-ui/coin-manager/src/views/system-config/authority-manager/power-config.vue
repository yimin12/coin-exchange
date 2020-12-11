<template>
  <div class="common-main">
    <el-form :rules="rules" ref="ruleForm" label-width="100px" class="search-container">
      <div class="operation-container">
        <el-button type="primary" icon="el-icon-edit" @click="handleCreate">新增功能权限</el-button>
      </div>
    </el-form>

    <el-table
      ref="multipleTable"
      :data="listData"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange"
      v-loading="listLoading"
    >
      <!-- <el-table-column
        type="selection"
        width="55">
      </el-table-column> -->

      <el-table-column
        prop="name"
        label="名称"
      >
      </el-table-column>
      <el-table-column
        prop="description"
        label="描述"
      >
      </el-table-column>

      <el-table-column
        label="操作"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="handleEdit(scope.$index, scope.row)" icon="el-icon-edit">编辑
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

    <powerConfigDialog ref="powerConfigDialog" @refreshList="_getList"></powerConfigDialog>
  </div>
</template>

<script>
  import powerConfigDialog from './component/powerconfig-dialog';
  import {sysConfigApi}  from "@/api/sysConfigApi";
  import commonMixin       from '@/components/mixin/common-mixin';

  export default {
    components: {powerConfigDialog},
    mixins    : [commonMixin],
    data() {
      return {
        rules   : {
        },
        ruleForm: {
          type: ''
        }
      };
    },

    methods: {
      async listCallback() {
        return await sysConfigApi.getPowerList(
          this.listQuery.current, this.listQuery.size);
      },
      handleCreate() {
        this.$refs.powerConfigDialog.showDialog(1);
      },
      handleEdit(index, row) {
        this.$refs.powerConfigDialog.showDialog(2, Object.assign({}, row));
      }
    }
  };
</script>

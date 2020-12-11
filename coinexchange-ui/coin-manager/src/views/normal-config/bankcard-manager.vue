<template>
  <div class="common-main">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="search-container">
      <el-form-item label="银行卡号" prop="bankCard">
        <el-input v-model="ruleForm.bankCard" class="form-input" clearable></el-input>
      </el-form-item>

      <div class="operation-container">
        <el-button icon="el-icon-search" @click="submitForm('ruleForm')">搜索</el-button>
        <el-button type="primary" icon="el-icon-edit" @click="handleCreate">新增银行卡</el-button>
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
      <!--<el-table-column
        type="selection"
        width="55">
      </el-table-column>-->
      <el-table-column
        prop="id"
        label="ID"
        width="180"
      >
      </el-table-column>
      <el-table-column
        prop="name"
        label="开户人姓名"
        width="120"
      >
      </el-table-column>
      <el-table-column
        prop="bankName"
        label="开户行（包含支行名称）"
        width="280"
      >
      </el-table-column>
      <el-table-column
        prop="bankCard"
        label="卡号"
        width="180"
      >
      </el-table-column>

      <el-table-column
        prop="status"
        label="状态"
      >
        <template slot-scope="scope">
          <el-tag :type="scope.row.status|elTagFilter">
            {{scope.row.status | statusFilter}}
          </el-tag>

        </template>
      </el-table-column>

      <el-table-column
        label="操作"
        width="250"
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

    <bankCardDialog ref="bankCardDialog" @refreshList="_getList"></bankCardDialog>
  </div>
</template>

<script>
  import bankCardDialog from './component/bankcard-dialog';
  import {normalConfigApi} from "@/api/normalConfigApi";
  import commonMixin from '@/components/mixin/common-mixin';

  export default {
    components: {bankCardDialog},
    mixins: [commonMixin],
    data() {
      return {
        rules: {
          title: [
            // {required: true, message: '请输入标题', trigger: 'blur'},
          ]
        },
        ruleForm: {
          bankCard: ''
        }
      };
    },

    methods: {
      listCallback() {
        return normalConfigApi.getBankList(this.ruleForm,
          this.listQuery.current, this.listQuery.size);
      },
      changeStatusCallback(id, status) {
        return normalConfigApi.setBanks({
          bankId: id,
          status: status
        });
      },

      // deleteCallback(ids) {
      //   return normalConfigApi.deleteNotice(ids)
      // },
      handleCreate() {
        this.$refs.bankCardDialog.showDialog(1);
      },
      handleEdit(index, row) {
        this.$refs.bankCardDialog.showDialog(2, Object.assign({}, row));
      }
    }
  };
</script>

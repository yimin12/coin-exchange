<template>
  <div class="common-main">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="search-container">
      <el-form-item label="用户ID" prop="userId">
        <el-input v-model="ruleForm.userId" class="form-input" clearable></el-input>
      </el-form-item>

      <el-form-item label="姓名" prop="realName">
        <el-input v-model="ruleForm.realName" class="form-input" clearable></el-input>
      </el-form-item>

      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="ruleForm.mobile" class="form-input" clearable></el-input>
      </el-form-item>

      <el-form-item label="层级" prop="level">
        <el-input v-model="ruleForm.level" class="form-input" clearable></el-input>
      </el-form-item>

      <div class="operation-container">
        <el-button icon="el-icon-search" @click="submitForm('ruleForm')">搜索</el-button>
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
      <el-table-column
        prop="id"
        label="用户ID"
        width="180"
      >
      </el-table-column>
      <el-table-column
        prop="level"
        label="层级"
      >
      </el-table-column>
      <el-table-column
        prop="mobile"
        label="手机号"
        width="120"
      >
      </el-table-column>
      <el-table-column
        prop="email"
        label="邮箱"
        width="120"
      >
      </el-table-column>
      <!--<el-table-column-->
      <!--prop="coinId"-->
      <!--label="币种ID"-->
      <!--&gt;-->
      <!--</el-table-column>-->
      <el-table-column
        prop="realName"
        label="姓名"
      >
      </el-table-column>
      <el-table-column
        prop="idCard"
        label="身份证号"
        width="180"
      >
      </el-table-column>

      <el-table-column
        prop="reviewsStatus"
        label="审核状态"
        width="100"
      >
        <template slot-scope="scope">
          <el-tag :type="scope.row.reviewsStatus|elTagFilter">
            {{scope.row.reviewsStatus | reviewsStatusFilter}}
          </el-tag>

        </template>
      </el-table-column>

      <el-table-column
        prop="agentNote"
        label="审核备注"
        width="100"
      >
      </el-table-column>

      <el-table-column
        prop="created"
        label="注册时间 "
        width="200"
      >
      </el-table-column>

      <el-table-column
        label="操作"
        width="200"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="primary"
            @click="handleEdit(scope.$index, scope.row)"
            v-if="scope.row.reviewsStatus==0"
            icon="el-icon-edit">审核
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

    <agentCheckDialog ref="agentCheckDialog" @refreshList="_getList"></agentCheckDialog>
  </div>
</template>

<script>
  import agentCheckDialog from './component/agentcheck-dialog';
  import {agentVerifyApi} from "@/api/agentVerifyApi";
  import commonMixin from '@/components/mixin/common-mixin';

  export default {
    components: {agentCheckDialog},
    mixins: [commonMixin],
    data() {
      return {
        rules: {},
        ruleForm: {}
      };
    },

    methods: {
      async listCallback() {
        return await agentVerifyApi.getAgentSellerList(this.ruleForm,
          this.listQuery.current, this.listQuery.size);
      },
      async handleEdit(index, row) {
        let {reviewsStatus} = row;
        if (reviewsStatus == 1 || reviewsStatus == 2) {
          reviewsStatus += '';
        }
        else {
          reviewsStatus = '';
        }
        await this.$refs.agentCheckDialog.showDialog(2, {
          ...row,
          reviewsStatus,
        });
      }
    }
  };
</script>

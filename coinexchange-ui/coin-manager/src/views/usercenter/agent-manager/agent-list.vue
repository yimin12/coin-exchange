<template>
  <div class="common-main">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="search-container">
      <el-form-item label="用户ID" prop="userId">
        <el-input v-model="ruleForm.userId" class="form-input" clearable></el-input>
      </el-form-item>

      <el-form-item label="姓名" prop="trueName">
        <el-input v-model="ruleForm.trueName" class="form-input" clearable></el-input>
      </el-form-item>

      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="ruleForm.mobile" class="form-input" clearable></el-input>
      </el-form-item>

      <el-form-item label="层级" prop="level">
        <el-input v-model="ruleForm.level" class="form-input" clearable></el-input>
      </el-form-item>

      <div class="operation-container">
        <el-button icon="el-icon-search" @click="beforeSubmit()">搜索</el-button>
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
      <el-table-column prop="id" label="用户ID" width="180">
      </el-table-column>
      <el-table-column prop="level" label="层级">
      </el-table-column>
      <el-table-column prop="mobile" label="手机号"
                       width="150">
      </el-table-column>
      <el-table-column prop="email" label="邮箱"
                       width="150">
      </el-table-column>
      <!--<el-table-column-->
      <!--prop="coinId"-->
      <!--label="币种ID"-->
      <!--&gt;-->
      <!--</el-table-column>-->
      <el-table-column prop="realName" label="姓名">
      </el-table-column>
      <el-table-column prop="idCard" label="身份证号"
                       width="180">
      </el-table-column>

      <el-table-column prop="reviewsStatus" label="状态">
        <template slot-scope="scope">
          <el-tag :type="scope.row.status|elTagFilter">
            {{scope.row.status | statusFilter}}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column prop="agentNote" label="审核备注"
                       width="180">
      </el-table-column>

      <el-table-column prop="created" label="注册时间"
                       width="180">
      </el-table-column>

      <el-table-column label="操作" width="350">
        <template slot-scope="scope">
          <el-button size="mini"
                     type="primary"
                     @click="handleEdit(scope.$index, scope.row)"
                     v-if="scope.row.username!='AgentAdmin'"
                     icon="el-icon-edit">编辑
          </el-button>
          <el-button
            size="mini"
            @click="nextAgent(scope.$index, scope.row)"
            icon="el-icon-edit">
            下级代理
          </el-button>
          <el-button
            size="mini"
            @click="handleChangeStatus(scope.$index,scope.row)"
            v-if="scope.row.status === 0" icon="el-icon-edit">启用
          </el-button>
          <el-button
            size="mini"
            type="danger"
            @click="handleChangeStatus(scope.$index,scope.row)"
            v-if="scope.row.status === 1 && scope.row.username!='AgentAdmin'" icon="el-icon-edit">禁用
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
    <agentListDialog ref="agentListDialog" @refreshList="_getList"></agentListDialog>

  </div>
</template>

<script>
  import agentListDialog from './component/agentlist-dialog';
  import {agentVerifyApi} from "@/api/agentVerifyApi";
  import commonMixin from '@/components/mixin/common-mixin';

  export default {
    components: {agentListDialog},
    mixins: [commonMixin],
    data() {
      return {
        rules: {
          title: [
            // {required: true, message: '请输入标题', trigger: 'blur'},
          ]
        },
        ruleForm: {}
      };
    },

    methods: {
      listCallback() {
        return agentVerifyApi.getSellerList(this.ruleForm,
          this.listQuery.current, this.listQuery.size);
      },
      handleEdit(index, row) {
        this.$refs.agentListDialog.showDialog(2, {
          ...row,
          status: row.status + ''
        });
      },
      beforeSubmit() {
        this.ruleForm.lower = ''
        this.submitForm('ruleForm')
      },
      nextAgent(index, row) {
        this.ruleForm.lower = 2;
        this.ruleForm.userId = row.id;
        this.submitForm('ruleForm')
      },
      async changeStatusCallback(id, status) {
        return await agentVerifyApi.getOperateUser(id, status)
      }
    }
  };
</script>

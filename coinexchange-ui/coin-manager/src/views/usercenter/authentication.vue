<template>
  <div class="common-main">
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="search-container">
      <el-form-item label="用户ID" prop="userId">
        <el-input v-model="ruleForm.userId" class="form-input" clearable></el-input>
      </el-form-item>

      <el-form-item label="用户名" prop="userName">
        <el-input v-model="ruleForm.userName" class="form-input" clearable></el-input>
      </el-form-item>

      <el-form-item label="姓名" prop="realName">
        <el-input v-model="ruleForm.realName" class="form-input" clearable></el-input>
      </el-form-item>


      <el-form-item label="手机号" prop="mobile">
        <el-input v-model="ruleForm.mobile" class="form-input" clearable></el-input>
      </el-form-item>

      <el-form-item label="审核状态" prop="reviewsStatus">
        <el-select v-model="ruleForm.reviewsStatus" class="form-input" placeholder="请选择" clearable>
          <el-option label="待审核" value="0"></el-option>
          <el-option label="审核通过" value="1"></el-option>
          <el-option label="审核拒绝" value="2"></el-option>
        </el-select>
      </el-form-item>
      <!-- <el-form-item label="时间" prop="created">
        <el-date-picker
          v-model="ruleForm.dateRange"
          type="daterange"
          align="right"
          unlink-panels
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          :picker-options="datePickerOptions"
          value-format="yyyy-MM-dd">
        </el-date-picker>
      </el-form-item> -->
      <div class="operation-container">
        <el-button icon="el-icon-search" @click="submitForm('ruleForm')">搜索</el-button>
      </div>
    </el-form>

    <el-table
      :data="listData"
      style="width: 100%"
      v-loading="listLoading"
    >
      <el-table-column
        prop="id"
        label="用户ID"
        width="180"
      >
      </el-table-column>
      <el-table-column
        prop="username"
        label="用户名"
        width="120"
      >
      </el-table-column>
      <el-table-column
        prop="mobile"
        label="手机号"
        width="150">
      </el-table-column>
      <el-table-column
        prop="email"
        label="邮箱"
        width="150">
      </el-table-column>
      <el-table-column
        prop="realName"
        label="姓名">
      </el-table-column>
      <el-table-column
        prop="idCard"
        label="身份证号"
        width="200">
      </el-table-column>
      <el-table-column
        prop="created"
        label="时间"
        width="200">
      </el-table-column>
      <el-table-column
        prop="authStatus"
        label="实名认证状态"
        width="120">
        <template slot-scope="scope">
          <el-tag :type="scope.row.reviewsStatus|elTagFilter">
            {{scope.row.reviewsStatus | authStatusFilter}}
          </el-tag>
        </template>
      </el-table-column>

      <el-table-column
        prop="reviewsStatus"
        label="审核状态"
        width="150">
        <template slot-scope="scope">
          <el-tag :type="scope.row.reviewsStatus|elTagFilter">
            {{scope.row.reviewsStatus | reviewsStatusFilter}}
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column
        label="操作">
        <template slot-scope="scope">
          <el-button type="primary" size="small" icon="el-icon-edit"
                     v-if="scope.row.reviewsStatus==0"
                     @click="handleVerify(scope.row.id)">审核
          </el-button>
          <el-button type="primary" size="small" icon="el-icon-search"
                     v-if="scope.row.reviewsStatus!=0"
                     @click="handleVerify(scope.row.id)">查看
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
</template>

<script>
  import commonMixin from '@/components/mixin/common-mixin'
  import {userApi} from "../../api/userApi";

  export default {
    mixins: [commonMixin],
    data() {
      return {
        ruleForm: {
          realName: '',
          userId: '',
          userName: '',
          mobile: '',
          reviewsStatus:'0',
          dateRange:''
        },
        rules: {},
      }
    },
    methods: {
      listCallback() {
        return userApi.getUserAuthList(this.ruleForm,
          this.listQuery.current, this.listQuery.size)
      },
      handleVerify(id) {
        this.$router.push({path: `authentication-detail/${id}`})
      },
    }
  }
</script>

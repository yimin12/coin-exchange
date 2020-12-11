<template>
  <div class="common-main">
    <!--<el-form :rules="rules" ref="ruleForm" label-width="100px" class="search-container">
      <div class="operation-container">

        <el-button type="primary" icon="el-icon-edit" @click="handleCreate">新增配置</el-button>
      </div>
    </el-form>-->
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px"
             class="search-container">
      <el-form-item label="规则类型" prop="type">
        <el-input v-model="ruleForm.type" class="form-input" clearable></el-input>
      </el-form-item>

      <el-form-item label="规则代码" prop="code">
        <el-input v-model="ruleForm.code" class="form-input" clearable></el-input>
      </el-form-item>

      <el-form-item label="规则名称" prop="name">
        <el-input v-model="ruleForm.name" class="form-input" clearable></el-input>
      </el-form-item>

      <div class="operation-container">
        <el-button icon="el-icon-search" @click="submitForm('ruleForm')">搜索
        </el-button>
        <el-button type="primary" icon="el-icon-edit" @click="handleCreate">新建</el-button>
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
        prop="type"
        label="类型"
        width="100"
      >
      </el-table-column>
      <el-table-column
        prop="code"
        label="规则代码"
        width="120"
        class-name="article-content-class"
      >
      </el-table-column>
      <el-table-column
        prop="name"
        label="规则名称"
        width="200"
      >
      </el-table-column>
      <el-table-column
        prop="desc"
        label="规则描述"
        width="300"
      >
      </el-table-column>
      <el-table-column
        prop="value"
        label="值"
        width="450"
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

    <cnyRechargeDialog ref="cnyRechargeDialog" @refreshList="_getList"></cnyRechargeDialog>
  </div>
</template>

<script>
  import cnyRechargeDialog from './component/cnyrecharge-dialog';
  import {normalConfigApi} from "@/api/normalConfigApi";
  import commonMixin from '@/components/mixin/common-mixin';

  export default {
    components: {cnyRechargeDialog},
    mixins: [commonMixin],
    data() {
      return {
        rules: {
          title: [
          ]
        },
        ruleForm: {
          type: '',
          code: ''
        }
      };
    },

    methods: {
      async listCallback() {
        return await normalConfigApi.getConfigList(this.listQuery.current, this.listQuery.size,
          this.ruleForm);
      },
      handleCreate() {
        this.$refs.cnyRechargeDialog.showDialog(1);
      },
      handleEdit(index, row) {
        this.$refs.cnyRechargeDialog.showDialog(2, Object.assign({}, row));
      }
    }
  };
</script>

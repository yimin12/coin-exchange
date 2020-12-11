<template>
  <div class="common-main">
    <el-form :rules="rules" ref="ruleForm" :model="ruleForm" label-width="100px" class="search-container">
      <el-form-item label="角色名称" prop="name">
      <el-input v-model="ruleForm.name" class="form-input" clearable></el-input>
    </el-form-item>

      <div class="operation-container">
        <el-button icon="el-icon-search" @click="submitForm('ruleForm')">搜索</el-button>
        <el-button type="primary" icon="el-icon-edit" @click="handleCreate">新建</el-button>
        <el-button type="danger" icon="el-icon-delete" @click="handleDelete">删除</el-button>
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
        type="selection"
        width="55"
      >
      </el-table-column>

      <el-table-column
        prop="name"
        label="角色"
        width="250"
      >
      </el-table-column>
      <el-table-column
        prop="code"
        label="权限"
      >
      </el-table-column>

      <el-table-column
        label="操作"
        width="200"
      >
        <template slot-scope="scope">
          <!--<el-button
            size="mini"
            type="primary"
            @click="handleEdit(scope.$index, scope.row)" icon="el-icon-edit">编辑
          </el-button>-->
          <el-button
            size="mini"
            type="primary"
            @click="configEdit(scope.row.id)" icon="el-icon-edit">配置
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

    <rolemanagerDialog ref="rolemanagerDialog" @refreshList="_getList"></rolemanagerDialog>
  </div>
</template>

<script>
  import rolemanagerDialog from './component/rolemanager-dialog';
  import {sysConfigApi}  from "@/api/sysConfigApi";
  import commonMixin       from '@/components/mixin/common-mixin';

  export default {
    components: {rolemanagerDialog},
    mixins    : [commonMixin],
    data() {
      return {
        rules   : {

        },
        ruleForm: {
        }
      };
    },

    methods: {

      listCallback() {
        return sysConfigApi.getRoleList(this.ruleForm,
          this.listQuery.current, this.listQuery.size);
      },
      // 需要特别处理
      needListProcess(data){
        return data.filter(item=>{
          console.log(item)
          return item.code !== "ROLE_ADMIN"
        })
      },
      deleteCallback(ids) {
        return sysConfigApi.deleteRole(ids)
      },
      handleCreate() {
        this.$refs.rolemanagerDialog.showDialog(1);
      },

      handleEdit(index, row) {
        this.$refs.rolemanagerDialog.showDialog(2, Object.assign({}, row));
      },

      configEdit(id) {
        this.$router.push({path:`role-config/${id}`})
      },
    }
  };
</script>

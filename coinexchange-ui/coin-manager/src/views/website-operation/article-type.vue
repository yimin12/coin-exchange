<template>
  <div class="common-main">
    <div class="operation-container">
      <el-button type="primary" icon="el-icon-edit" @click="handleCreateArticleType">新建</el-button>
      <el-button type="danger" icon="el-icon-delete" @click="handleDelete">删除</el-button>
    </div>

    <el-table
      ref="multipleTable"
      :data="articleTypeData"
      tooltip-effect="dark"
      style="width: 100%"
      @selection-change="handleSelectionChange">
      <el-table-column
        type="selection"
        width="55">
      </el-table-column>
      <el-table-column
        prop="type"
        label="文章类型"
      >
      </el-table-column>
      <el-table-column
        prop="content"
        label="描述"
      >
      </el-table-column>
      <el-table-column
        prop="createTime"
        label="创建时间"
      >
      </el-table-column>
      <el-table-column
        prop="state"
        label="状态"
        width="100"
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

    <el-dialog
      :title="dialogTitle"
      :visible.sync="dialogVisible"
      width="40%"
      @close="handleClose"
      @open="handleOpen"
    >
      <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px" class="search-container">
        <el-form-item label="文章类型" prop="type">
          <el-input v-model="ruleForm.type" class="form-input"></el-input>

        </el-form-item>
        <el-form-item label="描述" prop="desc">
          <el-input v-model="ruleForm.desc" class="form-input"></el-input>
        </el-form-item>
        <el-form-item label="状态" prop="state">
          <el-select v-model="ruleForm.state" class="form-input" placeholder="请选择">
            <el-option label="开启" value="1"></el-option>
            <el-option label="关闭" value="2"></el-option>
          </el-select>
        </el-form-item>
      </el-form>

      <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>

  </div>
</template>

<script>

  export default {
    data() {
      return {
        dialogTitle:'新建文章类型',

        dialogVisible:false,
        ruleForm:{
          state:''
        },
        rules:{

        },

        articleTypeData: [{
          createTime: '2016-05-03',
          type: '公告',
          state:1,
          content: '上海市普陀区金沙江路 1518 弄'
        }, {
          createTime: '2016-05-02',
          type: '资讯',
          state:1,
          content: '上海市普陀区金沙江路 1518 弄'
        }],
        deleteItems:[]
      }
    },
    methods: {
      handleCreateArticleType(){
        this.dialogVisible = true
      },
      handleSelectionChange(val) {
        this.deleteItems = val;
      },
      handleDelete(){
        if(this.deleteItems.length === 0){
          this.$alert('请选择需要删除的文章类型', '提示', {
            confirmButtonText: '确定',
            type: 'warning',
            callback: action => {
            }
          });
        }else{
          this.$alert('确定要删除该文章类型吗？', '提示', {
            confirmButtonText: '确定',
            type: 'warning',
            callback: action => {
              alert("ok")
            }
          });
        }
      },
      handleEdit(index, row){
        this.dialogTitle='编辑文章类型'
        this.dialogVisible = true
        this.ruleForm = row
      },
      handleOpen(){

      },
      handleClose(){

      }
    }
  }
</script>

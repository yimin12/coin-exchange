<template>
  <el-dialog
    :title="dialogTitle"
    :visible.sync="dialogVisible"
    width="40%"
    @close="closeDialog"
  >
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" class="conin-config-add"
             label-width="100px">
      <el-form-item label="资源名称" prop="name">
        <el-input v-model="ruleForm.name" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="资源类型" prop="type">
        <el-select v-model="ruleForm.type" class="form-input" placeholder="请选择">
          <el-option label="大banner图" value="WEB_BANNER"></el-option>
          <el-option label="小banner图" value="LINK_BANNER"></el-option>
        </el-select>
      </el-form-item>

      <!--<el-form-item label="配置值" prop="template">-->
      <!--<el-input v-model="ruleForm.value" class="form-input"></el-input>-->
      <!--</el-form-item>-->

      <!-- <el-form-item label="图片" prop="value">
        <el-upload
          ref="elUpload"
          class="upload-demo"
          action="admin/image/AliYunImgUpload"
          name="file"
          :on-success="handleUploadSuccess"
          :multiple="false"
          :headers="uploadHeader"
        >
          <el-button size="small" type="primary">点击上传</el-button>
        </el-upload>
        <img v-if="ruleForm.value"
               :src="ruleForm.value"
               class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
      </el-form-item> -->
      <el-form-item label="图片">
        <el-upload
          class="avatar-uploader"
           ref="elUpload"
           action="admin/image/AliYunImgUpload"
          :show-file-list="false"
          :headers="uploadHeaders"
          :on-success="handleUploadSuccess"
         >
          <img v-if="ruleForm.value"
               :src="ruleForm.value"
               class="avatar">
          <i v-else class="el-icon-plus avatar-uploader-icon"></i>
        </el-upload>
      </el-form-item>

      <el-form-item label="链接" prop="url">
        <el-input placeholder="请输入链接" v-model="ruleForm.url" class="form-input">
          <template slot="prepend">{{PROTOCOL}}</template>
        </el-input>
      </el-form-item>

      <el-form-item label="排序" prop="sort">
        <el-input-number v-model="ruleForm.sort" class="form-input" type="number"></el-input-number>
      </el-form-item>
      <el-form-item label="状态">
        <el-switch
          v-model="statusFlag"
          active-text="开启"
          inactive-text="关闭">
        </el-switch>
      </el-form-item>
    </el-form>
    <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
      </span>
  </el-dialog>
</template>

<script>
  import dialogMixin from '@/components/mixin/dialog-mixin'
  import { resourceConfigApi } from '@/api/resourceConfigApi'
  import {uploadApi} from "@/api/uploadApi";

  export default {
    mixins: [dialogMixin],
    data() {
      return {
        PROTOCOL: process.env.PROTOCOL,
        // dialogData:'',
        dialogTitle: '新建资源配置',
        ruleForm: {
          name: '',
          type: '',
          value: '',
          url: '',
          sort: ''
        },
        statusFlag: '',
        rules: {
          name: [
            { required: true, message: '请输入名称', trigger: 'blur' }
          ],
          value: [{ required: true, message: '请上传资源图片', trigger: 'blur' }],
          type: [{ required: true, message: '请选择资源类型', trigger: 'blur' }]
        },
        // 上传相关
        fileList: [],
        uploadHeaders: {
          Authorization: sessionStorage.token
        }
      }
    },
    watch: {
      dialogType(val) {
        if (val === 1) {
          this.statusFlag = 0
          this.dialogTitle = '新建资源配置'
        } else {
          this.dialogTitle = '编辑资源配置'
        }
      }
    },
    methods: {
      getRequestData() {
        const {url = ''} = this.ruleForm;
        return {
          ...this.ruleForm,
          url: url ? `${this.PROTOCOL}${url}` : '',
        }
      },
      createCallback() {
        return resourceConfigApi.createWebConfig(this.getRequestData())
      },
      updateCallback() {
        return resourceConfigApi.updateWebConfig(this.getRequestData())
      },
      editCallback(row){
        this.statusFlag = row.status === 1
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .form-input {
    width: 350px;
  }
</style>



<template>
  <el-dialog
    :title="dialogTitle"
    :visible.sync="dialogVisible"
    width="50%"
    @close="closeDialog"
  >
    <el-form :model="ruleForm" :rules="rules" ref="ruleForm" label-width="100px">
      <el-form-item label="文章标题" prop="title">
        <el-input v-model="ruleForm.title" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="文章简介" prop="description">
        <el-input v-model="ruleForm.description" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="文章作者" prop="author">
        <el-input v-model="ruleForm.author" class="form-input"></el-input>
      </el-form-item>
      <el-form-item label="文章排序" prop="sort">
        <el-input-number v-model="ruleForm.sort" class="form-input"></el-input-number>
      </el-form-item>
      <el-form-item label="文章内容" prop="content">
        <tinymce :height="150" v-model="ruleForm.content" ref="tinymce"></tinymce>
      </el-form-item>
    </el-form>

    <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false">取 消</el-button>
          <el-button type="primary" @click="submitForm('ruleForm')">确 定</el-button>
    </span>
  </el-dialog>
</template>

<script>
  import Tinymce from '@/components/Tinymce'
  import {noticeApi} from "@/api/noticeApi";
  import dialogMixin from '@/components/mixin/dialog-mixin'

  export default {
    components: {Tinymce},
    mixins: [dialogMixin],
    data() {
      return {
        dialogTitle: '新建文章',
        rules: {
          title: [
            {required: true, message: '请输入标题', trigger: 'blur'},
          ],
          description: [
            {required: true, message: '请输入简介', trigger: 'blur'},
          ],
          author: [
            {required: true, message: '请输入作者', trigger: 'blur'},
          ],
          sort: [
            {required: true, message: '请输入排序', trigger: 'blur'},
          ],
          content: [
            {required: true, message: '请输入内容', trigger: 'blur'},
          ],
        },
        ruleForm: {}
      }
    },
    watch: {
      dialogType(val) {
        if (val === 1) {
          this.dialogTitle = "新建文章"
        } else {
          this.dialogTitle = "编辑文章"
        }
      }
    },
    methods: {
      editCallback() {
        this.$nextTick(() => {
          this.$refs.tinymce.setContent(this.ruleForm.content)
        })
      },
      closeCallback() {
        this.$refs.tinymce.setContent('')
      },
      createCallback() {
        return noticeApi.createNotice(this.ruleForm)
      },
      updateCallback() {
        return noticeApi.updateNotice(this.ruleForm)
      },

    }
  }
</script>

<style scoped>

</style>



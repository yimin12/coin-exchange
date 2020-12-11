import {uploadApi} from "../../api/uploadApi";

/**
 * 全局CRUD对话框组件抽取
 */
export default {
  mounted() {

  },
  data(){
    return {
      dialogType:1,
      dialogVisible: false,
      uploadImgUrl:uploadApi.aliyunUrl,
      statusFlag:'',
      //oss预上传数据
      uploadHost:"",
      // 上传携带参数
      uploadData:{},
    }
  },
  filters:{
    // 实名认证状态 authStatus: 0 未认证 1初级 2高级
    authStatusFilter(authStatus) {
      const statusMap = {
        0: '未认证',
        1: '初级',
        2: '高级'
      }
      return statusMap[authStatus]
    },
  },
  watch: {
    statusFlag(val) {
      console.log("当前状态",val)
      if (val) {
        this.ruleForm.status = 1
      } else {
        this.ruleForm.status = 0
      }
    },
    // 银行卡状态 1启用 2禁用
    bankCardStatus(val){
      if (val) {
        this.ruleForm.status = 1
      } else {
        this.ruleForm.status = 2
      }
    }
  },
  methods: {
    showDialog(type,row){

      this.dialogVisible = true
      if (row){
        this.ruleForm = row
      }
      if(type ===1){
        this.dialogType = 1
      }else{
        this.dialogType = 2

        // 编辑状态的特殊处理
        if(typeof this.editCallback === "function"){
          this.editCallback(row)
        }
      }
    },
    closeDialog(){
      this.ruleForm = {}
      // 关闭状态的特殊处理
      if(typeof this.closeCallback === "function"){
        this.closeCallback()
      }
    },
    submitForm(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if(this.dialogType ===1){
            this._createItem()
          }else{
            this._updateItem()
          }
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
   async _createItem(){
      await this.createCallback()
      this.$notify({
        type: 'success',
        title: '提示',
        message: '新建成功！',
      });
      this.dialogVisible = false
      this.$emit("refreshList")
    },
    async _updateItem(){
      await this.updateCallback()
      this.$notify({
        type: 'success',
        title: '提示',
        message: "更新成功！"
      });
      this.dialogVisible = false
      this.$emit("refreshList")
    },
    handleUploadSuccess(response) {
      const { code,data } = response
      if(code == 200){
        this.ruleForm.value = data
      }
    },
    async beforeUpload(){
      let res = await uploadApi.getPreUpload()
      if(res.data.data){
        let preUploadData = res.data.data
        let {dir,policy,signature,callback,accessid,host} = preUploadData
        this.uploadHost = host
        this.uploadData.name=signature
        this.uploadData.key=`${dir}${new Date().getTime()}.jpg`
        this.uploadData.policy=policy
        this.uploadData.OSSAccessKeyId=accessid
        this.uploadData.success_action_status=200
        this.uploadData.callback=callback
        this.uploadData.signature=signature
      }else{
        return Promise.reject()
      }
    }
  }
}

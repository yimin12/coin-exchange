<template>
  <div class="ucenter-wrap" v-wechat-title="$t('m.userCenter.advancedCertification.title')">
    <NavHeader activeTab="5"></NavHeader>

    <div class="ucenter-content">
      <div class="ucenter-row">
        <sidebar :activeId="1"></sidebar>
        <div class="ucenter-main">
          <div class="ucenter-header">
            <h3 class="title">{{$t('m.userCenter.advancedCertification.title')}}</h3>
          </div>
          <div class="ucenter-body">
            <el-alert type="warning"
                      :title="$t('m.userCenter.advancedCertification.uploadSizeLimit')"></el-alert>
            <el-form ref="ruleForm" :model="ruleForm" :rules="rules">
              <div class="item">
                <div class="item-title">{{$t('m.userCenter.advancedCertification.cardFrontTips')}}
                </div>
                <el-row :gutter="24">
                  <el-col :span="10">
                    <el-form-item prop="imageUrl1">
                      <el-upload
                        class="avatar-uploader"
                        :action="uploadHost"
                        :show-file-list="false"
                        :on-success="handleUploadSuccess1"
                        :data="uploadData"
                        :before-upload="beforeUpload"
                        :header="uploadHeader">
                        <img v-if="ruleForm.imageUrl1" :src="ruleForm.imageUrl1" class="avatar">

                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                      </el-upload>
                    </el-form-item>
                  </el-col>
                  <el-col :span="4" class="eg">
                    {{$t('m.userCenter.advancedCertification.example')}}<i
                    class="el-icon-caret-right"></i>
                  </el-col>
                  <el-col :span="10">
                    <img src="../../assets/usercenter/demo-id-a.jpg">
                  </el-col>
                </el-row>
              </div>

              <div class="item">
                <div class="item-title">{{$t('m.userCenter.advancedCertification.cardBackTips')}}
                </div>
                <el-row :gutter="24">
                  <el-col :span="10">
                    <el-form-item prop="imageUrl2">
                      <el-upload
                        class="avatar-uploader"
                        :action="uploadHost"
                        :show-file-list="false"
                        :on-success="handleUploadSuccess2"
                        :data="uploadData"
                        :before-upload="beforeUpload"
                        :header="uploadHeader">
                        <img v-if="ruleForm.imageUrl2" :src="ruleForm.imageUrl2" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                      </el-upload>
                    </el-form-item>
                  </el-col>
                  <el-col :span="4" class="eg">
                    {{$t('m.userCenter.advancedCertification.example')}}<i
                    class="el-icon-caret-right"></i>
                  </el-col>
                  <el-col :span="10">
                    <img src="../../assets/usercenter/demo-id-b.jpg">
                  </el-col>
                </el-row>
              </div>

              <div class="item">
                <div class="item-title">
                  {{$t('m.userCenter.advancedCertification.frontPhotoAndSignatureTips')}}
                  <p class="description">
                    {{$t('m.userCenter.advancedCertification.frontPhotoAndSignatureDesc')}}</p>
                </div>
                <el-row :gutter="24">
                  <el-col :span="10">
                    <el-form-item prop="imageUrl3">

                      <el-upload
                        class="avatar-uploader"
                        :action="uploadHost"
                        :show-file-list="false"
                        :on-success="handleUploadSuccess3"
                        :data="uploadData"
                        :before-upload="beforeUpload"
                        :header="uploadHeader">
                        <img v-if="ruleForm.imageUrl3" :src="ruleForm.imageUrl3" class="avatar">
                        <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                      </el-upload>
                    </el-form-item>
                  </el-col>
                  <el-col :span="4" class="eg">
                    {{$t('m.userCenter.advancedCertification.example')}}<i
                    class="el-icon-caret-right"></i>
                  </el-col>
                  <el-col :span="10">
                    <img src="../../assets/usercenter/demo-id-c.jpg">
                  </el-col>
                </el-row>
              </div>
              <div class="submit-btn">
                <el-button type="primary" @click="submitForm('ruleForm')">{{$t('m.yes')}}
                </el-button>
              </div>
            </el-form>

          </div>
        </div>
      </div>
    </div>
    <div class="ucenter-footer">
      <mFooter class="footer-bar"></mFooter>
    </div>
  </div>
</template>
<script>
  import ToggleButton from 'vue-js-toggle-button'
  import Vue from 'vue'
  import NavHeader from 'components/nav-header/nav-header'
  import mFooter from 'components/m-footer/m-footer'
  import {util} from 'common/js/util'
  import {getchilds, getLoginRecord, setAuth, seniorAuth} from 'api/usercenter'
  import {OK} from 'api/config'
  import sidebar from 'components/usercenter/sidebar'
  import {uploadApi} from "../../api/uploadApi";
  import {mapGetters} from 'vuex'

  Vue.use(ToggleButton)
  export default {
    computed: {
      ...mapGetters(['token'])
    },
    data() {
      return {
        //oss预上传数据
        uploadHost:"",
        // 上传携带参数
        uploadData:{},
        uploadHeader: {
          Authorization: this.token
        },
        uploadImgUrl: uploadApi.aliyunUrl,
        imageUrl: '',
        ruleForm: {
          imageUrl1: '',
          imageUrl2: '',
          imageUrl3: '',

        },
        rules: {
          imageUrl1: [
            {
              validator: (rule, value, callback) => {
                if (value === '') {
                  callback(new Error(this.$t('m.userCenter.advancedCertification.plzUpFrontPhoto')));
                } else {
                  callback()
                }
              },
              trigger: 'blur'
            }
          ],
          imageUrl2: [
            {
              validator: (rule, value, callback) => {
                if (value === '') {
                  callback(new Error(this.$t('m.userCenter.advancedCertification.plzUpBackPhoto')));
                } else {
                  callback()
                }
              },
              trigger: 'blur'
            }
          ],
          imageUrl3: [
            {
              validator: (rule, value, callback) => {
                if (value === '') {
                  callback(new Error(this.$t('m.userCenter.advancedCertification.plzUpHandheldPhoto')));
                } else {
                  callback()
                }
              },
              trigger: 'blur'
            }
          ]
        }
      }
    },
    created() {

    },
    mounted() {
      console.log("token", this.token)
      this.uploadHeader.Authorization = this.token
    },
    components: {
      NavHeader,
      mFooter,
      sidebar
    },

    methods: {
      // beforeAvatarUpload(file) {
      //   const isJPG = file.type === 'image/jpeg';
      //   const isLt3M = file.size / 1024 / 1024 < 3;
      //
      //   if (!isJPG) {
      //     this.$message.error(this.$t('m.userCenter.advancedCertification.uploadFormatError'));
      //   }
      //   if (!isLt3M) {
      //     this.$message.error(this.$t('m.userCenter.advancedCertification.uploadPhotoSizeLimit'));
      //   }
      //   return isJPG && isLt3M;
      // },

      submitForm(formName) {

        this.$refs[formName].validate((valid) => {
          if (valid) {
            let avatar = [this.ruleForm.imageUrl1, this.ruleForm.imageUrl2, this.ruleForm.imageUrl3]
            console.log(avatar)
            this._seniorAuth(avatar)
          } else {
            console.log('error submit!!');
            return false;
          }
        });
      },
      async _seniorAuth(avatar) {
        await seniorAuth(avatar, this.token)
        this.$notify({
          type: 'success',
          title: this.$t('m.prompt'),
          message: this.$t('m.userCenter.advancedCertification.uploadSuccess')
        });
        this.$router.push("modify-id")
      },
      open2() {
        const h = this.$createElement;
        this.$msgbox({
          title: this.$t('m.userCenter.certificationTip'),
          message: h('p', null, [
            h('div', null),
            h('div', {style: 'color: teal'}, this.$t('m.userCenter.certificationSuccess'))
          ]),
          confirmButtonText: this.$t('m.yes'),
          beforeClose: (action, instance, done) => {
            if (action === 'confirm') {
              done();
              this.$router.push({path: '/usercenter/modify-id'});
            } else {
              done();
              this.$router.push({path: '/usercenter/modify-id'});
            }
          }
        }).then(action => {
          this.$message({
            type: 'info',
            message: 'action: ' + action
          });
        });
      },
      // 正面

      handleUploadSuccess1(response) {
        const { status,uri } = response
        if(status === "OK"){
          this.ruleForm.imageUrl1 = uploadApi.aliyunFileUrl + uri
        }
      },
      // 反面
      handleUploadSuccess2(response) {
        const { status,uri } = response
        if(status === "OK"){
          this.ruleForm.imageUrl2 = uploadApi.aliyunFileUrl + uri
        }
      },
      // 手持
      handleUploadSuccess3(response) {
        const { status,uri } = response
        if(status === "OK"){
          this.ruleForm.imageUrl3 = uploadApi.aliyunFileUrl + uri
        }
      },
      async beforeUpload(){
        let res = await uploadApi.getPreUpload()
        if(res.data){
          let preUploadData = res.data
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
</script>
<style lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/usercenter"
</style>
<style scoped lang="stylus" rel="stylesheet/stylus">
  .ucenter-wrap {
    .submit-btn {
      text-align center
      margin 10px 0
    }
    .item {
      border-bottom 1px solid #ededed
      padding 15px 0
      font-size 14px
      .item-title {
        text-align center
        padding-bottom 15px
        .description {
          color #999
          margin-top 10px
        }
      }
      .eg {
        margin-top 100px
        text-align center
      }
    }

    .avatar-uploader {
      height: 200px;
      background: #eee;
      margin: auto;
    }
    .avatar-uploader .el-upload {
      border: 1px dashed #d9d9d9;
      border-radius: 6px;
      cursor: pointer;
      position: relative;
      overflow: hidden;
    }
    .avatar-uploader .el-upload:hover {
      border-color: #409EFF;
    }
    .avatar-uploader-icon {
      font-size: 28px;
      color: #8c939d;
      width 348px
      height: 200px;
      line-height: 200px;
      text-align: center;
    }
    .avatar {
      display: block;
      width: 100%;
      height width 100%;
    }

    .el-upload {
      display block !important
    }
  }

</style>

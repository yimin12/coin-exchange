<template>
  <div class="common-main">
    <el-row type="flex">
      <el-col :span="3">
        <div class="label">ID</div>
      </el-col>
      <el-col :span="9">
        <div class="label-content" v-text="user.id"></div>
      </el-col>
      <el-col :span="3">
        <div class="label">用户名</div>
      </el-col>
      <el-col :span="9">
        <div class="label-content" v-text="user.userName"></div>
      </el-col>
    </el-row>

    <el-row type="flex">
      <el-col :span="3">
        <div class="label">手机号码</div>
      </el-col>
      <el-col :span="9">
        <div class="label-content" v-text="user.mobile"></div>
      </el-col>
      <el-col :span="3">
        <div class="label">真实姓名</div>
      </el-col>
      <el-col :span="9">
        <div class="label-content" v-text="user.realName"></div>
      </el-col>
    </el-row>
    <el-row type="flex">
      <el-col :span="3">
        <div class="label">证件类型</div>
      </el-col>
      <el-col :span="9">
        <div class="label-content">{{getIdCardType(user.idCardType)}}</div>
      </el-col>
      <el-col :span="3">
        <div class="label">证件号码</div>
      </el-col>
      <el-col :span="9">
        <div class="label-content" v-text="user.idCard"></div>
      </el-col>
    </el-row>
    <el-row type="flex">
      <el-col :span="3">
        <div class="label">邮箱</div>
      </el-col>
      <el-col :span="9">
        <div class="label-content" v-text="user.email"></div>
      </el-col>
      <el-col :span="3">
        <div class="label">注册时间</div>
      </el-col>
      <el-col :span="9">
        <div class="label-content" v-text="user.created"></div>
      </el-col>
    </el-row>

    <el-row type="flex">
      <el-col :span="3">
        <div class="label">证件照片</div>
      </el-col>
    </el-row>

    <el-row type="flex" justify="space-around">
      <el-col :span="7" :key="item.index" v-for="item in userAuthInfoList">
        <div class="img-container" @click="imgEnlarge(item.imageUrl)">
          <img :src="item.imageUrl" alt="">
        </div>
      </el-col>
    </el-row>

    <el-row type="flex">
      <el-col :span="3">
        <div class="label">审核历史</div>
      </el-col>
      <el-col :span="21" v-if="userAuthAuditRecord.length>0">
        <p v-for="item in userAuthAuditRecord" :key="item.id" style="margin: 10px 0px 0px 0px;">
          {{item.auditUserName}}:{{item.status == 1 ? '通过' : '拒绝'}}（{{item.remark}}）
        </p>
      </el-col>
    </el-row>

    <el-form :model="ruleForm"
             :rules="rules"
             ref="ruleForm"
             class="verify-form">

      <el-form-item label="审核状态" prop="status">
        <el-select
          v-model="ruleForm.status"
          class="form-input"
          placeholder="请选择">
          <el-option label="拒绝" value="2"></el-option>
          <el-option label="通过" value="1"></el-option>
          <el-option label="待审核" value="0"></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="拒绝原因" prop="remark" v-show="reject" class="red-require">
        <el-input v-model="ruleForm.remark" placeholder="请输入拒绝原因" class="form-input"></el-input>
      </el-form-item>

      <el-row type="flex" class="operation-container" justify="flex-start">
        <el-col :span="3">
          <el-button type="primary" @click="submitForm('ruleForm')" >确定</el-button>
        </el-col>
        <el-col :span="3">
          <el-button type="danger" @click="handleCancel">返回</el-button>
        </el-col>
      </el-row>
    </el-form>

    <el-dialog
      title=""
      :visible.sync="centerDialogVisible"
      width="30%"
      center>
      <img :src="checkImg" width="100%"/>
    </el-dialog>
  </div>
</template>

<script>
  import {userApi} from "../../api/userApi";

  export default {
    data() {
      let that = this;
      let validateRemark = function (rule, value, callback) {
        if (that.reject && !value) {
          callback(new Error('请填写拒绝原因!'));
        } else {
          callback();
        }
      };

      return {
        reject: false,
        id: '',
        user: {},
        userAuthAuditRecord: {},
        userAuthInfoList: {},
        centerDialogVisible: false,
        checkImg: '',
        ruleForm: {
          status: '',
          remark: '',
          authCode:''
        },
        rules: {
          reviewsStatus: [
            {required: true, message: '请选择审核状态', trigger: 'change'}
          ],
          remark: [
            {validator: validateRemark, trigger: "change"}
          ]
        }
      };
    },
    async mounted() {
      let id = this.$route.params.id;
      this.id = id;

      const {user,  authAuditRecordList ,userAuthInfoList} = await userApi.selUserAuthDetail(id);
      this.user = user;
      this.userAuthInfoList = userAuthInfoList;
      var lastUserAuth 
      var authCode = '';
      if(authAuditRecordList && authAuditRecordList.length>0){
        console.log(authAuditRecordList)
          this.userAuthAuditRecord = authAuditRecordList ;
          lastUserAuth = authAuditRecordList[0] ;
          authCode = lastUserAuth.authCode ;
      }
      if(userAuthInfoList){
        console.log(userAuthInfoList[0])
          authCode = userAuthInfoList[0].authCode ;
      }
      console.log(authCode)
      this.ruleForm = {
        status: user.reviewsStatus + '',
        authCode: authCode,
        remark: lastUserAuth == null ? '': lastUserAuth.remark
      };
    },
    watch: {
      "ruleForm.status"(curVal) {
        if (curVal == 2) {
          this.reject = true;
        } else {
          this.reject = false;
        }
      }
    },
    methods: {
      getIdCardType(type) {
        switch (type) {
          case 1:
            return '身份证'
          case 2:
            return '军官证'
          case 3:
            return '护照'
          case 4:
            return '台湾居民通行证'
          case 5:
            return '港澳居民通行证'
          case 9:
          default:
            return '其他';
        }
      },
      submitForm(formName) {
        let id = this.id;
        let review = this.ruleForm.status;
        let remark = this.ruleForm.remark;
        let authCode = this.ruleForm.authCode;
        let that = this;

        this.$refs[formName].validate(async (valid) => {
          if (valid) {
            const data = await that.handleVerify(id, review, remark,authCode);
            this.$notify({
              title: '成功',
              message: '实名认证审核成功',
              type: 'success',
              duration: 2000
            });
          } else {
            return false;
          }
        });
      },
      async handleVerify(id, review, remark,authCode) {
        await userApi.updateVerify(id, review, remark,authCode);
        this.$router.push("../authentication");
      },
      handleCancel() {
        this.$router.push("../authentication");
      },
      async imgEnlarge(imgUrl) {
        this.checkImg = imgUrl;
        this.centerDialogVisible = true;
      }
    }
  };
</script>

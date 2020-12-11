<template>
  <div class="common-main">
    <el-tabs type="border-card">
      <el-tab-pane label="用户信息">

        <el-row type="flex">
          <el-col :span="3">
            <div class="label">ID</div>
          </el-col>
          <el-col :span="9">
            <div class="label-content">{{this.user.id}}</div>
          </el-col>
          <el-col :span="3">
            <div class="label">用户名</div>
          </el-col>
          <el-col :span="9">
            <div class="label-content">{{this.user.username}}</div>
          </el-col>
        </el-row>

        <el-row type="flex">
          <el-col :span="3">
            <div class="label">手机号码</div>
          </el-col>
          <el-col :span="9">
            <div class="label-content">{{this.user.mobile}}</div>
          </el-col>
          <el-col :span="3">
            <div class="label">真实姓名</div>
          </el-col>
          <el-col :span="9">
            <div class="label-content">{{this.user.realName}}</div>
          </el-col>
        </el-row>

        <el-row type="flex">
          <el-col :span="3">
            <div class="label">证件类型</div>
          </el-col>
          <el-col :span="9">
            <div class="label-content">{{this.getIdCardType(this.user.idCardType)}}</div>
          </el-col>
          <el-col :span="3">
            <div class="label">证件号码</div>
          </el-col>
          <el-col :span="9">
            <div class="label-content">{{this.user.idCard}}</div>
          </el-col>
        </el-row>

        <el-row type="flex">
          <el-col :span="3">
            <div class="label">邮箱</div>
          </el-col>
          <el-col :span="9">
            <div class="label-content">{{this.user.email}}</div>
          </el-col>
          <el-col :span="3">
            <div class="label">注册时间</div>
          </el-col>
          <el-col :span="9">
            <div class="label-content">{{this.user.created}}</div>
          </el-col>
        </el-row>

      </el-tab-pane>
      <el-tab-pane label="银行卡管理">
        <user-bank :userId="userId"></user-bank>
      </el-tab-pane>
      <el-tab-pane label="钱包地址">
        <user-address :userId="userId"></user-address>
      </el-tab-pane>
      <el-tab-pane label="提币地址">
        <user-wallet :userId="userId"></user-wallet>
      </el-tab-pane>
      <el-tab-pane label="用户邀请列表">
        <user-inviter :userId="userId"></user-inviter>
      </el-tab-pane>

    </el-tabs>
  </div>
</template>

<script>
  import {userApi} from '@/api/userApi'
  import commonMixin from '@/components/mixin/common-mixin'
  import userBank from './user-manager/user-bank'
  import userWallet from './user-manager/user-wallet'
  import userAddress from './user-manager/user-Address'
  import userInviter from './user-manager/user-inviter'

  export default {
    components: {userBank, userWallet, userAddress, userInviter},
    data() {
      return {
        userId: this.$route.params.userId,
        user: {},
      }
    },
    async mounted() {
      const userId = this.userId;
      // 获取用户详情
      const user = await userApi.getUserDetail(userId);
      this.user = user;
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
            return '其他';
        }
      },
    }
  }
</script>

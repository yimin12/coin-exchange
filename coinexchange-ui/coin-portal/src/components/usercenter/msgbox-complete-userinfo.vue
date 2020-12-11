<template>
  <div>
  </div>
</template>

<script type="text/ecmascript-6">
  import { getAuth } from 'api/usercenter'
  import { OK } from 'api/config'
  import { mapMutations } from 'vuex'

  export default {
    data() {
      return {
        // isLogin1:false
      }
    },
    methods: {
      completeUserInformation() {
        this.$confirm('请设置用户名、绑定邮箱、交易密码', '提示', {
          confirmButtonText: '去设置',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.$router.push("/usercenter/complete-userinfo")
          //modify-password-trading
        }).catch(() => {

        });
      },

      ...mapMutations({
        setTrueName:'SET_TRUENAME',
        setIsAuth:'SET_ISAUTH',
        setPayPassword:'SET_PAYPASSWORD'
      }),


    }
  }
</script>


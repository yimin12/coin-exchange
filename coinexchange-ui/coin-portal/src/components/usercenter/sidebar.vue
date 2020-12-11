<template>
  <div class="ucenter-sidebar">
    <div class="ucenter-header">
      <h3 class="title">{{$t('m.navheader.usercenter')}}</h3>
    </div>
    <ul class="ucenter-body">
      <li v-for="(v ,index) of sidebar" :class="{active:activeId === index}" v-show="v.visible">
        <router-link :to="v.href">
          <i class="icon" :class="'icon-'+index"></i>
          <span class="text">{{i18nMap[index][lang]}}</span>
        </router-link>
      </li>
    </ul>
  </div>
</template>
<style scoped lang="stylus">
  .ucenter-sidebar {
    position absolute
    top 0
    left 0
    bottom 0
    width 220px
    background #fff
    border-radius 10px
    .ucenter-header {
      display flex
      align-items center
      padding 0 40px
      height 80px
      border-bottom 1px solid rgba(170, 171, 177, .5)
      color #454545
      .title {
        font-size 20px
      }
    }
    .ucenter-body {
      padding 28px 40px
      & > li {
        margin-bottom 30px
        .icon {
          width 26px
          height 22px
          background-repeat no-repeat
          background-position center left
          padding-right 15px
        }
        .icon-0 {
          background-image url('../../assets/usercenter/user.png')
        }
        .icon-1 {
          background-image url('../../assets/usercenter/shenfenzheng.png')
        }
        .icon-2 {
          background-image url('../../assets/usercenter/shouji.png')
        }
        .icon-3 {
          background-image url('../../assets/usercenter/denglumima.png')
        }
        .icon-4 {
          background-image url('../../assets/usercenter/zijinmima.png')
        }
        .icon-5 {
          background-image url('../../assets/usercenter/google.png')
        }
        .icon-6 {
          background-image url('../../assets/usercenter/tuiguang.png')
        }
        .icon-7 {
          background-image url('../../assets/usercenter/jiangli.png')
        }
        /*.icon-8 {
          background-image url('../../assets/usercenter/comunity.png')
        }*/
        .text {
        }
        & > a {
          font-size 16px
          color #AAABB1
          display flex
          justify-items space-between
          align-items center
          min-height 22px
          line-height 22px
        }
      }
      & > li:hover,
      & > li.active {
        .icon-0 {
          background-image url('../../assets/usercenter/user-active.png')
        }
        .icon-1 {
          background-image url('../../assets/usercenter/shenfenzheng-active.png')
        }
        .icon-2 {
          background-image url('../../assets/usercenter/shouji-active.png')
        }
        .icon-3 {
          background-image url('../../assets/usercenter/denglumima-active.png')
        }
        .icon-4 {
          background-image url('../../assets/usercenter/zijinmima-active.png')
        }
        .icon-5 {
          background-image url('../../assets/usercenter/google-active.png')
        }
        .icon-6 {
          background-image url('../../assets/usercenter/tuiguang-active.png')
        }
        .icon-7 {
          background-image url('../../assets/usercenter/jiangli-active.png')
        }
        /*.icon-8 {
          background-image url('../../assets/usercenter/comunity-active.png')
        }*/
        & > a {
          color #7392FF
        }
      }
    }
  }
</style>
<script>
  import {mapGetters} from 'vuex';

  export default {
    data() {
      return {
        i18nMap: [
          {
            'zh-CN': '账号信息',
            'zh-HK': '賬號信息',
            'en-US': 'Account info'
          },
          {
            'zh-CN': '身份认证',
            'zh-HK': '身份認證',
            'en-US': 'ID Card'
          },
          {
            'zh-CN': '手机号码',
            'zh-HK': '手機號碼',
            'en-US': 'Phone No.'
          },
          {
            'zh-CN': '登录密码',
            'zh-HK': '登錄密碼',
            'en-US': 'Login password'
          },
          {
            'zh-CN': '交易密码',
            'zh-HK': '交易密碼',
            'en-US': 'Pay password'
          },
          {
            'zh-CN': 'Google验证',
            'zh-HK': 'Google驗證',
            'en-US': 'Google verification'
          },
          {
            'zh-CN': '推广佣金',
            'zh-HK': '推廣傭金',
            'en-US': 'Promotion commission'
          },
          {
            'zh-CN': '邀请奖励',
            'zh-HK': '邀請獎勵',
            'en-US': 'Invitation reward'
          },
         /* {
            'zh-CN': '矿池社区',
            'zh-HK': '礦池社區',
            'en-US': 'mine pool community'
          }*/
        ]
      }
    },

    props: {
      activeId: 0
    },
    computed: {
      ...mapGetters(['setPayPassword','lang','gaStatus']),
      sidebar() {
        return [
          {
            title: '',
            href: '/usercenter',
            visible: true
          },
          {
            title: '',
            href: '/usercenter/modify-id',
            visible: true
          },
          {
            title: '',
            href: '/usercenter/modify-phone',
            visible: true
          },
          {
            title: '',
            href: '/usercenter/modify-password',
            visible: true
          },
          {
            title: '',
            href: this.setPayPassword ? '/usercenter/modify-password-trading' : '/usercenter/set-password-trading',
            visible: true
          },
          {
            title: '',
            href: this.gaStatus?'/usercenter/modify-google-auth':'/usercenter/set-google-auth',
            visible: true
          },
          {
            title: '',
            href: '/usercenter/modify-mail',
            visible: false
          },
          {
            title: '',
            href: '/usercenter/invite-reward',
            visible: true
          },
          // {
          //   title: '',
          //   href: '/usercenter/pool',
          //   visible: true
          // },
        ]
      }
    }
  }
</script>

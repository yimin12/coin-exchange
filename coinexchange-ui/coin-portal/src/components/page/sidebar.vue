<template>
  <div class="ucenter-sidebar">
    <ul class="navi">
      <li>
        <router-link to="/s/notice">{{$t('m.footer.notice')}}</router-link>
      </li>
      <li>
        <router-link to="/s/about">{{$t('m.footer.about')}}</router-link>
      </li>
      <li>
        <router-link to="/s/rate">{{$t('m.footer.rate')}}</router-link>
      </li>
      <li>
        <router-link to="/s/term">{{$t('m.footer.term')}}</router-link>
      </li>
      <li>
        <router-link to="/s/help">{{$t('m.footer.help')}}</router-link>
      </li>
      <!--<li>-->
        <!--<router-link to="/s/api">{{$t('m.footer.api')}}</router-link>-->
      <!--</li>-->
      <li>
        <router-link to="/s/order">{{$t('m.footer.order')}}</router-link>
      </li>
      <li>
        <router-link to="/s/apply">{{$t('m.footer.apply')}}</router-link>
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
    height: 530px;
    .navi {
      li {
        margin-top 45px
        a {
          font-size 16px
          color #AAABB1
          padding-left 40px
          border-left 3px solid #fff
          &.router-link-active {
            border-left-color #7392FF
            color #7392FF
          }
        }
      }
    }
  }
</style>

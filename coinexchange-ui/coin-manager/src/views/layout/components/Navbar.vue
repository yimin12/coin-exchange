<template>
  <div>
    <el-menu
      :default-active="activeIndex"
      class="el-menu-demo"
      mode="horizontal"
      background-color="#2E4155"
      text-color="#fff"
      active-text-color="#ffd04b"
      :router="true"
    >
      <el-menu-item
        :index="item.path"
        :key="item.path"
        v-for="item in topMenu"
        v-if="ids.indexOf(item.id)!==-1"
        @click="handleTopMenuClick(item)">
        {{item.meta.title}}
      </el-menu-item>
    </el-menu>

    <el-menu class="navbar" mode="horizontal">
      <hamburger class="hamburger-container" :toggleClick="toggleSideBar"
                 :isActive="sidebar.opened"></hamburger>

      <breadcrumb class="breadcrumb-container"></breadcrumb>


      <div class="right-menu">
        <error-log class="errLog-container right-menu-item"></error-log>

        <el-tooltip effect="dark" :content="$t('navbar.screenfull')" placement="bottom">
          <screenfull class="screenfull right-menu-item"></screenfull>
        </el-tooltip>

        <!--<lang-select class="international right-menu-item"></lang-select>-->

        <el-tooltip effect="dark" :content="$t('navbar.theme')" placement="bottom">
          <theme-picker class="theme-switch right-menu-item"></theme-picker>
        </el-tooltip>

        <el-dropdown class="avatar-container right-menu-item" trigger="click">
          <div class="avatar-wrapper">
            <img class="user-avatar" :src="avatar+'?imageView2/1/w/80/h/80'">
            <i class="el-icon-caret-bottom"></i>
          </div>
          <el-dropdown-menu slot="dropdown">
            <router-link to="/">
              <el-dropdown-item>
                {{$t('navbar.dashboard')}}
              </el-dropdown-item>
            </router-link>

            <el-dropdown-item divided>
              <span @click="logout" style="display:block;">{{$t('navbar.logOut')}}</span>
            </el-dropdown-item>
          </el-dropdown-menu>
        </el-dropdown>
      </div>
    </el-menu>
  </div>
</template>
<script>
  import {mapGetters} from 'vuex'
  import Breadcrumb from '@/components/Breadcrumb'
  import Hamburger from '@/components/Hamburger'
  import ErrorLog from '@/components/ErrorLog'
  import Screenfull from '@/components/Screenfull'
  import LangSelect from '@/components/LangSelect'
  import ThemePicker from '@/components/ThemePicker'

  export default {
    components: {
      Breadcrumb,
      Hamburger,
      ErrorLog,
      Screenfull,
      LangSelect,
      ThemePicker
    },
    computed: {
      ...mapGetters([
        'sidebar',
        'name',
        'avatar',
        'topMenu',
        "ids"
      ])
    },
    data() {
      return {
        activeIndex: "",
        // topMenu:[{name:"网站运营",path:"/website-operation"},
        //   {name:"统计分析",path:"/statistic"},
        //   {name:"用户中心",path:"/usercenter"},
        //   {name:"财务管理",path:"/finance"},
        //   {name:"资金明细",path:"/assets"},
        //   {name:"币币交易参数",path:"/trade-config"},
        //   {name:"参数配置",path:"/normal-config"},
        //   {name:"创新交易参数",path:"/forex-config"},
        //   {name:"系统配置",path:"/system-config"},
        // ]
      }
    },
    mounted() {
      const setSideMenu = this.setSideMenu;
      if (setSideMenu) {
        return;
      }
      const currentPath = this.$route.path;
      const pathArray = currentPath.split('/').filter(item => !!item);
      if (!pathArray || pathArray.length < 1) {
        return;
      }
      const menuItem = this.topMenu.find(item => item.path === `/${pathArray[0]}`);
      if (!menuItem) {
        return;
      }
      this.activeIndex = menuItem.path;
      this.$store.dispatch('setSideMenu', menuItem)
    },
    methods: {
      handleTopMenuClick(item) {
        for (let i = 0; i < this.topMenu.length; i++) {
          let menuItem = this.topMenu[i];
          if (menuItem.id === item.id) {
            console.log(menuItem)
            this.$store.dispatch('setSideMenu', menuItem).then(() => {
            })
          }
        }

      },
      toggleSideBar() {
        this.$store.dispatch('toggleSideBar')
      },
      logout() {
        this.$store.dispatch('LogOut').then(() => {
          location.reload()// In order to re-instantiate the vue-router object to avoid bugs
        })
      }
    }
  }
</script>

<style rel="stylesheet/scss" lang="scss" scoped>
  .center-menu {
    border: 1px solid #f00;
    display: flex;

  }

  .navbar {
    height: 50px;
    line-height: 50px;
    border-radius: 0px !important;
    .hamburger-container {
      line-height: 58px;
      height: 50px;
      float: left;
      padding: 0 10px;
    }
    .breadcrumb-container {
      float: left;
    }

    .errLog-container {
      display: inline-block;
      vertical-align: top;
    }
    .right-menu {
      float: right;
      height: 100%;
      &:focus {
        outline: none;
      }
      .right-menu-item {
        display: inline-block;
        margin: 0 8px;
      }
      .screenfull {
        height: 20px;
      }
      .international {
        vertical-align: top;
      }
      .theme-switch {
        vertical-align: 15px;
      }
      .avatar-container {
        height: 50px;
        margin-right: 30px;
        .avatar-wrapper {
          cursor: pointer;
          margin-top: 5px;
          position: relative;
          .user-avatar {
            width: 40px;
            height: 40px;
            border-radius: 10px;
          }
          .el-icon-caret-bottom {
            position: absolute;
            right: -20px;
            top: 25px;
            font-size: 12px;
          }
        }
      }
    }
  }
</style>

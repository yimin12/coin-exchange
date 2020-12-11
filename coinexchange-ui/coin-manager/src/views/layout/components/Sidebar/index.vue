<template>
  <el-scrollbar wrapClass="scrollbar-wrapper">
    <el-menu
      mode="vertical"
      :show-timeout="200"
      :default-active="$route.path"
      :collapse="isCollapse"
      background-color="#304156"
      text-color="#bfcbd9"
      active-text-color="#409EFF"
      :default-openeds="defaultOpends"
    >
      <sidebar-item :routes="permission_routers" :ids="ids" :sideMenu="sideMenu"></sidebar-item>
    </el-menu>
  </el-scrollbar>
</template>

<script>
import { mapGetters } from 'vuex'
import SidebarItem from './SidebarItem'

export default {
  components: { SidebarItem },
  computed: {
    ...mapGetters([
      'permission_routers',
      'sidebar',
      "ids",
      "sideMenu",
      "topMenu"
    ]),
    isCollapse() {
      return !this.sidebar.opened
    }
  },
  data(){
    return {
      defaultOpends:''
    }
  },
  created(){
    this.defaultOpends = this.topMenu.map((item)=>{

      return item.name
    })
    console.log(this.defaultOpends)
  }
}
</script>

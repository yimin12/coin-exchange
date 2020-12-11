<template>
  <div class="menu-wrapper">
    <template  v-if="sideMenu&&!sideMenu.hidden&&sideMenu.children">
      <el-submenu :index="sideMenu.name||sideMenu.path" :key="sideMenu.name">
        <template slot="title">
          <svg-icon v-if="sideMenu.meta&&sideMenu.meta.icon" :icon-class="sideMenu.meta.icon"></svg-icon>
          <span v-if="sideMenu.meta&&sideMenu.meta.title" slot="title">{{sideMenu.meta.title}}</span>
        </template>

        <template v-for="child in sideMenu.children" v-if="!child.hidden &&ids.indexOf(child.id)>-1" >
          <sidebar-item  class="nest-menu" v-if="child.children&&child.children.length>0 " :sideMenu="child" :key="child.path" :ids="ids"></sidebar-item>
          <router-link v-else :to="sideMenu.path+'/'+child.path" :key="child.name">
            <el-menu-item :index="sideMenu.path+'/'+child.path">
              <svg-icon v-if="child.meta&&child.meta.icon" :icon-class="child.meta.icon"></svg-icon>
              <span v-if="child.meta&&child.meta.title " slot="title">{{child.meta.title}}</span>
            </el-menu-item>
          </router-link>
        </template>
      </el-submenu>

    </template>
  </div>
</template>

<script>
import { generateTitle } from '@/utils/i18n'

export default {
  name: 'SidebarItem',
  props: {
    routes: {
      type: Array
    },
    // 当前路由
    sideMenu:{
      type: Object,
      default:function () {
        return {}
      }
    },
    ids: {
      type: Array,
      default:function () {
        return []
      }
    },
    isNest: {
      type: Boolean,
      default: false
    }
  },
  methods: {
    generateTitle
  }
}
</script>


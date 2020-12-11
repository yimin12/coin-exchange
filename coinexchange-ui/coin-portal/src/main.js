import 'babel-polyfill'
import Vue from 'vue'
import App from './App'
import router from './router'
import fastclick from 'fastclick'
import VueLazyload from 'vue-lazyload'
import store from './store'
import ElementUI from 'element-ui'

const zhCN = require( 'common/lang/zh')
const zhHK = require('common/lang/zh-hk')
const enUS = require('common/lang/en')

import eleLocaleZhCn from 'element-ui/lib/locale/lang/zh-CN' // element-ui lang
import eleLocaleEnUs from 'element-ui/lib/locale/lang/en'// element-ui lang
import eleLocaleZhHk from 'element-ui/lib/locale/lang/zh-TW'// element-ui lang

import 'element-ui/lib/theme-chalk/index.css'
import 'common/stylus/index.styl'

import 'vodal/common.css'
import 'vodal/rotate.css'

import 'common/stylus/patch.styl'

import VueI18n from 'vue-i18n'
import sock from './plugin/sock'

Vue.use(sock)
Vue.use(VueI18n) // 通过插件的形式挂载


const messages = {
  'zh-CN' : {
    ...zhCN,
    ...eleLocaleZhCn
  },
  'zh-HK' : {
    ...zhHK,
    ...eleLocaleZhHk
  },
  'en-US' : {
    ...enUS,
    ...eleLocaleEnUs
  }
}

const i18n = new VueI18n({
  locale: 'zh-CN',    // 语言标识
  // this.$i18n.locale // 通过切换locale的值来实现语言切换
  messages
})

Vue.use(ElementUI, {
  i18n: (key, value) => i18n.t(key, value)
})
Vue.use(require('vue-wechat-title'))
/** ****vote******/
import BootstrapVue from 'bootstrap-vue'
import '../static/css/bootstrap.min.css'
import '../static/css/animate.min.css'
import 'bootstrap-vue/dist/bootstrap-vue.css'
import VModal from 'vue-js-modal'
import bus from 'components/vote/common/bus';
Vue.prototype.bus = bus

Vue.use(BootstrapVue)
Vue.use(VModal, {
  dialog: true,
  dynamic: true
})

/* eslint-disable no-new */
new Vue({
  el: '#app',
  router,
  store,
  i18n,
  render: h => h(App)

})


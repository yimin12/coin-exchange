<template>
  <div id="app">
    <demo-error-modal/>
    <demo-conditional-modal/>
    <v-dialog/>
    <router-view></router-view>
  </div>
</template>

<script type="text/ecmascript-6">
import DemoErrorModal from "./components/vote/comportents/DemoErrorModal";
import DemoConditionalModal from "./components/vote/comportents/ConditionalModal";
import VModal from "vue-js-modal";

  export default {
    components: {
     DemoErrorModal,
     DemoConditionalModal
    }
  }
</script>

<style lang="stylus" rel="stylesheet/stylus">
  html{
    position: relative;
  }
  html, body , #app{
    height: auto;
    min-height: 100%;
  }

  /* 重定义element UI 表单placeholder样式 */
  .el-input__inner::-webkit-input-placeholder {
    color #aaabb1
  }
  .el-input__inner:-ms-input-placeholder { // IE10+
    color #aaabb1
  }
  .el-input__inner:-moz-placeholder { // Firefox4-18
    color #aaabb1
  }
  .el-input__inner::-moz-placeholder { // Firefox19+
    color #aaabb1
  }

  .el-dropdown-menu__item:focus,
  .el-dropdown-menu__item:not(.is-disabled):hover {
    background-color #e9eeff !important
    color #7392FF !important
  }

  .flip-list-item {
    list-style-type: none;
    /**
     * 可以在v-enter-active和v-move中分别用transition过渡，也可以在item中用transition，包含了这两项
     * 要用all不用transform，有可能是因为splice删除效果不是transform
     */
    /* transition: all 1s; */
  }

  .flip-list-enter-active, .flip-list-leave-active {
    transition: all 1s;
  }

  .flip-list-move {
    transition: all 1s;
  }

  .flip-list-enter, .flip-list-leave-to {
    opacity: 0;
    transform: translateX(50px);
  }

  /**
   * 要让删除的元素先脱离文档流，旁边的元素才能过渡过来
   */
  .flip-list-leave-active {
    position: absolute;
  }

</style>

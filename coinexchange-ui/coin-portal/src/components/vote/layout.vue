<template>
  <div>
    <NavHeader></NavHeader>
    <router-view></router-view>
    <mFooter></mFooter>
  </div>
</template>
<script>
import NavHeader from "components/nav-header/nav-header";
import mFooter from "components/m-footer/m-footer";

// Vue.use(BootstrapVue);
// Vue.use(VModal, {
//   dialog: true,
//   dynamic: true
// });

export default {
  components: {
    NavHeader,
    mFooter
    // DemoErrorModal,
    // DemoConditionalModal
  }
};
</script>

<style lang="css" scoped>
div{
  font-family: "微软雅黑";
}
</style>

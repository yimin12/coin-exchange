<template>
  <!--<p class="title" ref="title">-->
  <!--<span>{{first}}/</span>-->
  <!--<span>{{second}}</span>-->
  <div class="finance-header">


    <el-breadcrumb separator="/" ref="title">
      <el-breadcrumb-item :to="{ path: jumpto }">{{first}}</el-breadcrumb-item>
      <el-breadcrumb-item v-if="second">{{second}}</el-breadcrumb-item>
    </el-breadcrumb>
  </div>
  <!--</p>-->


</template>
<script>
  export default {
    //noborder 标题不需要下边框
    props: ["first", "second", 'noborder', 'jumpto'],

    data(){
      return {}
    },
    mounted(){
      if (this.noborder == true) {
        this.$refs.title.style = "border-bottom:none"
      }
    }
  }
</script>
<style scoped lang="stylus" rel="stylesheet/stylus">
  @import "~common/stylus/variable"
  .finance-header {
    background #fff
  }

  .el-breadcrumb__item {
    color #f00

  }

  .el-breadcrumb__inner {
    color #f00
  }

  .el-breadcrumb {
    margin-top 20px
    height 50px
    line-height 50px
    padding 0 20px
    border: 1px solid $color-global-border
    color #f00

  }
</style>

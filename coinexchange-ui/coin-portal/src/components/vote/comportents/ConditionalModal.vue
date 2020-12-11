<template>
<div class="vote-page">

  <modal name="conditional-modal" :adaptive="true" :max-width="500" :max-height="300" width="80%" height="50%">
    <div class="bgcolor" style="padding:30px; text-align: center">
      <p class="text-light" href="javascript:void(0)" onMouseOut="hideImg()" onmouseover="showImg()">扫码分享拉票</p>
    </div>
    <div class="container">
      <p class=" d-flex justify-content-around">
        <img src="../../../assets/vote/qq.svg">
        <img src="../../../assets/vote/weibo.svg">
        <img src="../../../assets/vote/wechat.svg">
      </p>
      <p class="d-flex justify-content-around">
        <img class="qrcode" src="../../../assets/vote/QR.png">
        <img class="qrcode" src="../../../assets/vote/QR.png">
        <img class="qrcode" src="../../../assets/vote/QR.png">
      </p>
    </div>
  </modal>
</div>
  
</template>
<script>
export default {
  name: "ConditionalModal",

  data() {
    return {
      showText: false,
      hideText: true
    };
  },
  methods: {
    // beforeOpen(event) {
    //   console.log('Event:', event)
    //   console.log('Params:', event.params)
    //   if (event.params.show === false) {
    //     event.stop()
    //   }
    // }
    // showImg() {
    //   $("wxImg").style.display = "block";
    // },
    // hideImg() {
    //   $("wxImg").style.display = "none";
    // }
  },
  // overShow() {
  //   this.showText = !this.showText;
  //   this.hideText = !this.hideText;
  // },
  // outHide() {
  //   this.showText = !this.showText;
  //   this.hideText = !this.hideText;
  // }
};
</script>


<style lang="stylus" scoped>
.bgcolor {
  background-color: #262a42;
}

.container {
  width: 100%;
  height: 100%;
  background-color: #262a42;

  img {
    width: 50px;
    height: 50px;
  }

  .qrcode {
    width: 100px;
    height 100px;
  }
}
</style>

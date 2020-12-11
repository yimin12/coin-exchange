<template>
  <div class="login-container">
    <div id="canvascontainer" ref='can'></div>

    <el-form class="login-form" autoComplete="on" :model="loginForm" :rules="loginRules"
             ref="loginForm" label-position="left">
      <div class="title-container">
        <h3 class="title">{{$t('login.title')}}</h3>
        <!--<lang-select class="set-language"></lang-select>-->
      </div>
      <el-form-item prop="username">
        <span class="svg-container svg-container_login">
          <svg-icon icon-class="user"/>
        </span>
        <el-input name="username" type="text" v-model="loginForm.username" autoComplete="on"
                  placeholder="username"/>
      </el-form-item>

      <el-form-item prop="password">
        <span class="svg-container">
          <svg-icon icon-class="password"/>
        </span>
        <el-input name="password" :type="passwordType" @keyup.enter.native="handleLogin"
                  v-model="loginForm.password" autoComplete="on" placeholder="password"/>
        <!--<span class="show-pwd" @click="showPwd">-->
        <!--<svg-icon icon-class="eye"/>-->
        <!--</span>-->
      </el-form-item>

      <!--<el-row>
        <el-col :span="15"><el-form-item prop="code">
          <el-input name="code" type="text"
                    v-model="loginForm.code" autoComplete="on" placeholder="请输入验证码"/>
          </el-form-item>
        </el-col>
        <el-col :span="9">
          <el-button style="width:100%;height:47px;"
            @click="onGetCode"
            :disabled="isdisabled"
          >
            {{labelText}}
          </el-button>
        </el-col>
      </el-row>-->

      <el-button
        type="primary"
        style="width:100%;margin-bottom:30px;"
        :loading="loading"
        @click.native.prevent="handleLogin">
        {{$t('login.logIn')}}
      </el-button>

      <!--<div class="tips">-->
      <!--<span>{{$t('login.username')}} : admin</span>-->
      <!--<span>{{$t('login.password')}} : {{$t('login.any')}}</span>-->
      <!--</div>-->

      <!--<div class="tips">-->
      <!--<span style="margin-right:18px;">{{$t('login.username')}} : editor</span>-->
      <!--<span>{{$t('login.password')}} : {{$t('login.any')}}</span>-->
      <!--</div>-->

      <!--<el-button class="thirdparty-button" type="primary" @click="showDialog=true">{{$t('login.thirdparty')}}</el-button>-->
    </el-form>

    <!--<el-dialog :title="$t('login.thirdparty')" :visible.sync="showDialog" append-to-body>-->
    <!--{{$t('login.thirdpartyTips')}}-->
    <!--<br/>-->
    <!--<br/>-->
    <!--<br/>-->
    <!--<social-sign />-->
    <!--</el-dialog>-->
  </div>
</template>

<script>
  import {isvalidUsername} from '@/utils/validate'
  import LangSelect from '@/components/LangSelect'
  import SocialSign from './socialsignin'

  export default {

    components: {LangSelect, SocialSign},
    name: 'login',
    data() {
      // const validateUsername = (rule, value, callback) => {
      //   if (!isvalidUsername(value)) {
      //     callback(new Error('Please enter the correct user name'))
      //   } else {
      //     callback()
      //   }
      // }
      // const validatePassword = (rule, value, callback) => {
      //   if (value.length < 6) {
      //     callback(new Error('The password can not be less than 6 digits'))
      //   } else {
      //     callback()
      //   }
      // }
      return {
        loginForm: {
          username: '',
          password: ''
        },
        loginRules: {
          username: [{required: true, message: "请输入用户名", trigger: 'blur',}],
          password: [{required: true, message: "请输入密码", trigger: 'blur',}]
        },
        labelText: '获取验证码',
        passwordType: 'password',
        loading: false,
        showDialog: false,
        isdisabled: false
      }
    },
    methods: {
      showPwd() {
        if (this.passwordType === 'password') {
          this.passwordType = ''
        } else {
          this.passwordType = 'password'
        }
      },
      handleLogin() {
        this.$refs.loginForm.validate(valid => {
          if (valid) {
            this.loading = true
            // this.loginForm.password = md5(this.loginForm.password)
            this.$store.dispatch('LoginByUsername', this.loginForm).then((response) => {
              this.loading = false
              this.$router.push({path: '/'})
              // 这里要做判断 有些角色 没有权限调用此接口
              //trade_coin_query  trade_market_query
              let authorities = response.authorities
              authorities.forEach(item=>{
                if(item.authority === "trade_coin_query"){
                  this.$store.dispatch('CoinAll');//获取所有币种信息
                }
                if(item.authority === "trade_market_query"){
                  this.$store.dispatch('MarketAll');//获取所有交易市场信息
                }
              })

            }).catch(() => {
              this.loading = false
            })
          } else {
            console.log('error submit!!')
            return false
          }
        })
      },
      onGetCode(){
        var countdown = 60;
        var text = this;

        function settime(){

          console.log("第二层this:"+text)

          if(countdown === 0){
            text.isdisabled = false;
            text.labelText = "获取验证码";
            countdown = 60;
            return false;
          }else{
            text.isdisabled = true;
            text.labelText = countdown + "s";
            countdown--
          }

          setTimeout(function() {
            settime();
          },1000);
        }

        settime();
      }
    },
    created() {
      // window.addEventListener('hashchange', this.afterQRScan)
    },
    mounted() {
      container = document.createElement('div');
      this.$refs.can.appendChild(container);

      camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 1, 10000);
      camera.position.z = 1000;

      scene = new THREE.Scene();

      particles = new Array();

      var PI2 = Math.PI * 2;
      var material = new THREE.ParticleCanvasMaterial({

        color: 0x0078de,
        program: function (context) {

          context.beginPath();
          context.arc(0, 0, 1, 0, PI2, true);
          context.fill();

        }

      });

      var i = 0;

      for (var ix = 0; ix < AMOUNTX; ix++) {

        for (var iy = 0; iy < AMOUNTY; iy++) {

          particle = particles[i++] = new THREE.Particle(material);
          particle.position.x = ix * SEPARATION - ((AMOUNTX * SEPARATION) / 2);
          particle.position.z = iy * SEPARATION - ((AMOUNTY * SEPARATION) / 2);
          scene.add(particle);

        }

      }

      renderer = new THREE.CanvasRenderer();
      renderer.setSize(window.innerWidth, window.innerHeight);
      container.appendChild(renderer.domElement);

      document.addEventListener('mousemove', onDocumentMouseMove, false);
      //

      window.addEventListener('resize', onWindowResize, false);

      animate();
    },
    destroyed() {
      // window.removeEventListener('hashchange', this.afterQRScan)
    }
  }


  var SEPARATION = 100, AMOUNTX = 50, AMOUNTY = 50;

  var container;
  var camera, scene, renderer;

  var particles, particle, count = 0;

  var mouseX = 0, mouseY = 0;

  var windowHalfX = window.innerWidth / 2;
  var windowHalfY = window.innerHeight / 2;


  // animate();

  function init() {


  }

  function onWindowResize() {

    windowHalfX = window.innerWidth / 2;
    windowHalfY = window.innerHeight / 2;

    camera.aspect = window.innerWidth / window.innerHeight;
    camera.updateProjectionMatrix();

    renderer.setSize(window.innerWidth, window.innerHeight);

  }

  //

  function onDocumentMouseMove(event) {

    mouseX = event.clientX - windowHalfX;
    mouseY = event.clientY - windowHalfY;

  }

  function onDocumentTouchStart(event) {

    if (event.touches.length === 1) {

      event.preventDefault();

      mouseX = event.touches[0].pageX - windowHalfX;
      mouseY = event.touches[0].pageY - windowHalfY;

    }

  }

  function onDocumentTouchMove(event) {

    if (event.touches.length === 1) {

      event.preventDefault();

      mouseX = event.touches[0].pageX - windowHalfX;
      mouseY = event.touches[0].pageY - windowHalfY;

    }

  }

  //

  function animate() {

    requestAnimationFrame(animate);

    render();


  }

  function render() {

    camera.position.x += (mouseX - camera.position.x) * .05;
    camera.position.y += (-mouseY - camera.position.y) * .05;
    camera.lookAt(scene.position);

    var i = 0;

    for (var ix = 0; ix < AMOUNTX; ix++) {

      for (var iy = 0; iy < AMOUNTY; iy++) {

        particle = particles[i++];
        particle.position.y = (Math.sin((ix + count) * 0.3) * 50) + (Math.sin((iy + count) * 0.5) * 50);
        particle.scale.x = particle.scale.y = (Math.sin((ix + count) * 0.3) + 1) * 2 + (Math.sin((iy + count) * 0.5) + 1) * 2;

      }

    }

    renderer.render(scene, camera);

    count += 0.1;

  }
</script>

<style rel="stylesheet/scss" lang="scss">

</style>

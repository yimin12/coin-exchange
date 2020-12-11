<template id="a">
  <div class="layoutcontent vote-page">
    <!--<link rel="stylesheet" href="/static/css/bootstrap.css">-->
    <!--<link rel="stylesheet" href="/static/css/bootstrap-vue.css">-->
    <!-- banner -->
    <div class="jumbotron banner">
      <div class="container banner">
        <h2 class="bannerinfo text-center" style="color:#0b93b9">投票上币，由你做主</h2>
        <p class="banner_times text-light text-center">每投 1 票需支付1 HHT，每次可以投多票，可以多次投票。投票支付的HHT不予退还。<br>以每期投票截止时间为准，票数榜排名靠前(同时参考人气榜排名)的项目可上线HashToken。
        </p>
      </div>
      <p class="text-center">
        <router-link to="/vote/help?tab=2" class="btn btn-outline-primary btn-lg" role="button" target="_blank">投币上币规则</router-link>
      </p>
    </div>

    <!-- body -->
    <!-- 倒计时 -->
    <div class="container infobar" style="background-color:#181b2a;">
      <div class="nextime">
        <span>下期投票开始时间</span>
        :
        <em v-if="nextime">{{nextime.beginTime}}</em>
        <br>
        <span class="nextimeInfo">本期投票已结束，投票结果请关注公告</span>
        <router-link to="/vote/help?tab=1" target="_blank"><img src="../../assets/vote/hand.svg"> 上币申请</router-link>
      </div>
    </div>

    <div class="container ">
      <!-- 币种排名 -->
      <b-card no-body style="background-color:#181b2a;">
        <b-tabs pills card>
          <b-tab title="票数榜" active>

            <div v-for="(item,index) in ByTicket" :key="index.id" class="content">

              <div class="checkinfoticket d-flex justify-content-between">
                <div class="left d-flex align-items-center">
                  <div class="crownbg">
                    <p class="crown text-center">{{index+1}}</p>
                  </div>

                  <div class="right">
                    <h3 class="text-light">{{item.title}}</h3>
                    <span class="text-info">{{item.name}}</span>
                  </div>
                </div>

                <div class="d-flex d-flex align-items-center">
                  <button type="button" @click="toggle(index)" class="btn btn-outline-primary">查看介绍</button>
                </div>

                <div class="totalTicket d-flex align-items-center">
                  <div class="vote d-flex flex-column">
                    <b class="text-light" style="font-size:24px;">{{item.totalTicket}}
                      <strong class="text-danger">票</strong>
                    </b>
                    <span class="text-secondary">{{item.totalPeople}}
                      <strong class="text-warning">人支持</strong>
                    </span>
                  </div>
                </div>

                <div class="votebutton d-flex align-items-center">
                  <button type="button" @click="conditionalShow" class="btn btn-outline-primary">为他拉票</button>
                  <button type="button" v-if="item.enableVote==1" @click="$modal.show('error-modal');spot(item);" class="btn btn-primary">开始投票</button>
                  <b-btn v-else disabled variant="secondary">暂停投票</b-btn>
                </div>

              </div>
              <div class="checkinfo">
                <div v-show="index==isShow" class="showinfo ">
                  <b-card>
                    <p class="card-text text-light">{{item.resume}}</p>
                    <a class="outlink" v-bind:href="item.officialUrl" target="_blank">前往官网</a>
                    <a class="outlink" v-bind:href="item.whitebookUrl" target="_blank">查看白皮书</a>
                  </b-card>
                </div>
              </div>

            </div>
          </b-tab>

          <!-- 人数榜 -->
          <b-tab title="人数榜">
            <div v-for="(post,index) in PeopleData" :key="index.id" class="content">

              <div class="checkinfoticket d-flex justify-content-between">
                <div class="left d-flex align-items-center">
                  <div class="crownbg">
                    <p class="crown text-center">{{index+1}}</p>
                  </div>

                  <div class="right">
                    <h3 class="text-light">{{post.title}}</h3>
                    <span class="text-info">{{post.name}}</span>
                  </div>
                  <div>
                  </div>
                </div>

                <div class="d-flex d-flex align-items-center">
                  <button type="button" @click="toggle(index)" class="btn btn-outline-primary">查看介绍</button>
                </div>
                <div class="totalTicket d-flex align-items-center">
                  <div class="vote d-flex flex-column">
                    <b class="text-light" style="font-size:24px;">{{post.totalTicket}}
                      <strong class="text-danger">票</strong>
                    </b>
                    <span class="text-secondary">{{post.totalPeople}}
                      <strong class="text-warning">人支持</strong>
                    </span>
                  </div>
                </div>

                <div class="votebutton d-flex align-items-center">

                  <button type="button" @click="conditionalShow" class="btn btn-outline-primary">为他拉票</button>
                  <button type="button" v-if="post.enableVote==1" @click="$modal.show('error-modal');spot(post);" class="btn btn-primary">开始投票</button>

                  <b-btn v-else disabled variant="secondary">暂停投票</b-btn>
                  <!-- <b-btn href="#" :disabled="disabled" variant="secondary">暂停投票</b-btn> -->

                </div>
              </div>
              <div class="checkinfo">
                <div v-show="index==isShow" class="showinfo ">
                  <b-card>
                    <p class="card-text text-light">{{post.resume}}</p>
                    <a class="outlink" v-bind:href="post.officialUrl" target="_blank">前往官网</a>
                    <a class="outlink" v-bind:href="post.whitebookUrl" target="_blank">查看白皮书</a>
                  </b-card>
                </div>
              </div>

            </div>
          </b-tab>
        </b-tabs>

      </b-card>
    </div>
  </div>
</template>


<script>
import axios from "axios";
import common from "./common/common";
// import bus from "./common/bus";

export default {
  name: "home",
  data () {
    return {
      ByTicket: {},
      PeopleData: {},
      nextime: {},
      UserVote: { balanceAmount: "1234123" },
      isShow: 0,
      // enableVote: 1, //测试用投票按钮
      // enableVote:0, // 根据后台返回true或false开关投票按钮
    };
  },
  ready () { },
  created () {
    this.getByTicket();
    this.getByPeopleData();
    this.getScheduleList();
  },
  mounted () {
    let that = this
    this.bus.$on("voteSuccess", function (msg) {
      that.getByTicket()
      that.getByPeopleData()
    });
  },
  methods: {
    toggle (index) {
      if (this.isShow == index) {
        this.isShow = -1;
      } else {
        this.isShow = index;
      }
    },
    conditionalShow () {
      this.$modal.show("conditional-modal");
    },
    isLogin (item) {
      console.log(isLogin);
      this.$store.commit(types.LOGIN);
      this.$router.push({
        path: "/login"
      });
    },
    getByPeopleData () {
      // 获取人数排行榜
      const url = `${
        common.apihost
        }vote/getVoteCandidateCoinListOrderByPeople.o`;

      axios.get(url).then(
        response => {
          this.PeopleData = response.data.result;
          console.log(response, 'response');
        },
        err => { }
      );
    },

    getScheduleList () {
      //获取下次投票开始时间
      const url = `${common.apihost}vote/getVoteScheduleList.o`;

      axios.get(url).then(
        response => {
          this.nextime = response.data.result[0];
          console.log(response);
        },
        err => { }
      );
    },

    getByTicket () {
      // 获取票数排名
      const url = `${
        common.apihost
        }vote/getVoteCandidateCoinListOrderByTicket.o`;

      axios.get(url).then(
        response => {
          this.ByTicket = response.data.result;
          console.log(response);
        },
        err => { }
      );
    },
    spot (item, post) {
      //监听A组件中的spot，并发送数据
      var msg = item.id;
      console.log(msg);
      this.bus.$emit("spot", msg);


    }
    /*
       getUserVote() {
       // 验证用户是否登录
       const url = `${
       common.apihost
       }/vote/serverGetBalanceAmountByUserIdAndCoinId.o`;
       axios.get(url).then(
       response => {
       // this.UserVote = response.data.result;
       if(response.statusCode == 1000){
       console.log('登录失败');
       }else{
       console.log('成功');
       }
       console.log(response);
       },
       err => {}
       )
       },
       */
  }
};
</script>

<style lang="stylus" scoped>
ul, li {
  padding: 0;
  margin: 0;
  list-style: none;
}

div.layoutcontent {
  background-color: #262a42;
  text-align: center;
}

div.jumbotron.banner {
  background: url('../../assets/vote/banner.jpg') no-repeat center center;
  height: 650px;
  margin-top: -2px;
  margin-bottom: 0px;
}

.container.infobar {
  width: 1108px;
  margin-bottom: 30px;
  border-radius: 20px;
  margin-top:30px;
  
}

.bannerinfo {
  font-size: 50px;
  font-weight: 400;
  margin-top: 200px;
  margin-bottom:40px;
}
.banner_times{
  line-height:30px;
}
div.nextime {
  padding-top: 50px;
  color: pink;
}

.container {
  min-height: 220px;
  padding-bottom:30px;
}

.checkinfoticket {
  height: 130px;

  .left {
    padding-left: 20px;
    width: 150px;
  }

  .crownbg {
    height: 50px;
    width: 50px;
    background: url('../../assets/vote/glod.svg');
    background-size: cover;
    display: block;
    margin-left: 20px;
    float: left;

    p.crown {
      width: 50px;
      height: 50px;
      line-height: 60px;
      font-size: 18px;
      color: black;
      font-weight: bold;
    }
  }

  a.outlink {
    padding-left: 30px;
  }

  .right {
    padding-left: 60px;
  }
}

div.card-body {
  background-color: #181b2a;
}
.votebutton{
  button{
    margin-left:10px;
  }
}
a.outlink {
  padding: 60px;
}
</style>


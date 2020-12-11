<template>
  <div class="vote-page">

    <modal name="error-modal" :classes="['v--modal', 'error-modal', hasBugs && 'has-bugs']" :pivot-y="0.2" :width="600" :height="400" @before-open="beforeOpen">
      <div class="error-modal-content">
        <div class="bugs-label text-light">票数: {{bugCount}}</div>
        <div class="username">
          <span class="text-light">账户可用积分:</span>
          <span class="text-light">{{voteNumber}}</span>
        </div>
        <!-- <input type="text"> -->
        <div class="input-group input-group-lg">
          <div class="input-group-prepend">
            <span class="input-group-text" id="inputGroup-sizing-sm">请输入数量</span>
          </div>
          <input v-model="bugCount" onkeyup="value=value.replace(/[^1234567890-]+/g,'')" maxlength="6" type="text" class="form-control done" aria-label="Small" aria-describedby="inputGroup-sizing-sm">
        </div>
        <button type="button" @click="createBug" class="btn btn-outline-primary">增加票数</button>
        <button type="button" @click="fixBug" class="btn btn-outline-primary">减少票数</button>

        <div style="padding: 50px;">
          <button type="button" @click="dj" class="btn btn-outline-primary">提交</button>
          <!--<el-button @click="dj">提交</el-button>-->
        </div>
      </div>
    </modal>
  </div>
</template>
<script>
import axios from "axios";
import common from "../common/common";
// import bus from "../common/bus";
// import { serverCreateVote, getUserVote } from '@/api/usercenter'
export default {
  name: "DemoErrorModal",
  data () {
    return {
      bugCount: {},
      // message: "",
      hasBugs: false,
      PeopleData: {},
      msg: "传值过来吖",
      voteNumber: 1
    };
  },
  beforeCreate () { },
  created () { },
  methods: {
    createBug () {
      this.bugCount++;
    },

    fixBug () {
      this.bugCount = Math.max(this.bugCount - 1, 0);
      this.hasBugs = false;
    },

    async beforeOpen (event) {
      this.bugCount = 1;
      try {
        let res = await getUserVote()
        if (res.statusCode == 0) {
          this.voteNumber = res.result.balanceAmount
        } else {
          throw new Error('获取用户积分失败')
        }
      } catch ({ message }) {
        this.$message.error(message);
      }
    },
    dj () {
      let that = this
      if (this.bugCount <= 0) {
        return that.$message({
          message: '输入无效',
          type: 'warning'
        });
      }
      serverCreateVote(this.bugCount, this.msg).then(({ statusCode, resultDesc }) => {
        if (statusCode == 1) {
          that.$message({
            message: '您的余额不足！',
            type: 'warning'
          });
        } else if (statusCode == 0) {
          that.bus.$emit('voteSuccess','success')
          that.$message({
            message: '投票成功！',
            type: 'success'
          });
        } else {
          that.$message({
            message: resultDesc,
            type: 'warning'
          });
        }
        that.$modal.hide('error-modal');
      }).catch((err) => {
        that.$message.error('投票失败！请联系管理员！');
      })
    }
  },
  mounted () {
    var _this = this;
    //监听A组件中的spot，并接受数据
    // this.bus.$on("spot", function (msg) {
    //   _this.msg = msg;
    // });
  }
};
</script>
<style lang="stylus" >
.error-modal {
      background: transparent !important;
  transition: box-shadow 1s;
  border-radius:10px !important;overflo:hidden;
    font-family: "微软雅黑";
    .username{
      margin-bottom:20px;
    }
    &.has-bugs {
      box-shadow: 0 24px 80px -2px rgba(255, 0, 0, 0.6) !important;
    }
}

.error-modal-content {
  padding: 10px;
  text-align: center;
  background-color: #262a42;

  .bugs-label {
    text-transform: uppercase;
    font-size: 60px;
    font-weight: 300;
        font-weight:400;

    letter-spacing: 2px;
    padding:30px 40px;
  }

  .input-group {
    margin-bottom: 30px;
  }

  button {
    width: 180px;
  }

  sub {
    color: #EC625F;
    transition: opacity 0.25s;
  }
}
</style>

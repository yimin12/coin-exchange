<template id="b" >
  <!-- <section class="article-info"> -->
  <main class="vote-page">
    <!--<link rel="stylesheet" href="/static/css/bootstrap.css">-->
    <!--<link rel="stylesheet" href="/static/css/bootstrap-vue.css">-->

    <div class="article-content d-flex justify-content-center">
      <div class="side-bar">
        <!-- <router-link to="page1"></router-link>
          <router-link to="page2"></router-link> -->
        <nav class="navbar navbar-light">
          <!-- <button to="/home" class="btn btn-outline-success" type="button">HHT上币申请说明</button> -->
          <!-- <button to="/tike" class="btn btn-outline-success" type="button">投票规则说明</button> -->
          <!-- <b-nav-item to="/tike" class="btn btn-sm btn-outline-success" type="button">投票规则说明</b-nav-item> -->
          <div @click="tab=1" class="a-lbl" :class="{'active-a':tab==1}">HHT上币申请说明</div>
          <div class="a-lbl" @click="tab=2" :class="{'active-a':tab==2}">投票规则说明</div>
          <!--<router-link to="/vote/help">HHT上币申请说明</router-link>-->
          <!--<router-link to="/vote/tike">投票规则说明</router-link>-->
        </nav>
        <!-- <div class="btn-group-vertical">
            HHT上币申请说明
          </div>
          <div class="btn-group-vertical">
            投票规则说明
          </div> -->
      </div>
      <div v-show="tab==1" class="article-body">
        <h1 class="text-light">HHT上币申请说明</h1>
        <p>为了保护投资者的利益，HashToken自主数字资产交易所（HHT）会对资产的合法性和真实性进行评估，只有达到投票上币要求的币种才能参与投票上币。所有上线投票的品种需要满足如下条件，包含但不限于</p>
        <p>&nbsp;</p>
        <p>一．项目无政策风险并且达到专业和合规要求</p>
        <p>二．能真实及时披露项目信息包含项目白皮书，定期发展及进度报告</p>
        <p>三．申请上线HHT交易的币种必须满足平台要求的基本上币资质。该些资质包括但不限于（一）币种团队核心成员须通过反洗钱反恐怖融资等背景调查，（二）币种不得被国家或地区认定为证券，（三）币种团队必须就上币申请提供合格律所出具的合格意见书，（四）交易平台关于上线币种交易的其他实际要求。</p>
        <p>四．申请上币项目如为美国的项目，还需满足平台的以下特殊要求：（一）币种团队须出具保证声明，保证中须包含以下内容：币种团队保证代币不属于美国证券类代币，若因Hadax上线该代币而遭受美国任何监管机构的调查或惩罚，团队成员同意赔偿一切损失并共同承担辩护责任。具体内容要求项目申请方提前咨询对接的相关工作人员来确定。（二）上币申请材料中要求“合格律所”及其出具的“合格意见书”均有特指含义，需项目申请方提前联系相关工作人员确定，以防因意见书不合格通不过平台审核。</p>
        <p>&nbsp;</p>
        <p>数字资产申请投票上币，请通过如下链接在线填写相关信息：</p>
        <p>
          <a href="https://goo.gl/forms/MEpM5gkQFKmElQ4x2"> https://goo.gl/forms/MEpM5gkQFKmElQ4x2</a>
        </p>
        <p>&nbsp;</p>
        <p>下线投票币种说明</p>
        <p>为保护投资者利益，HashToken自主数字资产交易所（HHT）保留投票项目下线或继续支持投票项目在平台上投票的权利，项目方如果触发如下条件，我们会公告通知投票项目下线，包含但不限于：</p>
        <p>&nbsp;</p>
        <ol>
          <li>项目团队解散；</li>
          <li>项目方面临重大法律合规问题；</li>
          <li>由于战略调整和发展需要，项目运营团队主动要求下线；</li>
          <li>严重的技术或安全问题没有及时得到解决；</li>
          <li>由于任何因素变化，致使该项目不再满足平台要求的上币基本资质要求；</li>
          <li>不满足继续投票的其他事项；</li>
        </ol>
        <p>&nbsp;</p>
        <p>HashToken自主数字资产交易所（HHT）保留其他未尽事宜的处理权益。</p>
      </div>

      <div v-show="tab==2" class="article-body">
        <h1 class="text-light">投票规则说明</h1>
        <p>&nbsp;</p>
        <p>为满足用户接触更多优质项目的需求，HashToken推出基于HHT平台的投票上币产品。</p>
        <p>通过投票上币页面，用户支付HT对项目进行投票。每轮投票周期结束后，排名靠前的项目将有机会在HHT平台上线交易，但HHT平台仍保留项目上线与下线的最终决定权。</p>
        <p>投票规则：</p>
        <ol>
          <li>仅HashToken全球专业站的实名用户可以参与投票；</li>
          <li>用户须使用HT投票；</li>
          <li>每个用户对每个项目最少可投1票，最多可投100万票，每投1票需支付0.1HT，投票次数和投票币种不限；</li>
          <li>投票支付的HT不予退还。</li>
        </ol>
        <p>投票上币规则：</p>
        <ol>
          <li>最终入选的上线名单，投票人数须超过1000人；</li>
          <li>投票结束后，票数排名前10名的币种，将有机会于5个工作日内根据票数排名依次首批上线HHT交易，开放BTC和ETH交易对；</li>
          <li>如果票数一样，则优先上线投票人数多的币种；</li>
          <li>每期投票结束后，没有进入排名前十的项目，会自动进入下一轮投票，历史投票票数及人数可以累积；</li>
          <li>为确保给用户及项目方一如既往提供稳定、有序以及优质的服务，前期HHT会保持每期上线10个项目，每个工作日上线两个项目的节奏；</li>
          <li>最终胜出的项目，在其投票的人群中，不少于300名新注册的用户。新注册用户指：新加坡标准时间 2018-02-12 12:00之后注册的用户；</li>
          <li>HashToken可能会根据实际情况调整后期投票上币规则。</li>
        </ol>
        <p>&nbsp;HHT交易用户门槛：</p>
        <ol>
          <li>HashToken Pro和HHT合计资产折合超过1BTC；</li>
          <li>第一次交易时间距今超过30天；</li>
          <li>上述两个条件满足1个，则可开通HHT的项目的交易。否则，可以充提，但不能交易。</li>
          <li>一旦开通HHT的交易，则该账户将永久能够交易HHT，不会再次判断资产。</li>
          <li>HHT上的项目交易不对经实名注册认证为美国人的用户开放。</li>
        </ol>
        <p>&nbsp;本活动解释权归HashToken全球专业站所有，如有虚假用户刷票等违规行为，HashToken有权采取相应措施。</p>
        <p>&nbsp;</p>
        <p>&nbsp;</p>
      </div>

    </div>
  </main>
</template>

<script>
export default
  {
    data () {
      return {
        tab: 1
      }
    },
    created () {
      this.tab = this.$route.query.tab || 1
    }
  }
</script>

<style lang="stylus" scoped>
  @import "../../../static/css/bootstrap-vue.css";
  .a-lbl {
  text-decoraction: none;
  margin-bottom:10px;
    color: white;
    cursor: pointer;
}
.active-a{
  color: #0b93b9;
}
.router-link {
  text-decoration: none;
}

main {
  width: 100%;
  height: 100%;
  background-color: #262a42;

  .article-content {
    width: 1160px;
    margin: 0 auto;
    padding: 60px;

    .side-bar {
      width: 200px;
      height: 100%;
      
      // display: block;
      // float: left;
      button {
        width: 150px;
      }
    }

    .article-body {
      width: 765px;

      p, ol, li {
        color: #fff;
        padding: 5px;
      }
    }
  }
}
</style>


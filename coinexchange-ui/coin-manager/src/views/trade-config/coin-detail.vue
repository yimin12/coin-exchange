<template>
  <div class="conin-config-add">
    <el-tabs v-model="activePaneName" type="border-card">
      <el-tab-pane label="币种信息" name="coin">
        <el-form :model="coinForm"
                 :rules="coinFormRules"
                 ref="coinForm"
                 label-width="150px"
                 class="search-container">

          <el-form-item label="标题" prop="title">
            <el-input v-model="coinForm.title" :disabled="!isAdd"></el-input>
          </el-form-item>

          <el-form-item label="币种名称" prop="name">
            <el-input v-model="coinForm.name" :disabled="!isAdd"></el-input>
          </el-form-item>

          <el-form-item label="图片" prop="img">
            <el-upload
              class="avatar-uploader"
              :action="uploadHost"
              :show-file-list="false"
              :on-success="handleUploadSuccess"
              :data="uploadData"
              :before-upload="beforeUpload"
            >
              <img v-if="coinForm.img"
                   :src="coinForm.img"
                   class="avatar">
              <i v-else class="el-icon-plus avatar-uploader-icon"></i>
            </el-upload>
          </el-form-item>
          <el-form-item label="钱包类型" prop="wallet">
            <el-select v-model="coinForm.wallet" class="form-input" @change="handleWalletChange">
              <el-option v-for="(value,key) in select.wallet_type"
                         :key="key"
                         :label="value"
                         :value="key"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="币种类型" prop="type"  v-show="!hiddenTab">
            <el-select v-model="coinForm.type" class="form-input">
              <el-option v-for="(item,index) in coinTypeOptions"
                         :key="index"
                         :label="item.code"
                         :value="item.code"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="最小提现单位" prop="baseAmount">
            <el-input-number v-model="coinForm.baseAmount" :min="0">
            </el-input-number>
          </el-form-item>

          <el-form-item label="单笔最小提现额度" prop="minAmount">
            <el-input-number v-model="coinForm.minAmount" :min="0">
            </el-input-number>
          </el-form-item>

          <el-form-item label="单笔最大提现额度" prop="maxAmount">
            <el-input-number v-model="coinForm.maxAmount" :min="0">
            </el-input-number>
          </el-form-item>

          <el-form-item label="单日最大提现额度" prop="dayMaxAmount">
            <el-input-number v-model="coinForm.dayMaxAmount" :min="0">
            </el-input-number>
          </el-form-item>

          <el-form-item label="最低提现手续费" prop="minFeeNum">
            <el-input-number v-model="coinForm.minFeeNum" :min="0">
            </el-input-number>
          </el-form-item>
          <el-form-item label="充值状态" prop="rechargeFlag" v-show="!hiddenTab">
            <el-select v-model="coinForm.rechargeFlag" class="form-input">
              <el-option v-for="(value,key) in select.rechargeFlag"
                         :key="key"
                         :label="value"
                         :value="key"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="提币状态" prop="withdrawFlag" v-show="!hiddenTab">
            <el-select v-model="coinForm.withdrawFlag" class="form-input">
              <el-option v-for="(value,key) in select.withdrawFlag"
                         :key="key"
                         :label="value"
                         :value="key"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="提现手续费费率" prop="rate" v-show="!hiddenTab">
            <el-input-number v-model="coinForm.rate" :min="0">
            </el-input-number>
          </el-form-item>

          <el-form-item label="小数位数" prop="round">
            <el-input-number v-model="coinForm.round" :min="0">
            </el-input-number>
          </el-form-item>



          <el-form-item>
            <el-button type="primary" @click="savecoinForm()">保存</el-button>
            <el-button type="danger" @click="backToList()">返回</el-button>
          </el-form-item>
        </el-form>
      </el-tab-pane>
      <el-tab-pane label="钱包配置" name="coinConfig" v-if="!hiddenTab">

        <el-form :model="coinConfigForm"
                 :rules="coinConfigFormRules"
                 ref="coinConfigForm"
                 label-width="100px"
                 class="search-container">
          <el-form-item label="服务器IP" prop="rpcIp">
            <el-input v-model="coinConfigForm.rpcIp" ></el-input>
          </el-form-item>
          <el-form-item label="端口" prop="rpcPort">
            <el-input v-model="coinConfigForm.rpcPort" type="number"></el-input>
          </el-form-item>
          <el-form-item label="定时任务" prop="task">
            <el-input v-model="coinConfigForm.task" placeholder="定时任务表达式：0/15 * * * * ?"></el-input>
          </el-form-item>
          <el-form-item label="最新区块" prop="lastBlock">
            <el-input type="number" v-model="coinConfigForm.lastBlock" placeholder=""></el-input>
          </el-form-item>

          <el-form-item label="状态" prop="status">
            <el-select v-model="coinConfigForm.status" class="form-input">
              <el-option v-for="(key,value) in select.status"
                         :key="key"
                         :label="key"
                         :value="value"></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="是否自动打币" prop="autoDraw">
            <el-select v-model="coinConfigForm.autoDraw" class="form-input">
              <el-option v-for="(key,value) in select.isAuto"
                         :key="key"
                         :label="key"
                         :value="value"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="最高打币额度" prop="autoDrawLimit">
            <el-input  v-model="coinConfigForm.autoDrawLimit" placeholder="默认或者小于等于0代表不限制打币额度"></el-input>
          </el-form-item>


          <el-form-item label="RPC用户名" prop="rpcUser">
            <el-input v-model="coinConfigForm.rpcUser"></el-input>
          </el-form-item>

          <el-form-item label="RPC密码" prop="rpcPwd">
            <el-input v-model="coinConfigForm.rpcPwd"></el-input>
          </el-form-item>

          <el-form-item label="合约地址" prop="contractAddress">
            <el-input v-model="coinConfigForm.contractAddress"></el-input>
          </el-form-item>

          <el-form-item label="最低确认数" prop="minConfirm">
            <el-input-number v-model="coinConfigForm.minConfirm" :min="0"></el-input-number>
          </el-form-item>
          <el-form-item label="最低保留额度" prop="creditLimit">
            <el-input-number v-model="coinConfigForm.creditLimit" :min="0">
            </el-input-number>
          </el-form-item>
          <el-form-item label="归集额度" prop="creditMaxLimit">
            <el-input type="number" v-model="coinConfigForm.creditMaxLimit" placeholder="ETH系列不需要填写"></el-input>
          </el-form-item>

          <!--<el-form-item label="主账户" prop="mainAccount">-->
            <!--<el-input v-model="coinConfigForm.mainAccount"></el-input>-->
          <!--</el-form-item>-->

          <!--<el-form-item label="主账户地址" prop="mainAddr">-->
            <!--<el-input v-model="coinConfigForm.mainAddr"></el-input>-->
          <!--</el-form-item>-->

          <!--<el-form-item label="钱包用户名" prop="walletUser">-->
            <!--<el-input v-model="coinConfigForm.walletUser"></el-input>-->
          <!--</el-form-item>-->

          <el-form-item label="钱包密码" prop="walletPass">
            <el-input v-model="coinConfigForm.walletPass" placeholder="只支持BTC系列，可以为空"></el-input>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" @click="savecoinConfigForm()">保存</el-button>
            <el-button type="danger" @click="backToList()">返回</el-button>

          </el-form-item>

        </el-form>
      </el-tab-pane>
      <el-tab-pane label="钱包归集提币地址" name="adminAddress" v-if="!hiddenTab">

        <el-button type="primary" @click="addAdminAddress">新增</el-button>
        <el-button type="danger" @click="backToList()">返回</el-button>

        <el-table
          ref="multipleTable"
          :data="listData"
          tooltip-effect="dark"
          style="width: 100%"
          @selection-change="handleSelectionChange"
          v-loading="listLoading">

          <el-table-column
            prop="address"
            label="钱包地址"
            width="350px">
          </el-table-column>

          <!--<el-table-column-->
            <!--prop="keystore"-->
            <!--label="密钥"-->
            <!--width="950px">-->
          <!--</el-table-column>-->

          <el-table-column
            prop="status"
            label="地址类型"
            width="100">
            <template slot-scope="scope">
              {{select.adminAddressStatus[scope.row.status]}}
            </template>
          </el-table-column>

          <el-table-column
            label="操作"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="primary"
                @click="editAdminAddress(scope.$index, scope.row)" icon="el-icon-edit">编辑
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-pagination
          class="pagination-container"
          background
          layout="total,prev, pager, next"
          :current-page.sync="listQuery.current"
          :page-size="listQuery.size"
          :total="listQuery.total"
          @current-change="handlePageChange"
        >
        </el-pagination>
      </el-tab-pane>
    </el-tabs>

    <admin-address-dialog ref="adminAddressDialog" @refreshList="_getList"></admin-address-dialog>

  </div>
</template>

<script>
  import {selectOption} from './common'
  import {coinConfigApi} from '@/api/coinConfigApi'
  import adminAddressDialog from './component/admin-address-dialog'
  import commonMixin from '@/components/mixin/common-mixin'
  import {mapGetters} from 'vuex'

  export default {
    components: {adminAddressDialog},
    mixins: [commonMixin],
    computed: {
      ...mapGetters(['getCoinTypeAll'])
    },
    data() {
      let ipReg =/^(1\d{2}|2[0-4]\d|25[0-5]|[1-9]\d|[1-9])(\.(1\d{2}|2[0-4]\d|25[0-5]|[1-9]\d|\d)){3}$/
      var checkIp = (rule, value, callback) => {
        if (!ipReg.test(value)) {
          callback(new Error('IP格式不正确！'));
        } else {
          callback();
        }
      };

      return {
        // 认购币隐藏tab
        hiddenTab: false,
        isAdd: true,
        operType: '',
        coinId: '',
        coinStatus: '',
        select: selectOption,
        activePaneName: 'coin',
        coinForm: {
          title: '',
          name: '',
          img: '',
          type: '',
          wallet: '',
          baseAmount: 1,
          minAmount: 1,
          maxAmount: 100000,
          dayMaxAmount: 200000,
          minFeeNum: 1,
          rate: 1,
          round: 2,
          rechargeFlag: '1',
          withdrawFlag: '1'
        },
        coinFormRules: {
          title: [
            {required: true, message: '请输入标题'},
          ],
          name: [
            {required: true, message: '请输入名称'},
          ],
          img: [
            {required: true, message: '请上传图片'},
          ],
          type: [
            {required: true, message: '请选择类型'},
          ],
          wallet: [
            {required: true, message: '请选择钱包类型'},
          ],
          baseAmount: [
            {required: true, message: '请输入最小提现单位'},
          ],
          minAmount: [
            {required: true, message: '请输入最小提现额度'},
          ],
          maxAmount: [
            {required: true, message: '请输入单笔最大提现额度'},
          ],
          dayMaxAmount: [
            {required: true, message: '请输入单日最大提现额度'},
          ],
          minFeeNum: [
            {required: true, message: '请输入最低提现手续费'},
          ],
          rate: [],
          round: [],
          rechargeFlag: [
            {required: true, message: '请选择充值状态'},
          ],
          withdrawFlag: [
            {required: true, message: '请选择提币状态'},
          ],
        },
        coinConfigForm: {
          creditLimit: 1,
          rpcIp: '',
          rpcPort: '',
          rpcUser: '',
          rpcPwd: '',
          contractAddress: '',
          lastBlock: '',
          minConfirm: '',
          task: '',
          // mainAccount: '',
          // mainAddr: '',
          // walletUser: '',
          walletPass: '',
          status: '',
          // 是否自动打币
          autoDraw :"",
          // 最高打币额度
          autoDrawLimit:''
        },
        coinConfigFormRules: {
          creditLimit: [],
          rpcIp: [
            {required: true, message: '请输入服务器IP'},
            {validator: checkIp},
          ],
          rpcPort: [
            {required: true, message: '请输入端口'},
          ],
          rpcUser: [],
          rpcPwd: [],
          contractAddress: [],
          lastBlock: [
            {required: true, message: '请输入最新区块'},
          ],
          minConfirm: [],
          task: [
            {required: true, message: '请输入定时任务'},
          ],
          // mainAccount: [],
          // mainAddr: [],
          // walletUser: [],
          walletPass: [],
          status: [
            {required: true, message: '请选择状态'},
          ],
        },
        adminAddressForm: {},
        adminAddressFormRules: {},
        coinTypeOptions: null,
        needUpload:true
      }
    },
    async mounted() {
      this.coinTypeOptions = this.getCoinTypeAll
      const operType = this.$route.params.type;
      const coinId = this.$route.params.id;
      this.hiddenTab = this.$route.query.hiddenTab;
      this.coinId = coinId;
      this.operType = operType;
      if (operType != 'edit' || !coinId) {
        this.isAdd = true;
        return;
      }
      this.isAdd = false;

      // console.log("mounted:",typeof this.hiddenTab,this.hiddenTab)
      //解决浏览器刷新后 hiddenTab 类型由boolean转string的问题
      if (this.hiddenTab === "false" || this.hiddenTab === false) {
        this.hiddenTab = false
      } else {
        this.hiddenTab = true
      }

      let coinForm, coinConfigForm = this.coinConfigForm;

      coinForm = await coinConfigApi.getCoin(coinId);
      console.log(coinForm)
      // rgb不调用配置信息
      if (!this.hiddenTab) {
        coinConfigForm = await coinConfigApi.getCoinConfig(coinId)
      }

      this.hiddenTab = coinForm.wallet === 'rgb';

      this._getList()
      this.coinForm = {
        ...coinForm,
        rechargeFlag: coinForm.rechargeFlag + '',
        withdrawFlag: coinForm.withdrawFlag + '',
      };
      this.coinConfigForm = coinConfigForm;
      this.coinConfigForm.status = this.coinConfigForm.status+""
      this.coinConfigForm.autoDraw = this.coinConfigForm.autoDraw?this.coinConfigForm.autoDraw+"":"0"
    },
    methods: {
      // 钱包类型change事件
      handleWalletChange(val) {
        if (val === 'rgb') {
          this.hiddenTab = true
          this.coinForm.type = "rgb"

        } else {
          this.hiddenTab = false
          this.coinForm.type = ""

        }
      },
      // 上传成功
      handleAvatarSuccess(response) {
        const {data} = response;
        this.coinForm.img = data;
      },
      // 验证表单
      validateForm(formName) {
        return new Promise((resolve, reject) => {
          this.$refs[formName].validate((valid) => {
            resolve(valid)
          });
        })
      },
      // 保存第一张表单
      async savecoinForm() {
        const formName = 'coinForm';
        const validateRes = await this.validateForm(formName);
        if (!validateRes) {
          return;
        }
        if (!this.coinForm.img) {
          this.$message.error('请上传图片');
          return;
        }
        const {minAmount, maxAmount, dayMaxAmount} = this.coinForm;
        if (minAmount >= maxAmount) {
          this.$message.error('单笔最小提现额度必须小于单笔最大提现额度');
          return;
        }
        if (maxAmount >= dayMaxAmount) {
          this.$message.error('单笔最大提现额度必须小于单日最大提现额度');
          return;
        }

        let response = {};
        if (this.isAdd) {
          response = await coinConfigApi.addCoin(this.coinForm);
        }
        else {
          response = await coinConfigApi.editCoin(this.coinForm);
        }

        const {id, status} = response;
        this.coinId = id;
        this.coinStatus = status;

        this.$notify({
          type: 'success',
          title: '提示',
          message: '保存成功！',
        });
        if (this.hiddenTab) {
          this.backToList()
        }
        this.activePaneName = 'coinConfig';

      },
      // 保存第二张表单
      async savecoinConfigForm() {
        const formName = 'coinConfigForm';
        const validateRes = await this.validateForm(formName);
        if (!validateRes) {
          return;
        }

        if (this.coinId === '0' || this.coinId === 0) {
          this.$message.error('请先新增币种信息');
          this.activePaneName = 'coin';
          return;
        }

        const postData = {
          id: this.coinId,
          status: this.coinStatus,
          ...this.coinConfigForm,
        }

        /*if (this.isAdd) {
          await coinConfigApi.addCoinConfig(postData);
        }
        else {
          await coinConfigApi.editCoinConfig(postData);
        }*/

        await coinConfigApi.editCoinConfig(postData);

        this.activePaneName = 'adminAddress';
        this.$notify({
          type: 'success',
          title: '提示',
          message: '保存成功！',
        });
      },
      // 第三张表单，查询表格
      listCallback() {
        return coinConfigApi.getAdminAddressByCoinId(this.coinId,
          this.listQuery.current, this.listQuery.size)
      },
      // 新增钱包归集地址
      addAdminAddress() {
        const row = {coinId: this.coinId};
        this.$refs.adminAddressDialog.showDialog(1, row);
      },
      // 修改钱包归集地址
      editAdminAddress(index, row) {
        this.$refs.adminAddressDialog.showDialog(2, {
          ...row,
          status: row.status + '',
        });
      },
      backToList() {
        this.$router.push("/trade-config/coin-config")
      }
    }
  }
</script>

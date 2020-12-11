import {datePickerOptions} from '@/utils/elementUtil'
import {formUtils} from '@/utils/formUtil'
import {commonApi} from '@/api/commonApi'
import * as cvs from '@/utils/cvs'
import {uploadApi} from "../../api/uploadApi";


/**
 * 全局CRUD组件抽取
 */
export default {
  mounted() {
    this._getList()
  },
  filters: {
    elTagFilter(status) {
      const statusMap = {
        0: 'danger',
        1: 'success',
        2: 'info',
        3: 'primary',
        4: 'warning',
      }
      return statusMap[status]
    },
    elTagFilter2(status) {
      const statusMap = {
        1: 'danger',
        0: 'success',
      }
      return statusMap[status]
    },
    statusFilter(status) {
      const statusMap = {
        0: '禁用',
        1: '启用',
        2: '错误状态',
      }
      return statusMap[status]
    },
    resourceTypeFilter(type) {
      const statusMap = {
        'WEB_BANNER': '大banner',
        'LINK_BANNER': '小banner'
      }
      return statusMap[type]
    },

    // 实名认证审核状态 userAuthStatus  0 待审核 1 通过 2 拒绝
    userAuthStatusFilter(reviewStatus) {
      const statusMap = {
        0: '待审核',
        1: '审核通过',
        2: '审核拒绝'
      }
      return statusMap[reviewStatus]
    },
    // 实名认证状态 authStatus: 0 未认证 1初级 2高级
    authStatusFilter(authStatus) {
      const statusMap = {
        0: '未认证',
        1: '初级',
        2: '高级'
      }
      return statusMap[authStatus]
    },

    // 代理商注册审核
    reviewsStatusFilter(reviewStatus) {
      const statusMap = {
        0: '待审核',
        1: '审核通过',
        2: '审核拒绝'
      }
      return statusMap[reviewStatus]
    },

    // 场外交易充值审核 充值来源类型 alipay，支付宝；cai1pay，财易付；bank，银联；
    rechargeTypeFilter(type) {
      const map = {
        linepay: '在线充值',
        alipay: '支付宝',
        cai1pay: '财易付',
        bank: '银联'
      }
      return map[type]
    },
      // 场外交易充值审核 充值来源类型 alipay，支付宝；cai1pay，财易付；bank，银联；
      businessTypeFilter(type) {
        const map = { 
          recharge_into: '充值',
          withdrawals_out: '提现审核通过',
          order_create: '下单',
          order_turnover: '成交',
          order_turnover_poundage: '成交手续费',
          order_cancel: '撤单',
          register_reward: '注册奖励',
          invite_reward:'邀请奖励',
          withdrawals: '提币冻结解冻',
          recharge: '充人民币' ,
          withdrawals_poundage: '提币手续费',
          cny_btcx_exchange: '兑换' ,
          bonus_into: '奖励充值' ,
          bonus_freeze: '奖励冻结'
        }
        return map[type]
      },
    // 场外交易充值审核 状态 0-待审核；1-审核通过；2-拒绝；3-充值成功；
    rechargeStatusFilter(type) {
      const map = {
        0: '待审核',
        1: '审核通过',
        2: '拒绝',
        3: '充值成功'
      }
      return map[type]
    },
    // cny 提现审核 状态 0-待审核；1-审核通过；2-拒绝；3-提现成功；
    withdrawStatusFilter(type) {
      const map = {
        0: '待审核',
        1: '审核通过',
        2: '拒绝',
        3: '提现成功'
      }
      return map[type]
    },

    // 虚拟币提现管理列表0-审核中；1-审核通过；2-拒绝；3-提币成功；4：撤销；5-打币中；
    coninWithdrawTypeFilter(type) {
      const map = {
        0: '审核中',
        1: '转出成功',
        2: '审核拒绝',
        3: '撤销',
        4: '审核通过',
        5: '打币中',
        6: '钱包异常，审核中'
      }
      return map[type]
    },
    // 资金流水列表收付类型1 入账 2 出账
    accountFlowDirectionFilter(type) {
      const map = {
        1: '入账',
        2: '出账',
      }
      return map[type]
    },
    // 币币交易列表状态 0未成交 1已成交 2已取消 4异常单
    entrustManagerListStatusFilter(type) {
      const map = {
        0: '未成交',
        1: '已成交',
        2: '已取消',
        4: '异常单',
      }
      return map[type]
    },
    // 币币交易列表交易方式 1-买入；2-卖出
    entrustManagerListTradtypeFilter(type) {
      const map = {
        1: '买入',
        2: '卖出',
      }
      return map[type]
    },
    // 创新交易列表价格类型(1 市价 2 限价)
    invovateListFilter(type) {
      const map = {
        1: '市价',
        2: '限价',
      }
      return map[type]
    },
    // 创新交易列表订单类型1 开仓 2 平仓
    invovateOrderTypeFilter(type) {
      const map = {
        1: '开仓',
        2: '平仓',
      }
      return map[type]
    },
    // 虚拟币充值记录状态 0-待入账；1-充值失败；2-到账失败；3-到账成功；
    coinRechargeTypeListFilter(type) {
      const map = {
        0: '成功',
        1: '失败',
      }
      return map[type]
    },
    // 客服回复状态（1 未处理 2已处理）
    serviceStatusFilter(status) {
      const map = {
        1: '未处理',
        2: '已处理',
      }
      return map[status]
    },
    // 资金账户列表状态
    accountAssetsListStatus(type) {
      const map = {
        1: '正常',
        2: '冻结'
      };
      return map[type]
    },
    // 交易区域配置类型 1 币币交易 2 创新交易
    tradeAreaListType(type) {
      const map = {
        1: '币币交易',
        2: '创新交易'
      }
      return map[type]
    },
    // 创建矿池状态
    poolStatusFilter(authStatus) {
      const statusMap = {
        0: '待审核',
        1: '通过',
        2: '拒绝'
      }
      return statusMap[authStatus]
    },
    // 手动打款
    withdrawType(type) {
      const map = {
        0: '站内',
        1: '站外自动',
        2: '站外手动'
      }
      return map[type]
    }

  },
  data() {
    return {
      listLoading: true,
      datePickerOptions: datePickerOptions,
      listQuery: {
        size: 15,
        total: 0,
        current: 1
      },
      listData: [],
      deleteItems: [],
      //oss预上传数据
      uploadHost:"",
      // 上传携带参数
      uploadData:{},
    }
  },
  methods: {
    async _getList() {
      this.listLoading = true
      let res = await this.listCallback()
      this.listLoading = false
      if (typeof this.needListProcess === "function") {
        this.listData = this.needListProcess(res.records)
      } else {
        this.listData = res.records
      }
      this.listQuery.current = res.current
      this.listQuery.size = res.size
      this.listQuery.total = res.total
    },
    submitForm(formName) {
      this.listQuery.current = 1;
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this._getList()

        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    // 导出excel
    exportExcel(formName, url, fileName) {

      this.$refs[formName].validate((valid) => {
        if (valid) {

          let {ruleForm, current, size} = this.$refs[formName].$parent;
          let params = {}
          formUtils.formDateRange(params, ruleForm);
          params.current = current
          params.size = size

          commonApi.exportExcel(url, params).then((res) => {
            let filename = fileName || decodeURIComponent(res.headers['content-filename']);
            cvs.exportCsv(res.data, filename);
          });
        } else {
          console.log('error submit!!');
          return false;
        }
      });
    },
    handleSelectionChange(val) {
      this.deleteItems = val;
    },
    handleDelete() {
      if (this.deleteItems.length === 0) {
        this.$alert('请选择需要删除的列表项!', '提示', {
          confirmButtonText: '确定',
          type: 'warning',
          callback: action => {
          }
        });
      } else {
        this.$alert('确定要删除吗？', '提示', {
          confirmButtonText: '确定',
          type: 'warning',
          callback: action => {
            if (action === "confirm") {
              this._deleteItems()
            }
          }
        });
      }
    },
    async handleChangeStatus(index, row) {
      let {id, status} = row;
      status = (status === 1 ? 0 : 1);
      await  this.changeStatusCallback(id, status)
      this.$notify({
        type: 'success',
        title: '提示',
        message: "更新成功！"
      });
      this._getList();
    },

    handlePageChange(currentPage) {
      this.listQuery.current = currentPage
      this._getList()
    },
    async _deleteItems() {
      let ids = this.deleteItems.map((item) => {
        console.log(item)
        return item.id
      });
      console.log("ids", ids)

      await this.deleteCallback(ids)
      this.$notify({
        type: 'success',
        title: '提示',
        message: '删除成功！',
      });
      this._getList()
    },
    async beforeUpload(){
      let res = await uploadApi.getPreUpload()
      if(res){
        let preUploadData = res
        console.log(preUploadData)
        let {dir,policy,signature,callback,accessid,host} = preUploadData
        this.uploadHost = host
        this.uploadData.name=signature
        this.uploadData.key=`${dir}${new Date().getTime()}.jpg`
        this.uploadData.policy=policy
        this.uploadData.OSSAccessKeyId=accessid
        this.uploadData.success_action_status=200
        this.uploadData.callback=callback
        this.uploadData.signature=signature
      }else{
        return Promise.reject()
      }
    },
    handleUploadSuccess(response) {
      const { status,uri } = response
      const aliyunFileUrl = 'https://coin-exchange-imgs.oss-cn-beijing.aliyuncs.com/'
      if(status === "OK"){
        this.coinForm.img = aliyunFileUrl + uri
        console.log(this.coinForm.img)
      }
    }
  }
}

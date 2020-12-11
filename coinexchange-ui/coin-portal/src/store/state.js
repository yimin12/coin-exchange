const state = {
  mobile  : '',
  username: '',
  isAuth  : 0 ,   // 0 未认证 1 已经认证  2 高级认证
  trueName  : '', // 实名
  setPayPassword  : false,
  inviteCode : '',
  idCard: '',
  idCardType:1,
  realName:'',
  authTime:'',
  token:'',
  countryCode:'',
  seniorAuthStatus:3, //  0 待审核1通过2拒绝3未填写
  lang:'', // 当前语言
  expireTime:'' , // token过期时间
  seniorAuthDesc:'', //高级认证拒绝原因
  gaStatus:0, // google 认证 0 未开启 1 已开启,
  email:""
}
export default state

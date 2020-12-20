package com.yimin.constant;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/12 21:11
 *   @Description :
 *
 */

/**
 * 常用的常量
 */
public class Constants {

    /**
     * UTF-8 字符集
     */
    public static final String UTF8 = "UTF-8";

    /**
     * GBK 字符集
     */
    public static final String GBK = "GBK";

    /**
     * http请求
     */
    public static final String HTTP = "http://";

    /**
     * https请求
     */
    public static final String HTTPS = "https://";

    /**
     * 成功标记
     */
    public static final Integer SUCCESS = 200;

    /**
     * 失败标记
     */
    public static final Integer FAIL = 500;


    /**
     * 验证码 redis key
     */
    public static final String CAPTCHA_CODE_KEY = "captcha_codes:";

    /**
     * 验证码有效期（分钟）
     */
    public static final long CAPTCHA_EXPIRATION = 2;


    /**
     * 数值类型精度
     */
    public static final  int SCALE = 8;

    public static final int AGENT_TYPE = 2;

    public static final String BCRYPT_SALT = "!waihui!_+waihui+-";

    /**
     * 首页Banner图
     */
    public static final String CONFIG_WEB_BANNER = "WEB_BANNER";

    public static final String TOKEN = "token";

    /*********************************** MATCH ********************************/
    public static final String MATCH_RESULT_DEAL_ORDER = "dealOrder";
    public static final String MATCH_RESULT_BUY_ORDER = "buyOrder";
    public static final String MATCH_RESULT_SELL_ORDER = "sellOrder";
    public static final String MATCH_RESULT_NEW_BUY_LOCK = "buyLock";
    public static final String MATCH_RESULT_NEW_SELL_LOCK = "sellLock";
    public static final String MATCH_RESULT_PART_MATCH = "partMatch";
    /*********************************** MATCH ********************************/


    /*********************************** CONFIG TABLE CODE ********************************/
    public static final String CONFIG_TYPE_SYSTEM = "SYSTEM";
    public static final String CONFIG_TYPE_CNY = "CNY";
    public static final String CONFIG_TYPE_SMS = "SMS";
    public static final String CONFIG_TYPE_REGISTER = "REGISTER_REWARD";
    public static final String CONFIG_TYPE_REWARD = "INVITE_REWARD";

    /**
     * 最小提现额度（USDT）
     */
    public static final  String CONFIG_WITHDRAW_MIN_AMOUNT = "WITHDRAW_MIN_AMOUNT";

    /**
     * 最大提现额度（USDT）
     */
    public static final String CONFIG_WITHDRAW_MAX_AMOUNT = "WITHDRAW_MAX_AMOUNT";

    /**
     * 最小取现手续费（USDT）
     */
    public static final String CONFIG_WITHDRAW_MIN_POUNDAGE = "WITHDRAW_MIN_POUNDAGE";

    /**
     * 取现手续费率（USDT）
     */
    public static final String CONFIG_WITHDRAW_POUNDAGE_RATE = "WITHDRAW_POUNDAGE_RATE";

    /**
     * 取现基数（USDT），取现值必须是基数的倍数，基数如果是100，那么取现值只能是100的倍数，例如：200, 300等
     */
    public static final String CONFIG_WITHDRAW_BASEAMOUNT = "CONFIG_WITHDRAW_BASEAMOUNT";

    /**
     * 每日最大提现额（USDT）
     */
    public static final String CONFIG_WITHDRAW_DAY_MAX_AMOUNT = "WITHDRAW_DAY_MAX_AMOUNT";

    /**
     * 提现状态（USDT）
     */
    String CONFIG_WITHDRAW_STATUS = "WITHDRAW_STATUS";

    /**
     * 币币交易状态
     */
    String CONFIG_TRADE_STATUS = "TRADE_STATUS";

    /**
     * 人民币充值USDT换算费率
     */
    String CONFIG_CNY2USDT = "CNY2USDT";

    /**
     * 人民币提现USDT换算费率
     */
    String CONFIG_USDT2CNY = "USDT2CNY";

    /**
     * 提现审核级数
     */
    String CONFIG_CASH_WITHDRAW_AUDIT_STEPS = "CASH_WITHDRAW_AUDIT_STEPS";

    /**
     * 充值审核级数
     */
    String CONFIG_CASH_RECHARGE_AUDIT_STEPS = "CASH_RECHARGE_AUDIT_STEPS";

    /**
     * 提币审核级数
     */
    String CONFIG_COIN_WITHDRAW_AUDIT_STEPS = "COIN_WITHDRAW_AUDIT_STEPS";

    String C2C_ADMIN_USER_ID = "C2C_ADMIN_USER_ID";
    /*********************************** CONFIG TABLE CODE ********************************/


    /************************************* CACHE KEY ************************************/
    /**
     * 交易区域
     */
    String CACHE_KEY_TRADE_AREA = "tradeArea:";

    /**
     * 交易区域
     */
    String CACHE_KEY_TRADE_AREAS = "tradeAreas:";

    /**
     * 币种
     */
    String CACHE_KEY_COIN = "coin:";

    /**
     * 交易对列表
     */
    String CACHE_KEY_MARKETS = "markets:";

    /**
     * 交易对列表
     */
    String CACHE_KEY_AREA_MARKETS = "areaMarkets:";

    /**
     * 交易对
     */
    String CACHE_KEY_MARKET = "market:";

    /**
     * 基础配置
     */
    String CACHE_KEY_CONFIG = "config:";

    /**
     * 系统配置列表
     */
    String CACHE_KEY_CONFIG_LIST = "config_list:";

    /**
     * 管理员
     */
    String CACHE_KEY_ADMIN_USER = "user_admin";
    String CACHE_KEY_C2C_ADMIN_USER = "user_c2c_admin";
    String CACHE_KEY_AGENT_ADMIN_USER = "user_agent_admin";
    /************************************* CACHE KEY ************************************/


    /**
     * 币币市场行情Socket推送
     */
    String CH_MARKETS_TICKER = "market.ticker";
    /**
     * 未成交委托订阅通道
     */
    String CH_ORDER_PENDING = "order.pending.update";
    /**
     * 历史委托订阅通道
     */
    String CH_ORDER_FINISHED = "order.finished.update";
    /**
     * 持仓汇总订阅通道
     */
    String CH_POSITION_SUMMARY_UPDATE = "position.summary.update";
    /**
     * 今日平仓订阅通道
     */
    String CH_CLOSEPOSITION_ORDERS_UPDATE = "closeposition.orders.update";
    /**
     * 今日委托订阅通道
     */
    String CH_ENTRUST_ORDERS_UPDATE = "entrust.orders.update";
    /**
     * 今日成交订阅通道
     */
    String CH_TURNOVER_ORDERS_UPDATE = "turnover.orders.update";


    /************************************* REDIS KEY ************************************/
    /**
     * 验证码redis存储Key
     */
    String REDIS_KEY_CAPTCHA_KEY = "CAPTCHA:";

    /**
     * 短信验证码redis存储Key
     */
    String REDIS_KEY_SMS_CODE_KEY = "SMSCODE:";

    /**
     * 登录设备存储key
     */
    String REDIS_KEY_DEVICES_KEY = "DEVICES";

    /**
     * 最大缓存数据量
     */
    public static long REDIS_MAX_CACHE_KLINE_SIZE = 10000L;

    /**
     * 币币交易K线 redis存储Key
     */
    public static String REDIS_KEY_TRADE_KLINE = "TRADE_KLINE:";


    /**
     * 币币交易撮合队列 redis存储Key
     */
    String REDIS_KEY_TRADE_MATCH = "TRADE_MATCH:";

    /**
     * 币币交易对
     */
    String REDIS_KEY_TRADE_MARKET = "TRADE_MARKET";

    /**
     * 法币充值审核锁
     */
    String REDIS_KEY_CASH_RECHARGE_AUDIT_LOCK = "CASH_RECHARGE_AUDIT_LOCK:";

    /**
     * 法币提现审核锁
     */
    String REDIS_KEY_CASH_WITHDRAW_AUDIT_LOCK = "CASH_WITHDRAW_AUDIT_LOCK:";

    /**
     * 数字货币提现审核锁
     */
    String REDIS_KEY_COIN_WITHDRAW_AUDIT_LOCK = "COIN_WITHDRAW_AUDIT_LOCK:";

    /**
     * 币币交易撤单锁
     */
    String REDIS_KEY_TRADE_ORDER_CANCEL_LOCK = "TRADE_ORDER_CANCEL_LOCK:";

    /**
     * 币币交易撮合锁
     */
    String REDIS_KEY_TRADE_ORDER_MATCH_LOCK = "TRADE_ORDER_MATCH_LOCK:";

    /**
     * 创新交易资金账户锁
     */
    String REDIS_KEY_TRADE_ACCOUNT_LOCK = "TRADE_ACCOUNT_LOCK:";
    /************************************* REDIS KEY ************************************/


    /************OAUTH*********************/
    String AUTHORIZATION_HEADER = "Authorization";

    String BEARER = "Bearer ";
    /************OAUTH*********************/

    /************STREAM*********************/
    String CHANNEL_SENDTO_USER = "user";
    String CHANNEL_SENDTO_GROUP = "group";
    String CHANNEL_TICKER_UPDATE = "ticker";




    /**
     * 钱包币
     */
    String COIN_TYPE_QBB = "qbb";
    /**
     * 认购币
     */
    String COIN_TYPE_RGB = "rgb";


    /**
     * 内置的Token
     */
    public static String INSIDE_TOKEN = "eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJzY29wZSI6WyJhbGwiXSwiZXhwIjozNzU1OTg3NTc1LCJqdGkiOiI1NGQ5NzNhOS1lZWY0LTQ1ZDEtOTViYi0wMDIyNTRiNmNmM2EiLCJjbGllbnRfaWQiOiJpbnNpZGUtYXBwIn0.f16HB5WYLjZOxihS9mh6HYS-WBoKZzkq8H5E7-8YGGU5SzCpb9evMmsPTobKatEb4jl2aB22kwnMgEzD_gpBsZ35ezviDSEpm1V0B7LdDTSsEXzrco6pgq6YfUpyEt_tiLuuJGaV86TWixufUC5Ov2mN3Vr15BWOjAo4i-2MOtSmi4zpnT7A_T17fzhnb4iMnJTzRj7QSsL329-mGuGw49-AuNQVzhMDzu41lkGJ833xVhEP6niWK5J7PfcrTANvuwunix_Bn147dRjaUaXPLM7LHj-psQk0MUuUusqQNCBRGrYdOIQTy8hjPBnEwEPwVP2qLPk_yyjYPP8KWZiT_w" ;

}

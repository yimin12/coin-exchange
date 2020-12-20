package com.yimin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.CashRecharge;
import com.yimin.model.CashParam;
import com.yimin.model.R;
import com.yimin.service.CashRechargeService;
import com.yimin.utils.ReportCsvUtils;
import com.yimin.vo.CashTradeVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/17 16:29
 *   @Description :
 *
 */
@RestController
@RequestMapping("/cashRecharges")
@Api(tags = "GCN 充值控制器")
public class CashRechargeController {

    @Autowired
    private CashRechargeService cashRechargeService;

    @GetMapping("/records")
    @ApiOperation(value = "条件分页查询")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数"),
            @ApiImplicitParam(name = "coinId", value = "币种的Id"),
            @ApiImplicitParam(name = "userId", value = "用户的Id"),
            @ApiImplicitParam(name = "userName", value = "用户的名称"),
            @ApiImplicitParam(name = "mobile", value = "用户的手机号"),
            @ApiImplicitParam(name = "status", value = "充值审核状态"),
            @ApiImplicitParam(name = "numMin", value = "充值最小金额"),
            @ApiImplicitParam(name = "numMax", value = "充值最大金额"),
            @ApiImplicitParam(name = "startTime", value = "充值开始时间"),
            @ApiImplicitParam(name = "endTime", value = "充值结束时间"),
    })
    public R<Page<CashRecharge>> findByPage(@ApiIgnore Page<CashRecharge> page, Long coinId, Long userId, String userName, String mobile, Byte status, String numMin, String numMax, String startTime, String endTime)
    {
        Page<CashRecharge> pageData = cashRechargeService.findByPage(page, coinId, userId, userName, mobile, status, numMin, numMax, startTime,
                endTime);
        return R.ok(pageData);
    }

    @GetMapping("/user/records")
    @ApiOperation(value = "查询当前用户的充值记录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current",value = "当前页") ,
            @ApiImplicitParam(name = "size",value = "每页显示的大小") ,
            @ApiImplicitParam(name = "status",value = "充值的状态") ,
    })
    public R<Page<CashRecharge>> findUserCashRecharge(@ApiIgnore Page<CashRecharge> page, Byte status){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        Page<CashRecharge> cashRechargePage = cashRechargeService.findUserCashRecharge(page, userId, status);
        return R.ok(cashRechargePage);
    }

    @GetMapping("/records/exports")
    @ApiOperation(value = "输出csv文件")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coinId", value = "币种的Id"),
            @ApiImplicitParam(name = "userId", value = "用户的Id"),
            @ApiImplicitParam(name = "userName", value = "用户的名称"),
            @ApiImplicitParam(name = "mobile", value = "用户的手机号"),
            @ApiImplicitParam(name = "status", value = "充值审核状态"),
            @ApiImplicitParam(name = "numMin", value = "充值最小金额"),
            @ApiImplicitParam(name = "numMax", value = "充值最大金额"),
            @ApiImplicitParam(name = "startTime", value = "充值开始时间"),
            @ApiImplicitParam(name = "endTime", value = "充值结束时间"),
    })
    public void recordsExport(@ApiIgnore Long coinId, Long userId, String userName, String mobile, Byte status, String numMin, String numMax, String startTime, String endTime){
        Page<CashRecharge> page = new Page<>(1, 10000);
        Page<CashRecharge> pageData = cashRechargeService.findByPage(page, coinId, userId, userName, mobile, status, numMin, numMax, startTime,
                endTime);
        List<CashRecharge> records = pageData.getRecords();
        if(!CollectionUtils.isEmpty(records)){
            String[] header = {"ID", "用户ID", "用户名", "真实用户名", "充值币种", "充值金额(USDT)", "手续费", "到账金额(CNY)", "充值方式", "充值订单", "参考号", "充值时间", "完成时间","状态", "审核被指", "审核级数"};
            String[] properties = {"id", "userId", "username", "realName", "coinName", "num", "fee", "mum", "type", "tradeno", "remark", "created", "lastTime", "status", "auditRemark", "step"};
            ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            try {
                ReportCsvUtils.reportList(requestAttributes.getResponse(), header, properties, "场外交易提现审核",records);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    @PostMapping("/buy")
    @ApiOperation(value = "GCN的充值操作")
    @ApiImplicitParams({
            @ApiImplicitParam( name = "cashParam",value = "现金交易的参数")
    })
    public R<CashTradeVo> buy(@RequestBody @Validated CashParam cashParam){
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString()) ;
        CashTradeVo cashTradeVo = cashRechargeService.buy(userId,cashParam) ;
        return R.ok(cashTradeVo) ;
    }
}

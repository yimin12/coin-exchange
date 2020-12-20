package com.yimin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.AdminBank;
import com.yimin.dto.AdminBankDto;
import com.yimin.feign.AdminBankServiceFeign;
import com.yimin.model.R;
import com.yimin.service.AdminBankService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/14 23:48
 *   @Description :
 *
 */
@RestController
@RequestMapping("/adminBanks")
@Api(tags = "公司银行卡的配置")
public class AdminBankController implements AdminBankServiceFeign {

    @Autowired
    private AdminBankService adminBankService;


    @GetMapping
    @ApiOperation(value = "条件查询公司的银行卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bankCard" ,value = "公司的银行卡") ,
            @ApiImplicitParam(name = "current" ,value = "当前页") ,
            @ApiImplicitParam(name = "size" ,value = "每页显示的条数")
    })
    @PreAuthorize("hasAuthority('admin_bank_query')")
    public R<Page<AdminBank>> findByPage(@ApiIgnore Page<AdminBank> page, String bankCard){
        Page<AdminBank> adminBankPage = adminBankService.findByPage(page, bankCard);
        return R.ok(adminBankPage);
    }

    @PostMapping
    @ApiOperation(value = "新增一个银行卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminBank" ,value = "adminBank json 数据")
    })
    @PreAuthorize("hasAuthority('admin_bank_create')")
    public R add(@RequestBody @Validated AdminBank adminBank){
        boolean save = adminBankService.save(adminBank);
        if(save){
            return R.ok();
        }
        return R.fail("新增失败");
    }

    @PatchMapping
    @ApiOperation(value = "修改一个银行卡")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "adminBank" ,value = "adminBank json 数据")
    })
    @PreAuthorize("hasAuthority('admin_bank_update')")
    public R update(@RequestBody @Validated AdminBank adminBank){
        boolean updateById = adminBankService.updateById(adminBank);
        if(updateById){
            return R.ok();
        }
        return R.fail("修改失败");
    }

    @PostMapping("/adminUpdateBankStatus")
    @ApiOperation(value = "修改银行卡的状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bankId" ,value = "要修改的银行卡的ID"),
            @ApiImplicitParam(name = "status" ,value = "要修改为的状态")
    })
    @PreAuthorize("hasAuthority('admin_bank_update')")
    public R changeStatus(Long bankId, Byte status){
        AdminBank adminBank = new AdminBank();
        adminBank.setId(bankId);
        adminBank.setStatus(status);
        boolean updateById = adminBankService.updateById(adminBank);
        if(updateById){
            return R.ok();
        }
        return R.fail("状态修改失败");
    }

    @Override
    public List<AdminBankDto> getAllAdminBanks() {
        List<AdminBankDto> adminBankDtos = adminBankService.getAllAdminBanks();
        return adminBankDtos;
    }
}

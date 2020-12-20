package com.yimin.feign;

import com.yimin.dto.AdminBankDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/18 14:04
 *   @Description :
 *
 */
@FeignClient(name = "admin-service",path = "/adminBanks",configuration = OAuth2FeignConfig.class)
public interface AdminBankServiceFeign {

    @GetMapping("/list")
    List<AdminBankDto> getAllAdminBanks();
}

package com.yimin.feign;

import com.yimin.dto.UserBankDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/18 16:12
 *   @Description :
 *      如果FeignClient里面出现多个相同的name，spring创建对象就会报错，容器管理会认为这两个对象是一样的
 */
@FeignClient(name = "member-service", contextId = "userBankServiceFeign",configuration = OAuth2FeignConfig.class ,path = "/userBanks")
public interface UserBankServiceFeign {

    @GetMapping("/{userId}/info}")
    UserBankDto getUserBankInfo(@PathVariable Long userId);
}

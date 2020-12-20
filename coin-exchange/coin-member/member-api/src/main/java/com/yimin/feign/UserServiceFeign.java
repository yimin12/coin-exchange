package com.yimin.feign;

import com.yimin.dto.UserDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/17 12:03
 *   @Description :
 *
 */
@FeignClient(name = "member-service", configuration = OAuth2FeignConfig.class, path = "/users")
public interface UserServiceFeign {

    /**
     * 用于admin-service 里面远程调用member-service
     * Map<Long, UserDto> Long:UserId, UserDto用户的基础性息
     * @param ids
     * @return
     */
    @GetMapping("/basic/users")
    Map<Long,UserDto> getBasicUsers(@RequestParam(value = "ids",required = false) List<Long> ids,
                                    @RequestParam(value = "username",required = false)String username,
                                    @RequestParam(value = "mobile",required = false)String mobile) ;
//    List<UserDto> getBasicUsers(@RequestParam("ids") List<Long> ids) ;
}

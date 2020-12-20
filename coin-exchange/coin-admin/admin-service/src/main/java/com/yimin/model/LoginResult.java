package com.yimin.model;

import com.yimin.domain.SysMenu;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/13 18:56
 *   @Description :
 *      显示登录成功的结果
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@ApiModel(value = "登陆的结果")
public class LoginResult {

    /**
     * 登录成功的token时来自我们authorization-server里面的
     */
    @ApiModelProperty(value = "登录成功的token,来自我们的authorization-server里面的")
    private String token;

    /**
     * 该用户的菜单数据
     */
    @ApiModelProperty(value = "该用户的菜单数据")
    private List<SysMenu> menus;

    /**
     * 该用户的权限数据
     */
    @ApiModelProperty(value = "该用户的权限数据")
    private List<SimpleGrantedAuthority> authorities;

}

package com.yimin.service.impl;

import com.yimin.constant.LoginConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.IncorrectResultSizeDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sun.rmi.runtime.Log;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/12 14:46
 *   @Description :
 *
 */
@Service
public class UserServiceDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // 支持后台人员内部登录
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        String login_type = requestAttributes.getRequest().getParameter("login_type"); // 区分时后台人员还是我们的用户登录
        if (StringUtils.isEmpty(login_type)) {
            throw new AuthenticationServiceException("登录类型不能为null");
        }
        UserDetails userDetails = null;
        try{
            String grant_type = requestAttributes.getRequest().getParameter("grant_type");
            if(LoginConstant.REFRESH_TYPE.equals(grant_type.toUpperCase())){
                username = adjustUsername(username, login_type);
            }
            switch (login_type){
                case LoginConstant.ADMIN_TYPE:
                    userDetails = loadSysUserByUsername(username);
                    break;
                case LoginConstant.MEMBER_TYPE:
                    userDetails = loadMemberUserByUsername(username);
                    break;
                default:
                    throw new AuthenticationServiceException("暂不支持的登录方式" + login_type);
            }
        } catch (IncorrectResultSizeDataAccessException e){
            // 我们的用户存在
            throw new UsernameNotFoundException("用户名" + username + "不存在");
        }
        return userDetails;
    }

    /**
     * 纠正用户的名称 (转变名称为id)
     * 再grant_fresh的状态下搜索出来的是名字，而我们统一将username转换成id进行处理
     * @param username  用户的id
     * @param loginType admin_type  member_type
     * @return
     */
    private String adjustUsername(String username, String loginType){
        if(LoginConstant.ADMIN_TYPE.equals(loginType)){
            return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_USER_WITH_ID, String.class, username);
        }
        if(LoginConstant.MEMBER_TYPE.equals(loginType)){
            return jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_USER_WITH_ID, String.class, username);
        }
        return username;
    }

    /**
     * 后台管理人员登录
     * @param username
     * @return
     */
    private UserDetails loadSysUserByUsername(String username) {
        // 1 使用用户名查询
        return jdbcTemplate.queryForObject(LoginConstant.QUERY_ADMIN_SQL, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                if (rs.wasNull()) {
                    throw new UsernameNotFoundException("用户名" + username + "不存在");
                }
                long id = rs.getLong("id"); // 用户的id
                String password = rs.getString("password"); // 用户的密码
                int status = rs.getInt("status");
                return new User(   // 3 封装成一个UserDetails对象，返回
                        String.valueOf(id), //使用id->username
                        password,
                        status == 1,
                        true,
                        true,
                        true,
                        getSysUserPermissions(id)
                );
            }
        }, username);
    }

    /**
     * 2 查询这个用户的对应权限,通过用户的id 查询用户的权限
     * @param id
     * @return
     */
    private Collection<? extends GrantedAuthority> getSysUserPermissions(long id) {
        // 1 当用户为超级管理员，他拥有的所有权限数据
        String roleCode = jdbcTemplate.queryForObject(LoginConstant.QUERY_ROLE_CODE_SQL, String.class, id);
        List<String> permissions = null; // 权限的内容
        if(LoginConstant.ADMIN_ROLE_CODE.equals(roleCode)){
            // Super user
            permissions = jdbcTemplate.queryForList(LoginConstant.QUERY_ALL_PERMISSIONS, String.class);
        } else {
            // user
            permissions = jdbcTemplate.queryForList(LoginConstant.QUERY_PERMISSION_SQL, String.class, id);
        }
        if(permissions == null || permissions.isEmpty()){
            return Collections.emptySet();
        }
        return permissions.stream()
                .distinct() // 去重
                .map(perm -> new SimpleGrantedAuthority(perm)).collect(Collectors.toSet());
        // 2 普通用户，需要使用角色 -> 权限数据
    }
    /**
     * 会员的登录
     * @param username
     * @return
     */
    private UserDetails loadMemberUserByUsername(String username) {
        return jdbcTemplate.queryForObject(LoginConstant.QUERY_MEMBER_SQL, new RowMapper<User>() {
            @Override
            public User mapRow(ResultSet rs, int rowNum) throws SQLException {
                if(rs.wasNull()){
                    throw new UsernameNotFoundException("用户名" + username + "不存在");
                }
                long id = rs.getLong("id");
                String password = rs.getString("password");
                int status = rs.getInt("status");
                return new User(
                        String.valueOf(id),
                        password,
                        status == 1,
                        true,
                        true,
                        true,
                        Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"))
                );
            }
        }, username, username);
    }
}

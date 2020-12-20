package com.yimin.controller;

import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.User;
import com.yimin.domain.UserAuthAuditRecord;
import com.yimin.domain.UserAuthInfo;
import com.yimin.dto.UserDto;
import com.yimin.feign.UserServiceFeign;
import com.yimin.model.*;
import com.yimin.service.UserAuthAuditRecordService;
import com.yimin.service.UserAuthInfoService;
import com.yimin.service.UserService;
import com.yimin.vo.UseAuthInfoVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/15 13:20
 *   @Description :
 *
 */
@RestController
@RequestMapping("/users")
@Api(tags = "会员的控制器")
public class UserController implements UserServiceFeign {

    @Autowired
    private UserService userService;

    @Autowired
    private UserAuthAuditRecordService userAuthAuditRecordService;


    @Autowired
    private UserAuthInfoService userAuthInfoService;

    @GetMapping
    @ApiOperation(value = "查询会员的列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数"),
            @ApiImplicitParam(name = "mobile", value = "会员的手机号"),
            @ApiImplicitParam(name = "userId", value = "会员的Id,精确查询"),
            @ApiImplicitParam(name = "userName", value = "会员的名称"),
            @ApiImplicitParam(name = "realName", value = "会员的真实名称"),
            @ApiImplicitParam(name = "status", value = "会员的状态")

    })
    @PreAuthorize("hasAuthority('user_query')")
    public R<Page<User>> findByPage(@ApiIgnore Page<User> page,
                                    String mobile,
                                    Long userId,
                                    String userName,
                                    String realName,
                                    Integer status
    ) {
        page.addOrder(OrderItem.desc("last_update_time"));
        Page<User> userPage = userService.findByPage(page, mobile, userId, userName, realName, status, null);
        return R.ok(userPage);
    }


    @PostMapping("/status")
    @ApiOperation(value = "修改用户的状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员的id"),
            @ApiImplicitParam(name = "status", value = "会员的状态"),
    })
    @PreAuthorize("hasAuthority('user_update')")
    public R updateStatus(Long id, Byte status) {
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        boolean updateById = userService.updateById(user);
        if (updateById) {
            return R.ok("更新成功");
        }
        return R.fail("更新失败");
    }


    @PatchMapping
    @ApiOperation(value = "修改用户")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "user", value = "会员的json数据"),
    })
    @PreAuthorize("hasAuthority('user_update')")
    public R updateStatus(@RequestBody @Validated User user) {
        boolean updateById = userService.updateById(user);
        if (updateById) {
            return R.ok("更新成功");
        }
        return R.fail("更新失败");
    }

    @GetMapping("/info")
    @ApiOperation(value = "查询会员的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "会员的Id")
    })
    public R<User> userInfo(Long id) {
        User user = userService.getById(id);
        return R.ok(user);
    }


    @GetMapping("/directInvites")
    @ApiOperation(value = "查询该用户邀请的用户列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "该用户的Id"),
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数"),

    })
    public R<Page<User>> getDirectInvites(@ApiIgnore Page<User> page, Long userId) {
        Page<User> userPage = userService.findDirectInvitePage(page, userId);
        return R.ok(userPage);
    }


    @GetMapping("/auths")
    @ApiOperation(value = "查询用户的审核列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页"),
            @ApiImplicitParam(name = "size", value = "每页显示的条数"),
            @ApiImplicitParam(name = "mobile", value = "会员的手机号"),
            @ApiImplicitParam(name = "userId", value = "会员的Id,精确查询"),
            @ApiImplicitParam(name = "userName", value = "会员的名称"),
            @ApiImplicitParam(name = "realName", value = "会员的真实名称"),
            @ApiImplicitParam(name = "reviewsStatus", value = "会员的状态")

    })
    public R<Page<User>> findUserAuths(
            @ApiIgnore Page<User> page,
            String mobile,
            Long userId,
            String userName,
            String realName,
            Integer reviewsStatus
    ) {
        Page<User> userPage = userService.findByPage(page, mobile, userId, userName, realName, null, reviewsStatus);
        return R.ok(userPage);
    }

    /**
     * 差询用户的认证详情
     * {
     * user:
     * userAuthInfoList:[]
     * userAuditRecordList:[]
     * }
     */
    @GetMapping("/auth/info")
    @ApiOperation(value = "查询用户的认证详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户的Id")
    })
    public R<UseAuthInfoVo> getUseAuthInfo(Long id) {

        User user = userService.getById(id);
        List<UserAuthAuditRecord> userAuthAuditRecordList = null;
        List<UserAuthInfo> userAuthInfoList = null;
        if (user != null) {
            // 用户的审核记录
            Integer reviewsStatus = user.getReviewsStatus();
            if (reviewsStatus == null || reviewsStatus == 0) { // 待审核
                userAuthAuditRecordList = Collections.emptyList(); // 用户没有审核记录
                userAuthInfoList = userAuthInfoService.getUserAuthInfoByUserId(id);
            } else {
                userAuthAuditRecordList = userAuthAuditRecordService.getUserAuthAuditRecordList(id);
                // 查询用户的认证详情列表-> 用户的身份信息 : if no record => it will throw exception
                UserAuthAuditRecord userAuthAuditRecord = userAuthAuditRecordList.size() == 0 ? new UserAuthAuditRecord(): userAuthAuditRecordList.get(0);// 之前我们查询时,是按照认证的日志排序的,第0 个值,就是最近被认证的一个值
                Long authCode = userAuthAuditRecord.getAuthCode(); // 认证的唯一标识
                userAuthInfoList = userAuthInfoService.getUserAuthInfoByCode(authCode);
            }
        }
        return R.ok(new UseAuthInfoVo(user, userAuthInfoList, userAuthAuditRecordList));
    }


    /**
     * 审核的本质:
     * 在于对一组图片(唯一Code)的认可,符合条件,审核通过
     *
     * @return
     */
    @PostMapping("/auths/status")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", value = "用户的ID"),
            @ApiImplicitParam(name = "authStatus", value = "用户的审核状态"),
            @ApiImplicitParam(name = "authCode", value = "一组图片的唯一标识"),
            @ApiImplicitParam(name = "remark", value = "审核拒绝的原因"),
    })
    public R updateUserAuthStatus(@RequestParam(required = true) Long id, @RequestParam(required = true) Byte authStatus, @RequestParam(required = true) Long authCode, String remark) {
        // 审核: 1 修改user 里面的reviewStatus
        // 2 在authAuditRecord 里面添加一条记录

        userService.updateUserAuthStatus(id, authStatus, authCode, remark);
        return R.ok();
    }


    @GetMapping("/current/info")
    @ApiOperation(value = "获取当前登录用户的详细情况然后脱敏")
    public R<User> currentUserInfo(){
        // 获取用户id
        String idStr = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        User user = userService.getById(Long.valueOf(idStr));
        user.setPassword("****");
        user.setPaypassword("*****");
        user.setAccessKeyId("*****");
        user.setAccessKeySecret("*********");
        return R.ok(user);
    }

    @PostMapping("/authAccount")
    @ApiOperation(value = "用户的实名认证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "" ,value = "")
    })
    public R identifyCheck(@RequestBody UserAuthForm userAuthForm){
        String idStr =
                SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        boolean isOk = userService.identifyVerify(Long.valueOf(idStr),userAuthForm) ;
        if(isOk){
            return R.ok() ;
        }
        return R.fail("认证失败") ;
    }

    @PostMapping("/authUser")
    @ApiOperation(value = "用户进行高级认证")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "imgs",value ="用户的图片地址" )
    })
    public R authUser(@RequestBody String []imgs){
        String idStr = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        userService.authUser(Long.valueOf(idStr), Arrays.asList(imgs)) ;
        return R.ok() ;
    }

    @PostMapping("/updatePhone")
    @ApiOperation(value = "修改手机号")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "updatePhoneParam",value = "updatePhoneParam 的json 数据")
    })
    public R updatePhone(@RequestBody UpdatePhoneParam updatePhoneParam){
        String idStr = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        boolean flag = userService.updatePhone(Long.valueOf(idStr), updatePhoneParam);
        if(flag){
            return R.ok();
        }
        return R.fail("修改失败");
    }

    @GetMapping("/checkTel")
    @ApiOperation(value = "检查新的手机号是否可用,如可用,则给该新手机发送验证码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "mobile" ,value = "新的手机号"),
            @ApiImplicitParam(name = "countryCode" ,value = "手机号的区域")
    })
    public R checkNewPhone(@RequestParam(required = true) String
                                   mobile,@RequestParam(required = true) String countryCode){
        boolean isOk = userService.checkNewPhone(mobile,countryCode) ;
        return isOk ? R.ok():R.fail("新的手机号校验失败") ;
    }

    @PostMapping("/updateLoginPassword")
    @ApiOperation(value = "修改用户的登录密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "updateLoginParam", value = "修改用户的登录密码")
    })
    public R updateLoginPwd(@RequestBody @Validated UpdateLoginParam updateLoginParam) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        boolean isOk = userService.updateUserLoginPwd(userId, updateLoginParam);
        if (isOk) {
            return R.ok();
        }
        return R.fail("修改失败");
    }

    @PostMapping("/updatePayPassword")
    @ApiOperation(value = "修改用户的交易密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "updateLoginParam", value = "修改用户的交易密码")
    })
    public R updatePayPwd(@RequestBody @Validated UpdateLoginParam updateLoginParam) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        boolean isOk = userService.updateUserPayPwd(userId, updateLoginParam);
        if (isOk) {
            return R.ok();
        }
        return R.fail("修改失败");
    }


    @PostMapping("/setPayPassword")
    @ApiOperation(value = "重新设置交易密码")
    public R setPayPassword(@RequestBody @Validated UnsetPayPasswordParam unsetPayPasswordParam) {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        boolean isOk = userService.unsetPayPassword(userId, unsetPayPasswordParam);
        if (isOk) {
            return R.ok();
        }
        return R.fail("重置失败");
    }


    @GetMapping("/invites")
    @ApiOperation(value = "用户的邀请列表")
    public R<List<User>> getUserInvites() {
        Long userId = Long.valueOf(SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString());
        List<User> users = userService.getUserInvites(userId);
        return R.ok(users);
    }


    @PostMapping("/register")
    @ApiOperation(value = "用户的注册")
    public R register(@RequestBody RegisterParam registerParam) {
        boolean flag = userService.register(registerParam);
        if (flag) {
            return R.ok();
        }
        return R.fail("注册失败");
    }


    @PostMapping("/setPassword")
    @ApiOperation(value = "用户重置密码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "unSetPasswordParam", value = "unSetPasswordParam json")
    })
    public R unsetPassword(@RequestBody @Validated UnSetPasswordParam unSetPasswordParam) {
        boolean isOk = userService.unsetLoginPwd(unSetPasswordParam);
        if (isOk) {
            return R.ok();
        }
        return R.fail("重置失败");
    }

    /**
     * 用于admin-service 里面远程调用member-service
     * @param ids
     * @return
     */
    @Override
    public Map<Long, UserDto> getBasicUsers(List<Long> ids, String username, String mobile) {
        Map<Long, UserDto> userDtos = userService.getBasicUsers(ids, username, mobile);
        return userDtos;
    }
}

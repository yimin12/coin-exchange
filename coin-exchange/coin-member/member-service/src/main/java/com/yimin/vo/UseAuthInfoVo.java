package com.yimin.vo;

import com.yimin.domain.User;
import com.yimin.domain.UserAuthAuditRecord;
import com.yimin.domain.UserAuthInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/15 14:14
 *   @Description :
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@ApiModel(value = "用户认证的详细信息")
public class UseAuthInfoVo implements Serializable {

    @ApiModelProperty(value = "用户")
    private User user ;

    @ApiModelProperty(value = "用户认证的详情列表")
    private List<UserAuthInfo> userAuthInfoList ;

    @ApiModelProperty(value = "用户审核历史")
    private List<UserAuthAuditRecord> authAuditRecordList ;
}

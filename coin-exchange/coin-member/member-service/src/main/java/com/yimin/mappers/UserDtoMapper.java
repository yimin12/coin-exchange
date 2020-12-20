package com.yimin.mappers;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/17 11:44
 *   @Description :
 *
 */


import com.yimin.domain.User;
import com.yimin.dto.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 用来做对象的映射转化
 * entity2Dto
 * Dto2Entity
 */
@Mapper(componentModel = "spring")
public interface UserDtoMapper {

    // 获取该对象的实例
    UserDtoMapper INSTANCE = Mappers.getMapper(UserDtoMapper.class);

    /**
     * 将entity 转化为dto
     * @param source
     * @return
     */
    UserDto convert2Dto(User source) ;

    /**
     * 将dto 对象转化为entity 对象
     * @param source
     * @return
     */
    User convert2Entity(UserDto source) ;

    /**
     * 将entity 转化为dto
     * @param source
     * @return
     */
    List<UserDto> convert2Dto(List<User> source) ;

    /**
     * 将dto 对象转化为entity 对象
     * @param source
     * @return
     */
    List<User> convert2Entity(List<UserDto> source) ;

}

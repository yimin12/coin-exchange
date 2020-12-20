package com.yimin.mappers;

import com.yimin.domain.UserBank;
import com.yimin.dto.UserBankDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/18 16:22
 *   @Description :
 *
 */
@Mapper(componentModel = "spring")
public interface UserBankDtoMapper {

    UserBankDtoMapper INSTANCE = Mappers.getMapper(UserBankDtoMapper.class);

    UserBank toConvertEntity(UserBankDto source);

    List<UserBank> toConvertEntity(List<UserBankDto> source);


    UserBankDto toConvertDto(UserBank source);

    List<UserBankDto> toConvertDto(List<UserBank> source);
}

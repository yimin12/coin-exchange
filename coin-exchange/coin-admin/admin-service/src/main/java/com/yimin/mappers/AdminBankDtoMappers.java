package com.yimin.mappers;

import com.yimin.domain.AdminBank;
import com.yimin.dto.AdminBankDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/18 14:30
 *   @Description :
 *
 */
@Mapper(componentModel = "spring")
public interface AdminBankDtoMappers {

    AdminBankDtoMappers INSTANCE = Mappers.getMapper(AdminBankDtoMappers.class);

    AdminBank toConvertEntity(AdminBankDto source);

    AdminBankDto toConvertDto(AdminBank source);

    List<AdminBank> toConvertEntity(List<AdminBankDto> source);

    List<AdminBankDto> toConvertDto(List<AdminBank> source);

}

package com.yimin.mappers;

import com.yimin.domain.Coin;
import com.yimin.dto.CoinDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/19 0:04
 *   @Description :
 *
 */
@Mapper(componentModel = "spring")
public interface CoinMappersDto {

    CoinMappersDto INSTANCE = Mappers.getMapper(CoinMappersDto.class) ;

    Coin toConvertEntity(CoinDto source) ;

    List<Coin> toConvertEntity(List<CoinDto> source) ;

    CoinDto toConvertDto(Coin source) ;

    List<CoinDto> toConvertDto(List<Coin> source) ;
}

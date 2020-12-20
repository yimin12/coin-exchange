package com.yimin.mappers;

import com.yimin.domain.Market;
import com.yimin.dto.MarketDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/18 20:41
 *   @Description :
 *
 */
@Mapper(componentModel = "spring")
public interface MarketDtoMappers {

    MarketDtoMappers INSTANCE = Mappers.getMapper(MarketDtoMappers.class) ;

    Market toConvertEntity(MarketDto source) ;


    MarketDto toConvertDto(Market source) ;


    List<Market> toConvertEntity(List<MarketDto> source) ;


    List<MarketDto> toConvertDto(List<Market> source) ;
}

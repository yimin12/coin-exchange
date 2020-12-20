package com.yimin.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yimin.domain.Coin;
import com.yimin.dto.CoinDto;
import com.yimin.feign.CoinServiceFeign;
import com.yimin.model.R;
import com.yimin.service.CoinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/17 16:05
 *   @Description :
 *
 */
@RestController
@RequestMapping("/coins")
@Api(tags = "数字货币的数据接口")
public class CoinController implements CoinServiceFeign {

    @Autowired
    private CoinService coinService ;

    /**
     * http://localhost:9527/finance/coins?name=xxx&type=usdt&status=1&title=xxx&wallet_type=rgb&current=1&size=15
     * @return
     */
    @GetMapping
    @ApiOperation(value = "分页条件查询数字货币")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "name" ,value = "数字货币的名称") ,
            @ApiImplicitParam(name = "type" ,value = "数字货币类型的名称") ,
            @ApiImplicitParam(name = "status" ,value = "数字货币类型的状态") ,
            @ApiImplicitParam(name = "status" ,value = "数字货币类型的标题") ,
            @ApiImplicitParam(name = "wallet_type" ,value = "数字货币钱包类型") ,
            @ApiImplicitParam(name = "current" ,value = "当前页") ,
            @ApiImplicitParam(name = "size" ,value = "每页显示的条数") ,
    })
    public R<Page<Coin>> findByPage(
            String name , String type , Byte status ,
            String title ,@RequestParam(name = "wallet_type",required = false) String walletType ,
            @ApiIgnore Page<Coin> page
    ){
        Page<Coin> coinPage = coinService.findByPage(name,type,status,title,walletType,page) ;
        return R.ok(coinPage) ;
    }
    /**
     * 禁用或启用
     */
    @PostMapping("/setStatus")
    @ApiOperation(value = "禁用或启用币种")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coin" ,value = "coin 的json 数据")
    })
    public R setStatus(@RequestBody Coin coin){
        boolean updateById = coinService.updateById(coin);
        if(updateById){
            return R.ok() ;
        }
        return R.fail("设置状态失败") ;
    }

    @GetMapping("/info/{id}")
    @ApiOperation(value = "查询币种的详细信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "币种的id")
    })
    public R<Coin> info(@PathVariable("id") Long id){
        Coin coin = coinService.getById(id);
        return R.ok(coin) ;
    }
    @GetMapping("/all")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status",value = "币种当前的状态")
    })
    @ApiOperation(value = "通过状态查询所有的币种信息")
    public R<List<Coin>> getCoinAll(Byte status){
        List<Coin> coins = coinService.getCoinsByStatus(status) ;
        return R.ok(coins) ;
    }

    @PatchMapping
    @ApiOperation(value = "修改我们的币种的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coin" ,value = "coin 的json 数据")
    })
    public R update(@RequestBody @Validated Coin coin){
        boolean updateById = coinService.updateById(coin);
        if(updateById){
            return R.ok() ;
        }
        return R.fail("修改失败") ;
    }

    @PostMapping
    @ApiOperation(value = "新增我们的币种的信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "coin" ,value = "coin 的json 数据")
    })
    public R<Coin> save(@RequestBody @Validated Coin coin){
        coin.setStatus((byte)1);
        boolean save = coinService.save(coin);
        // coin 新增成功后,会有Id ,这是mybatis-plus 在新增成功后,
        // 会自动的进行一个sql 语句的查询,查询的结果就是id,之后把id 设置给coin
        if(save){
            return R.ok(coin) ;
        }
        return R.fail("新增失败") ;
    }

    @Override
    public List<CoinDto> findCoins(List<Long> coinIds) {
        List<CoinDto> coinDtos = coinService.findList(coinIds);
        return coinDtos;
    }
}

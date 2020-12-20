import com.fasterxml.jackson.databind.ObjectMapper;
import com.yimin.model.R;
import com.yimin.model.WebLog;
import com.yimin.service.TestService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/13 0:29
 *   @Description :
 *
 */
@RestController
@Api(tags = "CoinCommon 里面测试的接口")
public class TestController {

    @Autowired
    private ObjectMapper objectMapper ;

    @Autowired
    private TestService testService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @GetMapping("/common/test")
    @ApiOperation(value = "测试方法", authorizations =
            {@Authorization("Authorization")})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param", value = "参数1", dataType
                    = "String", paramType = "query", example = "paramValue"),
            @ApiImplicitParam(name = "param1", value = "参数2", dataType
                    = "String", paramType = "query", example = "paramValue")
    })
    public R<String> testMethod(String param, String param1) {
        return R.ok("ok");
    }

    // test date formatted redis_template
    @GetMapping("/data/test")
    @ApiOperation(value = "日期格式化测试", authorizations = {@Authorization("Authorization")})
    public R<Date> testDate(){
        return R.ok(new Date());
    }

    @GetMapping("/redis/test")
    @ApiOperation(value = "redis测试", authorizations = {@Authorization("Authorization")})
    public R<Date> testRedis(){
        WebLog webLog = new WebLog();
        webLog.setResult("ok");
        webLog.setMethod("com.yimin.domain.WebLog.testRedis");
        redisTemplate.opsForValue().set("com.yimin.domain.WebLog", webLog);
        return R.ok(new Date());
    }

    @GetMapping("/jetcache/test")
    @ApiOperation(value = "jetcache缓存的测试", authorizations = {@Authorization("Authorization")})
    public R<Date> testJetCache(String username){
        WebLog webLog = testService.get(username);
        System.out.println(webLog);
        return R.ok(new Date());
    }
}

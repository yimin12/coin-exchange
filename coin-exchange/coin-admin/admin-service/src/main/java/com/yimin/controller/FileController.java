package com.yimin.controller;

import cn.hutool.core.date.DateUtil;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.common.utils.BinaryUtil;
import com.aliyun.oss.model.MatchMode;
import com.aliyun.oss.model.PolicyConditions;
import com.yimin.model.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
/*
 *   @Author : Yimin Huang
 *   @Contact : hymlaucs@gmail.com
 *   @Date : 2020/12/14 20:55
 *   @Description :
 *
 */
@RestController
@Api(tags = "文件上传控制器")
public class FileController {

    @Autowired
    private OSS ossClient;  // spring-cloud-alibaba-oss 会自动的注入该对象，报红不要紧

    @Value("${spring.cloud.alicloud.access-key}")
    private String accessId;

    @Value("${oss.bucket.name:coin-exchange-images}")
    private String bucketName;

    @Value("${spring.cloud.alicloud.oss.endpoint}")
    private String endPoint;


    @Value("${oss.callback.url:http://coinoss.free.idcfengye.com}")
    private String ossCallbackUrl;

    @ApiOperation(value = "文件上传")
    @PostMapping("/image/AliYunImgUpload")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "file" ,value = "你要上传的文件")
    })
    public R<String> fileUpload(@RequestParam("file") MultipartFile file) throws IOException{
        /**
         * 1 bucketName
         * 2 文件的名称 日期 +原始的文件名（uuid）来做
         * 3 文件的输入流
         */
        //////////////// 2020-10-10->2020/10/10/xxx.jpg
        String fileName = DateUtil.today().replaceAll("-","/") + "/" + file.getOriginalFilename();
        ossClient.putObject(bucketName, fileName, file.getInputStream()); // 开始文件上传

        // 上传成功后返回路径，路径为 https://exchange-imgs.oss-cn-beijing.aliyuncs.com/2020/10/10/1588586643_meitu_1.jpg
        return R.ok("https://" + bucketName + "." + endPoint + "/" + fileName); //能使用浏览器访问到文件路径http://xxx.com/路径
    }

    @GetMapping("/image/pre/upl")
    @ApiOperation(value = "文件的上传获取票据")
    public R<Map<String, String>> preUploadPolicy(){
        String dir = DateUtil.today().replaceAll("-","/") + "/";
        Map<String, String> policy = getPolicy(30L, 3 * 1024 * 1024L, dir);
        return R.ok(policy);
    }

    private Map<String, String> getPolicy(long expireTime, long maxFileSize, String dir) {
        try{
            Long expireEndTime = System.currentTimeMillis() + expireTime * 1000;
            Date expiration = new Date(expireEndTime);
            // PostObject请求最大可支持的文件大小为5 GB，即CONTENT_LENGTH_RANGE为5*1024*1024*1024。
            PolicyConditions policyConditions = new PolicyConditions();
            // 设置上传文件的最大体积
            policyConditions.addConditionItem(PolicyConditions.COND_CONTENT_LENGTH_RANGE, 0, maxFileSize);
            String postPolicy = ossClient.generatePostPolicy(expiration, policyConditions);
            byte[] binaryData = postPolicy.getBytes("utf-8");
            String encodedPolicy = BinaryUtil.toBase64String(binaryData);
            String postSignature = ossClient.calculatePostSignature(postPolicy);

            // 返回值
            Map<String, String> respMap = new LinkedHashMap<String, String>();
            respMap.put("accessid", accessId); // 设置我们之前的accessKey
            respMap.put("policy", encodedPolicy);
            respMap.put("signature", postSignature);
            respMap.put("dir", dir);
            respMap.put("host", "https://" + bucketName +"."+ endPoint); // 设置 上传的Host https://bulkname.endpoint
            respMap.put("expire", String.valueOf(expireEndTime / 1000));
            // respMap.put("expire", formatISO8601Date(expiration));

            JSONObject jasonCallback = new JSONObject();
            jasonCallback.put("callbackUrl", ossCallbackUrl); // 当我们的前端把文件上传到oss 服务器成功后
            // ,oss 服务器会想回调的callbackUrl(公网) 发一个post请求来告诉后端服务器用户上传文件的情况
            jasonCallback.put("callbackBody",
                    "filename=${object}&size=${size}&mimeType=${mimeType}&height=${imageInfo.height}&width=${imageInfo.width}");
            jasonCallback.put("callbackBodyType", "application/x-www-form-urlencoded");
            String base64CallbackBody = BinaryUtil.toBase64String(jasonCallback.toString().getBytes());
            respMap.put("callback", base64CallbackBody);
            return respMap;
        } catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


}

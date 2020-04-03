package com.online.edu.controller;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.online.edu.common.R;
import com.online.edu.handler.ConstantPropertiesUtil;
import org.joda.time.DateTime;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.UUID;

/**
 * 上传文件到阿里云oss
 */
@RestController
@RequestMapping("/eduservice/oss")
@CrossOrigin
public class FileUploadController {

    //上传讲师头像
    @PostMapping("upload")
    public R uploadTeacherImg(@RequestParam("file") MultipartFile file,
                              @RequestParam(value = "host",required = false) String host) {

        // Endpoint以杭州为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtil.END_POINT;
        // 云账号AccessKey有所有API访问权限，建议遵循阿里云安全最佳实践，创建并使用RAM子账号进行API访问或日常运维，请登录 https://ram.console.aliyun.com 创建。
        String accessKeyId = ConstantPropertiesUtil.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtil.ACCESS_KEY_SECRET;

        String yourBucketName = ConstantPropertiesUtil.BUCKET_NAME;

        try {
            //1.获取到上传的文件 MultipartFile

            //2.获得上传文件名称
            String filename = file.getOriginalFilename();

            //确定上传的文件的扩展名
            String suffix = "";
            //截取字符串
            int indexOf = filename.lastIndexOf(".");
            if (indexOf != -1) {
                suffix = filename.substring(indexOf);
            }

            //使用工具类获取当前日期
            String filePath = new DateTime().toString("yyyy/MM/dd");

            String hostName = ConstantPropertiesUtil.HOST;//头像文件夹

            //上传头像host里面为空，上传封面头像host有值
            if(!StringUtils.isEmpty(host)){//不为空
                hostName = host;
            }

            //确定上传文件的名称
            String uploadFile = filePath + "/" + hostName + "/" + UUID.randomUUID().toString() + suffix;


            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();

            //3.上传到阿里云oss
            // 创建OSSClient实例。
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            //第一个参数BucketName，第二个参数文件名称，第三个参数文件输入流
            ossClient.putObject(yourBucketName, uploadFile, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();



            //返回上传以后oss存储路径
            String path = "http://" + yourBucketName + "." + endpoint + "/" + uploadFile;
            return R.ok().data("imgurl", path);
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }

    }

}

package com.yhh.oss.controller.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.OSSException;
import com.yhh.oss.controller.service.OssService;
import com.yhh.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.UUID;

@Service
public class OssServiceImpl implements OssService {
    //上传文件到oss中
    public String uploadFileAvatar(MultipartFile file) {

        // Endpoint以华东1（杭州）为例，其它Region请按实际情况填写。
        String endpoint = ConstantPropertiesUtils.END_POINT;
        // 阿里云账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM用户进行API访问或日常运维，请登录RAM控制台创建RAM用户。
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;
        try {
            // 创建OSSClient实例
            OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

            // 获取上传文件的输入流
            InputStream inputStream = file.getInputStream();
            /**获取文件名称  ->相同会把最后一次覆盖
             *
             *  在文件名称里面添加随机唯一值
             */
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            String fileName = file.getOriginalFilename();
            fileName = uuid + fileName;
            /** 调用OSS方法实现上传 bucket名称，文件路径和文件名 aa/bb/1.jpg，上传文件输入流
             *
             * 文件按照日期分类  如2019/11/12/01.jpg
             */
            //获取当前日期 new Date();  SimpleDateFormat做格式转换
            String datePath = new DateTime().toString("yyyy/MM/dd");
            fileName = datePath + "/" + fileName;
            ossClient.putObject(bucketName, fileName, inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传到oss的路径手动拼接
            //https://skrhenren.oss-cn-beijing.aliyuncs.com/shape.jpg
            String url = "https://" + bucketName + "." + endpoint + "/" + fileName;
            return url;

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}

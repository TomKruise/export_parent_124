package com.tom.utils;

import com.google.gson.Gson;
import com.qiniu.common.QiniuException;
import com.qiniu.http.Response;
import com.qiniu.storage.Configuration;
import com.qiniu.storage.Region;
import com.qiniu.storage.UploadManager;
import com.qiniu.storage.model.DefaultPutRet;
import com.qiniu.util.Auth;


import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

public class ImageUploadUtils {

    private static String accessKey = "F0cu8ygnAcpl5-p4dfukzUJxUeGYcexMs4dVyyaY";
    private static String secretKey = "pVNvq890ZWESWgm1A6eP5J1r8fFF2vHmSoKnC14T";
    // 存储空间名称
    private static String bucket = "ee-124";
    // 存储空间分配的临时域名（免费用户有效期一个月）
    private static String url = "http://q6wpfj8yh.bkt.clouddn.com/";

    /**
     * 通过七牛云的API完成文件上传
     * 参数 ： 文件的字节流
     * 返回值：图片的访问路径
     */
    public static String upload(InputStream inputStream) {
        //构造一个带指定 Region 对象的配置类
       Configuration cfg = new Configuration(Region.huabei());//华北
        UploadManager uploadManager = new UploadManager(cfg);
        //默认不指定key的情况下，以文件内容的hash值作为文件名
        String key = null;
        try {
            Auth auth = Auth.create(accessKey, secretKey);
            String upToken = auth.uploadToken(bucket);
            try {
                Response response = uploadManager.put(inputStream, key, upToken, null, null);
                //解析上传成功的结果
                DefaultPutRet putRet = new Gson().fromJson(response.bodyString(), DefaultPutRet.class);
                key = url + putRet.key;
            } catch (QiniuException ex) {
                Response r = ex.response;
                System.err.println(r.toString());
                try {
                    System.err.println(r.bodyString());
                } catch (QiniuException ex2) {
                    //ignore
                }
            }
        } catch (Exception ex) {
            //ignore
        }
        return key;
    }

    public static void main(String[] args) throws Exception {
        File file = new File("E:\\temp\\upload\\1.jpg");
        InputStream input = new FileInputStream(file);
        String s = ImageUploadUtils.upload(input);
        System.out.println(s);
    }
}

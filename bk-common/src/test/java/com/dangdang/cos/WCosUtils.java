package com.dangdang.cos;

import java.io.File;
import java.io.FileOutputStream;
import java.security.NoSuchAlgorithmException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.common.DateUtil;
import com.dangdang.common.HttpUtil;
import com.dangdang.common.MD5Utils;
import com.dangdang.model.Qyer;

public class WCosUtils {
    /**
     * 微票 cos wiki地址：http://wiki.intra.wepiao.com/pages/viewpage.action?pageId=14613614
     */
    private static final Logger logger            = LoggerFactory.getLogger("db_info");
    /**
     * 微信错误头像的header 标示
     */
    private String              DATE2PATH_PATTERN = "/yyyy/MM/dd/HH/";
    private String              PHOTO_SUFFIX      = ".jpg";
    //cos默认region
    private String              COS_REGION        = "sh";

    private String              CGI_URL_PREFIX;
    private String              HEAD_FOLDER_PREFIX;
    private String              UCIMG_URL;
    //cgi 调用url
    private String              CREATE_FOLDER_URL;
    private String              UPLOAD_URL;
    //木桶空间名称
    private String              BUCKET_NAME;

    private WCosUtils() {
        //CGI_URL_PREFIX = "http://10.104.1.55/v2/cos/";
        CGI_URL_PREFIX = "http://commoncgi.intra.wepiao.com/v2/cos/";
        HEAD_FOLDER_PREFIX = "/head";
        UCIMG_URL = "https://ucimg-cos.wepiao.com";
        BUCKET_NAME = "ucimg";
        CREATE_FOLDER_URL = CGI_URL_PREFIX + "create-folder";
        UPLOAD_URL = CGI_URL_PREFIX + "upload";
    }

    private static WCosUtils instance = null;

    public static WCosUtils getInstance() {
        if (instance == null) {
            synchronized (WCosUtils.class) {
                if (instance == null) {
                    instance = new WCosUtils();
                }
            }
        }
        return instance;
    }

    public String readFolder(String folder, String context) {
        Map<String, String> params = new HashMap<>();
        params.put("bucketName", BUCKET_NAME);
        params.put("region", COS_REGION);
        params.put("path", folder);
        params.put("num", "199");
        if (StringUtils.isNotBlank(context)) {
            params.put("context", context);
        }
        String res = HttpUtil.getInstance().post("http://10.104.1.55/v2/cos/list-folder", params);
        JSONObject result = JSONObject.parseObject(res);
        Integer code = result.getInteger("code");
        if (null != code && code == 0) {
            return result.toJSONString();
        }
        return result.toJSONString();
    }

    /**
     * 将第三个的头像上传到微票的CDN上
     * @param photoUrl
     * @return
     */
    public Qyer changePhotoUrl2Wepiao(String src, String photoUrl, Date now) {

        if (StringUtils.isBlank(src)) {
            return null;
        }

        HttpResponse res = HttpUtil.getInstance().get2Response(src, null, 0);
        //失败尝试一次
        if (null == res) {
            res = HttpUtil.getInstance().get2Response(src, null, 0);
        }
        //访问不了，直接返回
        if (null == res) {
            return null;
        }

        File file = null;
        try {
            file = new File("D:/zealand/" + photoUrl+".jpg");
            //file = File.createTempFile(photoUrl, ".jpg");
            System.out.println(file.getPath());
            FileOutputStream outstream = new FileOutputStream(file);
            HttpEntity entity = res.getEntity();
            entity.writeTo(outstream);
            String folderPath = createFolder(now);
            //如果创建文件失败多尝试一次
            if (null == folderPath) {
                folderPath = createFolder(now);
            }
            if (null == folderPath) {
                return null;
            }
            String newUrl = uploadFile(file, folderPath, photoUrl, PHOTO_SUFFIX);
            //如果上传文件失败多尝试一次
            if (null == newUrl) {
                newUrl = uploadFile(file, folderPath, photoUrl, PHOTO_SUFFIX);
            }
            //            if (null != file) {
            //                file.delete();
            //            }

            Qyer qyer = new Qyer();
            qyer.setUrl(newUrl);
            qyer.setFileSize(file.length());
            qyer.setFileMd5(MD5Utils.fileMD5(file));
            return qyer;
        } catch (Exception e) {
            if (file != null) {
                file.delete();
            }
            file = null;
            System.out.println(e);
            logger.error("下载文件失败:" + photoUrl, e);
        }
        return null;
    }

    /**
     * 根据日期创建目录
     * @param date
     * @return
     */
    private String date2FolderPath(Date date) {
        if (null == date) {
            date = Calendar.getInstance().getTime();
        }
        return DateUtil.format(date, DATE2PATH_PATTERN);
    }

    /**
     * 创建目录  
     * @param date
     * @return
     */
    private String createFolder(Date date) {
        //用来存贮在redis中的路径
        String redisPath = date2FolderPath(date);
        return HEAD_FOLDER_PREFIX + redisPath;
        //真实存贮路径
        /*String realPath = HEAD_FOLDER_PREFIX + redisPath;

        Map<String, String> params = new HashMap<>();
        params.put("bucketName", BUCKET_NAME);
        params.put("region", COS_REGION);
        params.put("path", realPath);
        //params.put("bizAttr", "文件属性");
        String res = HttpUtil.getInstance().post(CREATE_FOLDER_URL, params);
        if (null == res) {
            return null;
        }
        JSONObject result = JSONObject.parseObject(res);
        Integer code = result.getInteger("code");
        if (null != code && (code == 0 || code == -178)) {
            return realPath;
        } else {
            return null;
        }*/

    }

    private String uploadFile(File file, String folderPath, String photoUrl, String suffix) throws NoSuchAlgorithmException {
        String newUrl = folderPath + MD5Utils.getMD5(photoUrl) + suffix;
        Map<String, String> params = new HashMap<>();
        params.put("bucketName", BUCKET_NAME);
        params.put("region", COS_REGION);
        params.put("dstPath", newUrl);
        params.put("insertOnly", "0");
        //params.put("bizAttr", "文件属性");
        Map<String, File> files = new HashMap<>();
        files.put("file", file);
        String res = HttpUtil.getInstance().upload(UPLOAD_URL, params, null, files, null, 0);
        if (null == res) {
            return null;
        }
        JSONObject result = JSONObject.parseObject(res);
        Integer code = result.getInteger("code");
        if (null != code && (code == 0 || code == -177)) {
            return UCIMG_URL + newUrl;
        }
        return null;

    }

}

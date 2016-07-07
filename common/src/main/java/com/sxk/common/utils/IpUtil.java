package com.sxk.common.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Random;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @description ipUtil,iP地址工具类
 * @author sxk
 * @email sxk5245@126.com
 * @date 2016年5月10日
 */
public class IpUtil {

    private static final Logger logger           = LoggerFactory.getLogger(IpUtil.class);
    private static final String IP_INFO_URL      = "http://ip.taobao.com/service/getIpInfo.php?ip=";
    private static final String COMMON_SEPARATOR = " ";
    private static final String IP_SEPARATOR     = ".";

    public static String getRandomIp() {
        //ip范围 
        int[][] range = { { 607649792, 608174079 },//36.56.0.0-36.63.255.255
                { 1038614528, 1039007743 },//61.232.0.0-61.237.255.255
                { 1783627776, 1784676351 },//106.80.0.0-106.95.255.255
                { 2035023872, 2035154943 },//121.76.0.0-121.77.255.255
                { 2078801920, 2079064063 },//123.232.0.0-123.235.255.255
                { -1950089216, -1948778497 },//139.196.0.0-139.215.255.255
                { -1425539072, -1425014785 },//171.8.0.0-171.15.255.255
                { -1236271104, -1235419137 },//182.80.0.0-182.92.255.255
                { -770113536, -768606209 },//210.25.0.0-210.47.255.255
                { -569376768, -564133889 }, //222.16.0.0-222.95.255.255
        };

        Random rdint = new Random();
        int index = rdint.nextInt(10);
        String ip = num2ip(range[index][0] + new Random().nextInt(range[index][1] - range[index][0]));
        return ip;
    }

    /*
     * 将十进制转换成ip地址
     */
    private static String num2ip(int ip) {
        int[] b = new int[4];
        StringBuffer sb = new StringBuffer();

        b[0] = (int) ((ip >> 24) & 0xff);
        b[1] = (int) ((ip >> 16) & 0xff);
        b[2] = (int) ((ip >> 8) & 0xff);
        b[3] = (int) (ip & 0xff);
        sb.append(Integer.toString(b[0]) + IP_SEPARATOR);
        sb.append(Integer.toString(b[1]) + IP_SEPARATOR);
        sb.append(Integer.toString(b[2]) + IP_SEPARATOR);
        sb.append(Integer.toString(b[3]));
        return sb.toString();
    }

    /**
     * 通过IP获取地址(需要联网，调用IP库)
     * 
     * @param ip
     * @return
     */
    public static String getIpInfo(String ip) {
        String info = null;
        try {
            URL url = new URL(IP_INFO_URL + ip);
            HttpURLConnection htpcon = (HttpURLConnection) url.openConnection();
            htpcon.setRequestMethod("GET");
            htpcon.setDoOutput(true);
            htpcon.setDoInput(true);
            htpcon.setUseCaches(false);

            InputStream in = htpcon.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            StringBuffer temp = new StringBuffer();
            String line = bufferedReader.readLine();
            while (line != null) {
                temp.append(line).append("\r\n");
                line = bufferedReader.readLine();
            }
            bufferedReader.close();

            info = parseIpInfo(temp.toString());
        } catch (Exception e) {
            logger.error("ip:{},error:{}", ip, e);
        }
        return info;
    }

    public static String parseIpInfo(String res) {
        try {
            System.out.println(res);
            JSONObject result = (JSONObject) JSON.parse(res);
            if (result.getIntValue("code") == 0) {
                JSONObject data = result.getJSONObject("data");
                if (null != data) {
                    StringBuffer sb = new StringBuffer();
                    sb.append(data.getString("country") + COMMON_SEPARATOR);
                    sb.append(data.getString("region") + COMMON_SEPARATOR);
                    sb.append(data.getString("city") + COMMON_SEPARATOR);
                    sb.append(data.getString("isp") + COMMON_SEPARATOR);
                    return sb.toString();
                }
            }
        } catch (Exception e) {
            logger.error("info:{},error:{}", res, e);
        }
        return null;
    }

    public static void main(String[] args) {
        String ip = getRandomIp();
        System.out.println(ip);
        System.out.println(getIpInfo(ip));
    }
}

package com.sxk.common.utils.http;

import java.util.HashMap;
import java.util.Map;

public class HttpTest {
    public static void main(String[] args) {
        String url = "http://10.104.140.34:8080/uc/v1/getpdabyopenid";
        String params = "{\"openId\": \"o0aT-d_xRcaHIy8--KG2V8FpO6jU\"}";
        Map<String, String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");

        String url2 = "http://localhost:8080/user2/sleep?millisecond=2000";
        String url3 = "http://localhost:8897/";
        String url4 = "https://www.google.com.hk/";
        for (int i = 0; i < 10; i++) {
            long start = System.currentTimeMillis();
            //String res = HttpUtils.post(url, params, headers, "UTF-8", 10);
            String res = HttpAsyncUtilTest.get(url3, null, null, "UTF-8", 5000);
            System.out.println("耗时毫秒:" + (System.currentTimeMillis() - start));
            System.out.println(res);
        }

    }
}

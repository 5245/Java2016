package com.dangdang.dao.impl;

import java.util.HashMap;
import java.util.Map;

import com.dangdang.common.HttpAsyncUtil;

/**
 * SMS短信即（Short Messaging Service）
 * @description 
 * @author sxk
 * @date 2017年3月10日
 *
 */
public class SMS4FakeMobile {
    public static void main(String[] args) {
        String alipayUrl = "https://memberprod.alipay.com/account/reg/section/reSendVerifyCode.json";
        Map<String, String> headers = new HashMap<>();
        Map<String, String> params = new HashMap<>();

        /* scene:mobileReg
         mobile:86-15369355602
         _input_charset:utf-8
         ctoken:DslvSbAonHbIy8n9*/

        params.put("scene", "mobileReg");
        params.put("mobile", "86-15369355602");
        params.put("_input_charset", "utf-8");
        params.put("ctoken", "DslvSbAonHbIy8n9");
        long start = System.currentTimeMillis();
        String res = HttpAsyncUtil.post(alipayUrl, params);
        System.out.println((System.currentTimeMillis() - start) + "ms  :  " + res);
    }
}

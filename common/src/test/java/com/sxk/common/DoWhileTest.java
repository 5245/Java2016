package com.sxk.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.util.Base64Utils;

import com.sxk.common.utils.MD5Utils;

public class DoWhileTest {

    private static final String HMAC_SHA1 = "HmacSHA1";

    public static void main2(String[] args) throws Exception {
        int i = 0;
        do {
            i++;
            System.out.println(i);
        } while (i != 10);

        String paramString1 = "http://ser3.2828.net/server/getUserInfo?userid=568984";
        // String paramString1 = "http://ser3.2828.net/server/memoryphotos_v250?userid=568984&todaytime=1469462400";
        String paramString2 = "12345678";

        SecretKeySpec signingKey = new SecretKeySpec(paramString2.getBytes(), HMAC_SHA1);
        Mac mac = Mac.getInstance(HMAC_SHA1);
        mac.init(signingKey);
        byte[] rawHmac = mac.doFinal(paramString1.getBytes());
        //nPoNSLDzIAFoTN1Xgx3Bb62T*Ag=
        //Xqhf5kmOSI1r4C5BetSB3QaQXgA=
        System.out.println(Base64Utils.encodeToString(rawHmac));

    }

    public static void main(String[] args) throws Exception {

        Map<String, String> params = new HashMap<>();
        params.put("appChannelId", "3430000001");
        params.put("appkey", "9");
        params.put("appver", "6.1.6");
        params.put("deviceid", "00000000-2f27-a740-ffff-ffffff4211db");
        params.put("from", "0123456789");
        params.put("imei", "898600e10116f0002365");
        params.put("mobileNo", "18710265851");
        params.put("nImei", "869552028454031");
        params.put("openId", "weiying_200000009");
        params.put("t", "1469431445");
        params.put("uid", "200000009");
        params.put("userId", "200000009");
        params.put("v", "2016050901");

        Set<String> keySet = params.keySet();
        List<String> keysList = new ArrayList<>(keySet);
        Collections.sort(keysList);
        for (Iterator<String> ite = keysList.iterator(); ite.hasNext();) {
            String temp = ite.next();
            System.out.println("key-value: " + temp + "," + params.get(temp));
        }
        System.out.println("==========================");
        Collections.sort(keysList, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o2.compareTo(o1);
            }
        });

        for (Iterator<String> ite = keysList.iterator(); ite.hasNext();) {
            String temp = ite.next();
            System.out.println("key-value: " + temp + "," + params.get(temp));
        }

        System.out.println(DigestUtils.md5Hex("abc z 1q").toUpperCase());
        System.out.println(MD5Utils.getMD5("abc z 1q"));

    }
}

package com.sxk.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

public class JSONTest {
    public static void main(String[] args) {
        JSONObject o1 = new JSONObject();
        JSONObject o2 = new JSONObject();
        JSONObject o3 = new JSONObject();
        JSONObject all = new JSONObject();
        o1.put("tagVal", "1");
        o1.put("update", System.currentTimeMillis() / 1000L);
        o2.put("coc3", o1);
        o3.put("0", o1);
        all.putAll(o2);
        all.putAll(o3);
        System.out.println(all.toJSONString());
        //避免JSON对象循环引用出现的"$ref"
        System.out.println(JSON.toJSONString(all, SerializerFeature.DisableCircularReferenceDetect));
    }
}

package com.gewara.util;

import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

public class JsonUtils {
    public static String getJsonValueByKey(String json, String key) {
        return JSON.parseObject(json).getString(key);
    }

    public static String writeObjectToJson(Object obj) {
        return JSON.toJSONString(obj);
    }

    public static Map<String, Object> readJsonToMap(String json) {
        return JSON.parseObject(json, new TypeReference<Map<String, Object>>() {
        });
    }
}

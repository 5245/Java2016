package com.sxk.common.utils;

import java.util.Set;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.sxk.common.utils.http.HttpAsyncUtil;
import com.sxk.common.utils.http.HttpUtils;

public class CityTest {
    public static void main(String[] args) {
        String cityUrl = "http://wx.wepiao.com/data/v5/city.json";
        String result = HttpUtils.get(cityUrl);
        int begin = result.indexOf(",");
        int end = result.lastIndexOf(")");
        String cityStr = result.substring(begin + 1, end);
        JSONObject citys = JSONObject.parseObject(cityStr);
        JSONArray hotCitys = citys.getJSONArray("hot");
        JSONObject nhCitys = citys.getJSONObject("list");
        System.out.println(hotCitys.size());
        System.out.println(nhCitys.size());

        for (int i = 0; i < hotCitys.size(); i++) {
            JSONObject obj = hotCitys.getJSONObject(i);
            String cname = obj.getString("name");
            System.out.println(cname);
        }

        Set<String> keys = nhCitys.keySet();
        for (String key : keys) {
            JSONObject obj = nhCitys.getJSONObject(key);
            String cname = obj.getString("name");
            System.out.println(cname);
        }

    }
}

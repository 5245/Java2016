package com.dangdang.dao.impl;

import java.util.List;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dangdang.common.ListUtiltiy;
import com.dangdang.dao.WechatCityDao;
import com.dangdang.model.WechatCity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:application.xml" })
public class WechatCity2JSON {

    @Autowired
    private WechatCityDao wechatCityDao;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testUpdateCity() {
        List<WechatCity> wCitys = wechatCityDao.findAll();
        Map<String, List<WechatCity>> pcodeMap = ListUtiltiy.groupToList(wCitys, "parentCode");

        JSONArray all = new JSONArray();
        for (WechatCity wcity : wCitys) {
            if (wcity.getParentCode() == null) {
                JSONObject obj_1 = new JSONObject();
                JSONArray array_1 = new JSONArray();
                List<WechatCity> cityList = pcodeMap.get(wcity.getNationalCode());
                for (WechatCity ccity : cityList) {
                    JSONObject obj_2 = new JSONObject();
                    JSONArray array_3 = new JSONArray();
                    List<WechatCity> dcityList = pcodeMap.get(ccity.getNationalCode());
                    for (WechatCity dcity : dcityList) {
                        JSONObject obj_3 = new JSONObject();
                        obj_3.put("s", dcity.getName());
                        //obj_3.put(dcity.getNationalCode(), dcity.getName());
                        array_3.add(obj_3);
                    }
                    obj_2.put("n", ccity.getName());
                    obj_2.put("a", array_3);
                    array_1.add(obj_2);
                }
                obj_1.put("p", wcity.getName());
                obj_1.put("c", array_1);
                all.add(obj_1);
            }
        }
        JSONObject obj = new JSONObject();
        obj.put("citylist", all);
        System.out.println(obj.toJSONString());
    }
}

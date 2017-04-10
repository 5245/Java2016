package com.dangdang.dao.impl;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONObject;
import com.dangdang.common.ListUtiltiy;
import com.dangdang.dao.CityDao;
import com.dangdang.dao.DistrictDao;
import com.dangdang.dao.ProvinceDao;
import com.dangdang.dao.WechatCityDao;
import com.dangdang.model.City;
import com.dangdang.model.District;
import com.dangdang.model.Province;
import com.dangdang.model.WechatCity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:application.xml" })
public class WechatTest4GD {

    @Autowired
    private WechatCityDao wechatCityDao;
    @Autowired
    private ProvinceDao   provinceDao;
    @Autowired
    private CityDao       cityDao;
    @Autowired
    private DistrictDao   districtDao;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testUpdate() {

        List<District> districts = districtDao.findAll();

        Map<String, District> districtMap = ListUtiltiy.groupToObject(districts, "districtName");
        for (District d : districts) {
            if (d.getDistrictId() < 3618) {
                continue;
            }
            districtMap.put(d.getDistrictName().trim(), d);
        }

        InputStream is = this.getClass().getClassLoader().getResourceAsStream("text.txt");
        BufferedReader br = null;
        int id = 4500;
        try {
            br = new BufferedReader(new InputStreamReader(is));
            String str = null;
            while (null != br && (str = br.readLine()) != null) {
                if (null != str && str.length() > 0) {
                    JSONObject obj = JSONObject.parseObject(str);
                    String districtName = obj.getString("countryName").trim();
                    String cityName = obj.getString("cityName").trim();
                    String parentCode = null;
                    if (cityName.equals("东莞市")) {
                        parentCode = "C441950";
                    } else if (cityName.equals("中山市")) {
                        parentCode = "C442050";
                    } else {
                        continue;
                    }
                    if (!districtMap.containsKey(districtName)) {
                        continue;
                    }
                    District d = districtMap.get(districtName);
                    WechatCity dcity = new WechatCity(obj.getString("nationalCode"), 3, districtName, parentCode, null, 0, 0, d.getDistrictId(), null);
                    dcity.setId(id);
                    wechatCityDao.save(dcity);
                    id++;
                    System.out.println(dcity.toString());
                }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                if (null != br) {
                    br.close();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    //@Test
    public void testCompare() {

        List<Object> noList = new ArrayList<>();
        List<Integer> districtList = new ArrayList<>();

        List<WechatCity> wCitys = wechatCityDao.findAll();
        List<Province> provinces = provinceDao.findAll();
        List<City> citys = cityDao.findAll();
        List<District> districts = districtDao.findAll();

        Map<Integer, WechatCity> pwcMap = ListUtiltiy.groupToObject(wCitys, "provinceId");
        Map<Integer, WechatCity> cwcMap = ListUtiltiy.groupToObject(wCitys, "cityId");
        Map<Integer, WechatCity> dwcMap = ListUtiltiy.groupToObject(wCitys, "districtId");
        Map<String, WechatCity> dwcStrMap = ListUtiltiy.groupToObject(wCitys, "districtIdOther");

        Map<Integer, District> districtMap = ListUtiltiy.groupToObject(districts, "districtId");

        for (Province p : provinces) {
            WechatCity wcCity = pwcMap.get((int) p.getProvinceId());
            if (null == wcCity) {
                noList.add(p);
            }
        }
        for (City c : citys) {
            WechatCity wcCity = cwcMap.get((int) c.getCityId());
            if (null == wcCity) {
                noList.add(c);
            }
        }
        Set<String> keys = dwcStrMap.keySet();
        for (String key : keys) {
            if (null != key) {
                String[] districtIds = key.split(",");
                for (int i = 0; i < districtIds.length; i++) {
                    District district = districtMap.get(Integer.parseInt(districtIds[i]));
                    if (null != district) {
                        dwcMap.put(Integer.parseInt(districtIds[i]), new WechatCity(Integer.parseInt(districtIds[i])));
                    }
                }
            }
        }

        for (District d : districts) {
            //if (!d.getDistrictName().equals("其他")) {
            WechatCity wcCity = dwcMap.get((int) d.getDistrictId());
            if (null == wcCity) {
                noList.add(d);
            }
            //}
        }

        for (Object o : noList) {
            System.out.println(o.toString());
        }
        System.out.println(noList.size());
        System.out.println(dwcMap.size());

        /*for(WechatCity city:wCitys){
            System.out.println(PinyinUtils.toPinyin(city.getName()));
        }*/

    }

}

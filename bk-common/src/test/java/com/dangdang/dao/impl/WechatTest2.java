package com.dangdang.dao.impl;

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
public class WechatTest2 {

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

    /* @Test
     public void testUpdate() {

         List<WechatCity> wCitys = wechatCityDao.findAll();
         List<Province> provinces = provinceDao.findAll();
         List<City> citys = cityDao.findAll();
         List<District> districts = districtDao.findAll();

         Map<String, Province> provinceMap = ListUtiltiy.groupToObject(provinces, "provinceName");
         for (Province p : provinces) {
             provinceMap.put(p.getProvinceName().substring(0, p.getProvinceName().length() - 1), p);
         }
         Map<String, City> cityMap = ListUtiltiy.groupToObject(citys, "cityName");
         for (City c : citys) {
             cityMap.put(c.getCityName().substring(0, c.getCityName().length() - 1), c);
         }
         Map<String, District> districtMap = ListUtiltiy.groupToObject(districts, "districtName");
         for (District d : districts) {
             districtMap.put(d.getDistrictName().substring(0, d.getDistrictName().length() - 1), d);
         }

         for (WechatCity wcity : wCitys) {
             int type = wcity.getType();
             String name = wcity.getName();
             String sname = name.substring(0, name.length() - 1);
             if (type == 1) {
                 Province p = provinceMap.get(name);
                 if (null == p) {
                     p = provinceMap.get(sname);
                 }
                 if (null != p) {
                     wcity.setProvinceId((int) p.getProvinceId());
                 }
             } else if (type == 2) {
                 City c = cityMap.get(name);
                 if (null == c) {
                     c = cityMap.get(sname);
                 }
                 if (null != c) {
                     wcity.setCityId((int) c.getCityId());
                     wcity.setPostCode(Integer.parseInt(c.getZipCode()));
                 }
             } else if (type == 3) {
                 District d = districtMap.get(name);
                 if (null == d) {
                     d = districtMap.get(sname);
                 }
                 if (null != d) {
                     wcity.setDistrictId((int) d.getDistrictId());
                 }
             }
             wechatCityDao.update(wcity);
             System.out.println(wcity.toString());
         }

     }*/

    @Test
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

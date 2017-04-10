package com.dangdang.dao.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
public class WechatTest4 {

    private static String PROVINCE_NAME = "海外";
    private static String PREFIX        = "F";

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

    //以District修复wechat_city的districtId
    @Test
    public void testUpdate() {

        List<Object> noList = new ArrayList<>();

        //List<WechatCity> wCitys = wechatCityDao.findAll();
        List<Province> provinces = provinceDao.findAll();
        List<City> citys = cityDao.findAll();
        List<District> districts = districtDao.findAll();

        Map<Long, List<City>> cityMap = ListUtiltiy.groupToList(citys, "provinceId");
        Map<Integer, List<District>> dListMap = ListUtiltiy.groupToList(districts, "cityId");

        int i = 0;
        for (Province p : provinces) {
            if (p.getProvinceName().equals(PROVINCE_NAME)) {
                WechatCity wcity1 = new WechatCity();
                String nationalCode1 = PREFIX + p.getProvinceId();
                wcity1.setNationalCode(nationalCode1);
                wcity1.setCityType(1);
                wcity1.setName(PROVINCE_NAME);
                wcity1.setParentCode(null);
                wcity1.setProvinceId((int) p.getProvinceId());
                cityDao.save(wcity1);
                System.out.println(wcity1.toString());
                noList.add(wcity1);
                List<City> clists = cityMap.get(p.getProvinceId());
                for (City c : clists) {
                    WechatCity wcity2 = new WechatCity();
                    String nationalCode2 = nationalCode1 + c.getCityId();
                    wcity2.setNationalCode(nationalCode2);
                    wcity2.setCityType(2);
                    wcity2.setName(c.getCityName());
                    wcity2.setParentCode(nationalCode1);
                    wcity2.setCityId((int) c.getCityId());
                    cityDao.save(wcity2);
                    System.out.println(wcity2.toString());
                    noList.add(wcity2);
                    List<District> dlists = dListMap.get((int) c.getCityId());
                    for (District d : dlists) {
                        WechatCity wcity3 = new WechatCity();
                        String nationalCode3 = nationalCode2 + d.getDistrictId();
                        wcity3.setNationalCode(nationalCode3);
                        wcity3.setCityType(3);
                        wcity3.setName(d.getDistrictName());
                        wcity3.setParentCode(nationalCode2);
                        wcity3.setDistrictId(d.getDistrictId());
                        cityDao.save(wcity3);
                        System.out.println(wcity3.toString());
                        noList.add(wcity3);
                        i++;
                    }
                }
            }
        }

//        for (Object c : noList) {
//            System.out.println(c.toString());
//        }
        System.out.println("更新:" + i);
        System.out.println("更新:" + noList.size());
    }

}

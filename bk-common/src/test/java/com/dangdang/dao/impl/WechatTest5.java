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
public class WechatTest5 {

    //construct other
    private static String PREFIX = "CO";

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

    //将其他和市辖区对应上
    //@Test
    public void testUpdateOther() {

        List<Object> noList = new ArrayList<>();
        List<Integer> districtList = new ArrayList<>();

        List<WechatCity> wCitys = wechatCityDao.findAll();
        List<Province> provinces = provinceDao.findAll();
        List<City> citys = cityDao.findAll();
        List<District> districts = districtDao.findAll();

        Map<String, WechatCity> ncMap = ListUtiltiy.groupToObject(wCitys, "nationalCode");
        Map<String, List<WechatCity>> pncMap = ListUtiltiy.groupToList(wCitys, "parentCode");

        Map<Integer, WechatCity> cwcMap = ListUtiltiy.groupToObject(wCitys, "cityId");
        Map<Integer, WechatCity> dwcMap = ListUtiltiy.groupToObject(wCitys, "districtId");

        for (District d : districts) {
            if (d.getDistrictName().equals("其他")) {
                WechatCity w = dwcMap.get(d.getDistrictId());
                if (null != w) {
                    continue;
                }
                WechatCity wcCity = cwcMap.get(d.getCityId());
                if (null != wcCity) {
                    List<WechatCity> wcList = pncMap.get(wcCity.getNationalCode());
                    for (WechatCity wc : wcList) {
                        if (wc.getName().equals("市辖区")) {
                            wc.setDistrictId(d.getDistrictId());
                            //wechatCityDao.update(wc);
                            noList.add(wc);
                        }
                    }
                }
            }
        }

        for (Object o : noList) {
            System.out.println(o.toString());
        }
        System.out.println(noList.size());

    }

    //将其他和市辖区对应不上的新增国标
    @Test
    public void testUpdateOther2() {
        int id = 4018;
        List<Object> noList = new ArrayList<>();
        List<Integer> districtList = new ArrayList<>();

        List<WechatCity> wCitys = wechatCityDao.findAll();
        List<District> districts = districtDao.findAll();

        Map<String, WechatCity> ncMap = ListUtiltiy.groupToObject(wCitys, "nationalCode");

        Map<Integer, WechatCity> cwcMap = ListUtiltiy.groupToObject(wCitys, "cityId");
        Map<Integer, WechatCity> dwcMap = ListUtiltiy.groupToObject(wCitys, "districtId");

        for (District d : districts) {
            if (d.getDistrictName().equals("其他")) {
                WechatCity w = dwcMap.get(d.getDistrictId());
                if (null != w) {
                    continue;
                }
                WechatCity cwcCity = cwcMap.get(d.getCityId());
                if (null != cwcCity) {
                    WechatCity pwcCity = ncMap.get(cwcCity.getParentCode());
                    if (null != pwcCity) {
                        WechatCity newOtherCity = new WechatCity(id);
                        String nationalCode = PREFIX + pwcCity.getProvinceId() + cwcCity.getCityId() + d.getDistrictId();
                        newOtherCity.setNationalCode(nationalCode);
                        newOtherCity.setName(d.getDistrictName());
                        newOtherCity.setParentCode(cwcCity.getNationalCode());
                        newOtherCity.setCityType(3);
                        newOtherCity.setDistrictId(d.getDistrictId());
                        wechatCityDao.save(newOtherCity);
                        noList.add(newOtherCity);
                        id++;
                    }

                }
            }
        }

        for (Object o : noList) {
            System.out.println(o.toString());
        }
        System.out.println(noList.size());

    }
}

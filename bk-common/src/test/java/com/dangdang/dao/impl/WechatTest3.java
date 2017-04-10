package com.dangdang.dao.impl;

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
import com.dangdang.model.WechatCity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:application.xml" })
public class WechatTest3 {

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

        //List<Object> noList = new ArrayList<>();

        List<WechatCity> wCitys = wechatCityDao.findAll();
        //List<Province> provinces = provinceDao.findAll();
        List<City> citys = cityDao.findAll();
        List<District> districts = districtDao.findAll();

        Map<Integer, WechatCity> codeMap = ListUtiltiy.groupToObject(wCitys, "code");
        Map<Long, City> cityMap = ListUtiltiy.groupToObject(citys, "cityId");

        Map<String, List<WechatCity>> wListMap = ListUtiltiy.groupToList(wCitys, "name");

        Map<String, List<District>> dListMap = ListUtiltiy.groupToList(districts, "districtName");

        int i = 0;
        Set<String> keys = dListMap.keySet();
        for (String key : keys) {
            List<District> lists = dListMap.get(key);
            //其中演出有"其他"的很多
            if (lists.size() > 1 && lists.size() < 10) {
                for (District d : lists) {
                    List<WechatCity> wls = wListMap.get(d.getDistrictName());
                    if (null != wls && wls.size() > 0) {
                        for (WechatCity wcity : wls) {
                            WechatCity pwcity = codeMap.get(wcity.getParentCode());
                            if (pwcity.getCityId() == 0) {
                                WechatCity ppwcity = codeMap.get(pwcity.getParentCode());
                                System.out.println(cityMap.containsKey(new Long(d.getCityId())));
                                City city = cityMap.get(new Long(d.getCityId()));
                                if (null != ppwcity && null != city && ppwcity.getProvinceId() == city.getProvinceId().intValue()) {
                                    i++;
                                    wcity.setDistrictId(d.getDistrictId());
                                    wechatCityDao.update(wcity);
                                    System.out.println(wcity.toString());
                                }
                            } else if (pwcity.getCityId() == d.getCityId()) {
                                i++;
                                wcity.setDistrictId(d.getDistrictId());
                                wechatCityDao.update(wcity);
                                System.out.println(wcity.toString());
                            }
                        }
                    }
                }
            }
        }
        System.out.println("更新:" + i);
    }

}

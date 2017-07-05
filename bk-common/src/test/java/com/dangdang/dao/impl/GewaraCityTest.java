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
import com.dangdang.dao.GwrCityDao;
import com.dangdang.dao.GwrCountyDao;
import com.dangdang.dao.GwrProvinceDao;
import com.dangdang.dao.ProvinceDao;
import com.dangdang.dao.WechatCityDao;
import com.dangdang.model.GwrCity;
import com.dangdang.model.GwrCounty;
import com.dangdang.model.GwrProvince;
import com.dangdang.model.WechatCity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:application.xml" })
public class GewaraCityTest {

    @Autowired
    private WechatCityDao  wechatCityDao;
    @Autowired
    private ProvinceDao    provinceDao;
    @Autowired
    private CityDao        cityDao;
    @Autowired
    private DistrictDao    districtDao;
    @Autowired
    private GwrProvinceDao gwrProvinceDao;
    @Autowired
    private GwrCityDao     gwrCityDao;
    @Autowired
    private GwrCountyDao   gwrCountyDao;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testCompare() {

        List<Object> noList = new ArrayList<>();
        List<WechatCity> wCitys = wechatCityDao.findAll();

        List<GwrProvince> gwrProvinces = gwrProvinceDao.findAll();
        List<GwrCity> gwrCitys = gwrCityDao.findAll();
        List<GwrCounty> gwrCountys = gwrCountyDao.findAll();

        Map<String, WechatCity> codeMap = ListUtiltiy.groupToObject(wCitys, "nationalCode");
        Map<String, WechatCity> nameMap = ListUtiltiy.groupToObject(wCitys, "name");

        for (GwrProvince gwrProvince : gwrProvinces) {
            WechatCity wechatCity = codeMap.get(gwrProvince.getProvincecode());
            if (null != wechatCity) {
                wechatCity.getName().equals(gwrProvince.getNationname());
            } else {
                noList.add(gwrProvince);
            }
        }

      /**
       * 
        GwrCity[id=5,citycode=331200,cityname=余姚市,cityename=<null>,provincecode=330000]
        GwrCity[id=11,citycode=522400,cityname=毕节地区,cityename=<null>,provincecode=520000]
        GwrCity[id=76,citycode=469034,cityname=陵水黎族自治县,cityename=<null>,provincecode=460000]
        GwrCity[id=77,citycode=469035,cityname=保亭黎族苗族自治县,cityename=<null>,provincecode=460000]
        GwrCity[id=78,citycode=469036,cityname=琼中黎族苗族自治县,cityename=<null>,provincecode=460000]
        GwrCity[id=79,citycode=469037,cityname=西沙群岛,cityename=<null>,provincecode=460000]
        GwrCity[id=80,citycode=469038,cityname=南沙群岛,cityename=<null>,provincecode=460000]
        GwrCity[id=81,citycode=469039,cityname=中沙群岛的岛礁及其海域,cityename=<null>,provincecode=460000]
        GwrCity[id=107,citycode=522200,cityname=铜仁地区,cityename=<null>,provincecode=520000]
        GwrCity[id=177,citycode=469031,cityname=昌江黎族自治县,cityename=<null>,provincecode=460000]
        GwrCity[id=178,citycode=469033,cityname=乐东黎族自治县,cityename=<null>,provincecode=460000]
        GwrCity[id=217,citycode=632100,cityname=海东地区,cityename=<null>,provincecode=630000]
        GwrCity[id=312,citycode=341400,cityname=巢湖市,cityename=<null>,provincecode=340000]
        GwrCity[id=374,citycode=321088,cityname=江都市,cityename=<null>,provincecode=320000]
        GwrCity[id=385,citycode=531000,cityname=普洱市,cityename=<null>,provincecode=530000]
        GwrCity[id=386,citycode=410881,cityname=济源市,cityename=<null>,provincecode=410000]
        *
        **/
        
//        for (GwrCity gwrCity : gwrCitys) {
//            WechatCity wechatCity = codeMap.get(gwrCity.getCitycode());
//            if (null != wechatCity) {
//                wechatCity.getName().equals(gwrCity.getCityname());
//            } else {
//                noList.add(gwrCity);
//            }
//        }

//        for (GwrCounty gwrCounty : gwrCountys) {
//            WechatCity wechatCity = codeMap.get(gwrCounty.getCountycode());
//            if (null != wechatCity) {
//                wechatCity.getName().equals(gwrCounty.getCountyname());
//            } else {
//                noList.add(gwrCounty);
//            }
//        }
        
        for (GwrCounty gwrCounty : gwrCountys) {
            WechatCity wechatCity = nameMap.get(gwrCounty.getCountyname());
            if (null != wechatCity) {
                
            } else {
                noList.add(gwrCounty);
            }
        }

        for (Object o : noList) {
            System.out.println(o.toString());
        }
        System.out.println(noList.size());

        /*for(WechatCity city:wCitys){
            System.out.println(PinyinUtils.toPinyin(city.getName()));
        }*/

    }
}

package com.dangdang.dao.impl;

import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.parser.Feature;
import com.dangdang.dao.CityDao;
import com.dangdang.dao.DistrictDao;
import com.dangdang.dao.ProvinceDao;
import com.dangdang.dao.QqCityDao;
import com.dangdang.dao.TencentQqAddressDao;
import com.dangdang.dao.WechatCityDao;
import com.dangdang.model.QqCity;
import com.dangdang.model.TencentQqAddress;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:application.xml" })
public class QqCityTest {

    private static final String[] cityArrays = new String[] { "北京", "天津", "上海", "重庆" };

    @Autowired
    private QqCityDao             qqCityDao;
    @Autowired
    private TencentQqAddressDao   tqaDao;
    @Autowired
    private WechatCityDao         wechatCityDao;
    @Autowired
    private ProvinceDao           provinceDao;
    @Autowired
    private CityDao               cityDao;
    @Autowired
    private DistrictDao           districtDao;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testUpdateCity() {

        /* 
         "120000": [
                    "天津",
                    {
                        "120101": "和平区",
                        "120102": "河东区",
                    }
                ],
               
        */

        List<TencentQqAddress> tqas = (List<TencentQqAddress>) tqaDao.findAll();
        TencentQqAddress tqa = tqas.get(0);
        JSONObject content = JSONObject.parseObject(tqa.getContent(), Feature.OrderedField);
        Set<String> pkeys = content.keySet();
        for (String pkey : pkeys) {
            JSONArray provinceArr = content.getJSONArray(pkey);
            String provinceName = provinceArr.getString(0);
            JSONObject citys = provinceArr.getJSONObject(1);
            if (!ArrayUtils.contains(cityArrays, provinceName)) {
                continue;
            }
            QqCity pcity = new QqCity(pkey, 1, provinceName, null, null, 0, 0, 0, null);
            qqCityDao.save(pcity);
            System.out.println(pcity.toString());
            Set<String> ckeys = citys.keySet();
            int i = 0;
            for (String ckey : ckeys) {
                Object obj = citys.get(ckey);
                if (obj instanceof String) {
                    i++;
                    String cityCode = pkey.replace("0000", "0100");
                    if (i == 1) {
                        String cityName = provinceName;
                        QqCity ccity = new QqCity(cityCode, 2, cityName, pkey, null, 0, 0, 0, null);
                        qqCityDao.save(ccity);
                        System.out.println(ccity.toString());
                    }

                    String districtName = (String) obj;
                    QqCity dcity = new QqCity(ckey, 3, districtName, cityCode, null, 0, 0, 0, null);
                    qqCityDao.save(dcity);

                }
            }
        }

    }

    @Test
    public void testUpdateDistrict() {

        /* 
         "120000": [
                    "天津",
                    {
                        "120101": "和平区",
                        "120102": "河东区",
                    }
                ],
                "130000": [
                    "河北省",
                    {
                        "130100": [
                            "石家庄市",
                            {
                                "130102": "长安区",
                                "130103": "桥东区",
                            }
                        ],
                    }
        */

        List<TencentQqAddress> tqas = (List<TencentQqAddress>) tqaDao.findAll();
        TencentQqAddress tqa = tqas.get(0);
        JSONObject content = JSONObject.parseObject(tqa.getContent(), Feature.OrderedField);
        Set<String> pkeys = content.keySet();
        for (String pkey : pkeys) {
            JSONArray provinceArr = content.getJSONArray(pkey);
            String provinceName = provinceArr.getString(0);
            JSONObject citys = provinceArr.getJSONObject(1);

            if (ArrayUtils.contains(cityArrays, provinceName)) {
                continue;
            }
            QqCity pcity = new QqCity(pkey, 1, provinceName, null, null, 0, 0, 0, null);
            qqCityDao.save(pcity);
            System.out.println(pcity.toString());
            Set<String> ckeys = citys.keySet();
            int i = 0;
            for (String ckey : ckeys) {

                Object obj = citys.get(ckey);
                String cityCode = null;
                String cityName = null;
                JSONObject districts = null;
                if (obj instanceof String) {
                    //直接挂在省得特殊处理，中山，东莞，石河子
                    /*i++;
                    if (i == 1) {
                        cityCode = pkey.replace("0000", "0100");
                        cityName = provinceName;
                        QqCity ccity = new QqCity(cityCode, 2, cityName, pkey, null, 0, 0, 0, null);
                        qqCityDao.save(ccity);
                        System.out.println(ccity.toString());
                    }

                    String districtName = (String) obj;
                    QqCity dcity = new QqCity(ckey, 3, districtName, pkey, null, 0, 0, 0, null);
                    qqCityDao.save(dcity);*/

                } else if (obj instanceof JSONArray) {
                    JSONArray cityArr = (JSONArray) obj;
                    cityCode = ckey;
                    cityName = cityArr.getString(0);
                    districts = cityArr.getJSONObject(1);

                    QqCity ccity = new QqCity(cityCode, 2, cityName, pkey, null, 0, 0, 0, null);
                    qqCityDao.save(ccity);
                    System.out.println(ccity.toString());

                    Set<String> dkeys = districts.keySet();
                    for (String dkey : dkeys) {
                        String districtName = districts.getString(dkey);
                        QqCity dcity = new QqCity(dkey, 3, districtName, cityCode, null, 0, 0, 0, null);
                        qqCityDao.save(dcity);
                        System.out.println(dcity.toString());
                    }
                }

            }
        }

    }
}

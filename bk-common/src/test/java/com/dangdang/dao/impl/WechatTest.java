package com.dangdang.dao.impl;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.dangdang.common.HttpAsyncUtil;
import com.dangdang.dao.WechatCityDao;
import com.dangdang.model.WechatCity;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:application.xml" })
public class WechatTest {

    private static final String SEPARATOR  = "&nbsp;";
    private static final String SEPARATOR2 = ";";
    @Autowired
    private WechatCityDao       cityDao;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSave() {
        //15年
        //String url = "http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/201608/t20160809_1386477.html";
        //13年
        String url = "http://www.stats.gov.cn/tjsj/tjbz/xzqhdm/201401/t20140116_501070.html";
        String res = HttpAsyncUtil.get(url);
        Document doc = Jsoup.parse(res);
        Elements A = doc.getElementsByClass("TRS_Editor");
        Elements all = A.first().getElementsByClass("MsoNormal");

        Iterator<Element> it = all.iterator();
        int i = 0;
        Integer provinceCode = 0;
        Integer cityCode = 0;
        int previous = 0;
        while (it.hasNext()) {
            i++;
            Element el = it.next();
            String str = el.html();
            int sindex = str.indexOf(SEPARATOR);
            int eindex = str.lastIndexOf(SEPARATOR2) + 1;
            int length = str.length();
            Integer code = Integer.parseInt(str.substring(0, sindex));
            String name = str.substring(eindex, length);
            name = StringUtils.deleteWhitespace(name);
            String[] array = str.split(SEPARATOR);
            int type = 0;
            int tlength = array.length;

            WechatCity city = new WechatCity();
            if (tlength == 4) {
                type = 1; //省
                if (tlength != previous) {
                    provinceCode = code;
                }
                city.setParentCode(null);
            } else if (tlength == 6) {
                type = 2; //市
                if (tlength != previous) {
                    cityCode = code;
                }
                city.setParentCode(provinceCode.toString());
            } else if (tlength == 8) {
                type = 3; //区
                city.setParentCode(cityCode.toString());
            }
            city.setName(name);
            city.setNationalCode(code.toString());
            city.setCityType(type);
            cityDao.save(city);
            System.out.println(city.toString());

            if (tlength != previous) {
                previous = tlength;
            }
        }

    }
}

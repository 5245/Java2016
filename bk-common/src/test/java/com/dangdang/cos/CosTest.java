package com.dangdang.cos;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.dangdang.common.DateUtil;
import com.dangdang.common.HttpUtil;
import com.dangdang.dao.QyerDao;
import com.dangdang.model.Qyer;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(value = { "classpath:application.xml" })
public class CosTest {

    private static final Logger logger             = LoggerFactory.getLogger("db_info");
    private static final String DATE2PATH_PATTERN  = "/yyyy/MM/dd/HH/";
    private static final String DATE2PATH_PATTERN2 = "yyyyMMddHH";
    private static final String DATE2PATH_PATTERN3 = "yyyy-MM-dd HH:mm";

    @Autowired
    private QyerDao             qyerDao;

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testSave() {
        //System.out.println(WCosUtils.getInstance().readFolder("/head/2017/07/20/13", null));
        //冰岛
        //String url = "http://m.qyer.com/place/iceland/photo?ajax=1&_=1500614425755&page=";
        //巴黎
        //String url = "http://m.qyer.com/place/paris/photo?ajax=1&_=1500614425755&page=";
        //威尼斯
        //String url = "http://m.qyer.com/place/venice/photo?ajax=1&_=1500614425755&page=";
        //意大利
        //String url = "http://m.qyer.com/place/italy/photo?ajax=1&_=1500614425755&page=";
        //新西兰
        String url = "http://m.qyer.com/place/new-zealand/photo?ajax=1&_=1500614425755&page=";
        //中国
        //String url = "http://m.qyer.com/place/china/photo?ajax=1&_=1500614425755&page=";
        //泰国
        //String url = "http://m.qyer.com/place/thailand/photo?ajax=1&_=1500614425755&page=";

        Map<String, String> headers = new HashMap<>();
        headers.put("User-Agent",
                "Mozilla/5.0 (Linux; Android 6.0; Nexus 5 Build/MRA58N) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/57.0.2987.133 Mobile Safari/537.36");

        JSONArray list = null;
        JSONObject photo = null;
        Qyer qyer = null;
        String startTime = "2016-03-20 ";
        Map<Integer, Date> dateMap = new HashMap<>();
        String value = null;
        for (int i = 0; i < 24; i++) {
            if (i < 10) {
                value = startTime + "0" + i + ":00:00";
            } else {
                value = startTime + i + ":00:00";
            }
            Date d = DateUtil.getDatebyStr(value, DateUtil.DEFAULT_DATE_PATTERN);
            dateMap.put(i, d);
        }
        String photoPrefix = "jpg_";
        for (int i = 1; i < 24; i++) {
            String response = HttpUtil.getInstance().get(url + i, null, headers);
            JSONObject res = JSONObject.parseObject(response);
            if (0 == res.getInteger("error_code")) {
                logger.info("qyer response:{}", response);
                if (res.containsKey("data")) {
                    list = res.getJSONObject("data").getJSONArray("list");
                    for (int j = 0; j < list.size(); j++) {
                        photo = list.getJSONObject(j);
                        Date now = dateMap.get(i);
                        if (null == now) {
                            continue;
                        }
                        String src = photo.getString("src").replaceAll("https://", "http://").replaceAll("index/670", "index");
                        String photoUrl = null;
                        if (j < 10) {
                            photoUrl = photoPrefix + DateUtil.format(now, DATE2PATH_PATTERN2) + "_00" + j;
                        } else if (j < 100) {
                            photoUrl = photoPrefix + DateUtil.format(now, DATE2PATH_PATTERN2) + "_0" + j;
                        } else {
                            photoUrl = photoPrefix + DateUtil.format(now, DATE2PATH_PATTERN2) + "_" + j;
                        }
                        qyer = WCosUtils.getInstance().changePhotoUrl2Wepiao(src, photoUrl, now);
                        if (null != qyer && null != qyer.getUrl()) {
                            qyer.setYear(now.getYear());
                            qyer.setMonth(now.getMonth());
                            qyer.setDay(now.getDate());
                            qyer.setHour(now.getHours());
                            qyer.setFileDesc(photo.getString("desc"));
                            if (photo.containsKey("ctime")) {
                                qyer.setCreateTime(DateUtil.getDatebyStr(photo.getString("ctime"), DATE2PATH_PATTERN3));
                            }
                            qyer.setFileName(photoUrl);
                            qyer.setFolder(DateUtil.format(now, DATE2PATH_PATTERN2));
                            qyerDao.save(qyer);
                            System.out.println(qyer.toString());
                        }
                    }
                }
            } else {
                System.out.println(response);
            }
        }
        System.out.println("success");

    }
}

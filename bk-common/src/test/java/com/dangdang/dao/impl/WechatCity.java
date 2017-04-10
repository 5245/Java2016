package com.dangdang.dao.impl;

import java.util.Iterator;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.dangdang.common.HttpAsyncUtil;

public class WechatCity {

    private static final String SEPARATOR  = "&nbsp;";
    private static final String SEPARATOR2 = ";";

    public static void main(String[] args) {
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
            System.out.println(name);
            System.out.println(code);
        }
    }

}

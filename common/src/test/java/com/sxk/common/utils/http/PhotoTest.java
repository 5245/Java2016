package com.sxk.common.utils.http;

import org.apache.http.Header;
import org.apache.http.HttpResponse;

public class PhotoTest {
    public static final String errorHeaderName  = "X-ErrNo";
    public static final int    errorHeaderValue = -6101;

    public static void main(String[] args) {
        //String url = "https://wx.qlogo.cn/mmopen/n9UUU8c5lXbdIq77r4qtTeoRgOibbasKC0PFJ3JUr9xmBnEIhQa1EB6sSmA1nia6pUicXKgwLyoE0icWNWJa3HY2qA/0";
        String url = "https://wx.qlogo.cn/mmopen/QSCknJPJASPuvIPIqTNqIjeOUEsBCNwIlSMe4Dh0ibjHnTvXKabW1feBibQH84bJ3Nm5OUZ46e6l7NbvDDygehHLdMVMtnwhRd/0";
        HttpResponse res = HttpUtils.get2Response(url, null, null, null, 0);
        if (res.containsHeader(errorHeaderName)) {
            Header[] headers = res.getHeaders("X-ErrNo");
            if (errorHeaderValue == Integer.parseInt(headers[0].getValue())) {
                System.out.println(headers[0].getValue());
            }
        }

    }
}

package com.sxk.test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.ParseException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.wepiao.db.enumeration.Constants;

/**
 * @description  http pool util 
 * @author sxk
 * @date 2016年7月6日
 *
 */
public class HttpUtil {

    private final Logger                       logger                 = LoggerFactory.getLogger(HttpUtil.class);
    private final Logger                       logUserInfo            = LoggerFactory.getLogger(Constants.LOG_USER_INFO);
    private final String                       logBody                = "HTTP Body:{}";

    private final String                       DEFAULT_CHARSET        = "UTF-8";

    private final String                       URL_PARAM_CONNECT_FLAG = "&";

    // http thread manager
    private PoolingHttpClientConnectionManager connectionManager      = null;
    private CloseableHttpClient                httpClient;

    private int                                connectionTimeOut      = 60000;
    private int                                socketTimeOut          = 60000;
    private int                                connReqTimeout         = 60000;
    private int                                maxTotal               = 200;
    private int                                defaultMaxPerRoute     = 50;

    private static HttpUtil                    instance               = null;

    private HttpUtil() {
        try {
            connectionManager = new PoolingHttpClientConnectionManager();

            connectionManager.setMaxTotal(maxTotal);
            connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);

            RequestConfig.Builder rcBuilder = RequestConfig.custom();
            rcBuilder.setConnectTimeout(connectionTimeOut);
            rcBuilder.setSocketTimeout(socketTimeOut);
            rcBuilder.setConnectionRequestTimeout(connReqTimeout);
            RequestConfig requestConfig = rcBuilder.build();
            HttpClientBuilder httpClientBuilder = HttpClients.custom();
            httpClientBuilder.setConnectionManager(connectionManager);
            httpClientBuilder.setDefaultRequestConfig(requestConfig);
            httpClient = httpClientBuilder.build();
        } catch (Exception e) {
            logger.error("httpUtil init fail", e);
        }
    }

    public static HttpUtil getInstance() {
        if (instance == null) {
            synchronized (HttpUtil.class) {
                if (instance == null) {
                    instance = new HttpUtil();
                }
            }
        }
        return instance;
    }

    //url校验
    public boolean verifyUrl(String url) {
        return (null == url || 0 == url.trim().length() || !url.startsWith("http")) ? false : true;
    }

    public String get(String url) {
        return get(url, null, null, null, 0);
    }

    public String get(String url, Map<String, String> params) {
        return get(url, params, null, null, 0);
    }

    public String get(String url, Map<String, String> params, Map<String, String> headers) {
        return get(url, params, headers, null, 0);
    }

    public String get(String url, Map<String, String> params, Map<String, String> headers, String charset, int timeout) {
        HttpResponse httpResponse = get2Response(url, params, headers, charset, 0);
        if (null != httpResponse) {
            try {
                return EntityUtils.toString(httpResponse.getEntity(), null == charset ? DEFAULT_CHARSET : charset);
            } catch (ParseException | IOException e) {
                logger.error("response content parse error", e);
            }
        }
        return null;
    }

    public HttpResponse get2Response(String url, Map<String, String> params, Map<String, String> headers, String charset, int timeout) {
        if (!verifyUrl(url)) {
            return null;
        }
        HttpGet httpGet = null;
        HttpResponse httpResponse;
        try {
            if (params != null && params.size() > 0) {
                StringBuffer paramsUrl = new StringBuffer();
                for (String key : params.keySet()) {
                    if (paramsUrl.length() > 0) {
                        paramsUrl.append(URL_PARAM_CONNECT_FLAG);
                    }
                    paramsUrl.append(key).append("=").append(params.get(key));
                }
                if (url.indexOf('?') == -1) {
                    url += "?" + paramsUrl.toString();
                } else {
                    url += URL_PARAM_CONNECT_FLAG + paramsUrl.toString();
                }
            }
            httpGet = new HttpGet(url);

            if (null != headers && headers.size() > 0) {
                for (String headerName : headers.keySet()) {
                    httpGet.addHeader(headerName, headers.get(headerName));
                }
            }
            RequestConfig.Builder rcBuilder = RequestConfig.custom();
            rcBuilder.setConnectionRequestTimeout(timeout == 0 ? connReqTimeout : timeout);
            RequestConfig requestConfig = rcBuilder.build();
            httpGet.setConfig(requestConfig);

            httpResponse = httpClient.execute(httpGet);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return httpResponse;
            }
        } catch (Exception e) {
            logger.error("请求地址错误:" + url, e);
        }
        return null;
    }

    public String post(String url, Map<String, String> params) {
        return post(url, params, null, null, 0);
    }

    public String post(String url, Map<String, String> params, Map<String, String> headers) {
        return post(url, params, headers, null, 0);
    }

    public String post(String url, Map<String, String> params, Map<String, String> headers, String charset) {
        return post(url, params, headers, charset, 0);
    }

    public String post(String url, Map<String, String> params, Map<String, String> headers, String charset, int timeout) {
        HttpResponse httpResponse = post2Response(url, params, headers, charset, timeout);
        if (null != httpResponse) {
            try {
                return EntityUtils.toString(httpResponse.getEntity(), null == charset ? DEFAULT_CHARSET : charset);
            } catch (ParseException | IOException e) {
                logger.error("response content parse error", e);
            }
        }
        return null;
    }

    private HttpResponse post2Response(String url, Map<String, String> params, Map<String, String> headers, String charset, int timeout) {
        if (!verifyUrl(url)) {
            return null;
        }
        HttpPost httpPost = new HttpPost(url);
        HttpResponse httpResponse;
        try {
            if (params != null && params.size() > 0) {
                List<NameValuePair> paramsList = new ArrayList<NameValuePair>();
                for (String key : params.keySet()) {
                    paramsList.add(new BasicNameValuePair(key, params.get(key)));
                }
                UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(paramsList, DEFAULT_CHARSET);
                httpPost.setEntity(urlEncodedFormEntity);
            }
            if (null != headers && headers.size() > 0) {
                for (String headerName : headers.keySet()) {
                    httpPost.addHeader(headerName, headers.get(headerName));
                }
            }
            RequestConfig.Builder rcBuilder = RequestConfig.custom();
            rcBuilder.setConnectionRequestTimeout(timeout == 0 ? connReqTimeout : timeout);
            RequestConfig requestConfig = rcBuilder.build();
            httpPost.setConfig(requestConfig);

            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return httpResponse;
            }
        } catch (Exception e) {
            logger.error("请求地址错误:" + url, e);
        }
        return null;
    }

    public String post(String url, String params) {
        return post(url, params, null, null, 0);
    }

    public String post(String url, String params, Map<String, String> headers) {
        return post(url, params, headers, null, 0);
    }

    public String post(String url, String params, Map<String, String> headers, String charset) {
        return post(url, params, headers, charset, 0);
    }

    public String post(String url, String params, Map<String, String> headers, String charset, int timeout) {
        HttpResponse httpResponse = post2Response(url, params, headers, charset, timeout);
        if (null != httpResponse) {
            try {
                return EntityUtils.toString(httpResponse.getEntity(), null == charset ? DEFAULT_CHARSET : charset);
            } catch (ParseException | IOException e) {
                logger.error("response content parse error", e);
            }
        }
        return null;
    }

    private HttpResponse post2Response(String url, String params, Map<String, String> headers, String charset, int timeout) {
        if (!verifyUrl(url)) {
            return null;
        }
        HttpPost httpPost = new HttpPost(url);
        HttpResponse httpResponse;
        try {
            if (params != null) {
                StringEntity urlEncodedFormEntity = new StringEntity(params, DEFAULT_CHARSET);
                httpPost.setEntity(urlEncodedFormEntity);
            }
            if (null != headers && headers.size() > 0) {
                for (String headerName : headers.keySet()) {
                    httpPost.addHeader(headerName, headers.get(headerName));
                }
            }
            RequestConfig.Builder rcBuilder = RequestConfig.custom();
            rcBuilder.setConnectionRequestTimeout(timeout == 0 ? connReqTimeout : timeout);

            RequestConfig requestConfig = rcBuilder.build();
            httpPost.setConfig(requestConfig);

            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return httpResponse;
            }
        } catch (Exception e) {
            logger.error("请求地址错误:" + url, e);
        }
        return null;
    }

    public String upload(String url, Map<String, File> files) {
        return upload(url, null, null, files, null, 0);
    }

    public String upload(String url, Map<String, String> params, Map<String, File> files) {
        return upload(url, params, null, files, null, 0);
    }

    public String upload(String url, Map<String, String> params, Map<String, String> headers, Map<String, File> files) {
        return upload(url, params, headers, files, null, 0);
    }

    public String upload(String url, Map<String, String> params, Map<String, String> headers, Map<String, File> files, String charset, int timeout) {
        long startMillis = System.currentTimeMillis();
        String responseBody = null;
        HttpResponse httpResponse = upload2Response(url, params, headers, files, charset, timeout);
        if (null != httpResponse) {
            try {
                responseBody = EntityUtils.toString(httpResponse.getEntity(), charset == null ? DEFAULT_CHARSET : charset);
            } catch (ParseException | IOException e) {
                logger.error("response content parse error", e);
            }
        }
        logMsg(url, params, headers, responseBody, startMillis, "upload");
        return responseBody;
    }

    private HttpResponse upload2Response(String url, Map<String, String> params, Map<String, String> headers, Map<String, File> files,
            String charset, int timeout) {
        if (!verifyUrl(url)) {
            return null;
        }
        HttpPost httpPost = new HttpPost(url);
        HttpResponse httpResponse;
        try {
            charset = charset == null ? DEFAULT_CHARSET : charset;
            MultipartEntityBuilder multipartEntityBuilder = MultipartEntityBuilder.create();
            multipartEntityBuilder.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            multipartEntityBuilder.setCharset(Charset.forName(charset));
            //发送的数据
            if (params != null) {
                for (String key : params.keySet()) {
                    multipartEntityBuilder.addTextBody(key, params.get(key), ContentType.create("text/plain", Charset.forName(charset)));
                }
            }
            //发送的文件
            if (files != null) {
                for (String key : files.keySet()) {
                    multipartEntityBuilder.addBinaryBody(key, files.get(key));
                }
            }
            HttpEntity httpEntity = multipartEntityBuilder.build();
            httpPost.setEntity(httpEntity);

            if (null != headers && headers.size() > 0) {
                for (String headerName : headers.keySet()) {
                    httpPost.addHeader(headerName, headers.get(headerName));
                }
            }
            RequestConfig.Builder rcBuilder = RequestConfig.custom();
            rcBuilder.setConnectionRequestTimeout(timeout == 0 ? connReqTimeout : timeout);
            RequestConfig requestConfig = rcBuilder.build();
            httpPost.setConfig(requestConfig);

            httpResponse = httpClient.execute(httpPost);
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return httpResponse;
            }
        } catch (Exception e) {
            logger.error("请求地址错误:" + url, e);
        }
        return null;
    }

    public File download(String url) {
        return download(url, 0);
    }

    public File download(String url, int timeout) {
        File file = null;
        HttpResponse httpResponse = get2Response(url, null, null, null, timeout);
        if (null != httpResponse) {
            try {
                file = File.createTempFile("file", ".temp");
                FileOutputStream outstream = new FileOutputStream(file);
                HttpEntity entity = httpResponse.getEntity();
                entity.writeTo(outstream);
            } catch (Exception e) {
                if (file != null) {
                    file.delete();
                }
                file = null;
                logger.error("下载文件失败:" + url, e);
            }
        }
        return file;
    }

    private void logMsg(String url, Object params, Map<String, String> headers, String respBody, long startMillis, String method) {
        JSONObject logMsg = new JSONObject();
        logMsg.put("requestUrl", url);
        logMsg.put("method", method);
        logMsg.put("requestParams", params);
        logMsg.put("requestHeaders", headers);
        logMsg.put("requestTime", DateUtils.format(new Date(startMillis)));
        logMsg.put("responseBody", respBody);
        long endMillis = System.currentTimeMillis();
        logMsg.put("runtime", endMillis - startMillis);
        logUserInfo.info(logBody, logMsg);
    }

}

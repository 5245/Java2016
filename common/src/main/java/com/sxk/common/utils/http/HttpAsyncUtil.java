package com.sxk.common.utils.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
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
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @description async http pool util 
 * @author sxk
 * @date 2016年7月6日
 *
 */
public class HttpAsyncUtil {

    private static final Logger                        logger                 = LoggerFactory.getLogger(HttpAsyncUtil.class);

    private static final String                        DEFAULT_CHARSET        = "UTF-8";

    private static final String                        URL_PARAM_CONNECT_FLAG = "&";

    // http thread manager
    private static PoolingNHttpClientConnectionManager connectionManager      = null;

    private static CloseableHttpAsyncClient            httpAsyncClient;
    //连接建立时间，三次握手完成时间，单位ms
    private static int                                 connectionTimeOut      = 1000;
    //数据传输过程中数据包之间间隔的最大时间，单位ms
    private static int                                 socketTimeOut          = 1000;
    //httpclient使用连接池来管理连接，这个时间就是从连接池获取连接的超时时间，单位ms
    private static int                                 connReqTimeout         = 1000;
    private static int                                 maxTotal               = 50;
    private static int                                 defaultMaxPerRoute     = 50;
    private static int                                 ioThreadCount          = Runtime.getRuntime().availableProcessors();
    private static boolean                             isKeepAlive            = true;

    /**
     *默认情况下i/o选择间隔设置为1000毫秒,因此粒度的套接字超时默认是1秒。
     *一个可以减少选择间隔,使i/o选择器线程遍历会话更多的代价更大的CPU利用率。
     *选择间隔 i/o女士选择器线程将有效地运行在一个繁忙的循环。
     * 所以如果设置超时时间小于1s的话，要相对应的设置setSelectInterval()才有效。
     */

    static {
        try {
            IOReactorConfig.Builder iorcBuilder = IOReactorConfig.custom();
            //iorcBuilder.setSelectInterval(1000);
            iorcBuilder.setConnectTimeout(connectionTimeOut);
            iorcBuilder.setSoTimeout(socketTimeOut);
            iorcBuilder.setIoThreadCount(ioThreadCount);
            iorcBuilder.setSoKeepAlive(isKeepAlive);
            IOReactorConfig ioReactorConfig = iorcBuilder.build();
            ConnectingIOReactor ioReactor = new DefaultConnectingIOReactor(ioReactorConfig);

            connectionManager = new PoolingNHttpClientConnectionManager(ioReactor);

            connectionManager.setMaxTotal(maxTotal);
            connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);

            RequestConfig.Builder rcBuilder = RequestConfig.custom();
            rcBuilder.setConnectionRequestTimeout(connReqTimeout);
            RequestConfig requestConfig = rcBuilder.build();
            HttpAsyncClientBuilder httpAsyncClientBuilder = HttpAsyncClients.custom();
            httpAsyncClientBuilder.setConnectionManager(connectionManager);
            httpAsyncClientBuilder.setDefaultRequestConfig(requestConfig);
            httpAsyncClient = httpAsyncClientBuilder.build();
            httpAsyncClient.start();
        } catch (Exception e) {
            logger.error("httpAsyncUtil init fail", e);
        }
    }

    //url校验
    public static boolean verifyUrl(String url) {
        return (null == url || 0 == url.trim().length() || !url.startsWith("http")) ? false : true;
    }

    public static String get(String url) {
        return get(url, null, null, null, 0);
    }

    public static String get(String url, Map<String, String> params) {
        return get(url, params, null, null, 0);
    }

    public static String get(String url, Map<String, String> params, Map<String, String> headers) {
        return get(url, params, headers, null, 0);
    }

    public static String get(String url, Map<String, String> params, Map<String, String> headers, String charset, int timeout) {
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

    private static HttpResponse get2Response(String url, Map<String, String> params, Map<String, String> headers, String charset, int timeout) {
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

            httpResponse = (HttpResponse) httpAsyncClient.execute(httpGet, null).get();
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return httpResponse;
            }
        } catch (Exception e) {
            logger.error("请求地址错误:" + url, e);
        } finally {
            closeHttpRequestBase(httpGet);
        }
        return null;
    }

    public static String post(String url, Map<String, String> params) {
        return post(url, params, null, null, 0);
    }

    public static String post(String url, Map<String, String> params, Map<String, String> headers) {
        return post(url, params, headers, null, 0);
    }

    public static String post(String url, Map<String, String> params, Map<String, String> headers, String charset) {
        return post(url, params, headers, charset, 0);
    }

    public static String post(String url, Map<String, String> params, Map<String, String> headers, String charset, int timeout) {
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

    private static HttpResponse post2Response(String url, Map<String, String> params, Map<String, String> headers, String charset, int timeout) {
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

            httpResponse = (HttpResponse) httpAsyncClient.execute(httpPost, null).get();
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return httpResponse;
            }
        } catch (Exception e) {
            logger.error("请求地址错误:" + url, e);
        } finally {
            closeHttpRequestBase(httpPost);
        }
        return null;
    }

    public static String post(String url, String params) {
        return post(url, params, null, null, 0);
    }

    public static String post(String url, String params, Map<String, String> headers) {
        return post(url, params, headers, null, 0);
    }

    public static String post(String url, String params, Map<String, String> headers, String charset) {
        return post(url, params, headers, charset, 0);
    }

    public static String post(String url, String params, Map<String, String> headers, String charset, int timeout) {
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

    private static HttpResponse post2Response(String url, String params, Map<String, String> headers, String charset, int timeout) {
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

            httpResponse = (HttpResponse) httpAsyncClient.execute(httpPost, null).get();
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return httpResponse;
            }
        } catch (Exception e) {
            logger.error("请求地址错误:" + url, e);
        } finally {
            closeHttpRequestBase(httpPost);
        }
        return null;
    }

    public static String upload(String url, Map<String, File> files) {
        return upload(url, null, null, files, null, 0);
    }

    public static String upload(String url, Map<String, String> params, Map<String, File> files) {
        return upload(url, params, null, files, null, 0);
    }

    public static String upload(String url, Map<String, String> params, Map<String, String> headers, Map<String, File> files) {
        return upload(url, params, headers, files, null, 0);
    }

    public static String upload(String url, Map<String, String> params, Map<String, String> headers, Map<String, File> files, String charset,
            int timeout) {
        HttpResponse httpResponse = upload2Response(url, params, headers, files, charset, timeout);
        if (null != httpResponse) {
            try {
                return EntityUtils.toString(httpResponse.getEntity(), charset == null ? DEFAULT_CHARSET : charset);
            } catch (ParseException | IOException e) {
                logger.error("response content parse error", e);
            }
        }
        return null;
    }

    private static HttpResponse upload2Response(String url, Map<String, String> params, Map<String, String> headers, Map<String, File> files,
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

            httpResponse = (HttpResponse) httpAsyncClient.execute(httpPost, null).get();
            if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
                return httpResponse;
            }
        } catch (Exception e) {
            logger.error("请求地址错误:" + url, e);
        } finally {
            closeHttpRequestBase(httpPost);
        }
        return null;
    }

    public static File download(String url) {
        return download(url, 0);
    }

    public static File download(String url, int timeout) {
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

    private static void closeHttpRequestBase(HttpRequestBase requestBase) {
        if (null != requestBase) {
            requestBase.releaseConnection();
            requestBase = null;
        }
    }

}

package com.sxk.common.utils.http;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;

import org.apache.commons.httpclient.Header;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpMethodRetryHandler;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.MultiThreadedHttpConnectionManager;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.RequestEntity;
import org.apache.commons.httpclient.methods.StringRequestEntity;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.commons.httpclient.methods.multipart.StringPart;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.util.StatusPrinter;

/**
 * @description sync http pool util 
 * @author sxk
 * @date 2016年7月6日
 */
public class HttpUtil {

    private static final Logger                       logger                 = LoggerFactory.getLogger(HttpAsyncUtil.class);

    private static final String                       DEFAULT_CHARSET        = "UTF-8";

    private static final String                       URL_PARAM_CONNECT_FLAG = "&";

    // http thread manager
    private static MultiThreadedHttpConnectionManager connectionManager      = null;

    private static int                                connectionTimeOut      = 60000;

    private static int                                socketTimeOut          = 180000;

    private static int                                maxConnectionPerHost   = 100;

    private static int                                maxTotalConnections    = 1000;

    private static HttpClient                         httpClient;

    static {
        initLog();
        connectionManager = new MultiThreadedHttpConnectionManager();
        connectionManager.getParams().setConnectionTimeout(connectionTimeOut);
        connectionManager.getParams().setSoTimeout(socketTimeOut);
        connectionManager.getParams().setDefaultMaxConnectionsPerHost(maxConnectionPerHost);
        connectionManager.getParams().setMaxTotalConnections(maxTotalConnections);
        httpClient = new HttpClient(connectionManager);
    }

    //logback 日志配置初始化，避免打印垃圾日志
    private static void initLog() {
        LoggerContext lc = (LoggerContext) LoggerFactory.getILoggerFactory();
        lc.reset();
        JoranConfigurator configurator = new JoranConfigurator();
        configurator.setContext(lc);
        try {
            configurator.doConfigure("src/main/resources/conf/logback.xml");
        } catch (Exception e) {
            e.printStackTrace();
        }
        StatusPrinter.printInCaseOfErrorsOrWarnings(lc);
    }

    public static String urlEncode(String str, String charset) {
        try {
            return URLEncoder.encode(str, charset);
        } catch (UnsupportedEncodingException e) {
            logger.error("URL:{} encode error:{}", str, e);
            return str;
        }
    }

    //url校验
    public static boolean verifyUrl(String url) {
        return (null == url || 0 == url.trim().length() || !url.startsWith("http")) ? false : true;
    }

    //POST
    public static String post(String url, Map<String, String> params) {
        return post(url, params, null, DEFAULT_CHARSET, 0);
    }

    public static String post(String url, Map<String, String> params, String charset) {
        return post(url, params, null, charset, 0);
    }

    public static String post(String url, Map<String, String> params, Map<String, String> headers) {
        return post(url, params, headers, DEFAULT_CHARSET, 0);
    }

    public static String post(String url, Map<String, String> params, String charset, int timeout) {
        return post(url, params, null, charset, timeout);
    }

    /**
     * @param url
     * @param params
     * @param headers
     * @param charset
     * @param timeout 0 就是默认值
     * @return
     */
    public static String post(String url, Map<String, String> params, Map<String, String> headers, String charset, int timeout) {
        if (!verifyUrl(url)) {
            return null;
        }

        PostMethod postMethod = new PostMethod(url);
        postMethod.getParams().setSoTimeout(timeout == 0 ? socketTimeOut : timeout);
        postMethod.getParams().setContentCharset(charset);
        if (params != null) {
            for (String key : params.keySet()) {
                postMethod.addParameter(key, params.get(key));
            }
        }
        if (headers != null) {
            for (String headerName : headers.keySet()) {
                postMethod.addRequestHeader(headerName, headers.get(headerName));
            }
        }
        try {
            int statusCode = httpClient.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                return postMethod.getResponseBodyAsString();
            }
            if ((statusCode == HttpStatus.SC_MOVED_TEMPORARILY) || (statusCode == HttpStatus.SC_MOVED_PERMANENTLY)
                    || (statusCode == HttpStatus.SC_SEE_OTHER) || (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
                Header header = postMethod.getResponseHeader("location");
                if (header != null) {
                    return post(header.getValue(), params, charset);
                }
            }
        } catch (HttpException e) {
            logger.error("httpException", e);
        } catch (IOException e) {
            logger.error("发生网络异常", e);
        } finally {
            closeHttpMethod(postMethod);
        }
        return null;
    }

    public static String post(String url, String params) {
        return post(url, params, null, DEFAULT_CHARSET, 0);
    }

    public static String post(String url, String params, Map<String, String> headers) {
        return post(url, params, headers, DEFAULT_CHARSET, 0);
    }

    public static String post(String url, String params, Map<String, String> headers, String charset, int timeout) {
        if (!verifyUrl(url)) {
            return null;
        }

        PostMethod postMethod = new PostMethod(url);
        postMethod.getParams().setSoTimeout(timeout == 0 ? socketTimeOut : timeout);
        postMethod.getParams().setContentCharset(charset);
        try {
            if (params != null) {
                RequestEntity requestEntity = new StringRequestEntity(params, "text/xml", DEFAULT_CHARSET);
                postMethod.setRequestEntity(requestEntity);
            }
            if (headers != null) {
                for (String headerName : headers.keySet()) {
                    postMethod.addRequestHeader(headerName, headers.get(headerName));
                }
            }
            int statusCode = httpClient.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                return postMethod.getResponseBodyAsString();
            }
            if ((statusCode == HttpStatus.SC_MOVED_TEMPORARILY) || (statusCode == HttpStatus.SC_MOVED_PERMANENTLY)
                    || (statusCode == HttpStatus.SC_SEE_OTHER) || (statusCode == HttpStatus.SC_TEMPORARY_REDIRECT)) {
                Header header = postMethod.getResponseHeader("location");
                if (header != null) {
                    return get(url + "?" + params, null, charset);
                }
            }
        } catch (HttpException e) {
            logger.error("httpException", e);
        } catch (IOException e) {
            logger.error("发生网络异常", e);
        } finally {
            closeHttpMethod(postMethod);
        }
        return null;
    }

    //GET
    public static String get(String url) {
        return get(url, null, null, DEFAULT_CHARSET, 0);
    }

    public static String get(String url, Map<String, String> params) {
        return get(url, params, null, DEFAULT_CHARSET, 0);
    }

    public static String get(String url, Map<String, String> params, Map<String, String> headers) {
        return get(url, params, headers, DEFAULT_CHARSET, 0);
    }

    public static String get(String url, Map<String, String> params, String charset) {
        return get(url, params, null, charset, 0);
    }

    public static String get(String url, Map<String, String> params, String charset, int timeout) {
        return get(url, params, null, charset, timeout);
    }

    public static String get(String url, Map<String, String> params, Map<String, String> headers, String charset, int timeout) {
        return get(url, params, headers, charset, timeout, null, 0);
    }

    /**
     * @param url
     * @param params
     * @param headers
     * @param charset
     * @param timeout
     * @param proxyHost
     * @param proxyPort
     * @return
     *
     */
    public static String get(String url, Map<String, String> params, Map<String, String> headers, String charset, int timeout, String proxyHost,
            int proxyPort) {
        if (!verifyUrl(url)) {
            return null;
        }
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

        GetMethod getMethod = new GZIPAwareGetMethod(url);

        if (headers != null) {
            for (String headerName : headers.keySet()) {
                getMethod.addRequestHeader(headerName, headers.get(headerName));
            }
        }

        getMethod.getParams().setContentCharset(charset);
        getMethod.getParams().setSoTimeout(timeout == 0 ? socketTimeOut : timeout);
        getMethod.getParams().setParameter(HttpMethodParams.RETRY_HANDLER, new HttpMethodRetryHandler() {
            @Override
            public boolean retryMethod(HttpMethod getMethod, IOException exception, int executionCount) {
                return executionCount > 2 ? false : true;
            }
        });

        if (null != proxyHost) {
            httpClient.getHostConfiguration().setProxy(proxyHost, proxyPort);
        }

        httpClient.getHttpConnectionManager().getParams().setConnectionTimeout(connectionTimeOut);

        try {

            int statusCode = httpClient.executeMethod(getMethod);
            if (statusCode == HttpStatus.SC_OK) {
                return getMethod.getResponseBodyAsString();
            }
        } catch (Exception e) {
            logger.error("请求地址错误:" + url, e);
        } finally {
            closeHttpMethod(getMethod);
        }
        return null;

    }

    //UPLOAD
    public static String upload(String url, Map<String, String> params, List<FilePart> files) {
        return upload(url, params, files, DEFAULT_CHARSET);
    }

    public static String upload(String url, Map<String, String> params, List<FilePart> files, String charset) {
        return upload(url, params, null, files, charset);
    }

    public static String upload(String url, Map<String, String> params, Map<String, String> headers, List<FilePart> files) {
        return upload(url, params, headers, files, DEFAULT_CHARSET);
    }

    /**
     * @param url
     * @param params
     * @param headers
     * @param files
     * @param charset
     *
     */
    public static String upload(String url, Map<String, String> params, Map<String, String> headers, List<FilePart> files, String charset) {
        if (!verifyUrl(url)) {
            return null;
        }
        PostMethod postMethod = new PostMethod(url);
        postMethod.getParams().setSoTimeout(socketTimeOut);
        try {
            List<Part> parts = new ArrayList<Part>();
            if (params != null) {
                for (String key : params.keySet()) {
                    String value = params.get(key);
                    parts.add(new StringPart(key, value, charset));
                }
            }
            if (files != null) {
                parts.addAll(files);
            }

            if (parts != null) {
                postMethod.setRequestEntity(new MultipartRequestEntity(parts.toArray(new Part[parts.size()]), new HttpMethodParams()));
            }
            postMethod.getParams().setContentCharset(charset);

            if (headers != null) {
                for (String key : headers.keySet()) {
                    String value = headers.get(key);
                    postMethod.addRequestHeader(key, value);
                }
            }
            int statusCode = httpClient.executeMethod(postMethod);
            if (statusCode == HttpStatus.SC_OK) {
                return postMethod.getResponseBodyAsString();
            }
        } catch (Exception e) {
            logger.error("请求地址错误:" + url, e);
        } finally {
            closeHttpMethod(postMethod);
        }
        return null;
    }

    //DOWNLOAD
    /**
     * @param url
     * @return
     */
    public static File download(String url) {
        if (!verifyUrl(url)) {
            return null;
        }
        File file = null;
        GetMethod getMethod = new GetMethod(url);
        try {
            httpClient.executeMethod(getMethod);
            file = File.createTempFile("file", ".temp");
            FileOutputStream output = new FileOutputStream(file);
            output.write(getMethod.getResponseBody());
            output.close();
        } catch (Exception e) {
            if (file != null) {
                file.delete();
            }
            file = null;
            logger.error("请求地址错误:" + url, e);
        } finally {
            closeHttpMethod(getMethod);
        }
        return file;
    }

    private static void closeHttpMethod(HttpMethod httpMethod) {
        if (null != httpMethod) {
            try {
                httpMethod.releaseConnection();
                httpMethod = null;
            } catch (Exception e) {
                logger.error("HttpConnection releaseConnection", e);
            }
        }
    }

    //内部类 gzip压缩
    private static class GZIPAwareGetMethod extends GetMethod {

        public GZIPAwareGetMethod(String uri) {
            super(uri);
        }

        @Override
        protected void readResponseBody(HttpState state, HttpConnection conn) throws IOException, HttpException {
            super.readResponseBody(state, conn);
            Header contentEncodingHeader = getResponseHeader("Content-Encoding");
            if (contentEncodingHeader != null && contentEncodingHeader.getValue().equalsIgnoreCase("gzip")) {
                setResponseStream(new GZIPInputStream(getResponseStream()));
            }
        }
    }

}

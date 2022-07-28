package com.springboot.oauth.client.utils;

import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.*;

/**
 * @author king
 */
public class HttpUtils {

    public static String POST = "post";
    private static final String CHARSET = "UTF-8";
    private static String contentType = "application/json";
    private static String charset = "utf-8";
    private static final int CONNECT_TIMEOUT = Integer.parseInt("60000");

    private static final int CONNECT_REQUEST_TIMEOUT = Integer.parseInt("60000");
    private static final int SO_TIMEOUT = Integer.parseInt("60000");

    public static String doPost(String url) {
        try {
            return doPost(url, null, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String doGet(String url, List<Header> headers) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {

            if (url.startsWith("https")) {
                httpClient = HttpUtils.getHttpsClient();
            } else {
                httpClient = HttpClients.createDefault();
            }
            // 创建客户端连接对象
            HttpGet httpGet = new HttpGet(url);
            RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT)
                    .setSocketTimeout(SO_TIMEOUT).build();
            httpGet.setConfig(config);
            //headerMap用作请求参数map
            if(null != headers && headers.size() > 0) {
                for (Header header: headers) {
                    httpGet.addHeader(header);
                }
            }
            // 获取返回对象
            response = httpClient.execute(httpGet);
            //清空headerMap，用作返回头map集合
            headers = headers == null ? new ArrayList<>() : headers;
            headers.clear();

            getResponseHeader(response, headers);
            // 整理返回值
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // 关闭连接和流
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String doPost(String url, Map<String, Object> paraMap, List<Header> headers)
            throws Exception {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse resp = null;
        String rtnValue = null;
        try {
            if (url.startsWith("https")) {
                httpClient = HttpUtils.getHttpsClient();
            } else {
                httpClient = HttpClients.createDefault();
            }

            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> list = new ArrayList<NameValuePair>();
            if (null != paraMap && paraMap.size() > 0) {
                for (Map.Entry<String, Object> entry : paraMap.entrySet()) {
                    list.add(new BasicNameValuePair(entry.getKey(), entry.getValue().toString()));
                }
            }

            if(null != headers && headers.size() > 0) {
                for (Header header: headers) {
                    httpPost.addHeader(header);
                }
            }

            // 设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SO_TIMEOUT)
                    .setConnectTimeout(CONNECT_TIMEOUT).build();
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(new UrlEncodedFormEntity(list, CHARSET));
            resp = httpClient.execute(httpPost);

            headers = headers == null ? new ArrayList<>() : headers;
            headers.clear();
            getResponseHeader(resp, headers);

            rtnValue = EntityUtils.toString(resp.getEntity(), CHARSET);
        } finally {
            if (null != resp) {
                resp.close();
            }
            if (null != httpClient) {
                httpClient.close();
            }
        }

        return rtnValue;
    }

    /**
     * 获取请求返回头参数集合
     * @param response CloseableHttpResponse
     * @param headers 请求返回头参数集合
     */
    public static void getResponseHeader(CloseableHttpResponse response, List<Header> headers) {
        Arrays.stream(response.getAllHeaders()).forEach(header -> headers.add(header));
    }

    public static CloseableHttpClient getHttpsClient() throws Exception {
        try {
            TrustManager[] trustManagers = new TrustManager[] { new X509TrustManager() {
                @Override
                public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
                        throws CertificateException {
                }

                @Override
                public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
                        throws CertificateException {
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            } };
            SSLContext sslContext = SSLContext.getInstance(SSLConnectionSocketFactory.TLS);
            sslContext.init(new KeyManager[0], trustManagers, new SecureRandom());
            SSLContext.setDefault(sslContext);
            sslContext.init(null, trustManagers, null);
            SSLConnectionSocketFactory connectionSocketFactory = new SSLConnectionSocketFactory(sslContext,
                    SSLConnectionSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            HttpClientBuilder clientBuilder = HttpClients.custom().setSSLSocketFactory(connectionSocketFactory);
            clientBuilder.setRedirectStrategy(new LaxRedirectStrategy());
            return clientBuilder.build();
        } catch (Exception e) {
            throw new Exception("http client 远程连接失败", e);
        }
    }
}

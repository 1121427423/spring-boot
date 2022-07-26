package com.springboot.security.utils;

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
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.KeyManager;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HttpUtils {

    public static String POST = "post";
    private static final String CHARSET = "UTF-8";
    private static String contentType = "application/json";
    private static String charset = "utf-8";
    private static final int CONNECT_TIMEOUT = Integer.parseInt("60000");

    private static final int CONNECT_REQUEST_TIMEOUT = Integer.parseInt("60000");
    private static final int SO_TIMEOUT = Integer.parseInt("60000");

    private static String cookie;

    public static void main(String[] args) {
//        Map<String, Object> map = new HashMap<>(2);
//        map.put("username","86547462");
//        map.put("password","123456");
//        try {
//            doPost("http://127.0.0.1:8001/login",map);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
        doGet("http://127.0.0.1:8001/oauth/authorize?response_type=code&client_id=1121427423&scope=all&redirect_uri=https://www.bilibili.com");
    }

    public static String doPost(String url) {
        try {
            return doPost(url, null);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private static String doGet(String url) {
        CloseableHttpClient client = null;
        CloseableHttpResponse response = null;
        try {
            // 创建客户端连接对象
            client = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            httpGet.setHeader("Cookie", "JSESSIONID=D6E78D2C7A55623B11D469BC3FA5FB5A");
            RequestConfig config = RequestConfig.custom().setConnectTimeout(CONNECT_TIMEOUT).setConnectionRequestTimeout(CONNECT_REQUEST_TIMEOUT)
                    .setSocketTimeout(SO_TIMEOUT).build();
            httpGet.setConfig(config);
            // 获取返回对象
            response = client.execute(httpGet);
            // 整理返回值
            HttpEntity entity = response.getEntity();
            return EntityUtils.toString(entity);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            // 关闭连接和流
            try {
                if (client != null) {
                    client.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String doPost(String url, Map<String, Object> paraMap)
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
            // 设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(SO_TIMEOUT)
                    .setConnectTimeout(CONNECT_TIMEOUT).build();
            httpPost.setConfig(requestConfig);
            httpPost.setEntity(new UrlEncodedFormEntity(list, CHARSET));
            resp = httpClient.execute(httpPost);
            Header[] headers = resp.getHeaders("Set-Cookie");
            cookie = String.valueOf(headers[0]);
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


    public static CloseableHttpClient getHttpsClient() throws Exception {
        try {
            TrustManager[] trustManagers = new TrustManager[] { new X509TrustManager() {
                public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
                        throws CertificateException {
                }

                public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
                        throws CertificateException {
                }

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

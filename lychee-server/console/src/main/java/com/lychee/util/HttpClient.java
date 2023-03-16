package com.lychee.util;

import org.apache.http.Header;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author jiangwei97@aliyun.com
 * @since 2023/3/12 18:02
 */
@Component
public class HttpClient {

    public static CloseableHttpClient httpClient = null;
    public static HttpClientContext context = null;
    public static CookieStore cookieStore = null;
    public static RequestConfig requestConfig = null;
    public static Header[] headers = null;
    public static String session = null;

    static {
        try {
            init();
        } catch (IOException | KeyManagementException | NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    private static void init() throws IOException, NoSuchAlgorithmException, KeyManagementException {

        context = HttpClientContext.create();
        cookieStore = new BasicCookieStore();
        requestConfig = RequestConfig.custom()
                .setConnectTimeout(120000)
                .setSocketTimeout(60000)
                .setConnectionRequestTimeout(60000)
                .build();
        // 设置默认跳转以及存储cookie 跳过证书验证
        // httpClient = client();
        // 设置默认跳转以及存储cookie - 需要证书验证
        httpClient = HttpClientBuilder.create()
//             .setKeepAliveStrategy(new DefaultConnectionKeepAliveStrategy())
//             .setRedirectStrategy(new DefaultRedirectStrategy()).setDefaultRequestConfig(requestConfig)
//             .setDefaultCookieStore(cookieStore)
                .build();
    }

    /**
     * 绕过验证
     */
    public static SSLContext createIgnoreVerifySSL() throws NoSuchAlgorithmException, KeyManagementException {
        SSLContext sc = SSLContext.getInstance("SSLv3");

        // 实现一个X509TrustManager接口，用于绕过验证，不用修改里面的方法
        X509TrustManager trustManager = new X509TrustManager() {
            @Override
            public void checkClientTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) {
            }

            @Override
            public void checkServerTrusted(
                    java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                    String paramString) {
            }

            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return null;
            }
        };

        sc.init(null, new TrustManager[]{trustManager}, null);
        return sc;
    }

    /**
     * 生成绕过证书认证的 httpClient 对象
     */
    public static CloseableHttpClient client() throws NoSuchAlgorithmException, KeyManagementException {
        //采用绕过验证的方式处理https请求
        SSLContext sslcontext = createIgnoreVerifySSL();

        //设置协议http和https对应的处理socket链接工厂的对象
        Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.INSTANCE)
                .register("https", new SSLConnectionSocketFactory(sslcontext))
                .build();
        PoolingHttpClientConnectionManager connManager = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
        HttpClients.custom().setConnectionManager(connManager);

        //创建自定义的httpclient对象
        return HttpClients.custom().setConnectionManager(connManager).build();
    }

    public String sendGet(String url, Map<String, String> headers, Map<String, String> param) throws IOException,
            URISyntaxException {
        //https://fanyi-api.baidu.com/api/trans/vip/translate?appid=20230317001603256&from=cn&to=en&salt=lychee&sign=5dee6e89a85945379dadecb0c667bd58&q=%25E4%25BD%25A0%25E5%25A5%25BD
        // URI uri = new URI(UrlParser.setQueryParams(url, param));
        MultiValueMap<String, String> multiValueMap = new LinkedMultiValueMap<>(
                param.entrySet().stream().collect(
                        Collectors.toMap(Map.Entry::getKey, e -> Collections.singletonList(e.getValue()))));
        URI uri = UriComponentsBuilder.fromUriString(url).queryParams(multiValueMap).build().toUri();
        HttpGet httpget = new HttpGet(uri);
        httpget.setProtocolVersion(HttpVersion.HTTP_1_0);
        headers.put(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
        headers.put("Accept-Encoding", "gzip");
        headers.keySet().forEach(s -> httpget.setHeader(s, headers.get(s)));

        return EntityUtils.toString(httpClient.execute(httpget, context).getEntity());
    }

    public String sendPost(String url, Map<String, String> headers, String param) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        httpPost.setProtocolVersion(HttpVersion.HTTP_1_0);
        headers.put(HTTP.CONN_DIRECTIVE, HTTP.CONN_CLOSE);
        headers.keySet().forEach(s -> httpPost.setHeader(s, headers.get(s)));
        StringEntity entity = new StringEntity(param, "UTF-8");
        httpPost.setEntity(entity);

        return EntityUtils.toString(httpClient.execute(httpPost, context).getEntity());
    }

}

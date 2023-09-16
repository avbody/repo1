package com.random.crawler_news.utils;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

@Component
public class HttpUtils {

    private PoolingHttpClientConnectionManager connectionManager;

    public HttpUtils() {
        this.connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(100); //最大连接数
        connectionManager.setDefaultMaxPerRoute(10); //每个主机的最大连接数
    }

    public String getHtml(String url) {
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        HttpGet httpGet = new HttpGet(url);

        RequestConfig config = RequestConfig.custom().setConnectTimeout(1000) //设置创建链接超时时间
                .setConnectionRequestTimeout(500) //设置获取链接的超时时间
                .setSocketTimeout(10000) //设置链接的超时时间
                .build();
        httpGet.setConfig(config);

        CloseableHttpResponse response = null;
        String html = "";
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200) {
                if (response.getEntity() != null) {
                    html = EntityUtils.toString(response.getEntity(), "utf8");
                }
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return html;
    }

    public String getImage(String url){
        CloseableHttpClient httpClient = HttpClients.custom().setConnectionManager(connectionManager).build();
        HttpGet httpGet = new HttpGet(url);

        RequestConfig config = RequestConfig.custom().setConnectTimeout(1000) //设置创建链接超时时间
                .setConnectionRequestTimeout(500) //设置获取链接的超时时间
                .setSocketTimeout(10000) //设置链接的超时时间
                .build();
        httpGet.setConfig(config);

        CloseableHttpResponse response = null;
        FileOutputStream fileOutputStream = null;
        String imageName = "";
        try {
            response = httpClient.execute(httpGet);
            if (response.getStatusLine().getStatusCode() == 200){
                //获取后缀名
                String extName = url.substring(url.lastIndexOf('.'));
                imageName = "D:\\Ztools\\news\\images\\" + UUID.randomUUID().toString().replace("-","") + extName;
                fileOutputStream = new FileOutputStream(imageName);
                response.getEntity().writeTo(fileOutputStream);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (fileOutputStream != null){
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return imageName;
    }
}

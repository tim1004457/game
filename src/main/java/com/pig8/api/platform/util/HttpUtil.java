/**
 *
 */
package com.pig8.api.platform.util;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author navy
 */
public class HttpUtil {

    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    private static final String JSON_TYPE = "application/json";
    private static final String TEXT_TYPE = "text/xml";
    private static final String DEFAULT_ENCODING = "UTF-8";

    public static String doPost(String url) throws IOException {
        return doPost(url, "", null);
    }

    public static String doPost(String url, String contentType, HttpEntity body)
            throws IOException {
        HttpPost httpPost = new HttpPost(url);
        if (StringUtils.isNotEmpty(contentType))
            httpPost.addHeader("Content-Type", contentType);
        else
            httpPost.addHeader("Content-Type", TEXT_TYPE);
        httpPost.setEntity(body);

        HttpEntity entity = null;
        try {
            HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
            CloseableHttpClient httpClient = httpClientBuilder.build();
            HttpContext context = new BasicHttpContext();
            HttpResponse remoteResponse = httpClient.execute(httpPost, context);
            logger.info(remoteResponse.getStatusLine().toString());
            entity = remoteResponse.getEntity();
        } catch (Exception e) {
            logger.error("fetch remote content" + url + "  error", e);
            httpPost.abort();
            return null;
        }

        // 404错误
        if (entity == null) {
            throw new RuntimeException(url + " is not found");
        }

        InputStream input = entity.getContent();
        try {
            return IOUtils.toString(input, DEFAULT_ENCODING);
        } finally {
            // 保证InputStream的关闭.
            IOUtils.closeQuietly(input);
        }
    }

    public String doGet(String url) throws IOException {
        String returnMsg = "";
        HttpGet httpget = new HttpGet(url);
        logger.info("get: " + httpget.getURI());
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        CloseableHttpClient httpClient = httpClientBuilder.build();
        HttpResponse response = httpClient.execute(httpget);
        logger.debug("status: " + response.getStatusLine());
        if (HttpStatus.SC_OK == response.getStatusLine().getStatusCode()) {
            HttpEntity entity = response.getEntity();
            if (entity != null) {
                returnMsg = EntityUtils.toString(entity);
                entity.consumeContent();
            }
        } else {
            throw new RuntimeException(response.getStatusLine().toString());
        }
        // System.out.println(html);
        return returnMsg;
    }

}

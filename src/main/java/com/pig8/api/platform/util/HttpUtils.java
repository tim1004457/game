package com.pig8.api.platform.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author navy
 *
 */
public class HttpUtils {
	private static final Logger logger = LoggerFactory.getLogger(HttpUtils.class);
	
	private static final String JSON_TYPE = "application/json";
	private static final String TEXT_TYPE = "text/xml";
	private static final String DEFAULT_ENCODING = "UTF-8";
	private static final int CONNECTION_POOL_SIZE = 10;
	private static final int TIMEOUT_SECONDS = 20;
	private HttpClient httpClient = null;

	/**
	 * 始始化HttpClient,且为多线程安全.
	 */
	@PostConstruct
	public void init() {
		// Set connection pool
		PoolingClientConnectionManager cm = new PoolingClientConnectionManager();
		cm.setMaxTotal(CONNECTION_POOL_SIZE);
		httpClient = new DefaultHttpClient(cm);

		// set timeout
		HttpParams httpParams = httpClient.getParams();
		HttpConnectionParams.setConnectionTimeout(httpParams, TIMEOUT_SECONDS *1000);
		HttpConnectionParams.setSoTimeout(httpParams, TIMEOUT_SECONDS * 1000);
	}

	/**
	 * 销毁HttpClient实例.
	 */
	@PreDestroy
	public void destroy() {
		if (httpClient != null) {
			httpClient.getConnectionManager().shutdown();
		}
	}

	/**
	 * get请求
	 * @param url
	 * @return
	 * @throws IOException
	 */
	public String doGet(String url) throws IOException {
		String returnMsg = "";
		HttpGet httpget = new HttpGet(url);
		logger.info("get: " + httpget.getURI());
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

	/**
	 * post请求，参数为键值对
	 * @param url
	 * @param parameters
	 * @return
	 * @throws IOException
	 */
	public String doPost(String url, Map<String, String> parameters) throws IOException {
		List<NameValuePair> nvps = new ArrayList<NameValuePair>();
		if (parameters != null && !parameters.isEmpty()) {
			Iterator<Entry<String, String>> itr = parameters.entrySet().iterator();
			while (itr.hasNext()) {
				Map.Entry<String, String> entry = (Map.Entry<String, String>) itr.next();
				NameValuePair nvp = new BasicNameValuePair((String) entry.getKey(), (String) entry.getValue());
				nvps.add(nvp);
			}
		}
		HttpPost httpPost = new HttpPost(url);
		StringEntity params = new UrlEncodedFormEntity(nvps, DEFAULT_ENCODING);	
		httpPost.setEntity(params);
		logger.info("post: " + httpPost.getURI());
		logger.info(nvps.toString());
		HttpEntity entity = null;
		try {
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

	/**
	 * post请求，参数为json串
	 * @param url
	 * @param jsonData
	 * @return
	 * @throws IOException
	 */
	public String doPostJson(String url, String jsonData) throws IOException {
		StringEntity params = new StringEntity(jsonData, DEFAULT_ENCODING);
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", JSON_TYPE);
		httpPost.setEntity(params);

		logger.info("post: " + httpPost.getURI());
		logger.info(jsonData);

		HttpEntity entity = null;
		try {
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
	
	/**
	 * post请求，参数为字符串
	 * @param url
	 * @param content
	 * @return
	 * @throws IOException
	 */
	public String doPostContent(String url, String content) throws IOException {
		StringEntity params = new StringEntity(content, DEFAULT_ENCODING);
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("Content-Type", TEXT_TYPE);
		httpPost.setEntity(params);

		logger.info("post: " + httpPost.getURI());
		logger.info(content);

		HttpEntity entity = null;
		try {
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

	public void close() {
		httpClient.getConnectionManager().shutdown();
	}
	
	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlName = url + "?" + param;
			URL realUrl = new URL(urlName);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 建立实际的连接
			conn.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = conn.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			logger.error("发送GET请求出现异常！", e);
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定URL发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是name1=value1&name2=value2的形式。
	 * @return URL所代表远程资源的响应
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			logger.error("发送POST请求出现异常！", e);
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}
	
	
	public static String sendPost(String url, byte[] content, String contentType) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			if (StringUtils.isNotEmpty(contentType))
				conn.setRequestProperty("Content-Type", contentType);
			conn.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);

			// 获取URLConnection对象对应的输出流
			//out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			//out.print(param);
			
			OutputStream outputStream = conn.getOutputStream();
			outputStream.write(content);
			outputStream.flush();
			// flush输出流的缓冲
			//out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(
					new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			logger.error("发送POST请求出现异常！", e);
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

}

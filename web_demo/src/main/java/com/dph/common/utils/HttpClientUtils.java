package com.dph.common.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.UnsupportedCharsetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.entity.mime.content.StringBody;
import org.apache.http.impl.client.AbstractResponseHandler;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.LaxRedirectStrategy;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * http工具类，使用连接池控制http连接，设置超时时间及失败重试
 * 
 * @author peihuadeng
 *
 */
public class HttpClientUtils {

	private final static String CHARSET = "UTF-8";// 编码
	private final static int MAX_CONNECTION = 1024; // 设置整个连接池最大连接数
	private final static int SINGLE_ROUTE_MAX_CONN = 100; // 设置单个路由默认连接数
	private final static boolean IS_RETRY = true;// 失败是否重试
	private final static int TRY_COUNT = 5;// 重试次数
	private final static RequestConfig config = RequestConfig.custom()// 定制配置
			.setSocketTimeout(5000)// 超时：5s
			.setConnectTimeout(5000)// 超时：5s
			.setConnectionRequestTimeout(5000)// 超时：5s
			.build();
	// 字符串应答控制器，直接以字符串行驶返回报文内容
	public final static BasicResponseHandler basicResponseHandler = new BasicResponseHandler();
	// json应答控制器
	public final static ResponseHandler<JsonDataPackage> jsonResponseHandler = new AbstractResponseHandler<JsonDataPackage>() {
		@Override
		public JsonDataPackage handleEntity(HttpEntity entity) throws IOException {
			String response = EntityUtils.toString(entity);
			if (StringUtils.isBlank(response)) {
				return null;
			}

			Map<String, Object> responseMap = JsonUtils.str2map(response, String.class, Object.class);
			if (responseMap == null) {
				return null;
			}
			JsonDataPackage dataPackage = new JsonDataPackage(responseMap);

			return dataPackage;
		}
	};

	public static String get(String url) {
		return get(url, null, null, basicResponseHandler);
	}

	public static String get(String url, Map<String, String> params) {
		return get(url, params, null, basicResponseHandler);
	}

	public static String get(String url, Map<String, String> params, Map<String, String> headers) {
		return get(url, params, headers, basicResponseHandler);
	}

	public static <T> T get(String url, ResponseHandler<T> responseHandler) {
		return get(url, null, null, responseHandler);
	}

	public static <T> T get(String url, Map<String, String> params, ResponseHandler<T> responseHandler) {
		return get(url, params, null, responseHandler);
	}

	/**
	 * http get方式请求
	 * 
	 * @param url：地址
	 * @param params：参数，将与地址组装在一起
	 * @param headers：请求头
	 * @param responseHandler：应答控制器
	 * @return
	 */
	public static <T> T get(String url, Map<String, String> params, Map<String, String> headers, ResponseHandler<T> responseHandler) {
		// 设置参数，组装完整url
		if (params != null && params.size() > 0) {
			List<NameValuePair> paramList = new ArrayList<NameValuePair>();
			for (Map.Entry<String, String> entry : params.entrySet()) {
				NameValuePair pair = new BasicNameValuePair(entry.getKey(), entry.getValue());
				paramList.add(pair);
			}

			String urlParam = URLEncodedUtils.format(paramList, CHARSET);
			url = (url.indexOf("?")) < 0 ? (url + "?" + urlParam) : (url.indexOf("?") == url.length() - 1 ? url + urlParam : url + "&" + urlParam);
		}

		HttpGet request = new HttpGet(url);
		// 添加请求头
		if (headers != null && headers.size() > 0) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
		}
		HttpClient client = HttpClientPool.getIntance().getHttpClient();
		try {
			T t = client.execute(request, responseHandler);

			return t;
		} catch (IOException e) {
			throw new RuntimeException("error occurs when httpclient getting", e);
		}
	}

	public static String post(String url, Map<String, String> params) {
		return post(url, params, null, basicResponseHandler);
	}

	public static String post(String url, Map<String, String> params, Map<String, String> headers) {
		return post(url, params, headers, basicResponseHandler);
	}

	public static <T> T post(String url, Map<String, String> params, ResponseHandler<T> responseHandler) {
		return post(url, params, null, responseHandler);
	}

	/**
	 * http post方式请求
	 * 
	 * @param url：地址
	 * @param params：参数，将写入报文内容
	 * @param headers：请求头
	 * @param responseHandler：应答控制器
	 * @return
	 */
	public static <T> T post(String url, Map<String, String> params, Map<String, String> headers, ResponseHandler<T> responseHandler) {
		HttpPost request = new HttpPost(url);
		// 设置请求头
		if (headers != null && headers.size() > 0) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
		}
		// 设置参数
		List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				nameValuePairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
			}
			try {
				request.setEntity(new UrlEncodedFormEntity(nameValuePairs, CHARSET));
			} catch (UnsupportedEncodingException e) {
				throw new RuntimeException(String.format("invalid charset[%s].", CHARSET), e);
			}
		}
		HttpClient client = HttpClientPool.getIntance().getHttpClient();
		try {
			T t = client.execute(request, responseHandler);

			return t;
		} catch (IOException e) {
			throw new RuntimeException("error occurs when httpclient posting", e);
		}
	}

	public static JsonDataPackage postJson(String url, Object jsonObject) {
		return postJson(url, jsonObject, null, jsonResponseHandler);
	}

	public static JsonDataPackage postJson(String url, Object jsonObject, Map<String, String> headers) {
		return postJson(url, jsonObject, headers, jsonResponseHandler);
	}

	public static <T> T postJson(String url, Map<String, String> params, ResponseHandler<T> responseHandler) {
		return postJson(url, params, null, responseHandler);
	}

	/**
	 * http post json方式请求
	 * 
	 * @param url：地址
	 * @param jsonObject：json对象参数，将对象转化成json然后写入报文内容
	 * @param headers：请求头，默认自动加上accept=application/json
	 * @param responseHandler：应答控制器
	 * @return
	 */
	public static <T> T postJson(String url, Object jsonObject, Map<String, String> headers, ResponseHandler<T> responseHandler) {
		HttpPost request = new HttpPost(url);
		// 设置请求头
		if (headers != null && headers.size() > 0) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
		}
		request.addHeader("Accept", "application/json");
		// 设置参数
		if (jsonObject != null) {
			String json = JsonUtils.bean2Str(jsonObject);
			try {
				StringEntity stringEntity = new StringEntity(json, ContentType.APPLICATION_JSON);
				request.setEntity(stringEntity);
			} catch (UnsupportedCharsetException e) {
				throw new RuntimeException(String.format("invalid charset[%s].", CHARSET), e);
			}
		}

		HttpClient client = HttpClientPool.getIntance().getHttpClient();
		try {
			T t = client.execute(request, responseHandler);

			return t;
		} catch (IOException e) {
			throw new RuntimeException("error occurs when httpclient posting json", e);
		}
	}

	/**
	 * http post方式发送文件及参数
	 * 
	 * @param url：地址
	 * @param params：参数，将写入报文内容
	 * @param headers：请求头
	 * @param fileKey：文件字段名
	 * @param file：文件
	 * @param responseHandler：应答控制器
	 * @return
	 */
	public static <T> T postFile(String url, Map<String, String> params, Map<String, String> headers, String fileKey, String file, ResponseHandler<T> responseHandler) {
		HttpPost request = new HttpPost(url);
		// 设置请求头
		if (headers != null && headers.size() > 0) {
			for (Map.Entry<String, String> entry : headers.entrySet()) {
				request.addHeader(entry.getKey(), entry.getValue());
			}
		}
		// 设置参数
		MultipartEntityBuilder mBuilder = MultipartEntityBuilder.create();
		if (params != null && params.size() > 0) {
			for (Map.Entry<String, String> entry : params.entrySet()) {
				mBuilder.addPart(entry.getKey(), new StringBody(entry.getValue(), ContentType.TEXT_PLAIN));
			}
		}
		// 添加文件
		mBuilder.addPart(fileKey, new FileBody(new File(file)));
		request.setEntity(mBuilder.build());
		HttpClient client = HttpClientPool.getIntance().getHttpClient();
		try {
			T t = client.execute(request, responseHandler);

			return t;
		} catch (IOException e) {
			throw new RuntimeException("error occurs when httpclient posting file", e);
		}
	}

	/**
	 * http连接池管理
	 * 
	 * @author peihuadeng
	 *
	 */
	public static class HttpClientPool {

		private final static Logger logger = LoggerFactory.getLogger(HttpClientPool.class);
		private static HttpClientPool instance = null;// 单例
		private PoolingHttpClientConnectionManager connectionManager = null;// 连接管理器

		/**
		 * 获取单例
		 * 
		 * @return
		 */
		public static synchronized HttpClientPool getIntance() {
			if (instance == null) {
				instance = new HttpClientPool();
			}

			return instance;
		}

		/**
		 * 销毁连接池
		 */
		public static synchronized void destory() {
			if (instance != null) {
				instance.close();
			}
		}

		/**
		 * 私有构造函数：创建连接池
		 */
		private HttpClientPool() {
			try {
				// 定义socket工厂类 指定协议（Http、Https）
				Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()// 泛型静态函数调用
						.register("http", PlainConnectionSocketFactory.getSocketFactory())// http协议
						.register("https", SSLConnectionSocketFactory.getSocketFactory())// https协议
						.build();

				// 创建连接管理器
				connectionManager = new PoolingHttpClientConnectionManager(registry);
				connectionManager.setMaxTotal(MAX_CONNECTION); // 设置最大连接数
				connectionManager.setDefaultMaxPerRoute(SINGLE_ROUTE_MAX_CONN); // 设置每个路由默认连接数
				// 设置目标主机的连接数
				// HttpHost host = new HttpHost("account.dafy.service"); // 针对的主机
				// connectionManager.setMaxPerRoute(new HttpRoute(host), 50);
			} catch (Exception e) {
				logger.error("error occurs when creating connection manager.", e);
				throw new RuntimeException("error occurs when creating connection manager.");
			}
		}

		/**
		 * 关闭连接池
		 */
		public void close() {
			if (connectionManager == null) {
				return;
			}
			connectionManager.shutdown(); // 关闭连接池
		}

		/**
		 * 获取http客户端
		 * 
		 * @return
		 */
		public HttpClient getHttpClient() {
			// 创建httpClient对象
			HttpClient httpClient = HttpClients.custom()// 定制httpclient
					.setConnectionManager(connectionManager)// 连接池
					.setRetryHandler(new DefaultHttpRequestRetryHandler(TRY_COUNT, IS_RETRY))// 重试设置
					.setDefaultRequestConfig(config)// 默认超时配置
					.setRedirectStrategy(new LaxRedirectStrategy())// 设置重定向机制
					.build();
			return httpClient;
		}
	}

	public static void main(String[] args) {
		HttpClientUtils.HttpClientPool.getIntance();
//		// test get
//		String getUrl = "http://localhost:8080/web_demo/student/view";
//		Map<String, String> getParams = new HashMap<String, String>();
//		getParams.put("id", "1");
//		String getResponse = HttpClientUtils.get(getUrl, getParams);
//		System.out.println("GET Response: " + getResponse);
//		// test post + redirect
//		String postUrl = "http://localhost:8080/web_demo/student/save";
//		Map<String, String> postParams = new HashMap<String, String>();
//		postParams.put("name", "test");
//		postParams.put("age", "1");
//		postParams.put("teacherId", "1");
//		String postResponse = HttpClientUtils.post(postUrl, postParams);
//		System.out.println("POST Response: " + postResponse);
//
//		// test post json
//		String postJsonUrl = "http://localhost:8080/web_demo/student/test";
//		Student student = new Student();
//		student.setId("1");
//		JsonDataPackage postJsonResponse = HttpClientUtils.postJson(postJsonUrl, student);
//		System.out.println("teacherId: " + postJsonResponse.getInteger("teacherId"));
//		System.out.println("POST JSON Response: " + postJsonResponse.getData());
//		// test get json
//		String getJsonUrl = "http://localhost:8080/web_demo/student/test";
//		Map<String, String> getJsonParams = new HashMap<String, String>();
//		getJsonParams.put("id", "1");
//		Map<String, String> getJsonHeader = new HashMap<String, String>();
//		getJsonHeader.put("Accept", "application/json");
//		JsonDataPackage getJsonResponse = HttpClientUtils.get(getJsonUrl, getJsonParams, getJsonHeader, HttpClientUtils.jsonResponseHandler);
//		System.out.println("teacherId: " + getJsonResponse.getInteger("teacherId"));
//		System.out.println("POST JSON Response: " + getJsonResponse.getData());
		HttpClientUtils.HttpClientPool.destory();
	}

}

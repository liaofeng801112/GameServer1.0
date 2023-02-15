package org.come.until;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;

import org.apache.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
// 三端
public class HttpClientSend {
	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		// PrintWriter out = null;
		OutputStreamWriter ow = null;
		BufferedReader in = null;
		StringBuffer result = null;
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			// out = new PrintWriter(conn.getOutputStream());
			ow = new OutputStreamWriter(conn.getOutputStream(), "utf-8");
			// 发送请求参数
			// out.print(param);
			// System.out.println("param: " + param);
			ow.write(param);
			// flush输出流的缓冲
			ow.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
			String line;
			result = new StringBuffer();
			while ((line = in.readLine()) != null) {
				result.append(line);
			}
			// System.out.println("result: " + result);
		} catch (Exception e) {
			// System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();

			return "error";
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (ow != null) {
					ow.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result.toString();
	}

	/**
	 * 发送 post请求
	 */
	public static String post(String json, String URL) {
		String obj = null;
		// 创建默认的httpClient实例.
		CloseableHttpClient httpclient = HttpClients.createDefault();
		// 创建httppost
		HttpPost httppost = new HttpPost(URL);
		httppost.addHeader("Content-type", "application/json; charset=utf-8");
		httppost.setHeader("Accept", "application/json");
		try {
			StringEntity s = new StringEntity(json, Charset.forName("UTF-8")); // 对参数进行编码，防止中文乱码
			s.setContentEncoding("UTF-8");
			httppost.setEntity(s);
			CloseableHttpResponse response = httpclient.execute(httppost);
			try {
				// 获取相应实体
				HttpEntity entity = response.getEntity();
				if (entity != null) {
					obj = EntityUtils.toString(entity, "UTF-8");
				}

			} finally {
				response.close();
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			// 关闭连接,释放资源
			try {
				httpclient.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return obj;
	}

	/**
	 * Get方法
	 */
	public static void sendGet(String URL, String param) {
		try {
			URL url = new URL(URL + "?" + param);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			connection.setDoOutput(true); // 设置该连接是可以输出的
			connection.setRequestMethod("GET"); // 设置请求方式
			connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

			BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "utf-8"));
			String line = null;
			StringBuilder result = new StringBuilder();
			while ((line = br.readLine()) != null) { // 读取数据
				result.append(line + "\n");
			}
			connection.disconnect();

			System.out.println(result.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

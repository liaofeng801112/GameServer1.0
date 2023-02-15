package org.come.until;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

public class HttpClient {
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
			return "postError";
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

	public static String geturl1(String geturl, String content) throws Exception {
		// 请求的webservice的url
		URL url = new URL(geturl);
		// 创建http链接
		HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

		// 设置请求的方法类型
		httpURLConnection.setRequestMethod("POST");

		// 设置请求的内容类型
		httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");

		// 设置发送数据
		httpURLConnection.setDoOutput(true);
		// 设置接受数据
		httpURLConnection.setDoInput(true);

		// 发送数据,使用输出流
		OutputStream outputStream = httpURLConnection.getOutputStream();
		// 发送的soap协议的数据
		// String requestXmlString = requestXml("北京");

		// String content = "user_id="+ URLEncoder.encode("13846", "gbk");

		// 发送数据
		// System.out.println("字符串编码: content 前 -" + new
		// StringUtil().checkString(content));
		outputStream.write(content.getBytes("UTF-8"));
		// System.out.println("字符串编码: content 后 -" + new
		// StringUtil().checkString(content));
		// BufferedWriter oWriter = new BufferedWriter(new OutputStreamWriter(
		// outputStream, "UTF-8"));

		// 接收数据
		InputStream inputStream = httpURLConnection.getInputStream();
		BufferedReader in = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
		// InputStreamReader in = new InputStreamReader(inputStream, "GB2312");
		StringBuffer buffer = new StringBuffer();
		String line = "";
		while ((line = in.readLine()) != null) {
			buffer.append(line);
		}

		String str = buffer.toString();
		// System.out.println("str: " + str);
		// System.out.println("字符串编码: str -" + new
		// StringUtil().checkString(str));
		return str;
	}
}

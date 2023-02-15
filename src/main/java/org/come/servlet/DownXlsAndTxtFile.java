package org.come.servlet;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.come.tool.NewAESUtil;
import org.come.tool.ReadExelTool;
import org.come.until.GsonUtil;

import com.google.gson.reflect.TypeToken;

/**
 * @author HGC<br>
 * @time 2020年1月8日 下午1:43:08<br>
 * @class 类名:DownXlsAndTxtFile<br>
 */
public class DownXlsAndTxtFile extends HttpServlet {

    /**
     * Constructor of the object.
     */
    public DownXlsAndTxtFile() {
        super();
    }

    /**
     * Destruction of the servlet. <br>
     */
    @Override
	public void destroy() {
        super.destroy(); // Just puts "destroy" string in log
        // Put your code here
    }

    /**
     * The doGet method of the servlet. <br>
     * 
     * This method is called when a form has its tag value method equals to get.
     * 
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    @Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    /**
     * The doPost method of the servlet. <br>
     * 
     * This method is called when a form has its tag value method equals to
     * post.
     * 
     * @param request
     *            the request send by the client to the server
     * @param response
     *            the response send by the server to the client
     * @throws ServletException
     *             if an error occurred
     * @throws IOException
     *             if an error occurred
     */
    @Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        response.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "*");

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(request.getInputStream(), "ISO-8859-1"));
        List<String> strings = new ArrayList<>();
        String line = null;
        boolean is = true;
        while ((line = bufferedReader.readLine()) != null) {
            if (is) {
                is = false;
                continue;
            }
            strings.add(line);
        }
        Map<String, String> map1 = new HashMap<>();
        // 获取参数
        for (int i = 0; i < strings.size() / 6; i++) {
            String key = strings.get(i * 6);
            String[] keyArr = key.split("\"");
            map1.put(keyArr[1], strings.get(6 * i + 4));
        }
        String key = map1.get("pwd");
        key = NewAESUtil.AESJDKDncode(key);
        key = key.substring(13);
        StringBuffer buffer = new StringBuffer();
        Map<String, String> map = new HashMap<String, String>();
        if (key != null && !"".equals(key) && key.equals(UpXlsAndTxtFile.pwdUp)) {
            String value = map1.get("params");
            if (value != null && !"".equals(value)) {
                String dncode = NewAESUtil.AESJDKDncode(value);
                dncode = dncode.substring(13);
                List<String> list = GsonUtil.getGsonUtil().getgson().fromJson(dncode, new TypeToken<List<String>>() {  }.getType());
                File directory = new File(ReadExelTool.class.getResource("/").getPath()+"config");
                if (!directory.exists()) {
                    buffer.append("下载失败");
                    buffer.append("\r\n");
                } else {
                    for (int i = 0; i < list.size(); i++) {
                        File file = new File(directory.getAbsolutePath() + File.separatorChar + list.get(i));
                        if (file.exists()) {
                            byte[] byteArray = InputStream2ByteArray(file);
                            String encode = NewAESUtil.AESJDKEncode(GsonUtil.getGsonUtil().getgson().toJson(byteArray));
                            map.put(list.get(i), encode);
                        } else {
                            buffer.append("找不到");
                            buffer.append(list.get(i));
                            buffer.append("\r\n");
                        }
                    }
                }
            }
        } else {
            buffer.append("验证码不正确");
            buffer.append("\r\n");
        }
        map.put("params", NewAESUtil.AESJDKEncode(buffer.toString()));
        String json = GsonUtil.getGsonUtil().getgson().toJson(map);
        String encode = NewAESUtil.AESJDKEncode(json);
        OutputStream outputStream = response.getOutputStream();
        outputStream.write(encode.getBytes("utf-8"));
        outputStream.flush();
        outputStream.close();
    }

    /** 文件转二进制 */
    public static byte[] InputStream2ByteArray(File file) {

        InputStream in = null;
        byte[] data = null;
        try {
            in = new FileInputStream(file);
            data = toByteArray(in);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null)
                    in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return data;
    }

    public static byte[] toByteArray(InputStream in) throws IOException {

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024 * 4];
        int n = 0;
        while ((n = in.read(buffer)) != -1) {
            out.write(buffer, 0, n);
        }
        return out.toByteArray();
    }

    /**
     * Initialization of the servlet. <br>
     * 
     * @throws ServletException
     *             if an error occurs
     */
    @Override
	public void init() throws ServletException {
        // Put your code here
    }

}

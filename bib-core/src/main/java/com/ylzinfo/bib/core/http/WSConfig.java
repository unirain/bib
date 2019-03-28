package com.ylzinfo.bib.core.http;


import cn.hutool.core.io.IoUtil;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/********************************************************************************
 *
 * Title: ws服务工具类
 *
 * Description:
 *
 * @author chenlm
 * create date on 2018/7/5
 *
 *******************************************************************************/
public class WSConfig {
    /**
     * 调用ws
     *
     * @param url  调用地址
     * @param soap 请求报文
     * @return
     * @throws Exception
     */
    public static String callWs(String url, String soap) throws Exception {
        //服务的地址
        try {
            HttpURLConnection conn = getPostUrlConnection(url);
            IoUtil.writeUtf8(conn.getOutputStream(), true, soap);
            InputStream inputStream = conn.getInputStream();
            String result = IoUtil.read(inputStream, "utf-8");
            IoUtil.close(inputStream);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw e;

        }
    }

    /**
     * 获取连接
     *
     * @param url
     * @return
     * @throws Exception
     */
    private static HttpURLConnection getPostUrlConnection(String url) throws Exception {
        URL wsUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) wsUrl.openConnection();
        conn.setDoInput(true);
        conn.setConnectTimeout(60000);
        conn.setReadTimeout(120000);
        conn.setDoOutput(true);
        conn.setRequestMethod("POST");
        conn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
        return conn;
    }
}

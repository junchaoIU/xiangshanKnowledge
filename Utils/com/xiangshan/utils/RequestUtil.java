package com.xiangshan.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;

public class RequestUtil {

	public static BufferedReader requestData(String requestURL,Map<String,String> params) throws Exception{
		
		URL url = new URL(requestURL); 
		HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("POST"); 
		conn.setRequestProperty("accept","*/*");
        conn.setRequestProperty("connection","Keep-Alive");
        conn.setRequestProperty("user-agent",
                "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
		//设置允许写出数据,默认是false 
        conn.setDoOutput(true); 
        //当前的连接可以从服务器读取内容, 默认是true
        conn.setDoInput(true); 
		
        if(params!=null) {
	        //获取向服务器写出数据的流 
	        OutputStream os = conn.getOutputStream(); 
	        //参数不以"?"开始 
	        Iterator<String> keys = params.keySet().iterator();
	        while(keys.hasNext()) {
	        		String key = keys.next();
	        		os.write((key+"="+params.get(key)).getBytes());
	        }
	        os.flush();
        }
        //得到服务器写回的响应数据 
        BufferedReader br=new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
        
        return br;
	}
}

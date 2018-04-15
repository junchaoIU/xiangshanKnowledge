package com.xiangshan.utils;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;

/**
 * 输入输出流工具类。用来将对象转成输入输出流对象。
 * 
 * @author Rosahen
 * @version 1.0
 */
public class StreamUtil {

	private StreamUtil() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * 建立字节数组输入流方法。将字符串对象转成字节数组输入流。
	 * 
	 * @param result 字符串
	 * @return 字符串对应的输入流
	 */
	public static InputStream buildByteArrayInputStream(String result) {
		
		InputStream inputStream = null;
		
		if(result==null) result = "-1";
		
		try {
			
			//创建字节数组输入流
			inputStream = new ByteArrayInputStream(result.getBytes("UTF-8"));
			
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			try {
				
				//如果转换失败,则创建带有失败信息的输入流
				inputStream = new ByteArrayInputStream("failed".getBytes("UTF-8"));
				
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			e1.printStackTrace();
		}
	
		return inputStream;
	}
}

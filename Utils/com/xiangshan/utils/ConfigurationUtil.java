package com.xiangshan.utils;

import java.io.InputStream;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * 配置文件的工具类。用来解析配置文件。
 * 
 * @author Rosahen
 * @version 1.0
 */
public class ConfigurationUtil {

	private ConfigurationUtil() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * 获取配置文件根元素的方法。
	 * 
	 * @param configurationFile 配置文件名。包括扩展名，配置文件要放在类目录下。
	 * @return 配置文件的根元素。
	 */
	public static Element getConfigurationRootElement(String configurationFile) {
		
		/**
		 * 加载类目录下指定的配置文件
		 */
		InputStream in = ConfigurationUtil.class.getClassLoader().getResourceAsStream(configurationFile);

		SAXReader reader = new SAXReader();

		//配置文档对象
		Document doc = null;
		
		try {
			//读取配置文件
			doc = reader.read(in);
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//获取根元素
		Element root = doc.getRootElement();
		
		return root;
	}
	
}

package com.xiangshan.listeners;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.apache.jena.ontology.OntModel;
import org.dom4j.Element;

import com.xiangshan.ontology.utils.JenaUtil;
import com.xiangshan.utils.ConfigurationUtil;

/**
 * 项目生命周期监听器。在此监听器对项目做一些初始化工作。
 * 
 * @author Rosahen
 * @version 1.0
 */
@WebListener
public class LifeCycleListener implements ServletContextListener {

	/**
	 * Default constructor.
	 */
	public LifeCycleListener() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent sce) {
		// TODO Auto-generated method stub

		// 获取本体配置文件的根元素
		Element root = ConfigurationUtil.getConfigurationRootElement("ontology-properties.xml");

		// 获取默认模型的URI
		String defaultModelURI = root.element("Named_Graph").element("Default_Graph").elementText("URI");

		// 获取应用路径
//		String applicationPath = sce.getServletContext().getRealPath("");

		// 将默认模型添加到Dataset中
		OntModel defaultOntModel = JenaUtil.addNamedModel(defaultModelURI);

		sce.getServletContext().setAttribute("defaultOntModel", defaultOntModel);

//		System.out.println("applicationPath:" + applicationPath);

	}

}

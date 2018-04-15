package com.xiangshan.catalog.actions;

import java.io.InputStream;

import com.xiangshan.catalog.service.interfaces.CatalogService;
import com.xiangshan.generic.actions.GenericAction;
import com.xiangshan.utils.StreamUtil;

/**
 * 本体目录Action类。此类用来处理获取本体的类节点和实例节点数据的请求。
 * 
 * @author Rosahen
 * @version 1.0
 */
public class CatalogAction extends GenericAction{

	/**
	 * 节点的名称。
	 */
	private String name;
	
	/**
	 * 数据的输入流。
	 */
	private InputStream result ;
	
	/**
	 * 本体目录Service。
	 */
	private CatalogService catalogService;
	
	
	/**
	 * 获取某个类节点的子类数据的方法。
	 * 
	 * @return 子类数据结果对应的name值。
	 */
	public String getSubClasses() {
		
		//如果节点的名称为"知识分类"(或其它,具体看目录的根节点被设置成什么值),意为着要获取本体的顶层类数据
		if(name.equals("知识分类")) {
			getTopClasses();
		}
		//否则,正常获取节点的子类数据即可
		else {
			
			//调用CatalogService中的方法获取子节点数据
			String classes = catalogService.getNodeChildren(name);
			
			//调用StreamUtil的方法，将字符串转换成输入流。
			result = StreamUtil.buildByteArrayInputStream(classes);
		}
		
		return "success";
	}
	
	/**
	 * 获取本体顶层类数据的方法。
	 * 
	 * @return 顶层类数据结果对应的name值。
	 */
	public String getTopClasses() {
		
		//调用CatalogService中的方法获取子节点数据
		String classes = catalogService.getTopClassNodes();
		
		//调用StreamUtil的方法，将字符串转换成输入流。
		result = StreamUtil.buildByteArrayInputStream(classes);
		
		return "success";
	}
	
	/**
	 * 获取某节点实例的方法。
	 * 
	 * @return 实例数据结果对应的name值。
	 */
	public String getInstances() {
		
		//调用CatalogService中的方法获取该节点实例的数据
		String instances = catalogService.getNodeContent(name);
		
		//调用StreamUtil的方法，将字符串转换成输入流。
		result = StreamUtil.buildByteArrayInputStream(instances);
		
		return "success";
	}
	
	public InputStream getResult() {
		return result;
	}

	public void setResult(InputStream result) {
		this.result = result;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public CatalogService getCatalogService() {
		return catalogService;
	}

	public void setCatalogService(CatalogService catalogService) {
		this.catalogService = catalogService;
	}
	

}

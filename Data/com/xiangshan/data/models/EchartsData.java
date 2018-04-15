package com.xiangshan.data.models;

import java.util.Collection;

import com.xiangshan.data.interfaces.Data;

/**
 * Echarts力向图的数据对象。
 * 
 * @author Rosahen
 * @version 1.0
 */
public class EchartsData implements Data{

	/**
	 * 节点集合
	 */
	private Collection<EchartsNode> nodes;
	
	/**
	 * 边集合
	 */
	private Collection<EchartsLink> links;

	public Collection<EchartsNode> getNodes() {
		return nodes;
	}

	public void setNodes(Collection<EchartsNode> nodes) {
		this.nodes = nodes;
	}

	public Collection<EchartsLink> getLinks() {
		return links;
	}

	public void setLinks(Collection<EchartsLink> links) {
		this.links = links;
	}

	
}

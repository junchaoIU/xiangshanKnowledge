package com.xiangshan.relation.actions;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import com.xiangshan.generic.actions.GenericAction;
import com.xiangshan.history.entities.QueryHistory;
import com.xiangshan.relation.service.interfaces.RelationSearchService;
import com.xiangshan.user.entities.UserInfo;
import com.xiangshan.utils.StreamUtil;

/**
 * 关系检索Action类。用来处理关系检索页面的请求。
 * 
 * @author Rosahen
 * @version 1.0
 */
public class RelationSearchAction extends GenericAction{

	/**
	 * 关系的主语。
	 */
	private String subject;
	
	/**
	 * 关系的宾语
	 */
	private String object;
	
	/**
	 * 页码
	 */
	private int pageNum;
	
	/**
	 * 每页的记录数
	 */
	private int pageSize;
	
	/**
	 * 检索历史类型。
	 */
	private String historyType;
	
	/**
	 * 检索结果的输入流。
	 */
	private InputStream result;
	
	/**
	 * 关系检索Service。
	 */
	private RelationSearchService relationSearchService;
	
	/**
	 * 关系分页检索方法。
	 * 
	 * @return 检索结果对应的name值。
	 */
	public String queryByPage() {
			
		//获取json格式的检索结果字符串
		String jsonData = relationSearchService.queryByPage(subject, object, pageNum, pageSize);
		
		//将结果转成输入流
		result = StreamUtil.buildByteArrayInputStream(jsonData);
		
		//jsonData等于null，不需要记录历史
		if(jsonData!=null&&historyType!=null) {
			UserInfo loginUser = (UserInfo) getSessionMap().get("login_user");
			
			QueryHistory history = new QueryHistory(UUID.randomUUID().toString(),subject,null,
					object,null,null,historyType,new Date(),loginUser.getUserName());
			
			addHistory(history);
			
		}
		
		return "success";
	}
	
	public String query() {
		
		//获取json格式的检索结果字符串
		String jsonData = relationSearchService.query(subject, object);
		
		//将结果转成输入流
		result = StreamUtil.buildByteArrayInputStream(jsonData);
		
		return "success";
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getObject() {
		return object;
	}

	public void setObject(String object) {
		this.object = object;
	}
	
	public int getPageNum() {
		return pageNum;
	}

	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getHistoryType() {
		return historyType;
	}

	public void setHistoryType(String historyType) {
		this.historyType = historyType;
	}

	public InputStream getResult() {
		return result;
	}

	public void setResult(InputStream result) {
		this.result = result;
	}

	public RelationSearchService getRelationSearchService() {
		return relationSearchService;
	}

	public void setRelationSearchService(RelationSearchService relationSearchService) {
		this.relationSearchService = relationSearchService;
	}
	
}

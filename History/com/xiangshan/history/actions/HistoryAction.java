package com.xiangshan.history.actions;

import java.io.InputStream;

import com.xiangshan.generic.actions.GenericAction;
import com.xiangshan.user.entities.UserInfo;
import com.xiangshan.utils.StreamUtil;
/**
 * 检索历史Action类。此类用来处理获取检索历史的请求。
 * 
 * @author Rosahen
 * @version 1.0
 */
public class HistoryAction extends GenericAction{

	private String historyId;
	private String historyType;
	private InputStream result;
	
	
	public String get() {
		
		UserInfo loginUser = (UserInfo) getSessionMap().get("login_user");
		
		String histories = historyService.getHistories(loginUser, historyType);
		
		result = StreamUtil.buildByteArrayInputStream(histories);
		
		return "success";
	}
	
	public String delete() {
		
		String isSucceed = historyService.deleteHistoryById(historyId);
		
		result = StreamUtil.buildByteArrayInputStream(isSucceed);
		
		return "success";
	}
	
	public String empty() {
		UserInfo loginUser = (UserInfo) getSessionMap().get("login_user");
		
		String isSucceed = historyService.emptyHistories(loginUser, historyType);
		
		result = StreamUtil.buildByteArrayInputStream(isSucceed);
		
		return "success";
	}
	
	
	public String getHistoryType() {
		return historyType;
	}

	public void setHistoryType(String historyType) {
		this.historyType = historyType;
	}

	public String getHistoryId() {
		return historyId;
	}

	public void setHistoryId(String historyId) {
		this.historyId = historyId;
	}

	public InputStream getResult() {
		return result;
	}


	public void setResult(InputStream result) {
		this.result = result;
	}
	
}

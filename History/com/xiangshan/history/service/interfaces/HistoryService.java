package com.xiangshan.history.service.interfaces;

import com.xiangshan.history.entities.QueryHistory;
import com.xiangshan.user.entities.UserInfo;

public interface HistoryService{

	String getHistories(UserInfo user, String type);
	
	String emptyHistories(UserInfo user, String type);
	
	String deleteHistoryById(String historyId);
	
	String recordHistory(QueryHistory history);
}

package com.xiangshan.history.dao.interfaces;

import java.util.Collection;

import com.xiangshan.history.entities.QueryHistory;
import com.xiangshan.user.entities.UserInfo;

public interface HistoryDao {

	Collection<QueryHistory> getHistoriesByType(UserInfo user, String type);
	
	boolean deleteHistoryById(String id);
	
	boolean emptyHistories(UserInfo user, String type);
}

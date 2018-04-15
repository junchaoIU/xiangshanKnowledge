package com.xiangshan.history.service.impls;

import java.util.Collection;
import java.util.List;

import com.xiangshan.generic.service.impls.BaseService;
import com.xiangshan.history.dao.interfaces.HistoryDao;
import com.xiangshan.history.entities.QueryHistory;
import com.xiangshan.history.service.interfaces.HistoryService;
import com.xiangshan.user.entities.UserInfo;
import com.xiangshan.utils.JsonUtil;

public class HistoryServiceImpl extends BaseService<QueryHistory> implements HistoryService{

	private HistoryDao historyDao;
	
	
	public HistoryDao getHistoryDao() {
		return historyDao;
	}

	public void setHistoryDao(HistoryDao historyDao) {
		this.historyDao = historyDao;
	}

	@Override
	public String getHistories(UserInfo user, String type) {
		// TODO Auto-generated method stub
		Collection<QueryHistory> histories = historyDao.getHistoriesByType(user, type);
		
		return JsonUtil.formatAsJson(histories);
	}

	@Override
	public String emptyHistories(UserInfo user, String type) {
		// TODO Auto-generated method stub
		boolean result = historyDao.emptyHistories(user, type);
		
		return JsonUtil.formatAsJson(result);
	}

	@Override
	public String deleteHistoryById(String historyId) {
		// TODO Auto-generated method stub
		
		boolean result = historyDao.deleteHistoryById(historyId);
		
		return JsonUtil.formatAsJson(result);
	}

	@Override
	public String recordHistory(QueryHistory history) {
		// TODO Auto-generated method stub
		
		boolean result = true;
		
		List<QueryHistory> histories = (List<QueryHistory>) dao.list(QueryHistory.class);
		
		int index = histories.indexOf(history);
		
		if(index==-1) {
			try{
				dao.save(history);
			}catch(Exception e) {
				e.printStackTrace();
				result = false;
			}
		}else {
			try{
				QueryHistory temp = histories.get(index);
				temp.setCreateDate(history.getCreateDate());
				dao.update(temp);
			}catch(Exception e) {
				e.printStackTrace();
				result = false;
			}
		}
		
		return JsonUtil.formatAsJson(result);
	}

}

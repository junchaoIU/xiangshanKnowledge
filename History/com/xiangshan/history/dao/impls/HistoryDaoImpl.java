package com.xiangshan.history.dao.impls;

import java.util.Collection;

import org.hibernate.Query;

import com.xiangshan.generic.dao.impls.GenericDaoImpl;
import com.xiangshan.history.dao.interfaces.HistoryDao;
import com.xiangshan.history.entities.QueryHistory;
import com.xiangshan.user.entities.UserInfo;

public class HistoryDaoImpl extends GenericDaoImpl<QueryHistory> implements HistoryDao{

	@SuppressWarnings("unchecked")
	@Override
	public Collection<QueryHistory> getHistoriesByType(UserInfo user, String type) {
		// TODO Auto-generated method stub
		try {
			Query query = getCurrentSession().createSQLQuery("select * from query_history where user_name=? and type=? order by create_date asc").addEntity(QueryHistory.class);
			query.setString(0, user.getUserName());
			query.setString(1, type);
			return query.list();
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public boolean deleteHistoryById(String id) {
		// TODO Auto-generated method stub
		try {
			Query query = getCurrentSession().createSQLQuery("delete from query_history where id=?");
			query.setString(0, id);
			if(query.executeUpdate()>0) return true;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean emptyHistories(UserInfo user, String type) {
		// TODO Auto-generated method stub
		try {
			Query query = getCurrentSession().createSQLQuery("delete from query_history where user_name=? and type=?");
			query.setString(0, user.getUserName());
			query.setString(1, type);
			
			if(query.executeUpdate()>0) return true;
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return false;
	}

}

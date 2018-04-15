package com.xiangshan.user.service.impls;

import com.xiangshan.generic.service.impls.BaseService;
import com.xiangshan.user.entities.UserInfo;
import com.xiangshan.user.service.interfaces.UserService;
import com.xiangshan.utils.JsonUtil;

public class UserServiceImpl extends BaseService<UserInfo> implements UserService{

	@Override
	public String register(UserInfo user) {
		// TODO Auto-generated method stub
		UserInfo userInfo = dao.getById(user.getUserName(), UserInfo.class);
		
		if(userInfo==null) {
			
			try{
				dao.save(user);
				return JsonUtil.formatAsJson(true);
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		return JsonUtil.formatAsJson(false);
	}

	@Override
	public String login(UserInfo user) {
		// TODO Auto-generated method stub
		
		UserInfo userInfo = dao.getById(user.getUserName(), UserInfo.class);
		
		if(userInfo!=null) {
			
			if(userInfo.getUserPassword().equals(user.getUserPassword())) {
				
				return JsonUtil.formatAsJson(true);
			}
		}
		return JsonUtil.formatAsJson(false);
	}

	@Override
	public String getUserInfos(UserInfo user) {
		// TODO Auto-generated method stub
		try {
			UserInfo userInfo = dao.getById(user.getUserName(), UserInfo.class);
			return JsonUtil.formatAsJson(userInfo);
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}

	@Override
	public String alterPassword(UserInfo user, String newPassword) {
		// TODO Auto-generated method stub
		
		try {
			if(user.equals(dao.getById(user.getUserName(), UserInfo.class))){
				
				user.setUserPassword(newPassword);
				
				dao.update(user);
				
				return JsonUtil.formatAsJson("true");
			}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
		return JsonUtil.formatAsJson("false");
	}

}

package com.xiangshan.user.service.interfaces;

import com.xiangshan.user.entities.UserInfo;

public interface UserService {

	String register(UserInfo user);
	
	String login(UserInfo user);
	
	String getUserInfos(UserInfo user);
	
	String alterPassword(UserInfo user, String newPassword);
}

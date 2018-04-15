package com.xiangshan.user.actions;

import java.io.InputStream;
import java.util.Date;

import com.xiangshan.generic.actions.GenericAction;
import com.xiangshan.user.entities.UserInfo;
import com.xiangshan.user.service.interfaces.UserService;
import com.xiangshan.utils.StreamUtil;

public class UserAction extends GenericAction{
	
	private String userName;
	
	private String password;
	
	private String newPassword;
	
	private int old;
	
	private String role;
	
	private UserService userService;
	
	private InputStream result;
	
	public String login() {
		UserInfo user = new UserInfo(userName,password);
		String isSucceed = userService.login(user);
		if(isSucceed.equals("true")) {
			getSessionMap().put("login_user", user);
		}
		result = StreamUtil.buildByteArrayInputStream(isSucceed);
		return "success";
	}
	
	public String logout() {
		
		getSessionMap().remove("login_user");
		result = StreamUtil.buildByteArrayInputStream("true");
		
		return "success";
	}
	
	public String register() {
		
		UserInfo user = new UserInfo(userName,password,old,role,new Date());
		
		String isSucceed = userService.register(user);
		
		result = StreamUtil.buildByteArrayInputStream(isSucceed);
		
		return "success";
	}
	
	public String alterPassword() {
		UserInfo online_user = (UserInfo) getSessionMap().get("login_user");
		
		if(online_user!=null) {
			
			UserInfo info = new UserInfo(online_user.getUserName(),password);
			
			String isSucceed = userService.alterPassword(info, newPassword);
			
			result = StreamUtil.buildByteArrayInputStream(isSucceed);
			
		}else {
			result = StreamUtil.buildByteArrayInputStream("修改失败，您还没有登陆!");
		}
		
		return "success";
		
	}
	
	public String getInfos() {
		UserInfo online_user = (UserInfo) getSessionMap().get("login_user");
		if(online_user!=null) {
			String infos = userService.getUserInfos(online_user);
			result = StreamUtil.buildByteArrayInputStream(infos);
		}else {
			result = StreamUtil.buildByteArrayInputStream(null);
		}
		
		return "success";
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	public int getOld() {
		return old;
	}

	public void setOld(int old) {
		this.old = old;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public UserService getUserService() {
		return userService;
	}

	public void setUserService(UserService userService) {
		this.userService = userService;
	}

	public InputStream getResult() {
		return result;
	}

	public void setResult(InputStream result) {
		this.result = result;
	}
	
}

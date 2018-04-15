package com.xiangshan.access.interceptors;

import java.util.Map;

import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.MethodFilterInterceptor;

public class AccessInterceptor extends MethodFilterInterceptor{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1410720244699636702L;

	@Override
	protected String doIntercept(ActionInvocation arg0) throws Exception {
		// TODO Auto-generated method stub
		
		Map<String, Object> session = arg0.getInvocationContext().getSession();
		
		Object loginUser = session.get("login_user");
		
		if(loginUser!=null) {
			
			return arg0.invoke();
		}else {
			
			return "homepage";
		}
	}

}

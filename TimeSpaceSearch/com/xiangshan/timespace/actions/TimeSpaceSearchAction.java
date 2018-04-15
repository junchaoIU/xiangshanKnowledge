package com.xiangshan.timespace.actions;

import java.io.InputStream;
import java.util.Date;
import java.util.UUID;

import com.xiangshan.generic.actions.GenericAction;
import com.xiangshan.history.entities.QueryHistory;
import com.xiangshan.timespace.service.interfaces.TimeSpaceSearchService;
import com.xiangshan.user.entities.UserInfo;
import com.xiangshan.utils.StreamUtil;

/**
 * 时空检索的Action类。用来处理时空检索页面的请求。
 * 
 * @author Rosahen
 * @version 1.0
 */
public class TimeSpaceSearchAction extends GenericAction{

	/**
	 * 检索关键词。
	 */
	private String keyword;
	
	private String mode;
	
	/**
	 * 检索的时间范围。
	 */
	private String time;
	
	/**
	 * 检索的地点范围
	 */
	private String position;
	
	/**
	 * 页码
	 */
	private int pageNum;
	
	/**
	 * 每页的记录数
	 */
	private int pageSize;
	
	/**
	 * 检索历史的类型。
	 */
	private String historyType;
	
	/**
	 * 检索结果的输入流。
	 */
	private InputStream result;
	
	/**
	 * 时空检索Service。
	 */
	private TimeSpaceSearchService timeSpaceSearchService;
	
	
	/**
	 * 时空检索方法。在特定的时间范围检索关键词。
	 * 
	 * @return 检索结果对应的name值。
	 */
	public String query() {
		
		//获取json格式的检索结果字符串
		String jsonData = timeSpaceSearchService.query(keyword, mode);
		
		//将结果转成输入流
		result = StreamUtil.buildByteArrayInputStream(jsonData);
		
		return "success";
	}
	
	/**
	 * 时空分页检索方法。
	 * 
	 * @return 检索结果对应的name值。
	 */
	public String queryByPage() {
		
		String jsonData = null;
		//获取json格式的检索结果字符串
		if(mode.equals("time")) {
			jsonData = timeSpaceSearchService.queryByTimeAndPage(keyword, time, pageNum, pageSize);
		}else if(position!=null){
			jsonData = timeSpaceSearchService.queryByPositionAndPage(keyword, position, pageNum, pageSize);
		}
		//将结果转成输入流
		result = StreamUtil.buildByteArrayInputStream(jsonData);
		
		//jsonData等于null,不需要记录历史
		if(jsonData!=null&&historyType!=null) {
			UserInfo loginUser = (UserInfo) getSessionMap().get("login_user");
			
			String timeSpaceScope = null;
			
			if(mode.equals("time")) timeSpaceScope = time;
			else timeSpaceScope = position;
			
			QueryHistory history = new QueryHistory(UUID.randomUUID().toString(),keyword,null,
					null,null,timeSpaceScope,historyType,new Date(),loginUser.getUserName());
			
			addHistory(history);
			
		}
		return "success";
	}
	
	public String queryByTime() {
		//获取json格式的检索结果字符串
		String jsonData = timeSpaceSearchService.queryByTime(keyword, time);
		
		//将结果转成输入流
		result = StreamUtil.buildByteArrayInputStream(jsonData);
		
		if(jsonData!=null&&historyType!=null) {
			UserInfo loginUser = (UserInfo) getSessionMap().get("login_user");
			
			String timeSpaceScope = null;
			
			if(mode.equals("time")) timeSpaceScope = time;
			else timeSpaceScope = position;
			
			QueryHistory history = new QueryHistory(UUID.randomUUID().toString(),keyword,null,
					null,null,timeSpaceScope,historyType,new Date(),loginUser.getUserName());
			
			addHistory(history);
			
		}

		return "success";
	}
	
	public String queryByPosition() {
		//获取json格式的检索结果字符串
		String jsonData = timeSpaceSearchService.queryByPosition(keyword, position);
		
		//将结果转成输入流
		result = StreamUtil.buildByteArrayInputStream(jsonData);
		
		if(jsonData!=null&&historyType!=null) {
			UserInfo loginUser = (UserInfo) getSessionMap().get("login_user");
			
			String timeSpaceScope = null;
			
			if(mode.equals("time")) timeSpaceScope = time;
			else timeSpaceScope = position;
			
			QueryHistory history = new QueryHistory(UUID.randomUUID().toString(),keyword,null,
					null,null,timeSpaceScope,historyType,new Date(),loginUser.getUserName());
			
			addHistory(history);
			
		}

		return "success";
	}
	
	public String getTimeline() {
		
		String timeline = timeSpaceSearchService.getTimeline(keyword);
		
		//将结果转成输入流
		result = StreamUtil.buildByteArrayInputStream(timeline);
		
		return "success";
	}
	
	public String getPositionLine() {
		
		String positionLine = timeSpaceSearchService.getPositionLine(keyword);
		
		//将结果转成输入流
		result = StreamUtil.buildByteArrayInputStream(positionLine);
		
		return "success";
	}
	
	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
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

	public TimeSpaceSearchService getTimeSpaceSearchService() {
		return timeSpaceSearchService;
	}

	public void setTimeSpaceSearchService(TimeSpaceSearchService timeSpaceSearchService) {
		this.timeSpaceSearchService = timeSpaceSearchService;
	}
	
}

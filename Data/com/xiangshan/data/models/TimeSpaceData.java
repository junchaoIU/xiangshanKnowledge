package com.xiangshan.data.models;

import com.xiangshan.data.interfaces.Data;

public class TimeSpaceData implements Data{

	private String key;
	
	private Data data;

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Data getData() {
		return data;
	}

	public void setData(Data data) {
		this.data = data;
	}
	

}

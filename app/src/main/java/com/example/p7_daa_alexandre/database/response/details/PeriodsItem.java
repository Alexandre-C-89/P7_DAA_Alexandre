package com.example.p7_daa_alexandre.database.response.details;

import com.google.gson.annotations.SerializedName;

public class PeriodsItem{

	@SerializedName("close")
	private Close close;

	@SerializedName("open")
	private Open open;

	public Close getClose(){
		return close;
	}

	public Open getOpen(){
		return open;
	}

	@Override
 	public String toString(){
		return 
			"PeriodsItem{" + 
			"close = '" + close + '\'' + 
			",open = '" + open + '\'' + 
			"}";
		}
}
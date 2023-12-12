package com.example.p7_daa_alexandre.database.response.details;

import com.google.gson.annotations.SerializedName;

public class Close{

	@SerializedName("time")
	private String time;

	@SerializedName("day")
	private int day;

	public String getTime(){
		return time;
	}

	public int getDay(){
		return day;
	}

	@Override
 	public String toString(){
		return 
			"Close{" + 
			"time = '" + time + '\'' + 
			",day = '" + day + '\'' + 
			"}";
		}
}
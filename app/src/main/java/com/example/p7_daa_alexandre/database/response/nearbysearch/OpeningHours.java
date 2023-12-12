package com.example.p7_daa_alexandre.database.response.nearbysearch;

import com.google.gson.annotations.SerializedName;

public class OpeningHours{

	@SerializedName("open_now")
	private boolean openNow;

	public boolean isOpenNow(){
		return openNow;
	}

	@Override
 	public String toString(){
		return 
			"OpeningHours{" + 
			"open_now = '" + openNow + '\'' + 
			"}";
		}
}
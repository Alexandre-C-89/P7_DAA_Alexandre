package com.example.p7_daa_alexandre.database.response.details;

import com.google.gson.annotations.SerializedName;

public class Location{

	@SerializedName("lng")
	private Object lng;

	@SerializedName("lat")
	private Object lat;

	public Object getLng(){
		return lng;
	}

	public Object getLat(){
		return lat;
	}

	@Override
 	public String toString(){
		return 
			"Location{" + 
			"lng = '" + lng + '\'' + 
			",lat = '" + lat + '\'' + 
			"}";
		}
}
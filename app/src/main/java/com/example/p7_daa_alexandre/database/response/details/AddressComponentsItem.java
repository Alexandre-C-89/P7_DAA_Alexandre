package com.example.p7_daa_alexandre.database.response.details;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class AddressComponentsItem{

	@SerializedName("types")
	private List<String> types;

	@SerializedName("short_name")
	private String shortName;

	@SerializedName("long_name")
	private String longName;

	public List<String> getTypes(){
		return types;
	}

	public String getShortName(){
		return shortName;
	}

	public String getLongName(){
		return longName;
	}

	@Override
 	public String toString(){
		return 
			"AddressComponentsItem{" + 
			"types = '" + types + '\'' + 
			",short_name = '" + shortName + '\'' + 
			",long_name = '" + longName + '\'' + 
			"}";
		}
}
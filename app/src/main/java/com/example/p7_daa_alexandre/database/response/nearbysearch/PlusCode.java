package com.example.p7_daa_alexandre.database.response.nearbysearch;

import com.google.gson.annotations.SerializedName;

public class PlusCode{

	@SerializedName("compound_code")
	private String compoundCode;

	@SerializedName("global_code")
	private String globalCode;

	public String getCompoundCode(){
		return compoundCode;
	}

	public String getGlobalCode(){
		return globalCode;
	}

	@Override
 	public String toString(){
		return 
			"PlusCode{" + 
			"compound_code = '" + compoundCode + '\'' + 
			",global_code = '" + globalCode + '\'' + 
			"}";
		}
}
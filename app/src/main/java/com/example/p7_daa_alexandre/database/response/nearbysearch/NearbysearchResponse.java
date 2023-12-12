package com.example.p7_daa_alexandre.database.response.nearbysearch;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class NearbysearchResponse{

	@SerializedName("html_attributions")
	private List<Object> htmlAttributions;

	@SerializedName("results")
	private List<ResultsItem> results;

	@SerializedName("status")
	private String status;

	public List<Object> getHtmlAttributions(){
		return htmlAttributions;
	}

	public List<ResultsItem> getResults(){
		return results;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"NearbysearchResponse{" + 
			"html_attributions = '" + htmlAttributions + '\'' + 
			",results = '" + results + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
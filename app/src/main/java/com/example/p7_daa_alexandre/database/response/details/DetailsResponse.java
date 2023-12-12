package com.example.p7_daa_alexandre.database.response.details;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DetailsResponse{

	@SerializedName("result")
	private Result result;

	@SerializedName("html_attributions")
	private List<Object> htmlAttributions;

	@SerializedName("status")
	private String status;

	public Result getResult(){
		return result;
	}

	public List<Object> getHtmlAttributions(){
		return htmlAttributions;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"DetailsResponse{" + 
			"result = '" + result + '\'' + 
			",html_attributions = '" + htmlAttributions + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}
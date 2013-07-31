package com.root.testproject.model;

import java.util.Comparator;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Data implements Comparator<Data>{
	public List<Data> data;
	@SerializedName("id")
	public int id;
	
	@SerializedName("name")
	public String name;
	
	@SerializedName("url")
	public String url;
	
	@SerializedName("make_icon")
	public String makeIcon;

	@Override
	public int compare(Data lhs, Data rhs) {
		int value = 0; 
		if (lhs.id > rhs.id) {
			value = 1;
		} else if (lhs.id < rhs.id) {
			value = -1;
		} else if (lhs.id == rhs.id) { 
			value = 0;
	    }
		return value;
	}

}

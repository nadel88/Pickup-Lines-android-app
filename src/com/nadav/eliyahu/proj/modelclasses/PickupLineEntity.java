package com.nadav.eliyahu.proj.modelclasses;

import android.annotation.SuppressLint;

@SuppressLint("NewApi")
public class PickupLineEntity {

	private long id;
	private String description;
	private String category;
	private int lang_flag; //set 1 for eng and 0 for heb
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		if(id>=1)
			this.id = id;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		if(!description.isEmpty())
			this.description = description;
	}
	
	public String getCategory() {
		return category;
	}
	
	public void setCategory(String category) {
		if(!category.isEmpty())
			this.category = category;
	}
	
	public int getLang_flag() {
		return lang_flag;
	}
	
	public void setLang_flag(int lang_flag) {
		if(lang_flag == 0 || lang_flag == 1)
			this.lang_flag = lang_flag;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "LINE ID: "+ this.getId()+"/n"+"LINE CATEGORY: "+this.getCategory()+"/n"+"LINE DESCRIPTION: "+this.getDescription();
	}
	
	
}

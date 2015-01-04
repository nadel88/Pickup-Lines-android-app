package com.nadav.eliyahu.proj.modelclasses;

import android.annotation.SuppressLint;

@SuppressLint("NewApi")
public class FavouritListEntity {
	
	private long id;
	private String description;
	private String category;
	
	
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
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "LINE ID: "+ this.getId()+"\n"+"LINE CATEGORY: "+this.getCategory()+"\n"+"LINE DESCRIPTION: "+this.getDescription();
	}
	
}

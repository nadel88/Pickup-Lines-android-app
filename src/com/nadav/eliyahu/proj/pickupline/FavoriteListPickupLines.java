package com.nadav.eliyahu.proj.pickupline;

import java.util.ArrayList;
import java.util.List;

public class FavoriteListPickupLines {
	
	 private static FavoriteListPickupLines instance = null;
	 
	 public static FavoriteListPickupLines getInstance() {
	      if(instance == null) {
	         instance = new FavoriteListPickupLines();
	      }
	      return instance;
	   }
	 
	
	//private members
	private ArrayList<String> favorites = new ArrayList<String>();

	
	//default constructor
	public FavoriteListPickupLines()
	{	
		setFavorites(favorites);
		
	}

	
	//setters & getters
	public ArrayList<String> getFavorites() {
		return favorites;
	}

	public void setFavorites(ArrayList<String> favorites) {
		this.favorites = favorites;
	}
	
	public List<String> deletePickupLineFromFavorites(ArrayList<String> fav , long index)
	{
		fav.remove(index);
		return fav;
		
	}
	
	public void addPickupLineToFavorites(ArrayList<String> fav , String pickupLine)
	{
		if(pickupLine!=null)
		{
			fav.add(pickupLine);
		}
		
		
	}
	
	
	
	
}

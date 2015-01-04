package com.nadav.eliyahu.proj.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nadav.eliyahu.proj.modelclasses.FavouritListEntity;

public class FavouritsDataSource {
	
public static final String LOGTAG="PICKAPPLINE";
	
	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;
	
	private static final String[] allColumns = {
		PickAppLineDBOpenHelper.COLUMN_ID,
		PickAppLineDBOpenHelper.COLUMN_CATEGORY,
		PickAppLineDBOpenHelper.COLUMN_DESC,
	};
	
	public FavouritsDataSource(Context context) {
		dbhelper = new PickAppLineDBOpenHelper(context);
	}
	
	public void open(){
		Log.i(LOGTAG,"Database opened");
		database = dbhelper.getWritableDatabase();
	}
	
	public void close(){
		Log.i(LOGTAG,"Database closed");
		dbhelper.close();
	}
	
	public FavouritListEntity create(FavouritListEntity favourit){
		ContentValues values = new ContentValues();
		values.put(PickAppLineDBOpenHelper.COLUMN_CATEGORY, favourit.getCategory());
		values.put(PickAppLineDBOpenHelper.COLUMN_DESC, favourit.getDescription());
		long insertid = database.insert(PickAppLineDBOpenHelper.TABLE_FAVOURITS, null, values);
		favourit.setId(insertid);
		return favourit;
	}
	
	public List<FavouritListEntity>findAll() {
		List<FavouritListEntity> favourits = new ArrayList<FavouritListEntity>();
		
		Cursor cursor = database.query(PickAppLineDBOpenHelper.TABLE_FAVOURITS, allColumns, 
				null, null, null, null, null);
		Log.i(LOGTAG,"Returned " + cursor.getCount() + "rows");
		if(cursor.getCount() > 0){
			while(cursor.moveToNext()) {
				FavouritListEntity favourit = new FavouritListEntity();
				favourit.setId(cursor.getLong(cursor.getColumnIndex(PickAppLineDBOpenHelper.COLUMN_ID)));
				favourit.setCategory(cursor.getString(cursor.getColumnIndex(PickAppLineDBOpenHelper.COLUMN_CATEGORY)));
				favourit.setDescription(cursor.getString(cursor.getColumnIndex(PickAppLineDBOpenHelper.COLUMN_DESC)));
				favourits.add(favourit);
			}
		}
		return favourits;
	}
	
	public void addToFavourites(FavouritsDataSource datasource,Context context,String category,String description) {
	    datasource.open();
		FavouritListEntity favourit = new FavouritListEntity();
		favourit.setCategory(category);
		favourit.setDescription(description);
		favourit = datasource.create(favourit);
    	Log.i(LOGTAG,"Line added to favourit list " + favourit.getId());
    		
    }
	
	public boolean deleteFromFavourites(FavouritListEntity favouriteItem)
	{
		String where = PickAppLineDBOpenHelper.COLUMN_ID + "=" + favouriteItem.getId();
		int result = database.delete(PickAppLineDBOpenHelper.TABLE_FAVOURITS, where, null);
		return(result == 1);
		
	}
	
}

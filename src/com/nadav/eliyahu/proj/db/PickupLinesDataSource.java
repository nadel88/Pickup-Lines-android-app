package com.nadav.eliyahu.proj.db;

import java.util.ArrayList;
import java.util.List;

import com.nadav.eliyahu.proj.modelclasses.PickupLineEntity;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PickupLinesDataSource {

	public static final String LOGTAG="PICKAPPLINE";
	
	SQLiteOpenHelper dbhelper;
	SQLiteDatabase database;
	
	private static final String[] allColumns = {
		PickAppLineDBOpenHelper.COLUMN_ID,
		PickAppLineDBOpenHelper.COLUMN_CATEGORY,
		PickAppLineDBOpenHelper.COLUMN_DESC,
		PickAppLineDBOpenHelper.COLUMN_LANG
	};
	
	public PickupLinesDataSource(Context context) {
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
	
	public PickupLineEntity create(PickupLineEntity pickupLine){
		ContentValues values = new ContentValues();
		values.put(PickAppLineDBOpenHelper.COLUMN_CATEGORY, pickupLine.getCategory());
		values.put(PickAppLineDBOpenHelper.COLUMN_DESC, pickupLine.getDescription());
		values.put(PickAppLineDBOpenHelper.COLUMN_LANG, pickupLine.getLang_flag());
		long insertid = database.insert(PickAppLineDBOpenHelper.TABLE_PICKUPLINES, null, values);
		pickupLine.setId(insertid);
		return pickupLine;
	}
	
	public List<PickupLineEntity>findAll() {
		List<PickupLineEntity> pickupLines = new ArrayList<PickupLineEntity>();
		
		Cursor cursor = database.query(PickAppLineDBOpenHelper.TABLE_PICKUPLINES, allColumns, 
				null, null, null, null, null);
		Log.i(LOGTAG,"Returned " + cursor.getCount() + "rows");
		if(cursor.getCount() > 0){
			while(cursor.moveToNext()) {
				PickupLineEntity pickupLine = new PickupLineEntity();
				pickupLine.setId(cursor.getLong(cursor.getColumnIndex(PickAppLineDBOpenHelper.COLUMN_ID)));
				pickupLine.setCategory(cursor.getString(cursor.getColumnIndex(PickAppLineDBOpenHelper.COLUMN_CATEGORY)));
				pickupLine.setDescription(cursor.getString(cursor.getColumnIndex(PickAppLineDBOpenHelper.COLUMN_DESC)));
				pickupLine.setLang_flag(cursor.getInt(cursor.getColumnIndex(PickAppLineDBOpenHelper.COLUMN_LANG)));
				pickupLines.add(pickupLine);
			}
		}
		return pickupLines;
	}
	
	public String findByCategory(String category){
		PickupLineEntity pickupLine = new PickupLineEntity();
		Cursor cursor = database.query(PickAppLineDBOpenHelper.TABLE_PICKUPLINES, allColumns, 
				"category=" +"'"+category+"'", null, null, null, "RANDOM()","1");
		Log.i(LOGTAG,"Returned row number " + cursor.getColumnIndex(allColumns[0]));
		if(cursor.getCount() > 0 && cursor.moveToFirst()){
			pickupLine.setId(cursor.getLong(cursor.getColumnIndex(PickAppLineDBOpenHelper.COLUMN_ID)));
			pickupLine.setCategory(cursor.getString(cursor.getColumnIndex(PickAppLineDBOpenHelper.COLUMN_CATEGORY)));
			pickupLine.setDescription(cursor.getString(cursor.getColumnIndex(PickAppLineDBOpenHelper.COLUMN_DESC)));
			pickupLine.setLang_flag(cursor.getInt(cursor.getColumnIndex(PickAppLineDBOpenHelper.COLUMN_LANG)));
			
		}
		return pickupLine.getDescription();
	}
}

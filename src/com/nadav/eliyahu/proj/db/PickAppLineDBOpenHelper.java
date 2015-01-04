package com.nadav.eliyahu.proj.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class PickAppLineDBOpenHelper extends SQLiteOpenHelper {

	private static final String LOGTAG = "PICKAPPLINE";

	private static final String DATABASE_NAME = "pickuplines.db";
	private static final int DATABASE_VERSION = 1;
	
	public static final String TABLE_PICKUPLINES = "PickupLines";
	public static final String TABLE_FAVOURITS = "favouritLines";
	public static final String COLUMN_ID = "lineId";
	public static final String COLUMN_CATEGORY = "category";
	public static final String COLUMN_DESC = "description";
	public static final String COLUMN_LANG = "lang";
	
	
	private static final String TABLE_CREATE_LINES = 
			"CREATE TABLE IF NOT EXISTS "+ TABLE_PICKUPLINES +" (" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_CATEGORY + " TEXT, " +
			COLUMN_DESC +" TEXT, " +
			COLUMN_LANG + " INTEGER " +
			")";
	
	private static final String TABLE_CREATE_FAV = 
			"CREATE TABLE IF NOT EXISTS " + TABLE_FAVOURITS + " (" +
			COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
			COLUMN_CATEGORY + " TEXT, " +
			COLUMN_DESC + " TEXT " +
			")";

	
	
	
	public PickAppLineDBOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		// TODO Auto-generated method stub
		db.execSQL(TABLE_CREATE_LINES);
		db.execSQL(TABLE_CREATE_FAV);
		Log.i(LOGTAG,"table has been created");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		db.execSQL("DROP TABLE IF EXISTS "+ TABLE_PICKUPLINES);
		onCreate(db);
	}
}

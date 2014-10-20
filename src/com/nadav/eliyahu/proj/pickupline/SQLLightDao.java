package com.nadav.eliyahu.proj.pickupline;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLLightDao extends SQLiteOpenHelper {
		
	public SQLLightDao(Context context) {
		super(context, "PickupLineDB", null, 1);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL("CREATE TABLE IF NOT EXISTS PickupLines(Id INT, Category VARCHAR, Line VARCHAR );");
		db.execSQL("CREATE TABLE IF NOT EXISTS SaveFavorites(Id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, Category VARCHAR, Line VARCHAR);");
		db.execSQL("INSERT INTO PickupLines VALUES(1,'street','Do you work at SubWay... because you just gave me a footlong');");
		db.execSQL("INSERT INTO PickupLines VALUES(2,'club','Im new in town -- could you give me directions to your apartment?');");
		db.execSQL("INSERT INTO PickupLines VALUES(3,'pub','Sure I could buy you a drink, but id be jealous of the glass');");
		db.execSQL("INSERT INTO PickupLines VALUES(4,'street','If i could rearrange the alphabet id put U and I together.');");
		db.execSQL("INSERT INTO PickupLines VALUES(5,'casual','Do you work at SubWay... because you just gave me a footlong');");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		
	}
	
	public Cursor getData(String category){
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from PickupLines where Category=" + "'" + category + "'"+"ORDER BY RANDOM() LIMIT 1", null );
	      return res;
	}
	
	public boolean deleteFromFavorites(int id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		//db.execSQL("DELETE FROM SaveFavorites WHERE id="+id+";");
		if( db.delete("saveFavorites", "Id="+id, null)>0)
			return true;
		else return false;
	}
	
	public boolean deleteAllFromFavorites()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		//db.execSQL("DELETE FROM SaveFavorites WHERE id="+id+";");
		return db.delete("saveFavorites", null, null)>0;
	}
	
	
	public void addToFavoritesDB(int id , String Category , String Line)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("INSERT INTO SaveFavorites VALUES("+id+ "," + "'" + Category + "'" + "," + "'" + Line + "');");
	}
	
	public ArrayList<String> getDataFromSaveList(ArrayList<String>alist , int size) //no arguments and type return = Cursor
	{
		 SQLiteDatabase db = this.getReadableDatabase();
		 Cursor res = db.rawQuery("select * from SaveFavorites", null);
		 res.moveToFirst();
		 alist = listSyncAfterClose(alist, res , size); //
		 return alist; //res
	}

	public int getNumberOfRows() {
		
		 SQLiteDatabase db = this.getReadableDatabase();
		 Cursor res = db.rawQuery("select * from SaveFavorites", null);
		return res.getCount();
	}
	
	public ArrayList<String> listSyncAfterClose(ArrayList<String>alist , Cursor c , int size)
	 {
			for(int index = 0 ; index<size; index++)
			{
				String tempStr = c.getString(c.getColumnIndex("Line"));
				alist.add(tempStr);
				c.moveToNext();
				
			}
			
			return alist;
	 }
	
	

	
	
	
	 
	
	
	
	
}

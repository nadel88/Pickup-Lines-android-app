package com.nadav.eliyahu.proj.pickupline;

import java.util.ArrayList;
import java.util.Iterator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
/**
 * This is the DataBase class that holds the data and the methods that help access it .
 * This class extends the @see SQLiteOpenHelper.
 * @author nadav
 *
 */
public class SQLLightDao extends SQLiteOpenHelper {
	
	/**
	 * This is the default constructor.
	 * @param context
	 */
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
	
	//utility methods
	
	/**
	 * This method retrieve the data for the pickup line view to be presented randomly according to categories.
	 * @param category The category of the pickup line.
	 * @return a Cursor object with the results from the query.
	 */
	public Cursor getData(String category){
	      SQLiteDatabase db = this.getReadableDatabase();
	      Cursor res =  db.rawQuery( "select * from PickupLines where Category=" + "'" + category + "'"+"ORDER BY RANDOM() LIMIT 1", null );
	      return res;
	}
	
	/**
	 * This method deletes from the favorites table according to id
	 * @param id the identifier of the pickup line in the database.
	 * @return boolean value to state that the query succeed or not. 
	 */
	public boolean deleteFromFavorites(int id)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		//db.execSQL("DELETE FROM SaveFavorites WHERE id="+id+";");
		if( db.delete("saveFavorites", "Id="+id, null)>0)
			return true;
		else return false;
	}
	/**
	 * This method deletes every record out of the favorites table.
	 * @return a boolean value to state that the query succeed or not.
	 */
	public boolean deleteAllFromFavorites()
	{
		SQLiteDatabase db = this.getWritableDatabase();
		//db.execSQL("DELETE FROM SaveFavorites WHERE id="+id+";");
		return db.delete("saveFavorites", null, null)>0;
	}
	
	/**
	 * This method add a record to the favorites table with parameters id , category and the pickup line 
	 * @param id the id is the identifier of the pickup line.
	 * @param Category the Category is the category according to which the pickup line is sorted.
	 * @param Line the Line is the pickup line itself as a String.
	 */
	public void addToFavoritesDB(int id , String Category , String Line)
	{
		SQLiteDatabase db = this.getWritableDatabase();
		db.execSQL("INSERT INTO SaveFavorites VALUES("+id+ "," + "'" + Category + "'" + "," + "'" + Line + "');");
	}
	
	/**
	 * 
	 * @param alist
	 * @param size
	 * @return
	 */
	public ArrayList<String> getDataFromSaveList(ArrayList<String>alist , int size) //no arguments and type return = Cursor
	{
		 SQLiteDatabase db = this.getReadableDatabase();
		 Cursor res = db.rawQuery("select * from SaveFavorites", null);
		 res.moveToFirst();
		 alist = listSyncAfterClose(alist, res , size); //
		 return alist; //res
	}

	/**
	 * 
	 * @return
	 */
	public int getNumberOfRows() {
		
		 SQLiteDatabase db = this.getReadableDatabase();
		 Cursor res = db.rawQuery("select * from SaveFavorites", null);
		return res.getCount();
	}
	/**
	 * 
	 * @param alist
	 * @param c
	 * @param size
	 * @return
	 */
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
	/**
	 * 
	 * @param al
	 * @param Category
	 */
	public void ArrayListToDB(ArrayList<String> al,String Category)
	{
		int i =0;
		for(Iterator<String>iter = al.iterator();iter.hasNext();i++)
		{
			addToFavoritesDB(i, Category, iter.next());
		}
		
	}
	/**
	 * 
	 * @param id
	 * @return
	 */
	public String getCategory(int id)
	{
		SQLiteDatabase db = this.getReadableDatabase();
		 Cursor res = db.rawQuery("select Category from SaveFavorites where id="+id, null);
		 res.moveToFirst();
		 String s = res.getString(res.getColumnIndex("Category"));
		 return s;
	}

	
	
	
	 
	
	
	
	
}

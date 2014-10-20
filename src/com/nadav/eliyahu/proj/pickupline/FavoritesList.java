package com.nadav.eliyahu.proj.pickupline;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

/**
 * This Activity implements a favorite list of pickup line represented as a ListView object
 * The List is being populated every time from the database and with the help of two array list to maintain it.
 * 
 * @author Nadav ELiyahu.
 *
 */
@SuppressLint({ "ShowToast", "ViewHolder" })
@TargetApi(Build.VERSION_CODES.HONEYCOMB)
public class FavoritesList extends Activity  
{
	
	int ListSize;
	int toDelete;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites_list);
		
		ArrayList<String> favorites = new ArrayList<String>();
		final ListView lv = (ListView)findViewById(R.id.listViewFavorites);
		ImageButton binButton = (ImageButton)findViewById(R.id.imageButtonBin);
		
		//animation for when an item is deleted
		final Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
		fadeOut.reset();
		fadeOut.setFillAfter(true);
		
		//use the Data Base and utility lists
		final SQLLightDao sqldao = new SQLLightDao(this);
		int limit_size = sqldao.getNumberOfRows();
		favorites = sqldao.getDataFromSaveList(favorites , limit_size);
		//copy to temp list for further use
		final ArrayList<String> TempList = new ArrayList<String>(favorites);
		
		//listener for when the user select an item from the list 
		//stores the position of the item to show it in another activity
		lv.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(FavoritesList.this,ShowFavoriteLine.class);				
				intent.putExtra("pickupline",TempList.get(position));
				startActivity(intent);
				
			}
		});
		
		//listener for long click in order to delete the item
		lv.setOnItemLongClickListener(new OnItemLongClickListener() 
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id)
			{	
				//store position of the item that was long clicked
					toDelete = position;				
					int countPos = position+1;
					Toast.makeText(FavoritesList.this, "pickup line "+countPos+" was selected", 4000).show();														
				return true;
			}
		});
		
		//the button that perform the delete action
		binButton.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{
				//use of the temp list to maintain the listView
				TempList.remove(toDelete);	
				//get the correct item to animate on
				lv.getChildAt(toDelete).startAnimation(fadeOut);
				//show loading dialog
				ProgressDialog dialog = ProgressDialog.show(FavoritesList.this, "removing pickup line", 
		             "Loading. Please wait...", true);
				//new thread to commit all changes on to the database and start the same activity again (sort of refresh)
				new Thread(new Runnable(){
						@Override
						public void run() {
							try {
								String categoryName = sqldao.getCategory(toDelete);
								sqldao.deleteAllFromFavorites();
								sqldao.ArrayListToDB(TempList, categoryName);
								Thread.sleep(2000);
								Intent intent = new Intent(FavoritesList.this , FavoritesList.class);
								startActivity(intent);
								finish();
							} catch (InterruptedException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							
						}
						
					}).start();											
				}
		});
		
		//performing the transformation from the arraylist to viewlist with the adapter.
		try
		{
			final StableArrayAdapter adapter = new StableArrayAdapter(this,
			        android.R.layout.simple_list_item_activated_1, favorites);
			    lv.setAdapter(adapter);
			    ListSize = favorites.size();
		}
		catch(NullPointerException e)
		{
			Log.d("CustomExceptionLog", "stableArrayAdapter has faild due to nullpointer exception");
		}
		
	}
	
	//when the user press back on the device the activity would terminate and the user would be brought to the main activity
	@Override
	public void onBackPressed() {
		finish();		
	}
	
	/**
	 * This private class is an implementation of a custom adapter to show the ListView from ArrayList.
	 * This class uses @see HashMap to map every object from the ArrayList to the ListView with an id.
	 * @author Nadav ELiyahu.
	 *
	 */
	//private class to implement custom adapter .
	private class StableArrayAdapter extends ArrayAdapter<String> 
	{

	    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

	    /**
	     * This method creates the mapping for the adapter.
	     * @param context The context of this class
	     * @param textViewResourceId The resource of the ListView.
	     * @param objects The List or ArrayList.
	     */
	    public StableArrayAdapter(Context context, int textViewResourceId,
	        List<String> objects) 
	    {
	      super(context, textViewResourceId, objects);
	      for (int i = 0; i < objects.size(); ++i) 
	      {
	        mIdMap.put(objects.get(i), i);
	      }
	    }	    	   
	}

}

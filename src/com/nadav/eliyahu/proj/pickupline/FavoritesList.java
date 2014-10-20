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
		
		final Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
		fadeOut.reset();
		fadeOut.setFillAfter(true);
		
		final SQLLightDao sqldao = new SQLLightDao(this);
		int limit_size = sqldao.getNumberOfRows();
		favorites = sqldao.getDataFromSaveList(favorites , limit_size);
		
		final List<String> TempList = new ArrayList<String>(favorites);
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(FavoritesList.this,ShowFavoriteLine.class);				
				intent.putExtra("pickupline",TempList.get(position));
				startActivity(intent);
				
			}
		});
		
		lv.setOnItemLongClickListener(new OnItemLongClickListener() 
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id)
			{					
					toDelete = position;
					int countPos = position+1;
					Toast.makeText(FavoritesList.this, "pickup line "+countPos+" was selected", 4000).show();														
				return true;
			}
		});
		
		binButton.setOnClickListener(new OnClickListener() 
		{
			
			@Override
			public void onClick(View v) 
			{	

				if(toDelete!= lv.getLastVisiblePosition())
				{
					//some logic here... to solve matching numbers between DB and ListView row numbers
				}
				if(sqldao.deleteFromFavorites(toDelete))
				{	
					lv.getChildAt(toDelete).startAnimation(fadeOut);
					//Toast.makeText(FavoritesList.this, "pickup line "+toDelete+" was deleted", 4000).show();
					ProgressDialog dialog = ProgressDialog.show(FavoritesList.this, "removing pickup line", 
		                    "Loading. Please wait...", true);
					new Thread(new Runnable(){

						@Override
						public void run() {
							try {
								
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
				else
				{
					sqldao.deleteAllFromFavorites();
					Toast.makeText(FavoritesList.this, "error deleting from DB", 4000).show();		
				}
			}
		});
		
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
	
	@Override
	public void onBackPressed() {
		finish();		
	}
		  
	private class StableArrayAdapter extends ArrayAdapter<String> 
	{

	    HashMap<String, Integer> mIdMap = new HashMap<String, Integer>();

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

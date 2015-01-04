package com.nadav.eliyahu.proj.pickupline;

import java.util.ArrayList;
import java.util.List;

import com.nadav.eliyahu.proj.db.FavouritsDataSource;
import com.nadav.eliyahu.proj.modelclasses.FavouritListEntity;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
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
public class FavoritesList extends ListActivity  
{
	
	FavouritListEntity favouritItem ;
	FavouritsDataSource datasource = new FavouritsDataSource(this);
	public static final String LOGTAG="PICKAPPLINE";
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites_list);
		
		
		
		final ListView lv = (ListView)findViewById(android.R.id.list);
		ImageButton binButton = (ImageButton)findViewById(R.id.imageButtonBin);
		
		//animation for when an item is deleted
		final Animation fadeOut = AnimationUtils.loadAnimation(this, R.anim.fade_out);
		fadeOut.reset();
		fadeOut.setFillAfter(true);
		
		
		datasource.open();
		
		List<FavouritListEntity> favourits  = datasource.findAll();
		
		final List<FavouritListEntity> TempList = new ArrayList<FavouritListEntity>(favourits);
		
		ArrayAdapter<FavouritListEntity>adapter = new ArrayAdapter<FavouritListEntity>(this,
				android.R.layout.simple_list_item_1,favourits);
		setListAdapter(adapter);
		
		
		lv.setOnItemClickListener(new OnItemClickListener() {
			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(FavoritesList.this,ShowFavoriteLine.class);				
				intent.putExtra("pickupline",TempList.get(position).getDescription());
				startActivity(intent);
				datasource.close();
				finish();
				
			}
		});
		
		//listener for long click in order to delete the item
		lv.setOnItemLongClickListener(new OnItemLongClickListener() 
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id)
			{
					int pos = position+1;
					favouritItem = (FavouritListEntity) parent.getItemAtPosition(position);
					Toast.makeText(FavoritesList.this, "pickup line "+pos+" was selected", 4000).show();														
				return true;
			}
		});
		
		
		binButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//show loading dialog
				ProgressDialog dialog = ProgressDialog.show(FavoritesList.this, "removing pickup line", 
		             "Loading. Please wait...", true);
				//new thread to commit all changes on to the database and start the same activity again (sort of refresh)
				new Thread(new Runnable(){
						@Override
						public void run() {
							try {
								if(datasource.deleteFromFavourites(favouritItem)){setResult(-1);}
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
		
		
		
	}
	
	//when the user press back on the device the activity would terminate and the user would be brought to the main activity
	@Override
	public void onBackPressed() {
		startActivity(new Intent(FavoritesList.this , MainActivity.class));
		finish();		
	}
	
	
	 @Override
	    protected void onPause() {
	    	// TODO Auto-generated method stub
	    	super.onPause();
	    	datasource.close();
	    }
	    
	    @Override
	    protected void onResume() {
	    	// TODO Auto-generated method stub
	    	super.onResume();
	    	datasource.open();
	    }
	    

}

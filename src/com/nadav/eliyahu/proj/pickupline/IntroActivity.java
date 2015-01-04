package com.nadav.eliyahu.proj.pickupline;

import java.util.List;

import com.nadav.eliyahu.proj.db.PickupLinesDataSource;
import com.nadav.eliyahu.proj.modelclasses.FavouritListEntity;
import com.nadav.eliyahu.proj.modelclasses.PickupLineEntity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

public class IntroActivity extends Activity 
{

	
	public static final String LOGTAG="PICKAPPLINE";
	PickupLinesDataSource datasource;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) 
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);
        datasource = new PickupLinesDataSource(this);
        datasource.open();
        
        new Thread(new Runnable(){

			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					Thread.sleep(5000);
					createData();
					startActivity(new Intent(IntroActivity.this,FacebookLogActivity.class));
					finish();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
        	
        }).start();
        
        
			
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
    
    private void createData() {
    	List<PickupLineEntity> db  = datasource.findAll();
    	if(db.size()==0)
    	{
	    	PickupLineEntity pickupLine = new PickupLineEntity();
	    	pickupLine.setCategory("street");
	    	pickupLine.setDescription("Do you work at SubWay... because you just gave me a footlong");
	    	pickupLine.setLang_flag(1);
	    	pickupLine = datasource.create(pickupLine);
	    	Log.i(LOGTAG,"Line created with id " + pickupLine.getId());
	    	
	    	pickupLine = new PickupLineEntity();
	    	pickupLine.setCategory("street");
	    	pickupLine.setDescription("Hey, do you believe in love on first sight or should i pass by you again");
	    	pickupLine.setLang_flag(1);
	    	pickupLine = datasource.create(pickupLine);
	    	Log.i(LOGTAG,"Line created with id " + pickupLine.getId());
	    	
	    	pickupLine = new PickupLineEntity();
	    	pickupLine.setCategory("street");
	    	pickupLine.setDescription("If i could rearrange the alphabet id put U and I together");
	    	pickupLine.setLang_flag(1);
	    	pickupLine = datasource.create(pickupLine);
	    	Log.i(LOGTAG,"Line created with id " + pickupLine.getId());
	    	
	    	pickupLine = new PickupLineEntity();
	    	pickupLine.setCategory("club");
	    	pickupLine.setDescription("Is this dance part of eternal life? Cause this time with you is the greatest gift");
	    	pickupLine.setLang_flag(1);
	    	pickupLine = datasource.create(pickupLine);
	    	Log.i(LOGTAG,"Line created with id " + pickupLine.getId());
	    	
	    	pickupLine = new PickupLineEntity();
	    	pickupLine.setCategory("club");
	    	pickupLine.setDescription("Hey I just realized this but... you look a lot like my next dance partner");
	    	pickupLine.setLang_flag(1);
	    	pickupLine = datasource.create(pickupLine);
	    	Log.i(LOGTAG,"Line created with id " + pickupLine.getId());
	    	
	    	pickupLine = new PickupLineEntity();
	    	pickupLine.setCategory("club");
	    	pickupLine.setDescription("If a fat man puts you in a bag at night, dont worry I told Santa I wanted to dance with you for Christmas");
	    	pickupLine.setLang_flag(1);
	    	pickupLine = datasource.create(pickupLine);
	    	Log.i(LOGTAG,"Line created with id " + pickupLine.getId());
	    	
	    	pickupLine = new PickupLineEntity();
	    	pickupLine.setCategory("club");
	    	pickupLine.setDescription("The word of the day is legs. Lets go back to my place and spread the word");
	    	pickupLine.setLang_flag(1);
	    	pickupLine = datasource.create(pickupLine);
	    	Log.i(LOGTAG,"Line created with id " + pickupLine.getId());
	    	
	    	pickupLine = new PickupLineEntity();
	    	pickupLine.setCategory("pub");
	    	pickupLine.setDescription("This isnt a beer belly, its a fuel tank for my love machine");
	    	pickupLine.setLang_flag(1);
	    	pickupLine = datasource.create(pickupLine);
	    	Log.i(LOGTAG,"Line created with id " + pickupLine.getId());
	    	
	    	pickupLine = new PickupLineEntity();
	    	pickupLine.setCategory("pub");
	    	pickupLine.setDescription("Heres 50 euro’s Drink until Im good looking and then come talk to me");
	    	pickupLine.setLang_flag(1);
	    	pickupLine = datasource.create(pickupLine);
	    	Log.i(LOGTAG,"Line created with id " + pickupLine.getId());
	    	
	    	pickupLine = new PickupLineEntity();
	    	pickupLine.setCategory("pub");
	    	pickupLine.setDescription("Hey You owe me a drink. I dropped mine when you walked by");
	    	pickupLine.setLang_flag(1);
	    	pickupLine = datasource.create(pickupLine);
	    	Log.i(LOGTAG,"Line created with id " + pickupLine.getId());
	    	
	    	pickupLine = new PickupLineEntity();
	    	pickupLine.setCategory("pub");
	    	pickupLine.setDescription("Do you like blow jobs or sex on the beach? I’m talking cocktails of course");
	    	pickupLine.setLang_flag(1);
	    	pickupLine = datasource.create(pickupLine);
	    	Log.i(LOGTAG,"Line created with id " + pickupLine.getId());
	    	
	    	pickupLine = new PickupLineEntity();
	    	pickupLine.setCategory("casual");
	    	pickupLine.setDescription("Do you work at SubWay... because you just gave me a footlong");
	    	pickupLine.setLang_flag(1);
	    	pickupLine = datasource.create(pickupLine);
	    	Log.i(LOGTAG,"Line created with id " + pickupLine.getId());
	    	
	    	pickupLine = new PickupLineEntity();
	    	pickupLine.setCategory("casual");
	    	pickupLine.setDescription("I will be Burger King and you be McDonalds. I will have it my way and you will be lovin it");
	    	pickupLine.setLang_flag(1);
	    	pickupLine = datasource.create(pickupLine);
	    	Log.i(LOGTAG,"Line created with id " + pickupLine.getId());
	    	
	    	pickupLine = new PickupLineEntity();
	    	pickupLine.setCategory("casual");
	    	pickupLine.setDescription("Are you religious? Because you are the answer to all my prayers.");
	    	pickupLine.setLang_flag(1);
	    	pickupLine = datasource.create(pickupLine);
	    	Log.i(LOGTAG,"Line created with id " + pickupLine.getId());
	    	
	    	pickupLine = new PickupLineEntity();
	    	pickupLine.setCategory("casual");
	    	pickupLine.setDescription("I was feeling a little off today... but you definitely turned me on");
	    	pickupLine.setLang_flag(1);
	    	pickupLine = datasource.create(pickupLine);
	    	Log.i(LOGTAG,"Line created with id " + pickupLine.getId());
    	}
    }
}

package com.nadav.eliyahu.proj.pickupline;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

public class FacebookLogActivity extends FragmentActivity {
	
	private MainFragment mainFragment;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.activity_facebook_log);
		if(savedInstanceState == null)
		{
			//add the fragment on initial activity setup
			mainFragment = new MainFragment();
			getSupportFragmentManager()
			.beginTransaction()
			.add(android.R.id.content, mainFragment)
			.commit();
			
		}
		else
		{
			//or set the fragment from restored state info
			mainFragment = (MainFragment) getSupportFragmentManager()
			.findFragmentById(android.R.id.content);
		}
		
		
	}
	
}

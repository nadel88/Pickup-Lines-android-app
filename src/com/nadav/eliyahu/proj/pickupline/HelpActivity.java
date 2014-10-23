package com.nadav.eliyahu.proj.pickupline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class HelpActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_help_activity);
		
	}
	
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		startActivity(new Intent(HelpActivity.this,MainActivity.class));
		finish();
	}
}

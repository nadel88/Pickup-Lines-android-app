package com.nadav.eliyahu.proj.pickupline;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MainActivity extends Activity implements OnClickListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		Button StreetButton = (Button)findViewById(R.id.ButtonStreetLine);
		Button ClubButton = (Button)findViewById(R.id.ButtonClubLine);
		Button PubButton = (Button)findViewById(R.id.ButtonPubLine);
		Button CasualButton = (Button)findViewById(R.id.ButtonCasualFriends);
		
		ClubButton.setOnClickListener(this);
		PubButton.setOnClickListener(this);
		CasualButton.setOnClickListener(this);
		StreetButton.setOnClickListener(this);
			
	}

	@Override
	public void onClick(View v) {
		switch(v.getId())
		{
			case R.id.ButtonStreetLine:
				startActivity(new Intent(MainActivity.this,StreetLinesActivity.class));
				break;
			case R.id.ButtonClubLine:
				startActivity(new Intent(MainActivity.this,ClubLinesActivity.class));
				break;
			case R.id.ButtonCasualFriends:
				startActivity(new Intent(MainActivity.this,CasualLinesActivity.class));
				break;
			case R.id.ButtonPubLine:
				startActivity(new Intent(MainActivity.this,PubLinesActivity.class));
				break;
		}
		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    MenuInflater inflater = getMenuInflater();
	    inflater.inflate(R.menu.menu,menu);
	    return true;
	}
	
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch(item.getItemId())
		{
		case R.id.favorites:
			startActivity(new Intent(MainActivity.this,FavoritesList.class));
			break;
		case R.id.help:
			break;
		}
	         return super.onOptionsItemSelected(item);
	    }
	}

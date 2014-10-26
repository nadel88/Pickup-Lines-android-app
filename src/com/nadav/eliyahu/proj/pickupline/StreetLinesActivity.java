package com.nadav.eliyahu.proj.pickupline;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("ShowToast")
public class StreetLinesActivity extends Activity {
	
	SQLLightDao sqldao;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_street_lines);
						
		ImageView ivBubble = (ImageView)findViewById(R.id.imageViewBubbleTalk);
		final TextView tv = (TextView)findViewById(R.id.textView2);
		ImageView heartButton = (ImageView)findViewById(R.id.imageViewHeart);
		ImageView XButton = (ImageView)findViewById(R.id.imageViewX);
		
		sqldao =  new SQLLightDao(this);
		
		Cursor c = sqldao.getData("street");
		c.moveToFirst();
		tv.setText(c.getString(c.getColumnIndex("Line")));
		
		
		//animation for the talk bubble
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.wobbleanim);
		shake.reset();
		shake.setFillAfter(true);
		ivBubble.startAnimation(shake);
		
		//animation for the text inside the talk bubble and for button add to favorites
		Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		fadeIn.reset();
		fadeIn.setFillAfter(true);
		tv.startAnimation(fadeIn);
		heartButton.startAnimation(fadeIn);
		XButton.startAnimation(fadeIn);
		
		heartButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				try{										
					int id = 0 ;
					int count = sqldao.getNumberOfRows();
					if(count==0)
						id = 0;
					else id = id+1;
					sqldao.addToFavoritesDB(id, "street", tv.getText().toString());
				}
				catch(NullPointerException e)
				{
					Log.d("CustomExceptionLog", "tv.getText().toString results in exception");
				}
				Toast.makeText(StreetLinesActivity.this, "This Pickup Line has been added to favorites", 4000).show();												
			}
				
		});
		
		
		XButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(StreetLinesActivity.this,StreetLinesActivity.class));
				finish();
				
			}
		});
		
	}
}

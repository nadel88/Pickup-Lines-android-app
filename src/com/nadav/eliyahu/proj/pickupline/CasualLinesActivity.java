package com.nadav.eliyahu.proj.pickupline;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class CasualLinesActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_casual_lines);
		
		ImageView ivBubble = (ImageView)findViewById(R.id.imageViewBubbleTalk);
		TextView tv = (TextView)findViewById(R.id.textView2);
		SQLLightDao sqldao;
		sqldao =  new SQLLightDao(this);
		
		
		
		Cursor c = sqldao.getData("casual");
		c.moveToFirst();
		tv.setText(c.getString(c.getColumnIndex("Line")));
		
		
		//animation for the talk bubble
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.wobbleanim);
		shake.reset();
		shake.setFillAfter(true);
		ivBubble.startAnimation(shake);
		
		//animation for the text inside the talk bubble
		Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		fadeIn.reset();
		fadeIn.setFillAfter(true);
		tv.startAnimation(fadeIn);
	}
}

package com.nadav.eliyahu.proj.pickupline;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;


public class ShowFavoriteLine extends FragmentActivity {
	
	PublishToFacebookFragment publishToFacebook;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		
		
		//code in test
		//=============
		
		if(savedInstanceState == null)
		{
			//add the fragment on initial activity setup
			publishToFacebook = new PublishToFacebookFragment();
			publishToFacebook.setArguments(getIntent().getExtras());
			getSupportFragmentManager()
			.beginTransaction()
			.add(android.R.id.content, publishToFacebook)
			.commit();
			
		}
		else
		{
			//or set the fragment from restored state info
			publishToFacebook = (PublishToFacebookFragment) getSupportFragmentManager()
			.findFragmentById(android.R.id.content);
		}
		
	
		//code in test
		//============
		
/*setContentView(R.layout.activity_show_favorite_line);
		
		TextView tv = (TextView)findViewById(R.id.textView2);
		ImageView ivBubble = (ImageView)findViewById(R.id.imageViewBubbleTalk);
		
		tv.setText(getIntent().getExtras().getString("pickupline"));
		
		Animation shake = AnimationUtils.loadAnimation(this, R.anim.wobbleanim);
		shake.reset();
		shake.setFillAfter(true);
		ivBubble.startAnimation(shake);
		
		//animation for the text inside the talk bubble and for button add to favorites
		Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
		fadeIn.reset();
		fadeIn.setFillAfter(true);
		tv.startAnimation(fadeIn);*/
	
		
	}
	
	@Override
	public void onBackPressed()
	{
	    startActivity(new Intent(ShowFavoriteLine.this,FavoritesList.class));
	    finish();	   
	}
	
}

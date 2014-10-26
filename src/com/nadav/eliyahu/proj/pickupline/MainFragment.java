package com.nadav.eliyahu.proj.pickupline;

import java.util.Arrays;

import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.LoginButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class MainFragment extends Fragment {
	
	private static final String TAG = "MainFragment";
	private UiLifecycleHelper uiHelper;
	SharedPreferences pref ;
	Editor editor ;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_facebook_log,container,false);
		
		//initialize shared preferences to store the session mode
		pref = getActivity().getApplicationContext().getSharedPreferences("MyPref", 0); // 0 - for private mode
		editor = pref.edit();
		
		
		Button conButton = (Button)view.findViewById(R.id.buttonContinue);
		LoginButton authButton = (LoginButton) view.findViewById(R.id.authButton);
		authButton.setFragment(this);
		authButton.setReadPermissions(Arrays.asList("public_profile", "email"));
		
		conButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 Intent intent = new Intent(getActivity(), MainActivity.class);
	                startActivity(intent);
				
			}
		});
		
		return view;
	}
	
	private void onSessionStateChange(Session session , SessionState state ,Exception exception)
	{
		if(state.isOpened())
		{
			Log.i(TAG, "Logged in...");
			//if open then store the state mode to true so the user could later post to facebook
			editor.putBoolean("session_status", true);
			editor.commit();
			Toast.makeText(getActivity(), "You are now connected to facebook", 3000).show();
			
		}
		else if(state.isClosed())
		{
			Log.i(TAG,"Logged out...");
			//if close then store the state mode to false so the user could not post to facebook.
			editor.putBoolean("session_status", false);
			editor.commit();
			Toast.makeText(getActivity(), "You are now Logged out of facebook", 3000).show();
			
		}
	}
	
	private Session.StatusCallback callback = new Session.StatusCallback() {
		
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			// TODO Auto-generated method stub
			onSessionStateChange(session, state, exception);
			
		}
	};
	
	@Override
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		uiHelper = new UiLifecycleHelper(getActivity(), callback);
		uiHelper.onCreate(savedInstanceState);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		uiHelper.onResume();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
	}
	
	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		uiHelper.onPause();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		uiHelper.onDestroy();
	}
	
	@Override
	public void onSaveInstanceState(Bundle outState) {
		// TODO Auto-generated method stub
		uiHelper.onSaveInstanceState(outState);
	}

}

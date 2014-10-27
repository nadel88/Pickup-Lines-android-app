package com.nadav.eliyahu.proj.pickupline;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.FacebookException;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.widget.FacebookDialog;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint({ "NewApi", "ShowToast" })
public class PublishToFacebookFragment extends Fragment {
	
	private static final String TAG = "PublishFragment";
	private UiLifecycleHelper uiHelper;
	private ImageView shareButton;
	
	//permission array to allow posting on uesrs wall.
	private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";
	private boolean pendingPublishReauthorization = false;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.activity_show_favorite_line,container,false);
		
		TextView tv = (TextView)view.findViewById(R.id.textView2);
		tv.setText(getArguments().getString("pickupline"));
		ImageView ivBubble = (ImageView)view.findViewById(R.id.imageViewBubbleTalk);
		
		//shareButton declaration.
		shareButton = (ImageView) view.findViewById(R.id.imageButtonShare);
		
		//check session state to set share button visibility
		if(getActivity().getSharedPreferences("MyPref", 0).getBoolean("session_status", false)==true)
		{
			shareButton.setVisibility(View.VISIBLE);
			
		}
		if(getActivity().getSharedPreferences("MyPref", 0).getBoolean("session_status", false)==false)
		{
			shareButton.setVisibility(View.INVISIBLE);
		}
		
		//when share button is clicked 
		shareButton.setOnClickListener(new View.OnClickListener() {
		    @Override
		    public void onClick(View v) {
		    	//open the publish dialog and allow the user to accept the permission to publish
		        publishStory();        
		    }
		});
		//animation for the for the bubble talk
		Animation shake = AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.wobbleanim);
		shake.reset();
		shake.setFillAfter(true);
		ivBubble.startAnimation(shake);
		
		//animation for the text inside the talk bubble 
		Animation fadeIn = AnimationUtils.loadAnimation(getActivity().getBaseContext(), R.anim.fade_in);
		fadeIn.reset();
		fadeIn.setFillAfter(true);
		tv.startAnimation(fadeIn);
		
		if (savedInstanceState != null) {
		    pendingPublishReauthorization = 
		        savedInstanceState.getBoolean(PENDING_PUBLISH_KEY, false);}
		return view;
	}
	
	private void onSessionStateChange(Session session , SessionState state ,Exception exception)
	{
		try{
			if(state.isOpened())
			{
				//set the share button to be visible if the facebook session is open
				//shareButton.setVisibility(View.VISIBLE);
				Log.i(TAG, "posted to wall...");
				if (pendingPublishReauthorization && 
				        state.equals(SessionState.OPENED_TOKEN_UPDATED)) {
				    pendingPublishReauthorization = false;
				    publishStory();}
				
			}
		
			else if(state.isClosed())
			{
				//set the share button to be invisible if the facebook session is closed
				//shareButton.setVisibility(View.INVISIBLE);
				Toast.makeText(getActivity(), "You are not logged in to facebook please log in to post.", 3000).show();
				Log.i(TAG,"not logged in...");
			}
		}
		//facebook exception if the connection was invalid for some reason
		catch(FacebookException fbe)
		{
			Log.i(TAG, "problem getting connection with facebook...");
			Toast.makeText(getActivity(), "A problem ...", 3000).show();;
		}
	}
	
	//handle the session callback (the answer for the session request).
	private Session.StatusCallback callback = new Session.StatusCallback() {
		
		@Override
		public void call(Session session, SessionState state, Exception exception) {
			// TODO Auto-generated method stub
			onSessionStateChange(session, state, exception);
			
		}
	};
	
	//implementation of the fragment interface
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
	
	//handle the activity result data
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		 super.onActivityResult(requestCode, resultCode, data);

		    uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
		        @Override
		        public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
		            Log.e("Activity", String.format("Error: %s", error.toString()));
		        }

		        @Override
		        public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
		            Log.i("Activity", "Success!");
		        }
		    });
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
		outState.putBoolean(PENDING_PUBLISH_KEY, pendingPublishReauthorization);
	    uiHelper.onSaveInstanceState(outState);
	}
	
	//the post itself
	private void publishStory() {
	    Session session = Session.getActiveSession();

	    if (session != null){

	        // Check for publish permissions    
	        List<String> permissions = session.getPermissions();
	        if (!isSubsetOf(PERMISSIONS, permissions)) {
	            pendingPublishReauthorization = true;
	            Session.NewPermissionsRequest newPermissionsRequest = new Session
	                    .NewPermissionsRequest(this, PERMISSIONS);
	        session.requestNewPublishPermissions(newPermissionsRequest);
	            return;
	        }

	        //the content of the post 
	        Bundle postParams = new Bundle();
	        postParams.putString("name", "Facebook SDK for Android");
	        postParams.putString("caption", "Build great social apps and get more installs.");
	        postParams.putString("description", "The Facebook SDK for Android makes it easier and faster to develop Facebook integrated Android apps.");
	        postParams.putString("link", "https://developers.facebook.com/android");
	        postParams.putString("picture", "https://raw.github.com/fbsamples/ios-3.x-howtos/master/Images/iossdk_logo.png");

	        
	        //handle the response from the callback of the request
	        Request.Callback callback= new Request.Callback() {
	            public void onCompleted(Response response) {
	            	//on complete return a JSON object 
	                JSONObject graphResponse = response
	                                           .getGraphObject()
	                                           .getInnerJSONObject();
	                String postId = null;
	                try {
	                    postId = graphResponse.getString("id");
	                } catch (JSONException e) {
	                    Log.i(TAG,
	                        "JSON error "+ e.getMessage());
	                }
	                FacebookRequestError error = response.getError();
	                if (error != null) {
	                    Toast.makeText(getActivity()
	                         .getApplicationContext(),
	                         error.getErrorMessage(),
	                         Toast.LENGTH_SHORT).show();
	                    } else {
	                        Toast.makeText(getActivity()
	                             .getApplicationContext(), 
	                             /*postId*/"your pickup line has been posted to your facebook wall",
	                             Toast.LENGTH_LONG).show();
	                }
	            }
	        };

	        Request request = new Request(session, "me/feed", postParams, 
	                              HttpMethod.POST, callback);

	        RequestAsyncTask task = new RequestAsyncTask(request);
	        task.execute();
	    }

	}
	
	//check if the user has granted permission to post on their wall and handle it.
	private boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
	    for (String string : subset) {
	        if (!superset.contains(string)) {
	            return false;
	        }
	    }
	    return true;
	}
	
	

}

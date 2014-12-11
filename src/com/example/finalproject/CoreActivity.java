package com.example.finalproject;

import com.parse.ParseUser;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class CoreActivity extends Activity {

	Tab tabMsg, tabFollowing, tabAllUsers;
	Fragment fragmentMsg = new MessagesFragment();
	Fragment fragmentFollowing = new FollowingFragment();
	Fragment fragmentAllUsers = new AllUsersFragment();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_core);
		ParseUser currentUser = ParseUser.getCurrentUser();
		if(currentUser.equals(null))
			finish();
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		tabMsg = actionBar.newTab().setText("Messsages");
		tabFollowing = actionBar.newTab().setText("Following");
		tabAllUsers = actionBar.newTab().setText("All Users");
		
		tabMsg.setTabListener(new MyTabListener(fragmentMsg));
		tabFollowing.setTabListener(new MyTabListener(fragmentFollowing));
		tabAllUsers.setTabListener(new MyTabListener(fragmentAllUsers));
		
		actionBar.addTab(tabMsg);
		actionBar.addTab(tabFollowing);
		actionBar.addTab(tabAllUsers);
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.core, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		ParseUser currentUser = ParseUser.getCurrentUser();
		int id = item.getItemId();
		if (id == R.id.sign_out) {
			ParseUser.logOut();
			currentUser = ParseUser.getCurrentUser();
			Intent intent = new Intent(CoreActivity.this,MainActivity.class);
			//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			//intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			finish();
			return true;
		} else if(id == R.id.my_profile){
			Intent intent = new Intent(CoreActivity.this, ProfileSettingsActivity.class );
			intent.putExtra("username", currentUser.getUsername());
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

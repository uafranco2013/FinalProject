package com.example.finalproject;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

public class ProfileSettingsActivity extends Activity {
	ImageView profilePic;
	TextView name, gender, birthday, address, userName;
	Switch follow;
	Button sendNewMsg, edit, delete;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile_settings);
		
		profilePic = (ImageView) findViewById(R.id.imageViewProfilePic);
		
		name = (TextView) findViewById(R.id.textViewName);
		gender = (TextView) findViewById(R.id.textViewGender);
		birthday = (TextView) findViewById(R.id.textViewBirthday);
		address = (TextView) findViewById(R.id.textViewAddress);
		userName = (TextView) findViewById(R.id.textViewUserName);
		
		sendNewMsg = (Button) findViewById(R.id.buttonSendNewMessage);
		edit = (Button) findViewById(R.id.buttonEdit);
		delete = (Button) findViewById(R.id.buttonDeleteAccount);
		
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereEqualTo("userName", "Dany");
		query.findInBackground(new FindCallback<ParseUser>() {
			
			@Override
			public void done(List<ParseUser> objects, ParseException e) {
				final ParseUser otherUser = objects.get(0);
				final ParseUser user = ParseUser.getCurrentUser();
				
				if (user==otherUser){
					userName.setText(user.getUsername().toString());
					name.setText(user.get("name").toString());
					gender.setText(user.get("gender").toString());
					birthday.setText(user.get("birthday").toString());
					address.setText(user.get("address").toString());
					//profilePic.
					
					sendNewMsg.setVisibility(View.GONE);
					follow.setVisibility(View.GONE);
					
					edit.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							//Intent intent = new Intent(ProfileSettingsActivity.this, );
							
						}
					});
					
					delete.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							try {
								user.delete();
								Intent intent = new Intent(ProfileSettingsActivity.this, MainActivity.class);
								startActivity(intent);
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						}
					});
					
				}else if (user!=otherUser){
					userName.setText(otherUser.getUsername().toString());
					name.setText(otherUser.get("name").toString());
					gender.setText(otherUser.get("gender").toString());
					birthday.setText(otherUser.get("birthday").toString());
					address.setText(otherUser.get("address").toString());
					
					edit.setVisibility(View.GONE);
					delete.setVisibility(View.GONE);
					
					sendNewMsg.setOnClickListener(new View.OnClickListener() {
						
						@Override
						public void onClick(View v) {
							Intent intent = new Intent(ProfileSettingsActivity.this, MessagesActivity.class);
							intent.putExtra("User", user.getUsername().toString());
							intent.putExtra("OtherUser", otherUser.getUsername().toString());
							startActivity(intent);
						}
					});
					
					ParseObject following = new ParseObject("Following");
					if (follow.isChecked()){
						following.put("CurrentUser", user.getUsername().toString());
						following.put("OtherUser", otherUser.getUsername().toString());
						following.put("isFollowing", true);
						following.saveInBackground();
					}else{
						following.put("isFollowing", false);
					}
				}
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile_settings, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

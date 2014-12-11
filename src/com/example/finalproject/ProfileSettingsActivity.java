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
import android.util.Log;
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
	Button sendNewMsg, edit, delete, followBtn;

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
		followBtn = (Button) findViewById(R.id.buttonFollow);
		
		sendNewMsg.setVisibility(View.INVISIBLE);
		edit.setVisibility(View.INVISIBLE);
		delete.setVisibility(View.INVISIBLE);
		followBtn.setVisibility(View.INVISIBLE);

		if (getIntent().getExtras() != null) {
			String userName2 = getIntent().getExtras().getString("username");
			//Log.d("demo", userName2);

			ParseQuery<ParseUser> query = ParseUser.getQuery();
			query.whereEqualTo("username", userName2);
			query.findInBackground(new FindCallback<ParseUser>() {

				public void done(List<ParseUser> objects, ParseException e) {
					final ParseUser otherUser = objects.get(0);
					final ParseUser user = ParseUser.getCurrentUser();

					if (user == otherUser) {
						userName.setText(user.getUsername().toString());
						name.setText(user.get("name").toString());
						gender.setText(user.get("gender").toString());
						birthday.setText(user.get("birthday").toString());
						address.setText(user.get("address").toString());
						// profilePic.

						edit.setVisibility(View.VISIBLE);
						delete.setVisibility(View.VISIBLE);

						edit.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								// Intent intent = new
								// Intent(ProfileSettingsActivity.this, );

							}
						});

						delete.setOnClickListener(new View.OnClickListener() {

							@Override
							public void onClick(View v) {
								try {
									user.delete();
									Intent intent = new Intent(
											ProfileSettingsActivity.this,
											MainActivity.class);
									startActivity(intent);
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						});

					} else if (user != otherUser) {
						userName.setText(otherUser.getUsername().toString());
						name.setText(otherUser.get("name").toString());
						gender.setText(otherUser.get("gender").toString());
						birthday.setText(otherUser.get("birthday").toString());
						address.setText(otherUser.get("address").toString());
						
						sendNewMsg.setVisibility(View.VISIBLE);
						followBtn.setVisibility(View.VISIBLE);
						
						sendNewMsg
								.setOnClickListener(new View.OnClickListener() {

									@Override
									public void onClick(View v) {
										Intent intent = new Intent(
												ProfileSettingsActivity.this,
												MessagesActivity.class);
										intent.putExtra("User", user
												.getUsername().toString());
										intent.putExtra("OtherUser", otherUser
												.getUsername().toString());
										startActivity(intent);
									}
								});
						
						final ParseObject following = new ParseObject("Following");
						followBtn.setOnClickListener(new View.OnClickListener() {
							
							public void onClick(View v) {
								if (followBtn.getText().toString().equals("Follow")){
									followBtn.setText("Unfollow");
									following.put("follower", user);
									following.put("following", otherUser);
									following.saveInBackground();
									Log.d("demo", "follower: " + user.getUsername());
								}else{
									followBtn.setText("Follow");
									following.deleteInBackground();
								}
								
							}
						});
					}
				}
			});
		}
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
		ParseUser currentUser = ParseUser.getCurrentUser();
		int id = item.getItemId();
		if (id == R.id.sign_out) {
			ParseUser.logOut();
			currentUser = ParseUser.getCurrentUser();
			Intent intent = new Intent(ProfileSettingsActivity.this,MainActivity.class);
			//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			//intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			finish();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

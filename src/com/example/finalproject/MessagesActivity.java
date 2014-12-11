package com.example.finalproject;

import com.parse.ParseUser;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MessagesActivity extends Activity {
	TextView contactName;
	ListView messages;
	EditText replyMessage;
	Button replyButton;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messages);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.messages, menu);
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
			Intent intent = new Intent(MessagesActivity.this,MainActivity.class);
			//intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			//intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
			intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			startActivity(intent);
			finish();
			return true;
		} else if(id == R.id.my_profile){
			Intent intent = new Intent(MessagesActivity.this, ProfileSettingsActivity.class );
			intent.putExtra("username", currentUser.getUsername());
			startActivity(intent);
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}

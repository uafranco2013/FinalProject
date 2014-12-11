package com.example.finalproject;

import java.util.ArrayList;
import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

public class MessagesActivity extends Activity {
	TextView contactName;
	ListView messages;
	EditText replyMessage;
	Button replyButton;
	ParseUser otherUser;
	ParseUser currentUser = ParseUser.getCurrentUser();
	String username = "";
	ParseQueryAdapter<ParseObject> adapter;
	AlertDialog alert;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_messages);
		messages = (ListView) findViewById(R.id.listViewMessages);
		contactName = (TextView) findViewById(R.id.textViewContactName);
		replyButton = (Button) findViewById(R.id.buttonReply);
		replyMessage = (EditText) findViewById(R.id.editTextReplyMessage);
		if(getIntent().getExtras() != null){
			username = getIntent().getExtras().getString("username");			
		}
		List<ParseUser> userList = new ArrayList<ParseUser>();
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereEqualTo("username", username);
		//query.find()
		try {
			userList = query.find();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		otherUser = userList.get(0);
		/**
		adapter = new ParseQueryAdapter<ParseObject>(MessagesActivity.this, new ParseQueryAdapter.QueryFactory<ParseObject>() {

			@Override
			public ParseQuery<ParseObject> create() {
				List<ParseUser> userList = new ArrayList<ParseUser>();
				ParseQuery<ParseUser> query = ParseUser.getQuery();
				query.whereEqualTo("username", username);
				//query.find()
				try {
					userList = query.find();
				} catch (ParseException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				otherUser = userList.get(0);
				//Log.d("other", otherUser.toString());
				List<ParseQuery<ParseObject>> queries = new ArrayList<ParseQuery<ParseObject>>();
				ParseQuery sending = new ParseQuery("Messages");
				sending.whereEqualTo("sender", currentUser);
				sending.whereEqualTo("receiver", otherUser);
				ParseQuery receiving = new ParseQuery("Messages");
				receiving.whereEqualTo("sender", otherUser);
				receiving.whereEqualTo("receiver", currentUser);
				queries.add(sending);
				queries.add(receiving);
				ParseQuery finalQuery = ParseQuery.or(queries);
				finalQuery.orderByDescending("createdAt");
				contactName.setText(otherUser.getString("name").toString());
				return finalQuery;
				
			}
		});
		adapter.setTextKey("message");
		*/
		final MessageAdapter adapter = new MessageAdapter(MessagesActivity.this,username);
		messages.setAdapter(adapter);
		messages.setSelection(0);
		contactName.setText(otherUser.getString("name").toString());
		
		messages.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					final int position, long id) {
				AlertDialog.Builder builder = new AlertDialog.Builder(MessagesActivity.this);
				builder.setMessage("Are you sure you would like to continue?")
			       .setTitle("Deleting message");
				builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			               ParseObject message = adapter.getItem(position);
			               if(message.getBoolean("isMostRecent")){
			            	   ParseQuery<ParseObject> nextOne =  ParseQuery.getQuery("Messages");
			            	   ParseObject next = null;
			            	   nextOne.whereEqualTo("receiver", currentUser);
			            	   nextOne.whereEqualTo("sender", otherUser);
			            	   nextOne.whereEqualTo("isMostRecent", false);
			            	   nextOne.addDescendingOrder("createdAt");
			            	   try {
								next = nextOne.getFirst();
							} catch (ParseException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
			            	   next.put("isMostRecent", true);
			            	   next.saveInBackground();
			            	   Log.d("test", next.getString("message"));
			               }
			               
			               message.deleteInBackground();
			               Intent intent = new Intent(MessagesActivity.this,CoreActivity.class);
			               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
			               intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			               startActivity(intent);
			               finish();
			               
			           }
				});
				
				builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
			           public void onClick(DialogInterface dialog, int id) {
			        	   
			           }
				});
				alert = builder.create();
				alert.show();
			           
				return false;
			}
		});
		
		//adapter.notifyDataSetChanged();
		replyButton.setOnClickListener(new View.OnClickListener() {
			
			public void onClick(View v) {
				ParseQuery<ParseObject> lastQuery = ParseQuery.getQuery("Messages");
				lastQuery.whereEqualTo("sender", currentUser);
				lastQuery.whereEqualTo("receiver", otherUser);
				lastQuery.whereEqualTo("isMostRecent", true);
				lastQuery.findInBackground(new FindCallback<ParseObject>() {

					@Override
					public void done(List<ParseObject> objects, ParseException e) {
						if(objects.size() > 0){
							ParseObject lastMessage = objects.get(0);
							lastMessage.put("isMostRecent", false);
							lastMessage.saveInBackground();
						}
						
						if(e == null){
							ParseObject message = new ParseObject("Messages");
							message.put("message", replyMessage.getText().toString().trim());
							message.put("sender", currentUser);
							message.put("receiver", otherUser);
							message.put("isMostRecent", true);
							message.saveInBackground();
						}
						else{
							Log.d("error", e.toString());
						}
					}
				});
				Intent intent = new Intent(MessagesActivity.this,CoreActivity.class);
				intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				startActivity(intent);
				finish();
			}
		});
		
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

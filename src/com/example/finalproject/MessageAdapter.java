package com.example.finalproject;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

public class MessageAdapter extends ParseQueryAdapter<ParseObject>{

	static ParseUser currentUser = ParseUser.getCurrentUser();
	static ParseUser otherUser;
	String username;
	public MessageAdapter(Context context, final String username) {
		super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
			public ParseQuery create() {
				ParseUser user = ParseUser.getCurrentUser();
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
				finalQuery.include("receiver");
				finalQuery.orderByDescending("createdAt");
				return finalQuery;
			}
		});
	}

	// Customize the layout by overriding getItemView
		@Override
		public View getItemView(ParseObject object, View v, ViewGroup parent) {
			if (v == null) {
				v = View.inflate(getContext(), R.layout.message_layout, null);
			}

			super.getItemView(object, v, parent);
			//TextView titleTextView = (TextView) v.findViewById(R.id.textViewReceiver);
			//ParseUser sender = object.getParseUser("sender");
			//titleTextView.setText(sender.getString("name"));
			
			TextView receiverText = (TextView) v.findViewById(R.id.textViewReceiver);
			//Log.d("stuff", object.getParseUser("receiver").getUsername());
			
			
			if(currentUser.getUsername().equals(object.getParseUser("receiver").getUsername())){
				//receiverText.setVisibility(View.GONE);		
				receiverText.setText(object.getString("message"));
				receiverText.setGravity(Gravity.LEFT);
				receiverText.setBackgroundColor(Color.LTGRAY);
			} else{
				//senderText.setVisibility(View.GONE);
				//senderText.setText(object.getString("message"));
				receiverText.setText(object.getString("message"));
				receiverText.setGravity(Gravity.RIGHT);
				receiverText.setBackgroundColor(Color.CYAN);
			}
			
			
			
			
			
			//messageText.setText(object.getString("message"));
			return v;
		}

}

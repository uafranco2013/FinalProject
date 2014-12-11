package com.example.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
public class CustomAdapter extends ParseQueryAdapter<ParseObject> {

	ParseUser currentUser = ParseUser.getCurrentUser();
	public CustomAdapter(Context context) {
		

		super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
			public ParseQuery create() {
				ParseUser user = ParseUser.getCurrentUser();
				ParseQuery query = new ParseQuery("Messages");
				query.whereEqualTo("receiver", user);
				query.whereEqualTo("isMostRecent", true);
				//query.whereEqualTo("sender", currentUser);
				query.include("sender");
				query.include("receiver");
				query.orderByDescending("createdAt");
				
				return query;
			}
		});
	}

	// Customize the layout by overriding getItemView
	@Override
	public View getItemView(ParseObject object, View v, ViewGroup parent) {
		if (v == null) {
			v = View.inflate(getContext(), R.layout.message_list_layout, null);
		}

		super.getItemView(object, v, parent);

		// Add the title view
		TextView titleTextView = (TextView) v.findViewById(R.id.textViewName);
		
		ParseUser sender = object.getParseUser("sender");
		//String word = sender.getEmail();
		//Log.d("test", sender.get("name") + " " +currentUser.get("name"));
		titleTextView.setText(sender.getString("name"));
		// Add a reminder of how long this item has been outstanding
		TextView messageText = (TextView) v.findViewById(R.id.textViewLastMsg);
		messageText.setText(object.getString("message"));
		return v;
	}

}

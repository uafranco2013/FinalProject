package com.example.finalproject;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.parse.ParseFile;
import com.parse.ParseImageView;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;
public class CustomAdapter extends ParseQueryAdapter<ParseObject> {

	static ParseUser currentUser = ParseUser.getCurrentUser();
	public CustomAdapter(Context context) {
		// Use the QueryFactory to construct a PQA that will only show
		// Todos marked as high-pri
		super(context, new ParseQueryAdapter.QueryFactory<ParseObject>() {
			public ParseQuery create() {
				
				ParseQuery query = new ParseQuery("Messages");
				query.whereEqualTo("receiver", currentUser);
				query.include("sender");
				query.include("receiver");
				//query.whereEqualTo("highPri", true);
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
		Log.d("test", sender.get("name") + " " +currentUser.get("name"));
		titleTextView.setText(sender.getString("name"));
		// Add a reminder of how long this item has been outstanding
		TextView timestampView = (TextView) v.findViewById(R.id.textViewLastMsg);
		timestampView.setText(object.getString("message"));
		return v;
	}

}

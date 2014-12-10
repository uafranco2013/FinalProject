package com.example.finalproject;

import java.util.List;

import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class AllUsersFragment extends Fragment {

	public AllUsersFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_all_users, container, false);
		/**
		ParseQuery<ParseUser> query = ParseUser.getQuery();
		query.whereEqualTo("username", "frcerrillo.2012");
		query.findInBackground(new FindCallback<ParseUser>() {
			
			@Override
			public void done(List<ParseUser> objects, ParseException e) {
				ParseUser receiver = objects.get(0);
				Log.d("test", objects.get(0).toString());
				ParseUser user = ParseUser.getCurrentUser();
				ParseObject messages = new ParseObject("Messages");
				messages.put("message", "Hi");
				messages.put("sender", receiver);
				messages.put("receiver", user);
				messages.saveInBackground();
			}
		});
		*/
		
		
		ParseQueryAdapter<ParseObject> adapter = new ParseQueryAdapter<ParseObject>(getActivity(), "_User");
		adapter.setTextKey("name");
		ListView listView = (ListView) view.findViewById(R.id.listViewUsers);
		listView.setAdapter(adapter);
		return view;
	}

}

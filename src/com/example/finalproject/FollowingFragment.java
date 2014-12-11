package com.example.finalproject;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

/**
 * 
 * 
 * 
 */
public class FollowingFragment extends Fragment {

	ListView listView;
	ParseUser currentUser = ParseUser.getCurrentUser();
	public FollowingFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_following, container, false);
		listView = (ListView) view.findViewById(R.id.listViewFollowing);
		currentUser = ParseUser.getCurrentUser();
		ParseQueryAdapter<ParseObject> adapter = new ParseQueryAdapter<ParseObject>(getActivity(), new ParseQueryAdapter.QueryFactory<ParseObject>() {

			@Override
			public ParseQuery<ParseObject> create() {
				ParseQuery query = new ParseQuery("Following");
				query.whereEqualTo("follower", currentUser);	
				query.orderByAscending("following");
				return query;
			}
		});
		
		adapter.setTextKey("following");
		listView.setAdapter(adapter);
		
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(), ProfileSettingsActivity.class);
				ParseObject object = (ParseObject) listView.getAdapter().getItem(position);
				String username = object.getString("following");
				intent.putExtra("username", username);
				startActivity(intent);
				
			}
		});
		
		return view;
	}

	
}

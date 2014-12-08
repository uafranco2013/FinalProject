package com.example.finalproject;

import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseQueryAdapter;

import android.os.Bundle;
import android.app.Fragment;
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
		/*ParseQueryAdapter<ParseObject> adapter = new ParseQueryAdapter<ParseObject>(getActivity(), "User");
		adapter.setTextKey("username");*/
		
		
		//adapter.setTextKey("name");
		
		
		ListView listView = (ListView) view.findViewById(R.id.listViewUsers);
		//listView.setAdapter(adapter);
		return view;
	}

}

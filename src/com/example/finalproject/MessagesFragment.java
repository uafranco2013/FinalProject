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
 * A simple {@link Fragment} subclass.
 * 
 */
public class MessagesFragment extends Fragment {

	ListView listView;
	public MessagesFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		View view = inflater.inflate(R.layout.fragment_messages, container, false);
		listView = (ListView) view.findViewById(R.id.listViewMsgs);
		/**
		ParseQueryAdapter<ParseObject> adapter =
				  new ParseQueryAdapter<ParseObject>(getActivity(), new ParseQueryAdapter.QueryFactory<ParseObject>() {
				    public ParseQuery<ParseObject> create() {
				      // Here we can configure a ParseQuery to our heart's desire.
				      ParseQuery query = new ParseQuery("Messages");
				      query.whereEqualTo("sender", ParseUser.getCurrentUser());
				      return query;
				    }
				  });
		
		adapter.setTextKey("message");
		listView.setAdapter(adapter);
		*/
		CustomAdapter adapter = new CustomAdapter(getActivity());
		
		listView.setAdapter(adapter);
		listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ParseObject object = (ParseObject) listView.getAdapter().getItem(position);
				ParseUser sender = object.getParseUser("sender");
				String username = sender.getString("username");
				//Log.d("something", username);
				Intent intent = new Intent(getActivity(),MessagesActivity.class);
				intent.putExtra("username", username);
				startActivity(intent);
				
				
			}
		});
		
		return view;
	}

}

package com.example.finalproject;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import android.os.Bundle;
import android.app.Fragment;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * 
 */

public class SignUpFragment extends Fragment {

	View view;
	EditText username,password,email,name,address,gender,birthday;
	public SignUpFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view = inflater.inflate(R.layout.fragment_sign_up, container, false);
		username = (EditText) view.findViewById(R.id.editTextSUUsername);
		password = (EditText) view.findViewById(R.id.editTextSUPassword);
		email = (EditText) view.findViewById(R.id.editTextEmail);
		name = (EditText) view.findViewById(R.id.editTextName);
		address = (EditText) view.findViewById(R.id.editTextAddress);
		gender = (EditText) view.findViewById(R.id.editTextGender);
		birthday = (EditText) view.findViewById(R.id.editTextBirthday);
		
		view.findViewById(R.id.buttonSubmit).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ParseUser user = new ParseUser();
				user.setUsername(username.getText().toString());
				user.setPassword(password.getText().toString());
				user.setEmail(email.getText().toString());
				user.put("name", name.getText().toString());
				user.put("address", address.getText().toString());
				user.put("gender", gender.getText().toString());
				user.put("birthday", birthday.getText().toString());
				user.signUpInBackground(new SignUpCallback() {
					  public void done(ParseException e) {
					    if (e == null) {
					    	Intent intent = new Intent(getActivity(),CoreActivity.class);
						    startActivity(intent);
					    } else {
					      Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
					    }
					  }
					});
			}
		});
		
		return view;
	}

}

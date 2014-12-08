package com.example.finalproject;

import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class SignInFragment extends Fragment {

	public SignInFragment() {
		// Required empty public constructor
	}

	EditText username,password;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
		// Inflate the layout for this fragment
		username = (EditText) view.findViewById(R.id.editTextUsername);
		password = (EditText) view.findViewById(R.id.editTextPassword);
		view.findViewById(R.id.buttonSignUp).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				FragmentManager fManager = getFragmentManager();
		        FragmentTransaction fTransaction = fManager.beginTransaction();
				SignUpFragment signupFrag = new SignUpFragment();
				fTransaction.replace(R.id.fragLayout, signupFrag);
				fTransaction.addToBackStack(null);
				fTransaction.commit();
				
			}
		});
		view.findViewById(R.id.buttonSignIn).setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				ParseUser.logInInBackground("Jerry", "showmethemoney", new LogInCallback() {					  
					@Override
					public void done(ParseUser user, ParseException e) {
						if (user != null) {
						      // Hooray! The user is logged in.
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

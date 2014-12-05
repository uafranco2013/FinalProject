package com.example.finalproject;

import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * A simple {@link Fragment} subclass.
 * 
 */
public class SignInFragment extends Fragment {

	public SignInFragment() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_sign_in, container, false);
		// Inflate the layout for this fragment

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
		return view;
	
	}



}

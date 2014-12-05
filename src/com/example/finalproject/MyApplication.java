package com.example.finalproject;

import com.parse.Parse;

import android.app.Application;

public class MyApplication extends Application{

	public void onCreate() {
		  Parse.initialize(this, "OXUoCUwwl3lHi6CPsgtlcqqjuWM4OnZOFEdLMYrx", "KaA9XQgdpVutsiYWGHGGWI7CGsgVuKVTBaNsdMJt");
		}
}

package com.example.finalproject;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SaveCallback;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

public class EditProfileActivity extends Activity {
	ImageView profilePic;
	Button changePic, saveChanges;
	EditText username, name, email, password, birthday, gender, address;
	private static final int READ_REQUEST_CODE = 42;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_edit_profile);
		
		profilePic = (ImageView) findViewById(R.id.imageViewEditProfilePic);
		
		changePic = (Button) findViewById(R.id.buttonEditPic);
		changePic.setVisibility(View.INVISIBLE);
		saveChanges = (Button) findViewById(R.id.buttonSaveChanges);
		
		username = (EditText) findViewById(R.id.editTextEditUserName);
		name = (EditText) findViewById(R.id.editTextEditName);
		email = (EditText) findViewById(R.id.editTextEditEmail);
		//password = (EditText) findViewById(R.id.editTextEditPassword);
		birthday = (EditText) findViewById(R.id.editTextEditBirthday);
		gender = (EditText) findViewById(R.id.editTextEditGender);
		address = (EditText) findViewById(R.id.editTextEditAddress);
		
		final ParseUser user = ParseUser.getCurrentUser();
		
		username.setText(user.getUsername());
		name.setText(user.getString("name"));
		email.setText(user.getEmail());
		//password.setText(user.getString("password"));
		birthday.setText(user.getString("birthday"));
		gender.setText(user.getString("gender"));
		address.setText(user.getString("address"));
		
		
		changePic.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				performFileSearch();				
			}
		});
				
		saveChanges.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				user.put("username", username.getText().toString());
				user.put("name", name.getText().toString());
				user.put("email", email.getText().toString());
				//user.put("password", password.getText().toString());
				user.put("birthday", birthday.getText().toString());
				user.put("gender", gender.getText().toString());
				user.put("address", address.getText().toString());
				user.saveInBackground(new SaveCallback() {
					
					@Override
					public void done(ParseException e) {
						// TODO Auto-generated method stub
						if (e == null) {
						    // Save was successful!
						    Log.d("demo", "Success!");
						  } else {
						    // Save failed. Inspect e for details.
						    Log.d("demo", e.toString());
						  }
					}
				});
				
				finish();
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.edit_profile, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void performFileSearch() {

	    // ACTION_OPEN_DOCUMENT is the intent to choose a file via the system's file
	    // browser.
	    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);

	    // Filter to only show results that can be "opened", such as a
	    // file (as opposed to a list of contacts or timezones)
	    intent.addCategory(Intent.CATEGORY_OPENABLE);

	    // Filter to show only images, using the image MIME data type.
	    // If one wanted to search for ogg vorbis files, the type would be "audio/ogg".
	    // To search for all documents available via installed storage providers,
	    // it would be "*/*".
	    intent.setType("image/*");

	    startActivityForResult(intent, READ_REQUEST_CODE);
	}
	
	/*@Override
	public void onActivityResult(int requestCode, int resultCode,
	        Intent resultData) {

	    // The ACTION_OPEN_DOCUMENT intent was sent with the request code
	    // READ_REQUEST_CODE. If the request code seen here doesn't match, it's the
	    // response to some other intent, and the code below shouldn't run at all.

	    if (requestCode == READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
	        // The document selected by the user won't be returned in the intent.
	        // Instead, a URI to that document will be contained in the return intent
	        // provided to this method as a parameter.
	        // Pull that URI using resultData.getData().
	        Uri uri = null;
	        if (resultData != null) {
	            uri = resultData.getData();
	            showImage(uri);  
	        }
	    }
	}
	
	private Bitmap getBitmapFromUri(Uri uri) throws IOException {
	    ParcelFileDescriptor parcelFileDescriptor =
	            getContentResolver().openFileDescriptor(uri, "r");
	    FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
	    Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
	    parcelFileDescriptor.close();
	    return image;
	}
	
	private String readTextFromUri(Uri uri) throws IOException {
	    InputStream inputStream = getContentResolver().openInputStream(uri);
	    BufferedReader reader = new BufferedReader(new InputStreamReader(
	            inputStream));
	    StringBuilder stringBuilder = new StringBuilder();
	    String line;
	    while ((line = reader.readLine()) != null) {
	        stringBuilder.append(line);
	    }
	    fileInputStream.close();
	    parcelFileDescriptor.close();
	    return stringBuilder.toString();
	}*/
}
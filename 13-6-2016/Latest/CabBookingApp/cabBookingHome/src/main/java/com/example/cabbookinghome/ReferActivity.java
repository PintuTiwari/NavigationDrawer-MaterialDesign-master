package com.example.cabbookinghome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.coboltforge.slidemenu.SlideMenu;
import com.example.cabbookinghome.R;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.CommonStatusCodes;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.plus.People.LoadPeopleResult;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.PlusShare;
import com.google.android.gms.plus.model.people.Person;
import com.google.android.gms.plus.model.people.PersonBuffer;

import epbit.Login.Friends;
import epbit.Login.LoginDetails;
import epbit.adapter.SlideMenuListener;
import epbit.service.ReferTask;

public class ReferActivity extends Activity implements
		 ConnectionCallbacks,
		OnConnectionFailedListener, ResultCallback<LoadPeopleResult> {

	Context context;
	ImageButton send_sms, share_facebook, share_email, share_twitter;
	LinearLayout share_gplus;
	Intent intent;
	SlideMenu slidemenu;
	Dialog dialog;
	ArrayList<String> list = new ArrayList<String>();
	String separator = "; ";
	String Numbers = "";
	private boolean mIntentInProgress;
	private boolean mSignInClicked;
	private ConnectionResult mConnectionResult;
	private GoogleApiClient mGoogleApiClient;

	public static final String mShareTitle = "";
	public static String mShareDescription = "";
	public static String mUrl = "http://bookmycab.com";
	String NAME_KEY = "name";
	String ID_KEY = "id";
	String CHECK = "check";
	String FRIEND_LIST = "friend_list";
	public int RC_GOOGLE_PLUS = 12;
	public int REQUEST_CODE_CONTACT = 1;
	// 1 for OK
	// 0 for ERROR
	public int RESULT_CODE_CONTACT = 0;
	private static final int RC_SIGN_IN = 5;
	public int RC_EMAIL = 7;
	public int RC_G_FRIENDS = 8;
	private static final String TAG = "MainActivity";
	List<String> friendsID = new ArrayList<String>();
	List<Person> recipients = new ArrayList<Person>();
	List<HashMap<String, String>> friends = new ArrayList<HashMap<String, String>>();
	ProgressDialog progressDialog;
	private int RESULT_GPLUSERROR = 404;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.referfriend1);
		context=ReferActivity.this;
		// send_sms = (ImageButton) findViewById(R.id.share_by_sms);
		// share_email = (ImageButton) findViewById(R.id.share_by_email);
		// share_facebook = (ImageButton) findViewById(R.id.share_by_facebook);
		// share_twitter = (ImageButton) findViewById(R.id.share_by_twitter);
		mShareDescription = getResources().getString(R.string.refer_content);
		share_gplus = (LinearLayout) findViewById(R.id.share_ggooglePlus);
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).addApi(Plus.API)
				.addScope(Plus.SCOPE_PLUS_LOGIN)
				.addScope(Plus.SCOPE_PLUS_PROFILE).build();

		attachSlideMenu();

		share_gplus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				startActivityForResult(new Intent(ReferActivity.this,
						GooglePlusActivity.class), RC_GOOGLE_PLUS);

			}

		});

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		super.onActivityResult(requestCode, resultCode, data);

		/*
		 * if (requestCode == 1 && resultCode == RESULT_CANCELED) {
		 * Toast.makeText(getApplicationContext(),
		 * "Unable to Retrieve contact !!", Toast.LENGTH_SHORT).show(); }
		 */

		// if (requestCode == RC_SIGN_IN) {
		// if (resultCode != RESULT_OK) {
		// mSignInClicked = false;
		// }
		//
		// mIntentInProgress = false;
		//
		// if (!mGoogleApiClient.isConnecting()) {
		// mGoogleApiClient.connect();
		// }
		// }
		// if (requestCode == RC_EMAIL && resultCode == RESULT_CANCELED) {
		// Toast.makeText(getApplicationContext(),
		// "Unable to Retrieve contact !!", Toast.LENGTH_SHORT).show();
		// }
		//
		// if (requestCode == RC_EMAIL) {
		// if (resultCode == RESULT_OK) {
		//
		// sharePost();
		//
		// }
		//
		// }
		//
		//

		if (requestCode == RC_GOOGLE_PLUS && resultCode == RESULT_OK) {
			Log.e("REFER COUNT ", "" + getIntent().getIntExtra("count", 0));
			LoginDetails.refer_count = getIntent().getIntExtra("count", 0);
			if (LoginDetails.REFER_IDS.length > 0) {
				new ReferTask(ReferActivity.this, getApplicationContext())
						.execute();
			}

		} else if (requestCode == RC_GOOGLE_PLUS
				&& resultCode == RESULT_GPLUSERROR) {
			Toast.makeText(getApplicationContext(),
					"Please install google plus and try again",
					Toast.LENGTH_LONG).show();
		}

		else {
			Toast.makeText(getApplicationContext(),
					"Unable to Refer !! Please try Again ", Toast.LENGTH_LONG)
					.show();
		}

	}

	private void attachSlideMenu() {

		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(getResources().getColor(
						R.color.action_titlebar_color)));
		getActionBar().setTitle(getResources().getString(R.string.app_name));
		getActionBar().setIcon(R.drawable.menu);

		slidemenu = (SlideMenu) findViewById(R.id.slideMenu);
		slidemenu.init(this, R.menu.myslide, new SlideMenuListener(
				(Activity) context, R.id.item_seven), 333);
	

	}



	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: // this is the app icon of the actionbar
			slidemenu.show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public void onConnectionFailed(ConnectionResult result) {

		if (!result.hasResolution()) {
			GooglePlayServicesUtil.getErrorDialog(result.getErrorCode(), this,
					0).show();
			return;
		}

		if (!mIntentInProgress) {
			// Store the ConnectionResult for later usage
			mConnectionResult = result;

			if (mSignInClicked) {
				// The user has already clicked 'sign-in' so we attempt to
				// resolve all
				// errors until the user is signed in, or they cancel.
				resolveSignInError();
			}
		}

	}

	@Override
	public void onResult(LoadPeopleResult peopleData) {

		// getting Friends ID

		Log.e("Check", "" + peopleData.getStatus().getStatusCode());

		if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
			PersonBuffer personBuffer = peopleData.getPersonBuffer();

			try {
				int count = personBuffer.getCount();
				for (int i = 0; i < count; i++) {
					Log.e(TAG, "Display name: "
							+ personBuffer.get(i).getDisplayName());
					Log.e(TAG, "id : " + personBuffer.get(i).getAboutMe());
					Log.e(TAG, "id : " + personBuffer.get(i).getUrl());
					friendsID.add("" + personBuffer.get(i).getId());
					Log.e("", personBuffer.get(i).getUrl());
					HashMap<String, String> person = new HashMap<String, String>();
					person.put(NAME_KEY, personBuffer.get(i).getDisplayName());
					person.put(ID_KEY, personBuffer.get(i).getId());
					person.put(CHECK, "0");
					friends.add(person);

				}

				Friends.list = friends;
				if (progressDialog != null && progressDialog.isShowing()) {
					progressDialog.dismiss();
				}
				startActivityForResult(new Intent(ReferActivity.this,
						SelectFriendsActivity.class), RC_EMAIL);

			} finally {
				personBuffer.close();
			}
		} else {
			Log.e(TAG,
					"Error requesting visible circles: "
							+ peopleData.getStatus());
		}

		// Sharing Predefined Post to Friends
		// Friends.list=friends;

		// sharePost();

	}

	@Override
	public void onConnected(Bundle arg0) {

		// mSignInClicked = false;
		// Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
		// // getProfileInformation();
		// //
		// signInWithGplus();
		//
		// // getPeople();
		//
		// Log.e("", "OnConnected");

	}

	@Override
	public void onConnectionSuspended(int arg0) {

		mGoogleApiClient.connect();

	}

	private void resolveSignInError() {
		if (mConnectionResult.hasResolution()) {
			try {
				mIntentInProgress = true;
				mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
			} catch (SendIntentException e) {
				mIntentInProgress = false;
				mGoogleApiClient.connect();
			}
		}
	}

	private void sharePost() {

		Log.e("", "" + Friends.list);
		recipients.clear();

		if (friends.size() > 0) {
			for (int i = 0; i < friends.size(); i++) {
				if (friends.get(i).get(CHECK).equals("1")) {
					recipients.add(PlusShare.createPerson(
							friends.get(i).get(ID_KEY),
							friends.get(i).get(NAME_KEY)));
				}

			}
			Intent shareIntent = new PlusShare.Builder(this)
					.setType("text/plain")
					.setText(getResources().getString(R.string.refer_content))
					.setRecipients(null,recipients)

					.getIntent();
			LoginDetails.refer_count = friends.size();
			startActivityForResult(shareIntent, RC_G_FRIENDS);
		} else {
			Toast.makeText(getApplicationContext(),
					"Unable to Fetch Friends List", Toast.LENGTH_SHORT).show();
		}

	}

	private void getPeople() {

		Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(
				this);

		Log.e("", "getPeople");
	}

	protected void onStart() {
		super.onStart();
		mGoogleApiClient.connect();

	}

	protected void onStop() {
		super.onStop();
		if (mGoogleApiClient.isConnected()) {
			mGoogleApiClient.disconnect();
		}
	}

	private void signInWithGplus() {
		if (!mGoogleApiClient.isConnecting()) {
			mSignInClicked = true;
			resolveSignInError();
		}
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			startActivity(new Intent(ReferActivity.this,
					ProfileActivity1.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NEW_TASK));

		}
		return super.onKeyDown(keyCode, event);
	}

}
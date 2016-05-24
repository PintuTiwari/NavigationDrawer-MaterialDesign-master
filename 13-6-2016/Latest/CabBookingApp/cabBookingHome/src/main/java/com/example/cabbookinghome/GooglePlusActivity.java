package com.example.cabbookinghome;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.IntentSender.SendIntentException;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.coboltforge.slidemenu.SlideMenu;
import com.coboltforge.slidemenu.SlideMenuInterface.OnSlideMenuItemClickListener;
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
import epbit.helper.CheckUserType;

public class GooglePlusActivity extends Activity implements
		ConnectionCallbacks, OnConnectionFailedListener,
		OnSlideMenuItemClickListener, ResultCallback<LoadPeopleResult> {

	public static String mShareDescription = "";
	private boolean mIntentInProgress;
	private boolean mSignInClicked;
	private ConnectionResult mConnectionResult;
	private GoogleApiClient mGoogleApiClient;
	SlideMenu slidemenu;
	List<Person> recipients;
	ProgressDialog progressDialog;
	List<HashMap<String, String>> friends = new ArrayList<HashMap<String, String>>();
	List<String> friendsID = new ArrayList<String>();
	private static final int RC_SIGN_IN = 5;
	public int RC_EMAIL = 7;
	public int RC_G_FRIENDS = 8;
	String NAME_KEY = "name";
	String ID_KEY = "id";
	String CHECK = "check";
	String FRIEND_LIST = "friend_list";
	Intent intent;
	int refercount = 0;
	boolean flag = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);

		friends.clear();

		progressDialog = ProgressDialog.show(GooglePlusActivity.this, "",
				"Please Wait.....", true, true);

		mShareDescription = getResources().getString(R.string.refer_content);
		mGoogleApiClient = new GoogleApiClient.Builder(this)
				.addConnectionCallbacks(this)
				.addOnConnectionFailedListener(this).addApi(Plus.API)
				.addScope(Plus.SCOPE_PLUS_LOGIN)
				.addScope(Plus.SCOPE_PLUS_PROFILE).build();
		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#00B0FA")));
		getActionBar().setTitle(getResources().getString(R.string.header_name));
		getActionBar().setIcon(R.drawable.menu);
		// mGoogleApiClient.connect();
	}

	@Override
	public void onConnected(Bundle arg0) {
		
		mSignInClicked = false;
		// Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();
		// getProfileInformation();
		//
		// signInWithGplus();
		if (flag == true)
			getPeople();
		flag = false;

		Log.e("", "OnConnected");
	}

	@Override
	public void onConnectionSuspended(int arg0) {
		
		mGoogleApiClient.connect();

	}

	private void sharePost() {
		
		Log.e("", "" + Friends.list);
		recipients = new ArrayList<Person>();
		recipients.clear();

		if (friends.size() > 0) {

			LoginDetails.REFER_IDS = new String[friends.size()];
			int count = 0;

			for (int i = 0; i < friends.size(); i++) {
				if (friends.get(i).get(CHECK).equals("1")) {

					recipients.add(PlusShare.createPerson(
							friends.get(i).get(ID_KEY),
							friends.get(i).get(NAME_KEY)));
					// LoginDetails.REFER_IDS[count]=new String();
					LoginDetails.REFER_IDS[count] = friends.get(i).get(ID_KEY);
					count++;
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

	private void attachSlideMenu() {
		
		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#00B0FA")));
		getActionBar().setTitle(getResources().getString(R.string.app_name));
		getActionBar().setIcon(R.drawable.menu);

		slidemenu = (SlideMenu) findViewById(R.id.slideMenu);
		slidemenu.init(this, R.menu.myslide, this, 333);
		// this can set the menu to initially shown instead of hidden
		// slidemenu.setAsShown();

		// set optional header image
		slidemenu.setHeaderImage(getResources().getDrawable(
				R.drawable.ic_launcher));

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
			resolveSignInError();
			if (mSignInClicked) {
				// The user has already clicked 'sign-in' so we attempt to
				// resolve all
				// errors until the user is signed in, or they cancel.
				resolveSignInError();
			}
		}
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

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RC_SIGN_IN) {
			if (resultCode != RESULT_OK) {
				mSignInClicked = false;
			}

			mIntentInProgress = false;

			if (!mGoogleApiClient.isConnecting()) {
				mGoogleApiClient.connect();
			}
		}
		if (requestCode == RC_EMAIL && resultCode == RESULT_CANCELED) {
			Toast.makeText(getApplicationContext(),
					"Unable to Retrieve contacts !!", Toast.LENGTH_SHORT)
					.show();
			setResult(RESULT_CANCELED, new Intent());
			finish();

		}

		if (requestCode == RC_EMAIL) {
			if (resultCode == RESULT_OK) {

				refercount = getIntent().getIntExtra("count", 0);
				LoginDetails.refer_count = getIntent().getIntExtra("count", 0);
				sharePost();

			}

		}
		if (requestCode == RC_G_FRIENDS && resultCode == RESULT_OK) {
			setResult(RESULT_OK, new Intent().putExtra("count", refercount));
			finish();
		}
		if (requestCode == RC_G_FRIENDS && resultCode == RESULT_CANCELED) {
			// Toast.makeText(getApplicationContext(), "Result canceled",
			// Toast.LENGTH_LONG).show();
			setResult(RESULT_CANCELED,
					new Intent().putExtra("count", refercount));
			finish();
		}

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

	private void getPeople() {
		

		Plus.PeopleApi.loadVisible(mGoogleApiClient, null).setResultCallback(
				this);

		Log.e("", "getPeople");
	}

	@Override
	public void onSlideMenuItemClick(int itemId) {
		

		
		switch (itemId) {

		case R.id.item_home:

			CheckUserType.intentservice(getApplicationContext());

			finish();
			break;

		case R.id.item_one:
			startActivity(new Intent(GooglePlusActivity.this, Profile.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();
			break;
		case R.id.item_two:

			startActivity(new Intent(GooglePlusActivity.this, Rides.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();

			break;
		case R.id.item_three:
//			 startActivity(new Intent(ProfileActivity1.this, CabMoney.class)
//			 .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//			 finish();

			break;
		case R.id.item_four:
			startActivity(new Intent(GooglePlusActivity.this, RateCard.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();

			break;
		case R.id.item_five:
			startActivity(new Intent(GooglePlusActivity.this, Help.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();

			break;
		case R.id.item_six:
			startActivity(new Intent(GooglePlusActivity.this, SignOut.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();

			break;
		case R.id.item_seven:
			startActivity(new Intent(GooglePlusActivity.this,
					ReferActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();

			break;

		}

	}

	@Override
	public void onResult(LoadPeopleResult peopleData) {
		
		friends.clear();
		// Friends.list.clear();
		int statuscode = peopleData.getStatus().getStatusCode();
		if (peopleData.getStatus().getStatusCode() == CommonStatusCodes.SUCCESS) {
			PersonBuffer personBuffer = peopleData.getPersonBuffer();

			try {
				int count = personBuffer.getCount();
				for (int i = 0; i < count; i++) {
					friendsID.add("" + personBuffer.get(i).getId());
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
				startActivityForResult(new Intent(GooglePlusActivity.this,
						SelectFriendsActivity.class), RC_EMAIL);

			} finally {
				personBuffer.close();
			}
		} else {

			Toast.makeText(getApplicationContext(),
					"Error Fetching Your Friend list ", Toast.LENGTH_LONG)
					.show();

		}

	}

	@Override
	protected void onRestart() {
		
		super.onRestart();
		flag = false;
	}

	private void signInWithGplus() {
		if (!mGoogleApiClient.isConnecting()) {
			mSignInClicked = true;
			resolveSignInError();
		}
	}

}

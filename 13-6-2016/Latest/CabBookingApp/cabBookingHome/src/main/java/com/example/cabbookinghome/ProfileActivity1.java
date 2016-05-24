package com.example.cabbookinghome;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.ColorDrawable;
import android.location.Location;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.Toast;

import com.coboltforge.slidemenu.SlideMenu;
import com.google.android.gcm.GCMRegistrar;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.GoogleMap.OnCameraChangeListener;
import com.google.android.gms.maps.GoogleMap.OnMapLoadedCallback;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import epbit.Login.LoginDetails;
import epbit.adapter.SlideMenuListener;
import epbit.constants.IWebConstant;
import epbit.gcmutils.CommonUtilities;
import epbit.gcmutils.ConnectionDetector;
import epbit.gcmutils.ServerUtilities;
import epbit.helper.ConversionTaskLatLonLoc;
import epbit.helper.CustomInfowindow;
import epbit.latlong.GPSTracker;
import epbit.latlong.LatLongDetails;
import epbit.utils.AlertUtil;
import epbit.utils.AlertUtil.ConnectionDialogClickListener;
import epbit.utils.MapUtil;

public class ProfileActivity1 extends FragmentActivity implements
OnClickListener, OnMapLoadedCallback {

private SlideMenu slidemenu;
private static GoogleMap googlemap;
GPSTracker gps;
Context context;
ConnectionDetector cd;
LatLongDetails user_latlongobj = new LatLongDetails();
ImageButton cabtypeyellowcabib, cabtypepersonalib, cabtypelimoseib,
	selectdestinationib;
AsyncTask<Void, Void, Void> mRegisterTask;
ProgressDialog dialog, dialog2;
// ImageButton refreshbutton;
private MarkerOptions marker;
private final int BACK_DIALOG = 1;

@Override
protected void onCreate(Bundle savedInstanceState) {
super.onCreate(savedInstanceState);
setContentView(R.layout.profiles);
context = ProfileActivity1.this;
gcm_server_process();
if (getIntent().getBooleanExtra("EXIT", false)) {
	finish();
}
LoginDetails.Address = "Getting your location .....";

getActionBar().setDisplayHomeAsUpEnabled(false);
getActionBar().setBackgroundDrawable(
		new ColorDrawable(getResources().getColor(
				R.color.action_titlebar_color)));
getActionBar().setTitle(getResources().getString(R.string.app_name));
getActionBar().setIcon(R.drawable.menu);
selectdestinationib = (ImageButton) findViewById(R.id.selectdestinationbutton);
selectdestinationib.setVisibility(View.INVISIBLE);
findViewById(R.id.refresh_map).setOnClickListener(this);
slidemenu = (SlideMenu) findViewById(R.id.slideMenu);
slidemenu.init(this, R.menu.myslide, new SlideMenuListener(
		(Activity) context, R.id.item_home), 333);
gps = new GPSTracker(ProfileActivity1.this, this);
slidemenu.setHeaderImage(getResources().getDrawable(
		R.drawable.ic_launcher));
dialog = ProgressDialog.show(ProfileActivity1.this, "",
		getString(R.string.getting_location), true, false);
setonClickListners();

}

private void load_map() {
user_latlongobj = findLocation();

intialiseMap();

animatecamera(user_latlongobj);
}

private void gcm_server_process() {
cd = new ConnectionDetector(getApplicationContext());

if (!cd.isConnectingToInternet()) {
	// alert.showAlertDialog(ProfileActivity1.this,
	// IWebConstant.GCM_INTERNET_ERROR,
	// IWebConstant.GCM_INTERNET_ERROR_MESSAGE, false);
	return;
}

try {
	GCMRegistrar.checkDevice(this);
	GCMRegistrar.checkManifest(this);
} catch (Exception e) {
	Log.e("EXCEPTION", e.toString());
}

registerReceiver(mHandleMessageReceiver, new IntentFilter(
		CommonUtilities.DISPLAY_MESSAGE_ACTION));

final String regId = GCMRegistrar.getRegistrationId(this);

LoginDetails.GCM_Reg_id = regId;
if (regId.equals("")) {
	GCMRegistrar.register(this, IWebConstant.SENDER_ID);

} else {
	// Device is already registered on GCM
	Log.e("GCM", "DEVICE ALREADY REGISTERED");
	if (GCMRegistrar.isRegisteredOnServer(this)) {
		final Context context = this;
		mRegisterTask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				// Register on our server
				// On server creates a new user

				ServerUtilities.register(context,
						LoginDetails.Username, LoginDetails.GCM_Reg_id);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				mRegisterTask = null;
			}

		};
		mRegisterTask.execute(null, null, null);

	} else {

		final Context context = this;
		mRegisterTask = new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... params) {
				ServerUtilities.register(context,
						LoginDetails.Username, LoginDetails.GCM_Reg_id);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				mRegisterTask = null;
			}

		};
		mRegisterTask.execute(null, null, null);
	}
}

}

/**
* Receiving push messages
* */
private final BroadcastReceiver mHandleMessageReceiver = new BroadcastReceiver() {
@Override
public void onReceive(Context context, Intent intent) {
	/*
	 * flag 1 to update driver location to the server flag 2 to show a
	 * message to driver to call its customer
	 */

	String flag = ""
			+ intent.getExtras()
					.getString(CommonUtilities.EXTRA_MESSAGE).charAt(0);

	String newMessage = intent.getExtras()
			.getString(CommonUtilities.EXTRA_MESSAGE).substring(1);

}
};

@Override
protected void onRestart() {

super.onRestart();
LoginDetails.Address = "Getting your location .....";
load_map();
}

@Override
protected void onResume() {

super.onResume();
try {

	load_map();
	new ConversionTaskLatLonLoc(context).execute();
	googlemap.setOnMapLoadedCallback(this);

} catch (Exception e) {
	Toast.makeText(getApplicationContext(), R.string.map_expception,
			Toast.LENGTH_SHORT).show();
	e.printStackTrace();
}
}

// method to intialise the Map on my Activity
private void getCabData() {

cabtypeyellowcabib.setImageDrawable(getResources().getDrawable(
		R.drawable.yellow_cab));
;
cabtypepersonalib.setImageDrawable(getResources().getDrawable(
		R.drawable.personal_cab));
cabtypelimoseib.setImageDrawable(getResources().getDrawable(
		R.drawable.limousine));
switch (LoginDetails.User_Cab_Type) {
case 1:
	cabtypeyellowcabib.setImageDrawable(getResources().getDrawable(
			R.drawable.yellow_cab_clicked));
	break;
case 2:
	cabtypepersonalib.setImageDrawable(getResources().getDrawable(
			R.drawable.personal_cab_clicked));
	break;

case 3:
	cabtypelimoseib.setImageDrawable(getResources().getDrawable(
			R.drawable.limousine_clicked));
	break;

}

}

private void intialiseMap() {

if (dialog == null)
	dialog = ProgressDialog.show(ProfileActivity1.this, "",
			getString(R.string.getting_location), true, true);
try {
	if (googlemap == null) {
		googlemap = ((MapFragment) getFragmentManager()
				.findFragmentById(R.id.mapfragment)).getMap();
		googlemap.setInfoWindowAdapter(new CustomInfowindow(context));
		// check if map is created successfully or not
		if (googlemap == null) {
			Toast.makeText(getApplicationContext(),
					R.string.maps_create_error, Toast.LENGTH_SHORT)
					.show();
		}
	}
} catch (Exception e) {
}
}

// Method to find the current Latitude and Longitude of the User
private LatLongDetails findLocation() {

LatLongDetails temp_latlongobj = new LatLongDetails();
if (gps.canGetLocation()) {
	if (gps.getLatitude() != 0) {
		temp_latlongobj.user_latitude = gps.getLatitude();
		temp_latlongobj.user_longitude = gps.getLongitude();
	} else {
		gps.getLocation();
		temp_latlongobj.user_latitude = gps.getLatitude();
		temp_latlongobj.user_longitude = gps.getLongitude();
		Log.e("Exception", "" + temp_latlongobj.user_latitude + "  "
				+ temp_latlongobj.user_longitude);
	}
}
return temp_latlongobj;
}

// method to Place the Marker on your current location
public void placeMarker(LatLongDetails user_latlongobj2,
	final Context contextPlace) {
try {
	if (googlemap == null) {
		intialiseMap();
		animatecamera(user_latlongobj);
	}
	if (LoginDetails.Address.length() < 1) {
		LoginDetails.Address = "Getting your location .....";
	}
	googlemap.clear();
	marker = new MarkerOptions().position(
			new LatLng(user_latlongobj2.user_latitude,
					user_latlongobj2.user_longitude)).title(
			LoginDetails.Address);

	System.out.println("This is the Address" + LoginDetails.Address);

	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));


	googlemap.addMarker(marker).showInfoWindow();
	System.out.println("PLACING MARKER" + LoginDetails.Address);
	if (marker == null || contextPlace == null) {
		Intent in =new Intent(this,ProfileActivity1.class);
		in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		in.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); 
		startActivity(in);
	}
	else
	fixmarker(marker, contextPlace);

} catch (Exception e) {
	fixmarker(marker, contextPlace);
	Log.d("Profileactivity", "" + e);
}

}

// animate the camera to current Latitude and Longitude

private void animatecamera(LatLongDetails user_latlongobj2) {
Log.e("animate", "done");
CameraPosition cameraPosition = new CameraPosition.Builder()
		.target(new LatLng(user_latlongobj2.user_latitude,
				user_latlongobj2.user_longitude)).zoom(16).build();
try {
	googlemap.animateCamera(CameraUpdateFactory
			.newCameraPosition(cameraPosition));

	marker = new MarkerOptions().position(
			new LatLng(user_latlongobj2.user_latitude,
					user_latlongobj2.user_longitude)).title(
			LoginDetails.Address);

	System.out.println("This is the Address" + LoginDetails.Address);

	marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));

	googlemap.addMarker(marker);
	

	marker.title("Your Location");
	if (LoginDetails.Address.equals("Getting your location .....")
			|| LoginDetails.Address.length() > 1)

		googlemap.addMarker(marker).showInfoWindow();

} catch (Exception e) {
	LoginDetails.googleplayflag = 1;
	Log.e("check", "ANIMATE CAMERA");

}

}

public  void fixmarker(final MarkerOptions marker,
	final Context contextFix) {

googlemap.setOnCameraChangeListener(new OnCameraChangeListener() {
	// try{

	@Override
	public void onCameraChange(CameraPosition arg0) {

		LatLongDetails.user_latitude = arg0.target.latitude;
		LatLongDetails.user_longitude = arg0.target.longitude;
		if(LatLongDetails.user_latitude!=0||LatLongDetails.user_longitude!=0)
		new ConversionTaskLatLonLoc(contextFix).execute();
		 else{
         	  googlemap.clear();
     			Toast.makeText(context,"error occured, please_wait", Toast.LENGTH_SHORT)
     					.show();
     			user_latlongobj = findLocation();
     			animatecamera(user_latlongobj);
     			getCabData();
           }
		MapUtil.clearMarkers(googlemap);
		MapUtil.dropPin(googlemap, arg0.target.latitude,
				arg0.target.longitude, R.drawable.marker,
				LoginDetails.Address).showInfoWindow();

	}
});

}

public void setonClickListners() {
cabtypeyellowcabib = (ImageButton) findViewById(R.id.yellowcabmapbutton);
cabtypepersonalib = (ImageButton) findViewById(R.id.personalcabmapbutton);
cabtypelimoseib = (ImageButton) findViewById(R.id.limosinemapbutton);

cabtypeyellowcabib.setOnClickListener(this);

cabtypepersonalib.setOnClickListener(this);

cabtypelimoseib.setOnClickListener(this);

selectdestinationib.setOnClickListener(this);

}

@Override
public boolean onKeyDown(int keyCode, KeyEvent event) {

if (keyCode == KeyEvent.KEYCODE_BACK) {

	AlertUtil.showAlertDialogwithListener(context, R.string.quit,
			R.string.really_quit, R.string.yes, R.string.no,
			dialog_listener, BACK_DIALOG).show();

	return true;
}
return super.onKeyDown(keyCode, event);

}

@Override
protected void onStop() {

super.onStop();
googlemap.clear();
Log.e("ProfileActivity", "OnStop is called");

googlemap = null;
try {
	unregisterReceiver(mHandleMessageReceiver);

} catch (Exception e) {

}

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

public void update_marker(Location arg0) {

user_latlongobj.user_latitude = arg0.getLatitude();
user_latlongobj.user_longitude = arg0.getLongitude();

new ConversionTaskLatLonLoc(context).execute();
animatecamera(user_latlongobj);

}

@Override
public void onClick(View v) {
switch (v.getId()) {
case R.id.refresh_map:
	googlemap.clear();
	Toast.makeText(context, R.string.please_wait, Toast.LENGTH_SHORT)
			.show();
	user_latlongobj = findLocation();
	animatecamera(user_latlongobj);
	getCabData();
	break;

case R.id.yellowcabmapbutton:
	LoginDetails.User_Cab_Type = 1;
	cabtypeyellowcabib.setImageDrawable(getResources().getDrawable(
			R.drawable.yellow_cab_clicked));
	;
	cabtypepersonalib.setImageDrawable(getResources().getDrawable(
			R.drawable.personal_cab));
	cabtypelimoseib.setImageDrawable(getResources().getDrawable(
			R.drawable.limousine));

	break;
case R.id.personalcabmapbutton:
	LoginDetails.User_Cab_Type = 2;
	cabtypeyellowcabib.setImageDrawable(getResources().getDrawable(
			R.drawable.yellow_cab));
	;
	cabtypepersonalib.setImageDrawable(getResources().getDrawable(
			R.drawable.personal_cab_clicked));
	cabtypelimoseib.setImageDrawable(getResources().getDrawable(
			R.drawable.limousine));

	break;
case R.id.limosinemapbutton:
	LoginDetails.User_Cab_Type = 3;
	cabtypeyellowcabib.setImageDrawable(getResources().getDrawable(
			R.drawable.yellow_cab));
	;
	cabtypepersonalib.setImageDrawable(getResources().getDrawable(
			R.drawable.personal_cab));
	cabtypelimoseib.setImageDrawable(getResources().getDrawable(
			R.drawable.limousine_clicked));

	break;

case R.id.selectdestinationbutton:
	int resultCode = GooglePlayServicesUtil
			.isGooglePlayServicesAvailable(getApplicationContext());

	if (resultCode == ConnectionResult.SUCCESS) {

		startActivity(new Intent(ProfileActivity1.this,
				SelectDestinationActivity.class));

	} else {

		Toast.makeText(getApplicationContext(),
				R.string.google_play_error, Toast.LENGTH_LONG).show();

	}

	break;
}
}

@Override
public void onMapLoaded() {
if (dialog != null && dialog.isShowing())
	dialog.dismiss();
selectdestinationib.setVisibility(View.VISIBLE);

}

ConnectionDialogClickListener dialog_listener = new ConnectionDialogClickListener() {

@Override
public void dialogClicklistener(int dialog_id, int button) {
	switch (dialog_id) {
	case BACK_DIALOG:
		if (button == DialogInterface.BUTTON_POSITIVE) {
			Intent intent = new Intent(getApplicationContext(),
					MainActivity.class);
			intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
			intent.putExtra("EXIT", true);
			startActivity(intent);

			finish();
		}
		break;

	}

}
};

}
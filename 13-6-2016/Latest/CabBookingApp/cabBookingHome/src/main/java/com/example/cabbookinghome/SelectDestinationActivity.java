package com.example.cabbookinghome;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.coboltforge.slidemenu.SlideMenu;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.PolylineOptions;

import epbit.Login.LoginDetails;
import epbit.adapter.SlideMenuListener;
import epbit.constants.IWebConstant;
import epbit.exception.NetworkException;
import epbit.exception.ParsingException;
import epbit.exception.ServerException;
import epbit.exception.TimeOutException;
import epbit.helper.MyWebViewClient;
import epbit.latlong.LatLongDetails;
import epbit.service.AppAsynchTask;
import epbit.utils.MapUtil;

public class SelectDestinationActivity extends ActionBarActivity implements
		 OnClickListener, TextWatcher,
		OnItemClickListener {
	View view;
	private static GoogleMap googlemap;
	TextView set_source_SD_id;
	AutoCompleteTextView destination_ACT;
	ArrayList<String> names;
	JSONArray contacts = null;
	ArrayAdapter<String> adp;
	public TextView sd_distance, sd_time;
	public Context context;
	public ProgressDialog progress;
	ImageButton book_now_Image_Button;
	private SlideMenu slidemenu;
	View mapfragment;
	private GetSuggestionTask get_sug_task;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.selecdestinationlayout);

		LoginDetails.S_D_Distance = "0 KM";
		LoginDetails.Destination = "";
		context = SelectDestinationActivity.this;
		MyWebViewClient.setActionBar(getSupportActionBar(), context, false);
		book_now_Image_Button = (ImageButton) findViewById(R.id.book_now_Imagebutton_SD);
		set_source_SD_id = (TextView) findViewById(R.id.source_textview_SD);
		destination_ACT = (AutoCompleteTextView) findViewById(R.id.autocompletetextview);
		slidemenu = (SlideMenu) findViewById(R.id.slideMenu);
		slidemenu.init(this, R.menu.myslide,  new SlideMenuListener(
				(Activity) context, R.id.item_three), 333);
		destination_ACT.setThreshold(0);
		slidemenu.setHeaderImage(getResources().getDrawable(
				R.drawable.ic_launcher));

		initView();

	}

	private void initView() {
        if (!(LoginDetails.Address.equals("Getting your location .....")))
		set_source_SD_id.setText(LoginDetails.Address);
		

		names = new ArrayList<String>();
		IntialiseMapOnSD();
		destination_ACT.addTextChangedListener(this);
		destination_ACT.setOnItemClickListener(this);
		book_now_Image_Button.setOnClickListener(this);

	}

	@Override
	protected void onResume() {

		super.onResume();
		IntialiseMapOnSD();
	}

	private void IntialiseMapOnSD() {

		if (googlemap == null) {
			googlemap = ((MapFragment) getFragmentManager().findFragmentById(
					R.id.mapfragment_SD)).getMap();
		}
		if (googlemap != null) {
			animateCameraToUserLocation("");
		} else {
			Toast.makeText(context, R.string.maps_create_error,
					Toast.LENGTH_SHORT).show();
		}

	}

	// ANIMATING CAMERA TO THE USER LOCATION
	private void animateCameraToUserLocation(String... params) {

		MapUtil.changeCameraFocus(googlemap, LatLongDetails.user_latitude,
				LatLongDetails.user_longitude, 12);

		if (params[0].equals(""))
			placeUserMarker();

	}

	// Placing Marker on the MAP

	private void placeUserMarker() {

		MapUtil.dropPin(googlemap, LatLongDetails.user_latitude,
				LatLongDetails.user_longitude, R.drawable.map_pointer, "");

	}

	public class GetSuggestionTask extends
			AppAsynchTask<String, String, String> {
		Context context;

		public GetSuggestionTask(Activity activity) {
			super(activity);
			this.context = activity;
			setShowdialog(false);

		}

		@Override
		protected String customDoInBackground(String... params)
				throws NetworkException, ServerException, ParsingException,
				TimeOutException, IOException, JSONException {
			names = new ArrayList<String>();
			names = (ArrayList<String>) MapUtil.getAddressSuggestions(
					IWebConstant.Browser_GOOGLEAPI_KEY, params[0], ""
							+ LatLongDetails.user_latitude, ""
							+ LatLongDetails.user_longitude);

			return names.toString();

		}

		@Override
		protected void customOnPostExecute(String result) {
			if (!names.isEmpty()) { // changed
				adp = new ArrayAdapter<String>(context,
						R.layout.autocompletelist, names);
				destination_ACT.setAdapter(adp);

			} else {
				Toast.makeText(context, R.string.suggesttion_error,
						Toast.LENGTH_SHORT).show();
			}
		}

	}


	
//	@Override
//	public void onSlideMenuItemClick(int itemId) {
//
//		switch (itemId) {
//
//		case R.id.item_home:
//			startActivity(new Intent(SelectDestinationActivity.this,
//					ProfileActivity1.class)
//					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//			finish();
//			break;
//		case R.id.item_one:
//			startActivity(new Intent(SelectDestinationActivity.this,
//					Profile.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//
//			break;
//		case R.id.item_two:
//
//			startActivity(new Intent(SelectDestinationActivity.this,
//					Rides.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//			break;
//		case R.id.item_three:
//			
//
//			break;
//		case R.id.item_four:
//			startActivity(new Intent(SelectDestinationActivity.this,
//					RateCard.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//
//			break;
//		case R.id.item_five:
//			startActivity(new Intent(SelectDestinationActivity.this, Help.class)
//					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//
//			break;
//		case R.id.item_six:
//			startActivity(new Intent(SelectDestinationActivity.this,
//					SignOut.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//
//			break;
//		case R.id.item_seven:
//			startActivity(new Intent(SelectDestinationActivity.this,
//					ReferActivity.class)
//					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//			finish();
//
//			break;
//
//		}
//
//	}

	
	@Override
	protected void onStop() {

		super.onStop();
		googlemap = null;
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

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			startActivity(new Intent(SelectDestinationActivity.this,
					ProfileActivity1.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
							| Intent.FLAG_ACTIVITY_NEW_TASK));

		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.book_now_Imagebutton_SD:

			if ((!LoginDetails.S_D_Distance.equalsIgnoreCase("0 KM"))
					&& LoginDetails.Destination.length() > 2)
				startActivity(new Intent(SelectDestinationActivity.this,
						RideActivity.class));
			else {
				if (LoginDetails.Destination.length() <= 0) {
					Toast.makeText(getApplicationContext(),
							R.string.select_destination_error,
							Toast.LENGTH_SHORT).show();
				}
			}

			break;

		}

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {
		if (get_sug_task != null) {
			get_sug_task.cancel(true);
		}
	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {
		googlemap.clear();
		LoginDetails.Destination = "";
		LoginDetails.S_D_Distance = "0 KM";

	}

	@Override
	public void afterTextChanged(Editable s) {
	

		if (s.length() > 3) {
			get_sug_task = new GetSuggestionTask((Activity) context);
			get_sug_task.execute(s.toString());
		}

	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {

		((InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE))
				.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);

		LoginDetails.Destination = parent.getItemAtPosition(position)
				.toString();
		

		new DrawRouteTask((Activity) context).execute();

	}

	public class DrawRouteTask extends
			AppAsynchTask<String, String, List<List<HashMap<String, String>>>> {
		Context context;

		public DrawRouteTask(Activity activity) {
			super(activity,R.string.drawing_route,false);
			this.context = activity;
		}

		@Override
		protected void onProgressUpdate(String... values) {
			super.onProgressUpdate(values);
			switch (Integer.parseInt(values[0])) {
			case 1:
				String loc[] = values[1].split(",");
				LatLongDetails.destination_latitude = Double
						.parseDouble(loc[0]);
				LatLongDetails.destination_longitude = Double
						.parseDouble(loc[1]);
				placeUserMarker();
				MapUtil.dropPin(googlemap, LatLongDetails.destination_latitude,
						LatLongDetails.destination_longitude, R.drawable.map_pointer,
						"");
				
				
				break;

			}

		}

		@Override
		protected List<List<HashMap<String, String>>> customDoInBackground(String... params)
				throws NetworkException, ServerException, ParsingException,
				TimeOutException, IOException, JSONException {

			String loc = MapUtil
					.getLatLongFromAddress(LoginDetails.Destination);
			String loc1[] = loc.split(",");
			LatLongDetails.destination_latitude = Double
					.parseDouble(loc1[0]);
			LatLongDetails.destination_longitude = Double
					.parseDouble(loc1[1]);
			publishProgress("1", loc);
			String res = "";
			HashMap<String, String> response = new HashMap<String, String>();
			List<List<HashMap<String, String>>> routes = new ArrayList<List<HashMap<String, String>>>();
			response = MapUtil.getTimeDistanceBetweenMapPoints(""
					+ LatLongDetails.user_latitude, ""
					+ LatLongDetails.user_longitude, ""
					+ LatLongDetails.destination_latitude, ""
					+ LatLongDetails.destination_longitude,
					IWebConstant.DISTANCE_UNITS);

			LoginDetails.S_D_Distance = response.get("distance");
			LoginDetails.S_D_Time = response.get("duration");
			res = response.get("response");
			routes = MapUtil.getRoutesBetweenMapPoints(res);

			return routes;
		}

		@Override
		protected void customOnPostExecute(List<List<HashMap<String, String>>> result) {

			if (result.size() == 0) {
				Toast.makeText(context, "No Route Found",
						Toast.LENGTH_SHORT).show();
			} else {
				ArrayList<LatLng> points = null;
				PolylineOptions polyLineOptions = null;

				// traversing through routes
				for (int i = 0; i < result.size(); i++) {
					points = new ArrayList<LatLng>();
					polyLineOptions = new PolylineOptions();
					List<HashMap<String, String>> path = result.get(i);

					for (int j = 0; j < path.size(); j++) {
						HashMap<String, String> point = path.get(j);

						double lat = Double.parseDouble(point.get("lat"));
						double lng = Double.parseDouble(point.get("lng"));
						LatLng position = new LatLng(lat, lng);

						points.add(position);
					}

					polyLineOptions.addAll(points);

					polyLineOptions.width(10);
					polyLineOptions.color(Color.RED);
				}

				googlemap.addPolyline(polyLineOptions);

				animateCameraToUserLocation("yes");
				Log.e("DISTANCE", LoginDetails.S_D_Distance);
				Log.e("TIME", LoginDetails.S_D_Time);

			}

		}

	}

}
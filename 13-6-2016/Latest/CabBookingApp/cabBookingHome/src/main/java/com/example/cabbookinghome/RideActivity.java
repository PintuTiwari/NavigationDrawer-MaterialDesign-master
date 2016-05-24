package com.example.cabbookinghome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.coboltforge.slidemenu.SlideMenu;
import com.example.cabbookinghome.R;

import epbit.Login.LoginDetails;
import epbit.adapter.SlideMenuListener;
import epbit.constants.IWebConstant;
import epbit.constants.ProjectURLs;
import epbit.gcmutils.ConnectionDetector;
import epbit.helper.Connection;
import epbit.helper.MyWebViewClient;
import epbit.latlong.LatLongDetails;
import epbit.service.ConfirmRejectCabService;
import epbit.service.RideNowConfirmService;

public class RideActivity extends ActionBarActivity implements
		 OnClickListener,
		android.content.DialogInterface.OnClickListener {

	private SlideMenu slidemenu;
	ImageButton ride_later, ride_now, confirm, reject;
	TextView source, cab_type, destination, distance, dis_time, cab_time,
			driver_name, contact, cab_num, fare, progress_text_view;
	LinearLayout getting_cabdetailsLL, got_cabDetailsLL, ridenowlaterlayout;
	ProgressBar progress;
	AsyncTask<Void, Void, Void> mRegisterTask;
	AlertDialog messagedialog;
	Context context;
	ConnectionDetector cd;
	public static boolean flag = false;
	HashMap<String, String> hashState = new HashMap<String, String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.ridescreen);
		initview();
		context = RideActivity.this;
		setMenu();
		setData();

		Update_user_loc_task update_task = new Update_user_loc_task(
				RideActivity.this, ridenowlaterlayout);
		update_task.execute();
	}

	public void initview() {
		getting_cabdetailsLL = (LinearLayout) findViewById(R.id.gettingdetailsLL);
		got_cabDetailsLL = (LinearLayout) findViewById(R.id.cab_details_layout);
		cab_time = (TextView) findViewById(R.id.cab_reach_time_Ride);
		driver_name = (TextView) findViewById(R.id.driver_name_Ride);
		contact = (TextView) findViewById(R.id.driver_contact_num_Ride);
		cab_num = (TextView) findViewById(R.id.Cab_num_Ride);
		progress_text_view = (TextView) findViewById(R.id.cab_details_progress_textview);
		progress = (ProgressBar) findViewById(R.id.cab_details_progress);
		ride_now = (ImageButton) findViewById(R.id.ride_now_IMGbutton);
		confirm = (ImageButton) findViewById(R.id.confirm_IMGbutton);
		reject = (ImageButton) findViewById(R.id.reject_IB_Ride);
		ridenowlaterlayout = (LinearLayout) findViewById(R.id.ridenowlaterbuttonlayout);
		ride_later = (ImageButton) findViewById(R.id.ride_later_IB_Ride);
		ridenowlaterlayout.setVisibility(View.INVISIBLE);
		source = (TextView) findViewById(R.id.current_loc_Ride);
		cab_type = (TextView) findViewById(R.id.cab_type_Ride);
		destination = (TextView) findViewById(R.id.destination_Ride);
		distance = (TextView) findViewById(R.id.distance_Ride);
		fare = (TextView) findViewById(R.id.fare_Ride);
		ride_later.setOnClickListener(this);
		ride_now.setOnClickListener(this);
		confirm.setOnClickListener(this);
		reject.setOnClickListener(this);
	}

	public void setData() {

		source.setText(LoginDetails.Address);
		if (LoginDetails.User_Cab_Type == 1) {
			fare.setText("$75");
			cab_type.setText("Yellow Cab");
		} else if (LoginDetails.User_Cab_Type == 2) {
			fare.setText("$100");
			cab_type.setText("Personal Cab");
		} else {
			fare.setText("$250");
			cab_type.setText("Limousine");
		}
		destination.setText(LoginDetails.Destination);

		distance.setText(LoginDetails.S_D_Distance);

	}

	private void setMenu() {

		// stubgetSupportActionBar().setDisplayHomeAsUpEnabled(false);
		MyWebViewClient.setActionBar(getSupportActionBar(), context,
				false);
		slidemenu = (SlideMenu) findViewById(R.id.slideMenu);
		slidemenu.init(this, R.menu.myslide, new SlideMenuListener(
				(Activity) context, R.id.item_three), 333);
		// this can set the menu to initially shown instead of hidden
		// slidemenu.setAsShown();

		// set optional header image
		
		// this demonstrates how to dynamically add menu items
		/*
		 * SlideMenuItem item = new SlideMenuItem(); item.id = MYITEMID;
		 * item.icon = getResources().getDrawable(R.drawable.help); item.label =
		 * "Help"; slidemenu.addMenuItem(item);
		 */

		// connect the fallback button in case there is no ActionBar
		/*
		 * ImageButton b = (ImageButton) findViewById(R.id.buttonMenu);
		 * b.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { slidemenu.show(); } });
		 */

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		return super.onCreateOptionsMenu(menu);
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

	private class Update_user_loc_task extends AsyncTask<Void, String, String> {
		Context mContext;

		@Override
		protected void onPreExecute() {

			super.onPreExecute();
			ridenowlaterlayout.setVisibility(View.INVISIBLE);
		}

		LinearLayout ridenowlaterlayout;

		public Update_user_loc_task(Context mContext,
				LinearLayout ridenowlaterlayout) {
			super();
			this.mContext = mContext;
			this.ridenowlaterlayout = ridenowlaterlayout;
		}

		@Override
		protected String doInBackground(Void... params) {

			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(
					ProjectURLs.UPDATE_USER_LOCATION_URL);

			List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>(4);
			namevaluepairs.add(new BasicNameValuePair("email",
					LoginDetails.Username));
			namevaluepairs.add(new BasicNameValuePair("lat", ""
					+ LatLongDetails.user_latitude));
			namevaluepairs.add(new BasicNameValuePair("long", ""
					+ LatLongDetails.user_longitude));
			namevaluepairs.add(new BasicNameValuePair("cabtype", ""
					+ LoginDetails.User_Cab_Type));
			Log.e("RIDE ACTIVITY", "" + namevaluepairs.toString());
			try {
				httppost.setEntity(new UrlEncodedFormEntity(namevaluepairs));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			httppost.setHeader("Content-type",
					"application/x-www-form-urlencoded");
			httppost.setHeader("Accept", "application/json");
			Log.e("URL POST", httppost.toString());
			try {

				HttpResponse str = httpclient.execute(httppost);

				if (str != null) {
					InputStream is = str.getEntity().getContent();

					BufferedReader reader = new BufferedReader(
							new InputStreamReader(is));
					StringBuilder sb = new StringBuilder();
					String line = null;
					while ((line = reader.readLine()) != null) {

						sb.append(line + "\n");
						System.out.println("I am Here" + sb.toString());

						Log.e("RESPONSE", sb.toString());
						try {
							LoginDetails.DriverName = "NOT FOUND";
							LoginDetails.CabNumber = "NOT FOUND";
							LoginDetails.DriverNumber = "NOT FOUND";
							LoginDetails.NearesCabReachingTime = "NOT FOUND";

							JSONObject jsonresult = new JSONObject(
									sb.toString());
							LoginDetails.NearestCabDistance = (int) Float
									.parseFloat(jsonresult
											.getString("distance"));
							int time = 0;
							time = (jsonresult.getInt("reach_time"));
							int hour = 0;
							hour = time / 60;
							int min = time % 60;

							if (hour == 0) {
								LoginDetails.NearesCabReachingTime = "" + min
										+ " min";

							} else {
								LoginDetails.NearesCabReachingTime = "" + hour
										+ " h " + min + " min";
							}

							LoginDetails.NearesCabReachingTime = ""
									+ (jsonresult.getInt("reach_time"))
									+ " min";

							LoginDetails.DriverName = jsonresult
									.getString("name");
							LoginDetails.DriverNumber = jsonresult
									.getString("number");

							LoginDetails.CabNumber = jsonresult
									.getString("cab_number");
							LoginDetails.Driver_email = jsonresult
									.getString("email");
							Log.e("Driver Email", LoginDetails.Driver_email);

						} catch (Exception e) {
							Log.e("EXCEPTION", "" + e.toString());
						}

					}

				}
			} catch (ClientProtocolException e) {
				e.printStackTrace();

				Log.e("EXCEPTION", " ");

			} catch (IOException e) {
				e.printStackTrace();
				Log.e("EXCEPTION", " ");
			}

			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

			if (LoginDetails.DriverNumber.equals("NOT FOUND")
					&& LoginDetails.CabNumber.equals("NOT FOUND")
					|| LoginDetails.DriverNumber.length() <= 1
					&& LoginDetails.CabNumber.length() <= 1) {
				progress_text_view.setText("Sorry No Cabs Available");
				progress.setVisibility(View.INVISIBLE);
				RideActivity.flag = true;
			} else {
				getting_cabdetailsLL.setVisibility(View.GONE);
				got_cabDetailsLL.setVisibility(View.VISIBLE);
				ridenowlaterlayout.setVisibility(View.VISIBLE);

				cab_time.setText("" + LoginDetails.NearesCabReachingTime);
				driver_name.setText("" + LoginDetails.DriverName);
				contact.setText("" + LoginDetails.DriverNumber);
				cab_num.setText("" + LoginDetails.CabNumber);

			}

		}
	}

	@Override
	public void onClick(View v) {

		switch (v.getId()) {
		case R.id.ride_later_IB_Ride:
			startActivity(new Intent(RideActivity.this, RideLaterActivity.class));

			break;
		case R.id.ride_now_IMGbutton:

			if (Connection.isNetworkAvailable(getApplicationContext())) {

				messagedialog = new AlertDialog.Builder(RideActivity.this)
						.create();
				messagedialog.setTitle(getResources().getString(
						R.string.messagedialogtitle));
				messagedialog.setMessage(getResources().getString(
						R.string.messagedialogmessage));
				messagedialog.setButton("OK", this);
				messagedialog.show();
			}

			break;
		case R.id.confirm_IMGbutton:

			if (Connection.isNetworkAvailable(getApplicationContext())) {
				Toast.makeText(getApplicationContext(), "Confirming",
						Toast.LENGTH_LONG).show();
				new ConfirmRejectCabService(getApplicationContext(), 1,
						context).execute(IWebConstant.CONFIRM);
				confirm.setVisibility(View.INVISIBLE);
				reject.setVisibility(View.INVISIBLE);
			}

			break;
		case R.id.reject_IB_Ride:
			if (Connection.isNetworkAvailable(getApplicationContext())) {

				new ConfirmRejectCabService(getApplicationContext(), 2,
						context).execute(IWebConstant.REJECT);
				confirm.setVisibility(View.INVISIBLE);
				reject.setVisibility(View.INVISIBLE);
			}

			break;
		default:
			break;
		}

	}

	@Override
	public void onClick(DialogInterface dialog, int which) {

		new RideNowConfirmService(getApplicationContext()).execute();
		ride_now.setVisibility(View.GONE);
		ride_later.setVisibility(View.GONE);
		confirm.setVisibility(View.VISIBLE);
		reject.setVisibility(View.VISIBLE);

	}
}
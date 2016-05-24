
package com.example.cabbookinghome;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.coboltforge.slidemenu.SlideMenu;
import com.example.cabbookinghome.R;
import com.google.android.gcm.GCMRegistrar;

import epbit.Login.LoginDetails;
import epbit.Login.PassengerDetails;
import epbit.adapter.SlideMenuListener;
import epbit.alertspinner.iAm_CustomList;
import epbit.constants.AppConstants;
import epbit.constants.IWebConstant;
import epbit.constants.ProjectURLs;
import epbit.exception.NetworkException;
import epbit.exception.ParsingException;
import epbit.exception.ServerException;
import epbit.exception.TimeOutException;
import epbit.gcmutils.CommonUtilities;
import epbit.gcmutils.ConnectionDetector;
import epbit.gcmutils.ServerUtilities;
import epbit.gcmutils.WakeLocker;
import epbit.helper.MyWebViewClient;
import epbit.latlong.GPSTracker;
import epbit.latlong.LatLongDetails;
import epbit.service.AppAsynchTask;
import epbit.service.ConfirmRejectCabService;
import epbit.service.GetDriverStatus;
import epbit.service.HttpService;
import epbit.service.NameValueCreator;
import epbit.utils.AlertUtil;
import epbit.utils.AlertUtil.ConnectionDialogClickListener;

public class DriverActivity extends ActionBarActivity implements
		OnClickListener, OnItemClickListener {

	TextView name, email, regid;

	AsyncTask<Void, Void, Void> mRegisterTask;
	GPSTracker gps;
	private SlideMenu slidemenu;
	LatLongDetails driver_latlongobj = new LatLongDetails();
	LinearLayout statuslinearlayout, pass_detail_layout, button_layout,
			details_layout,update_profile;
	Context context;
	TextView driverStatustextview, linear, pass_name, pass_num, pick_loc,
			drop_loc, pick_date, pick_time, pick_time_left, pick_date_left;
	ImageView driverStatusimageview;
	public Dialog dialog;
	public ListView list;
	ImageButton confirm_driver, reject_driver;
	private final int BACK_DIALOG = 1;
	ConnectionDetector cd;

	private boolean First_time_flage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.driverlayout);
		context = DriverActivity.this;
		gcm_server_process();
		LoginDetails.Driver_status = "";
		
		SharedPreferences myPreferences=getSharedPreferences("myprefreferences", 0);
		attachSlideMenu();
		statuslinearlayout = (LinearLayout) findViewById(R.id.driverstatusllid);
		driverStatusimageview = (ImageView) findViewById(R.id.driverstatusimgid);
		pass_detail_layout = (LinearLayout) findViewById(R.id.passenger_layout_id);
		button_layout = (LinearLayout) findViewById(R.id.driver_buttons_linear_id);
		pass_name = (TextView) findViewById(R.id.cust_name_dri);
		pass_num = (TextView) findViewById(R.id.cust_num_dri);
		pick_loc = (TextView) findViewById(R.id.pick_loc_dri);
		drop_loc = (TextView) findViewById(R.id.drop_loc_dri);
		pick_time = (TextView) findViewById(R.id.pick_time_dri);
		pick_date = (TextView) findViewById(R.id.pick_date_dri);
		pick_date_left = (TextView) findViewById(R.id.pick_up_date_tex);
		pick_time_left = (TextView) findViewById(R.id.pick_up_time_tex);
		driverStatustextview = (TextView) findViewById(R.id.driverstatustextid);
		confirm_driver = (ImageButton) findViewById(R.id.confirm_img_dri);
		reject_driver = (ImageButton) findViewById(R.id.reject_img_dri);
		details_layout = (LinearLayout) findViewById(R.id.details_layout);
		update_profile=(LinearLayout)findViewById(R.id.updateprofile);
		setLinearLayoutOnClickListener(context, statuslinearlayout,
				driverStatusimageview, driverStatustextview);
		 First_time_flage=  myPreferences.getBoolean("first_time_flag",true);
	        if(First_time_flage){
	        	update_profile.setVisibility(View.VISIBLE);
	        	First_time_flage=false;
	           Editor	 editor=myPreferences.edit();
	        	editor.putBoolean("first_time_flag", First_time_flage);
	        	editor.commit();
	        }
		new GetDriverStatus(context, driverStatusimageview,
				driverStatustextview).execute();
		confirm_driver.setOnClickListener(this);
		reject_driver.setOnClickListener(this);

	}

	private LatLongDetails get_driver_location() {
		gps = new GPSTracker(DriverActivity.this, this);
		LatLongDetails temp_latlongobj = new LatLongDetails();
		if (gps.canGetLocation()) {

			temp_latlongobj.driver_latitude = gps.getLatitude();
			temp_latlongobj.driver_longitude = gps.getLongitude();

		} else {
			gps.showSettingsAlert();

		}
		return temp_latlongobj;
	}

	private void gcm_server_process() {
		cd = new ConnectionDetector(getApplicationContext());

		if (!cd.isConnectingToInternet()) {
//			AlertDialogUtil.show(DriverActivity.this,
//					IWebConstant.GCM_INTERNET_ERROR,
//					IWebConstant.GCM_INTERNET_ERROR_MESSAGE, false);
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

		// Get GCM registration id
		final String regId = GCMRegistrar.getRegistrationId(this);

		LoginDetails.GCM_Reg_id = regId;
		// Check if regid already presents
		if (regId.equals("")) {
			GCMRegistrar.register(this, IWebConstant.SENDER_ID);

		} else {
			Log.e("GCM", "DEVICE ALREADY REGISTERED");
			if (GCMRegistrar.isRegisteredOnServer(this)) {

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
			} else {

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
			 * message to driver to call its customer for ride later flag 3 to
			 * show that ride later booking is rejected flag 4 to show a message
			 * to call its customer for ride now flag 5 to show that ride now
			 * booking is rejected
			 */

			String flag = ""
					+ intent.getExtras()
							.getString(CommonUtilities.EXTRA_MESSAGE).charAt(0);

			String newMessage = intent.getExtras()
					.getString(CommonUtilities.EXTRA_MESSAGE).substring(1);
			if (flag.contains("1")) {

				// Waking up mobile if it is sleeping
				WakeLocker.acquire(getApplicationContext());

				/**
				 * Take appropriate action on this message depending upon your
				 * app requirement For now i am just displaying it on the screen
				 * */

				driver_latlongobj = get_driver_location();
				update_driverlocation(getApplicationContext(), newMessage);
				WakeLocker.release();
			}
			if (flag.contains("2")) {
				int length = newMessage.split("]").length;
				String data[] = new String[length];
				data = newMessage.split("]");
				for (int i = 0; i < data.length; i++) {
					data[i] = data[i].replace("[", "");
					Log.e("Data" + i, " " + data[i].toString());
				}

				PassengerDetails.passengername = data[0];
				PassengerDetails.passengernumber = data[1];
				PassengerDetails.passemger_pick_date = data[2];
				PassengerDetails.passenger_pick_time = data[3];
				PassengerDetails.passenger_pick_loc = data[4];
				PassengerDetails.passenger_drop_loc = data[5];
				LoginDetails.Unique_Table_ID = data[6];

				details_layout.setVisibility(View.VISIBLE);
				pass_detail_layout.setVisibility(View.VISIBLE);
				button_layout.setVisibility(View.VISIBLE);

				pick_date_left.setVisibility(View.VISIBLE);
				pick_time_left.setVisibility(View.VISIBLE);
				pick_date.setVisibility(View.VISIBLE);
				pick_time.setVisibility(View.VISIBLE);
				pass_name.setText("" + PassengerDetails.passengername);
				pass_num.setText("" + PassengerDetails.passengernumber);
				pick_loc.setText("" + PassengerDetails.passenger_pick_loc);
				drop_loc.setText("" + PassengerDetails.passenger_drop_loc);
				pick_date.setText("" + PassengerDetails.passemger_pick_date);
				pick_time.setText("" + PassengerDetails.passenger_pick_time);

				driverStatusimageview.setImageResource(R.drawable.pending);
				driverStatustextview.setText("Pending");
				LoginDetails.Driver_status = "Pending";
				new DriverStatusUpdate((Activity) context).execute();

			}
			if (flag.contains("3")) {

				Toast.makeText(context, "" + newMessage, Toast.LENGTH_SHORT)
						.show();
				pass_detail_layout.setVisibility(View.INVISIBLE);
				button_layout.setVisibility(View.INVISIBLE);
				details_layout.setVisibility(View.INVISIBLE);

				PassengerDetails.passengername = "";
				PassengerDetails.passengernumber = "";
				PassengerDetails.passemger_pick_date = "";
				PassengerDetails.passenger_pick_time = "";
				PassengerDetails.passenger_pick_loc = "";
				PassengerDetails.passenger_drop_loc = "";

				driverStatusimageview.setImageResource(R.drawable.available);
				LoginDetails.Driver_status = "Available";
				driverStatustextview.setText(LoginDetails.Driver_status);

				new DriverStatusUpdate((Activity) context).execute();
				//
			}
			if (flag.contains("4")) {
				int length = newMessage.split("]").length;
				String data[] = new String[length];
				data = newMessage.split("]");
				for (int i = 0; i < data.length; i++) {
					data[i] = data[i].replace("[", "");
					Log.e("Data" + i, " " + data[i].toString());
				}

				PassengerDetails.passengername = data[0];
				PassengerDetails.passengernumber = data[1];
				PassengerDetails.passemger_pick_date = data[2];
				PassengerDetails.passenger_pick_time = data[3];
				PassengerDetails.passenger_pick_loc = data[4];
				PassengerDetails.passenger_drop_loc = data[5];
				LoginDetails.Unique_Table_ID = data[6];

				pass_detail_layout.setVisibility(View.VISIBLE);
				button_layout.setVisibility(View.VISIBLE);
				pass_name.setText("" + PassengerDetails.passengername);
				pass_num.setText("" + PassengerDetails.passengernumber);
				pick_loc.setText("" + PassengerDetails.passenger_pick_loc);
				drop_loc.setText("" + PassengerDetails.passenger_drop_loc);
				pick_date_left.setVisibility(View.VISIBLE);
				pick_time_left.setVisibility(View.VISIBLE);
				details_layout.setVisibility(View.VISIBLE);
				pick_date.setVisibility(View.VISIBLE);
				pick_date.setText("" + PassengerDetails.passemger_pick_date);
				pick_time.setText("" + PassengerDetails.passenger_pick_time);
				pick_time.setVisibility(View.VISIBLE);
				driverStatusimageview.setImageResource(R.drawable.pending);
				driverStatustextview.setText("Pending");
				LoginDetails.Driver_status = "Pending";
				new DriverStatusUpdate((Activity) context).execute();

			}
			if (flag.contains("5")) {

				Toast.makeText(context, "" + newMessage, Toast.LENGTH_SHORT)
						.show();
				pass_detail_layout.setVisibility(View.INVISIBLE);
				button_layout.setVisibility(View.INVISIBLE);
				details_layout.setVisibility(View.INVISIBLE);
				PassengerDetails.passengername = "";
				PassengerDetails.passengernumber = "";
				PassengerDetails.passemger_pick_date = "";
				PassengerDetails.passenger_pick_time = "";
				PassengerDetails.passenger_pick_loc = "";
				PassengerDetails.passenger_drop_loc = "";

				driverStatusimageview.setImageResource(R.drawable.available);
				LoginDetails.Driver_status = "Available";
				driverStatustextview.setText(LoginDetails.Driver_status);

				new DriverStatusUpdate((Activity) context).execute();
				//
			}
			if (flag.contains("8")) {

				driverStatusimageview.setImageResource(R.drawable.booked);
				LoginDetails.Driver_status = "Booked";
				driverStatustextview.setText(LoginDetails.Driver_status);

				new DriverStatusUpdate((Activity) context).execute();
				startActivity(new Intent(DriverActivity.this, Rides.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
								| Intent.FLAG_ACTIVITY_CLEAR_TASK));
				//
			}else {
				
			}

		}

		private void update_driverlocation(Context context, String newMessage) {
			Driver_loc_update_task task = new Driver_loc_update_task(context,
					newMessage);
			if (newMessage.contains("@")) {
				task.execute();
			}

		}

	};

	private class Driver_loc_update_task extends
			AsyncTask<Void, String, String> {

		String noti_user_email;
		Context context;

		public Driver_loc_update_task(Context context, String noti_user_email) {
			super();
			this.context = context;
			this.noti_user_email = noti_user_email;
		}

		@Override
		protected String doInBackground(Void... params) {
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(ProjectURLs.DRIVER_UPDATE_LOC_URL);
			String noti[];
			noti = noti_user_email.split(":");
			List<NameValuePair> namevaluepairs = new ArrayList<NameValuePair>();
			namevaluepairs.add(new BasicNameValuePair(
					IWebConstant.Driver_autoupdate_email, ""
							+ LoginDetails.Username));
			namevaluepairs.add(new BasicNameValuePair(
					IWebConstant.Driver_autoupdate_lat, ""
							+ LatLongDetails.driver_latitude));
			namevaluepairs.add(new BasicNameValuePair(
					IWebConstant.Driver_autoupdate_long, ""
							+ LatLongDetails.driver_longitude));
			namevaluepairs.add(new BasicNameValuePair(
					IWebConstant.NAME_VALUE_PAIR_KEY_DRIVER_SEND_USER_EMAIL,
					noti[0]));
			namevaluepairs.add(new BasicNameValuePair(
					IWebConstant.NAME_VALUE_PAIR_KEY_DRIVER_STATUS, ""
							+ LoginDetails.Driver_status));

			namevaluepairs.add(new BasicNameValuePair(
					IWebConstant.NAME_VALUE_PAIR_KEY_DRIVER_CAB_TYPE, ""
							+ LoginDetails.CabType));

			Log.e("Check Driver Update", " " + LoginDetails.Username + " "
					+ LatLongDetails.driver_latitude + " "
					+ LatLongDetails.driver_longitude + " " + noti[0] + " "
					+ LoginDetails.Driver_status + " " + LoginDetails.CabType);

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
				Log.e("HITTTTTING", "" + LatLongDetails.driver_latitude + " "
						+ LatLongDetails.driver_longitude);

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

					}

				}

			} catch (ClientProtocolException e) {
				Log.e("Exception", "" + e.toString());
				e.printStackTrace();
			} catch (IOException e) {
				Log.e("Exception", "" + e.toString());
				e.printStackTrace();
			}

			return "";
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);

		}
	}

	@Override
	protected void onDestroy() {
		if (mRegisterTask != null) {
			mRegisterTask.cancel(true);
		}
		try {
			unregisterReceiver(mHandleMessageReceiver);
			GCMRegistrar.onDestroy(this);
		} catch (Exception e) {
			Log.e("UnRegister Receiver Error", "> " + e.getMessage());
		}
		super.onDestroy();
	}

	private void attachSlideMenu() {

		if (android.os.Build.VERSION.SDK_INT >= 14) {
			getSupportActionBar().setDisplayHomeAsUpEnabled(false);
		}

		MyWebViewClient.setActionBar(getSupportActionBar(), context);
		slidemenu = (SlideMenu) findViewById(R.id.slideMenu);
		slidemenu.init(this, R.menu.driverslide, new SlideMenuListener(
				(Activity) context, R.id.item_home), 333);

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

	private void setLinearLayoutOnClickListener(final Context context,
			LinearLayout statuslinearlayout,
			final ImageView driverStatusimageview,
			final TextView driverStatustextview) {
		statuslinearlayout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				dialog = new Dialog(DriverActivity.this);
				dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
				dialog.setContentView(R.layout.iamlistspinner);

				dialog.setTitle("I am .....");

				iAm_CustomList listAdapter = new iAm_CustomList(
						DriverActivity.this, AppConstants.catgories,
						AppConstants.imageids);
				list = (ListView) dialog.findViewById(R.id.iam_listview);
				list.setAdapter(listAdapter);

				list.setOnItemClickListener(DriverActivity.this);

				//
				dialog.show();

			}
		});

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.confirm_img_dri:
			driverStatustextview.setText("Booked");
			driverStatusimageview.setImageResource(R.drawable.booked);

			button_layout.setVisibility(View.INVISIBLE);
			LoginDetails.Driver_status = "Booked";
			new DriverStatusUpdate((Activity) context).execute();

			new ConfirmRejectCabService(getApplicationContext(), 1, context)
					.execute(IWebConstant.CONFIRM);

			break;
		case R.id.reject_img_dri:
			Toast.makeText(getApplicationContext(), "Please Wait....",
					Toast.LENGTH_SHORT).show();
			details_layout.setVisibility(View.INVISIBLE);
			pass_detail_layout.setVisibility(View.INVISIBLE);
			button_layout.setVisibility(View.INVISIBLE);
			driverStatustextview.setText("Available");
			driverStatusimageview.setImageResource(R.drawable.available);
			LoginDetails.Driver_status = "Available";
			new DriverStatusUpdate((Activity) context).execute();
			new ConfirmRejectCabService(getApplicationContext(), 1, context)
					.execute(IWebConstant.REJECT);
			break;
		default:
			break;
		}

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

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		switch (position) {
		case 0:
			driverStatustextview.setText("Available");
			driverStatusimageview.setImageResource(R.drawable.available);
			LoginDetails.Driver_status = "available";

			break;
		case 1:
			driverStatustextview.setText("Booked");
			driverStatusimageview.setImageResource(R.drawable.booked);
			LoginDetails.Driver_status = "booked";
			break;
		// case 2:
		// driverStatustextview.setText("Couple");
		// break;

		case 2:
			driverStatustextview.setText("DND");
			driverStatusimageview.setImageResource(R.drawable.dnd);
			LoginDetails.Driver_status = "dnd";
			break;

		}

		new DriverStatusUpdate((Activity) context).execute("");
		// Log.e("Item clicked at Position", "" + position);
		dialog.dismiss();

	}

	public class DriverStatusUpdate extends
			AppAsynchTask<String, String, String> {
		Context context;

		public DriverStatusUpdate(Activity activity) {
			super(activity, R.string.updating_status, false);
			this.context = activity;

		}

		@Override
		protected String customDoInBackground(String... params)
				throws NetworkException, ServerException, ParsingException,
				TimeOutException, IOException, JSONException {

			String res = HttpService.httpPostService(
					ProjectURLs.DRIVER_STATUS_CHANGE_URL,
					NameValueCreator.createNameValuePair(
							IWebConstant.NAME_VALUE_PAIR_KEY_EMAIL,
							LoginDetails.Username,
							IWebConstant.NAME_VALUE_PAIR_KEY_DRIVER_STATUS,
							LoginDetails.Driver_status.toLowerCase()));

			String status = "0";
			JSONObject jsonObject = new JSONObject(res);
			if (jsonObject.getString("success").contains("1"))
				status = "1";

			return status;

		}

		@Override
		protected void customOnPostExecute(String result) {
			if (result.equals("1")) {
				Toast.makeText(
						context,
						context.getString(R.string.status_updated)
								+ (LoginDetails.Driver_status.toUpperCase()
										.charAt(0) + LoginDetails.Driver_status
										.substring(1)), Toast.LENGTH_SHORT)
						.show();
			}
		}

	}
}

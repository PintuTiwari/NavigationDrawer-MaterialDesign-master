package com.example.cabbookinghome;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.text.format.Time;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.coboltforge.slidemenu.SlideMenu;
import com.example.cabbookinghome.R;

import epbit.Login.LoginDetails;
import epbit.adapter.SlideMenuListener;
import epbit.constants.IWebConstant;
import epbit.helper.CheckDateNTime;
import epbit.helper.Connection;
import epbit.helper.MyWebViewClient;
import epbit.service.ConfirmRejectCabService;
import epbit.service.RideLaterService;

public class RideLaterActivity extends ActionBarActivity {
	private SlideMenu slidemenu;
	LinearLayout book_button_layout, confirm_button_layout;
	TextView date, time, currentloc, destinationloc, cab_type, fare, distance,
			driver_name, driver_number, cab_number;
	ImageButton booknow_button, confirmnow_button, reject_button;
	AlertDialog messagedialog;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.ride_later);

		date = (TextView) findViewById(R.id.pick_up_date_RL);
		time = (TextView) findViewById(R.id.pickup_time_RL);
		currentloc = (TextView) findViewById(R.id.current_loc_RideLater);
		destinationloc = (TextView) findViewById(R.id.destination_RideLater);
		cab_type = (TextView) findViewById(R.id.cab_type_RideLater);
		fare = (TextView) findViewById(R.id.fare_RideLater);
		distance = (TextView) findViewById(R.id.distance_RideLater);
		driver_name = (TextView) findViewById(R.id.driver_name_RideLater);
		driver_number = (TextView) findViewById(R.id.driver_contact_num_RideLater);
		cab_number = (TextView) findViewById(R.id.Cab_num_RideLater);
		booknow_button = (ImageButton) findViewById(R.id.booknowbutton_RL);
		confirmnow_button = (ImageButton) findViewById(R.id.confirm_img_user_rl);
		book_button_layout = (LinearLayout) findViewById(R.id.booknow_button_layout);
		confirm_button_layout = (LinearLayout) findViewById(R.id.confim_button_layout);
		reject_button = (ImageButton) findViewById(R.id.reject_img_user_rl);
		context = RideLaterActivity.this;
		attachSlideMenu();
		setData();

		// text=(TextView)findViewById(R.id.textView1);
		// text.setText("");

		final View dialogView = View.inflate(RideLaterActivity.this,
				R.layout.datetimepicker, null);
		final AlertDialog alertDialog = new AlertDialog.Builder(
				RideLaterActivity.this).create();
		alertDialog.setView(dialogView);
		alertDialog.show();

		dialogView.findViewById(R.id.date_time_set).setOnClickListener(
				new View.OnClickListener() {
					@SuppressLint("NewApi")
					@Override
					public void onClick(View view) {
						int hour, minute;

						DatePicker datePicker = (DatePicker) dialogView
								.findViewById(R.id.date_picker);
						TimePicker timePicker = (TimePicker) dialogView
								.findViewById(R.id.time_picker);

						// datePicker.setMinDate(System.currentTimeMillis());
						// long time = calendar.getTimeInMillis();
						if (CheckDateNTime.check(datePicker, timePicker,
								getApplicationContext()) == 1) {
							try {
								datePicker.setMinDate(System
										.currentTimeMillis());
							} catch (Exception e) {
								Toast.makeText(getApplicationContext(),
										"Time Already Passed .....",
										Toast.LENGTH_SHORT).show();
							}

						} else {
							CheckDateNTime.make_time(timePicker, datePicker);
							date.setText(LoginDetails.UserDate);
							time.setText(LoginDetails.UserTime);
							Log.e("Correct", "TIME SELECTED");
							alertDialog.dismiss();

						}

						Log.e("SET BUTTON", "PRESSED");
						Log.e("Hour", "" + timePicker.getCurrentHour());
						Log.e("Minute", "" + timePicker.getCurrentMinute());
						Log.e("DAY", "" + datePicker.getDayOfMonth());
						Log.e("Month", "" + datePicker.getMonth());
						Log.e("Year", "" + datePicker.getYear());
						Log.e("Current Time", "" + System.currentTimeMillis());

						Time today = new Time();
						today.setToNow();
						Log.e("Current Time", "PRESSED");
						Log.e("Hour", "" + today.hour);
						Log.e("Minute", "" + today.minute);
						Log.e("DAY", "" + today.monthDay);
						Log.e("Month", "" + today.month);
						Log.e("Year", "" + today.year);
						Log.e("Current Time", "" + System.currentTimeMillis());
						// DateFormat formatter = new SimpleDateFormat(System);

						// Create a calendar object that will convert the date
						// and time value in milliseconds to date.
						// Calendar calendar = Calendar.getInstance();
						// calendar.setTimeInMillis(milliSeconds);
						// return formatter.format(calendar.getTime());

					}
				});

		booknow_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (Connection.isNetworkAvailable(getApplicationContext())) {

					new RideLaterService(getApplicationContext()).execute();
					messagedialog = new AlertDialog.Builder(
							RideLaterActivity.this).create();
					messagedialog.setTitle(getResources().getString(
							R.string.messagedialogtitle));
					messagedialog.setMessage(getResources().getString(
							R.string.messagedialogmessage));
					messagedialog.setButton("OK",
							new DialogInterface.OnClickListener() {

								@Override
								public void onClick(DialogInterface dialog,
										int which) {

									book_button_layout.setVisibility(View.GONE);
									confirm_button_layout
											.setVisibility(View.VISIBLE);

								}
							});
					messagedialog.show();

				}
			}
		});

		confirmnow_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				if (Connection.isNetworkAvailable(getApplicationContext())) {

					new ConfirmRejectCabService(getApplicationContext(), 1,
							context).execute(IWebConstant.CONFIRM);
					confirmnow_button.setVisibility(View.INVISIBLE);
					reject_button.setVisibility(View.INVISIBLE);
				}
			}
		});

		reject_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				{
					Toast.makeText(getApplicationContext(),
							"Rejecting your Booking", Toast.LENGTH_LONG).show();
					new ConfirmRejectCabService(getApplicationContext(), 2,
							context).execute(IWebConstant.REJECT);
					confirmnow_button.setVisibility(View.INVISIBLE);
					reject_button.setVisibility(View.INVISIBLE);
				}
			}
		});

	}

	private void setData() {

		currentloc.setText(LoginDetails.Address);
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
		destinationloc.setText(LoginDetails.Destination);
		distance.setText(LoginDetails.S_D_Distance);
		driver_name.setText(LoginDetails.DriverName);
		driver_number.setText(LoginDetails.DriverNumber);
		cab_number.setText(LoginDetails.CabNumber);

	}

	private void attachSlideMenu() {

		// setting sliding Menu
		// set_SlidingMenu(getApplicationContext());
		MyWebViewClient.setActionBar(getSupportActionBar(), context, false);

		slidemenu = (SlideMenu) findViewById(R.id.slideMenu);
		slidemenu.init(this, R.menu.myslide, new SlideMenuListener(
				(Activity) context, R.id.item_three), 333);
	

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

}

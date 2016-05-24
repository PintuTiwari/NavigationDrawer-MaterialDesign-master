package com.example.cabbookinghome;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import epbit.Login.LoginDetails;
import epbit.constants.IWebConstant;
import epbit.helper.GpsUtil;
import epbit.helper.MyWebViewClient;
import epbit.service.MyAsynchtask;
import epbit.utils.AlertUtil;
import epbit.utils.AlertUtil.ConnectionDialogClickListener;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	public MainActivity() {

		super();
	}

	private EditText username_editext, password_editext;
	ProgressBar login_progress_bar;
	private GpsUtil gps;
	Context context;
	final int BACK_DIALOG = 1;
	final int GPS_DIALOG = 2;
	private boolean First_time_flage=true;

	public MainActivity(Context context) {
		super();
		this.context = context;
	}

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);

		context = MainActivity.this;
	
		gps = new GpsUtil(getApplicationContext(), context);
		MyWebViewClient.setActionBar(getSupportActionBar(), context);

		getSupportActionBar().setIcon(
				new ColorDrawable(getResources().getColor(
						android.R.color.transparent)));
		username_editext = (EditText) findViewById(R.id.username_edittext);
		password_editext = (EditText) findViewById(R.id.password_edittext);

		login_progress_bar = (ProgressBar) findViewById(R.id.loginprogress);
		findViewById(R.id.login_image_button_id).setOnClickListener(this);
		findViewById(R.id.forgot_password_textview_id).setOnClickListener(this);
		findViewById(R.id.Register_textview_id).setOnClickListener(this);
		login_progress_bar.setVisibility(View.INVISIBLE);

		if (getIntent().getBooleanExtra("EXIT", false)) {
			MyWebViewClient.handleIncomingIntent(context, SplashActivity.class);
		}
	}

	protected void loginProcess() {

		login_progress_bar.setVisibility(View.VISIBLE);

		if (!gps.checkGpsStatus()) {


			AlertUtil.showAlertDialogwithListener(context,
					R.string.gpsdisabledtitle, R.string.gpsdisabledmessage,
					R.string.gpsposbutton, R.string.gpsnegativebutton,
					dialog_listener, GPS_DIALOG).show();
			login_progress_bar.setVisibility(View.INVISIBLE);
		}

		else {

			if (username_editext.getText().toString().length() == 0) {

				Toast.makeText(getApplicationContext(),
						IWebConstant.EMAIL_NOT_ENTERED, Toast.LENGTH_SHORT).show();
				login_progress_bar.setVisibility(View.INVISIBLE);

			} else if (username_editext.getText().toString().contains("@") == false) {

				Toast.makeText(getApplicationContext(),
						IWebConstant.EMAIL_NOT_VALID, Toast.LENGTH_SHORT).show();
				login_progress_bar.setVisibility(View.INVISIBLE);
			} else if (password_editext.getText().toString().length() == 0) {
				Toast.makeText(getApplicationContext(),
						IWebConstant.ENTER_PASSWORD, Toast.LENGTH_SHORT).show();
				login_progress_bar.setVisibility(View.INVISIBLE);
			} else {

				LoginDetails.Username = username_editext.getText().toString();
				LoginDetails.Password = password_editext.getText().toString();
				new MyAsynchtask((Activity) context).execute("vinay");

			}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {

		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			AlertUtil.showAlertDialogwithListener(MainActivity.this,
					R.string.quit, R.string.really_quit, R.string.yes,
					R.string.no, dialog_listener, BACK_DIALOG).show();
			Toast.makeText(this, "Gps dissable", Toast.LENGTH_LONG).show();

		}

		return true;
	}

//	ConnectionDialogClickListener dialoglistener = new ConnectionDialogClickListener() {
//
//		@Override
//		public void dialogClicklistener(int dialog_id, int button) {
//			switch (dialog_id) {
//			case BACK_DIALOG:
//				if (button == DialogInterface.BUTTON_POSITIVE) {
//
//					Intent intent = new Intent(context, SplashActivity.class);
//					intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//					intent.putExtra("EXIT", true);
//					startActivity(intent);
//
//					finish();
//				}
//
//				break;
//
//			case GPS_DIALOG:
//				if (button == DialogInterface.BUTTON_POSITIVE) {
//					try {
//						Intent intent = new Intent(context, SplashActivity.class);
//						intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//						intent.putExtra("EXIT", true);
//						startActivity(intent);
//
//						finish();
//					} catch (Exception e) {
//						e.printStackTrace();
//						Toast.makeText(context, R.string.gps_error,
//								Toast.LENGTH_LONG).show();
//
//					}
//
//				} else {
//					android.os.Process.killProcess(android.os.Process.myPid());
//
//				}
//				break;
//			}
//
//		}
//	};
	ConnectionDialogClickListener dialog_listener = new ConnectionDialogClickListener() {

		@Override
		public void dialogClicklistener(int dialog_id, int button) {
			switch (dialog_id) {
			case GPS_DIALOG:
				if (button == DialogInterface.BUTTON_POSITIVE)
					try {
						new GpsUtil(context, context).toogle();
					} catch (Exception e) {
						Toast.makeText(context, R.string.gps_error,
								Toast.LENGTH_LONG).show();
						e.printStackTrace();
					}
				else {
					android.os.Process.killProcess(android.os.Process.myPid());
				}
				break;

			default:
				break;
			}

		}
	};
	@Override
	protected void onRestart() {

		super.onRestart();
		username_editext.setText("");
		password_editext.setText("");
		loginProcess();

		login_progress_bar.setVisibility(View.INVISIBLE);

	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.login_image_button_id:
			loginProcess();
			break;
		case R.id.forgot_password_textview_id:
			startActivity(new Intent(MainActivity.this,
					ForgotPasswordActivity.class));
			break;
		case R.id.Register_textview_id:
			startActivity(new Intent(MainActivity.this, RegisterActivity.class));
			break;

		}
	}

}

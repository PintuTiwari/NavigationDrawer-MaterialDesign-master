package com.example.cabbookinghome;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.ProgressBar;

import com.coboltforge.slidemenu.SlideMenu;
import com.example.cabbookinghome.R;

import epbit.Login.LoginDetails;
import epbit.adapter.SlideMenuListener;
import epbit.constants.ProjectURLs;
import epbit.helper.CheckUserType;
import epbit.helper.MyWebViewClient;
import epbit.utils.SharedPreferencesUtility;

public class Profile extends ActionBarActivity {

	WebView profile_web_view;
	private SlideMenu slidemenu;
	private Handler mHandler = new Handler();
	ProgressBar profileProgressupdateProfile;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.profile);
		context = Profile.this;
		profileProgressupdateProfile = (ProgressBar) findViewById(R.id.progressBarupdateprofile);
		profile_web_view = (WebView) findViewById(R.id.profile_webview);
		MyWebViewClient.enableWebViewSettings(profile_web_view);
		MyWebViewClient.setActionBar(getSupportActionBar(), context, false);
		profile_web_view.addJavascriptInterface(new DemoJavaScriptInterface(
				this), "uploadpic");
		profileProgressupdateProfile.setVisibility(View.GONE);
		profile_web_view.setVisibility(View.VISIBLE);
		profile_web_view.setWebViewClient(new MyWebViewClient(this));
		profile_web_view.loadUrl(ProjectURLs.getProfileUrl(
				LoginDetails.Username, LoginDetails.usertype));

		attachSlideMenu();
	}

	final class DemoJavaScriptInterface {

		DemoJavaScriptInterface(Context c) {

		}

		@JavascriptInterface
		public void clickOnUploadPic() {
			mHandler.post(new Runnable() {
				public void run() {

					startActivity(new Intent(Profile.this,
							UpdateProfilePicture.class)
							.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));

				}
			});

		}

		@JavascriptInterface
		public void clickOnDriver(String fullname, String contact,
				String cab_type, String cab_number) {
			LoginDetails.CabType = Integer.parseInt(cab_number);
			SharedPreferencesUtility.saveCabType(context, LoginDetails.CabType);
			mHandler.post(new Runnable() {
				public void run() {
				}
			});

		}

	}

	private void attachSlideMenu() {
		MyWebViewClient.setActionBar(getSupportActionBar(), context);
		slidemenu = (SlideMenu) findViewById(R.id.slideMenu);
		if (LoginDetails.usertype.equalsIgnoreCase("driver")) {
			slidemenu.init(this, R.menu.driverslide, new SlideMenuListener(
					(Activity) context, R.id.item_one), 333);
		} else {
			slidemenu.init(this, R.menu.myslide, new SlideMenuListener(
					(Activity) context, R.id.item_one), 333);
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			CheckUserType.intentservice(getApplicationContext());
			finish();
		}
		return super.onKeyDown(keyCode, event);
	}

	

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			slidemenu.show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
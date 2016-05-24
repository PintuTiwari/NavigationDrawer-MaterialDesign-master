
package com.example.cabbookinghome;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;

import com.coboltforge.slidemenu.SlideMenu;
import com.example.cabbookinghome.R;

import epbit.adapter.SlideMenuListener;
import epbit.constants.ProjectURLs;
import epbit.helper.MyWebViewClient;

public class RateCard extends ActionBarActivity {
	private SlideMenu slidemenu;
	WebView ratecardwebview;
	Context context;
	ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ratecard);
		ratecardwebview = (WebView) findViewById(R.id.ratecard_webview);
		MyWebViewClient.enableWebViewSettings(ratecardwebview);
		context = RateCard.this;
		ratecardwebview.setWebViewClient(new MyWebViewClient(this));
		ratecardwebview.loadUrl(ProjectURLs.RATE_CARD_PROJECT_URL);

		attachSlideMenu();
	}

	private void attachSlideMenu() {

		MyWebViewClient.setActionBar(getSupportActionBar(), context);

		slidemenu = (SlideMenu) findViewById(R.id.slideMenu);
		slidemenu.init(this, R.menu.myslide, new SlideMenuListener(
				(Activity) context, R.id.item_four), 333);

		

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

			Log.e("ProfileActivity", "KEY EVENT CALLED");
			startActivity(new Intent(RateCard.this, ProfileActivity1.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();

		}
		return super.onKeyDown(keyCode, event);
	}

}
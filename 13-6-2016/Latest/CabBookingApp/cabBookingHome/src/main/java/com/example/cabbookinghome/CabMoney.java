package com.example.cabbookinghome;

import android.app.Activity;
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

import epbit.Login.LoginDetails;
import epbit.adapter.SlideMenuListener;
import epbit.constants.ProjectURLs;
import epbit.helper.MyWebViewClient;

public class CabMoney extends ActionBarActivity {
	WebView cabMoneywebview;
	Context context;

	private SlideMenu slidemenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cabmoney);

		context = CabMoney.this;
		cabMoneywebview = (WebView) findViewById(R.id.cabmoney_webview);
		MyWebViewClient.enableWebViewSettings(cabMoneywebview);

		cabMoneywebview.setWebViewClient(new MyWebViewClient(this));
		cabMoneywebview.loadUrl(ProjectURLs.CAB_MONEY_URL
				+ LoginDetails.Username);

		MyWebViewClient.setActionBar(getSupportActionBar(), context, false);

		slidemenu = (SlideMenu) findViewById(R.id.slideMenu);
		slidemenu.init(this, R.menu.driverslide, new SlideMenuListener(
				(Activity) context, R.id.item_three), 333);
	

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

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {

			Log.e("ProfileActivity", "KEY EVENT CALLED");
			startActivity(new Intent(CabMoney.this, DriverActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
							| Intent.FLAG_ACTIVITY_CLEAR_TOP));
			finish();

		}
		return super.onKeyDown(keyCode, event);
	}

}
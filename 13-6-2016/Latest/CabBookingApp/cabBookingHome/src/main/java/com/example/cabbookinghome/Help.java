package com.example.cabbookinghome;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.WebView;

import com.coboltforge.slidemenu.SlideMenu;
import com.example.cabbookinghome.R;

import epbit.adapter.SlideMenuListener;
import epbit.constants.ProjectURLs;
import epbit.helper.CheckUserType;
import epbit.helper.MyWebViewClient;

public class Help extends ActionBarActivity {
	WebView help_webview;
	private SlideMenu slidemenu;
	Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.help);
		help_webview = (WebView) findViewById(R.id.help_web_view);
		MyWebViewClient.enableWebViewSettings(help_webview);
		context = Help.this;
		help_webview.setWebViewClient(new MyWebViewClient(this));
		help_webview.loadUrl(ProjectURLs.HELP_PROJECT_URL);
		attachSlideMenu();

	}

	private void attachSlideMenu() {
		MyWebViewClient.setActionBar(getSupportActionBar(), context, false);

		slidemenu = (SlideMenu) findViewById(R.id.slideMenu);
		if (CheckUserType.checkuser() == 1)
			slidemenu.init(this, R.menu.myslide,new SlideMenuListener(
					(Activity) context, R.id.item_five), 333);
		else
			slidemenu.init(this, R.menu.driverslide, new SlideMenuListener(
					(Activity) context, R.id.item_five), 333);

		
	}

//	

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

			CheckUserType.intentservice(getApplicationContext());

			finish();

		}
		return super.onKeyDown(keyCode, event);
	}

}
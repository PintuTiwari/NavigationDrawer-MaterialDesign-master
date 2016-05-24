package com.example.cabbookinghome;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageButton;
import android.widget.Toast;

import com.coboltforge.slidemenu.SlideMenu;
import com.example.cabbookinghome.R;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;

import epbit.Login.LoginDetails;
import epbit.adapter.SlideMenuListener;
import epbit.constants.ProjectURLs;
import epbit.helper.CheckUserType;
import epbit.helper.MyPayPalService;
import epbit.helper.MyWebViewClient;
import epbit.service.UpdatePaymentDetails;

public class Rides extends ActionBarActivity {

	private SlideMenu slidemenu;
	WebView rides_webview;
	private Context context;
	ImageButton paypalbutton;
	private Handler mHandler = new Handler();
	public static String table_id = "";
	JSONObject js;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.rides);

		context = Rides.this;

		MyPayPalService.startPayPalConfigurationsService(context);

		rides_webview = (WebView) findViewById(R.id.rides_webview);
		MyWebViewClient.enableWebViewSettings(rides_webview);
		rides_webview.setWebChromeClient(new MyWebChromeClient());
		rides_webview.addJavascriptInterface(new DemoJavaScriptInterface(),
				"android");
		rides_webview.setWebViewClient(new MyWebViewClient(Rides.this));

		context = Rides.this;
		if (LoginDetails.usertype.equalsIgnoreCase("driver")) {

			rides_webview.loadUrl(ProjectURLs.RIDES_Driver_URL
					+ LoginDetails.Username);
		} else {

			rides_webview.loadUrl(ProjectURLs.RIDES_Passenger_URL
					+ LoginDetails.Username);

		}
		attachSlideMenu();

	}

	final class DemoJavaScriptInterface {

		DemoJavaScriptInterface() {

		}

		@JavascriptInterface
		public void clickOnMakePayment(final String amount, final String id,
				final String currency) {
			mHandler.post(new Runnable() {
				public void run() {
					// mWebView.loadUrl("javascript:wave()");

					if (Float.parseFloat(amount) > 0) {
						Log.e("VALUES", "" + id);
						Log.e("VALUES", "" + amount);
						Log.e("VALUES", "" + currency);
						Rides.table_id = id;

						PayPalPayment thingToBuy = MyPayPalService
								.getThingToBuy(
										PayPalPayment.PAYMENT_INTENT_SALE,
										amount, currency, "RIDE PAYMENT");
						Intent intent = new Intent(Rides.this,
								PaymentActivity.class);

						intent.putExtra(PaymentActivity.EXTRA_PAYMENT,
								thingToBuy);

						startActivityForResult(intent,
								MyPayPalService.REQUEST_CODE_PAYMENT);
					} else {
						Toast.makeText(getApplicationContext(),
								"Amout Should be Greater than 0",
								Toast.LENGTH_SHORT).show();
					}

				}
			});

		}
	}

	private void attachSlideMenu() {
		MyWebViewClient.setActionBar(getSupportActionBar(), context, false);

		slidemenu = (SlideMenu) findViewById(R.id.slideMenu);
		if (CheckUserType.checkuser() == 1)
			slidemenu.init(this, R.menu.myslide, new SlideMenuListener(
					(Activity) context, R.id.item_two), 333);
		else
			slidemenu.init(this, R.menu.driverslide,  new SlideMenuListener(
					(Activity) context, R.id.item_two), 333); // this can set
																	// the menu
																	// to
																	// initially
																	// shown
																	// instead
																	// of hidden

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {

		js = MyPayPalService.processPayPalResponse(getApplicationContext(),
				requestCode, resultCode, data, rides_webview);
		if (js == null) {
			rides_webview
					.loadUrl(ProjectURLs.PAYMENT_FAIL_URL);
		} else {

			new UpdatePaymentDetails(getApplicationContext(), js, table_id,
					rides_webview).execute("");

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

	@Override
	public void onDestroy() {
		// Stop service when done
		stopService(new Intent(this, PayPalService.class));

		super.onDestroy();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			CheckUserType.intentservice(getApplicationContext());

		}
		return super.onKeyDown(keyCode, event);
	}

	final class MyWebChromeClient extends WebChromeClient {
		@Override
		public boolean onJsAlert(WebView view, String url, String message,
				JsResult result) {
			result.confirm();
			return true;
		}
	}

}
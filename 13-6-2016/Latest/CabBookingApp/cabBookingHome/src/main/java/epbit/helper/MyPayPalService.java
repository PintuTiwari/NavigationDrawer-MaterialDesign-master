package epbit.helper;

import java.math.BigDecimal;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.webkit.WebView;
import android.widget.Toast;

import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import epbit.service.UpdatePaymentDetails;

public class MyPayPalService {

	private static final String CONFIG_CLIENT_ID = "AdNuhRDgYbmpZ8vwKfBtb6EmQrEa0nKcrQKaE6I0cPg6oz7scZRLtImyEbrT";
	private static final String TAG = "paymentExample";
	/**
	 * - Set to PaymentActivity.ENVIRONMENT_PRODUCTION to move real money.
	 * 
	 * - Set to PaymentActivity.ENVIRONMENT_SANDBOX to use your test credentials
	 * from https://developer.paypal.com
	 * 
	 * - Set to PayPalConfiguration.ENVIRONMENT_NO_NETWORK to kick the tires
	 * without communicating to PayPal's servers.
	 */
	private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
//	private static final String CONFIG_ENVIRONMENT = PayPalConfiguration.ENVIRONMENT_PRODUCTION;

	// note that these credentials will differ between live & sandbox
	// environments.

	public static final int REQUEST_CODE_PAYMENT = 1;
	private static final int REQUEST_CODE_FUTURE_PAYMENT = 2;

	public static PayPalConfiguration config = new PayPalConfiguration()
			.environment(CONFIG_ENVIRONMENT)
			.clientId(CONFIG_CLIENT_ID)
			// The following are only used in PayPalFuturePaymentActivity.
			.merchantName("My Cabbooking")
			.merchantPrivacyPolicyUri(
					Uri.parse("https://www.example.com/privacy"))
			.merchantUserAgreementUri(
					Uri.parse("https://www.example.com/legal"));

	public static PayPalPayment getThingToBuy(String paymentIntent,
			String amount, String currency, String details) {
		return new PayPalPayment(new BigDecimal(amount), currency, details,
				paymentIntent);
	}

	public static JSONObject processPayPalResponse(Context context,
			int requestCode, int resultCode, Intent data, WebView rides_webview) {

		JSONObject js = null;
		if (requestCode == REQUEST_CODE_PAYMENT) {

			if (resultCode == Activity.RESULT_OK) {
				PaymentConfirmation confirm = data
						.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
				if (confirm != null) {
					try {
						Log.i("", confirm.toJSONObject().toString(4));
						js = confirm.toJSONObject();
						Log.i(TAG, confirm.getPayment().toJSONObject()
								.toString(4));

						/**
						 * TODO: send 'confirm' (and possibly
						 * confirm.getPayment() to your server for verification
						 * or consent completion. See
						 * https://developer.paypal.com
						 * /webapps/developer/docs/integration
						 * /mobile/verify-mobile-payment/ for more details.
						 * 
						 * For sample mobile backend interactions, see
						 * https://github
						 * .com/paypal/rest-api-sdk-python/tree/master
						 * /samples/mobile_backend
						 */
						// new UpdatePaymentDetails(context, js, unique_id)

						Toast.makeText(
								context,
								"PaymentConfirmation info received from PayPal",
								Toast.LENGTH_LONG).show();

					} catch (JSONException e) {
						Toast.makeText(context,
								"an extremely unlikely failure occurred: ",
								Toast.LENGTH_LONG).show();
					}
				}
			} else if (resultCode == Activity.RESULT_CANCELED) {
				Toast.makeText(context, "Payment has been canceled.",
						Toast.LENGTH_SHORT).show();
			} else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {

				Toast.makeText(
						context,
						"An invalid Payment or PayPalConfiguration was submitted. Please see the docs.",
						Toast.LENGTH_LONG).show();
			}
		}
		return js;
	}

	public static void startPayPalConfigurationsService(Context context) {
		Intent intent = new Intent(context, PayPalService.class);
		intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION,
				MyPayPalService.config);
		context.startService(intent);

	}

}
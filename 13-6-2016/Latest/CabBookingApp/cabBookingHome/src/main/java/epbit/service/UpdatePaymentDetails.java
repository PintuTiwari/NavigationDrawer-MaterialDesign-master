package epbit.service;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.webkit.WebView;

import com.example.cabbookinghome.Rides;

import epbit.constants.IWebConstant;
import epbit.constants.ProjectURLs;

public class UpdatePaymentDetails extends AsyncTask<String, String, String> {
	Context context;
	JSONObject js;
	String unique_id;
	WebView rides_webview;

	String transaction_id;
	String transaction_state;

	public UpdatePaymentDetails(Context context, JSONObject js,
			String unique_id, WebView rides_webview) {
		super();
		this.context = context;
		this.js = js;
		this.rides_webview = rides_webview;
		this.unique_id = unique_id;
	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();

	}

	@Override
	protected String doInBackground(String... params) {
		String response = "";
		try {

			if (js.has("response")) {

				transaction_id = js.getJSONObject("response").getString("id");
				transaction_state = js.getJSONObject("response").getString(
						"state");

				response = HttpService.httpPostService(
						ProjectURLs.Update_Payment_Details_URL,
						NameValueCreator.createNameValuePair(
								IWebConstant.NAME_VALUE_PAIR_KEY_PAYMENT_ID,
								Rides.table_id,
								IWebConstant.Name_VALUE_PAIR_KEY_TRANS_ID,
								transaction_id,
								IWebConstant.NAME_VALUE_PAIR_KEY_TRANS_STATE,
								transaction_state));

			}

		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (JSONException e) {

			e.printStackTrace();
		}

		return response;
	}

	@Override
	protected void onPostExecute(String result) {

		super.onPostExecute(result);
		try {
			if (new JSONObject(result).has("success")
					&& new JSONObject(result).getString("success") .equals("1")) {
				rides_webview.loadUrl(ProjectURLs.PAYMENT_SUCCESS_URL);
			} else {
				rides_webview.loadUrl(ProjectURLs.PAYMENT_FAIL_URL);

			}
		} catch (JSONException e) {

			e.printStackTrace();
		}

	}

}
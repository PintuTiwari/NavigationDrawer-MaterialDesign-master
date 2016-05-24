package epbit.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cabbookinghome.R;

import epbit.Login.LoginDetails;
import epbit.constants.IWebConstant;
import epbit.constants.ProjectURLs;

public class GetDriverStatus extends AsyncTask<String, String, String> {
	Context context;
	ImageView img;
	TextView text;
	String status;
	List<NameValuePair> list;
	String response;
	ProgressDialog dialog;
	private JSONObject json;

	public GetDriverStatus(Context context, 
			ImageView img, TextView text) {
		super();
		this.context = context;
		this.img = img;
		this.text = text;
	}

	@Override
	protected void onPreExecute() {

		super.onPreExecute();
		img.setVisibility(View.INVISIBLE);
		text.setVisibility(View.INVISIBLE);
		dialog = ProgressDialog.show(context, null,
				context.getString(R.string.get_d_status_dialog), true, false);
	}

	@Override
	protected String doInBackground(String... params) {

		response = "";

		try {
			list = NameValueCreator.createNameValuePair(
					IWebConstant.NAME_VALUE_PAIR_KEY_DRIVER_EMAIL,
					LoginDetails.Username);
			response = HttpService.httpPostService(
					ProjectURLs.GET_DRIVER_STATUS_URL, list);
			json = new JSONObject(response);
			if (json.has(IWebConstant.NAME_VALUE_PAIR_KEY_DRIVER_STATUS))
				status = json
						.getString(IWebConstant.NAME_VALUE_PAIR_KEY_DRIVER_STATUS);
			if (json.has(IWebConstant.NAME_VALUE_PAIR_KEY_CAB_TYPE))
				switch (json.getInt(IWebConstant.NAME_VALUE_PAIR_KEY_CAB_TYPE)) {
				case 7:
					LoginDetails.CabType = 1;
					break;
				case 8:
					LoginDetails.CabType = 2;
					break;
				case 9:
					LoginDetails.CabType = 3;
					break;

				default:
					break;
				}
			Log.e("RESPONSE", response);

		} catch (ClientProtocolException e) {

			e.printStackTrace();
		} catch (IOException e) {

			e.printStackTrace();
		} catch (JSONException e) {

			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(String result) {

		super.onPostExecute(result);
		dialog.dismiss();

		if (status.contains("available")) {
			img.setVisibility(View.VISIBLE);
			text.setVisibility(View.VISIBLE);
			img.setImageResource(R.drawable.available);
			text.setText("Available");
			LoginDetails.Driver_status = "available";

		} else if (status.contains("pending")) {
			img.setVisibility(View.VISIBLE);
			text.setVisibility(View.VISIBLE);
			img.setImageResource(R.drawable.pending);
			text.setText("Pending");
			LoginDetails.Driver_status = "pending";
		} else if (status.contains("booked")) {
			img.setVisibility(View.VISIBLE);
			text.setVisibility(View.VISIBLE);
			img.setImageResource(R.drawable.booked);
			text.setText("Booked");
			LoginDetails.Driver_status = "booked";
		} else {
			img.setVisibility(View.VISIBLE);
			text.setVisibility(View.VISIBLE);
			img.setImageResource(R.drawable.available);
			text.setText("No Status Available !!!");
			LoginDetails.Driver_status = "no status available";
		}
	}

}
package epbit.helper;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.example.cabbookinghome.ProfileActivity1;

import epbit.Login.LoginDetails;
import epbit.latlong.LatLongDetails;

public class ConversionTaskLatLonLoc extends AsyncTask<String, Void, String> {

	Context context;
	ProgressDialog dialog2;

	public ConversionTaskLatLonLoc(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		// dialog2=ProgressDialog.show(context.getApplicationContext(), "",
		// "getting ...",true,true);

	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		int result = 0;

		HttpClient Client = new DefaultHttpClient();
		// Create URL string
		// Log.i("httpget", URL);
		try {
			LoginDetails.Address = "Getting Your Location.....";

			String SetServerString = "";
			String url = "http://maps.googleapis.com/maps/api/geocode/json?latlng="
					+ LatLongDetails.user_latitude
					+ ","
					+ LatLongDetails.user_longitude + "&sensor=true";
			// Create Request to server and get response

			HttpGet httpget = new HttpGet(url);
			ResponseHandler<String> responseHandler = new BasicResponseHandler();

			SetServerString = Client.execute(httpget, responseHandler);
			Log.e("url", ""+url);
			Log.e("Lat Long to Addresss", SetServerString);
			// Show response on activity
			LoginDetails.conversionjsonstring = SetServerString;

			// System.out.println(SetServerString);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "" + result;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		String res = "", address = "";

		res = LoginDetails.conversionjsonstring;
		// Log.e("result", res);
		if (res != null && !res.equalsIgnoreCase("")) {
			try {
				JSONObject jObject = new JSONObject(res);
				// if (jObject.getString("status").equalsIgnoreCase("ok")) {
				JSONArray jArray = jObject.getJSONArray("results");
				if (jArray.length() > 0) {
					JSONObject geo = jArray.getJSONObject(0);
					address = geo.getString("formatted_address");
					if (address.length() < 1) {
						address = "Getting Your Location.....";
					} else {
						LoginDetails.Full_Address = address;
						String[] splite = address.split(",");
						if (splite.length > 3) {
							String temp = splite[2];
							// temp = temp.substring(0,4);
							address = "";
							address += splite[0] + "," + splite[1] + "," + temp
									+ "," + splite[3];
							// System.out.println("I AM DONE"+address);
						}
						LoginDetails.Address = address;

					}
				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("Exception Generated");
			}

		} else {

			address = "No Address";
		}
		if (dialog2 != null && dialog2.isShowing())
			dialog2.dismiss();
		Log.e("address", address);
		LoginDetails.Address = address;

		LatLongDetails object = new LatLongDetails();

		new ProfileActivity1().placeMarker(object, context);

	}

}

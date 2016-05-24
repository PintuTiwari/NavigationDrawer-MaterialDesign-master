package epbit.service;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.widget.Toast;

import com.example.cabbookinghome.R;

import epbit.Login.LoginDetails;
import epbit.constants.IWebConstant;
import epbit.constants.ProjectURLs;

public class ReferTask extends AsyncTask<String, String, String> {

	Context context;
	Activity act;
	private List<NameValuePair> nvp;
	private String response;
	private AlertDialog.Builder alert;
	private ProgressDialog progress;

	public ReferTask(Activity act, Context context) {
		super();
		this.context = context;
		this.act = act;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		progress = ProgressDialog.show(act, "", "Please wait ...", true);
		alert = new AlertDialog.Builder(act);

	}

	/*
	 * Parameter Username and Refer Count Response if "success"=0 fail
	 * "success"=1 updated but coupon not generated "success"=2 updated and
	 * coupon Generated
	 */

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub

		try {
			
			
			
			int count =0;
			for(String s: LoginDetails.REFER_IDS)
			{
				if(s!=null){
						
						count++;
				}
				
			}
			
			String temp[]=new String[count];
			 count=0;
			for(String s: LoginDetails.REFER_IDS)
			{
				if(s!=null){
						
				temp[count]=s;
				count++;
				}
				
			}
			
			
			nvp = NameValueCreator.createNameValuePair(
					IWebConstant.NAME_VALUE_PAIR_KEY_EMAIL, LoginDetails.Username,
					IWebConstant.NAME_VALUE_PAIR_REFER_COUNT, ""
							+ count,
							IWebConstant.NAME_VALUE_PAIR_REFER_IDS,
					Arrays.toString(temp));

			response = HttpService.httpPostService(
					ProjectURLs.REFER_FRIEND_URL, nvp);


		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Toast.makeText(context, "Server Not Responding", Toast.LENGTH_SHORT)
					.show();
			e.printStackTrace();
		} 

		return null;

	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		
		try{
			
			JSONObject Json = new JSONObject(response);
			progress.dismiss();
			if (Json.has("success")) {

				int value = Json.getInt("success");
				if (value == 0) {

					throw new JSONException("Failed to Update ");
				} else if (value == 1) {
					alert.setTitle(R.string.refertitle)
							.setMessage(
									act.getResources().getString(R.string.refermessage)
											+ Json.getInt("count")+" "
											+ act.getResources().getString(R.string.refermessagecontinue))
							.setNeutralButton(R.string.ok,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub

										}
									}).show();
				} else if (value == 2) {

					alert.setTitle(R.string.refertitle)
							.setMessage(R.string.refersuccessmessage)
							.setNeutralButton(R.string.ok,
									new DialogInterface.OnClickListener() {

										@Override
										public void onClick(
												DialogInterface dialog,
												int which) {
											// TODO Auto-generated method stub

										}
									}).show();

				}
			} else {
				throw new JSONException(
						"success keyword is missing in response");
			}
	}
		catch (JSONException e) {
			// TODO Auto-generated catch block
			Toast.makeText(
					context,
					"Unexpected Response from Server!! Please Try after Some time",
					Toast.LENGTH_LONG).show();
			e.printStackTrace();
		}
}
}
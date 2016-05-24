package epbit.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.AsyncTask;
import android.text.format.Time;
import android.util.Log;


import epbit.Login.LoginDetails;
import epbit.constants.IWebConstant;
import epbit.constants.ProjectURLs;

public class RideNowConfirmService extends AsyncTask<String	,String, String>
{
	Context context;
	List<NameValuePair> nameVP;
	String response;
	
	public RideNowConfirmService(Context context) {
		super();
		this.context = context;
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		
		Time today=new Time(Time.getCurrentTimezone());
		today.setToNow();
		
		nameVP=NameValueCreator.createNameValuePair(IWebConstant.NAME_VALUE_PAIR_KEY_DRIVER_EMAIL,LoginDetails.Driver_email,
				IWebConstant.NAME_VALUE_PAIR_KEY_DRIVER_CAB_TYPE,""+LoginDetails.CabType,
				IWebConstant.NAME_VALUE_PAIR_KEY_EMAIL,LoginDetails.Username,
				IWebConstant.NAME_VALUE_PAIR_KEY_PICK_ADDRESS,LoginDetails.Full_Address,
				IWebConstant.NAME_VALUE_PAIR_KEY_PICK_DATE,""+today.year+"-"+(today.month+1)+"-"+today.monthDay,
				IWebConstant.NAME_VALUE_PAIR_KEY_PICK_TIME,""+today.hour+":"+today.minute,
				IWebConstant.NAME_VALUE_PAIR_KEY_DEST_ADDRESS,LoginDetails.Destination,
				IWebConstant.Name_VALUE_PAIR_KEY_DISTANCE,LoginDetails.S_D_Distance,
				IWebConstant.NAME_VALUE_PAIR_KEY_CAB_NUMBER,LoginDetails.CabNumber
				);
		try {
			response=HttpService.httpPostService(ProjectURLs.RIDE_NOW_CONFIRM_URL, nameVP);
			Log.e("RESPONSE", response);
			LoginDetails.Unique_Table_ID=new JSONObject(response).getString(IWebConstant.NAME_VALUE_PAIR_KEY_ID);
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block'
			Log.e("EXCEPTION", "hitting CLIENT PROTOCOL Exception");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			Log.e("EXCEPTION", "hitting IO Exception");
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		//Toast.makeText(context, "Your booking is Confirmed", Toast.LENGTH_LONG).show();
	}
	
}
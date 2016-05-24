package epbit.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.example.cabbookinghome.ProfileActivity1;

import epbit.Login.LoginDetails;
import epbit.constants.IWebConstant;
import epbit.constants.ProjectURLs;


public class ConfirmRejectCabService extends AsyncTask<String, String, String>
{	
	Context context,dialog;
	String response;
	List<NameValuePair> nameVP;
	String flag="";
	JSONObject js;
	String jsonres="";
	int now_later_flag;
	ProgressDialog progressdialog;
	
	public ConfirmRejectCabService(Context context,int flag,Context dialog) {
		super();
		this.context = context;
		this.now_later_flag=flag;
		this.dialog=dialog;
	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
//		progressDialog=new AlertDialog.Builder(context).create();
//		progressDialog.setContentView(R.layout.waitdialog);
		if (progressdialog == null) {
            progressdialog = new ProgressDialog(dialog);
            progressdialog.setMessage("Please wait...");
            progressdialog.show();
        }
//		
	}

	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		
		nameVP=NameValueCreator.createNameValuePair(
				IWebConstant.NAME_VALUE_PAIR_KEY_CONFIRM_CAB,params[0],
				IWebConstant.NAME_VALUE_PAIR_USER_TYPE,LoginDetails.usertype,
				IWebConstant.NAME_VALUE_PAIR_KEY_ID,LoginDetails.Unique_Table_ID);
		try {
			if(now_later_flag==1){
			response=HttpService.httpPostService(ProjectURLs.CONFIRM_REJECT_Now_URL, nameVP);}
			else{
				response=HttpService.httpPostService(ProjectURLs.CONFIRM_REJECT_URL, nameVP);}
			Log.e("RESPONSE", response.toString());
			 js=new JSONObject(response);
			 jsonres=js.getString("success");
			
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
		return params[0];
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		
		if (progressdialog.isShowing()) {
            progressdialog.dismiss();
            progressdialog = null;
        }
		
		if(jsonres.equals("0"))
		{
			if(LoginDetails.usertype.equalsIgnoreCase("driver"))
			{
				Toast.makeText(context, "Cannot Confirm Booking!! Passenger has Rejected This Booking", Toast.LENGTH_SHORT).show();
			}
			else
			{
				Toast.makeText(context, "Cannot Confirm Booking!! Driver has Rejected This Booking", Toast.LENGTH_SHORT).show();
				
				context.startActivity(new Intent(dialog, ProfileActivity1.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
				

			}
			
			
			
		}
		else
		{
			if(result.equalsIgnoreCase("confirm"))
			{
				Toast.makeText(context, "Your Ride is Confirmed", Toast.LENGTH_SHORT).show();
				if(LoginDetails.usertype.equalsIgnoreCase("passenger"))
				{
//				context.startActivity(new Intent(dialog, Rides.class)
//				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
				}
			}
			else
			{
				Toast.makeText(context, "Your Ride is Rejected", Toast.LENGTH_SHORT).show();
				
				if(LoginDetails.usertype.equalsIgnoreCase("passenger"))
				{
				context.startActivity(new Intent(dialog, ProfileActivity1.class)
				.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
				}
				
			}
		}
		
		
	}

	
	
}
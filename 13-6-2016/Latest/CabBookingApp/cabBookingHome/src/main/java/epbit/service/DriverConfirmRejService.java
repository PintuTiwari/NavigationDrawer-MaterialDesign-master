package epbit.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;

import android.content.Context;


import epbit.Login.LoginDetails;
import epbit.constants.IWebConstant;
import epbit.constants.ProjectURLs;


public class DriverConfirmRejService 
{	static Context context;

	public DriverConfirmRejService(Context context) {
	super();
	this.context = context;
}

	public static void confirmRejectRide(int status)
	{	
		List<NameValuePair> nvp=NameValueCreator.createNameValuePair
				(IWebConstant.NAME_VALUE_PAIR_KEY_DRIVER_STATUS,""+status,
						IWebConstant.NAME_VALUE_PAIR_KEY_DRIVER_EMAIL,LoginDetails.Username);
		try {
			HttpService.httpPostService(ProjectURLs.Driver_RIDE_CONFIRM_URL, nvp);
			
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
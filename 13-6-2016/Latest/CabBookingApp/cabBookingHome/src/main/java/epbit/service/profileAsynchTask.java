
package epbit.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.content.Context;
import android.os.AsyncTask;
import android.webkit.WebView;
import android.widget.ProgressBar;
import epbit.Login.LoginDetails;
import epbit.constants.ProjectURLs;



public class profileAsynchTask extends AsyncTask<String,String,String>
{
	
	
	HttpResponse response;
	WebView profile_web_view;
	Context context;
	ProgressBar profileProgressupdateProfile;
	
	public profileAsynchTask(WebView profile_web_view,ProgressBar profileProgressupdateProfile, Context context) {
		super();
		this.profile_web_view = profile_web_view;
		this.context = context;
		this.profileProgressupdateProfile=profileProgressupdateProfile;
	}
	
	
	
	@Override
	protected String doInBackground(String... params) {
		// TODO Auto-generated method stub
		HttpClient httpclient = new DefaultHttpClient(); 
		HttpPost httppost = new HttpPost(ProjectURLs.PROFILE_ACTIVITY_URL); 
		
		
		//JSONObject json = new JSONObject(); 
		try { 
			
			List <NameValuePair> namevaluepairs=new ArrayList<NameValuePair>(2);
			  namevaluepairs.add(new BasicNameValuePair("email", LoginDetails.Username));
			  namevaluepairs.add(new BasicNameValuePair("user_type", LoginDetails.usertype));
	            httppost.setEntity(new UrlEncodedFormEntity(namevaluepairs));  
	            httppost.setHeader("Content-type",
						"application/x-www-form-urlencoded");
				httppost.setHeader("Accept", "application/json");
	             response = httpclient.execute(httppost);
		
	}
		catch(Exception e)
		{
			
		}
		return null;
	}
	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
//		profileProgressupdateProfile.setVisibility(View.GONE);
//		profile_web_view.setVisibility(View.VISIBLE);
//		profile_web_view.loadUrl(UrlMaker.makeprofileurl());
		
		
	}
	
}
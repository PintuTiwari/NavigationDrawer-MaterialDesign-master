package epbit.service;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

public class AppService
{	
	
	
	public static List<NameValuePair> doLOGIN(String username,String password)
	{
		List< NameValuePair> nameValuePair= new ArrayList<NameValuePair>();
		nameValuePair.add(new BasicNameValuePair("username", username));
		nameValuePair.add(new BasicNameValuePair("password", password));
		return nameValuePair;
		
	}
	
	
	
	
	
}
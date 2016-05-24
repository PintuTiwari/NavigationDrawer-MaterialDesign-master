package epbit.service;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import epbit.exception.NetworkException;




public class AppHttpClient
	{
	public static DefaultHttpClient httpclient;
	private static HttpURLConnection connection;
	private static OutputStreamWriter request;
	
	public static String sendHttpGetRequest(String urlString,String parameters)
	{
		String response=null;
		URL url=null;
		 try {
			 connection = (HttpURLConnection) url.openConnection();
		        connection.setDoOutput(true);
		        connection.setRequestProperty("Content-Type",
		                "application/x-www-form-urlencoded");
		        //set the request method to GET
		        connection.setRequestMethod("GET");
		        //get the output stream from the connection you created
		        request = new OutputStreamWriter(connection.getOutputStream());
		        //write your data to the ouputstream
		        request.write(parameters);
		        request.flush();
		        request.close();
		        String line = "";
		        InputStreamReader isr = new InputStreamReader(
		                connection.getInputStream());
		        //read in the data from input stream, this can be done a variety of ways
		        BufferedReader reader = new BufferedReader(isr);
		        StringBuilder sb = new StringBuilder();
		        while ((line = reader.readLine()) != null) {
		            sb.append(line + "\n");
		        }
		        //get the string version of the response data
		        response = sb.toString();
		        //do what you want with the data now

		        //always remember to close your input and output streams 
		        isr.close();
		        reader.close();
		
		 }
		 catch(Exception e)
		 {
			 System.out.println("EXCEPTION:GET:="+e.toString());
		 }
		return response;
		
	}
	
	public static String sendHttpPostRequest(String urlString,
			List<? extends NameValuePair> nameValuePairs) throws NetworkException {
		String responseData = null;
		try {
			HttpPost postRequest = new HttpPost(urlString);		
	 		httpclient = new DefaultHttpClient();
			postRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs));
			postRequest.setHeader("Content-type",
					"application/x-www-form-urlencoded");
			postRequest.setHeader("Accept", "application/json");
			HttpResponse response = null;
			response = httpclient.execute(postRequest);
			int responseCode = response.getStatusLine().getStatusCode();
			if (responseCode == 200) {
				System.out.println("RESPONSE CODE"+responseCode);
				responseData = EntityUtils.toString(response.getEntity());
			}
		} catch (Exception e) {
			throw new NetworkException();

		}
		
		
		
		return responseData;
	
	
	}
	}
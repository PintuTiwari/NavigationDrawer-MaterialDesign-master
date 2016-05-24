package epbit.helper;

import org.json.JSONArray;
import org.json.JSONObject;

import android.webkit.WebView.FindListener;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cabbookinghome.SelectDestinationActivity;

import epbit.Login.LoginDetails;
import epbit.Login.LoginResult;


public class ParseDistance_Time
{	
	String Temp;
	String res=LoginResult.response_String;
	
	public static void doMYWORK()throws Exception{
	
		
	JSONObject jsonobj=new JSONObject(LoginResult.response_String);
	
	JSONArray jsonarray=jsonobj.getJSONArray("routes");
	
	JSONObject jsonobj2=jsonarray.getJSONObject(0);
	
	JSONArray jsonArray2=jsonobj2.getJSONArray("legs");
		System.out.println(jsonobj2.getString("summary"));
	
	JSONObject jsonobj3=jsonArray2.getJSONObject(0);
	
	//getting distance 
	LoginDetails.S_D_Distance=jsonobj3.getJSONObject("distance").getString("text");
	
	//getting time
	LoginDetails.S_D_Time=jsonobj3.getJSONObject("duration").getString("text");
	 
	
	

	
	
	
	
	}
	
	
	}
	

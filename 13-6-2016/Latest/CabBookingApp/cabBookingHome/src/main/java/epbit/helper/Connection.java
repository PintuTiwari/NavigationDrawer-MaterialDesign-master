package epbit.helper;

import com.example.cabbookinghome.SelectDestinationActivity;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

public class Connection {

	public static  boolean isNetworkAvailable(Context context) {
	    ConnectivityManager connectivityManager 
	          = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
	    NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
	    		if(activeNetworkInfo != null && activeNetworkInfo.isConnected())
	    		{
	    			
	    			
	    			return true;
	    		}
	    		
	    		else {
	    			
//	    			String name=context.getClass().getSimpleName();
//	    			name=name.substring(name.lastIndexOf(".")+1);
//	    			if(context.getClass().getName()=="SelectDestinationActivity")
	    			{
	    				Toast.makeText(context, "Please Check Your Internet Connectivity !!!",Toast.LENGTH_SHORT).show();
	    			}
//	    			else{
//	    				Toast.makeText(context, "Please Check Your Internet Connectivity !!!",Toast.LENGTH_LONG).show();
//	    				
//	    				
//	    			}
					return false;
				}
	}
	
	
}

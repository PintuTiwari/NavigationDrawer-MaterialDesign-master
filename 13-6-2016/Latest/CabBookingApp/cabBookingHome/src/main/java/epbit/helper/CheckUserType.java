package epbit.helper;

import android.content.Context;
import android.content.Intent;

import com.example.cabbookinghome.DriverActivity;
import com.example.cabbookinghome.Help;
import com.example.cabbookinghome.ProfileActivity1;

import epbit.Login.LoginDetails;

public class CheckUserType
{
	
	//1 for user
	//2 for driver
	public static int checkuser()
	{
		if(LoginDetails.usertype.equalsIgnoreCase("passenger"))
		{
			return 1;
		}
		else {
			return 2;
			
		}
	}
	
	public static void intentservice(Context context)
	{
		if (CheckUserType.checkuser() == 1)
			context.startActivity(new Intent(context, ProfileActivity1.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
		else {
			context.startActivity(new Intent(context, DriverActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK));
		}
	}
	
}
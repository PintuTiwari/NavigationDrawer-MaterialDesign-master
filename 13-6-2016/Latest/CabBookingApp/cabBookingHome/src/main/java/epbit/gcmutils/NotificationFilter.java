package epbit.gcmutils;

import android.content.Context;
import android.content.Intent;

import com.example.cabbookinghome.GCMIntentService;

public class NotificationFilter {
	/*
	 * @params flag
	 * 
	 * 1 : notification for updating location 
	 * 2 : notification to call customer
	 * 3 : notification on Booking rejected 
	 * 4: notification to call customer in case of ride now 
	 * 5 : notification on booking rejected in case of ride now
	 * 6: notification for payment confirmation to user 
	 * 7: notification for payment confirmation to driver 
	 * 8: notification to inform driver that your ride has been arrived 
	 * 9: notification to inform Passenger that your ride has been arrived
	 */
	static String flag = "";
	private static String message;

	public static void processMyNoti(Context context, Intent intent) {

		// use GCM INTENT Service to display message in notification bar

		flag = "";
		if (intent.getExtras().containsKey("noti_data")) {
			message = intent.getExtras().getString("noti_data");
			flag = "" + 1;

		}
		if (intent.getExtras().containsKey("ride_confirm_msg")) {
			message = intent.getExtras().getString("ride_confirm_msg");
			flag = "" + 2;

		}
		if (intent.getExtras().containsKey("ride_reject_msg")) {
			message = intent.getExtras().getString("ride_reject_msg");
			flag = "" + 3;

		}
		if (intent.getExtras().containsKey("ride_now_confirm_msg")) {
			message = intent.getExtras().getString("ride_now_confirm_msg");
			flag = "" + 4;

		}
		if (intent.getExtras().containsKey("ride_now_reject_msg")) {
			message = intent.getExtras().getString("ride_now_reject_msg");
			flag = "" + 5;

		}

		if (intent.getExtras().containsKey("passenger_payment_msg")) {
			message = intent.getExtras().getString("passenger_payment_msg");
			flag = "" + 6;

		}

		if (intent.getExtras().containsKey("driver_payment_msg")) {
			message = intent.getExtras().getString("driver_payment_msg");
			flag = "" + 7;

		}
		if (intent.getExtras().containsKey("inform_driver_msg")) {
			message = intent.getExtras().getString("inform_driver_msg");
			flag = "" + 8;

			
		}
		if (intent.getExtras().containsKey("inform_passenger_msg")) {
			message = intent.getExtras().getString("inform_passenger_msg");
			flag = "" + 9;

		}

		CommonUtilities.displayMessage(context, flag + message);
		// notifies user
		GCMIntentService.generateNotification(context, flag + message);
		
		
		/*
		 * FaceBook SDK 
		 * */
		 
	}
}
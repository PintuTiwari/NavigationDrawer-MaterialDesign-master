package com.example.cabbookinghome;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.cabbookinghome.R;
import com.google.android.gcm.GCMBaseIntentService;

import epbit.Login.LoginDetails;
import epbit.constants.IWebConstant;
import epbit.gcmutils.CommonUtilities;
import epbit.gcmutils.NotificationFilter;
import epbit.gcmutils.ServerUtilities;

public class GCMIntentService extends GCMBaseIntentService {

	private static final String TAG = "GCMIntentService";

	public GCMIntentService() {
		super(IWebConstant.SENDER_ID);
	}

	/**
	 * Method called on device registered
	 **/
	@Override
	protected void onRegistered(Context context, String registrationId) {
		Log.i(TAG, "Device registered: regId = " + registrationId);
		CommonUtilities.displayMessage(context,
				IWebConstant.GCM_ALREADY_REGISTER_MESSAGE);
		Log.d("NAME", LoginDetails.Username);
		ServerUtilities
				.register(context, LoginDetails.Username, registrationId);
	}

	/**
	 * Method called on device un registred
	 * */
	@Override
	protected void onUnregistered(Context context, String registrationId) {
		Log.i(TAG, "Device unregistered");
		CommonUtilities.displayMessage(context,
				getString(R.string.gcm_unregistered));
		ServerUtilities.unregister(context, registrationId);
	}

	/**
	 * Method called on Receiving a new message
	 * */
	@Override
	protected void onMessage(Context context, Intent intent) {

		NotificationFilter.processMyNoti(context, intent);

	}

	/**
	 * Method called on receiving a deleted message
	 * */
	@Override
	protected void onDeletedMessages(Context context, int total) {
		Log.i(TAG, "Received deleted messages notification");
		String message = getString(R.string.gcm_deleted, total);
		CommonUtilities.displayMessage(context, message);
		// notifies user
		generateNotification(context, message);
	}

	/**
	 * Method called on Error
	 * */
	@Override
	public void onError(Context context, String errorId) {
		Log.i(TAG, "Received error: " + errorId);
		CommonUtilities.displayMessage(context,
				getString(R.string.gcm_error, errorId));
	}

	@Override
	protected boolean onRecoverableError(Context context, String errorId) {
		// log message
		Log.i(TAG, "Received recoverable error: " + errorId);
		CommonUtilities.displayMessage(context,
				getString(R.string.gcm_recoverable_error, errorId));
		return super.onRecoverableError(context, errorId);
	}

	/**
	 * Issues a notification to inform the user that server has sent a message.
	 */
	public static void generateNotification(Context context, String message) {
		int icon = R.drawable.notificationicon;
		long when = System.currentTimeMillis();
		String messagefornoti = "Cab Request is Arrived ...Confirm or Reject this Request ";
		String notibarmsg = "Click to See Passenger Details";

		NotificationManager notificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		String flag = "";

		flag = "" + message.charAt(0);

		if (flag.contains("2")) {

			/*
			 * @params icon - icon to be shown when notification arrives message
			 * to be shown when Notification Arrives
			 */

			Notification notification = new Notification(icon, messagefornoti,
					when);

			// Notification notification = new Notification(icon,
			// messagefornoti, when);

			String title = context.getString(R.string.app_name);

			Intent notificationIntent = new Intent(context,
					DriverActivity.class);
			// set intent so it does not start a new activity
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent intent = PendingIntent.getActivity(context, 0,
					notificationIntent, 0);

			/*
			 * @params icon - icon to be shown when notification arrives title
			 * Title of Notification to be shown in Notification Bar message to
			 * be shown in Notification in Notification Bar
			 */
			notification.setLatestEventInfo(context, title, notibarmsg, intent);
			notification.flags |= Notification.FLAG_NO_CLEAR;

			// Play default notification sound
			notification.defaults |= Notification.DEFAULT_SOUND;

			// notification.sound = Uri.parse("android.resource://" +
			// context.getPackageName() + "your_sound_file_name.mp3");

			// Vibrate if vibrate is enabled
			notification.defaults |= Notification.DEFAULT_VIBRATE;
			notificationManager.notify(0, notification);

		}

		if (flag.contains("1")) {
			Notification notification = new Notification(icon,
					message.substring(1), when);
			String title = context.getString(R.string.app_name);

			Intent notificationIntent = new Intent(context,
					DriverActivity.class);
			// set intent so it does not start a new activity
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent intent = PendingIntent.getActivity(context, 0,
					notificationIntent, 0);
			notification.setLatestEventInfo(context, title, message, intent);
			notification.flags |= Notification.FLAG_AUTO_CANCEL;

			// Play default notification sound
			// notification.defaults |= Notification.;

			// notification.sound = Uri.parse("android.resource://" +
			// context.getPackageName() + "your_sound_file_name.mp3");

			// Vibrate if vibrate is enabled
			// notification.defaults |= Notification.DEFAULT_VIBRATE;
			// notificationManager.notify(0, notification);
		}
		if (flag.contains("3")) {
			// Notification notification = new Notification(icon,
			// message.substring(1), when);
			Notification notification = new Notification(icon,
					message.substring(1), when);
			// Notification notification = new Notification(icon,
			// "yes change here", when);
			String title = context.getString(R.string.app_name);

			Intent notificationIntent = new Intent(context,
					DriverActivity.class);
			// set intent so it does not start a new activity
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent intent = PendingIntent.getActivity(context, 0,
					notificationIntent, 0);
			notification.setLatestEventInfo(context, title,
					message.substring(1), intent);
			notification.flags |= Notification.FLAG_AUTO_CANCEL;

			// Play default notification sound
			notification.defaults |= Notification.DEFAULT_SOUND;

			// notification.sound = Uri.parse("android.resource://" +
			// context.getPackageName() + "your_sound_file_name.mp3");

			// Vibrate if vibrate is enabled
			notification.defaults |= Notification.DEFAULT_VIBRATE;
			notificationManager.notify(0, notification);
		}
		if (flag.contains("4")) {
			/*
			 * @params icon - icon to be shown when notification arrives
			 * message- message to be shown when Notification Arrives
			 */

			Notification notification = new Notification(icon, messagefornoti,
					when);
			// Notification notification = new Notification.Builder(context)
			// .setContentText(messagefornoti)
			// .setSmallIcon(icon)
			// .setWhen(when)
			// .build();

			String title = context.getString(R.string.app_name);

			Intent notificationIntent = new Intent(context,
					DriverActivity.class);
			// set intent so it does not start a new activity
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent intent = PendingIntent.getActivity(context, 0,
					notificationIntent, 0);
			/*
			 * @params icon - icon to be shown when notification arrives title
			 * Title of Notification to be shown in Notification Bar message to
			 * be shown in Notification in Notification Bar
			 */
			notification.setLatestEventInfo(context, title, notibarmsg, intent);
			notification.flags |= Notification.FLAG_AUTO_CANCEL;

			// Play default notification sound
			notification.defaults |= Notification.DEFAULT_SOUND;

			// notification.sound = Uri.parse("android.resource://" +
			// context.getPackageName() + "your_sound_file_name.mp3");

			// Vibrate if vibrate is enabled
			notification.defaults |= Notification.DEFAULT_VIBRATE;
			notificationManager.notify(0, notification);

		}
		if (flag.contains("5")) {
			Notification notification = new Notification(icon,
					message.substring(1), when);
			// Notification notification = new Notification.Builder(context)
			// .setContentText(message.substring(1))
			// .setSmallIcon(icon)
			// .setWhen(when)
			// .build();
			String title = context.getString(R.string.app_name);

			Intent notificationIntent = new Intent(context,
					DriverActivity.class);
			// set intent so it does not start a new activity
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent intent = PendingIntent.getActivity(context, 0,
					notificationIntent, 0);
			notification.setLatestEventInfo(context, title,
					message.substring(1), intent);
			notification.flags |= Notification.FLAG_AUTO_CANCEL;

			// Play default notification sound
			notification.defaults |= Notification.DEFAULT_SOUND;

			// notification.sound = Uri.parse("android.resource://" +
			// context.getPackageName() + "your_sound_file_name.mp3");

			// Vibrate if vibrate is enabled
			notification.defaults |= Notification.DEFAULT_VIBRATE;
			notificationManager.notify(0, notification);
		}

		if (flag.contains("6")) {
			Notification notification = new Notification(icon,
					message.substring(1), when);
			String title = context.getString(R.string.app_name);

			Intent notificationIntent = new Intent(context,
					ProfileActivity1.class);
			// set intent so it does not start a new activity
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent intent = PendingIntent.getActivity(context, 0,
					notificationIntent, 0);
			notification.setLatestEventInfo(context, title,
					message.substring(1), intent);
			notification.flags |= Notification.FLAG_AUTO_CANCEL;

			// Play default notification sound
			// notification.defaults |= Notification.;

			// notification.sound = Uri.parse("android.resource://" +
			// context.getPackageName() + "your_sound_file_name.mp3");

			// Vibrate if vibrate is enabled
			notification.defaults |= Notification.DEFAULT_VIBRATE;
			notificationManager.notify(0, notification);
		}

		if (flag.contains("7")) {
			Notification notification = new Notification(icon,
					message.substring(1), when);
			String title = context.getString(R.string.app_name);

			Intent notificationIntent = new Intent(context,
					DriverActivity.class);
			// set intent so it does not start a new activity
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent intent = PendingIntent.getActivity(context, 0,
					notificationIntent, 0);
			notification.setLatestEventInfo(context, title,
					message.substring(1), intent);
			notification.flags |= Notification.FLAG_AUTO_CANCEL;

			// Play default notification sound
			// notification.defaults |= Notification.;

			// notification.sound = Uri.parse("android.resource://" +
			// context.getPackageName() + "your_sound_file_name.mp3");

			// Vibrate if vibrate is enabled
			notification.defaults |= Notification.DEFAULT_VIBRATE;
			notificationManager.notify(0, notification);
		}

		if (flag.contains("8")) {
			Notification notification = new Notification(icon,
					message.substring(1), when);
			String title = context.getString(R.string.app_name);

			Intent notificationIntent = new Intent(context,
					DriverActivity.class);
			// set intent so it does not start a new activity
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent intent = PendingIntent.getActivity(context, 0,
					notificationIntent, 0);
			notification.setLatestEventInfo(context, title,
					message.substring(1), intent);
			notification.flags |= Notification.FLAG_AUTO_CANCEL;

			// Play default notification sound
			// notification.defaults |= Notification.;

			// notification.sound = Uri.parse("android.resource://" +
			// context.getPackageName() + "your_sound_file_name.mp3");

			// Vibrate if vibrate is enabled
			notification.defaults |= Notification.DEFAULT_VIBRATE;
			notificationManager.notify(0, notification);
		}
		if (flag.contains("9")) {
			Notification notification = new Notification(icon,
					message.substring(1), when);
			String title = context.getString(R.string.app_name);

			Intent notificationIntent = new Intent(context,
					ProfileActivity1.class);
			// set intent so it does not start a new activity
			notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
					| Intent.FLAG_ACTIVITY_SINGLE_TOP);
			PendingIntent intent = PendingIntent.getActivity(context, 0,
					notificationIntent, 0);
			notification.setLatestEventInfo(context, title,
					message.substring(1), intent);
			notification.flags |= Notification.FLAG_AUTO_CANCEL;

			// Play default notification sound
			// notification.defaults |= Notification.;

			// notification.sound = Uri.parse("android.resource://" +
			// context.getPackageName() + "your_sound_file_name.mp3");

			// Vibrate if vibrate is enabled
			notification.defaults |= Notification.DEFAULT_VIBRATE;
			notificationManager.notify(0, notification);
		}

	}
}

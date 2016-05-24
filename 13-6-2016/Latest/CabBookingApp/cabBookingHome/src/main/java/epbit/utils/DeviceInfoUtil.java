package epbit.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.provider.Settings.Secure;
import android.telephony.TelephonyManager;
import android.util.Patterns;

public class DeviceInfoUtil {

	public static String getAndroidID(Context context) {
		String deviceId = Secure.getString(context.getContentResolver(),
				Secure.ANDROID_ID);
		return deviceId;
	}

	public static String getDeviceIMEI(Context context) {

		String szImei;
		try {

			TelephonyManager TelephonyMgr = (TelephonyManager) context
					.getSystemService(Context.TELEPHONY_SERVICE);
			szImei = TelephonyMgr.getDeviceId();
		} catch (Exception e) {
			szImei = "0000000000000000000000";
			e.printStackTrace();

		}
		return szImei;

	}

	public static String getModelName(Context context) {
		String deviceName = android.os.Build.MODEL;
		return deviceName;
	}

	public static String getBrandName(Context context) {

		String devicebrand = android.os.Build.MANUFACTURER;
		devicebrand = devicebrand.toUpperCase().charAt(0)
				+ devicebrand.substring(1);
		return devicebrand;
	}

	public static int getDeviceApiVersion(Context context) {

		return android.os.Build.VERSION.SDK_INT;

	}

	public static List<String> getAllEmailAccounts(Context context) {
		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(context).getAccounts();
		List<String> possibleEmail = new ArrayList<String>();
		for (Account account : accounts) {
			if (emailPattern.matcher(account.name).matches()) {
				possibleEmail.add(account.name);

			}
		}
		return possibleEmail;

	}

	public static String getPrimaryEmailAccount(Context context) {
		Pattern emailPattern = Patterns.EMAIL_ADDRESS; // API level 8+
		Account[] accounts = AccountManager.get(context).getAccounts();
		String email = new String();
		for (Account account : accounts) {
			if (emailPattern.matcher(account.name).matches()) {
				email = account.name;
				break;
			}
		}
		return email;

	}

	public static String getDeviceAndroidVersionName(Context context) {
		String device_version;
		int version = getDeviceApiVersion(context);
		switch (version) {
		case android.os.Build.VERSION_CODES.CUPCAKE:

			device_version = "CUPCAKE";
			break;

		case android.os.Build.VERSION_CODES.DONUT:

			device_version = "DONUT";
			break;
		case android.os.Build.VERSION_CODES.ECLAIR:

			device_version = "ECLAIR";
			break;
		case android.os.Build.VERSION_CODES.ECLAIR_0_1:

			device_version = "ECLAIR";
			break;
		case android.os.Build.VERSION_CODES.ECLAIR_MR1:

			device_version = "ECLAIR";
			break;

		case android.os.Build.VERSION_CODES.FROYO:

			device_version = "FROYO";
			break;
		case android.os.Build.VERSION_CODES.GINGERBREAD:

			device_version = "GINGERBREAD";
			break;
		case android.os.Build.VERSION_CODES.GINGERBREAD_MR1:

			device_version = "GINGERBREAD";
			break;
		case android.os.Build.VERSION_CODES.HONEYCOMB:

			device_version = "HONEYCOMB";
			break;
		case android.os.Build.VERSION_CODES.HONEYCOMB_MR1:

			device_version = "HONEYCOMB";
			break;
		case android.os.Build.VERSION_CODES.HONEYCOMB_MR2:

			device_version = "HONEYCOMB";
			break;
		case android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH:

			device_version = "ICE CREAM SANDWICH";
			break;
		case android.os.Build.VERSION_CODES.ICE_CREAM_SANDWICH_MR1:

			device_version = "ICE CREAM SANDWICH";
			break;
		case android.os.Build.VERSION_CODES.JELLY_BEAN:

			device_version = "JELLY BEAN";
			break;
		case android.os.Build.VERSION_CODES.JELLY_BEAN_MR1:

			device_version = "JELLY BEAN 4.2";
			break;
		case android.os.Build.VERSION_CODES.JELLY_BEAN_MR2:

			device_version = "JELLY BEAN 4.3";
			break;
		case android.os.Build.VERSION_CODES.KITKAT:

			device_version = "KITKAT";
			break;

		default:
			device_version = "NOT FOUND";
			break;
		}

		return device_version;

	}
}

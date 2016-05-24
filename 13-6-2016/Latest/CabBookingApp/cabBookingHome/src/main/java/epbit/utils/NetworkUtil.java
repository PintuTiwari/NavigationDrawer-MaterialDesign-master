package epbit.utils;

import java.lang.reflect.Method;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;

public class NetworkUtil {

	Context context;

	public NetworkUtil(Context context) {
		super();
		this.context = context;
	}

	@SuppressWarnings("deprecation")
	public boolean isAirplaneModeOn() {

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN_MR1) {
			return (Settings.System.getInt(context.getContentResolver(),
					Settings.System.AIRPLANE_MODE_ON, 0) != 0);

		} else {

			return (Settings.Global.getInt(context.getContentResolver(),
					Settings.Global.AIRPLANE_MODE_ON, 0) != 0);

		}

	}

	public boolean isWifiEnabled() {
		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);

		return wifiManager.isWifiEnabled();

	}

	public boolean isMobileDataEnabled() {
		ConnectivityManager dataManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);

		return (dataManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTED)
				|| (dataManager.getNetworkInfo(0).getState() == NetworkInfo.State.CONNECTING);

	}

	public void enableWifi() {

		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		wifiManager.setWifiEnabled(true);

	}

	public void disableWifi() {

		WifiManager wifiManager = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		wifiManager.setWifiEnabled(false);

	}

	public void enableMobileData() {

		Method dataMtd;
		try {

			dataMtd = ConnectivityManager.class.getDeclaredMethod(
					"setMobileDataEnabled", boolean.class);
			dataMtd.setAccessible(true);
			dataMtd.invoke(
					context.getSystemService(Context.CONNECTIVITY_SERVICE),
					true);

		} catch (Exception e) {
			Toast.makeText(context, "Unable to Turn on Mobile Data !!",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

	}

	public void disableMobileData() {

		//
		Method dataMtd;
		try {

			dataMtd = ConnectivityManager.class.getDeclaredMethod(
					"setMobileDataEnabled", boolean.class);
			dataMtd.setAccessible(true);
			dataMtd.invoke(
					context.getSystemService(Context.CONNECTIVITY_SERVICE),
					false);

		} catch (Exception e) {
			Toast.makeText(context, "Unable to Turn on Mobile Data !!",
					Toast.LENGTH_SHORT).show();
			e.printStackTrace();
		}

	}

}

package epbit.utils;




import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GeoLocationUtil implements LocationListener {

	Context context;
	final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters
	final long MIN_TIME_BW_UPDATES = 1000;
	static LocationManager locmanager;
	private static Location location;

	public GeoLocationUtil(Context context) {
		super();
		this.context = context;

		locmanager = (LocationManager) context
				.getSystemService(Context.LOCATION_SERVICE);
		locmanager.requestLocationUpdates(LocationManager.GPS_PROVIDER,
				MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
		locmanager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER,
				MIN_TIME_BW_UPDATES, MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

	}

	public Location getCurrentLocation(Context context) {

		location = locmanager
				.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		if (location == null)
			location = locmanager
					.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

		return location;

	}

	@Override
	public void onLocationChanged(Location loc) {
		// TODO Auto-generated method stub

		location = loc;
//		((HomeFragment) ((MainActivity) context).getSupportFragmentManager()
//				.findFragmentByTag(AppConstants.TAG_HOME)).setCurrentLocation();

	}

	@Override
	public void onProviderDisabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onProviderEnabled(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
		// TODO Auto-generated method stub

	}

}

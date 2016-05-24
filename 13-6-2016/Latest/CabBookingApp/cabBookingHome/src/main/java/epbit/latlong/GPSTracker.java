package epbit.latlong;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import com.example.cabbookinghome.MainActivity;
import com.example.cabbookinghome.ProfileActivity1;

import epbit.Login.LoginDetails;

public class GPSTracker extends Service implements LocationListener {

    private final Context mContext;
    	
    AlertDialog.Builder alertDialog;
    ProgressDialog waitDialog;
    DialogInterface mydialog;
    // flag for GPS status
    boolean isGPSEnabled = false;

    // flag for network status
    boolean isNetworkEnabled = false;

    // flag for GPS status
    boolean canGetLocation = false;

    Location location; // location
    double latitude; // latitude
    double longitude; // longitude

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 10 * 1; // 1 minute

    // Declaring a Location Manager
    protected LocationManager locationManager;
    Activity profileActivity1;

    public GPSTracker(Context context, Activity profileActivity1) {
        this.mContext = context;
        this.profileActivity1=profileActivity1;
        getLocation();
    }

    public Location getLocation() {
        try {
            locationManager = (LocationManager) mContext
                    .getSystemService(LOCATION_SERVICE);
            //
            // getting GPS status
            isGPSEnabled = locationManager
                    .isProviderEnabled(LocationManager.GPS_PROVIDER);

            // getting network status
            isNetworkEnabled = locationManager
                    .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

            if (!isGPSEnabled && !isNetworkEnabled ) {
                // no network provider is enabled
            	showSettingsAlert();
            } else {
                this.canGetLocation = true;
                if (isNetworkEnabled) {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        location = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        if (location != null) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                        }
                    }
                }
                // if GPS Enabled get lat/long using GPS Services
                if (isGPSEnabled) {
                    if (location == null) {
                    	
                        locationManager.requestLocationUpdates(
                        		
                                LocationManager.GPS_PROVIDER,
                                MIN_TIME_BW_UPDATES,
                                MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                        Log.d("GPS Enabled", "GPS Enabled");
                        if (locationManager != null) {
                            location = locationManager
                                    .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                            if (location != null) {
                                latitude = location.getLatitude();
                                longitude = location.getLongitude();
                            }
                            else{
                            	
                            
                            	 locationManager.requestLocationUpdates(
                                         LocationManager.GPS_PROVIDER,
                                         MIN_TIME_BW_UPDATES,
                                         MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                            	 
                            	 waitDialog = ProgressDialog.show(mContext, "", "Fetching Your Location",true);
//                            	 int counter=0;
//                            	 while(location==null&&counter<)
                            	 {
                            		 
                            		  
                            	        
                            	        

                            	 }
                            	 
                            	if( waitDialog.isShowing())
                            	 waitDialog.dismiss();
                            	  location = locationManager
                                          .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                             if (location != null) {
                                      latitude = location.getLatitude();
                                      longitude = location.getLongitude();
                             }
                             else{
                            	// Toast.makeText(mContext, "Unable to Fetch Location,Please Enable Gps", Toast.LENGTH_SHORT).show();
                             }
                            	
                            
                            }
                        }
                    }
                }
            }

        } 
        
        
        catch (Exception e) {
        	Log.e("GPS", "GPS Exception");
            e.printStackTrace();
        }

        return location;
    }
   
    /**
     * Stop using GPS listener
     * Calling this function will stop using GPS in your app
     * */
    public void stopUsingGPS(){
        if(locationManager != null){
            locationManager.removeUpdates(GPSTracker.this);
        }       
    }
   
    /**
     * Function to get latitude
     * */
    public double getLatitude(){
        if(location != null){
            latitude = location.getLatitude();
        }
       
        // return latitude
        return latitude;
    }
   
    /**
     * Function to get longitude
     * */
    public double getLongitude(){
        if(location != null){
            longitude = location.getLongitude();
        }
       
        // return longitude
        return longitude;
    }
   
    /**
     * Function to check GPS/wifi enabled
     * @return boolean
     * */
    public boolean canGetLocation() {
        return this.canGetLocation;
    }
   
    /**
     * Function to show settings alert dialog
     * On pressing Settings button will lauch Settings Options
     * */
    public void showSettingsAlert(){
        alertDialog = new AlertDialog.Builder(mContext);
        
        // Setting Dialog Title
        alertDialog.setTitle("GPS is Not Enabled");

        // Setting Dialog Message
        alertDialog.setMessage("Please Enable GPS from Settings and Restart the application");

        // On pressing Settings button
//        alertDialog.setPositiveButton("Settings", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog,int which) {
//            	mydialog=dialog;
//            	//dialog.dismiss();
//            	  dialog.dismiss();
//                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
//                mContext.startActivity(intent);
//               
//            
//                
//                //dialog.di();
//            }
//        });
//
//        // on pressing cancel button
//        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//            public void onClick(DialogInterface dialog, int which) {
//            	  Intent intent2 = new Intent((Activity)mContext, MainActivity.class);
//              	intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//              	intent2.putExtra("EXIT", true);
//              	mContext.startActivity(intent2);
//                  //ProfileActivity1.this.finish();    
//              	Log.e("CHECK", "GOING TO LOGIN");
//              	((Activity) mContext).finish();
//            dialog.dismiss();
//            }
//        });
        alertDialog.setNeutralButton("Ok",new DialogInterface.OnClickListener() {
			
			
		
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				dialog.dismiss();
				
				 Intent intent2 = new Intent((Activity)mContext, MainActivity.class);
           	intent2.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_CLEAR_TASK);
           	intent2.putExtra("EXIT", true);
           	mContext.startActivity(intent2);
               //ProfileActivity1.this.finish();    
           	Log.e("CHECK", "GOING TO LOGIN");
           	((Activity) mContext).finish();
         		
			}
		});

        // Showing Alert Message
        alertDialog.show();
    }

  @Override
 public void onLocationChanged(Location location) {
  // TODO Auto-generated method stub
  //latitude=location.getLatitude();
  //longitude=location.getLongitude();
	  if(LoginDetails.usertype.equalsIgnoreCase("passenger"))
	  ((ProfileActivity1) profileActivity1).update_marker(location);
  //ProfileActivity1.this.update_marker(location);
  
  
	  
 }

  @Override
 public void onProviderDisabled(String provider) {
  // TODO Auto-generated method stub
	  
  
 }

  @Override
 public void onProviderEnabled(String provider) {
  // TODO Auto-generated method stub
	//  mydialog.cancel();
	//  mydialog.cancel();
	  Log.e("GPS", "YES I AM ENABLED");
	  
  
 }

  @Override
 public void onStatusChanged(String provider, int status, Bundle extras) {
  // TODO Auto-generated method stub
  
 }

  @Override
 public IBinder onBind(Intent intent) {
  // TODO Auto-generated method stub
  return null;
 }
  
 
  
}

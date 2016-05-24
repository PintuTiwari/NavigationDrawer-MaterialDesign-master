package epbit.gcmutils;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

public final class CommonUtilities {
	
	// give your server registration url here

    // Google project id
   

    /**
     * Tag used on log messages.ConfirmRejectConfirmReject
     */
    static final String TAG = "AndroidHive GCM";

    public static final String DISPLAY_MESSAGE_ACTION =
            "com.example.cabbookinghome.DISPLAY_MESSAGE";

    public static final String EXTRA_MESSAGE = "message";

    /** 
     * Notifies UI to display a message.
     * <p>
     * This method is defined in the common helper because it's used both by
     * the UI and the background service.
     *
     * @param context application's context.
     * @param message message to be displayed.
     */
    public static void displayMessage(Context context, String message) {
    	Log.e("DISPLAY MESSAGE", "   "+message);
        Intent intent = new Intent(DISPLAY_MESSAGE_ACTION);
        intent.putExtra(EXTRA_MESSAGE, message);
        context.sendBroadcast(intent);
    }
}

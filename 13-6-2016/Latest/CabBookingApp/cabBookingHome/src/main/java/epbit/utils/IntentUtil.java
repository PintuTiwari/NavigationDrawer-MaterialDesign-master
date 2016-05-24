package epbit.utils;

import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

public class IntentUtil {

	Context context;

	public IntentUtil(Context context) {
		super();
		this.context = context;

	}

	public void sendEmailIntent(int chooser_dialog_title, String subject,
			String body) {
		Intent intent = new Intent(Intent.ACTION_SEND);
		intent.setType("message/rfc822");
		intent.putExtra(Intent.EXTRA_SUBJECT, subject);
		intent.putExtra(Intent.EXTRA_TEXT, body);

		context.startActivity(Intent.createChooser(intent,
				context.getString(chooser_dialog_title)));

	}

	public void navigateToLocationSettings() {
		Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
		context.startActivity(intent);
	}

}

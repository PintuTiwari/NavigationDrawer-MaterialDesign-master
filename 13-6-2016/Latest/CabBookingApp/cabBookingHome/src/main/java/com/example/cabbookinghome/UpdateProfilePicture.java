package com.example.cabbookinghome;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Base64;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.coboltforge.slidemenu.SlideMenu;

import epbit.Login.LoginDetails;
import epbit.adapter.SlideMenuListener;
import epbit.constants.IWebConstant;
import epbit.constants.ProjectURLs;
import epbit.helper.CheckUserType;

public class UpdateProfilePicture extends ActionBarActivity {
	ImageButton select_profile;
	ImageButton update_to_server;
	private static final int TAKE_REQUEST = 1888;
	private static final int SELECT_REQUEST = 1;
	ImageView pic_imageview;
	Bitmap pic;
	private SlideMenu slidemenu;
	ImageButton takepicture;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.updateprofilepicture);

		// FrameLayout FL=(FrameLayout)findViewById(R.id.framelayout);
		// FL.
		context = UpdateProfilePicture.this;
		attachSlideMenu();
		
		pic_imageview = (ImageView) findViewById(R.id.update_profile_preview);
		select_profile = (ImageButton) findViewById(R.id.select_profile_pic_button);
		select_profile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setType("image/*");
				intent.setAction(Intent.ACTION_GET_CONTENT);
				intent.addCategory(Intent.CATEGORY_OPENABLE);
				startActivityForResult(intent, SELECT_REQUEST);
			}
		});

		takepicture = (ImageButton) findViewById(R.id.take_profile_picture);
		takepicture.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startActivityForResult(new Intent(
						android.provider.MediaStore.ACTION_IMAGE_CAPTURE),
						TAKE_REQUEST);

			}
		});

		// will send Image to the Server
		update_to_server = (ImageButton) findViewById(R.id.update_to_Server);
		update_to_server.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (pic_imageview.getDrawable() == null) {
					Toast.makeText(getApplicationContext(),
							"Please Select/Capture a Image", Toast.LENGTH_SHORT);
				} else {

					Drawable d = pic_imageview.getDrawable();
					Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
					byte[] ba;
					do {
						ByteArrayOutputStream bao = new ByteArrayOutputStream();

						Log.e("BEFORE REDUCING",
								bitmap.getHeight() + " " + bitmap.getWidth()
										+ " " + bitmap.getRowBytes()
										* bitmap.getHeight());

						Log.e("After REDUCING",
								bitmap.getHeight() + " " + bitmap.getWidth()
										+ " " + bitmap.getRowBytes()
										* bitmap.getHeight());
						bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bao);

						ba = bao.toByteArray();
						if ((ba.length / 1024) >= 650) {
							bitmap = Bitmap.createScaledBitmap(bitmap,
									(int) (bitmap.getWidth() * 0.95),
									(int) (bitmap.getHeight() * 0.95), true);

						}

						Log.e("BYTE LENGTH", "" + ba.length / 1024);

					} while ((ba.length / 1024) >= 650);

					String ba1 = Base64.encodeToString(ba, Base64.DEFAULT);

					Toast.makeText(getApplicationContext(),
							"Updating Your Pic....	", Toast.LENGTH_LONG).show();
					ArrayList<NameValuePair> nameValuePairs = new

					ArrayList<NameValuePair>();

					nameValuePairs.add(new BasicNameValuePair(
							IWebConstant.NAME_VALUE_PAIR_KEY_UPDATEPIC, ba1));
					nameValuePairs.add(new BasicNameValuePair(
							IWebConstant.NAME_VALUE_PAIR_KEY_EMAIL,
							LoginDetails.Username));
					new updatepictask(getApplicationContext())
							.execute(nameValuePairs);

				}
			}
		});

	}

	public UpdateProfilePicture() {
		super();
	}

	private class updatepictask extends
			AsyncTask<List<? extends NameValuePair>, String, String> {

		Context context;
		int result_code = 0;

		public updatepictask(Context applicationContext) {
			this.context = applicationContext;
		}

		@Override
		protected String doInBackground(List<? extends NameValuePair>... params) {
			try {
				StringBuilder stringBuilder = new StringBuilder();

				HttpClient httpclient = new DefaultHttpClient();

				HttpPost httppost = new

				HttpPost(ProjectURLs.UPDATE_PROFILE_PIC_URL);

				httppost.setEntity(new UrlEncodedFormEntity(params[0]));

				HttpResponse response = httpclient.execute(httppost);

				HttpEntity entity = response.getEntity();
				InputStream stream = entity.getContent();
				int b;
				while ((b = stream.read()) != -1) {
					stringBuilder.append((char) b);
				}
				JSONObject jsonObject = new JSONObject();
				try {
					Log.e("Check String", stringBuilder.toString());
					jsonObject = new JSONObject(stringBuilder.toString());

					Log.e("Check", "" + jsonObject.getInt("success"));
					result_code = jsonObject.getInt("success");

				} catch (JSONException e) {

					Log.e("Exception", "Json");
					e.printStackTrace();
				}

				Log.e("UPDATE TASK", "Successfully Done");

			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {

				Log.e("log_tag", "Error in http connection " + e.toString());

			}

			return null;
		}

		@Override
		protected void onPostExecute(String result) {

			super.onPostExecute(result);
			if (result_code == 1) {
				Toast.makeText(context, "Profile pic Updated Successfully",
						Toast.LENGTH_SHORT).show();
				startActivity(new Intent(context, Profile.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
				finish();
			} else {
				Toast.makeText(context, "Failed to Update Profile Pic..",
						Toast.LENGTH_LONG).show();
			}
		}

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == TAKE_REQUEST && resultCode == RESULT_OK) {
			Log.i("Update Profile", "ONACTIVITY RESULT");
			pic = (Bitmap) data.getExtras().get("data");
			update_to_server = (ImageButton) findViewById(R.id.update_to_Server);
			update_to_server.setVisibility(View.VISIBLE);
			Log.i("Update Profile", "ONACTIVITY RESULT" + pic.toString());
			pic_imageview.setImageBitmap(pic);

		}
		if (requestCode == SELECT_REQUEST && resultCode == Activity.RESULT_OK)
			try {

				if (pic != null) {
					pic.recycle();
				}
				InputStream stream = getContentResolver().openInputStream(
						data.getData());
				pic = BitmapFactory.decodeStream(stream);
				stream.close();
				update_to_server = (ImageButton) findViewById(R.id.update_to_Server);
				update_to_server.setVisibility(View.VISIBLE);
				pic_imageview.setImageBitmap(pic);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

	private void attachSlideMenu() {

		try {
			getActionBar().setDisplayHomeAsUpEnabled(false);
			getActionBar().setBackgroundDrawable(
					new ColorDrawable(getResources().getColor(
							R.color.action_titlebar_color)));
			getActionBar().setTitle(getResources().getString(R.string.app_name));
			getActionBar().setIcon(R.drawable.menu);
			slidemenu = (SlideMenu) findViewById(R.id.slideMenu);
			if (LoginDetails.usertype.equalsIgnoreCase("driver")) {

				slidemenu.init(this, R.menu.driverslide, new SlideMenuListener(
						(Activity) context, R.id.item_one), 333);
			

			} else {
				slidemenu.init(this, R.menu.myslide, new SlideMenuListener(
						(Activity) context, R.id.item_one), 333);	
			}
			slidemenu.setHeaderImage(getResources().getDrawable(
					R.drawable.ic_launcher));
		} catch (Exception e) {
			Log.i("slidemenu", "" + e);
		}

	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {

		if (keyCode == KeyEvent.KEYCODE_BACK) {

			CheckUserType.intentservice(getApplicationContext());

			finish();

		}
		return super.onKeyDown(keyCode, event);

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			slidemenu.show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}

}
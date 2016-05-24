package com.example.cabbookinghome;

import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ListView;

import com.coboltforge.slidemenu.SlideMenu;
import com.example.cabbookinghome.R;

import epbit.Login.Friends;
import epbit.adapter.MyAdapter;
import epbit.adapter.SlideMenuListener;

public class SelectFriendsActivity extends Activity {
	String FRIEND_LIST = "friend_list";
	List<HashMap<String, String>> friend;
	List<String> checked_contacts;
	ListView list;
	ImageButton submit, cancel;
	private Intent returnIntent;
	MyAdapter adapter;
	int count = 0;
	private SlideMenu slidemenu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		count = 0;
		getActionBar().setDisplayHomeAsUpEnabled(false);
		getActionBar().setBackgroundDrawable(
				new ColorDrawable(Color.parseColor("#00B0FA")));
		getActionBar().setTitle(getResources().getString(R.string.header_name));
		getActionBar().setIcon(R.drawable.menu);
		setContentView(R.layout.selectcontacts_email);
		list = (ListView) findViewById(R.id.listview);
		submit = (ImageButton) findViewById(R.id.done);
		cancel = (ImageButton) findViewById(R.id.cancel);

		friend = Friends.list;
		slidemenu = (SlideMenu) findViewById(R.id.slideMenu);
		slidemenu.init(this, R.menu.myslide, new SlideMenuListener(
				(Activity) SelectFriendsActivity.this, R.id.item_three), 333);
		// this can set the menu to initially shown instead of hidden
		// slidemenu.setAsShown();

		// set optional header imageu
		// checked_contacts=new ArrayList<String>(friend.size());

		adapter = new MyAdapter(SelectFriendsActivity.this);

		list.setAdapter(adapter);

		submit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				for (int i = 0; i < friend.size(); i++) {
					if (friend.get(i).get("check").equals("1")) {

						count++;
					}
				}
				AlertDialog.Builder builder = new AlertDialog.Builder(
						SelectFriendsActivity.this);

				builder.setTitle("" + count + " Contacts has been Selected !!");
				builder.setMessage("Do you want to Select More ?");
				returnIntent = new Intent();
				builder.setPositiveButton("No",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								//
								Log.e("Sharing ", "" + count);
								returnIntent.putExtra("count", count);

								setResult(RESULT_OK, returnIntent);
								finish();

							}
						});
				builder.setNegativeButton("Yes", null);
				builder.show();

			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				setResult(RESULT_CANCELED, returnIntent);

				finish();
			}
		});

	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home: // this is the app icon of the actionbar
			slidemenu.show();
			break;
		}
		return super.onOptionsItemSelected(item);
	}
}
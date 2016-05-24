package com.example.cabbookinghome;

import java.util.ArrayList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cabbookinghome.R;

public class SelectEmailActivity extends Activity {

	public MyAdapter ma;
	public ImageButton select, cancel;
	public ArrayList<String> name1 = new ArrayList<String>();
	private ArrayList<String> phno1 = new ArrayList<String>();
	Dialog dialog;
	ArrayList<String> contacts = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		setContentView(R.layout.selectcontacts_email);
		getAllContacts(this.getContentResolver());
		ListView lv = (ListView) findViewById(R.id.listview);
		ma = new MyAdapter();
		lv.setAdapter(ma);
		lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				ma.toggle(arg2);

			}
		});
		lv.setItemsCanFocus(false);
		lv.setTextFilterEnabled(true);
		// adding
		select = (ImageButton) findViewById(R.id.done);
		select.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				StringBuilder checkedcontacts = new StringBuilder();

				for (int i = 0; i < name1.size(); i++)

				{
					if (ma.mCheckStates.get(i) == true) {
						checkedcontacts.append(phno1.get(i).toString());
						checkedcontacts.append(",");
						contacts.add(phno1.get(i).toString());

					} else {

					}

				}

				Toast.makeText(SelectEmailActivity.this, checkedcontacts, 1000)
						.show();
				AlertDialog.Builder builder = new AlertDialog.Builder(
						SelectEmailActivity.this);

				builder.setTitle("" + contacts.size()
						+ " Contacts has been Selected !!");
				builder.setMessage("Do you want to Select More ?");
				builder.setPositiveButton("No",
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								// TODO Auto-generated method stub
								Bundle b = new Bundle();

								Intent returnIntent = new Intent();
								returnIntent.putStringArrayListExtra("data",
										contacts);
								setResult(RESULT_OK, returnIntent);
								finish();

							}
						});
				builder.setNegativeButton("Yes", null);
				builder.show();

			}
		});

		cancel = (ImageButton) findViewById(R.id.cancel);
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				startActivity(new Intent(SelectEmailActivity.this,
						ReferActivity.class)
						.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			}
		});

	}

	public void getAllContacts(ContentResolver cr) {

		Cursor phones = cr.query(
				ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null,
				null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME
						+ " COLLATE NOCASE ASC");
		while (phones.moveToNext()) {
			String name = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
			String phoneNumber = phones
					.getString(phones
							.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
			name1.add(name.substring(0, 1).toUpperCase() + name.substring(1));
			phno1.add(phoneNumber);

		}

		phones.close();
	}

	class MyAdapter extends BaseAdapter implements
			CompoundButton.OnCheckedChangeListener {
		private SparseBooleanArray mCheckStates;
		LayoutInflater mInflater;
		TextView tv1, tv;
		CheckBox cb;

		MyAdapter() {
			mCheckStates = new SparseBooleanArray(name1.size());
			mInflater = (LayoutInflater) SelectEmailActivity.this
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}

		public MyAdapter(Context context) {
			// TODO Auto-generated constructor stub
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return name1.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub

			return 0;
		}

		@Override
		public View getView(final int position, View convertView,
				ViewGroup parent) {
			// TODO Auto-generated method stub
			View vi = convertView;
			if (convertView == null)
				vi = mInflater.inflate(R.layout.select_contact_email_row, null);
			tv = (TextView) vi.findViewById(R.id.textview1);
			// tv1 = (TextView) vi.findViewById(R.id.textview2);
			cb = (CheckBox) vi.findViewById(R.id.check);
			tv.setText(name1.get(position));
			// tv1.setText("Phone No :" + phno1.get(position));
			cb.setTag(position);
			cb.setChecked(mCheckStates.get(position, false));
			cb.setOnCheckedChangeListener(this);

			return vi;
		}

		public boolean isChecked(int position) {
			return mCheckStates.get(position, false);
		}

		public void setChecked(int position, boolean isChecked) {
			mCheckStates.put(position, isChecked);
		}

		public void toggle(int position) {
			setChecked(position, !isChecked(position));
		}

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			// TODO Auto-generated method stub

			mCheckStates.put((Integer) buttonView.getTag(), isChecked);
		}

	}
}
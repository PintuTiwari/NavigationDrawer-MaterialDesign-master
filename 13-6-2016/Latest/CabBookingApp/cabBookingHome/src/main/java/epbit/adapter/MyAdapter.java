package epbit.adapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.cabbookinghome.R;

import epbit.Login.Friends;

public class MyAdapter extends BaseAdapter implements
		CompoundButton.OnCheckedChangeListener {
	private List<String> mCheckStates=new ArrayList<String>(Friends.list.size());
	LayoutInflater mInflater;
	TextView tv1, tv;
	CheckBox cb;
	List<HashMap<String, String>> list;
	HashMap<String, String> datarow;
	String NAME_KEY = "name";
	String ID_KEY = "id";
	private String CHECK="check";

	public MyAdapter(Context context) {
		
		list=Friends.list;
	//	mCheckStates = checked_contacts;
		mInflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
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
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View vi = convertView;
		datarow=list.get(position);
		if (convertView == null)
			vi = mInflater.inflate(R.layout.select_contact_email_row, null);
		tv = (TextView) vi.findViewById(R.id.textview1);
		// tv1 = (TextView) vi.findViewById(R.id.textview2);
		cb = (CheckBox) vi.findViewById(R.id.check);
		tv.setText(list.get(position).get(NAME_KEY));
		// tv1.setText("Phone No :" + phno1.get(position));
		cb.setTag(position);
//		if(mCheckStates.get(position)==null)
//			mCheckStates.set(position, "0");
			
		//cb.setChecked(mCheckStates.set(position, "0"));
		if(Integer.parseInt(list.get(position).get(CHECK))==0)
				{
			cb.setChecked(false);
				}
		else
		{
			cb.setChecked(true);
		}
		cb.setOnCheckedChangeListener(this);

		return vi;
	}

	public int isChecked(int position) {
		
		return Integer.parseInt(list.get(position).get(CHECK));
		
//		return mCheckStates.get(position, false);
	}

	public void setChecked(int position, int isChecked) {
		
		HashMap<String, String> tempHashMap;
		tempHashMap=list.get(position);
		tempHashMap.put(CHECK, ""+isChecked);
		//list.set(position, list.get(position).put(CHECK, "1"));
//		mCheckStates.put(position, isChecked);
	}

	public void toggle(int position) {
		
		
		if(Integer.parseInt(mCheckStates.get(position))==0)
		{
			setChecked(position, 1);
			
		}
		else
		{
			setChecked(position, 0);
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		
		HashMap<String, String> tempHashMap=list.get((Integer)buttonView.getTag());

		if(isChecked)
		{
		tempHashMap.put(CHECK, "1");	
		}else
		{
			tempHashMap.put(CHECK, "0");
		}
		//if(isChecked)
		//mCheckStates.set((Integer) buttonView.getTag(), ""+1);
		//else
		//	mCheckStates.set((Integer) buttonView.getTag(), ""+0);
		
	}

}
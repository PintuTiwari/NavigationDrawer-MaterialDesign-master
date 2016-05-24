package epbit.adapter;

import java.util.HashMap;
import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;


public class FriendsAdapter extends BaseAdapter
{	
	
	Context context;
	List<HashMap<String, String>> datapool;
	HashMap<String, String> datarow;
	
	public FriendsAdapter(Context context,
			List<HashMap<String, String>> datapool) {
		super();
		this.context = context;
		this.datapool = datapool;
	}
	
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return datapool.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return datapool.get(position);
	}

	@Override
	public long getItemId(int postion) {
		// TODO Auto-generated method stub
		return postion;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		datarow=datapool.get(position);
		if (convertView==null) {
			
		}
		
		
		return null;
	}
	class Holder
	{
		TextView membername;
		CheckBox checkbox;
	}
	
}
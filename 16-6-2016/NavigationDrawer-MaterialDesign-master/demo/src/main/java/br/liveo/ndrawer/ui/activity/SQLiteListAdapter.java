package br.liveo.ndrawer.ui.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.liveo.ndrawer.R;

public class SQLiteListAdapter extends BaseAdapter {
	
    Context context;
    ArrayList<String> userID;
    ArrayList<String> UserName;
    ArrayList<String> User_PhoneNumber;
    ArrayList<String> UserSubject ;
    

    public SQLiteListAdapter(
    		Context context2,
    		ArrayList<String> id,
    		ArrayList<String> name,
    		ArrayList<String> phone,
    		ArrayList<String> subject
    		) 
    {
        	
    	this.context = context2;
        this.userID = id;
        this.UserName = name;
        this.User_PhoneNumber = phone;
        this.UserSubject = subject ;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return userID.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    public View getView(int position, View child, ViewGroup parent) {
    	
        Holder holder;
        
        LayoutInflater layoutInflater;
        
        if (child == null) {
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            child = layoutInflater.inflate(R.layout.listviewdatalayout, null);
            
            holder = new Holder();
            
            holder.textviewid = (TextView) child.findViewById(R.id.textViewID);
            holder.textviewname = (TextView) child.findViewById(R.id.textViewNAME);
            holder.textviewphone_number = (TextView) child.findViewById(R.id.textViewPHONE_NUMBER);
            holder.textviewsubject = (TextView) child.findViewById(R.id.textViewSUBJECT);
            
            child.setTag(holder);
            
        } else {
        	
        	holder = (Holder) child.getTag();
        }
        holder.textviewid.setText(userID.get(position));
        holder.textviewname.setText(UserName.get(position));
        holder.textviewphone_number.setText(User_PhoneNumber.get(position));
        holder.textviewsubject.setText(UserSubject.get(position));

        return child;
    }

    public class Holder {
        TextView textviewid;
        TextView textviewname;
        TextView textviewphone_number;
        TextView textviewsubject;
    }

}
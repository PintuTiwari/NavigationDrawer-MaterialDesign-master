package br.liveo.ndrawer.ui.activity;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.liveo.ndrawer.R;
//https://coderwall.com/p/ieiyjg/a-nice-way-to-maintain-server-session-id-on-android
//http://www.lightrains.com/blog/simple-tip-session-handling-android

/**
 * Created by Sairamkrishna on 4/7/2015.
 */
public class LogOut extends Fragment {
    Button bu=null;
    Button bu2=null;
    private static final String PREFS_NAME = "AndroidPref";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_main, container, false);
 /*   @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.logout);
*/
        bu=(Button)view.findViewById(R.id.button2);
        bu2=(Button)view.findViewById(R.id.button3);
        logout();
        return  view;
    }

    public  void logout(){
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences(LogOut.PREFS_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
        Intent i = new Intent(getActivity(), Login.class);

        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        startActivity(i);
        getActivity().finish();
    }

    public void close(View view){
        getActivity().finish();
    }
}
/*
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.liveo.ndrawer.R;

*/
/**
 * Created by rupa.dwivedi on 09-03-2016.
 *//*

public class LogOut extends android.support.v4.app.Fragment {
    SharedPreferences pref;
    private static final String PREF_NAME_PRIFRNCR = "isregisterd";


    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_main, container, false);
        logout();
        return view;
    }
    public  void logout(){
       */
/* // TODO Auto-generated method stub
        AlertDialog.Builder confirm = new AlertDialog.Builder(getActivity());
        confirm.setMessage("Exit Application?");
        confirm.setTitle("Confirm");
        confirm.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {*//*

                SharedPreferences sharedpreferences = getActivity().getSharedPreferences(LogOut.PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
              //  SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedpreferences.edit();
                editor.clear();
                editor.commit();
              */
/*  startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();*//*


                Intent i = new Intent(getActivity(), Login.class);

                // Closing all the Activities
                i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

                // Add new Flag to start new Activity
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

                // Staring Login Activity
                startActivity(i);
                getActivity().finish();
            }

       */
/* });
        confirm.setNegativeButton("No", new DialogInterface.OnClickListener() {

            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub

            }
        });

        confirm.show();*//*




    public  void logout1(){
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);

     //   SharedPreferences sharedpreferences = getSharedPreferences(MainActivity.MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }
}

*/

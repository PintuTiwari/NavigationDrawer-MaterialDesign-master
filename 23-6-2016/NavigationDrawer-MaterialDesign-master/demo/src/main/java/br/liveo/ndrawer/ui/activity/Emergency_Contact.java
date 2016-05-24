package br.liveo.ndrawer.ui.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import br.liveo.ndrawer.R;
import br.liveo.ndrawer.adapter.ConstantClass;

/**
 * Created by rupa.dwivedi on 28-01-2016.
 */
public class Emergency_Contact extends ActionBarActivity {
 // {"Id":59,"Name":"test","Address":"test","Relation":"test","ContactNumber":"7894561230","EmployeeId":1,"ExcelCode":null,"listEmergencyContacts":null}

    private RecyclerView recyclerView;
    private RecyclerAdapter_EmergencyContact adapter;
    private ArrayList<Person> friendArrayList;
    private com.github.clans.fab.FloatingActionButton mFab;
    EditText Name, Address, Relation, ContactNumber;
    String Names, Addresss, Relations, ContactNumbers, SchemaName, userId, CompanyId,Fieldvalue;
    private static final String PREF_NAME_PRIFRNCR = "isregisterd";
    Map<String, String> responseMap = new LinkedHashMap<String, String>();

    Context context = this;
    String getMyDetils_String;
    private int overallXScroll = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydetails1);
        findview();
        getprefrence();

        FrameLayout linearLayout=(FrameLayout)findViewById(R.id.frame_view_viewacademic);
        linearLayout.setVisibility(View.GONE);
        FrameLayout linearLayout1=(FrameLayout)findViewById(R.id.frame_view_family);
        linearLayout1.setVisibility(View.GONE);
        FrameLayout linearLayout2=(FrameLayout)findViewById(R.id.frame_view_nominee);
        linearLayout2.setVisibility(View.GONE);

        recyclerView = (RecyclerView) findViewById(R.id.recyle_view_emergency);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnewdetails();
            }
        });

        friendArrayList = new ArrayList<>();

        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
      //  recyclerView.setHasFixedSize(true);
        //recyclerView.setNestedScrollingEnabled(true);
       /* LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);*/
      //  recyclerView. setLayoutManager (new LinearLayoutManager(context));
        setRecyclerViewData(); //adding data to array list

        adapter = new RecyclerAdapter_EmergencyContact(this, friendArrayList);
        recyclerView.setAdapter(adapter);


        try {
            if(getMyDetils_String.length() > 0) {
                friendArrayList.clear();
                JSONArray internships = new JSONArray(getMyDetils_String);
                JSONObject leagueObject = internships.getJSONObject(0);
                //Loop the Array
                for (int i = 0; i < leagueObject.length(); i++) {
                    Log.e("Message", "loop");
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject jsonObject = internships.getJSONObject(i);
                    Log.d("Employement in page", jsonObject.toString());
                   /* Log.d("EmployementHi_response ", e.toString());
                    map.put("id", String.valueOf("id"));*/

                   /* Person person = new Person(Name.getText().toString().trim(), Address.getText().toString().trim(),
                            Relation.getText().toString().trim(),
                            Contact_Number.getText().toString().trim());*/
                    Person person=new Person();

                    if (!jsonObject.isNull("Name")) {
                        person.Company_Name = jsonObject.getString("Name");
                        Log.d("Company",person.Company_Name.toString());
                    }
                    if (!jsonObject.isNull("Address")) {
                        person.Position = jsonObject.getString("Address");
                        Log.d("Company",person.Position.toString());
                    }
                    if (!jsonObject.isNull("Relation")) {
                        person.From_Date = jsonObject.getString("Relation");
                        /*String date = jsonObject.getString("Relation");

                        String[] parts = date.split("T");
                        String part1 = parts[0]; // 004
                        String part2 = parts[1]; // 034556
                        System.out.println(part1);
                        person.From_Date=part1;*/
                        //Log.d("Company",person.To_Date.toString());
                    }
                    if (!jsonObject.isNull("ContactNumber")) {
                        person.To_Date = jsonObject.getString("ContactNumber");

                      /*  String date = jsonObject.getString("ContactNumber");
                        String[] parts = date.split("T");
                        String part1 = parts[0]; // 004
                        String part2 = parts[1]; // 034556
                        System.out.println(part1);
                        person.To_Date=part1;*/
                        Log.d("Company",person.To_Date.toString());
                    }
                    friendArrayList.add(i, person);
                }
                // recyclerView.notifyDataSetChanged();
            }
        } catch(JSONException e) {
            Log.e("log_tag", "Error parsing data "+e.toString());
        }
    }

    private void addnewdetails() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialog_layout = inflater.inflate(R.layout.dilog, null);
        final AlertDialog.Builder db = new AlertDialog.Builder(this);
        db.setView(dialog_layout);

        LinearLayout linearLayout = (LinearLayout) dialog_layout.findViewById(R.id.layout_main);
        linearLayout.setVisibility(View.GONE);
        LinearLayout linearLayout1 = (LinearLayout) dialog_layout.findViewById(R.id.layout_main_nominee);
        linearLayout1.setVisibility(View.GONE);
        LinearLayout linearLayout2 = (LinearLayout) dialog_layout.findViewById(R.id.layout_academic);
        linearLayout2.setVisibility(View.GONE);


        Name = (EditText) dialog_layout.findViewById(R.id.nameE);
        Address = (EditText) dialog_layout.findViewById(R.id.addressE);
        Relation = (EditText) dialog_layout.findViewById(R.id.relationE);
        ContactNumber = (EditText) dialog_layout.findViewById(R.id.contact_numberE);

        db.setTitle("Add Emergency contact Details");
        // setTitleColor(R.color.colorAccent);0
        //setTheme(R.style.MyTheme_ActionBar_TitleTextStyle);
        db.setView(dialog_layout);
        db.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        db.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UpdateDate();

            }
        });
        AlertDialog alert11 = db.create();
        alert11.show();

        Button buttonbackground = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonbackground.setTextColor(getResources().getColor(R.color.colorAccent));

        Button buttonbackground1 = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
        //buttonbackground1.setBackgroundColor(Color.BLUE);
        buttonbackground1.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    private void UpdateDate() {
        Names=Name.getText().toString();
        Addresss= Address.getText().toString();
        Relations= Relation.getText().toString();
        ContactNumbers= ContactNumber.getText().toString();
      //{"Id":62,"Name":"test","Address":"test","Relation":"test","ContactNumber":"7894561230","EmployeeId":1,"ExcelCode":null,"listEmergencyContacts":null}

        responseMap.put("Name", Name.getText().toString());
        responseMap.put("Address", Address.getText().toString());
        responseMap.put("Relation", Relation.getText().toString());
        responseMap.put("ContactNumber", ContactNumber.getText().toString());
        Log.d("AcademicMAP: ", responseMap.toString());

        Gson gson = new Gson();
        Fieldvalue = gson.toJson(responseMap);
        Log.d("Fieldvalue", Fieldvalue.toString());
        if(Fieldvalue!=null)
        {
            new NetCheck().execute();
        }
    }

    private void getprefrence() {
        SharedPreferences setting = this.getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        getMyDetils_String  = setting.getString("response", "response");
    }

    private void findview() {
        mFab = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menue1_emergency);
        FrameLayout linearLayout2 = (FrameLayout)findViewById(R.id.frame_view_viewacademic);
        linearLayout2.setVisibility(View.GONE);
    }


    public void openDialog(final int position) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Alert!");

        // set dialog message
        alertDialogBuilder
                .setMessage("Do you really want to delete?")
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close

                                dialog.cancel();
                                friendArrayList.get(position);
                               /* new DelTask().execute(
                                        friendArrayList.get(position).id,
                                        friendArrayList.get(position).make);*/
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }

    private class NetCheck extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(Emergency_Contact.this);
            nDialog.setMessage("Loading..");
            nDialog.setTitle("Checking Network");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args){


/**
 * Gets current device state and checks for working internet connection by trying Google.
 **/
            ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://www.google.com");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    }
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return false;

        }
        @Override
        protected void onPostExecute(Boolean th){

            if(th == true){
                nDialog.dismiss();
                new PAcademicDetails().execute();
            }
            else{
                nDialog.dismiss();
            }
        }
    }

    class PAcademicDetails extends AsyncTask<String, String, JSONObject> {
        /**
         * Defining Process dialog
         **/
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(Emergency_Contact.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            ConstantClass userFunction = new ConstantClass();

            JSONObject json = userFunction.saveemergencycontact(SchemaName, CompanyId, Fieldvalue);
            // Log.d("Fieldvalue", Fieldvalue.toString());

            return json;


        }

        @Override
        protected void onPostExecute(JSONObject json) {
            /**
             * Checks for success message.
             **/
        /*    try {
                if (json.getString(KEY_SUCCESS) != null) {
                    registerErrorMsg.setText("");
                    String res = json.getString(KEY_SUCCESS);

                    String red = json.getString(KEY_ERROR);

                    if(Integer.parseInt(res) == 1){
                        pDialog.setTitle("Getting Data");
                        pDialog.setMessage("Loading Info");

                        registerErrorMsg.setText("Successfully Registered");


                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        JSONObject json_user = json.getJSONObject("user");

                        *//**
             * Removes all the previous data in the SQlite database
             **//*

                        UserFunctions logout = new UserFunctions();
                        logout.logoutUser(getApplicationContext());
                        db.addUser(json_user.getString(KEY_FIRSTNAME),json_user.getString(KEY_LASTNAME),json_user.getString(KEY_EMAIL),json_user.getString(KEY_USERNAME),json_user.getString(KEY_UID),json_user.getString(KEY_CREATED_AT));
                        *//**
             * Stores registered data in SQlite Database
             * Launch Registered screen
             **//*

                        Intent registered = new Intent(getApplicationContext(), Registered.class);

                        *//**
             * Close all views before launching Registered screen
             **//*
                        registered.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pDialog.dismiss();
                        startActivity(registered);


                        finish();
                    }

                    else if (Integer.parseInt(red) ==2){
                        pDialog.dismiss();
                    }
                    else if (Integer.parseInt(red) ==3){
                        pDialog.dismiss();
                    }

                }


                else{
                    pDialog.dismiss();



                }

            } catch (JSONException e) {
                e.printStackTrace();


            }*/
            pDialog.dismiss();
        }
    }



    private void setRecyclerViewData() {
     /*   friendArrayList.add(new Friend("Phan Thanh", false, "Cashier"));
        friendArrayList.add(new Friend("Nguyen Tuan", true, "Developer"));
        friendArrayList.add(new Friend("Tran Van Minh", true, "Designer"));
        friendArrayList.add(new Friend("Pham Mai Anh", true, "architect"));
        friendArrayList.add(new Friend("Nguyen Quynh Trang", false, "Doctor"));
        friendArrayList.add(new Friend("Hoang Dinh Cuong", false, "artist"));
        friendArrayList.add(new Friend("Tran Cong Bach", true, "Student"));
        friendArrayList.add(new Friend("Vu Van Duong", false, "Teacher"));*/
    }

    private View.OnClickListener onAddingListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* final Dialog dialog = new Dialog(Profile_Activity.this);
                dialog.setContentView(R.layout.dialog_add); //layout for dialog
                dialog.setTitle("Add a new friend");
                dialog.setCancelable(false); //none-dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                EditText name = (EditText) dialog.findViewById(R.id.name);
                EditText job = (EditText) dialog.findViewById(R.id.job);
                Spinner spnGender = (Spinner) dialog.findViewById(R.id.gender);
                View btnAdd = dialog.findViewById(R.id.btn_ok);
                View btnCancel = dialog.findViewById(R.id.btn_cancel);

                //set spinner adapter
                ArrayList<String> gendersList = new ArrayList<>();
                gendersList.add("Male");
                gendersList.add("Female");
                ArrayAdapter<String> spnAdapter = new ArrayAdapter<String>(Profile_Activity.this,
                        android.R.layout.simple_dropdown_item_1line, gendersList);
                spnGender.setAdapter(spnAdapter);*/

                //set handling event for 2 buttons and spinner
                // spnGender.setOnItemSelectedListener(onItemSelectedListener());

          /*      Name = (EditText)findViewById(R.id.name);
                Address = (EditText) findViewById(R.id.address);
                Relation = (EditText) findViewById(R.id.relation);
                Contact_Number = (EditText)findViewById(R.id.contact_number);*/


           //     mFab.setOnClickListener(onConfirmListener(Name, Address, Relation, Contact_Number));
                //  btnAdd.setOnClickListener(onConfirmListener(name, job, dialog));
                //   btnCancel.setOnClickListener(onCancelListener(dialog));

                //dialog.show();
            }
        };
    }






   /* private View.OnClickListener onConfirmListener(final EditText Name, final EditText Address,
                                                   final EditText Relation,final EditText Dateof_Birthe) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person friend = new Person(Name.getText().toString().trim(),Address.getText().toString().trim(),Relation.getText().toString().trim(),
                        Contact_Number.getText().toString().trim());
                Log.d("hi", Name.getText().toString().trim());
                Log.d("hi", Address.getText().toString().trim());
                Log.d("hi", Relation.getText().toString().trim());
                Log.d("hi", Contact_Number.getText().toString().trim());
                //adding new object to arraylist
                friendArrayList.add(friend);
                Log.d("friendArrayList", friendArrayList.toString());
                //notify data set changed in RecyclerView adapter
                adapter.notifyDataSetChanged();

                //close dialog after all
                //  dialog.dismiss();
                clear();
            }

        };
    }
*/
    public void clear()
    {
        Name.setText("");
        Address.setText("");
        Relation.setText("");
        ContactNumber.setText("");

    }
    private View.OnClickListener onCancelListener(final Dialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        };
    }
}
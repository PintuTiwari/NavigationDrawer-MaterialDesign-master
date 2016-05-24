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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

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
public class Employee_Language extends AppCompatActivity {
    private static final String PREF_NAME_PRIFRNCR = "isregisterd";
    String getMyDetils_String;
    Map<String, String> responseMap = new LinkedHashMap<String, String>();


    private RecyclerView recyclerView;
    private com.github.clans.fab.FloatingActionButton mFab;

    private RecyclerAdapterEmpLanguage adapter;
    private ArrayList<Person> friendArrayList;
    private AppCompatButton fab;
    private boolean gender;
    Context context = this;
    EditText Language;
    CheckBox Can_Read,Can_Write,Can_Speak;
    private int overallXScroll = 0;
    View.OnClickListener checkBoxListener;
    String Read,Write,Speak,SchemaName, userId, CompanyId,Fieldvalue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employeelanguage);
        findview();
        getprefrence();
        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnewdetails();
            }
        });

  /*      LinearLayout linearLayout = (LinearLayout) findViewById(R.id.layout_main_joining_documents);
        linearLayout.setVisibility(View.GONE);
        LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.layout_maintraning_details);
        linearLayout1.setVisibility(View.GONE);
        LinearLayout linearLayout2 = (LinearLayout) findViewById(R.id.layout_mainemployeehistory);
        linearLayout2.setVisibility(View.GONE);*/

        friendArrayList = new ArrayList<>();

     /*   fab = (AppCompatButton) findViewById(R.id.fab);
        Language = (EditText) findViewById(R.id.language);
        Can_Read = (CheckBox) findViewById(R.id.canread);
        Can_Write = (CheckBox) findViewById(R.id.canwrite);
        Can_Speak = (CheckBox) findViewById(R.id.canspeak);*/
        recyclerView.setHasFixedSize(true);


        recyclerView.setNestedScrollingEnabled(true);
/*        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);*/
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        setRecyclerViewData(); //adding data to array list

        adapter = new RecyclerAdapterEmpLanguage(this, friendArrayList);
        recyclerView.setAdapter(adapter);


        try {
            if (getMyDetils_String.length() > 0) {
                // friendArrayList.clear();
                JSONArray internships = new JSONArray(getMyDetils_String);
                JSONObject leagueObject = internships.getJSONObject(0);
                //Loop the Array
                for (int i = 0; i < leagueObject.length(); i++) {
                    Log.e("Message", "loop");
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject jsonObject = internships.getJSONObject(i);
                    Log.d("Employement in page", jsonObject.toString());

                  //  Person person = new Person(Language.getText().toString().trim());
                    Person person = new Person();

                    if (!jsonObject.isNull("Language")) {
                        person.Company_Name = jsonObject.getString("Language");

                        Log.d("Language", person.Company_Name.toString());
                    }
                    if (!jsonObject.isNull("IsRead")) {
                       String IsRead= jsonObject.getString("IsRead");
                        if(IsRead=="true")
                        {
                            person.Position = "Yes";
                        }
                        else
                            person.Position = "No";
                        Log.d("IsRead", person.Position.toString());
                    }
                    if (!jsonObject.isNull("IsWrite")) {

                        String IsWrite= jsonObject.getString("IsWrite");
                        if(IsWrite=="true")
                        {
                            person.From_Date = "Yes";
                        }
                        else
                            person.From_Date = "No";
                        Log.d("IsRead", person.From_Date.toString());



                    }
                    if (!jsonObject.isNull("IsSpeak")) {
                        String IsSpeak= jsonObject.getString("IsSpeak");
                        if(IsSpeak=="true")
                        {
                            person.To_Date = "Yes";
                        }
                        else
                            person.To_Date = "No";
                        Log.d("IsRead", person.To_Date.toString());
                    }
                    friendArrayList.add(i, person);
                    //adapter.notifyDataSetChanged();
                }
                // recyclerView.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }

       // fab.setOnClickListener(onAddingListener());
    }

    public void addnewdetails() {
       /* LayoutInflater inflater = LayoutInflater.from(this);
        View dialog_layout = inflater.inflate(R.layout.checkbox, null);
        final AlertDialog.Builder db = new AlertDialog.Builder(this);
        db.setView(dialog_layout);

       *//* LinearLayout linearLayout = (LinearLayout) dialog_layout.findViewById(R.id.layout_main_joining_documents);
        linearLayout.setVisibility(View.GONE);
        LinearLayout linearLayout1 = (LinearLayout) dialog_layout.findViewById(R.id.layout_mainemployeehistory);
        linearLayout1.setVisibility(View.GONE);
        LinearLayout linearLayout2 = (LinearLayout) dialog_layout.findViewById(R.id.layout_maintraning_details);
        linearLayout2.setVisibility(View.GONE);*//*

      *//*  Language = (EditText) dialog_layout.findViewById(R.id.language);
        Can_Read = (CheckBox) dialog_layout.findViewById(R.id.canread);
        Can_Write = (CheckBox)dialog_layout.findViewById(R.id.canwrite);
        Can_Speak = (CheckBox) dialog_layout.findViewById(R.id.canspeak);
*//*
        db.setTitle("Add Employee Language Details");
        // setTitleColor(R.color.colorAccent);0
        setTheme(R.style.MyTheme_ActionBar_TitleTextStyle);
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

               // UpdateDate();

            }
        });
        AlertDialog alert11 = db.create();
        alert11.show();

        Button buttonbackground = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonbackground.setTextColor(getResources().getColor(R.color.colorAccent));

        Button buttonbackground1 = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
        //buttonbackground1.setBackgroundColor(Color.BLUE);
        buttonbackground1.setTextColor(getResources().getColor(R.color.colorAccent));*/
        LayoutInflater inflater = LayoutInflater.from(context);
        View dialog_layout = inflater.inflate(R.layout.employeelanguageui, null);
        final AlertDialog.Builder db = new AlertDialog.Builder(context);
        db.setView(dialog_layout);

        Language = (EditText)dialog_layout.findViewById(R.id.language);
        Can_Read = (CheckBox)dialog_layout. findViewById(R.id.canread);
        Can_Write = (CheckBox) dialog_layout.findViewById(R.id.canwrite);
        Can_Speak = (CheckBox)dialog_layout.findViewById(R.id.canspeak);

        db.setTitle("Add Language Details");
        // setTitleColor(R.color.colorAccent);0
       // setTheme(R.style.MyTheme_ActionBar_TitleTextStyle);
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
                   String Languages = Language.getText().toString();
                   String Can_Read1= String.valueOf(Can_Read.isChecked());
                   String Can_Write1 = String.valueOf(Can_Write.isChecked());
                   String Can_Speak1  = String.valueOf(Can_Speak.isChecked());

                    responseMap.put("Language", Languages);
                    responseMap.put("IsRead", Can_Read1);
                    responseMap.put("IsWrite", Can_Write1);
                    responseMap.put("IsSpeak", Can_Speak1);
                    Log.d("AcademicMAP: ", responseMap.toString());
                    Gson gson = new Gson();
                    Fieldvalue = gson.toJson(responseMap);
                    Log.d("Fieldvalue", Fieldvalue.toString());
                    if(Fieldvalue!=null)
                    {
                        new NetCheck().execute();
                    }

                 }
    private class NetCheck extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(Employee_Language.this);
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

            pDialog = new ProgressDialog(Employee_Language.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            ConstantClass userFunction = new ConstantClass();

            JSONObject json = userFunction.saveemployeelanguage(SchemaName, CompanyId, Fieldvalue);
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
       private void getprefrence() {
        SharedPreferences setting = this.getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        getMyDetils_String = setting.getString("response", "response");
        SchemaName = setting.getString("SchemaName", "SchemaName");
        userId = setting.getString("userId", "userId");
        CompanyId = setting.getString("CompanyId", "CompanyId");

    }

    private void findview() {
        mFab = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu1);
        recyclerView = (RecyclerView) findViewById(R.id.recyle_viewacademic);
    }

        /*checkBoxListener =new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(Can_Write.isChecked()) {
            //   Write= Can_Write.getText().toString();
                Write="Can_ Write";
                // String   chetimeslot = (String)Can_Write.getTag();
                    Log.d("hi", Write);


                }

                if(Can_Read.isChecked()) {
                     //Read= Can_Read.getText().toString();
                    Read="Can_Read";
                    Log.d("hi", Read);
                }

                if(Can_Speak.isChecked()) {
                    // Speak= Can_Speak.getTag().toString();
                    Speak="Can_Speak";
                    Log.d("hi", Speak);
                }
            }
        };
        Can_Write.setOnClickListener(checkBoxListener);
        Can_Read.setOnClickListener(checkBoxListener);
        Can_Speak.setOnClickListener(checkBoxListener);
    }*/




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

        /*        Language = (EditText)findViewById(R.id.language);
                Can_Read = (CheckBox) findViewById(R.id.canread);
                Can_Write = (CheckBox) findViewById(R.id.canwrite);
                Can_Speak = (CheckBox)findViewById(R.id.canspeak);

*/
                fab.setOnClickListener(onConfirmListener(Language, Can_Read, Can_Write, Can_Speak));
                //  btnAdd.setOnClickListener(onConfirmListener(name, job, dialog));
                //   btnCancel.setOnClickListener(onCancelListener(dialog));

                //dialog.show();
            }
        };
    }




    private AdapterView.OnItemSelectedListener onItemSelectedListener() {
        return new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    gender = true;
                } else {
                    gender = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        };
    }




    private View.OnClickListener onConfirmListener(final EditText Language, final CheckBox Can_Read,
                                                   final CheckBox Can_Write,final CheckBox Can_Speak) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Can_Write.isChecked()) {
                    //   Write= Can_Write.getText().toString();
                    Write = "Can Write";
                    // String   chetimeslot = (String)Can_Write.getTag();
                    Log.d("hi", Write);


                    if (Can_Read.isChecked()) {
                        //Read= Can_Read.getText().toString();
                        Read = "Can Read";
                        Log.d("hi", Read);


                        if (Can_Speak.isChecked()) {
                            // Speak= Can_Speak.getTag().toString();
                            Speak = "Can Speak";
                            Log.d("hi", Speak);


                            Person friend = new Person(Language.getText().toString().trim(),Write,Read,Speak);
                            Log.d("hi", Language.getText().toString().trim());
                            Log.d("higyhg", Speak);

                Log.d("hi", Can_Read.getText().toString().trim());
                Log.d("hi", Can_Write.getText().toString().trim());
                Log.d("hi", Can_Speak.getText().toString().trim());
                String Read= Can_Read.getText().toString();
                String Write= Can_Write.getText().toString();
                String Speak= Can_Speak.getText().toString();

                            //adding new object to arraylist
                            friendArrayList.add(friend);
                            //Log.d("friendArrayList", friendArrayList.toString());
                            //notify data set changed in RecyclerView adapter
                            adapter.notifyDataSetChanged();

                            //close dialog after all
                            //  dialog.dismiss();
                            clear();
                            Can_Write.setOnClickListener(checkBoxListener);
                            Can_Read.setOnClickListener(checkBoxListener);
                            Can_Speak.setOnClickListener(checkBoxListener);
                        }
                    }
                }
            }
        };
    }

    public void clear()
    {
        Language.setText("");
       // Address.setText("");
       // Relation.setText("");
       // Dateof_Birth.setText("");

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
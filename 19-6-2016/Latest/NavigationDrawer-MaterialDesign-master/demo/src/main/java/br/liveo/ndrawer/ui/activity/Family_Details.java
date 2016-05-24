package br.liveo.ndrawer.ui.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import br.liveo.ndrawer.R;
import br.liveo.ndrawer.adapter.ConstantClass;

public class Family_Details extends ActionBarActivity implements View.OnClickListener ,OnItemClickListener{
    String part1,part44,part66,part55;
    int part4,part6,part5;
    private RecyclerView recyclerView;
    private RecyclerAdapterNaminee adapter;
    private ArrayList<Person> friendArrayList;
    final int Date_Dialog_ID=0;
    private com.github.clans.fab.FloatingActionButton mFab;
    private boolean gender;
    int cDay,cMonth,cYear; // this is the instances of the current date
    int sDay,sMonth,sYear; // this is the instances of the entered date
    private Calendar cal;
    private int day;
    private int month;
    private int year;
    private ImageView ib;
    private int mYear, mMonth, mDay, mHour, mMinute;

    private static final String PREF_NAME_PRIFRNCR = "isregisterd";
    View dialog_layout;
    Context context = this;
    String getMyDetils_String;
    Map<String, String> responseMap = new LinkedHashMap<String, String>();

    String Names, Addresss, Relations, Ages,Dateof_Births, SchemaName, userId, CompanyId,Fieldvalue;

    EditText Name,Address,Relation,Age,Dateof_Birth;
    private int overallXScroll = 0;
    private OnItemClickListener listnere;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydetails1);
        /*listnere = new OnItemClickListener() {
            @Override
            public void onItemClicked(View v) {
                Academic_Dilog newFragment = new Academic_Dilog();
                newFragment.show(getSupportFragmentManager(), "missiles");
            }

            @Override
            public void onItemClick(int mCurrentPosition) {

            }
        };*/
        FrameLayout linearLayout=(FrameLayout)findViewById(R.id.frame_view_viewacademic);
        linearLayout.setVisibility(View.GONE);
        FrameLayout linearLayout1=(FrameLayout)findViewById(R.id.frame_view_emergency);
        linearLayout1.setVisibility(View.GONE);
        FrameLayout linearLayout2=(FrameLayout)findViewById(R.id.frame_view_nominee);
        linearLayout2.setVisibility(View.GONE);

        initializeRecyclerView();
        recyclerView = (RecyclerView) findViewById(R.id.recyle_family);
        mFab = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu1_family);
        friendArrayList = new ArrayList<>();

       /* ib = (ImageView) findViewById(R.id.imgSearch);
        ib.setOnClickListener(this);
*/    mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnewdetails();
               /* DialogFragment newFragment = new Academic_Dilog();
                newFragment.show(getSupportFragmentManager(), "missiles");*/
            }
        });


        /*Name = (EditText)findViewById(R.id.name);
        Address = (EditText) findViewById(R.id.address);
        Relation = (EditText) findViewById(R.id.relation);
        Dateof_Birth = (EditText)findViewById(R.id.dateofbirth);
        Age = (EditText) findViewById(R.id.age);*/

        //recyclerView.setHasFixedSize(true);
        //recyclerView.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        setRecyclerViewData(); //adding data to array list

        SharedPreferences setting = this.getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        getMyDetils_String  = setting.getString("response", "response");
        SchemaName = setting.getString("SchemaName", "SchemaName");
        userId = setting.getString("userId", "userId");
        CompanyId = setting.getString("CompanyId", "CompanyId");

        Log.d("SchemaNameAcdemic", SchemaName.toString());
        Log.d("userIdAcdemic", userId.toString());
        Log.d("CompanyIdAcdemic", CompanyId.toString());

       // mAdapter = new ApplicationAdapter(new ArrayList<AppInfo>(), R.layout.row_application, MainActivity.this);



      //  adapter = new RecyclerAdapterNaminee(this, friendArrayList,listener);

        adapter = new RecyclerAdapterNaminee(this, friendArrayList);
       // adapter = new RecyclerAdapterNaminee(this, friendArrayList,this);

        recyclerView.setAdapter(adapter);

        //fab.setOnClickListener(onAddingListener());


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

/*
                    Person person = new Person(Name.getText().toString().trim(), Address.getText().toString().trim(),
                            Relation.getText().toString().trim(),
                            Dateof_Birth.getText().toString().trim(),
                            Age.getText().toString().trim());*/
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
                        Log.d("Company",person.From_Date.toString());
                    }
                    if (!jsonObject.isNull("DOB")) {
                      //  person.To_Date = jsonObject.getString("DOB");
                        String date = jsonObject.getString("DOB");

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                        Date tempDate=simpleDateFormat.parse(date);
                        System.out.println("DATEOFBIRTH1 tempDate = " + tempDate);

                        //  SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        System.out.println("DATEOFBIRTH1 = " + outputDateFormat.format(tempDate));
                        String date1=outputDateFormat.format(tempDate);
                        String[] part3 = date1.split("/");
                        part44 = part3[0]; // 004
                        part4=Integer.parseInt(part44);

                        part55 = part3[1]; // 034556
                        part5=Integer.parseInt(part55);

                        part66= part3[2];
                        part6=Integer.parseInt(part66);
                    /*    String[] parts = date.split("T");
                        String part1 = parts[0]; // 004
                        String part2 = parts[1]; // 034556
                        System.out.println(part1);*/
                        person.To_Date=date1;

                        /*Calendar cDate= Calendar.getInstance();
                        cDate.set(part4, part5, part6);

                        cDay=cDate.get(Calendar.DAY_OF_MONTH);
                        cMonth=cDate.get(Calendar.MONTH);
                        cYear=cDate.get(Calendar.YEAR);
//assigning the edittext with the current date in the beginning
                        sDay=cDay;
                        sMonth=cMonth;
                        sYear=cYear;
                        updateDateDisplay(sYear, sMonth, sDay);*/

                        Log.d("Company",person.To_Date.toString());
                    }
                    if (!jsonObject.isNull("Age")) {
                        person.Training_Name = jsonObject.getString("Age");
                        Log.d("Company",person.Training_Name.toString());
                    }

                    friendArrayList.add(i, person);

                }
                // recyclerView.notifyDataSetChanged();
            }
        } catch(JSONException e) {
            Log.e("log_tag", "Error parsing data "+e.toString());
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

    void initializeRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyle_family);
        //adapter = new RecyclerAdapterNaminee(this, friendArrayList,this);
        adapter = new RecyclerAdapterNaminee(this, friendArrayList);

        recyclerView.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }




    private void addnewdetails() {
        LayoutInflater inflater = LayoutInflater.from(this);
         dialog_layout = inflater.inflate(R.layout.dilog, null);
        final AlertDialog.Builder db = new AlertDialog.Builder(this);
        db.setView(dialog_layout);

        LinearLayout linearLayout = (LinearLayout) dialog_layout.findViewById(R.id.layout_academic);
        linearLayout.setVisibility(View.GONE);
        LinearLayout linearLayout1 = (LinearLayout) dialog_layout.findViewById(R.id.layout_main_nominee);
        linearLayout1.setVisibility(View.GONE);
        LinearLayout linearLayout2 = (LinearLayout) dialog_layout.findViewById(R.id.layout_foremergency);
        linearLayout2.setVisibility(View.GONE);



        Name = (EditText)dialog_layout.findViewById(R.id.name);
        Address = (EditText) dialog_layout.findViewById(R.id.address);
        Relation = (EditText) dialog_layout.findViewById(R.id.relation);
        Dateof_Birth = (EditText)dialog_layout. findViewById(R.id.dateofbirth);
        Age = (EditText)dialog_layout. findViewById(R.id.age);
        Dateof_Birth.setFocusable(false);
        Dateof_Birth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cDate = Calendar.getInstance();

                mDay = cDate.get(Calendar.DAY_OF_MONTH);
                mMonth = cDate.get(Calendar.MONTH);
                mYear = cDate.get(Calendar.YEAR);


                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        // txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        Dateof_Birth.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        db.setTitle("Add Family Details");
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
        Names = Name.getText().toString();
        Addresss = Address.getText().toString();
        Relations = Relation.getText().toString();
        Ages = Age.getText().toString();
        Dateof_Births = Dateof_Birth.getText().toString();

        responseMap.put("Name", Names);
        responseMap.put("Address",Addresss);
        responseMap.put("Relation",Relations);
        responseMap.put("DOB",Dateof_Births);
        responseMap.put("Age", Ages);

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
            nDialog = new ProgressDialog(Family_Details.this);
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

            pDialog = new ProgressDialog(Family_Details.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            ConstantClass userFunction = new ConstantClass();

            JSONObject json = userFunction.saveacademicDetails(SchemaName, CompanyId, Fieldvalue);
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


    public void openDialog(final int position) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                context);

        // set title
        alertDialogBuilder.setTitle("Alert");
       // setTheme(R.style.MyTheme_ActionBar_TitleTextStyle);

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

                Name = (EditText)findViewById(R.id.name);
                Address = (EditText) findViewById(R.id.address);
                Relation = (EditText) findViewById(R.id.relation);
                Dateof_Birth = (EditText)findViewById(R.id.dateofbirth);
                Age = (EditText) findViewById(R.id.age);

                mFab.setOnClickListener(onConfirmListener(Name, Address, Relation, Dateof_Birth, Age));
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

    private View.OnClickListener onConfirmListener(final EditText Name, final EditText Address,
                                                   final EditText Relation,final EditText Dateof_Birth,final EditText Age) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person friend = new Person(Name.getText().toString().trim(),Address.getText().toString().trim(),Relation.getText().toString().trim(),
                        Dateof_Birth.getText().toString().trim(), Age.getText().toString().trim());
                Log.d("hi", Name.getText().toString().trim());
                Log.d("hi", Address.getText().toString().trim());
                Log.d("hi", Relation.getText().toString().trim());
                Log.d("hi", Dateof_Birth.getText().toString().trim());
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

    public void clear()
    {
        Name.setText("");
        Address.setText("");
        Relation.setText("");
        Dateof_Birth.setText("");
        Age.setText("");
    }
    private View.OnClickListener onCancelListener(final Dialog dialog) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        };
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void onItemClicked(View v) {
        Academic_Dilog newFragment = new Academic_Dilog();
        newFragment.show(getSupportFragmentManager(), "missiles");
    }

    @Override
    public void onItemClick(int mCurrentPosition) {

    }


}
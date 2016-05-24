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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
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

/**
 * Created by rupa.dwivedi on 29-01-2016.
 */
public class Training_Details extends AppCompatActivity {
  //  Training Name	Date of Completion	Certification Number	Institute	Action
    private RecyclerView recyclerView;
    private RecyclerAdapterTranning adapter;
    private ArrayList<Person1> friendArrayList;
    private com.github.clans.fab.FloatingActionButton fab;
    private boolean gender;
    String part1,part44,part66,part55;
    int part4,part6,part5;
    int cDay,cMonth,cYear; // this is the instances of the current date
    Calendar cDate;
    int sDay,sMonth,sYear; // this is the instances of the entered date
    private int mYear, mMonth, mDay, mHour, mMinute;

    private static final String PREF_NAME_PRIFRNCR = "isregisterd";
    final int Date_Dialog_ID=0;
    Map<String, String> responseMap = new LinkedHashMap<String, String>();

    Context context = this;
    String getMyDetils_String,SchemaName, userId, CompanyId,Fieldvalue;
    EditText Training_Name,Date_ofCompletion,Certification_Number, Institute;
    String Training_Names,Date_ofCompletions,Certification_Numbers, Institutes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trannningrecyclelayout);
        SharedPreferences setting = this.getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        getMyDetils_String  = setting.getString("response", "response");
        SchemaName = setting.getString("SchemaName", "SchemaName");
        userId = setting.getString("userId", "userId");
        CompanyId = setting.getString("CompanyId", "CompanyId");
        Log.d("hi", getMyDetils_String);
   /*     LinearLayout linearLayout1=(LinearLayout)findViewById(R.id.layout_mainlanguage);
        linearLayout1.setVisibility(View.GONE);

        LinearLayout linearLayout2=(LinearLayout)findViewById(R.id.layout_main_joining_documents);
        linearLayout2.setVisibility(View.GONE);


        LinearLayout linearLayout3=(LinearLayout)findViewById(R.id.layout_mainemployeehistory);
        linearLayout3.setVisibility(View.GONE);*/

        friendArrayList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyle_viewtranning);
        fab = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menue1_tranning);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnewdetails();
            }
        });
     /*   Training_Name = (EditText)findViewById(R.id.training_name);
        Date_ofCompletion = (EditText) findViewById(R.id.dateofcompletion);
        Certification_Number = (EditText) findViewById(R.id.certificationnumber);
        Institute = (EditText)findViewById(R.id.institute);*/



        recyclerView.setHasFixedSize(true);
        //recyclerView.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);

        recyclerView.setLayoutManager(layoutManager);

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

                /*    Person1 person = new Person1(Training_Name.getText().toString().trim(), Date_ofCompletion.getText().toString().trim(),
                            Certification_Number.getText().toString().trim(),
                            Institute.getText().toString().trim());*/
                    Person1 person=new Person1();
                    if (!jsonObject.isNull("TrainingName")) {
                        person.training_name = jsonObject.getString("TrainingName");
                        Log.d("TrainingName",person.training_name.toString());
                    }
                    if (!jsonObject.isNull("DateOfCompletion")) {
                        String date = jsonObject.getString("DateOfCompletion");
                      //  person.date_ofcompletion = jsonObject.getString("DateOfCompletion");

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
                        person.date_ofcompletion=date1;



                        Log.d("DateOfCompletion",person.date_ofcompletion.toString());
                    }
                    if (!jsonObject.isNull("CertificationNumber")) {
                        person.certification_number = jsonObject.getString("CertificationNumber");
                        Log.d("CertificationNumber",person.certification_number.toString());
                    }
                    if (!jsonObject.isNull("Institute")) {
                        person.institute = jsonObject.getString("Institute");
                        Log.d("Institute",person.institute.toString());
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


        setRecyclerViewData(); //adding data to array list





        // mAdapter = new ApplicationAdapter(new ArrayList<AppInfo>(), R.layout.row_application, MainActivity.this);



        // adapter = new RecyclerAdapterNaminee(this, friendArrayList);
        adapter = new RecyclerAdapterTranning(this, friendArrayList);
        recyclerView.setAdapter(adapter);

       // fab.setOnClickListener(onAddingListener());
    }



    private void addnewdetails() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialog_layout = inflater.inflate(R.layout.nominee_diloglayout, null);
        final AlertDialog.Builder db = new AlertDialog.Builder(this);
        db.setView(dialog_layout);

        LinearLayout linearLayout = (LinearLayout) dialog_layout.findViewById(R.id.layout_main_nominee);
        linearLayout.setVisibility(View.GONE);

        Training_Name = (EditText)dialog_layout.findViewById(R.id.training_name);
        Date_ofCompletion = (EditText)dialog_layout.findViewById(R.id.dateofcompletion);
        Certification_Number = (EditText)dialog_layout.findViewById(R.id.certificationnumber);
        Institute = (EditText)dialog_layout.findViewById(R.id.institute);
        Date_ofCompletion.setFocusable(false);
        Date_ofCompletion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar cDate = Calendar.getInstance();
                //http://www.javabeat.net/datepickerdialog-android/
                mDay = cDate.get(Calendar.DAY_OF_MONTH);
                mMonth = cDate.get(Calendar.MONTH);
                mYear = cDate.get(Calendar.YEAR);


                DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                        // txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                        Date_ofCompletion.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });


        db.setTitle("Add Academic Details");
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

        Training_Names = Training_Name.getText().toString();
        Date_ofCompletions = Date_ofCompletion.getText().toString();
        Certification_Numbers = Certification_Number.getText().toString();
        Institutes = Institute.getText().toString();

        responseMap.put("TrainingName", Training_Names);
        responseMap.put("DateOfCompletion", Date_ofCompletions);
        responseMap.put("CertificationNumber", Certification_Numbers);
        responseMap.put("Institute", Institutes);

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
            nDialog = new ProgressDialog(Training_Details.this);
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

            pDialog = new ProgressDialog(Training_Details.this);
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

       /*         Training_Name = (EditText)findViewById(R.id.training_name);
                Date_ofCompletion = (EditText) findViewById(R.id.date_completion);
                Certification_Number = (EditText) findViewById(R.id.certification_number);
                Institute = (EditText)findViewById(R.id.institute);*/
                Training_Name = (EditText)findViewById(R.id.companyname);
                Date_ofCompletion = (EditText) findViewById(R.id.position);
                Certification_Number = (EditText) findViewById(R.id.fromdate);
                Institute = (EditText)findViewById(R.id.todate);
                fab.setOnClickListener(onConfirmListener(Training_Name, Date_ofCompletion, Certification_Number, Institute));
                //  btnAdd.setOnClickListener(onConfirmListener(name, job, dialog));
                //   btnCancel.setOnClickListener(onCancelListener(dialog));

                //dialog.show();
            }
        };
    }


    private View.OnClickListener onConfirmListener(final EditText Training_Name, final EditText Date_ofCompletion,
                                                   final EditText Certification_Number,final EditText Institute) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Person1 friend = new Person1(Training_Name.getText().toString().trim(),
                        Date_ofCompletion.getText().toString().trim(),
                        Certification_Number.getText().toString().trim(),
                        Institute.getText().toString().trim());

                Log.d("hi", Training_Name.getText().toString().trim());
                Log.d("hi", Date_ofCompletion.getText().toString().trim());
                Log.d("hi", Certification_Number.getText().toString().trim());
                Log.d("hi", Institute.getText().toString().trim());
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
        Training_Name.setText("");
        Date_ofCompletion.setText("");
        Certification_Number.setText("");
        Institute.setText("");

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

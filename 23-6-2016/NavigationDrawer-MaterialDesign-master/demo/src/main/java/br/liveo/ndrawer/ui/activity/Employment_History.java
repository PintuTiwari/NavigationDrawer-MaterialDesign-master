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
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

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
public class Employment_History extends AppCompatActivity {

    private RecyclerView recyclerView;
     RVAdapter adapter;
    private ArrayList<Person> friendArrayList;
    private AppCompatButton fab;
    String part1,part44,part66,part55;
    int part4,part6,part5;
    private boolean gender;
    private static final String PREF_NAME_PRIFRNCR = "isregisterd";
    private int mYear, mMonth, mDay, mHour, mMinute;

    Context context = this;
    String getMyDetils_String;
    EditText Company_Name,Position,From_Date, To_Date;
    String   Company_Names,Positions,From_Dates, To_Dates,SchemaName, userId, CompanyId,Fieldvalue;
    Map<String, String> responseMap = new LinkedHashMap<String, String>();

    private com.github.clans.fab.FloatingActionButton mFab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.employment_layout);

         mFab = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu1_employment);

         mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addnewdetails();
            }
        });

       /* LinearLayout linearLayout1=(LinearLayout)findViewById(R.id.layout_mainlanguage);
        linearLayout1.setVisibility(View.GONE);
        LinearLayout linearLayout2=(LinearLayout)findViewById(R.id.layout_main_joining_documents);
        linearLayout2.setVisibility(View.GONE);

        LinearLayout linearLayout3=(LinearLayout)findViewById(R.id.layout_maintraning_details);
        linearLayout3.setVisibility(View.GONE);
*/

       /* cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);*/

        SharedPreferences setting = this.getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        getMyDetils_String  = setting.getString("response", "response");
        SchemaName = setting.getString("SchemaName", "SchemaName");
        userId = setting.getString("userId", "userId");
        CompanyId = setting.getString("CompanyId", "CompanyId");
        friendArrayList = new ArrayList<>();

        recyclerView = (RecyclerView) findViewById(R.id.recyle_employemnt);
       /* fab = (AppCompatButton) findViewById(R.id.fabtranning);
        Company_Name = (EditText)findViewById(R.id.companyname);
        Position = (EditText) findViewById(R.id.position);
        From_Date = (EditText) findViewById(R.id.fromdate);
        To_Date = (EditText)findViewById(R.id.todate);*/


        recyclerView.setHasFixedSize(true);
        //recyclerView.setNestedScrollingEnabled(true);
        setRecyclerViewData(); //adding data to array list
        // mAdapter = new ApplicationAdapter(new ArrayList<AppInfo>(), R.layout.row_application, MainActivity.this);
        // adapter = new RecyclerAdapterNaminee(this, friendArrayList);

        LinearLayoutManager llm = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(llm);

        final RVAdapter rvAdapter = new RVAdapter(this,friendArrayList);
        recyclerView.setAdapter(rvAdapter);
//        fab.setOnClickListener(onAddingListener());
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
                /*    Person person = new Person(Company_Name.getText().toString().trim(), Position.getText().toString().trim(),
                    From_Date.getText().toString().trim(),
                    To_Date.getText().toString().trim());*/
                    Person person=new Person();
                    if (!jsonObject.isNull("CompanyName")) {
                        person.Company_Name = jsonObject.getString("CompanyName");
                        Log.d("Company",person.Company_Name.toString());
                    }
                    if (!jsonObject.isNull("Position")) {
                        person.Position = jsonObject.getString("Position");
                        Log.d("Company",person.Position.toString());
                    }
                    if (!jsonObject.isNull("FromDate")) {
                        String date = jsonObject.getString("FromDate");
                      /*  String[] parts = date.split("T");
                        String part1 = parts[0]; // 004
                        String part2 = parts[1]; // 034556
                        System.out.println(part1);
                        person.From_Date=part1;*/
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                        Date tempDate=simpleDateFormat.parse(date);
                        System.out.println("DATEOFBIRTH1 tempDate = " + tempDate);

                        //  SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        System.out.println("FromDate = " + outputDateFormat.format(tempDate));
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
                        person.From_Date=date1;
                    }
                     if (!jsonObject.isNull("ToDate")) {

                        String date = jsonObject.getString("ToDate");
                      /*  String[] parts = date.split("T");
                        String part1 = parts[0]; // 004
                        String part2 = parts[1]; // 034556
                        System.out.println(part1);
                        person.To_Date=part1;*/

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

    private void addnewdetails() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialog_layout = inflater.inflate(R.layout.employemnt_addlayout, null);
        final AlertDialog.Builder db = new AlertDialog.Builder(this);
        db.setView(dialog_layout);

       /* LinearLayout linearLayout1=(LinearLayout)dialog_layout.findViewById(R.id.layout_mainlanguage);
        linearLayout1.setVisibility(View.GONE);
        LinearLayout linearLayout2=(LinearLayout)dialog_layout.findViewById(R.id.layout_main_joining_documents);
        linearLayout2.setVisibility(View.GONE);

        LinearLayout linearLayout3=(LinearLayout)dialog_layout.findViewById(R.id.layout_maintraning_details);
        linearLayout3.setVisibility(View.GONE);*/

        Company_Name = (EditText) dialog_layout.findViewById(R.id.companyname);
        Position = (EditText)dialog_layout. findViewById(R.id.position);
        From_Date = (EditText)dialog_layout. findViewById(R.id.fromdate);
        To_Date = (EditText)dialog_layout. findViewById(R.id.todate);

        To_Date.setFocusable(false);
        To_Date.setOnClickListener(new View.OnClickListener() {
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
                        To_Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        From_Date.setFocusable(false);
        From_Date.setOnClickListener(new View.OnClickListener() {
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
                        From_Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                    }
                }, mYear, mMonth, mDay);
                datePickerDialog.show();
            }
        });

        db.setTitle("Add Employment Details");
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
        Company_Names= Company_Name.getText().toString();
        Positions= Position.getText().toString();
        From_Dates=From_Date.getText().toString();
        To_Dates=To_Date.getText().toString();

        responseMap.put("CompanyName", Company_Names);
        responseMap.put("Position",Positions);
        responseMap.put("FromDate",From_Dates);
        responseMap.put("ToDate",To_Dates);

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
            nDialog = new ProgressDialog(Employment_History.this);
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

            pDialog = new ProgressDialog(Employment_History.this);
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
              /*  Company_Name = (EditText)findViewById(R.id.companyname);
                Position = (EditText) findViewById(R.id.position);
                From_Date = (EditText) findViewById(R.id.fromdate);
                To_Date = (EditText)findViewById(R.id.todate);*/

                fab.setOnClickListener(onConfirmListener(Company_Name, Position,From_Date,To_Date));
                // btnAdd.setOnClickListener(onConfirmListener(name, job, dialog));
                //   btnCancel.setOnClickListener(onCancelListener(dialog));

                //dialog.show();
            }
        };
    }

    private View.OnClickListener onConfirmListener(final EditText Company_Name, final EditText Position, final EditText From_Date, final EditText  To_Date) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person friend = new Person(Company_Name.getText().toString().trim(),From_Date.getText().toString().trim(),
                        To_Date.getText().toString().trim(),
                        Position.getText().toString().trim());

                Log.d("hi", Company_Name.getText().toString().trim());
                Log.d("hi", Position.getText().toString().trim());
                friendArrayList.clear();
                friendArrayList.add(friend);
                adapter.notifyDataSetChanged();
               // friendArrayList.add(friend);
                Log.d("friendArrayList", friendArrayList.toString());
                //notify data set changed in RecyclerView adapter
             //  adapter.notifyDataSetChanged();

                //close dialog after all
                //  dialog.dismiss();
                clear();
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

 /*   private View.OnClickListener onConfirmListener(final EditText Training_Name, final EditText Date_ofCompletion,
                                                   final EditText Certification_Number,final EditText Institute) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Adamic_Pojo friend = new Adamic_Pojo(Training_Name.getText().toString().trim(),
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
    }*/

    public void clear()
    {

        Company_Name.setText("");
        Position.setText("");
        From_Date.setText("");
        To_Date.setText("");

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

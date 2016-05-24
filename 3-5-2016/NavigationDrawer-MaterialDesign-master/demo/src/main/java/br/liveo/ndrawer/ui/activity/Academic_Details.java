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
import android.support.v7.widget.AppCompatButton;
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
import br.liveo.ndrawer.adapter.JSONParser;
import br.liveo.ndrawer.adapter.NetworkMan;

/**
 * Created by rupa.dwivedi on 27-01-2016.
 */
public class Academic_Details extends ActionBarActivity implements View.OnClickListener {
    LinearLayout layout_academic;
    private NetworkMan networkMan;

    private RecyclerView recyclerView;
    private RecyclerAdapterAcadmic adapter;
    private ArrayList<Person> friendArrayList;
    private AppCompatButton Save;
    Context context = this;
    FrameLayout frameLayout;
    String getMyDetils_String;
    Map<String, String> responseMap = new LinkedHashMap<String, String>();

    private static final String PREF_NAME_PRIFRNCR = "isregisterd";
    EditText DegreeName, Year_ofpassing, Institution_name, Grade_class, dateDisplay;
    String Degrees, Year_ofpassings, Institution_names, Grade_classs, SchemaName, userId, CompanyId,Fieldvalue;

    private int overallXScroll = 0;
    String temp1 = "{'Degree':'deshmukh.abc2@gmail.com','Yearof_Passing':'remarks','" +
            "Institution_Name':'','GradeClass':'Mumbai'}";

    // FloatingActionButton mFab;
    private com.github.clans.fab.FloatingActionButton mFab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydetails1);


        friendArrayList = new ArrayList<>();
        initializeRecyclerView();
        findview();
        getprefrence();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("Failure", "Failure");

                addnewdetails();
              //  Academic_Dilog academic_dilog=new Academic_Dilog();
               /* DialogFragment newFragment = new Academic_Dilog();
                newFragment.show(getSupportFragmentManager(), "missiles");*/
                Log.d("Failure", "Failure");

            }
        });
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        adapter = new RecyclerAdapterAcadmic(this, friendArrayList);
        recyclerView.setAdapter(adapter);
        /*recyclerView.setHasFixedSize(true);
     //   recyclerView.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);


      //  setRecyclerViewData(); //adding data to array list
        adapter = new RecyclerAdapterAcadmic(this, friendArrayList);
        recyclerView.setAdapter(adapter);*/
        //  Save.setOnClickListener(onAddingListener());


        try {
            if (getMyDetils_String.length() > 0) {
                friendArrayList.clear();
                JSONArray internships = new JSONArray(getMyDetils_String);
                //JSONObject leagueObject = internships.getJSONObject(0);
                //Loop the Array
                for (int i = 0; i < internships.length(); i++) {
                    Log.e("Message", "loop");
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject jsonObject = internships.getJSONObject(i);
                    Log.d("Employement in page", jsonObject.toString());
                   /* Log.d("EmployementHi_response ", e.toString());
                    map.put("id", String.valueOf("id"));*/

                   /* Person person = new Person(Degree.getText().toString().trim(), Year_ofpassing.getText().toString().trim(),
                            Institution_name.getText().toString().trim(),
                            Grade_class.getText().toString().trim());*/
                    Person person = new Person();

                    if (!jsonObject.isNull("DegreeName")) {
                        person.Company_Name = jsonObject.getString("DegreeName");
                        Log.d("Company", person.Company_Name.toString());
                    }
                    if (!jsonObject.isNull("YearOfPassing")) {
                        person.Position = jsonObject.getString("YearOfPassing");
                        Log.d("Company", person.Position.toString());
                    }
                    if (!jsonObject.isNull("InstitutionName")) {
                        person.From_Date = jsonObject.getString("InstitutionName");
                    }
                    if (!jsonObject.isNull("Grade_Class")) {
                        person.To_Date = jsonObject.getString("Grade_Class");
                    }
                    friendArrayList.add(i, person);
                }
                // recyclerView.notifyDataSetChanged();
            }
        } catch (JSONException e) {
            Log.e("log_tag", "Error parsing data " + e.toString());
        }
    }

    private void getprefrence() {
        SharedPreferences setting = this.getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        getMyDetils_String = setting.getString("response", "response");
        SchemaName = setting.getString("SchemaName", "SchemaName");
        userId = setting.getString("userId", "userId");
        CompanyId = setting.getString("CompanyId", "CompanyId");

        Log.d("SchemaNameAcdemic", SchemaName.toString());
        Log.d("userIdAcdemic", userId.toString());
        Log.d("CompanyIdAcdemic", CompanyId.toString());
    }

    private void findview()
    {
        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame_view_emergency);
        frameLayout.setVisibility(View.GONE);
        mFab = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.menu1);
        recyclerView = (RecyclerView) findViewById(R.id.recyle_viewacademic);

    }

    void initializeRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyle_viewacademic);
        adapter = new RecyclerAdapterAcadmic(this, friendArrayList);
        recyclerView.setAdapter(adapter);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
    }

    private void addnewdetails()
    {
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialog_layout = inflater.inflate(R.layout.dilog, null);
        final AlertDialog.Builder db = new AlertDialog.Builder(this);
        db.setView(dialog_layout);
        Log.d("Failure", "Failure");

        LinearLayout linearLayout = (LinearLayout) dialog_layout.findViewById(R.id.layout_main);
        linearLayout.setVisibility(View.GONE);
        LinearLayout linearLayout1 = (LinearLayout) dialog_layout.findViewById(R.id.layout_main_nominee);
        linearLayout1.setVisibility(View.GONE);
        LinearLayout linearLayout2 = (LinearLayout) dialog_layout.findViewById(R.id.layout_foremergency);
        linearLayout2.setVisibility(View.GONE);


        DegreeName = (EditText) dialog_layout.findViewById(R.id.degree);
        Year_ofpassing = (EditText) dialog_layout.findViewById(R.id.year_ofpassing);
        Institution_name = (EditText) dialog_layout.findViewById(R.id.institution_name);
        Grade_class = (EditText) dialog_layout.findViewById(R.id.grade_class);

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
        Degrees = DegreeName.getText().toString();
        Year_ofpassings = Year_ofpassing.getText().toString();
        Institution_names = Institution_name.getText().toString();
        Grade_classs = Grade_class.getText().toString();
        responseMap.put("DegreeName", DegreeName.getText().toString());
        responseMap.put("Year_ofpassing", Year_ofpassing.getText().toString());
        responseMap.put("Institution_name", Institution_name.getText().toString());
        responseMap.put("Grade_class", Grade_class.getText().toString());
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
            nDialog = new ProgressDialog(Academic_Details.this);
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

            pDialog = new ProgressDialog(Academic_Details.this);
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

               /* Degree = (EditText)findViewById(R.id.degree);
                Year_ofpassing = (EditText) findViewById(R.id.year_ofpassing);
                Institution_name = (EditText) findViewById(R.id.institution_name);
                Grade_class = (EditText)findViewById(R.id.grade_class);

                Save.setOnClickListener(onConfirmListener(Degree,Year_ofpassing,Institution_name,Grade_class));*/
                //  btnAdd.setOnClickListener(onConfirmListener(name, job, dialog));
                //   btnCancel.setOnClickListener(onCancelListener(dialog));

                //dialog.show();
            }
        };
    }








   /* private View.OnClickListener onConfirmListener(final EditText Degree, final EditText Year_ofpassing,
                                                   final EditText Institution_name,final EditText Grade_class) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Person friend = new Person(Degree.getText().toString().trim(), Year_ofpassing.getText().toString().trim(),
                        Institution_name.getText().toString().trim(), Grade_class.getText().toString().trim());

                Log.d("hi", Degree.getText().toString().trim());
                Log.d("hi", Year_ofpassing.getText().toString().trim());
                Log.d("hi", Institution_name.getText().toString().trim());
                Log.d("hi", Grade_class.getText().toString().trim());
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

    /*public void clear()
    {
        DegreeName.setText("");
        Year_ofpassing.setText("");
        Institution_name.setText("");
        Grade_class.setText("");

    }*/
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




    class AsyncMY_Details extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();
        private ProgressDialog pDialog;
        private static final String LOGIN_URL= "http://cloud.pockethcm.com/api/v2.0/EmployeeDetail";
        private static final String TAG_MESSAGE = "Message";
        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(Academic_Details.this);
            pDialog.setMessage("Please wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject json=null;
            JSONObject response=null;
           /* try {
                HashMap<String, String> params = new HashMap<>();
                params.put("schemaName", args[0]);
                params.put("companyId", args[1]);
                params.put("employeeId", args[2]);
                json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "GET", params);
                response =json.getJSONObject("dictionary");
                if (response != null) {
                    Log.d("AsyncMY_Detailsres", json.toString());
                    return response;
                }

            }    catch (Exception e) {
                e.printStackTrace();
            }
             return response;
            */
            return null;
        }

        protected void onPostExecute(JSONObject json) {
            String message = "";
            //pDialog.dismiss();
       /*     if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (json != null){
            }
            if (TAG_MESSAGE == TAG_MESSAGE) {
                Log.d("Success!", message);
            } else {
                Log.d("Failure", message);
            }*/
        }

    }
}


/*action bar code
*   //getSupportActionBar().setTitle("Academic Details");
       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/
      /*  getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        getSupportActionBar().setCustomView(R.layout.titelbar);*/

      /*  LinearLayout linearLayout=(LinearLayout)findViewById(R.id.layout_main);
        linearLayout.setVisibility(View.GONE);
        LinearLayout linearLayout1=(LinearLayout)findViewById(R.id.layout_main_nominee);
        linearLayout1.setVisibility(View.GONE);*/

     /*   ib = (ImageView) findViewById(R.id.imgSearch);
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        dateDisplay = (EditText) findViewById(R.id.dateofbirth);
        ib.setOnClickListener(this);*/


   /* public void openDialog(final int position) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

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
                               *//* new DelTask().execute(
                                        friendArrayList.get(position).id,
                                        friendArrayList.get(position).make);*//*
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

    }*/
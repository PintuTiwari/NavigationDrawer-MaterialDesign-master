package br.liveo.ndrawer.ui.activity;

import android.animation.AnimatorSet;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionMenu;
import com.google.gson.Gson;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import br.liveo.ndrawer.R;
import br.liveo.ndrawer.adapter.JSONParser;
import br.liveo.ndrawer.adapter.JSONParser1;
import br.liveo.ndrawer.network.ConnectionDetector;

//http://hmkcode.com/android-internet-connection-using-http-get-httpclient/
public class Profile_Activity extends Fragment {

    LinkedHashMap<String, String> map_mydetais = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> map_mydetaismarital = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> map_mydetaismetro = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> map_mydetaisstoppay = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> map_mydetaislocation = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> mapprofile_bloodgroup = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> mapprofile_printchuque = new LinkedHashMap<String, String>();


    LinkedHashMap<String, String> map_personaldetais = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> map_additionaldetais = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> map_academicdetais = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> map_employementhistory = new LinkedHashMap<String, String>();
    public  String SchemaName,userId,CompanyId,profilebloodgroup_string,printchuque_string;
    protected ProgressDialog mProgressDialog;

    private static final String PREF_NAME_PRIFRNCR = "isregisterd";
    String mydetails_string,personaldetais_String,additionaldetails_string,mydetailsmatrial_string,mydetailsmetro_string
            ,mydetailsstoppay_string,mydetailslocation_string,
            employementhistory_string;
    Context context = this.getActivity();
    private RecyclerView recyclerView;
    private RecyclerAdapter adapter;
    private ArrayList<Friend> friendArrayList;
    private FloatingActionButton fab;
    private boolean gender;
    private com.github.clans.fab.FloatingActionButton fab1;
    private com.github.clans.fab.FloatingActionButton fab2;
    private com.github.clans.fab.FloatingActionButton fab3;
    private List<FloatingActionMenu> menus = new ArrayList<>();
    private Handler mUiHandler = new Handler();
    ConnectionDetector cd;

    /*@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = getActivity().getLayoutInflater().inflate(R.layout.activity_main, container, false);
        // clear pref


        friendArrayList = new ArrayList<>();
        recyclerView = (RecyclerView)view. findViewById(R.id.recyle_view);
       // fab = (FloatingActionButton) view.findViewById(R.id.fab);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        setRecyclerViewData(); //adding data to array list
        adapter = new RecyclerAdapter(getActivity(), friendArrayList);

      //  populateTable();
        SharedPreferences setting1 =getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        	SchemaName = setting1.getString("SchemaName", "SchemaName");
        	userId = setting1.getString("userId", "userId");
        	CompanyId = setting1.getString("CompanyId", "CompanyId");

        Log.d("SchemaNamelogin1", SchemaName.toString());
        Log.d("userIdlogin1", userId.toString());
        Log.d("CompanyIdlogin1", CompanyId.toString());



        recyclerView.setAdapter(adapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
                Friend movie = friendArrayList.get(position);
                checkInternetConenction();
                //{


                    switch (position) {
                        case 0:
                            //new AsyncMY_Details().execute(SchemaName, userId, CompanyId);
                            AsyncMY_Details asyncTask2 = new AsyncMY_Details(); // Second
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                asyncTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SchemaName, userId, CompanyId);
                                runMultipleAsyncTask();
                            } else // Below Api Level 13
                            {
                                asyncTask2.execute();
                                runMultipleAsyncTask();
                            }

                            break;
                        case 1:
                            Async_PersonalDetails async_personalDetails = new Async_PersonalDetails(); // Second
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                async_personalDetails.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SchemaName, userId, CompanyId);
                                runMultipleAsyncTaskpersonal();
                            }
                            else // Below Api Level 13
                            {
                                async_personalDetails.execute();
                                runMultipleAsyncTaskpersonal();
                            }
                          //  new Async_PersonalDetails().execute(SchemaName, userId, CompanyId);
                            break;
                        case 2:
                            Async_AdditionalDetails async_additionalDetails = new Async_AdditionalDetails(); // Second
                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                                async_additionalDetails.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SchemaName, userId, CompanyId);
                            } else // Below Api Level 13
                            {
                                async_additionalDetails.execute();
                            }
                           // new Async_AdditionalDetails().execute(SchemaName, userId, CompanyId);
                            break;
                        case 3:
                            new Async_AcademicDetails().execute(SchemaName, userId, CompanyId);
                            break;
                        case 11:
                            new Async_EmploymentHistory().execute(SchemaName, userId, CompanyId);
                            //new HttpAsyncTask().execute(SchemaName, userId, CompanyId);
                            break;
                        case 6:
                            // new Async_EmploymentHistory().execute(SchemaName, userId, CompanyId);
                      /*  Intent intent = new Intent(getActivity(), Nominee_Details.class);
                        startActivity(intent);*/
                            new Async_FamilyDetails().execute(SchemaName, userId, CompanyId);

                            break;


                        case 7:
                            // new Async_EmploymentHistory().execute(SchemaName, userId, CompanyId);
                      /*  Intent intent = new Intent(getActivity(), Nominee_Details.class);
                        startActivity(intent);*/
                            new Async_NomineeDetails().execute(SchemaName, userId, CompanyId);

                            break;

                        case 8:
                            new Async_TrainingDetails().execute(SchemaName, userId, CompanyId);
                      /*  Intent intent1 = new Intent(getActivity(), Training_Details.class);
                        startActivity(intent1);*/
                            break;
                /*    case :
                        new Async_TrainingDetails().execute(SchemaName, userId, CompanyId);
                      *//*  Intent intent1 = new Intent(getActivity(), Training_Details.class);
                        startActivity(intent1);*//*
                        break;*/

                        case 5:
                            new Async_LanguageDetail().execute(SchemaName, userId, CompanyId);
                      /*  Intent intent1 = new Intent(getActivity(), Training_Details.class);
                        startActivity(intent1);*/
                            break;
                        case 4:
                            new Async_EmergencyDetails().execute(SchemaName, userId, CompanyId);
                      /*  Intent intent1 = new Intent(getActivity(), Training_Details.class);
                        startActivity(intent1);*/
                            break;

                        case 9:

                        Intent intent1 = new Intent(getActivity(), Joining_Documents.class);
                        startActivity(intent1);
                            break;

                    }
               // }
                /*else {
                    Toast.makeText(getActivity(), "Network is unavailable!", Toast.LENGTH_LONG).show();
                }*/
            }



            private boolean checkInternetConenction() {
                // get Connectivity Manager object to check connection
                ConnectivityManager connec =(ConnectivityManager)getActivity().getSystemService(getActivity().CONNECTIVITY_SERVICE);

                // Check for network connections
                if ( connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTED ||

                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTING ||
                        connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.CONNECTED ) {
                    Toast.makeText(getActivity(), " Connected ", Toast.LENGTH_LONG).show();
                    return true;
                }else if (
                        connec.getNetworkInfo(0).getState() == android.net.NetworkInfo.State.DISCONNECTED ||
                                connec.getNetworkInfo(1).getState() == android.net.NetworkInfo.State.DISCONNECTED  ) {
                    Toast.makeText(getActivity(), " Not Connected ", Toast.LENGTH_LONG).show();
                    return false;
                }
                return false;
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

            final FloatingActionMenu menu1 = (FloatingActionMenu) view.findViewById(R.id.menu1);
             //  fab.setOnClickListener(onAddingListener());
            ///  fab.setOnClickListener(onAddingListener());
            final com.github.clans.fab.FloatingActionButton programFab1 = new com.github.clans.fab.FloatingActionButton(getActivity());
            programFab1.setButtonSize(com.github.clans.fab.FloatingActionButton.SIZE_MINI);
            programFab1.setLabelText("Programmatically added button");
            programFab1.setImageResource(R.drawable.fab_add);
            menu1.addMenuButton(programFab1);
            programFab1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getActivity(), programFab1.getLabelText(), Toast.LENGTH_SHORT).show();
                }
            });

            menu1.setOnMenuButtonClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (menu1.isOpened()) {
                        Toast.makeText(getActivity(), menu1.getMenuButtonLabelText(), Toast.LENGTH_SHORT).show();
                    }

                    menu1.toggle(true);
                }
            });

            ContextThemeWrapper context = new ContextThemeWrapper(getActivity(), R.style.MenuButtonsStyle);
            com.github.clans.fab.FloatingActionButton programFab2 = new com.github.clans.fab.FloatingActionButton(context);
            programFab2.setLabelText("Programmatically added button");
            programFab2.setImageResource(R.drawable.fab_add);
            menus.add(menu1);
            menu1.hideMenuButton(false);
            int delay = 400;
            for (final FloatingActionMenu menu : menus) {
                mUiHandler.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        menu.showMenuButton(true);
                    }
                }, delay);
                delay += 150;
            }

            menu1.setClosedOnTouchOutside(true);
            fab1 = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fab1);
            fab2 = (com.github.clans.fab.FloatingActionButton)view. findViewById(R.id.fab2);
            fab3 = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.fab3);
            fab1.setEnabled(false);

            fab1.setOnClickListener(clickListener);
            fab2.setOnClickListener(clickListener);
            fab3.setOnClickListener(clickListener);
            return view;
    }


    private void createCustomAnimation() {
        // final FloatingActionMenu menu3 = (FloatingActionMenu) findViewById(R.id.menu3);
        AnimatorSet set = new AnimatorSet();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String text = "";

            switch (v.getId()) {
                case R.id.fab1:
                    text = fab1.getLabelText();
                    break;
                case R.id.fab2:
                    text = fab2.getLabelText();
                    fab2.setVisibility(View.GONE);
                    break;
                case R.id.fab3:
                    text = fab3.getLabelText();
                    fab2.setVisibility(View.VISIBLE);
                    break;
               /* case R.id.fab12:
                    text = fab12.getLabelText();
                    break;
                case R.id.fab22:
                    text = fab22.getLabelText();
                    break;
                case R.id.fab32:
                    text = fab32.getLabelText();
                    break;*/
            }

            Toast.makeText(getActivity(), text, Toast.LENGTH_SHORT).show();
        }
    };

    private void setRecyclerViewData() {
      //  friendArrayList.add(new Friend("Nguyen Tuan", true, "Developer"));
        friendArrayList.add(new Friend("My Details",true));
        friendArrayList.add(new Friend("Personal Details",true));
        friendArrayList.add(new Friend("Additional Details",true));
        friendArrayList.add(new Friend("Academic Details",true));
        friendArrayList.add(new Friend("Emergency Contact",true));
        friendArrayList.add(new Friend("Employee Language",true));
        friendArrayList.add(new Friend("Family Details",true));
        friendArrayList.add(new Friend("Nominee Details",true));
        friendArrayList.add(new Friend("Training Details",true));
        friendArrayList.add(new Friend("Joining Documents",true));
        friendArrayList.add(new Friend("Resume Upload", true));
        friendArrayList.add(new Friend("Employment History", true));
    }


    private void populateTable() {
        mProgressDialog = ProgressDialog.show(getActivity(), "Please wait","Long operation starts...", true);
        new Thread() {
            @Override
            public void run() {

                doLongOperation();
                try {

                    // code runs in a thread
                    ((Activity)context).runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mProgressDialog.dismiss();
                        }
                    });
                } catch (final Exception ex) {
                    Log.i("---","Exception in thread");
                }
            }
        }.start();

    }

    /** fake operation for testing purpose */
    protected void doLongOperation() {
        try { new AsyncMY_Details().execute(SchemaName, userId, CompanyId);
            Thread.sleep(10000);
        } catch (InterruptedException e) {
        }

    }
  /*  class AsyncMY_Details extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();
        String CellType,Fieldname,CellTypekey,Fieldnamekey;
        private ProgressDialog pDialog;
        private static final String LOGIN_URL= "http://cloud.pockethcm.com/api/v2.0/EmployeeDetail";
        private static final String TAG_MESSAGE = "Message";
        //https://cloud.pockethcm.com/api/v2.0/EnumValue?schemaName=Greyt56af30a136&companyId=1&Fieldname=MARITALSTATUS

        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
                @Override
                protected JSONObject doInBackground(String... args) {
                  //  JSONObject json=null;
                    JSONObject  json = null;
                    JSONObject response=null;
                    String employeeConfig=null;
                    HashMap<String, String> map = new HashMap<String, String>();
                    try {
                        HashMap<String, String> params = new HashMap<>();
                        params.put("schemaName", args[0]);
                        params.put("employeeId", args[1]);
                        params.put("CompanyId", args[2]);
                        json = jsonParser.makeHttpRequest(
                                LOGIN_URL, "GET", params);
//                      Log.d("json Response", json.toString());
                        response =json.getJSONObject("dictionary");
                        Log.d(" Response", response.toString());
                        employeeConfig =json.getString("employeeConfig");

                        JSONArray internships = new JSONArray(employeeConfig);
                        //Loop the Array
                        for (int i = 0; i < internships.length(); i++) {
                            Log.e("Message", "loop");

                            JSONObject jsonObject = internships.getJSONObject(i);
                            Log.d("employeeConfig for loop", jsonObject.toString(i));
                            CellType=jsonObject.getString("CellType");
                            Fieldname=jsonObject.getString("FieldName");
                            Log.d("CellType", CellType.toString());
                            Log.d("Fieldname", Fieldname.toString());

                        }

                     *//*   for (Map.Entry<String, String> entry1 : map.entrySet()) {
                             CellTypekey = entry1.getKey();
                             Fieldnamekey = entry1.getValue();
                            Log.d("KeyCellType",CellTypekey.toString());
                            Log.d("KeyFieldname",Fieldnamekey.toString());
                        }*//*
                        if (response != null) {
                            Log.d("AsyncMY_Detailsres", json.toString());

                            return response;
                        }

                    }    catch (Exception e) {
                        e.printStackTrace();
                    }
                    try {
                        Iterator<?> keys = json.keys();
                        while (keys.hasNext()) {
                            String key = (String) keys.next();
                            Log.d("KEYs ", key.toString());
                            String value = response.getString(key);
                            Log.d("Value ", value.toString());
                            map_mydetais.put(key, value);
                            //convert to string using gson
                            Gson gson = new Gson();
                            mydetails_string = gson.toJson(map_mydetais);
                            setPreference(mydetails_string);
                            Log.d("PreferenceMY_Details", mydetails_string.toString());
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    return response;
                }

        protected void onPostExecute(JSONObject json) {

            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
                if (json == null)
                {
                    Toast.makeText(getActivity(),
                            "Please try Again", Toast.LENGTH_SHORT).show();

                    pDialog.dismiss();
                } else {
                    Intent intent = new Intent(getActivity(), MyDetails.class);
                    startActivity(intent);
                   // runMultipleAsyncTask();
                   // new CelltypeMY_Details().execute(SchemaName, CompanyId, "MARITALSTATUS");
                   // new metroMY_Details().execute(SchemaName, CompanyId, "METRO");
                  //  new metroMY_Details().execute(SchemaName, CompanyId, "METRO");
                   // new STOPPAYMENTMY_Details().execute(SchemaName, CompanyId, "STOPPAYMENT");
                  //  new Location_Details().execute(SchemaName, CompanyId, "LOCATION");
                    Log.d("setSchemaName", SchemaName.toString());
                    Log.d("setuserId", userId.toString());
                    Log.d("setCompanyId", CompanyId.toString());


                }

        }
    }*/

    class AsyncMY_Details extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();
        private ProgressDialog pDialog;
        private static final String LOGIN_URL= "http://cloud.pockethcm.com/api/v2.0/EmployeeDetail";
        private static final String TAG_MESSAGE = "Message";
        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject json=null;
            JSONObject response=null;
            try {
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
        }

        protected void onPostExecute(JSONObject json) {
            String message = "";

          /*  if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }*/
            if (json != null) {
               /*  Toast.makeText(getActivity(), json.toString(),
                         Toast.LENGTH_LONG).show();*/
                Log.d("AsyncMY_Details ", json.toString());
                Intent intent = new Intent(getActivity(), MyDetails.class);
                startActivity(intent);
                try {
                    Iterator<?> keys = json.keys();
                    Log.d("AsyncMY_Details_keys", keys.toString());
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Log.d("AsyncMY_Detailskey ", key.toString());
                        String value = json.getString(key);
                        Log.d("AsyncMY_Detailsvalue ", value.toString());
                        map_mydetais.put(key, value);
                        //convert to string using gson
                        Gson gson = new Gson();
                        mydetails_string = gson.toJson(map_mydetais);
                        setPreference(mydetails_string);
                        Log.d("PreferenceMY_Details", mydetails_string.toString());
                    }

                }catch (NullPointerException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Please try again",
                            Toast.LENGTH_LONG).show();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }
            }
            pDialog.dismiss();
            if (TAG_MESSAGE == TAG_MESSAGE) {
                Log.d("Success!", message);
            } else {
                Log.d("Failure", message);
            }
        }

    }
    private void runMultipleAsyncTask() // Run Multiple Async Task
    {

            CelltypeMY_Details asyncTask = new CelltypeMY_Details(); // First
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)// Above Api Level 13
            {
                asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SchemaName, CompanyId, "MARITALSTATUS");
            } else // Below Api Level 13
            {
                asyncTask.execute();
            }
            MetroMY_Details asyncTask2 = new MetroMY_Details(); // Second
            // if(AppUtil.isCurrentVersionHoneycombAndAbove())// Above Api Level 13
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                asyncTask2.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SchemaName, CompanyId, "METRO");
            } else // Below Api Level 13
            {
                asyncTask2.execute();
            }

            STOPPAYMENTMY_Details asyncTask3 = new STOPPAYMENTMY_Details(); // Second
            // if(AppUtil.isCurrentVersionHoneycombAndAbove())// Above Api Level 13
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
                asyncTask3.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SchemaName, CompanyId, "STOPPAYMENT");
            } else // Below Api Level 13
            {
                asyncTask3.execute();
            }

    }

    private void runMultipleAsyncTaskpersonal() // Run Multiple Async Task
    {
        BLOODGRPMY_Details asyncTask = new BLOODGRPMY_Details();
        PRINTCHEQUEProfile printchequeProfile = new PRINTCHEQUEProfile(); // First
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)// Above Api Level 13
        {
            asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SchemaName, CompanyId, "BLOODGRP");
        } else // Below Api Level 13
        {
            asyncTask.execute();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)// Above Api Level 13
        {
            printchequeProfile.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SchemaName, CompanyId, "PRINTCHEQUE");
        } else // Below Api Level 13
        {
            asyncTask.execute();
        }

        /*for (Map.Entry<String, String> entry : map_personaldetais.entrySet())
        {
            String valueStr = entry.getValue();
           String keyStr = entry.getKey();
             Log.d("keyStr map_personaldetais", keyStr.toString());
             Log.d("valueStr map_personaldetais", valueStr.toString());


        switch (entry.getKey()) {

            case "BLOODGRP":
                asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SchemaName, CompanyId, "BLOODGRP");
                break;

            case "PRINTCHEQUE" :
                asyncTask.executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, SchemaName, CompanyId, "PRINTCHEQUE");
                break;

            default:
                System.out.println("You did not enter a valid value.");

        }
        }*/
       // PRINTCHEQUEProfile

    }

    private boolean isNetworkAvailable() {
        ConnectivityManager manager = (ConnectivityManager)
                getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = manager.getActiveNetworkInfo();

        boolean isAvailable = false;
        if (networkInfo != null && networkInfo.isConnected()) {
            isAvailable = true;
        }

        return isAvailable;
    }
    class STOPPAYMENTMY_Details extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();
        private ProgressDialog pDialog;
        private static final String LOGIN_URL= "https://cloud.pockethcm.com/api/v2.0/EnumValue";
        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            Log.d("Call", "CelltypeMY_Details");
        }
        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject json=null;
            JSONObject response=null;
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("schemaName", args[0]);
                params.put("companyId", args[1]);
                params.put("Fieldname", args[2]);
                response = jsonParser.makeHttpRequest(
                        LOGIN_URL, "GET", params);
                if (response != null) {
                    Log.d("marital", response.toString());
                    return response;
                }

            }
            catch (NullPointerException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Please try again",
                        Toast.LENGTH_LONG).show();
            }
            catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        protected void onPostExecute(JSONObject json) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (json != null) {
               /*  Toast.makeText(getActivity(), json.toString(),
                         Toast.LENGTH_LONG).show();*/
                Log.d("AsyncMY_Details ", json.toString());
             /*   Intent intent = new Intent(getActivity(), MyDetails.class);
                startActivity(intent);*/
                try {
                    Iterator<?> keys = json.keys();
                    Log.d("KEYFOR MARRIDE", keys.toString());
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Log.d("KEYFOR_MARRIDE ", key.toString());
                        String value = json.getString(key);
                        Log.d("VALUEFOR_MARRIDE", value.toString());
                        map_mydetaisstoppay.put(key, value);
                        //convert to string using gson
                        Gson gson = new Gson();
                        mydetailsstoppay_string = gson.toJson(map_mydetaisstoppay);
                        setPreference_stoppay(mydetailsstoppay_string);
                        Log.d("mydetailsmetro_string", mydetailsstoppay_string.toString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                pDialog.dismiss();
            }
        }
    }
    class Location_Details extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();
        private static final String LOGIN_URL= "https://cloud.pockethcm.com/api/v2.0/EnumValue";
        @Override
        protected void onPreExecute()
        {

        }
        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject json=null;
            JSONObject response=null;
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("schemaName", args[0]);
                params.put("companyId", args[1]);
                params.put("Fieldname", args[2]);
                response = jsonParser.makeHttpRequest(
                        LOGIN_URL, "GET", params);
                if (response != null) {
                    Log.d("marital", json.toString());
                    return response;
                }

            }    catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(JSONObject json) {

            if (json != null) {
               /*  Toast.makeText(getActivity(), json.toString(),
                         Toast.LENGTH_LONG).show();*/
                Log.d("AsyncMY_Details ", json.toString());
                Intent intent = new Intent(getActivity(), MyDetails.class);
                startActivity(intent);
                try {
                    Iterator<?> keys = json.keys();
                    Log.d("KEYFOR MARRIDE", keys.toString());
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Log.d("KEYFOR_MARRIDE ", key.toString());
                        String value = json.getString(key);
                        Log.d("VALUEFOR_MARRIDE", value.toString());
                        map_mydetaislocation.put(key, value);
                        //convert to string using gson
                        Gson gson = new Gson();
                        mydetailslocation_string = gson.toJson(map_mydetaislocation);
                        setPreference_location(mydetailslocation_string);
                        Log.d("mydetailsmetro_string", mydetailslocation_string.toString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void setPreference_location(String mydetailslocation_string) {
        SharedPreferences setting = this.getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("mydetailslocation_string", mydetailslocation_string).apply();
        Log.d("mydetailslocation", mydetailslocation_string.toString());
        //  editor.clear();
        editor.commit();
    }

    private void setPreference_stoppay(String mydetailsstoppay_string) {
        SharedPreferences setting = this.getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("mydetailsstoppay_string", mydetailsstoppay_string).apply();
        Log.d("mydetailsstoppay_string", mydetailsstoppay_string.toString());
        //  editor.clear();
        editor.commit();
    }

    class BLOODGRPMY_Details extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();
        String CellType;
        private ProgressDialog pDialog;
        private static final String LOGIN_URL= "https://cloud.pockethcm.com/api/v2.0/EnumValue";
        private static final String TAG_MESSAGE = "Message";
        //https://cloud.pockethcm.com/api/v2.0/EnumValue?schemaName=Greyt56af30a136&companyId=1&Fieldname=MARITALSTATUS
        // https://cloud.pockethcm.com/api/v2.0/EnumValue?schemaName=Greyt56af30a136&Fieldname=UrlTwitter&companyId=1
        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            Log.d("BLOODGRPMY_Details", "BLOODGRPMY_Details");
        }
        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject json=null;
            JSONObject response=null;
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("schemaName", args[0]);
                params.put("companyId", args[1]);
                params.put("Fieldname", args[2]);
                response = jsonParser.makeHttpRequest(
                        LOGIN_URL, "GET", params);
                if (response != null) {
                  //  Log.d("BLOODGRP", response.toString());
                    return response;
                }

            }    catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(JSONObject json) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (json != null) {
               /*  Toast.makeText(getActivity(), json.toString(),
                         Toast.LENGTH_LONG).show();*/
               // Log.d("AsyncMY_Details ", json.toString());
               /* Intent intent = new Intent(getActivity(), MyDetails.class);
                startActivity(intent);*/
                  Log.d("BLOODGRP", json.toString());

                try {
                    Iterator<?> keys = json.keys();
                   // Log.d("KEYFOR MARRIDE", keys.toString());
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                      //  Log.d("KEYFOR_MARRIDE ", key.toString());
                        String value = json.getString(key);
                     //   Log.d("VALUEFOR_MARRIDE", value.toString());

                        mapprofile_bloodgroup.put(key, value);
                        //convert to string using gson
                        Gson gson = new Gson();
                        profilebloodgroup_string = gson.toJson(mapprofile_bloodgroup);
                        setPreference_blood(profilebloodgroup_string);
                        //Log.d("mydetailsmetro_string", profilebloodgroup_string.toString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                pDialog.dismiss();
            }
        }

    }



    class PRINTCHEQUEProfile extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();
        String CellType;
        private ProgressDialog pDialog;
        private static final String LOGIN_URL= "https://cloud.pockethcm.com/api/v2.0/EnumValue";
        private static final String TAG_MESSAGE = "Message";
        //https://cloud.pockethcm.com/api/v2.0/EnumValue?schemaName=Greyt56af30a136&companyId=1&Fieldname=MARITALSTATUS
        // https://cloud.pockethcm.com/api/v2.0/EnumValue?schemaName=Greyt56af30a136&Fieldname=UrlTwitter&companyId=1
        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            Log.d("BLOODGRPMY_Details", "BLOODGRPMY_Details");
        }
        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject json=null;
            JSONObject response=null;
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("schemaName", args[0]);
                params.put("companyId", args[1]);
                params.put("Fieldname", args[2]);
                response = jsonParser.makeHttpRequest(
                        LOGIN_URL, "GET", params);
                if (response != null) {
                    //  Log.d("BLOODGRP", response.toString());
                    return response;
                }

            }    catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(JSONObject json) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (json != null) {
               /*  Toast.makeText(getActivity(), json.toString(),
                         Toast.LENGTH_LONG).show();*/
                // Log.d("AsyncMY_Details ", json.toString());
               /* Intent intent = new Intent(getActivity(), MyDetails.class);
                startActivity(intent);*/
                Log.d("BLOODGRP", json.toString());

                try {
                    Iterator<?> keys = json.keys();
                    // Log.d("KEYFOR MARRIDE", keys.toString());
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        //  Log.d("KEYFOR_MARRIDE ", key.toString());
                        String value = json.getString(key);
                        //   Log.d("VALUEFOR_MARRIDE", value.toString());

                        mapprofile_printchuque.put(key, value);
                        //convert to string using gson
                        Gson gson = new Gson();
                        printchuque_string = gson.toJson(mapprofile_printchuque);
                        setPreference_printchuque(printchuque_string);
                        //Log.d("mydetailsmetro_string", profilebloodgroup_string.toString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                pDialog.dismiss();
            }
        }

    }
    private void setPreference_printchuque(String printchuque_string) {
        SharedPreferences setting = this.getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("printchuque_string", printchuque_string).apply();


        // Log.d("mydetailsmetro_string", mydetailsmetro_string.toString());
        //  editor.clear();
        editor.commit();
    }

    class MetroMY_Details extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();
        String CellType;
        private ProgressDialog pDialog;
        private static final String LOGIN_URL= "https://cloud.pockethcm.com/api/v2.0/EnumValue";
        private static final String TAG_MESSAGE = "Message";
        //https://cloud.pockethcm.com/api/v2.0/EnumValue?schemaName=Greyt56af30a136&companyId=1&Fieldname=MARITALSTATUS
        // https://cloud.pockethcm.com/api/v2.0/EnumValue?schemaName=Greyt56af30a136&Fieldname=UrlTwitter&companyId=1
        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            Log.d("Call", "CelltypeMY_Details");
        }
        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject json=null;
            JSONObject response=null;
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("schemaName", args[0]);
                params.put("companyId", args[1]);
                params.put("Fieldname", args[2]);
                response = jsonParser.makeHttpRequest(
                        LOGIN_URL, "GET", params);
                if (response != null) {
                    Log.d("marital", response.toString());
                    return response;
                }

            }    catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(JSONObject json) {
            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (json != null) {
               /*  Toast.makeText(getActivity(), json.toString(),
                         Toast.LENGTH_LONG).show();*/
                Log.d("AsyncMY_Details ", json.toString());
               /* Intent intent = new Intent(getActivity(), MyDetails.class);
                startActivity(intent);*/
                try {
                    Iterator<?> keys = json.keys();
                    Log.d("KEYFOR MARRIDE", keys.toString());
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Log.d("KEYFOR_MARRIDE ", key.toString());
                        String value = json.getString(key);
                        Log.d("VALUEFOR_MARRIDE", value.toString());
                        map_mydetaismetro.put(key, value);
                        //convert to string using gson
                        Gson gson = new Gson();
                        mydetailsmetro_string = gson.toJson(map_mydetaismetro);
                        setPreference_metro(mydetailsmetro_string);
                        Log.d("mydetailsmetro_string", mydetailsmetro_string.toString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                pDialog.dismiss();
            }
        }

    }

    private void setPreference_metro(String mydetailsmetro_string) {
        SharedPreferences setting = this.getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("mydetailsmetro_string", mydetailsmetro_string).apply();


       // Log.d("mydetailsmetro_string", mydetailsmetro_string.toString());
        //  editor.clear();
        editor.commit();
    }

    private void setPreference_blood(String profilebloodgroup_string) {
        SharedPreferences setting = this.getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("profilebloodgroup_string", profilebloodgroup_string).apply();

     //   Log.d("profilebloodgroup_string", profilebloodgroup_string.toString());
        //  editor.clear();
        editor.commit();
    }

    class CelltypeMY_Details extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();
        String CellType;
        private ProgressDialog pDialog;
        private static final String LOGIN_URL= "https://cloud.pockethcm.com/api/v2.0/EnumValue";
        private static final String TAG_MESSAGE = "Message";
        //https://cloud.pockethcm.com/api/v2.0/EnumValue?schemaName=Greyt56af30a136&companyId=1&Fieldname=MARITALSTATUS
       // https://cloud.pockethcm.com/api/v2.0/EnumValue?schemaName=Greyt56af30a136&Fieldname=UrlTwitter&companyId=1
        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
            Log.d("Call", "CelltypeMY_Details");
        }
        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject json=null;
            JSONObject response=null;
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("schemaName", args[0]);
                params.put("companyId", args[1]);
                params.put("Fieldname", args[2]);
                response = jsonParser.makeHttpRequest(
                        LOGIN_URL, "GET", params);
                if (response != null) {
                    Log.d("marital", response.toString());
                    return response;
                }

            }    catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(JSONObject json) {
            String message = "";

          /*  if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }*/
            if (json != null) {
               /*  Toast.makeText(getActivity(), json.toString(),
                         Toast.LENGTH_LONG).show();*/
                Log.d("AsyncMY_Details ", json.toString());
              /*  Intent intent = new Intent(getActivity(), MyDetails.class);
                startActivity(intent);*/
                try {
                    Iterator<?> keys = json.keys();
                    Log.d("KEYFOR MARRIDE", keys.toString());
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Log.d("KEYFOR_MARRIDE ", key.toString());
                        String value = json.getString(key);
                        Log.d("VALUEFOR_MARRIDE", value.toString());
                        map_mydetaismarital.put(key, value);
                        //convert to string using gson
                        Gson gson = new Gson();
                        mydetailsmatrial_string = gson.toJson(map_mydetaismarital);
                        setPreference_Marride(mydetailsmatrial_string);
                        Log.d("setPreference_Marride", mydetailsmatrial_string.toString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
                    pDialog.dismiss();
            }
           /* if (TAG_MESSAGE == TAG_MESSAGE) {
                Log.d("Success!", message);
            } else {
                Log.d("Failure", message);
            }*/
        }

    }

    class Async_PersonalDetails extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();
        String  employeeConfig;
        String CellType,FieldName;


        private ProgressDialog pDialog;
        private static final String LOGIN_URL= "http://cloud.pockethcm.com/api/v2.0/PersonalDetail";
        private static final String TAG_MESSAGE = "Message";
        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected JSONObject doInBackground(String... args)
        {
            JSONObject json=null;
            JSONObject response=null;
            try
            {
                HashMap<String, String> params = new HashMap<>();
                params.put("schemaName", args[0]);
                params.put("employeeId", args[1]);
                params.put("CompanyId", args[2]);
                json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "GET", params);
                response =json.getJSONObject("dictionary");
              //  employeeConfig=json.getString("employeeConfig");
                JSONArray jCastArr = json.getJSONArray("employeeConfig");

                for (int i=0; i < jCastArr.length(); i++) {
                    JSONObject jpersonObj = jCastArr.getJSONObject(i);
                     CellType = jpersonObj.getString("CellType");
                     FieldName =  jpersonObj.getString("FieldName");
                }
                if (response != null)
                {
                    //Log.d("personaldetai_response ", json.toString());
                    return response;
                }
                }catch (Exception e)
                    {
                        e.printStackTrace();
                    }
                    return response;
        }

        protected void onPostExecute(JSONObject json)
        {
            String message = "";
          /*  if (pDialog != null && pDialog.isShowing())
            {
                pDialog.dismiss();
            }*/
            if (json != null)
            {
                Intent intent = new Intent(getActivity(), Personal_Details.class);
                startActivity(intent);
                try
                {
                    Iterator<?> keys = json.keys();
                    // Log.d("personaldetais_keys ", keys.toString());
                    while (keys.hasNext())
                    {
                        String key = (String) keys.next();
                        // Log.d("personaldetais_key ", key.toString());
                        String value = json.getString(key);
                        //  Log.d("personaldetais_value ", value.toString());
                        map_personaldetais.put(key, value);
                        //convert to string using gson
                        Gson gson = new Gson();
                       // String employeeConfig=gson.toJson(employeeConfig);
                        personaldetais_String = gson.toJson(map_personaldetais);
                        personal_setPreference(personaldetais_String, CellType,FieldName);
                       //  Log.d("setPreference_personal", personaldetais_String.toString());
                    }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                pDialog.dismiss();
            }
                if (TAG_MESSAGE == TAG_MESSAGE)
                {
                    Log.d("Success!", message);
                } else
                {
                    Log.d("Failure", message);
                }
        }

    }

    class Async_AdditionalDetails extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();
        private ProgressDialog pDialog;
        private static final String LOGIN_URL= "http://cloud.pockethcm.com/api/v2.0/AdditionalDetail";
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "Message";
        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject json=null;
            JSONObject response=null;
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("schemaName", args[0]);
                params.put("employeeId", args[1]);
                params.put("CompanyId", args[2]);
                json = jsonParser.makeHttpRequest(
                        LOGIN_URL, "GET", params);
                response =json.getJSONObject("dictionary");
                if (response != null) {
                    Log.d("Additional_response ", json.toString());
                    return response;
                }

            }    catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(JSONObject json)
        {
            String message = "";
          /*  if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }*/
            if (json != null) {

                Log.d("Additional_response ", json.toString());
                Intent intent = new Intent(getActivity(), Additionals_Details.class);
                startActivity(intent);
                try {
                    Iterator<?> keys = json.keys();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Log.d("Additional_key ", key.toString());
                        String value = json.getString(key);
                        Log.d("Additional_value ", value.toString());
                        map_additionaldetais.put(key, value);
                        //convert to string using gson
                        Gson gson = new Gson();
                        additionaldetails_string = gson.toJson(map_additionaldetais);
                        Additional_setPreference(additionaldetails_string);
                        Log.d("Preference_Additional", additionaldetails_string.toString());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            pDialog.dismiss();
            if (TAG_MESSAGE == TAG_MESSAGE) {
                Log.d("Success!", message);
            } else {
                Log.d("Failure", message);
            }
        }
    }





    class Async_AcademicDetails extends AsyncTask<String, String, String> {
        // Jsonparser1 jsonParser = new Jsonparser1();
        JSONParser1 jsonParser=new JSONParser1();

        private ProgressDialog pDialog;
        String LOGIN_URL= "http://cloud.pockethcm.com/api/v2.0/AcademicDetail";
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "Message";
        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args) {
            String json=null;
            String response="";
            JSONObject jo=null;
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("schemaName", SchemaName);
                params.put("employeeId", userId);
                params.put("companyId", CompanyId);

                response = jsonParser.makeHttpRequest(
                        LOGIN_URL, "GET", params);
                //makeGetRequest();
                Log.d("response ", response.toString());

                try {
                    JSONArray internships = new JSONArray(response);
                    JSONObject leagueObject = internships.getJSONObject(0);
                    //Loop the Array
                    for(int i=0;i < leagueObject.length();i++) {
                        Log.e("Message","loop");
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject e = internships.getJSONObject(i);
                        Log.d("EmployementHi_response ", e.toString());
                        map.put("id", String.valueOf("id"));
                    }

                } catch(JSONException e) {
                    Log.e("log_tag", "Error parsing data "+e.toString());
                }


//                response =json.getJSONArray("dictionary");
                 /*jo = new JSONObject();
                JSONArray ja = new JSONArray();

                jo.put("arrayName",response);*/


            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String response) {
            String message = "";

          /*  if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }*/
            if (response != null) {
               /* Toast.makeText(getActivity(), response.toString(),
                        Toast.LENGTH_LONG).show();*/
                Log.d("EmployementHi_response ", response.toString());
                Intent intent = new Intent(getActivity(), Academic_Details.class);
                startActivity(intent);
                try {
                    // JSONArray internships = new JSONArray(json);
                    //  JSONObject leagueObject = internships.getJSONObject(0);
                   /* Iterator<?> keys = leagueObject.keys();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Log.d("EmployementHi_key ", key.toString());
                        String value = leagueObject.getString(key);
                        Log.d("EmployementHi_value ", value.toString());
                        map_employementhistory.put(key, value);
                        //convert to string using gson
                        Gson gson = new Gson();
                        employementhistory_string = gson.toJson(map_employementhistory);
                        Employement_setPreference(employementhistory_string);
                        Log.d("Preference_EmployementH", employementhistory_string.toString());
                    }*/
                   /* for(int i=0;i < leagueObject.length();i++) {

                        JSONObject e = internships.getJSONObject(i);
                        Gson gson = new Gson();
                        employementhistory_string = gson.toJson(e);*/
                    // Gson gson = new Gson();
                    //  employementhistory_string = gson.toJson(leagueObject);
                    Academic_setPreference(response);
                    Log.d("Employement_setPrefer", response.toString());

                    // }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            pDialog.dismiss();
            if (TAG_MESSAGE == TAG_MESSAGE) {
                Log.d("Success!", message);
            } else {
                Log.d("Failure", message);
            }
        }

    }

    public String makeHttpRequest(String url, String method,
                                      HashMap<String, String> params) {
        String charset = "UTF-8";
        HttpURLConnection conn;
        DataOutputStream wr;
        URL urlObj;
        InputStream inputStream = null;
        String result = "";
        StringBuilder sbParams;
        String paramsString;
         InputStream is = null;
        sbParams = new StringBuilder();
        int i = 0;
        for (String key : params.keySet()) {
            try {
                if (i != 0) {
                    sbParams.append("&");
                }
                sbParams.append(key).append("=")
                        .append(URLEncoder.encode(params.get(key), charset));

            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            i++;
        }

        if (method.equals("POST")) {
            // request method is POST
            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(true);

                conn.setRequestMethod("POST");

                conn.setRequestProperty("Accept-Charset", charset);

                conn.setReadTimeout(10000);
                conn.setConnectTimeout(15000);

                conn.connect();

                paramsString = sbParams.toString();

                wr = new DataOutputStream(conn.getOutputStream());
                wr.writeBytes(paramsString);
                wr.flush();
                wr.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (method.equals("GET")) {
            // request method is GET

            if (sbParams.length() != 0) {
                url += "?" + sbParams.toString();
            }

            try {
                urlObj = new URL(url);

                conn = (HttpURLConnection) urlObj.openConnection();

                conn.setDoOutput(false);

                conn.setRequestMethod("GET");

                conn.setRequestProperty("Accept-Charset", charset);

                conn.setConnectTimeout(15000);

                conn.connect();

            } catch (IOException e) {
                e.printStackTrace();
            }

        }
       /* try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(is, "iso-8859-1"), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line + "\n");
            }
            //  Log.d("Call  BufferedReader function","Call  BufferedReader function");
            is.close();
            json = sb.toString();
            Log.e("JSON", json);
        } catch (Exception e) {
            Log.e("Buffer Error", "Error converting result " + e.toString());
        }

        try {
            //jArr = new JSONArray(json);
            jObj = new JSONObject(json);


        }catch (JSONException e) {
            Log.e("JSON Parser", "Error parsing data " + e.toString());
        }

        // return JSON String
        return jObj;*/
        try {
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }
   /* public static String GET(String url, String method,
                             HashMap<String, String> params ){
        InputStream inputStream = null;
        String result = "";
        try {

            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();

            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpGet(url));

            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();

            // convert inputstream to string
            if(inputStream != null)
                result = convertInputStreamToString(inputStream);
            else
                result = "Did not work!";

        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }

        return result;
    }*/
    private static String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;

        inputStream.close();
        return result;

    }
    private class HttpAsyncTask extends AsyncTask<String, String, String> {
       String LOGIN_URL= "http://cloud.pockethcm.com/api/v2.0/EmployementHistory";
        @Override
        protected String doInBackground(String... args) {
            HashMap<String, String> params = new HashMap<>();
            params.put("schemaName", SchemaName);
            params.put("employeeId", userId);
            params.put("companyId", CompanyId);



            return makeHttpRequest(LOGIN_URL, "GET", params);
        }
        // onPostExecute displays the results of the AsyncTask.
        @Override
        protected void onPostExecute(String result) {
            Toast.makeText(getActivity(), "Received!", Toast.LENGTH_LONG).show();
           // etResponse.setText(result);
            Log.d("Received",result.toString());

        }
    }
    class Async_EmploymentHistory extends AsyncTask<String, String, String> {
       // Jsonparser1 jsonParser = new Jsonparser1();
        JSONParser1 jsonParser=new JSONParser1();

        private ProgressDialog pDialog;
        String LOGIN_URL= "http://cloud.pockethcm.com/api/v2.0/EmployementHistory";
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "Message";
        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Pleasse Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args) {
            String json=null;
            String response="";
            JSONObject jo=null;
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("schemaName", SchemaName);
                params.put("employeeId", userId);
                params.put("companyId", CompanyId);

               response = jsonParser.makeHttpRequest(
                        LOGIN_URL, "GET", params);
                //makeGetRequest();
               Log.d("response ", response.toString());

                try {
                    JSONArray internships = new JSONArray(response);
                    JSONObject leagueObject = internships.getJSONObject(0);
                    //Loop the Array
                    for(int i=0;i < leagueObject.length();i++) {
                        Log.e("Message","loop");
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject e = internships.getJSONObject(i);
                        Log.d("EmployementHi_response ", e.toString());
                        map.put("id", String.valueOf("id"));
                    }

                } catch(JSONException e) {
                    Log.e("log_tag", "Error parsing data "+e.toString());
                }


//                response =json.getJSONArray("dictionary");
                 /*jo = new JSONObject();
                JSONArray ja = new JSONArray();

                jo.put("arrayName",response);*/


            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String response) {
          String message = "";

            /*if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }*/
             if (response != null) {
               /* Toast.makeText(getActivity(), response.toString(),
                        Toast.LENGTH_LONG).show();*/
                Log.d("EmployementHi_response ", response.toString());
                Intent intent = new Intent(getActivity(), Employment_History.class);
                startActivity(intent);
                try {
                  // JSONArray internships = new JSONArray(json);
                  //  JSONObject leagueObject = internships.getJSONObject(0);
                   /* Iterator<?> keys = leagueObject.keys();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Log.d("EmployementHi_key ", key.toString());
                        String value = leagueObject.getString(key);
                        Log.d("EmployementHi_value ", value.toString());
                        map_employementhistory.put(key, value);
                        //convert to string using gson
                        Gson gson = new Gson();
                        employementhistory_string = gson.toJson(map_employementhistory);
                        Employement_setPreference(employementhistory_string);
                        Log.d("Preference_EmployementH", employementhistory_string.toString());
                    }*/
                   /* for(int i=0;i < leagueObject.length();i++) {

                        JSONObject e = internships.getJSONObject(i);
                        Gson gson = new Gson();
                        employementhistory_string = gson.toJson(e);*/
                       // Gson gson = new Gson();
                      //  employementhistory_string = gson.toJson(leagueObject);
                        Employement_setPreference(response);
                        Log.d("Employement_setPrefer", response.toString());

                   // }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            pDialog.dismiss();
            if (TAG_MESSAGE == TAG_MESSAGE) {
                Log.d("Success!", message);
            } else {
                Log.d("Failure", message);
            }
        }

    }


    class Async_NomineeDetails extends AsyncTask<String, String, String> {
        // Jsonparser1 jsonParser = new Jsonparser1();
        JSONParser1 jsonParser=new JSONParser1();

        private ProgressDialog pDialog;
        String LOGIN_URL= "http://cloud.pockethcm.com/api/v2.0/NomineeDetail";
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "Message";
        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args) {
            String json=null;
            String response="";
            JSONObject jo=null;
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("schemaName", SchemaName);
                params.put("employeeId", userId);
                params.put("companyId", CompanyId);



                response = jsonParser.makeHttpRequest(
                        LOGIN_URL, "GET", params);
                //makeGetRequest();
                Log.d("response ", response.toString());

                try {
                    JSONArray internships = new JSONArray(response);
                    JSONObject leagueObject = internships.getJSONObject(0);
                    //Loop the Array
                    for(int i=0;i < leagueObject.length();i++) {
                        Log.e("Message","loop");
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject e = internships.getJSONObject(i);
                        Log.d("EmployementHi_response ", e.toString());
                        map.put("id", String.valueOf("id"));
                    }

                } catch(JSONException e) {
                    Log.e("log_tag", "Error parsing data "+e.toString());
                }


//                response =json.getJSONArray("dictionary");
                 /*jo = new JSONObject();
                JSONArray ja = new JSONArray();

                jo.put("arrayName",response);*/


            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String response) {
            String message = "";

            /*if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }*/
            if (response != null) {
               /* Toast.makeText(getActivity(), response.toString(),
                        Toast.LENGTH_LONG).show();*/
                Log.d("EmployementHi_response ", response.toString());
                Intent intent = new Intent(getActivity(), Nominee_Details.class);
                startActivity(intent);
                try {
                    // JSONArray internships = new JSONArray(json);
                    //  JSONObject leagueObject = internships.getJSONObject(0);
                   /* Iterator<?> keys = leagueObject.keys();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Log.d("EmployementHi_key ", key.toString());
                        String value = leagueObject.getString(key);
                        Log.d("EmployementHi_value ", value.toString());
                        map_employementhistory.put(key, value);
                        //convert to string using gson
                        Gson gson = new Gson();
                        employementhistory_string = gson.toJson(map_employementhistory);
                        Employement_setPreference(employementhistory_string);
                        Log.d("Preference_EmployementH", employementhistory_string.toString());
                    }*/
                   /* for(int i=0;i < leagueObject.length();i++) {

                        JSONObject e = internships.getJSONObject(i);
                        Gson gson = new Gson();
                        employementhistory_string = gson.toJson(e);*/
                    // Gson gson = new Gson();
                    //  employementhistory_string = gson.toJson(leagueObject);
                    NomineeDetails_setPreference(response);
                    Log.d("Employement_setPrefer", response.toString());

                    // }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            pDialog.dismiss();
            if (TAG_MESSAGE == TAG_MESSAGE) {
                Log.d("Success!", message);
            } else {
                Log.d("Failure", message);
            }
        }

    }

    private void NomineeDetails_setPreference(String response) {
        /*SharedPreferences setting = context.getSharedPreferences(
                PREF_NAME_PRIFRNCR, 0);*/
        SharedPreferences setting = this.getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("response", response).apply();
        Log.d("json", response.toString());
        //  editor.clear();
        editor.commit();
    }
    private void setPreference_Marride(String mydetailsmatrial_string) {
        /*SharedPreferences setting = context.getSharedPreferences(
                PREF_NAME_PRIFRNCR, 0);*/
        SharedPreferences setting = this.getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("mydetailsmatrial_string", mydetailsmatrial_string).apply();
        Log.d("mydetailsmatrial_string", mydetailsmatrial_string.toString());
        //  editor.clear();
        editor.commit();
    }


    class Async_FamilyDetails extends AsyncTask<String, String, String> {
        // Jsonparser1 jsonParser = new Jsonparser1();
        JSONParser1 jsonParser=new JSONParser1();

        private ProgressDialog pDialog;
        String LOGIN_URL= "http://cloud.pockethcm.com/api/v2.0/FamilyDetail";
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "Message";
        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args) {
            String json=null;
            String response="";
            JSONObject jo=null;
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("schemaName", SchemaName);
                params.put("employeeId", userId);
                params.put("companyId", CompanyId);



                response = jsonParser.makeHttpRequest(
                        LOGIN_URL, "GET", params);
                //makeGetRequest();
                Log.d("response ", response.toString());

                try {
                    JSONArray internships = new JSONArray(response);
                    JSONObject leagueObject = internships.getJSONObject(0);
                    //Loop the Array
                    for(int i=0;i < leagueObject.length();i++) {
                        Log.e("Message","loop");
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject e = internships.getJSONObject(i);
                        Log.d("FamilyDetail_response ", e.toString());
                        map.put("id", String.valueOf("id"));
                    }

                } catch(JSONException e) {
                    Log.e("log_tag", "Error parsing data "+e.toString());
                }


//                response =json.getJSONArray("dictionary");
                 /*jo = new JSONObject();
                JSONArray ja = new JSONArray();

                jo.put("arrayName",response);*/


            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String response) {
            String message = "";

         /*   if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }*/
            if (response != null) {
               /* Toast.makeText(getActivity(), response.toString(),
                        Toast.LENGTH_LONG).show();*/
                Log.d("FamilyDetail_response ", response.toString());
                Intent intent = new Intent(getActivity(), Family_Details.class);
                startActivity(intent);
                try {
                    // JSONArray internships = new JSONArray(json);
                    //  JSONObject leagueObject = internships.getJSONObject(0);
                   /* Iterator<?> keys = leagueObject.keys();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Log.d("EmployementHi_key ", key.toString());
                        String value = leagueObject.getString(key);
                        Log.d("EmployementHi_value ", value.toString());
                        map_employementhistory.put(key, value);
                        //convert to string using gson
                        Gson gson = new Gson();
                        employementhistory_string = gson.toJson(map_employementhistory);
                        Employement_setPreference(employementhistory_string);
                        Log.d("Preference_EmployementH", employementhistory_string.toString());
                    }*/
                   /* for(int i=0;i < leagueObject.length();i++) {

                        JSONObject e = internships.getJSONObject(i);
                        Gson gson = new Gson();
                        employementhistory_string = gson.toJson(e);*/
                    // Gson gson = new Gson();
                    //  employementhistory_string = gson.toJson(leagueObject);
                    FamilyDetail_setPreference(response);
                    Log.d("Employement_setPrefer", response.toString());

                    // }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            pDialog.dismiss();
            if (TAG_MESSAGE == TAG_MESSAGE) {
                Log.d("Success!", message);
            } else {
                Log.d("Failure", message);
            }
        }

    }

    private void FamilyDetail_setPreference(String response) {
        /*SharedPreferences setting = context.getSharedPreferences(
                PREF_NAME_PRIFRNCR, 0);*/
        SharedPreferences setting = this.getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("response", response).apply();
        Log.d("json", response.toString());
        //  editor.clear();
        editor.commit();
    }

    class Async_TrainingDetails extends AsyncTask<String, String, String> {
        // Jsonparser1 jsonParser = new Jsonparser1();
        JSONParser1 jsonParser=new JSONParser1();

        private ProgressDialog pDialog;
        String LOGIN_URL= "http://cloud.pockethcm.com/api/v2.0/TrainingDetail";
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "Message";
        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args) {
            String json=null;
            String response="";
            JSONObject jo=null;
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("schemaName", SchemaName);
                params.put("employeeId", userId);
                params.put("companyId", CompanyId);



                response = jsonParser.makeHttpRequest(
                        LOGIN_URL, "GET", params);
                //makeGetRequest();
                Log.d("response ", response.toString());

                try {
                    JSONArray internships = new JSONArray(response);
                    JSONObject leagueObject = internships.getJSONObject(0);
                    //Loop the Array
                    for(int i=0;i < leagueObject.length();i++) {
                        Log.e("Message","loop");
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject e = internships.getJSONObject(i);
                        Log.d("Training_response ", e.toString());
                        map.put("id", String.valueOf("id"));
                    }

                } catch(JSONException e) {
                    Log.e("log_tag", "Error parsing data "+e.toString());
                }


//                response =json.getJSONArray("dictionary");
                 /*jo = new JSONObject();
                JSONArray ja = new JSONArray();

                jo.put("arrayName",response);*/


            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String response) {
            String message = "";

          /*  if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }*/
            if (response != null) {
               /* Toast.makeText(getActivity(), response.toString(),
                        Toast.LENGTH_LONG).show();*/
                Log.d("Training_response ", response.toString());
                Intent intent = new Intent(getActivity(), Training_Details.class);
                startActivity(intent);
                try {
                    // JSONArray internships = new JSONArray(json);
                    //  JSONObject leagueObject = internships.getJSONObject(0);
                   /* Iterator<?> keys = leagueObject.keys();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Log.d("EmployementHi_key ", key.toString());
                        String value = leagueObject.getString(key);
                        Log.d("EmployementHi_value ", value.toString());
                        map_employementhistory.put(key, value);
                        //convert to string using gson
                        Gson gson = new Gson();
                        employementhistory_string = gson.toJson(map_employementhistory);
                        Employement_setPreference(employementhistory_string);
                        Log.d("Preference_EmployementH", employementhistory_string.toString());
                    }*/
                   /* for(int i=0;i < leagueObject.length();i++) {

                        JSONObject e = internships.getJSONObject(i);
                        Gson gson = new Gson();
                        employementhistory_string = gson.toJson(e);*/
                    // Gson gson = new Gson();
                    //  employementhistory_string = gson.toJson(leagueObject);
                    TrainingDetails_setPreference(response);
                    Log.d("Training_setPrefer", response.toString());

                    // }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            pDialog.dismiss();
            if (TAG_MESSAGE == TAG_MESSAGE) {
                Log.d("Success!", message);
            } else {
                Log.d("Failure", message);
            }
        }

    }

    private void TrainingDetails_setPreference(String response) {
        /*SharedPreferences setting = context.getSharedPreferences(
                PREF_NAME_PRIFRNCR, 0);*/
        SharedPreferences setting = this.getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("response", response).apply();
        Log.d("Training_response", response.toString());
        //  editor.clear();
        editor.commit();
    }

    class Async_LanguageDetail extends AsyncTask<String, String, String> {
        // Jsonparser1 jsonParser = new Jsonparser1();
        JSONParser1 jsonParser=new JSONParser1();

        private ProgressDialog pDialog;
        String LOGIN_URL= "http://cloud.pockethcm.com/api/v2.0/LanguageDetail";
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "Message";
        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args) {
            String json=null;
            String response="";
            JSONObject jo=null;
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("schemaName", SchemaName);
                params.put("employeeId", userId);
                params.put("companyId", CompanyId);



                response = jsonParser.makeHttpRequest(
                        LOGIN_URL, "GET", params);
                //makeGetRequest();
                Log.d("Async_LanguageDetail ", response.toString());

                try {
                    JSONArray internships = new JSONArray(response);
                    JSONObject leagueObject = internships.getJSONObject(0);
                    //Loop the Array
                    for(int i=0;i < leagueObject.length();i++) {
                        Log.e("Message","loop");
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject e = internships.getJSONObject(i);
                        Log.d("Async_LanguageDetail ", e.toString());
                        map.put("id", String.valueOf("id"));
                    }

                } catch(JSONException e) {
                    Log.e("log_tag", "Error parsing data "+e.toString());
                }


//                response =json.getJSONArray("dictionary");
                 /*jo = new JSONObject();
                JSONArray ja = new JSONArray();

                jo.put("arrayName",response);*/


            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String response) {
            String message = "";

           /* if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }*/
            if (response != null) {
               /* Toast.makeText(getActivity(), response.toString(),
                        Toast.LENGTH_LONG).show();*/
                Log.d("Training_response ", response.toString());
                Intent intent = new Intent(getActivity(), Employee_Language.class);
                startActivity(intent);
                try {
                    // JSONArray internships = new JSONArray(json);
                    //  JSONObject leagueObject = internships.getJSONObject(0);
                   /* Iterator<?> keys = leagueObject.keys();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Log.d("EmployementHi_key ", key.toString());
                        String value = leagueObject.getString(key);
                        Log.d("EmployementHi_value ", value.toString());
                        map_employementhistory.put(key, value);
                        //convert to string using gson
                        Gson gson = new Gson();
                        employementhistory_string = gson.toJson(map_employementhistory);
                        Employement_setPreference(employementhistory_string);
                        Log.d("Preference_EmployementH", employementhistory_string.toString());
                    }*/
                   /* for(int i=0;i < leagueObject.length();i++) {

                        JSONObject e = internships.getJSONObject(i);
                        Gson gson = new Gson();
                        employementhistory_string = gson.toJson(e);*/
                    // Gson gson = new Gson();
                    //  employementhistory_string = gson.toJson(leagueObject);
                    LanguageDetail_setPreference(response);
                    Log.d("Training_setPrefer", response.toString());

                    // }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            pDialog.dismiss();
            if (TAG_MESSAGE == TAG_MESSAGE) {
                Log.d("Success!", message);
            } else {
                Log.d("Failure", message);
            }
        }

    }

    class Async_EmergencyDetails extends AsyncTask<String, String, String> {
        // Jsonparser1 jsonParser = new Jsonparser1();
        JSONParser1 jsonParser=new JSONParser1();

        private ProgressDialog pDialog;
        String LOGIN_URL= "http://cloud.pockethcm.com/api/v2.0/EmergencyContachDetail";
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "Message";
        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(getActivity());
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected String doInBackground(String... args) {
            String json=null;
            String response="";
            JSONObject jo=null;
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("schemaName", SchemaName);
                params.put("companyId", CompanyId);
                params.put("employeeId", userId);
                response = jsonParser.makeHttpRequest(
                        LOGIN_URL, "GET", params);
                //makeGetRequest();
                Log.d("Async_EmergencyDetails ", response.toString());

                try {
                    JSONArray internships = new JSONArray(response);
                    JSONObject leagueObject = internships.getJSONObject(0);
                    //Loop the Array
                    for(int i=0;i < leagueObject.length();i++) {
                        Log.e("Message","loop");
                        HashMap<String, String> map = new HashMap<String, String>();
                        JSONObject e = internships.getJSONObject(i);
                        Log.d("Async_EmergencyDetails ", e.toString());
                        map.put("id", String.valueOf("id"));
                    }

                } catch(JSONException e) {
                    Log.e("log_tag", "Error parsing data "+e.toString());
                }


//                response =json.getJSONArray("dictionary");
                 /*jo = new JSONObject();
                JSONArray ja = new JSONArray();

                jo.put("arrayName",response);*/


            } catch (Exception e) {
                Log.e("log_tag", "Error parsing data "+e.toString());
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(String response) {
            String message = "";

           /* if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }*/
            if (response != null) {
               /* Toast.makeText(getActivity(), response.toString(),
                        Toast.LENGTH_LONG).show();*/
                Log.d("EmergencyDeta_response ", response.toString());
                Intent intent = new Intent(getActivity(), Emergency_Contact.class);
                startActivity(intent);
                try {
                    // JSONArray internships = new JSONArray(json);
                    //  JSONObject leagueObject = internships.getJSONObject(0);
                   /* Iterator<?> keys = leagueObject.keys();
                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Log.d("EmployementHi_key ", key.toString());
                        String value = leagueObject.getString(key);
                        Log.d("EmployementHi_value ", value.toString());
                        map_employementhistory.put(key, value);
                        //convert to string using gson
                        Gson gson = new Gson();
                        employementhistory_string = gson.toJson(map_employementhistory);
                        Employement_setPreference(employementhistory_string);
                        Log.d("Preference_EmployementH", employementhistory_string.toString());
                    }*/
                   /* for(int i=0;i < leagueObject.length();i++) {

                        JSONObject e = internships.getJSONObject(i);
                        Gson gson = new Gson();
                        employementhistory_string = gson.toJson(e);*/
                    // Gson gson = new Gson();
                    //  employementhistory_string = gson.toJson(leagueObject);
                    EmergencyDetails_setPreference(response);
                    Log.d("EmergencyDeta_setPrefer", response.toString());

                    // }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            pDialog.dismiss();
            if (TAG_MESSAGE == TAG_MESSAGE) {
                Log.d("Success!", message);
            } else {
                Log.d("Failure", message);
            }
        }

    }
    private void EmergencyDetails_setPreference(String response) {
        /*SharedPreferences setting = context.getSharedPreferences(
                PREF_NAME_PRIFRNCR, 0);*/
        SharedPreferences setting = this.getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("response", response).apply();
        Log.d("Training_response", response.toString());
        //  editor.clear();
        editor.commit();
    }

    private void LanguageDetail_setPreference(String response) {
        /*SharedPreferences setting = context.getSharedPreferences(
                PREF_NAME_PRIFRNCR, 0);*/
        SharedPreferences setting = this.getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("response", response).apply();
        Log.d("Training_response", response.toString());
        //  editor.clear();
        editor.commit();
    }



    private void Employement_setPreference(String response) {
        /*SharedPreferences setting = context.getSharedPreferences(
                PREF_NAME_PRIFRNCR, 0);*/
        SharedPreferences setting = this.getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("response", response).apply();
        Log.d("json", response.toString());
        //  editor.clear();
        editor.commit();
    }

    private void Academic_setPreference(String response) {
        /*SharedPreferences setting = context.getSharedPreferences(
                PREF_NAME_PRIFRNCR, 0);*/
        SharedPreferences setting = this.getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("response", response).apply();
        Log.d("additionaldetails", response.toString());
        //  editor.clear();
        editor.commit();
    }

    private void Additional_setPreference(String additionaldetails_string) {
        /*SharedPreferences setting = context.getSharedPreferences(
                PREF_NAME_PRIFRNCR, 0);*/
        SharedPreferences setting = this.getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("additionaldetails_string", additionaldetails_string).apply();
        Log.d("additionaldetails", additionaldetails_string.toString());
        //  editor.clear();
        editor.commit();
    }

    private void personal_setPreference(String personaldetais_String,String CellType,String FieldName) {
        /*SharedPreferences setting = context.getSharedPreferences(
                PREF_NAME_PRIFRNCR, 0);*/
        SharedPreferences setting = this.getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("personaldetais_String", personaldetais_String).apply();
        editor.putString("CellType", CellType).apply();
        editor.putString("FieldName", FieldName).apply();

       // Log.d("personaldetais_String", personaldetais_String.toString());
        //  editor.clear();
        editor.commit();
    }
    private void setPreference(String mydetails_string) {
        /*SharedPreferences setting = context.getSharedPreferences(
                PREF_NAME_PRIFRNCR, 0);*/
        SharedPreferences setting = this.getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("mydetails_string", mydetails_string).apply();
        Log.d("mydetails_string", mydetails_string.toString());
      //  editor.clear();
        editor.commit();
    }
    public interface ClickListener {
        void onClick(View view, int position);
        void onLongClick(View view, int position);
    }
    public static class RecyclerTouchListener implements RecyclerView.OnItemTouchListener
    {
        private GestureDetector gestureDetector;
        private Profile_Activity.ClickListener clickListener;

        public RecyclerTouchListener(Context context, final RecyclerView recyclerView, final Profile_Activity.ClickListener clickListener) {
            this.clickListener = clickListener;
            gestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    return true;
                }

                @Override
                public void onLongPress(MotionEvent e) {
                    View child = recyclerView.findChildViewUnder(e.getX(), e.getY());
                    if (child != null && clickListener != null) {
                        clickListener.onLongClick(child, recyclerView.getChildPosition(child));
                    }
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {

            View child = rv.findChildViewUnder(e.getX(), e.getY());
            if (child != null && clickListener != null && gestureDetector.onTouchEvent(e)) {
                clickListener.onClick(child, rv.getChildPosition(child));
            }
            return false;
        }
        @Override
        public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        }
        @Override
        public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {

        }
    }

}
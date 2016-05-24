package br.liveo.ndrawer.ui.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.AppCompatButton;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import br.liveo.ndrawer.R;
import br.liveo.ndrawer.adapter.ConstantClass;
/*
"dictionary":{"PERSONALEMAIL":"deshmukh.abc2@gmail.com","REMARKS":"remarks","BLOODGRP":"A-","ADDRESS1":"chembur","ADDRESS2":"chembur","PLACE":"Chembur",
"CITY":"Mumbai","PINCODE":"421004","EMAIL":"deshmukh.abc2@gmail.com","PRINTCHEQUE":"Yes","BANKACCNO":"123456","BANKBRANCHCODE":"1234569","PERSONALMOBILENO":
"98989898989","OFFICEEXTNO":"889"},

"employeeConfig":[{"Id":23,"CompanyId":1,"FieldName":"PERSONALEMAIL","FieldType":"TEXT","FieldSize":100,"Labelname":
"Personal Email Id","Validate":"N","TransField":"N","DField":"Y","UNICHK":"N","CellType":"N","Rights":null,"Filter":false,"Order":22,"Panel":2,"Type":2},

{"Id":24,"CompanyId":1,"FieldName":"REMARKS","FieldType":"TEXT","FieldSize":100,"Labelname":"Remarks","Validate":"N","TransField":"N","DField":"Y","UNICHK":"N","CellType":"N","Rights":null,"Filter":false,"Order":23,"Panel":2,"Type":2},
{"Id":27,"CompanyId":1,"FieldName":"BLOODGRP","FieldType":"TEXT","FieldSize":5,"Labelname":"Blood group","Validate":"N","TransField":"N","DField":"Y","UNICHK":"N","CellType":"E","Rights":null,"Filter":true,"Order":26,"Panel":2,"Type":2},
{"Id":31,"CompanyId":1,"FieldName":"ADDRESS1","FieldType":"TEXT","FieldSize":50,"Labelname":"Address 1","Validate":"N","TransField":"N","DField":"Y","UNICHK":"N","CellType":"N","Rights":null,"Filter":false,"Order":26,"Panel":2,"Type":2},
{"Id":29,"CompanyId":1,"FieldName":"ADDRESS2","FieldType":"TEXT","FieldSize":50,"Labelname":"Address 2","Validate":"N","TransField":"N","DField":"Y","UNICHK":"N","CellType":"N","Rights":null,"Filter":false,"Order":27,"Panel":2,"Type":2},
{"Id":30,"CompanyId":1,"FieldName":"PLACE","FieldType":"TEXT","FieldSize":30,"Labelname":"Place","Validate":"N","TransField":"N","DField":"Y","UNICHK":"N","CellType":"N","Rights":null,"Filter":false,"Order":28,"Panel":2,"Type":2},
{"Id":28,"CompanyId":1,"FieldName":"CITY","FieldType":"TEXT","FieldSize":30,"Labelname":"City","Validate":"N","TransField":"N","DField":"Y","UNICHK":"N","CellType":"N","Rights":null,"Filter":false,"Order":29,"Panel":2,"Type":2},
{"Id":26,"CompanyId":1,"FieldName":"PINCODE","FieldType":"TEXT","FieldSize":7,"Labelname":"PIN Code","Validate":"N","TransField":"N","DField":"Y","UNICHK":"N","CellType":"N","Rights":null,"Filter":false,"Order":30,"Panel":2,"Type":2},
{"Id":38,"CompanyId":1,"FieldName":"EMAIL","FieldType":"TEXT","FieldSize":100,"Labelname":"Email Id","Validate":"N","TransField":"N","DField":"Y","UNICHK":"N","CellType":"N","Rights":null,"Filter":true,"Order":37,"Panel":2,"Type":2},
{"Id":41,"CompanyId":1,"FieldName":"PRINTCHEQUE","FieldType":"TEXT","FieldSize":1,"Labelname":"Print Cheque","Validate":"N","TransField":"N","DField":"Y","UNICHK":"N","CellType":"E","Rights":null,"Filter":false,"Order":40,"Panel":2,"Type":2},
{"Id":44,"CompanyId":1,"FieldName":"BANKACCNO","FieldType":"TEXT","FieldSize":30,"Labelname":"Bank Account Number","Validate":"N","TransField":"N","DField":"Y","UN
*/
/**
 * Created by rupa.dwivedi on 21-01-2016.
 */
public class Personal_Details extends ActionBarActivity {
    //implements View.OnClickListener {
    private com.github.clans.fab.FloatingActionButton mFab;
    ScrollView scrollView;
    LinearLayout linearLayout;
    Spinner Blood_group,Print_Cheque,METRO_NONMETRO_TDS,Stop_Payment,Location,Grade,PROFTAX_LOCATION;
    public  String SchemaName,userId,CompanyId,Fieldvalue;

    String keyStr;
    AppCompatButton appCompatButton;
    Button showdata;
    private String selectedvalue,valueprintchuque;
    Typeface tf;
    LinkedHashMap<String, String> lhmap = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> bloodgroupmap = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> mapprofile_printchuque = new LinkedHashMap<String, String>();

    ArrayList<String> arrayforstate,arrayforprintchuque;
    Map<String,String> responseMap = new LinkedHashMap<String,String>();
    private static final String PREF_NAME_PRIFRNCR = "isregisterd";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personaldetails);
        String fontPath = "fonts/Roboto-Regular.ttf";
        tf = Typeface.createFromAsset(this.getAssets(), fontPath);
        arrayforstate = new ArrayList<>();
        arrayforprintchuque = new ArrayList<>();

        findbyView();

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                enableDate();
            }
        });

        showdata.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showAlert();
            }
        });

/*
        SharedPreferences settings = getSharedPreferences(
                PREF_NAME_PRIFRNCR, 0);*/
        SharedPreferences setting = this.getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        String getMyDetils_String = setting.getString("personaldetais_String", "personaldetais_String");
        String printchuque_string = setting.getString("printchuque_string", "printchuque_string");
        Log.d("printchuque_string", printchuque_string.toString());
        String profilebloodgroup_string = setting.getString("profilebloodgroup_string", "profilebloodgroup_string");
        SchemaName = setting.getString("SchemaName", "SchemaName");
        userId = setting.getString("userId", "userId");
        CompanyId = setting.getString("CompanyId", "CompanyId");

        try
        {
            JSONObject json = new JSONObject(profilebloodgroup_string);
            Iterator<?> keys = json.keys();

            JSONObject json1 = new JSONObject(printchuque_string);
            Iterator<?> keys1 = json1.keys();
            while (keys.hasNext())
            {
                String key = (String) keys.next();
                String value = json.getString(key);
                bloodgroupmap.put(key, value);
                Log.d("bloodgroupmap", key.toString());
            }
            while (keys1.hasNext())
            {
                String key = (String) keys1.next();
                String value = json1.getString(key);
                mapprofile_printchuque.put(key, value);
                Log.d("mapprofile_printchuque", key.toString());
            }
        }   catch (JSONException e) {
            e.printStackTrace();
        }


        try
            {
                JSONObject json = new JSONObject(getMyDetils_String);
                Iterator<?> keys = json.keys();
                while (keys.hasNext())
                {
                    String key = (String) keys.next();
                    String value = json.getString(key);
                    lhmap.put(key, value);
                    //Log.d("setPreference_personal", lhmap.toString());
                }
            }   catch (JSONException e) {
                e.printStackTrace();
            }

        for (Map.Entry<String, String> entry : lhmap.entrySet())
        {
            String valueStr = entry.getValue();
            keyStr = entry.getKey();
          //  Log.d("keyStr get", keyStr.toString());
          //  Log.d("valueStr get", valueStr.toString());

        }

        for (Map.Entry<String, String> entry : lhmap.entrySet())
        {
            final RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(280, 200); // Width , height
            TextInputLayout textInputLayout = new TextInputLayout(this);
            String valueStr = entry.getValue();
            keyStr = entry.getKey();

            if ("BLOODGRP".equalsIgnoreCase(keyStr))
            {

                String BLOODGRP=(entry.getValue());
                String[] BLOODGRP_UNS= {"Select","A+","A-","B+","B-","AB+","AB-","O+","O-"};
                String[] BLOODGRP_SE= {BLOODGRP};
                TextView textView2 = new TextView(this);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.LEFT;
                layoutParams.setMargins(15, 20, 10, 10); // (left, top, right, bottom)
                textView2.setLayoutParams(layoutParams);
                textView2.setText(" BLOODGRP");
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                // textView2.setBackgroundColor(0xffffdbdb); // hex color 0xAARRGGBB
                linearLayout.addView(textView2);
                String getvalue = entry.getValue();
                for (Map.Entry<String, String> entry1 : bloodgroupmap.entrySet()) {
                    String Id1 = entry1.getKey();
                    String ComboValue1 = entry1.getValue();
                    Log.d("LOCATIONID:: ", Id1.toString());
                    Log.d("LOCATIONV:: ", ComboValue1.toString());
                    arrayforstate.add(entry1.getValue());
                    if(getvalue.equalsIgnoreCase(Id1)) {
                        selectedvalue=bloodgroupmap.get(getvalue);
                    }
                    // arrayforstate.add(ComboValue1);
                }

                String EtId = entry.getKey().trim();
                Spinner spiBLOODGRP = new Spinner(this);
                spiBLOODGRP.setTag(EtId);
                spiBLOODGRP.setEnabled(false);
                spiBLOODGRP.setClickable(false);

                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams1.gravity = Gravity.LEFT;
                spiBLOODGRP.setMinimumHeight(60);
                spiBLOODGRP.setMinimumWidth(900);
                layoutParams.setMargins(10, 50, 10, 20); // (left, top, right, bottom)
                spiBLOODGRP.setLayoutParams(layoutParams1);
                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, arrayforstate);
                spiBLOODGRP.setAdapter(adapter_state);

                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                {
                    spiBLOODGRP.setDropDownVerticalOffset(-116);
                }
                int spinnerPosition = adapter_state.getPosition(selectedvalue);
                spiBLOODGRP.setSelection(spinnerPosition);

                //  textView.setText("BRANCH");
                //  textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                // textView.setBackgroundColor(0xffffdbdb); // hex color 0xAARRGGBB
                linearLayout.addView(spiBLOODGRP);

                continue;
            }

            if ("PRINTCHEQUE".equalsIgnoreCase(keyStr)) {

                String PRINTCHEQUE=(entry.getValue());
                String[] PRINTCHEQUE_UNS= {"Select" ,"Yes","No"};
                String[] PRINTCHEQUE_SE= {PRINTCHEQUE};

                String EtId = entry.getKey().trim();
                TextView tvPRINTCHEQUE = new TextView(this);
                tvPRINTCHEQUE.setTag(EtId);

                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.LEFT;
                layoutParams.setMargins(15, 20, 10, 10); // (left, top, right, bottom)
                tvPRINTCHEQUE.setLayoutParams(layoutParams);
                tvPRINTCHEQUE.setText(" PRINTCHEQUE");
                tvPRINTCHEQUE.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                // textView2.setBackgroundColor(0xffffdbdb); // hex color 0xAARRGGBB
                linearLayout.addView(tvPRINTCHEQUE);


                String getvalue = entry.getValue();
                for (Map.Entry<String, String> entry1 : mapprofile_printchuque.entrySet()) {
                    String Id1 = entry1.getKey();
                    String ComboValue1 = entry1.getValue();
                    Log.d("printchuque:: ", Id1.toString());
                    Log.d("printchuque:: ", ComboValue1.toString());
                    arrayforprintchuque.add(entry1.getValue());
                    if(getvalue.equalsIgnoreCase(Id1)) {
                        valueprintchuque=mapprofile_printchuque.get(getvalue);
                    }
                    // arrayforstate.add(ComboValue1);
                }

                String spinnerId = entry.getKey().trim();
                Spinner spiPRINTCHEQUE = new Spinner(this);
                spiPRINTCHEQUE.setTag(spinnerId);
                spiPRINTCHEQUE.setEnabled(false);
                spiPRINTCHEQUE.setClickable(false);

                LinearLayout.LayoutParams layoutParams1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT);
                layoutParams1.gravity = Gravity.LEFT;
                spiPRINTCHEQUE.setMinimumHeight(60);
                spiPRINTCHEQUE.setMinimumWidth(900);
                layoutParams.setMargins(10, 50, 10, 20); // (left, top, right, bottom)
                spiPRINTCHEQUE.setLayoutParams(layoutParams1);
                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, arrayforprintchuque);
                spiPRINTCHEQUE.setAdapter(adapter_state);

                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                {
                    spiPRINTCHEQUE.setDropDownVerticalOffset(-116);
                }
                int spinnerPosition = adapter_state.getPosition(valueprintchuque);
                spiPRINTCHEQUE.setSelection(spinnerPosition);

                linearLayout.addView(spiPRINTCHEQUE);
                continue;
            }


            textInputLayout.setPadding(10, 10, 10, 10);
            textInputLayout.setHint(entry.getKey());
            String EtdId = entry.getKey().trim();
            EditText et = new EditText(this);
            et.setTag(EtdId);
            et.setEnabled(false);
            et.setClickable(false);
            et.setText(entry.getValue());
            textInputLayout.addView(et);
            linearLayout.addView(textInputLayout);

        }


        appCompatButton = new AppCompatButton(this);
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(15, 10, 10, 40); // (left, top, right, bottom)
        appCompatButton.setLayoutParams(layoutParams);
        appCompatButton.setText("Submit");
        appCompatButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        appCompatButton.setBackground(getResources().getDrawable(R.color.colorbgprofile));
        appCompatButton.setTextColor(getResources().getColor(R.color.white));
        linearLayout.addView(appCompatButton);

        appCompatButton.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                UpdateDate();
            }
        });
    }

    private void showAlert()
    {
            //http://www.coderzheaven.com/2011/12/26/show-data-in-columns-in-a-tableview-dynamically-in-android/
            AlertDialog.Builder alert = new AlertDialog.Builder(this);

            LinearLayout layout       = new LinearLayout(this);
            TableRow tableRow         =new TableRow(this);
            TextView tvMessage        = new TextView(this);
            final EditText etInput = new EditText(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            alert.setTitle("HR Update Pending");
            // alert.setCustomTitle("HR Update Pending");
          /*  tvMessage.setText("Enter name:");
            etInput.setSingleLine();
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(tvMessage);
            tableRow.addView(etInput);
            layout.addView(tableRow);
            alert.setTitle("Custom alert demo");
            alert.setView(layout);*/
            TextView companyTV = new TextView(this);
            companyTV.setText("Field Name" +"   "+"Value");
            companyTV.setTextColor(Color.GRAY);
            companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(15, 5, 5, 0);
            layout.addView(companyTV);
            alert.setView(layout);

            /** Creating another textview **/
            /*    TextView companyTV1 = new TextView(this);
                companyTV1.setText("Value");
                companyTV1.setTextColor(Color.GRAY);
                companyTV1.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
                layout.setOrientation(LinearLayout.VERTICAL);
                companyTV1.setPadding(5, 5, 5, 0);
                layout.addView(companyTV1);
                alert.setView(layout);*/

            Log.d("responseMap pintu", responseMap.toString());
            for (Map.Entry<String, String> entry : responseMap.entrySet())
            {
                final RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(280, 200); // Width , height
                TextInputLayout textInputLayout = new TextInputLayout(this);
                String valueStr = entry.getValue();
                String keyStr = entry.getKey();
                TextView textView=new TextView(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setPadding(15, 5, 5, 0);
                textView.setText(entry.getKey()+ "     " + entry.getValue());
                layout.addView(textView);
                alert.setView(layout);
                if (entry.getValue() == null || entry.getValue().equalsIgnoreCase("") || entry.getValue().equalsIgnoreCase("0")) {
                    continue;
                }
            }

            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alert.setPositiveButton("Done", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    //   String name = etInput.getText().toString();
                    // Toast.makeText(getBaseContext(), name, Toast.LENGTH_SHORT).show();
                }
            });

            alert.show();
        }


    private void UpdateDate()
    {
        try
        {
            int nViews = linearLayout.getChildCount();
            Log.d("Key for id", "hi"+nViews);
            for (int i = 0; i <= nViews; i++)
            {
                View child = linearLayout.getChildAt(i);
                Log.d("Component type: ",child.getClass().toString());
                if (child instanceof TextInputLayout)
                {
                    TextInputLayout txtInputLayout = (TextInputLayout) child;
                    EditText edt = txtInputLayout.getEditText();
                    edt.getTag();
                    edt.getText();
                   /* Log.d("edt.getText() ", edt.getText().toString());
                    Log.d("Value for", edt.getTag()+"");
                    Log.d("edt.getId( id", edt.getId()+"");
*/
                    responseMap.put(edt.getTag() + "", edt.getText().toString());
                    //...
                }else if (child instanceof Spinner)
                {
                    Spinner txtvw = (Spinner) child;
                    txtvw.getTag();
                    txtvw.getSelectedItem();
                    responseMap.put(txtvw.getTag() + "", txtvw.getSelectedItem().toString());

                    // Log.d("spnr, value: ", txtvw.getText().toString());
                  /*  Log.d("spnr key :", txtvw.getSelectedItem() + "");
                    Log.d("txtvw.getText(): ", txtvw.getSelectedItem().toString());*/
                    Log.d("respmap: ",responseMap.toString());
                }
                Gson gson = new Gson();
                Fieldvalue = gson.toJson(responseMap);
                Log.d("Fieldvalue", Fieldvalue.toString());
            }
            Log.d("End of onClick", "bye");
        } catch (Exception e){
            Log.d("Excp ","onlick ",e);
            e.printStackTrace();
        }
        if(responseMap!=null)
        {
            new NetCheck().execute();
        }
    }

    private void enableDate()
    {
        try {
            int nViews = linearLayout.getChildCount();
            Log.d("Key for id", "hi" + nViews);
            for (int i = 0; i <= nViews; i++) {
                View child = linearLayout.getChildAt(i);
                Log.d("Component type: ", child.getClass().toString());
                if (child instanceof TextInputLayout) {
                    TextInputLayout txtInputLayout = (TextInputLayout) child;
                    EditText edt = txtInputLayout.getEditText();
                    edt.getTag();
                    edt.setEnabled(true);
                    edt.setClickable(true);
                    edt.getText();
                    Log.d("edt.getText() ", edt.getText().toString());

                } else if (child instanceof Spinner) {
                    Spinner txtvw = (Spinner) child;
                    txtvw.getTag();
                    txtvw.setEnabled(true);
                    txtvw.setClickable(true);

                    txtvw.getSelectedItem();
                    Log.d("spnr key :", txtvw.getSelectedItem() + "");
                }
            }
            Log.d("End of onClick", "bye");
        } catch (Exception e) {
            Log.d("Excp ", "onlick ", e);
            e.printStackTrace();
        }

    }


    private class NetCheck extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(Personal_Details.this);
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
                new ProcessRegister().execute();
            }
            else{
                nDialog.dismiss();
            }
        }
    }

    private class ProcessRegister extends AsyncTask<String, String, JSONObject> {

        /**
         * Defining Process dialog
         **/
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(Personal_Details.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            ConstantClass userFunction = new ConstantClass();

            JSONObject json = userFunction.saveadditionaldetails(SchemaName, CompanyId, Fieldvalue);
            Log.d("Fieldvalue",Fieldvalue.toString());

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
        }}






    private void findbyView()
    {
        showdata=(Button)findViewById(R.id.hrupdate);
        showdata.setTypeface(tf);
        mFab = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.menu1);
       // scrollView = (ScrollView) findViewById(R.id.main_schroll);
        linearLayout = (LinearLayout) findViewById(R.id.main_liner);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                Intent upIntent = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, upIntent)) {
                    // This activity is NOT part of this app's task, so create a new task
                    // when navigating up, with a synthesized back stack.
                    TaskStackBuilder.create(this)
                            // Add all of this activity's parents to the back stack
                            .addNextIntentWithParentStack(upIntent)
                                    // Navigate up to the closest parent
                            .startActivities();
                } else {
                    // This activity is part of this app's task, so simply
                    // navigate up to the logical parent activity.
                    NavUtils.navigateUpTo(this, upIntent);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}




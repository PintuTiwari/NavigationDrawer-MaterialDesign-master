package br.liveo.ndrawer.ui.activity;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
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
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.google.gson.Gson;
import com.weiwangcn.betterspinner.library.material.MaterialBetterSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import br.liveo.ndrawer.R;
import br.liveo.ndrawer.adapter.ConstantClass;
import br.liveo.ndrawer.adapter.JSONParser;
import br.liveo.ndrawer.adapter.JSONParser1;

//import com.github.clans.fab.FloatingActionButton;

/**
 * Created by rupa.dwivedi on 21-01-2016.
 */
public class MyDetails extends ActionBarActivity {
    String citylist="[{'Id':1,'CompanyId':1,'FieldName':'PTLOCATION','ComboValue':'Karnataka','DefaultFId':true},{'Id':2,'CompanyId':1,'FieldName':'PTLOCATION','ComboValue':'West Bengal','DefaultFId':true},{'Id':3,'CompanyId':1,'FieldName':'PTLOCATION','ComboValue':'Maharashtra','DefaultFId':true},{'Id':4,'CompanyId':1,'FieldName':'PTLOCATION','ComboValue':'Andhra Pradesh','DefaultFId':true},{'Id':5,'CompanyId':1,'FieldName':'PTLOCATION','ComboValue':'Gujarat','DefaultFId':true},{'Id':6,'CompanyId':1,'FieldName':'PTLOCATION','ComboValue':'Tamil Nadu','DefaultFId':true},{'Id':7,'CompanyId':1,'FieldName':'PAYMODE','ComboValue':'Cash','DefaultFId':true},{'Id':8,'CompanyId':1,'FieldName':'PAYMODE','ComboValue':'Account Transfer','DefaultFId':true},{'Id':9,'CompanyId':1,'FieldName':'PAYMODE','ComboValue':'Cheque','DefaultFId':true},{'Id':10,'CompanyId':1,'FieldName':'PAYMODE','ComboValue':'Demand Draft','DefaultFId':true},{'Id':11,'CompanyId':1,'FieldName':'DESIGNATION','ComboValue':'SWD','DefaultFId':false},{'Id':12,'CompanyId':1,'FieldName':'DEPARTMENT','ComboValue':'IT','DefaultFId':false},{'Id':13,'CompanyId':1,'FieldName':'ESILOCATION','ComboValue':'MUMBAI','DefaultFId':false},{'Id':14,'CompanyId':1,'FieldName':'BANKNAME','ComboValue':'HDFC BANK','DefaultFId':false},{'Id':15,'CompanyId':1,'FieldName':'ESIDISPENSARY','ComboValue':'NIL','DefaultFId':false},{'Id':16,'CompanyId':1,'FieldName':'ES','ComboValue':'CONFIRMED','DefaultFId':false},{'Id':17,'CompanyId':1,'FieldName':'ES','ComboValue':'PROBATION','DefaultFId':false},{'Id':18,'CompanyId':1,'FieldName':'ES','ComboValue':'CONSULTANT','DefaultFId':false},{'Id':19,'CompanyId':1,'FieldName':'ES','ComboValue':'RESIGNED','DefaultFId':false},{'Id':30,'CompanyId':1,'FieldName':'DEPARTMENT','ComboValue':'IT SOFTWARE','DefaultFId':false},{'Id':31,'CompanyId':1,'FieldName':'DEPARTMENT','ComboValue':'GENERAL MANAGEMENT','DefaultFId':false},{'Id':32,'CompanyId':1,'FieldName':'DEPARTMENT','ComboValue':'FINANCE/ACCOUNTS','DefaultFId':false},{'Id':33,'CompanyId':1,'FieldName':'DEPARTMENT','ComboValue':'HR/ADMIN','DefaultFId':false},{'Id':34,'CompanyId':1,'FieldName':'DESIGNATION','ComboValue':'GENERAL MANAGER - COMMERCIAL','DefaultFId':false},{'Id':35,'CompanyId':1,'FieldName':'DESIGNATION','ComboValue':'MANAGER','DefaultFId':false},{'Id':36,'CompanyId':1,'FieldName':'DESIGNATION','ComboValue':'DIRECTOR','DefaultFId':false},{'Id':37,'CompanyId':1,'FieldName':'DESIGNATION','ComboValue':'EXECUTIVE','DefaultFId':false},{'Id':38,'CompanyId':1,'FieldName':'COSTCENTRE','ComboValue':'MUMBAI','DefaultFId':false},{'Id':39,'CompanyId':1,'FieldName':'BRANCH','ComboValue':'CHEMBUR','DefaultFId':false},{'Id':40,'CompanyId':1,'FieldName':'LOCATION','ComboValue':'MUMBAI','DefaultFId':false},{'Id':41,'CompanyId':1,'FieldName':'PTLOCATION','ComboValue':'WESTBENGAL','DefaultFId':false},{'Id':42,'CompanyId':1,'FieldName':'ESIDISPENSARY','ComboValue':'CHENNAI','DefaultFId':false},{'Id':43,'CompanyId':1,'FieldName':'DEPARTMENT','ComboValue':'MANAGEMENT - GENERAL','DefaultFId':false},{'Id':87,'CompanyId':1,'FieldName':'GRADE','ComboValue':'A','DefaultFId':false},{'Id':88,'CompanyId':1,'FieldName':'GRADE','ComboValue':'B','DefaultFId':false},{'Id':451,'CompanyId':1,'FieldName':'DEPARTMENT','ComboValue':'ASS MANAGER ','DefaultFId':false},{'Id':452,'CompanyId':1,'FieldName':'PAYMODE','ComboValue':'YRYRRY','DefaultFId':false},{'Id':453,'CompanyId':1,'FieldName':'DEPARTMENT','ComboValue':'TESTER DEPT','DefaultFId':false}]";
    LinkedHashMap<String, String> map_citylist = new LinkedHashMap<String, String>();
    LinkedHashMap<String, String> map_location = new LinkedHashMap<String, String>();
    Map<String, String> map_sippayment= new LinkedHashMap<String, String>();
    Map<String, String> map_grade= new LinkedHashMap<String, String>();
    Map<String, String> map_costcenter= new LinkedHashMap<String, String>();
    Map<String, String> map_brench= new LinkedHashMap<String, String>();
    Map<String,String> Fieldname = new LinkedHashMap<String,String>();
    String  Fieldvalue;
    EditText et;
    final int Date_Dialog_ID=0,Date_Dialog_AN=1,Date_Dialog_RE=2,Date_Dialog_DJOIN=3,Date_Dialog_PF=4,Date_Dialog_CDATE=5;
    int cDay,cMonth,cYear; // this is the instances of the current date
    int cDayAN,cMonthAN,cYearAN; // this is the instances of the current date
    int cDayRE,cMonthRE,cYearRE; // this is the instances of the current date
    int cDayDJOIN,cMonthDJOIN,cYearDJOIN; // this is the instances of the current date
    int cDayPF,cMonthPF,cYearPF; // this is the instances of the current date
    int cDayCDATE,cMonthCDATE,cYearCDATE; // this is the instances of the current date


    Calendar cDate;
    int sDay,sMonth,sYear; // this is the instances of the entered date
    int sDayRE,sMonthRE,sYearRE; // this is the instances of the entered date
    int sDayDJOIN,sMonthDJOIN,sYearDJOIN; // this is the instances of the entered date
    int sDayPF,sMonthPF,sYearPF; // this is the instances of the entered date
    int sDayAN,sMonthAN,sYearAN; // this is the instances of the entered date
    int sDayCDATE,sMonthCDATE,sYearCDATE; // this is the instances of the entered date


    String part1,part44,part66,part55;
    int part4,part6,part5;
    String  citylist_string;
    private Calendar cal;
    private int day;
    private int month;
    Spinner textView1;
    String DATEOFBIRTH1,Dateofnniversary1,DateofADAY,DateofAMONTH,DateofAYEAR,Dateofretirement1,Dateofjoining1,PFdateofjoing1
            ,DateofREDAY,DateofREMONTH,DateofREYEAR,DateofDJOINDAY,DateofDJOINMONTH,DateofDJOINYEAR,Confirmation_PeriodMonth1,
            DateofPFDAY,DateofPFMONTH,DateofPFYEAR,DateofCDATEDAY,DateofCDATEMONTH,DateofCDATEYEAR;
    private int year;
    int Month ; // 004
    int Day ;
    int Year ;
    int DateofADAYs,DateofAMONTHs,DateofAYEARs,DateofREDAYs,DateofREMONTHs,DateofREYEARs,
            DateofDJOINDAYs,DateofDJOINMONTHs,DateofDJOINYEARs,DateofPFDAYs,DateofPFMONTHs,DateofPFYEARs,
            DateofCDATEDAYs,DateofCDATEMONTHs,DateofCDATEYEARs;
    ScrollView scrollView;
    LinearLayout linearLayout;
    MaterialBetterSpinner d;
    Spinner Marital_Status,Cost_Centre,Branch,METRO_NONMETRO_TDS,Stop_Payment,LOCATION,Grade,PROFTAX_LOCATION;
    EditText  Father_Name, Reteirment_Age,PFAccount_No,ESI_No, NoOf_Children, PAN_GIRNo_TDS,EMP_PF,Pension_Fund,Tax_Applicable;
    EditText DATEOFBIRTH,Photograph_ChooseFile,PFdateofjoing,Dateofretirement,Dateofjoining,Dateofnniversary,Cdate,Confirmation_PeriodMonth;
    public  String SchemaName,userId,CompanyId;
    String keyStr,key;
    TableRow tr_head;
    HashMap<String, String> getMyDetils_MapString = new HashMap<String, String>();
    LinkedHashMap<String, String> lhmap = new LinkedHashMap<String, String>();

    private static final String PREF_NAME_PRIFRNCR = "isregisterd";

    Toolbar mToolbar;

    private CalendarView calendarView;
    private int yr, mon, dy;
    private String Single,Married,Id,FieldName,selectedvalue,NonMetro,Metro,Yes,NO,selectedvaluelocation,Selected_STOPPAYMENT,Selectd_Grade,
            FieldNamelocation,sipymentvalue,Selected_LOCATION,Selected_METRO_NONMETRO,Selected_Branch,Selected_Cost_Centre,Selected_PLOCATION;
   // String[] arrayforstate;
    ArrayList<String> arrayforstate,arrayforlocation,arrayforsipyment,arrayforcostcenter,arrayforbrench,arrayforgrade;
    ArrayList<String> arrayformrride;
    Typeface tf;
    Button button,HRupdate;
   // FloatingActionButton mFab;
   // private com.github.clans.fab.FloatingActionButton mFab;
 private  com.github.clans.fab.FloatingActionButton mFab;

    ObjectAnimator fade;
    View mContainerHeader;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
     //   setTitle(R.string.settings1);
        setContentView(R.layout.mydetails);
      //  scrollView = (ScrollView) findViewById(R.id.main_schroll);
        linearLayout = (LinearLayout) findViewById(R.id.main_liner);
        arrayforstate = new ArrayList<>();
        arrayforlocation = new ArrayList<>();
        arrayforsipyment = new ArrayList<>();
        arrayforcostcenter = new ArrayList<>();
        arrayforbrench = new ArrayList<>();
        arrayforgrade = new ArrayList<>();



        String fontPath = "fonts/Roboto-Regular.ttf";
        tf = Typeface.createFromAsset(this.getAssets(), fontPath);

        button=(Button)findViewById(R.id.hrupdate);
        button.setTypeface(tf);
        mFab = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.menu1);



        mToolbar = (Toolbar)findViewById(R.id.toolbar);

        mFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

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
        });



        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                showAlert();
            }
        });




        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);
        Log.d("citylist_string ", citylist);

        SharedPreferences setting = this.getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String getMyDetils_String = setting.getString("mydetails_string", "mydetails_string");
        String getmatrial_string = setting.getString("mydetailsmatrial_string", "mydetailsmatrial_string");
        String mydetailsmetro_string = setting.getString("mydetailsmetro_string", "mydetailsmetro_string");
        String mydetailsstoppay_string = setting.getString("mydetailsstoppay_string", "mydetailsstoppay_string");
        String mydetailslocation_string = setting.getString("mydetailslocation_string", "mydetailslocation_string");

        SchemaName = setting.getString("SchemaName", "SchemaName");
        userId = setting.getString("userId", "userId");
        CompanyId = setting.getString("CompanyId", "CompanyId");

        Log.d("SchemaNamelogin1", SchemaName.toString());
        Log.d("userIdlogin1", userId.toString());
        Log.d("CompanyIdlogin1", CompanyId.toString());

        try {

            if (citylist.length() > 0) {
                Log.d("getMyDetils_String: ", citylist.toString());
                //friendArrayList.clear();
                JSONArray internships = new JSONArray(citylist);
                //	JSONArray leagueObject = internships.getJSONArray(0);
                Log.d("leagueObject", new Integer(internships.length()).toString());

                //Log.d("internships.length():", new Integer(internships.length()).toString());
                //Loop the Array
                for (int i = 0; i < internships.length(); i++) {

                    JSONObject jsonObject = internships.getJSONObject(i);
                    FieldName = jsonObject.getString("FieldName");
                    Log.d("FieldName:: ", FieldName.toString());

                    if (FieldName.contains("PTLOCATION")) {
                        String ComboValue = jsonObject.getString("ComboValue");
                        String Id = jsonObject.getString("Id");

                        Log.d("Id:: ", Id.toString());
                        Log.d("ComboValue:: ", ComboValue.toString());
                        map_citylist.put(Id, ComboValue);
                        // arrayforstate.add(ComboValue);
                        // arrayforstate= new String[map_citylist.size()];

                    }
                     if (FieldName.equalsIgnoreCase("LOCATION")) {
                        String ComboValue = jsonObject.getString("ComboValue");
                        String Id = jsonObject.getString("Id");
                        Log.d("LOCATIONID11:: ", Id.toString());
                        Log.d("LOCATIONValue11:: ", ComboValue.toString());
                        map_location.put(Id, ComboValue);
                    }
                     if (FieldName.contains("GRADE")) {
                        String ComboValue = jsonObject.getString("ComboValue");
                        String Id = jsonObject.getString("Id");
                        Log.d("LOCATIONID:: ", Id.toString());
                        Log.d("LOCATIONValue:: ", ComboValue.toString());
                        map_grade.put(Id, ComboValue);
                    }
                    if (FieldName.contains("COSTCENTRE")) {
                        String ComboValue = jsonObject.getString("ComboValue");
                        String Id = jsonObject.getString("Id");
                        Log.d("COSTCENTREID:: ", Id.toString());
                        Log.d("COSTCENTREalue:: ", ComboValue.toString());
                        map_costcenter.put(Id, ComboValue);
                    }
                    if (FieldName.contains("BRANCH")) {
                        String ComboValue = jsonObject.getString("ComboValue");
                        String Id = jsonObject.getString("Id");
                        Log.d("BRANCHID:: ", Id.toString());
                        Log.d("BRANCHalue:: ", ComboValue.toString());
                        map_brench.put(Id, ComboValue);
                    }

                }
                    }

        }catch (Exception e){
            e.printStackTrace();
        }




        try {
            JSONObject json = new JSONObject(getMyDetils_String);
            Iterator<?> keys = json.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                String value = json.getString(key);
                lhmap.put(key, value);
                Log.d("setPreferencemydetails", lhmap.toString());


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, String> entry : lhmap.entrySet()) {
            String valueStr = entry.getValue();
            keyStr = entry.getKey();
            Log.d("keyStr get", keyStr.toString());
            Log.d("valueStr get", valueStr.toString());

        }

        for (Map.Entry<String, String> entry : lhmap.entrySet()) {
            final RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(280, 200); // Width , height
            TextInputLayout textInputLayout = new TextInputLayout(this);
            String valueStr = entry.getValue();
            keyStr = entry.getKey();
            if(entry.getValue() == null || entry.getValue().equalsIgnoreCase("") || entry.getValue().equalsIgnoreCase("0")){
                continue;
            }
            if ("PHOTOGRAPH".equalsIgnoreCase(keyStr)) {
                continue;
            }

            if ("DATEOFBIRTH".equalsIgnoreCase(keyStr)) {
                textInputLayout.setPadding(10, 30, 10, 10);
                textInputLayout.setHint(entry.getKey());
                String EtId = entry.getKey().trim();
                DATEOFBIRTH = new EditText(this);
                DATEOFBIRTH.setTag(EtId);
               // DATEOFBIRTH.setText(entry.getValue());
                String date =entry.getValue();
                try
                {
                    String s= "9/19/1990 12:00:00 AM";
                    String currentDate = "2014-10-01 00:00:00.0";
                    //   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");

                    Date tempDate=simpleDateFormat.parse(date);
                    //  SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    System.out.println("DATEOFBIRTH1 = " + outputDateFormat.format(tempDate));
                    DATEOFBIRTH1=outputDateFormat.format(tempDate);
                    DATEOFBIRTH.setText(DATEOFBIRTH1);
                    DATEOFBIRTH.setFocusable(false);
                    DATEOFBIRTH.setEnabled(false);
                    DATEOFBIRTH.setClickable(false);


                    String[] part3 = DATEOFBIRTH1.split("/");
                    part44 = part3[0]; // 004
                    part4=Integer.parseInt(part44);

                    part55 = part3[1]; // 034556
                    part5=Integer.parseInt(part55);

                    part66= part3[2];
                    part6=Integer.parseInt(part66);
                    } catch (ParseException ex)
                        {
                            System.out.println("Parse Exception");
                        }

                            DATEOFBIRTH.setTypeface(tf);
                            DATEOFBIRTH.setCompoundDrawablesWithIntrinsicBounds(null, null,
                                    getResources().getDrawable(R.drawable.calender), null);
                            textInputLayout.addView(DATEOFBIRTH);




                DATEOFBIRTH.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
// TODO Auto-generated method stub
//triggers the DatePickerDialog
                        showDialog(Date_Dialog_ID);
                    }
                });

                cDate=Calendar.getInstance();
                cDate.set(part4, part5, part6);
               // cDate.set(part6, part5-1, part4);
                   /*  cDate.set(2916, 4, 9);

        cDate.add(Calendar.MONTH, 0);*/

              //  Date d = Calendar.getInstance().getTime(); // Current time


                cDay=cDate.get(Calendar.DAY_OF_MONTH);
                cMonth=cDate.get(Calendar.MONTH);
                cYear=cDate.get(Calendar.YEAR);
//assigning the edittext with the current date in the beginning
                sDay=cDay;
                sMonth=cMonth;
                sYear=cYear;
                updateDateDisplay(sYear, sMonth, sDay);

                linearLayout.addView(textInputLayout);
                DATEOFBIRTH.setEnabled(false);
                DATEOFBIRTH.setClickable(false);

                continue;
            }


            if ("CONFIRMATIONDATE".equalsIgnoreCase(keyStr)) {
                textInputLayout.setPadding(10, 30, 10, 10);
                textInputLayout.setHint(entry.getKey());

                String EtId = entry.getKey().trim();
                Confirmation_PeriodMonth = new EditText(this);
                Confirmation_PeriodMonth.setTag(EtId);
                // DATEOFBIRTH.setText(entry.getValue());
                String date =entry.getValue();
                try
                {
                    String s= "12/6/2015 12:00:00 AM";
                    String currentDate = "2014-10-01 00:00:00.0";
                    //   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                  //  SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy/MM/dd");

                    Date tempDate=simpleDateFormat.parse(date);
                    //  SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    System.out.println("Confirmation_PeriodMonth1= "+outputDateFormat.format(tempDate));
                    Confirmation_PeriodMonth1=outputDateFormat.format(tempDate);
                    Confirmation_PeriodMonth.setText(Confirmation_PeriodMonth1);
                    Confirmation_PeriodMonth.setFocusable(false);
                    Confirmation_PeriodMonth.setEnabled(false);
                    Confirmation_PeriodMonth.setClickable(false);

                    String[] part3 = Confirmation_PeriodMonth1.split("/");
                    DateofCDATEYEAR = part3[0]; // 004
                    DateofCDATEYEARs=Integer.parseInt(DateofCDATEYEAR);
                    System.out.println("Output date is = " + DateofCDATEYEARs);

                    DateofCDATEMONTH = part3[1]; // 034556
                    DateofCDATEMONTHs=Integer.parseInt(DateofCDATEMONTH);
                    System.out.println("Output date is = "+DateofCDATEMONTHs);


                    DateofCDATEDAY= part3[2];
                    DateofCDATEDAYs=Integer.parseInt(DateofCDATEDAY);
                    System.out.println("Output date is = "+DateofCDATEDAYs);

                } catch (ParseException ex)
                {
                    System.out.println("Parse Exception");
                }

                Confirmation_PeriodMonth.setTypeface(tf);
                Confirmation_PeriodMonth.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        getResources().getDrawable(R.drawable.calender), null);
                textInputLayout.addView(Confirmation_PeriodMonth);
                Confirmation_PeriodMonth.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
// TODO Auto-generated method stub
//triggers the DatePickerDialog
                        showDialog(Date_Dialog_CDATE);
                    }
                });

                Calendar cDate=Calendar.getInstance();
                cDate.set(DateofCDATEYEARs, DateofCDATEMONTHs, DateofCDATEDAYs);
                cDate.add(Calendar.MONTH, -1);
                cDayCDATE=cDate.get(Calendar.DAY_OF_MONTH);
                cMonthCDATE=cDate.get(Calendar.MONTH);
                cYearCDATE=cDate.get(Calendar.YEAR);
//assigning the edittext with the current date in the beginning
                sDayCDATE=cDayCDATE;
                sMonthCDATE=cMonthCDATE;
                sYearCDATE=cYearCDATE;
                //updateDateDisplayAN(sDayAN, sMonthAN, sYearAN);
                updateDateDisplayCDATE(sYearCDATE, sMonthCDATE, sDayCDATE);


                linearLayout.addView(textInputLayout);

                continue;
            }



            if ("DATEOFRETIREMENT".equalsIgnoreCase(keyStr)) {
                textInputLayout.setPadding(10, 30, 10, 10);
                textInputLayout.setHint(entry.getKey());

                String EtId = entry.getKey().trim();
                Dateofretirement = new EditText(this);
                Dateofretirement.setTag(EtId);
                // DATEOFBIRTH.setText(entry.getValue());
                String date =entry.getValue();
                try
                {
                    String s= "12/6/2015 12:00:00 AM";
                    String currentDate = "2014-10-01 00:00:00.0";
                    //   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");

                    Date tempDate=simpleDateFormat.parse(date);
                    //  SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    System.out.println("Dateofnniversary1= "+outputDateFormat.format(tempDate));
                    Dateofretirement1=outputDateFormat.format(tempDate);
                    Dateofretirement.setText(Dateofretirement1);
                    Dateofretirement.setFocusable(false);
                    Dateofretirement.setEnabled(false);
                    Dateofretirement.setClickable(false);

                    String[] part3 = Dateofretirement1.split("/");
                    DateofREYEAR = part3[0]; // 004
                    DateofREYEARs=Integer.parseInt(DateofREYEAR);
                    System.out.println("Output date is = " + DateofREYEARs);

                    DateofREMONTH = part3[1]; // 034556
                    DateofREMONTHs=Integer.parseInt(DateofREMONTH);
                    System.out.println("Output date is = "+DateofREMONTHs);


                    DateofREDAY= part3[2];
                    DateofREDAYs=Integer.parseInt(DateofREDAY);
                    System.out.println("Output date is = "+DateofREDAYs);

                } catch (ParseException ex)
                {
                    System.out.println("Parse Exception");
                }

                Dateofretirement.setTypeface(tf);
                Dateofretirement.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        getResources().getDrawable(R.drawable.calender), null);
                textInputLayout.addView(Dateofretirement);
                Dateofretirement.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
// TODO Auto-generated method stub
//triggers the DatePickerDialog
                        showDialog(Date_Dialog_RE);
                    }
                });

                Calendar cDate=Calendar.getInstance();
                cDate.set(DateofREYEARs, DateofREMONTHs, DateofREDAYs);
                cDate.add(Calendar.MONTH, -1);
                cDayRE=cDate.get(Calendar.DAY_OF_MONTH);
                cMonthRE=cDate.get(Calendar.MONTH);
                cYearRE=cDate.get(Calendar.YEAR);
//assigning the edittext with the current date in the beginning
                sDayRE=cDayRE;
                sMonthRE=cMonthRE;
                sYearRE=cYearRE;
                //updateDateDisplayAN(sDayAN, sMonthAN, sYearAN);
                updateDateDisplayRE(sYearRE, sMonthRE, sDayRE);


                linearLayout.addView(textInputLayout);

                continue;
            }


            if ("DATEOFANNIVERSARY".equalsIgnoreCase(keyStr)) {
                textInputLayout.setPadding(10, 30, 10, 10);
                textInputLayout.setHint(entry.getKey());
                String EtId = entry.getKey().trim();
                Dateofnniversary = new EditText(this);
                Dateofnniversary.setTag(EtId);

                // DATEOFBIRTH.setText(entry.getValue());
                String date =entry.getValue();
                try
                {
                    String s= "12/6/2015 12:00:00 AM";
                    String currentDate = "2014-10-01 00:00:00.0";
                    //   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");

                    Date tempDate=simpleDateFormat.parse(date);
                    //  SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    System.out.println("Dateofnniversary1= "+outputDateFormat.format(tempDate));
                    Dateofnniversary1=outputDateFormat.format(tempDate);
                    Dateofnniversary.setText(Dateofnniversary1);

                    Dateofnniversary.setFocusable(false);
                    Dateofnniversary.setEnabled(false);
                    Dateofnniversary.setClickable(false);

                    String[] part3 = Dateofnniversary1.split("/");
                    DateofAYEAR = part3[0]; // 004
                    DateofAYEARs=Integer.parseInt(DateofAYEAR);
                    System.out.println("Output date is = " + DateofAYEARs);

                    DateofAMONTH = part3[1]; // 034556
                    DateofAMONTHs=Integer.parseInt(DateofAMONTH);
                    System.out.println("Output date is = "+DateofAMONTHs);


                    DateofADAY= part3[2];
                    DateofADAYs=Integer.parseInt(DateofADAY);
                    System.out.println("Output date is = "+DateofADAYs);

                } catch (ParseException ex)
                {
                    System.out.println("Parse Exception");
                }

                Dateofnniversary.setTypeface(tf);
                Dateofnniversary.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        getResources().getDrawable(R.drawable.calender), null);
                textInputLayout.addView(Dateofnniversary);
                Dateofnniversary.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
// TODO Auto-generated method stub
//triggers the DatePickerDialog
                        showDialog(Date_Dialog_AN);
                    }
                });

                Calendar cDate=Calendar.getInstance();
                cDate.set(DateofAYEARs, DateofAMONTHs, DateofADAYs);
                cDate.add(Calendar.MONTH, -1);
                cDayAN=cDate.get(Calendar.DAY_OF_MONTH);
                cMonthAN=cDate.get(Calendar.MONTH);
                cYearAN=cDate.get(Calendar.YEAR);
//assigning the edittext with the current date in the beginning
                sDayAN=cDayAN;
                sMonthAN=cMonthAN;
                sYearAN=cYearAN;
                //updateDateDisplayAN(sDayAN, sMonthAN, sYearAN);
                updateDateDisplayAN(sYearAN, sMonthAN, sDayAN);


                linearLayout.addView(textInputLayout);

                continue;
            }


            if ("PFDATEOFJOINING".equalsIgnoreCase(keyStr)) {
                textInputLayout.setPadding(10, 30, 10, 10);
                textInputLayout.setHint(entry.getKey());
                String EtId = entry.getKey().trim();
                PFdateofjoing = new EditText(this);
                PFdateofjoing.setTag(EtId);                String date =entry.getValue();
                try
                {
                    String s= "12/6/2015 12:00:00 AM";
                    String currentDate = "2014-10-01 00:00:00.0";
                    //   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");

                    Date tempDate=simpleDateFormat.parse(date);
                    //  SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    System.out.println("PFdateofjoing1 = "+outputDateFormat.format(tempDate));
                    PFdateofjoing1=outputDateFormat.format(tempDate);
                    PFdateofjoing.setText(PFdateofjoing1);
                    PFdateofjoing.setFocusable(false);
                    PFdateofjoing.setEnabled(false);
                    PFdateofjoing.setClickable(false);

                    String[] part3 = PFdateofjoing1.split("/");
                    DateofPFYEAR = part3[0]; // 004
                    DateofPFYEARs=Integer.parseInt(DateofPFYEAR);
                    System.out.println("Output date is = "+DateofPFYEARs);

                    DateofPFMONTH = part3[1]; // 034556
                    DateofPFMONTHs=Integer.parseInt(DateofPFMONTH);
                    System.out.println("Output date is = "+DateofPFMONTHs);


                    DateofPFDAY= part3[2];
                    DateofPFDAYs=Integer.parseInt(DateofPFDAY);
                    System.out.println("Output date is = "+DateofPFDAYs);

                } catch (ParseException ex)
                {
                    System.out.println("Parse Exception");
                }

                PFdateofjoing.setTypeface(tf);
                PFdateofjoing.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        getResources().getDrawable(R.drawable.calender), null);
                textInputLayout.addView(PFdateofjoing);
                PFdateofjoing.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        showDialog(Date_Dialog_PF);
                    }
                });

                Calendar cDate=Calendar.getInstance();
                cDate.set(DateofPFYEARs, DateofPFMONTHs, DateofPFDAYs);
                cDate.add(Calendar.MONTH,-1);
                cDayPF=cDate.get(Calendar.DAY_OF_MONTH);
                cMonthPF=cDate.get(Calendar.MONTH);
                cYearPF=cDate.get(Calendar.YEAR);
//assigning the edittext with the current date in the beginning
                sDayPF=cDayPF;
                sMonthPF=cMonthPF;
                sYearPF=cYearPF;
                //updateDateDisplayAN(sDayAN, sMonthAN, sYearAN);
                updateDateDisplayPF(sYearPF, sMonthPF, sDayPF);
                linearLayout.addView(textInputLayout);
                continue;
            }


            if ("DATEOFJOINING".equalsIgnoreCase(keyStr)) {
                textInputLayout.setPadding(10, 30, 10, 10);
                textInputLayout.setHint(entry.getKey());
                String EtId = entry.getKey().trim();
                Dateofjoining = new EditText(this);
                Dateofjoining.setTag(EtId);

                // DATEOFBIRTH.setText(entry.getValue());
                String date =entry.getValue();
                try
                {
                    String s= "12/6/2015 12:00:00 AM";
                    String currentDate = "2014-10-01 00:00:00.0";
                    //   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");

                    Date tempDate=simpleDateFormat.parse(date);
                    //  SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    System.out.println("Dateofjoining1 = "+outputDateFormat.format(tempDate));
                    Dateofjoining1=outputDateFormat.format(tempDate);
                    Dateofjoining.setText(Dateofjoining1);
                    Dateofjoining.setFocusable(false);
                    Dateofjoining.setEnabled(false);
                    Dateofjoining.setClickable(false);


                    String[] part3 = Dateofjoining1.split("/");
                    DateofDJOINYEAR = part3[0]; // 004
                    DateofDJOINYEARs=Integer.parseInt(DateofDJOINYEAR);
                    System.out.println("Output date is = "+DateofDJOINYEARs);

                    DateofDJOINMONTH = part3[1]; // 034556
                    DateofDJOINMONTHs=Integer.parseInt(DateofDJOINMONTH);
                    System.out.println("Output date is = "+DateofDJOINMONTHs);


                    DateofDJOINDAY= part3[2];
                    DateofDJOINDAYs=Integer.parseInt(DateofDJOINDAY);
                    System.out.println("Output date is = "+DateofDJOINDAYs);

                } catch (ParseException ex)
                {
                    System.out.println("Parse Exception");
                }

                Dateofjoining.setTypeface(tf);
                Dateofjoining.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        getResources().getDrawable(R.drawable.calender), null);
                textInputLayout.addView(Dateofjoining);
                Dateofjoining.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {

                        showDialog(Date_Dialog_DJOIN);
                    }
                });

                Calendar cDate=Calendar.getInstance();
                cDate.set(DateofDJOINYEARs, DateofDJOINMONTHs, DateofDJOINDAYs);
                cDate.add(Calendar.MONTH,-1);
                cDayDJOIN=cDate.get(Calendar.DAY_OF_MONTH);
                cMonthDJOIN=cDate.get(Calendar.MONTH);
                cYearDJOIN=cDate.get(Calendar.YEAR);
//assigning the edittext with the current date in the beginning
                sDayDJOIN=cDayDJOIN;
                sMonthDJOIN=cMonthDJOIN;
                sYearDJOIN=cYearDJOIN;
                //updateDateDisplayAN(sDayAN, sMonthAN, sYearAN);
                updateDateDisplayDJOIN(sYearDJOIN, sMonthDJOIN, sDayDJOIN);
                linearLayout.addView(textInputLayout);
                continue;
            }
            if ("MARITALSTATUS".equalsIgnoreCase(keyStr)) {
                TextView textView2 = new TextView(this);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.LEFT;
                layoutParams.setMargins(15, 15, 10, 10); // (left, top, right, bottom)
                textView2.setLayoutParams(layoutParams);
                textView2.setText(" MARITALSTATUS");
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textView2.setTypeface(tf);
                linearLayout.addView(textView2);

                try {

                    if (getmatrial_string.length() > 0) {
                       // Log.d("getMyDetils_String: ", getmatrial_string.toString());
                        //friendArrayList.clear();
                        JSONObject internships = new JSONObject(getmatrial_string);
                        Log.d("leagueObject", new Integer(internships.length()).toString());
                        Single = internships.getString("Single");
                        Married = internships.getString("Married");
                        Log.d("Married", Married.toString());
                        Log.d("Single",Single.toString());

                    }

                }catch (Exception e){
                    e.printStackTrace();
                }
                String sSpinnerEtId = entry.getKey().trim();
                Marital_Status = new Spinner(this);
                Marital_Status.setTag(sSpinnerEtId);
                LayoutParams layoutParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                layoutParams1.gravity = Gravity.LEFT;
                Marital_Status.setMinimumHeight(60);
                Marital_Status.setMinimumWidth(900);
                layoutParams.setMargins(10, 40, 10, 10); // (left, top, right, bottom)
                Marital_Status.setLayoutParams(layoutParams1);
                String arr[]={Single,Married};
                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, arr);
                Marital_Status.setAdapter(adapter_state);
              //  ((TextView) view).setTypeface(tf);
                //  textView.setText("BRANCH");
                //  textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
              //  textView.setBackgroundColor(0xffffdbdb); // hex color 0xAARRGGBB
                Marital_Status.setEnabled(false);
                Marital_Status.setClickable(false);
                linearLayout.addView(Marital_Status);
                continue;

            }



            if ("PTLOCATION".equalsIgnoreCase(keyStr)) {

                TextView textView2 = new TextView(this);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.LEFT;
                layoutParams.setMargins(15, 10, 10, 10); // (left, top, right, bottom)
                textView2.setLayoutParams(layoutParams);
                textView2.setText(" PTLOCATION");
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textView2.setTypeface(tf);

                // textView2.setBackgroundColor(0xffffdbdb); // hex color 0xAARRGGBB
                linearLayout.addView(textView2);
                String getvalue = entry.getValue();
                for (Map.Entry<String, String> entry1 : map_citylist.entrySet()) {
                    String Id1 = entry1.getKey();
                    String ComboValue1 = entry1.getValue();
                   // Log.d("Id1:: ", Id1.toString());
                   // Log.d("ComboValue1:: ", ComboValue1.toString());
                    arrayforstate.add(ComboValue1);
                     if(getvalue.equalsIgnoreCase(Id1)) {
                         selectedvalue=map_citylist.get(getvalue);
                     }
                    // arrayforstate.add(ComboValue1);
                }
                String sSpinnerEtId = entry.getKey().trim();
                PROFTAX_LOCATION = new Spinner(this);
                PROFTAX_LOCATION.setTag(sSpinnerEtId);
                LayoutParams layoutParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                layoutParams1.gravity = Gravity.LEFT;
                PROFTAX_LOCATION.setMinimumHeight(60);
                PROFTAX_LOCATION.setMinimumWidth(900);
                layoutParams.setMargins(10, 30, 10, 10); // (left, top, right, bottom)
                PROFTAX_LOCATION.setLayoutParams(layoutParams1);

                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, arrayforstate);
               // adapter_state.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);

              //  textView1.setTheme(R.style.MySpinnerLook);
                // adapter_state.setDropDownViewResource(R.layout.doubleline_spinner);
                PROFTAX_LOCATION.setAdapter(adapter_state);
                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                {
                    PROFTAX_LOCATION.setDropDownVerticalOffset(-116);
                }
                    int spinnerPosition = adapter_state.getPosition(selectedvalue);
                PROFTAX_LOCATION.setSelection(spinnerPosition);

                PROFTAX_LOCATION.setEnabled(false);
                PROFTAX_LOCATION.setClickable(false);

                PROFTAX_LOCATION.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long itemId) {
                        //((TextView)view).setTextColor(Color.BLUE);
                        Selected_PLOCATION=PROFTAX_LOCATION.getSelectedItem().toString();
                        ((TextView) view).setTypeface(tf);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) { }
                });
                linearLayout.addView(PROFTAX_LOCATION);


                continue;
            }



               if ("COSTCENTRE".equalsIgnoreCase(keyStr)) {

                TextView textView2 = new TextView(this);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.LEFT;
                layoutParams.setMargins(15, 10, 10, 10); // (left, top, right, bottom)
                   textView2.setLayoutParams(layoutParams);
                   textView2.setText(" COSTCENTRE");
                   textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                   textView2.setTypeface(tf);

                   // textView2.setBackgroundColor(0xffffdbdb); // hex color 0xAARRGGBB
                linearLayout.addView(textView2);
                   String getvalue = entry.getValue();
                   for (Map.Entry<String, String> entry1 : map_costcenter.entrySet()) {
                       String Id1 = entry1.getKey();
                       String ComboValue1 = entry1.getValue();
                       // Log.d("Id1:: ", Id1.toString());
                       // Log.d("ComboValue1:: ", ComboValue1.toString());
                       arrayforcostcenter.add(ComboValue1);
                       if(getvalue.equalsIgnoreCase(Id1)) {
                           selectedvalue=map_costcenter.get(getvalue);
                       }
                       // arrayforstate.add(ComboValue1);
                   }
                   String sSpinnerEtId = entry.getKey().trim();
                   Cost_Centre = new Spinner(this);
                   Cost_Centre.setTag(sSpinnerEtId);
                LayoutParams layoutParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                layoutParams1.gravity = Gravity.LEFT;
                   Cost_Centre.setMinimumHeight(60);
                   Cost_Centre.setMinimumWidth(900);
                layoutParams.setMargins(10, 40, 10, 10); // (left, top, right, bottom)
                   Cost_Centre.setLayoutParams(layoutParams1);
                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, arrayforcostcenter);
                   Cost_Centre.setAdapter(adapter_state);
                   Cost_Centre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                       @Override
                       public void onItemSelected(AdapterView<?> adapterView, View view, int position, long itemId) {
                           //((TextView)view).setTextColor(Color.BLUE);
                           Selected_Cost_Centre = Cost_Centre.getSelectedItem().toString();
                           ((TextView) view).setTypeface(tf);


                       }

                       @Override
                       public void onNothingSelected(AdapterView<?> arg0) {
                       }
                   });
                   Cost_Centre.setEnabled(false);
                   Cost_Centre.setClickable(false);
                   linearLayout.addView(Cost_Centre);


                continue;
            }

            if ("BRANCH".equalsIgnoreCase(keyStr)) {

                TextView textView2 = new TextView(this);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.LEFT;
                layoutParams.setMargins(15, 30, 10, 10); // (left, top, right, bottom)
                textView2.setLayoutParams(layoutParams);
                textView2.setText(" BRANCH");
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textView2.setTypeface(tf);

                // textView2.setBackgroundColor(0xffffdbdb); // hex color 0xAARRGGBB
                linearLayout.addView(textView2);
                String getvalue = entry.getValue();
                for (Map.Entry<String, String> entry1 : map_brench.entrySet()) {
                    String Id1 = entry1.getKey();
                    String ComboValue1 = entry1.getValue();
                    // Log.d("Id1:: ", Id1.toString());
                    // Log.d("ComboValue1:: ", ComboValue1.toString());
                    arrayforbrench.add(ComboValue1);
                    if(getvalue.equalsIgnoreCase(Id1)) {
                        selectedvalue=map_brench.get(getvalue);
                    }
                    // arrayforstate.add(ComboValue1);
                }
                String sSpinnerEtId = entry.getKey().trim();
                Branch = new Spinner(this);
                Branch.setTag(sSpinnerEtId);
                LayoutParams layoutParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                layoutParams1.gravity = Gravity.LEFT;
                Branch.setMinimumHeight(60);
                Branch.setMinimumWidth(900);
                 layoutParams.setMargins(10, 40, 10, 10); // (left, top, right, bottom)
                Branch.setLayoutParams(layoutParams1);
                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, arrayforbrench);
                Branch.setAdapter(adapter_state);
                Branch.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long itemId) {
                        //((TextView)view).setTextColor(Color.BLUE);
                        Selected_Branch = Branch.getSelectedItem().toString();
                        ((TextView) view).setTypeface(tf);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
                Branch.setEnabled(false);
                Branch.setClickable(false);
                linearLayout.addView(Branch);
                continue;
            }

            if ("GRADE".equalsIgnoreCase(keyStr)) {
                TextView textView2 = new TextView(this);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.LEFT;
                layoutParams.setMargins(15, 20, 10, 10); // (left, top, right, bottom)
                textView2.setLayoutParams(layoutParams);
                textView2.setText(" GRADE");
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textView2.setTypeface(tf);

                // textView2.setBackgroundColor(0xffffdbdb); // hex color 0xAARRGGBB
                linearLayout.addView(textView2);
                String getvalue = entry.getValue();
                for (Map.Entry<String, String> entry1 : map_grade.entrySet()) {
                    String Id1 = entry1.getKey();
                    String ComboValue1 = entry1.getValue();
                    // Log.d("Id1:: ", Id1.toString());
                    // Log.d("ComboValue1:: ", ComboValue1.toString());
                    arrayforgrade.add(ComboValue1);
                    if(getvalue.equalsIgnoreCase(Id1)) {
                        selectedvalue=map_grade.get(getvalue);
                    }
                    // arrayforstate.add(ComboValue1);
                }
                String sSpinnerEtId = entry.getKey().trim();
                Grade= new Spinner(this);
                Grade.setTag(sSpinnerEtId);
                LayoutParams layoutParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                layoutParams1.gravity = Gravity.LEFT;
                Grade.setMinimumHeight(60);
                Grade.setMinimumWidth(900);
                layoutParams.setMargins(10, 50, 10, 10); // (left, top, right, bottom)
                Grade.setLayoutParams(layoutParams1);
                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, arrayforgrade);
                Grade.setAdapter(adapter_state);
                Grade.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long itemId) {
                        //((TextView)view).setTextColor(Color.BLUE);
                        Selectd_Grade = Grade.getSelectedItem().toString();
                        ((TextView) view).setTypeface(tf);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
                Grade.setEnabled(false);
                Grade.setClickable(false);
                linearLayout.addView(Grade);
                continue;
            }


           if ("METRO".equalsIgnoreCase(keyStr)) {
               TextView textView2 = new TextView(this);
               LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                       LayoutParams.WRAP_CONTENT);
               layoutParams.gravity = Gravity.LEFT;
               layoutParams.setMargins(15, 20, 10, 10); // (left, top, right, bottom)
               textView2.setLayoutParams(layoutParams);
               textView2.setText(" METRO");
               textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
               textView2.setTypeface(tf);

               // textView2.setBackgroundColor(0xffffdbdb); // hex color 0xAARRGGBB
               linearLayout.addView(textView2);
               try {

                   if (mydetailsmetro_string.length() > 0) {
                       Log.d("getMyDetils_String: ", mydetailsmetro_string.toString());
                       //friendArrayList.clear();
                       JSONObject internships = new JSONObject(mydetailsmetro_string);
                       Log.d("leagueObject", new Integer(internships.length()).toString());


                       Metro = internships.getString("Metro");
                       NonMetro = internships.getString("Non-Metro");
                       Log.d("Metro", Metro.toString());
                       Log.d("NonMetro",NonMetro.toString());

                   }

               }catch (Exception e){
                   e.printStackTrace();
               }
               String sSpinnerEtId = entry.getKey().trim();
               METRO_NONMETRO_TDS = new Spinner(this);
               METRO_NONMETRO_TDS.setTag(sSpinnerEtId);
               LayoutParams layoutParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT,
                       LayoutParams.WRAP_CONTENT);
               layoutParams1.gravity = Gravity.LEFT;
               METRO_NONMETRO_TDS.setMinimumHeight(60);
               METRO_NONMETRO_TDS.setMinimumWidth(900);
               layoutParams.setMargins(10, 40, 10, 10); // (left, top, right, bottom)
               METRO_NONMETRO_TDS.setLayoutParams(layoutParams1);
               String arr[]={Metro,NonMetro};
               ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                       android.R.layout.simple_spinner_item, arr);
               METRO_NONMETRO_TDS.setAdapter(adapter_state);

               METRO_NONMETRO_TDS.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                   @Override
                   public void onItemSelected(AdapterView<?> adapterView, View view, int position, long itemId) {
                       //((TextView)view).setTextColor(Color.BLUE);
                       Selected_METRO_NONMETRO=METRO_NONMETRO_TDS.getSelectedItem().toString();
                       ((TextView) view).setTypeface(tf);


                   }

                   @Override
                   public void onNothingSelected(AdapterView<?> arg0) {
                   }
               });
               METRO_NONMETRO_TDS.setEnabled(false);
               METRO_NONMETRO_TDS.setClickable(false);
               linearLayout.addView(METRO_NONMETRO_TDS);
                continue;
            }

            if ("STOPPAYMENT".equalsIgnoreCase(keyStr)) {
                TextView textView2 = new TextView(this);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.LEFT;
                layoutParams.setMargins(15, 20, 10, 10); // (left, top, right, bottom)
                textView2.setLayoutParams(layoutParams);
                textView2.setText(" STOPPAYMENT");
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textView2.setTypeface(tf);

                // textView2.setBackgroundColor(0xffffdbdb); // hex color 0xAARRGGBB
                linearLayout.addView(textView2);

                try {
                    JSONObject json = new JSONObject(mydetailsstoppay_string);
                    Iterator<?> keys = json.keys();

                    while (keys.hasNext()) {
                        String key = (String) keys.next();
                        Log.d("KeySIP", key.toString());
                        String value = json.getString(key);
                        Log.d("ValueSIP", value.toString());
                        map_sippayment.put(key, value);
                        Log.d("map_sippayment", map_sippayment.toString());


                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                for (Map.Entry<String, String> entry1 : map_sippayment.entrySet()) {
                    sipymentvalue = entry1.getValue();
                    arrayforsipyment.add(entry1.getValue());
                    Log.d("KeySIP11", sipymentvalue.toString());
                    String keyStr = entry1.getKey();
                    Log.d("ValueSIP11", keyStr.toString());
                    String getvalue = entry.getValue();
                }
                String sSpinnerEtId = entry.getKey().trim();
                Stop_Payment    = new Spinner(this);
                Stop_Payment.setTag(sSpinnerEtId);
                    LayoutParams layoutParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT,
                            LayoutParams.WRAP_CONTENT);
                    layoutParams1.gravity = Gravity.LEFT;
                Stop_Payment.setMinimumHeight(60);
                Stop_Payment.setMinimumWidth(900);
                layoutParams.setMargins(10, 50, 10, 10); // (left, top, right, bottom)
                Stop_Payment.setLayoutParams(layoutParams1);

                for (int i = 0; i < arrayforsipyment.size(); i++) {
                        System.out.println(arrayforsipyment.get(i));
                    }
                    ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                            android.R.layout.simple_spinner_item, arrayforsipyment);
                Stop_Payment.setAdapter(adapter_state);

                Stop_Payment.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long itemId) {
                        //((TextView)view).setTextColor(Color.BLUE);
                        Selected_STOPPAYMENT = Stop_Payment.getSelectedItem().toString();

                        //  Typeface externalFont=Typeface.createFromAsset(getAssets(), "fonts/HelveticaNeueLTCom-Lt.ttf");
                        ((TextView) view).setTypeface(tf);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
                Stop_Payment.setEnabled(false);
                Stop_Payment.setClickable(false);
                linearLayout.addView(Stop_Payment);

                continue;

            }

            if ("LOCATION".equalsIgnoreCase(keyStr)) {
                TextView textView2 = new TextView(this);
                LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                layoutParams.gravity = Gravity.LEFT;
                layoutParams.setMargins(15, 20, 10, 10); // (left, top, right, bottom)
                textView2.setLayoutParams(layoutParams);
                textView2.setText(" LOCATION");
                textView2.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                textView2.setTypeface(tf);

                // textView2.setBackgroundColor(0xffffdbdb); // hex color 0xAARRGGBB
                linearLayout.addView(textView2);
                String getvalue = entry.getValue();
                for (Map.Entry<String, String> entry1 : map_location.entrySet()) {
                    String Id1 = entry1.getKey();
                    String ComboValue1 = entry1.getValue();
                    Log.d("LOCATIONID:: ", Id1.toString());
                    Log.d("LOCATIONV:: ", ComboValue1.toString());
                    arrayforlocation.add(entry1.getValue());
                    if(getvalue.equalsIgnoreCase(Id1)) {
                        selectedvaluelocation=map_location.get(getvalue);
                    }
                    // arrayforstate.add(ComboValue1);
                }
                String sSpinnerEtId = entry.getKey().trim();
                LOCATION = new Spinner(this);
                LOCATION.setTag(sSpinnerEtId);
                LayoutParams layoutParams1 = new LayoutParams(LayoutParams.WRAP_CONTENT,
                        LayoutParams.WRAP_CONTENT);
                layoutParams1.gravity = Gravity.LEFT;
                LOCATION.setMinimumHeight(60);
                LOCATION.setMinimumWidth(900);
                layoutParams.setMargins(10, 40, 10, 20); // (left, top, right, bottom)
                LOCATION.setLayoutParams(layoutParams1);
                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, arrayforlocation);
                LOCATION.setAdapter(adapter_state);

                if(Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                {
                    LOCATION.setDropDownVerticalOffset(-116);
                }
                int spinnerPosition = adapter_state.getPosition(selectedvaluelocation);
                LOCATION.setSelection(spinnerPosition);



                LOCATION.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long itemId) {
                        //((TextView)view).setTextColor(Color.BLUE);
                         Selected_LOCATION=LOCATION.getSelectedItem().toString();
                        ((TextView) view).setTypeface(tf);


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> arg0) {
                    }
                });
                LOCATION.setEnabled(false);
                LOCATION.setClickable(false);
                linearLayout.addView(LOCATION);

                continue;
            }


            textInputLayout.setPadding(10, 30, 10, 10);
            textInputLayout.setHint(entry.getKey());
            String etId = entry.getKey().trim();
            et = new EditText(this);
            et.setTag(etId);
            Log.d("id for eit", etId);
            et.setText(entry.getValue());
            et.setTypeface(tf);
            textInputLayout.addView(et);
            et.setEnabled(false);
            et.setClickable(false);
            linearLayout.addView(textInputLayout);

        }

        AppCompatButton appCompatButton = new AppCompatButton(this);
        LayoutParams layoutParams = new LayoutParams(LayoutParams.WRAP_CONTENT,
                LayoutParams.WRAP_CONTENT);
        layoutParams.gravity = Gravity.CENTER;
        layoutParams.setMargins(15, 10, 10, 40); // (left, top, right, bottom)
        appCompatButton.setLayoutParams(layoutParams);
        appCompatButton.setText("Submit");
        appCompatButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
        appCompatButton.setBackground(getResources().getDrawable(R.color.colorbgprofile));
        appCompatButton.setTextColor(getResources().getColor(R.color.white));
        appCompatButton.setTypeface(tf);
        linearLayout.addView(appCompatButton);



    appCompatButton.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            try {
                //LinearLayout l
                Log.d("Key for id", "hi");

                int nViews = linearLayout.getChildCount();
                Log.d("Key for id", "hi"+nViews);
                for (int i = 0; i <= nViews; i++) {
                    View child = linearLayout.getChildAt(i);
                    Log.d("Component type: ",child.getClass().toString());
                    if (child instanceof TextInputLayout) {
                        TextInputLayout txtInputLayout = (TextInputLayout) child;
                        EditText edt = txtInputLayout.getEditText();
                        edt.getTag();
                        edt.getText();
                        Log.d("edt.getText() ", edt.getText().toString());
                        Log.d("Value for", edt.getTag()+"");
                        Log.d("edt.getId( id", edt.getId()+"");

                        Fieldname.put(edt.getTag() + "", edt.getText().toString());
                        //...
                    }else if (child instanceof Spinner) {
                        Spinner txtvw = (Spinner) child;
                        txtvw.getTag();
                        txtvw.getSelectedItem();
                        Log.d("spnr key :", txtvw.getSelectedItem() + "");
                       // Log.d("spnr, value: ", txtvw.getText().toString());
                        Fieldname.put(txtvw.getTag() + "", txtvw.getSelectedItem().toString());
                        Log.d("txtvw.getText(): ", txtvw.getSelectedItem().toString());
//                        Log.d("txtvw.getTag(): ", txtvw.getTag().toString());
                        Log.d("respmap: ",Fieldname.toString());
                    }
                     Gson gson = new Gson();
                     Fieldvalue = gson.toJson(Fieldname);
                    Log.d("Fieldvalue", Fieldvalue.toString());
                    //https://cloud.pockethcm.com/api/v2.0/SaveEmployeeDetail?schemaName=Greyt56af30a136&companyId=1  (POST) send Data in (Fieldname: “” , Fieldvalue : “”)
                }


                Log.d("End of onClick", "bye");
            } catch (Exception e){
                Log.d("Excp ","onlick ",e);
                e.printStackTrace();
            }
            if(FieldName!=null)
            {
                new NetCheck().execute();
            }
        }
    });
    }
    class MyDetailsPost extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();
        private ProgressDialog pDialog;
        private static final String LOGIN_URL= "https://cloud.pockethcm.com/api/v2.0/SaveEmployeeDetail";

        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(MyDetails.this);
            pDialog.setMessage("Please Wait...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }
        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject response=null;
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("schemaName", args[0]);
                params.put("companyId", args[1]);
                params.put("FATHERNAME","Prakash");
                response = jsonParser.makeHttpRequest(
                        LOGIN_URL, "GET", params);
                Log.d("SchemaName", SchemaName.toString());
                Log.d("companyId", CompanyId.toString());
                Log.d("Fieldvalue11",Fieldvalue.toString());

                if (response != null) {
                   // Log.d("marital", response.toString());
                    return response;
                }

            }    catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        protected void onPostExecute(JSONObject json) {


            if (json != null) {

                Log.d("Success!", json.toString());
            }
            pDialog.dismiss();
           /* if (TAG_MESSAGE == TAG_MESSAGE) {
                Log.d("Success!", message);
            } else {
                Log.d("Failure", message);
            }*/
        }


    }


    private class NetCheck extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(MyDetails.this);
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

            pDialog = new ProgressDialog(MyDetails.this);
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            ConstantClass userFunction = new ConstantClass();

            JSONObject json = userFunction.savemydetails(SchemaName, CompanyId, Fieldvalue);
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


    private void showAlert() {
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

        Log.d("responseMap pintu", Fieldname.toString());

        for (Map.Entry<String, String> entry : Fieldname.entrySet()) {
            final RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(280, 200); // Width , height
            TextInputLayout textInputLayout = new TextInputLayout(this);
            String valueStr = entry.getValue();

            String keyStr = entry.getKey();
            TextView textView=new TextView(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setPadding(15, 5, 5, 0);
            textView.setText(entry.getKey()+ "     " + entry.getValue());
          //  layout.setOrientation(LinearLayout.VERTICAL);

            //TextView textView1=new TextView(this);
            //textView1.setText(entry.getValue());
          //  layout.setOrientation(LinearLayout.VERTICAL);
            layout.addView(textView);
           // layout.addView(textView1);
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



    /** This function add the headers to the table **/
    public void addHeaders(){

        TableRow tr;
        TableLayout tl;
        tl=new TableLayout(this);
        /** Create a TableRow dynamically **/
        tr = new TableRow(this);
        tr.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        /** Creating a TextView to add to the row **/
        TextView companyTV = new TextView(this);
        companyTV.setText("Companies");
        companyTV.setTextColor(Color.GRAY);
        companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        companyTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        companyTV.setPadding(5, 5, 5, 0);
        tr.addView(companyTV);  // Adding textView to tablerow.

        /** Creating another textview **/
        TextView valueTV = new TextView(this);
        valueTV.setText("Operating Systems");
        valueTV.setTextColor(Color.GRAY);
        valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
        valueTV.setPadding(5, 5, 5, 0);
        valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(valueTV); // Adding textView to tablerow.

        // Add the TableRow to the TableLayout
        tl.addView(tr, new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        // we are adding two textviews for the divider because we have two columns
        tr = new TableRow(this);
        tr.setLayoutParams(new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));

        /** Creating another textview **/
        TextView divider = new TextView(this);
        divider.setText("-----------------");
        divider.setTextColor(Color.GREEN);
        divider.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        divider.setPadding(5, 0, 0, 0);
        divider.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(divider); // Adding textView to tablerow.

        TextView divider2 = new TextView(this);
        divider2.setText("-------------------------");
        divider2.setTextColor(Color.GREEN);
        divider2.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
        divider2.setPadding(5, 0, 0, 0);
        divider2.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
        tr.addView(divider2); // Adding textView to tablerow.

        // Add the TableRow to the TableLayout
        tl.addView(tr, new LayoutParams(
                LayoutParams.FILL_PARENT,
                LayoutParams.WRAP_CONTENT));
    }

    /** This function add the data to the table **/
    public void addData(){
        TableRow tr;
        TableLayout tl;
        tl=new TableLayout(this);

        for (int i = 0; i <10; i++)
        {
            /** Create a TableRow dynamically **/
            tr = new TableRow(this);
            tr.setLayoutParams(new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));

            /** Creating a TextView to add to the row **/
            TextView  companyTV = new TextView(this);
            companyTV.setText("hi");
            companyTV.setTextColor(Color.RED);
            companyTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            companyTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.WRAP_CONTENT));
            companyTV.setPadding(5, 5, 5, 5);
            tr.addView(companyTV);  // Adding textView to tablerow.

            /** Creating another textview **/
            TextView  valueTV = new TextView(this);
            valueTV.setText("hi");
            valueTV.setTextColor(Color.GREEN);
            valueTV.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
            valueTV.setPadding(5, 5, 5, 5);
            valueTV.setTypeface(Typeface.DEFAULT, Typeface.BOLD);
            tr.addView(valueTV); // Adding textView to tablerow.

            // Add the TableRow to the TableLayout
            tl.addView(tr, new LayoutParams(
                    LayoutParams.FILL_PARENT,
                    LayoutParams.WRAP_CONTENT));
        }
    }

    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id) {
            case Date_Dialog_ID:
                return new DatePickerDialog(this, onDateSet, cYear, cMonth, cDay);
            case Date_Dialog_AN:
                return new DatePickerDialog(this, onDateSetAN, cYearAN, cMonthAN, cDayAN);
            case Date_Dialog_RE:
                return new DatePickerDialog(this, onDateSetRE, cYearRE, cMonthRE, cDayRE);

            case Date_Dialog_DJOIN:
                return new DatePickerDialog(this, onDateSetDJOIN, cYearDJOIN, cMonthDJOIN, cDayDJOIN);

            case Date_Dialog_PF:
                return new DatePickerDialog(this, onDateSetPF, cYearPF, cMonthPF, cDayPF);
            case Date_Dialog_CDATE:
                return new DatePickerDialog(this, onDateSetCDATE, cYearCDATE, cMonthCDATE, cDayCDATE);


        }
        return null;
    }

    private void updateDateDisplay(int year,int month,int date) {
        DATEOFBIRTH.setText(date+"-"+(month)+"-"+year);
    }
    private void updateDateDisplayAN(int year,int month,int date) {
        Dateofnniversary.setText(date + "-" + (month + 1) + "-" + year);
    }
    private void updateDateDisplayRE(int year,int month,int date) {
        Dateofretirement.setText(date + "-" + (month+1) + "-" + year);
    }

    private void updateDateDisplayDJOIN(int year,int month,int date) {
        Dateofjoining.setText(date + "-" + (month+1) + "-" + year);
    }

    private void updateDateDisplayPF(int year,int month,int date) {
        PFdateofjoing.setText(date + "-" + (month + 1) + "-" + year);
    }

    private void updateDateDisplayCDATE(int year,int month,int date) {
        Confirmation_PeriodMonth.setText(date + "-" + (month + 1) + "-" + year);
    }
    DatePickerDialog.OnDateSetListener onDateSetDJOIN=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            System.out.println("2");
            sYearDJOIN=year;
            sMonthDJOIN=monthOfYear;
            sDayDJOIN=dayOfMonth;
            updateDateDisplayDJOIN(sYearDJOIN, sMonthDJOIN,sDayDJOIN);
        }
    };

    DatePickerDialog.OnDateSetListener onDateSetCDATE=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            System.out.println("2");
            sYearCDATE=year;
            sMonthCDATE=monthOfYear;
            sDayCDATE=dayOfMonth;
            updateDateDisplayCDATE(sYearCDATE, sMonthCDATE, sDayCDATE);
        }
    };


    DatePickerDialog.OnDateSetListener onDateSetPF=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            System.out.println("2");
            sYearPF=year;
            sMonthPF=monthOfYear;
            sDayPF=dayOfMonth;
            updateDateDisplayPF(sYearPF, sMonthPF, sDayPF);
        }
    };

    DatePickerDialog.OnDateSetListener onDateSet=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            System.out.println("2");
            sYear=year;
            sMonth=monthOfYear;
            sDay=dayOfMonth;
            updateDateDisplay(sYear,sMonth,sDay);
        }
    };

    DatePickerDialog.OnDateSetListener onDateSetAN=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            System.out.println("2");
            sYearAN=year;
            sMonthAN=monthOfYear;
            sDayAN=dayOfMonth;
            updateDateDisplayAN(sYearAN, sMonthAN, sDayAN);
        }
    };

    DatePickerDialog.OnDateSetListener onDateSetRE=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            System.out.println("2");
            sYearRE=year;
            sMonthRE=monthOfYear;
            sDayRE=dayOfMonth;
            updateDateDisplayRE(sYearRE, sMonthRE, sDayRE);
        }
    };

    class Async_MydetailSubmit extends AsyncTask<String, String, String> {
        // Jsonparser1 jsonParser = new Jsonparser1();
        JSONParser1 jsonParser=new JSONParser1();

        private ProgressDialog pDialog;
        String LOGIN_URL= "http://cloud.pockethcm.com/api/v2.0/TrainingDetail";
        private static final String TAG_SUCCESS = "success";
        private static final String TAG_MESSAGE = "Message";
        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(getApplication());
            pDialog.setMessage("Attempting login...");
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
              /*  params.put("schemaName", SchemaName);
                params.put("employeeId", userId);
                params.put("companyId", CompanyId);*/



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

            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (response != null) {
               /* Toast.makeText(getActivity(), response.toString(),
                        Toast.LENGTH_LONG).show();*/
                Log.d("Training_response ", response.toString());
                Intent intent = new Intent(getApplicationContext(), Training_Details.class);
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
                    //TrainingDetails_setPreference(response);
                    Log.d("Training_setPrefer", response.toString());

                    // }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (TAG_MESSAGE == TAG_MESSAGE) {
                Log.d("Success!", message);
            } else {
                Log.d("Failure", message);
            }
        }

    }


    private String getconvertdate(String date) {
        System.out.println(date.length());
        DateFormat inputFormat = null;
        if(date.length() == 20)
           // inputFormat = new SimpleDateFormat("yyyy-MMM-dd HH:mm:ss",Locale.ENGLISH);
        inputFormat = new SimpleDateFormat("MMM-dd-yyyy HH:mm:ss",Locale.ENGLISH);

        if(date.length() == 10)
            inputFormat = new SimpleDateFormat("yyyy-MM-dd",Locale.ENGLISH) ;

        if(null == inputFormat)
            return "Format invalid";

        inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        DateFormat outputFormat = new SimpleDateFormat("dd-MMM-yyyy", Locale.ENGLISH);
        Date parsed = null;
        try {
            parsed = inputFormat.parse(date);
        } catch (ParseException e) {
            return "Input Date invalid";
        }
        String outputText = outputFormat.format(parsed);
        Log.d("datemillis",outputText);
        DATEOFBIRTH.setText(outputText);
        return outputText;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
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







       @SuppressLint("ValidFragment")
       class DatePickerDialogFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

           public static final int DATE_DISPLAY = 0;
           public static final int PF_DATEOFJOING = 1;
           public static final int DATE_RETIIREMENT = 2;
           public static final int DATE_OFJOING = 3;
           public static final int DATE_OFANIVERSARY = 4;
           public static final int CDATE = 5;

           private int flag = 0;

        /*   public DatePickerDialogFragment() {
               Calendar calendar = Calendar.getInstance();

               SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
               if (flag == DATE_DISPLAY) {
                   calendar.set(year, month, day);
                   DATEOFBIRTH.setText(format.format(calendar.getTime()));
               }           }*/

           @Override
           public Dialog onCreateDialog(Bundle savedInstanceState) {
            /*Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);
*/
               return new DatePickerDialog(getActivity(), this, Year, Month, Day);
           }

           public void setFlag(int i) {
               flag = i;
           }



           @Override
           public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
              Calendar calendar = Calendar.getInstance();

               SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
               if (flag == DATE_DISPLAY) {
                   calendar.set(year, monthOfYear, dayOfMonth);
                   DATEOFBIRTH.setText(format.format(calendar.getTime()));
               } else if (flag == PF_DATEOFJOING) {
                   calendar.set(Year, Month, Day);
                   PFdateofjoing.setText(format.format(calendar.getTime()));
               } else if (flag == DATE_RETIIREMENT) {
                   calendar.set(Year, Month, Day);
                   Dateofretirement.setText(format.format(calendar.getTime()));
               } else if (flag == DATE_OFJOING) {
                   calendar.set(Year, Month, Day);
                   Dateofjoining.setText(format.format(calendar.getTime()));
               } else if (flag == DATE_OFANIVERSARY) {
                   calendar.set(Year, Month, Day);
                   Dateofnniversary.setText(format.format(calendar.getTime()));
               } else if (flag == CDATE) {
                   calendar.set(Year, Month, Day);
                   Cdate.setText(format.format(calendar.getTime()));
               }
           }


         /*  public void show(FragmentManager fragmentManager, int month, int day, int year) {
               Calendar calendar = Calendar.getInstance();

               SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
               if (flag == DATE_DISPLAY) {
                   calendar.set(year, month, day);
                   DATEOFBIRTH.setText(format.format(calendar.getTime()));
               } else if (flag == PF_DATEOFJOING) {
                   calendar.set(year, month, day);
                   PFdateofjoing.setText(format.format(calendar.getTime()));
               } else if (flag == DATE_RETIIREMENT) {
                   calendar.set(year, month, day);
                   Dateofretirement.setText(format.format(calendar.getTime()));
               } else if (flag == DATE_OFJOING) {
                   calendar.set(year, month, day);
                   Dateofjoining.setText(format.format(calendar.getTime()));
               } else if (flag == DATE_OFANIVERSARY) {
                   calendar.set(year, month, day);
                   Dateofnniversary.setText(format.format(calendar.getTime()));
               } else if (flag == CDATE) {
                   calendar.set(year, month, day);
                   Cdate.setText(format.format(calendar.getTime()));
               }
           }*/
       }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }*/

  /*  @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
}




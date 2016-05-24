package br.liveo.ndrawer.ui.activity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
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
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import br.liveo.ndrawer.R;
import br.liveo.ndrawer.adapter.ConstantClass;

/**
 * Created by rupa.dwivedi on 21-01-2016.
 */
public class Additionals_Details extends ActionBarActivity {

    //implements View.OnClickListener {
    private ImageView ib,pfdateofjoing,imgSearchdateofretirement,imgSearchdateofjoining,
            imgSearchdateofnniversary,imgSearchcdate;
    private Calendar cal;


    // private DatePickerDialogFragment mDatePickerDialogFragment;
    private int day;
    private int month;
    private int year;
    ScrollView scrollView;
    LinearLayout linearLayout;
    Spinner spiPRINTCHEQUE,spiBLOODGRP,Branch,METRO_NONMETRO_TDS,Stop_Payment,Location,Grade,PROFTAX_LOCATION;
    EditText Father_Name, Confirmation_PeriodMonth, Reteirment_Age,PFAccount_No,ESI_No, NoOf_Children, PAN_GIRNo_TDS,EMP_PF,Pension_Fund,Tax_Applicable;
    EditText DESIGNERDOJ,DATEEMP,TESTDATE1,Dateofretirement,Dateofjoining,Dateofnniversary,Cdate;
    private String[] matrial= {"Single","Marride"};
    private String[] matrial1= {"Marride","Single"};
    String keyStr,key;
    Button showdata;
    Typeface tf;
    int sDATEEMPDay,sDATEEMPMonth,sDATEEMPYear; // this is the instances of the entered date
    int cDATEEMPDay,cDATEEMPMonth,cDATEEMPYear; // this is the instances of the current date
    int sTESTDATE1Day,sTESTDATE1Month,sTESTDATE1Year; // this is the instances of the entered date
    int cTESTDATE1Day,cTESTDATE1Month,cTESTDATE1Year; // this is the instances of the current date


    int sDay,sMonth,sYear; // this is the instances of the entered date
    int  cDay,cMonth,cYear; // this is the instances of the current date
    final int Date_Dialog_DESI=0,Date_Dialog_DATEEMP=1,Date_Dialog_TESTDATE1=2,Date_Dialog_DJOIN=3,Date_Dialog_PF=4,Date_Dialog_CDATE=5;

   private com.github.clans.fab.FloatingActionButton mFab;
    AppCompatButton appCompatButton;
    Map<String,String> responseMap = new LinkedHashMap<String,String>();

    HashMap<String, String> getMyDetils_MapString = new HashMap<String, String>();
    LinkedHashMap<String, String> lhmap = new LinkedHashMap<String, String>();
    String DESIGNERDOJS,DATEEMPS,TESTDATE1S;
    int   DateDESIGNERDOJ,MonthDESIGNERDOJ,YearDESIGNERDOJ;
    String  DateDESIGNERDOJS,MonthDESIGNERDOJS,YearDESIGNERDOJS;

    int   DateDATEEMP,MonthDATEEMP,YearDATEEMP;
    String  DateDATEEMPS,MonthDATEEMPS,YearDATEEMPS;

    int   DateTESTDATE1,MonthTESTDATE1,YearTESTDATE1;
    String  DateTESTDATE1S,MonthTESTDATE1S,YearTESTDATE1S;
    public  String SchemaName,userId,CompanyId,Fieldvalue;


    private static final String PREF_NAME_PRIFRNCR = "isregisterd";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.additionaldetails);

        String fontPath = "fonts/Roboto-Regular.ttf";
        tf = Typeface.createFromAsset(this.getAssets(), fontPath);

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
        cal = Calendar.getInstance();
        day = cal.get(Calendar.DAY_OF_MONTH);
        month = cal.get(Calendar.MONTH);
        year = cal.get(Calendar.YEAR);

        SharedPreferences settings = getSharedPreferences(
                PREF_NAME_PRIFRNCR, 0);

        Gson gson = new Gson();
        String getMyDetils_String = settings.getString("additionaldetails_string", "additionaldetails_string");
        SchemaName = settings.getString("SchemaName", "SchemaName");
        userId = settings.getString("userId", "userId");
        CompanyId = settings.getString("CompanyId", "CompanyId");


        try {
            JSONObject json = new JSONObject(getMyDetils_String);
            Iterator<?> keys = json.keys();

            while (keys.hasNext()) {
                String key = (String) keys.next();
                String value = json.getString(key);
                lhmap.put(key, value);
                Log.d("setPreference_additiona", lhmap.toString());


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, String> entry : lhmap.entrySet())
        {
            String valueStr = entry.getValue();
            keyStr = entry.getKey();
            Log.d("keyStr get", keyStr.toString());
            Log.d("valueStr get", valueStr.toString());
        }

        for (Map.Entry<String, String> entry : lhmap.entrySet())
        {
            final RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(280, 200); // Width , height
            TextInputLayout textInputLayout = new TextInputLayout(this);
            String valueStr = entry.getValue();
            keyStr = entry.getKey();

            if ("Bloodgroup".equalsIgnoreCase(keyStr)) {

                TextView tv = new TextView(this);
                tv.setText("Bloodgroup");
                tv.setPadding(0, 10, 60, 10);



                String spinnerId = entry.getKey().trim();
                spiBLOODGRP = new Spinner(this);
                spiBLOODGRP.setTag(spinnerId);

                spiBLOODGRP.setFocusable(false);
                spiBLOODGRP.setEnabled(false);
                spiBLOODGRP.setClickable(false);

                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, matrial);
                spiBLOODGRP.setAdapter(adapter_state);
                spiBLOODGRP.setPadding(30, 10, 20, 10);

                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
                ll.setGravity(20);
                ll.addView(tv);
                ll.addView(spiBLOODGRP);
                // setContentView(ll);
                linearLayout.addView(ll);
                continue;
            }


            if ("PrintCheque".equalsIgnoreCase(keyStr)) {

                TextView tv = new TextView(this);
                tv.setText("PrintCheque");
                tv.setPadding(0, 10, 60, 10);

                spiPRINTCHEQUE = new Spinner(this);
                String spinnerId = entry.getKey().trim();
                Spinner spiPRINTCHEQUE = new Spinner(this);
                spiPRINTCHEQUE.setTag(spinnerId);

                spiPRINTCHEQUE.setFocusable(false);
                spiPRINTCHEQUE.setEnabled(false);
                spiPRINTCHEQUE.setClickable(false);

                ArrayAdapter<String> adapter_state = new ArrayAdapter<String>(this,
                        android.R.layout.simple_spinner_item, matrial);
                spiPRINTCHEQUE.setAdapter(adapter_state);
                spiPRINTCHEQUE.setPadding(30, 10, 20, 10);

                LinearLayout ll = new LinearLayout(this);
                ll.setOrientation(LinearLayout.HORIZONTAL);
                ll.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, LinearLayout.LayoutParams.FILL_PARENT));
                ll.setGravity(20);
                ll.addView(tv);
                ll.addView(spiPRINTCHEQUE);
                // setContentView(ll);
                linearLayout.addView(ll);
                continue;
            }
            if ("DESIGNERDOJ".equalsIgnoreCase(keyStr)) {
                textInputLayout.setPadding(10, 30, 10, 10);
                textInputLayout.setHint(entry.getKey());
                String EtId = entry.getKey().trim();
                DESIGNERDOJ = new EditText(this);
                DESIGNERDOJ.setTag(EtId);
                // DATEOFBIRTH.setText(entry.getValue());
                String date =entry.getValue();
                try
                {
                    String s= "9/19/1990 12:00:00 AM";
                    String currentDate = "2014-10-01 00:00:00.0";
                    //   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                   // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                    Date tempDate=simpleDateFormat.parse(date);
                    //  SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    System.out.println("DESIGNERDOJS = " + outputDateFormat.format(tempDate));
                    DESIGNERDOJS=outputDateFormat.format(tempDate);
                    System.out.println("DESIGNERDOJS = " + DESIGNERDOJS);

                    DESIGNERDOJ.setText(DESIGNERDOJS);
                    System.out.println("DESIGNERDOJS = " + DESIGNERDOJS);

                    DESIGNERDOJ.setFocusable(false);
                    DESIGNERDOJ.setEnabled(false);
                    DESIGNERDOJ.setClickable(false);

                    String[] part3 = DESIGNERDOJS.split("/");
                    YearDESIGNERDOJS = part3[0]; // 004
                    YearDESIGNERDOJ=Integer.parseInt(YearDESIGNERDOJS);

                    MonthDESIGNERDOJS = part3[1]; // 034556
                    MonthDESIGNERDOJ=Integer.parseInt(MonthDESIGNERDOJS);

                    DateDESIGNERDOJS= part3[2];
                    DateDESIGNERDOJ=Integer.parseInt(DateDESIGNERDOJS);

                } catch (ParseException ex)
                {
                    System.out.println("Parse Exception");
                }

                DESIGNERDOJ.setTypeface(tf);
                DESIGNERDOJ.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        getResources().getDrawable(R.drawable.calender), null);
                textInputLayout.addView(DESIGNERDOJ);




                DESIGNERDOJ.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
// TODO Auto-generated method stub
//triggers the DatePickerDialog
                        showDialog(Date_Dialog_DESI);
                    }
                });
               /* Calendar cDate=Calendar.getInstance();
                cDate.set(YearDESIGNERDOJ, MonthDESIGNERDOJ, DateDESIGNERDOJ);
                cDate.add(Calendar.MONTH, -1);
                cDay=cDate.get(Calendar.DAY_OF_MONTH);
                cMonth=cDate.get(Calendar.MONTH);
                cYear=cDate.get(Calendar.YEAR);*/

                Calendar cDate=Calendar.getInstance();
                cDate.set(YearDESIGNERDOJ, MonthDESIGNERDOJ-1, DateDESIGNERDOJ);
               // cDate.add(Calendar.MONTH, -1);
                cDay=cDate.get(Calendar.DAY_OF_MONTH);
                cMonth=cDate.get(Calendar.MONTH);
                cYear=cDate.get(Calendar.YEAR);

//assigning the edittext with the current date in the beginning
                sDay=cDay;
                sMonth=cMonth;
                sYear=cYear;
                DateDisplayDES(sYear, sMonth, sDay);

                linearLayout.addView(textInputLayout);
                DESIGNERDOJ.setEnabled(false);
                DESIGNERDOJ.setClickable(false);
                DESIGNERDOJ.setFocusable(false);

                continue;
            }


            if ("TESTDATE1".equalsIgnoreCase(keyStr)) {
                textInputLayout.setPadding(10, 30, 10, 10);
                textInputLayout.setHint(entry.getKey());
                String EtId = entry.getKey().trim();
                TESTDATE1 = new EditText(this);
                TESTDATE1.setTag(EtId);
                // DATEOFBIRTH.setText(entry.getValue());
                String date =entry.getValue();
                try
                {
                    String s= "9/19/1990 12:00:00 AM";
                    String currentDate = "2014-10-01 00:00:00.0";
                    //   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                 //   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                    Date tempDate=simpleDateFormat.parse(date);
                    //  SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    System.out.println("DESIGNERDOJS = " + outputDateFormat.format(tempDate));
                    TESTDATE1S=outputDateFormat.format(tempDate);
                    TESTDATE1.setText(TESTDATE1S);
                    TESTDATE1.setFocusable(false);
                    TESTDATE1.setEnabled(false);
                    TESTDATE1.setClickable(false);


                    String[] part3 = TESTDATE1S.split("/");
                    YearTESTDATE1S = part3[0]; // 004
                    YearTESTDATE1=Integer.parseInt(YearTESTDATE1S);

                    MonthTESTDATE1S = part3[1]; // 034556
                    MonthTESTDATE1=Integer.parseInt(MonthTESTDATE1S);

                    DateTESTDATE1S= part3[2];
                    DateTESTDATE1=Integer.parseInt(DateTESTDATE1S);

                } catch (ParseException ex)
                {
                    System.out.println("Parse Exception");
                }

                TESTDATE1.setTypeface(tf);
                TESTDATE1.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        getResources().getDrawable(R.drawable.calender), null);
                textInputLayout.addView(TESTDATE1);




                TESTDATE1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
// TODO Auto-generated method stub
//triggers the DatePickerDialog
                        showDialog(Date_Dialog_TESTDATE1);
                    }
                });
                Calendar cDate=Calendar.getInstance();
                cDate.set(YearTESTDATE1, MonthTESTDATE1-1, DateTESTDATE1);
               // cDate.add(Calendar.MONTH, -1);
                cTESTDATE1Day=cDate.get(Calendar.DAY_OF_MONTH);
                cTESTDATE1Month=cDate.get(Calendar.MONTH);
                cTESTDATE1Year=cDate.get(Calendar.YEAR);
//assigning the edittext with the current date in the beginning
                sTESTDATE1Day=cTESTDATE1Day;
                sTESTDATE1Month = cTESTDATE1Month;
                sTESTDATE1Year=cTESTDATE1Year;



                DateDisplayTESTDATE1(sTESTDATE1Year, sTESTDATE1Month, sTESTDATE1Day);
                TESTDATE1.setEnabled(false);
                TESTDATE1.setClickable(false);
                TESTDATE1.setFocusable(false);
                linearLayout.addView(textInputLayout);


                continue;
            }



            if ("DATEEMP".equalsIgnoreCase(keyStr)) {
                textInputLayout.setPadding(10, 30, 10, 10);
                textInputLayout.setHint(entry.getKey());
                String EtId = entry.getKey().trim();
                DATEEMP = new EditText(this);
                DATEEMP.setTag(EtId);
                // DATEOFBIRTH.setText(entry.getValue());
                String date =entry.getValue();
                try
                {
                    String s= "9/19/1990 12:00:00 AM";
                    String currentDate = "2014-10-01 00:00:00.0";
                    //   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
                   // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                    Date tempDate=simpleDateFormat.parse(date);
                    //  SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    System.out.println("DESIGNERDOJS = " + outputDateFormat.format(tempDate));
                    DATEEMPS=outputDateFormat.format(tempDate);
                    DATEEMP.setText(DATEEMPS);
                    DATEEMP.setFocusable(false);
                    DATEEMP.setEnabled(false);
                    DATEEMP.setClickable(false);


                    String[] part3 = DATEEMPS.split("/");
                    YearDATEEMPS = part3[0]; // 004
                    YearDATEEMP=Integer.parseInt(YearDATEEMPS);

                    MonthDATEEMPS = part3[1]; // 034556
                    MonthDATEEMP=Integer.parseInt(MonthDATEEMPS);

                    DateDATEEMPS= part3[2];
                    DateDATEEMP=Integer.parseInt(DateDATEEMPS);

                } catch (ParseException ex)
                {
                    System.out.println("Parse Exception");
                }

                DATEEMP.setTypeface(tf);
                DATEEMP.setCompoundDrawablesWithIntrinsicBounds(null, null,
                        getResources().getDrawable(R.drawable.calender), null);
                textInputLayout.addView(DATEEMP);




                DATEEMP.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
// TODO Auto-generated method stub
//triggers the DatePickerDialog
                        showDialog(Date_Dialog_DATEEMP);
                    }
                });
                Calendar cDate=Calendar.getInstance();
                cDate.set(YearDATEEMP, MonthDATEEMP-1, DateDATEEMP);
             //   cDate.add(Calendar.MONTH, -1);
                cDATEEMPDay=cDate.get(Calendar.DAY_OF_MONTH);
                cDATEEMPMonth=cDate.get(Calendar.MONTH);
                cDATEEMPYear=cDate.get(Calendar.YEAR);
//assigning the edittext with the current date in the beginning
                sDATEEMPDay=cDATEEMPDay;
                sDATEEMPMonth=cDATEEMPMonth;
                sDATEEMPYear=cDATEEMPYear;
                DateDisplayDATEEMP(sDATEEMPYear, sDATEEMPMonth, sDATEEMPDay);
                DATEEMP.setEnabled(false);
                DATEEMP.setClickable(false);
                DATEEMP.setFocusable(false);
                linearLayout.addView(textInputLayout);


                continue;
            }


            textInputLayout.setPadding(10, 10, 10, 10);
            textInputLayout.setHint(entry.getKey());
            EditText et = new EditText(this);
            et.setText(entry.getValue());
            String EtdId = entry.getKey().trim();
            et.setTag(EtdId);
            et.setEnabled(false);
            et.setClickable(false);
            textInputLayout.addView(et);
            linearLayout.addView(textInputLayout);

        }

       /* AppCompatButton t = new AppCompatButton(this);
        //  t.setTextColor(getResources().getDrawable(R.color.colorPrimary));
        t.setBackground(getResources().getDrawable(R.color.colorPrimary));
        linearLayout.addView(t);
        t.setText("Submit");*/




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
    @Override
    protected Dialog onCreateDialog(int id) {

        switch (id) {
            case Date_Dialog_DESI:
                return new DatePickerDialog(this, onDateSetDES, cYear, cMonth, cDay);
           case Date_Dialog_TESTDATE1:
                return new DatePickerDialog(this, onDateSetTESTDATE1, cTESTDATE1Year, cTESTDATE1Month, cTESTDATE1Day);
            case Date_Dialog_DATEEMP:
                return new DatePickerDialog(this, onDateSetDATEEMP, cDATEEMPYear, cDATEEMPMonth, cDATEEMPDay);


        }
        return null;
    }
    private void DateDisplayDES(int year,int month,int date) {
        DESIGNERDOJ.setText(date + "-" + (month + 1) + "-" + year);
    }
    DatePickerDialog.OnDateSetListener onDateSetDES=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            System.out.println("2");
            sYear=year;
            sMonth=monthOfYear;
            sDay=dayOfMonth;
            DateDisplayDES(sYear, sMonth, sDay);
        }
    };

    private void DateDisplayDATEEMP(int year,int month,int date) {
        DATEEMP.setText(date + "-" + (month + 1) + "-" + year);
    }
    DatePickerDialog.OnDateSetListener onDateSetDATEEMP=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            System.out.println("2");
            sDATEEMPYear=year;
            sDATEEMPMonth=monthOfYear;
            sDATEEMPDay=dayOfMonth;
            DateDisplayDATEEMP(sDATEEMPYear, sDATEEMPMonth, sDATEEMPDay);
        }
    };
    private void DateDisplayTESTDATE1(int year,int month,int date) {
        TESTDATE1.setText(date + "-" + (month + 1) + "-" + year);
    }
    DatePickerDialog.OnDateSetListener onDateSetTESTDATE1=new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            System.out.println("2");
            sTESTDATE1Year=year;
            sTESTDATE1Month=monthOfYear;
            sTESTDATE1Day=dayOfMonth;
            DateDisplayTESTDATE1(sTESTDATE1Year, sTESTDATE1Month, sTESTDATE1Day);
        }
    };



    private void UpdateDate() {
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
                    if(edt.getText() == null || "".equals(edt.getText())) {

                   /* Log.d("edt.getText() ", edt.getText().toString());
                    Log.d("Value for", edt.getTag()+"");
                    Log.d("edt.getId( id", edt.getId()+"");
*/
                         Toast.makeText(getBaseContext(), "Please Enter"+edt.getTag(), Toast.LENGTH_SHORT).show();

                    }else {
                        responseMap.put(edt.getTag() + "", edt.getText().toString());

                    }
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



    private class NetCheck extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(Additionals_Details.this);
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

            pDialog = new ProgressDialog(Additionals_Details.this);
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
                    edt.setFocusable(true);


                    edt.getText();
                    Log.d("edt.getText() ", edt.getText().toString());

                } else if (child instanceof Spinner) {
                    Spinner txtvw = (Spinner) child;
                    txtvw.getTag();
                    txtvw.setEnabled(true);
                    txtvw.setClickable(true);
                    txtvw.setFocusable(true);


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
    private void findbyView()
    {
        showdata=(Button)findViewById(R.id.hrupdate);
        showdata.setTypeface(tf);
        mFab = (com.github.clans.fab.FloatingActionButton)findViewById(R.id.menu1);
      //  scrollView = (ScrollView) findViewById(R.id.main_schroll);
        linearLayout = (LinearLayout) findViewById(R.id.main_liner);
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




package br.liveo.ndrawer.ui.activity;

import android.app.Activity;
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
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.liveo.ndrawer.R;
import br.liveo.ndrawer.adapter.ConstantClass;

//https://www.binpress.com/tutorial/android-l-recyclerview-and-cardview-tutorial/156
class RecyclerAdapterTranning extends RecyclerView.Adapter<RecyclerAdapterTranning.PersonViewHolder> {
    List<Person1> persons;
    EditText Training_Name,Date_ofCompletion,Certification_Number, Institute;
    private static final String PREF_NAME_PRIFRNCR = "isregisterd";
    String Training_Names,Date_ofCompletions,Certification_Numbers, Institutes;

    String SchemaName, userId, CompanyId,Fieldvalue;

    Map<String, String> responseMap = new LinkedHashMap<String, String>();
    String part1,part44,part66,part55;
    int part4,part6,part5;
    int cDay,cMonth,cYear; // this is the instances of the current date
    int sDay,sMonth,sYear; // this is the instances of the entered date
    final int Date_Dialog_ID=0;
    private Activity activity;
    private int mYear, mMonth, mDay ;

    public RecyclerAdapterTranning context = this;
    CardView cv,cv1,cv2,cv3;

    private com.github.clans.fab.FloatingActionButton delete,edit;
    RecyclerAdapterTranning(Activity activity,ArrayList<Person1> persons) {
        this.persons = persons;
        this.activity = activity;
    }


    @Override
    public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_nominee, parent, false);
        PersonViewHolder pvh = new PersonViewHolder(v);
        delete = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.deletefortraning);
        edit = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.editfortraning);

        cv3 = (CardView) v.findViewById(R.id.card_view);
        cv1 = (CardView) v.findViewById(R.id.employeehistory);
        cv = (CardView) v.findViewById(R.id.card_viewtranning);
        cv2 = (CardView) v.findViewById(R.id.card_viewnominee);
        cv1.setVisibility(View.GONE);
        cv2.setVisibility(View.GONE);
        cv3.setVisibility(View.GONE);

        SharedPreferences setting1 =activity.getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SchemaName = setting1.getString("SchemaName", "SchemaName");
        userId = setting1.getString("userId", "userId");
        CompanyId = setting1.getString("CompanyId", "CompanyId");
        return pvh;
    }
    //do here code copy in RecyclerAdapterNaminee_Details class do same as
    @Override
    public void onBindViewHolder(RecyclerAdapterTranning.PersonViewHolder viewHolder, final int position) {


        viewHolder.Training_Name.setText(persons.get(position).training_name);
        viewHolder.Date_ofCompletion.setText(String.valueOf(persons.get(position).date_ofcompletion));
        viewHolder.Certification_Number.setText(persons.get(position).certification_number);
        viewHolder.Institute.setText(String.valueOf(persons.get(position).institute));

       // viewHolder.cv.setOnClickListener(onClickListener(position));

        viewHolder.Training_Name.setText(persons.get(position).training_name);
        viewHolder.Date_ofCompletion.setText(persons.get(position).date_ofcompletion);

        viewHolder.Certification_Number.setText(persons.get(position).certification_number);
        viewHolder.Institute.setText(persons.get(position).institute);

       /* if (friends.get(position).isGender()) {
            viewHolder.imageView.setImageResource(R.drawable.xxhdpi);
        } else {
            viewHolder.imageView.setImageResource(R.mipmap.female);
        }*/
        //set on click listener for each element
       // viewHolder.cv.setOnClickListener(onClickListener(position));


        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // Toast.makeText(this() "Click on Thumbnail", Toast.LENGTH_LONG).show();
                // Toast.makeText(RecyclerAdapter(), "Click on Thumbnail", Toast.LENGTH_SHORT).show();
                System.out.println("Click on Thumbnail");
                // int position = (Integer) v.getTag();
                openDialog(position);

                //  friends.remove(position);

            }

        });


        viewHolder. edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                LayoutInflater inflater = LayoutInflater.from(activity);
                View dialog_layout = inflater.inflate(R.layout.nominee_diloglayout, null);
                AlertDialog.Builder db = new AlertDialog.Builder(activity);
                db.setView(dialog_layout);

                LinearLayout linearLayout = (LinearLayout) dialog_layout.findViewById(R.id.layout_main_nominee);
                linearLayout.setVisibility(View.GONE);

                db.setTitle("Add Academic Details");
                //activity.setTheme((R.style.MyTheme_ActionBar_TitleTextStyle));

                Training_Name = (EditText) dialog_layout.findViewById(R.id.training_name);
                Date_ofCompletion = (EditText) dialog_layout.findViewById(R.id.dateofcompletion);
                Certification_Number = (EditText) dialog_layout.findViewById(R.id.certificationnumber);
                Institute = (EditText) dialog_layout.findViewById(R.id.institute);

                setDataToView(Training_Name, Date_ofCompletion, Certification_Number, Institute, position);
                Training_Name.setText(Training_Name.getText().toString().trim());
                Date_ofCompletion.setText(Date_ofCompletion.getText().toString().trim());
                Certification_Number.setText(Certification_Number.getText().toString().trim());
                Institute.setText(Institute.getText().toString().trim());
                String date = Date_ofCompletion.getText().toString();
                Log.d("Date", date.toString());
                Date_ofCompletion.setFocusable(false);


                String date1 = Date_ofCompletion.getText().toString();
                Log.d("Date", date1.toString());
                Date_ofCompletion.setFocusable(false);

                String[] part33 = date1.split("/");
                part44 = part33[0]; // 004
                part4=Integer.parseInt(part44);

                part55 = part33[1]; // 034556
                part5=Integer.parseInt(part55);

                part66= part33[2];
                part6=Integer.parseInt(part66);


                Date_ofCompletion.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Calendar cDate = Calendar.getInstance();
                    //    cDate.set(part4, part5 - 1, part6);
                        cDate.set(part6, part5-1, part4);
                        mDay = cDate.get(Calendar.DAY_OF_MONTH);
                        mMonth = cDate.get(Calendar.MONTH);
                        mYear = cDate.get(Calendar.YEAR);


                        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                // txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                Date_ofCompletion.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                            }
                        }, mYear, mMonth, mDay);
                        datePickerDialog.show();
                    }
                });

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
                        //new AsyncMY_Details().execute();

                        //   String name = etInput.getText().toString();
                        // Toast.makeText(getBaseContext(), name, Toast.LENGTH_SHORT).show();
                    }
                });
                AlertDialog alertDialog = db.create();

                // show it
                alertDialog.show();


                Button buttonbackground = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonbackground.setTextColor(activity.getResources().getColor(R.color.colorAccent));


                Button buttonbackground1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                //buttonbackground1.setBackgroundColor(Color.BLUE);
                buttonbackground1.setTextColor(activity.getResources().getColor(R.color.colorAccent));
            }

        });
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
            nDialog = new ProgressDialog(activity);
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
            ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
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

            pDialog = new ProgressDialog(activity);
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


    private void setDataToView(TextView training_name, TextView date_ofcompletion,TextView certification_number,TextView institute,int position) {
        training_name.setText(persons.get(position).training_name);
        date_ofcompletion.setText(persons.get(position).date_ofcompletion);
        certification_number.setText(persons.get(position).certification_number);
        institute.setText(persons.get(position).institute);
    }


    private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.item_recycler_nominee);
                dialog.setTitle("Position " + position);
                dialog.setCancelable(true); // dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                TextView training_name = (TextView) dialog.findViewById(R.id.training_name);
                TextView date_ofcompletion = (TextView) dialog.findViewById(R.id.date_ofcompletion);
                TextView certification_number = (TextView) dialog.findViewById(R.id.certification_number);
                TextView institute = (TextView) dialog.findViewById(R.id.institute);



                setDataToView(training_name, date_ofcompletion,certification_number, institute,position);
                //String Name, String Address, String Relation, String Dateof_Birth, String Age
                dialog.show();
            }
        };
    }

    public void openDialog(final int position) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activity);

        // set title
        alertDialogBuilder.setTitle("Alert");
        // set dialog message
        alertDialogBuilder
                .setMessage("Do you really want to delete?")
                .setCancelable(true)

                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close

                                dialog.cancel();
                                persons.remove(position);

                                notifyDataSetChanged();
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
    @Override
    public int getItemCount() {
        if (persons != null) {
            return persons.size();
        }
        return 0;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {
        CardView cv,cv1,cv2,cv3;
        TextView training_name;
        TextView  Training_Name,Date_ofCompletion,Certification_Number, Institute;

        private com.github.clans.fab.FloatingActionButton delete,edit;
        PersonViewHolder(View itemView) {
            super(itemView);
            cv3 = (CardView) itemView.findViewById(R.id.card_view);
            cv1 = (CardView) itemView.findViewById(R.id.employeehistory);
            cv = (CardView) itemView.findViewById(R.id.card_viewtranning);
            cv2 = (CardView) itemView.findViewById(R.id.card_viewnominee);
            cv1.setVisibility(View.GONE);
            cv2.setVisibility(View.GONE);
            cv3.setVisibility(View.GONE);

            Training_Name = (TextView) itemView.findViewById(R.id.training_name);
            Date_ofCompletion = (TextView) itemView.findViewById(R.id.date_ofcompletion);
            Certification_Number = (TextView) itemView.findViewById(R.id.certification_number);
            Institute = (TextView) itemView.findViewById(R.id.institute);

            delete = (com.github.clans.fab.FloatingActionButton) itemView.findViewById(R.id.deletefortraning);
            edit = (com.github.clans.fab.FloatingActionButton) itemView.findViewById(R.id.editfortraning);
            cv = (CardView) itemView.findViewById(R.id.card_viewtranning);
        }
    }
}

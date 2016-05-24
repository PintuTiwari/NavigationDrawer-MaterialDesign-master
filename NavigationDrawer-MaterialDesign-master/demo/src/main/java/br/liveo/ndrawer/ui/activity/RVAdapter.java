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
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.liveo.ndrawer.R;
import br.liveo.ndrawer.adapter.ConstantClass;

//http://stackoverflow.com/questions/11028386/initialize-date-in-android-datepicker-to-a-specific-date-that-is-not-the-curren
class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {
   List<Person> persons;
    EditText Company_Name,Position,From_Date,To_Date;

    private Activity activity;
    public RVAdapter context = this;
    CardView cv,cv1,cv2,cv3;
    private int mYear, mMonth, mDay ;
    private int mYear1, mMonth1, mDay1, mHour, mMinute;
    private static final String PREF_NAME_PRIFRNCR = "isregisterd";

    String part44,part66,part55;
    int part4,part6,part5;

    String To_Date1,To_Date2,To_Date3;
    int To_Date11,To_Date22,To_Date33;
    String   Company_Names,Positions,From_Dates, To_Dates,SchemaName, userId, CompanyId,Fieldvalue;
    Map<String, String> responseMap = new LinkedHashMap<String, String>();


    private com.github.clans.fab.FloatingActionButton delete,edit;
   RVAdapter(Activity activity,List<Person> persons) {
       this.persons = persons;
       this.activity = activity;
   }


   @Override
   public PersonViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recycler_nominee, parent, false);
       PersonViewHolder pvh = new PersonViewHolder(v);
       delete = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.deleteforemplyoment);
       edit = (com.github.clans.fab.FloatingActionButton) v.findViewById(R.id.editforemployment);
       cv3 = (CardView) v.findViewById(R.id.card_view);
       cv = (CardView) v.findViewById(R.id.employeehistory);
       cv1 = (CardView) v.findViewById(R.id.card_viewtranning);
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
   public void onBindViewHolder(RVAdapter.PersonViewHolder viewHolder, final int position) {

       viewHolder.companyname.setText(persons.get(position).Company_Name);
       viewHolder.Position.setText(String.valueOf(persons.get(position).Position));

       viewHolder.fromdate.setText(persons.get(position).From_Date);
       viewHolder.todate.setText(String.valueOf(persons.get(position).To_Date));

     //  viewHolder.cv.setOnClickListener(onClickListener(position));


       //setting data to view holder elements

       viewHolder.companyname.setText(persons.get(position).Company_Name);
       viewHolder.Position.setText(persons.get(position).Position);

       viewHolder.fromdate.setText(persons.get(position).From_Date);
       viewHolder.todate.setText(persons.get(position).To_Date);

       /* if (friends.get(position).isGender()) {
            viewHolder.imageView.setImageResource(R.drawable.xxhdpi);
        } else {
            viewHolder.imageView.setImageResource(R.mipmap.female);
        }*/
       //set on click listener for each element
       //viewHolder.cv.setOnClickListener(onClickListener(position));


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
               View dialog_layout = inflater.inflate(R.layout.employemnt_addlayout, null);
               AlertDialog.Builder db = new AlertDialog.Builder(activity);
               db.setView(dialog_layout);

              /* LinearLayout linearLayout1=(LinearLayout)dialog_layout.findViewById(R.id.layout_mainlanguage);
               linearLayout1.setVisibility(View.GONE);
               LinearLayout linearLayout2=(LinearLayout)dialog_layout.findViewById(R.id.layout_main_joining_documents);
               linearLayout2.setVisibility(View.GONE);

               LinearLayout linearLayout3=(LinearLayout)dialog_layout.findViewById(R.id.layout_maintraning_details);
               linearLayout3.setVisibility(View.GONE);*/

               db.setTitle("Add Employment Details");
               // activity.setTheme((R.style.MyTheme_ActionBar_TitleTextStyle));
               System.out.println("Click on Thumbnail");
               Company_Name = (EditText) dialog_layout.findViewById(R.id.companyname);
               Position = (EditText) dialog_layout.findViewById(R.id.position);
               From_Date = (EditText) dialog_layout.findViewById(R.id.fromdate);
               To_Date = (EditText) dialog_layout.findViewById(R.id.todate);


               setDataToView(Company_Name, Position, From_Date, To_Date, position);
               Company_Name.setText(Company_Name.getText().toString().trim());
               Position.setText(Position.getText().toString().trim());
               From_Date.setText(From_Date.getText().toString().trim());
               To_Date.setText(To_Date.getText().toString().trim());
               To_Date.setFocusable(false);
               From_Date.setFocusable(false);

               String date = String.valueOf(To_Date.getText());

               if(date == null || "".equals(date)){

                   To_Date.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {

                           Calendar cDate = Calendar.getInstance();
                           //   cDate.set(part4, part5 - 1, part6);
                           cDate.set(To_Date33, To_Date22 - 1, To_Date11);

                           mDay = cDate.get(Calendar.DAY_OF_MONTH);
                           mMonth = cDate.get(Calendar.MONTH);
                           mYear = cDate.get(Calendar.YEAR);


                           DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {

                               @Override
                               public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                   To_Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                               }
                           }, mYear, mMonth, mDay);
                           datePickerDialog.show();
                       }
                   });

               }else{

                   Log.d("Date", date.toString());

                   To_Date.setFocusable(false);
                   String[] part3 = date.split("/");
                   To_Date1 = part3[0]; // 004
                   To_Date11 = Integer.parseInt(To_Date1);

                   To_Date2 = part3[1]; // 034556
                   To_Date22 = Integer.parseInt(To_Date2);

                   To_Date3 = part3[2];
                   To_Date33 = Integer.parseInt(To_Date3);
                   To_Date.setOnClickListener(new View.OnClickListener() {
                       @Override
                       public void onClick(View v) {

                           Calendar cDate = Calendar.getInstance();
                           //   cDate.set(part4, part5 - 1, part6);
                           cDate.set(To_Date33, To_Date22 - 1, To_Date11);

                           mDay = cDate.get(Calendar.DAY_OF_MONTH);
                           mMonth = cDate.get(Calendar.MONTH);
                           mYear = cDate.get(Calendar.YEAR);


                           DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {

                               @Override
                               public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                   To_Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                               }
                           }, mYear, mMonth, mDay);
                           datePickerDialog.show();
                       }
                   });

               }

               String date1 = From_Date.getText().toString();
               Log.d("Date", date1.toString());

               String[] part33 = date1.split("/");
               part44 = part33[0]; // 004
               part4 = Integer.parseInt(part44);

               part55 = part33[1]; // 034556
               part5 = Integer.parseInt(part55);

               part66 = part33[2];
               part6 = Integer.parseInt(part66);


               From_Date.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {

                       Calendar cDate = Calendar.getInstance();
                       //   cDate.set(part4, part5 - 1, part6);
                       cDate.set(part6, part5 - 1, part4);
                       mDay = cDate.get(Calendar.DAY_OF_MONTH);
                       mMonth = cDate.get(Calendar.MONTH);
                       mYear = cDate.get(Calendar.YEAR);


                       DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {

                           @Override
                           public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                               // txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                               From_Date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);

                           }
                       }, mYear, mMonth, mDay);
                       datePickerDialog.show();
                   }
               });

               //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
               // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.a");

                   /* SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                    Date tempDate=simpleDateFormat.parse(date);
                    System.out.println("DATEOFBIRTH1 tempDate = " + tempDate);

                    //  SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    System.out.println("DATEOFBIRTH1 = " + outputDateFormat.format(tempDate));
                   // Dateof_Birth.setText(Dateof_Birth1);
                    Dateof_Birth1=outputDateFormat.format(tempDate);*/


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
                       // new AsyncMY_Details().execute();
                       UpdateDate();
                       //   String name = etInput.getText().toString();
                       // Toast.makeText(getBaseContext(), name, Toast.LENGTH_SHORT).show();
                   }
               });
               AlertDialog alertDialog = db.create();

               // show it
               alertDialog.show();


               Button buttonbackground = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
               //  buttonbackground.setTextColor(activity.getResources().getColor(R.color.colorAccent));


               Button buttonbackground1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
               //buttonbackground1.setBackgroundColor(Color.BLUE);
               //   buttonbackground1.setTextColor(activity.getResources().getColor(R.color.colorAccent));
           }

       });
   }



         /*  viewHolder. edit.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(final View v) {
                   System.out.println("Click on Thumbnail");
                   EditText Company_Name,Position,From_Date,To_Date;

                   Company_Name = (EditText)activity.findViewById(R.id.companyname);
                   Position = (EditText) activity.findViewById(R.id.position);
                   From_Date = (EditText)activity.findViewById(R.id.fromdate);
                   To_Date = (EditText) activity.findViewById(R.id.todate);



                   setDataToView(Company_Name, Position,From_Date,To_Date,position);
                   Company_Name.setText(Company_Name.getText().toString().trim());
                   Position.setText(Position.getText().toString().trim());
                   From_Date.setText(From_Date.getText().toString().trim());
                   To_Date.setText(To_Date.getText().toString().trim());
               }

           });
       }*/


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


    private void setDataToView(TextView Company_Name, TextView Position,TextView From_Date,TextView To_Date,int position) {
        Company_Name.setText(persons.get(position).Company_Name);
        Position.setText(persons.get(position).Position);
        From_Date.setText(persons.get(position).From_Date);
        To_Date.setText(persons.get(position).To_Date);
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
                TextView Company_Name = (TextView) dialog.findViewById(R.id.companyname);
                TextView Position = (TextView) dialog.findViewById(R.id.position);
                TextView From_Date = (TextView) dialog.findViewById(R.id.fromdate);
                TextView To_Date = (TextView) dialog.findViewById(R.id.todate);

                setDataToView(Company_Name, Position,From_Date, To_Date,position);
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
       TextView companyname;
       TextView Position,fromdate,todate;
       private com.github.clans.fab.FloatingActionButton delete,edit;
       PersonViewHolder(View itemView) {
           super(itemView);
           cv3 = (CardView) itemView.findViewById(R.id.card_view);
           cv = (CardView) itemView.findViewById(R.id.employeehistory);
           cv1 = (CardView) itemView.findViewById(R.id.card_viewtranning);
           cv2 = (CardView) itemView.findViewById(R.id.card_viewnominee);
           cv1.setVisibility(View.GONE);
           cv2.setVisibility(View.GONE);
           cv3.setVisibility(View.GONE);

           companyname = (TextView) itemView.findViewById(R.id.companyname);
           Position = (TextView) itemView.findViewById(R.id.position);
           fromdate = (TextView) itemView.findViewById(R.id.fromdate);
           todate = (TextView) itemView.findViewById(R.id.todate);

           delete = (com.github.clans.fab.FloatingActionButton) itemView.findViewById(R.id.deleteforemplyoment);
           edit = (com.github.clans.fab.FloatingActionButton) itemView.findViewById(R.id.editforemployment);
       }
   }
}

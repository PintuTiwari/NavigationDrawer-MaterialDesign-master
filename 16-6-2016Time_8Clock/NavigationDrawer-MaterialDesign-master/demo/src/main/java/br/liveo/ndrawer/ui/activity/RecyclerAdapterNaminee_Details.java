package br.liveo.ndrawer.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
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

//https://www.binpress.com/tutorial/android-l-recyclerview-and-cardview-tutorial/156
public class RecyclerAdapterNaminee_Details extends RecyclerView.Adapter<RecyclerAdapterNaminee_Details.ViewHolder> {
    String part1,part44,part66,part55;
    int part4,part6,part5;
    private List<Person> friends;
    private Activity activity;
    CardView cv,cv1,cv2,cv3;
    private int mYear, mMonth, mDay, mHour, mMinute;
    public RecyclerAdapterNaminee_Details context = this;
    private com.github.clans.fab.FloatingActionButton delete,edit;
    EditText Name_Nominee,Address_Nominee,Nominee_Relation, Amount_Paidto_Nominee,Dateof_Birth, NameAddress_ofGuardian,Age;
    private static final String PREF_NAME_PRIFRNCR = "isregisterd";
    String Name_Nominees, Address_Nominees, Nominee_Relations, Dateof_Births,NameAddress_ofGuardians,Amount_Paidto_Nominees, Ages,SchemaName, userId, CompanyId,Fieldvalue;

    Map<String, String> responseMap = new LinkedHashMap<String, String>();
    public RecyclerAdapterNaminee_Details(Activity activity, List<Person> friends) {
        this.friends = friends;
        this.activity = activity;
    }




    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //inflate your layout and pass it to view holder
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.nomeeitem, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

    /*    cv3 = (CardView) view.findViewById(R.id.card_view);
        cv = (CardView) view.findViewById(R.id.employeehistory);
        cv1 = (CardView) view.findViewById(R.id.card_viewtranning);

        cv1.setVisibility(View.GONE);
        cv3.setVisibility(View.GONE);
        cv.setVisibility(View.GONE);
*/
    /*    CardView linearLayout1=(CardView)view.findViewById(R.id.card_view);
        linearLayout1.setVisibility(View.GONE);
*/
        delete = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.delete);
        edit = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.edit);

        SharedPreferences setting1 =activity.getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SchemaName = setting1.getString("SchemaName", "SchemaName");
        userId = setting1.getString("userId", "userId");
        CompanyId = setting1.getString("CompanyId", "CompanyId");

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterNaminee_Details.ViewHolder viewHolder, final int position) {

        //setting data to view holder elements

        viewHolder.Name_Nominee1.setText(friends.get(position).Name);
        viewHolder.Address_Nominee1.setText(friends.get(position).Address);
        viewHolder.Nominee_Relation1.setText(friends.get(position).Relation);
        viewHolder.Amount_Paidto_Nominee1.setText(friends.get(position).AmountPaidToNominee);
        viewHolder.Dateof_Birth1.setText(friends.get(position).DOB);
        viewHolder.NameAddress_ofGuardian1.setText(friends.get(position).Name_Address_Guardian);
        viewHolder.Age1.setText(friends.get(position).Age);


       /* if (friends.get(position).isGender()) {
            viewHolder.imageView.setImageResource(R.drawable.xxhdpi);
        } else {
            viewHolder.imageView.setImageResource(R.mipmap.female);
        }*/
        //set on click listener for each element
      //  viewHolder.container.setOnClickListener(onClickListener(position));


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
                // Toast.makeText(this() "Click on Thumbnail", Toast.LENGTH_LONG).show();
                // Toast.makeText(RecyclerAdapter(), "Click on Thumbnail", Toast.LENGTH_SHORT).show();
                System.out.println("Click on Thumbnail");
                Name_Nominee = (EditText)activity.findViewById(R.id.name_nominee);
                Address_Nominee = (EditText)activity. findViewById(R.id.address_nominee);
                Nominee_Relation = (EditText) activity.findViewById(R.id.nominee_relation);
                Dateof_Birth = (EditText)activity.findViewById(R.id.dob);
                Amount_Paidto_Nominee = (EditText)activity. findViewById(R.id.ammountpaid_nominee);
                NameAddress_ofGuardian = (EditText)activity. findViewById(R.id.naddress_guardian);
                Age = (EditText) activity.findViewById(R.id.age_nominee);

                setDataToView(Name_Nominee, Address_Nominee, Nominee_Relation, Dateof_Birth,Amount_Paidto_Nominee,NameAddress_ofGuardian, Age, position);


                Name_Nominee.setText(Name_Nominee.getText().toString().trim());
                Address_Nominee.setText( Address_Nominee.getText().toString().trim());
                Nominee_Relation.setText( Nominee_Relation.getText().toString().trim());
                Dateof_Birth.setText( Dateof_Birth.getText().toString().trim());
                Amount_Paidto_Nominee.setText( Amount_Paidto_Nominee.getText().toString().trim());
                NameAddress_ofGuardian.setText( NameAddress_ofGuardian.getText().toString().trim());
                Age.setText( Age.getText().toString().trim());
            }

        });



        viewHolder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                LayoutInflater inflater = LayoutInflater.from(activity);
                View dialog_layout = inflater.inflate(R.layout.nominee_diloglayout, null);
                AlertDialog.Builder db = new AlertDialog.Builder(activity);
                db.setView(dialog_layout);

                LinearLayout linearLayout = (LinearLayout) dialog_layout.findViewById(R.id.layout_main_traning);
                linearLayout.setVisibility(View.GONE);
                db.setTitle("Add Academic Details");
                //activity.setTheme((R.style.MyTheme_ActionBar_TitleTextStyle));

                Name_Nominee = (EditText)dialog_layout.findViewById(R.id.name_nominee);
                Address_Nominee = (EditText)dialog_layout. findViewById(R.id.address_nominee);
                Nominee_Relation = (EditText) dialog_layout.findViewById(R.id.nominee_relation);
                Dateof_Birth = (EditText)dialog_layout.findViewById(R.id.dob);
                Amount_Paidto_Nominee = (EditText)dialog_layout. findViewById(R.id.ammountpaid_nominee);
                NameAddress_ofGuardian = (EditText)dialog_layout. findViewById(R.id.naddress_guardian);
                Age = (EditText) dialog_layout.findViewById(R.id.age_nominee);



                setDataToView(Name_Nominee, Address_Nominee, Nominee_Relation, Dateof_Birth,Amount_Paidto_Nominee,NameAddress_ofGuardian, Age, position);

                Name_Nominee.setText(Name_Nominee.getText().toString().trim());
                Address_Nominee.setText(Address_Nominee.getText().toString().trim());
                Nominee_Relation.setText( Nominee_Relation.getText().toString().trim());
                Dateof_Birth.setText(Dateof_Birth.getText().toString().trim());
                Amount_Paidto_Nominee.setText(Amount_Paidto_Nominee.getText().toString().trim());
                NameAddress_ofGuardian.setText(NameAddress_ofGuardian.getText().toString().trim());
                Age.setText(Age.getText().toString().trim());

                String date = Dateof_Birth.getText().toString();
                Log.d("Date", date.toString());
                Dateof_Birth.setFocusable(false);

                String[] part3 = date.split("/");
                part44 = part3[0]; // 004
                part4=Integer.parseInt(part44);

                part55 = part3[1]; // 034556
                part5=Integer.parseInt(part55);

                part66= part3[2];
                part6=Integer.parseInt(part66);


                Dateof_Birth.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // listener.onItemClicked(v);
                        // Get Current Date
                  /* final Calendar c = Calendar.getInstance();
                   mYear = c.get(Calendar.YEAR);
                   mMonth = c.get(Calendar.MONTH);
                   mDay = c.get(Calendar.DAY_OF_MONTH);
*/
                        Calendar cDate= Calendar.getInstance();
                      //  cDate.set(part4, part5-1, part6);
                        cDate.set(part6, part5-1, part4);
                        mDay=cDate.get(Calendar.DAY_OF_MONTH);
                        mMonth=cDate.get(Calendar.MONTH);
                        mYear=cDate.get(Calendar.YEAR);



                        DatePickerDialog datePickerDialog = new DatePickerDialog(activity, new DatePickerDialog.OnDateSetListener() {

                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                                // txtDate.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                                Dateof_Birth.setText(dayOfMonth + "-" + (monthOfYear +1 ) + "-" + year);

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
                        //new AsyncMY_Details().execute();
                        UpdateDate();
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

        Name_Nominees=Name_Nominee.getText().toString();
        Address_Nominees= Address_Nominee.getText().toString();
        Nominee_Relations=  Nominee_Relation.getText().toString();
        Dateof_Births=  Dateof_Birth.getText().toString();
        Amount_Paidto_Nominees= Amount_Paidto_Nominee.getText().toString();
        NameAddress_ofGuardians= NameAddress_ofGuardian.getText().toString();
        Ages=Age.getText().toString();

        responseMap.put("Name", Name_Nominees);
        responseMap.put("Address",Address_Nominees);
        responseMap.put("Relation",Nominee_Relations);
        responseMap.put("DOB",Dateof_Births);
        responseMap.put("Name_Address_Guardian", NameAddress_ofGuardians);
        responseMap.put("AmountPaidToNominee", Amount_Paidto_Nominees);
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

   /* private void setDataToView(TextView name, TextView job, ImageView genderIcon, int position) {
        name.setText(friends.get(position).getName());
        job.setText(friends.get(position).getJob());
        if (friends.get(position).isGender()) {
            genderIcon.setImageResource(R.mipmap.male);
        } else {
            genderIcon.setImageResource(R.mipmap.female);
        }
    }*/

    private void setDataToView(TextView Name_Nominee, TextView Address_Nominee, TextView Nominee_Relation, TextView Dateof_Birth,
                               TextView Amount_Paidto_Nominee,TextView NameAddress_ofGuardian,TextView Age,int position) {


        Name_Nominee.setText(friends.get(position).Name);
        Address_Nominee.setText(friends.get(position).Address);
        Nominee_Relation.setText(friends.get(position).Relation);
        Dateof_Birth.setText(friends.get(position).DOB);
        Amount_Paidto_Nominee.setText(friends.get(position).AmountPaidToNominee);
        NameAddress_ofGuardian.setText(friends.get(position).Name_Address_Guardian);
        Age.setText(friends.get(position).Age);

        
        /*if (friends.get(position).isGender()) {
            genderIcon.setImageResource(R.mipmap.male);
        } else {
            genderIcon.setImageResource(R.mipmap.female);
        }*/
    }

  //  String Name, String Address, String Relation, String Dateof_Birth, String Ag

    @Override
    public int getItemCount() {
        return (null != friends ? friends.size() : 0);
    }

   /* private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.item_recycler_nominee);
                dialog.setTitle("Position " + position);
                dialog.setCancelable(true); // dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                TextView Name_Nominee = (TextView) dialog.findViewById(R.id.name);
                TextView Address_Nominee = (TextView) dialog.findViewById(R.id.address);
                TextView Nominee_Relation = (TextView) dialog.findViewById(R.id.relation);
                TextView Dateof_Birth = (TextView) dialog.findViewById(R.id.dateofbirth);
                TextView Age = (TextView) dialog.findViewById(R.id.age);
               // Button  Delete = (Button) view.findViewById(R.id.delete);


               // setDataToView(Name, Address, Relation,Dateof_Birth,Age, position);
                setDataToView(Name_Nominee, Address_Nominee, Nominee_Relation, Dateof_Birth,Amount_Paidto_Nominee,NameAddress_ofGuardian, Age, position);

                //String Name, String Address, String Relation, String Dateof_Birth, String Age
                dialog.show();
            }
        };
    }*/



   /* public void Delete(View v) {
        int position = (Integer) v.getTag();
        openDialog(position);

    }*/

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
                                friends.remove(position);

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


    /**
     * View holder to display each RecylerView item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
       private com.github.clans.fab.FloatingActionButton delete,edit;
        TextView Name_Nominee1,Address_Nominee1,Nominee_Relation1, Amount_Paidto_Nominee1,
                Dateof_Birth1, NameAddress_ofGuardian1,Age1;

        private View container;

        public ViewHolder(View view) {
            super(view);
          //  imageView = (ImageView) view.findViewById(R.id.image);
            Name_Nominee1 = (TextView) view.findViewById(R.id.name_nominee);
            Address_Nominee1 = (TextView) view.findViewById(R.id.address_nominee);
            Nominee_Relation1 = (TextView) view.findViewById(R.id.relation_nominee);
            Amount_Paidto_Nominee1 = (TextView) view.findViewById(R.id.amountpaid_nominee);
            Dateof_Birth1 = (TextView) view.findViewById(R.id.dob);
            NameAddress_ofGuardian1 = (TextView) view.findViewById(R.id.address_guardin);
            Age1 = (TextView) view.findViewById(R.id.age_nominee);

            delete = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.delete);
            edit = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.edit);

            container = view.findViewById(R.id.card_viewnominee);
        }


    }
}
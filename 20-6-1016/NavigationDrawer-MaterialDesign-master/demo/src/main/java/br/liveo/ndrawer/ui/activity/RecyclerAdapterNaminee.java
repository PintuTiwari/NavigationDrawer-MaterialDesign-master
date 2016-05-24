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

import br.liveo.interfaces.OnItemClickListener;
import br.liveo.ndrawer.R;
import br.liveo.ndrawer.adapter.ConstantClass;

//https://www.binpress.com/tutorial/android-l-recyclerview-and-cardview-tutorial/156
public class RecyclerAdapterNaminee extends RecyclerView.Adapter<RecyclerAdapterNaminee.ViewHolder>  {
    String part1,part44,part66,part55;
    int part4,part6,part5;
    private List<Person> friends;
    private Activity activity;
    private OnItemClickListener listener;
    String Nominee,Dateof_Birth1;
    int cDay,cMonth,cYear; // this is the instances of the current date
    int sDay,sMonth,sYear; // this is the instances of the entered date

    final int Date_Dialog_ID=0;
    public RecyclerAdapterNaminee context = this;
   private com.github.clans.fab.FloatingActionButton delete,edit;
    EditText Name,Address,Relation,Dateof_Birth,Age;
    private int mYear, mMonth, mDay, mHour, mMinute;
    private static final String PREF_NAME_PRIFRNCR = "isregisterd";

    String Names, Addresss, Relations, Ages,Dateof_Births, SchemaName, userId, CompanyId,Fieldvalue;

    Map<String, String> responseMap = new LinkedHashMap<String, String>();


   /* public RecyclerAdapterNaminee(Activity activity, List<Person> friends ,OnItemClickListener listener) {
        this.friends = friends;
        this.activity = activity;
        this.listener = listener;
    }

*/

    public RecyclerAdapterNaminee(Activity activity, List<Person> friends) {
        this.friends = friends;
        this.activity = activity;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //inflate your layout and pass it to view holder
      LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_nominee, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        CardView linearLayout1=(CardView)view.findViewById(R.id.employeehistory);
        linearLayout1.setVisibility(View.GONE);

        CardView linearLayout2=(CardView)view.findViewById(R.id.card_viewtranning);
        linearLayout2.setVisibility(View.GONE);

        CardView linearLayout3=(CardView)view.findViewById(R.id.card_viewnominee);
        linearLayout3.setVisibility(View.GONE);


        delete = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.delete);
        edit = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.edit);

        SharedPreferences setting1 =activity.getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SchemaName = setting1.getString("SchemaName", "SchemaName");
        userId = setting1.getString("userId", "userId");
        CompanyId = setting1.getString("CompanyId", "CompanyId");


        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterNaminee.ViewHolder viewHolder, final int position) {

        //setting data to view holder elements
        viewHolder.name1.setText(friends.get(position).Company_Name);
        viewHolder.address.setText(friends.get(position).Position);
        viewHolder.Relation.setText(friends.get(position).From_Date);
        viewHolder.dob.setText(friends.get(position).To_Date);
        viewHolder.age.setText(friends.get(position).Training_Name);


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

        /*viewHolder.edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                listener.onItemClicked( v);

            }

        });
    }*/
   viewHolder. edit.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(final View v) {
           LayoutInflater inflater = LayoutInflater.from(activity);
           View dialog_layout = inflater.inflate(R.layout.dilog, null);
           AlertDialog.Builder db = new AlertDialog.Builder(activity);
           db.setView(dialog_layout);

           LinearLayout linearLayout = (LinearLayout) dialog_layout.findViewById(R.id.layout_academic);
           linearLayout.setVisibility(View.GONE);
           LinearLayout linearLayout1 = (LinearLayout) dialog_layout.findViewById(R.id.layout_main_nominee);
           linearLayout1.setVisibility(View.GONE);
           LinearLayout linearLayout2 = (LinearLayout) dialog_layout.findViewById(R.id.layout_foremergency);
           linearLayout2.setVisibility(View.GONE);

           db.setTitle("Add Family Details");
           // activity.setTheme((R.style.MyTheme_ActionBar_TitleTextStyle));
           System.out.println("Click on Thumbnail");
           Name = (EditText) dialog_layout.findViewById(R.id.name);
           Address = (EditText) dialog_layout.findViewById(R.id.address);
           Relation = (EditText) dialog_layout.findViewById(R.id.relation);
           Dateof_Birth = (EditText) dialog_layout.findViewById(R.id.dateofbirth);
           Age = (EditText) dialog_layout.findViewById(R.id.age);


           setDataToView(Name, Address, Relation, Dateof_Birth, Age, position);
           Name.setText(Name.getText().toString().trim());
           Address.setText(Address.getText().toString().trim());
           Relation.setText(Relation.getText().toString().trim());
           //  Dateof_Birth.setText( Dateof_Birth.getText().toString().trim());
           Age.setText(Age.getText().toString().trim());
           String date = Dateof_Birth.getText().toString();
           Log.d("Date", date.toString());
           Dateof_Birth.setFocusable(false);

         /*  //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
           SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

           Date tempDate= null;
           try {
               tempDate = simpleDateFormat.parse(date);
           } catch (ParseException e) {
               e.printStackTrace();
           }
           System.out.println("DATEOFBIRTH1 tempDate = " + tempDate);

           //  SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
           SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy/MM/dd");
           System.out.println("DATEOFBIRTH1 = " + outputDateFormat.format(tempDate));
           String date1=outputDateFormat.format(tempDate);*/
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
                   cDate.set(part6, part5-1, part4);
                   //                   cDate.set(part4, part5-1, part6);

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


           //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss a");
           // SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.a");

                /*   SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                Date tempDate= null;
                try {
                    tempDate = simpleDateFormat.parse(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                System.out.println("DATEOFBIRTH1 tempDate = " + tempDate);

                    //  SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                    SimpleDateFormat outputDateFormat = new SimpleDateFormat("yyyy/MM/dd");
                    System.out.println("DATEOFBIRTH1 = " + outputDateFormat.format(tempDate));
                   // Dateof_Birth.setText(Dateof_Birth1);
                    Dateof_Birth1=outputDateFormat.format(tempDate);
                    String[] part3 = date.split("/");
                    part44 = part3[0]; // 004
                    part4=Integer.parseInt(part44);

                    part55 = part3[1]; // 034556
                    part5=Integer.parseInt(part55);

                    part66= part3[2];
                    part6=Integer.parseInt(part66);

                //  SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");


                Dateof_Birth.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
// TODO Auto-generated method stub
//triggers the DatePickerDialog
                        activity.showDialog(Date_Dialog_ID);
                    }
                });
               Calendar cDate= Calendar.getInstance();
                cDate.set(part4, part5, part6);

              cDate.add(Calendar.MONTH, 0);

                cDay=cDate.get(Calendar.DAY_OF_MONTH);
                cMonth=cDate.get(Calendar.MONTH);
                cYear=cDate.get(Calendar.YEAR);
//assigning the edittext with the current date in the beginning
                sDay=cDay;
                sMonth=cMonth;
                sYear=cYear;
                updateDateDisplay(sYear, sMonth, sDay);*/
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
           buttonbackground.setTextColor(activity.getResources().getColor(R.color.colorAccent));


           Button buttonbackground1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
           //buttonbackground1.setBackgroundColor(Color.BLUE);
           buttonbackground1.setTextColor(activity.getResources().getColor(R.color.colorAccent));
       }

   });
    }




    private void UpdateDate() {
        Names = Name.getText().toString();
        Addresss = Address.getText().toString();
        Relations = Relation.getText().toString();
        Ages = Age.getText().toString();
        Dateof_Births = Dateof_Birth.getText().toString();

        responseMap.put("Name", Names);
        responseMap.put("Address", Addresss);
        responseMap.put("Relation", Relations);
        responseMap.put("DOB", Dateof_Births);
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

    private void setDataToView(TextView Name, TextView Address, TextView Relation, TextView Dateof_Birth,TextView Age,int position) {
        Name.setText(friends.get(position).Company_Name);
        Address.setText(friends.get(position).Position);
        Relation.setText(friends.get(position).From_Date);
        Dateof_Birth.setText(friends.get(position).To_Date);
        Age.setText(friends.get(position).Training_Name);
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

    private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.item_recycler_nominee);
                dialog.setTitle("Position " + position);
                dialog.setCancelable(true); // dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                TextView Name = (TextView) dialog.findViewById(R.id.name);
                TextView Address = (TextView) dialog.findViewById(R.id.address);
                TextView Relation = (TextView) dialog.findViewById(R.id.relation);
                TextView Dateof_Birth = (TextView) dialog.findViewById(R.id.dateofbirth);
                TextView Age = (TextView) dialog.findViewById(R.id.ageforfamily);
               // Button  Delete = (Button) view.findViewById(R.id.delete);


                setDataToView(Name, Address, Relation,Dateof_Birth,Age, position);
                //String Name, String Address, String Relation, String Dateof_Birth, String Age
                dialog.show();
            }
        };
    }



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
        private TextView name1;
        private TextView address;
        private TextView Relation;
        private TextView dob;
        private TextView age;
        private com.github.clans.fab.FloatingActionButton delete,edit;

        private View container;

        public ViewHolder(View view) {
            super(view);
          //  imageView = (ImageView) view.findViewById(R.id.image);
            name1 = (TextView) view.findViewById(R.id.name);
            address = (TextView) view.findViewById(R.id.address);
            dob = (TextView) view.findViewById(R.id.dateofbirth);
            Relation = (TextView) view.findViewById(R.id.relation);
            age = (TextView) view.findViewById(R.id.ageforfamily);
            delete = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.delete);
            edit = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.edit);
            container = view.findViewById(R.id.card_view);
        }


    }


}
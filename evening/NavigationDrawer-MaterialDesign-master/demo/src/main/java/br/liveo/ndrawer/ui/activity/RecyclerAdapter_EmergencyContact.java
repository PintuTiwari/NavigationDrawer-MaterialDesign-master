package br.liveo.ndrawer.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.liveo.ndrawer.R;
import br.liveo.ndrawer.adapter.ConstantClass;
import br.liveo.ndrawer.adapter.JSONParser;

/**
 * Created by rupa.dwivedi on 28-01-2016.
 */
public class RecyclerAdapter_EmergencyContact extends RecyclerView.Adapter<RecyclerAdapter_EmergencyContact.ViewHolder> {

    private List<Person> friends;
    private Activity activity;
    private com.github.clans.fab.FloatingActionButton delete,edit;
    private static final String PREF_NAME_PRIFRNCR = "isregisterd";
    Map<String, String> responseMap = new LinkedHashMap<String, String>();

    public RecyclerAdapter_EmergencyContact context = this;
    EditText Name,Address,Relation,ContactNumber;
    TextView CardNameTitel,CardAddressTitel,CardRelationTitel,CardContact_NumberTitel;
    String Names, Addresss, Relations, ContactNumbers, SchemaName, userId, CompanyId,Fieldvalue;


    public RecyclerAdapter_EmergencyContact(Activity activity, List<Person> friends) {
        this.friends = friends;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //inflate your layout and pass it to view holder
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.itrm_recivala_damic, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);

        CardNameTitel = (TextView) view.findViewById(R.id.cardtitel1);
        CardAddressTitel = (TextView) view.findViewById(R.id.cardtitel2);
        CardRelationTitel = (TextView) view.findViewById(R.id.cardtitel3);
        CardContact_NumberTitel = (TextView) view.findViewById(R.id.cardtitel4);
        CardNameTitel.setText("Name");
        CardAddressTitel.setText("Address");
        CardRelationTitel.setText("Relation");
        CardContact_NumberTitel.setText("Cuntact Number");

        delete = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.delete);
        edit = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.edit);

        SharedPreferences setting1 =activity.getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SchemaName = setting1.getString("SchemaName", "SchemaName");
        userId = setting1.getString("userId", "userId");
        CompanyId = setting1.getString("CompanyId", "CompanyId");



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter_EmergencyContact.ViewHolder viewHolder, final int position) {

        //setting data to view holder elements
      /*  viewHolder.CardNamevalue.setText(friends.get(position).Company_Name);
        viewHolder.CardAddressvalue.setText(friends.get(position).Position);
        viewHolder.CardRelationvalue.setText(friends.get(position).From_Date);
        viewHolder.CardContact_Numbervalue.setText(friends.get(position).To_Date);*/

        viewHolder.Degree.setText(friends.get(position).Company_Name);
        viewHolder.Year_ofpassing.setText(friends.get(position).Position);
        viewHolder.Institution_name.setText(friends.get(position).From_Date);
        viewHolder.Grade_class.setText(friends.get(position).To_Date);
        //viewHolder.container.setOnClickListener(onClickListener(position));




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
                View dialog_layout = inflater.inflate(R.layout.dilog, null);
                AlertDialog.Builder db = new AlertDialog.Builder(activity);
                db.setView(dialog_layout);
                LinearLayout linearLayout=(LinearLayout) dialog_layout.findViewById(R.id.layout_main);
                linearLayout.setVisibility(View.GONE);
                LinearLayout linearLayout1 = (LinearLayout) dialog_layout.findViewById(R.id.layout_main_nominee);
                linearLayout1.setVisibility(View.GONE);
                LinearLayout linearLayout2 = (LinearLayout) dialog_layout.findViewById(R.id.layout_academic);
                linearLayout2.setVisibility(View.GONE);

                db.setTitle("Add Emergency contact Details");
                //activity.setTheme((R.style.MyTheme_ActionBar_TitleTextStyle));

                Name = (EditText) dialog_layout.findViewById(R.id.nameE);
                Address = (EditText) dialog_layout.findViewById(R.id.addressE);
                Relation = (EditText) dialog_layout.findViewById(R.id.relationE);
                ContactNumber = (EditText) dialog_layout.findViewById(R.id.contact_numberE);

                setDataToView(Name, Address, Relation, ContactNumber, position);
                Name.setText(Name.getText().toString().trim());
                System.out.println("Click" + Name.getText().toString().trim());
                Address.setText( Address.getText().toString().trim());
                Relation.setText( Relation.getText().toString().trim());
                ContactNumber.setText( ContactNumber.getText().toString().trim());

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
                     //   new AsyncMY_Details().execute();
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
        Names=Name.getText().toString();
        Addresss= Address.getText().toString();
        Relations= Relation.getText().toString();
        ContactNumbers= ContactNumber.getText().toString();

        responseMap.put("Name", Name.getText().toString());
        responseMap.put("Address", Address.getText().toString());
        responseMap.put("Relation", Relation.getText().toString());
        responseMap.put("ContactNumber", ContactNumber.getText().toString());
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

            JSONObject json = userFunction.saveemergencycontact(SchemaName, CompanyId, Fieldvalue);
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


    private void setDataToView(TextView Name, TextView Address, TextView Relation, TextView ContactNumber,int position) {
        Name.setText(friends.get(position).Company_Name);
        Address.setText(friends.get(position).Position);
        Relation.setText(friends.get(position).From_Date);
        ContactNumber.setText(friends.get(position).To_Date);

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
                dialog.setContentView(R.layout.itrm_recivala_damic);
                dialog.setTitle("Position " + position);
                dialog.setCancelable(true); // dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                TextView Name = (TextView) dialog.findViewById(R.id.name);
                TextView Address = (TextView) dialog.findViewById(R.id.address);
                TextView Relation = (TextView) dialog.findViewById(R.id.relation);
                TextView Dateof_Birth = (TextView) dialog.findViewById(R.id.dateofbirth);
                TextView Age = (TextView) dialog.findViewById(R.id.age);
                // Button  Delete = (Button) view.findViewById(R.id.delete);


                setDataToView(Name, Address, Relation,Dateof_Birth,position);
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
        TextView CardNamevalue,CardAddressvalue,CardRelationvalue,CardContact_Numbervalue;
        private TextView Degree;
        private TextView Year_ofpassing;
        private TextView Institution_name;
        private TextView Grade_class;
        private com.github.clans.fab.FloatingActionButton delete,edit;

        private View container;

        public ViewHolder(View view) {
            super(view);
           /* CardNamevalue = (TextView) view.findViewById(R.id.degree);
            CardAddressvalue = (TextView) view.findViewById(R.id.year_ofpassing);
            CardRelationvalue = (TextView) view.findViewById(R.id.institution);
            CardContact_Numbervalue = (TextView) view.findViewById(R.id.grade_class);*/
            Degree = (TextView) view.findViewById(R.id.degree);
            Year_ofpassing = (TextView) view.findViewById(R.id.year_ofpassing);
            Institution_name = (TextView) view.findViewById(R.id.institution);
            Grade_class = (TextView) view.findViewById(R.id.grade_class);

            delete = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.delete);
            edit = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.edit);
            container = view.findViewById(R.id.card_view);
        }


    }

    class AsyncMY_Details extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();
        private ProgressDialog pDialog;
        private static final String LOGIN_URL= "http://cloud.pockethcm.com/api/v2.0/EmployeeDetail";
        private static final String TAG_MESSAGE = "Message";
        @Override
        protected void onPreExecute()
        {
            pDialog = new ProgressDialog(activity);
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

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
import android.support.design.widget.FloatingActionButton;
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
public class RecyclerAdapterAcadmic extends RecyclerView.Adapter<RecyclerAdapterAcadmic.ViewHolder> {
    private com.github.clans.fab.FloatingActionButton delete,edit;
    private List<Person> friends;
    private Activity activity;
    String Degrees, Year_ofpassings, Institution_names, Grade_classs, SchemaName, userId, CompanyId,Fieldvalue;
    Map<String, String> responseMap = new LinkedHashMap<String, String>();

    public RecyclerAdapterAcadmic context = this;
    private static final String PREF_NAME_PRIFRNCR = "isregisterd";

    FloatingActionButton Save;
    EditText Degree,Year_ofpassing,Institution_name,Grade_class;
    AsyncTask<String, String, Boolean> execute;
    public RecyclerAdapterAcadmic(Activity activity, List<Person> friends) {
        this.friends = friends;
        this.activity = activity;
        this.execute=execute;
    }

    @Override
    public int getItemViewType(int position) {
        Log.i("getItemVi(position)", "position=" + position);
        //If position is 0 this means we need to use FirstOpenRestaurantVHolder
        if (position == 0) {
            return 0;

        } else if (position > 0) {
            return 1;
        }


        return 1; //TODO ojo con este default
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //inflate your layout and pass it to view holder
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.itrm_recivala_damic, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        delete = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.delete);
        edit = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.edit);
        SharedPreferences setting1 =activity.getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SchemaName = setting1.getString("SchemaName", "SchemaName");
        userId = setting1.getString("userId", "userId");
        CompanyId = setting1.getString("CompanyId", "CompanyId");

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterAcadmic.ViewHolder viewHolder, final int position) {

        //setting data to view holder elements
         viewHolder.Degree.setText(friends.get(position).Company_Name);
         viewHolder.Year_ofpassing.setText(friends.get(position).Position);
         viewHolder.Institution_name.setText(friends.get(position).From_Date);
         viewHolder.Grade_class.setText(friends.get(position).To_Date);
        // viewHolder.container.setOnClickListener(onClickListener(position));

        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                System.out.println("Click on Thumbnail");
                // int position = (Integer) v.getTag();
                openDialog(position);


                //  friends.remove(position);
            }
        });


        viewHolder. edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                Log.d("viewHolder", "viewHolder");

                LayoutInflater inflater = LayoutInflater.from(activity);
                View dialog_layout = inflater.inflate(R.layout.dilog, null);
                AlertDialog.Builder db = new AlertDialog.Builder(activity);
                db.setView(dialog_layout);

                LinearLayout linearLayout=(LinearLayout) dialog_layout.findViewById(R.id.layout_main);
                linearLayout.setVisibility(View.GONE);
                LinearLayout linearLayout1 = (LinearLayout) dialog_layout.findViewById(R.id.layout_main_nominee);
                linearLayout1.setVisibility(View.GONE);

                LinearLayout linearLayout3 = (LinearLayout) dialog_layout.findViewById(R.id.layout_foremergency);
                linearLayout3.setVisibility(View.GONE);

                db.setTitle("Add Academic Details");
             //   activity.setTheme((R.style.MyTheme_ActionBar_TitleTextStyle));

                Degree = (EditText)dialog_layout.findViewById(R.id.degree);
                Year_ofpassing = (EditText)dialog_layout. findViewById(R.id.year_ofpassing);
                Institution_name = (EditText) dialog_layout.findViewById(R.id.institution_name);
                Grade_class = (EditText)dialog_layout.findViewById(R.id.grade_class);

                setDataToView(Degree, Year_ofpassing, Institution_name, Grade_class, position);

                Degree.setText(Degree.getText().toString().trim());
                System.out.println("Click" + Year_ofpassing.getText().toString().trim());
                Year_ofpassing.setText(Year_ofpassing.getText().toString().trim());
                Institution_name.setText(Institution_name.getText().toString().trim());
                Grade_class.setText(Grade_class.getText().toString().trim());


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
                alertDialog.show();

                Button buttonbackground = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonbackground.setTextColor(activity.getResources().getColor(R.color.colorAccent));
                Button buttonbackground1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
                //buttonbackground1.setBackgroundColor(Color.BLUE);
                buttonbackground1.setTextColor(activity.getResources().getColor(R.color.colorAccent));

            }


        });



        }

    private void UpdateDate()
    {
        Degrees = Degree.getText().toString();
        Year_ofpassings = Year_ofpassing.getText().toString();
        Institution_names = Institution_name.getText().toString();
        Grade_classs = Grade_class.getText().toString();
        responseMap.put("DegreeName", Degree.getText().toString());
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
        //new AsyncMY_Details().execute();
                /*  if (networkMan.isConnected()){
                 new AsyncMY_Details().execute();
                 //   new MenuGetAsync().execute(SchemaName, CompanyId, userId, "Admin");
                }*/
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


    private void setDataToView(TextView Degree, TextView Year_ofpassing, TextView Institution_name, TextView Grade_class,int position) {
        Degree.setText(friends.get(position).Company_Name);
        Year_ofpassing.setText(friends.get(position).Position);
        Institution_name.setText(friends.get(position).From_Date);
        Grade_class.setText(friends.get(position).To_Date);

    }

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
                TextView Degree = (TextView) dialog.findViewById(R.id.degree);
                TextView Year_ofpassing = (TextView) dialog.findViewById(R.id.year_ofpassing);
                TextView Institution_name = (TextView) dialog.findViewById(R.id.institution);
                TextView Grade_class = (TextView) dialog.findViewById(R.id.grade_class);

                // Button  Delete = (Button) view.findViewById(R.id.delete);

                setDataToView(Degree, Year_ofpassing, Institution_name,Grade_class, position);
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
      // activity.setTheme((R.style.MyTheme_ActionBar_TitleTextStyle));

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
                              //  execute.execute();

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


        Button buttonbackground = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonbackground.setTextColor(activity.getResources().getColor(R.color.colorAccent));

        Button buttonbackground1 = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE);
        //buttonbackground1.setBackgroundColor(Color.BLUE);
        buttonbackground1.setTextColor(activity.getResources().getColor(R.color.colorAccent));

    }


    /**
     * View holder to display each RecylerView item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {

        private TextView Degree;
        private TextView Year_ofpassing;
        private TextView Institution_name;
        private TextView Grade_class;
        private com.github.clans.fab.FloatingActionButton delete,edit;


        private View container;

        public ViewHolder(View view) {
            super(view);
            //  imageView = (ImageView) view.findViewById(R.id.image);
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
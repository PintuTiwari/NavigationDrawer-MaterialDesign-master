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
import android.widget.CheckBox;
import android.widget.EditText;
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

/**
 * Created by rupa.dwivedi on 28-01-2016.
 */
public class RecyclerAdapterEmpLanguage extends RecyclerView.Adapter<RecyclerAdapterEmpLanguage.ViewHolder> {

    private List<Person> friends;
    private Activity activity;
   private com.github.clans.fab.FloatingActionButton delete,edit;
    EditText Language;
    RecyclerAdapterEmpLanguage context=this;
    CheckBox Can_Read;
    CheckBox Can_Write;
    CheckBox Can_Speak;
    String SchemaName;
    String userId;
    String CompanyId;
    String Fieldvalue;
    View.OnClickListener checkBoxListener;
    private static final String PREF_NAME_PRIFRNCR = "isregisterd";
    Map<String, String> responseMap = new LinkedHashMap<String, String>();


    public RecyclerAdapterEmpLanguage(Activity activity, List<Person> friends) {
        this.friends = friends;
        this.activity = activity;
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
        View view = inflater.inflate(R.layout.item_language, viewGroup, false);
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
    public void onBindViewHolder(RecyclerAdapterEmpLanguage.ViewHolder viewHolder, final int position) {

        //setting data to view holder elementsviewHolder.Language.setText(friends.get(position).Company_Name());
        viewHolder.Language.setText(friends.get(position).Company_Name);
        viewHolder.Can_Read.setText(friends.get(position).Position);
        viewHolder.Can_Write.setText(friends.get(position).From_Date);
        viewHolder.Can_Speak.setText(friends.get(position).To_Date);



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
                LayoutInflater inflater = LayoutInflater.from(activity);
                View dialog_layout = inflater.inflate(R.layout.employeelanguageui, null);
                AlertDialog.Builder db = new AlertDialog.Builder(activity);
                db.setView(dialog_layout);

                db.setTitle("Add Language Details");
                Language = (EditText)dialog_layout.findViewById(R.id.language);
                Can_Read = (CheckBox)dialog_layout. findViewById(R.id.canread);
                Can_Write = (CheckBox) dialog_layout.findViewById(R.id.canwrite);
                Can_Speak = (CheckBox)dialog_layout.findViewById(R.id.canspeak);


                Language.setText(friends.get(position).Company_Name);



                String canread=(friends.get(position).Position);
                String canwrite=(friends.get(position).From_Date);
                String canspeck=(friends.get(position).To_Date);

               // setDataToView(Language, Can_Read, Can_Write, Can_Speak, position);
                Language.setText(Language.getText().toString().trim());



               // Can_Read.setText(Can_Read.getText().toString().trim());
               // Can_Write.setText(Can_Write.getText().toString().trim());
               // Can_Speak.setText(Can_Speak.getText().toString().trim());

                if("Yes".equalsIgnoreCase(canread))
                { Can_Read.setChecked(true);

                }else {
                    Can_Read.setChecked(false);


                }
                if("Yes".equalsIgnoreCase(canwrite))
                {Can_Write.setChecked(true);
                }else {
                    Can_Write.setChecked(false);
                }

                if("Yes".equalsIgnoreCase(canspeck))
                {Can_Speak.setChecked(true);
                }else {
                    Can_Speak.setChecked(false);

                }

             /*  checkBoxListener =new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        String Read,Write,Speak;

                        if(Can_Write.isChecked()) {
                            //   Write= Can_Write.getText().toString();
                            Write="Can_ Write";
                            // String   chetimeslot = (String)Can_Write.getTag();
                            Log.d("hi", Write);


                        }

                        if(Can_Read.isChecked()) {
                            //Read= Can_Read.getText().toString();
                            Read="Can_Read";
                            Log.d("hi", Read);
                        }

                        if(Can_Speak.isChecked()) {
                            // Speak= Can_Speak.getTag().toString();
                            Speak="Can_Speak";
                            Log.d("hi", Speak);
                        }
                    }
                };*/
                /*
                Can_Write.setOnClickListener(checkBoxListener);
                Can_Read.setOnClickListener(checkBoxListener);
                Can_Speak.setOnClickListener(checkBoxListener);
                Language.setText(Language.getText().toString().trim());
               setDataToView(Language, Can_Read, Can_Write, Can_Speak, position);
               // setDataToView(Name, position);
                Language.setText(Language.getText().toString().trim());
                Can_Read.setText( Can_Read.getText().toString().trim());
                Can_Write.setText( Can_Write.getText().toString().trim());
                Can_Speak.setText( Can_Speak.getText().toString().trim());

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
*/


       /* LinearLayout linearLayout = (LinearLayout) dialog_layout.findViewById(R.id.layout_main_joining_documents);
        linearLayout.setVisibility(View.GONE);
        LinearLayout linearLayout1 = (LinearLayout) dialog_layout.findViewById(R.id.layout_mainemployeehistory);
        linearLayout1.setVisibility(View.GONE);
        LinearLayout linearLayout2 = (LinearLayout) dialog_layout.findViewById(R.id.layout_maintraning_details);
        linearLayout2.setVisibility(View.GONE);*/

      /*  Language = (EditText) dialog_layout.findViewById(R.id.language);
        Can_Read = (CheckBox) dialog_layout.findViewById(R.id.canread);
        Can_Write = (CheckBox)dialog_layout.findViewById(R.id.canwrite);
        Can_Speak = (CheckBox) dialog_layout.findViewById(R.id.canspeak);
*/
                db.setTitle("Add Employee Language Details");
                // setTitleColor(R.color.colorAccent);0
               //activity.setTheme(R.style.MyTheme_ActionBar_TitleTextStyle);
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

                    }
                });
                AlertDialog alert11 = db.create();
                alert11.show();

                Button buttonbackground = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
                buttonbackground.setTextColor(activity.getResources().getColor(R.color.colorAccent));

                Button buttonbackground1 = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
                //buttonbackground1.setBackgroundColor(Color.BLUE);
                buttonbackground1.setTextColor(activity.getResources().getColor(R.color.colorAccent));

            }

        });
    }
    private void UpdateDate() {
        String Languages = Language.getText().toString();
        String Can_Read1= String.valueOf(Can_Read.isChecked());
        String Can_Write1 = String.valueOf(Can_Write.isChecked());
        String Can_Speak1  = String.valueOf(Can_Speak.isChecked());

        responseMap.put("Language", Languages);
        responseMap.put("IsRead", Can_Read1);
        responseMap.put("IsWrite", Can_Write1);
        responseMap.put("IsSpeak", Can_Speak1);
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

            JSONObject json = userFunction.saveemployeelanguage(SchemaName, CompanyId, Fieldvalue);
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
   private void setDataToView(TextView Language, TextView Can_Readt, TextView Can_Writet, TextView Can_Speakt,int position) {

       Language.setText(friends.get(position).Company_Name);
       String Can_Read1 =friends.get(position).Position;


       Can_Readt.setText(friends.get(position).Position);
       Can_Writet.setText(friends.get(position).From_Date);
       Can_Speakt.setText(friends.get(position).To_Date);

      /* Log.d("Can_Read1",Can_Read1);
       if(Can_Read1=="Yes")
       {
           Can_Read.isChecked();
       }
       String Can_Write1 =(friends.get(position).From_Date);
       Log.d("Can_Write1", Can_Write1);
       if(Can_Write1=="Yes")
       {
           Can_Write.isChecked();
       } String Can_Speak1 =friends.get(position).To_Date;
       Log.d("Can_Speak1", Can_Speak1);

       if(Can_Speak1=="Yes")
       {
           Can_Speak.isChecked();
       }

*/



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
                dialog.setContentView(R.layout.item_language);
                dialog.setTitle("Position " + position);
                dialog.setCancelable(true); // dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                TextView Language = (TextView) dialog.findViewById(R.id.language);
                TextView Can_Readt = (TextView) dialog.findViewById(R.id.canread);
                TextView Can_Writet = (TextView) dialog.findViewById(R.id.canwrite);
                TextView Can_Speakt = (TextView) dialog.findViewById(R.id.canspeak);

                // Button  Delete = (Button) view.findViewById(R.id.delete);


            //   setDataToView(Language, Can_Readt, Can_Writet,Can_Speakt,position);
               // setDataToView(Name,position);
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

        private TextView Language;
        private TextView Can_Read;
        private TextView Can_Write;
        private TextView Can_Speak;
        private com.github.clans.fab.FloatingActionButton delete,edit;

        private View container;

        public ViewHolder(View view) {
            super(view);
            //  imageView = (ImageView) view.findViewById(R.id.image);
            Language = (TextView) view.findViewById(R.id.language);
            Can_Read = (TextView) view.findViewById(R.id.canread);
            Can_Write = (TextView) view.findViewById(R.id.canwrite);
            Can_Speak = (TextView) view.findViewById(R.id.canspeak);

            delete = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.delete);
            edit = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.edit);
            container = view.findViewById(R.id.card_view);
        }


    }
}
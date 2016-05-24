package br.liveo.ndrawer.ui.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Iterator;

import br.liveo.ndrawer.R;
import br.liveo.ndrawer.adapter.JSONParser;
import br.liveo.ndrawer.adapter.JSONParser1;
import br.liveo.ndrawer.adapter.NetworkMan;
import br.liveo.ndrawer.adapter.UserSessionManager;
import butterknife.Bind;
import butterknife.ButterKnife;
//    http://cloud.pockethcm.com/api/v2.0/EmployeeDetail?userId=1&schemaName=Greyt56af30a136&companyId=45

/*textInputLayout
card_viewtranning
 */
/*EmployementHistory
TrainingDetail
NomineeDetail
FamilyDetail
LanguageDetail
EmergencyContachDetail
AcademicDetail
AdditionalDetail
PersonalDetail
EmployeeDetail*/
//http://www.materialup.com/posts/adam-lapinski-s-photo-on-google
/*remove item
http://theopentutorials.com/tutorials/android/listview/android-expandable-list-view-example/*/

/*json  spinner
http://www.androidbegin.com/tutorial/android-populating-spinner-json-tutorial/*/
public class Login extends AppCompatActivity
{
    public static final String PREFS_NAME = "MyPrefsFile";
    private static final String PREF_USERNAME = "savusername";
    private static final String PREF_PASSWORD = "savpassword";
    private static final String PREF_PASSWORD1 = "savpassword1";
    CheckBox savepass;
    public  String SchemaName,userId,CompanyId,invalid,Role,Role_String;

    String menu_String;
    private ProgressDialog pDialog;
    private static final String PREF_NAME_PRIFRNCR = "isregisterd";
    private static final String PREF_NAME_PRIFRNCR1 = "menue";
    private static final String PREFRENCES_NAME = "Name You Like";
    Context context = this;
    String Company_code, Employee_code, password,myDetailshashMapString,my_ProfilehashMapString,respone_Status;
    @Bind(R.id.companycode) EditText Companycode;
    @Bind(R.id.employeecode) EditText Employeecode;
    @Bind(R.id.password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    private NetworkMan networkMan;
    boolean isUserLoggedIn = true;
    // User Session Manager Class
    UserSessionManager session;
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  logout();
        session = new UserSessionManager(getApplicationContext());
        isUserLoggedIn = session.isUserLoggedIn();
        if(isUserLoggedIn)
        {
            Intent startMainPage = new Intent(Login.this, NavigationActivity.class);
            startMainPage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(startMainPage);
            finish();
        }
        else{

            Intent startMainPage = new Intent(Login.this, Login.class);
            startMainPage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            }

        //Pref();
        // logout();
          /*SharedPreferences    prefsNagSetting = context.getSharedPreferences(PREFS_NAME, 0);
// GET THE NAG SETTING
            String blNagSetting = prefsNagSetting.getString("Company_code", null);
          //  Log.d("hi", blNagSetting);
            if (blNagSetting != null)   {
              //  Log.d("hi", blNagSetting);

                Intent startMainPage = new Intent(Login.this, NavigationActivity.class);
                startMainPage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(startMainPage);
                finish();

            } else { // NO NEED FOR THE else BLOCK. JUST AN ILLUSTRATION

                Intent startMainPage = new Intent(Login.this, Login.class);
                startMainPage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
               // Log.d("hi", blNagSetting);
            }*/

        setContentView(R.layout.activity_login);
        savepass= (CheckBox)findViewById(R.id.checkBox);
        ButterKnife.bind(this);



        networkMan = new NetworkMan(this);
        _loginButton.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                login();
            }
        });
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Please wait...");
        pDialog.setCancelable(false);
        //    SharedPreferences pref = getSharedPreferences(PREFS_NAME,MODE_PRIVATE);
        SharedPreferences setting = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

            /*Companycode.setText(setting.getString(PREF_USERNAME, null));
            Employeecode.setText(setting.getString(PREF_PASSWORD, null));
            _passwordText.setText(setting.getString(PREF_PASSWORD1, null));
            SharedPreferences setting1 = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

            Companycode.setText(setting1.getString("Company_code", null));

            Employeecode.setText(setting.getString(PREF_PASSWORD, null));
            _passwordText.setText(setting.getString(PREF_PASSWORD1, null));

            String Companycode = setting1.getString("Company_code", null);
            Log.d("hi", Companycode);*/
    }

    /*public  void logout(){
        SharedPreferences sharedpreferences = this.getSharedPreferences(Login.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }*/

    public void logout(){
        SharedPreferences settings = getSharedPreferences(
                PREFRENCES_NAME, Context.MODE_PRIVATE);
        settings.edit().clear().commit();

     /*   SharedPreferences sharedpreferences = this.getSharedPreferences(Login.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();*/
    }

    public void login() {
        // proceedLogin();
        run();

    }



    public void run() {
        // TODO Auto-generated method stub

        if (Companycode.getText().length() == 0) {

            ShowAlert.displayAlert(Login.this, "Please enter valid Username");
            Companycode.requestFocus();
            return;
        }

        if (Employeecode.getText().length() == 0) {
            ShowAlert.displayAlert(Login.this, "Please enter valid Password");
            Employeecode.requestFocus();
            return;
        }


        if (_passwordText.getText().length() == 0) {
            ShowAlert.displayAlert(Login.this, "Please enter valid Password");
            _passwordText.requestFocus();
            return;
        }
        /*
        String username = strUserName.getText().toString();
        String password = strPassword.getText().toString();*/
        /*Company_code = Companycode.getText().toString();
        Employee_code = Employeecode.getText().toString();
        password = _passwordText.getText().toString();*/

        if (savepass.isChecked()) {
            SharedPreferences setting = context.getSharedPreferences(
                    PREFS_NAME, 0);
//           getSharedPreferences(PREFS_NAME, MODE_PRIVATE)
//                    .edit()
//                    .putString(PREF_USERNAME, Company_code)
//                    .putString(PREF_PASSWORD, Employee_code)
//                    .putString(PREF_PASSWORD1, password)
//
//                    .commit();

          /*  SharedPreferences.Editor editor = setting.edit();
            editor.putString("Company_code", Company_code).apply();
            editor.putString("Employee_code", Employee_code).apply();
            editor.putString("password", password).apply();

            editor.commit();*/
            String companycode = Companycode.getText().toString().trim();
            Log.d("after get",companycode);
            String employeecode = Employeecode.getText().toString().trim();
            String Password = _passwordText.getText().toString().trim();
            SharedPreferences settings = getSharedPreferences(PREFRENCES_NAME, 0);
            settings.edit().putString("companycode", companycode).putString("employeecode", employeecode)
                    .putString("Password", Password).commit();
            Log.d("after put", companycode);



            // settings.edit().clear().commit();
            session.createUserLoginSession(companycode, employeecode,Password);
        }


       /* if (Company_code.equalsIgnoreCase("C001") && Employee_code.equalsIgnoreCase("001")&& password.equalsIgnoreCase("123456")) {
            Intent i = new Intent(Login.this, MainActivity.class); // here the nextpage to be loaded is specified
            startActivity(i);*/
        if (Companycode.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(Login.this, "Please Enter Company Code", Toast.LENGTH_SHORT).show();
        } else if (Employeecode.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(Login.this, "Please Enter Employee Code", Toast.LENGTH_SHORT).show();
        } else if (_passwordText.getText().toString().trim().equalsIgnoreCase("")) {
            Toast.makeText(Login.this, "Please Enter Password", Toast.LENGTH_SHORT).show();


        } else {
            if (networkMan.isConnected()) {
                Company_code = Companycode.getText().toString();
                Employee_code = Employeecode.getText().toString();
                password = _passwordText.getText().toString();
                new GetAsync().execute(Company_code, Employee_code,password);
                //   new MenuGetAsync().execute(SchemaName, CompanyId, userId, "Admin");

                //Intent a=new Intent(Login.this,MainActivity.class);
                // startActivity(a);
            }

           /* System.out.println("inside the else");
            ShowAlert.displayAlert(Login.this, "Invalid Company code /Password");
            return;
*/
        }
    }


    @Override
    public void onStart()
    {
        try
        {
            super.onStart();

        }
        catch(Exception ex)
        {
            Log.d("hi",ex.toString());
        }

    }



    class GetAsync extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();
        private ProgressDialog pDialog;
        private static final String LOGIN_URL = "http://cloud.pockethcm.com/api/v2.0/SelfServiceLogin";
        private static final String LOGIN_URL1 = "https://cloud.pockethcm.com/api/v2.0/SelfserviceMenu";

        private static final String TAG_INVILID = "Message";
        private static final String TAG_SchemaName = "SchemaName";
        private static final String TAG_userId = "userId";
        private static final String TAG_Role = "Role";
        private static final String TAG_CompanyId = "CompanyId";
        private static final String TAG_MESSAGE = "Invalid Credentials";

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {
            JSONObject json=null;
            JSONObject json1=null;
            try {
                HashMap<String, String> params = new HashMap<>();
                params.put("CompanyId", args[0]);
                params.put("UserName", args[1]);
                params.put("Password", args[2]);
                json = jsonParser.makeHttpRequest(LOGIN_URL, "GET", params);
                if(json!=null) {
                    SchemaName = json.getString(TAG_SchemaName);
                    CompanyId = json.getString(TAG_CompanyId);
                    userId = json.getString(TAG_userId);
                    Role = json.getString(TAG_Role);
                    Log.d("Login json", json.toString());
                    Log.d("loginuserId", userId.toString());
                    Log.d("loginCompanyId", CompanyId.toString());
                    Log.d("loginSchemaName", SchemaName.toString());
                    if (Role.contains("1")) {
                        Role_String = "Admin";
                    } else if (Role.contains("2")) {
                        Role_String = "Manager";
                    } else if (Role.contains("3")) {
                        Role_String = "User";
                    } else if (Role.contains("4")) {
                        Role_String = "Finance";
                    }
                   /* Admin = 1,
                            Manager =2,
                            User=3,
                            Finance=4*/


                    setPreference(SchemaName, userId, CompanyId);
                    Log.d("setPreference", SchemaName.toString());
                    Log.d("setPreference", userId.toString());
                    Log.d("setPreference", CompanyId.toString());


                }else
                {
                    Toast.makeText(getApplicationContext(),
                            "Please try Again", Toast.LENGTH_SHORT).show();
                }

            } catch (NullPointerException e) {e.printStackTrace();}
            catch (Exception e) {e.printStackTrace();}
            return json;
        }
        protected void onPostExecute(JSONObject json) {

            if (json == null)
            {
                Toast.makeText(getApplicationContext(),
                        "Please try Again", Toast.LENGTH_SHORT).show();
                // new MenuGetAsync().execute(SchemaName, CompanyId, userId, "Admin");

                pDialog.dismiss();
            } else
                  /*  {
                        try {
                               invalid = json.getString(TAG_INVILID);
                          }catch (JSONException e) {e.printStackTrace();}
                            if (invalid!=null)
                            {if (pDialog != null && pDialog.isShowing()) {
                                pDialog.dismiss();
                            }
                                Toast.makeText(getApplicationContext(), "Please enter valid Details", Toast.LENGTH_SHORT).show();
                            }
                                else
                                {*/
                new MenuGetAsync().execute(SchemaName, CompanyId, userId, Role_String);
            Log.d("setSchemaName", SchemaName.toString());
            Log.d("setuserId",userId.toString());
            Log.d("setCompanyId",CompanyId.toString());
            Log.d("SetRole_String",Role_String.toString());
                                 /*   Intent intObj = new Intent(Login.this, NavigationActivity.class);
                                    startActivity(intObj);*/
            // }
            // }
        }
    }

    class MenuGetAsync extends AsyncTask<String, String, String> {
        // public  String SchemaName,userId,CompanyId,invalid,Role;
        JSONParser1 jsonParser=new JSONParser1();
        private ProgressDialog pDialog;
        //    private static final String LOGIN_URL = "http://cloud.pockethcm.com/api/v2.0/SelfServiceLogin";
        private static final String LOGIN_URL1 = "http://cloud.pockethcm.com/api/v2.0/SelfserviceMenu";

        private static final String TAG_INVILID = "Message";
        private static final String TAG_SchemaName = "SchemaName";
        private static final String TAG_userId = "userId";
        private static final String TAG_Role = "Role";
        private static final String TAG_CompanyId = "CompanyId";
        private static final String TAG_MESSAGE = "Invalid Credentials";

        @Override
        protected void onPreExecute() {
            pDialog = new ProgressDialog(Login.this);
            pDialog.setMessage("Attempting login...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... args) {
            String json="";
            try {

                HashMap<String, String> params1 = new HashMap<>();
                params1.put("schemaName", SchemaName);
                params1.put("companyId", CompanyId);
                params1.put("employeeId", userId);
                params1.put("userrole", Role_String);
                // Log.d("SchemaName", json.toString());
                Log.d("userId1", userId.toString());
                Log.d("SchemaName1", SchemaName.toString());
                Log.d("userrole", Role_String.toString());
                Log.d("CompanyId1", CompanyId.toString());

                json = jsonParser.makeHttpRequest(LOGIN_URL1, "GET", params1);
                Log.d("response ", json.toString());

                    /*Save data */
                //   setPreference(SchemaName, userId, CompanyId);
                //Gson gson = new Gson();
                //  menu_String = gson.toJson(json);
                setPreferencemenu(json);
                Log.d("setPreferencemenu", json.toString());



            } catch (NullPointerException e) {e.printStackTrace();}
            catch (Exception e) {e.printStackTrace();}
            return json;
        }
        protected void onPostExecute(String response) {
            String message = "";

            if (pDialog != null && pDialog.isShowing()) {
                pDialog.dismiss();
            }
            if (response != null) {
               /* Toast.makeText(getActivity(), response.toString(),
                        Toast.LENGTH_LONG).show();*/
                Log.d("menueRes ", response.toString());
                Intent intent=new Intent(Login.this,NavigationActivity.class);

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
                    setPreferencemenu(response);
                    Log.d("Employement_setPrefer", response.toString());

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


    private void setPreference(String SchemaName,String userId,String CompanyId )
    {
        SharedPreferences setting = context.getSharedPreferences(
                PREF_NAME_PRIFRNCR, 0);
        //  SharedPreferences setting = this.getApplication().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("SchemaName", SchemaName).apply();
        editor.putString("userId", userId).apply();
        editor.putString("CompanyId", CompanyId).apply();
        Log.d("setPreference1", SchemaName.toString());
        Log.d("setPreference1", userId.toString());
        Log.d("setPreference1", CompanyId.toString());
        editor.commit();

    }/*public void Pref() {
        SharedPreferences setting = this.getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        String SchemaName = setting.getString("SchemaName", "SchemaName");
        String userId = setting.getString("userId", "userId");
        String CompanyId = setting.getString("CompanyId", "CompanyId");

        Log.d("SchemaNamelogin1", SchemaName.toString());
         Log.d("userIdlogin1", userId.toString());
        Log.d("CompanyIdlogin1", CompanyId.toString());
    }*/

    private void setPreferencemenu(String json )
    {
        SharedPreferences setting = context.getSharedPreferences(
                PREF_NAME_PRIFRNCR1, 0);
        //  SharedPreferences setting = this.getApplication().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("json", json).apply();

        editor.commit();

    }



    /*****************************************************************/
    public  void jsonToMap(String t) throws JSONException {
        HashMap<String, String> map = new HashMap<String, String>();
        JSONObject jObject = new JSONObject(t);
        Iterator<?> keys = jObject.keys();
        while( keys.hasNext() )
        {
            String key = (String)keys.next();
            String value = jObject.getString(key);
            map.put(key, value);
            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            intent.putExtra("map",map);
            System.out.println("map : " + map);
            startActivity(intent);
            finish();
        }
        System.out.println("json : " + jObject);
    }

/*
    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }*/

    public void onLoginFailed() {
        if(networkMan.isConnected()){
            Toast.makeText(getApplicationContext(), "not able to reach server", Toast.LENGTH_LONG).show();
        } else {
        /*  Intent i = new Intent(this, LoginActivity.class);
          startActivity(i); //Redirect to login
          finish();*/
            Toast.makeText(getApplicationContext(), "not able to reach server", Toast.LENGTH_LONG).show();
        }
        _loginButton.setEnabled(true);
    }
    private void showpDialog() {
        if (!pDialog.isShowing())
            pDialog.show();
    }
    private void hidepDialog() {
        if (pDialog.isShowing())
            pDialog.dismiss();
    }
/*************************************************************************************/

}




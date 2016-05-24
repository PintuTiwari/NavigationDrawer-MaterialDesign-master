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
    String Company_code, Employee_code, password;
    @Bind(R.id.companycode) EditText Companycode;
    @Bind(R.id.employeecode) EditText Employeecode;
    @Bind(R.id.password) EditText _passwordText;
    @Bind(R.id.btn_login) Button _loginButton;
    private NetworkMan networkMan;
    boolean isUserLoggedIn = true;
    // User Session Manager Class
    UserSessionManager session;
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "foo@example.com:hello", "bar@example.com:world"};

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
       // logout();
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
    }

    /*public  void logout(){
        SharedPreferences sharedpreferences = this.getSharedPreferences(Login.PREFS_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.clear();
        editor.commit();
    }*/

    public void logout()
    {
        SharedPreferences settings = getSharedPreferences(PREFRENCES_NAME, Context.MODE_PRIVATE);
        settings.edit().clear().commit();
    }
    public void login()
    {
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

        if (savepass.isChecked()) {
            SharedPreferences setting = context.getSharedPreferences(
                    PREFS_NAME, 0);

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
                new LoginAsync().execute(Company_code, Employee_code,password);
            }
        }
    }
        @Override
        public void onStart() {try
            {super.onStart();}
            catch(Exception ex) {Log.d("hi",ex.toString());}}

    class LoginAsync extends AsyncTask<String, String, JSONObject> {
        JSONParser jsonParser = new JSONParser();
        private ProgressDialog pDialog;
        private static final String LOGIN_URL = "http://cloud.pockethcm.com/api/v2.0/SelfServiceLogin";
        private static final String LOGIN_URL1 = "https://cloud.pockethcm.com/api/v2.0/SelfserviceMenu";
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
                    setPreference(SchemaName, userId, CompanyId);
                    Log.d("setPreference", SchemaName.toString());
                    Log.d("setPreference", userId.toString());
                    Log.d("setPreference", CompanyId.toString());
                }else
                {Toast.makeText(getApplicationContext(), "Please try Again", Toast.LENGTH_SHORT).show();}
            } catch (NullPointerException e) {e.printStackTrace();}
            catch (Exception e) {e.printStackTrace();}
            return json;
        }
        protected void onPostExecute(JSONObject json) {
            if (json == null)
            {
                Toast.makeText(getApplicationContext(), "Please try Again", Toast.LENGTH_SHORT).show();
                pDialog.dismiss();
            } else
                new MenuGetAsync().execute(SchemaName, CompanyId, userId, Role_String);
                Log.d("setSchemaName", SchemaName.toString());
                Log.d("setuserId",userId.toString());
                Log.d("setCompanyId",CompanyId.toString());
                Log.d("SetRole_String",Role_String.toString());
        }
    }

    class MenuGetAsync extends AsyncTask<String, String, String> {
        JSONParser1 jsonParser=new JSONParser1();
        private ProgressDialog pDialog;
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
                Log.d("userId1", userId.toString());
                Log.d("SchemaName1", SchemaName.toString());
                Log.d("userrole", Role_String.toString());
                Log.d("CompanyId1", CompanyId.toString());

                json = jsonParser.makeHttpRequest(LOGIN_URL1, "GET", params1);
                Log.d("response ", json.toString());
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
                Log.d("menueRes ", response.toString());
                Intent intent=new Intent(Login.this,NavigationActivity.class);
                startActivity(intent);
                try {
                    setPreferencemenu(response);
                    Log.d("Employement_setPrefer", response.toString());
                } catch (Exception e) {
                    e.printStackTrace();}
            }
            if (TAG_MESSAGE == TAG_MESSAGE) {Log.d("Success!", message);}
            else {Log.d("Failure", message);}
        }
    }

    private void setPreference(String SchemaName,String userId,String CompanyId )
    {
        SharedPreferences setting = context.getSharedPreferences(
                PREF_NAME_PRIFRNCR, 0);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("SchemaName", SchemaName).apply();
        editor.putString("userId", userId).apply();
        editor.putString("CompanyId", CompanyId).apply();
        Log.d("setPreference1", SchemaName.toString());
        Log.d("setPreference1", userId.toString());
        Log.d("setPreference1", CompanyId.toString());
        editor.commit();
    }

    private void setPreferencemenu(String json )
    {
        SharedPreferences setting = context.getSharedPreferences(
                PREF_NAME_PRIFRNCR1, 0);
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

}




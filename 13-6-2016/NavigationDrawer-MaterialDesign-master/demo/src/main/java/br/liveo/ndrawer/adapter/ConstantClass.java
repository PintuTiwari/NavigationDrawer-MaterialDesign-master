package br.liveo.ndrawer.adapter;

/**
 * Author :Raj Amal
 * Email  :raj.amalw@learn2crack.com
 * Website:www.learn2crack.com
 **/

import android.util.Log;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class ConstantClass {

    private PostJSONParser jsonParser;

    //URL of the PHP API

    private static String mydetailspostURL = "https://cloud.pockethcm.com/api/v2.0/SaveEmployeeDetail";
    private static String additionalpostURL = "https://cloud.pockethcm.com/api/v2.0/SaveAdditionalDetail";
    private static String employeelanguagepostURL = "https://cloud.pockethcm.com/api/v2.0/SaveAcademicDetail";

    private static String acadmicpostURL = "https://cloud.pockethcm.com/api/v2.0/SaveLanguageDetail";


    // constructor
    public ConstantClass(){
        jsonParser = new PostJSONParser();
    }
    public JSONObject saveemergencycontact(String SchemaName, String CompanyId, String Fieldvalue){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("SchemaName", SchemaName));
        params.add(new BasicNameValuePair("CompanyId", CompanyId));
        params.add(new BasicNameValuePair("Fieldvalue", Fieldvalue));
        JSONObject json = jsonParser.getJSONFromUrl(additionalpostURL, params);
        return json;
    }

    public JSONObject saveadditionaldetails(String SchemaName, String CompanyId, String Fieldvalue){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("SchemaName", SchemaName));
        params.add(new BasicNameValuePair("CompanyId", CompanyId));
        params.add(new BasicNameValuePair("Fieldvalue", Fieldvalue));
        JSONObject json = jsonParser.getJSONFromUrl(additionalpostURL, params);
        return json;
    }
    public JSONObject saveacademicDetails(String SchemaName, String CompanyId, String Fieldvalue){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("SchemaName", SchemaName));
        params.add(new BasicNameValuePair("CompanyId", CompanyId));
        params.add(new BasicNameValuePair("Fieldvalue", Fieldvalue));
        JSONObject json = jsonParser.getJSONFromUrl(acadmicpostURL, params);
        return json;
    }

    public JSONObject saveemployeelanguage(String SchemaName, String CompanyId, String Fieldvalue){
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("SchemaName", SchemaName));
        params.add(new BasicNameValuePair("CompanyId", CompanyId));
        params.add(new BasicNameValuePair("Fieldvalue", Fieldvalue));
        JSONObject json = jsonParser.getJSONFromUrl(employeelanguagepostURL, params);
        return json;
    }





     /**
      * Function to  Register
      **/

    public JSONObject savemydetails(String SchemaName, String CompanyId, String Fieldvalue){
        // Building Parameters
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("SchemaName", SchemaName));
        params.add(new BasicNameValuePair("CompanyId", CompanyId));
        params.add(new BasicNameValuePair("Fieldvalue", Fieldvalue));
        Log.d("Fieldvalue", Fieldvalue.toString());
        JSONObject json = jsonParser.getJSONFromUrl(mydetailspostURL,params);
        return json;
    }


    /**
     * Function to logout user
     * Resets the temporary data stored in SQLite Database
     * */
  /*  public boolean logoutUser(Context context){
        DatabaseHandler db = new DatabaseHandler(context);
        db.resetTables();
        return true;
    }*/

}


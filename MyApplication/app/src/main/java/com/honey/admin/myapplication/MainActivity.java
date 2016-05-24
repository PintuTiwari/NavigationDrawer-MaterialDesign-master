package com.honey.admin.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //http://saigeethamn.blogspot.in/2009/08/android-developer-tutorial-for_31.html
    private TextView Publicprovidentfund,Publicprovidentfund1;
    private TextView subtile,Displaydata;
    RecyclerAdapterUpload adapter;
    private Button uploadsubmit;
    SharedPreferences    prefsNagSetting;
    static final  int SELECT_PICTURE =1;
    private static final String PREF_NAME_PRIFRNCR = "isregisterd";
    private static final String PREF_NAME_PRIFRNCR1 = "isregisterd1";
    private String add;
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Publicprovidentfund=(TextView)findViewById(R.id.providentfund);
        Publicprovidentfund=(TextView)findViewById(R.id.providentfund);
        ImageView upload=(ImageView)findViewById(R.id.upload);
        ImageView upload1=(ImageView)findViewById(R.id.upload1);
        upload.setOnClickListener(this);
        upload1.setOnClickListener(this);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.upload:



                Log.d("House Rent Paid", "House Rent Paid");




                ArrayList<Friend_Naminee> friendArrayList1 = new ArrayList<>();
                uploade1("House Rent Paid", friendArrayList1);
                subtile.setText("House Rent Paid");
                subtile.setEnabled(false);
                break;
            case R.id.upload1:
                //friendArrayList11.clear();

                  /*if (buttonkey1 != null) {
                    ArrayList<Friend_Naminee> friendArrayList2 = new ArrayList<>();

                    Friend_Naminee friend = new Friend_Naminee(add1);
                    if (friend.getName().equalsIgnoreCase(add1)) {
                        boolean found = false;
                        for (Friend_Naminee friend1 : friendArrayList2) {
                            if (friend1.getName().equalsIgnoreCase(add1)) {
                                found = true;
                                adapter = new RecyclerAdapterUpload(this, friendArrayList2);
                                recyclerView.setAdapter(adapter);
                                break;
                            }
                        }
                        if (!found) {


                            friendArrayList2.add(friend);
                            Log.d("friendArrayList", String.valueOf(friendArrayList2.size()));
                        }
                    }
*/
              /*  friendArrayList1 = new ArrayList<>();
                Friend_Naminee friend_naminee=new Friend_Naminee(add1);
                for (int i = 0; i < add1.length(); i++) {
                    friendArrayList1.add(friend_naminee);
                    Log.d("textfriendArrayList", String.valueOf(friendArrayList1.size()));
                }*/
                    ArrayList<Friend_Naminee> friendArrayList3 = new ArrayList<>();
                    uploade1("Entertainment Allowance", friendArrayList3);
                    subtile.setText("Entertainment Allowance");
                    subtile.setEnabled(false);
                    break;
               //}
        }
    }

    public  void uploade1(String butoonKey,  ArrayList<Friend_Naminee> friendArrayList1) {
    if(butoonKey!=null)
    {
        SharedPreferences prefsNagSetting = this.getSharedPreferences(PREF_NAME_PRIFRNCR, 0);
        String butoonKey1 = prefsNagSetting.getString("butoonKey", "");
        String add = prefsNagSetting.getString("add", "");
        Log.d("butoonKey1", butoonKey1);
        Log.d("add1", add);
        }
        LayoutInflater inflater = LayoutInflater.from(this);
        View dialog_layout = inflater.inflate(R.layout.uploadfile, null);
        final AlertDialog.Builder db = new AlertDialog.Builder(this);

        db.setView(dialog_layout);
        recyclerView = (RecyclerView) dialog_layout.findViewById(R.id.recyle_view);
        subtile = (TextView)dialog_layout.findViewById(R.id.februarynonmetro);
        Displaydata = (TextView)dialog_layout.findViewById(R.id.displayuploaddata);
        Button choosefile1 = (Button)dialog_layout.findViewById(R.id.choosefile);
        choosefile1.setTag(butoonKey);
        //Log.d("butoonKey", butoonKey);
        choosefile1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String butoonKey = String.valueOf(v.getTag());
                  Log.d(" v.getId()", String.valueOf(v.getTag()));
                //  uploadFile();
                getImageFromGallery(butoonKey);
            }
        });
        uploadsubmit = (Button) dialog_layout.findViewById(R.id.uploadsubmit);

        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);



        uploadsubmit.setOnClickListener(onAddingListener1(friendArrayList1));

        adapter = new RecyclerAdapterUpload(this, friendArrayList1);
        recyclerView.setAdapter(adapter);






        db.setTitle("Proof Documents");
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
                //UpdateDate();

            }
        });
        AlertDialog alert11 = db.create();
        alert11.show();

        Button buttonbackground = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonbackground.setTextColor(getResources().getColor(R.color.colorAccent));

        Button buttonbackground1 = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
        buttonbackground1.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    private View.OnClickListener onAddingListener1(final ArrayList friendArrayList1) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadsubmit.setOnClickListener(onConfirmListener1(add, friendArrayList1));
                Displaydata.setText("");
            }
        };
    }

    private View.OnClickListener onConfirmListener1(final String Name, final ArrayList<Friend_Naminee> friendArrayList1) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences prefsNagSetting = get();
                String buttonkey = prefsNagSetting.getString("buttonKey", "");
                String add = prefsNagSetting.getString("add", "");
                Log.d("getimagepathtoaddarray", add);
                if (buttonkey != null) {
                    Friend_Naminee friend = new Friend_Naminee(add);
                    boolean found = false;
                    for (Friend_Naminee friend1 : friendArrayList1) {
                        if (friend1.getName().equalsIgnoreCase(add)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) {


                        friendArrayList1.add(friend);
                        Log.d("friendArrayList", String.valueOf(friendArrayList1.size()));
                    }
                    adapter.notifyDataSetChanged();


                }
            }

        };
    }
    public SharedPreferences get()
    {
        SharedPreferences    prefsNagSetting = this.getSharedPreferences(PREF_NAME_PRIFRNCR, 0);
        return prefsNagSetting;
    }
    private void getImageFromGallery(String butoonKey) {
        Log.d("eachbuttonKey", butoonKey);
        Intent chooser = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getDownloadCacheDirectory().getPath().toString());
        chooser.addCategory(Intent.CATEGORY_OPENABLE);
        //set here the buttonkey in intent so that it is available in onActivityResult
        // chooser.putExtra("buttonKey", buttonKey);

        SharedPreferences setting = this.getSharedPreferences(PREF_NAME_PRIFRNCR1, 0);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("butoonKey", butoonKey).apply();
        Log.d("storingbuttonkeytopre ", butoonKey);
        editor.commit();

      /*  Log.d("putExtra: ", buttonKey);
        String buttonKey1 =  chooser.getExtras().getString("buttonKey");
        Log.d("getStringExtra: ", buttonKey1.toString());*/

        chooser.setDataAndType(uri, "*/*");
        try {
          /*  SharedPreferences    prefsNagSetting = this.getSharedPreferences(PREF_NAME_PRIFRNCR, 0);
            String buttonkey = prefsNagSetting.getString("buttonkey", "");
            String add = prefsNagSetting.getString("add","");
            Log.d("prefsNagSetting buttonkey",buttonkey);
            Log.d("prefsNagSetting add", add);

            if( buttonkey != null ){
                startActivityForResult(chooser, SELECT_PICTURE);

                //    yaha pe kaya karna because mai to adapter call kara rahi thi abh shered ki value usme kaise dalu
            }else{
                startActivityForResult(chooser, SELECT_PICTURE);
            }*/
            // as imagedata is present in sharedpreference for buttonkey, call here the code that will do Displaydata.setText(imageData);
            // }else {
            //  if there is no date in the shared preference we will do the normal work of chossing the file.
            startActivityForResult(chooser, SELECT_PICTURE);
            // finish();


        }
        catch (android.content.ActivityNotFoundException ex)
        {
            //Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri uri = data.getData();

                String add = uri.getPath();
                Log.d("eachFilePath", add.toString());
                Displaydata.setText(add);

                SharedPreferences    prefsNagSetting = this.getSharedPreferences(PREF_NAME_PRIFRNCR1, 0);
                String butoonKey = prefsNagSetting.getString("butoonKey","");
                Toast.makeText(this, "You have chosen the book: " + " " + butoonKey, Toast.LENGTH_LONG).show();
              //  Log.d("getbuttonkeytppref ", buttonKey);


                if( butoonKey != null&& add!=null ){
                    SharedPreferences setting = this.getSharedPreferences(PREF_NAME_PRIFRNCR, 0);
                    SharedPreferences.Editor editor = setting.edit();
                    editor.putString("butoonKey", butoonKey).apply();
                    editor.putString("add", add).apply();
                   // Log.d("butoonKey", butoonKey.toString());
                    //Log.d("add", add.toString());
                    editor.commit();
                }
                /*if (data != null) {
                    if (data.getStringExtra("buttonKey") != null) {
                        Uri selectedImage = data.getData(); // use this URI for getting and updating the fragment
                        // String buttonKey=data.getStringExtra("buttonKey");
                        Log.d("getStringExtra: ", selectedImage.toString());
                        // get the buttonKey from intent that we set inside getImageFromGallery method, we will use this intent to store value in sharedprefernec along with add.
                        //sharedpreference.setData(buttonkey,add);
                    }
                }*/
            }
        }
    }
}

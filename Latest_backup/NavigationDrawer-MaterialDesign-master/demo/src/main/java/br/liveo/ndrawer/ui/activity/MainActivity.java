/*
 * Copyright 2015 Rudson Lima
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package br.liveo.ndrawer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;

import br.liveo.interfaces.OnItemClickListener;
import br.liveo.interfaces.OnPrepareOptionsMenuLiveo;
import br.liveo.model.HelpLiveo;
import br.liveo.navigationliveo.NavigationLiveo;
import br.liveo.ndrawer.R;
import br.liveo.ndrawer.ui.fragment.MainFragment;
//http://www.androidhub4you.com/2012/12/listview-into-scrollview-in-android.html
public class MainActivity extends NavigationLiveo implements OnItemClickListener{
    public static final String PREFS_NAME = "MyPrefsFile";
    private HelpLiveo mHelpLiveo;
    String valueStr ,keyStr,getMyDetils_String,getMyDetils_String1,getMyDetils_String2;
    @Override
    public void onInt(Bundle savedInstanceState) {

        // User Information
        this.userName.setText("Rudson Lima");
        this.userEmail.setText("rudsonlive@gmail.com");
        this.userPhoto.setImageResource(R.drawable.ic_rudsonlive);
        this.userBackground.setImageResource(R.drawable.my_pic);
        SharedPreferences setting = this.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);

        Gson gson = new Gson();
        getMyDetils_String  = setting.getString("Company_code", "Company_code");
        getMyDetils_String1  = setting.getString("Employee_code", "Employee_code");
        getMyDetils_String2  = setting.getString("password", "password");
        Log.d("PREF_USERNAME",getMyDetils_String +""+getMyDetils_String+""+getMyDetils_String2);

        if (getMyDetils_String != null &&getMyDetils_String!=null && getMyDetils_String!=null) {

        }
       // Log.d("PREF_USERNAME",getMyDetils_String);
       // Log.d("PREF_USERNAME",getMyDetils_String);
        // Creating items navigation
        mHelpLiveo = new HelpLiveo();
       // mHelpLiveo.add(getString(R.string.inbox), R.mipmap.ic_inbox_black_24dp, 7);
        mHelpLiveo.add(getString(R.string.inbox), R.mipmap.ic_inbox_black_24dp);

        mHelpLiveo.addSubHeader(getString(R.string.categories)); //Item subHeader
        mHelpLiveo.add(getString(R.string.starred), R.mipmap.ic_star_black_24dp);
        mHelpLiveo.add(getString(R.string.sent_mail), R.mipmap.ic_send_black_24dp);
        mHelpLiveo.add(getString(R.string.drafts), R.mipmap.ic_drafts_black_24dp);
        mHelpLiveo.add(getString(R.string.logout), R.mipmap.ic_drafts_black_24dp);
        mHelpLiveo.addSeparator(); // Item separator
       /* mHelpLiveo.add(getString(R.string.trash), R.mipmap.ic_delete_black_24dp);
        mHelpLiveo.add(getString(R.string.spam), R.mipmap.ic_report_black_24dp, 120);*/

        //{optional} - Header Customization - method customHeader
//        View mCustomHeader = getLayoutInflater().inflate(R.layout.custom_header_user, this.getListView(), false);
//        ImageView imageView = (ImageView) mCustomHeader.findViewById(R.id.imageView);

        with(this).startingPosition(0) //Starting position in the list
                .addAllHelpItem(mHelpLiveo.getHelp())

                //{optional} - List Customization "If you remove these methods and the list will take his white standard color"
                //.selectorCheck(R.drawable.selector_check) //Inform the background of the selected item color
                //.colorItemDefault(R.color.nliveo_blue_colorPrimary) //Inform the standard color name, icon and counter
                //.colorItemSelected(R.color.nliveo_purple_colorPrimary) //State the name of the color, icon and meter when it is selected
                //.backgroundList(R.color.nliveo_black_light) //Inform the list of background color
                //.colorLineSeparator(R.color.nliveo_transparent) //Inform the color of the subheader line

                //{optional} - SubHeader Customization
                //.colorItemSelected(R.color.nliveo_blue_colorPrimary)
                //.colorNameSubHeader(R.color.nliveo_blue_colorPrimary)
              //  .colorLineSeparator(R.color.nliveo_blue_colorPrimary)
                //this fo setting
               // .footerItem(R.string.settings, R.mipmap.ic_settings_black_24dp)
              //  .footerSecondItem(R.string.settings, R.mipmap.ic_settings_black_24dp)

                //{optional} - Header Customization
                //.customHeader(mCustomHeader)

                //{optional} - Footer Customization
                //.footerNameColor(R.color.nliveo_blue_colorPrimary)
                //.footerIconColor(R.color.nliveo_blue_colorPrimary)
                //.footerBackground(R.color.nliveo_white)

               // .setOnClickUser(onClickPhoto)
               // .setOnPrepareOptionsMenu(onPrepare)
               // .setOnClickFooter(onClickFooter)
                //.setOnClickFooterSecond(onClickFooter)
                .build();
                int position = this.getCurrentPosition();
                //this.setElevationToolBar(position != 2 ? 15 : 0);
        }

                /*
                set the value of 15 if position!= 1 else set 2
                 this is and id else
                if(position != 1) { return 15; }else { return 2; }*/


    @Override
    public void onItemClicked(View v) {

    }

    @Override
    public void onItemClick(int position) {
        Fragment mFragment;
        FragmentManager mFragmentManager = getSupportFragmentManager();
        switch (position){
            case 0:
                mFragment = new Profile_Activity();
                Intent basket= new Intent();
                basket.putExtra("abc", valueStr);
                basket.putExtra("map", keyStr);
                break;
            case 2:
                mFragment = new PayInfo_Activity();
                break;
            case 5:
               mFragment =  new LogOut();
                /*Intent intent = new Intent(this, LogOut.class);
                startActivity(intent);*/
                break;
            default:
                mFragment = MainFragment.newInstance(mHelpLiveo.get(position).getName());
              //  Log.d("hi", MainFragment.newInstance(mHelpLiveo.get(position).getName()));
                break;
        }

            if (mFragment != null){
                mFragmentManager.beginTransaction().replace(R.id.container, mFragment).commit();
            }

                // setElevationToolBar(position != 2 ? 15 : 0);

    }

    private OnPrepareOptionsMenuLiveo onPrepare = new OnPrepareOptionsMenuLiveo() {
        @Override
        public void onPrepareOptionsMenu(Menu menu, int position, boolean visible) {
        }
    };

    private View.OnClickListener onClickPhoto = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Toast.makeText(getApplicationContext(), "onClickPhoto :D", Toast.LENGTH_SHORT).show();
            closeDrawer();
        }
    };

    private View.OnClickListener onClickFooter = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            startActivity(new Intent(getApplicationContext(), SettingsActivity.class));
            closeDrawer();
        }
    };
}


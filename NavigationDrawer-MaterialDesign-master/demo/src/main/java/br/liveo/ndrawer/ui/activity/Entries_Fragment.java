package br.liveo.ndrawer.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import br.liveo.ndrawer.R;
//http://www.tutorialsbuzz.com/2015/10/Android-Sliding-TabLayout-ListView-WhatsApp.html
public class Entries_Fragment extends Fragment {



    String[] items = {"TDS Entry", "Worksheet - TDS", "TDS Proof Entry"};
    ListView lv;
    Context context;
    private RecyclerView recyclerView;
    private PayInfoRecycleAdapter adapter;
    private ArrayList<Person> friendArrayList;
    String getMyDetils_String;
    Map<String, String> responseMap = new LinkedHashMap<String, String>();
    EditText Month,Year,NetPay;
    private static final String PREF_NAME_PRIFRNCR = "isregisterd";
    private static final String PREF_NAME_TOTAL = "total";

    String Months, Years, NetPays,SchemaName, userId, CompanyId,Fieldvalue;
    String part1,part44,part66,part55;
    int part4,part6,part5;
    private com.github.clans.fab.FloatingActionButton mFab;
    int Totalsum=0;
    int Total;
    int Aprilsnon;
    int Maysnon;
    int Junesnon;
    int Julysnon;
    int Augustsnon;
    int Septembersnon;
    int Octobersnon;
    int Novembersnon;
    int Decembersnon;
    int Januarysnon;
    int Februarysnon;
    int Marchsnon;
    int snowDensity;
    int Aprils,Mays,Junes, Julys, Augusts, Septembers, Octobers, Novembers, Decembers, Januarys, Februarys, Marchs;

    EditText Aprilnon,Maynon,Junenon, Julynon, Augustnon, Septembernon, Octobernon, Novembernon, Decembernon, Januarynon, Februarynon,
            Marchnon;
    EditText April,May,June, July, August, September, October, November, December, January, February, March;

    /**
     * A custom {@link ViewPager} title strip which looks much like Tabs present in Android v4.0 and
     * above, but is designed to give continuous feedback to the user when scrolling.
     */
    private SlidingTabLayout mSlidingTabLayout;

    /**
     * A {@link ViewPager} which will be used in conjunction with the {@link SlidingTabLayout} above.
     */
    private ViewPager mViewPager;

    /**
     * Inflates the {@link android.view.View} which will be displayed by this {@link Fragment}, from the app's
     * resources.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        SharedPreferences setting = getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SchemaName = setting.getString("SchemaName", "SchemaName");
        userId = setting.getString("userId", "userId");
        CompanyId = setting.getString("CompanyId", "CompanyId");
        friendArrayList = new ArrayList<>();
        Log.d("SchemaNameAcdemic", SchemaName.toString());
        Log.d("userIdAcdemic", userId.toString());
        Log.d("CompanyIdAcdemic", CompanyId.toString());

        SharedPreferences settings = getActivity().getSharedPreferences("YOUR_PREF_NAME", 0);
         snowDensity = settings.getInt("SNOW_DENSITY", -1);
        Log.d("SNOW_DENSITY", String.valueOf(snowDensity));//0 is the default value
      /*  SharedPreferences setting1 = getActivity().getSharedPreferences(PREF_NAME_TOTAL, Context.MODE_PRIVATE);
        Total = setting1.getInt("Totalsum",0);

       Log.d("GET", String.valueOf(Total));*/
/*if(obj !=  null && obj.getlength > 0)*/



        return inflater.inflate(R.layout.payinfo_layout, container, false);

    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        // Get the ViewPager and set it's PagerAdapter so that it can display items
        mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
        mViewPager.setAdapter(new SamplePagerAdapter());

        // Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
        // it's PagerAdapter set.
        mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
        mSlidingTabLayout.setViewPager(mViewPager);
    }



    class SamplePagerAdapter extends PagerAdapter {

        /**
         * @return the number of pages to display
         */
        @Override
        public int getCount() {
            return items.length;
        }

        /**
         * @return true if the value returned from {@link #instantiateItem(android.view.ViewGroup, int)} is the
         * same object as the {@link android.view.View} added to the {@link ViewPager}.
         */
        @Override
        public boolean isViewFromObject(View view, Object o) {
            return o == view;
        }


        @Override
        public CharSequence getPageTitle(int position) {
            return items[position];
        }

        /**
         * Instantiate the {@link android.view.View} which should be displayed at {@code position}. Here we
         * inflate a layout from the apps resources and then change the text view to signify the position.
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // Inflate a new layout from our resources
            View view = null;

            if (position == 0) {
                view = getActivity().getLayoutInflater().inflate(R.layout.expandable_child_1_layout, container, false);
                TextView clickhere=(TextView)view.findViewById(R.id.clickhere);
                TextView houserentpaid=(TextView)view.findViewById(R.id.houserentpaid);
                 //  houserentpaid.setText(snowDensity);
                houserentpaid.setText(Integer.toString(snowDensity));

                clickhere.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        addnewdetails();
                    }
                });
                container.addView(view);

            }


            if(position==1) {
                view = getActivity().getLayoutInflater().inflate(R.layout.expandable_child_1_layout, container, false);
                TextView clickhere=(TextView)view.findViewById(R.id.clickhere);
                clickhere.setVisibility(View.GONE);
                container.addView(view);


            }
            if(position==2) {
                view = getActivity().getLayoutInflater().inflate(R.layout.expandable_child_1_layout, container, false);
                TextView clickhere=(TextView)view.findViewById(R.id.clickhere);
                clickhere.setVisibility(View.GONE);
                container.addView(view);
            }

            return view;


        }



        /**
         * Destroy the item from the {@link ViewPager}. In our case this is simply removing the
         * {@link android.view.View}.
         */
        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    private void addnewdetails()
    {



        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialog_layout = inflater.inflate(R.layout.expandable_child_2_layout, null);
        final AlertDialog.Builder db = new AlertDialog.Builder(getActivity());
        db.setView(dialog_layout);
        Log.d("Failure", "Failure");

        April = (EditText) dialog_layout.findViewById(R.id.aprilmetro);
        May = (EditText) dialog_layout.findViewById(R.id.maymetro);
        June = (EditText) dialog_layout.findViewById(R.id.junemetro);
        July = (EditText) dialog_layout.findViewById(R.id.julymetro);
        August = (EditText) dialog_layout.findViewById(R.id.augustmetro);
        September = (EditText) dialog_layout.findViewById(R.id.septembermetro);
        October = (EditText) dialog_layout.findViewById(R.id.octobermetro);
        November = (EditText) dialog_layout.findViewById(R.id.novembermetro);
        December = (EditText) dialog_layout.findViewById(R.id.decembermetro);
        January = (EditText) dialog_layout.findViewById(R.id.januarymetro);
        February = (EditText) dialog_layout.findViewById(R.id.februarymetro);
        March = (EditText) dialog_layout.findViewById(R.id.marchmetro);


        Aprilnon = (EditText) dialog_layout.findViewById(R.id.aprilnonmetro);
        Maynon = (EditText) dialog_layout.findViewById(R.id.maynonmetro);
        Junenon = (EditText) dialog_layout.findViewById(R.id.junenonmetro);
        Julynon = (EditText) dialog_layout.findViewById(R.id.julynonmetro);
        Augustnon = (EditText) dialog_layout.findViewById(R.id.augustnonmetro);
        Septembernon = (EditText) dialog_layout.findViewById(R.id.septembernonmetro);
        Octobernon = (EditText) dialog_layout.findViewById(R.id.octobernonmetro);
        Novembernon = (EditText) dialog_layout.findViewById(R.id.novembernonmetro);
        Decembernon = (EditText) dialog_layout.findViewById(R.id.decembernonmetro);
        Januarynon = (EditText) dialog_layout.findViewById(R.id.januarynonmetro);
        Februarynon = (EditText) dialog_layout.findViewById(R.id.februarynonmetro);
        Marchnon = (EditText) dialog_layout.findViewById(R.id.marchnonmetro);





        db.setTitle("Click here to enter rent paid details");
        // setTitleColor(R.color.colorAccent);0
        //setTheme(R.style.MyTheme_ActionBar_TitleTextStyle);
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
             //  SharedPreferences sharedpreferences = getActivity().getSharedPreferences(PREF_NAME_TOTAL,Context.MODE_PRIVATE);
                SharedPreferences settings = getActivity().getSharedPreferences("YOUR_PREF_NAME", 0);
                SharedPreferences.Editor editor = settings.edit();
                editor.clear();
                UpdateDate();

            }
        });
        AlertDialog alert11 = db.create();
        alert11.show();

        Button buttonbackground = alert11.getButton(DialogInterface.BUTTON_NEGATIVE);
        buttonbackground.setTextColor(getResources().getColor(R.color.colorAccent));

        Button buttonbackground1 = alert11.getButton(DialogInterface.BUTTON_POSITIVE);
        //buttonbackground1.setBackgroundColor(Color.BLUE);
        buttonbackground1.setTextColor(getResources().getColor(R.color.colorAccent));
    }

    private void UpdateDate() {
       /* if(obj !=  null && obj.getlength > 0)*/
        if(April !=  null&&April.length() > 0&& (Aprilnon!=  null&&April.length() > 0))
        {
        /*&& (Aprilnon.getText().length() != 0)
                && (May.getText().length() != 0)&& (Maynon.getText().length() != 0)
                && (June.getText().length() != 0)&& (Junenon.getText().length() != 0)
                && (July.getText().length() != 0)&& (Julynon.getText().length() != 0)
                && (August.getText().length() != 0)&& (Augustnon.getText().length() != 0)
                && (September.getText().length() != 0)&& (Septembernon.getText().length() != 0)
                && (October.getText().length() != 0)&& (Octobernon.getText().length() != 0)
                && (November.getText().length() != 0)&& (Novembernon.getText().length() != 0)
                && (December.getText().length() != 0)&& (Decembernon.getText().length() != 0)
                && (January.getText().length() != 0)&& (Januarynon.getText().length() != 0)
                && (February.getText().length() != 0)&& (Februarynon.getText().length() != 0)
                && (March.getText().length() != 0)&& (Marchnon.getText().length() != 0))*/

            Aprils = Integer.parseInt(April.getText().toString());
         /*   Mays = Integer.parseInt(May.getText().toString());
            Junes =Integer.parseInt(June.getText().toString());
            Julys = Integer.parseInt(July.getText().toString());
            Augusts = Integer.parseInt(August.getText().toString());
            Septembers = Integer.parseInt(September.getText().toString());
            Octobers = Integer.parseInt(October.getText().toString());
            Novembers = Integer.parseInt(November.getText().toString());
            Decembers =Integer.parseInt(December.getText().toString());
            Januarys = Integer.parseInt(January.getText().toString());
            Februarys = Integer.parseInt(February.getText().toString());
            Marchs =  Integer.parseInt(March.getText().toString());*/


            Aprilsnon = Integer.parseInt(Aprilnon.getText().toString());
           /* Maysnon = Integer.parseInt(Maynon.getText().toString());
            Junesnon = Integer.parseInt(Junenon.getText().toString());
            Julysnon = Integer.parseInt(Julynon.getText().toString());
            Augustsnon = Integer.parseInt(Augustnon.getText().toString());
            Septembersnon = Integer.parseInt(Septembernon.getText().toString());
            Octobersnon = Integer.parseInt(Octobernon.getText().toString());
            Novembersnon = Integer.parseInt(Novembernon.getText().toString());
            Decembersnon = Integer.parseInt(Decembernon.getText().toString());
            Januarysnon = Integer.parseInt(Januarynon.getText().toString());
            Februarysnon = Integer.parseInt(Februarynon.getText().toString());
            Marchsnon = Integer.parseInt(Marchnon.getText().toString());*/

            Log.d("Aprils", String.valueOf(Aprils));
            Log.d("Aprilsnon", String.valueOf(Aprilsnon));
            Totalsum+= Aprils+Aprilsnon;
            /* Totalsum+= Aprilsnon+Maysnon+Junesnon+Julysnon+Augustsnon+Septembersnon+Octobersnon+Novembersnon+
                    Decembersnon+Januarysnon+ Februarysnon+Marchsnon+Aprils+Mays+Junes+Julys+
                    Augusts+Septembers+Octobers+Novembers+Decembers+Januarys+Februarys+Marchs;
*/


            Log.d("Totalsum", String.valueOf(Totalsum));

            SharedPreferences settings = getActivity().getSharedPreferences("YOUR_PREF_NAME", 0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putInt("SNOW_DENSITY", ++Totalsum);
            editor.commit();
            //personal_setPreference(Totalsum);
     /*   Mays = Integer.parseInt(May.getText().toString());
        Junes =Integer.parseInt(June.getText().toString());
        Julys = Integer.parseInt(July.getText().toString());
        Augusts = Integer.parseInt(August.getText().toString());
        Septembers = Integer.parseInt(September.getText().toString());
        Octobers = Integer.parseInt(October.getText().toString());
        Novembers = Integer.parseInt(November.getText().toString());
        Decembers =Integer.parseInt(December.getText().toString());
        Januarys = Integer.parseInt(January.getText().toString());
        Februarys = Integer.parseInt(February.getText().toString());
        Marchs =  Integer.parseInt(March.getText().toString());*/
        }else
        {

        }




     /*   Maysnon = Integer.parseInt(Maynon.getText().toString());
        Junesnon = Integer.parseInt(Junenon.getText().toString());
        Julysnon = Integer.parseInt(Julynon.getText().toString());
        Augustsnon = Integer.parseInt(Augustnon.getText().toString());
        Septembersnon = Integer.parseInt(Septembernon.getText().toString());
        Octobersnon = Integer.parseInt(Octobernon.getText().toString());
        Novembersnon = Integer.parseInt(Novembernon.getText().toString());
        Decembersnon = Integer.parseInt(Decembernon.getText().toString());
        Januarysnon = Integer.parseInt(Januarynon.getText().toString());
        Februarysnon = Integer.parseInt(Februarynon.getText().toString());
            Marchsnon = Integer.parseInt(Marchnon.getText().toString());*/
/*        Totalsum= Aprilsnon+Maysnon+Junesnon+Julysnon+Augustsnon+Septembersnon+Octobersnon+Novembersnon+
                Decembersnon+Januarysnon+ Februarysnon+Marchsnon+Aprils+Mays+Junes+Julys+
                Augusts+Septembers+Octobers+Novembers+Decembers+Januarys+Februarys+Marchs;*/



    }

    private void personal_setPreference(int Totalsum) {
       SharedPreferences setting = this.getActivity().getSharedPreferences(PREF_NAME_TOTAL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putInt("Totalsum", Totalsum).commit();;
        Log.d("TotalsumPRE", String.valueOf(Totalsum));

        editor.commit();
    }
}

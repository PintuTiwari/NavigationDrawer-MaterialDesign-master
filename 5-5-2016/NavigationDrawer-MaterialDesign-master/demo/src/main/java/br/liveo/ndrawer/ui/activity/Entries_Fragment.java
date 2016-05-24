package br.liveo.ndrawer.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import br.liveo.ndrawer.R;
//http://www.tutorialsbuzz.com/2015/10/Android-Sliding-TabLayout-ListView-WhatsApp.html
public class Entries_Fragment extends Fragment {

    TextView houserentpaid;
    LinearLayout linearLayout;
    RelativeLayout relativeLayout;
    Button save;
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
    int Aprilsnon,Maysnon,Junesnon,Julysnon, Augustsnon, Septembersnon, Octobersnon, Novembersnon, Decembersnon, Januarysnon, Februarysnon, Marchsnon, snowDensity;
    int Aprils,Mays,Junes, Julys, Augusts, Septembers, Octobers, Novembers, Decembers, Januarys, Februarys, Marchs;

    int Aprilsnondis,Maysnondis,Junesnondis,Julysnondis,Augustsnondis,Septembersnondis, Octobersnondis,Novembersnondis,Decembersnondis,Januarysnondis,Februarysnondis,Marchsnondis;
    int Aprilsdis,Maysdis,Junesdis, Julysdis, Augustsdis, Septembersdis, Octobersdis, Novembersdis, Decembersdis, Januarysdis, Februarysdis, Marchsdis;

    EditText Aprilnon,Maynon,Junenon, Julynon, Augustnon, Septembernon, Octobernon, Novembernon, Decembernon, Januarynon, Februarynon, Marchnon;
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
    String myValue;

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


        SharedPreferences get = getActivity().getSharedPreferences("EDITEXT", 0);
        Maysdis = get.getInt("Mays", -1);
        Aprilsnondis = get.getInt("Aprilsnon", -1);
        Aprilsdis = get.getInt("Aprils", -1);
        Maysnondis = get.getInt("Maysnon", -1);
        Junesnondis = get.getInt("Junesnon", -1);
        Junesdis = get.getInt("Junes", -1);
        Julysnondis = get.getInt("Julysnon", -1);
        Julysdis = get.getInt("Julys", -1);
        Augustsnondis = get.getInt("Augustsnon", -1);
        Augustsdis = get.getInt("Augusts", -1);
        Septembersnondis = get.getInt("Septembersnon", -1);
        Septembersdis = get.getInt("Septembers", -1);

        Octobersnondis = get.getInt("Octobersnon", -1);
        Octobersdis = get.getInt("Octobers", -1);
        Novembersnondis = get.getInt("Novembersnon", -1);
        Novembersdis = get.getInt("Novembers", -1);
        Decembersnondis = get.getInt("Decembersnon", -1);
        Decembersdis = get.getInt("Decembers", -1);
        Januarysnondis = get.getInt("Januarysnon", -1);
        Januarysdis = get.getInt("Januarys", -1);
        Februarysnondis = get.getInt("Februarysnon", -1);
        Februarysdis = get.getInt("Februarys", -1);
        Marchsnondis = get.getInt("Marchsnon", -1);
        Marchsdis = get.getInt("Marchs", -1);
        Log.d("onCreateView", String.valueOf(Maysdis));
        return inflater.inflate(R.layout.payinfo_layout, container, false);

    }


    // Called at the start of the visible lifetime.
    @Override
    public void onStart(){
        super.onStart();
        Log.d("onStart","onStart" );
        // Apply any required UI change now that the Fragment is visible.
    }

    // Called at the start of the active lifetime.
    @Override
    public void onResume(){
        super.onResume();
        Log.d("onResume", "onResume");
        // Resume any paused UI updates, threads, or processes required
        // by the Fragment but suspended when it became inactive.
    }

    // Called at the end of the active lifetime.
    @Override
    public void onPause(){
        Log.d("onPause", "onPause");
        // Suspend UI updates, threads, or CPU intensive processes
        // that don't need to be updated when the Activity isn't
        // the active foreground activity.
        // Persist all edits or state changes
        // as after this call the process is likely to be killed.
        super.onPause();
    }

    // Called to save UI state changes at the
    // end of the active lifecycle.
    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        // Save UI state changes to the savedInstanceState.
        // This bundle will be passed to onCreate, onCreateView, and
        // onCreateView if the parent Activity is killed and restarted.
        super.onSaveInstanceState(savedInstanceState);
        Log.d("onSaveInstanceState", "onSaveInstanceState");
    }

    // Called at the end of the visible lifetime.
    @Override
    public void onStop(){
        // Suspend remaining UI updates, threads, or processing
        // that aren't required when the Fragment isn't visible.
        super.onStop();
        Log.d("onStop", "onStop");
    }

    // Called when the Fragment's View has been detached.
    @Override
    public void onDestroyView() {
        // Clean up resources related to the View.
        super.onDestroyView();
        Log.d("onDestroyView", "onDestroyView");
    }

    // Called at the end of the full lifetime.
    @Override
    public void onDestroy(){
        // Clean up any resources including ending threads,
        // closing database connections etc.
        super.onDestroy();
    }

    // Called when the Fragment has been detached from its parent Activity.
    @Override
    public void onDetach() {
        super.onDetach();
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
        //  onCreate(null);

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
         * inflate Maysa layout from the apps resources and then change the text view to signify the position.
         */
        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            // Inflate a new layout from our resources
            View view = null;

            if (position == 0) {
                view = getActivity().getLayoutInflater().inflate(R.layout.expandable_child_1_layout, container, false);
                TextView clickhere=(TextView)view.findViewById(R.id.clickhere);
                houserentpaid=(TextView)view.findViewById(R.id.houserentpaid);
                linearLayout=(LinearLayout)view.findViewById(R.id.layout_main_traning);
                relativeLayout=(RelativeLayout)view.findViewById(R.id.layout_main_traning1);

                houserentpaid.setText(Integer.toString(snowDensity));
                Button   save=(Button)view.findViewById(R.id.button4);
                clickhere.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        addnewdetails();
                    }
                });

                save.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        try {
                            int nViews = linearLayout.getChildCount();
                            Log.d("linearLayout", "hi" + nViews);
                            linearLayout.getId();

                            int nViews1 = relativeLayout.getChildCount();
                            Log.d("relativeLayout", "hi" + nViews1);

                            for (int i = 0; i <= nViews; i++) {
                                View child = linearLayout.getChildAt(i);
                                for (int j = 0; j <= nViews1; j++) {
                                    View child1 = relativeLayout.getChildAt(i);
                                    Log.d("child1", "hi" + child1);
                                    relativeLayout.getId();
                                    Log.d("child1:getClass", child1.getClass().toString());
                                    if (child1 instanceof TextInputLayout) {
                                        TextInputLayout txtInputLayout = (TextInputLayout) child1;
                                        EditText edt = txtInputLayout.getEditText();
                                        edt.getId();
                                        edt.setEnabled(true);
                                        edt.setClickable(true);
                                        edt.getText();
                                        Log.d("edt.getText() ", edt.getText().toString());

                                    } else if (child instanceof Spinner) {
                                        Spinner txtvw = (Spinner) child;
                                        txtvw.getTag();
                                        txtvw.setEnabled(true);
                                        txtvw.setClickable(true);

                                        txtvw.getSelectedItem();
                                        Log.d("spnr key :", txtvw.getSelectedItem() + "");
                                    }


                                }
                            }
                            Log.d("End of onClick", "bye");
                        } catch (Exception e) {
                            Log.d("Excp ", "onlick ", e);
                            e.printStackTrace();
                        }


                    }
                });


               /* save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                     *//*   Log.d("spnr key :", "key");
                     *
                     * [14:55:14 | Edited 14:56:14] Sunil Tiwari: put
if( relative != null){
for(){
}
 }
                     *
                       try {
                            int nViews = linearLayout.getChildCount();
                            Log.d("linearLayout", "hi" + nViews);
                            linearLayout.getId();

                            int nViews1 = relativeLayout.getChildCount();
                            Log.d("relativeLayout", "hi" + nViews1);

                            for (int i = 0; i <= nViews; i++) {
                                View child = linearLayout.getChildAt(i);
                                for (int j = 0; j <= nViews1; j++) {
                                    View child1 = relativeLayout.getChildAt(i);
                                    Log.d("child1", "hi" + child1);
                                    relativeLayout.getId();
                                    Log.d("child1:getClass", child1.getClass().toString());
                                    if (child1 instanceof TextInputLayout) {
                                        TextInputLayout txtInputLayout = (TextInputLayout) child1;
                                        EditText edt = txtInputLayout.getEditText();
                                        edt.getId();
                                        edt.setEnabled(true);
                                        edt.setClickable(true);
                                        edt.getText();
                                        Log.d("edt.getText() ", edt.getText().toString());

                                    } else if (child instanceof Spinner) {
                                        Spinner txtvw = (Spinner) child;
                                        txtvw.getTag();
                                        txtvw.setEnabled(true);
                                        txtvw.setClickable(true);

                                        txtvw.getSelectedItem();
                                        Log.d("spnr key :", txtvw.getSelectedItem() + "");
                                    }


                                }
                            }
                            Log.d("End of onClick", "bye");
                        } catch (Exception e) {
                            Log.d("Excp ", "onlick ", e);
                            e.printStackTrace();
                        }

*//*
                        addnewdetails();
                    }
                });*/
                container.addView(view);


            }



            if(position==1) {
               /* view = getActivity().getLayoutInflater().inflate(R.layout.expandable_child_1_layout, container, false);
                TextView clickhere=(TextView)view.findViewById(R.id.clickhere);
                clickhere.setVisibility(View.GONE);
                container.addView(view);
*/

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

        //linearLayout=(LinearLayout)dialog_layout.findViewById(R.id.layout_main_traning);


        April.setText(Integer.toString(Aprilsdis));
            Aprilnon.setText(Integer.toString(Aprilsnondis));
            May.setText(Integer.toString(Maysdis));
            Maynon.setText(Integer.toString(Maysnondis));
            June.setText(Integer.toString(Junesdis));
            Junenon.setText(Integer.toString(Junesnondis));
            July.setText(Integer.toString(Julysdis));
            Julynon.setText(Integer.toString(Julysnondis));
            August.setText(Integer.toString(Augustsdis));
            Augustnon.setText(Integer.toString(Augustsnondis));
            September.setText(Integer.toString(Septembersdis));
            Septembernon.setText(Integer.toString(Septembersnondis));
            October.setText(Integer.toString(Octobersdis));
            Octobernon.setText(Integer.toString(Octobersnondis));
            Novembernon.setText(Integer.toString(Novembersnondis));
            November.setText(Integer.toString(Novembersdis));
            December.setText(Integer.toString(Decembersdis));
            Decembernon.setText(Integer.toString(Decembersnondis));
            January.setText(Integer.toString(Januarysdis));
            Januarynon.setText(Integer.toString(Januarysnondis));
            February.setText(Integer.toString(Februarysdis));
            Februarynon.setText(Integer.toString(Februarysnondis));
            March.setText(Integer.toString(Marchsdis));
            Marchnon.setText(Integer.toString(Marchsnondis));
            Log.d("Maysdis", String.valueOf(Maysdis));





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
                Log.d("SharedMayclearb", String.valueOf(Mays));

                SharedPreferences settings1 = getActivity().getSharedPreferences("EDITEXT", 0);
                SharedPreferences.Editor editor1 = settings1.edit();
                editor1.clear();
                Log.d("SharedMayclearf", String.valueOf(Mays));


                // Create an instance of ExampleFragment
                //PayInfo_Activity firstFragment = new PayInfo_Activity();
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

        int Totalsum=0;

        if(April !=  null&&April.length() > 0)
        {
            Aprils = Integer.parseInt(April.getText().toString());

            Totalsum = Totalsum + Aprils;
            April.setText(Integer.toString(Aprils));

        }
        if (Aprilnon != null && Aprilnon.length() > 0) {
            Aprilsnon = Integer.parseInt(Aprilnon.getText().toString());
            Totalsum = Totalsum + Aprilsnon;
            Log.d("Totalsum", String.valueOf(Totalsum));
            Aprilnon.setText(Integer.toString(Aprilsnon));
        }

        if (May != null && May.length() > 0) {
            Mays = Integer.parseInt(May.getText().toString());
            Totalsum = Totalsum + Mays;
            Log.d("Totalsum", String.valueOf(Totalsum));
            May.setText(Integer.toString(Mays));
            Log.d("Mays", String.valueOf(Mays));


        }
        if (Maynon != null && Maynon.length() > 0) {
            Maysnon = Integer.parseInt(Maynon.getText().toString());
            Totalsum = Totalsum + Maysnon;
            Log.d("Totalsum", String.valueOf(Totalsum));
            Maynon.setText(Integer.toString(Maysnon));
        }
        if (June != null && June.length() > 0) {
            Junes = Integer.parseInt(June.getText().toString());
            Totalsum = Totalsum + Junes;
            Log.d("Totalsum", String.valueOf(Totalsum));
            June.setText(Integer.toString(Junes));

        }

        if (Junenon != null && Junenon.length() > 0) {
            Junesnon = Integer.parseInt(Junenon.getText().toString());
            Totalsum = Totalsum + Junesnon;
            Log.d("Totalsum", String.valueOf(Totalsum));
            Junenon.setText(Integer.toString(Junesnon));
        }

        if (July != null && July.length() > 0) {
            Julys = Integer.parseInt(July.getText().toString());
            Totalsum = Totalsum + Julys;
            Log.d("Totalsum", String.valueOf(Totalsum));
            July.setText(Integer.toString(Julys));

        }
        if (Julynon != null && Julynon.length() > 0) {
            Julysnon = Integer.parseInt(Julynon.getText().toString());
            Totalsum = Totalsum + Julysnon;
            Log.d("Totalsum", String.valueOf(Totalsum));
            Julynon.setText(Integer.toString(Julysnon));

        }

        if (August != null && August.length() > 0) {
            Augusts = Integer.parseInt(August.getText().toString());
            Totalsum = Totalsum + Augusts;
            Log.d("Totalsum", String.valueOf(Totalsum));
            August.setText(Integer.toString(Augusts));

        }

        if (Augustnon != null && Augustnon.length() > 0) {
            Augustsnon = Integer.parseInt(Augustnon.getText().toString());
            Totalsum = Totalsum + Augustsnon;
            Log.d("Totalsum", String.valueOf(Totalsum));
            Augustnon.setText(Integer.toString(Augustsnon));

        }


        if (September != null && September.length() > 0) {
            Septembers = Integer.parseInt(September.getText().toString());
            Totalsum = Totalsum + Septembers;
            Log.d("Totalsum", String.valueOf(Totalsum));
            September.setText(Integer.toString(Septembers));

        }

        if (Septembernon != null && Septembernon.length() > 0) {
            Septembersnon = Integer.parseInt(Septembernon.getText().toString());
            Totalsum = Totalsum + Septembersnon;
            Log.d("Totalsum", String.valueOf(Totalsum));
            Septembernon.setText(Integer.toString(Septembersnon));

        }

        if (October != null && October.length() > 0) {
            Octobers = Integer.parseInt(October.getText().toString());
            Totalsum = Totalsum + Octobers;
            Log.d("Totalsum", String.valueOf(Totalsum));
            October.setText(Integer.toString(Octobers));

        }

        if (Octobernon != null && Octobernon.length() > 0) {
            Octobersnon = Integer.parseInt(Octobernon.getText().toString());
            Totalsum = Totalsum + Octobersnon;
            Log.d("Totalsum", String.valueOf(Totalsum));
            Octobernon.setText(Integer.toString(Octobersnon));

        }

        if (Novembernon != null && Novembernon.length() > 0) {
            Novembersnon = Integer.parseInt(Novembernon.getText().toString());
            Totalsum = Totalsum + Novembersnon;
            Log.d("Totalsum", String.valueOf(Totalsum));
            Novembernon.setText(Integer.toString(Novembersnon));

        }
        if (November != null && November.length() > 0) {
            Novembers = Integer.parseInt(November.getText().toString());
            Totalsum = Totalsum + Novembers;
            Log.d("Totalsum", String.valueOf(Totalsum));
            November.setText(Integer.toString(Novembers));

        }
        if (December != null && December.length() > 0) {
            Decembers = Integer.parseInt(December.getText().toString());
            Totalsum = Totalsum + Decembers;
            Log.d("Totalsum", String.valueOf(Totalsum));
            December.setText(Integer.toString(Decembers));

        }
        if (Decembernon != null && Decembernon.length() > 0) {
            Decembersnon = Integer.parseInt(Decembernon.getText().toString());
            Totalsum = Totalsum + Decembersnon;
            Log.d("Totalsum", String.valueOf(Totalsum));
            Decembernon.setText(Integer.toString(Decembersnon));

        }

        if (January != null && January.length() > 0) {
            Januarys = Integer.parseInt(January.getText().toString());
            Totalsum = Totalsum + Januarys;
            Log.d("Totalsum", String.valueOf(Totalsum));
            January.setText(Integer.toString(Januarys));

        }
        if (Januarynon != null && Januarynon.length() > 0) {
            Januarysnon = Integer.parseInt(Januarynon.getText().toString());
            Totalsum = Totalsum + Januarysnon;
            Log.d("Totalsum", String.valueOf(Totalsum));
            Januarynon.setText(Integer.toString(Januarysnon));

        }

        if (February != null && February.length() > 0) {
            Februarys = Integer.parseInt(February.getText().toString());
            Totalsum = Totalsum + Februarys;
            Log.d("Totalsum", String.valueOf(Totalsum));
            February.setText(Integer.toString(Februarys));

        }
        if (Februarynon != null && Februarynon.length() > 0) {
            Februarysnon = Integer.parseInt(February.getText().toString());
            Totalsum = Totalsum + Februarysnon;
            Log.d("Totalsum", String.valueOf(Totalsum));
            Februarynon.setText(Integer.toString(Februarysnon));

        }

        if (March != null && March.length() > 0) {
            Marchs = Integer.parseInt(March.getText().toString());
            Totalsum = Totalsum + Marchs;
            Log.d("Totalsum", String.valueOf(Totalsum));
            March.setText(Integer.toString(Marchs));

        }
        if (Marchnon != null && Marchnon.length() > 0) {
            Marchsnon = Integer.parseInt(Marchnon.getText().toString());
            Totalsum = Totalsum + Marchsnon;
            Log.d("Totalsum", String.valueOf(Totalsum));
            Marchnon.setText(Integer.toString(Marchsnon));

        }
        houserentpaid.setText(Integer.toString(Totalsum));
        Log.d("houserentpaid", String.valueOf(Totalsum));

        //  houserentpaid.setText(Integer.toString(snowDensity));

        Log.d("Aprils", String.valueOf(Aprils));
        Log.d("Aprilsnon", String.valueOf(Aprilsnon));
        SharedPreferences settings = getActivity().getSharedPreferences("YOUR_PREF_NAME", 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putInt("SNOW_DENSITY", Totalsum);
        editor.commit();

        SharedPreferences settings1 = getActivity().getSharedPreferences("EDITEXT", 0);
        SharedPreferences.Editor editor1 = settings1.edit();

        editor1.putInt("Aprils", Aprils);
        editor1.putInt("Aprilsnon", Aprilsnon);
        editor1.putInt("Mays", Mays);
        editor1.putInt("Maysnon", Maysnon);
        editor1.putInt("Junes", Junes);
        editor1.putInt("Junesnon", Junesnon);
        editor1.putInt("Julys", Julys);
        editor1.putInt("Julysnon", Julysnon);
        editor1.putInt("Augusts", Augusts);
        editor1.putInt("Augustsnon", Augustsnon);
        editor1.putInt("Septembers", Septembers);
        editor1.putInt("Septembersnon", Septembersnon);
        editor1.putInt("Octobers", Octobers);

        editor1.putInt("Octobersnon", Octobersnon);
        editor1.putInt("Novembers", Novembers);
        editor1.putInt("Novembersnon", Novembersnon);
        editor1.putInt("Decembers", Decembers);
        editor1.putInt("Decembersnon", Decembersnon);
        editor1.putInt("Januarys", Januarys);
        editor1.putInt("Januarysnon", Januarysnon);
        editor1.putInt("Februarys", Februarys);
        editor1.putInt("Februarysnon", Februarysnon);
        editor1.putInt("Marchs", Marchs);
        editor1.putInt("Marchsnon", Marchsnon);
        Log.d("SharedMayssave", String.valueOf(Mays));
        editor1.commit();

        SharedPreferences get = getActivity().getSharedPreferences("EDITEXT", 0);
        Maysdis = get.getInt("Mays", -1);
        Aprilsnondis = get.getInt("Aprilsnon", -1);
        Aprilsdis = get.getInt("Aprils", -1);
        Maysnondis = get.getInt("Maysnon", -1);
        Junesnondis = get.getInt("Junesnon", -1);
        Junesdis = get.getInt("Junes", -1);
        Julysnondis = get.getInt("Julysnon", -1);
        Julysdis = get.getInt("Julys", -1);
        Augustsnondis = get.getInt("Augustsnon", -1);
        Augustsdis = get.getInt("Augusts", -1);
        Septembersnondis = get.getInt("Septembersnon", -1);
        Septembersdis = get.getInt("Septembers", -1);

        Octobersnondis = get.getInt("Octobersnon", -1);
        Octobersdis = get.getInt("Octobers", -1);
        Novembersnondis = get.getInt("Novembersnon", -1);
        Novembersdis = get.getInt("Novembers", -1);
        Decembersnondis = get.getInt("Decembersnon", -1);
        Decembersdis = get.getInt("Decembers", -1);
        Januarysnondis = get.getInt("Januarysnon", -1);
        Januarysdis = get.getInt("Januarys", -1);
        Februarysnondis = get.getInt("Februarysnon", -1);
        Februarysdis = get.getInt("Februarys", -1);
        Marchsnondis = get.getInt("Marchsnon", -1);
        Marchsdis = get.getInt("Marchs", -1);
        Log.d("SharedMayGet", String.valueOf(Maysdis));

    }


    private void personal_setPreference(int Totalsum) {
        SharedPreferences setting = this.getActivity().getSharedPreferences(PREF_NAME_TOTAL, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = setting.edit();
        editor.putInt("Totalsum", Totalsum).commit();;
        Log.d("TotalsumPRE", String.valueOf(Totalsum));

        editor.commit();

    }

}


//Set the id for all the textfields
    /*   public void total()
       {
           //Set the id for all the textfields
           List<String> monthsList = new ArrayList();
           monthsList.put("April");
           monthsList.put("May");
           monthsList.put("June");
           monthsList.put("July");
           monthsList.put("August");
           monthsList.put("September");
           monthsList.put("October");
           monthsList.put("November");
           monthsList.put("December");
           monthsList.put("January");
           monthsList.put("February");
           monthsList.put("March");

           int Totalsum = 0;
           for(String month : monthsList){
               String m1 = (Editext)findviewbyid(month).getText();
               string m2 =  (Editext)findviewbyid(month+"non").getText();
               if(m1 != null && m1.length >0){
                   Totalsum = Totalsum +Integer.parseInt(m1);
               }
               if(m2 != null && m2.length >0){
                   Totalsum = Totalsum +Integer.parseInt(m2);
               }
           }

       }*/
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
package br.liveo.ndrawer.ui.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ListView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import br.liveo.ndrawer.R;

/**
 * Created by dhawal sodha parmar on 4/30/2015.
 */
public class PayInfo_Activity extends Fragment {

    String[] items = {"Salary Info", "PF Info", "Loan Info","Gratuity"};
    ListView lv;
    Context context;
    private RecyclerView recyclerView;
    private PayInfoRecycleAdapter adapter;
    private ArrayList<Person> friendArrayList;
    String getMyDetils_String;
    Map<String, String> responseMap = new LinkedHashMap<String, String>();
    EditText Month,Year,NetPay;
    private static final String PREF_NAME_PRIFRNCR = "isregisterd";
    String Months, Years, NetPays,SchemaName, userId, CompanyId,Fieldvalue;
    String part1,part44,part66,part55;
    int part4,part6,part5;
    private com.github.clans.fab.FloatingActionButton mFab;

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
        Gson gson = new Gson();
        getMyDetils_String  = setting.getString("response", "response");
        SchemaName = setting.getString("SchemaName", "SchemaName");
        userId = setting.getString("userId", "userId");
        CompanyId = setting.getString("CompanyId", "CompanyId");
        friendArrayList = new ArrayList<>();
        Log.d("SchemaNameAcdemic", SchemaName.toString());
        Log.d("userIdAcdemic", userId.toString());
        Log.d("CompanyIdAcdemic", CompanyId.toString());
        try {
            if(getMyDetils_String.length() > 0) {
                friendArrayList.clear();
                JSONArray internships = new JSONArray(getMyDetils_String);
                JSONObject leagueObject = internships.getJSONObject(0);
                //Loop the Array
                for (int i = 0; i < leagueObject.length(); i++) {
                    Log.e("Message", "loop");
                    HashMap<String, String> map = new HashMap<String, String>();
                    JSONObject jsonObject = internships.getJSONObject(i);
                    Log.d("Employement in page", jsonObject.toString());
                    Person person=new Person();
                    if (!jsonObject.isNull("Name")) {
                        person.Month = jsonObject.getString("Name");
                        Log.d("Company",person.Month.toString());
                    }
                    if (!jsonObject.isNull("Address")) {
                        person.Year = jsonObject.getString("Address");
                        Log.d("Company",person.Year.toString());
                    }
                    if (!jsonObject.isNull("Relation")) {
                        person.NetPay = jsonObject.getString("Relation");
                        Log.d("Company",person.NetPay.toString());
                    }
                    friendArrayList.add(i, person);
                }
            }
        } catch(JSONException e) {
            Log.e("log_tag", "Error parsing data "+e.toString());
        }
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
                view = getActivity().getLayoutInflater().inflate(R.layout.payinfoitem_layout, container, false);
                // Add the newly created View to the ViewPager
                recyclerView = (RecyclerView) view.findViewById(R.id.recyle_payinfosalary);
               // mFab = (com.github.clans.fab.FloatingActionButton) view.findViewById(R.id.menu1);

                container.addView(view);
             /*   mFab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

               *//* DialogFragment newFragment = new Academic_Dilog();
                newFragment.show(getSupportFragmentManager(), "missiles");*//*
                    }
                });
*/
                adapter = new PayInfoRecycleAdapter(getActivity(), friendArrayList);
                recyclerView.setAdapter(adapter);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);

            }


           if(position==1) {
               view = getActivity().getLayoutInflater().inflate(R.layout.payinfoitem_layout, container, false);
               // Add the newly created View to the ViewPager
               recyclerView = (RecyclerView) view.findViewById(R.id.recyle_payinfosalary);
               container.addView(view);

              PfInfoRecycleAdapter   adapter = new PfInfoRecycleAdapter(getActivity(), friendArrayList);
              // PfInfoRecycleAdapter   adapter = new PfInfoRecycleAdapter(getActivity());

               recyclerView.setAdapter(adapter);
               final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
               layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
               recyclerView.setLayoutManager(layoutManager);
               recyclerView.setHasFixedSize(true);

           }
            if(position==2) {
                view = getActivity().getLayoutInflater().inflate(R.layout.payinfoitem_layout, container, false);
                // Add the newly created View to the ViewPager
                recyclerView = (RecyclerView) view.findViewById(R.id.recyle_payinfosalary);
                container.addView(view);

                LoanInfoRecycleAdapter  adapter = new LoanInfoRecycleAdapter(getActivity(), friendArrayList);
                recyclerView.setAdapter(adapter);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
            }

           if(position==3) {
              view = getActivity().getLayoutInflater().inflate(R.layout.payinfoitem_layout, container, false);
                // Add the newly created View to the ViewPager
                recyclerView = (RecyclerView) view.findViewById(R.id.recyle_payinfosalary);
                container.addView(view);

                adapter = new PayInfoRecycleAdapter(getActivity(), friendArrayList);
                recyclerView.setAdapter(adapter);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
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
}

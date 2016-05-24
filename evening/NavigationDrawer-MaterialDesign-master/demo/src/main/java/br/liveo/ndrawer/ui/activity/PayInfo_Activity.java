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
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import br.liveo.ndrawer.R;

/**
 * Created by dhawal sodha parmar on 4/30/2015.
 */
public class PayInfo_Activity extends Fragment {

    String[] items = {"Salary Info", "PF Info", "Loan Info"};
    ListView lv;
    String pfaccoutn;
    Context context;
    private RecyclerView recyclerView;
    private PayInfoRecycleAdapter adapter;
    private ArrayList<Person> friendArrayList,salaryArrayList,loanArrayList;
    String getMyDetils_String,salaryarray,loanstring;
    Map<String, String> responseMap = new LinkedHashMap<String, String>();
    EditText Month,Year,NetPay;
    private static final String PREF_NAME_PRIFRNCR = "isregisterd";
    String Months, Years, NetPays,SchemaName, userId, CompanyId,Fieldvalue;
    String part1,part44,years,months;
    int part4,year,month;
    private com.github.clans.fab.FloatingActionButton mFab;
    TextView PfAccNo;

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
      // getMyDetils_String  = setting.getString("response", "response");
        getMyDetils_String= "[{'Id':0,'CompanyId':1,'EmployeeId':1,'Date':'2015-05-01T00:00:00','PF':15,'EPF':5,'FPF':10,'TotalPF':30,'VPF':0,'PfAccNo':'123456'},{'Id':0,'CompanyId':1,'EmployeeId':1,'Date':'2015-06-01T00:00:00','PF':1479,'EPF':938,'FPF':541,'TotalPF':2958,'VPF':0,'PfAccNo':'123456'},{'Id':0,'CompanyId':1,'EmployeeId':1,'Date':'2015-07-01T00:00:00','PF':1479,'EPF':938,'FPF':541,'TotalPF':2958,'VPF':0,'PfAccNo':'123456'}]";
        salaryarray= "[{'Id':0,'CompanyId':1,'EmployeeId':1,'Date':'2015-07-01T00:00:00','NetPay':138017},{'Id':0,'CompanyId':1,'EmployeeId':1,'Date':'2015-06-01T00:00:00','NetPay':143017},{'Id':0,'CompanyId':1,'EmployeeId':1,'Date':'2015-05-01T00:00:00','NetPay':1431}]";
        loanstring="[{'CompanyId':0,'EmployeeId':0,'Date':'2017-01-01T00:00:00','LoanCode':'TESTENTRY','LoanDescription':'HOME LOAN','EmployeeName':null,'Amount':10000,'Paid':0,'Balance':10000,'DateString':null},{'CompanyId':0,'EmployeeId':0,'Date':'2017-09-07T00:00:00','LoanCode':'TESTENTRY','LoanDescription':'HOME LOAN','EmployeeName':null,'Amount':10000,'Paid':0,'Balance':10000,'DateString':null},{'CompanyId':0,'EmployeeId':0,'Date':'2015-01-01T00:00:00','LoanCode':'TESTENTRY','LoanDescription':'HOME LOAN','EmployeeName':null,'Amount':1000000,'Paid':300000,'Balance':700000,'DateString':null}]";
        SchemaName = setting.getString("SchemaName", "SchemaName");
        userId = setting.getString("userId", "userId");
        CompanyId = setting.getString("CompanyId", "CompanyId");
        friendArrayList = new ArrayList<>();
        salaryArrayList= new ArrayList<>();
        loanArrayList = new ArrayList<>();
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
                    if (!jsonObject.isNull("Date")) {
                      //  person.pfdate = jsonObject.getString("Date");
                        String date = jsonObject.getString("Date");
                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                        Date tempDate=simpleDateFormat.parse(date);
                        System.out.println("DATEOFBIRTH1 tempDate = " + tempDate);
                        SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                        System.out.println("DATEOFBIRTH1 = " + outputDateFormat.format(tempDate));
                        String date1=outputDateFormat.format(tempDate);
                        String[] part3 = date1.split("/");
                        part44 = part3[0]; // 004
                        part4=Integer.parseInt(part44);

                        months = part3[1]; // 034556
                        month=Integer.parseInt(months);
                        person.pfMonth=months;
                        Log.d("pfMonth", String.valueOf(person.pfMonth));

                        years= part3[2];
                        year=Integer.parseInt(years);
                        person.pfYear=years;
                        Log.d("pfYear", String.valueOf(person.pfYear));

                        Log.d("Date",person.pfYear.toString());
                    }
                    if (!jsonObject.isNull("PF")) {
                        person.pfPerMonth = jsonObject.getString("PF");
                        Log.d("pfPerMonth",person.pfPerMonth.toString());
                    }
                    if (!jsonObject.isNull("EPF")) {
                        person.EPF = jsonObject.getString("EPF");
                        Log.d("EPF",person.EPF.toString());
                    }
                    if (!jsonObject.isNull("FPF")) {
                        person.FPF = jsonObject.getString("FPF");
                        Log.d("FPF",person.FPF.toString());
                    }
                    if (!jsonObject.isNull("TotalPF")) {
                        person.Total = jsonObject.getString("TotalPF");
                        Log.d("TotalPF",person.Total.toString());
                    }
                    if (!jsonObject.isNull("VPF")) {
                        person.VPF = jsonObject.getString("VPF");
                        Log.d("VPF",person.VPF.toString());
                    }
                    if (!jsonObject.isNull("PfAccNo")) {
                        person.PfAccNo = jsonObject.getString("PfAccNo");
                        pfaccoutn=person.PfAccNo.toString();

                        Log.d("PfAccNo",person.PfAccNo.toString());
                    }

                    friendArrayList.add(i, person);

                }
            }
        } catch(JSONException e) {
            Log.e("log_tag", "Error parsing data "+e.toString());
        } catch (ParseException e) {
            e.printStackTrace();
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
                PfAccNo = (TextView) view.findViewById(R.id.pfaccoutnumber);
                PfAccNo.setVisibility(View.GONE);
                container.addView(view);
                try {
                    if(salaryarray.length() > 0) {
                        salaryArrayList.clear();
                        JSONArray internships = new JSONArray(salaryarray);
                        JSONObject leagueObject = internships.getJSONObject(0);
                        //Loop the Array
                        for (int i = 0; i < leagueObject.length(); i++) {
                            Log.e("Message", "loop");
                            HashMap<String, String> map = new HashMap<String, String>();
                            JSONObject jsonObject = internships.getJSONObject(i);
                            Log.d("salaryArrayList", jsonObject.toString());
                            Person person=new Person();
                            if (!jsonObject.isNull("Date")) {
                               // person.salarydate = jsonObject.getString("Date");
                                String date = jsonObject.getString("Date");

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                                Date tempDate=simpleDateFormat.parse(date);
                                System.out.println("DATEOFBIRTH1 tempDate = " + tempDate);
                                SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                System.out.println("DATEOFBIRTH1 = " + outputDateFormat.format(tempDate));
                                String date1=outputDateFormat.format(tempDate);
                                String[] part3 = date1.split("/");
                                part44 = part3[0]; // 004
                                part4=Integer.parseInt(part44);

                                months = part3[1]; // 034556
                                month=Integer.parseInt(months);
                                person.salarymonth=months;
                                Log.d("NetPay", String.valueOf(person.salarymonth));

                                years= part3[2];
                                year=Integer.parseInt(years);
                                person.salaryyear=years;
                                Log.d("NetPay", String.valueOf(person.salaryyear));

                               // Log.d("salarydate",person.salarydate.toString());
                            }
                            if (!jsonObject.isNull("NetPay")) {
                                person.salaryNetPay = jsonObject.getString("NetPay");
                                Log.d("NetPay",person.salaryNetPay.toString());
                            }
                            salaryArrayList.add(i, person);

                        }
                    }
                } catch(JSONException e) {
                    Log.e("log_tag", "Error parsing data "+e.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                adapter = new PayInfoRecycleAdapter(getActivity(), salaryArrayList);
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
               PfAccNo = (TextView) view.findViewById(R.id.pfaccoutnumber);
               PfAccNo.setText("PF A/C No" +" "+pfaccoutn);

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
                PfAccNo = (TextView) view.findViewById(R.id.pfaccoutnumber);
                PfAccNo.setVisibility(View.GONE);
                container.addView(view);
                try {
                    if(loanstring.length() > 0) {
                        loanArrayList.clear();
                        JSONArray internships = new JSONArray(loanstring);
                        JSONObject leagueObject = internships.getJSONObject(0);

                        for (int i = 0; i < leagueObject.length(); i++) {
                            Log.e("Message", "loop");
                            HashMap<String, String> map = new HashMap<String, String>();
                            JSONObject jsonObject = internships.getJSONObject(i);
                            Log.d("loanArrayList", jsonObject.toString());
                            Person person=new Person();
                            if (!jsonObject.isNull("Date")) {
                              //  person.loanDate = jsonObject.getString("Date");

                                String date = jsonObject.getString("Date");

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

                                Date tempDate=simpleDateFormat.parse(date);
                                System.out.println("DATEOFBIRTH1 tempDate = " + tempDate);
                                SimpleDateFormat outputDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                                System.out.println("DATEOFBIRTH1 = " + outputDateFormat.format(tempDate));
                                String date1=outputDateFormat.format(tempDate);
                                String[] part3 = date1.split("/");
                                part44 = part3[0]; // 004
                                part4=Integer.parseInt(part44);

                                months = part3[1]; // 034556
                                month=Integer.parseInt(months);

                                years= part3[2];
                                year=Integer.parseInt(years);
                                person.loanDate=date1;
                            }
                            if (!jsonObject.isNull("LoanCode")) {
                                person.LoanCode = jsonObject.getString("LoanCode");
                                Log.d("LoanCode",person.LoanCode.toString());
                            }
                            if (!jsonObject.isNull("LoanDescription")) {
                                person.LoanDescription = jsonObject.getString("LoanDescription");
                                Log.d("LoanDescription",person.LoanDescription.toString());
                            }
                            if (!jsonObject.isNull("Amount")) {
                                person.loanAmount = jsonObject.getString("Amount");
                                Log.d("loanAmount",person.loanAmount.toString());
                            }
                            if (!jsonObject.isNull("Paid")) {
                                person.loanPaid = jsonObject.getString("Paid");
                                Log.d("Paid",person.loanPaid.toString());
                            }
                            if (!jsonObject.isNull("Balance")) {
                                person.loanBalance = jsonObject.getString("Balance");
                                Log.d("Balance",person.loanBalance.toString());
                            }

                            loanArrayList.add(i, person);

                        }
                    }
                } catch(JSONException e) {
                    Log.e("log_tag", "Error parsing data "+e.toString());
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                LoanInfoRecycleAdapter  adapter = new LoanInfoRecycleAdapter(getActivity(), loanArrayList);
                recyclerView.setAdapter(adapter);
                final LinearLayoutManager layoutManager = new LinearLayoutManager(context);
                layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setHasFixedSize(true);
            }

          /* if(position==3) {
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
                }*/
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

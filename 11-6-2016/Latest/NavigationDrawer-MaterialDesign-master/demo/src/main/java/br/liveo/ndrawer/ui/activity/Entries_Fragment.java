package br.liveo.ndrawer.ui.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import br.liveo.ndrawer.R;
//http://www.tutorialsbuzz.com/2015/10/Android-Sliding-TabLayout-ListView-WhatsApp.html
//http://zacktutorials.blogspot.in/2014/07/android-downloading-and-viewing-pdf.html
//http://androiddevelopement.blogspot.in/2012/01/select-and-crop-image-on-android.html
//https://reecon.wordpress.com/2010/04/25/uploading-files-to-http-server-using-post-android-sdk/
//http://vba9simo.blogspot.in/2015/10/load-file-from-external-storage-using.html
//https://developer.xamarin.com/recipes/android/other_ux/pick_image/
public class Entries_Fragment extends Fragment  {

    private static final int SELECT_PICTURE =1 ;
    TextView houserentpaid,declarationamount;
    LinearLayout linearLayout;
    RelativeLayout relativeLayout;
    Button uploadsubmit;
    Button save;
    String[] items = {"TDS Entry", "Worksheet - TDS", "TDS Proof Entry"};
    String responnsedisplay="[{'Id':24,'CompanyId':1,'FinNo':1,'OrderNo':1,'ItSec':'RENTPAID','SecHead':'House Rent Paid','SubSec':'','SsecDesc':'','MaxVal':0,'Amount':'0','ProofAmount':0,'listTdsDecl':null},{'Id':25,'CompanyId':1,'FinNo':1,'OrderNo':2,'ItSec':'RENTPAID','SecHead':'','SubSec':'RENTPAID','SsecDesc':'House Rent Paid','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':26,'CompanyId':1,'FinNo':1,'OrderNo':2,'ItSec':'SEC16','SecHead':'Deductions :','SubSec':'','SsecDesc':'','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':27,'CompanyId':1,'FinNo':1,'OrderNo':3,'ItSec':'SEC80C','SecHead':'Section 80C','SubSec':'','SsecDesc':'','MaxVal':100000,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':28,'CompanyId':1,'FinNo':1,'OrderNo':6,'ItSec':'SECVIA','SecHead':'Aggregate of Deductible amounts under Chapter VI - A','SubSec':'','SsecDesc':'','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':29,'CompanyId':1,'FinNo':1,'OrderNo':2,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD14','SsecDesc':'LIC premium paid','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':30,'CompanyId':1,'FinNo':1,'OrderNo':3,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD15','SsecDesc':'Public provident fund','MaxVal':0,'Amount':-2,'ProofAmount':0,'listTdsDecl':null},{'Id':31,'CompanyId':1,'FinNo':1,'OrderNo':4,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD16','SsecDesc':'Approved super annuation fund','MaxVal':0,'Amount':-2,'ProofAmount':0,'listTdsDecl':null},{'Id':32,'CompanyId':1,'FinNo':1,'OrderNo':5,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD17','SsecDesc':'Any sum paid on housing document','MaxVal':0,'Amount':-2,'ProofAmount':0,'listTdsDecl':null},{'Id':33,'CompanyId':1,'FinNo':1,'OrderNo':6,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD18','SsecDesc':'PF deducted by previous employer','MaxVal':0,'Amount':-2,'ProofAmount':0,'listTdsDecl':null},{'Id':34,'CompanyId':1,'FinNo':1,'OrderNo':7,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD19','SsecDesc':'NSC','MaxVal':0,'Amount':-2,'ProofAmount':0,'listTdsDecl':null},{'Id':35,'CompanyId':1,'FinNo':1,'OrderNo':8,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD20','SsecDesc':'ULIP','MaxVal':0,'Amount':-2,'ProofAmount':0,'listTdsDecl':null},{'Id':36,'CompanyId':1,'FinNo':1,'OrderNo':9,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD21','SsecDesc':'Contribution to pension fund','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':37,'CompanyId':1,'FinNo':1,'OrderNo':10,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD22','SsecDesc':'Contribution to ELSS','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':38,'CompanyId':1,'FinNo':1,'OrderNo':11,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD23','SsecDesc':'Tution fees paid','MaxVal':12000,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':39,'CompanyId':1,'FinNo':1,'OrderNo':1,'ItSec':'SECVIA','SecHead':'','SubSec':'SUBFLD25','SsecDesc':'Medical insurance premium (Mediclaim)','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':40,'CompanyId':1,'FinNo':1,'OrderNo':2,'ItSec':'SECVIA','SecHead':'','SubSec':'SUBFLD26','SsecDesc':'Repayment of loan taken for higher education','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':41,'CompanyId':1,'FinNo':1,'OrderNo':3,'ItSec':'SECVIA','SecHead':'','SubSec':'SUBFLD27','SsecDesc':'Donations to certain funds, charitable institutions','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':42,'CompanyId':1,'FinNo':1,'OrderNo':13,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD29','SsecDesc':'Repayment of housing loan','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':43,'CompanyId':1,'FinNo':1,'OrderNo':1,'ItSec':'SEC16','SecHead':'','SubSec':'SUBFLD30','SsecDesc':'(a) Entertainment allowance','MaxVal':0,'Amount':1220,'ProofAmount':0,'listTdsDecl':null},{'Id':44,'CompanyId':1,'FinNo':1,'OrderNo':14,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD32','SsecDesc':'Investment in mutual fund','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':45,'CompanyId':1,'FinNo':1,'OrderNo':15,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD34','SsecDesc':'Others','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':46,'CompanyId':1,'FinNo':1,'OrderNo':5,'ItSec':'SECVIA','SecHead':'','SubSec':'SUBFLD35','SsecDesc':'80D (Self, Spouse and Kids)','MaxVal':15000,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':47,'CompanyId':1,'FinNo':1,'OrderNo':6,'ItSec':'SECVIA','SecHead':'','SubSec':'SUBFLD36','SsecDesc':'80D Mediclaim (Parents)','MaxVal':20000,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':48,'CompanyId':1,'FinNo':1,'OrderNo':2,'ItSec':'Add Income','SecHead':'','SubSec':'OTHINC1','SsecDesc':'Income from Housing Property - [Let Out]','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':49,'CompanyId':1,'FinNo':1,'OrderNo':3,'ItSec':'Less Income','SecHead':'','SubSec':'OTHINC2','SsecDesc':'Interest on Housing Property - [Let Out]','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':50,'CompanyId':1,'FinNo':1,'OrderNo':4,'ItSec':'Less Income','SecHead':'','SubSec':'OTHINC3','SsecDesc':'Interest on Housing Property - [Residential] (Max Upto 1,50,000)','MaxVal':150000,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':51,'CompanyId':1,'FinNo':1,'OrderNo':5,'ItSec':'Add Income','SecHead':'','SubSec':'OTHINC4','SsecDesc':'Income from Other Sources','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':52,'CompanyId':1,'FinNo':1,'OrderNo':6,'ItSec':'Add Income','SecHead':'','SubSec':'OTHINC6','SsecDesc':'Capital Gains','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':0,'CompanyId':1,'FinNo':1,'OrderNo':0,'ItSec':'Medical','SecHead':'Medical','SubSec':'','SsecDesc':'','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':0,'CompanyId':1,'FinNo':1,'OrderNo':0,'ItSec':'Medical','SecHead':'','SubSec':'Medical_Bill','SsecDesc':'Total Amount Of Medical Bill Claimed','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':0,'CompanyId':1,'FinNo':1,'OrderNo':0,'ItSec':'Medical','SecHead':'','SubSec':'NoOfChildren','SsecDesc':'No Of children Eligible for Education','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':0,'CompanyId':1,'FinNo':1,'OrderNo':0,'ItSec':'SECVIA','SecHead':'','SubSec':'PrimeMin_Fund','SsecDesc':'Prime Ministers Relief Fund','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':0,'CompanyId':1,'FinNo':1,'OrderNo':0,'ItSec':'SECVIA','SecHead':'','SubSec':'ChiefMin_Fund','SsecDesc':'Chief Ministers Relief Fund','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null}]";
    Context context;
    private RecyclerView recyclerView;
    RecyclerAdapterUpload adapter;
    ArrayList<Friend_Naminee> friendArrayList11,friendArrayList1;
    String getMyDetils_String;
    LinkedHashMap<String, String> lhmap = new LinkedHashMap<String, String>();

    Map<String, String> responseMap = new LinkedHashMap<String, String>();
    private static final String PREF_NAME_PRIFRNCR = "isregisterd";
    private static final String PREF_NAME_TOTAL = "total";

    String add, Years, NetPays,SchemaName, userId, CompanyId,Fieldvalue;
    String part1,part44,part66,part55;
    int part4,part6,part5;
    private com.github.clans.fab.FloatingActionButton fab;
    int Aprilsnon,Maysnon,Junesnon,Julysnon, Augustsnon, Septembersnon, Octobersnon, Novembersnon, Decembersnon, Januarysnon, Februarysnon, Marchsnon, snowDensity;
    int Aprils,Mays,Junes, Julys, Augusts, Septembers, Octobers, Novembers, Decembers, Januarys, Februarys, Marchs;

    int Aprilsnondis,Maysnondis,Junesnondis,Julysnondis,Augustsnondis,Septembersnondis, Octobersnondis,Novembersnondis,Decembersnondis,Januarysnondis,Februarysnondis,Marchsnondis;
    int Aprilsdis,Maysdis,Junesdis, Julysdis, Augustsdis, Septembersdis, Octobersdis, Novembersdis, Decembersdis, Januarysdis, Februarysdis, Marchsdis;

    EditText Aprilnon,Maynon,Junenon, Julynon, Augustnon, Septembernon, Octobernon, Novembernon, Decembernon, Januarynon, Februarynon, Marchnon;
    EditText April,May,June, July, August, September, October, November, December, January, February, March;
    String houserentpaids,Entertainmentallowances, LICpremiumpaids, Publicprovidentfunds, Approvedannuationfunds, Anysumpaidhousingdocuments, PFdeductedpreviousemployers , NSCs, ULIPs, Contributionpensionfunds, ContributionELSS, Tutionfeespaids, Repaymenthousingloans, Investmentmutualfunds, Otherss, Medicalinsurancepremiums, Repaymentloaneducations, DonationscertainfundsAggregates, SelfSpouseKidss, MediclaimParentss, IncomeHousingPropertys, IncomefromSourcess, CapitalGainss, InterestHousingPropertys, InterestHousingPropertyResidentials, TotalAmountMedicalBillClaimeds, NochildrenEligibleEducations, PrimeMinistersReliefFunds, ChiefMinistersReliefFunds;
    TextView subtile;
    String houserentpaidsprof,Entertainmentallowancesprof, LICpremiumpaidsprof, Publicprovidentfundsprof, Approvedannuationfundsprof, Anysumpaidhousingdocumentsprof, PFdeductedpreviousemployersprof , NSCsprof, ULIPsprof, Contributionpensionfundsprof, ContributionELSSprof, Tutionfeespaidsprof, Repaymenthousingloansprof, Investmentmutualfundsprof, Otherssprof, Medicalinsurancepremiumsprof, Repaymentloaneducationsprof, DonationscertainfundsAggregatesprof, SelfSpouseKidssprof, MediclaimParentssprof, IncomeHousingPropertysprof, IncomefromSourcessprof, CapitalGainssprof, InterestHousingPropertysprof, InterestHousingPropertyResidentialsprof, TotalAmountMedicalBillClaimedsprof, NochildrenEligibleEducationsprof, PrimeMinistersReliefFundsprof, ChiefMinistersReliefFundsprof;

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    String key ,value;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences setting = getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SchemaName = setting.getString("SchemaName", "SchemaName");
        userId = setting.getString("userId", "userId");
        CompanyId = setting.getString("CompanyId", "CompanyId");
        friendArrayList1 = new ArrayList<>();
        friendArrayList11 = new ArrayList<>();

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

        try {
            if (responnsedisplay.length() > 0) {
                JSONArray internships = new JSONArray(responnsedisplay);
                for (int i = 0; i < internships.length(); i++) {
                    Person person = new Person();
                    JSONObject jsonObject = internships.getJSONObject(i);
                    String  SsecDesc = jsonObject.getString("SsecDesc");
                    String  SubSec = jsonObject.getString("SubSec");
                    lhmap.put(SubSec, SsecDesc);

                    for (Map.Entry<String, String> entry : lhmap.entrySet()) {
                         value = entry.getValue();
                         key = entry.getKey();
                    }
                    if(SubSec.contains("RENTPAID")){
                        houserentpaids = jsonObject.getString("Amount");
                        houserentpaidsprof = jsonObject.getString("ProofAmount");

                        Log.d("houserentpaids",houserentpaids.toString());
                    }
                    if(SubSec.contains("SUBFLD14")){
                        LICpremiumpaids = jsonObject.getString("Amount");
                        LICpremiumpaidsprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("SUBFLD15")){
                        Publicprovidentfunds = jsonObject.getString("Amount");
                        Publicprovidentfundsprof = jsonObject.getString("ProofAmount");}
                    if( SubSec.contains("SUBFLD16")){
                        Approvedannuationfunds = jsonObject.getString("Amount");
                        Approvedannuationfundsprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("SUBFLD17")){
                        Anysumpaidhousingdocuments = jsonObject.getString("Amount");
                        Anysumpaidhousingdocumentsprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("SUBFLD18")){
                        PFdeductedpreviousemployers = jsonObject.getString("Amount");
                        PFdeductedpreviousemployersprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("SUBFLD19")){
                        NSCs = jsonObject.getString("Amount");
                        NSCsprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("SUBFLD20")){
                        ULIPs = jsonObject.getString("Amount");
                        ULIPsprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("SUBFLD21")){
                        Contributionpensionfunds = jsonObject.getString("Amount");
                        Contributionpensionfundsprof = jsonObject.getString("ProofAmount");}
                    if( SubSec.contains("SUBFLD22")){
                        ContributionELSS = jsonObject.getString("Amount");
                        ContributionELSSprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("SUBFLD23")){
                        Tutionfeespaids = jsonObject.getString("Amount");
                        Tutionfeespaids = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("SUBFLD25")){
                        Medicalinsurancepremiums = jsonObject.getString("Amount");
                        Medicalinsurancepremiumsprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("SUBFLD26")){
                        Repaymentloaneducations = jsonObject.getString("Amount");
                        Repaymentloaneducationsprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("SUBFLD27")){
                        DonationscertainfundsAggregates = jsonObject.getString("Amount");
                        DonationscertainfundsAggregatesprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("SUBFLD29")){
                        Repaymenthousingloans = jsonObject.getString("Amount");
                        Repaymenthousingloansprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("SUBFLD30")){
                        Entertainmentallowances = jsonObject.getString("Amount");
                        Entertainmentallowancesprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("SUBFLD32")){
                        Investmentmutualfunds = jsonObject.getString("Amount");
                        Investmentmutualfundsprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("SUBFLD34")){
                        Otherss = jsonObject.getString("Amount");
                        Otherssprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("SUBFLD35")){
                        SelfSpouseKidss = jsonObject.getString("Amount");
                        SelfSpouseKidssprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("SUBFLD36")){
                        MediclaimParentss = jsonObject.getString("Amount");
                        MediclaimParentssprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("OTHINC1")){
                        IncomeHousingPropertys = jsonObject.getString("Amount");
                        IncomeHousingPropertysprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("OTHINC2")){
                        InterestHousingPropertys = jsonObject.getString("Amount");
                        InterestHousingPropertysprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("OTHINC3")){
                        InterestHousingPropertyResidentials = jsonObject.getString("Amount");
                        InterestHousingPropertyResidentialsprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("OTHINC4")){
                        IncomefromSourcess = jsonObject.getString("Amount");
                        IncomefromSourcessprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("OTHINC6")){
                        CapitalGainss = jsonObject.getString("Amount");
                        CapitalGainss = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("Medical_Bill")){
                        TotalAmountMedicalBillClaimeds = jsonObject.getString("Amount");
                        TotalAmountMedicalBillClaimedsprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("NoOfChildren")){
                        NochildrenEligibleEducations = jsonObject.getString("Amount");
                        NochildrenEligibleEducationsprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("PrimeMin_Fund")){
                        PrimeMinistersReliefFunds = jsonObject.getString("Amount");
                        PrimeMinistersReliefFundsprof = jsonObject.getString("ProofAmount");}
                    if(SubSec.contains("ChiefMin_Fund")){
                        ChiefMinistersReliefFunds = jsonObject.getString("Amount");
                        ChiefMinistersReliefFundsprof = jsonObject.getString("ProofAmount");}
                }
            }
        } catch(JSONException e){
            Log.e("log_tag", "Error parsing data " + e.toString());}
        return inflater.inflate(R.layout.payinfo_layout, container, false);
    }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
            mViewPager.setAdapter(new SamplePagerAdapter());
            mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
            mSlidingTabLayout.setViewPager(mViewPager);
        }


        /*Tab class */
       class SamplePagerAdapter extends PagerAdapter implements View.OnClickListener
        {
            @Override
            public int getCount() {return items.length;}
            @Override
            public boolean isViewFromObject(View view, Object o) {return o == view;}
            @Override
            public CharSequence getPageTitle(int position) {return items[position];}

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                View view = null;

            TextView Entertainmentallowance, LICpremiumpaid, Publicprovidentfund, Approvedannuationfund, Anysumpaidhousingdocument, PFdeductedpreviousemployer , NSC, ULIP, Contributionpensionfund, ContributionELS, Tutionfeespaid, Repaymenthousingloan, Investmentmutualfund, Others, Medicalinsurancepremium, Repaymentloaneducation, DonationscertainfundsAggregate, SelfSpouseKids, MediclaimParents, IncomeHousingProperty, IncomefromSources, CapitalGains, InterestHousingProperty, InterestHousingPropertyResidential, TotalAmountMedicalBillClaimed, NochildrenEligibleEducation, PrimeMinistersReliefFund, ChiefMinistersReliefFund;
            TextView DEntertainmentallowance, DLICpremiumpaid, DPublicprovidentfund, DApprovedannuationfund, DAnysumpaidhousingdocument, DPFdeductedpreviousemployer , DNSC, DULIP, DContributionpensionfund, DContributionELS, DTutionfeespaid, DRepaymenthousingloan, DInvestmentmutualfund, DOthers, DMedicalinsurancepremium, DRepaymentloaneducation, DDonationscertainfundsAggregate, DSelfSpouseKids, DMediclaimParents, DIncomeHousingProperty, DIncomefromSources, DCapitalGains, DInterestHousingProperty, DInterestHousingPropertyResidential, DTotalAmountMedicalBillClaimed, DNochildrenEligibleEducation, DPrimeMinistersReliefFund, DChiefMinistersReliefFund;

            if (position == 0) {
                view = getActivity().getLayoutInflater().inflate(R.layout.expandable_child_1_layout, container, false);
                TextView clickhere=(TextView)view.findViewById(R.id.clickhere);
                houserentpaid=(TextView)view.findViewById(R.id.houserentpaid);
                linearLayout=(LinearLayout)view.findViewById(R.id.layout_main_traning);
                relativeLayout=(RelativeLayout)view.findViewById(R.id.layout_main_traning1);
               // houserentpaid.setText(Integer.toString(snowDensity));
                if(houserentpaids !=  null&&houserentpaids.length() > 0) {
                    houserentpaid.setText(houserentpaids);
                    Log.d("house resnt","houseresnt");
                }else {Log.d("else resnt","else resnt");}

                /******************************************************************/
                Medicalinsurancepremium=(TextView)view.findViewById(R.id.medicalinsurancepremium);
                Repaymentloaneducation=(TextView)view.findViewById(R.id.paymentofloan);
                DonationscertainfundsAggregate=(TextView)view.findViewById(R.id.donationcertain);
                SelfSpouseKids=(TextView)view.findViewById(R.id.selfspouse);
                MediclaimParents=(TextView)view.findViewById(R.id.mediclaim);
                IncomeHousingProperty=(TextView)view.findViewById(R.id.incomefromhousing);
                IncomefromSources=(TextView)view.findViewById(R.id.incomefrom);
                CapitalGains=(TextView)view.findViewById(R.id.capitalgains);
                InterestHousingProperty=(TextView)view.findViewById(R.id.interesthousingproperty);
                InterestHousingPropertyResidential=(TextView)view.findViewById(R.id.interesthousingres);
                TotalAmountMedicalBillClaimed=(TextView)view.findViewById(R.id.totalamount);
                NochildrenEligibleEducation=(TextView)view.findViewById(R.id.numberofc);
                PrimeMinistersReliefFund=(TextView)view.findViewById(R.id.primeministersr);
                ChiefMinistersReliefFund=(TextView)view.findViewById(R.id.chiefministersr);

                Entertainmentallowance=(TextView)view.findViewById(R.id.eallowance);
                LICpremiumpaid=(TextView)view.findViewById(R.id.licpremium);
                Publicprovidentfund=(TextView)view.findViewById(R.id.providentfund);
                Approvedannuationfund=(TextView)view.findViewById(R.id.approvedfund);
                Anysumpaidhousingdocument=(TextView)view.findViewById(R.id.hdocument);
                PFdeductedpreviousemployer=(TextView)view.findViewById(R.id.pfdeductedpreviousemployer);
                NSC=(TextView)view.findViewById(R.id.nsc);
                ULIP=(TextView)view.findViewById(R.id.ulip);
                Contributionpensionfund=(TextView)view.findViewById(R.id.contributionpensionfund);
                ContributionELS=(TextView)view.findViewById(R.id.contributionelss);
                Tutionfeespaid=(TextView)view.findViewById(R.id.tutionfeespaid);
                Repaymenthousingloan=(TextView)view.findViewById(R.id.repaymenthousingloan);
                Investmentmutualfund=(TextView)view.findViewById(R.id.investmentmutualfund);
                Others=(TextView)view.findViewById(R.id.others);
                /***********************************************************************/


                /*************************************************************************/
                Medicalinsurancepremium.setText(Medicalinsurancepremiums);
                Repaymentloaneducation.setText(Repaymentloaneducations);
                DonationscertainfundsAggregate.setText(DonationscertainfundsAggregates);
                SelfSpouseKids.setText(SelfSpouseKidss);
                MediclaimParents.setText(MediclaimParentss);
                IncomeHousingProperty.setText(IncomeHousingPropertys);
                IncomefromSources.setText(IncomefromSourcess);
                CapitalGains.setText(CapitalGainss);
                InterestHousingProperty.setText(InterestHousingPropertys);
                InterestHousingPropertyResidential.setText(InterestHousingPropertyResidentials);
                TotalAmountMedicalBillClaimed.setText(TotalAmountMedicalBillClaimeds);
                NochildrenEligibleEducation.setText(NochildrenEligibleEducations);
                PrimeMinistersReliefFund.setText(PrimeMinistersReliefFunds);
                ChiefMinistersReliefFund.setText(ChiefMinistersReliefFunds);

                Entertainmentallowance.setText(Entertainmentallowances);
                LICpremiumpaid.setText(LICpremiumpaids);
                Publicprovidentfund.setText(Publicprovidentfunds);
                Approvedannuationfund.setText(Approvedannuationfunds);
                Anysumpaidhousingdocument.setText(Anysumpaidhousingdocuments);
                PFdeductedpreviousemployer.setText(PFdeductedpreviousemployers);
                NSC.setText(NSCs);
                ULIP.setText(ULIPs);
                Contributionpensionfund.setText(Contributionpensionfunds);
                ContributionELS.setText(ContributionELSS);
                Tutionfeespaid.setText(Tutionfeespaids);
                Repaymenthousingloan.setText(Repaymenthousingloans);
                Investmentmutualfund.setText(Investmentmutualfunds);
                Others.setText(Otherss);
                /*******************************************************************/

                Button   save=(Button)view.findViewById(R.id.button4);
                clickhere.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        addnewdetails();
                    }
                });
                container.addView(view);


                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try
                        {
                            int nViews = linearLayout.getChildCount();
                            Log.d("linearLayout", "linearLayout"+nViews);
                            for (int i = 0; i <= nViews; i++)
                            {
                                View child = linearLayout.getChildAt(i);
                                Log.d("linearLayout child: ",child.getClass().toString());

                                if (child instanceof TextInputLayout)
                                {
                                    TextInputLayout txtInputLayout = (TextInputLayout) child;
                                    EditText edt = txtInputLayout.getEditText();
                                    edt.getTag();
                                    edt.getText();
                                    if(edt.getText() == null || "".equals(edt.getText())) {


                                        Toast.makeText(getActivity(), "Please Enter" + edt.getTag(), Toast.LENGTH_SHORT).show();

                                    }else {
                                        responseMap.put(edt.getTag() + "", edt.getText().toString());

                                    }
                                    //...
                                }else if (child instanceof Spinner)
                                {
                                    Spinner txtvw = (Spinner) child;
                                    txtvw.getTag();
                                    txtvw.getSelectedItem();
                                    responseMap.put(txtvw.getTag() + "", txtvw.getSelectedItem().toString());

                                    // Log.d("spnr, value: ", txtvw.getText().toString());
                  /*  Log.d("spnr key :", txtvw.getSelectedItem() + "");
                    Log.d("txtvw.getText(): ", txtvw.getSelectedItem().toString());*/
                                    Log.d("respmap: ",responseMap.toString());
                                }
                                Gson gson = new Gson();
                                Fieldvalue = gson.toJson(responseMap);
                                Log.d("Fieldvalue", Fieldvalue.toString());
                            }
                            Log.d("End of onClick", "bye");
                        } catch (Exception e){
                            Log.d("Excp ","onlick ",e);
                            e.printStackTrace();
                        }
                    }
                });
            }



            if(position==1) {
               /* view = getActivity().getLayoutInflater().inflate(R.layout.expandable_child_1_layout, container, false);
                TextView clickhere=(TextView)view.findViewById(R.id.clickhere);
                clickhere.setVisibility(View.GONE);
                container.addView(view);
*/

            }
            if(position==2) {


                view = getActivity().getLayoutInflater().inflate(R.layout.expandable_child_3_layout, container, false);
                TextView clickhere=(TextView)view.findViewById(R.id.clickhere);
                clickhere.setVisibility(View.GONE);
                ImageView upload=(ImageView)view.findViewById(R.id.upload);
                ImageView upload1=(ImageView)view.findViewById(R.id.upload1);

                upload.setOnClickListener(this);
                upload1.setOnClickListener(this);



                //upload1.setOnClickListener((View.OnClickListener) getActivity());
                /*upload.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        uploade();
                    }
                });
                upload1.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        uploade();
                    }
                });*/
                container.addView(view);
                houserentpaid=(TextView)view.findViewById(R.id.houserentpaid);
                declarationamount=(TextView)view.findViewById(R.id.declarationamount);
                TextView houserelative=(TextView)view.findViewById(R.id.houserelative);


                houserentpaid.setText(houserentpaidsprof);
                declarationamount.setText(houserentpaids);



                DMedicalinsurancepremium=(TextView)view.findViewById(R.id.damountmedicalinsurancepremium);
                DRepaymentloaneducation=(TextView)view.findViewById(R.id.damountpaymentofloan);
                DDonationscertainfundsAggregate=(TextView)view.findViewById(R.id.damountdonationcertain);
                DSelfSpouseKids=(TextView)view.findViewById(R.id.damountselfspouse);
                DMediclaimParents=(TextView)view.findViewById(R.id.damountmediclaim);
                DIncomeHousingProperty=(TextView)view.findViewById(R.id.damountincomefromhousing);
                DIncomefromSources=(TextView)view.findViewById(R.id.damountincomefrom);
                DCapitalGains=(TextView)view.findViewById(R.id.damountcapitalgains);
                DInterestHousingProperty=(TextView)view.findViewById(R.id.damountinteresthousingproperty);
                DInterestHousingPropertyResidential=(TextView)view.findViewById(R.id.damountinteresthousingres);
                DTotalAmountMedicalBillClaimed=(TextView)view.findViewById(R.id.dtotalamount);
                DNochildrenEligibleEducation=(TextView)view.findViewById(R.id.dnumberofc);
                DPrimeMinistersReliefFund=(TextView)view.findViewById(R.id.dprimeministersr);
                DChiefMinistersReliefFund=(TextView)view.findViewById(R.id.dchiefministersr);

                DEntertainmentallowance=(TextView)view.findViewById(R.id.damounteallowance);
                DLICpremiumpaid=(TextView)view.findViewById(R.id.damountlicpremium);
                DPublicprovidentfund=(TextView)view.findViewById(R.id.damountprovidentfund);
                DApprovedannuationfund=(TextView)view.findViewById(R.id.damountapprovedfund);
                DAnysumpaidhousingdocument=(TextView)view.findViewById(R.id.damounthdocument);
                DPFdeductedpreviousemployer=(TextView)view.findViewById(R.id.damountpfdeductedpreviousemployer);
                DNSC=(TextView)view.findViewById(R.id.damountnsc);
                DULIP=(TextView)view.findViewById(R.id.damountulip);
                DContributionpensionfund=(TextView)view.findViewById(R.id.damountcontributionpensionfund);
                DContributionELS=(TextView)view.findViewById(R.id.damountcontributionelss);
                DTutionfeespaid=(TextView)view.findViewById(R.id.damounttutionfeespaid);
                DRepaymenthousingloan=(TextView)view.findViewById(R.id.damountrepaymenthousingloan);
                DInvestmentmutualfund=(TextView)view.findViewById(R.id.damountinvestmentmutualfund);
                DOthers=(TextView)view.findViewById(R.id.damountothers);



                Medicalinsurancepremium=(TextView)view.findViewById(R.id.medicalinsurancepremium);
                Repaymentloaneducation=(TextView)view.findViewById(R.id.paymentofloan);
                DonationscertainfundsAggregate=(TextView)view.findViewById(R.id.donationcertain);
                SelfSpouseKids=(TextView)view.findViewById(R.id.selfspouse);
                MediclaimParents=(TextView)view.findViewById(R.id.mediclaim);
                IncomeHousingProperty=(TextView)view.findViewById(R.id.incomefromhousing);
                IncomefromSources=(TextView)view.findViewById(R.id.incomefrom);
                CapitalGains=(TextView)view.findViewById(R.id.capitalgains);
                InterestHousingProperty=(TextView)view.findViewById(R.id.interesthousingproperty);
                InterestHousingPropertyResidential=(TextView)view.findViewById(R.id.interesthousingres);
                TotalAmountMedicalBillClaimed=(TextView)view.findViewById(R.id.totalamount);
                NochildrenEligibleEducation=(TextView)view.findViewById(R.id.numberofc);
                PrimeMinistersReliefFund=(TextView)view.findViewById(R.id.primeministersr);
                ChiefMinistersReliefFund=(TextView)view.findViewById(R.id.chiefministersr);

                Entertainmentallowance=(TextView)view.findViewById(R.id.eallowance);
                LICpremiumpaid=(TextView)view.findViewById(R.id.licpremium);
                Publicprovidentfund=(TextView)view.findViewById(R.id.providentfund);
                Approvedannuationfund=(TextView)view.findViewById(R.id.approvedfund);
                Anysumpaidhousingdocument=(TextView)view.findViewById(R.id.hdocument);
                PFdeductedpreviousemployer=(TextView)view.findViewById(R.id.pfdeductedpreviousemployer);
                NSC=(TextView)view.findViewById(R.id.nsc);
                ULIP=(TextView)view.findViewById(R.id.ulip);
                Contributionpensionfund=(TextView)view.findViewById(R.id.contributionpensionfund);
                ContributionELS=(TextView)view.findViewById(R.id.contributionelss);
                Tutionfeespaid=(TextView)view.findViewById(R.id.tutionfeespaid);
                Repaymenthousingloan=(TextView)view.findViewById(R.id.repaymenthousingloan);
                Investmentmutualfund=(TextView)view.findViewById(R.id.investmentmutualfund);
                Others=(TextView)view.findViewById(R.id.others);

                Medicalinsurancepremium.setText(Medicalinsurancepremiumsprof);
                Repaymentloaneducation.setText(Repaymentloaneducationsprof);
                DonationscertainfundsAggregate.setText(DonationscertainfundsAggregatesprof);
                SelfSpouseKids.setText(SelfSpouseKidssprof);
                MediclaimParents.setText(MediclaimParentssprof);
                IncomeHousingProperty.setText(IncomeHousingPropertysprof);
                IncomefromSources.setText(IncomefromSourcessprof);
                CapitalGains.setText(CapitalGainssprof);
                InterestHousingProperty.setText(InterestHousingPropertysprof);
                InterestHousingPropertyResidential.setText(InterestHousingPropertyResidentialsprof);
                TotalAmountMedicalBillClaimed.setText(TotalAmountMedicalBillClaimedsprof);
                NochildrenEligibleEducation.setText(NochildrenEligibleEducationsprof);
                PrimeMinistersReliefFund.setText(PrimeMinistersReliefFundsprof);
                ChiefMinistersReliefFund.setText(ChiefMinistersReliefFundsprof);

                Entertainmentallowance.setText(Entertainmentallowancesprof);
                LICpremiumpaid.setText(LICpremiumpaidsprof);
                Publicprovidentfund.setText(Publicprovidentfundsprof);
                Approvedannuationfund.setText(Approvedannuationfundsprof);
                Anysumpaidhousingdocument.setText(Anysumpaidhousingdocumentsprof);
                PFdeductedpreviousemployer.setText(PFdeductedpreviousemployersprof);
                NSC.setText(NSCsprof);
                ULIP.setText(ULIPsprof);
                Contributionpensionfund.setText(Contributionpensionfundsprof);
                ContributionELS.setText(ContributionELSSprof);
                Tutionfeespaid.setText(Tutionfeespaidsprof);
                Repaymenthousingloan.setText(Repaymenthousingloansprof);
                Investmentmutualfund.setText(Investmentmutualfundsprof);
                Others.setText(Otherssprof);




                DMedicalinsurancepremium.setText(Medicalinsurancepremiums);
                DRepaymentloaneducation.setText(Repaymentloaneducationsprof);
                DDonationscertainfundsAggregate.setText(DonationscertainfundsAggregates);
                DSelfSpouseKids.setText(SelfSpouseKidss);
                DMediclaimParents.setText(MediclaimParentss);
                DIncomeHousingProperty.setText(IncomeHousingPropertys);
                DIncomefromSources.setText(IncomefromSourcess);
                DCapitalGains.setText(CapitalGainss);
                DInterestHousingProperty.setText(InterestHousingPropertys);
                DInterestHousingPropertyResidential.setText(InterestHousingPropertyResidentials);
                DTotalAmountMedicalBillClaimed.setText(TotalAmountMedicalBillClaimeds);
                DNochildrenEligibleEducation.setText(NochildrenEligibleEducations);
                DPrimeMinistersReliefFund.setText(PrimeMinistersReliefFunds);
                DChiefMinistersReliefFund.setText(ChiefMinistersReliefFunds);

                DEntertainmentallowance.setText(Entertainmentallowances);
                DLICpremiumpaid.setText(LICpremiumpaids);
                DPublicprovidentfund.setText(Publicprovidentfunds);
                DApprovedannuationfund.setText(Approvedannuationfunds);
                DAnysumpaidhousingdocument.setText(Anysumpaidhousingdocuments);
                DPFdeductedpreviousemployer.setText(PFdeductedpreviousemployers);
                DNSC.setText(NSCs);
                DULIP.setText(ULIPs);
                DContributionpensionfund.setText(Contributionpensionfunds);
                DContributionELS.setText(ContributionELSS);
                DTutionfeespaid.setText(Tutionfeespaids);
                DRepaymenthousingloan.setText(Repaymenthousingloans);
                DInvestmentmutualfund.setText(Investmentmutualfunds);
                DOthers.setText(Otherss);
            }
            return view;
        }





        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public void onClick(View v) {

            switch(v.getId())
            {
                case R.id.upload :

                    uploade();
                    subtile.setText("House Rent Paid");
                    subtile.setEnabled(false);
                    Log.d(" v.getId()", String.valueOf(v.getId()));
                    break;
                case R.id.upload1 :

                    uploade();
                    subtile.setText("Entertainment Allowance");
                    subtile.setEnabled(false);
                    Log.d(" v.getId()", String.valueOf(v.getId()));
                    break;
            }
        }


    }



    private void uploade() {
        RecyclerView recyclerView;

        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialog_layout = inflater.inflate(R.layout.uploadfile, null);
        final AlertDialog.Builder db = new AlertDialog.Builder(getActivity());
        db.setView(dialog_layout);

          recyclerView = (RecyclerView) dialog_layout.findViewById(R.id.recyle_view);
          subtile = (TextView)dialog_layout.findViewById(R.id.februarynonmetro);
         // add = (EditText)dialog_layout.findViewById(R.id.add);

        Button  choosefile = (Button)dialog_layout.findViewById(R.id.choosefile);
         uploadsubmit=(Button)dialog_layout.findViewById(R.id.uploadsubmit);

          recyclerView.setHasFixedSize(true);
          LinearLayoutManager layoutManager = new LinearLayoutManager(this.context);
          recyclerView.setLayoutManager(layoutManager);
          uploadsubmit.setOnClickListener(onAddingListener());

        adapter = new RecyclerAdapterUpload(getActivity(), friendArrayList11);
        recyclerView.setAdapter(adapter);

        adapter = new RecyclerAdapterUpload(getActivity(), friendArrayList1);
        recyclerView.setAdapter(adapter);

        choosefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  uploadFile();
                getImageFromGallery();
              }
        });


        db.setTitle("Proof Documents");
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

    private View.OnClickListener onAddingListener() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadsubmit.setOnClickListener(onConfirmListener(add));
            }
        };
    }

    private View.OnClickListener onConfirmListener(final String Name) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Friend_Naminee friend = new Friend_Naminee(Name.getText().toString().trim());
                Friend_Naminee friend = new Friend_Naminee(Name);

               // Log.d("hi", Name.getText().toString().trim());
                friendArrayList11.add(friend);
                friendArrayList1.add(friend);
                Log.d("friendArrayList", friendArrayList11.toString());
                adapter.notifyDataSetChanged();

               // clear();
            }

        };
    }
    public void foo(View v){

        if (v.getId() == R.id.upload){
        }
        else if (v.getId() == R.id.upload1){
        }

    }

    private void uploadFile()
    {
        String title = "Proof Documents";
      /*  CharSequence[] itemlist ={"Take a Photo",
                "Pick from Gallery",
                "Open from File"};*/

        CharSequence[] itemlist ={"Pick from Gallery", "Open from File"};

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // builder.setIcon(R.drawable.icon_app);
        builder.setTitle(title);
        builder.setItems(itemlist, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case 0:// Take Photo
                        // Do Take Photo task here
                        getImageFromGallery();
                        break;
                    case 1:// Choose Existing Photo
                        // Do Pick Photo task here
                        break;
                    case 2:// Choose Existing File
                        // Do Pick file here
                        break;
                    default:
                        break;
                }
            }
        });
        AlertDialog alert = builder.create();
        alert.setCancelable(true);
        alert.show();
    }

    private void getImageFromGallery() {

       // Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
      //  intent.setType("file*/*");
        /*intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,
                "Select Picture"), SELECT_PICTURE);*/


        Intent chooser = new Intent(Intent.ACTION_GET_CONTENT);
        Uri uri = Uri.parse(Environment.getDownloadCacheDirectory().getPath().toString());
        chooser.addCategory(Intent.CATEGORY_OPENABLE);
        chooser.setDataAndType(uri, "*/*");
        try {
            startActivityForResult(chooser, SELECT_PICTURE);
        }
        catch (android.content.ActivityNotFoundException ex)
        {
            //Toast.makeText(this, "Please install a File Manager.", Toast.LENGTH_SHORT).show();
        }
    }


   /* private void getAvaFromGallery() {
        Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }*/
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode,data);
        if(resultCode == getActivity().RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri uri = data.getData();
                 add = uri.getPath();
                Log.d("FilePath", add.toString());
            }
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
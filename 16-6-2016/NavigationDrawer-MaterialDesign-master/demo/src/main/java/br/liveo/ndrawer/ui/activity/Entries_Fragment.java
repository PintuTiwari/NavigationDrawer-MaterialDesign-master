package br.liveo.ndrawer.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
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
import com.microsoft.azure.storage.CloudStorageAccount;
import com.microsoft.azure.storage.blob.BlobContainerPermissions;
import com.microsoft.azure.storage.blob.BlobContainerPublicAccessType;
import com.microsoft.azure.storage.blob.CloudBlobClient;
import com.microsoft.azure.storage.blob.CloudBlobContainer;
import com.microsoft.azure.storage.blob.CloudBlockBlob;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.liveo.ndrawer.R;
import br.liveo.ndrawer.adapter.ConstantClass;
//http://codetheory.in/android-pick-select-image-from-gallery-with-intents/
//http://android-er.blogspot.in/2013/08/load-picture-with-intentactiongetcontent.html
//http://www.theappguruz.com/blog/android-take-photo-camera-gallery-code-sample

//http://www.tutorialsbuzz.com/2015/10/Android-Sliding-TabLayout-ListView-WhatsApp.html
//http://zacktutorials.blogspot.in/2014/07/android-downloading-and-viewing-pdf.html
//http://androiddevelopement.blogspot.in/2012/01/select-and-crop-image-on-android.html
//https://reecon.wordpress.com/2010/04/25/uploading-files-to-http-server-using-post-android-sdk/
//http://vba9simo.blogspot.in/2015/10/load-file-from-external-storage-using.html
//https://developer.xamarin.com/recipes/android/other_ux/pick_image/
//http://www.coderzheaven.com/2012/04/29/download-file-android-device-remote-server-custom-progressbar-showing-progress/
//http://www.worldbestlearningcenter.com/tips/Android-merge-pdf-files.htm
//http://stackoverflow.com/questions/30295996/azure-android-uploading-a-photo-to-blob-storage-error?rq=1
//https://github.com/soarcn/BottomSheet#style
//
public class Entries_Fragment extends Fragment  {
    TextView houserentpaid,declarationamount;
    LinearLayout linearLayout;
    RelativeLayout relativeLayout;
    Button uploadsubmit,submit;
    Button save;
    String[] items = {"TDS Entry", "Worksheet - TDS", "TDS Proof Entry"};
    String responnsedisplay="[{'Id':24,'CompanyId':1,'FinNo':1,'OrderNo':1,'ItSec':'RENTPAID','SecHead':'House Rent Paid','SubSec':'','SsecDesc':'','MaxVal':0,'Amount':'0','ProofAmount':0,'listTdsDecl':null},{'Id':25,'CompanyId':1,'FinNo':1,'OrderNo':2,'ItSec':'RENTPAID','SecHead':'','SubSec':'RENTPAID','SsecDesc':'House Rent Paid','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':26,'CompanyId':1,'FinNo':1,'OrderNo':2,'ItSec':'SEC16','SecHead':'Deductions :','SubSec':'','SsecDesc':'','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':27,'CompanyId':1,'FinNo':1,'OrderNo':3,'ItSec':'SEC80C','SecHead':'Section 80C','SubSec':'','SsecDesc':'','MaxVal':100000,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':28,'CompanyId':1,'FinNo':1,'OrderNo':6,'ItSec':'SECVIA','SecHead':'Aggregate of Deductible amounts under Chapter VI - A','SubSec':'','SsecDesc':'','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':29,'CompanyId':1,'FinNo':1,'OrderNo':2,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD14','SsecDesc':'LIC premium paid','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':30,'CompanyId':1,'FinNo':1,'OrderNo':3,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD15','SsecDesc':'Public provident fund','MaxVal':0,'Amount':-2,'ProofAmount':0,'listTdsDecl':null},{'Id':31,'CompanyId':1,'FinNo':1,'OrderNo':4,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD16','SsecDesc':'Approved super annuation fund','MaxVal':0,'Amount':-2,'ProofAmount':0,'listTdsDecl':null},{'Id':32,'CompanyId':1,'FinNo':1,'OrderNo':5,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD17','SsecDesc':'Any sum paid on housing document','MaxVal':0,'Amount':-2,'ProofAmount':0,'listTdsDecl':null},{'Id':33,'CompanyId':1,'FinNo':1,'OrderNo':6,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD18','SsecDesc':'PF deducted by previous employer','MaxVal':0,'Amount':-2,'ProofAmount':0,'listTdsDecl':null},{'Id':34,'CompanyId':1,'FinNo':1,'OrderNo':7,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD19','SsecDesc':'NSC','MaxVal':0,'Amount':-2,'ProofAmount':0,'listTdsDecl':null},{'Id':35,'CompanyId':1,'FinNo':1,'OrderNo':8,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD20','SsecDesc':'ULIP','MaxVal':0,'Amount':-2,'ProofAmount':0,'listTdsDecl':null},{'Id':36,'CompanyId':1,'FinNo':1,'OrderNo':9,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD21','SsecDesc':'Contribution to pension fund','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':37,'CompanyId':1,'FinNo':1,'OrderNo':10,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD22','SsecDesc':'Contribution to ELSS','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':38,'CompanyId':1,'FinNo':1,'OrderNo':11,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD23','SsecDesc':'Tution fees paid','MaxVal':12000,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':39,'CompanyId':1,'FinNo':1,'OrderNo':1,'ItSec':'SECVIA','SecHead':'','SubSec':'SUBFLD25','SsecDesc':'Medical insurance premium (Mediclaim)','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':40,'CompanyId':1,'FinNo':1,'OrderNo':2,'ItSec':'SECVIA','SecHead':'','SubSec':'SUBFLD26','SsecDesc':'Repayment of loan taken for higher education','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':41,'CompanyId':1,'FinNo':1,'OrderNo':3,'ItSec':'SECVIA','SecHead':'','SubSec':'SUBFLD27','SsecDesc':'Donations to certain funds, charitable institutions','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':42,'CompanyId':1,'FinNo':1,'OrderNo':13,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD29','SsecDesc':'Repayment of housing loan','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':43,'CompanyId':1,'FinNo':1,'OrderNo':1,'ItSec':'SEC16','SecHead':'','SubSec':'SUBFLD30','SsecDesc':'(a) Entertainment allowance','MaxVal':0,'Amount':1220,'ProofAmount':0,'listTdsDecl':null},{'Id':44,'CompanyId':1,'FinNo':1,'OrderNo':14,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD32','SsecDesc':'Investment in mutual fund','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':45,'CompanyId':1,'FinNo':1,'OrderNo':15,'ItSec':'SEC80C','SecHead':'','SubSec':'SUBFLD34','SsecDesc':'Others','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':46,'CompanyId':1,'FinNo':1,'OrderNo':5,'ItSec':'SECVIA','SecHead':'','SubSec':'SUBFLD35','SsecDesc':'80D (Self, Spouse and Kids)','MaxVal':15000,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':47,'CompanyId':1,'FinNo':1,'OrderNo':6,'ItSec':'SECVIA','SecHead':'','SubSec':'SUBFLD36','SsecDesc':'80D Mediclaim (Parents)','MaxVal':20000,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':48,'CompanyId':1,'FinNo':1,'OrderNo':2,'ItSec':'Add Income','SecHead':'','SubSec':'OTHINC1','SsecDesc':'Income from Housing Property - [Let Out]','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':49,'CompanyId':1,'FinNo':1,'OrderNo':3,'ItSec':'Less Income','SecHead':'','SubSec':'OTHINC2','SsecDesc':'Interest on Housing Property - [Let Out]','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':50,'CompanyId':1,'FinNo':1,'OrderNo':4,'ItSec':'Less Income','SecHead':'','SubSec':'OTHINC3','SsecDesc':'Interest on Housing Property - [Residential] (Max Upto 1,50,000)','MaxVal':150000,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':51,'CompanyId':1,'FinNo':1,'OrderNo':5,'ItSec':'Add Income','SecHead':'','SubSec':'OTHINC4','SsecDesc':'Income from Other Sources','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':52,'CompanyId':1,'FinNo':1,'OrderNo':6,'ItSec':'Add Income','SecHead':'','SubSec':'OTHINC6','SsecDesc':'Capital Gains','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':0,'CompanyId':1,'FinNo':1,'OrderNo':0,'ItSec':'Medical','SecHead':'Medical','SubSec':'','SsecDesc':'','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':0,'CompanyId':1,'FinNo':1,'OrderNo':0,'ItSec':'Medical','SecHead':'','SubSec':'Medical_Bill','SsecDesc':'Total Amount Of Medical Bill Claimed','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':0,'CompanyId':1,'FinNo':1,'OrderNo':0,'ItSec':'Medical','SecHead':'','SubSec':'NoOfChildren','SsecDesc':'No Of children Eligible for Education','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':0,'CompanyId':1,'FinNo':1,'OrderNo':0,'ItSec':'SECVIA','SecHead':'','SubSec':'PrimeMin_Fund','SsecDesc':'Prime Ministers Relief Fund','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null},{'Id':0,'CompanyId':1,'FinNo':1,'OrderNo':0,'ItSec':'SECVIA','SecHead':'','SubSec':'ChiefMin_Fund','SsecDesc':'Chief Ministers Relief Fund','MaxVal':0,'Amount':0,'ProofAmount':0,'listTdsDecl':null}]";
    Context context;
    public static final String storageConnectionString =
            "DefaultEndpointsProtocol=http;" +
                    "AccountName=pockethcm;" +
                    "AccountKey=zkbqSBuSsi7Ujvyuh52f+Az8MsVqM6UIZK48e5aqYmrUFtnkDEdkcMT2eiXQprh1RKOmHQkwVyJyi31zJUdSYg==";
    private RecyclerView recyclerView;
    RecyclerAdapterUpload adapter;
    ArrayList<Friend_Naminee> friendArrayList11;
    String getMyDetils_String;
    LinkedHashMap<String, String> lhmap = new LinkedHashMap<String, String>();
    EditText houserentpaidtds, EntertainmentallowanceAmt, LICpremiumpaidAmt, PublicprovidentfundAmt, ApprovedannuationfundAmt,
            AnysumpaidhousingdocumentAmt, PFdeductedpreviousemployerAmt , NSCAmt, ULIPAmt,
            ContributionpensionfundAmt, ContributionELSAmt, TutionfeespaidAmt,
            RepaymenthousingloanAmt, InvestmentmutualfundAmt, OthersAmt, MedicalinsurancepremiumAmt,
            RepaymentloaneducationAmt, DonationscertainfundsAggregateAmt, SelfSpouseKidsAmt,
            MediclaimParentsAmt, IncomeHousingPropertyAmt, IncomefromSourcesAmt,
            CapitalGainsAmt, InterestHousingPropertyAmt, InterestHousingPropertyResidentialAmt,
            TotalAmountMedicalBillClaimedAmt, NochildrenEligibleEducationAmt, PrimeMinistersReliefFundAmt,
            ChiefMinistersReliefFundAmt;
    Map<String, String> responseMap = new LinkedHashMap<String, String>();
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
    TextView subtile,Displaydata;
    String houserentpaidsprof,Entertainmentallowancesprof, LICpremiumpaidsprof, Publicprovidentfundsprof, Approvedannuationfundsprof, Anysumpaidhousingdocumentsprof, PFdeductedpreviousemployersprof , NSCsprof, ULIPsprof, Contributionpensionfundsprof, ContributionELSSprof, Tutionfeespaidsprof, Repaymenthousingloansprof, Investmentmutualfundsprof, Otherssprof, Medicalinsurancepremiumsprof, Repaymentloaneducationsprof, DonationscertainfundsAggregatesprof, SelfSpouseKidssprof, MediclaimParentssprof, IncomeHousingPropertysprof, IncomefromSourcessprof, CapitalGainssprof, InterestHousingPropertysprof, InterestHousingPropertyResidentialsprof, TotalAmountMedicalBillClaimedsprof, NochildrenEligibleEducationsprof, PrimeMinistersReliefFundsprof, ChiefMinistersReliefFundsprof;

    private SlidingTabLayout mSlidingTabLayout;
    private ViewPager mViewPager;
    String key ,value;


    String Name;
    static final  int SELECT_PICTURE =1;
    private static final String PREF_NAME_PRIFRNCR = "isregisterd";
    private static final String PREF_NAME_PRIFRNCR1 = "isregisterd1";
    Boolean CheckEditTextEmpty ;
    String SQLiteQuery ;
    SQLiteHelper SQLITEHELPER;
    SQLiteDatabase SQLITEDATABASE;
    Cursor cursor;

    ArrayList<String> ID_ArrayList = new ArrayList<String>();
    ArrayList<String> NAME_ArrayList = new ArrayList<String>();
    ArrayList<String> PHONE_NUMBER_ArrayList = new ArrayList<String>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        SharedPreferences setting = getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SchemaName = setting.getString("SchemaName", "SchemaName");
        userId = setting.getString("userId", "userId");
        CompanyId = setting.getString("CompanyId", "CompanyId");
        //     friendArrayList1 = new ArrayList<>();
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
        SQLITEHELPER = new SQLiteHelper(getActivity());

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


                TextView DEntertainmentallowancePamt, DLICpremiumpaidPamt, DPublicprovidentfundPamt,
                        DApprovedannuationfundPamt, DAnysumpaidhousingdocumentPamt, DPFdeductedpreviousemployerPamt ,
                        DNSCPamt, DULIPPamt, DContributionpensionfundPamt, DContributionELSPamt,
                        DTutionfeespaidPamt, DRepaymenthousingloanPamt, DInvestmentmutualfundPamt,
                        DOthersPamt, DMedicalinsurancepremiumPamt, DRepaymentloaneducationPamt,
                        DDonationscertainfundsAggregatePamt, DSelfSpouseKidsPamt, DMediclaimParentsPamt,
                        DIncomeHousingPropertyPamt,
                        DIncomefromSourcesPamt, DCapitalGainsPamt, DInterestHousingPropertyPamt,
                        DInterestHousingPropertyResidentialPamt, DTotalAmountMedicalBillClaimedPamt,
                        DNochildrenEligibleEducationPamt, DPrimeMinistersReliefFundEditText, DChiefMinistersReliefFundPamt;

                view = getActivity().getLayoutInflater().inflate(R.layout.expandable_child_3_layout, container, false);
                TextView clickhere=(TextView)view.findViewById(R.id.clickhere);
                clickhere.setVisibility(View.GONE);

                submit=(Button)view.findViewById(R.id.submit);
                container.addView(view);
                houserentpaidtds=(EditText)view.findViewById(R.id.houserentpaid);
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



                MedicalinsurancepremiumAmt= (EditText) view.findViewById(R.id.medicalinsurancepremium);
                RepaymentloaneducationAmt= (EditText) view.findViewById(R.id.paymentofloan);
                DonationscertainfundsAggregateAmt= (EditText) view.findViewById(R.id.donationcertain);
                SelfSpouseKidsAmt= (EditText) view.findViewById(R.id.selfspouse);
                MediclaimParentsAmt= (EditText) view.findViewById(R.id.mediclaim);
                IncomeHousingPropertyAmt=(EditText)view.findViewById(R.id.incomefromhousing);
                IncomefromSourcesAmt=(EditText)view.findViewById(R.id.incomefrom);
                CapitalGainsAmt=(EditText)view.findViewById(R.id.capitalgains);
                InterestHousingPropertyAmt=(EditText)view.findViewById(R.id.interesthousingproperty);
                InterestHousingPropertyResidentialAmt=(EditText)view.findViewById(R.id.interesthousingres);
                TotalAmountMedicalBillClaimedAmt=(EditText)view.findViewById(R.id.totalamount);
                NochildrenEligibleEducationAmt=(EditText)view.findViewById(R.id.numberofc);
                PrimeMinistersReliefFundAmt=(EditText)view.findViewById(R.id.primeministersr);
                ChiefMinistersReliefFundAmt=(EditText)view.findViewById(R.id.chiefministersr);

                EntertainmentallowanceAmt=(EditText)view.findViewById(R.id.eallowance);
                LICpremiumpaidAmt=(EditText)view.findViewById(R.id.licpremium);
                PublicprovidentfundAmt= (EditText) view.findViewById(R.id.providentfund);
                ApprovedannuationfundAmt= (EditText) view.findViewById(R.id.approvedfund);
                AnysumpaidhousingdocumentAmt= (EditText) view.findViewById(R.id.hdocument);
                PFdeductedpreviousemployerAmt=(EditText)view.findViewById(R.id.pfdeductedpreviousemployer);
                NSCAmt=(EditText)view.findViewById(R.id.nsc);
                ULIPAmt=(EditText)view.findViewById(R.id.ulip);
                ContributionpensionfundAmt=(EditText)view.findViewById(R.id.contributionpensionfund);
                ContributionELSAmt=(EditText)view.findViewById(R.id.contributionelss);
                TutionfeespaidAmt=(EditText)view.findViewById(R.id.tutionfeespaid);
                RepaymenthousingloanAmt=(EditText)view.findViewById(R.id.repaymenthousingloan);
                InvestmentmutualfundAmt=(EditText)view.findViewById(R.id.investmentmutualfund);
                OthersAmt=(EditText)view.findViewById(R.id.others);

                MedicalinsurancepremiumAmt.setText(Medicalinsurancepremiumsprof);
                RepaymentloaneducationAmt.setText(Repaymentloaneducationsprof);
                DonationscertainfundsAggregateAmt.setText(DonationscertainfundsAggregatesprof);
                SelfSpouseKidsAmt.setText(SelfSpouseKidssprof);
                MediclaimParentsAmt.setText(MediclaimParentssprof);
                IncomeHousingPropertyAmt.setText(IncomeHousingPropertysprof);
                IncomefromSourcesAmt.setText(IncomefromSourcessprof);
                CapitalGainsAmt.setText(CapitalGainssprof);
                InterestHousingPropertyAmt.setText(InterestHousingPropertysprof);
                InterestHousingPropertyResidentialAmt.setText(InterestHousingPropertyResidentialsprof);
                TotalAmountMedicalBillClaimedAmt.setText(TotalAmountMedicalBillClaimedsprof);
                NochildrenEligibleEducationAmt.setText(NochildrenEligibleEducationsprof);
                PrimeMinistersReliefFundAmt.setText(PrimeMinistersReliefFundsprof);
                ChiefMinistersReliefFundAmt.setText(ChiefMinistersReliefFundsprof);

                EntertainmentallowanceAmt.setText(Entertainmentallowancesprof);
                LICpremiumpaidAmt.setText(LICpremiumpaidsprof);
                PublicprovidentfundAmt.setText(Publicprovidentfundsprof);
                ApprovedannuationfundAmt.setText(Approvedannuationfundsprof);
                AnysumpaidhousingdocumentAmt.setText(Anysumpaidhousingdocumentsprof);
                PFdeductedpreviousemployerAmt.setText(PFdeductedpreviousemployersprof);
                NSCAmt.setText(NSCsprof);
                ULIPAmt.setText(ULIPsprof);
                ContributionpensionfundAmt.setText(Contributionpensionfundsprof);
                ContributionELSAmt.setText(ContributionELSSprof);
                TutionfeespaidAmt.setText(Tutionfeespaidsprof);
                RepaymenthousingloanAmt.setText(Repaymenthousingloansprof);
                InvestmentmutualfundAmt.setText(Investmentmutualfundsprof);
                OthersAmt.setText(Otherssprof);

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

                ImageView upload=(ImageView)view.findViewById(R.id.upload);
                ImageView upload1=(ImageView)view.findViewById(R.id.upload1);
                ImageView upload2=(ImageView)view.findViewById(R.id.upload2);
                ImageView upload3=(ImageView)view.findViewById(R.id.upload3);
                ImageView upload4=(ImageView)view.findViewById(R.id.upload4);
                ImageView upload5=(ImageView)view.findViewById(R.id.upload5);
                ImageView upload6=(ImageView)view.findViewById(R.id.upload6);
                ImageView upload7=(ImageView)view.findViewById(R.id.upload7);
                ImageView upload8=(ImageView)view.findViewById(R.id.upload8);
                ImageView upload9=(ImageView)view.findViewById(R.id.upload9);
                ImageView upload10=(ImageView)view.findViewById(R.id.upload10);
                ImageView upload11=(ImageView)view.findViewById(R.id.upload11);
                ImageView upload12=(ImageView)view.findViewById(R.id.upload12);
                ImageView upload13=(ImageView)view.findViewById(R.id.upload13);
                ImageView upload14=(ImageView)view.findViewById(R.id.upload14);
                ImageView upload15=(ImageView)view.findViewById(R.id.upload15);
                ImageView upload16=(ImageView)view.findViewById(R.id.upload16);
                ImageView upload17=(ImageView)view.findViewById(R.id.upload17);
                ImageView upload18=(ImageView)view.findViewById(R.id.upload18);
                ImageView upload19=(ImageView)view.findViewById(R.id.upload19);
                ImageView upload20=(ImageView)view.findViewById(R.id.upload20);
                ImageView upload21=(ImageView)view.findViewById(R.id.upload21);
                ImageView upload22=(ImageView)view.findViewById(R.id.upload22);
                ImageView upload23=(ImageView)view.findViewById(R.id.upload23);
                ImageView upload24=(ImageView)view.findViewById(R.id.upload24);
                ImageView upload25=(ImageView)view.findViewById(R.id.upload25);
                ImageView upload26=(ImageView)view.findViewById(R.id.upload26);
                ImageView upload27=(ImageView)view.findViewById(R.id.upload27);
                ImageView upload28=(ImageView)view.findViewById(R.id.upload28);

                upload.setOnClickListener(this);
                upload1.setOnClickListener(this);
                upload2.setOnClickListener(this);
                upload3.setOnClickListener(this);
                upload4.setOnClickListener(this);
                upload5.setOnClickListener(this);
                upload6.setOnClickListener(this);
                upload7.setOnClickListener(this);
                upload8.setOnClickListener(this);
                upload9.setOnClickListener(this);
                upload10.setOnClickListener(this);
                upload11.setOnClickListener(this);
                upload12.setOnClickListener(this);
                upload13.setOnClickListener(this);
                upload14.setOnClickListener(this);
                upload15.setOnClickListener(this);
                upload16.setOnClickListener(this);
                upload17.setOnClickListener(this);
                upload18.setOnClickListener(this);
                upload19.setOnClickListener(this);
                upload20.setOnClickListener(this);
                upload21.setOnClickListener(this);
                upload21.setOnClickListener(this);
                upload22.setOnClickListener(this);
                upload23.setOnClickListener(this);
                upload24.setOnClickListener(this);
                upload25.setOnClickListener(this);
                upload26.setOnClickListener(this);
                upload27.setOnClickListener(this);
                upload28.setOnClickListener(this);
                submit.setOnClickListener(this);
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
                case R.id.submit :


                    String SUBFLD30=EntertainmentallowanceAmt.getText().toString();
                    String SUBFLD14 =LICpremiumpaidAmt.getText().toString();
                    String SUBFLD15= PublicprovidentfundAmt.getText().toString();
                    String SUBFLD16=ApprovedannuationfundAmt.getText().toString();
                    String SUBFLD17=AnysumpaidhousingdocumentAmt.getText().toString();
                    String SUBFLD18=PFdeductedpreviousemployerAmt.getText().toString();
                    String SUBFLD19= NSCAmt.getText().toString();
                    String SUBFLD20= ULIPAmt.getText().toString();
                    String SUBFLD21=ContributionpensionfundAmt.getText().toString();
                    String SUBFLD22= ContributionELSAmt.getText().toString();
                    String SUBFLD23= TutionfeespaidAmt.getText().toString();
                    String SUBFLD29=RepaymenthousingloanAmt.getText().toString();
                    String SUBFLD32= InvestmentmutualfundAmt.getText().toString();
                    String SUBFLD34=OthersAmt.getText().toString();

                    String RENTPAID=  houserentpaidtds.getText().toString();
                    String SUBFLD25= MedicalinsurancepremiumAmt.getText().toString();
                    String SUBFLD26= RepaymentloaneducationAmt.getText().toString();
                    String SUBFLD27=DonationscertainfundsAggregateAmt.getText().toString();
                    String SUBFLD35= SelfSpouseKidsAmt.getText().toString();
                    String SUBFLD36= MediclaimParentsAmt.getText().toString();
                    String OTHINC1=IncomeHousingPropertyAmt.getText().toString();
                    String OTHINC4=IncomefromSourcesAmt.getText().toString();
                    String OTHINC6=CapitalGainsAmt.getText().toString();
                    String OTHINC3=InterestHousingPropertyAmt.getText().toString();
                    String OTHINC2= InterestHousingPropertyResidentialAmt.getText().toString();
                    String Medical_Bill=TotalAmountMedicalBillClaimedAmt.getText().toString();
                    String NoOfChildren=NochildrenEligibleEducationAmt.getText().toString();
                    String PrimeMin_Fund= PrimeMinistersReliefFundAmt.getText().toString();
                    String ChiefMin_Fund=ChiefMinistersReliefFundAmt.getText().toString();

                    responseMap.put("SUBFLD34",SUBFLD34);
                    responseMap.put("SUBFLD25",SUBFLD25);
                    responseMap.put("SUBFLD26",SUBFLD26);
                    responseMap.put("SUBFLD27",SUBFLD27);
                    responseMap.put("SUBFLD35",SUBFLD35);
                    responseMap.put("SUBFLD36",SUBFLD36);
                    responseMap.put("OTHINC1",OTHINC1);
                    responseMap.put("OTHINC4",OTHINC4);
                    responseMap.put("OTHINC6",OTHINC6);
                    responseMap.put("OTHINC3",OTHINC3);
                    responseMap.put("OTHINC2",OTHINC2);
                    responseMap.put("Medical_Bill",Medical_Bill);
                    responseMap.put("NoOfChildren",NoOfChildren);
                    responseMap.put("PrimeMin_Fund",PrimeMin_Fund);
                    responseMap.put("ChiefMin_Fund",ChiefMin_Fund);

                    responseMap.put("SUBFLD34",SUBFLD34);
                    responseMap.put("SUBFLD32",SUBFLD32);
                    responseMap.put("SUBFLD29",SUBFLD29);
                    responseMap.put("SUBFLD22",SUBFLD22);
                    responseMap.put("SUBFLD21",SUBFLD21);
                    responseMap.put("SUBFLD19",SUBFLD19);
                    responseMap.put("SUBFLD18",SUBFLD18);
                    responseMap.put("SUBFLD20",SUBFLD20);
                    responseMap.put("SUBFLD23",SUBFLD23);
                    responseMap.put("SUBFLD17",SUBFLD17);
                    responseMap.put("SUBFLD16",SUBFLD16);
                    responseMap.put("SUBFLD15",SUBFLD15);
                    responseMap.put("SUBFLD14",SUBFLD14);
                    responseMap.put("SUBFLD30",SUBFLD30);
                    responseMap.put("RENTPAID",RENTPAID);


                    if(responseMap!=null)
                    {
                        new NetCheck().execute();
                    }



                    break;
                case R.id.upload :
                    ArrayList<Friend_Naminee> friendArrayList1 = new ArrayList<>();
                    uploade1("House Rent Paid", friendArrayList1);

                    DBCreate();
                    break;
                case R.id.upload1 :
                   /* new BottomSheet.Builder(getActivity()).title("Entertainment Allowance").sheet(R.menu.bottom).listener(new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            switch (which) {
                                case R.id.folder:
                                    getImageFromGallery("Entertainment Allowance");
                                    break;}}}).show();*/


                    ArrayList<Friend_Naminee> friendArrayList2 = new ArrayList<>();
                    uploade1("Entertainment Allowance", friendArrayList2);
                    DBCreate();
                    break;
                case R.id.upload2 :
                    ArrayList<Friend_Naminee> friendArrayList3 = new ArrayList<>();
                    uploade1("LIC premium paid", friendArrayList3);
                    DBCreate();
                    break;
                case R.id.upload3 :
                    ArrayList<Friend_Naminee> friendArrayList4 = new ArrayList<>();
                    uploade1("Public provident fund", friendArrayList4);
                    DBCreate();
                    break;
                case R.id.upload4 :
                    ArrayList<Friend_Naminee> friendArrayList5 = new ArrayList<>();
                    uploade1("Approved super annuation fund", friendArrayList5);
                    DBCreate();
                    break;
                case R.id.upload5 :
                    ArrayList<Friend_Naminee> friendArrayList6 = new ArrayList<>();
                    uploade1("Any sum paid on housing document", friendArrayList6);
                    DBCreate();
                    break;
                case R.id.upload6 :
                    ArrayList<Friend_Naminee> friendArrayList7 = new ArrayList<>();
                    uploade1("PF deducted by previous employer", friendArrayList7);
                    DBCreate();
                    break;
                case R.id.upload7 :
                    ArrayList<Friend_Naminee> friendArrayList8 = new ArrayList<>();
                    uploade1("NSC", friendArrayList8);
                    DBCreate();
                    break;

                case R.id.upload8 :
                    ArrayList<Friend_Naminee> friendArrayList9 = new ArrayList<>();
                    uploade1("ULIP", friendArrayList9);
                    DBCreate();
                    break;
                case R.id.upload9 :
                    ArrayList<Friend_Naminee> friendArrayList10 = new ArrayList<>();
                    uploade1("Contribution to pension fund", friendArrayList10);
                    DBCreate();

                    break;
                case R.id.upload10 :
                    ArrayList<Friend_Naminee> friendArrayList11 = new ArrayList<>();
                    uploade1("Contribution to ELSS", friendArrayList11);
                    DBCreate();
                    break;
                case R.id.upload11 :
                    ArrayList<Friend_Naminee> friendArrayList12 = new ArrayList<>();
                    uploade1("Tution fees paid", friendArrayList12);
                    DBCreate();
                    break;
                case R.id.upload12 :
                    ArrayList<Friend_Naminee> friendArrayList13 = new ArrayList<>();
                    uploade1("Repayment of housing loan", friendArrayList13);
                    DBCreate();
                    break;
                case R.id.upload13 :
                    ArrayList<Friend_Naminee> friendArrayList14 = new ArrayList<>();
                    uploade1("Investment in mutual fund", friendArrayList14);
                    DBCreate();
                    break;
                case R.id.upload14 :
                    ArrayList<Friend_Naminee> friendArrayList15 = new ArrayList<>();
                    uploade1("Others", friendArrayList15);
                    DBCreate();
                    break;
                case R.id.upload15 :
                    ArrayList<Friend_Naminee> friendArrayList16 = new ArrayList<>();
                    uploade1("Medical insurance premium (Mediclaim)", friendArrayList16);
                    DBCreate();
                    break;
                case R.id.upload16 :
                    ArrayList<Friend_Naminee> friendArrayList17 = new ArrayList<>();
                    uploade1("Repayment of loan taken for higher education", friendArrayList17);
                    DBCreate();
                    break;
                case R.id.upload17 :
                    ArrayList<Friend_Naminee> friendArrayList18 = new ArrayList<>();
                    uploade1("Donations to certain funds, charitable institutions", friendArrayList18);
                    DBCreate();
                    break;
                case R.id.upload18 :
                    ArrayList<Friend_Naminee> friendArrayList19 = new ArrayList<>();
                    uploade1("Self, Spouse and Kids)", friendArrayList19);
                    DBCreate();
                    break;
                case R.id.upload19 :
                    ArrayList<Friend_Naminee> friendArrayList20 = new ArrayList<>();
                    uploade1("80D Mediclaim (Parents))", friendArrayList20);
                    DBCreate();
                    break;
                case R.id.upload20 :
                    ArrayList<Friend_Naminee> friendArrayList21 = new ArrayList<>();
                    uploade1("Income from Housing Property - [Let Out]", friendArrayList21);
                    DBCreate();
                    break;
                case R.id.upload21 :
                    ArrayList<Friend_Naminee> friendArrayList22 = new ArrayList<>();
                    uploade1("Income from Other Sources", friendArrayList22);
                    DBCreate();
                    break;
                case R.id.upload22 :
                    ArrayList<Friend_Naminee> friendArrayList23 = new ArrayList<>();
                    uploade1("Capital Gains", friendArrayList23);
                    DBCreate();
                    break;
                case R.id.upload23 :
                    ArrayList<Friend_Naminee> friendArrayList24 = new ArrayList<>();
                    uploade1("Interest on Housing Property - [Let Out]", friendArrayList24);
                    DBCreate();
                    break;
                case R.id.upload24 :
                    ArrayList<Friend_Naminee> friendArrayList25 = new ArrayList<>();
                    uploade1("Interest on Housing Property - \n[Residential] (Max Upto 1,50,000)", friendArrayList25);
                    DBCreate();
                    break;
                case R.id.upload25 :
                    ArrayList<Friend_Naminee> friendArrayList26 = new ArrayList<>();
                    uploade1("Total Amount Of Medical Bill Claimed", friendArrayList26);
                    DBCreate();
                    break;
                case R.id.upload26 :
                    ArrayList<Friend_Naminee> friendArrayList27 = new ArrayList<>();
                    uploade1("No Of children Eligible for Education", friendArrayList27);
                    DBCreate();
                    break;
                case R.id.upload27 :
                    ArrayList<Friend_Naminee> friendArrayList28 = new ArrayList<>();
                    uploade1("Prime Ministers Relief Fund", friendArrayList28);
                    DBCreate();
                    break;
                case R.id.upload28 :
                    ArrayList<Friend_Naminee> friendArrayList29 = new ArrayList<>();
                    uploade1("Chief Ministers Relief Fund", friendArrayList29);
                    DBCreate();
                    break;
            }
        }
    }
    private class NetCheck extends AsyncTask<String,String,Boolean>
    {
        private ProgressDialog nDialog;

        @Override
        protected void onPreExecute(){
            super.onPreExecute();
            nDialog = new ProgressDialog(getActivity());
            nDialog.setMessage("Loading..");
            nDialog.setTitle("Checking Network");
            nDialog.setIndeterminate(false);
            nDialog.setCancelable(true);
            nDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... args){


/**
 * Gets current device state and checks for working internet connection by trying Google.
 **/
            ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo netInfo = cm.getActiveNetworkInfo();
            if (netInfo != null && netInfo.isConnected()) {
                try {
                    URL url = new URL("http://www.google.com");
                    HttpURLConnection urlc = (HttpURLConnection) url.openConnection();
                    urlc.setConnectTimeout(3000);
                    urlc.connect();
                    if (urlc.getResponseCode() == 200) {
                        return true;
                    }
                } catch (MalformedURLException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            return false;

        }
        @Override
        protected void onPostExecute(Boolean th){

            if(th == true){
                nDialog.dismiss();
                new ProcessRegister().execute();
            }
            else{
                nDialog.dismiss();
            }
        }
    }

    private class ProcessRegister extends AsyncTask<String, String, JSONObject> {

        /**
         * Defining Process dialog
         **/
        private ProgressDialog pDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            pDialog = new ProgressDialog(getActivity());
            pDialog.setTitle("Contacting Servers");
            pDialog.setMessage("Wait ...");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        @Override
        protected JSONObject doInBackground(String... args) {

            ConstantClass userFunction = new ConstantClass();

            JSONObject json = userFunction.saveadditionaldetails(SchemaName, CompanyId, Fieldvalue);
            Log.d("Fieldvalue", Fieldvalue.toString());

            return json;


        }
        @Override
        protected void onPostExecute(JSONObject json) {
            /**
             * Checks for success message.
             **/
        /*    try {
                if (json.getString(KEY_SUCCESS) != null) {
                    registerErrorMsg.setText("");
                    String res = json.getString(KEY_SUCCESS);

                    String red = json.getString(KEY_ERROR);

                    if(Integer.parseInt(res) == 1){
                        pDialog.setTitle("Getting Data");
                        pDialog.setMessage("Loading Info");

                        registerErrorMsg.setText("Successfully Registered");


                        DatabaseHandler db = new DatabaseHandler(getApplicationContext());
                        JSONObject json_user = json.getJSONObject("user");

                        *//**
             * Removes all the previous data in the SQlite database
             **//*

                        UserFunctions logout = new UserFunctions();
                        logout.logoutUser(getApplicationContext());
                        db.addUser(json_user.getString(KEY_FIRSTNAME),json_user.getString(KEY_LASTNAME),json_user.getString(KEY_EMAIL),json_user.getString(KEY_USERNAME),json_user.getString(KEY_UID),json_user.getString(KEY_CREATED_AT));
                        *//**
             * Stores registered data in SQlite Database
             * Launch Registered screen
             **//*

                        Intent registered = new Intent(getApplicationContext(), Registered.class);

                        *//**
             * Close all views before launching Registered screen
             **//*
                        registered.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        pDialog.dismiss();
                        startActivity(registered);


                        finish();
                    }

                    else if (Integer.parseInt(red) ==2){
                        pDialog.dismiss();
                    }
                    else if (Integer.parseInt(red) ==3){
                        pDialog.dismiss();
                    }

                }


                else{
                    pDialog.dismiss();



                }

            } catch (JSONException e) {
                e.printStackTrace();


            }*/
            pDialog.dismiss();
        }}



    public void DBCreate(){

        SQLITEDATABASE = getActivity().openOrCreateDatabase("DemoDataBase", Context.MODE_PRIVATE, null);

        SQLITEDATABASE.execSQL("CREATE TABLE IF NOT EXISTS upload(id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, name VARCHAR, phone_number VARCHAR);");
    }

    public void SubmitData2SQLiteDB(String EntriesKey,String add){


        CheckEditTextIsEmptyOrNot(EntriesKey, add);
        Log.d("SubmitData2SQLiteDB", EntriesKey);
        Log.d("SubmitData2SQLiteDB add", add);


        if(CheckEditTextEmpty == true)
        {

            SQLiteQuery = "INSERT INTO upload (name,phone_number) VALUES('"+EntriesKey+"', '"+add+"');";

            SQLITEDATABASE.execSQL(SQLiteQuery);

            // Toast.makeText(MainActivity.this,"Data Submit Successfully", Toast.LENGTH_LONG).show();

            //  ClearEditTextAfterDoneTask();

        }
        else {

            //Toast.makeText(Entries_Fragment.this,"Please Fill All the Fields", Toast.LENGTH_LONG).show();
        }
    }

    public void CheckEditTextIsEmptyOrNot(String Name,String PhoneNumber ){

        if(TextUtils.isEmpty(Name) || TextUtils.isEmpty(PhoneNumber)){

            CheckEditTextEmpty = false ;

        }
        else {
            CheckEditTextEmpty = true ;
        }
    }

    private List ShowSQLiteDBdata(String key) {
        Log.d("ShowSQLiteDBdata, key ", key);
        Log.d("AftebrowsGetKEY", key);


        SQLITEDATABASE = SQLITEHELPER.getWritableDatabase();

        //   cursor = SQLITEDATABASE.rawQuery("SELECT * FROM upload", null);
        //        cursor = SQLITEDATABASE.rawQuery("SELECT * FROM upload", null);
        cursor = SQLITEDATABASE.rawQuery("SELECT * FROM " + "upload" + " where name = '" +key + "'" , null);



        ID_ArrayList.clear();
        NAME_ArrayList.clear();
        PHONE_NUMBER_ArrayList.clear();
        //SUBJECT_ArrayList.clear();

        if (cursor.moveToFirst()) {
            do {
                ID_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_ID)));

                NAME_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Name)));
                Log.d("NAME_ArrayList", NAME_ArrayList.toString());
                PHONE_NUMBER_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_PhoneNumber)));

                //   SUBJECT_ArrayList.add(cursor.getString(cursor.getColumnIndex(SQLiteHelper.KEY_Subject)));

            } while (cursor.moveToNext());
        }

  /*      ListAdapter = new SQLiteListAdapter(ListViewActivity.this,

                ID_ArrayList,
                NAME_ArrayList,
                PHONE_NUMBER_ArrayList,
                SUBJECT_ArrayList

        );

        LISTVIEW.setAdapter(ListAdapter);*/

        cursor.close();
        return PHONE_NUMBER_ArrayList;
    }

    private void uploade1(String buttonKey,  ArrayList<Friend_Naminee> friendArrayList1) {

        Log.d("uploade1Ent, buttonkey ", buttonKey);
        //
     /* SharedPreferences    prefsNagSetting =get();
        String buttonkey = prefsNagSetting.getString("buttonKey", "");
        String add = prefsNagSetting.getString("add","");
        Log.d("uploade1Ent, buttonkey ", buttonkey);
        Log.d("uploade1Ent, add ", add);

        Friend_Naminee friend = new Friend_Naminee(add);
        boolean found=false;
        for (Friend_Naminee friend1   : friendArrayList1) {
            if(friend1.getName().equalsIgnoreCase(add)){
                found=true;
                break;
            }
        }
        if(!found) {
             friendArrayList1.add(friend);
            Log.d("uploade1Ent friendArrayList", String.valueOf(friendArrayList1.size()));
        }*/


        List<String> pathLISt = ShowSQLiteDBdata(buttonKey);
        for(String add :pathLISt) {
            if(add != null && add != ""){
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
                    Log.d("uploade1Ent friendArr", String.valueOf(friendArrayList1.size()));
                }
            }
        }


        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View dialog_layout = inflater.inflate(R.layout.uploadfile, null);
        final AlertDialog.Builder db = new AlertDialog.Builder(getActivity());

        db.setView(dialog_layout);
        recyclerView = (RecyclerView) dialog_layout.findViewById(R.id.recyle_view);
        subtile = (TextView)dialog_layout.findViewById(R.id.februarynonmetro);
        Displaydata = (TextView)dialog_layout.findViewById(R.id.displayuploaddata);
        Button  choosefile = (Button)dialog_layout.findViewById(R.id.choosefile);
        uploadsubmit=(Button)dialog_layout.findViewById(R.id.uploadsubmit);
        subtile.setText(buttonKey);//each textve selected value displayed in label
        choosefile.setTag(buttonKey);
      //  Log.d("butoonKey", buttonKey);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this.context);
        recyclerView.setLayoutManager(layoutManager);
       // friendArrayList1 = new ArrayList<>();
        choosefile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String buttonKey = String.valueOf(v.getTag());
                Log.d("MT before galary", String.valueOf(v.getTag()));
                //  uploadFile();
                uploadsubmit.setEnabled(true);
                getImageFromGallery(buttonKey);
            }
        });
        uploadsubmit.setOnClickListener(onAddingListener1(friendArrayList1));

        adapter = new RecyclerAdapterUpload(getActivity(), friendArrayList1);
        recyclerView.setAdapter(adapter);

        SharedPreferences setting = getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR1, 0);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("EntriesKey", buttonKey).apply();
        Log.d("EntriesKeyFirsttimeput",buttonKey);
        editor.commit();

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
                //  subtile.setText(buttonKey);
            }
        };
    }

  private View.OnClickListener onConfirmListener1(final String Name, final ArrayList<Friend_Naminee> friendArrayList1) {
      return new View.OnClickListener() {
          @Override
          public void onClick(View v) {



              SharedPreferences    prefsgetEntries=getEntries();
              String key = prefsgetEntries.getString("EntriesKey", "");
              Log.d("AftebrowsGetKEY",key);
              List<String> pathLISt = ShowSQLiteDBdata(key);
              for(String add :pathLISt) {
                  if(add != null && add != ""){
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
                          Log.d("uploade1Ent friendArr", String.valueOf(friendArrayList1.size()));
                         new MyTask().execute();
                          SharedPreferences    prefsNagSetting = getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, 0);
                          String add1 = prefsNagSetting.getString("add", "");
                          Log.d("addpath", add1);

                      }
                  }
              }
              adapter.notifyDataSetChanged();
          }

      };
  }

    private class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            //Here starts the code for Azure Storage Blob
            try {
                // Retrieve storage account from connection-string
                CloudStorageAccount storageAccount = CloudStorageAccount.parse(storageConnectionString);

                // Create the blob client
                CloudBlobClient blobClient = storageAccount.createCloudBlobClient();

                // Get a reference to a container
                // The container name must be lower case
                String schemaname1=SchemaName.toString().toLowerCase();

                Log.d("schemaname1",schemaname1);
             //   str = CaseFormat.LOWER_HYPHEN.to(CaseFormat.UPPER_CAMEL, "student-name");
              //  CloudBlobContainer container = blobClient.getContainerReference("proofdocuments"+"-"+schemaname1);
                CloudBlobContainer container = blobClient.getContainerReference("proofdocuments" + "-" + schemaname1);

                //      Field1.setText(s.toString().toLowerCase());


                // Create the container if it does not exist
                container.createIfNotExists();

                // Create a permissions object
                BlobContainerPermissions containerPermissions = new BlobContainerPermissions();

                // Include public access in the permissions object
                containerPermissions.setPublicAccess(BlobContainerPublicAccessType.CONTAINER);

                // Set the permissions on the container
                container.uploadPermissions(containerPermissions);
               SharedPreferences    prefsNagSetting = getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, 0);
                String add = prefsNagSetting.getString("add", "");
                Log.d("addpath",add);
                CloudBlockBlob blob = container.getBlockBlobReference("image_1055026155.jpg");
                File source = new File(add);
                blob.upload(new FileInputStream(source.getAbsolutePath()), source.length());
                Log.d("Testing", "Done");
            } catch (Exception e) {
                Log.e("SARATH", e.toString());
            }
            return null;
        }


    }

    public SharedPreferences get()
    {
        SharedPreferences    prefsNagSetting = getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, 0);
        return prefsNagSetting;
    }


    public SharedPreferences getEntries()
    {
        SharedPreferences    prefsNagSetting = getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR1, 0);
        return prefsNagSetting;
    }
    private void getImageFromGallery(String buttonKey) {

        Log.d("MT galaey buttonKey: ", buttonKey);

        SharedPreferences setting = getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR1, 0);
        SharedPreferences.Editor editor = setting.edit();
        editor.putString("EntriesKey", buttonKey).apply();
        Log.d("MT SharedPreferences", buttonKey);
        editor.commit();

        Intent chooser = new Intent(Intent.ACTION_GET_CONTENT);
      //  Uri uri = Uri.parse(Environment.getDownloadCacheDirectory().getPath().toString());
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
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
             //   Uri uri = data.getData();
            // String   add = uri.getPath();
                String selectedImagePath;
                Uri selectedImageUri = data.getData();

                //MEDIA GALLERY
                selectedImagePath = ImageFilePath.getPath(getActivity(), selectedImageUri);
                Log.i("Image File Path", ""+selectedImagePath);
                Displaydata.setText("File Path : \n"+selectedImagePath);
              //  Displaydata.setText(add);

                SharedPreferences    prefsNagSetting = getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR1, 0);
                String EntriesKey = prefsNagSetting.getString("EntriesKey","");
               // Toast.makeText(this, "You have chosen the book: " + " " + EntriesKey, Toast.LENGTH_LONG).show();
                Log.d("MT finly getString", EntriesKey);

                SharedPreferences setting = getActivity().getSharedPreferences(PREF_NAME_PRIFRNCR, 0);
                SharedPreferences.Editor editor = setting.edit();
                editor.putString("butoonKey", EntriesKey).apply();
                editor.putString("add", selectedImagePath).apply();
                SubmitData2SQLiteDB(EntriesKey, selectedImagePath);

                Log.d("SubmitData2SQLiteDB", EntriesKey);
              //  Log.d("SubmitData2SQLiteDB", add);


             //   Displaydata.setText(add);
            }
        }
    }





   /* public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }*/

    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
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



/*

    private void uploadFile()
    {
        String title = "Proof Documents";
      */
/*  CharSequence[] itemlist ={"Take a Photo",
                "Pick from Gallery",
                "Open from File"};*//*


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
    }*/
  /* private void getAvaFromGallery() {
        Intent intent = new Intent();
        intent.setType("image*//*");
        intent.setAction(Intent.ACTION_PICK);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), SELECT_PICTURE);
    }*/

   /* public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA};
        Cursor cursor = getActivity().managedQuery(uri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
     public void foo(View v){

        if (v.getId() == R.id.upload){
        }
        else if (v.getId() == R.id.upload1){
        }

    }
*/
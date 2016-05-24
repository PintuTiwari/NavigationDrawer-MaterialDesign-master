package br.liveo.ndrawer.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.DrawerLayout.DrawerListener;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LayoutAnimationController;
import android.view.animation.ScaleAnimation;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.liveo.model.HelpLiveo;
import br.liveo.ndrawer.R;


public class NavigationActivity extends FragmentActivity implements NavigationView.OnNavigationItemSelectedListener{
	Map<String, Module> modulesMap = new LinkedHashMap<String, Module>();
	private HelpLiveo mHelpLiveo;
	public MenuDetails menuDetails=null;
/*	https://cloud.pockethcm.com/api/v2.0/SelfserviceMenu?schemaName=Greyt56af30a136&companyId=1&employeeId=1&userrole=Admin

	Admin = 1,
	Manager =2,
	User=3,
	Finance=4*/
	private Toolbar mToolbar;


//	String getMyDetils_String="[{'Id':1,'CompanyId':1,'Menu':'Pay Info','SubMenu':'Salary Info','URL':'/Admin/SalarySummary','Order':0,'isHeading':false,'Module':1,'Role':1},{'Id':5,'CompanyId':1,'Menu':'Entries','SubMenu':'TDS Entry','URL':'/Employee/TDSEntry','Order':0,'isHeading':false,'Module':1,'Role':1},{'Id':8,'CompanyId':1,'Menu':'HR Info','SubMenu':'Contact Info','URL':'/Admin/ContactInfo','Order':0,'isHeading':false,'Module':1,'Role':1},{'Id':14,'CompanyId':1,'Menu':'Administrator','SubMenu':'Profile Settings','URL':'/Admin/ProfileSetting','Order':0,'isHeading':false,'Module':1,'Role':1},{'Id':23,'CompanyId':1,'Menu':'Reports','SubMenu':'Age','URL':'/Reports/AgeReport','Order':0,'isHeading':false,'Module':1,'Role':1},{'Id':43,'CompanyId':1,'Menu':'Transaction','SubMenu':'Leave Request','URL':'/Leave/LeaveRequest','Order':0,'isHeading':false,'Module':2,'Role':1},{'Id':49,'CompanyId':1,'Menu':'Master','SubMenu':'Leader Import','URL':'/Leave/ManagerImport','Order':0,'isHeading':false,'Module':2,'Role':1},{'Id':65,'CompanyId':1,'Menu':'My Report','SubMenu':'Leave Balance','URL':'/LeaveReport/LeaveBalance','Order':0,'isHeading':false,'Module':2,'Role':1},{'Id':74,'CompanyId':1,'Menu':'User Report','SubMenu':'Leave Balance','URL':'/LeaveReport/HRLeaveBalance','Order':0,'isHeading':false,'Module':2,'Role':1},{'Id':80,'CompanyId':1,'Menu':'Employee Report','SubMenu':'User Manager','URL':'/LeaveReport/UserManager','Order':0,'isHeading':false,'Module':2,'Role':1},{'Id':384,'CompanyId':1,'Menu':'Policies','SubMenu':'HR Policy','URL':'https://pockethcm.blob.core.windows.net/policies-greyt56af30a136/Greyt56af30a136-1-Screenshot (1).png','Order':0,'isHeading':false,'Module':1,'Role':1},{'Id':476,'CompanyId':1,'Menu':'General Info','SubMenu':'test','URL':'https://pockethcm.blob.core.windows.net/generalinfo-greyt56af30a136/Greyt56af30a136-1-IMG-20151110-WA0068.jpg','Order':0,'isHeading':false,'Module':1,'Role':1}]";
	private static final String PREFS_NAME = "MyPrefsFile";
	private DrawerLayout mDrawerLayout;
	ImageView home;
	Fragment fragment = null;
	TextView appname;
	ExpandableListView expListView;
	HashMap<String, List<String>> listDataChild;
	ExpandableListAdapter listAdapter;
	List<String> listDataHeader;
	private int mSelectedId;
	private static final String PREF_NAME_PRIFRNCR = "isregisterd";
	private static final String PREF_NAME_PRIFRNCR1 = "menue";

	Typeface tf;
	String getMyDetils_String;
	ImageView lblListHeaderimage;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String fontPath = "fonts/Roboto-Regular.ttf";
		setContentView(R.layout.navigation);
		home = (ImageView)findViewById(R.id.home);
	    home.setOnClickListener(homeOnclickListener);
		//appname = (TextView)findViewById(R.id.appname);

		SharedPreferences setting1 =this.getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
	    String	SchemaName = setting1.getString("SchemaName", "SchemaName");
		String	userId = setting1.getString("userId", "userId");
		String	CompanyId = setting1.getString("CompanyId", "CompanyId");

          Log.d("SchemaNamelogin", SchemaName.toString());
            Log.d("userIdlogin", userId.toString());
           Log.d("CompanyIdlogin", CompanyId.toString());

		tf = Typeface.createFromAsset(this.getAssets(), fontPath);
		//appname.setTypeface(tf);
		SharedPreferences setting = this.getSharedPreferences(PREF_NAME_PRIFRNCR1, Context.MODE_PRIVATE);

		Gson gson = new Gson();
		getMyDetils_String  = setting.getString("json", "json");
		Log.d("EmployementHi_response ", getMyDetils_String.toString());
		setUpDrawer();

		try {
			if(getMyDetils_String.length() > 0) {

				//friendArrayList.clear();
				JSONArray internships = new JSONArray(getMyDetils_String);
				JSONObject leagueObject = internships.getJSONObject(0);
				//Loop the Array
				for (int i = 0; i < leagueObject.length(); i++) {
					HashMap<String, String> map = new HashMap<String, String>();
					JSONObject jsonObject = internships.getJSONObject(i);
					Log.d("Employement in page", jsonObject.toString());
                   /* Log.d("EmployementHi_response ", e.toString());
                    map.put("id", String.valueOf("id"));*/


				/*	Person person = new Person(Name.getText().toString().trim(), Address.getText().toString().trim(),
							Relation.getText().toString().trim(),
							Dateof_Birth.getText().toString().trim(),
							Age.getText().toString().trim());*/
					/*if (!jsonObject.isNull("Name")) {
						person.Company_Name = jsonObject.getString("Name");
						Log.d("Company",person.Company_Name.toString());
					}
					if (!jsonObject.isNull("Address")) {
						person.Position = jsonObject.getString("Address");
						Log.d("Company",person.Position.toString());
					}
					if (!jsonObject.isNull("Relation")) {
						person.From_Date = jsonObject.getString("Relation");
						Log.d("Company",person.From_Date.toString());
					}
					if (!jsonObject.isNull("DOB")) {
						//  person.To_Date = jsonObject.getString("DOB");
						String date = jsonObject.getString("DOB");
						String[] parts = date.split("T");
						String part1 = parts[0]; // 004
						String part2 = parts[1]; // 034556
						System.out.println(part1);
						person.To_Date=part1;

						Log.d("Company",person.To_Date.toString());
					}
					if (!jsonObject.isNull("Age")) {
						person.Training_Name = jsonObject.getString("Age");
						Log.d("Company",person.Training_Name.toString());
					}

					friendArrayList.add(i, person);
				}*/
					// recyclerView.notifyDataSetChanged();
				}
			}
		} catch(JSONException e) {
			Log.e("log_tag", "Error parsing data " + e.toString());
		}



	}



	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu, menu);
		menu.findItem(R.id.logout).setVisible(true);

		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.logout) {
/*			Uri uri = Uri.parse("https://github.com/Lesilva/BetterSpinner");
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);*/
			logout();
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	public  void logout(){
		SharedPreferences sharedpreferences = this.getSharedPreferences(NavigationActivity.PREFS_NAME,Context.MODE_PRIVATE);
		SharedPreferences.Editor editor = sharedpreferences.edit();
		editor.clear();
		editor.commit();
		Intent i = new Intent(this, LogOut.class);

		// Closing all the Activities
		i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

		// Add new Flag to start new Activity
		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

		// Staring Login Activity
		startActivity(i);
			finish();
	}

	public void close(View view){
		finish();
	}


	private void setupLayoutAnimation() {
		AnimationSet set = new AnimationSet(true);
		Animation animation = new AlphaAnimation(0.0f, 1.0f);
		animation.setDuration(50);
		set.addAnimation(animation);

		animation = new ScaleAnimation(1.0f, 1.0f, 0.0f, 1.0f, 0.5f, 1.0f);
		animation.setDuration(50);
		set.addAnimation(animation);

		LayoutAnimationController controller = new LayoutAnimationController(set, 0.75f);
		expListView.setLayoutAnimationListener(null);
		expListView.setLayoutAnimation(controller);
	}

	private void setupLayoutAnimationClose(final int groupPosition) {
		AnimationSet set = new AnimationSet(true);
		Animation animation = new AlphaAnimation(1.0f, 0.0f);
		animation.setDuration(50);
		animation.setFillAfter(true);
		animation.setFillEnabled(true);
		set.addAnimation(animation);
		animation = new ScaleAnimation(1.0f, 1.0f, 1.0f, 0.0f, 0.5f, 0.0f);
		animation.setDuration(50);
		animation.setFillAfter(true);
		animation.setFillEnabled(true);
		set.addAnimation(animation);
		set.setFillAfter(true);
		set.setFillEnabled(true);
		LayoutAnimationController controller = new LayoutAnimationController(set, 0.75f);
		controller.setOrder(LayoutAnimationController.ORDER_REVERSE);
		expListView.setLayoutAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {

			}

			@Override
			public void onAnimationEnd(Animation animation) {
				expListView.collapseGroup(groupPosition);
			}

			@Override
			public void onAnimationRepeat(Animation animation) {

			}
		});
		expListView.setLayoutAnimation(controller);
	}



	/**
	 *
	 * Get the names and icons references to build the drawer menu...
	 */
	private void setUpDrawer() {
		 TextView userName;
		 TextView userEmail;
		 ImageView userPhoto;
		 ImageView userBackground;
		final RelativeLayout relativeLayout;
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerLayout.setScrimColor(getResources().getColor(android.R.color.transparent));
		mDrawerLayout.setDrawerListener(mDrawerListener);
		expListView = (ExpandableListView) findViewById(R.id.lvExp);
		View header = getLayoutInflater().inflate(R.layout.header, null);
		// User Information
		userName=(TextView)header.findViewById(R.id.userName);
		userEmail=(TextView)header.findViewById(R.id.userEmail);
		userPhoto=(ImageView)header.findViewById(R.id.userPhoto);
		userBackground=(ImageView)header.findViewById(R.id.userBackground);
		relativeLayout=(RelativeLayout)header.findViewById(R.id.profilelayout);


		userName.setText("Rudson Lima");
		userName.setTypeface(tf);
		userEmail.setText("rudsonlive@gmail.com");
		userEmail.setTypeface(tf);
		userPhoto.setImageResource(R.drawable.ic_rudsonlive);
		//userBackground.setImageResource(R.drawable.bgimage);
		//Establecemos header
		expListView.addHeaderView(header);
		prepareListData();
		listAdapter = new ExpandableListAdapter(this, listDataHeader, listDataChild);
		// setting list adapter
		expListView.setAdapter(listAdapter);

		fragment = new Profile_Activity();
		getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
		mDrawerLayout.closeDrawer(expListView);
		//ExpandableListView - collapse groups automatically
		expListView.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {

			@Override
			public void onGroupExpand(int groupPosition) {
				ExpandableListAdapter customExpandAdapter = (ExpandableListAdapter)expListView.getExpandableListAdapter();



				if (customExpandAdapter == null) {return;}
				for (int i = 0; i < customExpandAdapter.getGroupCount(); i++) {
					if (i != groupPosition) {
						expListView.collapseGroup(i);

					}

				}
			}
		});



		expListView.setOnChildClickListener(new OnChildClickListener() {
			@Override
			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
				//Map<String,MenuDetails> menuDetailsMap = module.getMenuDetailsMap();


				Log.d("childPosition: ", new Integer(childPosition).toString());
				Log.d("groupPosition: ", new Integer(groupPosition).toString());
				Log.d("v.getId()", " " + v.getId());

				/*Module module = (new ArrayList<Module>(modulesMap.values())).get(groupPosition);
				menuDetails = (new ArrayList<MenuDetails>(module.getMenuDetailsMap().values())).get(childPosition);
				itemSelection(groupPosition);
				Log.d("menuDetails : ", menuDetails.getDisplyActivity());
				String className = "br.liveo.ndrawer.ui.activity.";// really passed in from config
				Class c = null;
				try {
					//c = Class.forName(className);
					c = Class.forName(className + menuDetails.getDisplyActivity());

					fragment = (Fragment) c.newInstance();
					Log.d("fragment: ", fragment.toString());

				} catch (Exception e) {
					e.printStackTrace();
				}
*/
				switch (groupPosition) {

					case 0:
						switch (childPosition) {
							case 0:
								fragment = new PayInfo_Activity();
								break;
						    case 1:
								fragment = new Entries_Fragment();

								/*Intent startMainPage = new Intent(NavigationActivity.this, Entries_Activity.class);
								startMainPage.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
								startActivity(startMainPage);*/

								/*Fragment newFragment = new Entries_Activity();
								// consider using Java coding conventions (upper first char class names!!!)
								FragmentTransaction transaction = getFragmentManager().beginTransaction();

								// Replace whatever is in the fragment_container view with this fragment,
								// and add the transaction to the back stack
								transaction.replace(R.id.content_frame, newFragment);
								transaction.addToBackStack(null);

								// Commit the transaction
								transaction.commit();*/
							/*case 2:
								fragment = new EarthFragment();
								break;
							case 4:
								fragment = new AboutPagerFragment();
								break;
							default:
								break;*/
						}
						break;

					case 2:
						fragment = new LogOut();
						break;

					default:
						break;

					/*case 1:
						switch (childPosition) {
							case 0:
								fragment = new VenusFragment();
								break;
							case 1:
								fragment = new VenusFragment();
								break;
							case 2:
								fragment = new EarthFragment();
								break;
							default:
								break;
						}
						break;

					case 2:
						switch (childPosition) {
							case 0:
								fragment = new VenusFragment();
								break;
							case 1:
								fragment = new VenusFragment();
								break;
							case 2:
								fragment = new EarthFragment();
								break;
							default:
								break;
						}
						break;

					default:
						break;*/
				}
				getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
				mDrawerLayout.closeDrawer(expListView);

				return false;
			}
		});
		relativeLayout.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(NavigationActivity.this, "clicked", Toast.LENGTH_SHORT).show();
				mDrawerLayout.closeDrawer(GravityCompat.START);

				fragment = new Profile_Activity();
				if (fragment != null){
					getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();
				}
			}
		});
	}

	View.OnClickListener homeOnclickListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			if(mDrawerLayout.isDrawerOpen(expListView)){
				mDrawerLayout.closeDrawer(expListView);
			}else{
				mDrawerLayout.openDrawer(expListView);
			}
		}
	};


	private OnItemClickListener mDrawerItemClickedListener = new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {

			/*switch(position){
				case 0:
					fragment = new Profile_Activity();
					break;
				case 1:
					fragment = new VenusFragment();
					break;
				case 2:
					fragment = new EarthFragment();
					break;
				default:
					return;
			}*/

			getSupportFragmentManager().beginTransaction().replace(R.id.content_frame, fragment).commit();

			mDrawerLayout.closeDrawer(expListView);
		}
	};

	// Catch the events related to the drawer to arrange views according to this
	// action if necessary...
	private DrawerListener mDrawerListener = new DrawerListener() {

		@Override
		public void onDrawerStateChanged(int status) {

		}

		@Override
		public void onDrawerSlide(View view, float slideArg) {


		}

		@Override
		public void onDrawerOpened(View view) {
		}

		@Override
		public void onDrawerClosed(View view) {
		}
	};

	private void prepareListData() {
	//	Map<String, Module> modulesMap = new HashMap<String, Module>();


		try {

			if (getMyDetils_String.length() > 0) {
				Log.d("getMyDetils_String: ", getMyDetils_String.toString());
				//friendArrayList.clear();
				JSONArray internships = new JSONArray(getMyDetils_String);
				//	JSONArray leagueObject = internships.getJSONArray(0);
				Log.d("leagueObject.length():", new Integer(internships.length()).toString());

				//Log.d("internships.length():", new Integer(internships.length()).toString());
				//Loop the Array
				for (int i = 0; i < internships.length(); i++) {

					JSONObject jsonObject = internships.getJSONObject(i);
					String moduleId = jsonObject.getString("Module");
					Log.d("jsonObject:: ", jsonObject.toString());


					Module module = (Module) modulesMap.get(moduleId);// modulekey from jason
//					Log.d("moduleId start:", moduleId);
					if(module == null){
						module = new Module();
						Map<String,MenuDetails> menuDetailsMap = module.getMenuDetailsMap();
						module.setModuleId(moduleId);
						try {
							MenuDetails menuDetails = new MenuDetails();
							module.getMenuDetailsMap().put(jsonObject.getString("Id"), menuDetails);
							module.setMenuDetailsMap(menuDetailsMap);
							menuDetails.setId(jsonObject.getString("Id"));
						//	menuDetails.setDisplyActivity(jsonObject.getString("DisplyActivity"));
							menuDetails.setCompanyId(jsonObject.getString("CompanyId"));
							menuDetails.setIsHeading(jsonObject.getString("isHeading"));
							menuDetails.setMenu(jsonObject.getString("Menu"));
							menuDetails.setSubMenu(jsonObject.getString("SubMenu"));
							menuDetails.setModule(jsonObject.getString("Module"));
							menuDetails.setOrder(jsonObject.getString("Order"));
							menuDetails.setRole(jsonObject.getString("Role"));
							menuDetails.setURL(jsonObject.getString("URL"));
							Log.d("P1 menuDetails:: ", menuDetails.toString());
						}catch (Exception e){
							e.printStackTrace();
						}
						}else{
							Map<String,MenuDetails> menuDetailsMap = module.getMenuDetailsMap();
							module.setModuleId(moduleId);
							try{
							MenuDetails menuDetails = new MenuDetails();
							module.getMenuDetailsMap().put(jsonObject.getString("Id"), menuDetails);
							menuDetails.setId(jsonObject.getString("Id"));
						//	menuDetails.setDisplyActivity(jsonObject.getString("DisplyActivity"));
							menuDetails.setCompanyId(jsonObject.getString("CompanyId"));
							menuDetails.setIsHeading(jsonObject.getString("isHeading"));
							menuDetails.setMenu(jsonObject.getString("Menu"));
							menuDetails.setSubMenu(jsonObject.getString("SubMenu"));
							menuDetails.setModule(jsonObject.getString("Module"));
							menuDetails.setOrder(jsonObject.getString("Order"));
							menuDetails.setRole(jsonObject.getString("Role"));
							menuDetails.setURL(jsonObject.getString("URL"));
							Log.d("P2 menuDetails:: ", menuDetails.toString());
							}catch (Exception e){
								e.printStackTrace();
							}
						}
//					Log.d("moduleId End:", moduleId);
						modulesMap.put(moduleId,module);
					}
				}
			}catch (Exception e){
				e.printStackTrace();
			}
			Log.d("My test1: ",modulesMap.toString());




		listDataHeader = new ArrayList<String>();
		listDataChild = new HashMap<String, List<String>>();
		// Adding child data
		listDataHeader.add("Personal Info");
		listDataHeader.add("Leave Tracking");
		//listDataHeader.add("Logout");


		/*	listDataHeader = new ArrayList<String>();
			listDataChild = new HashMap<String, List<String>>();
			// Adding child data
			listDataHeader.add("Batsman");
			listDataHeader.add("Bowler");
			listDataHeader.add("All rounder");
			listDataHeader.add("Wicket keeper");*/

			int listRow = 0;
			for (Map.Entry<String, Module> entry : modulesMap.entrySet()) {
				String moduleId = entry.getKey();
				Module module = entry.getValue();

				Map<String,MenuDetails> menuDetailsMap = module.getMenuDetailsMap();
				Log.d("menuDetailsMap.size: ",new Integer(menuDetailsMap.size()).toString());
				List<String> menuList = new ArrayList<String>();
				for (Map.Entry<String, MenuDetails> entry1 : menuDetailsMap.entrySet()) {
					String Id = entry1.getKey();
					MenuDetails menuDetails = entry1.getValue();
					String menu = menuDetails.getMenu();
					menuList.add(menu);
				}
				listDataChild.put(listDataHeader.get(listRow), menuList);
				listRow++;
			}







	/*	// Adding child data
		List<String> batsman = new ArrayList<String>();
		batsman.add("V. Kohli");
		batsman.add("G.J. Bailey");
		batsman.add("H.M. Amla");

		List<String> bowler = new ArrayList<String>();
		bowler.add("D.W. Steyn");
		bowler.add("J.M. Anderson");
		bowler.add("M.G. Johnson");

		List<String> all = new ArrayList<String>();
		all.add("R.A. Jadeja");
		all.add("Shakib Al Hasan");
		all.add("D.J. Bravo");

		List<String> wk = new ArrayList<String>();
		wk.add("A.B. de Villiers");
		wk.add("M.S. Dhoni");
		wk.add("K.C. Sangakkara");

		listDataChild.put(listDataHeader.get(0), batsman); // Header, Child data
		listDataChild.put(listDataHeader.get(1), bowler);
		listDataChild.put(listDataHeader.get(2), all);
		listDataChild.put(listDataHeader.get(2), wk);*/
		}

	@Override
	public boolean onNavigationItemSelected(MenuItem item) {
		item.setChecked(true);
		mSelectedId=item.getItemId();
		itemSelection(mSelectedId);
		return true;

	}

	private int itemSelection(int mSelectedId) {
		return mSelectedId;
	}

	public class ExpandableListAdapter extends BaseExpandableListAdapter {

		private Context _context;
		private List<String> _listDataHeader; // header titles
		// child data in format of header title, child title
		private HashMap<String, List<String>> _listDataChild;

		public ExpandableListAdapter(Context context, List<String> listDataHeader,
									 HashMap<String, List<String>> listChildData) {
			this._context = context;
			this._listDataHeader = listDataHeader;
			this._listDataChild = listChildData;
		}

		@Override
		public Object getChild(int groupPosition, int childPosititon) {
			return this._listDataChild.get(this._listDataHeader.get(groupPosition))
					.get(childPosititon);
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public View getChildView(int groupPosition, final int childPosition,
								 boolean isLastChild, View convertView, ViewGroup parent) {

			final String childText = (String) getChild(groupPosition, childPosition);

			if (convertView == null) {
				LayoutInflater infalInflater = (LayoutInflater) this._context
						.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				convertView = infalInflater.inflate(R.layout.list_item, null);
			}



			TextView txtListChild = (TextView) convertView
					.findViewById(R.id.lblListItem);

			 txtListChild.setTypeface(tf);

			txtListChild.setText(childText);
			return convertView;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return this._listDataChild.get(this._listDataHeader.get(groupPosition))
					.size();
		}

		@Override
		public Object getGroup(int groupPosition) {
			return this._listDataHeader.get(groupPosition);
		}

		@Override
		public int getGroupCount() {
			return this._listDataHeader.size();
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
								 View convertView, ViewGroup parent) {
			String headerTitle = (String) getGroup(groupPosition);
			//ImageView SelectedHeaderimage;

				if (convertView == null) {
					LayoutInflater infalInflater = (LayoutInflater) this._context
							.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
					convertView = infalInflater.inflate(R.layout.list_group, null);
				}

			((ImageView) convertView.findViewById(R.id.image1))
					.setImageResource(isExpanded?R.drawable.ic_play_arrow_black_dow_18dp:R.drawable.ic_play_arrow_black_18dp);
			/*if(expListView.isGroupExpanded(groupPosition)){
				//mProgAdap.prepareToCollapseGroup(groupPosition);
				setupLayoutAnimationClose(groupPosition);
				expListView.requestLayout();
			}else{
				boolean autoScrollToExpandedGroup = false;
				expListView.expandGroup(groupPosition,autoScrollToExpandedGroup);
				setupLayoutAnimation();
				/*//*//*
			}*/
		/*	if (isExpanded) {
				ImageView SelectedHeaderimage = (ImageView) convertView.findViewById(R.id.image1);
				 	SelectedHeaderimage.setImageResource(R.drawable.ic_play_arrow_black_dow_18dp);
			} else {
				ImageView SelectedHeaderimage = (ImageView) convertView.findViewById(R.id.image1);
				SelectedHeaderimage.setImageResource(R.drawable.ic_play_arrow_black_18dp);
			}*/
			if(headerTitle.contains("Personal Info")) {
				ImageView lblListHeaderimage = (ImageView) convertView.findViewById(R.id.image);
				lblListHeaderimage.setImageResource(R.drawable.ic_person_black_18dp);
			}
			if(headerTitle.contains("Leave Tracking")) {
				ImageView lblListHeaderimage = (ImageView) convertView.findViewById(R.id.image);
				lblListHeaderimage.setImageResource(R.drawable.ic_sort_black_18dp);
			}

			/*if(headerTitle.contains("Leave Tracking")) {
				ImageView	SelectedHeaderimage = (ImageView) convertView.findViewById(R.id.image1);
				SelectedHeaderimage.setImageResource(R.drawable.ic_play_arrow_black_18dp);
			}*/
				TextView lblListHeader = (TextView) convertView
						.findViewById(R.id.lblListHeader);


				//lblListHeader.setTypeface(null, Typeface.BOLD);
				lblListHeader.setText(headerTitle);
				lblListHeader.setTypeface(tf);

			if(headerTitle.contains("Logout")) {
				ImageView SelectedHeaderimage = (ImageView) convertView.findViewById(R.id.image);
				SelectedHeaderimage.setImageResource(R.drawable.ic_person_black_18dp);
			}


			return convertView;
		}

		//https://cloud.pockethcm.com/api/v2.0/EnumValue?schemaName=Greyt56af30a136&companyId=1&Fieldname=MARITALSTATUS
		/*View v;
		if (convertView == null) {
			v = newGroupView(isExpanded, parent);
		} else {
			v = convertView;
		}
		bindView(v, mGroupData.get(groupPosition), mGroupFrom, mGroupTo);
		((ImageView) v.findViewById(R.id.videos_group_indicator))
				.setImageResource(isExpanded?R.drawable.videos_chevron_expanded:R.drawable.videos_chevron_collapsed);
		return v;*/
		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}
	}
}

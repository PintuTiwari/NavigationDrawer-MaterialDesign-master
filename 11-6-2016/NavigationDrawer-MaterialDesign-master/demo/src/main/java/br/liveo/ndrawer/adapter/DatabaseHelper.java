package br.liveo.ndrawer.adapter;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

	
	public final static String ISREGISTER="isregister";
	
	public final static String ISPOLICY="ispolicy";


	public DatabaseHelper(Context context) {
		super(context, "stusent_detail", null, 1);
	}

	public final static String Table_Name="table_details";
	
	public final static String Table_Policy="table_policy";
	
	
	public final static String COULMN_id="_id";
	public final static String ROLL_NUMBER="roll_number";
	public final static String STD_NAME="sdt_name";
	public final static String STD_ADDRESS="std_address";
	public final static String STD_MOBILE="std_mobile";
	public final static String INSERT_DATE="insert_date";
	public final static String PROMOTOR_ISSUE="promotor_issuer";
	
	
	public final static String MODEL="model";
	public final static String MANUFACT_YEAR="manufac_year";
	public final static String STATE="state";
	public final static String CITY="city";
	public final static String HIRING_CHARGES="hiring_charge";
	public final static String OWNER_NAME="first_name";
	public final static String EMAIL="e_mail";
	public final static String WORK_ID="work_id";
	public final static String WORK_OWNR="work_ownr";
	public final static String VEHICLE_NAME="vehicle_name";
	public final static String VEHICLE_ID="vehicle_id";
	
	public final static String MACHIN_ID="machin_id";
	
	public final static String PROJECT_NAME="project_name";
	

// database creation sql statement.
	private String DATABASE_CREATION="Create table "+Table_Name+" ( "+COULMN_id+" integer primary key autoincrement ,"
			+VEHICLE_NAME+" text ,"
			+VEHICLE_ID +" text ,"
			+STD_NAME +" text ,"
			+STD_MOBILE +" text );";
	
	
	private String DATABASE_POLICY="Create table "+Table_Policy+" ( "+COULMN_id+" integer primary key autoincrement ,"
			+ISPOLICY+" text ,"
			+EMAIL +" text ,"
			+STD_NAME +" text ,"
			+STD_MOBILE +" text );";


	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(DATABASE_CREATION);
		db.execSQL(DATABASE_POLICY);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + Table_Name);
		db.execSQL("DROP TABLE IF EXISTS " + Table_Policy);
	


		onCreate(db);
	}

}

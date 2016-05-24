package epbit.constants;

import java.util.ArrayList;

import com.example.cabbookinghome.R;

public class AppConstants {

	public static ArrayList<String> categories = new ArrayList<String>();
	public static ArrayList<String> countryname= new ArrayList<String>();
	public static ArrayList<String> countryid= new ArrayList<String>();
	public static String CountryNamesString []=new String[238];
	public static ArrayList<String> getcategories() {
		categories.clear();
		categories.add("Available");
		categories.add("Booked");
		categories.add("Pending");
		categories.add("DND");

		return categories;

	}
	public static String catgories[]={"Available","Booked",
		"DND"};

	public static Integer imageids[] = { R.drawable.available,
			R.drawable.booked, R.drawable.dnd,
			R.drawable.dnd };
	
	
}

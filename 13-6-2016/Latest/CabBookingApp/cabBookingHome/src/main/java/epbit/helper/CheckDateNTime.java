package epbit.helper;

import epbit.Login.LoginDetails;

import android.content.Context;
import android.text.format.Time;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.TimePicker;
import android.widget.Toast;

public class CheckDateNTime {
	// 0 for pass
	// 1 for error
	//
	
	public static int check(DatePicker datepicker, TimePicker timepicker,
			Context context) {
		Time today = new Time(Time.getCurrentTimezone());
		today.setToNow();
		int flag = 1;
		if (datepicker.getYear() < today.year) {
			Toast.makeText(context, "Wrong Year Selected", Toast.LENGTH_SHORT)
					.show();
		}

		else if (datepicker.getYear() == today.year) {

			if (datepicker.getMonth() < today.month) {
				Toast.makeText(context, "Wrong Month Selected",
						Toast.LENGTH_SHORT).show();

			} else if (datepicker.getMonth() == today.month) {

				if (datepicker.getDayOfMonth() < today.monthDay) {
					Toast.makeText(context, "Wrong Day Selected",
							Toast.LENGTH_SHORT).show();

				} else if (datepicker.getDayOfMonth() == today.monthDay) {

					if (timepicker.getCurrentHour() < today.hour) {
						Toast.makeText(context, "Wrong Hour selected	",
								Toast.LENGTH_SHORT).show();
					} else if (timepicker.getCurrentHour() == today.hour) {
						if (timepicker.getCurrentMinute() < today.minute) {
							Toast.makeText(context, "Wrong Minutes",
									Toast.LENGTH_SHORT);
						}

						else {
							flag = 0;
						}

					} else {
						flag = 0;
					}

				} else {
					flag = 0;
				}

			} else {
				flag = 0;
			}

		} else {

			flag = 0;

		}
		return flag;
	}

	public static void make_time(TimePicker timePicker, DatePicker datePicker) {
		String MY_DATE_TIME = "";
		String MY_TIME_IN_FORMAT="";
		int Hour, Minute, Day, Month, Year;
		String AM_PM, Month_String = "";
		Hour = timePicker.getCurrentHour();
		Minute = timePicker.getCurrentMinute();
		Day = datePicker.getDayOfMonth();
		Month = datePicker.getMonth();
		Year = datePicker.getYear();
		LoginDetails.UserTimehitformat=""+Hour+":"+Minute;
		LoginDetails.UserDatehitformat=""+Year+"-"+((Month+1)<10?"0"+(Month+1):(Month+1))+"-"+((Day)<10?"0"+(Day):(Day));
		Log.e("TIME",""+Hour+" "+Minute+" "+Day+" "+Month+" "+Year);
		
		if (Hour < 12) {
			AM_PM = "AM";
		} else if (Hour == 12) {
			AM_PM = "Noon";
		} else if (Hour == 0) {
			AM_PM = "MidNight";
		} else {
			AM_PM = "PM";
		}

		if (Hour > 12) {
			Hour = Hour - 12;
		}
		switch (Month+1) {
		case 1:
			Month_String="JAN";
			break;
		case 2:

			Month_String="FEB";
			break;
		case 3:

			Month_String="MAR";
			break;
		case 4:

			Month_String="APR";
			break;
		case 5:

			Month_String="MAY";
			break;
		case 6:

			Month_String="JUN";
			break;
		case 7:

			Month_String="JUL";
			break;
		case 8:

			Month_String="AUG";
			break;
		case 9:

			Month_String="SEP";
			break;
		case 10:

			Month_String="OCT";
			break;
		case 11:

			Month_String="NOV";
			break;
		case 12:

			Month_String="DEC";
			break;

		default:
			Month_String="";
			break;
		}
		
		MY_DATE_TIME = "" + (Hour<10?"0"+Hour:Hour) + ":" + (Minute<10?"0"+Minute:Minute) + " " + AM_PM + " on " + Day
				+ " " + Month_String + " " + Year;
		LoginDetails.UserTime="" + (Hour<10?"0"+Hour:Hour) + ":" + (Minute<10?"0"+Minute:Minute) + " " + AM_PM;
		LoginDetails.UserDate=""+Day
				+ " " + Month_String + " " + Year;
		

	}

}
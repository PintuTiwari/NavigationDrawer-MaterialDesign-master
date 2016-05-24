package com.javacodegeeks.androidstartactivityforresultexample;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ActivityOne extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		//set the layout of the Activity
		setContentView(R.layout.activity_main);
	}

	public void redBackground(View v) {
		// Here we create an Intent 
		// and we put an extra in order to pass it to the ActivityTwo        
		Intent intent = new Intent(this, ActivityTwo.class);
		
		//the extra is a String that tell the background color choice
		intent.putExtra("color","red");
		
		//we start ActivityTwo with the above extra value
		startActivityForResult(intent,1);
		finish();
	}

	public void greenBackground(View v) {
		Intent intent = new Intent(this, ActivityTwo.class);
		intent.putExtra("color","green");
		startActivityForResult(intent,1);
		finish();
	}

	public void blueBackground(View v) {
		Intent intent = new Intent(this, ActivityTwo.class);
		intent.putExtra("color","blue");
		startActivityForResult(intent,1);
		finish();
	}

}

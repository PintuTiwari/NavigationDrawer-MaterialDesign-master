package com.javacodegeeks.androidstartactivityforresultexample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class ActivityTwo extends Activity {
	LinearLayout containerTwo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set the layout of the Activity
		setContentView(R.layout.activity_two);
		containerTwo = (LinearLayout) findViewById(R.id.containerTwo);

		// get the intent that we have passed from ActivityOne
		Intent intent = getIntent();

		// get the extra value
		String color = intent.getStringExtra("color");
		changeBackground(color);
	}

	public void openActivityOne(View v) {
		Intent intent = new Intent(this, ActivityOne.class);
		startActivity(intent);
		finish();
	}

	public void changeBackground(String color) {
		// depending the extra value String, choose a background color
		if (color.equals("red")) {
			containerTwo.setBackgroundColor(Color.RED);
		} else if (color.equals("green")) {
			containerTwo.setBackgroundColor(Color.GREEN);
		} else if (color.equals("blue")) {
			containerTwo.setBackgroundColor(Color.BLUE);
		}
	}
}

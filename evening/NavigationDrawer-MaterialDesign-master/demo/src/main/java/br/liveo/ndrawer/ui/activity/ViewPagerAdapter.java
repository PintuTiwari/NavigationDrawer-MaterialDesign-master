package br.liveo.ndrawer.ui.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

	CharSequence Titles[];
	int NumbOfTabs;
	Fragment fragment;
/*	public ViewPagerAdapter(Fragment fm, CharSequence mTitles[],
							int mNumbOfTabsumb) {
		//super(fm);
		this.Titles = mTitles;
		this.fragment = fm;
		this.NumbOfTabs = mNumbOfTabsumb;
	}*/

	public ViewPagerAdapter(FragmentManager supportFragmentManager, CharSequence[] titles, int numboftabs) {
		super(supportFragmentManager);
		this.Titles = titles;
		this.NumbOfTabs = numboftabs;
	}

	// This method return the fragment for the every position in the View Pager
	@Override
	public Fragment getItem(int position) {
		switch (position) {
		case 0:
			return new Entries_Fragment();

		case 1:
			return new Entries_Fragment();
		case 2:
			return new Entries_Fragment();
		}
		return null;
	}
	// This method return the titles for the Tabs in the Tab Strip
	@Override
	public CharSequence getPageTitle(int position) {
		return Titles[position];
	}
	// This method return the Number of tabs for the tabs Strip
	@Override
	public int getCount() {
		return NumbOfTabs;
	}
}
package br.liveo.ndrawer.ui.activity;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import br.liveo.ndrawer.R;
//http://www.tutorialsbuzz.com/2015/10/Android-Sliding-TabLayout-ListView-WhatsApp.html
public class Entries_Activity extends Fragment {


	View rootView;
	ExpandableListView lv;
	private String[] groups;
	private String[][] children;
	Fragment fragment = null;
	String[] items = {"TDS Entry", "Worksheet - TDS", "TDS Proof Entry"};
	private SlidingTabLayout mSlidingTabLayout;
	CharSequence Titles[] = {"Calls", "Chats", "Contacts"};
	int Numboftabs = 3;
	private ViewPagerAdapter adapter;
	/**
	 * A {@link ViewPager} which will be used in conjunction with the {@link SlidingTabLayout} above.
	 */
	private ViewPager mViewPager;
	public Entries_Activity() {

	}

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		groups = new String[]{"House Rent Paid", "Deductions", "Section 80C",
				"Aggregate of Deductible amounts under Chapter VI - A","Medical",
				"Donations to certain funds, charitable institutions"};

		children = new String[][]{
				{"s simply "},
				{"s simply "},
				{"s simply "},
				{"Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor at Hampden-Sydney College in Virginia, looked up one of the more obscure Latin words, consectetur, from a Lorem Ipsum passage, and going through the cites of the word in classical literature, discovered the undoubtable source. Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of comes from a line in section 1.10.32."},
				{"It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English. Many desktop publishing packages and web page editors now use Lorem Ipsum as their default model text, and a search for 'lorem ipsum' will uncover many web sites still in their infancy. Various versions have evolved over the years, sometimes by accident, sometimes on purpose (injected humour and the like)."},
				{"There are many variations of passages of Lorem Ipsum available, but the majority have suffered alteration in some form, by injected humour, or randomised words which don't look even slightly believable. If you are going to use a passage of Lorem Ipsum, you need to be sure there isn't anything embarrassing hidden in the middle of text. All the Lorem Ipsum generators on the Internet tend to repeat predefined chunks as necessary, making this the first true generator on the Internet. It uses a dictionary of over 200 Latin words, combined with a handful of model sentence structures, to generate Lorem Ipsum which looks reasonable. The generated Lorem Ipsum is therefore always free from repetition, injected humour, or non-characteristic words etc."}
		};
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		rootView = inflater.inflate(R.layout.third, container, false);

		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

	mViewPager = (ViewPager) view.findViewById(R.id.viewpager);
		mViewPager.setAdapter(new SamplePagerAdapter());

		// Give the SlidingTabLayout the ViewPager, this must be done AFTER the ViewPager has had
		// it's PagerAdapter set.
		mSlidingTabLayout = (SlidingTabLayout) view.findViewById(R.id.sliding_tabs);
		mSlidingTabLayout.setViewPager(mViewPager);

/*
		lv = (ExpandableListView) view.findViewById(R.id.expListView);
		lv.setAdapter(new ExpandableListAdapter(getContext(), groups, children));
		lv.setGroupIndicator(null);

		lv.setDescendantFocusability(ExpandableListView.FOCUS_AFTER_DESCENDANTS);*/

	}
	/*WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
	Display display = wm.getDefaultDisplay();
	Point size = new Point();
	display.getSize(size);
	textView.setWidth(size.x / 3);*/



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
				view = getActivity().getLayoutInflater().inflate(R.layout.expandablelistlayout, container, false);
				 Toast.makeText(getActivity(), "expandablelistlayout", Toast.LENGTH_SHORT).show();
				ExpandableListView lv = (ExpandableListView) view.findViewById(R.id.expListView);
				container.addView(view);
				lv.setAdapter(new ExpandableListAdapter(getContext(), groups, children));
				lv.setGroupIndicator(null);

				lv.setDescendantFocusability(ExpandableListView.FOCUS_AFTER_DESCENDANTS);



			}


			if(position==1) {
				view = getActivity().getLayoutInflater().inflate(R.layout.item_recycler, container, false);
				// Add the newly created View to the ViewPager
				Toast.makeText(getActivity(), "HI", Toast.LENGTH_SHORT).show();

				container.addView(view);


			}
			if(position==2) {
				view = getActivity().getLayoutInflater().inflate(R.layout.expandablelistlayout, container, false);
				// Add the newly created View to the ViewPager
				Toast.makeText(getActivity(), "HIIII", Toast.LENGTH_SHORT).show();

				container.addView(view);

			}

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


	public class ExpandableListAdapter extends BaseExpandableListAdapter {

		private final LayoutInflater inf;
		private String[] groups;
		private String[][] children;
		Context context;
		String fontPath = "fonts/Roboto-Regular.ttf";
		Typeface tf;


		public ExpandableListAdapter(Context context,String[] groups, String[][] children) {
			this.groups = groups;
			this.children = children;
			this.context = context;
			inf = LayoutInflater.from(getActivity());
		}

		@Override
		public int getGroupCount() {
			return groups.length;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return children[groupPosition].length;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return groups[groupPosition];
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return children[groupPosition][childPosition];
		}

		@Override
		public long getGroupId(int groupPosition) {
			return groupPosition;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return childPosition;
		}

		@Override
		public boolean hasStableIds() {
			return true;
		}

		/*@Override
		public View getChildView(int groupPosition, final int childPosition, boolean isLastChild, View convertView, ViewGroup parent)
		{

			ViewHolder holder;
			if (convertView == null) {
				convertView = inf.inflate(R.layout.list_item, parent, false);
				holder = new ViewHolder();

				holder.text = (TextView) convertView.findViewById(R.id.lblListItem);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}

			holder.text.setText(getChild(groupPosition, childPosition).toString());

			return convertView;
		}*/

		@Override
		public View getChildView(int groupPosition, int childPosition,
								 boolean isLastChild, View convertView, ViewGroup parent) {
			tf = Typeface.createFromAsset(getActivity().getAssets(), fontPath);

			View v = null;
			if(groupPosition == 2) {
				v = View.inflate(context, R.layout.expandable_child_layout, null);
				EditText txtView = (EditText) v.findViewById(R.id.providentfund);


			}
			if(groupPosition == 3) {
				v = View.inflate(context, R.layout.expandable_child_1_layout, null);
			}
			if(groupPosition == 1) {
				v = View.inflate(context, R.layout.expandable_child_2_layout, null);
			}
			if(groupPosition == 4) {
				v = View.inflate(context, R.layout.expandable_child_3_layout, null);
			/*	TextView txtView = (TextView) v.findViewById(R.id.txtChld1);
				txtView.setText("Purple");
				txtView.setTextSize(15f);*/
			}
			if(groupPosition == 5) {
				v = View.inflate(context, R.layout.expandable_child_4_layout, null);
			/*	TextView txtView = (TextView) v.findViewById(R.id.txtChld1);
				txtView.setText("Purple");
				txtView.setTextSize(15f);*/
			}

			v.invalidate();
			v.setFocusableInTouchMode(true);

			return v;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
			ViewHolder holder;

			if (convertView == null) {
				convertView = inf.inflate(R.layout.entrieslistgroup, parent, false);

				holder = new ViewHolder();
				holder.text = (TextView) convertView.findViewById(R.id.lblListHeader);
				holder.text.setTypeface(tf);
				convertView.setTag(holder);

			}
			else {
				holder = (ViewHolder) convertView.getTag();

			}

			holder.text.setText(getGroup(groupPosition).toString());



			((ImageView) convertView.findViewById(R.id.image1))
					.setImageResource(isExpanded ? R.drawable.ic_expand_less_black_12dp : R.drawable.ic_expand_more_black_12dp);
			return convertView;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		private class ViewHolder {
			TextView text;
		}
	}
}

/*ExpandableListView expandableListView;
	@Override
	*//*public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.third, null);
		expandableListView=(ExpandableListView)rootView.findViewById(R.id.expandableListView);
		ExampleAdapter adapter = new ExampleAdapter(this, getLayoutInflater());
		setListAdapter(adapter);
		return rootView;
	}*//*
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.third);
		ExampleAdapter adapter = new ExampleAdapter(this, getLayoutInflater());
		setListAdapter(adapter);

	}
	class ExampleAdapter implements ExpandableListAdapter {
		Context context;
		LayoutInflater layoutInflater;
		public ExampleAdapter(Context context, LayoutInflater layoutInflater) {
			this.context = context;
			this.layoutInflater = layoutInflater;
		}

		@Override
		public boolean areAllItemsEnabled() {
			return true;
		}

		@Override
		public Object getChild(int groupPosition, int childPosition) {
			return null;
		}

		@Override
		public long getChildId(int groupPosition, int childPosition) {
			return 0;
		}

		@Override
		public View getChildView(int groupPosition, int childPosition,
								 boolean isLastChild, View convertView, ViewGroup parent) {

			View v = null;
			if(groupPosition == 0) {
				v = View.inflate(context, R.layout.expandable_child_layout, null);
				TextView txtView = (TextView) v.findViewById(R.id.txtChld1);
				txtView.setText("Green");
				txtView.setTextSize(15f);
				txtView.setBackgroundColor(Color.GREEN);
			}
			if(groupPosition == 1) {
				v = View.inflate(context, R.layout.expandable_child_1_layout, null);
			}
		*//*	if(groupPosition == 2) {
				v = View.inflate(context, R.layout.expandable_child_2_layout, null);
			}
			if(groupPosition == 3) {
				v = View.inflate(context, R.layout.expandable_child_layout, null);
				TextView txtView = (TextView) v.findViewById(R.id.txtChld1);
				txtView.setText("Purple");
				txtView.setTextSize(15f);
			}*//*
			v.invalidate();
			return v;
		}

		@Override
		public int getChildrenCount(int groupPosition) {
			return 1;
		}

		@Override
		public long getCombinedChildId(long groupId, long childId) {
			return 0;
		}

		@Override
		public long getCombinedGroupId(long groupId) {
			return 0;
		}

		@Override
		public Object getGroup(int groupPosition) {
			return null;
		}



		@Override
		public int getGroupCount() {
			return 4;
		}

		@Override
		public long getGroupId(int groupPosition) {
			return 0;
		}

		@Override
		public View getGroupView(int groupPosition, boolean isExpanded,
								 View convertView, ViewGroup parent) {

			View v = convertView.inflate(context, R.layout.expandable_group_layout, null);
			TextView txtView = (TextView) v.findViewById(R.id.txt1);
			if(groupPosition == 0) {
				txtView.setText("Group Head 1");
				txtView.setTextSize(15f);
			}
			if(groupPosition == 1) {
				txtView.setText("Group Head 2");
				txtView.setTextSize(15f);
			}
			if(groupPosition == 2) {
				txtView.setText("Group Head 3");
				txtView.setTextSize(15f);
			}
			if(groupPosition == 3) {
				txtView.setText("Group Head 4");
				txtView.setTextSize(15f);
			}
			v.invalidate();
			return v;

		}

		@Override
		public boolean hasStableIds() {
			return false;
		}

		@Override
		public boolean isChildSelectable(int groupPosition, int childPosition) {
			return true;
		}

		@Override
		public boolean isEmpty() {
			return false;
		}

		@Override
		public void onGroupCollapsed(int groupPosition) {

		}

		@Override
		public void onGroupExpanded(int groupPosition) {

		}

		@Override
		public void registerDataSetObserver(DataSetObserver observer) {

		}

		@Override
		public void unregisterDataSetObserver(DataSetObserver observer) {

		}

	}
}
*/

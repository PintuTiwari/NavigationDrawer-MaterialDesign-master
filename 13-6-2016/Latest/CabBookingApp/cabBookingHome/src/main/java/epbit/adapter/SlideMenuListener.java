package epbit.adapter;

import android.app.Activity;
import android.content.Intent;

import com.coboltforge.slidemenu.SlideMenu;
import com.coboltforge.slidemenu.SlideMenuInterface.OnSlideMenuItemClickListener;
import com.example.cabbookinghome.CabMoney;
import com.example.cabbookinghome.Help;
import com.example.cabbookinghome.Profile;
import com.example.cabbookinghome.R;
import com.example.cabbookinghome.RateCard;
import com.example.cabbookinghome.ReferActivity;
import com.example.cabbookinghome.Rides;
import com.example.cabbookinghome.SignOut;

import epbit.helper.CheckUserType;

public class SlideMenuListener implements OnSlideMenuItemClickListener {
	Activity activity;
	int user_type;
	int sidemenu_id;
	//0 for user
	// 1 for driver
	public SlideMenuListener(Activity activity,int sidemenu_id) {
		super();
		this.activity = activity;
//		this.user_type = user_type;
		this.sidemenu_id=sidemenu_id;
		((SlideMenu)activity.findViewById(R.id.slideMenu)).setHeaderImage(activity.getResources().getDrawable(
				R.drawable.ic_launcher));
		
		
	}

	@Override
	public void onSlideMenuItemClick(int itemId) {
		
		if(itemId!=sidemenu_id){
		switch (itemId) {

		case R.id.item_home:
			CheckUserType.intentservice(activity);
			break;
		case R.id.item_one:
			activity.startActivity(new Intent(activity, Profile.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			activity.finish();

			break;
		case R.id.item_two:

			activity.startActivity(new Intent(activity, Rides.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			activity.finish();

			break;
		case R.id.item_three:
			activity.startActivity(new Intent(activity, CabMoney.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			activity.finish();

			break;
		case R.id.item_four:
			activity.startActivity(new Intent(activity, RateCard.class)
			.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			activity.finish();
			break;
		case R.id.item_five:
			activity.startActivity(new Intent(activity, Help.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			activity.finish();

			break;
		case R.id.item_six:
			activity.startActivity(new Intent(activity, SignOut.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			activity.finish();

			break;
		case R.id.item_seven:
			activity.startActivity(new Intent(activity, ReferActivity.class)
					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
			activity.finish();

			break;
//		case R.id.item_eight:
//			activity.startActivity(new Intent(activity, PaymentActivity.class)
//					.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
//			activity.finish();
//			break;
		}
	}
	}
	

}

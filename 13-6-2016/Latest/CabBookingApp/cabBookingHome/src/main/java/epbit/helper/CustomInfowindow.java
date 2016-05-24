package epbit.helper;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.cabbookinghome.R;
import com.google.android.gms.maps.GoogleMap.InfoWindowAdapter;
import com.google.android.gms.maps.model.Marker;

public class CustomInfowindow implements InfoWindowAdapter {

	private Context context;

	public CustomInfowindow(Context context) {
		super();
		this.context = context;
	}

	@Override
	public View getInfoWindow(Marker arg0) {

		return null;
	}

	@Override
		public View getInfoContents(Marker marker) {

			LayoutInflater inflator = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View v = inflator.inflate(R.layout.custom_marker, null);
			TextView infowindow = (TextView) v
					.findViewById(R.id.info_window_id);
			if (marker!=null&&marker.getTitle().length()>0)
			{
				
				infowindow.setText(marker.getTitle());
				

			}
			else{
				infowindow.setText("No Address");
				
			}
					
					
			return v;
		}

}

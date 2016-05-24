package br.liveo.ndrawer.ui.activity;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import br.liveo.ndrawer.R;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<Friend> friends;
    private List<Friend> friends1;
    private Activity activity;
    private Activity activity1;
    public RecyclerAdapter context = this;
    Button delete,edit;
    EditText Name,Address,Relation,Dateof_Birth;

    public RecyclerAdapter(Activity activity, List<Friend> friends) {
        this.friends = friends;
        this.activity = activity;
    }


    @Override
    public int getItemViewType(int position) {
       Log.i("getItemVi(position)", "position=" + position);
        //If position is 0 this means we need to use FirstOpenRestaurantVHolder
        if (position == 0) {
            return 0;

        } else if (position > 0) {
            return 1;
        }


        return 1; //TODO ojo con este default
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        Log.i("onCreateVewHo(viewType)", "viewType=" + viewType);

        LayoutInflater inflater = activity.getLayoutInflater();
      //  LayoutInflater inflater ;
      View view = inflater.inflate(R.layout.item_recycler, viewGroup, false);
       ViewHolder viewHolder = new ViewHolder(view);



      /*  if (viewType == 0) {
            inflater=  activity.getLayoutInflater();
            View view = inflater.inflate(R.layout.item_recycler, viewGroup, false);
             viewHolder = new ViewHolder(view);

        } else {
            inflater=  activity1.getLayoutInflater();
            View view = inflater.inflate(R.layout.item_recycler, viewGroup, false);
             viewHolder = new ViewHolder(view);
        }

*/
        return viewHolder;
    }



    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder viewHolder, int position) {

        //setting data to view holder elements
        viewHolder.name.setText(friends.get(position).getName());
        viewHolder.job.setText(friends.get(position).getJob());

        if (friends.get(position).isGender()) {
            viewHolder.imageView.setImageResource(R.drawable.xxhdpi);
        } else {
            viewHolder.imageView.setImageResource(R.drawable.xxhdpi);
        }
        //set on click listener for each element
    //    viewHolder.container.setOnClickListener(onClickListener(position));
    }

  //  private void setDataToView(TextView name, TextView job, ImageView genderIcon, int position) {
        private void setDataToView(TextView job, ImageView genderIcon, int position) {
     //   name.setText(friends.get(position).getName());
        job.setText(friends.get(position).getJob());
        if (friends.get(position).isGender()) {
            genderIcon.setImageResource(R.drawable.xxhdpi);
        } else {
            genderIcon.setImageResource(R.drawable.xxhdpi);
        }
    }

    @Override
    public int getItemCount() {
        return (null != friends ? friends.size() : 0);
    }

   /* private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.item_recycler);
                dialog.setTitle("Position " + position);
                dialog.setCancelable(true); // dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                //TextView name = (TextView) dialog.findViewById(R.id.name);
                TextView job = (TextView) dialog.findViewById(R.id.job);
                ImageView icon = (ImageView) dialog.findViewById(R.id.image);

              //  setDataToView(name, job, icon, position);
                setDataToView(job, icon, position);

                dialog.show();
            }
        };
    }*/







   /* private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch(position) {

                    case 0:

                        Toast.makeText(v.getContext(),"MyDetails" + position, Toast.LENGTH_SHORT).show();
                        HashMap<String, String> hashMap = new HashMap<String, String>();
                        hashMap.put("FATHERNAME", "Prakash");
                        hashMap.put("PTLOCATION", "3");
                        hashMap.put("MARITALSTATUS", "Single");
                        hashMap.put("DATEOFBIRTH", "01/01/1986 12:00:00 AM");
                        hashMap.put("CONFIRMATIONPERIOD", "12");
                        hashMap.put("DATEOFANNIVERSARY", "");
                        hashMap.put("DATEOFJOINING", "01/01/2015 12:00:00 AM");
                        hashMap.put("RETIREMENTAGE", "20");
                        hashMap.put("DATEOFRETIREMENT", "23/01/2015 12:00:00 AM");
                        hashMap.put("PFACCNO", "213213");
                        hashMap.put("ESINO", "142");
                        hashMap.put("GRADE", "142");
                        hashMap.put("DATEOFRETIREMENT", "87");
                        hashMap.put("CHILDREN", "0");
                        hashMap.put("COSTCENTRE", "38");
                        hashMap.put("BRANCH", "39");
                        hashMap.put("PANNO", "21321321321321321321");
                        hashMap.put("METRO", "Non-Metro");
                        hashMap.put("EMPPF", "3.67");
                        hashMap.put("PENSIONFUND", "8.33");
                        hashMap.put("TAXAPPLICABLE", "6");
                        hashMap.put("STOPPAYMENT", "Yes");
                        hashMap.put("LOCATION", "40");

                //  intnt =  new Intent(context, MyDetails.class);
                        final Intent intent = new Intent(v.getContext(), MyDetails.class);
                        intent.putExtra("map", hashMap);
                         v.getContext().startActivity(intent);
                        break;
                    case 1:
                       // Toast.makeText(view.getContext(), "Additionals etails" + i, Toast.LENGTH_SHORT).show();

                        Toast.makeText(v.getContext(),"Additionals_Details" + position, Toast.LENGTH_SHORT).show();
                        //  intent =  new Intent(context, MyDetails.class);
                        final Intent intent1 = new Intent(v.getContext(), Personal_Details.class);
                        v.getContext().startActivity(intent1);
                        break;


                    case 2:
                        Toast.makeText(v.getContext(),"Personal_Details" + position, Toast.LENGTH_SHORT).show();
                        final Intent intent2 = new Intent(v.getContext(), Additionals_Details.class);
                        v.getContext().startActivity(intent2);
                        break;

                    case 6:

                        Toast.makeText(v.getContext(),"Personal_Details" + position, Toast.LENGTH_SHORT).show();
                        final Intent intent3 = new Intent(v.getContext(), Family_Details.class);
                        v.getContext().startActivity(intent3);
                        break;

                    case 3:
                        Toast.makeText(v.getContext(),"Academic_Details" + position, Toast.LENGTH_SHORT).show();
                        final Intent intent4 = new Intent(v.getContext(), Academic_Details.class);
                        v.getContext().startActivity(intent4);
                        break;

                    case 4:
                        Toast.makeText(v.getContext(), "Academic_Details" + position, Toast.LENGTH_SHORT).show();
                        final Intent intent5 = new Intent(v.getContext(), Emergency_Contact.class);
                        v.getContext().startActivity(intent5);
                        break;

                    case 5:
                        Toast.makeText(v.getContext(), "Academic_Details" + position, Toast.LENGTH_SHORT).show();
                        final Intent intent6 = new Intent(v.getContext(), Employee_Language.class);
                        v.getContext().startActivity(intent6);
                        break;

                    case 7:
                        Toast.makeText(v.getContext(), "Academic_Details" + position, Toast.LENGTH_SHORT).show();
                        final Intent intent7 = new Intent(v.getContext(), Nominee_Details.class);
                        v.getContext().startActivity(intent7);
                        break;

                    case 8:
                        Toast.makeText(v.getContext(), "Academic_Details" + position, Toast.LENGTH_SHORT).show();
                        final Intent intent8 = new Intent(v.getContext(), Training_Details.class);
                        v.getContext().startActivity(intent8);
                        break;

                    case 9:
                        Toast.makeText(v.getContext(), "Academic_Details" + position, Toast.LENGTH_SHORT).show();
                        final Intent intent9 = new Intent(v.getContext(), Joining_Documents.class);
                        v.getContext().startActivity(intent9);
                        break;
                }
            }
        };
    }
*/


    /**
     * View holder to display each RecylerView item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name;
        private TextView job;
        private View container;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.image);
            name = (TextView) view.findViewById(R.id.name);
            job = (TextView) view.findViewById(R.id.job);
            container = view.findViewById(R.id.card_view);
        }
    }
}
package com.honey.admin.myapplication;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

//https://www.binpress.com/tutorial/android-l-recyclerview-and-cardview-tutorial/156
public class RecyclerAdapterUpload extends RecyclerView.Adapter<RecyclerAdapterUpload.ViewHolder> {

    private List<Friend_Naminee> friends;
    private Activity activity;
    public RecyclerAdapterUpload context = this;
    Button delete,edit;
    EditText Name,Address,Relation,Dateof_Birth,Age;


    public RecyclerAdapterUpload(Activity activity, List<Friend_Naminee> friends) {
        this.friends = friends;
        this.activity = activity;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //inflate your layout and pass it to view holder
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.item_recycler_upload, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        delete = (Button) view.findViewById(R.id.delete);



        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerAdapterUpload.ViewHolder viewHolder, final int position) {

        //setting data to view holder elements
        viewHolder.name1.setText(friends.get(position).getName());
      /*  viewHolder.address.setText(friends.get(position).getJob());
        viewHolder.Relation.setText(friends.get(position).getRelation());
        viewHolder.dob.setText(friends.get(position).getDateof_Birth());
        viewHolder.age.setText(friends.get(position).getAge());
*/
       /* if (friends.get(position).isGender()) {
            viewHolder.imageView.setImageResource(R.drawable.xxhdpi);
        } else {
            viewHolder.imageView.setImageResource(R.mipmap.female);
        }*/
        //set on click listener for each element
        viewHolder.container.setOnClickListener(onClickListener(position));


        viewHolder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // Toast.makeText(this() "Click on Thumbnail", Toast.LENGTH_LONG).show();
                // Toast.makeText(RecyclerAdapter(), "Click on Thumbnail", Toast.LENGTH_SHORT).show();
                System.out.println("Click on Thumbnail");
                // int position = (Integer) v.getTag();
                openDialog(position);
                //  friends.remove(position);

            }

        });
    }


  /* viewHolder. edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                // Toast.makeText(this() "Click on Thumbnail", Toast.LENGTH_LONG).show();
                // Toast.makeText(RecyclerAdapter(), "Click on Thumbnail", Toast.LENGTH_SHORT).show();
                System.out.println("Click on Thumbnail");
                Name = (EditText)activity.findViewById(R.id.name);
                Address = (EditText)activity. findViewById(R.id.address);
                Relation = (EditText) activity.findViewById(R.id.relation);
                Dateof_Birth = (EditText)activity.findViewById(R.id.dateofbirth);
                Age = (EditText) activity.findViewById(R.id.age);

                setDataToView(Name, Address, Relation, Dateof_Birth, Age, position);
                Name.setText(Name.getText().toString().trim());
                Address.setText( Address.getText().toString().trim());
                Relation.setText( Relation.getText().toString().trim());
                Dateof_Birth.setText( Dateof_Birth.getText().toString().trim());
                Age.setText( Age.getText().toString().trim());
            }

        });
    }*/


   /* private void setDataToView(TextView name, TextView job, ImageView genderIcon, int position) {
        name.setText(friends.get(position).getName());
        job.setText(friends.get(position).getJob());
        if (friends.get(position).isGender()) {
            genderIcon.setImageResource(R.mipmap.male);
        } else {
            genderIcon.setImageResource(R.mipmap.female);
        }
    }*/

    private void setDataToView(TextView Name, TextView Address, TextView Relation, TextView Dateof_Birth,TextView Age,int position) {
        Name.setText(friends.get(position).getName());
       /* Address.setText(friends.get(position).getJob());
        Relation.setText(friends.get(position).getRelation());
        Dateof_Birth.setText(friends.get(position).getDateof_Birth());
        Age.setText(friends.get(position).getAge());*/
        /*if (friends.get(position).isGender()) {
            genderIcon.setImageResource(R.mipmap.male);
        } else {
            genderIcon.setImageResource(R.mipmap.female);
        }*/
    }

    //  String Name, String Address, String Relation, String Dateof_Birth, String Ag

    @Override
    public int getItemCount() {
        return (null != friends ? friends.size() : 0);
    }

    private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.item_recycler_upload);
                dialog.setTitle("Position " + position);
                dialog.setCancelable(true); // dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                TextView Name = (TextView) dialog.findViewById(R.id.name);

                // Button  Delete = (Button) view.findViewById(R.id.delete);


                setDataToView(Name, Address, Relation,Dateof_Birth,Age, position);
                //String Name, String Address, String Relation, String Dateof_Birth, String Age
                dialog.show();
            }
        };
    }



   /* public void Delete(View v) {
        int position = (Integer) v.getTag();
        openDialog(position);

    }*/

    public void openDialog(final int position) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activity);

        // set title
        alertDialogBuilder.setTitle("Alert!" +position);
        // set dialog message
        alertDialogBuilder
                .setMessage("Do you really want to delete?")
                .setCancelable(true)

                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // if this button is clicked, close

                                dialog.cancel();
                                friends.remove(position);

                                notifyDataSetChanged();
                            }
                        })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // if this button is clicked, just close
                        // the dialog box and do nothing
                        dialog.cancel();
                    }
                });

        // create alert dialog
        AlertDialog alertDialog = alertDialogBuilder.create();

        // show it
        alertDialog.show();

    }


    /**
     * View holder to display each RecylerView item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView name1;
        private TextView address;
        private TextView Relation;
        private TextView dob;
        private TextView age;
        Button delete,edit;

        private View container;

        public ViewHolder(View view) {
            super(view);
            //  imageView = (ImageView) view.findViewById(R.id.image);
            name1 = (TextView) view.findViewById(R.id.name);
            delete = (Button) view.findViewById(R.id.delete);
            container = view.findViewById(R.id.card_view);
        }



    }
}
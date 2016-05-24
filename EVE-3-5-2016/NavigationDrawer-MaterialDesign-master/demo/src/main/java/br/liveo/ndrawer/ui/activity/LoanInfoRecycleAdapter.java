package br.liveo.ndrawer.ui.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Environment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import br.liveo.ndrawer.R;

//https://www.binpress.com/tutorial/android-l-recyclerview-and-cardview-tutorial/156
public class LoanInfoRecycleAdapter extends RecyclerView.Adapter<LoanInfoRecycleAdapter.ViewHolder> {
    String part1,part44,part66,part55;
    int part4,part6,part5;
    private List<Person> friends;
    private Activity activity;
    private String PFINFO;

    CardView cv,cv1,cv2,cv3;
    private int mYear, mMonth, mDay, mHour, mMinute;
    public LoanInfoRecycleAdapter context = this;
    private ImageView download,edit;
    EditText Month,Year,NetPay;
    private static final String PREF_NAME_PRIFRNCR = "isregisterd";
    String Months, Years, NetPays,SchemaName, userId, CompanyId,Fieldvalue;
    Typeface tf;
    private ProgressDialog pDialog;

    // Progress dialog type (0 - for Horizontal progress bar)
    public static final int progress_bar_type = 0;

    // File url to download
    private static String file_url = "http://api.androidhive.info/progressdialog/hive.jpg";


    Map<String, String> responseMap = new LinkedHashMap<String, String>();
    public LoanInfoRecycleAdapter(Activity activity, List<Person> friends) {
        this.friends = friends;
        this.activity = activity;
    }
   //http://www.cuelogic.com/blog/android-code-to-upload-download-large-files-to-server-2/


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {

        //inflate your layout and pass it to view holder
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(R.layout.loaninfolist, viewGroup, false);
        ViewHolder viewHolder = new ViewHolder(view);
        String fontPath = "fonts/Roboto-Regular.ttf";
        tf = Typeface.createFromAsset(activity.getAssets(), fontPath);
        Log.d("viewGroup",viewGroup.toString());
        Log.d("viewType", String.valueOf(viewType));


        // Month	Year	Net Pay (Rs)
        //Employee Contribution		Employer Contribution
       // Month	Year	Per Month	Cumulative	EPF(Rs)	Cumulative	FPF(Rs)	Cumulative	Total(Rs)	VPF
        //Loan Code	Loan Description	Loan Date	Amount (Rs)	Paid (Rs)	Balance (Rs)

        download = (ImageView) view.findViewById(R.id.download);

        SharedPreferences setting1 =activity.getSharedPreferences(PREF_NAME_PRIFRNCR, Context.MODE_PRIVATE);
        SchemaName = setting1.getString("SchemaName", "SchemaName");
        userId = setting1.getString("userId", "userId");
        CompanyId = setting1.getString("CompanyId", "CompanyId");

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(LoanInfoRecycleAdapter.ViewHolder viewHolder, final int position) {
        //setting data to view holder elements
        viewHolder.Month.setText(friends.get(position).Month);
        viewHolder.Year.setText(friends.get(position).Year);
        viewHolder.NetPay.setText(friends.get(position).NetPay);

      /*  viewHolder.download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                openDialog(position);
                //  friends.remove(position);

            }

        });*/


   /*viewHolder. edit.setOnClickListener(new View.OnClickListener() {
       @Override
       public void onClick(final View v) {

           Month = (EditText) activity.findViewById(R.id.name_nominee);
           Year = (EditText) activity.findViewById(R.id.address_nominee);
           NetPay = (EditText) activity.findViewById(R.id.nominee_relation);

           setDataToView(Month, Year, NetPay, position);

           Month.setText(Month.getText().toString().trim());
           Year.setText(Year.getText().toString().trim());
           NetPay.setText(NetPay.getText().toString().trim());

       }

   });*/

    }









    private void setDataToView(TextView Month, TextView Year, TextView NetPay, int position)
    {
        Month.setText(friends.get(position).Month);
        Year.setText(friends.get(position).Year);
        NetPay.setText(friends.get(position).NetPay);

    }

  //  String Name, String Address, String Relation, String Dateof_Birth, String Ag

    @Override
    public int getItemCount() {
        return (null != friends ? friends.size() : 0);
    }

   /* private View.OnClickListener onClickListener(final int position) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(activity);
                dialog.setContentView(R.layout.item_recycler_nominee);
                dialog.setTitle("Position " + position);
                dialog.setCancelable(true); // dismiss when touching outside Dialog

                // set the custom dialog components - texts and image
                TextView Name_Nominee = (TextView) dialog.findViewById(R.id.name);
                TextView Address_Nominee = (TextView) dialog.findViewById(R.id.address);
                TextView Nominee_Relation = (TextView) dialog.findViewById(R.id.relation);
                TextView Dateof_Birth = (TextView) dialog.findViewById(R.id.dateofbirth);
                TextView Age = (TextView) dialog.findViewById(R.id.age);
               // Button  Delete = (Button) view.findViewById(R.id.delete);


               // setDataToView(Name, Address, Relation,Dateof_Birth,Age, position);
                setDataToView(Name_Nominee, Address_Nominee, Nominee_Relation, Dateof_Birth,Amount_Paidto_Nominee,NameAddress_ofGuardian, Age, position);

                //String Name, String Address, String Relation, String Dateof_Birth, String Age
                dialog.show();
            }
        };
    }*/



   /* public void Delete(View v) {
        int position = (Integer) v.getTag();
        openDialog(position);

    }*/

    public void openDialog(final int position) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                activity);

        // set title
        alertDialogBuilder.setTitle("Alert");
        // set dialog message
        alertDialogBuilder
                .setMessage("Do you really want to delete?")
                .setCancelable(true)

                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

//@ pass-var like this: doDownload(String url, String fileName.ext);

                                doDownload("https://www.ets.org/s/toefl/pdf/PBT_reg_form.pdf", "");
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

    protected void doDownload(final String urlLink, final String fileName) {
        Thread dx = new Thread() {

            public void run() {
            //    File root = android.os.Environment.getExternalStorageDirectory();
              /*  File dir = new File(root.getAbsolutePath() + "/Content2/");
                if(dir.exists()==false) {
                    dir.mkdirs();
                }*/
                File file = new File(Environment.getExternalStorageDirectory() + File.separator + "test.txt");

//create the file
                try {
                    file.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

             /*   if (root.canWrite()){
                    File fileDir = new File(root.getAbsolutePath()+"/fun/");
                    fileDir.mkdirs();

                    File file = new File(fileDir, "itisfun.txt");
                    FileWriter filewriter = new FileWriter(file);
                    BufferedWriter out = new BufferedWriter(filewriter);
                    out.write("I m enjoying......dude");
                    out.close();
                }*/

//text you want to write to your file
                String text = "your_text";

//check if file exists
                if(file.exists()) {
                    OutputStream fo = null;
                    try {
                        fo = new FileOutputStream(file);
                        //write the data
                       // fo.write(text);

                        //close to avoid memory leaks
                        fo.close();
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }


                    //give a log message that the file was created with "text"
                    System.out.println("file created: "+file);
                }

                //Save the path as a string value


                try {
                    URL url = new URL(urlLink);
                    Log.i("FILE_NAME", "File name is "+fileName);
                    Log.i("FILE_URLLINK", "File URL is "+url);
                    URLConnection connection = url.openConnection();
                    connection.connect();
                    // this will be useful so that you can show a typical 0-100% progress bar
                    int fileLength = connection.getContentLength();

                    // download the file
                    InputStream input = new BufferedInputStream(url.openStream());
                    OutputStream output = new FileOutputStream(file+"/"+fileName);


                    byte data[] = new byte[1024];
                    long total = 0;
                    int count;
                    while ((count = input.read(data)) != -1) {
                        total += count;

                        output.write(data, 0, count);
                    }

                    output.flush();
                    output.close();
                    input.close();
                } catch (Exception e) {
                    e.printStackTrace();
                    Log.i("ERROR ON DOWNLOADING FILES", "ERROR IS" +e);
                }
            }
        };
        dx.start();
    }



        /**
     * View holder to display each RecylerView item
     */
    protected class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
       private ImageView download,edit;
        TextView Month,Year,NetPay;
        private View container;

        public ViewHolder(View view) {
            super(view);
          //  imageView = (ImageView) view.findViewById(R.id.image);
            Month = (TextView) view.findViewById(R.id.loancode);
            Year = (TextView) view.findViewById(R.id.loandescription);
            NetPay = (TextView) view.findViewById(R.id.loandate);
            container = view.findViewById(R.id.card_viewpayinfo);
        }


    }
}
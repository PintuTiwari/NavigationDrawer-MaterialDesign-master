package br.liveo.ndrawer.ui.activity;

/**
 * Created by rupa.dwivedi on 09-03-2016.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class ShowAlert {


    public static void displayAlert(Activity act,String msg)
    {
        AlertDialog.Builder alert = new AlertDialog.Builder(act);
        alert.setMessage(msg).setPositiveButton("OK", new DialogInterface.OnClickListener(){
            @Override
            public void onClick(DialogInterface dialog, int which)
            {

            }
        })
                .show();
    }

}

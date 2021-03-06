package com.sax.inc.coetegaz.Utils;


import android.content.DialogInterface;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.sax.inc.coetegaz.R;

import androidx.appcompat.app.AppCompatActivity;
import viethoa.com.snackbar.TopSnackBarMessage;

public class UtilsToast {

    public  static void showToast(AppCompatActivity activity, String message, ETypeMessage typeMessage)
    {
        switch (typeMessage)
        {
            case ERROR:
                TopSnackBarMessage topSnackBarMessage_error = new TopSnackBarMessage(activity);
                topSnackBarMessage_error.showErrorMessage(message);

                break;

            case SUCCES:

                TopSnackBarMessage topSnackBarMessage_succes = new TopSnackBarMessage(activity);
                topSnackBarMessage_succes.showSuccessMessage(message);
                break;

            case WARNING:

                TopSnackBarMessage topSnackBarMessage_warring = new TopSnackBarMessage(activity);
                topSnackBarMessage_warring.showWarningMessage(message);
                break;
        }
    }

    public  static void showCFAlertDialog(AppCompatActivity activity, String title, String message, ETypeMessage typeMessage)
    {
        switch (typeMessage)
        {
            case ERROR:
                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(activity)
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                        .setTitle(title)
                        .setCancelable(false)
                        .setMessage(message)
                        .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.show(); // Show
                break;

            case SUCCES:

                CFAlertDialog.Builder builder_positive = new CFAlertDialog.Builder(activity)
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                        .setTitle(title)
                        .setCancelable(false)
                        .setMessage(message)
                        .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder_positive.show(); // Show
                break;
        }
    }


    public  static void showToastVolleyError(AppCompatActivity activity, String message)
    {

        if(activity.getResources().getString(R.string.volley_network_error).equals(message))
        {

            try {
                TopSnackBarMessage topSnackBarMessage = new TopSnackBarMessage(activity);
                topSnackBarMessage.showErrorMessage("Pas de connexion internet. Veuillez v??rifier vos param??tres de connexion internet. Merci");

            }
            catch (Exception e)
            {

            }
        }
        else if(activity.getResources().getString(R.string.volley_server_error).equals(message))
        {
            try {
                TopSnackBarMessage topSnackBarMessage = new TopSnackBarMessage(activity);
                topSnackBarMessage.showErrorMessage("Votre requ??te n'a pas pu abouti.Contactez l'administrateur technique. Merci");

            }
            catch (Exception e)
            {

            }

        }

        else if(activity.getResources().getString(R.string.volley_auth_error).equals(message))
        {
            try {
                TopSnackBarMessage topSnackBarMessage = new TopSnackBarMessage(activity);
                topSnackBarMessage.showErrorMessage("D??sol?? ! Vous n'??tes reconnu au syst??me.Contactez l'administrateur technique. Merci");

            }
            catch (Exception e)
            {

            }

        }
        else if(activity.getResources().getString(R.string.volley_parse_error).equals(message))
        {
            try {
                TopSnackBarMessage topSnackBarMessage = new TopSnackBarMessage(activity);
                topSnackBarMessage.showErrorMessage("Erreur de convertion de donn??es. Contactez l'administrateur technique. Merci");

            }
            catch (Exception e)
            {

            }

        }

        else if(activity.getResources().getString(R.string.volley_timeout_error).equals(message))
        {
            try {
                TopSnackBarMessage topSnackBarMessage = new TopSnackBarMessage(activity);
                topSnackBarMessage.showErrorMessage("D??sol?? ! Votre requ??te a d??pass?? le d??lai d'attente. Merci");

            }
            catch (Exception e)
            {

            }

        }

    }
}

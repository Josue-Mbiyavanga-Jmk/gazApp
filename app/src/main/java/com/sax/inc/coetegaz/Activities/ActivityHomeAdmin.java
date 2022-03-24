package com.sax.inc.coetegaz.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.sax.inc.coetegaz.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.interpolator.view.animation.FastOutSlowInInterpolator;

public class ActivityHomeAdmin extends AppCompatActivity {

    private ImageView BtLogout,BtNewShop,BtNewAgent;
    private LinearLayout contenaireLogout,contenaireNewShop,
            contenaireNewAgent;
    private TextView link_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_admin);

        initialiseWidget();
        event();

    }


    @Override
    public void onBackPressed() {
        logOut();
    }

    private void logOut() {

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityHomeAdmin.this)
                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                .setTitle("Infos")
                .setCancelable(true)
                .setMessage("Voulez-vous vraiment quitter l'application ?\n\nEn quittant sur 'Quitter et déconnecter' votre session d'utilisateur sera déconnectée.")
                .addButton("Quitter et déconnecter", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                       /* boolean b=  UtilsFirebase.isDestroySession();
                        if(b) {
                            finish();
                        }*/
                    }
                })
                .addButton("Quitter et garder sesssion", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                })
                .addButton("Résuire l'application", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        finish();
                    }
                });


        builder.show(); // Show
    }

    private void initialiseWidget() {
        contenaireNewAgent=findViewById(R.id.contenaireNewAgent);
        contenaireNewShop=findViewById(R.id.contenaireNewShop);
        contenaireLogout=findViewById(R.id.contenaireLogout);


        BtNewShop=findViewById(R.id.BtNewShop);
        BtNewAgent=findViewById(R.id.BtNewAgent);
        BtLogout=findViewById(R.id.BtLogout);
        link_about=findViewById(R.id.link_about);

    }

    private  void event()
    {
        BtNewShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ActivityHomeAdmin.this, ActivityAgenceAdd.class);
                startActivity(go);
            }
        });

        BtNewAgent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ActivityHomeAdmin.this, ActivityAgentAdd.class);
                startActivity(go);
            }
        });

        link_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ActivityHomeAdmin.this, ActivityAbout.class);
                startActivity(go);
            }
        });


        BtLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               logOut();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        scaleViewAnimation(contenaireNewAgent,100);
        scaleViewAnimation(contenaireNewShop,200);
        scaleViewAnimation(contenaireLogout,300);
    }

    private void scaleViewAnimation(View view, int startDelay){
        // Reset view
        view.setScaleX(0);
        view.setScaleY(0);
        // Animate view
        view.animate()
                .scaleX(1f)
                .scaleY(1f)
                .setInterpolator(new FastOutSlowInInterpolator())
                .setStartDelay(startDelay)
                .setDuration(500)
                .start();
    }


}

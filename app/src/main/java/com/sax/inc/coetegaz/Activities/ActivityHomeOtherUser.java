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

public class ActivityHomeOtherUser extends AppCompatActivity {

    private ImageView BtLogout,BtMouv,BtStat,BtLocaliseAgence,BtRecharge,BtNewAbonne;
    private LinearLayout contenaireLogout,contenaireNewStat,
            contenaireMouv,contenaireLocaliseAgence,contenaireRechargeGaz,
            contenaireNewAbonne;
    private TextView link_about;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_other_user);

        initialiseWidget();
        event();

    }


    @Override
    public void onBackPressed() {
        logOut();
    }

    private void logOut() {

        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityHomeOtherUser.this)
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
        contenaireNewAbonne=findViewById(R.id.contenaireNewAbonne);
        contenaireRechargeGaz=findViewById(R.id.contenaireRechargeGaz);
        contenaireLocaliseAgence=findViewById(R.id.contenaireLocaliseAgence);
        contenaireMouv=findViewById(R.id.contenaireMouv);
        contenaireNewStat=findViewById(R.id.contenaireNewStat);
        contenaireLogout=findViewById(R.id.contenaireLogout);


        BtNewAbonne=findViewById(R.id.BtNewAbonne);
        BtRecharge=findViewById(R.id.BtRecharge);
        BtLocaliseAgence=findViewById(R.id.BtLocaliseAgence);
        BtMouv=findViewById(R.id.BtMouv);
        BtStat=findViewById(R.id.BtStat);
        BtLogout=findViewById(R.id.BtLogout);

        link_about=findViewById(R.id.link_about);

    }

    private  void event()
    {
        BtNewAbonne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ActivityHomeOtherUser.this, ActivityClientAdd.class);
                startActivity(go);
            }
        });

        BtRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ActivityHomeOtherUser.this, ActivityClientSearch.class);
                startActivity(go);
            }
        });

        BtLocaliseAgence.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ActivityHomeOtherUser.this, ActivityAgenceRecherche.class);
                startActivity(go);
            }
        });

        BtMouv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ActivityHomeOtherUser.this, ActivityMouvementList.class);
                startActivity(go);
            }
        });

        BtStat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ActivityHomeOtherUser.this, ActivityStatOtherUserMenuPrincipal.class);
                startActivity(go);
            }
        });

        link_about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ActivityHomeOtherUser.this, ActivityAbout.class);
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

        scaleViewAnimation(contenaireNewAbonne,100);
        scaleViewAnimation(contenaireRechargeGaz,200);
        scaleViewAnimation(contenaireLocaliseAgence,300);

        scaleViewAnimation(contenaireMouv,400);
        scaleViewAnimation(contenaireNewStat,500);
        scaleViewAnimation(contenaireLogout,600);

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

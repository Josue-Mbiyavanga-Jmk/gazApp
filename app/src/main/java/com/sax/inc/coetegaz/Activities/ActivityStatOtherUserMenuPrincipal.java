package com.sax.inc.coetegaz.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.sax.inc.coetegaz.Memory.Constant;
import com.sax.inc.coetegaz.R;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityStatOtherUserMenuPrincipal extends AppCompatActivity {

    private ImageView bt_back;
    private RelativeLayout containe_stat_recharge,containe_stat_entree,containe_stat_abonnement;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stat_menu_other_user);
        initialiseWidgets();
        boxEvent();

    }


    private void initialiseWidgets() {
        containe_stat_abonnement =  findViewById(R.id.containe_stat_abonnement);
        containe_stat_entree =  findViewById(R.id.containe_stat_entree);
        containe_stat_recharge =  findViewById(R.id.containe_stat_recharge);
        bt_back =  findViewById(R.id.bt_back);


    }

    private void boxEvent() {

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        containe_stat_abonnement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ActivityStatOtherUserMenuPrincipal.this, ActivityStatistiqueUser.class);
                go.putExtra("TYPE", Constant.ABONNEMENT);
                startActivity(go);
            }
        });
        containe_stat_recharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent go = new Intent(ActivityStatOtherUserMenuPrincipal.this, ActivityStatistiqueUser.class);
                go.putExtra("TYPE", Constant.RECHARGE);
                startActivity(go);
            }
        });
        containe_stat_entree.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ActivityStatOtherUserMenuPrincipal.this, ActivityStatistiqueUser.class);
                go.putExtra("TYPE", Constant.INPUT);
                startActivity(go);
            }
        });

    }

}

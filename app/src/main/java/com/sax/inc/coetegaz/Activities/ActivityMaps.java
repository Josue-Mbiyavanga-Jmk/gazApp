package com.sax.inc.coetegaz.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sax.inc.coetegaz.Fragments.FragCarte;
import com.sax.inc.coetegaz.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class ActivityMaps extends AppCompatActivity {

    public  static FragmentTransaction transaction;
    private FloatingActionButton BtFlotList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_carte);

        initialiseWidgets();
        boxEvent();

                setTitle("Les agences");
                transaction = getSupportFragmentManager().beginTransaction().replace(R.id.ConteneurBase, FragCarte.newInstance());
                transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
                transaction.commit();
    }

    private void boxEvent() {

        BtFlotList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityMaps.this, ActivityAgenceListSearch.class);
                startActivity(intent);
            }
        });

    }
    private void initialiseWidgets() {
        BtFlotList =  findViewById(R.id.BtFlotList);

    }

}

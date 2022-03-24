package com.sax.inc.coetegaz.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sax.inc.coetegaz.Dao.MouvementStockDao;
import com.sax.inc.coetegaz.Fragments.FragMouvementEmpty;
import com.sax.inc.coetegaz.Fragments.FragMouvementList;
import com.sax.inc.coetegaz.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class ActivityMouvementList extends AppCompatActivity {


    private FloatingActionButton BtFlotNew;
    public  static FragmentTransaction transaction;
    private int position=0; // gère le positionnement des fragment après un backpressed de l'activité précédente


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouvement_list);
        initialiseWidgets();
        boxEvent();

        setTitle("Les mouvements de stock");
    }


    private void initialiseWidgets() {
        BtFlotNew =  findViewById(R.id.BtFlotNew);

    }

    private void boxEvent() {

      BtFlotNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ActivityMouvementList.this, ActivityMouvementAdd.class);
                startActivity(intent);
            }
        });

    }

    private void loadFocus()
    {

        long count= MouvementStockDao.count();

        if(count == 0L)
        {
            transaction = getSupportFragmentManager().beginTransaction().replace(R.id.ConteneurBase, FragMouvementEmpty.newInstance());
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            transaction.commit();
        }
        else
        {
            transaction = getSupportFragmentManager().beginTransaction().replace(R.id.ConteneurBase, FragMouvementList.newInstance());
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            transaction.commit();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFocus();
    }
}

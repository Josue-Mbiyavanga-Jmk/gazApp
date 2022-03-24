package com.sax.inc.coetegaz.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sax.inc.coetegaz.Dao.AgenceDao;
import com.sax.inc.coetegaz.Fragments.FragAgenceEmpty;
import com.sax.inc.coetegaz.Fragments.FragAgenceList;
import com.sax.inc.coetegaz.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class ActivityAgenceAdd extends AppCompatActivity {

    public  static FragmentTransaction transaction;
    private FloatingActionButton BtFlotNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        initView();
        event();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadFocus();
    }

    private void initView() {
        setTitle("Les agences");
        BtFlotNew =  findViewById(R.id.BtFlotNew);
    }

    private void event() {

        BtFlotNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ActivityAgenceAdd.this,ActivityAgenceRegister.class);
                startActivity(go);

            }
        });

    }

    private void loadFocus()
    {
        long count= AgenceDao.count();

        if(count== 0L)
        {
            transaction = getSupportFragmentManager().beginTransaction().replace(R.id.ConteneurBase, FragAgenceEmpty.newInstance());
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            transaction.commit();
        }
        else
        {
            transaction = getSupportFragmentManager().beginTransaction().replace(R.id.ConteneurBase, FragAgenceList.newInstance());
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            transaction.commit();
        }
    }
}

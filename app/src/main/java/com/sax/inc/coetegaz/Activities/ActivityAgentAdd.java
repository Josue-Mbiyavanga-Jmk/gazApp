package com.sax.inc.coetegaz.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sax.inc.coetegaz.Dao.UserDao;
import com.sax.inc.coetegaz.Fragments.FragAgentEmpty;
import com.sax.inc.coetegaz.Fragments.FragAgentList;
import com.sax.inc.coetegaz.R;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;

public class ActivityAgentAdd extends AppCompatActivity {

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
        setTitle("Les agents");
        BtFlotNew =  findViewById(R.id.BtFlotNew);
    }

    private void event() {

        BtFlotNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ActivityAgentAdd.this,ActivityAgentRegister.class);
                startActivity(go);

            }
        });

    }

    private void loadFocus()
    {
        long count= UserDao.count();

        if(count== 0L)
        {
            transaction = getSupportFragmentManager().beginTransaction().replace(R.id.ConteneurBase, FragAgentEmpty.newInstance());
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            transaction.commit();
        }
        else
        {
            transaction = getSupportFragmentManager().beginTransaction().replace(R.id.ConteneurBase, FragAgentList.newInstance());
            transaction.setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            transaction.commit();
        }
    }
}

package com.sax.inc.coetegaz.Activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.gms.maps.model.LatLng;
import com.sax.inc.coetegaz.Adaptaters.RecylerAdapterListAgence;
import com.sax.inc.coetegaz.Dao.AgenceDao;
import com.sax.inc.coetegaz.Dao.CommuneDao;
import com.sax.inc.coetegaz.Dao.QuartierDao;
import com.sax.inc.coetegaz.Entites.EAgence;
import com.sax.inc.coetegaz.Entites.ECommune;
import com.sax.inc.coetegaz.Entites.EQuartier;
import com.sax.inc.coetegaz.R;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class ActivityAgencesList extends AppCompatActivity implements RecylerAdapterListAgence.ItemButtonListener {


    public  static FragmentTransaction transaction;
    private int Focus=0;
    private ImageView bt_back;
    private List lit;

    private RecyclerView recycler_view;
    private RecylerAdapterListAgence mAdapter;
    private List<EAgence> list_items;
    private RelativeLayout contenaireHelp;

    int count =0;
    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frag_list_agence_item);
        setTitle("Liste des agences");
        initWidget();

    }


    @Override
    public void onResume() {
        list_items.clear();
        list_items.addAll(AgenceDao.getAllLoad());

        if(list_items.size()==0){
            contenaireHelp.setVisibility(View.VISIBLE);
            recycler_view.setVisibility(View.GONE);

        }else{
            contenaireHelp.setVisibility(View.GONE);
            recycler_view.setVisibility(View.VISIBLE);
            mAdapter.notifyDataSetChanged();

        }
        mAdapter.notifyDataSetChanged();
        super.onResume();
    }

    void initWidget()
    {
        list_items=new ArrayList<>();
        recycler_view =  findViewById(R.id.generique_recyclerview);
        contenaireHelp =  findViewById(R.id.contenaireHelp);

        mAdapter = new RecylerAdapterListAgence(ActivityAgencesList.this, list_items,this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(ActivityAgencesList.this);
        recycler_view.setLayoutManager(mLayoutManager);
        recycler_view.setItemAnimator(new DefaultItemAnimator());
        recycler_view.setAdapter(mAdapter);
    }



    @Override
    public void onCallClickListener(int position) {
        final EAgence o = list_items.get(position);
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:"+o.getTelephone()));
        startActivity(intent);
    }

    @Override
    public void onSharLocaliseClickListener(int position) {
        final EAgence o = list_items.get(position);

        StringBuilder uri=new StringBuilder();

        EQuartier eQuartier= QuartierDao.get(o.getQuartierref());
        ECommune eCommune= CommuneDao.get(o.getCommuneref());

        String adress="N° " +o.getNumero()+" Avenue : "+
                o.getAvenue()+" Q/ "+eQuartier.getName()+
                " C/ "+eCommune.getName();

        uri.append("Agence de "+ o.getName());
        uri.append("\nTélephone : "+ o.getTelephone());
        uri.append("\nRéférence : "+ o.getReflieu());
        uri.append("\nAdresse : "+ adress+"\n\n");

        String coordonnee = "";
        coordonnee = o.getGps();

        String[] tab=coordonnee.split("#");

        LatLng marker=new LatLng(Double.valueOf(tab[0]),Double.valueOf(tab[1]));


        Double latitude = marker.latitude;
        Double longitude = marker.longitude;

        String gps_coordonnees = "http://maps.google.com/maps?saddr=" +latitude+","+longitude;

        uri.append(gps_coordonnees);

        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String ShareSub = "Selectionnez une application";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, ShareSub);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, uri.toString());
        startActivity(Intent.createChooser(sharingIntent, "Partage via"));
    }

    @Override
    public void onSharLocaliseGPSClickListener(int position) {
        final EAgence o = list_items.get(position);

        String coordonnee = "";
        coordonnee = o.getGps();

        String[] tab=coordonnee.split("#");

        LatLng marker=new LatLng(Double.valueOf(tab[0]),Double.valueOf(tab[1]));


        Double latitude = marker.latitude;
        Double longitude = marker.longitude;

        String uri = "http://maps.google.com/maps?saddr=" +latitude+","+longitude;
        //Url https://maps.googleapis.com/maps/api/staticmap?center=Australia&size=640x400&style=element:labels|visibility:off&style=element:geometry.stroke|visibility:off&style=feature:landscape|element:geometry|saturation:-100&style=feature:water|saturation:-100|invert_lightness:true&key=YOUR_API_KEY
        Intent sharingIntent = new Intent(Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        String ShareSub = "Selectionnez une application";
        sharingIntent.putExtra(Intent.EXTRA_SUBJECT, ShareSub);
        sharingIntent.putExtra(Intent.EXTRA_TEXT, uri);
        startActivity(Intent.createChooser(sharingIntent, "Partage via"));
    }

    @Override
    public void onItemClickListener(int position) {


    }



}

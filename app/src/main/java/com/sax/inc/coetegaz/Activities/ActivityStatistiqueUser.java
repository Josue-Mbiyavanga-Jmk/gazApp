package com.sax.inc.coetegaz.Activities;

import androidx.appcompat.app.AppCompatActivity;
import customfonts.MyTextView_Roboto_Regular;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.sax.inc.coetegaz.Dao.MouvementStockDao;
import com.sax.inc.coetegaz.Entites.EMouvementStock;
import com.sax.inc.coetegaz.Memory.Constant;
import com.sax.inc.coetegaz.R;

import java.util.ArrayList;
import java.util.List;

public class ActivityStatistiqueUser extends AppCompatActivity {

    private PieChart pieChart;
    private BarChart barChart;
    private ImageView bt_back;
    private MyTextView_Roboto_Regular txt_total,txt_total_second,help,txt_bouteille1,txt_bouteille2,description;
    private TextView txt_title;
    private ArrayList<String> produits;
    private  ArrayList<Integer> colors;
    private ArrayList<BarEntry> yValues;
    private ArrayList<Entry> yValue;
    private int total=0;
    private String TYPE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistique_user);
        initWidgets();
        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            TYPE=bundle.getString("TYPE");
        }
        loadChart();
    }

    @Override
    protected void onStart() {
        super.onStart();
        //appel des méthodes
        event();
        piechartMethod();
        barChartMethod();
    }

    private void initWidgets() {
        pieChart = findViewById(R.id.PieChart);
        barChart = findViewById(R.id.BarChart);
        txt_total = findViewById(R.id.txt_total);
        txt_title = findViewById(R.id.txt_title);
        bt_back = findViewById(R.id.bt_back);
        txt_total_second = findViewById(R.id.txt_total_second);
        help = findViewById(R.id.help);
        txt_bouteille1 = findViewById(R.id.txt_bouteille1);
        txt_bouteille2 = findViewById(R.id.txt_bouteille2);
        description = findViewById(R.id.description);

        //groupe list
        produits = new ArrayList<>();
        produits.add("6Kg");
        produits.add("9Kg");
        produits.add("12Kg");

        colors = new ArrayList<>();
      /*  colors.add(getResources().getColor(R.color.android_rose));
        colors.add(getResources().getColor(R.color.NewBlueBis1));
        colors.add(getResources().getColor(R.color.android_violet_dark)); */
        colors.add(getResources().getColor(R.color.android_orange_dark));
        colors.add(getResources().getColor(R.color.TwitterColor));
        colors.add(getResources().getColor(R.color.BgVert));
       /* colors.add(getResources().getColor(R.color.android_red));
        colors.add(getResources().getColor(R.color.fbutton_color_turquoise)); */
    }

    private void loadChart(){
        //les entrées de 2 graphiques
        yValue = new ArrayList<>();
        yValues = new ArrayList<>();
        //mettre les valeurs de quantité de chaque groupe
        List<EMouvementStock> mes_mouvement;
        if(TYPE.equals(Constant.ABONNEMENT)){
            txt_title.setText("Statistique des abonnements");
            String help_txt = getResources().getString(R.string.helps_stat);
            String help_txt_poche = getResources().getString(R.string.help_bouteilles);
            String help_txt_poche_legend = getResources().getString(R.string.help_bouteille_legend);
            help.setText(String.format(help_txt, "abonnements"));
            txt_bouteille1.setText(String.format(help_txt_poche, "achetées"));
            txt_bouteille2.setText(String.format(help_txt_poche, "achetées"));
            description.setText(String.format(help_txt_poche_legend, "achetées"));
            mes_mouvement= MouvementStockDao.getMouvementByProduitAndType(Constant.ABONNEMENT);

        }else if(TYPE.equals(Constant.RECHARGE)){
            txt_title.setText("Statistique des recharges");
            String help_txt = getResources().getString(R.string.helps_stat);
            String help_txt_poche = getResources().getString(R.string.help_bouteilles);
            String help_txt_poche_legend = getResources().getString(R.string.help_bouteille_legend);
            help.setText(String.format(help_txt, "recharges"));
            txt_bouteille1.setText(String.format(help_txt_poche, "rechargées"));
            txt_bouteille2.setText(String.format(help_txt_poche, "rechargées"));
            description.setText(String.format(help_txt_poche_legend, "rechargées"));
            mes_mouvement= MouvementStockDao.getMouvementByProduitAndType(Constant.RECHARGE);

        }else{
            txt_title.setText("Statistique des entrées");
            String help_txt = getResources().getString(R.string.helps_stat);
            String help_txt_poche = getResources().getString(R.string.help_bouteilles);
            String help_txt_poche_legend = getResources().getString(R.string.help_bouteille_legend);
            help.setText(String.format(help_txt, "entrées"));
            txt_bouteille1.setText(String.format(help_txt_poche, "entrées"));
            txt_bouteille2.setText(String.format(help_txt_poche, "entrées"));
            description.setText(String.format(help_txt_poche_legend, "entrées"));
            mes_mouvement= MouvementStockDao.getMouvementByProduitAndType(Constant.INPUT);

        }
        int index=0;
        for (EMouvementStock mouvementStock:mes_mouvement){
            yValue.add(new BarEntry(mouvementStock.getQuantite(),index));
            yValues.add(new BarEntry(mouvementStock.getQuantite(),index));
            total = total + mouvementStock.getQuantite();
            index++;
        }
    }

    private void piechartMethod(){
        String type="";
        String typeSecond="";
        if(TYPE.equals(Constant.ABONNEMENT)){
            type="achetées";
            typeSecond="achat";
        }else if(TYPE.equals(Constant.INPUT)){
            type="entrées";
            typeSecond="entrée";
        }else if(TYPE.equals(Constant.RECHARGE)){
            type="rechargées";
            typeSecond="recharge";
        }
        // création du dataset
        PieDataSet pieDataSet = new PieDataSet(yValue,"indice");
        pieDataSet.setSliceSpace(3f);
        pieDataSet.setSelectionShift(5f);
        pieDataSet.setValueTextSize(12);
        pieDataSet.setColors(colors);
        //data of chart
        PieData pieData = new PieData(produits,pieDataSet);
        pieData.setValueTextSize(12f);
        pieData.setValueTextColor(Color.YELLOW);
        //chart config
        pieChart.setData(pieData);
        pieChart.setUsePercentValues(true);
        pieChart.setCenterText("Stat / "+typeSecond+"s");
        pieChart.setExtraOffsets(5,10,5,5);
        pieChart.setDragDecelerationFrictionCoef(0.95f);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);
        pieChart.setTransparentCircleRadius(41f);
        pieChart.setTransparentCircleAlpha(25);
        pieChart.setDescription("Nombre de bouteilles "+type+" par produit en %");
        pieChart.animateY(2000, Easing.EasingOption.EaseInOutCubic);
        //chart event : pas mis!!!
        //mise à jour du total
        txt_total.setText(total+"");

    }

    private void barChartMethod(){
        //création du dataset
        BarDataSet dataSet = new BarDataSet(yValues,"");
        dataSet.setColors(colors);
        //ce qui sera affiché en haut du chart
        ArrayList<String> produitCategorie = new ArrayList<>();
        produitCategorie.add("6Kg");
        produitCategorie.add("9Kg");
        produitCategorie.add("12Kg");

        //data
        BarData data = new BarData(produitCategorie,dataSet);
        //chart config
        barChart.setData(data);
        barChart.setDescription(" ");
        barChart.setTouchEnabled(false);
        barChart.animateY(5000);
        Legend legend = barChart.getLegend();
        legend.setForm(Legend.LegendForm.CIRCLE);
        legend.setCustom(colors,produits);
        legend.setPosition(Legend.LegendPosition.BELOW_CHART_LEFT);
        //chart event : pas mis!!!
        //mise à jour du total
        txt_total_second.setText(total+"");
    }

    private void event(){
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }
}

package com.sax.inc.coetegaz.Activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.sax.inc.coetegaz.App.AppController;
import com.sax.inc.coetegaz.Dao.AgenceDao;
import com.sax.inc.coetegaz.Dao.CommuneDao;
import com.sax.inc.coetegaz.Dao.ProvinceDao;
import com.sax.inc.coetegaz.Dao.TypeProduitDao;
import com.sax.inc.coetegaz.Dao.VilleDao;
import com.sax.inc.coetegaz.Entites.EAgence;
import com.sax.inc.coetegaz.Entites.ECommune;
import com.sax.inc.coetegaz.Entites.EProvince;
import com.sax.inc.coetegaz.Entites.EServeur;
import com.sax.inc.coetegaz.Entites.ETypeProduit;
import com.sax.inc.coetegaz.Entites.EVille;
import com.sax.inc.coetegaz.NetWork.HttpRequest;
import com.sax.inc.coetegaz.R;
import com.sax.inc.coetegaz.Utils.GPS;
import com.sax.inc.coetegaz.Utils.HttpCallbackString;
import com.sax.inc.coetegaz.Utils.UtilEServeur;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import customfonts.MyTextView_Roboto_Regular;

public class ActivityAgenceRecherche extends AppCompatActivity {

    private MyTextView_Roboto_Regular BtSearch;
    private SpinKitView progress_load;
    private SearchableSpinner spinner_province,spinner_ville,spinner_commune,
            spinner_type_produit;
    private String province="",provinceid="",ville="",villeid="",commune="", communeid="", type_produit = "", groupeid="";
    private List<String> elementGroupe, elementProvince,elementVille,elementCommune;
    private List<ETypeProduit> typeProduitList;
    private List<ECommune> communeList;
    private List<EVille> villeList;
    private List<EProvince> provinceList;
    private EServeur serveur;
    private List<EAgence> deleteAgence;
    private FloatingActionButton BtFlotList;
    private boolean gps_active=false;


    private BroadcastReceiver gpsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().matches(LocationManager.PROVIDERS_CHANGED_ACTION)) {
                //GPS status change
                if(!checkGps())
                {
                    //createGpsDisabledAlert();
                    gps_active=false;
                }
                else
                {
                    gps_active=true;
                    AppController global=(AppController) (ActivityAgenceRecherche.this.getApplicationContext());
                    global.gp=new GPS(ActivityAgenceRecherche.this);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agence_search);
        initView();

        try {
            registerReceiver(gpsReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
        }catch (Exception e){

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        BtSearch.setEnabled(true);
        if(!checkGps())
        {
            //createGpsDisabledAlert();
            gps_active=false;
        }
        else
        {
            gps_active=true;
            AppController global=(AppController) (ActivityAgenceRecherche.this.getApplicationContext());
            global.gp=new GPS(ActivityAgenceRecherche.this);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        event();
        load();
    }



    private void initView(){

        setTitle("Recherche");
        BtSearch = findViewById(R.id.BtSearch);
        spinner_province = findViewById(R.id.spinner_province);
        spinner_ville = findViewById(R.id.spinner_ville);
        spinner_commune = findViewById(R.id.spinner_commune);
        spinner_type_produit = findViewById(R.id.spinner_type_produit);
        progress_load = findViewById(R.id.spin_kit_load);
        BtFlotList = findViewById(R.id.BtFlotList);
        progress_load.setVisibility(View.GONE);
        
        deleteAgence=new ArrayList<>();
      
    }

    private void load()
    {
        //chargement des spinners

        provinceList=new ArrayList<>();
        elementProvince =new ArrayList<>();
        provinceList = ProvinceDao.getAll();
        for(EProvince o:provinceList){
            elementProvince.add(o.getName());
        }

        refresh(spinner_province,elementProvince);
        
        villeList=new ArrayList<>();
        elementVille =new ArrayList<>();
        refresh(spinner_ville,elementVille);
      
        communeList=new ArrayList<>();
        elementCommune =new ArrayList<>();
        refresh(spinner_commune,elementCommune);
        //
        typeProduitList =new ArrayList<>();
        elementGroupe =new ArrayList<>();
        typeProduitList = TypeProduitDao.getAll();
        for(ETypeProduit o: typeProduitList){
            elementGroupe.add(o.getName());
        }

        refresh(spinner_type_produit,elementGroupe);
    }

    private void refresh(SearchableSpinner spinner, List<String> list) {
        ArrayAdapter<String> adp = new ArrayAdapter<String>(ActivityAgenceRecherche.this,android.R.layout.simple_dropdown_item_1line,list);
        spinner.setAdapter(adp);

    }

    private void event(){
        //
        BtSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(gps_active){
                    if(provinceid.equals("") && villeid.equals("") && communeid.equals("") && type_produit.equals("")){

                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgenceRecherche.this)
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                .setTitle("Attention!")
                                .setCancelable(false)
                                .setMessage("Aucun élément selectionné pour la recherche. Veuillez choisir une ou plusieurs entités administratives avec un type produit.")
                                .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        builder.show(); // Show
                    }
                    else if(provinceid.equals("") &&  !type_produit.equals("")){

                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgenceRecherche.this)
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                .setTitle("Attention!")
                                .setCancelable(false)
                                .setMessage("Vous devez toujours selectionner au moins une entité administrative, combinée  avec un type produit.")
                                .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        builder.show(); // Show
                    }
                    else if(!provinceid.equals("") && type_produit.equals("")){

                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgenceRecherche.this)
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                .setTitle("Attention!")
                                .setCancelable(false)
                                .setMessage("Vous devez selectionner une bouteille avec laquelle la recherche doit se faire.")
                                .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        builder.show(); // Show
                    }
                    else {
                        //progression
                        progress_load.setVisibility(View.VISIBLE);
                        //
                        BtSearch.setEnabled(false);
                        //
                        serveur = UtilEServeur.getServeur();

                        //test de champs non vide pour la recherche
                        if (!provinceid.equals("") && villeid.equals("") && communeid.equals("") ) {
                            //http searchByProvinceTypeProduit
                            searchByProvinceTypeProduit();
                        } else if (!provinceid.equals("") && !villeid.equals("") && communeid.equals("")) {
                            //http searchByVilleTypeProduit
                            searchByVilleTypeProduit();
                        }
                        else if (!communeid.equals("")) {
                            //http searchByCommuneTypeProduit
                            searchByCommuneTypeProduit();

                        }


                    }
                }else{
                    createGpsDisabledAlert();
                }


            }
        });

      //evenement de selection
        spinner_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                province=spinner_province.getSelectedItem().toString();

                //remplir ville
                EProvince eProvince = ProvinceDao.get(province);
                provinceid = String.valueOf(eProvince.getId_native());
                //rendre toujours les villes et communes dépendante de Province
                elementVille.clear();
                elementCommune.clear();
                //add prompt
                elementVille.add("-- Ville -- *");
                elementCommune.add("-- Commune -- *");
                //
                villeList.clear();
                villeList = VilleDao.getByProvince(eProvince.getId_native());
                for(EVille o:villeList){
                    elementVille.add(o.getName());
                }
                //
                refresh(spinner_ville,elementVille);
                refresh(spinner_commune,elementCommune);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //
        spinner_ville.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ville=spinner_ville.getSelectedItem().toString();
                //remplir commune
                if(ville.equals("-- Ville -- *")){
                    villeid ="";
                }
                else {

                    EVille eVille = VilleDao.get(ville);
                    villeid = String.valueOf(eVille.getId_native());
                    //
                    communeList.clear();
                    communeList = CommuneDao.getByVille(eVille.getId_native());
                    for(ECommune o:communeList){
                        elementCommune.add(o.getName());
                    }

                    refresh(spinner_commune,elementCommune);
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //
        spinner_commune.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                commune=spinner_commune.getSelectedItem().toString();
                //
                if(commune.equals("-- Commune -- *")){
                    communeid ="";
                }
                else {

                    ECommune eCommune = CommuneDao.get(commune);
                    communeid = String.valueOf(eCommune.getId_native());
                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //
        spinner_type_produit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                type_produit = spinner_type_produit.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        BtFlotList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent go = new Intent(ActivityAgenceRecherche.this,ActivityAgencesList.class);
                startActivity(go);
            }
        });
    }

    private void goToNextActivity() {

        Intent go = new Intent(ActivityAgenceRecherche.this,ActivityMaps.class);
        startActivity(go);
        finish();
    }

    private void searchByTypeProduit(){
        //
        HttpRequest.getAgenceByTypeProduit(ActivityAgenceRecherche.this, serveur, new String[]{type_produit}, new HttpCallbackString() {
            @Override
            public void onSuccess(String response) {
                try{

                    JSONObject jsonObject = new JSONObject(response);
                    int result = jsonObject.getInt("result");
                    if(result == 200) {

                        //suppression des éléments de l'ancienne recherche avant la nouvelle
                        deleteAgence.addAll(AgenceDao.getAllSearch());
                        for (EAgence donnor:deleteAgence){
                            AgenceDao.delete(donnor.getId());
                        }
                        //
                        JSONArray array = jsonObject.getJSONArray("data");
                        for(int i=0;i<array.length();i++){

                            JSONObject object = array.getJSONObject(i);
                            EAgence agence =  new Gson().fromJson(object.toString(),EAgence.class);
                            //status 0 pour les autres agences
                            agence.setStatus(0);
                            //sauvegarde
                            AgenceDao.create(agence);
                        }
                        //arret de la progression
                        progress_load.setVisibility(View.GONE);
                        //
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgenceRecherche.this)
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                .setTitle("Succès")
                                .setCancelable(false)
                                .setMessage("Recherche terminée avec succès et la liste des agences est prête.")
                                .addButton("Continuer", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        //arret du message
                                        dialog.dismiss();
                                        //changer d'écran
                                        goToNextActivity();

                                    }
                                });

                        builder.show(); // Show


                    }
                    else {
                        //
                        BtSearch.setEnabled(true);
                        //
                        progress_load.setVisibility(View.GONE);
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgenceRecherche.this)
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                .setTitle("Infos")
                                .setCancelable(false)
                                .setMessage("Aucune agence qui réponde à vos critères de recherche n'a été trouvé!")
                                .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        builder.show(); // Show
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message) {
                //
                BtSearch.setEnabled(true);
                progress_load.setVisibility(View.GONE);
                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgenceRecherche.this)
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                        .setTitle("Echec")
                        .setCancelable(false)
                        .setMessage("Recherche non terminée. Vérifiez votre connexion pour plus de sûreté et de rapidité lors de la recherche.")
                        .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.show(); // Show
            }
        });
    }

    private void searchByCommuneTypeProduit(){
        //
        HttpRequest.getCommuneTypeProduitAgence(ActivityAgenceRecherche.this, serveur, new String[]{communeid, type_produit}, new HttpCallbackString() {
            @Override
            public void onSuccess(String response) {
                try{

                    JSONObject jsonObject = new JSONObject(response);
                    int result = jsonObject.getInt("result");
                    if(result == 200) {

                        //suppression des éléments de l'ancienne recherche avant la nouvelle
                        deleteAgence.addAll(AgenceDao.getAllSearch());
                        for (EAgence donnor:deleteAgence){
                            AgenceDao.delete(donnor.getId());
                        }
                        //
                        JSONArray array = jsonObject.getJSONArray("data");
                        for(int i=0;i<array.length();i++){

                            JSONObject object = array.getJSONObject(i);
                            EAgence agence =  new Gson().fromJson(object.toString(),EAgence.class);
                            //status 0 pour les autres agences
                            agence.setStatus(0);
                            //sauvegarde
                            AgenceDao.create(agence);
                        }
                        //arret de la progression
                        progress_load.setVisibility(View.GONE);
                        //
                        goToNextActivity();

                    }
                    else {
                        //
                        BtSearch.setEnabled(true);
                        //
                        progress_load.setVisibility(View.GONE);
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgenceRecherche.this)
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                .setTitle("Infos")
                                .setCancelable(false)
                                .setMessage("Aucune agence qui réponde à vos critères de recherche n'a été trouvé!")
                                .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        builder.show(); // Show
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message) {
                //
                BtSearch.setEnabled(true);
                progress_load.setVisibility(View.GONE);
                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgenceRecherche.this)
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                        .setTitle("Echec")
                        .setCancelable(false)
                        .setMessage("Recherche non terminée. Vérifiez votre connexion pour plus de sûreté et de rapidité lors de la recherche.")
                        .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.show(); // Show
            }
        });
    }

    private void searchByCommune(){
        //
        HttpRequest.getCommuneAgence(ActivityAgenceRecherche.this, serveur, new String[]{communeid}, new HttpCallbackString() {
            @Override
            public void onSuccess(String response) {
                try{

                    JSONObject jsonObject = new JSONObject(response);
                    int result = jsonObject.getInt("result");
                    if(result == 200) {

                        //suppression des éléments de l'ancienne recherche avant la nouvelle
                        deleteAgence.addAll(AgenceDao.getAllSearch());
                        for (EAgence donnor:deleteAgence){
                            AgenceDao.delete(donnor.getId());
                        }
                        //
                        JSONArray array = jsonObject.getJSONArray("data");
                        for(int i=0;i<array.length();i++){

                            JSONObject object = array.getJSONObject(i);
                            EAgence agence =  new Gson().fromJson(object.toString(),EAgence.class);
                            //status 0 pour les autres agences
                            agence.setStatus(0);
                            //sauvegarde
                            AgenceDao.create(agence);

                        }
                        //arret de la progression
                        progress_load.setVisibility(View.GONE);
                        //
                        goToNextActivity();


                    }
                    else {
                        //
                        BtSearch.setEnabled(true);
                        //
                        progress_load.setVisibility(View.GONE);
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgenceRecherche.this)
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                .setTitle("Infos")
                                .setCancelable(false)
                                .setMessage("Aucune agence qui réponde à vos critères de recherche n'a été trouvé!")
                                .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        builder.show(); // Show
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message) {
                //
                BtSearch.setEnabled(true);
                progress_load.setVisibility(View.GONE);
                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgenceRecherche.this)
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                        .setTitle("Echec")
                        .setCancelable(false)
                        .setMessage("Recherche non terminée. Vérifiez votre connexion pour plus de sûreté et de rapidité lors de la recherche.")
                        .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.show(); // Show
            }
        });
    }

    private void searchByVilleTypeProduit(){
        //
        HttpRequest.getVilleTypeProduitAgence(ActivityAgenceRecherche.this, serveur, new String[]{villeid, type_produit}, new HttpCallbackString() {
            @Override
            public void onSuccess(String response) {
                try{

                    JSONObject jsonObject = new JSONObject(response);
                    int result = jsonObject.getInt("result");
                    if(result == 200) {

                        //suppression des éléments de l'ancienne recherche avant la nouvelle
                        deleteAgence.addAll(AgenceDao.getAllSearch());
                        for (EAgence donnor:deleteAgence){
                            AgenceDao.delete(donnor.getId());
                        }
                        //
                        JSONArray array = jsonObject.getJSONArray("data");
                        for(int i=0;i<array.length();i++){

                            JSONObject object = array.getJSONObject(i);
                            EAgence agence =  new Gson().fromJson(object.toString(),EAgence.class);
                            //status 0 pour les autres agences
                            agence.setStatus(0);
                            //sauvegarde
                            AgenceDao.create(agence);
                        }
                        //arret de la progression
                        progress_load.setVisibility(View.GONE);
                        //
                        goToNextActivity();

                    }
                    else {
                        //
                        BtSearch.setEnabled(true);
                        //
                        progress_load.setVisibility(View.GONE);
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgenceRecherche.this)
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                .setTitle("Infos")
                                .setCancelable(false)
                                .setMessage("Aucune agence qui réponde à vos critères de recherche n'a été trouvé!")
                                .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        builder.show(); // Show
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message) {
                //
                BtSearch.setEnabled(true);
                progress_load.setVisibility(View.GONE);
                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgenceRecherche.this)
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                        .setTitle("Echec")
                        .setCancelable(false)
                        .setMessage("Recherche non terminée. Vérifiez votre connexion pour plus de sûreté et de rapidité lors de la recherche.")
                        .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.show(); // Show
            }
        });
    }

    private void searchByVille(){
        //
        HttpRequest.getVilleAgence(ActivityAgenceRecherche.this, serveur, new String[]{villeid}, new HttpCallbackString() {
            @Override
            public void onSuccess(String response) {
                try{

                    JSONObject jsonObject = new JSONObject(response);
                    int result = jsonObject.getInt("result");
                    if(result == 200) {

                        //suppression des éléments de l'ancienne recherche avant la nouvelle
                        deleteAgence.addAll(AgenceDao.getAllSearch());
                        for (EAgence donnor:deleteAgence){
                            AgenceDao.delete(donnor.getId());
                        }
                        //
                        JSONArray array = jsonObject.getJSONArray("data");
                        for(int i=0;i<array.length();i++){

                            JSONObject object = array.getJSONObject(i);
                            EAgence agence =  new Gson().fromJson(object.toString(),EAgence.class);
                            //status 0 pour les autres agences
                            agence.setStatus(0);
                            //sauvegarde
                            AgenceDao.create(agence);

                        }
                        //arret de la progression
                        progress_load.setVisibility(View.GONE);
                        //
                        goToNextActivity();
                    }
                    else {
                        //
                        BtSearch.setEnabled(true);
                        //
                        progress_load.setVisibility(View.GONE);
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgenceRecherche.this)
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                .setTitle("Infos")
                                .setCancelable(false)
                                .setMessage("Aucune agence qui réponde à vos critères de recherche n'a été trouvé!")
                                .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        builder.show(); // Show
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message) {
                //
                BtSearch.setEnabled(true);
                progress_load.setVisibility(View.GONE);
                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgenceRecherche.this)
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                        .setTitle("Echec")
                        .setCancelable(false)
                        .setMessage("Recherche non terminée. Vérifiez votre connexion pour plus de sûreté et de rapidité lors de la recherche.")
                        .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.show(); // Show
            }
        });
    }

    private void searchByProvinceTypeProduit(){
        //
        HttpRequest.getProvinceTypeProduitAgence(ActivityAgenceRecherche.this, serveur, new String[]{provinceid, type_produit}, new HttpCallbackString() {
            @Override
            public void onSuccess(String response) {
                try{

                    JSONObject jsonObject = new JSONObject(response);
                    int result = jsonObject.getInt("result");
                    if(result == 200) {

                        //suppression des éléments de l'ancienne recherche avant la nouvelle
                        deleteAgence.addAll(AgenceDao.getAllSearch());
                        for (EAgence donnor:deleteAgence){
                            AgenceDao.delete(donnor.getId());
                        }
                        //
                        JSONArray array = jsonObject.getJSONArray("data");
                        for(int i=0;i<array.length();i++){

                            JSONObject object = array.getJSONObject(i);
                            EAgence agence =  new Gson().fromJson(object.toString(),EAgence.class);
                            //status 0 pour les autres agences
                            agence.setStatus(0);
                            //sauvegarde
                            AgenceDao.create(agence);

                        }

                        if(array.length()==0){
                            BtSearch.setEnabled(true);
                            progress_load.setVisibility(View.GONE);
                            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgenceRecherche.this)
                                    .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                    .setTitle("Echec")
                                    .setCancelable(false)
                                    .setMessage("Recherche non terminée. Vérifiez votre connexion pour plus de sûreté et de rapidité lors de la recherche.")
                                    .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                            builder.show(); // Show
                        }else {
                            progress_load.setVisibility(View.GONE);
                            goToNextActivity();
                        }
                        //arret de la progression
                        //



                    }
                    else {
                        //
                        BtSearch.setEnabled(true);
                        //
                        progress_load.setVisibility(View.GONE);
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgenceRecherche.this)
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                .setTitle("Infos")
                                .setCancelable(false)
                                .setMessage("Aucune agence qui réponde à vos critères de recherche n'a été trouvé!")
                                .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        builder.show(); // Show
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message) {
                //
                BtSearch.setEnabled(true);
                progress_load.setVisibility(View.GONE);
                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgenceRecherche.this)
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                        .setTitle("Echec")
                        .setCancelable(false)
                        .setMessage("Recherche non terminée. Vérifiez votre connexion pour plus de sûreté et de rapidité lors de la recherche.")
                        .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.show(); // Show
            }
        });
    }

    private void searchByProvince(){
        //
        HttpRequest.getProvinceAgence(ActivityAgenceRecherche.this, serveur, new String[]{provinceid}, new HttpCallbackString() {
            @Override
            public void onSuccess(String response) {
                try{

                    JSONObject jsonObject = new JSONObject(response);
                    int result = jsonObject.getInt("result");
                    if(result == 200) {

                        //suppression des éléments de l'ancienne recherche avant la nouvelle
                        deleteAgence.addAll(AgenceDao.getAllSearch());
                        for (EAgence donnor:deleteAgence){
                            AgenceDao.delete(donnor.getId());
                        }
                        // recuperation des éléments en local
                        JSONArray array = jsonObject.getJSONArray("data");
                        for(int i=0;i<array.length();i++){

                            JSONObject object = array.getJSONObject(i);
                            EAgence agence =  new Gson().fromJson(object.toString(),EAgence.class);
                            //status 0 pour les autres agences
                            agence.setStatus(0);
                            //sauvegarde
                            AgenceDao.create(agence);

                        }
                        //arret de la progression
                        progress_load.setVisibility(View.GONE);
                        //
                        goToNextActivity();

                    }
                    else {
                        //
                        BtSearch.setEnabled(true);
                        //
                        progress_load.setVisibility(View.GONE);
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgenceRecherche.this)
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                .setTitle("Infos")
                                .setCancelable(false)
                                .setMessage("Aucune agence qui réponde à vos critères de recherche n'a été trouvé!")
                                .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        builder.show(); // Show
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message) {
                //
                BtSearch.setEnabled(true);
                progress_load.setVisibility(View.GONE);
                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgenceRecherche.this)
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                        .setTitle("Echec")
                        .setCancelable(false)
                        .setMessage("Recherche non terminée. Vérifiez votre connexion pour plus de sûreté et de rapidité lors de la recherche.")
                        .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.show(); // Show
            }
        });
    }


    @SuppressLint("RestrictedApi")
    private void createGpsDisabledAlert() {

        MaterialDialog mDialog = new MaterialDialog.Builder(ActivityAgenceRecherche.this)
                .setTitle("Localisation")
                .setMessage("Vous devez impérativement activer votre GPS sans quoi vous n'allez pas continuer cette opération.\n\nVoulez-vous l'activer maintenant ?")
                .setCancelable(false)
                .setPositiveButton("Activer GPS",  new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        // on scan un compteur
                        dialogInterface.dismiss();
                        showGpsOptions();
                    }
                })
                .setNegativeButton("Annuler",  new MaterialDialog.OnClickListener() {
                    @Override
                    public void onClick(com.shreyaspatil.MaterialDialog.interfaces.DialogInterface dialogInterface, int which) {
                        dialogInterface.dismiss();
                        if(!checkGps())
                        {
                            gps_active=false;
                        }

                    }
                }).build();

        // Show Dialog
        mDialog.show();

    }

    private void showGpsOptions() {
        startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
    }

    private  boolean checkGps()
    {
        final LocationManager manager = (LocationManager)ActivityAgenceRecherche.this. getSystemService( Context.LOCATION_SERVICE );

        try {

            assert manager != null;
            gps_active=true;
            return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        catch (Exception  e)
        {
            gps_active=false;
            return  false;
        }

    }
}

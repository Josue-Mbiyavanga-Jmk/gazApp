package com.sax.inc.coetegaz.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sax.inc.coetegaz.Dao.MouvementStockDao;
import com.sax.inc.coetegaz.Dao.TypeProduitDao;
import com.sax.inc.coetegaz.Entites.EMouvementStock;
import com.sax.inc.coetegaz.Entites.EServeur;
import com.sax.inc.coetegaz.Entites.ETypeProduit;
import com.sax.inc.coetegaz.Memory.Constant;
import com.sax.inc.coetegaz.Memory.Keys;
import com.sax.inc.coetegaz.Memory.Preferences;
import com.sax.inc.coetegaz.NetWork.HttpRequest;
import com.sax.inc.coetegaz.R;
import com.sax.inc.coetegaz.Utils.ETypeMessage;
import com.sax.inc.coetegaz.Utils.HttpCallbackString;
import com.sax.inc.coetegaz.Utils.UtilEServeur;
import com.sax.inc.coetegaz.Utils.UtilTimeStampToDate;
import com.sax.inc.coetegaz.Utils.Utils;
import com.sax.inc.coetegaz.Utils.UtilsConnexionData;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import customfonts.MyTextView_Roboto_Regular;

import static com.sax.inc.coetegaz.Utils.UtilsToast.showCFAlertDialog;

public class ActivityMouvementAdd extends AppCompatActivity {

    private MyTextView_Roboto_Regular BtAdd;
    private SpinKitView progress_load;
    private SearchableSpinner spinner_type_produit;
    private EditText edt_qt,edt_observation;
    private ImageView bt_back;
    private String typeproduit = "";
    private List<String> elementTypeProduit;
    private List<ETypeProduit> typeProduitList;
    private String mOperation="";
    private EMouvementStock eMouvementStock;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mouvement_stock);
        initView();
        load();
        event();

    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    private void initView(){

        setTitle("Mouvement stock");
        edt_observation = findViewById(R.id.edt_observation);
        edt_qt = findViewById(R.id.edt_qt);
        bt_back =  findViewById(R.id.bt_back);
        BtAdd = findViewById(R.id.BtAdd);
        spinner_type_produit = findViewById(R.id.spinner_groupe);
        progress_load = findViewById(R.id.spin_kit_load);
        progress_load.setVisibility(View.GONE);
    }

    private void load()
    {
        typeProduitList =new ArrayList<>();
        elementTypeProduit =new ArrayList<>();
        typeProduitList = TypeProduitDao.getAll();
        for(ETypeProduit o: typeProduitList){
            elementTypeProduit.add(o.getName());
        }

        refresh(spinner_type_produit, elementTypeProduit);
    }

    private void refresh(SearchableSpinner spinner, List<String> list) {
        ArrayAdapter<String> adp = new ArrayAdapter<String>(ActivityMouvementAdd.this,android.R.layout.simple_dropdown_item_1line,list);
        spinner.setAdapter(adp);

    }



    private void event(){
        //

        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        BtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(typeproduit.equals(""))
                {
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityMouvementAdd.this)
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                            .setTitle("info")
                            .setCancelable(false)
                            .setMessage("Veuillez choisir le type produit dont vous voulez éffecter le mouvement dans le stock.")
                            .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    builder.show(); // Show
                    return;
                }

                boolean b= Utils.isEmptyFields(new Object[]{edt_qt});
                if(!b)
                {
                    Boolean connect = UtilsConnexionData.isConnected(ActivityMouvementAdd.this);
                    //connexion existante
                    if(connect){

                        eMouvementStock=new EMouvementStock();
                        long date= UtilTimeStampToDate.getTimeStamp();
                        eMouvementStock.setDatewrite(date);
                        String ref_agence=   Preferences.get(Keys.PreferencesKeys.STORE_ID_AGENCE);
                        eMouvementStock.setAgenceref(Integer.valueOf(ref_agence));
                        eMouvementStock.setTypeproduit(typeproduit);
                        String obsev=edt_observation.getText().toString().trim();
                        eMouvementStock.setObservation(obsev.equals("")?"Rien n'a signaler" : obsev);
                        int qt=Integer.valueOf(edt_qt.getText().toString().trim());
                        eMouvementStock.setQuantite(qt);
                        eMouvementStock.setType(Constant.INPUT);
                        String pseudo_user=   Preferences.get(Keys.PreferencesKeys.STORE_PSEUDO_USER);
                        eMouvementStock.setUserwrite(pseudo_user);

                        addMouvement(); // Appel Http
                    }
                    else {
                        showCFAlertDialog(ActivityMouvementAdd.this,"Pas de connexion",
                                "Problème de connexion survenu, veuillez la vérifier.", ETypeMessage.ERROR);
                    }

                }


            }
        });


        //
        spinner_type_produit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typeproduit = spinner_type_produit.getSelectedItem().toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    private void addMouvement(){
        //
        progress_load.setVisibility(View.VISIBLE);
        BtAdd.setEnabled(false);

        EServeur eServeur= UtilEServeur.getServeur();
        //préparation de gson
        //sérialisation de l'objet Mouvement en json
        // Gson gson_detail = new GsonBuilder().setPrettyPrinting().create();
        Gson gson_detail = new GsonBuilder().serializeNulls().create();;
        final   String param= gson_detail.toJson(eMouvementStock);

        HttpRequest.addingMouvement(ActivityMouvementAdd.this, eServeur, new String[]{param}, new HttpCallbackString() {
            @Override
            public void onSuccess(String response) {
                try{

                    JSONObject jsonObject = new JSONObject(response);
                    int result = jsonObject.getInt("result");
                    if(result == 200) {


                        //
                        JSONArray array = jsonObject.getJSONArray("data");
                        for(int i=0;i<array.length();i++){

                            JSONObject object = array.getJSONObject(i);
                            EMouvementStock eMouv =  new Gson().fromJson(object.toString(),EMouvementStock.class);
                            eMouv.setIdnative(eMouv.getId());
                            MouvementStockDao.create(eMouv);
                            progress_load.setVisibility(View.GONE);
                            onBackPressed();

                        }
                        //arret de la progression
                        progress_load.setVisibility(View.GONE);

                        //
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityMouvementAdd.this)
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                .setTitle("Info")
                                .setCancelable(false)
                                .setMessage("Ce mouvement a été éffectué avec succès. Merci")
                                .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        //arret du message
                                        dialog.dismiss();
                                        BtAdd.setEnabled(true);
                                        onBackPressed();
                                    }
                                });

                        builder.show(); // Show
                    }
                    else {

                        progress_load.setVisibility(View.GONE);
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityMouvementAdd.this)
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                .setTitle("Info")
                                .setCancelable(false)
                                .setMessage("L'opération n'a pas abouti. Veuillez réessayer. Merci")
                                .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        BtAdd.setEnabled(true);
                                        onBackPressed();
                                    }
                                });

                        builder.show(); // Show
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                    progress_load.setVisibility(View.GONE);
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityMouvementAdd.this)
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                            .setTitle("Info")
                            .setCancelable(false)
                            .setMessage("L'opération n'a pas abouti. Veuillez réessayer. Merci")
                            .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    BtAdd.setEnabled(true);
                                }
                            });

                    builder.show(); // Show
                }
            }

            @Override
            public void onError(String message) {
                //
                progress_load.setVisibility(View.GONE);
                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityMouvementAdd.this)
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                        .setTitle("Info")
                        .setCancelable(false)
                        .setMessage("L'opération n'a pas abouti. Veuillez réessayer. Merci")
                        .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                BtAdd.setEnabled(true);
                            }
                        });
                builder.show(); // Show
            }
        });
    }



}

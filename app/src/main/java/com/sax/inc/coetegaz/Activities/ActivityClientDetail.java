package com.sax.inc.coetegaz.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.nex3z.notificationbadge.NotificationBadge;
import com.sax.inc.coetegaz.Dao.ClientDao;
import com.sax.inc.coetegaz.Dao.CommuneDao;
import com.sax.inc.coetegaz.Dao.MouvementStockDao;
import com.sax.inc.coetegaz.Dao.ProvinceDao;
import com.sax.inc.coetegaz.Dao.QuartierDao;
import com.sax.inc.coetegaz.Dao.VilleDao;
import com.sax.inc.coetegaz.Entites.EClient;
import com.sax.inc.coetegaz.Entites.ECommune;
import com.sax.inc.coetegaz.Entites.EMouvementStock;
import com.sax.inc.coetegaz.Entites.EProvince;
import com.sax.inc.coetegaz.Entites.EQuartier;
import com.sax.inc.coetegaz.Entites.EServeur;
import com.sax.inc.coetegaz.Entites.ETypeProduit;
import com.sax.inc.coetegaz.Entites.EVille;
import com.sax.inc.coetegaz.Memory.Constant;
import com.sax.inc.coetegaz.Memory.Keys;
import com.sax.inc.coetegaz.Memory.Preferences;
import com.sax.inc.coetegaz.NetWork.HttpRequest;
import com.sax.inc.coetegaz.R;
import com.sax.inc.coetegaz.Utils.HttpCallbackString;
import com.sax.inc.coetegaz.Utils.UtilEServeur;
import com.sax.inc.coetegaz.Utils.UtilTimeStampToDate;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import customfonts.MyTextView_Roboto_Regular;

public class ActivityClientDetail extends AppCompatActivity {

    private TextView txt_name,txt_adresse,txt_num_tel,txt_token;
    private EditText edit_quantite;
    private ImageView img_qr,bt_back;
    private SearchableSpinner spinner_abonnement;
    private MyTextView_Roboto_Regular BtAddPanier;
    private NotificationBadge badge;
    private int clientid;
    private String produitName;
    private EClient client;
    private List<String> nameAbonnementList;
    private List<ETypeProduit> produits;
    private List<Panier> paniers;
    private KProgressHUD progress;
    private LinearLayout contenaire_panier;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_detail);
        initView();
        displayInfo();
        events();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    private void initView(){
        txt_name = findViewById(R.id.txt_name);
        txt_adresse = findViewById(R.id.txt_adresse);
        txt_num_tel = findViewById(R.id.txt_num_tel);
        txt_token = findViewById(R.id.txt_token);
        edit_quantite = findViewById(R.id.edit_quantite);
        img_qr = findViewById(R.id.img_qr);
        bt_back = findViewById(R.id.bt_back);
        spinner_abonnement = findViewById(R.id.spinner_abonnement);
        contenaire_panier = findViewById(R.id.contenaire_panier);
        BtAddPanier = findViewById(R.id.BtAddPanier);
        badge =findViewById(R.id.badge);
        badge.setText("0");//initialisation du compteur à zéro

        paniers=new ArrayList<>();

    }

    private void displayInfo(){
        Bundle bundle = getIntent().getExtras();
        if (bundle!= null) {
            clientid = bundle.getInt("clientid");
            //action
            client = ClientDao.get(clientid);
            //
            EQuartier quartier = QuartierDao.get(client.getQuartierref());
            //
            ECommune commune = CommuneDao.get(client.getCommuneref());
            //
            EVille ville = VilleDao.get(client.getVilleref());
            //
            EProvince province = ProvinceDao.get(client.getProvinceref());
            //affichage
            //noms
            if(client.getName()==null){
                txt_name.setText("Noms absent"); //nom complet
            }
            else {
                txt_name.setText(client.getName()); //nom complet
            }
            //telephone
            if(client.getTelephone()==null){
                txt_num_tel.setText("Non renseigné"); //phone
            }
            else {
                txt_num_tel.setText(client.getTelephone()); //phone
            }
            //adresse
            if(client.getNumero()==null && client.getAvenue()!=null){
                txt_adresse.setText("Av. "+
                        client.getAvenue()+" Q/ "+quartier.getName()+" C/ "+commune.getName()+
                        " V/ "+ ville.getName() + " P/ " +province.getName());
            }
            else if(client.getNumero()!=null && client.getAvenue()!=null){

                txt_adresse.setText("N° "+client.getNumero()+", Av. "+
                        client.getAvenue()+" Q/ "+quartier.getName()+" C/ "+commune.getName()+
                        " V/ "+ ville.getName() + " P/ " +province.getName());

            }
            if(client.getToken()==null){
                txt_token.setText("Identifiant absent"); //token
            }else {
                txt_token.setText(client.getToken()+ "       [IDENTIFIANT]"); //token
            }
            //abonnement
            String jsonList = client.getListabonnement();
            produits = new Gson().fromJson(jsonList,new TypeToken<List<ETypeProduit>>() {}.getType()); //convert json array to java object's list
            nameAbonnementList=new ArrayList<>();
            for(ETypeProduit item:produits){
                nameAbonnementList.add(item.getName()); //plustard tester la présence de 2 produits identiques
            }
            refresh(spinner_abonnement,nameAbonnementList);



        }
    }

    private void events(){
        //evenement de selection
        spinner_abonnement.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                produitName=spinner_abonnement.getSelectedItem().toString();
                edit_quantite.setText("1");

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        //événement du boutton
        BtAddPanier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(produitName.equals("")){
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityClientDetail.this)
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                            .setTitle("Infos")
                            .setCancelable(false)
                            .setMessage("Aucune offre abonnement n'a été selectionée!")
                            .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    builder.show(); // Show
                }

                if(edit_quantite.getText().toString().equals("")){
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityClientDetail.this)
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                            .setTitle("Infos")
                            .setCancelable(false)
                            .setMessage("Aucune quantité n'a été introduite!")
                            .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });

                    builder.show(); // Show
                }else {
                    //incrémentation du panier
                    int count = Integer.parseInt(badge.getTextView().getText().toString());
                    int total = count + 1;
                    badge.setText(total+"");
                    //sauvegarde des élément du panier
                    Panier element = new Panier();
                    element.setNom(produitName);
                    element.setQuantite(Integer.parseInt(edit_quantite.getText().toString().trim()));
                    paniers.add(element);
                    //réinitialisation
                    edit_quantite.setText("");
                    edit_quantite.requestFocus();
                }

            }
        });
        //
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        //événement du panier
        contenaire_panier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                  int count = Integer.parseInt(badge.getTextView().getText().toString());
                  if(count>0){
                      displayDialog();
                  }
                  else {
                      CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityClientDetail.this)
                              .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                              .setTitle("Attention")
                              .setCancelable(false)
                              .setMessage("Le panier ne contient rien en ce moment")
                              .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                  @Override
                                  public void onClick(DialogInterface dialog, int which) {
                                      dialog.dismiss();
                                  }
                              });

                      builder.show(); // Show
                  }

            }
        });

        img_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // try {
                    Intent intent = new Intent(ActivityClientDetail.this, ActivityClientViewQR.class);
                    intent.putExtra("QR", client.getQr());
                    startActivity(intent);

               /* } catch (Exception e) {
                    e.printStackTrace();
                }*/
            }

        });


    }

    private void displayDialog(){
        View dialogView = getLayoutInflater().inflate(R.layout.view_detail_panier, null);
        final BottomSheetDialog dialog = new BottomSheetDialog(ActivityClientDetail.this);
        dialog.setContentView(dialogView);

        TextView  txt_quantity=dialogView.findViewById(R.id.txt_quantity);
        TextView  txt_quantity2=dialogView.findViewById(R.id.txt_quantity2);
        TextView  txt_quantity3=dialogView.findViewById(R.id.txt_quantity3);
        TextView txt_offre=dialogView.findViewById(R.id.txt_offre);
        TextView txt_offre2=dialogView.findViewById(R.id.txt_offre2);
        TextView txt_offre3=dialogView.findViewById(R.id.txt_offre3);
        TextView txt_paie=dialogView.findViewById(R.id.txt_paie);
        TextView txt_paie2=dialogView.findViewById(R.id.txt_paie2);
        TextView txt_paie3=dialogView.findViewById(R.id.txt_paie3);

        MyTextView_Roboto_Regular BtRecharge=dialogView.findViewById(R.id.BtRecharge);
        MyTextView_Roboto_Regular BtCancel=dialogView.findViewById(R.id.BtCancel);

        RelativeLayout ligne1=dialogView.findViewById(R.id.ligne_1);
        View v1=dialogView.findViewById(R.id.v1);
        RelativeLayout ligne2=dialogView.findViewById(R.id.ligne_2);
        View v2=dialogView.findViewById(R.id.v2);
        RelativeLayout ligne3=dialogView.findViewById(R.id.ligne_3);
        View v3=dialogView.findViewById(R.id.v3);

        for (Panier panier:paniers){
            if(panier.getNom().equals("6 Kg")){
                txt_quantity.setText(panier.getQuantite()+"");
                txt_offre.setText(panier.getNom());
                float paie = panier.getQuantite() * 12;
                txt_paie.setText(paie+" $");
                panier.setTotal(paie); //mis à jour du total à payer
                //activer la visibilité
                v1.setVisibility(View.VISIBLE);
                ligne1.setVisibility(View.VISIBLE);

            }
            else if(panier.getNom().equals("9 Kg")){
                txt_quantity2.setText(panier.getQuantite()+"");
                txt_offre2.setText(panier.getNom());
                float paie = panier.getQuantite() * 18;
                txt_paie2.setText(paie+" $");
                panier.setTotal(paie); //mis à jour du total à payer
                //activer la visibilité
                v2.setVisibility(View.VISIBLE);
                ligne2.setVisibility(View.VISIBLE);

            } else if(panier.getNom().equals("12 Kg")){
                txt_quantity3.setText(panier.getQuantite()+"");
                txt_offre3.setText(panier.getNom());
                float paie = panier.getQuantite() * 24;
                txt_paie3.setText(paie+" $");
                panier.setTotal(paie); //mis à jour du total à payer
                //activer la visibilité
                v3.setVisibility(View.VISIBLE);
                ligne3.setVisibility(View.VISIBLE);


            }
        }
        //annuler
        BtCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss(); //fermer le dialog
                paniers.clear();  //vider le panier
                badge.setText(0+"");
                //réinitialisation
                edit_quantite.setText("");
                edit_quantite.requestFocus();

            }
        });

        //recharger
        BtRecharge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 dialog.dismiss(); //fermer le dialog
                //demarrer la progression
                progress = KProgressHUD.create(ActivityClientDetail.this)
                        .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                        .setLabel("Veuillez patienter")
                        .setDetailsLabel("Demande de recharge en...")
                        .setCancellable(false)
                        .setAnimationSpeed(2)
                        .setDimAmount(0.5f)
                        .show();
                //former un mouvement pour le http
                List<EMouvementStock> mvtList = new ArrayList<>();
                for(Panier panier:paniers){
                    EMouvementStock mvt = new EMouvementStock();
                    mvt.setType(Constant.RECHARGE);
                    mvt.setTypeproduit(panier.getNom());
                    mvt.setQuantite(panier.getQuantite());
                    mvt.setTotalpaie(panier.getTotal());
                    mvt.setTotalpaie(panier.getTotal());
                    mvt.setObservation("rien à signaler pour la recharge");
                    String userPseudo = Preferences.get(Keys.PreferencesKeys.STORE_PSEUDO_USER);
                    String agence_id = Preferences.get(Keys.PreferencesKeys.STORE_ID_AGENCE);
                    mvt.setUserwrite(userPseudo);
                    mvt.setAgenceref(Integer.parseInt(agence_id));
                    mvt.setStatus(1);
                    mvt.setDatewrite(UtilTimeStampToDate.getTimeStamp());
                    mvt.setDateupdate(UtilTimeStampToDate.getTimeStamp());
                    mvt.setClient(client.getToken());
                    //
                    mvtList.add(mvt);
                }

                // convert users list to JSON array
                String mouvement_json_array = new Gson().toJson(mvtList);

                //http
                rechargeClient(mouvement_json_array,progress);

            }
        });

        //affichage du dialogue
        dialog.show();
    }

    private void rechargeClient(String param,final KProgressHUD progress){

        EServeur serveur= UtilEServeur.getServeur();
        HttpRequest.addingMouvement_v1(ActivityClientDetail.this, serveur, new String[]{param}, new HttpCallbackString() {
            @Override
            public void onSuccess(String response) {
                try {
                    EMouvementStock mouvementStock = null;
                    JSONObject jsonObject = new JSONObject(response);
                    int result = jsonObject.getInt("result");
                    if(result == 200) {
                        progress.dismiss();//arret de la progression
                        JSONArray data = jsonObject.getJSONArray("data");
                        for(int i=0;i<data.length();i++){
                            JSONObject object = data.getJSONObject(i);
                            mouvementStock = new Gson().fromJson(object.toString(), EMouvementStock.class);
                            if(mouvementStock.getStatus() ==1){
                                MouvementStockDao.create(mouvementStock);
                                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityClientDetail.this)
                                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                        .setTitle("Infos")
                                        .setCancelable(false)
                                        .setMessage("la recharge du client pour la bouteille de "+mouvementStock.getTypeproduit()+" est effectué avec succès.")
                                        .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });

                                builder.show(); // Show
                            }
                            else {
                                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityClientDetail.this)
                                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                        .setTitle("Echec")
                                        .setCancelable(false)
                                        .setMessage("la recharge du client pour la bouteille de "+mouvementStock.getTypeproduit()+" a echoué car la quantité disponible en stock est de "+mouvementStock.getQuantite()+".")
                                        .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                            }
                                        });

                                builder.show(); // Show
                            }

                        }
                        paniers.clear();  //vider le panier
                        badge.setText(0+"");
                        //réinitialisation
                        spinner_abonnement.setSelection(0);
                        edit_quantite.setText("");
                        edit_quantite.requestFocus();

                    } else {
                        progress.dismiss(); //arret du progress
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityClientDetail.this)
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                .setTitle("Infos")
                                .setCancelable(false)
                                .setMessage("la recharge du client n'a pas aboutie.")
                                .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                        builder.show(); // Show
                    }

                }catch (JSONException e){
                    e.printStackTrace();
                    //
                    progress.dismiss();

                }
            }

            @Override
            public void onError(String message) {
                progress.dismiss(); //arret du progress
                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityClientDetail.this)
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                        .setTitle("Echec")
                        .setCancelable(false)
                        .setMessage("Opération de recharge a échoué. Vérifiez votre connexion s'il vous plait")
                        .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });

                builder.show(); // Show
            }
        });
    }

    private void refresh(SearchableSpinner spinner,List<String> list) {
        ArrayAdapter<String> adp = new ArrayAdapter<String>(ActivityClientDetail.this,android.R.layout.simple_dropdown_item_1line,list);
        spinner.setAdapter(adp);

    }

    //Classe Interne pour le besoin
    private static class Panier{
        int quantite;
        float total;
        String nom;

        public Panier() {
        }

        public Panier(int quantite, String nom, float total) {
            this.quantite = quantite;
            this.nom = nom;
            this.total = total;
        }

        public int getQuantite() {
            return quantite;
        }

        public void setQuantite(int quantite) {
            this.quantite = quantite;
        }

        public String getNom() {
            return nom;
        }

        public void setNom(String nom) {
            this.nom = nom;
        }

        public float getTotal() {
            return total;
        }

        public void setTotal(float total) {
            this.total = total;
        }
    }

}

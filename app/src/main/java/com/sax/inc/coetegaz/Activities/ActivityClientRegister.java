package com.sax.inc.coetegaz.Activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
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
import com.sax.inc.coetegaz.Memory.Keys;
import com.sax.inc.coetegaz.Memory.Preferences;
import com.sax.inc.coetegaz.NetWork.HttpRequest;
import com.sax.inc.coetegaz.R;
import com.sax.inc.coetegaz.Utils.ETypeMessage;
import com.sax.inc.coetegaz.Utils.HttpCallbackString;
import com.sax.inc.coetegaz.Utils.UtilEServeur;
import com.sax.inc.coetegaz.Utils.UtilTimeStampToDate;
import com.sax.inc.coetegaz.Utils.UtilsConnexionData;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import static com.sax.inc.coetegaz.Utils.UtilsToast.showCFAlertDialog;

public class ActivityClientRegister extends AppCompatActivity {

    //variables réelles
    private View id_view_register_steep_on,id_view_register_two,
            id_view_register_tree,id_view_register_last,
            contenaire_categorie_donneur,help_type_donneur;
    private EditText edt_name_complet,edt_phone,edt_email
            ,edt_taille,edt_poids,edt_avenue,edt_numero;

    private SearchableSpinner spinner_province,
            spinner_ville,spinner_commune,spinner_quartier;

    private CheckBox ch6kg,ch9kg,ch12kg;



    private ImageView circle1,circle2;
    private Button BtSave,BtPreview;

    private RelativeLayout bottom;
    private SpinKitView spin_kit_load_save;
    private FirebaseAuth mAuth;
    private String mProvince="",mVille="",
            mCommune="",mQuartier="";

    private String provinceid="",villeid="", communeid="",quartierid;

    private int FocusActivity = 0;
    private List<String>  elementProvince,elementVille,elementCommune,elementQuartier;
    private List<ECommune> communeList;
    private List<EVille> villeList;
    private List<EProvince> provinceList;
    private List<EQuartier> quartierList;

    private EClient eClient;
    List<ETypeProduit> produitList ;
    List<String> abonnements ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_register);
        //cast
        initView();
        //factorize methods
        event();

        initialiseLists();

        // Initialiser Firebase Auth
        mAuth = FirebaseAuth.getInstance();

    }

    private void initView(){

        // circular steep
        circle1 = findViewById(R.id.circle1);
        circle2 = findViewById(R.id.circle2);


        //View portion
        id_view_register_steep_on = findViewById(R.id.id_view_register_steep_on);
        id_view_register_two = findViewById(R.id.id_view_register_two);


        bottom = findViewById(R.id.bottom);


        // Editext
        edt_name_complet = findViewById(R.id.edt_name_complet);
        edt_phone = findViewById(R.id.edt_phone);
        edt_email = findViewById(R.id.edt_email);
        edt_avenue = findViewById(R.id.edt_avenue);
        edt_email = findViewById(R.id.edt_email);
        edt_numero = findViewById(R.id.edt_numero);

        // SearchableSpinner
        spinner_province = findViewById(R.id.spinner_province);
        spinner_ville =findViewById(R.id.spinner_ville);
        spinner_commune =findViewById(R.id.spinner_commune);
        spinner_quartier = findViewById(R.id.spinner_quartier);


        //SpinKitView
        spin_kit_load_save = findViewById(R.id.spin_kit_load_save);
        spin_kit_load_save.setVisibility(View.GONE);
        //checkBox
        ch6kg = findViewById(R.id.ch6kg);
        ch9kg = findViewById(R.id.ch9kg);
        ch12kg = findViewById(R.id.ch12kg);

        //Button
        BtSave = findViewById(R.id.BtSave);
        BtPreview = findViewById(R.id.BtPreview);

        BtPreview.setVisibility(View.GONE);
    }

    private void event(){
        BtPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (FocusActivity) {
                    case 1:
                        //si c'est O, alors aller à 1
                        changeFocus(0);
                        visibilityControl(0);

                        BtPreview.setVisibility(View.GONE);
                        FocusActivity=0;
                        BtSave.setText("Continuer");

                        break;
                    case 2:
                        //si c'est 2, alors aller à 1
                        changeFocus(1);
                        visibilityControl(1);

                        BtPreview.setVisibility(View.VISIBLE);
                        FocusActivity=1;
                        BtSave.setText("Continuer");
                        break;

                    default:
                        break;
                }
            }
        });

        BtSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (FocusActivity) {
                    case 0:
                        //si c'est O, alors aller à 1

                        boolean b=isEmptyFields(new Object[]{edt_name_complet});


                        if(!b)
                        {
                            changeFocus(1);
                            visibilityControl(1);

                            BtPreview.setVisibility(View.VISIBLE);
                            FocusActivity=1;
                            BtSave.setText("Continuer");
                        }
                        break;

                    case 1:

                        Boolean connect = UtilsConnexionData.isConnected(ActivityClientRegister.this);
                        //connexion existante
                        if(connect){

                            boolean b_finish=isEmptyFields(new Object[]{edt_name_complet});

                            if(!b_finish)
                            {
                                // Traitement

                                    String nom = edt_name_complet.getText().toString();
                                    String tel = edt_phone.getText().toString();
                                    String email = edt_email.getText().toString();

                                    String avenue = edt_avenue.getText().toString();
                                    String numero = edt_numero.getText().toString();

                                    eClient =new EClient();

                                    // steep on
                                    eClient.setName(nom);
                                    eClient.setTelephone(tel);
                                    eClient.setEmail(email);


                                eClient.setProvinceref(Integer.valueOf(provinceid));
                                eClient.setVilleref(Integer.valueOf(villeid));
                                eClient.setCommuneref(Integer.valueOf(communeid));
                                eClient.setQuartierref(Integer.valueOf(quartierid));

                                eClient.setAvenue(avenue);
                                eClient.setNumero(numero);

                                    // steep tree
                                    produitList = new ArrayList<>();
                                    for(String unAbonnement :abonnements){
                                        if(unAbonnement.equals("6 Kg")){
                                            ETypeProduit produit = new ETypeProduit();
                                            produit.setName("6 Kg");
                                            produit.setPrixrecharge(12);
                                            produit.setPrixabonnement(25);
                                            produit.setStatus(1);
                                            produit.setDatewrite(UtilTimeStampToDate.getTimeStamp());
                                            produit.setDateupdate(UtilTimeStampToDate.getTimeStamp());
                                            produitList.add(produit);//add to final Abonnement List
                                        }
                                        if(unAbonnement.equals("9 Kg")){
                                            ETypeProduit produit = new ETypeProduit();
                                            produit.setName("9 Kg");
                                            produit.setPrixrecharge(18);
                                            produit.setPrixabonnement(37);
                                            produit.setStatus(1);
                                            produit.setDatewrite(UtilTimeStampToDate.getTimeStamp());
                                            produit.setDateupdate(UtilTimeStampToDate.getTimeStamp());
                                            produitList.add(produit);//add to final Abonnement List
                                        }
                                        if(unAbonnement.equals("12 Kg")){
                                            ETypeProduit produit = new ETypeProduit();
                                            produit.setName("12 Kg");
                                            produit.setPrixrecharge(24);
                                            produit.setPrixabonnement(54);
                                            produit.setStatus(1);
                                            produit.setDatewrite(UtilTimeStampToDate.getTimeStamp());
                                            produit.setDateupdate(UtilTimeStampToDate.getTimeStamp());
                                            produitList.add(produit);//add to final Abonnement List
                                        }
                                    }
                                    String produit_json_array = new Gson().toJson(produitList);   // convert users list to JSON array
                                    eClient.setListabonnement("");
                                    //others
                                    eClient.setDateupdate(UtilTimeStampToDate.getTimeStamp());
                                    eClient.setDatewrite(UtilTimeStampToDate.getTimeStamp());

                                    /* Qr, token(UID),description, ... seront complétés au niveau serveur */

                                    insertToSQLRemoteServer(produit_json_array);

                            }
                        }
                        else {
                            showCFAlertDialog(ActivityClientRegister.this,"Pas de connexion",
                                    "Problème de connexion survenu, veuillez la vérifier.",ETypeMessage.ERROR);
                        }

                        break;


                    default:

                        break;
                }
            }
        });



        //evenement de selection
        spinner_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mProvince=spinner_province.getSelectedItem().toString();

                //remplir ville
                EProvince eProvince = ProvinceDao.get(mProvince);
                provinceid = String.valueOf(eProvince.getId_native());
                //rendre toujours les villes et communes dépendante de Province
                elementVille.clear();
                elementCommune.clear();
                elementQuartier.clear();
                //add prompt
                elementVille.add("-- Ville -- *");
                elementCommune.add("-- Commune -- *");
                elementQuartier.add("-- Quartier -- *");
                //
                villeList.clear();
                villeList = VilleDao.getByProvince(eProvince.getId_native());
                for(EVille o:villeList){
                    elementVille.add(o.getName());
                }
                //
                refresh(spinner_ville,elementVille);
                refresh(spinner_commune,elementCommune);
                refresh(spinner_quartier,elementQuartier);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //
        spinner_ville.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mVille=spinner_ville.getSelectedItem().toString();

                if(mVille.equals("-- Ville -- *")){
                    villeid ="";
                }
                else {

                    EVille eVille = VilleDao.get(mVille);
                    villeid = String.valueOf(eVille.getId_native());
                    //
                    elementCommune.clear();
                    elementQuartier.clear();
                    //add prompt
                    elementCommune.add("-- Commune -- *");
                    elementQuartier.add("-- Quartier -- *");


                    communeList.clear();
                    communeList = CommuneDao.getByVille(eVille.getId_native());
                    for(ECommune o:communeList){
                        elementCommune.add(o.getName());
                    }

                    refresh(spinner_commune,elementCommune);
                    refresh(spinner_quartier,elementQuartier);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinner_commune.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mCommune=spinner_commune.getSelectedItem().toString();
                //
                if(mCommune.equals("-- Commune -- *")){
                    communeid ="";
                }
                else {

                    ECommune eCommune = CommuneDao.get(mCommune);
                    communeid = String.valueOf(eCommune.getId_native());


                    elementQuartier.clear();
                    elementQuartier.add("-- Quartier -- *");

                    quartierList = QuartierDao.getByCommune(eCommune.getId_native());
                    for(EQuartier o:quartierList){
                        elementQuartier.add(o.getName());
                    }

                    refresh(spinner_quartier,elementQuartier);
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });
        spinner_quartier.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mQuartier=spinner_quartier.getSelectedItem().toString();

                if(!mQuartier.equals(""))
                {
                    EQuartier o= QuartierDao.get(mQuartier);

                    if(o!=null)
                    {
                        quartierid=o.getId_native()+"";
                    }else{
                        quartierid="";
                    }
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {


            }
        });


        //les checkbox
        eventCheckBox();


    }

    public void onCheckClicked(View v){
        CheckBox box = (CheckBox)v;
        if(box.isChecked()){
            abonnements.add("6 Kg"); //on save si c'est coché
        }
        else {
            for(int i=0;i<abonnements.size();i++){
                if(abonnements.get(i).equals("6 Kg")){
                    abonnements.remove(i); //on delete si ce n'est pas coché
                }
            }
        }

    }

    public void onCheckClicked1(View v){
        CheckBox box = (CheckBox)v;
        if(box.isChecked()){
            abonnements.add("9 Kg"); //on save si c'est coché
        }
        else {
            for(int i=0;i<abonnements.size();i++){
                if(abonnements.get(i).equals("9 Kg")){
                    abonnements.remove(i); //on delete si ce n'est pas coché
                }
            }
        }

    }

    public void onCheckClicked2(View v){
        CheckBox box = (CheckBox)v;
        if(box.isChecked()){
                abonnements.add("12 Kg"); //on save si c'est coché
            }
            else {
                for(int i=0;i<abonnements.size();i++){
                    if(abonnements.get(i).equals("12 Kg")){
                        abonnements.remove(i); //on delete si ce n'est pas coché
                    }
                }
            }

    }

    private void eventCheckBox(){
        abonnements = new ArrayList<>();
        onCheckClicked(ch6kg);//premier check
        onCheckClicked1(ch9kg);//2nd check
        onCheckClicked2(ch12kg);//3ème check



    }






    //Insert dans la BD sql distante
    private void insertToSQLRemoteServer(final String listabonnement){
        //demarrage du progress une fois pour toute
        spin_kit_load_save.setVisibility(View.VISIBLE);
        bottom.setVisibility(View.GONE);
        //
        EServeur eServeur= UtilEServeur.getServeur();
        //préparation de gson
        //sérialisation de l'objet Donneur en json
        // Gson gson_detail = new GsonBuilder().setPrettyPrinting().create();

        Gson gson_detail = new GsonBuilder().serializeNulls().create();;
        final   String param= gson_detail.toJson(eClient);
        String param_agence = Preferences.get(Keys.PreferencesKeys.STORE_ID_AGENCE);
        String param_user = Preferences.get(Keys.PreferencesKeys.STORE_PSEUDO_USER);

        HttpRequest.addingClientAbonnement(ActivityClientRegister.this, eServeur, new String[]{param,param_agence,param_user,listabonnement}, new HttpCallbackString() {
            @Override
            public void onSuccess(String response) {
                try {
                    //parsing... c'est ici qu'on va lancer :insertToFirestoreAndSQLORMlite
                    JSONObject jsonObject = new JSONObject(response);
                    //parsing
                    int result = jsonObject.getInt("result");
                    if(result == 200) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        JSONArray jsonArrayMvt = jsonObject.getJSONArray("data_mouvement");
                        //parsing de l'objet Entité venant du serveur sql distant
                        JSONObject object = jsonArray.getJSONObject(0);
                        //desérialisation de json en objet client
                        EClient o = new Gson().fromJson(object.toString(),EClient.class);
                        o.setListabonnement(listabonnement);
                        ClientDao.create(o);
                        //pour les mouvement
                        for(int i=0;i<jsonArrayMvt.length();i++){
                            JSONObject object1 = jsonArrayMvt.getJSONObject(i);
                            EMouvementStock mt =  new Gson().fromJson(object1.toString(),EMouvementStock.class);
                            MouvementStockDao.create(mt);
                        }
                        spin_kit_load_save.setVisibility(View.GONE);
                        //après création quoi faire
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityClientRegister.this)
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                .setTitle("Infos")
                                .setCancelable(false)
                                .setMessage("Enregistrement et abonnement du client ont été effectués avec succès! Voici l'ID du client : "+o.getToken())
                                .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                });

                        builder.show(); // Show

                    }
                    else if(result == 204){
                        spin_kit_load_save.setVisibility(View.GONE);
                        //Stock insuffisant
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityClientRegister.this)
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                .setTitle("Echec")
                                .setCancelable(false)
                                .setMessage("L'abonnement de ce client ne peut etre réalisé parce que le stock disponible est insuffisant!")
                                .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        finish();
                                    }
                                });

                        builder.show(); // Show
                    }
                    else {
                        //Echec de l'insert
                        spin_kit_load_save.setVisibility(View.GONE);
                        bottom.setVisibility(View.VISIBLE);
                        //
                        showCFAlertDialog(ActivityClientRegister.this,"Opération non aboutie !",
                                "Le processus de création de compte n'est pas terminé complètement, veuillez réessayer.",ETypeMessage.ERROR);
                    }

                } catch (JSONException e) {
                    spin_kit_load_save.setVisibility(View.GONE);
                    bottom.setVisibility(View.VISIBLE);
                    showCFAlertDialog(ActivityClientRegister.this,"Opération non aboutie !",
                            "Le processus de création de compte n'est pas terminé complètement, veuillez réessayer.",ETypeMessage.ERROR);
                }

            }

            @Override
            public void onError(String message) {

                //Echec de l'insert
                spin_kit_load_save.setVisibility(View.GONE);
                bottom.setVisibility(View.VISIBLE);
                showCFAlertDialog(ActivityClientRegister.this,"Opération non aboutie !",
                        "Le processus de création de compte n'est pas terminé complètement, veuillez réessayer.",ETypeMessage.ERROR);
            }
        });
    }



    private Boolean isEmptyFields(Object[] objects){
        boolean b=false;
        for (Object o:objects)
        {
            if(o instanceof EditText )
            {
                String text= ((EditText)o).getText().toString().trim();
                if(text.isEmpty()){
                    ((EditText) o).setError("Remplir ce champ!");
                    b=true;
                }

            }
        }
        return b;
    }


    //method custom visibility
    private void visibilityControl(int position)
    {
        switch (position)
        {
            case 0:
                //la portion à afficher et celles à cacher
                id_view_register_steep_on.setVisibility(View.VISIBLE);
                id_view_register_two.setVisibility(View.GONE);

                break;

            case 1:

                //la portion à afficher et celles à cacher
                id_view_register_steep_on.setVisibility(View.GONE);
                id_view_register_two.setVisibility(View.VISIBLE);

                break;



            default:

                break;

        }

    }

    //method custom focus
    private void changeFocus(int position)
    {
        switch (position)
        {
            case 0:
                circle1.setBackground(ActivityClientRegister.this.getResources().getDrawable(R.drawable.circlebarre_focus));
                circle2.setBackground(ActivityClientRegister.this.getResources().getDrawable(R.drawable.circlebarre));

                FocusActivity=position;
                break;

            case 1:

                circle1.setBackground(ActivityClientRegister.this.getResources().getDrawable(R.drawable.circlebarre));
                circle2.setBackground(ActivityClientRegister.this.getResources().getDrawable(R.drawable.circlebarre_focus));
                FocusActivity = position;
                break;
            default:

                break;

        }


    }



    private void refresh(SearchableSpinner spinner, List<String> list) {
        ArrayAdapter<String> adp = new ArrayAdapter<String>(ActivityClientRegister.this,android.R.layout.simple_dropdown_item_1line,list);
        spinner.setAdapter(adp);
    }

    private void loadLists() {

        elementProvince=new ArrayList<>();
        provinceList= ProvinceDao.getAll();

        for (EProvince e:provinceList) {
            elementProvince.add(e.getName());
        }
        refresh(spinner_province,elementProvince);

        villeList=new ArrayList<>();
        elementVille =new ArrayList<>();
        refresh(spinner_ville,elementVille);
        //
        communeList=new ArrayList<>();
        elementCommune =new ArrayList<>();
        refresh(spinner_commune,elementCommune);

        quartierList=new ArrayList<>();
        elementQuartier =new ArrayList<>();
        refresh(spinner_quartier,elementQuartier);

    }

    private void initialiseLists() {


        villeList=new ArrayList<>();
        communeList=new ArrayList<>();
        quartierList=new ArrayList<>();
        provinceList=new ArrayList<>();


        villeList.clear();
        communeList.clear();
        quartierList.clear();
        provinceList.clear();


        loadLists();
    }



}

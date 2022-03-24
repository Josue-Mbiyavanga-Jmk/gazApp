package com.sax.inc.coetegaz.Activities;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sax.inc.coetegaz.App.AppController;
import com.sax.inc.coetegaz.Dao.AgenceDao;
import com.sax.inc.coetegaz.Dao.CommuneDao;
import com.sax.inc.coetegaz.Dao.ProvinceDao;
import com.sax.inc.coetegaz.Dao.QuartierDao;
import com.sax.inc.coetegaz.Dao.VilleDao;
import com.sax.inc.coetegaz.Entites.EAgence;
import com.sax.inc.coetegaz.Entites.ECommune;
import com.sax.inc.coetegaz.Entites.EProvince;
import com.sax.inc.coetegaz.Entites.EQuartier;
import com.sax.inc.coetegaz.Entites.EVille;
import com.sax.inc.coetegaz.NetWork.HttpRetrofit;
import com.sax.inc.coetegaz.R;
import com.sax.inc.coetegaz.Utils.ETypeMessage;
import com.sax.inc.coetegaz.Utils.GPS;
import com.sax.inc.coetegaz.Utils.GenericObjet;
import com.sax.inc.coetegaz.Utils.UtilGPS;
import com.sax.inc.coetegaz.Utils.UtilTimeStampToDate;
import com.sax.inc.coetegaz.Utils.UtilsConnexionData;
import com.shreyaspatil.MaterialDialog.MaterialDialog;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

import static com.sax.inc.coetegaz.Utils.UtilsToast.showCFAlertDialog;

public class ActivityAgenceRegister extends AppCompatActivity {

    //variables réelles
    private View id_view_register_steep_on,id_view_register_two;
    private EditText edt_name_complet,edt_phone
            ,edt_ref_lieu,edt_open_heure,edt_avenue,edt_numero;

    private SearchableSpinner spinner_province,
            spinner_ville,spinner_commune,spinner_quartier;

    private ImageView circle1,circle2;
    private Button BtSave,BtPreview;

    private RelativeLayout bottom;
    private SpinKitView spin_kit_load_save;
    private String mProvince="",mVille="",
            mCommune="",mQuartier="";

    private String provinceid="",villeid="", communeid="",quartierid;


    private int FocusActivity = 0;

    private List<String>  elementProvince,elementVille,elementCommune,elementQuartier;
    private List<ECommune> communeList;
    private List<EVille> villeList;
    private List<EProvince> provinceList;
    private List<EQuartier> quartierList;

    private EAgence eAgence;

    private BroadcastReceiver gpsReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().matches(LocationManager.PROVIDERS_CHANGED_ACTION)) {
                //GPS status change
                if(!checkGps())
                {
                    createGpsDisabledAlert();
                }
                else
                {
                    AppController global=(AppController) (ActivityAgenceRegister.this.getApplicationContext());
                    global.gp=new GPS(ActivityAgenceRegister.this);
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agence_register);
        //cast
        initView();
        //factorize methods
        event();

        initialiseLists();
        try {
            registerReceiver(gpsReceiver, new IntentFilter(LocationManager.PROVIDERS_CHANGED_ACTION));
        }catch (Exception e){

        }


    }

    @Override
    protected void onResume() {
        super.onResume();

        if(!checkGps())
        {
            createGpsDisabledAlert();
        }
        else
        {
            AppController global=(AppController) (ActivityAgenceRegister.this.getApplicationContext());
            global.gp=new GPS(ActivityAgenceRegister.this);
        }
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
        edt_avenue = findViewById(R.id.edt_avenue);
        edt_numero = findViewById(R.id.edt_numero);
        edt_ref_lieu = findViewById(R.id.edt_ref_lieu);
        edt_open_heure = findViewById(R.id.edt_open_heure);

        // SearchableSpinner
        spinner_province = findViewById(R.id.spinner_province);
        spinner_ville =findViewById(R.id.spinner_ville);
        spinner_commune =findViewById(R.id.spinner_commune);
        spinner_quartier = findViewById(R.id.spinner_quartier);


        //SpinKitView
        spin_kit_load_save = findViewById(R.id.spin_kit_load_save);
        spin_kit_load_save.setVisibility(View.GONE);

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

                        boolean b=isEmptyFields(new Object[]{edt_name_complet,edt_phone,edt_ref_lieu});

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

                        Boolean connect = UtilsConnexionData.isConnected(ActivityAgenceRegister.this);
                        //connexion existante
                        if(connect){

                            boolean b_finish=isEmptyFields(new Object[]{edt_name_complet});

                            if(!b_finish)
                            {
                                // Traitement

                                    String nom = edt_name_complet.getText().toString();
                                    String tel = edt_phone.getText().toString();
                                    String open_heure = edt_open_heure.getText().toString();

                                    String avenue = edt_avenue.getText().toString();
                                    String numero = edt_numero.getText().toString();
                                    String ref_lieu = edt_ref_lieu.getText().toString();

                                    eAgence =new EAgence();

                                    // steep on
                                    eAgence.setName(nom);
                                    eAgence.setTelephone(tel);

                                    // steep two
                                    eAgence.setProvinceref(Integer.valueOf(provinceid));
                                    eAgence.setVilleref(Integer.valueOf(villeid));
                                    eAgence.setCommuneref(Integer.valueOf(communeid));
                                    eAgence.setQuartierref(Integer.valueOf(quartierid));
                                    eAgence.setAvenue(avenue);
                                    eAgence.setNumero(numero);
                                    eAgence.setHeureopen(open_heure);
                                    eAgence.setReflieu(ref_lieu);

                                String coordonnee = "";
                                String tab[];
                                coordonnee = UtilGPS.getGpsData(ActivityAgenceRegister.this);

                                if (!TextUtils.isEmpty(coordonnee)) {
                                    eAgence.setGps(coordonnee);
                                }

                                    //status 0:offline, 1 : onligne, ...
                                    eAgence.setStatus(0);

                                    //others
                                    eAgence.setDateupdate(UtilTimeStampToDate.getTimeStamp());
                                    eAgence.setDatewrite(UtilTimeStampToDate.getTimeStamp());

                                    insertToSQLRemoteServer();

                            }
                        }
                        else {
                            showCFAlertDialog(ActivityAgenceRegister.this,"Pas de connexion",
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




    }






    //Insert dans la BD sql distante
    private void insertToSQLRemoteServer(){
        //demarrage du progress une fois pour toute
        spin_kit_load_save.setVisibility(View.VISIBLE);
        bottom.setVisibility(View.GONE);

        Gson gson_detail = new GsonBuilder().serializeNulls().create();
        final   String param= gson_detail.toJson(eAgence);

        Thread thread =new Thread(new Runnable() {
            @Override
            public void run() {

                final GenericObjet<EAgence>response= HttpRetrofit.addingAgence(param);

                if(response!=null){

                    if(response.getResult()==200){
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                spin_kit_load_save.setVisibility(View.GONE);
                                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgenceRegister.this)
                                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                        .setTitle("Info")
                                        .setCancelable(false)
                                        .setMessage("L'agence est créée  avec succès."+"\n")
                                        .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.dismiss();
                                                EAgence agence=response.getData().get(0);
                                                agence.setStatus(1);
                                                AgenceDao.create(agence);
                                                onBackPressed();
                                            }
                                        });

                                builder.show(); // Show
                            }
                        });

                    }else{
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                spin_kit_load_save.setVisibility(View.GONE);
                                showCFAlertDialog(ActivityAgenceRegister.this,"Opération non aboutie !",
                                        "Le processus de création de l'agence n'est pas terminé complètement, veuillez réessayer.",ETypeMessage.ERROR);
                            }
                        });

                    }
                }
                else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            spin_kit_load_save.setVisibility(View.GONE);
                            showCFAlertDialog(ActivityAgenceRegister.this,"Opération non aboutie !",
                                    "Le processus de création de l'agence n'est pas terminé complètement, veuillez réessayer.",ETypeMessage.ERROR);
                        }
                    });

                }
            }
        });

        thread.start();

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
                circle1.setBackground(ActivityAgenceRegister.this.getResources().getDrawable(R.drawable.circlebarre_focus));
                circle2.setBackground(ActivityAgenceRegister.this.getResources().getDrawable(R.drawable.circlebarre));

                FocusActivity=position;
                break;

            case 1:

                circle1.setBackground(ActivityAgenceRegister.this.getResources().getDrawable(R.drawable.circlebarre));
                circle2.setBackground(ActivityAgenceRegister.this.getResources().getDrawable(R.drawable.circlebarre_focus));
                FocusActivity = position;
                break;
            default:

                break;

        }


    }



    private void refresh(SearchableSpinner spinner, List<String> list) {
        ArrayAdapter<String> adp = new ArrayAdapter<String>(ActivityAgenceRegister.this,android.R.layout.simple_dropdown_item_1line,list);
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

    @SuppressLint("RestrictedApi")
    private void createGpsDisabledAlert() {

        MaterialDialog mDialog = new MaterialDialog.Builder(ActivityAgenceRegister.this)
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
                            onBackPressed();
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
        final LocationManager manager = (LocationManager)ActivityAgenceRegister.this. getSystemService( Context.LOCATION_SERVICE );

        try {

            assert manager != null;
            return manager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        }
        catch (Exception  e)
        {
            return  false;
        }

    }




}

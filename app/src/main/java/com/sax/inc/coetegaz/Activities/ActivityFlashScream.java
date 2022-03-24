package com.sax.inc.coetegaz.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.sax.inc.coetegaz.Dao.AgenceDao;
import com.sax.inc.coetegaz.Dao.CommuneDao;
import com.sax.inc.coetegaz.Dao.ProvinceDao;
import com.sax.inc.coetegaz.Dao.QuartierDao;
import com.sax.inc.coetegaz.Dao.TypeProduitDao;
import com.sax.inc.coetegaz.Dao.VilleDao;
import com.sax.inc.coetegaz.DataBases.DatabaseManager;
import com.sax.inc.coetegaz.Entites.EAgence;
import com.sax.inc.coetegaz.Entites.ECommune;
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
import com.sax.inc.coetegaz.Utils.UtilsConnexionData;
import com.sax.inc.coetegaz.Utils.UtilsToast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;


public class ActivityFlashScream extends AppCompatActivity {

    private Button BtnSignin;
    String TAG="com.sax.inc.coetegaz.Activities.ActivityFlashScream";
    public static final int MULTIPLE_PERMISSIONS = 10;

    private SpinKitView progress_load;
    private FirebaseAuth mAuth;
    private boolean b=false;

   /* String[] permissions= new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.CAMERA,
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION};*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_screen);

        mAuth = FirebaseAuth.getInstance();


        String conf = Preferences.get(Keys.PreferencesKeys.CONFIG_IP);
         b=false;
        if(conf==null)
        {
            Preferences.save(Keys.PreferencesKeys.CONFIG_IP,"195.110.35.241");
            Preferences.save(Keys.PreferencesKeys.CONFIG_PORT,"80");
            b=true;
        }

        String date_inf = Preferences.get(Keys.PreferencesKeys.TIME_STAMP_REF);
        if(date_inf==null)
        {
            Preferences.save(Keys.PreferencesKeys.TIME_STAMP_REF,"2000-03-30 12:05:08.347075");
        }

        initialiseWidget();

     /*   Dexter.withActivity(this).withPermissions(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.CAMERA,
                Manifest.permission.VIBRATE,
                Manifest.permission.CALL_PHONE
        ).withListener(new MultiplePermissionsListener() {
            @Override
            public void onPermissionsChecked(MultiplePermissionsReport report) {*//* ... *//*}

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }

        }).check();*/


    }



    private void initialiseWidget()
    {
        progress_load =findViewById(R.id.spin_kit_load);
    }

    private void events(){

    }

    @Override
    protected void onStart() {
        super.onStart();
        events();

        String load = Preferences.get(Keys.PreferencesKeys.LOAD_DATA_FIRST);
        if (load == null){
            loaddata();
        }else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    Intent intent=new Intent(ActivityFlashScream.this,ActivityLogin.class);
                    startActivity(intent);
                    finish();


                }
            },3000);
        }
    }

    private void loaddata()
    {
                progress_load.setVisibility(View.VISIBLE);

                        Boolean connect = UtilsConnexionData.isConnected(ActivityFlashScream.this);
                        //connexion existante
                        if(connect){

                            //chargement des éléments
                            loadElement();
                        }
                        //pas de connexion
                        else {
                            progress_load.setVisibility(View.GONE);
                            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityFlashScream.this)
                                    .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                    .setTitle("Pas de connexion")
                                    .setCancelable(false)
                                    .setMessage("Problème de connexion survenu, veuillez la vérifier.")
                                    .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                            finish();
                                        }
                                    });

                            builder.show(); // Show
                        }





    }

    private void loadElement(){

        //
        EServeur eServeur= UtilEServeur.getServeur();

        HttpRequest.getAllEntiteAdmin_v1(ActivityFlashScream.this, eServeur, new HttpCallbackString() {
            @Override
            public void onSuccess(String response) {

                try {
                    //parsing
                    JSONObject jsonObject = new JSONObject(response);
                    int result = jsonObject.getInt("result");
                    if(result == 200) {

                        JSONArray province = jsonObject.getJSONArray("province");
                        //parsing de l'objet Entité venant du serveur sql distant
                        DatabaseManager.clearProvince();
                        //les provinces
                        for(int i = 0; i<province.length();i++){
                            JSONObject object = province.getJSONObject(i);
                            EProvince eProvince =  new Gson().fromJson(object.toString(),EProvince.class);
                            eProvince.setId_native(eProvince.getId());

                            ProvinceDao.create(eProvince);
                        }
                        //
                        DatabaseManager.clearVille();
                        //les villes
                        JSONArray ville = jsonObject.getJSONArray("ville");
                        for(int i = 0; i<ville.length();i++){
                            JSONObject object = ville.getJSONObject(i);
                            EVille eVille =  new Gson().fromJson(object.toString(),EVille.class);
                            eVille.setId_native(eVille.getId());

                            VilleDao.create(eVille);

                        }

                        //
                        DatabaseManager.clearCommune();
                        //les communes
                        JSONArray commune = jsonObject.getJSONArray("commune");
                        for(int i = 0; i<commune.length();i++){
                            JSONObject object = commune.getJSONObject(i);
                            ECommune eCommune =  new Gson().fromJson(object.toString(),ECommune.class);
                            eCommune.setId_native(eCommune.getId());

                            CommuneDao.create(eCommune);


                        }

                        //
                        DatabaseManager.clearQuartier();
                        //les quartiers
                        JSONArray quartier = jsonObject.getJSONArray("quartier");
                        for(int i = 0; i<quartier.length();i++){
                            JSONObject object = quartier.getJSONObject(i);
                            EQuartier eQuartier =  new Gson().fromJson(object.toString(),EQuartier.class);
                            eQuartier.setId_native(eQuartier.getId());

                            QuartierDao.create(eQuartier);

                        }

                        //
                        DatabaseManager.clearTypeProduit();
                        //les types de Produit
                        JSONArray typeproduit = jsonObject.getJSONArray("typeproduit");
                        for(int i = 0; i<typeproduit.length();i++){
                            JSONObject object = typeproduit.getJSONObject(i);
                            ETypeProduit eTypeProduit =  new Gson().fromJson(object.toString(), ETypeProduit.class);
                            /////eTypeProduit.setIdnative(eTypeProduit.getId());

                            TypeProduitDao.create(eTypeProduit);

                        }

                        //
                        DatabaseManager.clearAgence();
                        //les types de Produit
                        JSONArray agence = jsonObject.getJSONArray("agence");
                        for(int i = 0; i<agence.length();i++){
                            JSONObject object = agence.getJSONObject(i);
                            EAgence eAgence =  new Gson().fromJson(object.toString(), EAgence.class);
                            //mettre le status à 2 lors du chargement
                            eAgence.setStatus(2);
                            AgenceDao.create(eAgence);

                        }


                        Preferences.save(Keys.PreferencesKeys.LOAD_DATA_FIRST,"YES");
                        progress_load.setVisibility(View.GONE);
                        //
                        Intent signin = new Intent(ActivityFlashScream.this, ActivityLogin.class);
                        startActivity(signin);
                        finish();

                    }

                    else {
                        //les éléments pas chargées
                        progress_load.setVisibility(View.GONE);
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String message) {
                progress_load.setVisibility(View.GONE);
                UtilsToast.showToast(ActivityFlashScream.this,"Le chargement des données n'a pas pu abouti. merci!", ETypeMessage.ERROR);
            }
        });

    }




}

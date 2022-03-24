package com.sax.inc.coetegaz.Activities;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.sax.inc.coetegaz.Dao.AgenceDao;
import com.sax.inc.coetegaz.Dao.UserDao;
import com.sax.inc.coetegaz.DataBases.DatabaseManager;
import com.sax.inc.coetegaz.Entites.EAgence;
import com.sax.inc.coetegaz.Entites.EServeur;
import com.sax.inc.coetegaz.Entites.EUser;
import com.sax.inc.coetegaz.Memory.Constant;
import com.sax.inc.coetegaz.Memory.Keys;
import com.sax.inc.coetegaz.Memory.Preferences;
import com.sax.inc.coetegaz.NetWork.HttpRequest;
import com.sax.inc.coetegaz.R;
import com.sax.inc.coetegaz.Utils.HttpCallbackString;
import com.sax.inc.coetegaz.Utils.UtilEServeur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import customfonts.MyTextView_Roboto_Regular;


public class ActivityLogin extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private MyTextView_Roboto_Regular Btconnecte;

    private EditText edit_pass,edit_adress;
    private SpinKitView progress_load;
    String TAG="com.sax.inc.coetegaz.Activities.ActivityLogin";
    private View base;
    //pour le manipuler tout le long de l'application
    private String email,pass_word,pseudo,profil;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login);
        initialiseWidget();

        mAuth = FirebaseAuth.getInstance();

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Dexter.withActivity(this).withPermissions(
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
            public void onPermissionsChecked(MultiplePermissionsReport report) {/* ... */}

            @Override
            public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {

            }

        }).check();

    }

    private void initialiseWidget() {
        Btconnecte=findViewById(R.id.Btconnect);
        edit_pass=findViewById(R.id.edit_pass);
        edit_adress=findViewById(R.id.edit_adress);
        base=findViewById(R.id.base);
        progress_load=findViewById(R.id.spin_kit_load);
        progress_load.setVisibility(View.GONE);
    }


    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        event();

    }

    @Override
    protected void onResume() {
        super.onResume();

        //redirection du user ici

    }

    private void event(){

        Btconnecte.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                 pass_word=edit_pass.getText().toString().trim();
                 email=edit_adress.getText().toString().trim();

                if(pass_word.equals("")||  email.equals(""))
                {
                    if(pass_word.equals("") )
                    {
                        edit_pass.setError(ActivityLogin.this.getResources().getString(R.string.txt_requid));
                    }
                    if(email.equals(""))
                    {
                        edit_adress.setError(ActivityLogin.this.getResources().getString(R.string.txt_requid));

                    }

                    return;
                }
                else
                {
                    if(pass_word.length()<6)
                    {
                        String message="Votre mot de passe doit avoir au moins 6 caractères";
                        edit_pass.setError(message);

                        return;
                    }

                    String finish = Preferences.get(Keys.PreferencesKeys.FINISH_AUTH);


                    if(finish == null){
                        //authentification firebase
                        authentificationFireBase();

                    }
                    else {
                        //authentification en local
                        authentificationLocale();

                    }


                }

            }
        });
    }

    private void authentificationFireBase(){
        progress_load.setVisibility(View.VISIBLE);
        Btconnecte.setEnabled(false);
        final String mail = email+"@coetegaz.cd";
        mAuth.signInWithEmailAndPassword(mail, pass_word)
                .addOnCompleteListener(ActivityLogin.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                           // FirebaseUser user = mAuth.getCurrentUser();
                           // String s1= user.getUid();
                           // goToNextActivity();
                            //c'est bon, utilisateur active
                             recuperationUser();

                        } else {
                            progress_load.setVisibility(View.GONE);
                            Btconnecte.setEnabled(true);
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Snackbar.make(base,"Pseudo ou mot de passe incorrect", Snackbar.LENGTH_LONG).show();

                        }
                    }
                });
    }

    private void authentificationLocale(){

        progress_load.setVisibility(View.VISIBLE);
        EUser eUser=new EUser();
        eUser.setPseudo(email);
        eUser.setPassword(pass_word.trim());

        if(checkingLocal(eUser))
        {
            progress_load.setVisibility(View.GONE);
            goToNextActivity();

        }
        else
        {
            progress_load.setVisibility(View.GONE);
            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityLogin.this)
                    .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                    .setTitle("Authentification echouée")
                    .setCancelable(false)
                    .setMessage("Pseudo ou mot de passe incorrect")
                    .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            Btconnecte.setEnabled(true);
                        }
                    });

            builder.show(); // Show
        }
    }

    private void recuperationUser(){
        //
        EServeur serveur= UtilEServeur.getServeur();

        HttpRequest.getUserByPseudo(ActivityLogin.this, serveur, new String[]{email}, new HttpCallbackString() {
            @Override
            public void onSuccess(String response) {
                try {
                    //parsing
                    EUser user = null;
                    JSONObject jsonObject = new JSONObject(response);
                    int result = jsonObject.getInt("result");
                    if(result == 200) {

                        JSONArray data = jsonObject.getJSONArray("data");
                        JSONObject object = data.getJSONObject(0);
                        user = new Gson().fromJson(object.toString(), EUser.class);
                        profil = user.getProfil();

                        DatabaseManager.clearUser();
                        Boolean b = UserDao.create(user);
                        if(b){
                            //save elements of user
                            Preferences.save(Keys.PreferencesKeys.PROFIL_USER,profil);
                            Preferences.save(Keys.PreferencesKeys.STORE_PSEUDO_USER,user.getPseudo());
                            Preferences.save(Keys.PreferencesKeys.STORE_ID_USER,user.getIdnative());

                            if(profil.equals(Constant.SUPERADMIN)){
                                Preferences.save(Keys.PreferencesKeys.FINISH_AUTH,"YES");
                                goToNextActivity();
                            }else{
                                //recupère agence et la garde en local
                                recuperationAgenceUser(user.getAgenceref());
                            }


                        }

                    }
                    else {
                        progress_load.setVisibility(View.GONE);
                        //
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityLogin.this)
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                .setTitle("Echec !")
                                .setCancelable(false)
                                .setMessage("Authentification echouée, réessayez tout en vérifiant vos coordonnées.")
                                .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        Btconnecte.setEnabled(true);


                                    }
                                });

                        builder.show(); // Show
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                    progress_load.setVisibility(View.GONE);
                    //
                    CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityLogin.this)
                            .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                            .setTitle("Echec !")
                            .setCancelable(false)
                            .setMessage("Authentification echouée, réessayez tout en vérifiant vos coordonnées.")
                            .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    Btconnecte.setEnabled(true);


                                }
                            });

                    builder.show(); // Show

                        }

            }

            @Override
            public void onError(String message) {
                progress_load.setVisibility(View.GONE);
                //
                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityLogin.this)
                        .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                        .setTitle("Echec !")
                        .setCancelable(false)
                        .setMessage("Authentification echoué, réessayez tout en vérifiant votre connexion internet.")
                        .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                Btconnecte.setEnabled(true);


                            }
                        });

                builder.show(); // Show

            }
        });
    }

    private void recuperationAgenceUser(final int agenceRef){
        String idnative = String.valueOf(agenceRef);
        //
        EServeur serveur= UtilEServeur.getServeur();
        HttpRequest.getAgence(ActivityLogin.this, serveur, new String[]{idnative}, new HttpCallbackString() {
            @Override
            public void onSuccess(String response) {
                try {
                    //parsing
                    EAgence agence = null;
                    JSONObject jsonObject = new JSONObject(response);
                    int result = jsonObject.getInt("result");
                    if(result == 200) {

                        JSONArray data = jsonObject.getJSONArray("data");
                        JSONObject object = data.getJSONObject(0);
                        agence = new Gson().fromJson(object.toString(), EAgence.class);
                        //difference entre agence mère et agence de recherche
                        agence.setStatus(1);

                        Boolean b = AgenceDao.create(agence);
                        if(b){
                            //save elements
                            Preferences.save(Keys.PreferencesKeys.FINISH_AUTH,"YES");
                            Preferences.save(Keys.PreferencesKeys.STORE_ID_AGENCE,agence.getIdnative());
                            Preferences.save(Keys.PreferencesKeys.STORE_NAME_AGENCE,agence.getName());
                            //operations finales
                            progress_load.setVisibility(View.GONE);
                            goToNextActivity();
                            Btconnecte.setEnabled(true);
                        }
                        //

                    }
                    else {
                        //
                        progress_load.setVisibility(View.GONE);
                        Btconnecte.setEnabled(true);
                    }

                } catch (JSONException e){
                    e.printStackTrace();
                    //
                    progress_load.setVisibility(View.GONE);
                    Btconnecte.setEnabled(true);
                }

            }

            @Override
            public void onError(String message) {
                //
                progress_load.setVisibility(View.GONE);
                Btconnecte.setEnabled(true);

            }
        });
    }

    private boolean checkingLocal(EUser eUser)
    {

        if(eUser!=null) // on verifie si l'utilisateur existe dans le système ( En local )
        {
            EUser o= UserDao.getUser(eUser);

            if(o==null)
            {
                return false;
            }
            else
            {
                profil = o.getProfil();
                return true;
            }

        }
        else
        {
            return false;
        }
    }

    private void goToNextActivity() {
       // Garder le profil

        if (profil.equals(Constant.SUPERADMIN)) {
            //super admin screen or Activity
            Intent intent=new Intent(ActivityLogin.this, ActivityHomeAdmin.class);
            startActivity(intent);
            finish();

          }

        else if (profil.equals(Constant.MANAGER)){
              //manager screen or Activity
              Intent intent=new Intent(ActivityLogin.this, ActivityHomeOtherUser.class);
              startActivity(intent);
              finish();

        }

        else if (profil.equals(Constant.CAISSIER)) {
              //caissier screen or Activity
             Intent intent=new Intent(ActivityLogin.this, ActivityHomeOtherUser.class);
              startActivity(intent);
              finish();
        }


    }

}

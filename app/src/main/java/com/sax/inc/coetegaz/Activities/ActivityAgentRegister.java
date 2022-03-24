package com.sax.inc.coetegaz.Activities;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RelativeLayout;

import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sax.inc.coetegaz.Dao.AgenceDao;
import com.sax.inc.coetegaz.Dao.UserDao;
import com.sax.inc.coetegaz.Entites.EAgence;
import com.sax.inc.coetegaz.Entites.EServeur;
import com.sax.inc.coetegaz.Entites.EUser;
import com.sax.inc.coetegaz.Memory.Constant;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import static com.sax.inc.coetegaz.Utils.UtilsToast.showCFAlertDialog;

public class ActivityAgentRegister extends AppCompatActivity {

    //variables réelles
    private View id_view_register_steep_on,id_view_register_two;
    private EditText edt_name_complet,edt_phone,edt_email
            ,edit_pseudo,edit_pass,edit_pass_confirm;
    private FirebaseAuth mAuth;

    private SearchableSpinner spinner_genre;

    private ImageView circle1,circle2;
    private Button BtSave,BtPreview;

    private RelativeLayout bottom;
    private SpinKitView spin_kit_load_save;
    private int FocusActivity = 0;
    private AutoCompleteTextView autoCptAgence;
    private EUser eUser;
    private String mGenre;
    List<String> list_agences;
    private String mProfil="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_agent_register);
        //cast
        initView();
        //factorize methods
        event();

        initialiseLists();

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
        edit_pseudo = findViewById(R.id.edit_pseudo);
        edit_pass = findViewById(R.id.edit_pass);
        edit_pass_confirm = findViewById(R.id.edit_pass_confirm);
        autoCptAgence = findViewById(R.id.autoCptAgence);

        // SearchableSpinner
        spinner_genre = findViewById(R.id.spinner_genre);


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

                        boolean b=isEmptyFields(new Object[]{edt_name_complet,edt_phone});

                        if(mGenre.equals(""))
                        {
                            showCFAlertDialog(ActivityAgentRegister.this,"Info","Vous devez signaler le genre de la personne ( Homme ou femme ? )", ETypeMessage.ERROR);
                            return;
                        }
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

                        Boolean connect = UtilsConnexionData.isConnected(ActivityAgentRegister.this);
                        //connexion existante

                        String nom = edt_name_complet.getText().toString();
                        String tel = edt_phone.getText().toString();
                        String email = edt_email.getText().toString();
                        String agence = autoCptAgence.getText().toString();

                        String pass = edit_pass.getText().toString();
                        String confirmPass = edit_pass_confirm.getText().toString();
                        String pseudo = edit_pseudo.getText().toString();

                        if(connect){

                            boolean b_finish=isEmptyFields(new Object[]{edit_pass,edit_pseudo});

                            if(!b_finish)
                            {
                                // Traitement

                                if(pass.length() < 6)
                                {
                                    showCFAlertDialog(ActivityAgentRegister.this,"Info","Votre mot de passe doit avoir au moins 6 caractères", ETypeMessage.ERROR);
                                    return;
                                }

                                if(confirmPass.length() < 6)
                                {
                                    showCFAlertDialog(ActivityAgentRegister.this,"Info","Votre mot de passe de confirmation doit avoir au moins 6 caractères", ETypeMessage.ERROR);
                                    return;

                                }

                                if (!pass.equals(confirmPass)) {

                                    showCFAlertDialog(ActivityAgentRegister.this,"Info","Les deux mot de passes doivent être identiques", ETypeMessage.ERROR);
                                    return;
                                }

                                if(mProfil.equals(""))
                                {
                                    showCFAlertDialog(ActivityAgentRegister.this,"Info","Vous devez affecter un profil à cet agent", ETypeMessage.ERROR);
                                    return;
                                }

                                    eUser =new EUser();

                                    // steep on
                                    eUser.setName(nom);
                                    eUser.setTelephone(tel);
                                    eUser.setEmail(email);
                                    eUser.setGenre(mGenre);
                                    eUser.setPassword(pass);
                                    eUser.setPseudo(pseudo);

                                    EAgence agence1=AgenceDao.get(agence);
                                    eUser.setAgenceref(agence1.getIdnative());
                                    eUser.setProfil(mProfil);

                                    // steep two


                                    //status 0:offline, 1 : onligne, ...
                                    eUser.setStatus(0);

                                    //others
                                    eUser.setDateupdate(UtilTimeStampToDate.getTimeStamp());
                                    eUser.setDatewrite(UtilTimeStampToDate.getTimeStamp());

                                    insertToSQLRemoteServer();

                            }
                        }
                        else {
                            showCFAlertDialog(ActivityAgentRegister.this,"Pas de connexion",
                                    "Problème de connexion survenu, veuillez la vérifier.",ETypeMessage.ERROR);
                        }

                        break;


                    default:

                        break;
                }
            }
        });

        spinner_genre.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                mGenre=spinner_genre.getSelectedItem().toString();
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
        //
        EServeur eServeur= UtilEServeur.getServeur();
        //préparation de gson
        //sérialisation de l'objet Donneur en json
        // Gson gson_detail = new GsonBuilder().setPrettyPrinting().create();
        Gson gson_detail = new GsonBuilder().serializeNulls().create();;
        final   String param= gson_detail.toJson(eUser);

        HttpRequest.addingUser(ActivityAgentRegister.this, eServeur, new String[]{param}, new HttpCallbackString() {
            @Override
            public void onSuccess(String response) {
                try {
                    //parsing... c'est ici qu'on va lancer :insertToFirestoreAndSQLORMlite
                    JSONObject jsonObject = new JSONObject(response);
                    //parsing
                    int result = jsonObject.getInt("result");
                    if(result == 200) {
                        JSONArray jsonArray = jsonObject.getJSONArray("data");
                        //parsing de l'objet Entité venant du serveur sql distant
                        JSONObject object = jsonArray.getJSONObject(0);
                        //desérialisation de json en objet donneur
                        final  EUser o = new Gson().fromJson(object.toString(),EUser.class);
                        o.setIdnative(o.getId());
                        o.setStatus(1);

                        //actions
                        mAuth.createUserWithEmailAndPassword(o.getPseudo()+"@coetegaz.cd",o.getPassword()).addOnCompleteListener(ActivityAgentRegister.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    UserDao.create(o);
                                    spin_kit_load_save.setVisibility(View.GONE);
                                    onBackPressed();
                                }
                                else {
                                    if (task.getException() instanceof FirebaseAuthUserCollisionException) {
                                        //
                                        spin_kit_load_save.setVisibility(View.GONE);
                                        //
                                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityAgentRegister.this)
                                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                                .setTitle("Echec !")
                                                .setCancelable(false)
                                                .setMessage("Désolé car cet utilisateur existe déjà.")
                                                .addButton("D'accord", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                                    @Override
                                                    public void onClick(DialogInterface dialog, int which) {
                                                        dialog.dismiss();

                                                    }
                                                });

                                        builder.show(); // Show
                                    }
                                }


                            }
                        });



                    }
                    else {
                        //Echec de l'insert
                        spin_kit_load_save.setVisibility(View.GONE);
                        bottom.setVisibility(View.VISIBLE);
                        //
                        showCFAlertDialog(ActivityAgentRegister.this,"Opération non aboutie !",
                                "Le processus de création de compte n'est pas terminé complètement, veuillez réessayer.",ETypeMessage.ERROR);
                    }

                } catch (JSONException e) {
                    spin_kit_load_save.setVisibility(View.GONE);
                    bottom.setVisibility(View.VISIBLE);
                    showCFAlertDialog(ActivityAgentRegister.this,"Opération non aboutie !",
                            "Le processus de création de compte n'est pas terminé complètement, veuillez réessayer.",ETypeMessage.ERROR);
                }

            }

            @Override
            public void onError(String message) {

                //Echec de l'insert
                spin_kit_load_save.setVisibility(View.GONE);
                bottom.setVisibility(View.VISIBLE);
                showCFAlertDialog(ActivityAgentRegister.this,"Opération non aboutie !",
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
                circle1.setBackground(ActivityAgentRegister.this.getResources().getDrawable(R.drawable.circlebarre_focus));
                circle2.setBackground(ActivityAgentRegister.this.getResources().getDrawable(R.drawable.circlebarre));

                FocusActivity=position;
                break;

            case 1:

                circle1.setBackground(ActivityAgentRegister.this.getResources().getDrawable(R.drawable.circlebarre));
                circle2.setBackground(ActivityAgentRegister.this.getResources().getDrawable(R.drawable.circlebarre_focus));
                FocusActivity = position;
                break;
            default:

                break;

        }


    }



    private void refresh(SearchableSpinner spinner, List<String> list) {
        ArrayAdapter<String> adp = new ArrayAdapter<String>(ActivityAgentRegister.this,android.R.layout.simple_dropdown_item_1line,list);
        spinner.setAdapter(adp);
    }

    private void loadLists() {

        List<String> genre=new ArrayList<>();
        genre.add("Homme");
        genre.add("Femme");
        refresh(spinner_genre,genre);

        list_agences =new ArrayList<>();
        list_agences.clear();

        List<EAgence> eAgences= AgenceDao.getAll();

        for (EAgence eAgence:eAgences) {
            list_agences.add(eAgence.getName());
        }

        //Creating the instance of ArrayAdapter containing list of fruit names
        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this, android.R.layout.select_dialog_item, list_agences);
        //Getting the instance of AutoCompleteTextView

        autoCptAgence.setThreshold(1);//will start working from first character
        autoCptAgence.setAdapter(adapter);//setting the adapter data into the AutoCompleteTextView
        autoCptAgence.setTextColor(Color.GREEN);




    }

    private void initialiseLists() {
        loadLists();
    }


    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch(view.getId()) {
            case R.id.rdManager:
                if (checked)
                    mProfil= Constant.MANAGER;
                break;
            case R.id.rdCaissier:
                if (checked)
                    mProfil= Constant.CAISSIER;
                break;
        }
    }


}

package com.sax.inc.coetegaz.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.blikoon.qrcodescanner.QrCodeActivity;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.github.ybq.android.spinkit.SpinKitView;
import com.google.gson.Gson;
import com.kaopiz.kprogresshud.KProgressHUD;
import com.sax.inc.coetegaz.Dao.ClientDao;
import com.sax.inc.coetegaz.Entites.EClient;
import com.sax.inc.coetegaz.Entites.EServeur;
import com.sax.inc.coetegaz.NetWork.HttpRequest;
import com.sax.inc.coetegaz.R;
import com.sax.inc.coetegaz.Utils.ETypeMessage;
import com.sax.inc.coetegaz.Utils.HttpCallbackString;
import com.sax.inc.coetegaz.Utils.RequestCode;
import com.sax.inc.coetegaz.Utils.UtilEServeur;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import androidx.appcompat.app.AppCompatActivity;

import static com.sax.inc.coetegaz.Utils.UtilsToast.showCFAlertDialog;

public class ActivityClientSearch extends AppCompatActivity {

    private String value_decode;
    private boolean checkScan=false;
    private EditText edt_Id;
    private ImageButton bt_search_qr;
    private SpinKitView progress_load;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_client_search);
        //à enlever
        setTitle("Recherche client");
        initialiseWidget();
        event();


    }

    private void initialiseWidget() {
        bt_search_qr=findViewById(R.id.bt_search_qr);
        edt_Id=findViewById(R.id.edt_Id);
        progress_load=findViewById(R.id.spin_kit_load);
        progress_load.setVisibility(View.GONE);
    }
    private void event()
    {
        bt_search_qr.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!checkScan)
                {
                    Intent intent=new Intent(ActivityClientSearch.this, QrCodeActivity.class);
                    startActivityForResult(intent, RequestCode.REQUEST_CODE_QR_SCAN);
                }
            }
        });

        edt_Id.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String value=edt_Id.getText().toString().trim();
                    if(value.equals("")){
                        edt_Id.setError("Ce champ est obligatoire");
                    }else{
                        searchByToken(value);
                    }
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onStart() {

        super.onStart();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (resultCode == RESULT_OK) {
                switch (requestCode) {
                    case RequestCode.REQUEST_CODE_QR_SCAN:

                        value_decode= data.getExtras().getString("com.blikoon.qrcodescanner.got_qr_scan_relult");
                        searchByQR(value_decode);
                        break;

                    default:
                        checkScan=true;
                        break;
                }
            }
            else
            {
                onBackPressed();
            }
        } catch (Exception e) {
            e.printStackTrace();
            showCFAlertDialog(ActivityClientSearch.this,"Scanneur non allumé",
                    "Désolé ! Veuillez Réessayez. Merci.", ETypeMessage.ERROR);
        }
    }

    private void searchByQR(String value){

        final KProgressHUD hud = KProgressHUD.create(ActivityClientSearch.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Veuillez patienter")
                .setDetailsLabel("Recherche encours ...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

     EServeur serveur = UtilEServeur.getServeur();
     HttpRequest.getQrClient(ActivityClientSearch.this, serveur, new String[]{value}, new HttpCallbackString() {
         @Override
         public void onSuccess(String response) {
             try {
                 JSONObject jsonObject = new JSONObject(response);
                 int result = jsonObject.getInt("result");
                 if(result == 200) {
                     EClient client = null;
                     JSONArray array = jsonObject.getJSONArray("data");
                     for (int i = 0; i < array.length(); i++) {

                         JSONObject object = array.getJSONObject(i);
                         client = new Gson().fromJson(object.toString(), EClient.class);

                         EClient clientExist = ClientDao.getByToken(client.getToken());
                         if (clientExist != null) {
                             //le mettre jour
                             ClientDao.update(client);
                         } else {
                             //le créer
                             ClientDao.create(client);
                         }


                     }
                     //arret de la progression
                     hud.dismiss();

                     if(client!=null){
                         Intent go = new Intent(ActivityClientSearch.this, ActivityClientDetail.class);
                         go.putExtra("clientid", client.getIdnative());
                         startActivity(go);
                     }else {

                         hud.dismiss();
                         CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityClientSearch.this)
                                 .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                 .setTitle("Infos")
                                 .setCancelable(false)
                                 .setMessage("Aucun Client portant ce code QR n'a été trouvé!")
                                 .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                     @Override
                                     public void onClick(DialogInterface dialog, int which) {
                                         dialog.dismiss();
                                         onBackPressed();
                                     }
                                 });

                         builder.show(); // Show
                     }

                 }
                 else {
                     //
                     hud.dismiss();
                     CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityClientSearch.this)
                             .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                             .setTitle("Infos")
                             .setCancelable(false)
                             .setMessage("Aucun client portant ce code QR n'a été trouvé!")
                             .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                 @Override
                                 public void onClick(DialogInterface dialog, int which) {
                                     dialog.dismiss();
                                     onBackPressed();
                                 }
                             });

                     builder.show(); // Show
                 }

             }catch (JSONException e){
                 e.printStackTrace();
             }
         }

         @Override
         public void onError(String message) {

         }
     });

    }

    private void searchByToken(String value){

        final KProgressHUD hud = KProgressHUD.create(ActivityClientSearch.this)
                .setStyle(KProgressHUD.Style.SPIN_INDETERMINATE)
                .setLabel("Veuillez patienter")
                .setDetailsLabel("Recherche encours ...")
                .setCancellable(false)
                .setAnimationSpeed(2)
                .setDimAmount(0.5f)
                .show();

        EServeur serveur = UtilEServeur.getServeur();
        HttpRequest.getTokenClient(ActivityClientSearch.this, serveur, new String[]{value}, new HttpCallbackString() {
            @Override
            public void onSuccess(String response) {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    int result = jsonObject.getInt("result");
                    if(result == 200) {
                        EClient client = null;
                        JSONArray array = jsonObject.getJSONArray("data");
                        for (int i = 0; i < array.length(); i++) {

                            JSONObject object = array.getJSONObject(i);
                            client = new Gson().fromJson(object.toString(), EClient.class);

                            EClient clientExist = ClientDao.getByToken(client.getToken());
                            if (clientExist != null) {
                                //le mettre jour
                                ClientDao.update(client);
                            } else {
                                //le créer
                                ClientDao.create(client);
                            }


                        }
                        //arret de la progression
                        hud.dismiss();

                        if(client!=null){
                            Intent go = new Intent(ActivityClientSearch.this, ActivityClientDetail.class);
                            go.putExtra("clientid", client.getIdnative());
                            startActivity(go);
                        }else {

                            hud.dismiss();
                            CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityClientSearch.this)
                                    .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                    .setTitle("Infos")
                                    .setCancelable(false)
                                    .setMessage("Aucun Client portant ce token coete gaz n'a été trouvé!")
                                    .addButton("Réessayer", -1, -1, CFAlertDialog.CFAlertActionStyle.NEGATIVE, CFAlertDialog.CFAlertActionAlignment.CENTER, new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });

                            builder.show(); // Show
                        }

                    }
                    else {
                        //
                        hud.dismiss();
                        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityClientSearch.this)
                                .setDialogStyle(CFAlertDialog.CFAlertStyle.BOTTOM_SHEET)
                                .setTitle("Infos")
                                .setCancelable(false)
                                .setMessage("Aucun client portant ce token coete gaz n'a été trouvé!")
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
                }
            }

            @Override
            public void onError(String message) {
                //
                edt_Id.setText("");
                hud.dismiss();
                CFAlertDialog.Builder builder = new CFAlertDialog.Builder(ActivityClientSearch.this)
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

}

package com.sax.inc.coetegaz.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;


import com.sax.inc.coetegaz.R;
import com.sax.inc.coetegaz.Utils.ETypeMessage;
import com.sax.inc.coetegaz.Utils.UtilsToast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;


public class ActivityAbout extends AppCompatActivity {

    private TextView txt_mail,txt_tel;
    private ImageView bt_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        setTitle("A propos");


        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

       initializeWidget();
       event();

    }

    @Override
    protected void onStart() {
        super.onStart();

    }

    private  void initializeWidget()
    {
        txt_mail =  findViewById(R.id.txt_mail);
        txt_tel =  findViewById(R.id.txt_tel);
        bt_back = findViewById(R.id.bt_back);

    }

    private  void event()
    {
        txt_mail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String uriText =
                        "mailto:serviceclient@cnssap.cd" +
                                "?subject=" + Uri.encode("Demande client") +
                                "&body=" + Uri.encode("");

                Uri uri = Uri.parse(uriText);

                Intent sendIntent = new Intent(Intent.ACTION_SENDTO);
                sendIntent.setData(uri);
                if (sendIntent.resolveActivity(getPackageManager()) != null) {
                    startActivity(Intent.createChooser(sendIntent, "Envoi email"));
                }
            }
        });

        txt_tel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onCallBtnClick();
            }
        });
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    private void onCallBtnClick(){
        if (Build.VERSION.SDK_INT < 23) {
            phoneCall();
        }else {

            if (ActivityCompat.checkSelfPermission(ActivityAbout.this,
                    Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {

                phoneCall();
            }else {
                final String[] PERMISSIONS_STORAGE = {Manifest.permission.CALL_PHONE};
                //Asking request Permissions
                ActivityCompat.requestPermissions(ActivityAbout.this, PERMISSIONS_STORAGE, 9);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        boolean permissionGranted = false;
        switch (requestCode) {
            case 9:
                permissionGranted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (permissionGranted) {
            phoneCall();
        } else {
            UtilsToast.showToast(ActivityAbout.this, "Vous devez autoriser au système la permission de passer un appel", ETypeMessage.ERROR);
        }

    }


    private void phoneCall() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:+243890204835"));
        startActivity(intent);
    }
}

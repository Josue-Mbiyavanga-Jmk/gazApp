package com.sax.inc.coetegaz.Activities;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.sax.inc.coetegaz.R;
import com.sax.inc.coetegaz.Utils.QRCodeUtil;

import androidx.appcompat.app.AppCompatActivity;

public class ActivityClientViewQR extends AppCompatActivity {

    private ImageView bt_back,imag_qr_code;
    private int qr;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_qr);
        initView();
        event();
      //  try {
            Bundle bundle = getIntent().getExtras();
            if (bundle!= null) {
               // qr = Integer.parseInt(bundle.getString("QR"));
                qr = bundle.getInt("QR");
            }
       /* } catch (Exception e){
            e.printStackTrace();
        }*/


    }

    @Override
    protected void onResume() {
        super.onResume();
        viewQr();
    }

    private void initView(){

        imag_qr_code =findViewById(R.id.imag_qr_code);
        bt_back =  findViewById(R.id.bt_back);
    }


    private void viewQr(){
        Bitmap qrcodeBitmap = QRCodeUtil.createQRImage(qr+"",300,300,null);
        imag_qr_code.setImageBitmap(qrcodeBitmap);
    }


    private void event(){
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}

package com.sax.inc.coetegaz.NetWork;

import com.sax.inc.coetegaz.Entites.EAgence;
import com.sax.inc.coetegaz.Implements.IHttpRestrofit;
import com.sax.inc.coetegaz.Memory.Parameters;
import com.sax.inc.coetegaz.Utils.GenericObjet;

import java.io.IOException;

import retrofit2.Call;

public class HttpRetrofit {

    public  static GenericObjet<EAgence> addingAgence (String... param){
        IHttpRestrofit service=ServiceGenerator.createService(IHttpRestrofit.class);
        Call<GenericObjet<EAgence>> call= service.newAgence(Parameters.PAGE_REQUEST_ADD,param[0]);
        GenericObjet<EAgence> rst=null;
        try {
             rst=call.execute().body();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return rst;
    }
}

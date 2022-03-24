package com.sax.inc.coetegaz.Implements;

import com.sax.inc.coetegaz.Entites.EAgence;
import com.sax.inc.coetegaz.Memory.Parameters;
import com.sax.inc.coetegaz.Utils.GenericObjet;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IHttpRestrofit {

    @FormUrlEncoded
    @POST(Parameters.URL_REQUEST_POST_AGENCE_v1)
    Call<GenericObjet<EAgence>> newAgence(@Field("PAGE_REQUEST") String pageRequest,
                                          @Field("agence") String agence);

}

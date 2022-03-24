package com.sax.inc.coetegaz.NetWork;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkError;
import com.android.volley.NoConnectionError;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.ServerError;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.sax.inc.coetegaz.Entites.EServeur;
import com.sax.inc.coetegaz.Memory.Constant;
import com.sax.inc.coetegaz.Memory.Parameters;
import com.sax.inc.coetegaz.R;
import com.sax.inc.coetegaz.Utils.HttpCallback;
import com.sax.inc.coetegaz.Utils.HttpCallbackJSON;
import com.sax.inc.coetegaz.Utils.HttpCallbackString;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static com.sax.inc.coetegaz.Memory.Parameters.Config.ConfigSystem.IP;


/**
 * Created by kevin lukanga on 28/05/2019.
 */

public class HttpRequest {


    private static boolean isConnected(Context context) {
        boolean b=false;
        try {
            if (context != null) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                b= networkInfo != null && networkInfo.isConnected();
            }

        } catch (Exception e) {

            return false;
        }

        return b;
    }

    //les requetes http ci-dessous
    //Agence
    public static void addingAgence(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_AGENCE_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_ADD);
                    params.put("agence", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateAgence(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_AGENCE_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_UPDATE);
                    params.put("agence", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAgence(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_AGENCE_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_BY_ID);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAllAgence(Context context, EServeur eServeur, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_AGENCE_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_ALL);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getNameAgence(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_AGENCE_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_BY_NAME);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getQuartierAgence(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_AGENCE_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_QUARTIER);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getCommuneAgence(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_AGENCE_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_COMMUNE);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getCommuneTypeProduitAgence(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_AGENCE_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_COMMUNE_PRODUIT);
                    params.put("commune", param[0]);
                    params.put("typeproduit", param[1]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getVilleTypeProduitAgence(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_AGENCE_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_VILLE_PRODUIT);
                    params.put("ville", param[0]);
                    params.put("typeproduit", param[1]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getVilleAgence(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_AGENCE_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_VILLE);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getProvinceAgence(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_AGENCE_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_PROVINCE);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getProvinceTypeProduitAgence(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_AGENCE_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_PROVINCE_PRODUIT);
                    params.put("province", param[0]);
                    params.put("typeproduit", param[1]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAgenceByTypeProduit(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_AGENCE_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_AGENCE_BY_PRODUIT);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteAgence(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_AGENCE_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_DELETE);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Client
    public static void addingClient(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_CLIENT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_ADD);
                    params.put("client", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addingClientAbonnement(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_CLIENT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_ADD_v1);
                    params.put("client", param[0]);
                    params.put("agence", param[1]);
                    params.put("user", param[2]);
                    params.put("listabonnement", param[3].toString());
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void inscriptClient(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_CLIENT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_INSCRIPT);
                    params.put("client", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateTokenClient(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_CLIENT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_UPDATE_BY_TOKEN);
                    params.put("client", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateClient(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_CLIENT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_UPDATE);
                    params.put("client", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getTokenClient(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_CLIENT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_CLIENT_BY_TOKEN);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getQrClient(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_CLIENT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_CLIENT_BY_QR);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getClientByPseudo(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_CLIENT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_BY_PSEUDO);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getClient(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_CLIENT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_BY_ID);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAllClient(Context context, EServeur eServeur, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_CLIENT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_ALL);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteClient(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_CLIENT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_DELETE);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //TypeProduit
    public static void addingTypeProduit(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_TYPE_PRODUIT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_ADD);
                    params.put("typeproduit", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateTypeProduit(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_TYPE_PRODUIT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_UPDATE);
                    params.put("typeproduit", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getTypeProduit(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_TYPE_PRODUIT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_BY_ID);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAllTypeProduit(Context context, EServeur eServeur, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_TYPE_PRODUIT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_ALL);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getNameTypeProduit(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_TYPE_PRODUIT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_BY_NAME);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteTypeProduit(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_TYPE_PRODUIT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_DELETE);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //AgenceTypeProduit
    public static void addingAgenceTypeProduit(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_AGENCE_TYPE_PRODUIT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_ADD);
                    params.put("agencetypeproduit", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateAgenceTypeProduit(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_AGENCE_TYPE_PRODUIT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_UPDATE);
                    params.put("agencetypeproduit", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAgenceTypeProduit(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_AGENCE_TYPE_PRODUIT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_BY_ID);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAgenceTypeProduitByAgence(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_AGENCE_TYPE_PRODUIT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_BY_AGENCE);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteAgenceTypeProduit(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_AGENCE_TYPE_PRODUIT_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_DELETE);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //User
    public static void addingUser(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_USER_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_ADD);
                    params.put("user", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void updateUser(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_USER_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_UPDATE);
                    params.put("user", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAllUser(Context context, EServeur eServeur, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_USER_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_ALL);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getUser(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_USER_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_BY_ID);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getUserByPseudo(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_USER_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_BY_PSEUDO);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAgenceUser(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_USER_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_BY_AGENCE);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteUser(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_USER_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_DELETE);
                    params.put("value", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //MouvementStock
    public static void addingMouvement(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_MOUVEMENT_STOCK_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_ADD);
                    params.put("mouvement", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void addingMouvement_v1(Context context, EServeur eServeur, final String [] param, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_MOUVEMENT_STOCK_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_ADD_v1);
                    params.put("mouvement", param[0]);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //EntiteAdmin
    public static void getAllEntiteAdmin(Context context, EServeur eServeur, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_ENTITE_ADMIN_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_ALL);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getAllEntiteAdmin_v1(Context context, EServeur eServeur, HttpCallbackString callback) {
        try {
            StringRequest request = new StringRequest(Request.Method.POST,
                    IP(eServeur.getHOSTNAME(),eServeur.getPORT(), Constant.HTTP)+ Parameters.URL_REQUEST_POST_ENTITE_ADMIN_v1,
                    requestSuccessListener(callback),
                    requestErrorListener(context,callback))
            {
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<>();
                    params.put("PAGE_REQUEST", Parameters.PAGE_REQUEST_GET_ALL);
                    return params;
                }
            };
            request.setRetryPolicy(new DefaultRetryPolicy(
                    Parameters.VOLLEY_RETRY_POLICY,
                    DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                    DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

            MySingleton.getInstance(context).addToRequestQueue(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    //...
    //config volley
    private static Response.Listener<String> requestSuccessListener(final HttpCallback callback) {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        JSONObject data;

                        if(response.equals("[]"))
                        {
                            data=new JSONObject();
                            data.put("empty","1");
                        }
                        else
                        {
                            JSONArray jsonArray =new JSONArray(response);
                            data=jsonArray.getJSONObject(0);
                        }

                        try {

                        } catch (IllegalStateException e) {
                            String messageError = "Read JSON response Error !!!";
                            callback.onError(messageError);
                        }
                        callback.onSuccess(data);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private static Response.Listener<String> requestSuccessListener(final HttpCallbackString callback) {
        return new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                if (response != null) {
                    try {
                        callback.onSuccess(response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }

    private static Response.Listener<JSONObject> requestSuccessListener(final HttpCallbackJSON callback) {
        return new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                if (response != null) {
                    try {
                        callback.onSuccess(response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
    }
    private static Response.ErrorListener requestErrorListener(final Context mContext, final HttpCallbackString connexionCallback) {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                // Handle your error types accordingly.For Timeout & No connection error, you can show 'retry' button.
                // For AuthFailure, you can re login with user credentials.
                // For ClientError, 400 & 401, Errors happening on client side when sending api request.
                // In this case you can check how client is forming the api and debug accordingly.
                // For ServerError 5xx, you can do retry or handle accordingly.

                error.printStackTrace();

                String errorMessage = "";
                if (error instanceof NetworkError) {
                    errorMessage = mContext.getResources().getString(R.string.volley_network_error);
                } else if (error instanceof ServerError) {
                    errorMessage = mContext.getResources().getString(R.string.volley_server_error);
                } else if (error instanceof AuthFailureError) {
                    errorMessage = mContext.getResources().getString(R.string.volley_auth_error);
                } else if (error instanceof ParseError) {
                    errorMessage = mContext.getResources().getString(R.string.volley_parse_error);
                } else if (error instanceof NoConnectionError) {
                    errorMessage = mContext.getResources().getString(R.string.volley_no_connection_error);
                } else if (error instanceof TimeoutError) {
                    errorMessage = mContext.getResources().getString(R.string.volley_timeout_error);
                }


                connexionCallback.onError(errorMessage);

            }
        };
    }


}

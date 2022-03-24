package com.sax.inc.coetegaz.Memory;


public class Parameters {


    //SERVER DE PRODUCTION
    //SERVER DE PRODUCTION
    private static final String BASE_URL = Config.ConfigSystem.SERV_PROD_HOST;
    private static final String BASE_URL_RETROFIT = Config.ConfigSystem.SERV_PROD_HOST;
    public static String BASE_URL_PORT = "54.38.181.246:80";

    public static final int VOLLEY_RETRY_POLICY = 30000;

    /// URL AND PARAMETERS
    public static final String URL_REQUEST_POST_CLIENT_v1 = "ws_ctgz/api/services/SClient.php";
    public static final String URL_REQUEST_POST_CLIENT_v1_54 = "api_gaz/api/services/SClient.php";
    public static final String URL_REQUEST_POST_AGENCE_v1_54 = "api_gaz/api/services/SAgence.php";
    public static final String URL_REQUEST_POST_AGENCE_v1 = "ws_ctgz/api/services/SAgence.php";
    public static final String URL_REQUEST_POST_AGENCE_TYPE_PRODUIT_v1_54 = "api_gaz/api/services/SAgenceTypeProduit.php";
    public static final String URL_REQUEST_POST_AGENCE_TYPE_PRODUIT_v1 = "ws_ctgz/api/services/SAgenceTypeProduit.php";
    public static final String URL_REQUEST_POST_ENTITE_ADMIN_v1_54 = "api_gaz/api/services/SEntiteAdmin.php";
    public static final String URL_REQUEST_POST_ENTITE_ADMIN_v1 = "ws_ctgz/api/services/SEntiteAdmin.php";
    public static final String URL_REQUEST_POST_MOUVEMENT_STOCK_v1_54 =  "api_gaz/api/services/SMouvementStock.php";
    public static final String URL_REQUEST_POST_MOUVEMENT_STOCK_v1 =  "ws_ctgz/api/services/SMouvementStock.php";
    public static final String URL_REQUEST_POST_TYPE_PRODUIT_v1_54 = "api_gaz/api/services/STypeProduit.php";
    public static final String URL_REQUEST_POST_TYPE_PRODUIT_v1 = "ws_ctgz/api/services/STypeProduit.php";
    public static final String URL_REQUEST_POST_USER_v1_54 = "api_gaz/api/services/SUser.php";
    public static final String URL_REQUEST_POST_USER_v1 = "ws_ctgz/api/services/SUser.php";
    public static final String PAGE_REQUEST_GET="get";
    public static final String PAGE_REQUEST_GET_ALL="getAll";
    public static final String PAGE_REQUEST_GET_ALL_v1="getAll_v1";
    public static final String PAGE_REQUEST_ADD="add";
    public static final String PAGE_REQUEST_ADD_v1="add_v1";
    public static final String PAGE_REQUEST_INSCRIPT="inscript";
    public static final String PAGE_REQUEST_UPDATE="update";
    public static final String PAGE_REQUEST_UPDATE_BY_TOKEN="updateToken";
    public static final String PAGE_REQUEST_GET_QUARTIER="getByQuartier";
    public static final String PAGE_REQUEST_GET_COMMUNE="getByCommune";
    public static final String PAGE_REQUEST_GET_COMMUNE_PRODUIT="getByCommuneTypeProduit";
    public static final String PAGE_REQUEST_GET_VILLE="getByVille";
    public static final String PAGE_REQUEST_GET_VILLE_PRODUIT="getByVilleTypeProduit";
    public static final String PAGE_REQUEST_GET_PROVINCE="getByProvince";
    public static final String PAGE_REQUEST_GET_PROVINCE_PRODUIT="getByProvinceTypeProduit";
    public static final String PAGE_REQUEST_GET_BY_AGENCE="getByAgence";
    public static final String PAGE_REQUEST_GET_AGENCE_BY_PRODUIT="getByTypeProduit";
    public static final String PAGE_REQUEST_GET_BY_NAME="getByName";
    public static final String PAGE_REQUEST_GET_BY_ID="getByID";
    public static final String PAGE_REQUEST_GET_BY_PSEUDO="getByPseudo";
    public static final String PAGE_REQUEST_GET_CLIENT_BY_QR="getByQr";
    public static final String PAGE_REQUEST_GET_CLIENT_BY_TOKEN="getByToken";
    public static final String PAGE_REQUEST_DELETE="delete";

    public static class Config {


        public static class ConfigSystem {
            //LOCALHOST
            static final String LOCAL_HOST = "http://10.195.48.111/";
            static String LOCAL_HOST_PORT = "http://10.195.48.111:80/";


            static final String SERV_PROD_HOST = "http://vps63737.lws-hosting.com/";
            static final String SERV_PROD_PORT = "http://vps63737.lws-hosting.com:8090/";


            public  static String IP(String ip, String port, String protole)
            {
                return   LOCAL_HOST_PORT=protole+"://"+ip+":"+port+"/";
            }
        }


        //FTP DIRECTORY
        public static class DirectoryFTP {

        }
        //URL SERVICES
        public static class URL_SERVICE {
            public static final String URL_SERVICE_HOST = "dgrhu_ws/api/services/";

        }
    }



}
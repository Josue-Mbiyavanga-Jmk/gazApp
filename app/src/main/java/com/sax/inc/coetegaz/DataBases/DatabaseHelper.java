package com.sax.inc.coetegaz.DataBases;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import com.sax.inc.coetegaz.Entites.EAgence;
import com.sax.inc.coetegaz.Entites.EAgenceTypeProduit;
import com.sax.inc.coetegaz.Entites.EClient;
import com.sax.inc.coetegaz.Entites.ECommune;
import com.sax.inc.coetegaz.Entites.EMouvementStock;
import com.sax.inc.coetegaz.Entites.EProvince;
import com.sax.inc.coetegaz.Entites.EQuartier;
import com.sax.inc.coetegaz.Entites.ETypeProduit;
import com.sax.inc.coetegaz.Entites.EUser;
import com.sax.inc.coetegaz.Entites.EVille;


/**
 * Created by kevin lukanga 01/05/2019.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {
    //DatabaseHelper
    private static final String TAG = DatabaseHelper.class.getSimpleName();

    private static final String DATABASE_NAME = "internGAZ.db";
    private static final int DATABASE_VERSION = 1;

    //Data Access Object
    private Dao<EClient, String> eClientDao = null;
    private Dao<EAgence, String> eAgenceDao = null;
    private Dao<EAgenceTypeProduit, String> eAgenceTypeProduitDao = null;
    private Dao<EMouvementStock, String> eMouvementStockDao = null;
    private Dao<EUser, String> eUserDao = null;
    private Dao<ETypeProduit, String> eTypeProduitDao = null;

    private Dao<EProvince, String> eProvinceDao = null;
    private Dao<EVille, String> eVilleDao = null;
    private Dao<ECommune, String> eCommuneDao = null;
    private Dao<EQuartier, String> eQuartierDao = null;



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, EClient.class);
            TableUtils.createTable(connectionSource, EAgence.class);
            TableUtils.createTable(connectionSource, EAgenceTypeProduit.class);
            TableUtils.createTable(connectionSource, EMouvementStock.class);
            TableUtils.createTable(connectionSource, ETypeProduit.class);
            TableUtils.createTable(connectionSource, EUser.class);
            TableUtils.createTable(connectionSource, EQuartier.class);
            TableUtils.createTable(connectionSource, ECommune.class);
            TableUtils.createTable(connectionSource, EVille.class);
            TableUtils.createTable(connectionSource, EProvince.class);


        } catch (Exception e) {
            Log.e(TAG, "BD n'a pas été créée", e);
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase database, ConnectionSource connectionSource, int oldVersion, int newVersion) {

    }

    //customize
   public Dao<EClient, String> getClientDao() {
        if (null == eClientDao) {
            try {
                eClientDao = getDao(EClient.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return eClientDao;
    }

    public Dao<EAgence, String> getAgenceDao() {
        if (null == eAgenceDao) {
            try {
                eAgenceDao = getDao(EAgence.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return eAgenceDao;
    }

    public Dao<EAgenceTypeProduit, String> getAgenceTypeProduitDao() {
        if (null == eAgenceTypeProduitDao) {
            try {
                eAgenceTypeProduitDao = getDao(EAgenceTypeProduit.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return eAgenceTypeProduitDao;
    }

    public Dao<EMouvementStock, String> getMouvementStockDao() {
        if (null == eMouvementStockDao) {
            try {
                eMouvementStockDao = getDao(EMouvementStock.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return eMouvementStockDao;
    }

    public Dao<EUser, String> getUserDao() {
        if (null == eUserDao) {
            try {
                eUserDao = getDao(EUser.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return eUserDao;
    }


    public Dao<ETypeProduit, String> getTypeProduitDao() {
        if (null == eTypeProduitDao) {
            try {
                eTypeProduitDao = getDao(ETypeProduit.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return eTypeProduitDao;
    }

    public Dao<EQuartier, String> getQuartierDao() {
        if (null == eQuartierDao) {
            try {
                eQuartierDao = getDao(EQuartier.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return eQuartierDao;
    }

    public Dao<ECommune, String> getCommuneDao() {
        if (null == eCommuneDao) {
            try {
                eCommuneDao = getDao(ECommune.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return eCommuneDao;
    }


    public Dao<EVille, String> getVilleDao() {
        if (null == eVilleDao) {
            try {
                eVilleDao = getDao(EVille.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return eVilleDao;
    }

    public Dao<EProvince, String> getProvinceDao() {
        if (null == eProvinceDao) {
            try {
                eProvinceDao = getDao(EProvince.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return eProvinceDao;
    }

}

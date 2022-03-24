package com.sax.inc.coetegaz.DataBases;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

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

import java.sql.SQLException;

/**
 * Created by kevin lukanga 01/05/2019.
 */
@SuppressLint("NewApi")
public class DatabaseManager<T> {
    private static Context context;
    @SuppressWarnings("rawtypes")
    static private DatabaseManager instance;
    private DatabaseHelper helper;

    private DatabaseManager(Context ctx) {
        helper = new DatabaseHelper(ctx);
    }

    @SuppressWarnings("rawtypes")
    static public void init(Context ctx) {
        context = ctx;
        if (null == instance) {
            instance = new DatabaseManager(ctx);
        }
    }

    @SuppressWarnings("rawtypes")
    static public DatabaseManager getInstance() {
        if (null == instance) {
            instance = new DatabaseManager(context);
        }
        return instance;
    }

   /* public static boolean isFileExiste() {
        try {
            new FileOutputStream(DatabaseHelper.DB_PATH
                    + DatabaseHelper.DATABASE_NAME);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }*/

  /*  public static boolean isFileExist() {
        try {
            new FileOutputStream(DatabaseHelper.DB_PATH
                    + DatabaseHelper.DATABASE_NAME);
            return true;
        } catch (FileNotFoundException e) {
            return false;
        }
    }*/

    public static void clearProvince() {
        try {
            TableUtils.clearTable(DatabaseManager.getInstance().getConnectionSource(), EProvince.class);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearVille() {
        try {
            TableUtils.clearTable(DatabaseManager.getInstance().getConnectionSource(), EVille.class);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearCommune() {
        try {
            TableUtils.clearTable(DatabaseManager.getInstance().getConnectionSource(), ECommune.class);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearQuartier() {
        try {
            TableUtils.clearTable(DatabaseManager.getInstance().getConnectionSource(), EQuartier.class);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearAgence() {
        try {
            TableUtils.clearTable(DatabaseManager.getInstance().getConnectionSource(), EAgence.class);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearAgenceTypeProduit() {
        try {
            TableUtils.clearTable(DatabaseManager.getInstance().getConnectionSource(), EAgenceTypeProduit.class);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearClient() {
        try {
            TableUtils.clearTable(DatabaseManager.getInstance().getConnectionSource(), EClient.class);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearMouvement() {
        try {
            TableUtils.clearTable(DatabaseManager.getInstance().getConnectionSource(), EMouvementStock.class);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearUser() {
        try {
            TableUtils.clearTable(DatabaseManager.getInstance().getConnectionSource(), EUser.class);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void clearTypeProduit() {
        try {
            TableUtils.clearTable(DatabaseManager.getInstance().getConnectionSource(), ETypeProduit.class);


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public DatabaseHelper getHelper() {
        return helper;
    }

    public SQLiteDatabase getSQDatabase() {
        SQLiteDatabase sqDatabase = null;
        try {

            sqDatabase = helper.getWritableDatabase();
        } catch (Exception e) {
            Log.w("Erreur",  e + "");
        }
        return sqDatabase;
    }

    public void viderTable(Class<T> entity) {
        ConnectionSource connectionSource = getHelper().getConnectionSource();
        try {
            TableUtils.clearTable(connectionSource, entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ConnectionSource getConnectionSource() {
        ConnectionSource connectionSource = null;
        try {
            connectionSource = getHelper().getConnectionSource();
        } catch (Exception e) {
        }
        return connectionSource;
    }

    public SQLiteDatabase getSQLiteDatabase() {
        SQLiteDatabase sqDatabase = null;
        try {

            sqDatabase = helper.getWritableDatabase();
        } catch (Exception e) {
            Log.w("Erreur",  e + "");
        }
        return sqDatabase;
    }

    public void clearTable(Class<T> entity) {
        ConnectionSource connectionSource = getHelper().getConnectionSource();
        try {
            TableUtils.clearTable(connectionSource, entity);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

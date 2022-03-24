package com.sax.inc.coetegaz.Dao;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.sax.inc.coetegaz.DataBases.DatabaseManager;
import com.sax.inc.coetegaz.Entites.ETypeProduit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class TypeProduitDao {

    public static boolean create(ETypeProduit o) {
        boolean ok = false;
        try {
            int nbr = DatabaseManager.getInstance().getHelper().getTypeProduitDao().create(o);

            if (nbr > 0) {
                ok = true;
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean update(ETypeProduit o) {
        boolean ok = false;
        try {
            int nbr = DatabaseManager.getInstance().getHelper().getTypeProduitDao().update(o);

            if (nbr > 0) {
                ok = true;
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ETypeProduit get(long Id) {
        ETypeProduit item = null;
        try {
            List<ETypeProduit> dataList = DatabaseManager.getInstance().getHelper()
                    .getTypeProduitDao().queryForEq("idnative", Id);
            if (dataList != null && !dataList.isEmpty()) {
                item = dataList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static ETypeProduit get(String name) {
        ETypeProduit item = null;
        try {
            List<ETypeProduit> dataList = DatabaseManager.getInstance().getHelper()
                    .getTypeProduitDao().queryForEq("name", name);
            if (dataList != null && !dataList.isEmpty()) {
                item = dataList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static List<ETypeProduit> getAll() {

        List<ETypeProduit> eItemList = new ArrayList<>();
        try {

            eItemList = DatabaseManager.getInstance().getHelper().getTypeProduitDao()
                    .queryBuilder().orderBy("id", true)
                    /*.where().eq("", 0)*/
                    .query();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return eItemList;
    }

    public static boolean delete(int Id) {
        boolean b = true;
        try {
            DeleteBuilder deleteBuilder = DatabaseManager.getInstance().getHelper().getTypeProduitDao().deleteBuilder();
            deleteBuilder.
                    where().eq("id",Id);
            deleteBuilder.delete();
        } catch (Exception e) {
            b = false;
            e.printStackTrace();
        }
        return b;
    }

    public static long count() {
        long count = 0;
        try {
            count = DatabaseManager.getInstance().getHelper()
                    .getTypeProduitDao().queryBuilder().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


}

package com.sax.inc.coetegaz.Dao;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.sax.inc.coetegaz.DataBases.DatabaseManager;
import com.sax.inc.coetegaz.Entites.EAgenceTypeProduit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgenceTypeProduitDao {

    public static boolean create(EAgenceTypeProduit o) {
        boolean ok = false;
        try {
            int nbr = DatabaseManager.getInstance().getHelper().getAgenceTypeProduitDao().create(o);

            if (nbr > 0) {
                ok = true;
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean update(EAgenceTypeProduit o) {
        boolean ok = false;
        try {
            int nbr = DatabaseManager.getInstance().getHelper().getAgenceTypeProduitDao().update(o);

            if (nbr > 0) {
                ok = true;
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static EAgenceTypeProduit get(long Id) {
        EAgenceTypeProduit item = null;
        try {
            List<EAgenceTypeProduit> dataList = DatabaseManager.getInstance().getHelper()
                    .getAgenceTypeProduitDao().queryForEq("idnative", Id);
            if (dataList != null && !dataList.isEmpty()) {
                item = dataList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static EAgenceTypeProduit get(String name) {
        EAgenceTypeProduit item = null;
        try {
            List<EAgenceTypeProduit> dataList = DatabaseManager.getInstance().getHelper()
                    .getAgenceTypeProduitDao().queryForEq("name", name);
            if (dataList != null && !dataList.isEmpty()) {
                item = dataList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static List<EAgenceTypeProduit> getAll() {

        List<EAgenceTypeProduit> eItemList = new ArrayList<>();
        try {

            eItemList = DatabaseManager.getInstance().getHelper().getAgenceTypeProduitDao()
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
            DeleteBuilder deleteBuilder = DatabaseManager.getInstance().getHelper().getAgenceTypeProduitDao().deleteBuilder();
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
                    .getAgenceTypeProduitDao().queryBuilder().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


}

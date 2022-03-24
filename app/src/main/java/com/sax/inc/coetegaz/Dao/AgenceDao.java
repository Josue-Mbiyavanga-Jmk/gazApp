package com.sax.inc.coetegaz.Dao;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.sax.inc.coetegaz.DataBases.DatabaseManager;
import com.sax.inc.coetegaz.Entites.EAgence;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AgenceDao {

    public static boolean create(EAgence o) {
        boolean ok = false;
        try {
            int nbr = DatabaseManager.getInstance().getHelper().getAgenceDao().create(o);

            if (nbr > 0) {
                ok = true;
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean update(EAgence o) {
        boolean ok = false;
        try {
            int nbr = DatabaseManager.getInstance().getHelper().getAgenceDao().update(o);

            if (nbr > 0) {
                ok = true;
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static EAgence get(long Id) {
        EAgence item = null;
        try {
            List<EAgence> dataList = DatabaseManager.getInstance().getHelper()
                    .getAgenceDao().queryForEq("idnative", Id);
            if (dataList != null && !dataList.isEmpty()) {
                item = dataList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static EAgence get(String name) {
        EAgence item = null;
        try {
            List<EAgence> dataList = DatabaseManager.getInstance().getHelper()
                    .getAgenceDao().queryForEq("name", name);
            if (dataList != null && !dataList.isEmpty()) {
                item = dataList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static List<EAgence> getAll() {

        List<EAgence> eItemList = new ArrayList<>();
        try {

            eItemList = DatabaseManager.getInstance().getHelper().getAgenceDao()
                    .queryBuilder().orderBy("id", true)
                    /*.where().eq("", 0)*/
                    .query();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return eItemList;
    }

    public static List<EAgence> getAllSearch() {

        List<EAgence> eItemList = new ArrayList<>();
        try {

            eItemList = DatabaseManager.getInstance().getHelper().getAgenceDao()
                    .queryBuilder().orderBy("id", true)
                    .where().eq("status", 0)
                    .query();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return eItemList;
    }

    public static List<EAgence> getAllLoad() {

        List<EAgence> eItemList = new ArrayList<>();
        try {

            eItemList = DatabaseManager.getInstance().getHelper().getAgenceDao()
                    .queryBuilder().orderBy("id", true)
                    .where().eq("status", 2)
                    .query();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return eItemList;
    }

    public static boolean delete(int Id) {
        boolean b = true;
        try {
            DeleteBuilder deleteBuilder = DatabaseManager.getInstance().getHelper().getAgenceDao().deleteBuilder();
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
                    .getAgenceDao().queryBuilder().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


}

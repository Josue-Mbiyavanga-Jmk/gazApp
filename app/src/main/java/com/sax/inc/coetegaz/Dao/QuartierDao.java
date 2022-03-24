package com.sax.inc.coetegaz.Dao;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.sax.inc.coetegaz.DataBases.DatabaseManager;
import com.sax.inc.coetegaz.Entites.EQuartier;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QuartierDao {

    public static boolean create(EQuartier o) {
        boolean ok = false;
        try {
            int nbr = DatabaseManager.getInstance().getHelper().getQuartierDao().create(o);

            if (nbr > 0) {
                ok = true;
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean update(EQuartier o) {
        boolean ok = false;
        try {
            int nbr = DatabaseManager.getInstance().getHelper().getQuartierDao().update(o);

            if (nbr > 0) {
                ok = true;
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static EQuartier get(long Id) {
        EQuartier item = null;
        try {
            List<EQuartier> dataList = DatabaseManager.getInstance().getHelper()
                    .getQuartierDao().queryForEq("idnative", Id);
            if (dataList != null && !dataList.isEmpty()) {
                item = dataList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static EQuartier get(String name) {
        EQuartier item = null;
        try {
            List<EQuartier> dataList = DatabaseManager.getInstance().getHelper()
                    .getQuartierDao().queryForEq("name", name);
            if (dataList != null && !dataList.isEmpty()) {
                item = dataList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static List<EQuartier> getByCommune(int ref) {
        List<EQuartier> eItemList = new ArrayList<>();
        try {

            eItemList = DatabaseManager.getInstance().getHelper().getQuartierDao()
                    .queryBuilder().orderBy("id", true)
                    .where().eq("communeref", ref)
                    .query();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return eItemList;
    }

    public static List<EQuartier> getAll() {

        List<EQuartier> eItemList = new ArrayList<>();
        try {

            eItemList = DatabaseManager.getInstance().getHelper().getQuartierDao()
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
            DeleteBuilder deleteBuilder = DatabaseManager.getInstance().getHelper().getQuartierDao().deleteBuilder();
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
                    .getQuartierDao().queryBuilder().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }
}

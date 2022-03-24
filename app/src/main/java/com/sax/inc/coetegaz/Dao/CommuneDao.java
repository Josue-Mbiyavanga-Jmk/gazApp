package com.sax.inc.coetegaz.Dao;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.sax.inc.coetegaz.DataBases.DatabaseManager;
import com.sax.inc.coetegaz.Entites.ECommune;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CommuneDao {

    public static boolean create(ECommune o) {
        boolean ok = false;
        try {
            int nbr = DatabaseManager.getInstance().getHelper().getCommuneDao().create(o);

            if (nbr > 0) {
                ok = true;
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean update(ECommune o) {
        boolean ok = false;
        try {
            int nbr = DatabaseManager.getInstance().getHelper().getCommuneDao().update(o);

            if (nbr > 0) {
                ok = true;
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static ECommune get(long Id) {
        ECommune item = null;
        try {
            List<ECommune> dataList = DatabaseManager.getInstance().getHelper()
                    .getCommuneDao().queryForEq("idnative", Id);
            if (dataList != null && !dataList.isEmpty()) {
                item = dataList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static ECommune get(String name) {
        ECommune item = null;
        try {
            List<ECommune> dataList = DatabaseManager.getInstance().getHelper()
                    .getCommuneDao().queryForEq("name", name);
            if (dataList != null && !dataList.isEmpty()) {
                item = dataList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static List<ECommune> getByVille(int ref) {
        List<ECommune> eItemList = new ArrayList<>();
        try {

            eItemList = DatabaseManager.getInstance().getHelper().getCommuneDao()
                    .queryBuilder().orderBy("id", true)
                    .where().eq("villeref", ref)
                    .query();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return eItemList;
    }

    public static List<ECommune> getAll() {

        List<ECommune> eItemList = new ArrayList<>();
        try {

            eItemList = DatabaseManager.getInstance().getHelper().getCommuneDao()
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
            DeleteBuilder deleteBuilder = DatabaseManager.getInstance().getHelper().getCommuneDao().deleteBuilder();
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
                    .getCommuneDao().queryBuilder().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

}

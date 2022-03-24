package com.sax.inc.coetegaz.Dao;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.sax.inc.coetegaz.DataBases.DatabaseManager;
import com.sax.inc.coetegaz.Entites.EClient;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClientDao {

    public static boolean create(EClient o) {
        boolean ok = false;
        try {
            int nbr = DatabaseManager.getInstance().getHelper().getClientDao().create(o);

            if (nbr > 0) {
                ok = true;
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean update(EClient o) {
        boolean ok = false;
        try {
            int nbr = DatabaseManager.getInstance().getHelper().getClientDao().update(o);

            if (nbr > 0) {
                ok = true;
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static EClient get(long Id) {
        EClient item = null;
        try {
            List<EClient> dataList = DatabaseManager.getInstance().getHelper()
                    .getClientDao().queryForEq("idnative", Id);
            if (dataList != null && !dataList.isEmpty()) {
                item = dataList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static EClient get(String name) {
        EClient item = null;
        try {
            List<EClient> dataList = DatabaseManager.getInstance().getHelper()
                    .getClientDao().queryForEq("name", name);
            if (dataList != null && !dataList.isEmpty()) {
                item = dataList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static EClient getByToken(String token) {
        EClient item = null;
        try {
            List<EClient> dataList = DatabaseManager.getInstance().getHelper()
                    .getClientDao().queryForEq("token", token);
            if (dataList != null && !dataList.isEmpty()) {
                item = dataList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static List<EClient> getAll() {

        List<EClient> eItemList = new ArrayList<>();
        try {

            eItemList = DatabaseManager.getInstance().getHelper().getClientDao()
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
            DeleteBuilder deleteBuilder = DatabaseManager.getInstance().getHelper().getClientDao().deleteBuilder();
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
                    .getClientDao().queryBuilder().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }


}

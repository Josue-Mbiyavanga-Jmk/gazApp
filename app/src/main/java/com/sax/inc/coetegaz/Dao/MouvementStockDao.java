package com.sax.inc.coetegaz.Dao;

import com.j256.ormlite.stmt.DeleteBuilder;
import com.sax.inc.coetegaz.DataBases.DatabaseManager;
import com.sax.inc.coetegaz.Entites.EMouvementStock;
import com.sax.inc.coetegaz.Entites.ETypeProduit;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MouvementStockDao {

    public static boolean create(EMouvementStock o) {
        boolean ok = false;
        try {
            int nbr = DatabaseManager.getInstance().getHelper().getMouvementStockDao().create(o);

            if (nbr > 0) {
                ok = true;
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean update(EMouvementStock o) {
        boolean ok = false;
        try {
            int nbr = DatabaseManager.getInstance().getHelper().getMouvementStockDao().update(o);

            if (nbr > 0) {
                ok = true;
            }
            return ok;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static EMouvementStock get(long Id) {
        EMouvementStock item = null;
        try {
            List<EMouvementStock> dataList = DatabaseManager.getInstance().getHelper()
                    .getMouvementStockDao().queryForEq("idnative", Id);
            if (dataList != null && !dataList.isEmpty()) {
                item = dataList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static EMouvementStock get(String name) {
        EMouvementStock item = null;
        try {
            List<EMouvementStock> dataList = DatabaseManager.getInstance().getHelper()
                    .getMouvementStockDao().queryForEq("name", name);
            if (dataList != null && !dataList.isEmpty()) {
                item = dataList.get(0);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return item;
    }

    public static List<EMouvementStock> getAll() {

        List<EMouvementStock> eItemList = new ArrayList<>();
        try {

            eItemList = DatabaseManager.getInstance().getHelper().getMouvementStockDao()
                    .queryBuilder().orderBy("id", true)
                    /*.where().eq("", 0)*/
                    .query();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return eItemList;
    }

    public static List<EMouvementStock> getAllByInput(String type) {

        List<EMouvementStock> eItemList = new ArrayList<>();
        try {

            eItemList = DatabaseManager.getInstance().getHelper().getMouvementStockDao()
                    .queryBuilder().orderBy("id", true)
                    .where().eq("type", type)
                    .query();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return eItemList;
    }

    public static boolean delete(int Id) {
        boolean b = true;
        try {
            DeleteBuilder deleteBuilder = DatabaseManager.getInstance().getHelper().getMouvementStockDao().deleteBuilder();
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
                    .getMouvementStockDao().queryBuilder().countOf();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    public static List<EMouvementStock> getMouvementByProduitAndType(String type) {
        List<EMouvementStock> eItemList = new ArrayList<>();
        List<EMouvementStock> mes_mouvement= new ArrayList<>();
        try {
            //recuperation
            eItemList = DatabaseManager.getInstance().getHelper().getMouvementStockDao()
                    .queryBuilder().orderBy("id", true)
                    .where().eq("type", type)
                    .query();

            List<ETypeProduit> eTypeProduits=TypeProduitDao.getAll();
            for (ETypeProduit produit:eTypeProduits) {

                int qt=0;
                for (EMouvementStock mv:eItemList) {

                    if (mv.getTypeproduit().equals(produit.getName())){
                        qt+=mv.getQuantite();
                    }
                }
                mes_mouvement.add(new EMouvementStock(qt));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return mes_mouvement;
    }


}

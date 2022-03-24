package com.sax.inc.coetegaz.Entites;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_ville")
public class EVille {
    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "idnative")
    private int idnative;
    //lors de la deserialisation, affecter id à id_native
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "provinceref")
    private int provinceref;


    public EVille() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId_native() {
        return idnative;
    }

    public void setId_native(int id_native) {
        this.idnative = id_native;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getProvinceref() {
        return provinceref;
    }

    public void setProvinceref(int provinceref) {
        this.provinceref = provinceref;
    }

}

package com.sax.inc.coetegaz.Entites;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_quartier")
public class EQuartier {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "idnative")
    private int idnative;
    //lors de la deserialisation, affecter id à id_native
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "communeref")
    private int communeref;


    public EQuartier() {
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

    public int getCommuneref() {
        return communeref;
    }

    public void setCommuneref(int communeref) {
        this.communeref = communeref;
    }



}

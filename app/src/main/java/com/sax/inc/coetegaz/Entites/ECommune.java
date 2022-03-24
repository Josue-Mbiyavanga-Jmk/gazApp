package com.sax.inc.coetegaz.Entites;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_commune")
public class ECommune {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "idnative")
    private int idnative;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "villeref")
    private int villeref;


    public ECommune() {
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

    public int getVilleref() {
        return villeref;
    }

    public void setVilleref(int villeref) {
        this.villeref = villeref;
    }


}

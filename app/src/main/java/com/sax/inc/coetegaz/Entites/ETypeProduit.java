package com.sax.inc.coetegaz.Entites;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_type_produit")
public class ETypeProduit {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "idnative")
    private int idnative;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "description")
    private String description;
    @DatabaseField(columnName = "prixrecharge")
    private float prixrecharge;
    @DatabaseField(columnName = "prixabonnement")
    private float prixabonnement;
    @DatabaseField(columnName = "status")
    private int status;
    //On fait des String mais on manipulera des Long
    @DatabaseField(columnName = "datewrite")
    private long datewrite;
    @DatabaseField(columnName = "dateupdate")
    private long dateupdate;


    public ETypeProduit() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdnative() {
        return idnative;
    }

    public void setIdnative(int idnative) {
        this.idnative = idnative;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public float getPrixrecharge() {
        return prixrecharge;
    }

    public void setPrixrecharge(float prixrecharge) {
        this.prixrecharge = prixrecharge;
    }

    public float getPrixabonnement() {
        return prixabonnement;
    }

    public void setPrixabonnement(float prixabonnement) {
        this.prixabonnement = prixabonnement;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }


    public long getDatewrite() {
        return datewrite;
    }

    public void setDatewrite(long datewrite) {
        this.datewrite = datewrite;
    }

    public long getDateupdate() {
        return dateupdate;
    }

    public void setDateupdate(long dateupdate) {
        this.dateupdate = dateupdate;
    }
}

package com.sax.inc.coetegaz.Entites;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_mouvement_stock")
public class EMouvementStock {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "idnative")
    private int idnative;
    @DatabaseField(columnName = "type")
    private String type;
    @DatabaseField(columnName = "typeproduit")
    private String typeproduit;
    @DatabaseField(columnName = "quantite")
    private int quantite;
    @DatabaseField(columnName = "totalpaie")
    private float totalpaie;
    @DatabaseField(columnName = "observation")
    private String observation;
    @DatabaseField(columnName = "userwrite")
    private String userwrite;
    @DatabaseField(columnName = "agenceref")
    private int agenceref;
    @DatabaseField(columnName = "status")
    private int status;
    //On fait des String mais on manipulera des Long
    @DatabaseField(columnName = "datewrite")
    private long datewrite;
    @DatabaseField(columnName = "dateupdate")
    private long dateupdate;
    @DatabaseField(columnName = "client")
    private String client;



    public EMouvementStock() {
    }

    public EMouvementStock( int quantite) {
        this.quantite = quantite;
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


    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTypeproduit() {
        return typeproduit;
    }

    public void setTypeproduit(String typeproduit) {
        this.typeproduit = typeproduit;
    }

    public int getQuantite() {
        return quantite;
    }

    public void setQuantite(int quantite) {
        this.quantite = quantite;
    }

    public float getTotalpaie() {
        return totalpaie;
    }

    public void setTotalpaie(float totalpaie) {
        this.totalpaie = totalpaie;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public String getUserwrite() {
        return userwrite;
    }

    public void setUserwrite(String userwrite) {
        this.userwrite = userwrite;
    }

    public int getAgenceref() {
        return agenceref;
    }

    public void setAgenceref(int agenceref) {
        this.agenceref = agenceref;
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

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }
}

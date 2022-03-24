package com.sax.inc.coetegaz.Entites;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_agence")
public class EAgence {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "idnative")
    private int idnative;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "telephone")
    private String telephone;
    @DatabaseField(columnName = "gps")
    private String gps;
    @DatabaseField(columnName = "provinceref")
    private int provinceref;
    @DatabaseField(columnName = "villeref")
    private int villeref;
    @DatabaseField(columnName = "communeref")
    private int communeref;
    @DatabaseField(columnName = "quartierref")
    private int quartierref;
    @DatabaseField(columnName = "avenue")
    private String avenue;
    @DatabaseField(columnName = "numero")
    private String numero;
    @DatabaseField(columnName = "reflieu")
    private String reflieu;
    @DatabaseField(columnName = "heureopen")
    private String heureopen;
    @DatabaseField(columnName = "status")
    private int status;
    @DatabaseField(columnName = "quantite_produit")
    private int quantite_produit;
    @DatabaseField(columnName = "ref")
    private String ref;
    //On fait des String mais on manipulera des Long
    @DatabaseField(columnName = "datewrite")
    private long datewrite;
    @DatabaseField(columnName = "dateupdate")
    private long dateupdate;
    @DatabaseField(columnName = "countwait")
    private long countwait;

    public EAgence() {
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


    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }



    public int getProvinceref() {
        return provinceref;
    }

    public void setProvinceref(int provinceref) {
        this.provinceref = provinceref;
    }

    public int getVilleref() {
        return villeref;
    }

    public void setVilleref(int villeref) {
        this.villeref = villeref;
    }

    public int getCommuneref() {
        return communeref;
    }

    public void setCommuneref(int communeref) {
        this.communeref = communeref;
    }

    public int getQuartierref() {
        return quartierref;
    }

    public void setQuartierref(int quartierref) {
        this.quartierref = quartierref;
    }

    public String getAvenue() {
        return avenue;
    }

    public void setAvenue(String avenue) {
        this.avenue = avenue;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
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

    public String getReflieu() {
        return reflieu;
    }

    public void setReflieu(String reflieu) {
        this.reflieu = reflieu;
    }

    public String getHeureopen() {
        return heureopen;
    }

    public void setHeureopen(String heureopen) {
        this.heureopen = heureopen;
    }

    public long getCountwait() {
        return countwait;
    }

    public void setCountwait(long countwait) {
        this.countwait = countwait;
    }

    public int getQuantite_produit() {
        return quantite_produit;
    }

    public void setQuantite_produit(int quantite_produit) {
        this.quantite_produit = quantite_produit;
    }
}

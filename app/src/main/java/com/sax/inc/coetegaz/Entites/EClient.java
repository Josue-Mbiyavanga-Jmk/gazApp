package com.sax.inc.coetegaz.Entites;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_client")
public class EClient {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "idnative")
    private int idnative;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "qr")
    private String qr;
    @DatabaseField(columnName = "token")
    private String token;
    @DatabaseField(columnName = "telephone")
    private String telephone;
    @DatabaseField(columnName = "email")
    private String email;
    @DatabaseField(columnName = "gps")
    private String gps;
    @DatabaseField(columnName = "listabonnement")
    private String listabonnement;
    @DatabaseField(columnName = "status")
    private int status;
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
    @DatabaseField(columnName = "ref")
    private String ref;
    //On fait des String mais on manipulera des Long
    @DatabaseField(columnName = "datewrite")
    private long datewrite;
    @DatabaseField(columnName = "dateupdate")
    private long dateupdate;

    public EClient() {
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

    public String getQr() {
        return qr;
    }

    public void setQr(String qr) {
        this.qr = qr;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGps() {
        return gps;
    }

    public void setGps(String gps) {
        this.gps = gps;
    }

    public String getListabonnement() {
        return listabonnement;
    }

    public void setListabonnement(String listabonnement) {
        this.listabonnement = listabonnement;
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
}

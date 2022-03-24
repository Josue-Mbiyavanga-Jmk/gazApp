package com.sax.inc.coetegaz.Entites;


import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "tb_user")
public class EUser {

    @DatabaseField(columnName = "id", generatedId = true)
    private int id;
    @DatabaseField(columnName = "idnative")
    private int idnative;
    @DatabaseField(columnName = "name")
    private String name;
    @DatabaseField(columnName = "pseudo")
    private String pseudo;
    @DatabaseField(columnName = "password")
    private String password;
    @DatabaseField(columnName = "profil")
    private String profil;
    @DatabaseField(columnName = "telephone")
    private String telephone;
    @DatabaseField(columnName = "email")
    private String email;
    @DatabaseField(columnName = "genre")
    private String genre;
    @DatabaseField(columnName = "status")
    private int status;
    @DatabaseField(columnName = "agenceref")
    private int agenceref;
    @DatabaseField(columnName = "ref")
    private String ref;
    //On fait des String mais on manipulera des Long
    @DatabaseField(columnName = "datewrite")
    private long datewrite;
    @DatabaseField(columnName = "dateupdate")
    private long dateupdate;


    public EUser() {
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

    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfil() {
        return profil;
    }

    public void setProfil(String profil) {
        this.profil = profil;
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

    public int getAgenceref() {
        return agenceref;
    }

    public void setAgenceref(int agenceref) {
        this.agenceref = agenceref;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
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

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }
}

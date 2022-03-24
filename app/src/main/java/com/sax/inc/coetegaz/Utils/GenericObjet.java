package com.sax.inc.coetegaz.Utils;

import java.util.List;

public class GenericObjet<T> {

    private   int result; // code réponse http
    private List<T> data; // data : un table Json qu'on retourne ensemble avec result
    private T objet; // objet json qu'on retourne ensemble avec result

    public GenericObjet() {
    }

    public int getResult() {
        return result;
    }

    public void setResult(int result) {
        this.result = result;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public T getObjet() {
        return objet;
    }

    public void setObjet(T objet) {
        this.objet = objet;
    }
}

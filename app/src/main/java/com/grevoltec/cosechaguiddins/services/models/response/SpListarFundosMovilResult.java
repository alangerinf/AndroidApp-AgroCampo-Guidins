package com.grevoltec.cosechaguiddins.services.models.response;

import com.google.gson.annotations.SerializedName;
import com.grevoltec.cosechaguiddins.services.models.AbsModel;

public class SpListarFundosMovilResult extends AbsModel {

    @SerializedName("idFundo")
    private String idFundo;
    @SerializedName("codigoFundo")
    private String codigoFundo;
    @SerializedName("nombreFundo")
    private String nombreFundo;

    public SpListarFundosMovilResult() {
    }

    public SpListarFundosMovilResult(String idFundo, String codigoFundo, String nombreFundo) {
        this.idFundo = idFundo;
        this.codigoFundo = codigoFundo;
        this.nombreFundo = nombreFundo;
    }

    public String getIdFundo() {
        return idFundo;
    }

    public void setIdFundo(String idFundo) {
        this.idFundo = idFundo;
    }

    public String getCodigoFundo() {
        return codigoFundo;
    }

    public void setCodigoFundo(String codigoFundo) {
        this.codigoFundo = codigoFundo;
    }

    public String getNombreFundo() {
        return nombreFundo;
    }

    public void setNombreFundo(String nombreFundo) {
        this.nombreFundo = nombreFundo;
    }

}

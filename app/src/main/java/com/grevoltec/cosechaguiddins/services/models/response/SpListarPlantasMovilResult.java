package com.grevoltec.cosechaguiddins.services.models.response;

import com.google.gson.annotations.SerializedName;
import com.grevoltec.cosechaguiddins.services.models.AbsModel;

public class SpListarPlantasMovilResult extends AbsModel {

    @SerializedName("idPlanta")
    private String idPlanta;
    @SerializedName("codigoPlanta")
    private String codigoPlanta;
    @SerializedName("nombrePlanta")
    private String nombrePlanta;

    public SpListarPlantasMovilResult() {
    }

    public SpListarPlantasMovilResult(String idPlanta, String codigoPlanta, String nombrePlanta) {
        this.idPlanta = idPlanta;
        this.codigoPlanta = codigoPlanta;
        this.nombrePlanta = nombrePlanta;
    }

    public String getIdPlanta() {
        return idPlanta;
    }

    public void setIdPlanta(String idPlanta) {
        this.idPlanta = idPlanta;
    }

    public String getCodigoPlanta() {
        return codigoPlanta;
    }

    public void setCodigoPlanta(String codigoPlanta) {
        this.codigoPlanta = codigoPlanta;
    }

    public String getNombrePlanta() {
        return nombrePlanta;
    }

    public void setNombrePlanta(String nombrePlanta) {
        this.nombrePlanta = nombrePlanta;
    }

}

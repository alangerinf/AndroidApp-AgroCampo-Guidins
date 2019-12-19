package com.grevoltec.cosechaguiddins.services.models.response;

import com.google.gson.annotations.SerializedName;
import com.grevoltec.cosechaguiddins.services.models.AbsModel;

public class SpListarAcopiosMovilResult extends AbsModel {

    @SerializedName("idAcopio")
    private String idAcopio;
    @SerializedName("codigoAcopio")
    private String codigoAcopio;
    @SerializedName("nombreAcopio")
    private String nombreAcopio;

    public SpListarAcopiosMovilResult() {
    }

    public SpListarAcopiosMovilResult(String idAcopio, String codigoAcopio, String nombreAcopio) {
        this.idAcopio = idAcopio;
        this.codigoAcopio = codigoAcopio;
        this.nombreAcopio = nombreAcopio;
    }

    public String getIdAcopio() {
        return idAcopio;
    }

    public void setIdAcopio(String idAcopio) {
        this.idAcopio = idAcopio;
    }

    public String getCodigoAcopio() {
        return codigoAcopio;
    }

    public void setCodigoAcopio(String codigoAcopio) {
        this.codigoAcopio = codigoAcopio;
    }

    public String getNombreAcopio() {
        return nombreAcopio;
    }

    public void setNombreAcopio(String nombreAcopio) {
        this.nombreAcopio = nombreAcopio;
    }

}

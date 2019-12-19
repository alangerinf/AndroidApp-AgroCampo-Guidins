package com.grevoltec.cosechaguiddins.services.models.response;

import com.google.gson.annotations.SerializedName;
import com.grevoltec.cosechaguiddins.services.models.AbsModel;

public class SpListarCultivosMovilResult extends AbsModel {

    @SerializedName("idCultivo")
    private String idCultivo;
    @SerializedName("codigoCultivo")
    private String codigoCultivo;
    @SerializedName("nombreCultivo")
    private String nombreCultivo;

    public SpListarCultivosMovilResult() {
    }

    public SpListarCultivosMovilResult(String idCultivo, String codigoCultivo, String nombreCultivo) {
        this.idCultivo = idCultivo;
        this.codigoCultivo = codigoCultivo;
        this.nombreCultivo = nombreCultivo;
    }

    public String getIdCultivo() {
        return idCultivo;
    }

    public void setIdCultivo(String idCultivo) {
        this.idCultivo = idCultivo;
    }

    public String getCodigoCultivo() {
        return codigoCultivo;
    }

    public void setCodigoCultivo(String codigoCultivo) {
        this.codigoCultivo = codigoCultivo;
    }

    public String getNombreCultivo() {
        return nombreCultivo;
    }

    public void setNombreCultivo(String nombreCultivo) {
        this.nombreCultivo = nombreCultivo;
    }

}

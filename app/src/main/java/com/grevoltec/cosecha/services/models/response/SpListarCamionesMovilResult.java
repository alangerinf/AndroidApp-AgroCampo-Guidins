package com.grevoltec.cosecha.services.models.response;

import com.google.gson.annotations.SerializedName;
import com.grevoltec.cosecha.services.models.AbsModel;

public class SpListarCamionesMovilResult extends AbsModel
{

    @SerializedName("idCamion")
    private String idCamion;
    @SerializedName("codigoCamion")
    private String codigoCamion;
    @SerializedName("placaCamion")
    private String placaCamion;
    @SerializedName("codigoQRCamion")
    private String codigoQRCamion;

    public SpListarCamionesMovilResult() {
    }

    public SpListarCamionesMovilResult(String idCamion, String codigoCamion, String placaCamion, String codigoQRCamion) {
        this.idCamion = idCamion;
        this.codigoCamion = codigoCamion;
        this.placaCamion = placaCamion;
        this.codigoQRCamion = codigoQRCamion;
    }

    public String getIdCamion() {
        return idCamion;
    }

    public void setIdCamion(String idCamion) {
        this.idCamion = idCamion;
    }

    public String getCodigoCamion() {
        return codigoCamion;
    }

    public void setCodigoCamion(String codigoCamion) {
        this.codigoCamion = codigoCamion;
    }

    public String getPlacaCamion() {
        return placaCamion;
    }

    public void setPlacaCamion(String placaCamion) {
        this.placaCamion = placaCamion;
    }

    public String getCodigoQRCamion() {
        return codigoQRCamion;
    }

    public void setCodigoQRCamion(String codigoQRCamion) {
        this.codigoQRCamion = codigoQRCamion;
    }

}

package com.grevoltec.cosecha.services.models.response;

import com.google.gson.annotations.SerializedName;
import com.grevoltec.cosecha.services.models.AbsModel;

public class SpListarTurnosMovilResult extends AbsModel {

    @SerializedName("idTurno")
    private String idTurno;
    @SerializedName("codigoTurno")
    private String codigoTurno ;
    @SerializedName("codigoQRTurno")
    private String codigoQRTurno ;
    @SerializedName("nombreTurno")
    private String nombreTurno ;
    @SerializedName("idFundo")
    private String idFundo ;

    public SpListarTurnosMovilResult() {
    }

    public SpListarTurnosMovilResult(String idTurno, String codigoTurno, String codigoQRTurno, String nombreTurno, String idFundo) {
        this.idTurno = idTurno;
        this.codigoTurno = codigoTurno;
        this.codigoQRTurno = codigoQRTurno;
        this.nombreTurno = nombreTurno;
        this.idFundo = idFundo;
    }

    public String getIdTurno() {
        return idTurno;
    }

    public void setIdTurno(String idTurno) {
        this.idTurno = idTurno;
    }

    public String getCodigoTurno() {
        return codigoTurno;
    }

    public void setCodigoTurno(String codigoTurno) {
        this.codigoTurno = codigoTurno;
    }

    public String getCodigoQRTurno() {
        return codigoQRTurno;
    }

    public void setCodigoQRTurno(String codigoQRTurno) {
        this.codigoQRTurno = codigoQRTurno;
    }

    public String getNombreTurno() {
        return nombreTurno;
    }

    public void setNombreTurno(String nombreTurno) {
        this.nombreTurno = nombreTurno;
    }

    public String getIdFundo() {
        return idFundo;
    }

    public void setIdFundo(String idFundo) {
        this.idFundo = idFundo;
    }

    @Override
    public String toString() {
        return "SpListarTurnosMovilResult{" +
                "idTurno='" + idTurno + '\'' +
                ", codigoTurno='" + codigoTurno + '\'' +
                ", codigoQRTurno='" + codigoQRTurno + '\'' +
                ", nombreTurno='" + nombreTurno + '\'' +
                ", idFundo='" + idFundo + '\'' +
                '}';
    }
}

package com.grevoltec.cosechaguiddins.services.models.request;

import com.grevoltec.cosechaguiddins.services.models.AbsModel;

public class SpSincronizarRecepcionMovilRequest extends AbsModel {

    private int idplanta;
    private String fecharecep;
    private int iduser;
    private String qrpallet;
    private String horapallet;
    private String imeiequipo;
    private String nroequipo;
    private String pesopallet;

    public SpSincronizarRecepcionMovilRequest() {
    }

    public SpSincronizarRecepcionMovilRequest(int idplanta, String fecharecep, int iduser, String qrpallet, String horapallet, String imeiequipo, String nroequipo, String pesopallet) {
        this.idplanta = idplanta;
        this.fecharecep = fecharecep;
        this.iduser = iduser;
        this.qrpallet = qrpallet;
        this.horapallet = horapallet;
        this.imeiequipo = imeiequipo;
        this.nroequipo = nroequipo;
        this.pesopallet = pesopallet;
    }

    public int getIdplanta() {
        return idplanta;
    }

    public void setIdplanta(int idplanta) {
        this.idplanta = idplanta;
    }

    public String getFecharecep() {
        return fecharecep;
    }

    public void setFecharecep(String fecharecep) {
        this.fecharecep = fecharecep;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getQrpallet() {
        return qrpallet;
    }

    public void setQrpallet(String qrpallet) {
        this.qrpallet = qrpallet;
    }

    public String getHorapallet() {
        return horapallet;
    }

    public void setHorapallet(String horapallet) {
        this.horapallet = horapallet;
    }

    public String getImeiequipo() {
        return imeiequipo;
    }

    public void setImeiequipo(String imeiequipo) {
        this.imeiequipo = imeiequipo;
    }

    public String getNroequipo() {
        return nroequipo;
    }

    public void setNroequipo(String nroequipo) {
        this.nroequipo = nroequipo;
    }

    public String getPesopallet() {
        return pesopallet;
    }

    public void setPesopallet(String pesopallet) {
        this.pesopallet = pesopallet;
    }

}

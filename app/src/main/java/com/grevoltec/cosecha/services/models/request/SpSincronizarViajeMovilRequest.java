package com.grevoltec.cosecha.services.models.request;

import com.grevoltec.cosecha.services.models.AbsModel;

public class SpSincronizarViajeMovilRequest extends AbsModel {

    private int iduser;
    private String qrcamion;
    private String horacamion;
    private int viajecompleto;
    private String imeiequipo;
    private String numeroequipo;
    private String qrpallet;
    private String horapallet;

    public SpSincronizarViajeMovilRequest() {
    }

    public SpSincronizarViajeMovilRequest(int iduser, String qrcamion, String horacamion, int viajecompleto, String imeiequipo, String numeroequipo, String qrpallet, String horapallet) {
        this.iduser = iduser;
        this.qrcamion = qrcamion;
        this.horacamion = horacamion;
        this.viajecompleto = viajecompleto;
        this.imeiequipo = imeiequipo;
        this.numeroequipo = numeroequipo;
        this.qrpallet = qrpallet;
        this.horapallet = horapallet;
    }

    public int getIduser() {
        return iduser;
    }

    public void setIduser(int iduser) {
        this.iduser = iduser;
    }

    public String getQrcamion() {
        return qrcamion;
    }

    public void setQrcamion(String qrcamion) {
        this.qrcamion = qrcamion;
    }

    public String getHoracamion() {
        return horacamion;
    }

    public void setHoracamion(String horacamion) {
        this.horacamion = horacamion;
    }

    public int getViajecompleto() {
        return viajecompleto;
    }

    public void setViajecompleto(int viajecompleto) {
        this.viajecompleto = viajecompleto;
    }

    public String getImeiequipo() {
        return imeiequipo;
    }

    public void setImeiequipo(String imeiequipo) {
        this.imeiequipo = imeiequipo;
    }

    public String getNumeroequipo() {
        return numeroequipo;
    }

    public void setNumeroequipo(String numeroequipo) {
        this.numeroequipo = numeroequipo;
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

}
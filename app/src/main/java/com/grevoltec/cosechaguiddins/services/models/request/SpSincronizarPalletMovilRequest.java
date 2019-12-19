package com.grevoltec.cosechaguiddins.services.models.request;

import com.grevoltec.cosechaguiddins.services.models.AbsModel;

public class SpSincronizarPalletMovilRequest extends AbsModel {

    private int iduser;
    private String qrpallet;
    private String horapallet;
    private int palletcomp;
    private String imeiequipo;
    private String nroequipo;
    private String qrjaba;
    private String horajaba;

    public SpSincronizarPalletMovilRequest() {
    }

    public SpSincronizarPalletMovilRequest(int iduser, String qrpallet, String horapallet, int palletcomp, String imeiequipo, String nroequipo, String qrjaba, String horajaba) {
        this.iduser = iduser;
        this.qrpallet = qrpallet;
        this.horapallet = horapallet;
        this.palletcomp = palletcomp;
        this.imeiequipo = imeiequipo;
        this.nroequipo = nroequipo;
        this.qrjaba = qrjaba;
        this.horajaba = horajaba;
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

    public int getPalletcomp() {
        return palletcomp;
    }

    public void setPalletcomp(int palletcomp) {
        this.palletcomp = palletcomp;
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

    public String getQrjaba() {
        return qrjaba;
    }

    public void setQrjaba(String qrjaba) {
        this.qrjaba = qrjaba;
    }

    public String getHorajaba() {
        return horajaba;
    }

    public void setHorajaba(String horajaba) {
        this.horajaba = horajaba;
    }

}

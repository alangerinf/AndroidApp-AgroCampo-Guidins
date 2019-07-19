package com.grevoltec.cosecha.services.models.request;

import com.grevoltec.cosecha.services.models.AbsModel;

public class SpListarTurnosMovilRequest extends AbsModel {

    private int idacc;
    private int idfund;

    public SpListarTurnosMovilRequest() {
    }

    public SpListarTurnosMovilRequest(int idacc, int idfund) {
        this.idacc = idacc;
        this.idfund = idfund;
    }

    public int getIdacc() {
        return idacc;
    }

    public void setIdacc(int idacc) {
        this.idacc = idacc;
    }

    public int getIdfund() {
        return idfund;
    }

    public void setIdfund(int idfund) {
        this.idfund = idfund;
    }

}

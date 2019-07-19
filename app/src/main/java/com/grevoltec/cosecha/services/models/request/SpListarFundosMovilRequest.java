package com.grevoltec.cosecha.services.models.request;

import com.grevoltec.cosecha.services.models.AbsModel;

public class SpListarFundosMovilRequest extends AbsModel {

    private int idacc;

    public SpListarFundosMovilRequest() {
    }

    public SpListarFundosMovilRequest(int idacc) {
        this.idacc = idacc;
    }

    public int getIdacc() {
        return idacc;
    }

    public void setIdacc(int idacc) {
        this.idacc = idacc;
    }

}

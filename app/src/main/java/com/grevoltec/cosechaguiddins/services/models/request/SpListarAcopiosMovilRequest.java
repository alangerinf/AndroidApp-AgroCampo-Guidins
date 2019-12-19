package com.grevoltec.cosechaguiddins.services.models.request;

import com.grevoltec.cosechaguiddins.services.models.AbsModel;

public class SpListarAcopiosMovilRequest extends AbsModel {

    private int idacc;

    public SpListarAcopiosMovilRequest() {
    }

    public SpListarAcopiosMovilRequest(int idacc) {
        this.idacc = idacc;
    }

    public int getIdacc() {
        return idacc;
    }

    public void setIdacc(int idacc) {
        this.idacc = idacc;
    }

}

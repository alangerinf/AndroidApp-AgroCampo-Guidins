package com.grevoltec.cosecha.services.models.request;

import com.grevoltec.cosecha.services.models.AbsModel;

public class SpListarUsuariosMovilRequest extends AbsModel {

    private int idacc;

    public SpListarUsuariosMovilRequest() {
    }

    public SpListarUsuariosMovilRequest(int idacc) {
        this.idacc = idacc;
    }

    public int getIdacc() {
        return idacc;
    }

    public void setIdacc(int idacc) {
        this.idacc = idacc;
    }

}

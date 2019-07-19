package com.grevoltec.cosecha.services.models.response;

import com.google.gson.annotations.SerializedName;
import com.grevoltec.cosecha.services.models.AbsModel;

public class SpSincronizarViajeMovilResult extends AbsModel {

    @SerializedName("CODIGO")
    private String codigo;

    public SpSincronizarViajeMovilResult() {
    }

    public SpSincronizarViajeMovilResult(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}

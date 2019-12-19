package com.grevoltec.cosechaguiddins.services.models.response;

import com.google.gson.annotations.SerializedName;
import com.grevoltec.cosechaguiddins.services.models.AbsModel;

public class SpSincronizarPalletMovilResult extends AbsModel {

    @SerializedName("CODIGO")
    private String codigo;

    public SpSincronizarPalletMovilResult() {
    }

    public SpSincronizarPalletMovilResult(String codigo) {
        this.codigo = codigo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

}

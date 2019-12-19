package com.grevoltec.cosechaguiddins.services.models.response;

import com.google.gson.annotations.SerializedName;
import com.grevoltec.cosechaguiddins.services.models.AbsModel;

public class SpListarUsuariosMovilResult extends AbsModel {

    @SerializedName("idUsuario")
    private String idUsuario;
    @SerializedName("usuario")
    private String usuario;
    @SerializedName("claveUsuario")
    private String claveUsuario;
    @SerializedName("nombreUsuario")
    private String nombreUsuario;

    public SpListarUsuariosMovilResult() {
    }

    public SpListarUsuariosMovilResult(String idUsuario, String usuario, String claveUsuario, String nombreUsuario) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.claveUsuario = claveUsuario;
        this.nombreUsuario = nombreUsuario;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClaveUsuario() {
        return claveUsuario;
    }

    public void setClaveUsuario(String claveUsuario) {
        this.claveUsuario = claveUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

}

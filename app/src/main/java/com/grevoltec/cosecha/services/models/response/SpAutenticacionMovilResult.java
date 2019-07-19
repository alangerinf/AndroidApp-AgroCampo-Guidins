package com.grevoltec.cosecha.services.models.response;

import com.google.gson.annotations.SerializedName;
import com.grevoltec.cosecha.services.models.AbsModel;

public class SpAutenticacionMovilResult extends AbsModel {

    @SerializedName("idEmpresa")
    private String idEmpresa ;
    @SerializedName("idPerfil")
    private String idPerfil;
    @SerializedName("idUsuario")
    private String idUsuario;
    @SerializedName("nombreUsuario")
    private String nombreUsuario;
    @SerializedName("usuario")
    private String usuario;

    public SpAutenticacionMovilResult() {
    }

    public SpAutenticacionMovilResult(String idEmpresa, String idPerfil, String idUsuario, String nombreUsuario, String usuario) {
        this.idEmpresa = idEmpresa;
        this.idPerfil = idPerfil;
        this.idUsuario = idUsuario;
        this.nombreUsuario = nombreUsuario;
        this.usuario = usuario;
    }

    public String getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(String idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public String getIdPerfil() {
        return idPerfil;
    }

    public void setIdPerfil(String idPerfil) {
        this.idPerfil = idPerfil;
    }

    public String getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }
}

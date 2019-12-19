package com.grevoltec.cosechaguiddins.entities;

import com.grevoltec.cosechaguiddins.entities.core.AbsEntity;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "usuarios")
public class UsuarioEntity extends AbsEntity {

    @DatabaseField(id = true, columnName = ID)
    private int idUsuario;
    @DatabaseField(columnName = "usaurio")
    private String usuario;
    @DatabaseField(columnName = "usuario_clave")
    private String claveUsuario;
    @DatabaseField(columnName = "usuario_nombre")
    private String nombreUsuario;
    @DatabaseField(columnName = "empresa")
    private int idEmpresa;

    public UsuarioEntity() {
    }

    public UsuarioEntity(int idUsuario, String usuario, String claveUsuario, String nombreUsuario, int idEmpresa) {
        this.idUsuario = idUsuario;
        this.usuario = usuario;
        this.claveUsuario = claveUsuario;
        this.nombreUsuario = nombreUsuario;
        this.idEmpresa = idEmpresa;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
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

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    @Override
    public String toString() {
        return "UsuarioEntity{" +
                "idUsuario=" + idUsuario +
                ", usuario='" + usuario + '\'' +
                ", claveUsuario='" + claveUsuario + '\'' +
                ", nombreUsuario='" + nombreUsuario + '\'' +
                ", idEmpresa=" + idEmpresa +
                '}';
    }
}
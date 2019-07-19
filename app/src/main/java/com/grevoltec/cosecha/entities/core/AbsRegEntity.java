package com.grevoltec.cosecha.entities.core;

import com.j256.ormlite.field.DatabaseField;

public abstract class AbsRegEntity extends  AbsEntity {

    @DatabaseField( columnName = ID, generatedId = true)
    protected long poid;

    @DatabaseField(columnName = "codigo_sync")
    protected String synCodigo;

    public AbsRegEntity() {
    }

    public AbsRegEntity(long poid, String synCodigo) {
        this.poid = poid;
        this.synCodigo = synCodigo;
    }

    public long getPoid() {
        return poid;
    }

    public void setPoid(long poid) {
        this.poid = poid;
    }

    public String getSynCodigo() {
        return synCodigo;
    }

    public void setSynCodigo(String synCodigo) {
        this.synCodigo = synCodigo;
    }

    public boolean isSync(){
        if(this.synCodigo == null) return  false;
        if(this.synCodigo.equalsIgnoreCase("")) return  false;
        return true;
    }

    public boolean isDB(){
        return (this.poid >0);
    }

}

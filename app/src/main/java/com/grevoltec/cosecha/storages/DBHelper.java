package com.grevoltec.cosecha.storages;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.grevoltec.cosecha.app.AppCosecha;
import com.grevoltec.cosecha.entities.AcopioEntity;
import com.grevoltec.cosecha.entities.CamionEntity;
import com.grevoltec.cosecha.entities.CosechaEntity;
import com.grevoltec.cosecha.entities.CultivoEntity;
import com.grevoltec.cosecha.entities.FundoEntity;
import com.grevoltec.cosecha.entities.PalletEntity;
import com.grevoltec.cosecha.entities.PlantaEntity;
import com.grevoltec.cosecha.entities.RecepcionEntity;
import com.grevoltec.cosecha.entities.TurnoEntity;
import com.grevoltec.cosecha.entities.UsuarioEntity;
import com.grevoltec.cosecha.entities.ViajeEntity;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.List;

public class DBHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "cosecha_databases.db";
    private static final int DATABASE_VERSION = 2;

    private Dao<AcopioEntity, Integer> acopioDao;
    private Dao<CamionEntity, Integer> camionDao;
    private Dao<CultivoEntity, Integer> cultivoDao;
    private Dao<FundoEntity, Integer> fundoDao;
    private Dao<PlantaEntity, Integer> plantaDao;
    private Dao<TurnoEntity, Integer> turnoDao;
    private Dao<UsuarioEntity, Integer> usuarioDao;

    private Dao<CosechaEntity, Long> cosechaDao;
    private Dao<PalletEntity, Long> palletDao;
    private Dao<RecepcionEntity, Long> recepcionDao;
    private Dao<ViajeEntity, Long> viajeDao;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, AcopioEntity.class);
            TableUtils.createTable(connectionSource, CamionEntity.class);
            TableUtils.createTable(connectionSource, CosechaEntity.class);
            TableUtils.createTable(connectionSource, CultivoEntity.class);
            TableUtils.createTable(connectionSource, FundoEntity.class);
            TableUtils.createTable(connectionSource, PalletEntity.class);
            TableUtils.createTable(connectionSource, PlantaEntity.class);
            TableUtils.createTable(connectionSource, RecepcionEntity.class);
            TableUtils.createTable(connectionSource, TurnoEntity.class);
            TableUtils.createTable(connectionSource, UsuarioEntity.class);
            TableUtils.createTable(connectionSource, ViajeEntity.class);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        onCreate(db, connectionSource);

        try {
            TableUtils.dropTable(connectionSource, AcopioEntity.class,false);
            TableUtils.dropTable(connectionSource, CamionEntity.class,true);
            TableUtils.dropTable(connectionSource, CosechaEntity.class,true);
            TableUtils.dropTable(connectionSource, CultivoEntity.class,true);
            TableUtils.dropTable(connectionSource, FundoEntity.class,true);
            TableUtils.dropTable(connectionSource, PalletEntity.class,true);
            TableUtils.dropTable(connectionSource, PlantaEntity.class,true);
            TableUtils.dropTable(connectionSource, RecepcionEntity.class,true);
            TableUtils.dropTable(connectionSource, TurnoEntity.class,true);
            TableUtils.dropTable(connectionSource, UsuarioEntity.class,true);
            TableUtils.dropTable(connectionSource, ViajeEntity.class,true);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public Dao<AcopioEntity, Integer> getAcopioDao() throws SQLException{
        if (acopioDao == null) {
            acopioDao = getDao(AcopioEntity.class);
        }
        return acopioDao;
    }

    public Dao<CamionEntity, Integer> getCamionDao() throws SQLException{
        if (camionDao == null) {
            camionDao = getDao(CamionEntity.class);
        }
        return camionDao;
    }

    public Dao<CultivoEntity, Integer> getCultivoDao() throws SQLException{
        if (cultivoDao == null) {
            cultivoDao = getDao(CultivoEntity.class);
        }
        return cultivoDao;
    }

    public Dao<FundoEntity, Integer> getFundoDao() throws SQLException{
        if (fundoDao == null) {
            fundoDao = getDao(FundoEntity.class);
        }
        return fundoDao;
    }

    public Dao<PlantaEntity, Integer> getPlantaDao() throws SQLException{
        if (plantaDao == null) {
            plantaDao = getDao(PlantaEntity.class);
        }
        return plantaDao;
    }

    public Dao<TurnoEntity, Integer> getTurnoDao() throws SQLException{
        if (turnoDao == null) {
            turnoDao = getDao(TurnoEntity.class);
        }
        return turnoDao;
    }

    public Dao<UsuarioEntity, Integer> getUsuarioDao() throws SQLException{
        if (usuarioDao == null) {
            usuarioDao = getDao(UsuarioEntity.class);
        }
        return usuarioDao;
    }

    public Dao<CosechaEntity, Long> getCosechaDao() throws SQLException{
        if (cosechaDao == null) {
            cosechaDao = getDao(CosechaEntity.class);
        }
        return cosechaDao;
    }

    public Dao<PalletEntity, Long> getPalletDao() throws SQLException{
        if (palletDao == null) {
            palletDao = getDao(PalletEntity.class);
        }
        return palletDao;
    }

    public Dao<RecepcionEntity, Long> getRecepcionDao() throws SQLException{
        if (recepcionDao == null) {
            recepcionDao = getDao(RecepcionEntity.class);
        }
        return recepcionDao;
    }

    public Dao<ViajeEntity, Long> getViajeDao() throws SQLException{
        if (viajeDao == null) {
            viajeDao = getDao(ViajeEntity.class);
        }
        return viajeDao;
    }

    /* Clears */
    public void clearAllUsuarios() throws SQLException {
        List<UsuarioEntity> data = getUsuarioDao().queryForAll();
        for (UsuarioEntity entity: data) {
            getUsuarioDao().delete(entity);
        }
    }

    public void clearAllFundos() throws SQLException  {
        List<FundoEntity> data = getFundoDao().queryForAll();
        for (FundoEntity entity: data) {
            getFundoDao().delete(entity);
        }
    }

    public void clearAllCultivos() throws SQLException  {
        List<CultivoEntity> data = getCultivoDao().queryForAll();
        for (CultivoEntity entity: data) {
            getCultivoDao().delete(entity);
        }
    }

    public void clearAllCaminiones() throws SQLException  {
        List<CamionEntity> data = getCamionDao().queryForAll();
        for (CamionEntity entity: data) {
            getCamionDao().delete(entity);
        }
    }

    public void clearAllPlantas() throws SQLException  {
        List<PlantaEntity> data = getPlantaDao().queryForAll();
        for (PlantaEntity entity: data) {
            getPlantaDao().delete(entity);
        }
    }

    public void clearAllTurnos() throws SQLException  {
        List<TurnoEntity> data = getTurnoDao().queryForAll();
        for (TurnoEntity entity: data) {
            getTurnoDao().delete(entity);
        }
    }

    public void clearAllAcopios() throws SQLException  {
        List<AcopioEntity> data = getAcopioDao().queryForAll();
        for (AcopioEntity entity: data) {
            getAcopioDao().delete(entity);
        }
    }

    public void clearAllData() throws SQLException  {
        clearAllCosecha();
        clearAllPallet();
        clearAllDespacho();
        clearAllRecepcion();
    }

    private void clearAllCosecha() throws SQLException  {
        List<CosechaEntity> data = getCosechaDao().queryForAll();
        for (CosechaEntity entity: data) {
            getCosechaDao().delete(entity);
        }
    }

    private void clearAllPallet() throws SQLException  {
        List<PalletEntity> data = getPalletDao().queryForAll();
        for (PalletEntity entity: data) {
            getPalletDao().delete(entity);
        }
    }

    private void clearAllDespacho() throws SQLException  {
        List<ViajeEntity> data = getViajeDao().queryForAll();
        for (ViajeEntity entity: data) {
            getViajeDao().delete(entity);
        }
    }

    private void clearAllRecepcion() throws SQLException  {
        List<RecepcionEntity> data = getRecepcionDao().queryForAll();
        for (RecepcionEntity entity: data) {
            getRecepcionDao().delete(entity);
        }
    }

    @Override
    public void close() {
        super.close();
        acopioDao = null;
        camionDao = null;
        cultivoDao = null;
        fundoDao = null;
        plantaDao = null;
        turnoDao = null;
        usuarioDao = null;

        cosechaDao = null;
        palletDao = null;
        recepcionDao = null;
        viajeDao = null;
    }

}

package com.grevoltec.cosechaguiddins.views.secure.fragments.recepcion;

import android.widget.Button;

import com.grevoltec.cosechaguiddins.entities.PlantaEntity;
import com.grevoltec.cosechaguiddins.entities.RecepcionEntity;
import com.grevoltec.cosechaguiddins.util.AppException;

import java.util.List;

public interface IRecepPallet {

    void goToFirstStep();
    void goToSecondStep(PlantaEntity plantaSelected);
    void readQR(String qr, double peso) throws AppException;
    void validateQR(String qrm) throws AppException;
    Button getActionButton();
    List<RecepcionEntity> getRecepcion();
    PlantaEntity getPlantaSelected();
    void setOnRecepcionListener(IRecepPalletChangeListener listener);

    interface IRecepPalletChangeListener {
        void onRecepcionChange();
        void onClickActionButton();
    }

}
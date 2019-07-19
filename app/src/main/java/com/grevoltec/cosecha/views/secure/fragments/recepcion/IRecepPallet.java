package com.grevoltec.cosecha.views.secure.fragments.recepcion;

import android.widget.Button;

import com.grevoltec.cosecha.entities.PlantaEntity;
import com.grevoltec.cosecha.entities.RecepcionEntity;
import com.grevoltec.cosecha.util.AppException;

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
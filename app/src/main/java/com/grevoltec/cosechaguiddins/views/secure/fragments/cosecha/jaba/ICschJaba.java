package com.grevoltec.cosechaguiddins.views.secure.fragments.cosecha.jaba;

import android.widget.Button;

import com.grevoltec.cosechaguiddins.entities.CosechaEntity;
import com.grevoltec.cosechaguiddins.entities.FundoEntity;
import com.grevoltec.cosechaguiddins.entities.TurnoEntity;
import com.grevoltec.cosechaguiddins.util.AppException;

import java.util.List;

public interface ICschJaba {

    void goToFirstStep();
    void goToSecondStep(FundoEntity fundoSelected, TurnoEntity turnoSelected,int cantPersonas);
    void readQR(String qr) throws AppException;
    void validateQR(String qr) throws AppException;
    Button getActionButton();
    List<CosechaEntity> getCosecha();
    TurnoEntity getTurnoSelected();
    void setOnCosechaListener(ICschJabaChangeListener listener);

    interface ICschJabaChangeListener {
        void onCosechaChange();
        void onClickActionButton();
    }

}
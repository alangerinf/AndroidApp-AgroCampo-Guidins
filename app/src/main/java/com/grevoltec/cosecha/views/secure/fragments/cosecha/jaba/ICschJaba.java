package com.grevoltec.cosecha.views.secure.fragments.cosecha.jaba;

import android.widget.Button;

import com.grevoltec.cosecha.entities.CosechaEntity;
import com.grevoltec.cosecha.entities.FundoEntity;
import com.grevoltec.cosecha.entities.TurnoEntity;
import com.grevoltec.cosecha.util.AppException;

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
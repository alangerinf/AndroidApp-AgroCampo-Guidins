package com.grevoltec.cosecha.views.secure.fragments.despacho.viaje;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.grevoltec.cosecha.R;
import com.grevoltec.cosecha.app.AppCosecha;
import com.grevoltec.cosecha.entities.CamionEntity;
import com.grevoltec.cosecha.util.AppException;
import com.grevoltec.cosecha.util.QrUtils;
import com.grevoltec.cosecha.views.secure.fragments.core.fragment.AbsQrFragment;
import com.grevoltec.cosecha.views.secure.fragments.core.holder.AbsViewHolder;
import com.grevoltec.cosecha.views.secure.fragments.despacho.IViajeCrear;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.List;

@EFragment(R.layout.desp_crear_frag)
public class DespViajeCrearFragment extends AbsQrFragment {

    @ViewById(R.id.rowCamion)
    protected CardView rowCamion;
    protected CamionViewHolder camionViewHolder;
    @ViewById(R.id.lblError)
    protected TextView lblError;
    @ViewById(R.id.rowError)
    protected CardView rowError;
    @ViewById(R.id.btnLecturar)
    protected FloatingActionButton btnLecturar;

    protected IViajeCrear onStepChangeListener;
    protected String qr;

    @AfterViews
    protected void onAfterViews() {
        rowCamion.setVisibility(View.GONE);
        setupQRCodeReader();
        camionViewHolder = new CamionViewHolder(rowCamion);
        loadQRTest();
    }

    protected void loadQRTest(){
        if(AppCosecha.IsTest) {
            onQRCodeRead("C0001");
        }
    }

    public DespViajeCrearFragment setOnStepChangeListener(IViajeCrear onStepChangeListener) {
        this.onStepChangeListener = onStepChangeListener;
        return this;
    }

    @Override
    protected void onQRCodeRead(String text){
        qr = null;
        rowError.setVisibility(View.GONE);
        rowCamion.setVisibility(View.GONE);
        try{
            onStepChangeListener.validateQRCaminion(text);
            qr = text;
            /* CamionViewHolder  */
            camionViewHolder.lblCamion.setText(QrUtils.QR_CAMIONES.getCodigo(qr));
            camionViewHolder.lblPlaca.setText(getPlaca(qr));
            rowCamion.setVisibility(View.VISIBLE);
        }catch (AppException ex){
            lblError.setText(ex.getMessage());
            rowError.setVisibility(View.VISIBLE);
        }catch (Exception ex1){
            ex1.printStackTrace();
            lblError.setText(R.string.ups_error_inesperado);
            rowError.setVisibility(View.VISIBLE);
        }
        /*
        btnLecturar.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopCameraQR();
            }
        }, 200);
         */
    }

    private String getPlaca(String qr){
        try{
            List<CamionEntity> data = AppCosecha.getHelper().getCamionDao().queryForEq("qr",qr);
            if(data.size() == 1){
                return data.get(0).getPlacaCamion();
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Click(R.id.btnLecturar)
    protected void onClickBtnLecturar(){
        if(qr== null){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.error);
            builder.setMessage(R.string.lea_qr_camion_para_continuar);
            builder.setCancelable(false);
            builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            return;
        }
        onStepChangeListener.goToSecondStep(qr);
    }

    @Override
    public void onTorchOn() {

    }

    @Override
    public void onTorchOff() {

    }

    /* CamionViewHolder */
    public static class CamionViewHolder extends AbsViewHolder {

        public TextView lblCamion;
        public TextView lblPlaca;

        public CamionViewHolder(View view) {
            super(view);
            this.lblCamion = view.findViewById(R.id.lblCamion);
            this.lblPlaca = view.findViewById(R.id.lblPlaca);
        }

    }

}

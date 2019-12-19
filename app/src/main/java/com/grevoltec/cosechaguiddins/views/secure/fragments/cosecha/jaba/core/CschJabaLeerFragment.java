package com.grevoltec.cosechaguiddins.views.secure.fragments.cosecha.jaba.core;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.grevoltec.cosechaguiddins.R;
import com.grevoltec.cosechaguiddins.util.AppException;
import com.grevoltec.cosechaguiddins.util.QrUtils;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.fragment.AbsQrFragment;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.holder.AbsViewHolder;
import com.grevoltec.cosechaguiddins.views.secure.fragments.cosecha.jaba.ICschJaba;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.csch_read_jaba_frag)
public class CschJabaLeerFragment extends AbsQrFragment {

    @ViewById(R.id.rowJaba)
    protected CardView rowJaba;
    protected JabaViewHolder jabaViewHolder;
    @ViewById(R.id.lblError)
    protected TextView lblError;
    @ViewById(R.id.rowError)
    protected CardView rowError;
    @ViewById(R.id.btnLecturar)
    protected FloatingActionButton btnLecturar;

    protected ICschJaba onStepChangeListener;
    protected String qr;

    @AfterViews
    protected void onAfterViews() {
        rowJaba.setVisibility(View.GONE);
        setupQRCodeReader();
        jabaViewHolder = new JabaViewHolder(rowJaba);
    }

    @Override
    protected void onClickQRImage() {
        rowJaba.setVisibility(View.GONE);
        super.onClickQRImage();
    }

    public CschJabaLeerFragment setOnStepChangeListener(ICschJaba onStepChangeListener) {
        this.onStepChangeListener = onStepChangeListener;
        return this;
    }

    @Override
    protected void onQRCodeRead(String text){
        qr = null;
        rowError.setVisibility(View.GONE);
        rowJaba.setVisibility(View.GONE);
        try{
            onStepChangeListener.validateQR(text);
            qr = text;

            /* jabaViewHolder  */
            jabaViewHolder.lblDni.setText(QrUtils.QR_JABAS.getDNI(qr));
            jabaViewHolder.lblNombre.setText(QrUtils.QR_JABAS.getCosechador(qr));
            jabaViewHolder.lblTurno.setText(QrUtils.QR_JABAS.getFecha(qr)+" - "+onStepChangeListener.getTurnoSelected().getNombreTurno());
            jabaViewHolder.lblNro.setText(QrUtils.QR_JABAS.getNroEtiqueta(qr)+"/"+QrUtils.QR_JABAS.getTotalEtiqueta(qr));
            rowJaba.setVisibility(View.VISIBLE);

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

    String TAG = CschJabaLeerFragment.class.getSimpleName();
    @Click(R.id.btnLecturar)
    protected void onClickBtnLecturar(){
        rowError.setVisibility(View.GONE);
        if(qr != null){
            try{
                onStepChangeListener.readQR(qr);
                rowJaba.setVisibility(View.GONE);
                qr = null;
            }catch (AppException ex){
                lblError.setText(ex.getMessage());
                rowError.setVisibility(View.VISIBLE);
            }catch (Exception ex1){
                ex1.printStackTrace();
                Log.d(TAG,ex1.toString());
                lblError.setText(R.string.ups_error_inesperado);
                rowError.setVisibility(View.VISIBLE);
            }
        }else{
            lblError.setText(R.string.lea_qr_antes_continuar);
            rowError.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onTorchOn() {

    }

    @Override
    public void onTorchOff() {

    }

    /* JabaViewHolder */
    public static class JabaViewHolder extends AbsViewHolder {

        public TextView lblNombre;
        public TextView lblTurno;
        public TextView lblDni;
        public TextView lblNro;

        public JabaViewHolder(View view) {
            super(view);
            this.lblNombre = view.findViewById(R.id.lblNombre);
            this.lblTurno = view.findViewById(R.id.lblTurno);
            this.lblDni = view.findViewById(R.id.lblDni);
            this.lblNro = view.findViewById(R.id.lblNro);
        }

    }

}
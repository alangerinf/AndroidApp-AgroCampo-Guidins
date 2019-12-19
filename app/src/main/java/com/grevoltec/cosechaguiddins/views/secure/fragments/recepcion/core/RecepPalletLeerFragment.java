package com.grevoltec.cosechaguiddins.views.secure.fragments.recepcion.core;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.text.InputType;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grevoltec.cosechaguiddins.R;
import com.grevoltec.cosechaguiddins.app.AppCosecha;
import com.grevoltec.cosechaguiddins.util.AppException;
import com.grevoltec.cosechaguiddins.util.EntityUtils;
import com.grevoltec.cosechaguiddins.util.QrUtils;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.fragment.AbsQrFragment;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.holder.AbsViewHolder;
import com.grevoltec.cosechaguiddins.views.secure.fragments.recepcion.IRecepPallet;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

@EFragment(R.layout.recep_read_palt_frag)
public class RecepPalletLeerFragment extends AbsQrFragment {

    @ViewById(R.id.rowPallet)
    protected CardView rowPallet;
    protected PalletViewHolder palletViewHolder;
    @ViewById(R.id.lblError)
    protected TextView lblError;
    @ViewById(R.id.rowError)
    protected CardView rowError;
    @ViewById(R.id.btnLecturar)
    protected FloatingActionButton btnLecturar;

    protected IRecepPallet onStepChangeListener;
    protected String qr;

    @AfterViews
    protected void onAfterViews() {
        rowPallet.setVisibility(View.GONE);
        setupQRCodeReader();
        palletViewHolder = new PalletViewHolder(rowPallet);
        loadQRTest();
    }

    protected void loadQRTest(){
        if(AppCosecha.IsTest) {
            onQRCodeRead("P010001170501");
        }
    }

    @Override
    protected void onClickQRImage() {
        rowPallet.setVisibility(View.GONE);
        super.onClickQRImage();
    }

    public RecepPalletLeerFragment setOnStepChangeListener(IRecepPallet onStepChangeListener) {
        this.onStepChangeListener = onStepChangeListener;
        return this;
    }

    @Override
    protected void onQRCodeRead(String text){
        qr = null;
        rowError.setVisibility(View.GONE);
        rowPallet.setVisibility(View.GONE);
        try{
            onStepChangeListener.validateQR(text);
            qr = text;
            /* ViajeViewHolder  */
            palletViewHolder.lblPallet.setText(QrUtils.QR_PALLETS.getCorrelativo(qr));
            palletViewHolder.lblNombre.setText(EntityUtils.ACOPIO.getNombreByCodigoOrDefault(QrUtils.QR_PALLETS.getAcopio(qr)));
            //palletViewHolder.lblNro.setText(EntityUtils.PALLET.getSizeByQROrDefault(qr) );
            rowPallet.setVisibility(View.VISIBLE);
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

    @Click(R.id.btnLecturar)
    protected void onClickBtnLecturar(){
        rowError.setVisibility(View.GONE);
        if(qr != null){
            final EditText edittext = new EditText(getContext());
            edittext.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
            edittext.setGravity(Gravity.CENTER);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
            edittext.setLayoutParams(params);
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.ingrese_peso);
            builder.setView(edittext);
            builder.setPositiveButton(R.string.aceptar, null);
            builder.setNegativeButton(R.string.cancelar, null);
            final AlertDialog dialog = builder.create();
            dialog.setOnShowListener(new DialogInterface.OnShowListener() {
                @Override
                public void onShow(DialogInterface dialogInterface) {
                    Button button = ((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE);
                    button.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            try{
                                double peso = 0;
                                try{
                                    peso = Double.parseDouble(edittext.getText().toString());
                                }catch (Exception ex1){
                                    throw new AppException(getString(R.string.ingrese_peso_valido));
                                }
                                onStepChangeListener.readQR(qr,peso);
                                rowPallet.setVisibility(View.GONE);
                                qr = null;
                            }catch (AppException ex){
                                lblError.setText(ex.getMessage());
                                rowError.setVisibility(View.VISIBLE);
                            }catch (Exception ex1){
                                ex1.printStackTrace();
                                lblError.setText(R.string.ups_error_inesperado);
                                rowError.setVisibility(View.VISIBLE);
                            }
                            dialog.dismiss();
                        }
                    });
                }
            });
            dialog.show();
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

    /* ViajeViewHolder */
    public static class PalletViewHolder extends AbsViewHolder {

        public TextView lblNombre;
        public TextView lblPallet;
        public TextView lblNro;

        public PalletViewHolder(View view) {
            super(view);
            this.lblNombre = view.findViewById(R.id.lblNombre);
            this.lblPallet = view.findViewById(R.id.lblPallet);
            this.lblNro = view.findViewById(R.id.lblNro);
        }

    }

}
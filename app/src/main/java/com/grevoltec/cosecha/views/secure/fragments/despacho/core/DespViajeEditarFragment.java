package com.grevoltec.cosecha.views.secure.fragments.despacho.core;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.TextView;

import com.grevoltec.cosecha.R;
import com.grevoltec.cosecha.app.AppCosecha;
import com.grevoltec.cosecha.util.AppException;
import com.grevoltec.cosecha.util.EntityUtils;
import com.grevoltec.cosecha.util.QrUtils;
import com.grevoltec.cosecha.views.secure.fragments.core.fragment.AbsQrFragment;
import com.grevoltec.cosecha.views.secure.fragments.core.holder.AbsViewHolder;
import com.grevoltec.cosecha.views.secure.fragments.despacho.IViajeModifica;
import com.j256.ormlite.stmt.UpdateBuilder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;

@EFragment(R.layout.desp_editar_frag)
public class DespViajeEditarFragment extends AbsQrFragment implements IViajeModifica.IViajeModificaChangeListener {

    @ViewById(R.id.rowViaje)
    protected CardView rowViaje;
    protected ViajeViewHolder viajeViewHolder;
    @ViewById(R.id.lblError)
    protected TextView lblError;
    @ViewById(R.id.rowError)
    protected CardView rowError;

    protected IViajeModifica onStepChangeListener;
    protected String qr;

    @AfterViews
    protected void onAfterViews() {
        rowViaje.setVisibility(View.GONE);
        setupQRCodeReader();
        viajeViewHolder = new ViajeViewHolder(rowViaje);
        onStepChangeListener.setListener(this);
        rowError.postDelayed(new Runnable() {
            @Override
            public void run() {
                loadQRTest();
            }
        }, 4500);
    }

    protected void loadQRTest(){
        if(AppCosecha.IsTest) {
            onQRCodeRead("C0002");
        }
    }

    @Override
    protected void onClickQRImage() {
        rowViaje.setVisibility(View.GONE);
        super.onClickQRImage();
    }

    public DespViajeEditarFragment setOnStepChangeListener(IViajeModifica onStepChangeListener) {
        this.onStepChangeListener = onStepChangeListener;
        return this;
    }

    @Override
    protected void onQRCodeRead(String text){
        qr = null;
        rowError.setVisibility(View.GONE);
        rowViaje.setVisibility(View.GONE);
        try{
            onStepChangeListener.validateQR(text);
            qr = text;
            /* ViajeViewHolder  */
            viajeViewHolder.lblNombre.setText(EntityUtils.CAMION.getPlacaByCodigoOrDefault(QrUtils.QR_CAMIONES.getCodigo(qr)));
            viajeViewHolder.lblJaba.setText(qr);
            rowViaje.setVisibility(View.VISIBLE);
        }catch (AppException ex){
            lblError.setText(ex.getMessage());
            rowError.setVisibility(View.VISIBLE);
        }catch (Exception ex1){
            ex1.printStackTrace();
            lblError.setText(R.string.ups_error_inesperado);
            rowError.setVisibility(View.VISIBLE);
        }
        rowError.postDelayed(new Runnable() {
            @Override
            public void run() {
                stopCameraQR();
            }
        }, 200);
    }

    @Override
    public void onClickActionButton() {
        try {
            UpdateBuilder update = AppCosecha.getHelper().getViajeDao().updateBuilder();
            update.updateColumnValue("camion_qr", qr)
                    .where().eq("camion_qr", onStepChangeListener.getQrCamionViaje())
                    .and().eq("viaje_completo", 0);
            update.update();

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.mensaje);
            builder.setCancelable(false);
            builder.setMessage(R.string.confirmacion_registro_local);
            builder.setPositiveButton(R.string.continuar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    getActivity().onBackPressed();
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /* ViajeViewHolder */
    public static class ViajeViewHolder extends AbsViewHolder {

        public TextView lblNombre;
        public TextView lblJaba;
        public TextView lblNro;

        public ViajeViewHolder(View view) {
            super(view);
            this.lblNombre = view.findViewById(R.id.lblNombre);
            this.lblJaba = view.findViewById(R.id.lblJaba);
            this.lblNro = view.findViewById(R.id.lblNro);
        }

    }

}

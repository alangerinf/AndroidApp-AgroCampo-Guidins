package com.grevoltec.cosechaguiddins.views.secure.fragments.cosecha.jaba.core;

import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.grevoltec.cosechaguiddins.R;
import com.grevoltec.cosechaguiddins.app.AppCosecha;
import com.grevoltec.cosechaguiddins.entities.FundoEntity;
import com.grevoltec.cosechaguiddins.entities.TurnoEntity;
import com.grevoltec.cosechaguiddins.views.secure.fragments.cosecha.jaba.ICschJaba;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.fragment.AbsFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.csch_sel_fundo_frag)
public class CschSelFundoFragment extends AbsFragment {

    @ViewById(R.id.lblTitle)
    protected TextView lblTitle;

    @ViewById(R.id.lblFundo)
    protected TextView lblFundo;
    @ViewById(R.id.txtFundo)
    protected TextView txtFundo;
    @ViewById(R.id.selFundo)
    protected CardView selFundo;

    @ViewById(R.id.lblLote)
    protected TextView lblLote;
    @ViewById(R.id.txtLote)
    protected TextView txtLote;
    @ViewById(R.id.selLote)
    protected CardView selLote;

    @ViewById(R.id.lblCantPersonas)
    protected TextView lblCantPersonas;
    @ViewById(R.id.eTxtCantPersonas)
    protected EditText eTxtCantPersonas;

    @ViewById(R.id.selCantPersonas)
    protected CardView selCantPersonas;
    @ViewById(R.id.cBoxEditarCantPersonas)
    protected CheckBox cBoxEditarCantPersonas;





    @ViewById(R.id.btnLecturar)
    protected FloatingActionButton btnLecturar;

    protected ICschJaba onStepChangeListener;
    protected FundoEntity fundoSelected;
    protected TurnoEntity turnoSelected;
    protected int idx;


    @Override
    public void onStart() {
        super.onStart();
        cBoxEditarCantPersonas.setOnCheckedChangeListener((buttonView, isChecked) -> {
                eTxtCantPersonas.setEnabled(isChecked);
        });
    }


    @AfterViews
    protected void onAfterViews() {
        try{
            List<FundoEntity> fundos = getFundos();
            if(fundos.size() > 0){
                fundoSelected = fundos.get(0);
            }
            onChangeFundoSelected();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    protected void onChangeFundoSelected(){
        turnoSelected = null;
        txtFundo.setText("");
        if(fundoSelected != null){
            txtFundo.setText(fundoSelected.getNombreFundo());
            List<TurnoEntity> turnos = getLotes(fundoSelected.getIdFundo());
            if(turnos.size() > 0){
                turnoSelected = turnos.get(0);
            }
        }
        onChangeTurnoSelected();
    }

    protected void onChangeTurnoSelected(){
        txtLote.setText("");
        if(turnoSelected != null)
            txtLote.setText(turnoSelected.getNombreTurno());
    }

    @Click(R.id.btnLecturar)
    protected void onClickBtnLecturar(){


        int cantPersonas=1;

        if( eTxtCantPersonas.getText().toString().equals("") || Integer.parseInt(eTxtCantPersonas.getText().toString())==0 ){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.error);
            builder.setMessage("Ingrese una Cantidad de Personas");
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
        }else {
            cantPersonas=Integer.parseInt(eTxtCantPersonas.getText().toString());
        }

        if(fundoSelected== null){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.error);
            builder.setMessage(R.string.seleccione_fundo_para_continuar);
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
        if(turnoSelected== null){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.error);
            builder.setMessage(R.string.seleccione_turno_para_continuar);
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
        onStepChangeListener.goToSecondStep(fundoSelected, turnoSelected,cantPersonas);
    }
    String TAG = CschSelFundoFragment.class.getSimpleName();

    @Click(R.id.selFundo)
    protected void onClickSelFundo(){
        Log.d(TAG,"onClickSelFundo");
        final List<FundoEntity> fundos = getFundos();
        if(fundos.size() > 0){
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.seleccione_fundo);
            String[] items = new String[fundos.size()];
            int checkedItem = 1;
            int idFundoSelected = (fundoSelected != null ? fundoSelected.getIdFundo() :  -1);
            for (int i=0; i< fundos.size(); i++){
                FundoEntity entity = fundos.get(i);
                items[i] = entity.getNombreFundo();
                if(entity.getIdFundo() == idFundoSelected){
                    checkedItem = i;
                }
            }
            builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    idx = which;
                }
            });
            builder.setPositiveButton(R.string.seleccionar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if(idx >= 0) {
                        fundoSelected = fundos.get(idx);
                        onChangeFundoSelected();
                    }
                }
            });
            builder.setNegativeButton(R.string.cancelar, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    @Click(R.id.selLote)
    protected void onClickSelLote(){
        if(fundoSelected != null){
            final List<TurnoEntity> turnos = getLotes(fundoSelected.getIdFundo());
            if(turnos.size() > 0){
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.seleccione_turno);
                String[] items = new String[turnos.size()];
                int checkedItem = 1;
                int idTurnoSelected= (turnoSelected != null ? turnoSelected.getIdTurno() :  -1);
                for (int i=0; i< turnos.size(); i++){
                    TurnoEntity entity = turnos.get(i);
                    items[i] = entity.getNombreTurno();
                    if(entity.getIdTurno() == idTurnoSelected){
                        checkedItem = i;
                    }
                }
                builder.setSingleChoiceItems(items, checkedItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        idx = which;
                    }
                });
                builder.setPositiveButton(R.string.seleccionar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if(idx > 0) {
                            turnoSelected = turnos.get(idx);
                            onChangeTurnoSelected();
                        }
                    }
                });
                builder.setNegativeButton(R.string.cancelar, null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }
    }

    public CschSelFundoFragment setOnStepChangeListener(ICschJaba onStepChangeListener) {
        this.onStepChangeListener = onStepChangeListener;
        return this;
    }

    private List<FundoEntity> getFundos() {
        List<FundoEntity> result = new ArrayList<>();
        try{
            List<FundoEntity> data = AppCosecha.getHelper().getFundoDao().queryForAll();
            for (FundoEntity item: data) {
                result.add(item);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    private List<TurnoEntity> getLotes(int idFundo) {
        return getLotes(""+ idFundo);
    }

    private List<TurnoEntity> getLotes(String idFundo) {
        List<TurnoEntity> result = new ArrayList<>();
        try{
            List<TurnoEntity> data = AppCosecha.getHelper().getTurnoDao().queryForAll();
            for (TurnoEntity item: data) {
                if(idFundo.equalsIgnoreCase(item.getIdFundo()))
                    result.add(item);
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

}
package com.grevoltec.cosechaguiddins.views.secure.fragments.recepcion;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grevoltec.cosechaguiddins.R;
import com.grevoltec.cosechaguiddins.app.AppCosecha;
import com.grevoltec.cosechaguiddins.entities.RecepcionEntity;
import com.grevoltec.cosechaguiddins.util.AppException;
import com.grevoltec.cosechaguiddins.util.EntityUtils;
import com.grevoltec.cosechaguiddins.util.QrUtils;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.adapter.AbsRecyclerViewAdapter;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.holder.AbsViewHolder;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.holder.TitleViewHolder;
import com.grevoltec.cosechaguiddins.views.secure.fragments.recepcion.core.RecepResumenFragment;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.core_recycler_with_title_and_toolbar)
public class RecepPalletEditarFragment extends Fragment {

    @ViewById(R.id.toolbar)
    protected Toolbar toolbar;
    @ViewById(R.id.btnAction)
    protected Button btnAction;
    @ViewById(R.id.layHeader)
    protected LinearLayout layHeader;
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;

    protected RecepPalletRecyclerViewAdapter adapter;
    protected TitleViewHolder titleViewHolder;
    protected List<RecepcionEntity> entities;

    @AfterViews
    protected void onAfterViews() {
        entities = new ArrayList<>();
        toolbar.setTitle(R.string.editar_pallete);
        titleViewHolder = new TitleViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.core_title_small, null, false));
        layHeader.addView(titleViewHolder.itemView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        onRecepcionChange();
        btnAction.setText(R.string.grabar);
        btnAction.setVisibility(View.VISIBLE);
    }

    @Click(R.id.btnAction)
    public void onClickActionButton() {
        try {
            for (RecepcionEntity entity: entities) {
                AppCosecha.getHelper().getRecepcionDao().createOrUpdate(entity);
            }
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.mensaje);
            builder.setCancelable(false);
            builder.setMessage(R.string.confirmacion_registro_local);
            builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
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

    private List<RecepcionEntity> getEntities(){
        List<RecepcionEntity> result = new ArrayList<>();
        try{
            result.addAll(AppCosecha.getHelper().getRecepcionDao().queryForAll());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return result;
    }

    public void onRecepcionChange() {
        if(adapter == null){
            entities.clear();
            entities.addAll(getEntities());
            adapter = new RecepPalletRecyclerViewAdapter(getContext(), entities);
            recyclerView.setAdapter(adapter);
        }
        titleViewHolder.lblTitle.setText(entities.size()+" "+getString(R.string.pallets_leidos));
        adapter.notifyDataSetChanged();
    }

    /* RecepPalletRecyclerViewAdapter */
    public static class RecepPalletRecyclerViewAdapter extends AbsRecyclerViewAdapter<RecepcionEntity, RecepResumenFragment.RecepPalletRecyclerViewAdapter.ViajeViewHolder> {

        public RecepPalletRecyclerViewAdapter(Context context, List<RecepcionEntity> entities) {
            super(context, entities);
        }

        @Override
        public RecepResumenFragment.RecepPalletRecyclerViewAdapter.ViajeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new RecepResumenFragment.RecepPalletRecyclerViewAdapter.ViajeViewHolder(inflaterLayout(viewGroup, R.layout.palt_row_viaje));
        }

        @Override
        public void onBindViewHolder(final RecepResumenFragment.RecepPalletRecyclerViewAdapter.ViajeViewHolder viewHolder, int i) {
            final RecepcionEntity entity = mEntities.get(i);
            final String qr = entity.getQrpallet();
            viewHolder.lblJaba.setText(QrUtils.QR_PALLETS.getCorrelativo(qr));
            viewHolder.lblNombre.setText(EntityUtils.ACOPIO.getNombreByCodigoOrDefault(QrUtils.QR_PALLETS.getAcopio(qr)));
            viewHolder.lblNro.setText(entity.getPesopallet()+" "+mContext.getString(R.string.kg));
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final EditText edittext = new EditText(mContext);
                    edittext.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL | InputType.TYPE_NUMBER_FLAG_SIGNED);
                    edittext.setGravity(Gravity.CENTER);
                    edittext.setText(String.valueOf(entity.getPesopallet()));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
                    edittext.setLayoutParams(params);
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                    builder.setTitle(R.string.ingrese_peso);
                    builder.setMessage(viewHolder.lblNombre.getText().toString()+"  "+mContext.getString(R.string.codigo)+": "+ viewHolder.lblJaba.getText().toString());
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
                                            entity.setPesopallet(peso);
                                            viewHolder.lblNro.setText(entity.getPesopallet()+" "+mContext.getString(R.string.kg));
                                        }catch (Exception ex1){
                                            throw new AppException(mContext.getString(R.string.ingrese_peso_valido));
                                        }
                                    }catch (AppException ex){
                                        showAlert(ex.getMessage());
                                    }catch (Exception ex1){
                                        ex1.printStackTrace();
                                        showAlert( mContext.getString(R.string.ups_error_inesperado));
                                    }
                                    dialog.dismiss();
                                }
                            });
                        }
                    });
                    dialog.show();
                }
            });
        }

        public void showAlert(String mensaje){
            new AlertDialog.Builder(mContext)
                    .setTitle(R.string.error)
                    .setMessage(mensaje)
                    .setCancelable(false)
                    .setPositiveButton(R.string.aceptar, null).show();
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

}
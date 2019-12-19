package com.grevoltec.cosechaguiddins.views.secure.fragments.recepcion.core;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grevoltec.cosechaguiddins.R;
import com.grevoltec.cosechaguiddins.app.AppCosecha;
import com.grevoltec.cosechaguiddins.entities.RecepcionEntity;
import com.grevoltec.cosechaguiddins.util.AppException;
import com.grevoltec.cosechaguiddins.util.EntityUtils;
import com.grevoltec.cosechaguiddins.util.QrUtils;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.adapter.AbsRecyclerViewAdapter;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.adapter.SwipeController;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.adapter.SwipeControllerActions;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.holder.AbsViewHolder;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.holder.TitleViewHolder;
import com.grevoltec.cosechaguiddins.views.secure.fragments.recepcion.IRecepPallet;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.core_recycler_with_title)
public class RecepResumenFragment extends Fragment implements IRecepPallet.IRecepPalletChangeListener {

    @ViewById(R.id.layHeader)
    protected LinearLayout layHeader;
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;

    protected RecepPalletRecyclerViewAdapter adapter;
    protected TitleViewHolder titleViewHolder;
    protected IRecepPallet onStepChangeListener;
    protected SwipeController swipeController = null;

    protected List<RecepcionEntity> forDelete;

    @AfterViews
    protected void onAfterViews() {
        forDelete = new ArrayList<>();
        titleViewHolder = new TitleViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.core_title_small, null, false));
        layHeader.addView(titleViewHolder.itemView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        onRecepcionChange();
        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(final int position) {
                final RecepcionEntity entity = adapter.getItemByPosition(position);
                if(entity.isSync()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(R.string.error);
                    builder.setCancelable(false);
                    builder.setMessage(R.string.registro_no_borrado_ya_sync);
                    builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().onBackPressed();
                        }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else if(entity.isDB()){
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(R.string.alerta);
                    builder.setCancelable(false);
                    builder.setMessage(R.string.desea_borrar_registro_local);
                    builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            try{
                                forDelete.add(entity);
                                adapter.removeItem(position);
                                titleViewHolder.lblTitle.setText(getEntities().size()+" "+getString(R.string.pallets_leidos));
                            }catch (Exception ex){
                                ex.printStackTrace();
                            }
                        }
                    });
                    builder.setNegativeButton(R.string.cancelar, null);
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }else {
                    adapter.removeItem(position);
                }
            }
        });
        ItemTouchHelper itemTouchhelper = new ItemTouchHelper(swipeController);
        itemTouchhelper.attachToRecyclerView(recyclerView);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
                swipeController.onDraw(c);
            }
        });
    }

    private List<RecepcionEntity> getEntities(){
        return this.onStepChangeListener.getRecepcion();
    }

    public RecepResumenFragment setOnStepChangeListener(IRecepPallet onStepChangeListener) {
        this.onStepChangeListener = onStepChangeListener;
        this.onStepChangeListener.setOnRecepcionListener(this);
        return this;
    }

    @Override
    public void onRecepcionChange() {
        if(adapter == null){
            adapter = new RecepPalletRecyclerViewAdapter(getContext(), getEntities());
            recyclerView.setAdapter(adapter);
        }
        titleViewHolder.lblTitle.setText(getEntities().size()+" "+getString(R.string.pallets_leidos));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClickActionButton() {
        try {
            if(getEntities().size() == 0 && forDelete.size() == 0) throw new AppException(getString(R.string.agrege_accion_para_continuar));

            for (RecepcionEntity entity: forDelete) {
                AppCosecha.getHelper().getRecepcionDao().delete(entity);
            }
            for (RecepcionEntity entity: getEntities()) {
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
        } catch (AppException ae) {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.error);
            builder.setCancelable(false);
            builder.setMessage(ae.getMessage());
            builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            ae.printStackTrace();
        }
    }

    /* RecepPalletRecyclerViewAdapter */
    public static class RecepPalletRecyclerViewAdapter extends AbsRecyclerViewAdapter<RecepcionEntity, RecepPalletRecyclerViewAdapter.ViajeViewHolder> {

        public RecepPalletRecyclerViewAdapter(Context context, List<RecepcionEntity> entities) {
            super(context, entities);
        }

        @Override
        public ViajeViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new ViajeViewHolder(inflaterLayout(viewGroup, R.layout.palt_row_viaje));
        }

        @Override
        public void onBindViewHolder(ViajeViewHolder viewHolder, int i) {
            final RecepcionEntity entity = mEntities.get(i);
            final String qr = entity.getQrpallet();
            viewHolder.lblJaba.setText(QrUtils.QR_PALLETS.getCorrelativo(qr));
            viewHolder.lblNombre.setText(EntityUtils.ACOPIO.getNombreByCodigoOrDefault(QrUtils.QR_PALLETS.getAcopio(qr)));
            viewHolder.lblNro.setText(entity.getPesopallet()+" "+mContext.getString(R.string.kg));
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


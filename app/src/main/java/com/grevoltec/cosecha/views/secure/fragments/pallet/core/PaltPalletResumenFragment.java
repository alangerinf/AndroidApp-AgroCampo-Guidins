package com.grevoltec.cosecha.views.secure.fragments.pallet.core;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
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

import com.grevoltec.cosecha.R;
import com.grevoltec.cosecha.app.AppCosecha;
import com.grevoltec.cosecha.entities.PalletEntity;
import com.grevoltec.cosecha.entities.TurnoEntity;
import com.grevoltec.cosecha.util.AppException;
import com.grevoltec.cosecha.util.QrUtils;
import com.grevoltec.cosecha.views.secure.fragments.core.adapter.AbsRecyclerViewAdapter;
import com.grevoltec.cosecha.views.secure.fragments.core.adapter.SwipeController;
import com.grevoltec.cosecha.views.secure.fragments.core.adapter.SwipeControllerActions;
import com.grevoltec.cosecha.views.secure.fragments.core.fragment.AbsFragment;
import com.grevoltec.cosecha.views.secure.fragments.core.holder.AbsViewHolder;
import com.grevoltec.cosecha.views.secure.fragments.core.holder.TitleViewHolder;
import com.grevoltec.cosecha.views.secure.fragments.pallet.IPaltCrear;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.core_recycler_with_title)
public class PaltPalletResumenFragment extends AbsFragment implements IPaltCrear.IPaltJabaChangeListener {

    @ViewById(R.id.layHeader)
    protected LinearLayout layHeader;
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;

    protected PaltPalletRecyclerViewAdapter adapter;
    protected TitleViewHolder titleViewHolder;
    protected IPaltCrear onStepChangeListener;
    protected SwipeController swipeController = null;

    protected List<PalletEntity> forDelete;
    protected boolean showOnlyDNI = true;

    @AfterViews
    protected void onAfterViews() {
        forDelete = new ArrayList<>();
        titleViewHolder = new TitleViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.core_title_small, null, false));
        layHeader.addView(titleViewHolder.itemView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        onJabaChange();
        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(final int position) {
                final PalletEntity entity = adapter.getItemByPosition(position);
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
                                titleViewHolder.lblTitle.setText(getEntities().size()+" "+getString(R.string.jabas_leidas));
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
                    titleViewHolder.lblTitle.setText(getEntities().size()+" "+getString(R.string.jabas_leidas));
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

    private List<PalletEntity> getEntities(){
        return this.onStepChangeListener.getPallets();
    }

    public PaltPalletResumenFragment setOnStepChangeListener(IPaltCrear onStepChangeListener) {
        this.onStepChangeListener = onStepChangeListener;
        this.onStepChangeListener.setOnPalletsListener(this);
        return this;
    }

    @Override
    public void onJabaChange() {
        if(adapter == null){
            adapter = new PaltPalletRecyclerViewAdapter(getContext(), getEntities());
            adapter.showOnlyDNI = this.showOnlyDNI;
            recyclerView.setAdapter(adapter);
        }
        titleViewHolder.lblTitle.setText(getEntities().size()+" "+getString(R.string.jabas_leidas));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClickActionButton() {
        try {
            if(getEntities().size() == 0 && forDelete.size() == 0) throw new AppException(getString(R.string.agrege_registro_para_continuar));

            for (PalletEntity entity: forDelete) {
                AppCosecha.getHelper().getPalletDao().delete(entity);
            }
            for (PalletEntity entity: getEntities()) {
                AppCosecha.getHelper().getPalletDao().createOrUpdate(entity);
            }

            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setTitle(R.string.mensaje);
            builder.setCancelable(false);
            builder.setMessage(getString(R.string.confirmacion_registro_local));
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

    @Override
    public String deleteJaba(String qr) throws AppException {
        PalletEntity entity = null;
        for (PalletEntity item: onStepChangeListener.getPallets()) {
            if(qr.equalsIgnoreCase(item.getQrjaba())) {
                entity = item;
                break;
            };
        }
        if(entity == null) throw new AppException(getString(R.string.registro_no_encontrado_para_eliminar));
        if(entity.isSync()){
            throw new AppException(getString(R.string.registro_no_borrado_ya_sync));
        }else if(entity.isDB()){
            forDelete.add(entity);
            adapter.removeItem(entity);
            titleViewHolder.lblTitle.setText(getEntities().size()+" "+getString(R.string.jabas_leidas));
            return getString(R.string.se_marcaron_eliminar_registros);
        }else{
            adapter.removeItem(entity);
            titleViewHolder.lblTitle.setText(getEntities().size()+" "+getString(R.string.jabas_leidas));
            return getString(R.string.se_eliminaron_registros);
        }
    }

    /* RecepPalletRecyclerViewAdapter */
    public static class PaltPalletRecyclerViewAdapter extends AbsRecyclerViewAdapter<PalletEntity, PaltPalletRecyclerViewAdapter.JabaViewHolder> {

        public boolean showOnlyDNI;

        public PaltPalletRecyclerViewAdapter(Context context, List<PalletEntity> entities) {
            super(context, entities);
        }

        @Override
        public JabaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new JabaViewHolder(inflaterLayout(viewGroup, R.layout.palt_row_jaba));
        }

        @Override
        public void onBindViewHolder(JabaViewHolder viewHolder, int i) {
            final PalletEntity entity = mEntities.get(i);
            final String qr = entity.getQrjaba();
            viewHolder.lblNombre.setText(QrUtils.QR_JABAS.getCosechador(qr));
            viewHolder.lblTurno.setText(QrUtils.QR_JABAS.getFecha(qr)+(entity.getCosecha()!=null ? (" - "+getTurnoName(entity.getCosecha().getIdtur())) : "" ) );
            viewHolder.lblDni.setText( QrUtils.QR_JABAS.getDNI(qr) + ( showOnlyDNI? "" : " / "+QrUtils.QR_JABAS.getCosechador(qr) ) );
            viewHolder.lblNro.setText(QrUtils.QR_JABAS.getNroEtiqueta(qr)+"/"+QrUtils.QR_JABAS.getTotalEtiqueta(qr));
            viewHolder.lblObs.setVisibility(entity.getCosecha()!=null ? (entity.getCosecha().getEnvaseobs()== 1 ? View.VISIBLE: View.INVISIBLE) : View.INVISIBLE );
            viewHolder.lblPer.setVisibility(entity.getCosecha()!=null ? (entity.getCosecha().getEnvaseper()== 1 ? View.VISIBLE: View.INVISIBLE) : View.INVISIBLE );
        }

        private String getTurnoName(int idTurno){
            try{
                TurnoEntity entity = AppCosecha.getHelper().getTurnoDao().queryBuilder().where().eq("id",idTurno).queryForFirst();
                if(entity != null) return entity.getNombreTurno();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return ""+idTurno;
        }

        /* ViajeViewHolder */
        public static class JabaViewHolder extends AbsViewHolder {

            public TextView lblNombre;
            public TextView lblTurno;
            public TextView lblDni;
            public TextView lblNro;
            public TextView lblObs;
            public TextView lblPer;

            public JabaViewHolder(View view) {
                super(view);
                this.lblNombre = view.findViewById(R.id.lblNombre);
                this.lblTurno = view.findViewById(R.id.lblTurno);
                this.lblDni = view.findViewById(R.id.lblDni);
                this.lblNro = view.findViewById(R.id.lblNro);
                this.lblObs = view.findViewById(R.id.lblObs);
                this.lblPer = view.findViewById(R.id.lblPer);
            }

        }

    }

}

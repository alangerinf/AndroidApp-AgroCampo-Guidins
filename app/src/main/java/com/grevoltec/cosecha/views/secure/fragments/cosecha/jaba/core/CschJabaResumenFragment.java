package com.grevoltec.cosecha.views.secure.fragments.cosecha.jaba.core;

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
import com.grevoltec.cosecha.entities.CosechaEntity;
import com.grevoltec.cosecha.util.AppException;
import com.grevoltec.cosecha.util.QrUtils;
import com.grevoltec.cosecha.views.secure.fragments.core.adapter.SwipeController;
import com.grevoltec.cosecha.views.secure.fragments.core.adapter.SwipeControllerActions;
import com.grevoltec.cosecha.views.secure.fragments.cosecha.jaba.ICschJaba;
import com.grevoltec.cosecha.views.secure.fragments.core.adapter.AbsRecyclerViewAdapter;
import com.grevoltec.cosecha.views.secure.fragments.core.fragment.AbsFragment;
import com.grevoltec.cosecha.views.secure.fragments.core.holder.AbsViewHolder;
import com.grevoltec.cosecha.views.secure.fragments.core.holder.TitleViewHolder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;
import java.util.List;

@EFragment(R.layout.core_recycler_with_title)
public class CschJabaResumenFragment extends AbsFragment implements ICschJaba.ICschJabaChangeListener {

    @ViewById(R.id.layHeader)
    protected LinearLayout layHeader;
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;

    protected CschJabaRecyclerViewAdapter adapter;
    protected TitleViewHolder titleViewHolder;
    protected ICschJaba onStepChangeListener;
    protected SwipeController swipeController = null;

    @AfterViews
    protected void onAfterViews() {
        titleViewHolder = new TitleViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.core_title_small, null, false));
        layHeader.addView(titleViewHolder.itemView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        onCosechaChange();
        swipeController = new SwipeController(new SwipeControllerActions() {
            @Override
            public void onRightClicked(int position) {
                adapter.removeItem(position);
                onCosechaChange();
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

    public CschJabaResumenFragment setOnStepChangeListener(ICschJaba onStepChangeListener) {
        this.onStepChangeListener = onStepChangeListener;
        this.onStepChangeListener.setOnCosechaListener(this);
        return this;
    }

    @Override
    public void onPause() {
        this.onStepChangeListener.setOnCosechaListener(null);
        super.onPause();
    }

    @Override
    public void onResume() {
        this.onStepChangeListener.setOnCosechaListener(this);
        onCosechaChange();
        super.onResume();
    }

    private List<CosechaEntity> getEntities() {
        return this.onStepChangeListener.getCosecha();
    }

    @Override
    public void onCosechaChange() {
        if(adapter == null){
            adapter = new CschJabaRecyclerViewAdapter(getContext(), getEntities());
            recyclerView.setAdapter(adapter);
        }
        titleViewHolder.lblTitle.setText(getEntities().size()+" "+getString(R.string.jabas_leidas));
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onClickActionButton() {
        try {
            if(getEntities().size() == 0) throw new AppException(getString(R.string.agrege_registro_para_continuar));

            for (CosechaEntity entity: getEntities()) {
                AppCosecha.getHelper().getCosechaDao().createOrUpdate(entity);
            }

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

    /* CschJabaRecyclerViewAdapter */
    public class CschJabaRecyclerViewAdapter extends AbsRecyclerViewAdapter<CosechaEntity, CschJabaRecyclerViewAdapter.JabaViewHolder> {

        public CschJabaRecyclerViewAdapter(Context context, List<CosechaEntity> entities) {
            super(context, entities);
        }

        @Override
        public JabaViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            return new JabaViewHolder(inflaterLayout(viewGroup, R.layout.csch_row_jaba));
        }

        @Override
        public void onBindViewHolder(final JabaViewHolder viewHolder, int i) {
            final CosechaEntity entity = mEntities.get(i);
            String qr = entity.getCodigoqr();
            viewHolder.lblNombre.setText(entity.getCosechador());
            viewHolder.lblTurno.setText(QrUtils.QR_JABAS.getFecha(qr)+" - "+onStepChangeListener.getTurnoSelected().getNombreTurno());
            viewHolder.lblDni.setText(entity.getDni());
            viewHolder.lblNro.setText(QrUtils.QR_JABAS.getNroEtiqueta(qr)+"/"+QrUtils.QR_JABAS.getTotalEtiqueta(qr));
            viewHolder.lblObs.setVisibility(entity.getEnvaseobs()== 1 ? View.VISIBLE: View.INVISIBLE);
            viewHolder.lblPer.setVisibility(entity.getEnvaseper()== 1 ? View.VISIBLE: View.INVISIBLE);
            viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setTitle(R.string.seleccione);
                    String[] items = new String[]{getString(R.string.envase_observado),getString(R.string.envase_perdido)};
                    final boolean[] itemsState = new boolean[]{entity.getEnvaseobs()== 1,entity.getEnvaseper()== 1};
                    builder.setMultiChoiceItems(items, itemsState, new DialogInterface.OnMultiChoiceClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                            itemsState[which] = isChecked;
                        }
                    });
                    builder.setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            entity.setEnvaseobs(itemsState[0]? 1 : 0);
                            entity.setEnvaseper(itemsState[1]? 1 : 0);
                            viewHolder.lblObs.setVisibility(entity.getEnvaseobs()== 1 ? View.VISIBLE: View.INVISIBLE);
                            viewHolder.lblPer.setVisibility(entity.getEnvaseper()== 1 ? View.VISIBLE: View.INVISIBLE);
                        }
                    });
                    builder.setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) { }
                    });
                    AlertDialog dialog = builder.create();
                    dialog.show();
                }
            });
        }

        /* JabaViewHolder */
        public class JabaViewHolder extends AbsViewHolder {

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
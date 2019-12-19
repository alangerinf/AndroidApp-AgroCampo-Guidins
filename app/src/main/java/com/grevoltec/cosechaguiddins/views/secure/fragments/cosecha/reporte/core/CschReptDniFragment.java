package com.grevoltec.cosechaguiddins.views.secure.fragments.cosecha.reporte.core;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.grevoltec.cosechaguiddins.R;
import com.grevoltec.cosechaguiddins.app.AppCosecha;
import com.grevoltec.cosechaguiddins.entities.CosechaEntity;
import com.grevoltec.cosechaguiddins.models.AppReporte;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.fragment.AbsFragment;
import com.grevoltec.cosechaguiddins.views.secure.fragments.core.holder.AbsViewHolder;
import com.j256.ormlite.dao.GenericRawResults;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@EFragment(R.layout.core_recycler_with_title)
public class CschReptDniFragment extends AbsFragment {

    @ViewById(R.id.layHeader)
    protected LinearLayout layHeader;
    @ViewById(R.id.recyclerView)
    protected RecyclerView recyclerView;
    protected CschRepDniRecyclerViewAdapter adapter;
    protected CschDniFilterViewHolder headerViewHolder;

    protected String qDni = "";

    @AfterViews
    protected void onAfterViews() {
        headerViewHolder = new CschDniFilterViewHolder(LayoutInflater.from(getContext()).inflate(R.layout.csch_filter_report, null, false));
        layHeader.addView(headerViewHolder.itemView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new CschRepDniRecyclerViewAdapter(getContext(), getEntities(qDni));
        recyclerView.setAdapter(adapter);
        setupFilter(headerViewHolder);
        loadDNITest();
    }

    protected void loadDNITest(){
        if(AppCosecha.IsTest) {
            headerViewHolder.txtFilter.setText("45602950");
        }
    }

    private void setupFilter(final CschDniFilterViewHolder header) {

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), R.layout.core_list_item, getDNIs());
        header.txtFilter.setAdapter(adapter);
        header.txtFilter.addTextChangedListener(new TextWatcher() {
            private String ltext = "";

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String ntext = charSequence.toString();
                ntext = (ntext == null ? "" : ntext);
                if (ntext.compareToIgnoreCase(ltext) != 0) {
                    onChangeFilter(ntext);
                    ltext = ntext;
                }
                if("".compareToIgnoreCase(ntext)!=0) headerViewHolder.btnClear.setVisibility(View.VISIBLE);
                else headerViewHolder.btnClear.setVisibility(View.INVISIBLE);
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
        header.btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headerViewHolder.txtFilter.setText("");
            }
        });
    }

    private void onChangeFilter(String data) {
        try {
            headerViewHolder.lblResult.setText("");
            adapter.replace(getEntities(data));
            if(data != null){
                if(data.length() == 8) {
                    headerViewHolder.lblResult.setText(getNameOrDefault(data, "DNI no lecturado."));
                    headerViewHolder.lblResult.setTextColor(("DNI no lecturado.".equalsIgnoreCase(headerViewHolder.lblResult.getText().toString())) ?
                            AppCosecha.getResource().getColor(R.color.colorIncompleto)
                            : AppCosecha.getResource().getColor(R.color.colorCompleto));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private List<String> getDNIs() {
        List<String> result = new ArrayList<>();
        try {
            GenericRawResults<String[]> iGroup = AppCosecha.getHelper().getCosechaDao()
                    .queryRaw("SELECT cosechas.qr_dni " +
                            "FROM cosechas " +
                            "GROUP BY cosechas.qr_dni " +
                            "ORDER BY 1");
            for (String[] row : iGroup.getResults()) {
                result.add(row[0]);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private List<AppReporte> getEntities(String dni) {
        List<AppReporte> result = new ArrayList<>();
        try {
            GenericRawResults<String[]> iGroup = AppCosecha.getHelper().getCosechaDao()
                    .queryRaw("SELECT cosechas.qr_dni, cosechas.tur_id, turnos.turno_nombre, COUNT(*) " +
                            "FROM cosechas " +
                            "LEFT JOIN turnos ON cosechas.tur_id = turnos.id " +
                            "WHERE cosechas.qr_dni = '" + dni + "' " +
                            "GROUP BY cosechas.qr_dni, cosechas.tur_id, turnos.turno_nombre " +
                            "ORDER BY 4 DESC, 3");
            int i = 1;
            for (String[] row : iGroup.getResults()) {
                result.add(new AppReporte("" + (i++), getValue(row[0], row[2], row[1]), row[3]));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return result;
    }

    private CosechaEntity getFirstCosechaEntity(String dni) {
        try {
            return AppCosecha.getHelper().getCosechaDao()
                    .queryBuilder()
                    .where().eq("qr_dni",dni)
                    .queryForFirst();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private String getNameOrDefault(String dni, String def) {
        CosechaEntity entity = getFirstCosechaEntity(dni);
        return  entity!= null ? entity.getCosechador() : def;
    }

    private String getValue(String dni, String value, String def) {
        if (value != null) {
            if (!value.isEmpty()) return value;
        }
        return def;
    }

    /* CschRepDniRecyclerViewAdapter */
    public static class CschRepDniRecyclerViewAdapter extends RecyclerView.Adapter<CschRepDniRecyclerViewAdapter.RepDniViewHolder> {

        private List<AppReporte> mEntities;
        private Context mContext;

        public CschRepDniRecyclerViewAdapter(Context context, List<AppReporte> entities) {
            this.mEntities = entities;
            this.mContext = context;
        }

        @Override
        public CschRepDniRecyclerViewAdapter.RepDniViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
            View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.csch_row_report, viewGroup, false);
            CschRepDniRecyclerViewAdapter.RepDniViewHolder viewHolder = new CschRepDniRecyclerViewAdapter.RepDniViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(CschRepDniRecyclerViewAdapter.RepDniViewHolder viewHolder, int i) {
            AppReporte entity = mEntities.get(i);
            viewHolder.lblPos.setText(entity.getOrden());
            viewHolder.lblNombre.setText(entity.getGroup());
            viewHolder.lblNro.setText(entity.getCount());
        }

        @Override
        public int getItemCount() {
            return (null != mEntities ? mEntities.size() : 0);
        }

        public void replace(List<AppReporte> items) {
            mEntities.clear();
            if (items != null) mEntities.addAll(items);
            notifyDataSetChanged();
        }

        /* RepDniViewHolder */
        public static class RepDniViewHolder extends AbsViewHolder {

            public TextView lblPos;
            public TextView lblNombre;
            public TextView lblNro;

            public RepDniViewHolder(View view) {
                super(view);
                this.lblPos = view.findViewById(R.id.lblPos);
                this.lblNombre = view.findViewById(R.id.lblNombre);
                this.lblNro = view.findViewById(R.id.lblNro);
            }

        }

    }

    /* CschDniFilterViewHolder */
    public static class CschDniFilterViewHolder extends AbsViewHolder {

        public AutoCompleteTextView txtFilter;
        public ImageButton btnClear;
        public TextView lblResult;

        public TextView titPos;
        public TextView titNombre;
        public TextView titNro;

        public CschDniFilterViewHolder(View view) {
            super(view);
            this.txtFilter = view.findViewById(R.id.txtFilter);
            this.btnClear = view.findViewById(R.id.btnClear);
            this.lblResult = view.findViewById(R.id.lblResult);

            this.titPos = view.findViewById(R.id.titPos);
            this.titNombre = view.findViewById(R.id.titNombre);
            this.titNro = view.findViewById(R.id.titNro);
            init();
        }

        protected void init() {
            this.titPos.setText(R.string.nro);
            this.titNombre.setText(R.string.turno);
            this.titNro.setText(R.string.jabas);
        }

    }

}

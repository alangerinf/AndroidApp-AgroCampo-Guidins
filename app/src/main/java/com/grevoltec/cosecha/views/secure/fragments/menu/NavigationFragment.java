package com.grevoltec.cosecha.views.secure.fragments.menu;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.TextView;

import com.grevoltec.cosecha.R;
import com.grevoltec.cosecha.models.AppMenu;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

@EFragment(R.layout.nav_header_main)
public class NavigationFragment extends Fragment {

    @ViewById(R.id.mainLayout)
    protected ViewGroup mainLayout;
    @ViewById(R.id.imgUser)
    protected CircleImageView imgUser;
    @ViewById(R.id.lblTitle)
    protected TextView lblTitle;
    @ViewById(R.id.lblSubTitle)
    protected TextView lblSubTitle;
    @ViewById(R.id.navList)
    protected ExpandableListView navList;
    protected AppMenuListAdapter expandableListAdapter;

    @AfterViews
    protected void onAfterViews(){
        lblTitle.setText("Usuario");
        lblSubTitle.setText("user@local.dev");

        navList.setAdapter(expandableListAdapter = new AppMenuListAdapter(this.getContext(), buildMenu()));
    }

    private List<AppMenu> buildMenu(){
        List<AppMenu> appMenus = new ArrayList<>();
        /* Sync */
        appMenus.add(
                buildItemMenu(R.string.sincronizar,R.string.sincronizar, 0) //R.drawable.ic_dashboard_sync
                        .addSubMenu(buildItemMenu(R.string.descarga_inicial,R.string.descarga_inicial, 0))
                        .addSubMenu(buildItemMenu(R.string.enviar_datos,R.string.enviar_datos, 0))
        );
        /* Cosechar */
        appMenus.add(
                buildItemMenu(R.string.cosechar,R.string.cosechar, 0) //R.drawable.ic_dashboard_harvest
                        .addSubMenu(buildItemMenu(R.string.lectura_jabas,R.string.lectura_jabas, 0))
                        .addSubMenu(buildItemMenu(R.string.reportes,R.string.reportes, 0))
        );
        /* Paletizar */
        appMenus.add(
                buildItemMenu(R.string.paletizar,R.string.paletizar, 0 )  //R.drawable.ic_dashboard_palletize
                        .addSubMenu(buildItemMenu(R.string.crear_pallete,R.string.crear_pallete, 0))
                        .addSubMenu(buildItemMenu(R.string.completar_pallete,R.string.completar_pallete, 0))
                        .addSubMenu(buildItemMenu(R.string.agregar_quitar_jabas,R.string.agregar_quitar_jabas, 0))
                        .addSubMenu(buildItemMenu(R.string.modificar_pallete,R.string.modificar_pallete, 0))
        );
        /* Despachar */
        appMenus.add(
                buildItemMenu(R.string.despachar,R.string.despachar, 0) //R.drawable.ic_dashboard_despachar
                        .addSubMenu(buildItemMenu(R.string.crear_viaje,R.string.crear_viaje, 0))
                        .addSubMenu(buildItemMenu(R.string.completar_viaje,R.string.completar_viaje, 0))
                        .addSubMenu(buildItemMenu(R.string.agregar_quitar_pallete,R.string.agregar_quitar_pallete, 0))
                        .addSubMenu(buildItemMenu(R.string.modificar_viaje,R.string.modificar_viaje, 0))
        );
        /* Recepcionar */
        appMenus.add(
                buildItemMenu(R.string.recepcionar,R.string.recepcionar, 0) //R.drawable.ic_dashboard_recepcionar
                        .addSubMenu(buildItemMenu(R.string.leer_pallete,R.string.leer_pallete, 0))
                        .addSubMenu(buildItemMenu(R.string.editar_pallete,R.string.editar_pallete, 0))
                        .addSubMenu(buildItemMenu(R.string.eliminar_pallete,R.string.eliminar_pallete, 0))
        );
        /* Limpiar */
        appMenus.add(buildItemMenu(R.string.limpiar,R.string.limpiar, 0)); //R.drawable.ic_dashboard_clear
        /* Salir */
        appMenus.add(buildItemMenu(R.string.salir,R.string.salir, R.drawable.ic_power_settings)); //R.drawable.ic_dashboard_clear
        return appMenus;
    }

    private AppMenu buildItemMenu(int id, int id_name, int id_drawable){
        return  buildItemMenu(id,getResources(),id_name, id_drawable);
    }

    private AppMenu buildItemMenu(int id, Resources resources, int id_name, int id_drawable){
        return  new AppMenu()
                .setId(id)
                .setNombre(getResources().getText(id_name).toString())
                .setIcon(id_drawable)
                ;
    }

    /* AppMenuListAdapter */
    public final class AppMenuListAdapter extends BaseExpandableListAdapter {

        private Context context;
        private List<AppMenu> appMenus;

        public AppMenuListAdapter(Context context, List<AppMenu> appMenus) {
            this.context = context;
            this.appMenus = appMenus;
        }

        @Override
        public Object getChild(int listPosition, int expandedListPosition) {
            return this.appMenus.get(listPosition).getSubMenus().get(expandedListPosition);
        }

        @Override
        public long getChildId(int listPosition, int expandedListPosition) {
            return expandedListPosition;
        }

        @Override
        public View getChildView(int listPosition, final int expandedListPosition,
                                 boolean isLastChild, View convertView, ViewGroup parent) {
            final AppMenu appMenuChild = (AppMenu) getChild(listPosition, expandedListPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_item, null);
            }
            TextView expandedListTextView = (TextView) convertView.findViewById(R.id.expandedListItem);
            expandedListTextView.setText(appMenuChild.getNombre());
            View divider = (View) convertView.findViewById(R.id.divider);
            divider.setVisibility(isLastChild? View.VISIBLE : View.GONE);
            return convertView;
        }

        @Override
        public int getChildrenCount(int listPosition) {
            return this.appMenus.get(listPosition).getSubMenus().size();
        }

        @Override
        public Object getGroup(int listPosition) {
            return this.appMenus.get(listPosition);
        }

        @Override
        public int getGroupCount() {
            return this.appMenus.size();
        }

        @Override
        public long getGroupId(int listPosition) {
            return listPosition;
        }

        @Override
        public View getGroupView(int listPosition, boolean isExpanded, View convertView, ViewGroup parent) {
            final AppMenu appMenuGroup = (AppMenu) getGroup(listPosition);
            if (convertView == null) {
                LayoutInflater layoutInflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = layoutInflater.inflate(R.layout.list_group, null);
            }
            TextView listTitleTextView = (TextView) convertView.findViewById(R.id.listTitle);
            listTitleTextView.setTypeface(null, Typeface.BOLD);
            listTitleTextView.setText(appMenuGroup.getNombre());
            listTitleTextView.setCompoundDrawablesWithIntrinsicBounds(appMenuGroup.getIcon(),0, !appMenuGroup.hasSubMenus() ? 0 : (isExpanded ? R.drawable.ic_expand_more_black : R.drawable.ic_chevron_right_black), 0);
            return convertView;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public boolean isChildSelectable(int listPosition, int expandedListPosition) {
            return true;
        }
    }

}

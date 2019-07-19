package com.grevoltec.cosecha.views.secure.fragments;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.grevoltec.cosecha.R;
import com.grevoltec.cosecha.SecureActivity;
import com.grevoltec.cosecha.app.AppCosecha;
import com.grevoltec.cosecha.entities.AcopioEntity;
import com.grevoltec.cosecha.entities.CosechaEntity;
import com.grevoltec.cosecha.entities.CultivoEntity;
import com.grevoltec.cosecha.entities.FundoEntity;
import com.grevoltec.cosecha.entities.PalletEntity;
import com.grevoltec.cosecha.entities.PlantaEntity;
import com.grevoltec.cosecha.entities.RecepcionEntity;
import com.grevoltec.cosecha.entities.TurnoEntity;
import com.grevoltec.cosecha.entities.ViajeEntity;
import com.grevoltec.cosecha.services.ServiceManager;
import com.grevoltec.cosecha.services.SpInsertarService;
import com.grevoltec.cosecha.services.SpListarService;
import com.grevoltec.cosecha.services.models.response.SpListarAcopiosMovilResult;
import com.grevoltec.cosecha.services.models.response.SpListarFundosMovilResult;
import com.grevoltec.cosecha.services.models.response.SpListarPlantasMovilResult;
import com.grevoltec.cosecha.services.models.response.SpListarTurnosMovilResult;
import com.grevoltec.cosecha.services.models.response.SpSincronizarCosechaMovilResult;
import com.grevoltec.cosecha.services.models.response.SpSincronizarPalletMovilResult;
import com.grevoltec.cosecha.services.models.response.SpSincronizarRecepcionMovilResult;
import com.grevoltec.cosecha.services.models.response.SpSincronizarViajeMovilResult;
import com.grevoltec.cosecha.util.AppException;
import com.grevoltec.cosecha.views.secure.fragments.cosecha.jaba.CschJabaTabFragment_;
import com.grevoltec.cosecha.views.secure.fragments.cosecha.reporte.CschReptTabFragment_;
import com.grevoltec.cosecha.views.secure.fragments.despacho.DespViajeCompletarFragment_;
import com.grevoltec.cosecha.views.secure.fragments.despacho.DespViajeCrearTabFragment_;
import com.grevoltec.cosecha.views.secure.fragments.despacho.DespViajeEditaTabFragment_;
import com.grevoltec.cosecha.views.secure.fragments.despacho.DespViajeModificaTabFragment_;
import com.grevoltec.cosecha.views.secure.fragments.despacho.core.DespViajeSelectFragment;
import com.grevoltec.cosecha.views.secure.fragments.pallet.PaltPalletCompletarFragment_;
import com.grevoltec.cosecha.views.secure.fragments.pallet.PaltPalletCrearTabFragment_;
import com.grevoltec.cosecha.views.secure.fragments.pallet.PaltPalletEditaTabFragment_;
import com.grevoltec.cosecha.views.secure.fragments.pallet.PaltPalletModificaTabFragment_;
import com.grevoltec.cosecha.views.secure.fragments.pallet.core.PaltPalletSelectFragment;
import com.grevoltec.cosecha.views.secure.fragments.recepcion.RecepPalletEditarFragment_;
import com.grevoltec.cosecha.views.secure.fragments.recepcion.RecepPalletEliminaFragment_;
import com.grevoltec.cosecha.views.secure.fragments.recepcion.RecepPalletTabFragment_;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.util.List;

import retrofit2.Call;

@EFragment(R.layout.dashboard_layout)
public class DashboardFragment extends Fragment {

    private Handler mHandler = new Handler();

    @ViewById(R.id.bgHeader)
    protected ImageView bgHeader;
    @ViewById(R.id.btnSincronizar)
    protected CardView btnSincronizar;
    @ViewById(R.id.btnCosechar)
    protected CardView btnCosechar;
    @ViewById(R.id.btnPaletizar)
    protected CardView btnPaletizar;
    @ViewById(R.id.btnDespachar)
    protected CardView btnDespachar;
    @ViewById(R.id.btnRecepcionar)
    protected CardView btnRecepcionar;
    @ViewById(R.id.btnLimpiar)
    protected CardView btnLimpiar;

    protected Dialog subMenu = null;

    private SpListarService spListarService;
    private SpInsertarService spInsertarService;
    private AlertDialog alert;

    @AfterViews
    protected void onAfterViews() {
        subMenu = new Dialog(getContext(), R.style.AppTheme_DialogTheme);
    }

    @Click(value = R.id.btnSincronizar)
    protected void onClickSincronizar(){
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getText(R.string.advertencia))
                .setMessage(R.string.desea_sincronizar_ahora)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            btnLimpiar.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    initSync();
                                }
                            }, 500);
                        }catch (Exception ex){
                            ex.printStackTrace();
                            showError(getString(R.string.ups_inesperado_sincronizacion));
                        }
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }

    @Click(value = R.id.btnCerrarSession)
    protected void onClickCerrarSession(){
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getText(R.string.advertencia))
                .setMessage(R.string.desea_salir_aplicacion)
                .setPositiveButton(R.string.si, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        System.exit(0);
                    }
                })
                .setNegativeButton(R.string.no, null)
                .show();
    }

    protected void initSync(){
        final SecureActivity activity = (getActivity() instanceof SecureActivity) ? (SecureActivity) getActivity() : null;
        if (activity != null) {
            taskSync(activity);
        }
    }

    @Click(value = R.id.btnCosechar)
    protected void onClickCosechar(){
        showSubMenu(R.id.btnCosechar);
    }

    @Click(value = R.id.btnPaletizar)
    protected void onClickPaletizar(){
        showSubMenu(R.id.btnPaletizar);
    }

    @Click(value = R.id.btnDespachar)
    protected void onClickDespachar(){
        showSubMenu(R.id.btnDespachar);
    }

    @Click(value = R.id.btnRecepcionar)
    protected void onClickRecepcionar(){
        showSubMenu(R.id.btnRecepcionar);
    }

    @Click(value = R.id.btnLimpiar)
    protected void onClickLimpiar(){
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getText(R.string.advertencia))
                .setMessage(R.string.confirmacion_para_eliminar_data_local)
                .setPositiveButton(R.string.continuar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        try{
                            AppCosecha.getHelper().clearAllData();
                            btnLimpiar.postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    showInfo(getString(R.string.se_limpiaron_registros));
                                }
                            }, 500);
                        }catch (Exception ex){
                            ex.printStackTrace();
                            showError(getString(R.string.ups_inesperado_limpieza));
                        }
                    }
                })
                .setNegativeButton(R.string.cancelar, null)
                .show();
    }

    public void showInfo(String mensaje){
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getText(R.string.info))
                .setMessage(mensaje)
                .setCancelable(false)
                .setPositiveButton(R.string.aceptar, null).show();
    }

    public void showError(String mensaje){
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getText(R.string.error))
                .setMessage(mensaje)
                .setCancelable(false)
                .setPositiveButton(R.string.aceptar, null).show();
    }

    private View.OnClickListener buildListener(final int item){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment fragment = null;
                Bundle bundle = null;
                switch (item){
                    case R.string.lectura_jabas:
                        fragment = new CschJabaTabFragment_();
                        break;
                    case R.string.reportes:
                        fragment = new CschReptTabFragment_();
                        break;
                    case R.string.crear_pallete:
                        fragment = new PaltPalletCrearTabFragment_();
                        break;
                    case R.string.completar_pallete:
                        fragment = new PaltPalletCompletarFragment_();
                        break;
                    case R.string.agregar_quitar_jabas:
                        bundle = new Bundle();
                        bundle.putInt(PaltPalletSelectFragment.PARAM_TYPE_SELECT, PaltPalletSelectFragment.TYPE_EDIT_JABA);
                        fragment = new PaltPalletEditaTabFragment_();
                        fragment.setArguments(bundle);
                        break;
                    case R.string.modificar_pallete:
                        bundle = new Bundle();
                        bundle.putInt(PaltPalletSelectFragment.PARAM_TYPE_SELECT, PaltPalletSelectFragment.TYPE_EDIT_PALLET);
                        fragment = new PaltPalletModificaTabFragment_();
                        fragment.setArguments(bundle);
                        break;
                    case R.string.crear_viaje:
                        fragment = new DespViajeCrearTabFragment_();
                        break;
                    case R.string.completar_viaje:
                        fragment = new DespViajeCompletarFragment_();
                        break;
                    case R.string.agregar_quitar_pallete:
                        bundle = new Bundle();
                        bundle.putInt(DespViajeSelectFragment.PARAM_TYPE_SELECT, DespViajeSelectFragment.TYPE_EDIT_PALLET);
                        fragment = new DespViajeEditaTabFragment_();
                        fragment.setArguments(bundle);
                        break;
                    case R.string.modificar_viaje:
                        bundle = new Bundle();
                        bundle.putInt(DespViajeSelectFragment.PARAM_TYPE_SELECT, DespViajeSelectFragment.TYPE_EDIT_VIAJE);
                        fragment = new DespViajeModificaTabFragment_();
                        fragment.setArguments(bundle);
                        break;
                    case R.string.leer_pallete:
                        fragment = new RecepPalletTabFragment_();
                        break;
                    case R.string.editar_pallete:
                        fragment = new RecepPalletEditarFragment_();
                        break;
                    case R.string.eliminar_pallete:
                        fragment = new RecepPalletEliminaFragment_();
                        break;
                    default:
                        Toast.makeText(getContext(), "No Implementado "+item, Toast.LENGTH_SHORT).show();
                }
                if(fragment != null){
                    subMenu.dismiss();
                    ((SecureActivity)getActivity()).SetFragment(fragment);
                }
            }
        };
    }

    @UiThread
    protected void onSyncComplete(final SecureActivity activity) {
        hideAlert();
        new AlertDialog.Builder(getContext())
                .setTitle(getResources().getText(R.string.mensaje))
                .setMessage(R.string.sincronizacion_completa)
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                })
                .setCancelable(false)
                .show();
    }

    protected void showAlert() {
        mHandler.post(new Runnable() {
            public void run() {
                alert = new AlertDialog.Builder(getContext())
                        .setTitle(R.string.Sincronizando)
                        .setMessage(R.string.conectando_con_servidor)
                        .setCancelable(false)
                        .show();
            }
        });
    }

    private void showMessage(final String msg) {
        mHandler.post(new Runnable() {
            public void run() {
                alert.setMessage(msg);
            }
        });
    }

    private void hideAlert() {
        mHandler.post(new Runnable() {
            public void run() {
                alert.dismiss();
            }
        });
    }

    protected void taskMasters(final SecureActivity activity, int idEmpresa) throws AppException {
        try {
            //Fundos
            showMessage(getString(R.string.cargando_fundos));
            List<CultivoEntity> cultivos = AppCosecha.getHelper().getCultivoDao().queryForAll();
            AppCosecha.getHelper().clearAllFundos();
            for (CultivoEntity cultivo : cultivos) {
                Call<List<SpListarFundosMovilResult>> call = spListarService.GetListarFundos(idEmpresa, cultivo.getIdCultivo());
                List<SpListarFundosMovilResult> result = call.execute().body();
                for (SpListarFundosMovilResult item : result) {
                    AppCosecha.getHelper().getFundoDao()
                            .createOrUpdate(new FundoEntity(Integer.parseInt(item.getIdFundo()), item.getCodigoFundo(),
                                    item.getNombreFundo(), idEmpresa, cultivo.getIdCultivo()));
                }
            }
            if (AppCosecha.getHelper().getFundoDao().countOf() == 0) throw new AppException(getString(R.string.usuario_sin_fundos_asignados));
            //Turnos
            showMessage(getString(R.string.cargando_turnos));
            AppCosecha.getHelper().clearAllTurnos();
            for (CultivoEntity cultivo : cultivos) {
                Call<List<SpListarTurnosMovilResult>> call = spListarService.GetListarTurnos(idEmpresa, cultivo.getIdCultivo());
                List<SpListarTurnosMovilResult> result = call.execute().body();
                for (SpListarTurnosMovilResult turno : result) {
                    AppCosecha.getHelper().getTurnoDao().createOrUpdate(
                            new TurnoEntity(Integer.parseInt(turno.getIdTurno()), turno.getCodigoTurno(), turno.getNombreTurno(),
                                    turno.getCodigoQRTurno(), idEmpresa, cultivo.getIdCultivo(), turno.getIdFundo()));
                }
            }
            if (AppCosecha.getHelper().getTurnoDao().countOf() == 0) throw new AppException(getString(R.string.usuario_sin_turnos_asignados));
            //Acopios
            showMessage(getString(R.string.cargando_acopios));
            Call<List<SpListarAcopiosMovilResult>> call2 = spListarService.GetListarAcopios(idEmpresa);
            List<SpListarAcopiosMovilResult> result2 = call2.execute().body();
            if (result2.size() == 0) throw new AppException(getString(R.string.usuario_sin_acopios_asignados));
            AppCosecha.getHelper().clearAllAcopios();
            for (SpListarAcopiosMovilResult item : result2) {
                AppCosecha.getHelper().getAcopioDao().createOrUpdate(new AcopioEntity(Integer.parseInt(item.getIdAcopio()), item.getCodigoAcopio(), item.getNombreAcopio(), idEmpresa));
            }
            //Plantas
            showMessage(getString(R.string.cargando_plantas));
            Call<List<SpListarPlantasMovilResult>> call3 = spListarService.GetListarPlantas(idEmpresa);
            List<SpListarPlantasMovilResult> results3 = call3.execute().body();
            AppCosecha.getHelper().clearAllPlantas();
            for (SpListarPlantasMovilResult item : results3) {
                AppCosecha.getHelper().getPlantaDao().createOrUpdate(new PlantaEntity(Integer.parseInt(item.getIdPlanta()), item.getCodigoPlanta(), item.getNombrePlanta(), idEmpresa));
            }
        } catch (AppException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Background
    protected void taskSync(final SecureActivity activity) {
        spListarService = ServiceManager.getRetrofit().create(SpListarService.class);
        spInsertarService = ServiceManager.getRetrofit().create(SpInsertarService.class);
        showAlert();
        try {
            //Cosecha
            showMessage(getString(R.string.preparando_cosecha_enviar));
            List<CosechaEntity> cosechaEntities = AppCosecha.getHelper().getCosechaDao().queryForAll();
            for (CosechaEntity entity: cosechaEntities) {
                if(!entity.isSync()){
                    Call<List<SpSincronizarCosechaMovilResult>> call = spInsertarService.GetSincronizarMovilCosecha(
                            entity.getIduser(),
                            entity.getIdtur(),
                            entity.getCodigoqr(),
                            entity.getHoraenvase(),
                            entity.getImeiequipo(),
                            AppCosecha.getTelefono(entity.getNroequipo()),
                            entity.getEnvaseobs(),
                            entity.getEnvaseper(),
                            entity.getCantPersonas()
                    );
                    List<SpSincronizarCosechaMovilResult> result = call.execute().body();
                    SpSincronizarCosechaMovilResult iResult = result.get(0);
                    entity.setSynCodigo(""+iResult.getCodigo());
                    AppCosecha.getHelper().getCosechaDao().update(entity);
                }
            }
            //Pallets
            showMessage(getString(R.string.preparando_pallet_enviar));
            List<PalletEntity> palletEntities = AppCosecha.getHelper().getPalletDao().queryForAll();
            for (PalletEntity entity: palletEntities) {
                if(!entity.isSync() && entity.getPalletcomp() == 1){
                    Call<List<SpSincronizarPalletMovilResult>> call = spInsertarService.GetSincronizarMovilPallet(
                            entity.getIduser(),
                            entity.getQrpallet(),
                            entity.getHorapallet(),
                            entity.getPalletcomp(),
                            entity.getImeiequipo(),
                            AppCosecha.getTelefono(entity.getNroequipo()),
                            entity.getQrjaba(), entity.getHorajaba()
                    );
                    List<SpSincronizarPalletMovilResult> result = call.execute().body();
                    SpSincronizarPalletMovilResult iResult = result.get(0);
                    entity.setSynCodigo(""+iResult.getCodigo());
                    AppCosecha.getHelper().getPalletDao().update(entity);
                }
            }
            //Despacho
            showMessage(getString(R.string.preparando_despacho_enviar));
            List<ViajeEntity> viajeEntities = AppCosecha.getHelper().getViajeDao().queryForAll();
            for (ViajeEntity entity: viajeEntities) {
                if(!entity.isSync() && entity.getViajecompleto() == 1){
                    Call<List<SpSincronizarViajeMovilResult>> call = spInsertarService.GetSincronizarMovilViaje(entity.getIduser(), entity.getQrcamion(), entity.getHoracamion(),
                            entity.getViajecompleto(), entity.getImeiequipo(), AppCosecha.getTelefono(entity.getNumeroequipo()), entity.getQrpallet(), entity.getHorapallet());
                    List<SpSincronizarViajeMovilResult> result = call.execute().body();
                    SpSincronizarViajeMovilResult iResult = result.get(0);
                    entity.setSynCodigo(""+iResult.getCodigo());
                    AppCosecha.getHelper().getViajeDao().update(entity);
                }
            }
            //Recepcion
            showMessage(getString(R.string.preparando_recepcion_enviar));
            List<RecepcionEntity> recepcionEntities = AppCosecha.getHelper().getRecepcionDao().queryForAll();
            for (RecepcionEntity entity: recepcionEntities) {
                if(!entity.isSync()){
                    Call<List<SpSincronizarRecepcionMovilResult>> call = spInsertarService.GetSincronizarMovilRecepcion(entity.getIdplanta(), entity.getFecharecep(), entity.getIduser(),
                            entity.getQrpallet(), entity.getHorapallet(), entity.getImeiequipo(), AppCosecha.getTelefono(entity.getNroequipo()), entity.getPesopallet());
                    List<SpSincronizarRecepcionMovilResult> result = call.execute().body();
                    SpSincronizarRecepcionMovilResult iResult = result.get(0);
                    entity.setSynCodigo(""+iResult.getCodigo());
                    AppCosecha.getHelper().getRecepcionDao().update(entity);
                }
            }
            taskMasters(activity, AppCosecha.getUserLogin().getIdEmpresa());
            onSyncComplete(activity);
        } catch (final AppException ex) {
            hideAlert();
            mHandler.post(new Runnable() {
                public void run() {
                    new AlertDialog.Builder(getContext())
                            .setTitle(getResources().getText(R.string.error))
                            .setMessage(ex.getMessage())
                            .setCancelable(false)
                            .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                }
            });
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* Show Menu */
    private SubMenuViewHolder showSubMenu(int menu){
        SubMenuViewHolder result = null;
        switch (menu){
            case R.id.btnCosechar:
                result = showSubMenu(R.drawable.ic_dashboard_harvest, R.string.cosechar, R.string.cosechar_desc);
                result.addGroupLayout12()
                        .setItem11("-", R.string.lectura_jabas, buildListener(R.string.lectura_jabas))
                        .setItem12("-", R.string.reportes, buildListener(R.string.reportes));
                break;
            case R.id.btnPaletizar:
                result = showSubMenu(R.drawable.ic_dashboard_palletize, R.string.paletizar, R.string.paletizar_desc);
                result.addGroupLayout12()
                        .setItem11("-", R.string.crear_pallete, buildListener(R.string.crear_pallete))
                        .setItem12("-", R.string.completar_pallete, buildListener(R.string.completar_pallete));
                result.addGroupLayout12()
                        .setItem11("-", R.string.agregar_quitar_jabas, buildListener(R.string.agregar_quitar_jabas))
                        .setItem12("-", R.string.modificar_pallete, buildListener(R.string.modificar_pallete));
                break;
            case R.id.btnDespachar:
                result = showSubMenu(R.drawable.ic_dashboard_despachar, R.string.despachar, R.string.despachar_desc);
                result.addGroupLayout12()
                        .setItem11("-", R.string.crear_viaje, buildListener(R.string.crear_viaje))
                        .setItem12("-", R.string.completar_viaje, buildListener(R.string.completar_viaje));
                result.addGroupLayout12()
                        .setItem11("-", R.string.agregar_quitar_pallete, buildListener(R.string.agregar_quitar_pallete))
                        .setItem12("-", R.string.modificar_viaje, buildListener(R.string.modificar_viaje));
                break;
            case R.id.btnRecepcionar:
                result = showSubMenu(R.drawable.ic_dashboard_recepcionar, R.string.recepcionar, R.string.recepcionar_desc);
                result.addGroupLayout13()
                        .setItem11("-", R.string.leer_pallete, buildListener(R.string.leer_pallete))
                        .setItem12("-", R.string.editar_pallete, buildListener(R.string.editar_pallete))
                        .setItem13("-", R.string.eliminar_pallete, buildListener(R.string.eliminar_pallete));
                break;
        }
        if(result != null) result.show();
        return result;
    }

    private SubMenuViewHolder showSubMenu(int ico, int title, int desc){
        return new SubMenuViewHolder(subMenu)
                .init(ico, title, desc);
    }

    public static class SubMenuViewHolder{

        private final Dialog subMenu;
        public LinearLayout iFirst;
        public View lnkClose;
        public ImageView imgSubMenu;
        public TextView titleSubMenu;
        public TextView descSubMenu;

        public SubMenuViewHolder(final Dialog subMenu){
            this.subMenu = subMenu;
            this.subMenu.setContentView(R.layout.submenu_layout);
            _init();
        }

        private void _init(){
            lnkClose = subMenu.findViewById(R.id.lnkClose);
            lnkClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    subMenu.dismiss();
                }
            });
            iFirst = this.subMenu.findViewById(R.id.subItems);
            imgSubMenu = this.subMenu.findViewById(R.id.imgSubMenu);
            titleSubMenu = this.subMenu.findViewById(R.id.titleSubMenu);
            descSubMenu = this.subMenu.findViewById(R.id.descSubMenu);
        }

        private SubMenuViewHolder init(int ico, int title, int desc){
            imgSubMenu.setImageResource(ico);
            if(title != 0) titleSubMenu.setText(title);
            if(desc != 0) descSubMenu.setText(desc);
            return this;
        }

        private SubMenuViewHolderGroup12 addGroupLayout12(){
            View child = this.subMenu.getLayoutInflater().inflate(R.layout.submenu_12_item_layout, null);
            iFirst.addView(child);
            return new SubMenuViewHolderGroup12(child);
        }

        private SubMenuViewHolderGroup13 addGroupLayout13(){
            View child = this.subMenu.getLayoutInflater().inflate(R.layout.submenu_13_item_layout, null);
            iFirst.addView(child);
            return new SubMenuViewHolderGroup13(child);
        }

        private SubMenuViewHolder show(){
            this.subMenu.show();
            return this;
        }

        /* SubMenu View HolderGroup 12 */
        public static class SubMenuViewHolderGroup12{

            protected final View _view;
            public CardView cardView11;
            public TextView lblInfo11;
            public TextView lblTitle11;
            public CardView cardView12;
            public TextView lblInfo12;
            public TextView lblTitle12;

            public SubMenuViewHolderGroup12(final View view){
                this._view = view;
                _init();
            }

            protected void _init(){
                cardView11 = this._view.findViewById(R.id.cardView11);
                lblInfo11 = this._view.findViewById(R.id.lblInfo11);
                lblTitle11 = this._view.findViewById(R.id.lblTitle11);
                cardView12 = this._view.findViewById(R.id.cardView12);
                lblInfo12 = this._view.findViewById(R.id.lblInfo12);
                lblTitle12 = this._view.findViewById(R.id.lblTitle12);
            }

            public SubMenuViewHolderGroup12 setItem11(String info, int title, View.OnClickListener listener){
                cardView11.setOnClickListener(listener);
                lblInfo11.setText(info);
                lblTitle11.setText(title);
                return  this;
            }

            public SubMenuViewHolderGroup12 setItem12(String info, int title, View.OnClickListener listener){
                cardView12.setOnClickListener(listener);
                lblInfo12.setText(info);
                lblTitle12.setText(title);
                return  this;
            }

        }

        /* SubMenu View HolderGroup 13 */
        public static class SubMenuViewHolderGroup13 extends  SubMenuViewHolderGroup12 {

            public CardView cardView13;
            public TextView lblInfo13;
            public TextView lblTitle13;

            public SubMenuViewHolderGroup13(View view) {
                super(view);
            }

            @Override
            protected void _init() {
                super._init();
                cardView13 = this._view.findViewById(R.id.cardView13);
                lblInfo13 = this._view.findViewById(R.id.lblInfo13);
                lblTitle13 = this._view.findViewById(R.id.lblTitle13);
            }

            @Override
            public SubMenuViewHolderGroup13 setItem11(String info, int title, View.OnClickListener listener) {
                super.setItem11(info, title, listener);
                return this;
            }

            @Override
            public SubMenuViewHolderGroup13 setItem12(String info, int title, View.OnClickListener listener) {
                super.setItem12(info, title, listener);
                return this;
            }

            public SubMenuViewHolderGroup13 setItem13(String info, int title, View.OnClickListener listener){
                cardView13.setOnClickListener(listener);
                lblInfo13.setText(info);
                lblTitle13.setText(title);
                return  this;
            }

        }

    }

}
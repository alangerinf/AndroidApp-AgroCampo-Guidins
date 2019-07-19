package com.grevoltec.cosecha.views.access.fragments.auth;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.grevoltec.cosecha.MainActivity;
import com.grevoltec.cosecha.R;
import com.grevoltec.cosecha.app.AppCosecha;
import com.grevoltec.cosecha.entities.AcopioEntity;
import com.grevoltec.cosecha.entities.CultivoEntity;
import com.grevoltec.cosecha.entities.FundoEntity;
import com.grevoltec.cosecha.entities.PlantaEntity;
import com.grevoltec.cosecha.entities.TurnoEntity;
import com.grevoltec.cosecha.entities.UsuarioEntity;
import com.grevoltec.cosecha.services.ServiceManager;
import com.grevoltec.cosecha.services.SpListarService;
import com.grevoltec.cosecha.services.models.response.SpAutenticacionMovilResult;
import com.grevoltec.cosecha.services.models.response.SpListarAcopiosMovilResult;
import com.grevoltec.cosecha.services.models.response.SpListarFundosMovilResult;
import com.grevoltec.cosecha.services.models.response.SpListarPlantasMovilResult;
import com.grevoltec.cosecha.services.models.response.SpListarTurnosMovilResult;
import com.grevoltec.cosecha.util.AppException;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

@EFragment(R.layout.login_layout)
public class LoginFragment extends Fragment {

    private Handler mHandler = new Handler();

    @ViewById(R.id.txtUsuario)
    protected EditText txtUsuario;
    @ViewById(R.id.txtPassword)
    protected EditText txtPassword;
    @ViewById(R.id.txtCultivo)
    protected TextView txtCultivo;
    @ViewById(R.id.btnLogin)
    protected Button btnLogin;

    private SpListarService spListarService;
    private AlertDialog alert;

    private List<CultivoEntity> cultivos;
    private CultivoEntity cultivoSelected;
    protected int idx;

    @AfterViews
    protected void onAfterViews() {
        cultivos = new ArrayList<>();
        try {
            cultivos.addAll(AppCosecha.getHelper().getCultivoDao().queryForAll());
            cultivoSelected = cultivos.get(0);
            txtCultivo.setText(cultivoSelected.getNombreCultivo());
        }catch (Exception ex){
            ex.printStackTrace();
        }
        txtCultivo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setTitle(R.string.seleccione_un_cultivo);
                String[] items = new String[cultivos.size()];
                int checkedItem = 1;
                int idCultivoSelected = (cultivoSelected != null ? cultivoSelected.getIdCultivo() :  -1);
                for (int i=0; i< cultivos.size(); i++){
                    CultivoEntity entity = cultivos.get(i);
                    items[i] = entity.getNombreCultivo();
                    if(entity.getIdCultivo() == idCultivoSelected){
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
                        cultivoSelected = cultivos.get(idx);
                        txtCultivo.setText(cultivoSelected.getNombreCultivo());
                    }
                });
                builder.setNegativeButton(R.string.cancelar, null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
        test();
    }

    private void test() {
        //if(AppCosecha.IsTest)
        {
            //txtUsuario.setText("dquirozn");
            //txtUsuario.setText("demo");
            //txtPassword.setText("123456");
        }
    }

    @Click(R.id.btnLogin)
    protected void onClick() {
        final MainActivity activity = (getActivity() instanceof MainActivity) ? (MainActivity) getActivity() : null;
        if (activity != null) {
            taskLogin(activity, txtUsuario.getText().toString(), txtPassword.getText().toString(), cultivoSelected.getIdCultivo());
        }
    }

    @UiThread
    protected void onLoginUser(final MainActivity activity, SpAutenticacionMovilResult iUser, String username, String password,int cultivo) {
        try {
            AppCosecha.getHelper().clearAllUsuarios();
            AppCosecha.setUserLogin(new UsuarioEntity(Integer.parseInt(iUser.getIdUsuario()), username, password, iUser.getNombreUsuario(), Integer.parseInt(iUser.getIdEmpresa())));
            AppCosecha.getHelper().getUsuarioDao().createOrUpdate(AppCosecha.getUserLogin());
            activity.loadActivitySecure();
        } catch (SQLException e) {
            e.printStackTrace();
            new AlertDialog.Builder(getContext())
                    .setTitle(R.string.error)
                    .setMessage(R.string.upps_error_aplicacion)
                    .setCancelable(false)
                    .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().finish();
                            System.exit(0);
                        }
                    }).show();
        }
        hideAlert();
    }

    protected void showAlert() {
        mHandler.post(new Runnable() {
            public void run() {
                alert = new AlertDialog.Builder(getContext())
                        .setTitle(R.string.autenticando)
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

    protected void taskMasters(final MainActivity activity, SpAutenticacionMovilResult iUser) throws AppException {
        try {
            //Fundos
            showMessage(getString(R.string.cargando_fundos));
            List<CultivoEntity> cultivos = AppCosecha.getHelper().getCultivoDao().queryForAll();
            AppCosecha.getHelper().clearAllFundos();
            for (CultivoEntity cultivo : cultivos) {
                Call<List<SpListarFundosMovilResult>> call = spListarService.GetListarFundos(Integer.parseInt(iUser.getIdEmpresa()), cultivo.getIdCultivo());
                List<SpListarFundosMovilResult> result = call.execute().body();
                for (SpListarFundosMovilResult item : result) {
                    AppCosecha.getHelper().getFundoDao()
                            .createOrUpdate(new FundoEntity(Integer.parseInt(item.getIdFundo()), item.getCodigoFundo(),
                                    item.getNombreFundo(), Integer.parseInt(iUser.getIdEmpresa()), cultivo.getIdCultivo()));
                }
            }
            if (AppCosecha.getHelper().getFundoDao().countOf() == 0) throw new AppException(getString(R.string.usuario_sin_fundos_asignados));
            //Turnos
            showMessage(getString(R.string.cargando_turnos));
            AppCosecha.getHelper().clearAllTurnos();
            for (CultivoEntity cultivo : cultivos) {
                Call<List<SpListarTurnosMovilResult>> call = spListarService.GetListarTurnos(Integer.parseInt(iUser.getIdEmpresa()), cultivo.getIdCultivo());
                List<SpListarTurnosMovilResult> result = call.execute().body();
                for (SpListarTurnosMovilResult turno : result) {
                    AppCosecha.getHelper().getTurnoDao().createOrUpdate(
                            new TurnoEntity(Integer.parseInt(turno.getIdTurno()), turno.getCodigoTurno(), turno.getNombreTurno(),
                                    turno.getCodigoQRTurno(), Integer.parseInt(iUser.getIdEmpresa()), cultivo.getIdCultivo(), turno.getIdFundo()));
                }
            }
            if (AppCosecha.getHelper().getTurnoDao().countOf() == 0) throw new AppException(getString(R.string.usuario_sin_turnos_asignados));
            //Acopios
            showMessage(getString(R.string.cargando_acopios));
            Call<List<SpListarAcopiosMovilResult>> call2 = spListarService.GetListarAcopios(Integer.parseInt(iUser.getIdEmpresa()));
            List<SpListarAcopiosMovilResult> result2 = call2.execute().body();
            if (result2.size() == 0) throw new AppException(getString(R.string.usuario_sin_acopios_asignados));
            AppCosecha.getHelper().clearAllAcopios();
            for (SpListarAcopiosMovilResult item : result2) {
                AppCosecha.getHelper().getAcopioDao().createOrUpdate(new AcopioEntity(Integer.parseInt(item.getIdAcopio()), item.getCodigoAcopio(), item.getNombreAcopio(), Integer.parseInt(iUser.getIdEmpresa())));
            }
            //Plantas
            showMessage(getString(R.string.cargando_plantas));
            Call<List<SpListarPlantasMovilResult>> call3 = spListarService.GetListarPlantas(Integer.parseInt(iUser.getIdEmpresa()));
            List<SpListarPlantasMovilResult> results3 = call3.execute().body();
            AppCosecha.getHelper().clearAllPlantas();
            for (SpListarPlantasMovilResult item : results3) {
                AppCosecha.getHelper().getPlantaDao().createOrUpdate(new PlantaEntity(Integer.parseInt(item.getIdPlanta()), item.getCodigoPlanta(), item.getNombrePlanta(), Integer.parseInt(iUser.getIdEmpresa())));
            }
        } catch (AppException ex) {
            ex.printStackTrace();
            throw ex;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Background
    protected void taskLogin(final MainActivity activity, String username, String password, int cultivo) {
        spListarService = ServiceManager.getRetrofit().create(SpListarService.class);
        showAlert();
        try {
            Call<List<SpAutenticacionMovilResult>> call = spListarService.GetAutenticacion(username, password, cultivo);
            List<SpAutenticacionMovilResult> result = call.execute().body();
            if (result.size() == 1) {
                showMessage(getString(R.string.cargando_informacion_usuario));
                SpAutenticacionMovilResult iResult = result.get(0);
                taskMasters(activity, iResult);
                onLoginUser(activity, iResult, username, password,cultivo);
                return;
            }
            throw new AppException(getString(R.string.credenciales_invalidas));
        } catch (final AppException ex) {
            hideAlert();
            mHandler.post(new Runnable() {
                public void run() {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.error)
                            .setMessage(ex.getMessage())
                            .setCancelable(false)
                            .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            }).show();
                    txtPassword.setText("");
                }
            });
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
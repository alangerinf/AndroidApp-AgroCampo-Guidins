package com.grevoltec.cosechaguiddins.views.access.fragments.auth;

import android.content.DialogInterface;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.grevoltec.cosechaguiddins.MainActivity;
import com.grevoltec.cosechaguiddins.R;
import com.grevoltec.cosechaguiddins.app.AppCosecha;
import com.grevoltec.cosechaguiddins.app.NetworkStatusManager;
import com.grevoltec.cosechaguiddins.entities.CamionEntity;
import com.grevoltec.cosechaguiddins.entities.CultivoEntity;
import com.grevoltec.cosechaguiddins.services.ServiceManager;
import com.grevoltec.cosechaguiddins.services.SpListarService;
import com.grevoltec.cosechaguiddins.services.models.response.SpListarCamionesMovilResult;
import com.grevoltec.cosechaguiddins.services.models.response.SpListarCultivosMovilResult;
import com.grevoltec.cosechaguiddins.util.AppException;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import retrofit2.Call;

@EFragment(R.layout.splash_layout)
public class SplashFragment extends Fragment {

    private Handler mHandler = new Handler();

    @ViewById(R.id.pgbLoader)
    protected ProgressBar pgbLoader;
    @ViewById(R.id.lblInfo)
    protected TextView lblInfo;

    private SpListarService spListarService;

    @AfterViews
    protected void onAfterViews() {
        loadData();
    }

    @UiThread
    protected void setProgress(final int progress) {
        mHandler.post(new Runnable() {
            public void run() {
                pgbLoader.setProgress(progress);
            }
        });
    }

    @UiThread
    protected void setMessage(final String message) {
        mHandler.post(new Runnable() {
            public void run() {
                lblInfo.setText(message);
            }
        });
    }

    @UiThread
    protected void setMessage(final int message) {
        mHandler.post(new Runnable() {
            public void run() {
                lblInfo.setText(message);
            }
        });
    }

    @UiThread
    protected void onFinishLoadData(final boolean _result) {
        final MainActivity activity = (getActivity() instanceof MainActivity) ? (MainActivity) getActivity() : null;
        boolean result = _result;
        if (!result) {
            try {
                if (AppCosecha.getHelper().getCultivoDao().countOf() <= 0) {
                    throw new AppException(getString(R.string.ups_no_cargaron_cultivos));
                }
                if (AppCosecha.getHelper().getCamionDao().countOf() <= 0) {
                    throw new AppException(getString(R.string.ups_no_cargaron_camiones));
                }
                if (AppCosecha.getHelper().getPlantaDao().countOf() <= 0) {
                    throw new AppException(getString(R.string.ups_no_cargaron_plantas));
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (AppException ex) {
                new AlertDialog.Builder(getContext())
                        .setTitle(R.string.error)
                        .setMessage(ex.getMessage())
                        .setCancelable(false)
                        .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                                System.exit(0);
                            }
                        }).show();
            }
        }
        if (result && activity != null) {
            activity.loadFragmentLogin();
        }
    }

    protected boolean isOk(){
        try {
            Date now = new Date();
            Date max = new Date(118, 11, 13, 23, 59);
            if(now.getTime() > max.getTime()) throw new AppException(getString(R.string.supero_tiempo_prueba));
            if(!NetworkStatusManager.isConnected(getContext())){
                if(AppCosecha.getHelper().getCultivoDao().countOf() == 0){
                    throw new AppException(getString(R.string.error_trabajo_sin_conexion));
                }
            }
        }catch (final AppException ex){
            mHandler.post(new Runnable() {
                public void run() {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.error)
                            .setMessage(ex.getMessage())
                            .setCancelable(false)
                            .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getActivity().finish();
                                    System.exit(0);
                                }
                            }).show();
                }
            });
            return false;
        } catch (SQLException e) {
            e.printStackTrace();
            mHandler.post(new Runnable() {
                public void run() {
                    new AlertDialog.Builder(getContext())
                            .setTitle(R.string.error)
                            .setMessage(R.string.error_trabajo_sin_conexion)
                            .setCancelable(false)
                            .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    getActivity().finish();
                                    System.exit(0);
                                }
                            }).show();
                }
            });
            return false;
        }
        return true;
    }

    @Background
    protected void loadData() {
        //if(!isOk()) return;
        int progress = 10;
        setProgress(R.string.conectando_con_servidor);
        spListarService = ServiceManager.getRetrofit().create(SpListarService.class);
        try {
            /* SpListarCultivosMovilResult */
            setMessage(R.string.cargando_cultivos);
            setProgress(progress);
            progress += 15;
            Call<List<SpListarCultivosMovilResult>> call = spListarService.GetListarCultivos();
            setProgress(progress);
            progress += 15;
            List<SpListarCultivosMovilResult> results = call.execute().body();
            setProgress(progress);
            progress += 15;
            AppCosecha.getHelper().clearAllCultivos();
            for (SpListarCultivosMovilResult item : results) {
                AppCosecha.getHelper().getCultivoDao().createOrUpdate(
                        new CultivoEntity(Integer.parseInt(item.getIdCultivo()), item.getCodigoCultivo(), item.getNombreCultivo()));
            }
            /* SpListarCamionesMovilResult */
            setMessage(R.string.cargando_camiones);
            setProgress(progress);
            progress += 15;
            Call<List<SpListarCamionesMovilResult>> call2 = spListarService.GetListarCamiones();
            setProgress(progress);
            progress += 15;
            List<SpListarCamionesMovilResult> results2 = call2.execute().body();
            setProgress(progress);
            progress += 15;
            AppCosecha.getHelper().clearAllCaminiones();
            for (SpListarCamionesMovilResult item : results2) {
                AppCosecha.getHelper().getCamionDao().createOrUpdate(
                        new CamionEntity(Integer.parseInt(item.getIdCamion()), item.getCodigoCamion(), item.getPlacaCamion(), item.getCodigoQRCamion()));
            }
            onFinishLoadData(true);
        } catch (final Exception ex) {
            ex.printStackTrace();
            mHandler.post(new Runnable() {
                public void run() {
                    Toast.makeText(getContext(), ex.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
            onFinishLoadData(false);
        }
    }

}
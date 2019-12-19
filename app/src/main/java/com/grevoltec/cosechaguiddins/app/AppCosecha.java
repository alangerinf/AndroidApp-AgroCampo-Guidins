package com.grevoltec.cosechaguiddins.app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.res.Resources;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import com.grevoltec.cosechaguiddins.R;
import com.grevoltec.cosechaguiddins.entities.UsuarioEntity;
import com.grevoltec.cosechaguiddins.storages.DBHelper;
import com.j256.ormlite.android.apptools.OpenHelperManager;

import java.text.SimpleDateFormat;
import java.util.Date;

public class AppCosecha extends Application {

    private static AppCosecha app;
    private static DBHelper mDBHelper;
    private static UsuarioEntity mUser;

    public static boolean IsTest = false;

    @Override
    public void onCreate() {
        super.onCreate();
        mDBHelper = OpenHelperManager.getHelper(this, DBHelper.class);
        app = this;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        OpenHelperManager.releaseHelper();
    }

    public static DBHelper getHelper() {
        return mDBHelper;
    }

    public static AppCosecha getInstance() {
        return app;
    }

    public static UsuarioEntity getUserLogin() {
        return mUser;
    }

    public static void setUserLogin(UsuarioEntity mUser) {
        AppCosecha.mUser = mUser;
    }

    @SuppressLint("MissingPermission")
    public static String getImei() {
        try {
            String deviceUniqueIdentifier = null;
            TelephonyManager tm = (TelephonyManager) app.getSystemService(Context.TELEPHONY_SERVICE);
            if (null != tm) {
                deviceUniqueIdentifier = tm.getDeviceId();
            }
            if (null == deviceUniqueIdentifier || 0 == deviceUniqueIdentifier.length()) {
                deviceUniqueIdentifier = Settings.Secure.getString(app.getContentResolver(), Settings.Secure.ANDROID_ID);
            }
            return deviceUniqueIdentifier;
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return "000000000000";
    }

    public static String getDate(){
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZZZZZ");
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        //SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        return simpleDateFormat.format(new Date());
    }

    public static String getTelefono() {
        return app.getString(R.string.telefono_default);
    }

    public static String getTelefono(String value) {
        return app.getString(R.string.telefono_default);
    }

    public static Resources getResource(){
        return app.getResources();
    }

    public static Context getContext(){
        return app.getApplicationContext();
    }

}

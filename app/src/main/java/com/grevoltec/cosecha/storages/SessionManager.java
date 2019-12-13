package com.grevoltec.cosecha.storages;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;
import android.widget.Toast;


import com.grevoltec.cosecha.entities.UsuarioEntity;

import java.util.Map;

public class SessionManager {

    private static String TAG = SessionManager.class.getSimpleName();
    private static String namePreferences_userdata = "session";

    //data user
    private static String user_id = "id";
    private static String user_user = "user";
    private static String user_name = "name";
    private static String user_password = "password";
    private static String user_idEmpresa = "idEmpresa";

    public static boolean saveUser(Context ctx, UsuarioEntity user){
        boolean flag = false;
        try{
            SharedPreferences preferences = ctx.getSharedPreferences(namePreferences_userdata, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.putInt(user_id,user.getIdUsuario());
            editor.putString(user_user,user.getUsuario());
            editor.putString(user_password,user.getClaveUsuario());
            editor.putInt(user_idEmpresa,user.getIdEmpresa());
            editor.putString(user_name,user.getNombreUsuario());
            flag = editor.commit();
        }catch (Exception e){
            Log.d(TAG,"saveUser:"+e.toString());
            Toast.makeText(ctx,"saveUser:"+e.toString(), Toast.LENGTH_LONG).show();
        }
        return flag;
    }

    public static UsuarioEntity getUser(Context ctx){
        UsuarioEntity user = null;
            try {
                SharedPreferences preferences = ctx.getSharedPreferences(namePreferences_userdata, Context.MODE_PRIVATE);
                if(
                        preferences.contains(user_id)
                        &&
                        preferences.contains(user_user)
                        &&
                        preferences.contains(user_password)
                        &&
                        preferences.contains(user_idEmpresa)
                        &&
                        preferences.contains(user_name)
                ){

                    user = new UsuarioEntity();
                    user.setIdUsuario(preferences.getInt(user_id,0));
                    user.setUsuario(preferences.getString(user_user,""));
                    user.setClaveUsuario(preferences.getString(user_password,""));
                    user.setIdEmpresa(preferences.getInt(user_idEmpresa,0));
                    user.setNombreUsuario(preferences.getString(user_name,""));
                }
            }catch (Exception e) {
                Log.d(TAG,"getUser:" + e.toString()) ;
                Toast.makeText(ctx, "getUser:" + e.toString(), Toast.LENGTH_LONG).show();
            }

        return user;
    }

    public static boolean deleteUser(Context ctx){
        boolean flag = false;
        try {
            SharedPreferences preferences = ctx.getSharedPreferences(namePreferences_userdata, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = preferences.edit();
            editor.clear();
            flag = editor.commit(); // commit changes
        }catch (Exception e) {
            Toast.makeText(ctx, "deleteUser:" + e.toString(), Toast.LENGTH_LONG).show();
            Log.d(TAG,"deleteUser: "+e.toString());
        }
        return flag;
    }

    public static Map<String,?> getMapUser(Context ctx){
        try {
            SharedPreferences preferences = ctx.getSharedPreferences(namePreferences_userdata, Context.MODE_PRIVATE);
            Map<String,?> map = preferences.getAll();
            return  map;
        }catch (Exception e) {
            Toast.makeText(ctx, "getMapUser:" + e.toString(), Toast.LENGTH_LONG).show();
        }
        return null;
    }

}

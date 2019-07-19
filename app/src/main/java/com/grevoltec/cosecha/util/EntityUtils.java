package com.grevoltec.cosecha.util;

import com.grevoltec.cosecha.app.AppCosecha;
import com.grevoltec.cosecha.entities.AcopioEntity;
import com.grevoltec.cosecha.entities.CamionEntity;

public class EntityUtils {

    public static class  ACOPIO{
        public static String getNombreByCodigoOrDefault(String cod){
            try{
                AcopioEntity acopioEntity = getEntityByCodigoOrDefault(cod);
                if(acopioEntity != null) return acopioEntity.getNombreAcopio();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return cod;
        }
        public static AcopioEntity getEntityByCodigoOrDefault(String cod){
            try{
                return AppCosecha.getHelper().getAcopioDao().queryBuilder().where().eq("codigo",cod).queryForFirst();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return null;
        }
    }

    public static class PALLET{
        public static String getSizeByQROrDefault(String qr){
            try{
                return ""+ AppCosecha.getHelper().getPalletDao().queryBuilder().where().eq("pallet_qr",qr).countOf();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return "0";
        }

        public static String getCountOrDefault(){
            try{
                return ""+ AppCosecha.getHelper().getPalletDao().queryBuilder().countOf();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return "0";
        }
    }

    public static class VIAJE{
        public static String getCountOrDefault(){
            try{
                return ""+ AppCosecha.getHelper().getViajeDao().queryBuilder().countOf();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return "0";
        }
    }

    public static class  CAMION{
        public static String getPlacaByCodigoOrDefault(String cod){
            try{
                CamionEntity camionEntity = getEntityByCodigoOrDefault(cod);
                if(camionEntity != null) return camionEntity.getPlacaCamion();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return cod;
        }
        public static CamionEntity getEntityByCodigoOrDefault(String cod){
            try{
                return AppCosecha.getHelper().getCamionDao().queryBuilder().where().eq("codigo",cod).queryForFirst();
            }catch (Exception ex){
                ex.printStackTrace();
            }
            return null;
        }
    }

}

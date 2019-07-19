package com.grevoltec.cosecha.util;

import com.grevoltec.cosecha.R;
import com.grevoltec.cosecha.app.AppCosecha;
import com.grevoltec.cosecha.entities.CosechaEntity;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.grevoltec.cosecha.app.AppCosecha.getContext;

public class QrUtils {

    public static class  QR_JABAS{

        public static void validateQR(String qr) throws AppException{
            if(qr == null) throw new AppException(getContext().getString(R.string.qr_invalido));
            if(qr.length() != 36) throw new AppException(getContext().getString(R.string.qr_invalido_jaba));
            if(!qr.startsWith("J")) throw new AppException(getContext().getString(R.string.qr_invalido_jaba));
            //validacion de fecha xd

            //obtener  fecha  actual
            Date date = new Date();
            String modifiedDate= new SimpleDateFormat("yyMMdd").format(date);

            //compararla  con la fecha que se tiene del qr
            String fechaQR = qr.substring(30);
           // Toast.makeText(getContext(),fechaQR +" "+modifiedDate,Toast.LENGTH_LONG).show();
            if(!fechaQR.equals(modifiedDate)){
                throw new AppException(getContext().getString(R.string.fecha_codigo_qr_invalida));
            }
            List<CosechaEntity> data = null;
            try {
                data = AppCosecha.getHelper().getCosechaDao().queryForAll();
                for (CosechaEntity entity: data) {
                    if(entity.getCodigoqr().equals(qr)){
                        throw new AppException(getContext().getString(R.string.qr_ya_leido));
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }


        }

        public static String getDNI(String qr){
            return qr.substring(6, 14);
        }

        public static String getCosechador(String qr){
            return qr.substring(14, 26);
        }

        public static String getNroEtiqueta(String qr){
            return qr.substring(26, 28);
        }

        public static String getTotalEtiqueta(String qr){
            return qr.substring(28, 30);
        }

        public static String getFecha(String qr){
            return qr.substring(30, 36);
        }

    }

    public static class  QR_PALLETS{

        public static void validateQR(String qr) throws AppException{
            if(qr == null) throw new AppException(getContext().getString(R.string.qr_invalido));
            if(qr.length() != 13) throw new AppException(getContext().getString(R.string.qr_invalido_pallet));
            if(!qr.startsWith("P")) throw new AppException(getContext().getString(R.string.qr_invalido_pallet));
        }

        public static String getAcopio(String qr){
            return qr.substring(1, 3);
        }

        public static String getCorrelativo(String qr){
            return qr.substring(3, 7);
        }

        public static String getFecha(String qr){
            return qr.substring(7, 13);
        }

    }

    public static class  QR_CAMIONES{

        public static void validateQR(String qr) throws AppException{
            if(qr == null) throw new AppException(getContext().getString(R.string.qr_invalido));
            if(qr.length() != 5) throw new AppException(getContext().getString(R.string.qr_invalido_vehiculo));
            if(!qr.startsWith("C")) throw new AppException(getContext().getString(R.string.qr_invalido_vehiculo));
        }

        public static String getCodigo(String qr){
            return qr.substring(1, 5);
        }

    }

}

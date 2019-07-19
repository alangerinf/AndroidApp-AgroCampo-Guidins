package com.grevoltec.cosecha.services;

import com.grevoltec.cosecha.services.models.response.SpSincronizarCosechaMovilResult;
import com.grevoltec.cosecha.services.models.response.SpSincronizarPalletMovilResult;
import com.grevoltec.cosecha.services.models.response.SpSincronizarRecepcionMovilResult;
import com.grevoltec.cosecha.services.models.response.SpSincronizarViajeMovilResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface SpInsertarService {

    //Get: api/sp_sincronizar_cosecha_movil/1/1/asdfas/2017-10-15/asdasdasd/946831855/13/3
    @GET("api/sp_sincronizar_cosecha_movil/{iduser}/{idtur}/{codigoqr}/{horaenvase}/{imeiequipo}/{nroequipo}/{envaseobs}/{envaseper}")
    @Headers({"Accept: application/json"})
    Call<List<SpSincronizarCosechaMovilResult>> GetSincronizarMovilCosecha(@Path("iduser") int iduser, @Path("idtur") int idtur, @Path("codigoqr") String codigoqr, @Path("horaenvase") String horaenvase, @Path("imeiequipo") String imeiequipo, @Path("nroequipo") String nroequipo, @Path("envaseobs") int envaseobs, @Path("envaseper") int envaseper,@Path("cantpersonas") int cantPersonas);


    //Get: api/sp_sincronizar_pallet_movil/1/asdqwesda/2017-10-15/0/adasd155/987654321/adasd848/2017-10-15
    @GET("api/sp_sincronizar_pallet_movil/{iduser}/{qrpallet}/{horapallet}/{palletcomp}/{imeiequipo}/{nroequipo}/{qrjaba}/{horajaba}")
    @Headers({"Accept: application/json"})
    Call<List<SpSincronizarPalletMovilResult>> GetSincronizarMovilPallet(@Path("iduser") int iduser, @Path("qrpallet") String qrpallet, @Path("horapallet") String horapallet, @Path("palletcomp") int palletcomp, @Path("imeiequipo") String imeiequipo, @Path("nroequipo") String nroequipo, @Path("qrjaba") String qrjaba, @Path("horajaba") String horajaba);

    //Get: api/sp_sincronizar_recepcion_movil/1/2017-10-15/1/asdas544/2017-10-15/qwerasd/948562135/123
    @GET("api/sp_sincronizar_recepcion_movil/{idplanta}/{fecharecep}/{iduser}/{qrpallet}/{horapallet}/{imeiequipo}/{nroequipo}/{pesopallet}/")
    @Headers({"Accept: application/json"})
    Call<List<SpSincronizarRecepcionMovilResult>> GetSincronizarMovilRecepcion(@Path("idplanta") int idplanta, @Path("fecharecep") String fecharecep, @Path("iduser") int iduser, @Path("qrpallet") String qrpallet, @Path("horapallet") String horapallet, @Path("imeiequipo") String imeiequipo, @Path("nroequipo") String nroequipo, @Path("pesopallet") double pesopallet);

    //Get: api/sp_sincronizar_viaje_movil/1/adsda121/2017-10-10/1/asdad/945874632/adqwea/2017-10-15
    @GET("api/sp_sincronizar_viaje_movil/{iduser}/{qrcamion}/{horacamion}/{viajecompleto}/{imeiequipo}/{numeroequipo}/{qrpallet}/{horapallet}")
    @Headers({"Accept: application/json"})
    Call<List<SpSincronizarViajeMovilResult>> GetSincronizarMovilViaje(@Path("iduser") int iduser, @Path("qrcamion") String qrcamion, @Path("horacamion") String horacamion, @Path("viajecompleto") int viajecompleto, @Path("imeiequipo") String imeiequipo, @Path("numeroequipo") String numeroequipo, @Path("qrpallet") String qrpallet, @Path("horapallet") String horapallet);

}
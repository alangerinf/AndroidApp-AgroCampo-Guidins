package com.grevoltec.cosechaguiddins.services;

import com.grevoltec.cosechaguiddins.services.models.response.SpAutenticacionMovilResult;
import com.grevoltec.cosechaguiddins.services.models.response.SpListarAcopiosMovilResult;
import com.grevoltec.cosechaguiddins.services.models.response.SpListarCamionesMovilResult;
import com.grevoltec.cosechaguiddins.services.models.response.SpListarCultivosMovilResult;
import com.grevoltec.cosechaguiddins.services.models.response.SpListarFundosMovilResult;
import com.grevoltec.cosechaguiddins.services.models.response.SpListarPlantasMovilResult;
import com.grevoltec.cosechaguiddins.services.models.response.SpListarTurnosMovilResult;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface SpListarService {

    //Get: api/sp_autenticacion_movil/dquirozn/123456/1
    @GET("api/sp_autenticacion_movil/{user}/{clave}/{idcult}")
    @Headers({"Accept: application/json"})
    Call<List<SpAutenticacionMovilResult>> GetAutenticacion(@Path("user") String user, @Path("clave") String clave, @Path("idcult") int idcult);

    //GET: api/sp_listar_acopios_movil/1
    @GET("api/sp_listar_acopios_movil/{idemp}")
    @Headers({"Accept: application/json"})
    Call<List<SpListarAcopiosMovilResult>> GetListarAcopios(@Path("idemp") int idemp);

    //GET: api/sp_listar_camiones_movil
    @GET("api/sp_listar_camiones_movil")
    @Headers({"Accept: application/json"})
    Call<List<SpListarCamionesMovilResult>> GetListarCamiones();

    //GET: api/sp_listar_cultivos_movil
    @GET("api/sp_listar_cultivos_movil")
    @Headers({"Accept: application/json"})
    Call<List<SpListarCultivosMovilResult>> GetListarCultivos();

    //GET: api/sp_listar_fundos_movil/1/1
    @GET("api/sp_listar_fundos_movil/{idemp}/{idcult}")
    @Headers({"Accept: application/json"})
    Call<List<SpListarFundosMovilResult>> GetListarFundos(@Path("idemp") int idemp, @Path("idcult") int idcult);

    //GET: api/sp_listar_plantas_movil/1
    @GET("api/sp_listar_plantas_movil/{idemp}")
    @Headers({"Accept: application/json"})
    Call<List<SpListarPlantasMovilResult>> GetListarPlantas(@Path("idemp") int idemp);

    //GET: api/sp_listar_turnos_movil/1/1
    @GET("api/sp_listar_turnos_movil/{idemp}/{idcult}")
    @Headers({"Accept: application/json"})
    Call<List<SpListarTurnosMovilResult>> GetListarTurnos(@Path("idemp") int idemp, @Path("idcult") int idcult);

}
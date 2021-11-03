package com.weblieu.findtrue.repositry;

import com.weblieu.findtrue.model.Res;

import io.reactivex.Observable;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface Api {
    @Headers({"Content-Type:application/json",
            "x-client-id:54709fd88ae1ff17a3811256f90745",
            "x-client-secret:fa07c20143622f376ccb79eb7e3a545d2878b92b"})
    @POST("cftoken/order")
    Observable<Res> getToken(@Query("orderId") String orderId, @Query("orderAmount") String orderAmount, @Query("orderCurrency") String orderCurrency);
}

package com.interstellar.elite.core.requestbuilder;


import com.interstellar.elite.RetroRequestBuilder;
import com.interstellar.elite.core.model.UpdateOrderRequestEntity;
import com.interstellar.elite.core.response.ClientCommonResponse;
import com.interstellar.elite.core.response.CompleteOrderResponse;
import com.interstellar.elite.core.response.DocumentResponse;
import com.interstellar.elite.core.response.DocumentViewResponse;
import com.interstellar.elite.core.response.NotificationResponse;
import com.interstellar.elite.core.response.OrderDetailResponse;
import com.interstellar.elite.core.response.ProductDocumentResponse;
import com.interstellar.elite.core.response.RtoProductDisplayResponse;

import java.util.HashMap;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

public class ProductRequestBuilder extends RetroRequestBuilder {

    public ProductRequestBuilder.ProductNetworkService getService() {

        return super.build().create(ProductRequestBuilder.ProductNetworkService.class);
    }

    public interface ProductNetworkService {

//        @GET("/api/loadproduct")
//        Call<ProductResponse> getProductMaster();    // Not in Used

//        @GET("/api/load-all-cities")
//        Call<CityResponse> getCityMaster();

//        @POST("/api/insertorder")
//        Call<OrderResponse> insertOrder(@Body InsertOrderRequestEntity requestEntity);

        @POST("/api/get-orders")
        Call<OrderDetailResponse> getOrder(@Body HashMap<String, String> body);


        @POST("/api/update-order")
        Call<ClientCommonResponse> updateOrder(@Body UpdateOrderRequestEntity body);



        @POST("/api/get-rto-list")
        Call<RtoProductDisplayResponse> getRTOProductList(@Body HashMap<String, String> body);   //used

        @POST("/api/get-rto-list-chahngevehicle")
        Call<RtoProductDisplayResponse> getRTOProductListOnChangeVehicle(@Body HashMap<String, String> body);   //used

//        @POST("/api/get-non-rto-list")
//        Call<NonRtoProductDisplayResponse> getNonRTOProductList(@Body HashMap<String, String> body);   //used

        @POST("/api/get-product-wise-document")
        Call<ProductDocumentResponse> getProdDocument(@Body HashMap<String, String> body);   //used

        @POST("/api/get-order-document")
        Call<DocumentViewResponse> getDocumentView(@Body HashMap<String, String> body);   //used

        @POST("/api/get-notification")
        Call<NotificationResponse> getNotification(@Body HashMap<String, String> body);   //used


        @Multipart
        @POST("/api/doc-upload")
        Call<DocumentResponse> uploadDocument(@Part() MultipartBody.Part doc, @PartMap() Map<String, Integer> partMap);  //used


        @POST("/api/get-complete-orders")
        Call<CompleteOrderResponse> getCompletetOrder(@Body HashMap<String, String> body);



        //******************  Chat *******************************//

//        @POST("/api/get-all-chat-detail")
//        Call<ChatResponse> getChatDetail(@Body HashMap<String, String> body);
//
//        @POST("/api/update-read-customer-chat")
//        Call<UpdateChatResponse> updateReadChat(@Body HashMap<String, String> body);
//
//        @POST("/api/save-customer-chat")
//        Call<SaveChatResponse> saveChat(@Body BodyHashMap<String, String> body);
    }
}
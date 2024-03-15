package com.dating.blinddate.Notification.API;

import com.dating.blinddate.Notification.Model.NotificationRequest;
import com.google.gson.JsonObject;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface RetrofitInterface {

    @Headers({
            "Content-Type:application/json",
            "Authorization:key=AAAAvBNzkWQ:APA91bEU42bnYaQXFIhpXt0mIxniP4BEHwaDFIIOtAQGkMVx4gA1zhi5zqnPwslhRnjyAbwpj0VLZ8-OTsKtym1RSYwlSI5jneQTsTK0v40sP6oO732oKUDtdg-LUYCi3g7rOPecf4xL"
    })
    @POST("fcm/send")
    Call<JsonObject> sendNotification(@Body NotificationRequest notificationRequest);

}

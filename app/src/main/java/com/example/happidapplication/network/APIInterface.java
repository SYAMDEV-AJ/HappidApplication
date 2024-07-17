package com.example.happidapplication.network;


import com.example.happidapplication.modelclass.SubmitRequest;
import com.example.happidapplication.modelclass.SubmitResponse;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.POST;

public interface APIInterface {

    @POST("c5acbf88-9c7f-4108-ba69-e83dbdddd37c")
    Call<SubmitResponse> submit(@Body SubmitRequest loginrequest);
}


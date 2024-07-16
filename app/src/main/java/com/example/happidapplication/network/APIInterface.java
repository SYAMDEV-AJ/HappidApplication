package com.example.happidapplication.network;


import com.example.happidapplication.modelclass.SubmitRequest;
import com.example.happidapplication.modelclass.SubmitResponse;

import retrofit2.Call;
import retrofit2.http.Body;

import retrofit2.http.POST;

public interface APIInterface {

    @POST("getLeaveDetails")
    Call<SubmitResponse> submit(@Body SubmitRequest loginrequest);
}


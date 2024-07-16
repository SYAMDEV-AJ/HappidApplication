package com.example.happidapplication.modelclass;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SubmitResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("result")
    @Expose
    private String result;

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}

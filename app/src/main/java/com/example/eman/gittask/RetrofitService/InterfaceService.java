package com.example.eman.gittask.RetrofitService;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Eman on 1/16/2017.
 */


public interface InterfaceService {

    @GET("repos")
    Call<List<ResultModle>> getData();
}


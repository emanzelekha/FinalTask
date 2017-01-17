package com.example.eman.gittask.RetrofitService;


import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

import static com.example.eman.gittask.MainActivity.PagesNum;

/**
 * Created by Eman on 1/16/2017.
 */


public interface InterfaceService {


    @GET
    Call<List<ResultModle>> getData(@Url String url);
}


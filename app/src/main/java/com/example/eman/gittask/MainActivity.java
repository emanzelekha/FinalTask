package com.example.eman.gittask;

import android.app.ProgressDialog;

import android.content.Context;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import com.example.eman.gittask.Fonts.TypefaceUtil;
import com.example.eman.gittask.RecyclerView.Adapter;
import com.example.eman.gittask.RecyclerView.Model;
import com.example.eman.gittask.RetrofitService.InterfaceService;
import com.example.eman.gittask.RetrofitService.ResultModle;





import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;


import okhttp3.Cache;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.example.eman.gittask.RetrofitService.caching.REWRITE_CACHE_CONTROL_INTERCEPTOR;

public class MainActivity extends AppCompatActivity {

    @Bind(R.id.recycler)
    RecyclerView recyclerView;
    @Bind(R.id.activity_main)
    View activity_main;
    int width;
    public static boolean work;
    ProgressDialog progressDialog;
    private List<Model> disList = new ArrayList<>();
    private Adapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        TypefaceUtil.overrideFonts(this, activity_main);
        work=isNetworkConnected();
        ////////////////////////////////////////////////////////////////////////////////////////////
        Display display = ((WindowManager) getSystemService(getApplication().WINDOW_SERVICE)).getDefaultDisplay();
         width = display.getWidth();




        ////////////////////////////////////////////////////////////////////////////////////////////
        mAdapter = new Adapter(disList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplication());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        ////////////////////////////////////////////////////////////////////////////////////////////////
        File httpCacheDirectory = new File(this.getCacheDir(), "responses");
        int cacheSize = 10 * 1024 * 1024; // 10 MiB
        Cache cache = new Cache(httpCacheDirectory, cacheSize);

        OkHttpClient client = new OkHttpClient.Builder()
                .addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(cache).build();

//setup cache


//add cache to the client


        //////////////////////////////////////////////////////////////////////////////////////////////
        Retrofit retrofit = new Retrofit.Builder().baseUrl("https://api.github.com/users/square/").client(client).
                addConverterFactory(GsonConverterFactory.create()).build();
        InterfaceService AllData = retrofit.create(InterfaceService.class);//conected api
        Call<List<ResultModle>> connection = AllData.getData();
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Please Waite...");
        progressDialog.show();
        connection.enqueue(new Callback<List<ResultModle>>() {

            @Override
            public void onResponse(Call<List<ResultModle>> call, Response<List<ResultModle>> response) {
                progressDialog.dismiss();
                for (int i = 0; i < response.body().size(); i++) {

                    Model disUserControl = new Model(response.body().get(i).getName(),response.body().get(i).getDescription()
                            , response.body().get(i).getOwner().getLogin(), response.body().get(i).getFork()
                            , response.body().get(i).getHtml_url(),response.body().get(i).getOwner().getHtml_url(),width);
                    disList.add(disUserControl);
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<ResultModle>> call, Throwable t) {


            }
        });


    }
    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }

}

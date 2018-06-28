package com.pointnexus.heroes.heroestest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InserirActivity extends AppCompatActivity {

    private static final String TAG = InserirActivity.class.getSimpleName();
    List<String> supplierNames = Arrays.asList("sup1", "sup2");
    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir);


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //GsonConverterFactory para converter diretamente json data para objeto
                .build();

        api = retrofit.create(Api.class);

        // prepare call in Retrofit 2.0
        Heroi heroi = new Heroi(1,
                "John Doe",
                500,
                555,
                5,
                5.0,
                5,supplierNames,supplierNames);

        Call<Heroi> call = api.createUser("Content-Type","application/json","",heroi);

        call.enqueue(new Callback<Heroi>() {
            @Override
            public void onResponse(Call<Heroi> call, Response<Heroi> response) {
            }

            @Override
            public void onFailure(Call<Heroi> call, Throwable t) {

            }
        });
    }

}
package com.pointnexus.heroes.heroestest;

import android.app.Service;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FotosActivity extends AppCompatActivity {
    Api api;
    ImageView imgKing;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fotos);


        //create a file to write bitmap data
        File f = new File(getApplicationContext().getCacheDir(), "test.jpg");
        try {
            f.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

//Convert bitmap to byte array
        ImageView yourImageView = (ImageView) findViewById(R.id.imageView);
        Bitmap bitmap = ((BitmapDrawable)yourImageView.getDrawable()).getBitmap();

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 0 /*ignored for PNG*/, bos);
        byte[] bitmapdata = bos.toByteArray();

//write the bytes in file
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(f);
            fos.write(bitmapdata);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        imgKing = (ImageView)findViewById(R.id.imageView3);
        Glide.with(this)
                .load(bitmap)
                .into(imgKing);



        RequestBody reqFile = RequestBody.create(MediaType.parse("image/*"), f);
        MultipartBody.Part body = MultipartBody.Part.createFormData("upload", f.getName(), reqFile);

        //Service service = new Retrofit.Builder().baseUrl(Api.BASE_URL).build().create(Service.class);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .build();

        api = retrofit.create(Api.class);
        Call<ResponseBody> req = api.postImage("Content-Type","multipart/form-data","",body);
        req.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                Toast.makeText(getApplicationContext(), "Em teste", Toast.LENGTH_SHORT).show();
                Log.e("Success", new Gson().toJson(response.body()));
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Toast.makeText(getApplicationContext(), "Não Foi", Toast.LENGTH_SHORT).show();
            }
        });

    }
}

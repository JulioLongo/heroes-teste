package com.pointnexus.heroes.heroestest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

        ListView listView;
        Button btnInserir;
        Button btnMagias;
        Button btnClasses;
        Button btnHerois;
        Button btnFotos;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            listView = (ListView) findViewById(R.id.listViewHeroes);
            btnInserir = (Button) findViewById(R.id.botaoInserir);
            btnMagias = (Button) findViewById(R.id.botaoMagias);
            btnClasses = (Button) findViewById(R.id.botaoClasses);
            btnHerois = (Button) findViewById(R.id.botaoHerois);
            btnFotos = (Button) findViewById(R.id.botaoFotos);

            btnFotos.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, FotosActivity.class);
                    startActivity(intent);
                }
            });

            btnMagias.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, MagiasActivity.class);
                    startActivity(intent);
                }
            });

            btnClasses.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, ClassesActivity.class);
                    startActivity(intent);
                }
            });


            btnInserir.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, InserirActivity.class);
                    startActivity(intent);
                }
            });

            btnHerois.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MainActivity.this, HeroiActivity.class);
                    startActivity(intent);
                }
            });


        }

    }

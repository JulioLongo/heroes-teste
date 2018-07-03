package com.pointnexus.heroes.heroestest;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.Locale;

public class IntroActivity extends AppCompatActivity {

    ImageView imgKing;
    ImageView imgClique;
    TextView txtTexto;

    int i = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        imgKing = (ImageView)findViewById(R.id.imgKing);
        imgClique = (ImageView)findViewById(R.id.imgClique);
        txtTexto = (TextView)findViewById(R.id.txtTexto);
        ConstraintLayout mylayout = (ConstraintLayout) findViewById(R.id.mylayout);

        Glide.with(this)
                .load(R.drawable.king)
                .into(imgKing);

        Glide.with(this)
                .load(R.drawable.clique)
                .into(imgClique);

        //DEFINIR FONTE PIXEL
        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "fonts/fontpixel.ttf");
        txtTexto.setTypeface(custom_font);

        //AO CLICAR EM QUALQUER LUGAR DA TELA
        mylayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mudarTexto();

            }
        });

    }

    public void mudarTexto(){

        i++;
        if(i==1){
            txtTexto.setText("Recentemente nosso reino cresceu muito e perdemos a organização de nossos guerreiros.");
        }
        if(i==2){
            txtTexto.setText("Nos ajude a cadastrar os herois novamente em nossos registros.");
        }
        if(i==3){
            finish();
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);
        }
    }
}

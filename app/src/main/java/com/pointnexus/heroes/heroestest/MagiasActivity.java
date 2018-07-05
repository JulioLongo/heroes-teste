package com.pointnexus.heroes.heroestest;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pointnexus.heroes.heroestest.Models.Magias;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MagiasActivity extends AppCompatActivity {

    ListView listViewMagias;
    Button btnProcurar;
    TextView txtId;
    EditText edIdHero;
    ProgressDialog pd;
    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_magias);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //GsonConverterFactory para converter diretamente json data para objeto
                .build();

        api = retrofit.create(Api.class);

        //Instancia Botoes
        btnProcurar = (Button) findViewById(R.id.btnProcurar);
        txtId = (TextView) findViewById(R.id.txtId);
        edIdHero = (EditText) findViewById(R.id.edIdHero);
        listViewMagias = (ListView) findViewById(R.id.listViewMagias);

        btnProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procurarMagias();
            }
        });

        //Mostra Progress Dialog
        pd = new ProgressDialog(MagiasActivity.this);
        pd.setMessage("carregando");
        pd.show();

        //ALIMENTA LISTVIEW
        pegarMagias();
    }

    public void procurarMagias() {
        //PEGA ID DIGITADO
        String magiaId = edIdHero.getText().toString();

        //CHAMA RESQUEST DA API PARA PEGAR RESPONSE COM ID DEFINIDO
        Call<Magias> call = api.pegarMagias(magiaId);

        call.enqueue(new Callback<Magias>() {
            @Override
            public void onResponse(Call<Magias> call, Response<Magias> response) {
                pd.dismiss();

                //MOSTRA RESULTADOS
                Magias magiapega = response.body();
                if (magiapega != null) {
                    txtId.setText("Magia: "+magiapega.getName());
                } else {
                    txtId.setText("Magia não encontrada");
                }
            }

            @Override
            public void onFailure(Call<Magias> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Metodo pegar CLASSES
    private void pegarMagias() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //GsonConverterFactory para converter diretamente json data para objeto
                .build();

        Api api = retrofit.create(Api.class);

        //chama metodo da api para pegar response
        Call<List<Magias>> call = api.pegarMagias();

        call.enqueue(new Callback<List<Magias>>() {
            @Override
            public void onResponse(Call<List<Magias>> call, Response<List<Magias>> response) {
                pd.dismiss();

                //Pega todos os resultados numa lista
                List<Magias> listaMagias = response.body();

                //String array para a listView
                String[] magiasArray = new String[listaMagias.size()];

                //Inserir todos no array
                for (int i = 0; i < listaMagias.size(); i++) {
                    magiasArray[i] = listaMagias.get(i).getName();
                }

                //mostrar array no listview
                listViewMagias.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        magiasArray));

                listViewMagias.setBackgroundColor(Color.WHITE);
            }

            @Override
            public void onFailure(Call<List<Magias>> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

package com.pointnexus.heroes.heroestest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ClassesActivity extends AppCompatActivity {

    ListView listView;
    Button btnProcurar;
    TextView responseText;
    EditText editText;
    Api api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classes);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //GsonConverterFactory para converter diretamente json data para objeto
                .build();

        api = retrofit.create(Api.class);

        //Instancia Botoes
        btnProcurar = (Button) findViewById(R.id.main_btn_lookup);
        responseText = (TextView) findViewById(R.id.main_text_response);
        editText = (EditText) findViewById(R.id.main_edit_username);
        listView = (ListView) findViewById(R.id.listViewHeroes);

        btnProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procurarClasse();
            }
        });

        //ALIMENTA LISTVIEW
        pegarClasses();
    }

        public void procurarClasse() {
            //PEGA ID DIGITADO
            String classeId = editText.getText().toString();

            //CHAMA RESQUEST DA API PARA PEGAR RESPONSE COM ID DEFINIDO
            Call<Classes> call = api.pegarClasses(classeId);

            call.enqueue(new Callback<Classes>() {
                @Override
                public void onResponse(Call<Classes> call, Response<Classes> response) {
                    //MOSTRA RESULTADOS
                    Classes classepega = response.body();
                    if (classepega != null) {
                        responseText.setText(classepega.getName());
                    } else {
                        responseText.setText("bugo");
                    }
                }

                @Override
                public void onFailure(Call<Classes> call, Throwable t) {
                    Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        }


    //Metodo pegar CLASSES
    private void pegarClasses() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //GsonConverterFactory para converter diretamente json data para objeto
                .build();

        Api api = retrofit.create(Api.class);

        //chama metodo da api para pegar response
        Call<List<Classes>> call = api.pegarClasses();

        call.enqueue(new Callback<List<Classes>>() {
            @Override
            public void onResponse(Call<List<Classes>> call, Response<List<Classes>> response) {
                //Pega todos os resultados
                List<Classes> listaClasses = response.body();

                //String array para a listView
                String[] classes = new String[listaClasses.size()];

                //Inserir todos no array
                for (int i = 0; i < listaClasses.size(); i++) {
                    classes[i] = listaClasses.get(i).getName();
                }

                //mostrar array no listview
                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        classes));
            }

            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

}

package com.pointnexus.heroes.heroestest;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

    EditText edNomeHeroi;
    TextView txtClasse;
    Button btnAnterior;
    Button btnProximo;
    Button btnCriar;
    EditText edHealthPoints;
    EditText edDefense;
    EditText edDamage;
    EditText edAttackSpeed;
    EditText edMovimentSpeed;

    int idClasse;
    int contadorClasse;
    int maxNumClasse;
    int maxNumClassedoArray;
    String[] classes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir);


        edNomeHeroi = (EditText)findViewById(R.id.edNomeHeroi);
        txtClasse = (TextView)findViewById(R.id.txtClasse);

        btnProximo = (Button)findViewById(R.id.btnProximo);
        btnAnterior = (Button)findViewById(R.id.btnAnterior);
        btnCriar = (Button)findViewById(R.id.btnCriar);

        edHealthPoints = (EditText)findViewById(R.id.edHealthPoints);
        edDefense = (EditText)findViewById(R.id.edDefense);
        edDamage = (EditText)findViewById(R.id.edDamage);
        edAttackSpeed = (EditText)findViewById(R.id.edAttackSpeed);
        edMovimentSpeed = (EditText)findViewById(R.id.edMovimentSpeed);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //GsonConverterFactory para converter diretamente json data para objeto
                .build();

        api = retrofit.create(Api.class);
        pegarClasses();


        btnCriar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                criarHeroi();
            }
        });

        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contadorClasse == 0) {
                    contadorClasse = maxNumClassedoArray;
                    txtClasse.setText(classes[contadorClasse]);

                    //Define valor que vai ser passado
                    idClasse = contadorClasse +1;
                    return;
                }

                contadorClasse = contadorClasse - 1;
                txtClasse.setText(classes[contadorClasse]);

                //Define valor que vai ser passado
                idClasse = contadorClasse + 1;

                System.out.println(idClasse);
                System.out.println(contadorClasse);
                System.out.println(maxNumClasse);
            }
        });

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(contadorClasse == maxNumClassedoArray) {
                    contadorClasse = 0;
                    txtClasse.setText(classes[contadorClasse]);

                    //Define valor que vai ser passado
                    idClasse = contadorClasse + 1;
                    return;
                }

                contadorClasse = contadorClasse +1;
                txtClasse.setText(classes[contadorClasse]);

                //Define valor que vai ser passado
                idClasse = contadorClasse + 1;

                System.out.println(idClasse);
                System.out.println(contadorClasse);
                System.out.println(maxNumClasse);
            }
        });

    }

    public void criarHeroi(){
        String nomeHeroi = edNomeHeroi.getText().toString();
        //int idClasse = (TextView)findViewById(R.id.txtClasse);
        int healthPoints = Integer.parseInt(edHealthPoints.getText().toString());
        int defense = Integer.parseInt(edDefense.getText().toString());
        int damage = Integer.parseInt(edDamage.getText().toString());
        double attackSpeed = Double.parseDouble(edAttackSpeed.getText().toString());
        int movimentSpeed = Integer.parseInt(edMovimentSpeed.getText().toString());

        // prepare call in Retrofit 2.0
        Heroi heroi = new Heroi(idClasse,
                nomeHeroi /*nomeheroi*/,
                healthPoints,
                defense,
                damage,
                attackSpeed,
                movimentSpeed,supplierNames,supplierNames);

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
                maxNumClasse = listaClasses.size();
                maxNumClassedoArray = maxNumClasse - 1;
                classes = new String[listaClasses.size()];

                //Inserir todos no array
                for (int i = 0; i < listaClasses.size(); i++) {
                    classes[i] = listaClasses.get(i).getName();
                }

                //mostrar array no listview
                txtClasse.setText(classes[0]);
                idClasse = 1;
            }

            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
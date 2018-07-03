package com.pointnexus.heroes.heroestest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
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

    List<Integer> photos = Arrays.asList();
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

    ListView listView;

    ListView listViewPronta;

    ListView listViewNumero;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayListPronta;

    private ArrayAdapter<Integer> adapterNumero;
    private ArrayList<Integer> arrayListNumero;

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

        listView = (ListView) findViewById(R.id.listaEspecialidade);

        listViewPronta = (ListView) findViewById(R.id.listaEspeciladePronta);
        arrayListPronta = new ArrayList<String>();

        listViewNumero = (ListView) findViewById(R.id.listaMagiasNumero);
        arrayListNumero = new ArrayList<Integer>();

        ///////CRIAR ADAPTADOR ARRAY
        adapterNumero = new ArrayAdapter<Integer>(
                getApplicationContext(),
                android.R.layout.simple_spinner_item,
                arrayListNumero);

        ////////CRIAR LISTA
        listViewNumero.setAdapter(adapterNumero);

        ///////CRIAR ADAPTADOR ARRAY
        adapter = new ArrayAdapter<String>(
                getApplicationContext(),
                android.R.layout.simple_spinner_item,
                arrayListPronta);

        ////////CRIAR LISTA
        listViewPronta.setAdapter(adapter);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //GsonConverterFactory para converter diretamente json data para objeto
                .build();

        api = retrofit.create(Api.class);
        pegarClasses();
        pegarMagias();


        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //POSIÇÃO CLICADA E NOME CLICADO
                String pegarNome = (String) parent.getItemAtPosition(position);

                //ABRE NOVA JANELA E PASSA OBJETOS E NOME CLICADO
                Toast.makeText(getApplicationContext(), pegarNome, Toast.LENGTH_SHORT).show();
                arrayListPronta.add(pegarNome);
                arrayListNumero.add(position+1);

                adapterNumero.notifyDataSetChanged();
                adapter.notifyDataSetChanged();

            }
        });

        listViewPronta.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //POSIÇÃO CLICADA E NOME CLICADO
                String pegarNome = (String) parent.getItemAtPosition(position);

                //ABRE NOVA JANELA E PASSA OBJETOS E NOME CLICADO
                Toast.makeText(getApplicationContext(), pegarNome, Toast.LENGTH_SHORT).show();
                int posicaoClique = position;
                arrayListPronta.remove(posicaoClique);
                arrayListNumero.remove(posicaoClique);

                adapter.notifyDataSetChanged();
                adapterNumero.notifyDataSetChanged();

            }
        });


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
        /*int[] Magias = {};

        for (int i=0;i<adapterNumero.getCount();i++) {
            String selectedFromList = (listViewNumero.getItemAtPosition(i).toString());
            Magias[i] = Integer.parseInt(selectedFromList);
        }*/

        // prepare call in Retrofit 2.0
        HeroiTeste heroi = new HeroiTeste(idClasse,
                nomeHeroi ,
                healthPoints,
                defense,
                damage,
                attackSpeed,
                movimentSpeed,arrayListNumero, arrayListNumero);


        Call<HeroiTeste> call = api.createUserr("Content-Type","application/json","",heroi);

        call.enqueue(new Callback<HeroiTeste>() {
            @Override
            public void onResponse(Call<HeroiTeste> call, Response<HeroiTeste> response) {

            }

            @Override
            public void onFailure(Call<HeroiTeste> call, Throwable t) {

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

                //mostrar a primeira classe do array no textView
                txtClasse.setText(classes[0]);

                //Id classe definida caso clique no botão inserir
                idClasse = 1;
            }

            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {
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
                //Pega todos os resultados numa lista
                List<Magias> listaMagias = response.body();

                //String array para a listView
                String[] magiasArray = new String[listaMagias.size()];

                //Inserir todos no array
                for (int i = 0; i < listaMagias.size(); i++) {
                    magiasArray[i] = listaMagias.get(i).getName();
                }

                //mostrar array no listview
                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        magiasArray));
            }

            @Override
            public void onFailure(Call<List<Magias>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
package com.pointnexus.heroes.heroestest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.pointnexus.heroes.heroestest.Models.Heroi;
import com.pointnexus.heroes.heroestest.Models.HeroiTeste;
import com.pointnexus.heroes.heroestest.Models.Magias;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SelecionarMagiaActivity extends AppCompatActivity {

    Api api;

    ListView listView;
    ListView listViewPronta;
    ListView listViewNumero;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> arrayListPronta;

    private ArrayAdapter<Integer> adapterNumero;
    private ArrayList<Integer> arrayListNumero;

    Button btnCriar;

    ProgressDialog pd;

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecionar_magia);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //GsonConverterFactory para converter diretamente json data para objeto
                .build();

        api = retrofit.create(Api.class);

        btnCriar = (Button)findViewById(R.id.btnCriar);

        Intent myIntent = getIntent(); // gets the previously created intent
        String opcaos = myIntent.getStringExtra("OPCAO");
        if(opcaos.equals("criar")){
            btnCriar.setText("Criar Herói");
        }
        else if(opcaos.equals("atualizar")){
            btnCriar.setText("Atualizar Herói");
        }


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
                Intent myIntent = getIntent(); // gets the previously created intent
                String opcaos = myIntent.getStringExtra("OPCAO");
                if(opcaos.equals("criar")){
                    CriarHeroi();
                    return;
                }
                if(opcaos.equals("atualizar")){
                    atualzarHeroi();
                    return;
                }

            }
        });

        //Mostra Progress Dialog
        pd = new ProgressDialog(SelecionarMagiaActivity.this);
        pd.setMessage("carregando");
        pd.show();

        //Alimenta list view
        pegarMagias();

    }

    public void CriarHeroi(){
        Intent myIntent = getIntent(); // gets the previously created intent
        String nomeHeroi = myIntent.getStringExtra("NOMEHEROI"); // will return "FirstKeyValue"
        int idClasse = myIntent.getIntExtra("IDCLASSE",0);
        int healthPoints = myIntent.getIntExtra("HEALTHPOINTS",0);
        int defense = myIntent.getIntExtra("DEFENSE",0);
        int damage = myIntent.getIntExtra("DAMAGE",0);
        double attackSpeed = myIntent.getDoubleExtra("ATTACKSPEED",0);
        int movimentSpeed = myIntent.getIntExtra("MOVIMENTSPEED",0);


        // PREPARAR CALL
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
                pd.dismiss();

                Toast.makeText(SelecionarMagiaActivity.this, "Heroi Criado", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(SelecionarMagiaActivity.this,MainActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<HeroiTeste> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(SelecionarMagiaActivity.this, "Falhou", Toast.LENGTH_LONG).show();
            }
        });

    }

    //Metodo pegar CLASSES
    private void pegarMagias() {

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
                listView.setAdapter(new ArrayAdapter<String>(getApplicationContext(),
                        android.R.layout.simple_list_item_1,
                        magiasArray));

                listView.setBackgroundColor(Color.WHITE);
                listViewPronta.setBackgroundColor(Color.WHITE);
            }

            @Override
            public void onFailure(Call<List<Magias>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                pd.dismiss();
            }
        });
    }

    public void atualzarHeroi(){
        Intent myIntent = getIntent(); // gets the previously created intent
        id = myIntent.getStringExtra("ID");
        String nomeHeroi = myIntent.getStringExtra("NOMEHEROI"); // will return "FirstKeyValue"
        int idClasse = myIntent.getIntExtra("IDCLASSE",0);
        int healthPoints = myIntent.getIntExtra("HEALTHPOINTS",0);
        int defense = myIntent.getIntExtra("DEFENSE",0);
        int damage = myIntent.getIntExtra("DAMAGE",0);
        double attackSpeed = myIntent.getDoubleExtra("ATTACKSPEED",0);
        int movimentSpeed = myIntent.getIntExtra("MOVIMENTSPEED",0);

        // prepare call in Retrofit 2.0
        HeroiTeste heroi = new HeroiTeste(idClasse,
                nomeHeroi /*nomeheroi*/,
                healthPoints,
                defense,
                damage,
                attackSpeed,
                movimentSpeed,arrayListNumero, arrayListNumero);

        Call<HeroiTeste> call = api.atualizarHeroi("Content-Type","application/json","",id,heroi);

        call.enqueue(new Callback<HeroiTeste>() {
            @Override
            public void onResponse(Call<HeroiTeste> call, Response<HeroiTeste> response) {
                Toast.makeText(getApplicationContext(), "Herói Atualizado", Toast.LENGTH_LONG).show();
                finish();
                Intent intent = new Intent(SelecionarMagiaActivity.this,HeroiActivity.class);
                startActivity(intent);
            }

            @Override
            public void onFailure(Call<HeroiTeste> call, Throwable t) {

            }
        });

    }
}

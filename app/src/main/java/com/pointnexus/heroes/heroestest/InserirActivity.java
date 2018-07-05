package com.pointnexus.heroes.heroestest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.pointnexus.heroes.heroestest.Models.Classes;
import com.pointnexus.heroes.heroestest.Models.HeroiTeste;
import com.pointnexus.heroes.heroestest.Models.Magias;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InserirActivity extends AppCompatActivity {

    Api api;
    EditText edNomeHeroi;
    TextView txtClasse;

    Button btnAnterior;
    Button btnProximo;
    Button btnProximaTela;

    EditText edHealthPoints;
    EditText edDefense;
    EditText edDamage;
    EditText edAttackSpeed;
    EditText edMovimentSpeed;

    int idClasse;
    int contador;
    int maxNumClasse;
    int maxNumClassedoArray;

    String[] classesArray;

    ProgressDialog pd;
    String opcao;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inserir);


        //PEGAR OBJETOS
        edNomeHeroi = (EditText)findViewById(R.id.edNomeHeroi);
        txtClasse = (TextView)findViewById(R.id.txtClasse);

        btnProximo = (Button)findViewById(R.id.btnProximo);
        btnAnterior = (Button)findViewById(R.id.btnAnterior);
        btnProximaTela = (Button)findViewById(R.id.btnCriar);

        edHealthPoints = (EditText)findViewById(R.id.edHealthPoints);
        edDefense = (EditText)findViewById(R.id.edDefense);
        edDamage = (EditText)findViewById(R.id.edDamage);
        edAttackSpeed = (EditText)findViewById(R.id.edAttackSpeed);
        edMovimentSpeed = (EditText)findViewById(R.id.edMovimentSpeed);


        Intent intent = getIntent(); // gets the previously created intent
        opcao = intent.getStringExtra("OPCAO");
        id = intent.getStringExtra("ID");
        String nome = intent.getStringExtra("NOME");
        String classe = intent.getStringExtra("CLASSE");
        String healthpoints = intent.getStringExtra("HEALTHPOINTS");
        String defense = intent.getStringExtra("DEFENSE");
        String damage = intent.getStringExtra("DAMAGE");
        String speedattack = intent.getStringExtra("SPEEDATTACK");
        String movimentspeed = intent.getStringExtra("MOVIMENTSPEED");

        if(opcao.equals("atualizar")) {
            edNomeHeroi.setHint(nome);
            txtClasse.setHint(classe);
            edHealthPoints.setHint(healthpoints);
            edDefense.setHint(defense);
            edDamage.setHint(damage);
            edAttackSpeed.setHint(speedattack);
            edMovimentSpeed.setHint(movimentspeed);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //GsonConverterFactory para converter diretamente json data para objeto
                .build();

        api = retrofit.create(Api.class);

        pd = new ProgressDialog(InserirActivity.this);
        pd.setMessage("carregando");
        pd.show();

        //Pegar Valores de Classe
        pegarClasses();


        //FUNCOES DOS BOTOES
        btnProximaTela.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProximaTela();
            }
        });

        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contador == 0) {
                    contador = maxNumClassedoArray;
                    txtClasse.setText(classesArray[contador]);

                    //ARRAY COMECA EM 0 E ID COMECA EM 1 ENTAO
                    idClasse = contador +1;
                    return;
                }

                contador = contador - 1;
                txtClasse.setText(classesArray[contador]);

                //ARRAY COMECA EM 0 E ID COMECA EM 1 ENTAO
                idClasse = contador + 1;

                System.out.println(idClasse);
                System.out.println(contador);
                System.out.println(maxNumClasse);
            }
        });

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(contador == maxNumClassedoArray) {
                    contador = 0;
                    txtClasse.setText(classesArray[contador]);

                    //ARRAY COMECA EM 0 E ID COMECA EM 1 ENTAO
                    idClasse = contador + 1;
                    return;
                }


                contador = contador +1;
                txtClasse.setText(classesArray[contador]);

                //ARRAY COMECA EM 0 E ID COMECA EM 1 ENTAO
                idClasse = contador + 1;

                System.out.println("ID Classe"+idClasse);
                System.out.println("Contador"+contador);
                System.out.println("Numero max"+maxNumClasse);
            }
        });

    }

    public void ProximaTela(){
        Intent intent = new Intent(InserirActivity.this,SelecionarMagiaActivity.class);

        if(edNomeHeroi.getText().toString().equals("")){
            Toast.makeText(InserirActivity.this,"Preencha todods os campos",Toast.LENGTH_LONG).show();
            return;
        }else if(edHealthPoints.getText().toString().equals("")){
            Toast.makeText(InserirActivity.this,"Preencha todods os campos",Toast.LENGTH_LONG).show();
            return;
        }else if(edDefense.getText().toString().equals("")){
            Toast.makeText(InserirActivity.this,"Preencha todods os campos",Toast.LENGTH_LONG).show();
            return;
        }else if(edDamage.getText().toString().equals("")){
            Toast.makeText(InserirActivity.this,"Preencha todods os campos",Toast.LENGTH_LONG).show();
            return;
        }else if(edAttackSpeed.getText().toString().equals("")){
            Toast.makeText(InserirActivity.this,"Preencha todods os campos",Toast.LENGTH_LONG).show();
            return;
        }else if(edMovimentSpeed.getText().toString().equals("")){
            Toast.makeText(InserirActivity.this,"Preencha todods os campos",Toast.LENGTH_LONG).show();
            return;
        }


        intent.putExtra("ID",id);
        intent.putExtra("NOMEHEROI",edNomeHeroi.getText().toString());
        intent.putExtra("IDCLASSE",idClasse);
        intent.putExtra("HEALTHPOINTS",Integer.parseInt(edHealthPoints.getText().toString()));
        intent.putExtra("DEFENSE",Integer.parseInt(edDefense.getText().toString()));
        intent.putExtra("DAMAGE",Integer.parseInt(edDamage.getText().toString()));
        intent.putExtra("ATTACKSPEED",Double.parseDouble(edAttackSpeed.getText().toString()));
        intent.putExtra("MOVIMENTSPEED",Integer.parseInt(edMovimentSpeed.getText().toString()));
        intent.putExtra("OPCAO",opcao);
        startActivity(intent);

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
                pd.dismiss();

                //Pega todos os resultados
                List<Classes> listaClasses = response.body();

                //String array para a listView
                maxNumClasse = listaClasses.size();

                maxNumClassedoArray = maxNumClasse - 1;
                classesArray = new String[listaClasses.size()];

                //Inserir todos no array
                for (int i = 0; i < listaClasses.size(); i++) {
                    classesArray[i] = listaClasses.get(i).getName();
                }

                //mostrar a primeira classe do array no textView
                txtClasse.setText(classesArray[0]);

                //Id classe definida caso clique no botão inserir
                idClasse = 1;
            }

            @Override
            public void onFailure(Call<List<Classes>> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


}
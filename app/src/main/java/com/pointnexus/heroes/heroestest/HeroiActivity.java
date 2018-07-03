package com.pointnexus.heroes.heroestest;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Body;

public class HeroiActivity extends AppCompatActivity {

    ListView listView;
    Button btnProcurar;
    TextView responseText;
    EditText editText;
    Api api;

    TextView txtId;
    TextView txtNome;
    TextView txtClasse;
    TextView txtHealthPoints;
    TextView txtDefesa;
    TextView txtDano;
    TextView txtVelocidadeAtaque;
    TextView txtVelocidadeMovimento;

    Button btnAnterior;
    Button btnProximo;
    Button btnDeletar;
    Button btnAtualizar;

    int contadorId;
    int maxNumId;
    int maxNumIdArray;

    int idUpdateDelete;
    int [] id;
    String[] nome;
    String[] classe;
    int [] healthPoints;
    int [] defesa;
    int [] dano;
    double [] velocidadeAtaque;
    int [] velocidadeMovimento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_heroi);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //GsonConverterFactory para converter diretamente json data para objeto
                .build();

        api = retrofit.create(Api.class);

        //Instancia Botoes
        btnProcurar = (Button) findViewById(R.id.main_btn_lookup);
        editText = (EditText) findViewById(R.id.main_edit_username);


        txtId = (TextView)findViewById(R.id.txtId);
        txtNome = (TextView)findViewById(R.id.txtNome);
        txtClasse = (TextView)findViewById(R.id.txtClasse);
        txtHealthPoints = (TextView)findViewById(R.id.txtHealthPoints);
        txtDefesa = (TextView)findViewById(R.id.txtDefesa);
        txtDano = (TextView)findViewById(R.id.txtDano);
        txtVelocidadeAtaque = (TextView)findViewById(R.id.txtVelocidadeAtaque);
        txtVelocidadeMovimento = (TextView)findViewById(R.id.txtVelocidadeMovimento);


        btnAnterior = (Button) findViewById(R.id.btnAnterior);
        btnProximo = (Button) findViewById(R.id.btnProximo);
        btnDeletar = (Button) findViewById(R.id.btnDeletar);
        btnAtualizar = (Button) findViewById(R.id.btnAtualizar);

        btnAtualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HeroiActivity.this, AtualizarActivity.class);
                intent.putExtra("ID",txtId.getText().toString());
                intent.putExtra("NOME",txtNome.getText().toString());
                intent.putExtra("CLASSE",txtClasse.getText().toString());
                intent.putExtra("HEALTHPOINTS",txtHealthPoints.getText().toString());
                intent.putExtra("DEFENSE",txtDefesa.getText().toString());
                intent.putExtra("DAMAGE",txtDano.getText().toString());
                intent.putExtra("SPEEDATTACK",txtVelocidadeAtaque.getText().toString());
                intent.putExtra("MOVIMENTSPEED",txtVelocidadeMovimento.getText().toString());

                finish();
                startActivity(intent);
            }
        });

        btnDeletar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletar();
            }
        });

        btnAnterior.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(contadorId == 0) {
                    contadorId = maxNumIdArray;

                    darValores();

                    //Define valor que vai ser passado
                    //idClasse = contadorClasse +1;
                    return;
                }

                contadorId = contadorId - 1;

                darValores();

                //Define valor que vai ser passado
                //idClasse = contadorClasse + 1;

                //System.out.println(idClasse);
                System.out.println(contadorId);
                System.out.println(maxNumId);

            }
        });

        btnProximo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //If para voltar ao primeiro id caso acabe a lista
                if(contadorId == maxNumIdArray) {
                    contadorId = 0;

                    darValores();

                    //Define valor que vai ser passado
                    //idClasse = contadorClasse + 1;
                    return;
                }

                contadorId = contadorId +1;

                darValores();

                //Define valor que vai ser passado
                //idClasse = contadorClasse + 1;


            }
        });

        btnProcurar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                procurarHerois();
            }
        });
        //ALIMENTA LISTVIEW
        pegarHerois();
    }

    public void procurarHerois() {
        //PEGA ID DIGITADO
        String heroiId = editText.getText().toString();

        //CHAMA RESQUEST DA API PARA PEGAR RESPONSE COM ID DEFINIDO
        Call<Heroi> call = api.pegarHerois(heroiId);

        call.enqueue(new Callback<Heroi>() {
            @Override
            public void onResponse(Call<Heroi> call, Response<Heroi> response) {
                //MOSTRA RESULTADOS
                Heroi heroipego = response.body();
                if (heroipego != null) {
                    txtId.setText(String.valueOf(heroipego.getId()));
                    idUpdateDelete = Integer.parseInt(txtId.getText().toString());
                    txtNome.setText(heroipego.getName());
                    txtClasse.setText(heroipego.getClassName());
                    txtHealthPoints.setText(String.valueOf(heroipego.getHealthPoints()));
                    txtDefesa.setText(String.valueOf(heroipego.getDefense()));
                    txtDano.setText(String.valueOf(heroipego.getDamage()));
                    txtVelocidadeAtaque.setText(String.valueOf(heroipego.getAttackSpeed()));
                    txtVelocidadeMovimento.setText(String.valueOf(heroipego.getMovimentSpeed()));
                } else {
                    txtId.setText("bugo");
                }
            }

            @Override
            public void onFailure(Call<Heroi> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Metodo pegar Herois
    private void pegarHerois() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //GsonConverterFactory para converter diretamente json data para objeto
                .build();

        Api api = retrofit.create(Api.class);

        //chama metodo da api para pegar response
        Call<List<Heroi>> call = api.pegarHerois();


        call.enqueue(new Callback<List<Heroi>>() {
            @Override
            public void onResponse(Call<List<Heroi>> call, Response<List<Heroi>> response) {
                //Pega todos os resultados
                List<Heroi> listaHerois = response.body();

                maxNumId = listaHerois.size();
                maxNumIdArray = maxNumId - 1;

                //String array para a listView
                id = new int[listaHerois.size()];
                nome = new String[listaHerois.size()];
                classe = new String[listaHerois.size()];
                healthPoints = new int[listaHerois.size()];
                defesa = new int[listaHerois.size()];
                dano = new int[listaHerois.size()];
                velocidadeAtaque = new double[listaHerois.size()];
                velocidadeMovimento = new int[listaHerois.size()];

                //Inserir todos no array
                for (int i = 0; i < listaHerois.size(); i++) {
                    id[i] = listaHerois.get(i).getId();
                    nome[i] = listaHerois.get(i).getName();
                    classe[i] = listaHerois.get(i).getClassName();
                    healthPoints[i] = listaHerois.get(i).getHealthPoints();
                    defesa[i] = listaHerois.get(i).getDefense();
                    dano[i] = listaHerois.get(i).getDamage();
                    velocidadeMovimento[i] = listaHerois.get(i).getMovimentSpeed();
                    velocidadeAtaque[i] = listaHerois.get(i).getAttackSpeed();
                }

                //mostrar array no listview
                txtId.setText(String.valueOf(id[0]));
                idUpdateDelete = (id[0]);
                txtNome.setText(nome[0]);
                txtClasse.setText(classe[0]);
                txtHealthPoints.setText(String.valueOf(healthPoints[0]));
                txtDefesa.setText(String.valueOf(defesa[0]));
                txtDano.setText(String.valueOf(dano[0]));
                txtVelocidadeAtaque.setText(String.valueOf(velocidadeAtaque[0]));
                txtVelocidadeMovimento.setText(String.valueOf(velocidadeMovimento[0]));

            }

            @Override
            public void onFailure(Call<List<Heroi>> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    //Metodo deletar heroi
    private void deletar() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()) //GsonConverterFactory para converter diretamente json data para objeto
                .build();

        Api api = retrofit.create(Api.class);

        //chama metodo da api para pegar response
        Call<Heroi> call = api.deletarHeroi(String.valueOf(idUpdateDelete));

        call.enqueue(new Callback<Heroi>() {
            @Override
            public void onResponse(Call<Heroi> call, Response<Heroi> response) {
                finish();
                Intent intent = new Intent(HeroiActivity.this,HeroiActivity.class);
                startActivity(intent);

            }

            @Override
            public void onFailure(Call<Heroi> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void darValores(){
        txtId.setText(String.valueOf(id[contadorId]));
        idUpdateDelete = (id[contadorId]);
        txtNome.setText(nome[contadorId]);
        txtClasse.setText(classe[contadorId]);
        txtHealthPoints.setText(String.valueOf(healthPoints[contadorId]));
        txtDefesa.setText(String.valueOf(defesa[contadorId]));
        txtDano.setText(String.valueOf(dano[contadorId]));
        txtVelocidadeAtaque.setText(String.valueOf(velocidadeAtaque[contadorId]));
        txtVelocidadeMovimento.setText(String.valueOf(velocidadeMovimento[contadorId]));
    }
}

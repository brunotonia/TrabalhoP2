package com.example.angelo.trabalhop2;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ListView;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

public class CalendarioActivity extends Activity {

    /* Variáveis de BD */
    private TreinoDbHelper base;
    private SQLiteDatabase db;
    /* Variáveis de Tela */
    private EditText exercicio, repeticao, carga, intervalo;
    private CalendarView calendario;
    private ListView listView;
    private ArrayAdapter<String> arrayAdapter;
    /* Variáveis Globais */
    private Intent it = null;
    private Bundle params = null;
    private Usuario usuario;
    private Long data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_calendario);

        /*Recuperando informações do usuário da tela de login */
        recuperarParams();

        /* Inicializando interface */
        base = new TreinoDbHelper(getApplicationContext());
        exercicio = (EditText)findViewById(R.id.edExercicio);
        repeticao = (EditText)findViewById(R.id.edRepeticao);
        carga = (EditText)findViewById(R.id.edCarga);
        intervalo = (EditText)findViewById(R.id.edIntervalo);

        final CalendarView calendario;

        calendario = (CalendarView)findViewById(R.id.calendarView);
        calendario.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override
            public void onSelectedDayChange(@NonNull final CalendarView view, int year, int month, int dayOfMonth) {
                AlertDialog.Builder builder = new AlertDialog.Builder(CalendarioActivity.this);
                CharSequence[]itens = new  CharSequence[3];
                itens[0]="Novo exercício";
                itens[1]="Consulta treino";
                itens[2]="Cancelar";

                /*final int dia, mes, ano;
                dia = dayOfMonth;
                mes = month+1;
                ano = year;*/

                builder.setTitle("Selecione uma opção").setItems(itens, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // carrega valor da data
                        data = calendario.getDate();
                        // usa um switch fica melhor o código
                        switch (which) {
                            case 0:
                                ir_addActivity(CalendarioActivity.this);
                                break;
                            case 1:
                                visualizarTreino(view);
                                break;
                            case 2:
                                break;
                        }

                        /*if (which==0){
                            Intent intent = new Intent(getApplicationContext(), AddActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putInt("dia", dia);
                            bundle.putInt("mes", mes);
                            bundle.putInt("ano", ano);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }if (which==1){
                            visualizarTreino(view);
                        } else{
                            return;
                        }*/
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
                // um toast pra ver o "id" da data, vai ter que refazer as tabelas com um long contendo a data
                Toast.makeText(CalendarioActivity.this,
                        new Long(calendario.getDate()).toString(), Toast.LENGTH_LONG).show();
            }
        });
    }

    /* Visualizar Treino */
    public void visualizarTreino(View view){
        ListView lista = (ListView) findViewById(R.id.ltvListaTreino);
        // TODO alterar função base.consultarTreino()
        ArrayAdapter<Treino> arrayAd = new ArrayAdapter<Treino>(getApplicationContext(),
                android.R.layout.simple_list_item_1, base.consultarTreino());
        lista.setAdapter(arrayAd);
    }

    /* Recuperar params */
    private void recuperarParams() {
        it = getIntent();
        params = it.getExtras();
        usuario = new Usuario(params.getLong("usuarioId"), params.getString("usuarioNome"),
                params.getString("usuarioLogin"), params.getString("usuarioSenha"));
    }

    /* Carregar params */
    private void carregarParams() {
        params = new Bundle();
        /* carregando paramentros de uma tela a outra */
        params.putLong("usuarioId", usuario.getId());
        params.putString("usuarioNome", usuario.getNome());
        params.putString("usuarioLogin", usuario.getLogin());
        params.putString("usuarioSenha", usuario.getSenha());
        /* Adicionar data para cadastrar e buscar */
        params.putLong("treinoData", data);
    }

    private void ir_addActivity (Context context) {
        carregarParams();
        /* iniciando nova tela*/
        Intent iAddActivity = new Intent(this, AddActivity.class);
        iAddActivity.putExtras(params);
        startActivity(iAddActivity);
    }

}

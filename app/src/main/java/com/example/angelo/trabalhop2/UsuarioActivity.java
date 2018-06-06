package com.example.angelo.trabalhop2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

/* Simplificando a classe */
// de:
//public class UsuarioActivity extends AppCompatActivity implements View.OnClickListener {
// para:
public class UsuarioActivity extends Activity {

    //private DatabaseHelper base;
    private EditText nome, login, senha;
    private Button salvar, cancelar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usuario);

        /* Inicializa BD */
        // base = new DatabaseHelper(getApplicationContext());
        // não preciso iniciar o banco aqui, no método salvar fica melhor

        /* Inicializa Elementos de Tela */
        nome = (EditText)findViewById(R.id.edNome);
        login = (EditText)findViewById(R.id.edLogin);
        senha = (EditText)findViewById(R.id.edSenha);
        salvar = (Button)findViewById(R.id.btSalvar);
        cancelar = (Button)findViewById(R.id.btCancelar);

        /* Listener do Botão Salvar */
        //salvar.setOnClickListener(this);
        salvar.setOnClickListener(new View.OnClickListener() {
                                      @Override
                                      public void onClick(View v) {
                                          salvar(UsuarioActivity.this);
                                      }
        });

        /* Listener do Botão Cancelar */
        //cancelar.setOnClickListener(this);
        cancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelar(UsuarioActivity.this);
            }
        });
    }

    private void salvar(Context context) {
        Usuario usuario = new Usuario(nome.getText().toString(), login.getText().toString(),senha.getText().toString());
        try {
            DatabaseHelper base = new DatabaseHelper(getApplicationContext());
            boolean resposta = base.salvarUsuario(usuario);
            if (resposta) {
                /* Exibir Mensagem */
                Toast.makeText(context, "Usuário cadastrado com sucesso", Toast.LENGTH_LONG).show();
                /* Usuário salvo, voltar a tela de login */
                retornar_login(this);
            } else {
                Toast.makeText(context, "Não foi possível cadastrar usuário", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void cancelar (Context context) {
        /* Voltar a tela de login */
        retornar_login(this);
    }

    private void retornar_login (Context context) {
        Bundle params = new Bundle(); // usa-se pra passar parametros de uma tela pra outra
        Intent iLogin = new Intent(this, LoginActivity.class);
        iLogin.putExtras(params);
        startActivity(iLogin);
    }

    /*
    public void salvarUsuario(View view){
        Usuario usuario = new Usuario(nome.getText().toString(), login.getText().toString(),senha.getText().toString());
        base.salvarUsuario(usuario);
        nome.setText("");
        login.setText("");
        senha.setText("");

    }*/

    /*@Override
    public void onClick(View view) {
        if (view.getId()==salvar.getId()){
            Usuario usuario = new Usuario(nome.getText().toString(), login.getText().toString(),senha.getText().toString());
            base.salvarUsuario(usuario);
            nome.setText("");
            login.setText("");
            senha.setText("");
            Toast.makeText(context, "Usuário cadastrado.", Toast.LENGTH_LONG).show();
        }else {
            this.finish();
            return;
        }
    }*/
}

package com.example.angelo.trabalhop2;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

    /* Ainda não achei pq inicalizar aqui */
    //private UsuarioDbHelper base;
    //private SQLiteDatabase db;
    private Button entrar, novo;
    private EditText edUsuario, edSenha;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* Inicializa Elementos de Tela */
        entrar = (Button)findViewById(R.id.btEntrar);
        novo = (Button)findViewById(R.id.btNovo);
        edUsuario = (EditText)findViewById(R.id.edUsuario);
        edSenha = (EditText)findViewById(R.id.edSenha);

        /* Listener botão de Novo Usuário */
        novo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(),UsuarioActivity.class);
                startActivity(intent);
            }
        });

        /* Listener botão Entrar */
        entrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                entrar(LoginActivity.this, edUsuario.getText().toString(), edSenha.getText().toString());
            }
        });
    }

    private void entrar (Context context, String login, String senha) {
        try {
            Usuario usuario = new UsuarioDbHelper(context).login(login, senha);
            Toast.makeText(context, "id " + usuario.getId() + "!", Toast.LENGTH_LONG).show();
            if (usuario.getId() != -1L) {
                Toast.makeText(context, "Bem Vindo " + usuario.getNome() + "!", Toast.LENGTH_LONG).show();

            } else {
                Toast.makeText(context, "Usuário ou Senha incorretos", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            //Toast.makeText(context, "Erro - Não foi possível logar", Toast.LENGTH_LONG).show();

        }
    }

    /* simplificando e colocando no listener */
    /*public void novoUsuario(Conten content){
        Intent intent = new Intent(getApplicationContext(),UsuarioActivity.class);
        startActivity(intent);
    }*/
}

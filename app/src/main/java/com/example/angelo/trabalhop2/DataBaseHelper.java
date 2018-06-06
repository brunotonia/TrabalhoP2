package com.example.angelo.trabalhop2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Treino.db";

    public DataBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Criando e povoando tabela usuário_categoria
        db.execSQL(UsuarioCategoriaConstantes.CREATE_TABLE);
        db.execSQL(UsuarioCategoriaConstantes.INSERT_CATEGORIA1);
        db.execSQL(UsuarioCategoriaConstantes.INSERT_CATEGORIA2);
        // Criando tabela usuário
        db.execSQL(UsuarioConstantes.CREATE_TABLE);
        // Criando e povoando tabela treino_categoria
        db.execSQL(TreinoCategoriaConstantes.CREATE_TABLE);
        db.execSQL(TreinoCategoriaConstantes.INSERT_CATEGORIA1);
        db.execSQL(TreinoCategoriaConstantes.INSERT_CATEGORIA2);
        db.execSQL(TreinoCategoriaConstantes.INSERT_CATEGORIA3);
        // Criando tabela treino
        db.execSQL(TreinoConstantes.CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(UsuarioConstantes.DROP_TABLE);
        db.execSQL(UsuarioCategoriaConstantes.DROP_TABLE);
        db.execSQL(TreinoConstantes.DROP_TABLE);
        db.execSQL(TreinoCategoriaConstantes.DROP_TABLE);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public Usuario login(String login, String senha) {
        /* Variáveis do BD */
        SQLiteDatabase db = this.getWritableDatabase();
        Usuario usuario = new Usuario(-1L, new UsuarioCategoria(-1L, "erro"),
                "erro", "erro", "senha");
        String colunas[] = {UsuarioConstantes.COLUMN_ID, UsuarioConstantes.COLUMN_CATEGORIA,
                UsuarioConstantes.COLUMN_NOME, UsuarioConstantes.COLUMN_LOGIN,
                UsuarioConstantes.COLUMN_SENHA};
        String selecao = UsuarioConstantes.COLUMN_LOGIN + " =? AND " +
                UsuarioConstantes.COLUMN_SENHA + " =? ";
        String valores[] = {login,senha};

        /* Realizando busca no banco de dados */
        Cursor cursor =  db.query(UsuarioConstantes.TABLE_NOME, colunas, selecao, valores,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            usuario.setId(cursor.getLong(cursor.getColumnIndex(UsuarioConstantes.COLUMN_ID)));
            usuario.setCategoria(buscaUsuarioCategoria(cursor.getLong(cursor.getColumnIndex(UsuarioConstantes.COLUMN_CATEGORIA))));
            usuario.setNome(cursor.getString(cursor.getColumnIndex(UsuarioConstantes.COLUMN_NOME)));
            usuario.setLogin(cursor.getString(cursor.getColumnIndex(UsuarioConstantes.COLUMN_LOGIN)));
            usuario.setSenha(cursor.getString(cursor.getColumnIndex(UsuarioConstantes.COLUMN_SENHA)));
        }
        return usuario;
    }

    public UsuarioCategoria buscaUsuarioCategoria (Long id) {
        /* Variáveis do BD */
        SQLiteDatabase db = this.getWritableDatabase();
        UsuarioCategoria categoria = new UsuarioCategoria(-1L, "erro");

        String colunas[] = {UsuarioCategoriaConstantes.COLUMN_ID, UsuarioCategoriaConstantes.COLUMN_CATEGORIA};
        String selecao = UsuarioCategoriaConstantes.COLUMN_ID + " =? ";
        String valores[] = {id.toString()};

        /* Realizando busca no banco de dados */
        Cursor cursor =  db.query(UsuarioConstantes.TABLE_NOME, colunas, selecao, valores,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            categoria.setId(cursor.getLong(cursor.getColumnIndex(UsuarioCategoriaConstantes.COLUMN_ID)));
            categoria.setCategoria(cursor.getString(
                    cursor.getColumnIndex(UsuarioCategoriaConstantes.COLUMN_CATEGORIA)));;
        }
        return categoria;
    }

}

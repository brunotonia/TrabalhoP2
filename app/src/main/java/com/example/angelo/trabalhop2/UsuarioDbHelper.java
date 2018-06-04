package com.example.angelo.trabalhop2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class UsuarioDbHelper extends SQLiteOpenHelper {
    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "Usuario.db";

    private static final String CREATEUSUARIO = "create table " + TreinoContract.UsuarioDb.TABLE_NAME + "( "
            + TreinoContract.UsuarioDb._ID + " integer primary key autoincrement, "
            + TreinoContract.UsuarioDb.COLUMN_NOME + " text, "
            + TreinoContract.UsuarioDb.COLUMN_LOGIN + " text, "
            + TreinoContract.UsuarioDb.COLUMN_SENHA + " text)";

    private static final String DELETEUSUARIO = "drop table if exists " + TreinoContract.UsuarioDb.TABLE_NAME;

    public UsuarioDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    /*private static final String CREATECATEGORIA = "create table " + TreinoContract.CategoriaDb.TABLE_NAME + "( "
            + TreinoContract.CategoriaDb._ID + " integer primary key autoincrement, "
            + TreinoContract.CategoriaDb.COLUMN_DESCRICAO + " text)";
    private static final String DELETECATEGORIA = "drop table if exists " + TreinoContract.CategoriaDb.TABLE_NAME;*/

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATEUSUARIO);
        //db.execSQL(CREATECATEGORIA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(DELETEUSUARIO);
        //db.execSQL(DELETECATEGORIA);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onDowngrade(db, oldVersion, newVersion);
    }

    public boolean salvarUsuario(Usuario usuario) {
        /* Variáveis do BD */
        SQLiteDatabase db = this.getWritableDatabase();
        /* Parametros do banco de dados */
        ContentValues contentValues = new ContentValues();
        contentValues.put(TreinoContract.UsuarioDb.COLUMN_NOME, usuario.getNome());
        contentValues.put(TreinoContract.UsuarioDb.COLUMN_LOGIN, usuario.getLogin());
        contentValues.put(TreinoContract.UsuarioDb.COLUMN_SENHA, usuario.getSenha());
        /* Inserindo no Banco de Dados */
        Long l = db.insert(TreinoContract.UsuarioDb.TABLE_NAME, null, contentValues);

        return (l != -1L);
    }

    public Usuario login(String login, String senha) {
        /* Variáveis do BD */
        SQLiteDatabase db = this.getWritableDatabase();

        /* Variáveis do Método*/
        // criando usuário com valor incorreto para erro
        Usuario usuario = new Usuario(-1L, "erro", "erro", "erro");

        String colunas[] = {TreinoContract.UsuarioDb._ID, TreinoContract.UsuarioDb.COLUMN_NOME,
                TreinoContract.UsuarioDb.COLUMN_LOGIN, TreinoContract.UsuarioDb.COLUMN_SENHA};
        String selecao = TreinoContract.UsuarioDb.COLUMN_LOGIN + " =? AND " + TreinoContract.UsuarioDb.COLUMN_SENHA + " =? ";
        String valores[] = {login,senha};

        /* Realizando busca no banco de dados */
        Cursor cursor =  db.query(TreinoContract.UsuarioDb.TABLE_NAME, colunas, selecao, valores,
                null, null, null, null);
        if (cursor.moveToFirst()) {
            usuario.setId(cursor.getLong(cursor.getColumnIndex(TreinoContract.UsuarioDb._ID)));
            usuario.setNome(cursor.getString(cursor.getColumnIndex(TreinoContract.UsuarioDb.COLUMN_NOME)));
            usuario.setLogin(cursor.getString(cursor.getColumnIndex(TreinoContract.UsuarioDb.COLUMN_LOGIN)));
            usuario.setSenha(cursor.getString(cursor.getColumnIndex(TreinoContract.UsuarioDb.COLUMN_SENHA)));
        }
        return usuario;
    }


}

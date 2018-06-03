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

    public boolean salvarUsuario(Usuario usuario){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(TreinoContract.UsuarioDb.COLUMN_NOME, usuario.getNome());
        contentValues.put(TreinoContract.UsuarioDb.COLUMN_LOGIN, usuario.getLogin());
        contentValues.put(TreinoContract.UsuarioDb.COLUMN_SENHA, usuario.getSenha());
        long id = db.insert(TreinoContract.TreinoDb.TABLE_NAME, null, contentValues);
        usuario.setId(id);
        return true;
    }

    public Usuario login(String login, String senha) {
        /* Variáveis do BD */
        SQLiteDatabase db = this.getWritableDatabase();

        /* Variáveis do Método*/
        // criando usuário com valor incorreto para erro
        Usuario usuario = new Usuario(-1L, "erro", "erro", "erro");

        String colunas[] = {"_ID", "COLUMN_NOME", "COLUMN_LOGIN", "COLUMN_SENHA"};
        String selecao = "COLUMN_LOGIN" + " =? AND " + "COLUMN_SENHA" + " =? ";
        String valores[] = {login,senha};

        /* Realizando busca no banco de dados */
        Cursor cursor =  db.query(TreinoContract.UsuarioDb.TABLE_NAME, colunas, selecao, valores, null, null, null, null);
        if (cursor.moveToFirst()) {
            usuario.setId(cursor.getLong(cursor.getColumnIndex("_ID")));
            usuario.setNome(cursor.getString(cursor.getColumnIndex("COLUMN_NOME")));
            usuario.setLogin(cursor.getString(cursor.getColumnIndex("COLUMN_LOGIN")));
            usuario.setNome(cursor.getString(cursor.getColumnIndex("COLUMN_SENHA")));
        }

        return usuario;
    }


}

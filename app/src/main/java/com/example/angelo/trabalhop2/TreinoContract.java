package com.example.angelo.trabalhop2;

import android.provider.BaseColumns;

public class TreinoContract {

    public TreinoContract(){
    }

    public static abstract class UsuarioCategoriaDb implements BaseColumns {
        public static final String TABLE_NAME = "usuarioCategoria";
        public static final String _ID = "id";
        public static final String COLUMN_DESCRICAO = "descricao";
    }

    public static abstract class UsuarioDb implements BaseColumns {
        public static final String TABLE_NAME = "usuario";
        public static final String COLUMN_NOME = "nome";
        public static final String COLUMN_LOGIN = "login";
        public static final String COLUMN_SENHA = "senha";
        public static final String COLUMN_CATEGORIA = "categoria";
    }



    public static abstract class TreinoDb implements BaseColumns {
        public static final String TABLE_NAME = "treino";
        public static final String COLUMN_EXERCICIO = "exercicio";
        public static final String COLUMN_REPETICAO = "repeticao";
        public static final String COLUMN_CARGA = "carga";
        public static final String COLUMN_INTERVALO = "intervalo";
        public static final String COLUMN_USUARIO = "usuario";
        public static final String COLUMN_DATA = "data";
    }



    public static abstract class CategoriaDb implements BaseColumns {
        public static final String TABLE_NAME = "categoria";
        public static final String COLUMN_DESCRICAO = "descrição";
    }
}
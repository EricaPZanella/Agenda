package com.example.agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDados extends SQLiteOpenHelper {

    private static final int VERSAO_BANCO = 1;
    private static final String BANCO_CONTATO = "bd_contatos";

    private static final String TABELA_CONTATO = "tb_contatos";

    private static final String COLUNA_CODIGO = "codigo";
    private static final String COLUNA_NOME = "nome";
    private static final String COLUNA_TELEFONE = "telefone";
    private static final String COLUNA_EMAIL = "email";


    public BancoDados(Context context) {
        super(context, BANCO_CONTATO, null, VERSAO_BANCO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String QUERY_COLUNA = "CREATE TABLE " + TABELA_CONTATO + "("
                + COLUNA_CODIGO + " INTEGER PRIMARY KEY, " + COLUNA_NOME + " TEXT, "
                + COLUNA_TELEFONE + " TEXT, " + COLUNA_EMAIL + " TEXT) ";

        db.execSQL(QUERY_COLUNA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*CRUD ABAIXO */
    void adicionarContato(Contato contato){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME, contato.getNome());
        values.put(COLUNA_TELEFONE, contato.getTelefone());
        values.put(COLUNA_EMAIL, contato.getEmail());

        db.insert(TABELA_CONTATO,null,values);
        db.close();

    }

    void excluirContato(Contato contato){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABELA_CONTATO, COLUNA_CODIGO + " = ?", new String[] {String.valueOf(contato.getCodigo())});
        db.close();
    }

}

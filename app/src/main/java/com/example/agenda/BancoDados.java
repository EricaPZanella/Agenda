package com.example.agenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

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

        String QUERY_COLUNA = "CREATE TABLE " + TABELA_CONTATO + " ("
                + COLUNA_CODIGO + " INTEGER PRIMARY KEY, " + COLUNA_NOME + " TEXT, "
                + COLUNA_TELEFONE + " TEXT, " + COLUNA_EMAIL + " TEXT) ";

        db.execSQL(QUERY_COLUNA);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    /*CRUD ABAIXO */
    public void adicionarContato(Contato contato){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME, contato.getNome());
        values.put(COLUNA_TELEFONE, contato.getTelefone());
        values.put(COLUNA_EMAIL, contato.getEmail());

        db.insert(TABELA_CONTATO,null,values);
        db.close();

    }

    public void excluirContato(Contato contato){
        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABELA_CONTATO, COLUNA_CODIGO + " = ?", new String[] {String.valueOf(contato.getCodigo())});
        db.close();
    }

    public Contato selecionarContato(int codigo){

        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = (Cursor) db.query(TABELA_CONTATO, new String[] {COLUNA_CODIGO,
        COLUNA_NOME, COLUNA_TELEFONE, COLUNA_EMAIL}, COLUNA_CODIGO + " = ?",
                new String[] {String.valueOf(codigo)}, null, null, null, null);

        if(cursor!= null){
            cursor.moveToFirst();
        }

        Contato contato = new Contato(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2), cursor.getString(3));

        return contato;
    }

    public void atualizarContato(Contato contato){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(COLUNA_NOME, contato.getNome());
        values.put(COLUNA_TELEFONE, contato.getTelefone());
        values.put(COLUNA_EMAIL, contato.getEmail());

        db.update(TABELA_CONTATO, values, COLUNA_CODIGO + " = ?",
                new String[] {String.valueOf(contato.getCodigo())});

    }

    public List <Contato> listarTodosContatos(){
        List<Contato> listaContato = new ArrayList<Contato>();

        String query = "SELECT * FROM " + TABELA_CONTATO;

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        if(cursor.moveToFirst()){
            do{
                Contato contato = new Contato();
                contato.setCodigo(Integer.parseInt(cursor.getString(0)));
                contato.setNome(cursor.getString(1));
                contato.setTelefone(cursor.getString(2));
                contato.setEmail(cursor.getString(3));

                listaContato.add(contato);
            }while(cursor.moveToNext());

        }

        return listaContato;
    }

}

package com.example.agenda;

import android.app.Service;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editCodigo, editNome, editTelefone, editEmail;
    Button btnLimpar, btnSalvar, btnExcluir;
    ListView listViewContatos;
    BancoDados bd = new BancoDados(this);

    ArrayAdapter<String> adapter;
    ArrayList<String> arrayList;
    InputMethodManager imm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editCodigo = (EditText)findViewById(R.id.editCodigo);
        editNome = (EditText)findViewById(R.id.editNome);
        editTelefone = (EditText)findViewById(R.id.editTelefone);
        editEmail = (EditText)findViewById(R.id.editEmail);

        btnLimpar = (Button)findViewById(R.id.btnLimpar);
        btnSalvar = (Button)findViewById(R.id.btnSalvar);
        btnExcluir = (Button)findViewById(R.id.btnExcluir);

        imm = (InputMethodManager) this.getSystemService(Service.INPUT_METHOD_SERVICE);

        listViewContatos = (ListView)findViewById(R.id.listViewClientes);

        listarContatos();

        btnLimpar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                limparCampos();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String codigo = editCodigo.getText().toString();
                String nome = editNome.getText().toString();
                String telefone = editTelefone.getText().toString();
                String email = editEmail.getText().toString();

                if(nome.isEmpty()){
                    editNome.setError("Este campo é obrigatório!");
                }else{
                    if(codigo.isEmpty()){
                        //insert
                    bd.adicionarContato(new Contato(nome, telefone, email));
                    Toast.makeText(MainActivity.this, "Contato salvo!", Toast.LENGTH_LONG).show();
                    limparCampos();
                    listarContatos();
                    escondeTeclado();

                    }else {
                        //update
                    bd.atualizarContato(new Contato(Integer.parseInt(codigo), nome, telefone, email));
                    Toast.makeText(MainActivity.this, "Contato modificado!", Toast.LENGTH_LONG).show();
                    limparCampos();
                    listarContatos();
                    escondeTeclado();

                    }
                }
            }

        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String codigo = editCodigo.getText().toString();

                if(codigo.isEmpty()){
                    Toast.makeText(MainActivity.this, "Nenhum contato selecionado!", Toast.LENGTH_LONG).show();
                }else{

                    Contato contato = new Contato();
                    contato.setCodigo(1);
                    bd.excluirContato(contato);
                    Toast.makeText(MainActivity.this, "Contato excluído!", Toast.LENGTH_LONG).show();
                    limparCampos();
                    listarContatos();

                }
            }
        });

        listViewContatos.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String conteudo = (String) listViewContatos.getItemAtPosition(position);

                //Toast.makeText(MainActivity.this, "Select: " + conteudo, Toast.LENGTH_LONG).show();
                String codigo = conteudo.substring(0, conteudo.indexOf(" - "));

                Contato contato = bd.selecionarContato(Integer.parseInt(codigo));

                editCodigo.setText(String.valueOf(contato.getCodigo()));
                editNome.setText(contato.getNome());
                editTelefone.setText(contato.getTelefone());
                editEmail.setText(contato.getEmail());
            }
        });



        //teste inserir
//        bd.adicionarContato(new Contato("Lea","47 999022084","leazanella@gmail.com"));
//        Toast.makeText(MainActivity.this, "Contato salvo!", Toast.LENGTH_LONG).show();

        //teste excluir
//        Contato contato = new Contato();
//        contato.setCodigo(1);
//        bd.excluirContato(contato);
//        Toast.makeText(MainActivity.this, "Contato excluído!", Toast.LENGTH_LONG).show();

        //teste lista contato
//        Contato contato = bd.selecionarContato(3);
//
//        Log.d("Contato selecionado", "Codigo: " + contato.getCodigo() + " | Nome: " + contato.getNome() +
//                " | Telefone: " + contato.getTelefone() + " | Email: " + contato.getEmail());


        //teste atualizar contato
//        Contato contato = new Contato();
//        contato.setCodigo(3);
//        contato.setNome("Lea mae");
//        contato.setTelefone("91685486");
//        contato.setEmail("lea@gmail.com");
//        bd.atualizarContato(contato);
//
//        Toast.makeText(MainActivity.this, "Contato atualizado!", Toast.LENGTH_LONG).show();
//        Log.d("Contato selecionado", "Codigo: " + contato.getCodigo() + " | Nome: " + contato.getNome() +
//                " | Telefone: " + contato.getTelefone() + " | Email: " + contato.getEmail());

    }

    public void escondeTeclado(){
        imm.hideSoftInputFromWindow(editNome.getWindowToken(),0);
    }

    public void limparCampos(){
        editCodigo.setText("");
        editNome.setText("");
        editTelefone.setText("");
        editEmail.setText("");

        editNome.requestFocus();

    }

    public void listarContatos(){

        List<Contato> contatos = bd.listarTodosContatos();

        arrayList = new ArrayList<String>();

        adapter = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrayList);

        listViewContatos.setAdapter(adapter);

        for(Contato c : contatos){
            //Log.d("Lista", "\n Cod.: " + c.getCodigo() + " | Nome: "
            //+ c.getNome());
            arrayList.add(c.getCodigo() + " - " + c.getNome() + " - " + c.getTelefone());
            adapter.notifyDataSetChanged();
        }
    }
}

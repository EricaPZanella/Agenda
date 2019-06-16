package com.example.agenda;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editCodigo, editNome, editTelefone, editEmail;
    Button btnLimpar, btnSalvar, btnExcluir;
    ListView listViewClientes;
    BancoDados bd = new BancoDados(this);

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

        listViewClientes = (ListView)findViewById(R.id.listViewClientes);

        //teste inserir
        //bd.adicionarContato(new Contato("Erica","47 999022083","ericapzanella@gmail.com"));
        //Toast.makeText(MainActivity.this, "Contato salvo!", Toast.LENGTH_LONG).show();

        //teste excluir
//        Contato contato = new Contato();
//        contato.setCodigo(1);
//        bd.excluirContato(contato);
//        Toast.makeText(MainActivity.this, "Contato excluído!", Toast.LENGTH_LONG).show();

    }
}

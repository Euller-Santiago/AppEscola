package br.com.etec.appescolacorrecao;

import android.content.DialogInterface;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class TelaCadastroProfessor extends AppCompatActivity {
    Button btnCadastra, btnLimpa, btnEdita;
    EditText edtNome, edtTelefone, edtFormacao, edtEmail, edtCpf;
    ListView lstProfessores;
    ProfessorDB banco;
    ArrayList<Professor> listaProfessores;
    ArrayAdapter<Professor> listaProfessorAdapter;
    Professor professorExclui;
    int idProfessor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro_professor);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cadastro Professor");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(
                    new ColorDrawable(
                            ContextCompat.getColor(this, R.color.pro)
                    )
            );

        }

        btnCadastra = findViewById(R.id.btnCadastrarProfessor);
        btnEdita = findViewById(R.id.btnEditarProfessor);
        btnLimpa = findViewById(R.id.btnLimparProfessor);


        edtNome = findViewById(R.id.edtNomeProfessor);
        edtTelefone = findViewById(R.id.edtTelefoneProfessor);
        edtFormacao = findViewById(R.id.edtFormacaoProfessor);
        edtEmail = findViewById(R.id.edtEmailProfessor);
        edtCpf = findViewById(R.id.edtCPFProfessor);

        btnEdita.setEnabled(false);
        lstProfessores = findViewById(R.id.lstProfessores);


        registerForContextMenu(lstProfessores);
        preencheLista();


        lstProfessores.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Professor objContato = listaProfessorAdapter.getItem(position);
                idProfessor = objContato.getId();
                edtNome.setText(objContato.getNome());
                edtTelefone.setText(objContato.getTelefone());
                edtFormacao.setText(objContato.getFormacao());
                edtEmail.setText(objContato.getEmail());
                edtCpf.setText(objContato.getCpf());

                btnEdita.setEnabled(true);
                btnCadastra.setEnabled(false);
            }
        });

        lstProfessores.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                professorExclui = listaProfessorAdapter.getItem(position);

                return false;
            }
        });


        btnCadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                cadastrarProfessor();
            }
        });


        btnEdita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                alterarProfessor();
            }
        });


        btnLimpa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                limpar();
            }
        });


    }

    public void limpar(){
        edtTelefone.setText(null);
        edtNome.setText(null);
        edtFormacao.setText(null);
        edtEmail.setText(null);
        edtCpf.setText(null);

        btnCadastra.setEnabled(true);
        btnEdita.setEnabled(false);

        edtNome.requestFocus();
    }

    public void preencheLista() {
        //método responsável por buscar informações no banco de dados
        //e apresentar na LISTVIEW onde o usuário pode visualizar as informações

        //zerando o arraylist
        listaProfessores = null;
        //criar um objeto da classe BDAGENDA
        banco = new ProfessorDB(TelaCadastroProfessor.this);
        //realizando uma consulta ao banco de dados e armazenando as informações
        //no arraylist listaContatos
        listaProfessores = banco.consultarProfessor();
        Log.e("teste", String.valueOf(listaProfessores));
        //fechando a conexão com o banco de dados
       // banco.close();
        //verifica se a listaContatos não está vazia, isto é,
        //se houve retorno de informações na consulta ao banco de dados
        //caso afirmativo, poderemos mostrar as informações na LISTVIEW(TELA)
        if (listaProfessores != null) {
            //vamos configurar o ADAPTER, objeto que prepara as informações no formato
            //necessario para ser inserido na LISTVIEW
            listaProfessorAdapter = new ArrayAdapter<>(
                    TelaCadastroProfessor.this,
                    android.R.layout.simple_list_item_1,
                    listaProfessores);
            //inserindo os dados que estão no ADAPTER para a LISTVIEW(TELA)
            lstProfessores.setAdapter(listaProfessorAdapter);
        }
    }

    public void alterarProfessor() {
        String nome = edtNome.getText().toString();
        String telefone = edtTelefone.getText().toString();
        String email = edtEmail.getText().toString();
        String formacao = edtFormacao.getText().toString();
        String cpf = edtCpf.getText().toString();

        //validando as informações
        if (nome.isEmpty()) {
            edtNome.setError("Preencher este campo");
        } else if (email.isEmpty()) {
            edtEmail.setError("Preencher este campo");
        } else if (telefone.isEmpty()) {
            edtTelefone.setError("Preencher este campo");
        }
        else if (cpf.isEmpty()) {
            edtCpf.setError("Preencher este campo");
        }
        else if (formacao.isEmpty()) {
            edtFormacao.setError("Preencher este campo");
        }else {
            Professor professor = new Professor();
            professor.setId(idProfessor);
            professor.setNome(nome);
            professor.setEmail(email);
            professor.setTelefone(telefone);
            professor.setFormacao(formacao);
            professor.setCpf(cpf);

            banco = new ProfessorDB(TelaCadastroProfessor.this);
            long resposta = banco.alterarCadastroProf(professor);
            Log.e("cad","Valor " + resposta);
            if (resposta == 1) {
                Toast.makeText(this, "Contato Alterado", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erro ao alterar", Toast.LENGTH_SHORT).show();
            }
            limpar();
            preencheLista();
            //banco.close();
        }
    }
    public void cadastrarProfessor() {

        String nome = edtNome.getText().toString();
        String email = edtEmail.getText().toString();
        String telefone = edtTelefone.getText().toString();
        String formacao = edtFormacao.getText().toString();
        String cpf = edtCpf.getText().toString();

        //validando as informações
        if (nome.isEmpty()) {
            edtNome.setError("Preencher este campo");
        } else if (telefone.isEmpty()) {
            edtTelefone.setError("Preencher este campo");
        } else if (email.isEmpty()) {
            edtEmail.setError("Preencher este campo");
        }else if (formacao.isEmpty()){
            edtFormacao.setText(" ");
            edtFormacao.setError("Preencher este campo");
        } else {
            Professor professor = new Professor();
            professor.setNome(nome);
            professor.setTelefone(telefone);
            professor.setEmail(email);
            professor.setFormacao(formacao);
            professor.setCpf(cpf);

            banco = new ProfessorDB(TelaCadastroProfessor.this);
            long resposta = banco.cadastrarProfessor(professor);
            Log.e("cad","Valor " + resposta);
            if (resposta >= 1) {
                Toast.makeText(this, "Contato Salvo", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Erro ao Salvar", Toast.LENGTH_SHORT).show();
            }
            limpar();
            preencheLista();
            //banco.close();
        }
    }
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuItem mDelete = menu.add("Apagar Registro");
        mDelete.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                banco = new ProfessorDB(TelaCadastroProfessor.this);
                AlertDialog.Builder msgExcluir = new AlertDialog.Builder(TelaCadastroProfessor.this);
                msgExcluir.setTitle("Excluir");
                msgExcluir.setMessage("Confirma exclusão do contato?");
                msgExcluir.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        long resposta = banco.excluirProfessor(professorExclui);
                       // banco.close();
                        if (resposta == -1){
                            Toast.makeText(TelaCadastroProfessor.this, "Erro ao Excluir!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TelaCadastroProfessor.this, "Contato Excluído!", Toast.LENGTH_SHORT).show();
                            preencheLista();
                        }
                    }
                });
                msgExcluir.setNegativeButton("Não",null);
                //controle para não fechar o alert clicando fora
                msgExcluir.setCancelable(false);
                //configurando o icone
                //msgExcluir.setIcon(getResources().getDrawable(R.drawable.icon_delete));//VECTOR ASSET
                msgExcluir.show();
                return false;
            }
        });
    }
}








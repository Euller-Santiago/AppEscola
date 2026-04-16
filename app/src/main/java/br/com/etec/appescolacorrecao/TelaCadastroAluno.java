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

public class TelaCadastroAluno extends AppCompatActivity {

    Button btnCadastra, btnLimpa, btnEdita;
    EditText edtNome, edtCidade, edtTurma, edtIdade, edtCpf;
    ListView lstAluno;
    AlunoDB banco;
    ArrayList<Aluno> listaAlunos;
    ArrayAdapter<Aluno> listaAlunoAdapter;
    Aluno alunoExclui;
    int idAluno;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_cadastro_aluno);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Cadastro aluno");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(
                    new ColorDrawable(
                            ContextCompat.getColor(this, R.color.alu)
                    )
            );
        }

        btnCadastra = findViewById(R.id.btnCadastrarAluno);
        btnEdita = findViewById(R.id.btnEditarAluno);
        btnLimpa = findViewById(R.id.btnLimparAluno);


        edtNome = findViewById(R.id.edtNomeAluno);
        edtCidade = findViewById(R.id.edtCidadeAluno);
        edtTurma = findViewById(R.id.edtTurmaAluno);
        edtIdade = findViewById(R.id.edtIdadeAluno);
        edtCpf = findViewById(R.id.edtCPFAluno);
        edtCpf.addTextChangedListener(MascaraCPF.maskcpf(edtCpf));

        btnEdita.setEnabled(false);
        lstAluno = findViewById(R.id.lstAluno);


        registerForContextMenu(lstAluno);
        preencheLista();


        lstAluno.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Aluno objAluno = listaAlunoAdapter.getItem(position);
                idAluno = objAluno.getId();
                edtNome.setText(objAluno.getNome());
                edtCidade.setText(objAluno.getCidade());
                edtTurma.setText(objAluno.getTurma());
                edtIdade.setText(objAluno.getIdade());
                edtCpf.setText(objAluno.getCpf());

                btnEdita.setEnabled(true);
                btnCadastra.setEnabled(false);
            }
        });

        lstAluno.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int position, long l) {

                alunoExclui = listaAlunoAdapter.getItem(position);

                return false;
            }
        });


        btnCadastra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                cadastrarAluno();
            }
        });


        btnEdita.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                alteraraluno();
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
        edtCidade.setText(null);
        edtNome.setText(null);
        edtTurma.setText(null);
        edtIdade.setText(null);
        edtCpf.setText(null);

        btnCadastra.setEnabled(true);
        btnEdita.setEnabled(false);

        edtNome.requestFocus();
    }

    public void preencheLista() {
        //método responsável por buscar informações no banco de dados
        //e apresentar na LISTVIEW onde o usuário pode visualizar as informações

        //zerando o arraylist
        listaAlunos = null;
        //criar um objeto da classe BDAGENDA
        banco = new AlunoDB(TelaCadastroAluno.this);
        //realizando uma consulta ao banco de dados e armazenando as informações
        //no arraylist listaContatos
        listaAlunos = banco.consultarAluno();
        Log.e("teste", String.valueOf(listaAlunos));
        //fechando a conexão com o banco de dados
        // banco.close();
        //verifica se a listaContatos não está vazia, isto é,
        //se houve retorno de informações na consulta ao banco de dados
        //caso afirmativo, poderemos mostrar as informações na LISTVIEW(TELA)
        if (listaAlunos != null) {
            //vamos configurar o ADAPTER, objeto que prepara as informações no formato
            //necessario para ser inserido na LISTVIEW
            listaAlunoAdapter = new ArrayAdapter<>(
                    TelaCadastroAluno.this,
                    android.R.layout.simple_list_item_1,
                    listaAlunos);
            //inserindo os dados que estão no ADAPTER para a LISTVIEW(TELA)
            lstAluno.setAdapter(listaAlunoAdapter);
        }
    }

    public void alteraraluno() {
        String nome = edtNome.getText().toString();
        String cidade = edtCidade.getText().toString();
        String idade = edtIdade.getText().toString();
        String turma = edtTurma.getText().toString();
        String cpf = edtCpf.getText().toString();

        //validando as informações
        if (nome.isEmpty()) {
            edtNome.setError("Preencher este campo");
        } else if (idade.isEmpty()) {
            edtIdade.setError("Preencher este campo");
        } else if (cidade.isEmpty()) {
            edtCidade.setError("Preencher este campo");
        }
        else if (cpf.isEmpty()) {
            edtCpf.setError("Preencher este campo");
        }
        else if (turma.isEmpty()) {
            edtTurma.setError("Preencher este campo");
        }else {
            Aluno aluno = new Aluno();
            aluno.setId(idAluno);
            aluno.setNome(nome);
            aluno.setIdade(idade);
            aluno.setCidade(cidade);
            aluno.setTurma(turma);
            aluno.setCpf(cpf);

            banco = new AlunoDB (TelaCadastroAluno.this);
            long resposta = banco.alterarCadastroAluno(aluno);
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
    public void cadastrarAluno() {

        String nome = edtNome.getText().toString();
        String cidade = edtCidade.getText().toString();
        String idade = edtIdade.getText().toString();
        String turma = edtTurma.getText().toString();
        String cpf = edtCpf.getText().toString();

        if (nome.isEmpty()) {
            edtNome.setError("Preencher este campo");
        } else if (idade.isEmpty()) {
            edtIdade.setError("Preencher este campo");
        } else if (cidade.isEmpty()) {
            edtCidade.setError("Preencher este campo");
        }
        else if (cpf.isEmpty()) {
            edtCpf.setError("Preencher este campo");
        }
        else if (turma.isEmpty()) {
            edtTurma.setError("Preencher este campo");
        }else {
            Aluno aluno = new Aluno();
            aluno.setNome(nome);
            aluno.setIdade(idade);
            aluno.setCidade(cidade);
            aluno.setTurma(turma);
            aluno.setCpf(cpf);

            banco = new AlunoDB(TelaCadastroAluno.this);
            long resposta = banco.cadastroAluno(aluno);
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
                banco = new AlunoDB(TelaCadastroAluno.this);
                AlertDialog.Builder msgExcluir = new AlertDialog.Builder(TelaCadastroAluno.this);
                msgExcluir.setTitle("Excluir");

                msgExcluir.setMessage("Confirma exclusão do contato?");
                msgExcluir.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        long resposta = banco.execluirAluno(alunoExclui);
                        // banco.close();
                        if (resposta == -1){
                            Toast.makeText(TelaCadastroAluno.this, "Erro ao Excluir!", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(TelaCadastroAluno.this, "Contato Excluído!", Toast.LENGTH_SHORT).show();
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
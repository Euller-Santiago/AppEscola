package br.com.etec.appescolacorrecao;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class TelaConsultaDinAluno extends AppCompatActivity {

    EditText edtConsultaDinAluno;
    ListView lstResultadosDinAlunos;
    ArrayList<Aluno> listaResultadosDinAlunos = new ArrayList<>();
    ArrayAdapter<Aluno> adapterResultadosDinAlunos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_consulta_din_aluno);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Consulta do nome do Aluno");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(
                    new ColorDrawable(
                            ContextCompat.getColor(this, R.color.pro)
                    )
            );

        }

        edtConsultaDinAluno = findViewById(R.id.edtConsultaDinAluno);
        lstResultadosDinAlunos = findViewById(R.id.lstResultadosDinAluno);



        edtConsultaDinAluno.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Aluno al = new Aluno();
                //armazenando o valor no objeto
                al.setNome(s.toString());
                //criando um objeto para realizar a pesquisa
                AlunoDB banco = new AlunoDB(TelaConsultaDinAluno.this);
                //executando a pesquisa e recebendo os dados
                listaResultadosDinAlunos = banco.consultaNome(al);
                adapterResultadosDinAlunos = new ArrayAdapter<>(TelaConsultaDinAluno.this, android.R.layout.simple_list_item_1,listaResultadosDinAlunos);
                lstResultadosDinAlunos.setAdapter(adapterResultadosDinAlunos);


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    }
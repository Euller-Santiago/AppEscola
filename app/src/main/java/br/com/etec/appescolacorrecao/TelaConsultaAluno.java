package br.com.etec.appescolacorrecao;

import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;

public class TelaConsultaAluno extends AppCompatActivity {

    EditText edtCPF;
    ListView lstResultadosALunos;
    Button btnCPF;
    ArrayList <Aluno> listaAlunos = new ArrayList<>();
    ArrayAdapter <Aluno> adapterAlunos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_tela_consulta_aluno);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Menu de Consulta do CPF do Aluno");

        if (getSupportActionBar() != null) {
            getSupportActionBar().setBackgroundDrawable(
                    new ColorDrawable(
                            ContextCompat.getColor(this, R.color.alu)
                    )
            );
        }
        edtCPF = findViewById(R.id.edtConsultaAluno);
        edtCPF.addTextChangedListener(MascaraCPF.maskcpf(edtCPF));
        lstResultadosALunos = findViewById(R.id.lstResultadosAluno);
        btnCPF = findViewById(R.id.btnBuscaAluno);

        btnCPF.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Aluno al = new Aluno();
                //armazenando o valor no objeto
                al.setCpf(edtCPF.getText().toString());
                //criando um objeto para realizar a pesquisa
                AlunoDB banco = new AlunoDB(TelaConsultaAluno.this);
                //executando a pesquisa e recebendo os dados
                listaAlunos = banco.consultaCPF(al);
                adapterAlunos = new ArrayAdapter<>(TelaConsultaAluno.this, android.R.layout.simple_list_item_1,listaAlunos);
                lstResultadosALunos.setAdapter(adapterAlunos);

            }
        });

    }



}
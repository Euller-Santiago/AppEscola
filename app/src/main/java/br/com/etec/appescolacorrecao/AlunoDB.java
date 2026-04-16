package br.com.etec.appescolacorrecao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class AlunoDB {
    //nome, cidade, turma, cpf;
    private SQLiteDatabase db;
    public static final String TABELA = "tbAluno";
    public final String ID = "id";
    public final String NOME = "nome";
    public final String CIDADE = "cidade";
    public final String TURMA = "turma";
    public final String IDADE = "idade";
    public final String CPF = "cpf";

    public AlunoDB(Context context) {

        BancoDB banco = new BancoDB(context);
        db = banco.getWritableDatabase();

    }

    public long cadastroAluno(Aluno al) {
        ContentValues valores = new ContentValues();
        long retornoDB;
        valores.put(NOME, al.getNome());
        valores.put(TURMA, al.getTurma());
        valores.put(CIDADE, al.getCidade());
        valores.put(IDADE, al.getIdade());
        valores.put(CPF, al.getCpf());
        retornoDB = db.insert(TABELA, null, valores);
        return retornoDB;
    }

    public long alterarCadastroAluno(Aluno al) {
        ContentValues valores = new ContentValues();
        long retornoDB;
        valores.put(NOME, al.getNome());
        valores.put(TURMA, al.getTurma());
        valores.put(CIDADE, al.getCidade());
        valores.put(IDADE, al.getIdade());
        valores.put(CPF, al.getCpf());
        String[] args = {String.valueOf(al.getId())};
        retornoDB = db.update(TABELA, valores, "id=?", args);
        return retornoDB;
    }

    public long execluirAluno(Aluno al) {
        long retornoDB;
        String[] args = {String.valueOf(al.getId())};
        retornoDB = db.delete(TABELA, ID + "=?", args);
        return retornoDB;
    }

    public ArrayList<Aluno> consultarAluno() {
        String[] colunas = {ID, NOME, TURMA, CPF, CIDADE,IDADE};
        Aluno al;
        Cursor cursor = db.query(TABELA, colunas,
                null,
                null,
                null,
                null,
                "upper(nome)",
                null);
        ArrayList<Aluno> listaAlunoAl = new ArrayList<>();
        while (cursor.moveToNext()) {
            al = new Aluno();
            al.setId(cursor.getInt(0));
            al.setNome(cursor.getString(1));
            al.setTurma(cursor.getString(2));
            al.setCpf(cursor.getString(3));
            al.setCidade(cursor.getString(4));
            al.setIdade(cursor.getString(5));
            listaAlunoAl.add(al);
        }
        return listaAlunoAl;

    }

    public ArrayList<Aluno> consultaNome(Aluno al) {
        String[] colunas = {ID, NOME, TURMA, CPF, CIDADE, IDADE};
        String nome = "%" + al.getNome() + "%";
        Aluno aluno;
        Cursor cursor = db.query(TABELA, colunas,
                NOME + " like ?", new String[]{nome}, null, null,
                null);
        ArrayList<Aluno> listaAluno = new ArrayList<>();
        while (cursor.moveToNext()) {
            aluno = new Aluno();
            aluno.setId(cursor.getInt(0));
            aluno.setNome(cursor.getString(1));
            aluno.setTurma(cursor.getString(2));
            aluno.setCpf(cursor.getString(3));
            aluno.setCidade(cursor.getString(4));
            aluno.setIdade(cursor.getString(5));
            listaAluno.add(aluno);
        }

        cursor.close();
        return listaAluno;
    }

    public ArrayList<Aluno> consultaCPF(Aluno al) {
        String[] colunas = {ID, NOME, TURMA, CPF, CIDADE,IDADE};
        String cpf = al.getCpf();
        Aluno aluno;
        Cursor cursor = db.query(TABELA, colunas,
                CPF + " = ?", new String[]{cpf}, null, null,
                null);
        ArrayList<Aluno> listaAluno = new ArrayList<>();
        while (cursor.moveToNext()) {
            aluno = new Aluno();
            aluno.setId(cursor.getInt(0));
            aluno.setNome(cursor.getString(1));
            aluno.setTurma(cursor.getString(2));
            aluno.setCpf(cursor.getString(3));
            aluno.setCpf(cursor.getString(4));
            aluno.setIdade(cursor.getString(5));
            listaAluno.add(aluno);
        }
        return listaAluno;
    }
}

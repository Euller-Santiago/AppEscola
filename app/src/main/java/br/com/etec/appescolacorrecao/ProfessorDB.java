package br.com.etec.appescolacorrecao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ProfessorDB {
    public static final String TABELA = "tbProfessor";
    public final String ID = "id";
    public final String NOME = "nome";
    public final String FORMACAO = "formacao";
    public final String TELEFONE = "telefone";
    public final String CPF = "cpf";
    public final String EMAIL = "email";
    private SQLiteDatabase db;

    public ProfessorDB(Context context) {
        BancoDB banco = new BancoDB(context);
        db = banco.getWritableDatabase();

    }

    public long cadastrarProfessor(Professor PROF) {
        ContentValues valores = new ContentValues();
        long retornoDB;
        valores.put(NOME, PROF.getNome());
        valores.put(EMAIL, PROF.getEmail());
        valores.put(FORMACAO, PROF.getFormacao());
        valores.put(TELEFONE, PROF.getTelefone());
        valores.put(CPF, PROF.getCpf());
        retornoDB = db.insert(TABELA, null, valores);
        return retornoDB;
    }

    public long alterarCadastroProf(Professor prof) {
        ContentValues valores = new ContentValues();
        long retornoDB;
        valores.put(NOME, prof.getNome());
        valores.put(EMAIL, prof.getEmail());
        valores.put(FORMACAO, prof.getFormacao());
        valores.put(TELEFONE, prof.getTelefone());
        valores.put(CPF, prof.getCpf());
        String[] args = {String.valueOf(prof.getId())};
        //update set nome = ? , email = ? where id = ?
        retornoDB = db.update(TABELA, valores, "id=?", args);
        return retornoDB;
    }

    public long excluirProfessor(Professor prof) {
        long retornoDB;
        String[] args = {String.valueOf(prof.getId())};
        retornoDB = db.delete(TABELA, ID + "=?", args);
        return retornoDB;
    }
    //select * from tbProfessor;
    public ArrayList<Professor> consultarProfessor() {
        String[] colunas = {ID, NOME, EMAIL, FORMACAO, CPF,TELEFONE};
        Professor pr;
        Cursor cursor = db.query(TABELA, colunas,
                null,
                null,
                null,
                null,
                "upper(nome)",
                null);
        ArrayList<Professor> listaContatoProf = new ArrayList<>();
        while (cursor.moveToNext()) {
            pr = new Professor();
            pr.setId(cursor.getInt(0));
            pr.setNome(cursor.getString(1));
            pr.setEmail(cursor.getString(2));
            pr.setFormacao(cursor.getString(3));
            pr.setCpf(cursor.getString(4));
            pr.setTelefone(cursor.getString(5));
            listaContatoProf.add(pr);
        }
        return listaContatoProf;

    }
    //select * from tbProfessor where nome like %nome%;
    public ArrayList<Professor> consultaNomeProfessor(Professor prof) {
        String[] colunas = {ID, NOME, EMAIL, FORMACAO, CPF,TELEFONE};
        String nome = "%" + prof.getNome() + "%";
        Professor pr;
        Cursor cursor = db.query(TABELA, colunas,
                NOME + " like ?", new String[]{nome}, null, null,
                null);
        ArrayList<Professor> listaContatoProf = new ArrayList<>();
        while (cursor.moveToNext()) {
            pr = new Professor();
            pr.setId(cursor.getInt(0));
            pr.setNome(cursor.getString(1));
            pr.setEmail(cursor.getString(2));
            pr.setFormacao(cursor.getString(3));
            pr.setCpf(cursor.getString(4));
            pr.setTelefone(cursor.getString(5));
            listaContatoProf.add(pr);
        }

        cursor.close();
        return listaContatoProf;
    }

    public ArrayList<Professor> consultaCpfProfessor(Professor prof) {
        String[] colunas = {ID, NOME, EMAIL, FORMACAO, CPF,TELEFONE};
        String cpf = prof.getCpf();
        Professor pr;
        Cursor cursor = db.query(TABELA, colunas,
                CPF + " = ?", new String[]{cpf}, null, null,
                null);
        ArrayList<Professor> listaContatoProf = new ArrayList<>();
        while (cursor.moveToNext()) {
            pr = new Professor();
            pr.setId(cursor.getInt(0));
            pr.setNome(cursor.getString(1));
            pr.setEmail(cursor.getString(2));
            pr.setFormacao(cursor.getString(3));
            pr.setCpf(cursor.getString(4));
            pr.setTelefone(cursor.getString(5));
            listaContatoProf.add(pr);
        }
        return listaContatoProf;
    }
}



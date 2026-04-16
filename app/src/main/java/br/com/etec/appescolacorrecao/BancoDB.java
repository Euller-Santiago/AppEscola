package br.com.etec.appescolacorrecao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class BancoDB extends SQLiteOpenHelper {

    public static final String NOME_BANCO = "ESCOLA.db";
    public static final int VERSAO = 1;

    public BancoDB(Context context) {
        super(context, NOME_BANCO, null, VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE tbProfessor (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "email TEXT," +
                "telefone TEXT," +
                "formacao TEXT," +
                "cpf TEXT)");

        db.execSQL("CREATE TABLE tbAluno (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nome TEXT," +
                "cidade TEXT," +
                "idade TEXT," +
                "turma TEXT," +
                "cpf TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS tbProfessor");
        db.execSQL("DROP TABLE IF EXISTS tbAluno");
        onCreate(db);
    }
}


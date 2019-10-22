package com.example.loginwindow.sqlite;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.loginwindow.model.Usuario;
import com.example.loginwindow.model.Veiculo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VeiculoSQLITE {

    private static final String DATABASE_NAME = "datanew";

    private SQLiteDatabase mDatabase;

    public VeiculoSQLITE(Context mCtx){
        this.mDatabase = (SQLiteDatabase) mCtx.openOrCreateDatabase(DATABASE_NAME,Context.MODE_PRIVATE,null);
        createVeiculoTable();
    }

    private void createVeiculoTable() {
        mDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS veiculos (\n" +
                        "    idVeiculo INTEGER NOT NULL PRIMARY KEY,\n" +
                        "    matriculaUsuario INTEGER NOT NULL,\n" +
                        "    kilometragem INTEGER NOT NULL,\n" +
                        "    localOrigem varchar(200) NOT NULL, \n" +
                        "    localDestino varchar(200) NOT NULL, \n" +
                        "    data TEXT NOT NULL, \n" +
                        "    FOREIGN KEY(matriculaUsuario) REFERENCES usuarios(id)\n" +
                        ");"
        );
    }

    public Veiculo findVeiculoById(Veiculo veiculo, Context context) throws ParseException {

        UsuarioSQLITE user = new UsuarioSQLITE(context);

        int id = veiculo.getIdVeiculo();

        String findVeiculo = "SELECT * FROM veiculos \n" +
                            "WHERE idVeiculo = " +id+ ";";

        Cursor cursor = mDatabase.rawQuery(findVeiculo,null);

        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");

        if(cursor.moveToFirst()){

            Usuario usuario = user.findUserById(cursor.getInt(1));

            int kms = cursor.getInt(2);
            String localOrigem = cursor.getString(3);
            String localDestino = cursor.getString(4);
            Date data = formato.parse(cursor.getString(5));

        }

        return veiculo;

    }









}

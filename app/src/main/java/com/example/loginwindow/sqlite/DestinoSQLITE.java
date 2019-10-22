package com.example.loginwindow.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.loginwindow.model.Destino;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DestinoSQLITE {

    private static final String DATABASE_NAME = "datanew";

    private SQLiteDatabase mDatabase;

    public DestinoSQLITE(Context mCtx) {
        this.mDatabase = (SQLiteDatabase) mCtx.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
        createOrigemTable();
    }

    //this method will create the table
    //as we are going to call this method everytime we will launch the application
    //I have added IF NOT EXISTS to the SQL
    //so it will only create the table when the table is not already created

    private void createOrigemTable() {
        mDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS destino (\n" +
                        "    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                        "    local varchar(200) NOT NULL,\n" +
                        "    horas varchar(200) NOT NULL,\n" +
                        "    km INTEGER NOT NULL \n" +
                        ");"
        );
    }

    public void addDestino(String local, String hora, int km) {

        String insertSQL = "INSERT INTO destino \n" +
                "(local, horas, km)\n" +
                "VALUES \n" +
                "(?, ?, ?, ?);";

        ContentValues cv = new ContentValues();
        cv.put("local", local);
        cv.put("horas", hora);
        cv.put("km", km);
        System.out.println(local);
        System.out.println(hora);
        System.out.println(km);
        mDatabase.insert("destino",null,cv);


    }

    public void delete(Destino destino) {
        String sql = "DELETE FROM destino WHERE id = ?";
        mDatabase.execSQL(sql, new Integer[]{destino.getId()});
    }

    public void reloadEmployeesFromDatabase(List<Destino> destinoList) {
        Cursor cursorDestino = mDatabase.rawQuery("SELECT * FROM destino ORDER BY horas", null);
        if (cursorDestino.moveToFirst()) {
            destinoList.clear();
            do {
                destinoList.add(new Destino(
                        cursorDestino.getInt(0),
                        cursorDestino.getString(1),
                        cursorDestino.getString(2),
                        cursorDestino.getInt(3)
                ));
            } while (cursorDestino.moveToNext());
        }
        cursorDestino.close();
    }

    public boolean updateDestino(Destino destino){

        String local = destino.getLocal();
        String hora = destino.getHora();
        int km = destino.getKm();


        String sql = "UPDATE destino \n" +
                "SET local = ?, \n" +
                "horas = ?, \n" +
                "km = ? \n" +
                "WHERE id = ?;\n";

        mDatabase.execSQL(sql, new String[]{local, hora, String.valueOf(km), String.valueOf(destino.getId())});

        return true;

    }
}


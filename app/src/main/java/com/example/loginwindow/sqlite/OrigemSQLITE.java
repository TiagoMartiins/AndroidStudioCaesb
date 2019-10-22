package com.example.loginwindow.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.widget.EditText;

import com.example.loginwindow.model.Origem;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class OrigemSQLITE {

        private static final String DATABASE_NAME = "datanew";

        private SQLiteDatabase mDatabase;

    public OrigemSQLITE(Context mCtx) {
        this.mDatabase = (SQLiteDatabase) mCtx.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
        createOrigemTable();
    }

        //this method will create the table
        //as we are going to call this method everytime we will launch the application
        //I have added IF NOT EXISTS to the SQL
        //so it will only create the table when the table is not already created

        private void createOrigemTable() {
        mDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS origem (\n" +
                        "    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                        "    local varchar(200) NOT NULL,\n" +
                        "    horas varchar(200) NOT NULL,\n" +
                        "    km INTEGER NOT NULL \n" +
                        ");"
        );
    }

    public void addOrigem(String local, String hora, int km) {

        String insertSQL = "INSERT INTO origem \n" +
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
        //System.out.println(imgFormatada);
        mDatabase.insert("origem",null,cv);


    }

    public void delete(Origem origem) {
        String sql = "DELETE FROM origem WHERE id = ?";
        mDatabase.execSQL(sql, new Integer[]{origem.getId()});
    }

    public void reloadEmployeesFromDatabase(List<Origem> origemList) {
        Cursor cursorOrigem = mDatabase.rawQuery("SELECT * FROM origem ORDER BY horas", null);
        if (cursorOrigem.moveToFirst()) {
            origemList.clear();
            do {
                origemList.add(new Origem(
                        cursorOrigem.getInt(0),
                        cursorOrigem.getString(1),
                        cursorOrigem.getString(2),
                        cursorOrigem.getInt(3)
                ));
            } while (cursorOrigem.moveToNext());
        }
        cursorOrigem.close();
    }

    public boolean updateOrigem(Origem origem){

        String local = origem.getLocal();
        String hora = origem.getHora();
        int km = origem.getKm();


        String sql = "UPDATE origem \n" +
                "SET local = ?, \n" +
                "horas = ?, \n" +
                "km = ? \n" +
                "WHERE id = ?;\n";

        System.out.println(origem.getHora());
        mDatabase.execSQL(sql, new String[]{local, hora, String.valueOf(km), String.valueOf(origem.getId())});

        return true;

    }
}

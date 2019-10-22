package com.example.loginwindow.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.EditText;
import android.widget.ListView;

import com.example.loginwindow.model.Origem;
import com.example.loginwindow.model.Usuario;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class UsuarioSQLITE {

    private static final String DATABASE_NAME = "datanew";

    private SQLiteDatabase mDatabase;

    public UsuarioSQLITE(Context mCtx) {
        this.mDatabase = (SQLiteDatabase) mCtx.openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE,null);
        createUserTable();
    }

    //this method will create the table
    //as we are going to call this method everytime we will launch the application
    //I have added IF NOT EXISTS to the SQL
    //so it will only create the table when the table is not already created

    private void createUserTable() {
        mDatabase.execSQL(
                "CREATE TABLE IF NOT EXISTS usuarios (\n" +
                        "    id INTEGER NOT NULL PRIMARY KEY AUTOINCREMENT,\n" +
                        "    nome varchar(200) NOT NULL,\n" +
                        "    email varchar(200) NOT NULL,\n" +
                        "    senha varchar(200) NOT NULL, \n" +
                        "    dataCadastro varchar(200) NOT NULL, \n" +
                        "    equipe varchar(200) NOT NULL \n" +
                        //"    foto blob \n" +
                        ");"
        );
    }

    public Usuario findUserById(int id){


        String findUser = "SELECT * FROM usuarios \n" +
                "WHERE id = "+id+";";

        Cursor cursor = mDatabase.rawQuery(findUser,null);

        if(cursor.moveToFirst()){
            Usuario usuario = new Usuario();
            usuario.setId(cursor.getInt(0));
            usuario.setNome(cursor.getString(1));
            usuario.setEmail(cursor.getString(2));
            usuario.setSenha(cursor.getString(3));
            usuario.setDataCadastro(cursor.getString(4));
            usuario.setEquipe(cursor.getString(5));
           // ByteArrayInputStream imageStream = new ByteArrayInputStream(cursor.getBlob(6));
            //Bitmap foto = BitmapFactory.decodeStream(imageStream);
            //usuario.setFotoPerfil(foto);

            System.out.println("-----------------");
            System.out.println(usuario);
            return usuario;

        }else {
            return null;
        }

    }

    public void addUser(EditText editTextNome, EditText editTextEmail, EditText editTextSenha, EditText editTextEquipe) {

        String nome = editTextNome.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String senha = editTextSenha.getText().toString().trim();
        String equipe = editTextEquipe.getText().toString().trim();
       // byte [] imgFormatada;

        //ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //img.compress(Bitmap.CompressFormat.PNG, 0, stream);
        //imgFormatada = stream.toByteArray();



        //getting the current time for joining date
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String dataCadastro = sdf.format(cal.getTime());


        String insertSQL = "INSERT INTO usuarios \n" +
                "(nome, email, senha, dataCadastro,equipe)\n" +
                "VALUES \n" +
                "(?, ?, ?, ?,?,?);";

        ContentValues cv = new ContentValues();
        cv.put("nome", nome);
        cv.put("email", email);
        cv.put("senha", senha);
        cv.put("dataCadastro", dataCadastro);
        cv.put("equipe", equipe);
        //cv.put("foto",imgFormatada);
        System.out.println(nome);
        System.out.println(senha);
        System.out.println(email);
        //System.out.println(imgFormatada);
        mDatabase.insert("usuarios",null,cv);

        //using the same method execsql for inserting values
        //this time it has two parameters
        //first is the sql string and second is the parameters that is to be binded with the query
        //mDatabase.execSQL(insertSQL, new String[]{nome, email, senha, dataCadastro,equipe, Arrays.toString(imgFormatada)});

    }

    public void delete(Usuario usuario) {
        String sql = "DELETE FROM usuarios WHERE id = ?";
        mDatabase.execSQL(sql, new Integer[]{usuario.getId()});
    }

    public Usuario autenticarUsuario(Usuario usuario){
        String email = usuario.getEmail();
        String senha = usuario.getSenha();

        String autenticarSQL = "SELECT * FROM usuarios \n" +
                "WHERE email = '"+email+"' AND senha = "+senha+";";

        Cursor cursor = mDatabase.rawQuery(autenticarSQL,null);

        if(cursor.moveToFirst()){
            final Usuario usuarioLogado = new Usuario();
            usuarioLogado.setId(cursor.getInt(0));
            usuarioLogado.setNome(cursor.getString(1));
            usuarioLogado.setEmail(cursor.getString(2));
            usuarioLogado.setSenha(cursor.getString(3));
            usuarioLogado.setDataCadastro(cursor.getString(4));
            usuarioLogado.setEquipe(cursor.getString(5));
            //ByteArrayInputStream imageStream = new ByteArrayInputStream(cursor.getBlob(6));
            //Bitmap foto = BitmapFactory.decodeStream(imageStream);
            //usuarioLogado.setFotoPerfil(foto);

            System.out.println("-----------------");
            System.out.println(usuarioLogado);
            return usuarioLogado;

        }else {
            return null;
        }

    }

    public void reloadEmployeesFromDatabase(List userList) {
        Cursor cursorUsuarios = mDatabase.rawQuery("SELECT * FROM usuarios", null);
        if (cursorUsuarios.moveToFirst()) {
            userList.clear();
            do {

                //ByteArrayInputStream imageStream = new ByteArrayInputStream(cursorUsuarios.getBlob(6));
                //Bitmap foto = BitmapFactory.decodeStream(imageStream);
                userList.add(new Usuario(
                        cursorUsuarios.getInt(0),
                        cursorUsuarios.getString(1),
                        cursorUsuarios.getString(2),
                        cursorUsuarios.getString(3),
                        cursorUsuarios.getString(4),
                        cursorUsuarios.getString(5)
                        //foto
                ));
            } while (cursorUsuarios.moveToNext());
        }
        cursorUsuarios.close();
    }

    public List<Usuario> showEmployeesFromDatabase(List<Usuario> userList,Context mCtx) {
        //we used rawQuery(sql, selectionargs) for fetching all the employees
        Cursor cursorUsuarios = mDatabase.rawQuery("SELECT * FROM usuarios", null);
        System.out.println(cursorUsuarios);
        //if the cursor has some data
        if (cursorUsuarios.moveToFirst()) {
            //looping through all the records
            do {

                //ByteArrayInputStream imageStream = new ByteArrayInputStream(cursorUsuarios.getBlob(6));
                //Bitmap foto = BitmapFactory.decodeStream(imageStream);

                //pushing each record in the employee list
                userList.add(new Usuario(
                        cursorUsuarios.getInt(0),
                        cursorUsuarios.getString(1),
                        cursorUsuarios.getString(2),
                        cursorUsuarios.getString(3),
                        cursorUsuarios.getString(4),
                        cursorUsuarios.getString(5)
                        //foto
                ));
            } while (cursorUsuarios.moveToNext());
        }
        //closing the cursor
        cursorUsuarios.close();

        System.out.println("Lista de users: ------------------");
        System.out.println(userList);

        return userList;

    }

    /*public void updateUsuario(final Usuario usuario, Context mCtx) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);

        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(R.layout.dialog_update_employee, null);
        builder.setView(view);


        final EditText editTextName = view.findViewById(R.id.editTextName);
        final EditText editTextSalary = view.findViewById(R.id.editTextSalary);
        final Spinner spinnerDepartment = view.findViewById(R.id.spinnerDepartment);

        editTextName.setText(usuario.getNome());
        editTextSalary.setText(String.valueOf(usuario.getSalary()));

        final AlertDialog dialog = builder.create();
        dialog.show();

        view.findViewById(R.id.buttonUpdateEmployee).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString().trim();
                String salary = editTextSalary.getText().toString().trim();
                String dept = spinnerDepartment.getSelectedItem().toString();

                if (name.isEmpty()) {
                    editTextName.setError("Nome Obrigatório");
                    editTextName.requestFocus();
                    return;
                }

                if (salary.isEmpty()) {
                    editTextSalary.setError("Salario Obrigatório");
                    editTextSalary.requestFocus();
                    return;
                }

                String sql = "UPDATE employees \n" +
                        "SET name = ?, \n" +
                        "department = ?, \n" +
                        "salary = ? \n" +
                        "WHERE id = ?;\n";

                mDatabase.execSQL(sql, new String[]{name, dept, salary, String.valueOf(employee.getId())});
                dialog.dismiss();
            }
        });
    }*/


}

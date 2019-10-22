package com.example.loginwindow.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.loginwindow.R;
import com.example.loginwindow.model.Usuario;
import com.example.loginwindow.sqlite.UsuarioSQLITE;

import java.text.NumberFormat;
import java.util.List;


public class AdapterUser extends ArrayAdapter<Usuario> {

    Context mCtx;
    int listLayoutRes;
    List<Usuario> userList;
    UsuarioSQLITE banco;
    NumberFormat formataNumero = NumberFormat.getCurrencyInstance();

    public AdapterUser(Context mCtx, int listLayoutRes, List<Usuario> userList) {
        super(mCtx, listLayoutRes, userList);

        this.mCtx = mCtx;
        this.listLayoutRes = listLayoutRes;
        this.userList = userList;
        this.banco = new UsuarioSQLITE(mCtx);
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater = LayoutInflater.from(mCtx);
        View view = inflater.inflate(listLayoutRes, null);

        final Usuario user = userList.get(position);


        TextView textViewName = view.findViewById(R.id.textViewName);
        TextView textViewDept = view.findViewById(R.id.textViewDepartment);
        TextView textViewJoiningDate = view.findViewById(R.id.textViewJoiningDate);


        textViewName.setText(user.getNome());
        textViewDept.setText(user.getEmail());
        textViewJoiningDate.setText(user.getDataCadastro());


        Button buttonDelete = view.findViewById(R.id.buttonDeleteEmployee);
        Button buttonEdit = view.findViewById(R.id.buttonEditEmployee);

        //adding a clicklistener to button
        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //banco.u(employee,mCtx);
                banco.reloadEmployeesFromDatabase(userList);
            }
        });

        //the delete operation
        buttonDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mCtx);
                builder.setTitle("Tem Certeza que quer deletar?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        banco.delete(user);
                        //banco.reloadEmployeesFromDatabase(employeeList);
                        Toast.makeText(mCtx, "Deletado com Sucesso", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return view;
    }






   /* private void findByName(String filtro){
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM employees WHERE name = %"+filtro+"%");
        if(cursor.moveToFirst()){
            employeeList.clear();
            do{
                employeeList.add(new Employee(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getDouble(4)
                ));
            } while (cursor.moveToNext());
        }
        cursor.close();
        notifyDataSetChanged();
            }
    */



}


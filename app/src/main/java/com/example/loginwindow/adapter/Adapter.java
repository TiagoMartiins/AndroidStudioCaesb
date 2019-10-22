



package com.example.loginwindow.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.loginwindow.R;
import com.example.loginwindow.model.Usuario;

import java.text.SimpleDateFormat;
import java.util.List;


public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {

private List<Usuario> users;
private Context context;

public Adapter(List<Usuario> users, Context context) {
        this.users = users;
        this.context = context;
        }

@NonNull
@Override
public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_list_users, parent, false);
        return new MyViewHolder( item );
        }

@Override
public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Usuario user = users.get(position);
        holder.nome.setText( user.getNome() );
        holder.dataCadastro.setText( user.getDataCadastro() );
        holder.foto.setImageResource(R.drawable.ic_person_24dp);

        }

@Override
public int getItemCount() {
        return users.size();
        }

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView nome;
    TextView dataCadastro;
    ImageView foto;

    public MyViewHolder(View itemView) {
        super(itemView);

        nome = itemView.findViewById(R.id.textNome);
        dataCadastro  = itemView.findViewById(R.id.textData);
        foto   = itemView.findViewById(R.id.userImage);

    }
}

}



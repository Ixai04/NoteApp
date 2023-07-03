package com.example.NoteApp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class Adaptador extends RecyclerView.Adapter<Adaptador.ViewHolder> {
    private final LayoutInflater inflater;
    private final List<Nota> notas;

    Adaptador(Context context, List<Nota> notas) {
        this.inflater = LayoutInflater.from(context);
        this.notas = notas;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = inflater.inflate(R.layout.custom_list_view, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        String titulo = notas.get(i).getTitulo();
        String fecha = notas.get(i).getFecha();
        String hora = notas.get(i).getHora();
        Log.d("FECHA en ", "Fecha en: " + fecha);

        viewHolder.nTitulo.setText(titulo);
        viewHolder.nFecha.setText(fecha);
        viewHolder.nHora.setText(hora);
        viewHolder.nID.setText(String.valueOf(notas.get(i).getId()));

    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView nTitulo, nFecha, nHora, nID;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            nTitulo = itemView.findViewById(R.id.nTitulo);
            nFecha = itemView.findViewById(R.id.nFecha);
            nHora = itemView.findViewById(R.id.nTiempo);
            nID = itemView.findViewById(R.id.listId);

            itemView.setOnClickListener(v -> {
                Intent i = new Intent(v.getContext(), Detalles.class);
                i.putExtra("ID", notas.get(getAdapterPosition()).getId());
                v.getContext().startActivity(i);
            });
        }
    }
}

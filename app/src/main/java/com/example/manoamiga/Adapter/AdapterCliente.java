package com.example.manoamiga.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.manoamiga.Detalles_ct;
import com.example.manoamiga.R;
import com.example.manoamiga.pogo.cliente;
import java.util.ArrayList;
import java.util.List;
public class AdapterCliente extends RecyclerView.Adapter<AdapterCliente.ViewHolder> {

    private List<cliente> clientes;
    private List<cliente> originalList;

    public AdapterCliente(List<cliente> clientes) {
        this.clientes = new ArrayList<>(clientes);
        this.originalList = new ArrayList<>(clientes);
    }

    public void filterList(List<cliente> filteredList) {
        clientes.clear();
        clientes.addAll(filteredList);
        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public AdapterCliente.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_clientes, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterCliente.ViewHolder holder, int position) {
        cliente cliente = clientes.get(position);
        holder.idClienteTextView.setText("ID: " + cliente.getId());
        holder.nombreClienteTextView.setText("Nombre: " + cliente.getNombre());
        holder.apellidoClienteTextView.setText("Apellido: " + cliente.getApellido());
        holder.telefonoClienteTextView.setText("Telefono: " + cliente.getTelefono());

        holder.verDetallesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Detalles_ct.class);
                intent.putExtra("uid", cliente.getId());
                v.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return clientes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombreClienteTextView;
        TextView apellidoClienteTextView;
        TextView telefonoClienteTextView;
        TextView idClienteTextView;
        Button verDetallesButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            idClienteTextView = itemView.findViewById(R.id.idUserTextView);
            nombreClienteTextView = itemView.findViewById(R.id.nombreUserTextView);
            apellidoClienteTextView = itemView.findViewById(R.id.apellidoUserTextView);
            telefonoClienteTextView = itemView.findViewById(R.id.telefonoTextView);
            verDetallesButton = itemView.findViewById(R.id.verDetallesButton);
        }
    }
}

package com.example.manoamiga.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.manoamiga.Detalles_sv;
import com.example.manoamiga.R;
import com.example.manoamiga.pogo.servicio;
import java.util.ArrayList;
import java.util.List;

public class AdapterServicio extends RecyclerView.Adapter<AdapterServicio.ViewHolder> {

    private List<servicio> servicios;
    private List<servicio> originalList;

    public AdapterServicio(List<servicio> servicios) {
        this.servicios = new ArrayList<>(servicios);
        this.originalList = new ArrayList<>(servicios);
    }

    public void filterList(List<servicio> filteredList) {
        servicios.clear();
        servicios.addAll(filteredList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_servicios, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        servicio servicio = servicios.get(position);
        holder.idServicioTextView.setText("ID: " + servicio.getId());
        holder.nombreServicioTextView.setText("Nombre: " + servicio.getServicio());
        holder.precioServicioTextView.setText("Precio: " + servicio.getPrecio() + "$");

        holder.verDetallesButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), Detalles_sv.class);
                intent.putExtra("uid", servicio.getId());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return servicios.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView nombreServicioTextView;
        TextView precioServicioTextView;
        TextView idServicioTextView;
        Button verDetallesButton;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            idServicioTextView = itemView.findViewById(R.id.idServicioTextView);
            nombreServicioTextView = itemView.findViewById(R.id.nombreServicioTextView);
            precioServicioTextView = itemView.findViewById(R.id.precioServicioTextView);
            verDetallesButton = itemView.findViewById(R.id.verDetallesButton);


        }
    }
}
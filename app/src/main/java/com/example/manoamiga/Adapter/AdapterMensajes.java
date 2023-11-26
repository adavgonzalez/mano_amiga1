package com.example.manoamiga.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.manoamiga.R;
import com.example.manoamiga.pogo.cliente;
import com.example.manoamiga.pogo.mensaje;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AdapterMensajes extends RecyclerView.Adapter<AdapterMensajes.ViewHolder>{

    private List<mensaje> mensajes;
    private List<mensaje> originalList;

    public AdapterMensajes(List<mensaje> mensajes) {
        this.mensajes = mensajes;
        this.originalList = mensajes;
    }
    public void filterList(List<mensaje> filteredList) {
        mensajes.clear();
        mensajes.addAll(filteredList);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AdapterMensajes.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_mensajes, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterMensajes.ViewHolder holder, int position) {
        mensaje mensaje = mensajes.get(position);
        holder.textViewMessageBody.setText(mensaje.getMensaje());

        // Formatear el timestamp a una cadena de texto en el formato deseado
        String formattedTimestamp = formatDateAndTime(mensaje.getTimestamp());
        holder.textViewMessageTime.setText(formattedTimestamp);

        holder.textViewSenderName.setText(mensaje.getNombre());
    }

    private String formatDateAndTime(Long timestamp) {
        if (timestamp == null) {
            return "";
        }

        // Configurar el formato de fecha y hora deseado
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss", Locale.getDefault());

        // Convertir el timestamp a una cadena de texto con el formato deseado
        return sdf.format(new Date(timestamp));
    }


    @Override
    public int getItemCount() {
        return mensajes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView textViewMessageBody;
        TextView textViewMessageTime;
        TextView textViewSenderName;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            textViewMessageBody = itemView.findViewById(R.id.textViewMessageBody);
            textViewMessageTime = itemView.findViewById(R.id.textViewMessageTime);
            textViewSenderName = itemView.findViewById(R.id.textViewSenderName);
        }
    }
}

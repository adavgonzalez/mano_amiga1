package com.example.manoamiga.Adapter;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.manoamiga.ChatPv;
import com.example.manoamiga.R;
import com.example.manoamiga.pogo.chats;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class AdapterChats2 extends RecyclerView.Adapter<AdapterChats2.ViewHolder>{

    private List<chats> chats;
    private List<chats> originalList;

    public AdapterChats2(List<chats> chats) {
        this.chats = chats;
        this.originalList = chats;
    }

    public void filterList(List<chats> filteredList) {
        chats.clear();
        chats.addAll(filteredList);
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public AdapterChats2.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_chats, parent, false);
        ViewHolder viewHolder = new ViewHolder(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterChats2.ViewHolder holder, int position) {

            chats chat = chats.get(position);
            holder.idChatTextView.setText(chat.getNombre());

            holder.chatOptionsButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), ChatPv.class);
                    intent.putExtra("uid", chat.getId());
                    v.getContext().startActivity(intent);
                }
            });

    }

    @Override
    public int getItemCount() {
        return chats.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView idChatTextView;
        Button chatOptionsButton;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idChatTextView = itemView.findViewById(R.id.idChatTextView);
            chatOptionsButton = itemView.findViewById(R.id.chatOptionsButton);
        }
    }
}

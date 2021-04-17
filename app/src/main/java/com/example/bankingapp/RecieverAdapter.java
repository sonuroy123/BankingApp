package com.example.bankingapp;

import android.content.Context;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class RecieverAdapter extends RecyclerView.Adapter<RecieverAdapter.myViewHolder> {
    RecieverList recieverList;

    private Context context;
    List<model> ModelList;
    private RecyclerviewClickListener listener;

    public RecieverAdapter(RecieverList recieverList, List<model> modelList_receiverList,RecyclerviewClickListener listener) {
        this.recieverList= recieverList;
        this.ModelList = modelList_receiverList;
        this.listener = listener;
    }

    @Override
    public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater =LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.list_item_receiver_layout,parent,false);
        return new myViewHolder(view);
    }
    @NonNull
    @Override
    public void onBindViewHolder( myViewHolder holder, int position) {

        holder.txt1Title.setText(String.valueOf(ModelList.get(position).getName()));
        holder.receiver_email.setText(String.valueOf(ModelList.get(position).getEmail()));
        holder.receiver_balance.setText(String.valueOf(ModelList.get(position).getBalance()));
    }

    @Override
    public int getItemCount() {
        return ModelList.size();
    }

    public class myViewHolder extends RecyclerView.ViewHolder{
        private TextView txt1Title;
        private TextView receiver_email;
        private TextView receiver_balance;

        public myViewHolder( View itemView) {
            super(itemView);
            txt1Title =(TextView) itemView.findViewById(R.id.txtTitle);
            receiver_email  =(TextView) itemView.findViewById(R.id.email_sender);
            receiver_balance=(TextView) itemView.findViewById(R.id.balance_sender);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    recieverList.selectUser(getAdapterPosition());
                }

            });
        }

    }
    public interface RecyclerviewClickListener{
        void onClick(View v,int position);
    }
}

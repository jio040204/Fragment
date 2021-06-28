package com.example.room;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> implements OnCustomerItemClickLitener{
    ArrayList<Customer> items = new ArrayList<>();

    OnCustomerItemClickLitener listener;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType){
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.customer_item, viewGroup, false);

        return new ViewHolder(itemView, this);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Customer item = items.get(position);
        ViewHolder viewHolder;
        holder.setItem(item);

    }

    @Override
    public int getItemCount(){
        return items.size();
    }
    public void addItem(Customer item){
        items.add(item);
    }
    public void setItems(ArrayList<Customer> items) {
        this.items = items;
    }
    public Customer getItem(int position){
        return items.get(position);
    }
    public void setItem(int position, Customer item){
        items.set(position, item);
    }
    public void setOnItemClickListener(OnCustomerItemClickLitener listener){
        this.listener = listener;
    }
    @Override
    public void onItemClick(ViewHolder holder, View view, int position){
        if(listener !=null){
            listener.onItemClick(holder, view, position);
        }
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        TextView textView1;
        TextView textView2;
        TextView textView3;
        ImageView imageView;

        public ViewHolder(View itemView, final CustomerAdapter listener){
            super(itemView);

            textView1 = itemView.findViewById(R.id.textView1);
            textView2 = itemView.findViewById(R.id.textView2);
            textView3 = itemView.findViewById(R.id.textView3);
            imageView = itemView.findViewById(R.id.imageView);

            itemView.setOnClickListener((view)->{
                int position = getAdapterPosition();

                if(listener!=null){
                    listener.onItemClick(ViewHolder.this, view, position);
                }
            });
        }
        public void setItem(Customer item){
            textView1.setText(item.getName());
            textView2.setText(item.getAdd());
            textView3.setText(item.getNum());
            imageView.setImageResource(item.getNum());
        }
    }
}
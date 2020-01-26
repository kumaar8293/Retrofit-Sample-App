package com.mukesh.retrofitsampleapp.view.home;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.mukesh.retrofitsampleapp.R;

import java.util.List;

public abstract class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.MyHolder> {
    private List<String> nameList;

    public abstract void customOnClick(int position);

    public HomeAdapter(List<String> nameList) {
        this.nameList = nameList;
    }

    @NonNull
    @Override
    public MyHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_row, parent, false);

        return new MyHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyHolder holder, int position) {

        holder.button.setText((nameList.get(position) + " " + position));
    }


    @Override
    public int getItemCount() {
        return nameList.size();
    }

    class MyHolder extends RecyclerView.ViewHolder {

        private Button button;

        MyHolder(@NonNull View itemView) {
            super(itemView);
            button = itemView.findViewById(R.id.button);

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    customOnClick(getAdapterPosition());
                }
            });

        }
    }
}

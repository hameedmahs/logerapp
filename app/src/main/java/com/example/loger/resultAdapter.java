package com.example.loger;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class resultAdapter extends RecyclerView.Adapter<resultAdapter.listVH> {
    public resultAdapter(List<reult_First> result_list) {
        this.result_list = result_list;
    }

    List<reult_First> result_list;

    @NonNull
    @Override
    public listVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.result, parent, false);
        return new listVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull listVH holder, int position) {
        //   holder.titlesem.setText(String.valueOf(position));
        reult_First reult_first = result_list.get(position);
        holder.titlesem.setText(reult_first.getSem());

        boolean isExpanded = result_list.get(position).isExpanded();
        holder.expandablelayout.setVisibility(isExpanded ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return result_list.size();
    }

    class listVH extends RecyclerView.ViewHolder {

        TextView titlesem;
        ConstraintLayout expandablelayout;

        public listVH(@NonNull View itemView) {
            super(itemView);
            titlesem = itemView.findViewById(R.id.titleTextView);
            expandablelayout = itemView.findViewById(R.id.expandableLayout);
            titlesem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    reult_First first = result_list.get(getAdapterPosition());
                    first.setExpanded(!first.isExpanded());
                    notifyItemChanged(getAdapterPosition());
                }
            });
        }
    }
}

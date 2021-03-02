package com.example.loger;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

public class View_Holder extends RecyclerView.ViewHolder {

    View view;

    public View_Holder(@NonNull View itemView) {
        super(itemView);

        view=itemView;
    }
    public void setdetails(Context context,String title,String image){

        TextView mtitleview=view.findViewById(R.id.text_front);
        ImageView mimageview=view.findViewById(R.id.image_front);

        mtitleview.setText(title);
        Picasso.get().load(image).into(mimageview);

    }
}

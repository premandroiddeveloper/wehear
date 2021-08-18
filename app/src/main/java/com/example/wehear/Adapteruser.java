package com.example.wehear;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapteruser extends RecyclerView.Adapter<Adapteruser.viewholder> {
    ArrayList<ModelUserClass> ml1;
    Context context;

    public Adapteruser(ArrayList<ModelUserClass> ml1, Context context) {
        this.ml1 = ml1;
        this.context = context;
    }

    @NonNull
    @Override
    public Adapteruser.viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
      View view= LayoutInflater.from(context).inflate(R.layout.sample_user_item,parent,false);

        return new viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Adapteruser.viewholder holder, int position) {
        ModelUserClass usersset=ml1.get(position);
          holder.t1.setText(usersset.getName());
          holder.t2.setText(usersset.getPhoneno());
    }

    @Override
    public int getItemCount() {
        return ml1.size();
    }
    public class viewholder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView t1,t2;
        public viewholder(@NonNull View itemView) {
            super(itemView);
            image=(ImageView)itemView.findViewById(R.id.profile_image);
            t1=(TextView)itemView.findViewById(R.id.username);
            t2=(TextView)itemView.findViewById(R.id.usernumber);

        }
    }
}

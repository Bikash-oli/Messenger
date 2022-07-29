package com.example.bikash_messenger.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bikash_messenger.Fragments.ChatActivity;
import com.example.bikash_messenger.Models.Users;
import com.example.bikash_messenger.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder>{

    ArrayList<Users> list;
    Context context;

    public UsersAdapter(ArrayList<Users> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.show_user,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder holder, int position) {
        Users user=list.get(position);
        Picasso.get().load(user.getProfilepic()).placeholder(R.drawable.ic_user).into(holder.image);
        holder.username.setText(user.getUsername());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i=new Intent(context, ChatActivity.class);
                i.putExtra("userId",user.getUserId());
                i.putExtra("profilePic",user.getProfilepic());
                i.putExtra("userName",user.getUsername());
                context.startActivity(i);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView image;
        TextView username,lastmessage;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            image=itemView.findViewById(R.id.profile_image);
            username=itemView.findViewById(R.id.username);
            lastmessage=itemView.findViewById(R.id.lastmessage);

        }
    }
}

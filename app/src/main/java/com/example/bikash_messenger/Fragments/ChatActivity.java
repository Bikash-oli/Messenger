package com.example.bikash_messenger.Fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.bikash_messenger.Adapters.ChatAdapter;
import com.example.bikash_messenger.Home;
import com.example.bikash_messenger.Models.MessagesModel;
import com.example.bikash_messenger.R;
import com.example.bikash_messenger.databinding.ActivityChatBinding;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Date;


public class ChatActivity extends AppCompatActivity {

    ActivityChatBinding binding;
    FirebaseDatabase database;
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityChatBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        database=FirebaseDatabase.getInstance();
        auth=FirebaseAuth.getInstance();

        final String senderId=auth.getUid();
        String receiveId=getIntent().getStringExtra("userId");
        String userName=getIntent().getStringExtra("userName");
        String profilePic=getIntent().getStringExtra("profilePic");

        binding.userName.setText(userName);
        Picasso.get().load(profilePic).placeholder(R.drawable.ic_user).into(binding.profileImage);

        binding.backArrow.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Intent i=new Intent(ChatActivity.this, Home.class);
                startActivity(i);
            }
        });

        final ArrayList<MessagesModel> messagesModels=new ArrayList<>();
        final ChatAdapter chatAdapter=new ChatAdapter(messagesModels,this);
        binding.chatRecyclerViewa.setAdapter(chatAdapter);

        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        binding.chatRecyclerViewa.setLayoutManager(layoutManager);

        final String senderRoom= senderId+receiveId;
        final String receiverRoom=receiveId+senderId;
        database.getReference().child("chats").child(senderRoom).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messagesModels.clear();
                for(DataSnapshot snapshot1: snapshot.getChildren()){
                    MessagesModel model = snapshot1.getValue(MessagesModel.class);
                    messagesModels.add(model);
                }
                chatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        binding.send.setOnClickListener(new View.OnClickListener(){
            public void onClick(View view){
                String message = binding.editMessage.getText().toString();
                final MessagesModel model=new MessagesModel(senderId,message);
                model.setTimestamp(new Date().getTime());
                binding.editMessage.setText("");

                database.getReference().child("chats").child(senderRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        database.getReference().child("chats").child(receiverRoom).push().setValue(model).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {

                            }
                        });
                    }
                });
            }
        });
    }


}
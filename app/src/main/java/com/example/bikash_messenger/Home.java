package com.example.bikash_messenger;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bikash_messenger.Adapters.FragmentsAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;

public class Home extends AppCompatActivity {
    TextView logout;
    FirebaseAuth firebaseAuth;
    ViewPager viewPager;
    TabLayout tabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        viewPager = findViewById(R.id.viewpager);
        tabLayout = findViewById(R.id.tablayout);

        viewPager.setAdapter(new FragmentsAdapter(getSupportFragmentManager()));
        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                Toast.makeText(Home.this, "settings", LENGTH_SHORT).show();
                break;
            case R.id.log:
                firebaseAuth.signOut();
                Intent i = new Intent(Home.this, MainActivity.class);
                startActivity(i);
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}

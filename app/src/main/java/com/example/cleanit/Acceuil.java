package com.example.cleanit;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class Acceuil extends AppCompatActivity {
    Button vet;
    Button chauss;
    Button lit;
    DB db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acceuil);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#82BFE9")));
        getWindow().setStatusBarColor(getResources().getColor(R.color.statBar, this.getTheme()));
        getSupportActionBar().setTitle("CLEAN IT");
        db= new DB(this);
        vet= findViewById(R.id.vet);
        chauss = findViewById(R.id.chauss);
        lit = findViewById(R.id.lit);

        vet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Acceuil.this,Vetements.class));
            }
        });
        chauss.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Acceuil.this,Chaussures.class));
            }
        });
        lit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Acceuil.this,Literie.class));
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        String phone1 = getIntent().getExtras().getString("phone");
        Toast.makeText(this, "Selected Item: " +item.getTitle(), Toast.LENGTH_SHORT).show();
        switch (item.getItemId()) {
            case R.id.gopanier:
                Intent pan = new Intent(Acceuil.this,PanierActivity.class);
                startActivity(pan);
                break;
            case R.id.user:
                Intent i = new Intent(Acceuil.this,userProfileActivity.class);
                i.putExtra("phone1",phone1);
                startActivity(i);

            default:
                return super.onOptionsItemSelected(item);
        }
    return true;
    }
}
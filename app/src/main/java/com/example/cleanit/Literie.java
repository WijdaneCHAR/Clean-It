package com.example.cleanit;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Literie extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Article> myListData = new ArrayList<>();
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_literie);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#82BFE9")));
        getWindow().setStatusBarColor(getResources().getColor(R.color.statBar, this.getTheme()));
        getSupportActionBar().setTitle("Literie");
        db = new DB(this);

        ArrayList<Article> myListDatadb = new ArrayList<>();
        myListDatadb = db.getAllArticles();
        for (int i = 0; i < myListDatadb.size(); i++) {
            if(myListDatadb.get(i).getCategoryArticle().equals("literie")) {
                myListData.add(new Article(myListDatadb.get(i).getIdArticle(),myListDatadb.get(i).getNameArticle(), myListDatadb.get(i).getUnitPriceArticle(), myListDatadb.get(i).getCategoryArticle(), myListDatadb.get(i).getImgId()));
            }
        };

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewLit);
        ArticleAdapter adapter = new ArticleAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        recyclerView = findViewById(R.id.recyclerViewLit);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

    }
}

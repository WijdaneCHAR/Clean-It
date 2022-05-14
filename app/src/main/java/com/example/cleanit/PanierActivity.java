package com.example.cleanit;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PanierActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Article> myListData = new ArrayList<>();
    DB db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_panier);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#82BFE9")));
        getWindow().setStatusBarColor(getResources().getColor(R.color.statBar, this.getTheme()));
        getSupportActionBar().setTitle("Panier");
        recyclerView=findViewById(R.id.recyclerViewPanier);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        db = new DB(this);
        ArrayList<Article> myListDatadb = new ArrayList<>();
        myListDatadb = db.getAllArticlesPanier();
        for (int i = 0; i < myListDatadb.size(); i++) {
                myListData.add(new Article(myListDatadb.get(i).getIdArticle(),myListDatadb.get(i).getNameArticle(), myListDatadb.get(i).getUnitPriceArticle(),myListDatadb.get(i).getSoldPriceArticle(),myListDatadb.get(i).getQteArticle(), myListDatadb.get(i).getImgId()));
        }

        PanierAdapter adapter = new PanierAdapter(myListData);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setAdapter(adapter);
//        /*TextView totalCommande = findViewById(R.id.totalCommande);
//        totalCommande.setText(String.valueOf(db.getCommandLineQte()*db.getPriceArticle()));*/
        Button btn= (Button)findViewById(R.id.total);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(PanierActivity.this);
                dialog.setContentView(R.layout.popup_date);
                Button conf = dialog.findViewById(R.id.confirmer);
                conf.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        DB db = new DB(v.getContext());
                        TextView txt1 = findViewById(R.id.txtDC);
                        TextView txt2 = findViewById(R.id.txtDL);
                        DatePicker dP1 = dialog.findViewById(R.id.date_collecte);
                        DatePicker dP2 = dialog.findViewById(R.id.date_livraison);
                        Boolean CheckInsertDataService = db.UpdateCommandData(dP1.toString(),dP2.toString(),db.getIdUser());
                        if(CheckInsertDataService == true){
                            Toast toast =Toast.makeText(v.getContext(), "commande ajoutee",Toast.LENGTH_SHORT);
                            toast.show();
                        }

                        db.InsertCommandData("","",db.getIdUser());

                        txt1.setText("Date de collecte: "+ dP1.getDayOfMonth()+"/"+ (dP1.getMonth() + 1)+"/"+dP1.getYear());
                        txt2.setText("Date de livraison: "+ dP2.getDayOfMonth()+"/"+ (dP2.getMonth() + 1)+"/"+dP2.getYear());
                        dialog.dismiss();

                    }
                });
                dialog.show();

            }
        });
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_panier, menu);
        return true;


    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //Handle item selection
        switch (item.getItemId()) {

            case R.id.i2:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage("Voulez vous vraiment vider votre panier ?")
                        .setCancelable(false)
                        .setPositiveButton("Supprimer Tout", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerViewPanier);

                                db = new DB(getApplicationContext());

                                db.DeleteCommandLine();

                                Boolean RemoveCmdLine = db.DeleteCommand();
                                if(RemoveCmdLine == true){
                                    Toast toast =Toast.makeText(getApplicationContext(), "Tous les articles sont supprimes",Toast.LENGTH_SHORT);
                                    toast.show();
                                }
                                recyclerView.removeAllViewsInLayout();
                               //recyclerView.notify();
                                setContentView(R.layout.delete);
                            }
                        })
                        .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Vider mon Panier");
                alert.show();
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}


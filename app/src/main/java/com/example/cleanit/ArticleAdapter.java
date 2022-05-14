package com.example.cleanit;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class ArticleAdapter extends RecyclerView.Adapter<ArticleAdapter.ViewHolder>{

    private ArrayList<Article> listdata;
    private DB db;

    // RecyclerView recyclerView;
    public ArticleAdapter(ArrayList<Article> listdata) {
        this.listdata = listdata;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem= layoutInflater.inflate(R.layout.activity_article_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Article myListData = listdata.get(position);
        holder.name.setText(listdata.get(position).getNameArticle());
        holder.price.setText(String.valueOf(listdata.get(position).getUnitPriceArticle()));
        holder.idArticle.setText(String.valueOf(listdata.get(position).getIdArticle()));
        holder.pic.setImageBitmap(BitmapFactory.decodeByteArray(listdata.get(position).getImgId(),0,listdata.get(position).getImgId().length));

        holder.panier.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DB(v.getContext());
                int idTXT = Integer.parseInt(holder.idArticle.getText().toString());
                Boolean CheckInsertData = db.InsertCommandLineData(1, 0,db.getUnitPriceArticle(), idTXT, db.getIdCommand());
                Toast toast;
                if(CheckInsertData == true){
                    toast = Toast.makeText(v.getContext(), "Article ajoute", Toast.LENGTH_SHORT);
                }
            }
        });
    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView pic;
        public ImageView panier;
        public TextView name;
        public TextView price;
        public TextView qte;
        public TextView idArticle;
        public LinearLayout linearLayout;
        public Button plus;
        public Button moins;
        public ViewHolder(View itemView) {
            super(itemView);
            this.pic = (ImageView) itemView.findViewById(R.id.picpanier);
            this.name = (TextView) itemView.findViewById(R.id.namepanier);
            this.price = (TextView) itemView.findViewById(R.id.pricepanier);
            this.qte = (TextView) itemView.findViewById(R.id.qtepanier);
            this.idArticle = (TextView) itemView.findViewById(R.id.idArticle);
            this.panier = (ImageView) itemView.findViewById(R.id.panier);
            this.plus = itemView.findViewById(R.id.pluspanier);
            this.moins = itemView.findViewById(R.id.moinspanier);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLay);
        }
    }


}

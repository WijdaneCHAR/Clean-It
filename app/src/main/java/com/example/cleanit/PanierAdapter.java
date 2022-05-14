package com.example.cleanit;

import android.annotation.SuppressLint;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PanierAdapter extends RecyclerView.Adapter<PanierAdapter.ViewHolder> {
    private ArrayList<Article> listdata;
    DB db;


    public PanierAdapter(ArrayList<Article> listdata) {
        this.listdata = listdata;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View listItem = layoutInflater.inflate(R.layout.activity_panier_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(listItem);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        final Article myListData = listdata.get(position);
        holder.idService.setText(listdata.get(position).getIdArticle() + " ");
        holder.nameArticle.setText(listdata.get(position).getNameArticle());
        holder.prixUnit.setText(String.valueOf(listdata.get(position).getUnitPriceArticle()));
        holder.prixSold.setText(String.valueOf(listdata.get(position).getSoldPriceArticle()));
        holder.imageArticle.setImageBitmap(BitmapFactory.decodeByteArray(listdata.get(position).getImgId(), 0, listdata.get(position).getImgId().length));
        holder.qteArticle.setText(String.valueOf(listdata.get(position).getQteArticle()));
        holder.priceArticle.setText(String.valueOf(Integer.parseInt(holder.qteArticle.getText().toString()) * Integer.parseInt(holder.prixUnit.getText().toString())));

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DB(v.getContext());
                int plusQte = Integer.parseInt(holder.qteArticle.getText().toString());
                plusQte++;
                holder.qteArticle.setText(String.valueOf(plusQte));
                if (db.getDescriptionArticle() == 1) {
                    holder.priceArticle.setText(String.valueOf(Integer.parseInt(holder.qteArticle.getText().toString()) * db.getSoldPriceArticle()));
                    boolean check = db.UpdateQte(holder.idService.getText().toString(), Integer.parseInt(holder.qteArticle.getText().toString()));

                }
                if (db.getDescriptionArticle() == 0) {
                    holder.priceArticle.setText(String.valueOf(Integer.parseInt(holder.qteArticle.getText().toString()) * db.getUnitPriceArticle()));
                    boolean check = db.UpdateQte(holder.idService.getText().toString(), Integer.parseInt(holder.qteArticle.getText().toString()));

                }
            }
        });
        holder.moins.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DB(v.getContext());
                int subQte = Integer.parseInt(holder.qteArticle.getText().toString());
                subQte--;
                if (subQte <= 0) {
                    subQte = 1;
                }
                holder.qteArticle.setText(String.valueOf(subQte));
                if (db.getDescriptionArticle() == 1) {
                    holder.priceArticle.setText(String.valueOf(Integer.parseInt(holder.qteArticle.getText().toString()) * db.getSoldPriceArticle()));
                    boolean check = db.UpdateQte(holder.idService.getText().toString(), Integer.parseInt(holder.qteArticle.getText().toString()));

                }
                if (db.getDescriptionArticle() == 0) {
                    holder.priceArticle.setText(String.valueOf(Integer.parseInt(holder.qteArticle.getText().toString()) * db.getUnitPriceArticle()));
                    boolean check = db.UpdateQte(holder.idService.getText().toString(), Integer.parseInt(holder.qteArticle.getText().toString()));

                }
            }
        });
        holder.check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    db = new DB(buttonView.getContext());
                    db.UpdateCommandLineData(holder.idService.getText().toString(), 1, Integer.parseInt(holder.prixUnit.getText().toString()) + 2);
                    holder.prixUnit.setText(String.valueOf(listdata.get(position).getUnitPriceArticle()));
                    holder.prixSold.setText(String.valueOf(listdata.get(position).getSoldPriceArticle()));
                    holder.priceArticle.setText(String.valueOf(Integer.parseInt(holder.qteArticle.getText().toString()) * Integer.parseInt(holder.prixSold.getText().toString())));

                }
                else {
                    db = new DB(buttonView.getContext());
                    db.UpdateCommandLineData(holder.idService.getText().toString(), 0, db.getUnitPriceArticle());
                    holder.prixUnit.setText(String.valueOf(listdata.get(position).getUnitPriceArticle()));
                    holder.prixSold.setText(String.valueOf(listdata.get(position).getSoldPriceArticle()));
                    holder.priceArticle.setText(String.valueOf(Integer.parseInt(holder.qteArticle.getText().toString()) * Integer.parseInt(holder.prixUnit.getText().toString())));
                }
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db = new DB(v.getContext());

                //int idA = Integer.parseInt(holder.idService.getText().toString());
                Boolean removeOneArticlePanier = db.DeleteOneCommandLine(holder.idService.getText().toString());

                if (removeOneArticlePanier == true) {
                    listdata.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, listdata.size());
                    Toast toast = Toast.makeText(v.getContext(), "Article supprime", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

    }


    @Override
    public int getItemCount() {
        return listdata.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageArticle;
        public TextView nameArticle;
        public TextView priceArticle;
        public TextView qteArticle;
        public TextView prixUnit;
        public TextView prixSold;
        public TextView idService;
        public LinearLayout linearLayout;
        public RecyclerView recyclerView;
        public Button plus;
        public Button moins;
        public ImageView delete;
        public CheckBox check;

        public ViewHolder(View itemView) {
            super(itemView);
            this.imageArticle = itemView.findViewById(R.id.picpanier);
            this.nameArticle = itemView.findViewById(R.id.namepanier);
            this.prixUnit = itemView.findViewById(R.id.prixUnit);
            this.prixSold = itemView.findViewById(R.id.prixSold);
            this.priceArticle = itemView.findViewById(R.id.pricepanier);
            this.qteArticle = itemView.findViewById(R.id.qtepanier);
            this.idService = itemView.findViewById(R.id.idService);
            this.check = itemView.findViewById(R.id.check);
            this.plus = itemView.findViewById(R.id.pluspanier);
            this.moins = itemView.findViewById(R.id.moinspanier);
            this.delete = itemView.findViewById(R.id.delete);
            linearLayout = itemView.findViewById(R.id.linearLay);
        }
    }

}

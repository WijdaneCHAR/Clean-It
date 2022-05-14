package com.example.cleanit;

import android.widget.DatePicker;

public class Article {
    private String nameArticle;
    private String categoryArticle;
    private int unitPriceArticle;

    public int getSoldPriceArticle() {
        return soldPriceArticle ;
    }

    public void setSoldPriceArticle(int soldPriceArticle) {
        this.soldPriceArticle = soldPriceArticle;
    }

    private int soldPriceArticle;
    private int qte;
    private byte[] imgId;
    private int priceArticle;
    private int total;
    private int idArticle;
    private boolean description;
    private DatePicker dateCollecte;
    private DatePicker dateLivraison;
    private int image;

    public Article(String nameArticle, int unitPriceArticle, byte[] imgId) {
        this.nameArticle = nameArticle;
        this.unitPriceArticle = unitPriceArticle;
        this.imgId = imgId;
    }

    public Article(int idArticle, String nameArticle, int unitPriceArticle, byte[] imgId) {
        this.idArticle = idArticle;
        this.nameArticle = nameArticle;
        this.unitPriceArticle = unitPriceArticle;
        this.imgId = imgId;
    }

    public Article(int idArticle, String nameArticle, int unitPriceArticle, int soldPriceArticle, int qte, byte[] imgId) {
        this.idArticle = idArticle;
        this.nameArticle = nameArticle;
        this.soldPriceArticle = soldPriceArticle;
        this.unitPriceArticle = unitPriceArticle;
        this.imgId = imgId;
        this.qte = qte;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public Article(int idArticle,String nameArticle, int unitPriceArticle,String categoryArticle, byte[] imgId) {
        this.idArticle = idArticle;
        this.nameArticle = nameArticle;
        this.unitPriceArticle = unitPriceArticle;
        this.imgId = imgId;
        this.categoryArticle=categoryArticle;
    }

    public String getNameArticle() {
        return nameArticle;
    }
    public void setNameArticle(String nameArticle) {
        this.nameArticle = nameArticle;
    }

    public String getCategoryArticle() {
        return categoryArticle;
    }
    public void setCategoryArticle(String categoryArticle) {
        this.categoryArticle = categoryArticle;
    }

    public int getIdArticle() {
        return idArticle;
    }
    public void setIdArticle(int idArticle) {
        this.idArticle = idArticle;
    }

    public DatePicker getDateLivraison() {
        return dateLivraison;
    }

    public void setDateLivraison(DatePicker dateLivraison) {
        this.dateLivraison = dateLivraison;
    }
    public int getUnitPriceArticle() {
        return unitPriceArticle;
    }
    public void setUnitPriceArticle(int priceArticle) {
        this.unitPriceArticle = unitPriceArticle;
    }
    public DatePicker getDateCollecte() {
        return dateCollecte;
    }

    public void setDateCollecte(DatePicker dateCollecte) {
        this.dateCollecte = dateCollecte;
    }

    public int getPriceArticle() {
        return priceArticle;
    }

    public void setPriceArticle(int priceArticle) {
        this.priceArticle = priceArticle;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public boolean isDescription() {
        return description;
    }

    public void setDescription(boolean description) {
        this.description = description;
    }

    public void setQte(int qte) {
        this.qte = qte;
    }
    public int getQteArticle() {
        return qte;
    }

    public byte[] getImgId() {
        return imgId;
    }
    public void setImgId(byte[] imgId) {
        this.imgId = imgId;
    }
}


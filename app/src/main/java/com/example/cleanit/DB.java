package com.example.cleanit;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

public class DB extends SQLiteOpenHelper {

    private static final String table_user = "user";
    private static final String table_service = "service";
    private static final String table_command = "command";
    private static final String table_command_line = "command_line";

    public DB(Context context) {
        super(context, "cleanit.sqlite", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("create Table user(id_user INTEGER primary key AUTOINCREMENT,fullname TEXT,phone TEXT,email TEXT,address TEXT,password TEXT)");
        db.execSQL("create Table " + table_user + "(id_user INTEGER primary key AUTOINCREMENT,fullname TEXT,phone TEXT,email TEXT,address TEXT," +
                "password TEXT)");
        db.execSQL("create Table " + table_command + "(id_command INTEGER primary key AUTOINCREMENT," +
                " dateCollecte String, dateLivraison String,id_user INTEGER," +
                " FOREIGN KEY (id_user) REFERENCES " + table_user + "(id_user))");
        db.execSQL("create Table " + table_service + "(id_service INTEGER primary key AUTOINCREMENT,servicename TEXT,unitprice INTEGER," +
                "category TEXT)");

        db.execSQL("create Table " + table_command_line + "(id_command_line INTEGER primary key AUTOINCREMENT, quantity INTEGER," +
                " description INTEGER,soldPrice INTEGER, id_command INTEGER,id_service INTEGER, FOREIGN KEY (id_command) REFERENCES " + table_command +
                "(id_command), FOREIGN KEY (id_service) REFERENCES " + table_service + "(id_service))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists " + table_user);
        db.execSQL("drop Table if exists " + table_service);
        db.execSQL("drop Table if exists " + table_command);
        onCreate(db);
    }

    //Add user to database
    public Boolean InsertUserData(String fullname, String email, String password, String phone, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname", fullname);
        contentValues.put("email", email);
        contentValues.put("phone", phone);
        contentValues.put("address", address);
        contentValues.put("password", password);
        long result = db.insert("user", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    //Add command
    public Boolean InsertCommandData(String dateCollecte, String dateLivraison, int id_user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("dateCollecte", dateCollecte);
        contentValues.put("dateLivraison", dateLivraison);
        contentValues.put("id_user", id_user);
        long result = db.insert("command", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }


    //get articals
    public ArrayList<Article> getAllArticles() {
        ArrayList<Article> articles = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM service", null);
        //le code pour travailler avec le cursor:
        //check le cursor si il contient les datas
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int idService = cursor.getInt(cursor.getColumnIndex("id_service"));
                @SuppressLint("Range") String servicename = cursor.getString(cursor.getColumnIndex("servicename"));
                @SuppressLint("Range") int unitprice = cursor.getInt(cursor.getColumnIndex("unitprice"));
                @SuppressLint("Range") String category = cursor.getString(cursor.getColumnIndex("category"));
                @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));

                Article r = new Article(idService, servicename, unitprice, category, image);
                articles.add(r);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return articles;
    }

    public ArrayList<Article> getAllArticlesPanier() {
        ArrayList<Article> articles = new ArrayList<>();

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM service INNER JOIN command_line WHERE service.id_service = command_line.id_service", null);
        //le code pour travailler avec le cursor:
        //check le cursor si il contient les datas
        if (cursor != null && cursor.moveToFirst()) {
            do {
                @SuppressLint("Range") int idService = cursor.getInt(cursor.getColumnIndex("id_service"));
                @SuppressLint("Range") String servicename = cursor.getString(cursor.getColumnIndex("servicename"));
                @SuppressLint("Range") int qte = cursor.getInt(cursor.getColumnIndex("quantity"));
                @SuppressLint("Range") int unitPrice = cursor.getInt(cursor.getColumnIndex("unitprice"));
                @SuppressLint("Range") int soldPrice = cursor.getInt(cursor.getColumnIndex("soldPrice"));
                @SuppressLint("Range") byte[] image = cursor.getBlob(cursor.getColumnIndex("image"));
                Article r = new Article(idService, servicename,unitPrice, soldPrice,qte, image);
                articles.add(r);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return articles;
    }

    @SuppressLint("Range")
    public int getIdCommand() {
        int idCommand = 0;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM command", null);
        //le code pour travailler avec le cursor:
        //check le cursor si il contient les datas
        if (cursor != null && cursor.moveToFirst()) {
            do {
                idCommand = cursor.getInt(cursor.getColumnIndex("id_command"));

            } while (cursor.moveToNext());
            cursor.close();
        }
        return idCommand;
    }

    @SuppressLint("Range")
    public int getUnitPriceArticle() {
        int price = 0;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM service", null);
        //le code pour travailler avec le cursor:
        //check le cursor si il contient les datas
        if (cursor != null && cursor.moveToFirst()) {
            do {
                price = cursor.getInt(cursor.getColumnIndex("unitprice"));

            } while (cursor.moveToNext());
            cursor.close();
        }
        return price;
    }

    @SuppressLint("Range")
    public int getSoldPriceArticle() {
        int price = 0;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM command_line", null);
        //le code pour travailler avec le cursor:
        //check le cursor si il contient les datas
        if (cursor != null && cursor.moveToFirst()) {
            do {
                price = cursor.getInt(cursor.getColumnIndex("soldPrice"));

            } while (cursor.moveToNext());
            cursor.close();
        }
        return price;
    }

    @SuppressLint("Range")
    public int getDescriptionArticle() {
        int desc = 0;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM command_line", null);
        //le code pour travailler avec le cursor:
        //check le cursor si il contient les datas
        if (cursor != null && cursor.moveToFirst()) {
            do {
                desc = cursor.getInt(cursor.getColumnIndex("description"));

            } while (cursor.moveToNext());
            cursor.close();
        }
        return desc;
    }

    @SuppressLint("Range")
    public int getIdUser() {
        int idUser = 0;

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM user", null);
        //le code pour travailler avec le cursor:
        //check le cursor si il contient les datas
        if (cursor != null && cursor.moveToFirst()) {
            do {
                idUser = cursor.getInt(cursor.getColumnIndex("id_user"));

            } while (cursor.moveToNext());
            cursor.close();
        }
        return idUser;
    }

    //Add commndes to table-commnd_line
    public Boolean InsertCommandLineData(int quantity, int description,int soldPrice, int id_service, int id_command) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("quantity", quantity);
        contentValues.put("description", description);
        contentValues.put("soldPrice", soldPrice);
        contentValues.put("id_service", id_service);
        contentValues.put("id_command", id_command);

        long result = db.insert("command_line", null, contentValues);
        if (result == -1)
            return false;
        else
            return true;
    }

    public Boolean DeleteOneCommandLine(String id_service) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from command_line where id_service = ?", new String[]{String.valueOf(id_service)});
        if (cursor.getCount() > 0) {
            long result = db.delete("command_line", "id_service=?", new String[]{String.valueOf(id_service)});
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }

    }

    public Boolean DeleteCommandLine() {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("command_line", null, null);
        db.execSQL("delete from command_line");
        db.close();
        if (result == -1)
            return false;
        else
            return true;
    }

    public Boolean DeleteCommand() {
        SQLiteDatabase db = this.getWritableDatabase();
        long result = db.delete("command", null, null);
        db.execSQL("delete from command");
        db.close();
        if (result == -1)
            return false;
        else
            return true;
    }

    public Boolean UpdateCommandData(String dateCollecte, String dateLivraison, int idUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("dateCollecte", dateCollecte);
        contentValues.put("dateLivraison", dateLivraison);

        Cursor cursor = db.rawQuery("Select * from command", null);
        if (cursor.getCount() > 0) {
            long result = db.update("command", contentValues, "id_user=?", new String[]{String.valueOf(idUser)});
            if (result == -1)
                return false;
            else
                return true;
        } else
            return false;
    }

    public Boolean UpdateCommandLineData(String id_service, int description,int soldPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("description", description);
        contentValues.put("soldPrice", soldPrice);

        Cursor cursor = db.rawQuery("Select * from command_line", null);
        if (cursor.getCount() > 0) {
            long result = db.update("command_line", contentValues, "id_service=?", new String[]{String.valueOf(id_service)});
            if (result == -1)
                return false;
            else
                return true;
        } else
            return false;
    }

    public Boolean UpdateQte(String id_service, int qte) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("quantity", qte);

        Cursor cursor = db.rawQuery("Select * from command_line", null);
        if (cursor.getCount() > 0) {
            long result = db.update("command_line", contentValues, "id_service=?", new String[]{id_service});
            if (result == -1)
                return false;
            else
                return true;
        } else
            return false;
    }

    public Boolean UpdateServiceData(String id_service, int unitPrice) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("unitPrice", unitPrice);

        Cursor cursor = db.rawQuery("Select * from service", null);
        if (cursor.getCount() > 0) {
            long result = db.update("service", contentValues, "id_service=?", new String[]{String.valueOf(id_service)});
            if (result == -1)
                return false;
            else
                return true;
        } else
            return false;
    }

    public Boolean UpdateUserData(String fullname, String email, String phone, String Nphone, String address) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("fullname", fullname);
        contentValues.put("email", email);
        contentValues.put("phone", Nphone);
        contentValues.put("address", address);

        Cursor cursor = db.rawQuery("Select * from user where phone = ?", new String[]{phone});
        if (cursor.getCount() > 0) {
            long result = db.update("user", contentValues, "phone=?", new String[]{phone});
            if (result == -1)
                return false;
            else
                return true;
        } else
            return false;
    }

    public Boolean ChangePassword(String password, String phone) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", md5(password));
        Cursor cursor = db.rawQuery("Select * from user where phone = ?", new String[]{phone});
        if (cursor.getCount() > 0) {
            long result = db.update("user", contentValues, "phone=?", new String[]{phone});
            if (result == -1)
                return false;
            else
                return true;
        } else
            return false;

    }

    //password hashing
    public String md5(String s) {
        try {
            // Create MD5 Hash
            MessageDigest digest = java.security.MessageDigest.getInstance("MD5");
            digest.update(s.getBytes());
            byte messageDigest[] = digest.digest();

            // Create Hex String
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }

//    public Boolean DeleteUserData(String email){
//        SQLiteDatabase db = this.getWritableDatabase();
//
//        Cursor cursor = db.rawQuery("Select * from UserDetails where Email = ?", new String[] {email});
//        if(cursor.getCount() >0) {
//            long result = db.delete("UserDetails", "Email=?", new String[]{email});
//            if (result == -1)
//                return false;
//            else
//                return true;
//        }
//        else
//            return false;
//    }

    public Cursor getData() {

        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("Select * from user", null);
    }

//public Cursor getData ()
//{
//    SQLiteDatabase DB = this.getWritableDatabase();
//    Cursor cursor = DB.rawQuery("Select * from user", null);
//    return cursor;
//}

//    public Boolean checkusername(String phone) {
//        SQLiteDatabase MyDB = this.getWritableDatabase();
//        Cursor cursor = MyDB.rawQuery("Select * from user where phone = ?", new String[]{phone});
//        if (cursor.getCount() > 0)
//            return true;
//        else
//            return false;
//    }

    public Boolean checkusernamepassword(String phone, String password) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("Select * from user where phone = ? and password = ?", new String[]{phone, password});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }
}

package com.example.cleanit;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class userProfileActivity extends AppCompatActivity {

    private EditText fullname, phone, email, address;
    private Button update,save,logout,changePassword;
    private DB db;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_user_profile);

        fullname = findViewById(R.id.fullname);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);

        save = findViewById(R.id.save);
        //save.setEnabled(false);
        db = new DB(this);

        Cursor res= db.getData();
       // String emailTXT = email.getText().toString();
        String A = "jsj";
        String B="";
        String C="";
        String D="";
        String phonefromactiv = getIntent().getExtras().getString("phone1");
        while(res.moveToNext()){
            if(res.getString(2).equals(phonefromactiv)){
                A = res.getString(1);
                B = res.getString(2);
                C = res.getString(3);
                D = res.getString(4);
            }
        }
        fullname.setText(A);
        email.setText(C);
        phone.setText(B);
        address.setText(D);

        //disable editText
//        fullname.setEnabled(false);
//        email.setEnabled(false);
//        phone.setEnabled(false);
//        address.setEnabled(false);

        save = findViewById(R.id.save);
//        update = findViewById(R.id.edit);
//        update.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                fullname.setEnabled(true);
//                email.setEnabled(true);
//                phone.setEnabled(true);
//                address.setEnabled(true);
//
//                save.setEnabled(true);
//
//            }        });


        builder = new AlertDialog.Builder(this);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Uncomment the below code to Set the message and title from the strings.xml file
                //builder.setMessage(R.string.dialog_message) .setTitle(R.string.dialog_title);

                //Setting message manually and performing action on button click
                builder.setMessage("Voulez vous enregistrer les modifications effectuées ?")
                        .setCancelable(false)
                        .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                String nameTXT = fullname.getText().toString();
                                String emailTXT = email.getText().toString();
                                String phoneTXT = phone.getText().toString();
                                String addressTXT = address.getText().toString();

                                if(!validateEmail()){
                                    Toast.makeText(userProfileActivity.this,"Entrer un email valide!",Toast.LENGTH_SHORT).show();
                                }
                                else if(!validatePhone()){
                                    Toast.makeText(userProfileActivity.this,"Entrer un numéro de téléphone valide!",Toast.LENGTH_SHORT).show();
                                }else{
                                    Boolean checkupdatedata = db.UpdateUserData(nameTXT,emailTXT,phonefromactiv,phoneTXT,addressTXT);
                                    if(checkupdatedata==true)
                                        Toast.makeText(userProfileActivity.this, "Modifications enregistrées!", Toast.LENGTH_SHORT).show();
                                    else
                                        Toast.makeText(userProfileActivity.this, "Modifications non enregistrées!", Toast.LENGTH_SHORT).show();
                                }

                            }
                        })
                        .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //  Action for 'NO' Button
                                dialog.cancel();
                                Toast.makeText(getApplicationContext(),"Modifications annulées!",
                                        Toast.LENGTH_SHORT).show();
//                                fullname.setEnabled(false);
//                                email.setEnabled(false);
//                                phone.setEnabled(false);
//                                address.setEnabled(false);
//                                save.setEnabled(false);
                            }
                        });
                //Creating dialog box
                AlertDialog alert = builder.create();
                //Setting the title manually
                alert.setTitle("Veuillez confirmer vos modifications!");
                alert.show();
            }
        });

        /*save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String nameTXT = fullname.getText().toString();
                String emailTXT = email.getText().toString();
                String phoneTXT = phone.getText().toString();
                String addressTXT = address.getText().toString();

                Boolean checkupdatedata = db.UpdateUserData(nameTXT,emailTXT,phonefromactiv,phoneTXT,addressTXT);
                if(checkupdatedata==true)
                    Toast.makeText(userProfileActivity.this, "Entry Updated", Toast.LENGTH_SHORT).show();
                else
                    Toast.makeText(userProfileActivity.this, "New Entry Not Updated", Toast.LENGTH_SHORT).show();

                fullname.setEnabled(false);
                email.setEnabled(false);
                phone.setEnabled(false);
                address.setEnabled(false);
                save.setEnabled(false);

            }        });*/


        logout = findViewById(R.id.logout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent  = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        changePassword = findViewById(R.id.changePassword);
        //String p = getIntent().getExtras().getString("phone");
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(userProfileActivity.this,ChangePasswordActivity.class);
                i.putExtra("p",phonefromactiv);
                startActivity(i);
            }
        });

    }

    private boolean validateEmail() {
        String emailInput = email.getText().toString().trim();
        if (!Patterns.EMAIL_ADDRESS.matcher(emailInput).matches()) {
            return false;
        } else {
            return true;
        }
    }
    private boolean validatePhone() {
        String phoneInput = phone.getText().toString().trim();
        if (phoneInput.length()<10) {
            return false;
        }
        else if (!Patterns.PHONE.matcher(phoneInput).matches()) {
            return false;
        } else {
            return true;
        }
    }
}


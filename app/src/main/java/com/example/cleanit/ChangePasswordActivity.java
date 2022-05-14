package com.example.cleanit;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class ChangePasswordActivity extends AppCompatActivity {

    private Button changePassword;
    private EditText pass,newPassword;
    private DB db;
    private TextView confirmPassError;
    AlertDialog.Builder builder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        getSupportActionBar().hide();

        db = new DB(this);
        changePassword = findViewById(R.id.btnChange);
        pass = findViewById(R.id.password);
        newPassword = findViewById(R.id.newPassword);
        confirmPassError = findViewById(R.id.confirmPassError);

        newPassword.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(!validatePassword()){
                    validatePassword();
                }

                return false;
            }
        });
        String p = getIntent().getExtras().getString("p");
        builder = new AlertDialog.Builder(this);
//        changePassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                String passwordTXT = pass.getText().toString();
//                Boolean ChangePass = db.ChangePassword(passwordTXT,p);
//                if(!validatePassword()){
//                    validatePassword();
//                }
//                else if(ChangePass==true) {
//                    Toast.makeText(ChangePasswordActivity.this, "Mot de passe modifé!", Toast.LENGTH_SHORT).show();
//                    startActivity(new Intent(ChangePasswordActivity.this, com.example.cleanit.userProfileActivity.class));
//                }else {
//                    validatePassword();
//                    Toast.makeText(ChangePasswordActivity.this, "Echec de changement de mot de passe", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String passwordTXT = pass.getText().toString();
                Boolean ChangePass = db.ChangePassword(passwordTXT,p);

                if(passwordTXT.equals("") || ChangePass.equals("")){
                    Toast.makeText(ChangePasswordActivity.this, "Please enter all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    builder.setMessage("Voulez vous modifier votre mot de passe ?")
                            .setCancelable(false)
                            .setPositiveButton("Oui", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
//
                                    if(!validatePassword()){
                                        validatePassword();
                                    }
                                    else if(ChangePass==true) {
                                        Toast.makeText(ChangePasswordActivity.this, "Mot de passe modifé!", Toast.LENGTH_SHORT).show();
                                        startActivity(new Intent(ChangePasswordActivity.this, userProfileActivity.class));
                                    }else {
                                        validatePassword();
                                        Toast.makeText(ChangePasswordActivity.this, "Echec de changement de mot de passe", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            })
                            .setNegativeButton("Non", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //  Action for 'NO' Button
                                    dialog.cancel();
                                    Toast.makeText(getApplicationContext(),"Modifications annulées!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            });
                    //Creating dialog box
                    AlertDialog alert = builder.create();
                    //Setting the title manually
                    alert.setTitle("Veuillez confirmer la modification de votre mot de passe!");
                    alert.show();
                }
            }
        });
    }

    private boolean validatePassword() {
        String passwordInput = pass.getText().toString().trim();
        String ConfirmpasswordInput = newPassword.getText().toString().trim();

        if (passwordInput.length()<5) {
            Toast.makeText(ChangePasswordActivity.this, "Mot de passe doit contenir au moins 5 caractères", Toast.LENGTH_SHORT).show();
            return false;
        }
        else if (!passwordInput.equals(ConfirmpasswordInput)) {
            confirmPassError.setText("Password Would Not be matched");
            return false;
        }else {
            confirmPassError.setText("Password Matched");
            return true;
        }
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
            for (int i=0; i<messageDigest.length; i++)
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            return hexString.toString();

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "";
    }
}
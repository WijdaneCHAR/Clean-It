package com.example.cleanit;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {

    private EditText fullname, phone, email,address,password,confirmpassword;
    private TextView confirmPassError;
    private Button insert;
    private DB db;

    private Context mContext;
    private Activity mActivity;

    private ConstraintLayout mConstraintLayout;
    private Button mButton;

    private PopupWindow mPopupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        getWindow().setStatusBarColor(getResources().getColor(R.color.statBar, this.getTheme()));
        //insert user data
        fullname = findViewById(R.id.fullname);
        password = findViewById(R.id.password);
        confirmpassword = findViewById(R.id.newPassword);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);
        address = findViewById(R.id.address);
        //error message
        confirmPassError=findViewById(R.id.ConfirmPassAlert);

        insert = findViewById(R.id.btnRegister);
        db = new DB(this);

        // Get the application context
        mContext = getApplicationContext();

        mConstraintLayout = (ConstraintLayout) findViewById(R.id.rl);

        // Get the activity
        mActivity = RegisterActivity.this;
//        insert.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Initialize a new instance of LayoutInflater service
//                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
//
//                // Inflate the custom layout/view
//                View customView = inflater.inflate(R.layout.custom_layout,null);
//
//                /*
//                    public PopupWindow (View contentView, int width, int height)
//                        Create a new non focusable popup window which can display the contentView.
//                        The dimension of the window must be passed to this constructor.
//
//                        The popup does not provide any background. This should be handled by
//                        the content view.
//
//                    Parameters
//                        contentView : the popup's content
//                        width : the popup's width
//                        height : the popup's height
//                */
//                // Initialize a new instance of popup window
//                mPopupWindow = new PopupWindow(
//                        customView,
//                        LayoutParams.WRAP_CONTENT,
//                        LayoutParams.WRAP_CONTENT
//                );
//
//                // Set an elevation value for popup window
//                // Call requires API level 21
//                if(Build.VERSION.SDK_INT>=21){
//                    mPopupWindow.setElevation(5.0f);
//                }
//
//                // Get a reference for the custom view close button
//                ImageButton closeButton = (ImageButton) customView.findViewById(R.id.ib_close);
//
//                // Set a click listener for the popup window close button
//                closeButton.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        // Dismiss the popup window
//                        mPopupWindow.dismiss();
//                    }
//                });
//
//                // Finally, show the popup window at the center location of root relative layout
//                mPopupWindow.showAtLocation(mConstraintLayout, Gravity.CENTER, 0, 0);
//                Spinner spinner = (Spinner) findViewById(R.id.spinner);
//               // String questions[] = {"Your childhood friend's name","Your favorite drink"};
//// Create an ArrayAdapter using the string array and a default spinner layout
////                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(v.getContext(),
////                        R.array.secret_questions, android.R.layout.simple_spinner_item);
//                ArrayAdapter adapter = new ArrayAdapter(v.getContext(), android.R.layout.simple_spinner_item);
//// Specify the layout to use when the list of choices appears
//                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//// Apply the adapter to the spinner
//
////                adapter.add("Your childhood friend's name");
//////                adapter.add("item 2");
////                spinner.setAdapter(adapter);
//            }
//
//
//        });



        insert.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                String fullnameTXT = fullname.getText().toString();
                String emailTXT = email.getText().toString();
                String passTXT = md5(password.getText().toString());
                String confirmpassTXT = md5(confirmpassword.getText().toString());
//                String passTXT = (password.getText().toString());
//                String confirmpassTXT = (confirmpassword.getText().toString());
                String phoneTXT = phone .getText().toString();
                String addressTXT = address .getText().toString();

//                Boolean CheckInsertData = db.InsertUserData(fullnameTXT,emailTXT,passTXT,phoneTXT,addressTXT);
                if(phoneTXT.equals("")||passTXT.equals("")||emailTXT.equals("")||addressTXT.equals("")||fullnameTXT.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Veuillez remplir tous les champs!", Toast.LENGTH_SHORT).show();
                }
                else if(!validatePassword()){
                        validatePassword();
                }
                else if(!validateEmail()){
                        Toast.makeText(RegisterActivity.this,"Entrer un email valide!",Toast.LENGTH_SHORT).show();
                }
                else if(!validatePhone()){
                        Toast.makeText(RegisterActivity.this,"Entrer un numéro de téléphone valide!",Toast.LENGTH_SHORT).show();
                }
                else {
                    Boolean CheckInsertData = db.InsertUserData(fullnameTXT, emailTXT, passTXT, phoneTXT, addressTXT);
                    if (CheckInsertData == true) {
                        Toast.makeText(RegisterActivity.this, "Compte créé!", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(RegisterActivity.this, com.example.cleanit.LoginActivity.class));
                    } else {
                        Toast.makeText(RegisterActivity.this, "Erreur lors de la création du compte!", Toast.LENGTH_SHORT).show();
                    }
                }



                db.InsertCommandData("","",db.getIdUser());

            }
        });

        TextView btn=findViewById(R.id.alreadyHaveAccount);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this, LoginActivity.class));
                finish();
            }
        });

    }
    private boolean validatePassword() {
        String passwordInput = password.getText().toString().trim();
        String ConfirmpasswordInput = confirmpassword.getText().toString().trim();

        if (passwordInput.length()<5) {
            confirmPassError.setText("Mot de passe doit contenir au moins 5 caractères");
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

    public static boolean isValidPassword(final String password) {

        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

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
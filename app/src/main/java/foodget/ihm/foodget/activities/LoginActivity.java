package foodget.ihm.foodget.activities;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.security.MessageDigest;

import foodget.ihm.foodget.DatabaseHelper;
import foodget.ihm.foodget.R;
import foodget.ihm.foodget.models.User;
import foodget.ihm.foodget.services.NotificationService;

public class LoginActivity extends AppCompatActivity{
    EditText mTextUserName;
    EditText mTextPassword;
    Button mLoginButton;
    TextView mTextViewRegister;
    DatabaseHelper db;
    private User loggedUser;
    private String TAG = "Login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        printHashKey(this);

        mTextUserName = findViewById(R.id.textUser);
        mTextPassword = findViewById(R.id.textPass);
        mLoginButton = findViewById(R.id.loginButton);
        mTextViewRegister = findViewById(R.id.registerText);
        db = new DatabaseHelper(this);

        Intent i = new Intent(LoginActivity.this, NotificationService.class);
        startService(i);


        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mTextUserName.getText().toString().trim();
                String pass= mTextPassword.getText().toString().trim();
                User res = db.checkUser(user, pass);
                Log.d(TAG, "Utilisateur checked : " + user);
                if(user.equals("") || pass.equals("")) {
                    Toast.makeText(LoginActivity.this, "Nom d'utilisateur ou Mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (res != null) {
                        Toast.makeText(LoginActivity.this, "Connecté avec succès !", Toast.LENGTH_SHORT).show();
                        loggedUser = res;
                        Intent loginIntent = new Intent(getApplicationContext(), ManagementActivity.class);
                        loginIntent.putExtra("user",loggedUser);
                        startActivity(loginIntent);
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Nom d'utilisateur ou Mot de passe incorrect", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
//    public static void printHashKey(Context context) {
//        try {
//            final PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
//            for (android.content.pm.Signature signature : info.signatures) {
//                final MessageDigest md = MessageDigest.getInstance("SHA");
//                md.update(signature.toByteArray());
//                final String hashKey = new String(Base64.encode(md.digest(), 0));
//                Log.i("AppLog", "key:" + hashKey + "=");
//            }
//        } catch (Exception e) {
//            Log.e("AppLog", "error:", e);
//        }
//    }
}
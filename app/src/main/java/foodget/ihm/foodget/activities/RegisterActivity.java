package foodget.ihm.foodget.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import foodget.ihm.foodget.DatabaseHelper;
import foodget.ihm.foodget.R;
import foodget.ihm.foodget.StaticContentUsers;
import foodget.ihm.foodget.models.Alert;
import foodget.ihm.foodget.models.Alerts;
import foodget.ihm.foodget.models.User;

public class RegisterActivity extends AppCompatActivity {

    String TAG = "register";
    EditText mTextUserName;
    EditText mTextPassword;
    EditText mConfirmPassword;
    Button mRegisterButton;
    TextView mTextViewLogin;
    EditText mTextEmail;
    EditText mTextFName;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mTextUserName = findViewById(R.id.textUser);
        mTextPassword = findViewById(R.id.textPass);
        mTextEmail = findViewById(R.id.textEmail);
        mTextFName = findViewById(R.id.textFname);
        mConfirmPassword = findViewById(R.id.textPassConfirmation);
        mRegisterButton = findViewById(R.id.registerButton);
        mTextViewLogin = findViewById(R.id.loginText);
        db = new DatabaseHelper(this);

        mTextViewLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent loginIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(loginIntent);
            }
        });

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = mTextUserName.getText().toString().trim();
                String password = mTextPassword.getText().toString().trim();
                String email = mTextEmail.getText().toString().trim();
                String fName = mTextFName.getText().toString().trim();
                String confPassword = mConfirmPassword.getText().toString().trim();
                if (username.equals("") || password.equals("") || email.equals("") || fName.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Tous les champs sont obligatoires !", Toast.LENGTH_SHORT).show();
                } else {
                    if (!password.equals(confPassword)) {
                        Toast.makeText(RegisterActivity.this, "Mots de passes différents !", Toast.LENGTH_SHORT).show();
                    } else {
                        User newUser = new User(username, password, email, fName, 0);
                        long val = StaticContentUsers.createUserInDataBase(newUser, db);
                        Log.d(TAG, "Retour création" + val);
                        Log.d(TAG, " Liste utilisateurs : " + StaticContentUsers.getRegisteredUsers());
                        if (val > 0) {
                            Toast.makeText(RegisterActivity.this, "Inscription réussie !", Toast.LENGTH_SHORT).show();
                            Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                            db.addAlert(new Alert(Alerts.WELCOME.toString().replace("%username%", username),
                                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("d/MM/yy HH:mm", Locale.FRANCE))), newUser);
                            startActivity(registerIntent);
                        } else {
                            Toast.makeText(RegisterActivity.this, "ERREUR !", Toast.LENGTH_SHORT).show();
                        }

                    }
                }


            }
        });
    }
}

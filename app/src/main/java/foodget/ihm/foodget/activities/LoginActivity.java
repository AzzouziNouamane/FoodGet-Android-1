package foodget.ihm.foodget.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import foodget.ihm.foodget.DatabaseHelper;
import foodget.ihm.foodget.R;
import foodget.ihm.foodget.models.User;

public class LoginActivity extends AppCompatActivity{
    EditText mTextUserName;
    EditText mTextPassword;
    Button mLoginButton;
    TextView mTextViewRegister;
    DatabaseHelper db;
    private User loggedUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTextUserName = (EditText)findViewById(R.id.textUser);
        mTextPassword = (EditText)findViewById(R.id.textPass);
        mLoginButton = (Button)findViewById(R.id.loginButton);
        mTextViewRegister = (TextView)findViewById(R.id.registerText);
        db = new DatabaseHelper(this);

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
                boolean res = db.checkUser(user, pass);
                if(user.equals("") || pass.equals("")) {
                    Toast.makeText(LoginActivity.this, "Nom d'utilisateur ou Mot de passe incorrecte", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(res) {
                        Toast.makeText(LoginActivity.this, "Successfully Logged IN", Toast.LENGTH_SHORT).show();
                        loggedUser = db.userLogged(user, pass);
                        Intent loginIntent = new Intent(getApplicationContext(), ManagementActivity.class);
                        loginIntent.putExtra("user",loggedUser);
                        startActivity(loginIntent);
                    }
                    else {
                        Toast.makeText(LoginActivity.this, "Wrong username/password", Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
    }
}
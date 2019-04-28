package foodget.ihm.foodget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class RegisterActivity extends AppCompatActivity {

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

        mTextUserName = (EditText)findViewById(R.id.textUser);
        mTextPassword = (EditText)findViewById(R.id.textPass);
        mTextEmail = (EditText)findViewById(R.id.textEmail);
        mTextFName = (EditText)findViewById(R.id.textFname);
        mConfirmPassword = (EditText)findViewById(R.id.textPassConfirmation);
        mRegisterButton = (Button)findViewById(R.id.registerButton);
        mTextViewLogin = (TextView)findViewById(R.id.loginText);
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
                if(username.equals("") || password.equals("") || email.equals("") || fName.equals("")) {
                    Toast.makeText(RegisterActivity.this, "Tous les champs sont obligatoires !", Toast.LENGTH_SHORT).show();
                }
                else {
                    if(!password.equals(confPassword)) {
                        Toast.makeText(RegisterActivity.this, "Mots de passes différents !", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        User newUser = new User(username, password, email, fName);
                        long val = db.addUser(newUser);
                        if( val > 0) {
                            Toast.makeText(RegisterActivity.this, "Inscription réussie !", Toast.LENGTH_SHORT).show();
                            Intent registerIntent = new Intent(RegisterActivity.this, LoginActivity.class);
                            startActivity(registerIntent);
                        }
                        else {
                            Toast.makeText(RegisterActivity.this, "ERREUR !", Toast.LENGTH_SHORT).show();
                        }

                    }
                }


            }
        });
    }
}

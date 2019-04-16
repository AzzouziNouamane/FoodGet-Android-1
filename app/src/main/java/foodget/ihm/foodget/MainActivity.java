package foodget.ihm.foodget;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    EditText mTextUserName;
    EditText mTextPassword;
    Button mLoginButton;
    TextView mTextViewRegister;
    DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mTextUserName = (EditText)findViewById(R.id.textUser);
        mTextPassword = (EditText)findViewById(R.id.textPass);
        mLoginButton = (Button)findViewById(R.id.loginButton);
        mTextViewRegister = (TextView)findViewById(R.id.registerText);
        db = new DatabaseHelper(this);

        mTextViewRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent registerIntent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(registerIntent);
            }
        });

        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = mTextUserName.getText().toString().trim();
                String pass= mTextPassword.getText().toString().trim();
                Boolean res = db.checkUser(user, pass);
                if(res == true) {
                    Toast.makeText(MainActivity.this, "Successfully Logged IN", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(MainActivity.this, "Wrong username/password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

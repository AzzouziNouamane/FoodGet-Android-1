package foodget.ihm.foodget.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import foodget.ihm.foodget.DatabaseHelper;
import foodget.ihm.foodget.R;
import foodget.ihm.foodget.models.User;

public class ForgottenPasswordActivity extends AppCompatActivity {
    EditText mTextNewPass;
    EditText mTextConfirmPass;
    EditText mTextOldPass;
    Button mSubmit;
    DatabaseHelper db;
    User currentUser;


    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgotten_password_2);
        mSubmit = findViewById(R.id.ConfirmButton);
        mTextNewPass = findViewById(R.id.textnewpass);
        mTextConfirmPass = findViewById(R.id.textnewpassconfirm);
        mSubmit.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                String newpass=mTextNewPass.getText().toString().trim();
                String newpassconfirm=mTextConfirmPass.getText().toString().trim();
                if (newpass.equals(newpassconfirm)){
                    //db.UpdatePassword(oldpass,newpass);
                }

            }
        });

        Bundle data = getIntent().getExtras();
        User tempUser = data.getParcelable("USER");
        currentUser = tempUser;
    }


}

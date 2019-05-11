package foodget.ihm.foodget.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import foodget.ihm.foodget.DatabaseHelper;
import foodget.ihm.foodget.R;
import foodget.ihm.foodget.models.User;

public class NewPasswordActivity extends AppCompatActivity {
    EditText mTextNewPass;
    EditText mTextConfirmPass;
    EditText mTextOldPass;
    Button Submit;
    Button mRetour;
    Button mAccueil;
    DatabaseHelper db;
    User currentUser;
    String oldpass;
    String newpass;
    String newpassconfirm;




    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        Submit=(Button) findViewById(R.id.ConfirmButton) ;

        mAccueil=(Button) findViewById(R.id.AccueilButton);
        mTextNewPass=(EditText) findViewById(R.id.textnewpass);
        mTextConfirmPass=(EditText) findViewById(R.id.textnewpassconfirm);
        mTextOldPass=(EditText) findViewById(R.id.textoldpass);

        Bundle data = getIntent().getExtras();
        User tempUser = (User) data.getParcelable("USER");
        currentUser = tempUser;
        currentUser.getEmail();



        mAccueil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent MainMenu = new Intent(getApplicationContext(), ManagementActivity.class);
                MainMenu.putExtra("user",currentUser);
                startActivity(MainMenu);
            }

        });

        Submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                oldpass=currentUser.getPassword();
                newpass=mTextNewPass.getText().toString().trim();
                newpassconfirm=mTextConfirmPass.getText().toString().trim();

                if(!(oldpass.equals(mTextOldPass.getText().toString().trim()))){
                    Toast.makeText(NewPasswordActivity.this,"Votre ancien mot de passe n'est pas correct",Toast.LENGTH_LONG).show();
                }



                else if(newpass.equals(newpassconfirm)){
                    currentUser.setPassword(newpass);
                    Toast.makeText(NewPasswordActivity.this,"Mot de passe changé avec succès",Toast.LENGTH_LONG).show();
                } else{
                    Toast.makeText(NewPasswordActivity.this,"Veuillez confirmer votre nouveau mot de passe",Toast.LENGTH_LONG).show();
                }
            }
        });


    }




}

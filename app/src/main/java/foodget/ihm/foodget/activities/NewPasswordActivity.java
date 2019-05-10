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
    Button mSubmit;
    Button mRetour;
    Button mAccueil;
    DatabaseHelper db;
    User currentUser;




    @Override
    protected void onCreate(Bundle savedInstanceState){

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_password);
        mSubmit=(Button) findViewById(R.id.ConfirmButton) ;
        mRetour=(Button) findViewById(R.id.RetourButton);
        mAccueil=(Button) findViewById(R.id.AcceuilButton);
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


    }




}

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

public class NewNameActivity extends AppCompatActivity {
    EditText TextNewName;
    Button Acceuil;
    Button Submit;
    DatabaseHelper db;
    User currentUser;
    String name, affichage;



    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_name);
        Bundle data = getIntent().getExtras();
        User tempUser = (User) data.getParcelable("USER");
        currentUser = tempUser;
        Acceuil = (Button) findViewById(R.id.AcceuilButton);
        Submit = (Button) findViewById(R.id.ConfirmButton);
        TextNewName = (EditText) findViewById(R.id.textnewname);

        Acceuil.setOnClickListener(new View.OnClickListener(){
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
                name=TextNewName.getText().toString().trim();

                if(name.equals("")){
                    Toast.makeText(NewNameActivity.this,"Veuillez saisir un prénom",Toast.LENGTH_LONG).show();
                }
                else{
                currentUser.setfName(name);
                Toast.makeText(NewNameActivity.this,"Prénom changé avec succès",Toast.LENGTH_LONG).show();
            }
            }
        });

    }

}

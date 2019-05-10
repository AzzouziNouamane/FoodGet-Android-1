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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import foodget.ihm.foodget.DatabaseHelper;
import foodget.ihm.foodget.R;
import foodget.ihm.foodget.models.User;

public class NewMailActivity extends AppCompatActivity {
    EditText TextOldMail;
    EditText TextNewMail;
    Button Submit;
    Button Acceuil;
    DatabaseHelper db;
    User currentUser;
    String oldmail;
    String newmail;





    public static boolean isValid(String email)
    {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }





    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_mail);


        db= new DatabaseHelper(this);

        Bundle data = getIntent().getExtras();
        User tempUser = (User) data.getParcelable("USER");
        currentUser = tempUser;

        Acceuil = (Button) findViewById(R.id.AcceuilButton);
        TextOldMail = (EditText) findViewById(R.id.textoldmail);
        TextNewMail = (EditText) findViewById(R.id.textnewmail);
        Submit = (Button) findViewById(R.id.ConfirmButton);



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
                oldmail = currentUser.getEmail();
                newmail = TextNewMail.getText().toString().trim();

                if (!(oldmail.equals(TextOldMail.getText().toString().trim()))){
                    Toast.makeText(NewMailActivity.this,"Votre ancienne adresse n'est pas correcte",Toast.LENGTH_LONG).show();
                }

                else if (!(isValid(newmail))) {
                    Toast.makeText(NewMailActivity.this,"Veuillez saisir une adresse mail valide",Toast.LENGTH_LONG).show();

                } else {
                    currentUser.setEmail(newmail);
                    Toast.makeText(NewMailActivity.this,"Adresse mail modifiée avec succès",Toast.LENGTH_LONG).show();

                }


            }
        });






    }

}

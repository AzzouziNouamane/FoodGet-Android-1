package foodget.ihm.foodget;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

public class MyAccountActivity extends AppCompatActivity {
    Button UpdateMail;
    Button UpdateName;
    Button UpdatePassWord;
    Button Logout;
    User currentUser;


    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_accueil:
                    Intent MainMenuIntent = new Intent(MyAccountActivity.this,MainMenu.class);
                    MainMenuIntent.putExtra("USER", currentUser);
                    startActivity(MainMenuIntent);
                    break;

                case R.id.navigation_compte:
                    Intent MyAccountIntent = new Intent(MyAccountActivity.this,MyAccountActivity.class);
                    MyAccountIntent.putExtra("USER", currentUser);
                    startActivity(MyAccountIntent);
                    break;
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myaccount);

        UpdateMail = (Button) findViewById(R.id.ModifierMailButton);
        UpdateName = (Button) findViewById(R.id.ModifierPrenomButton);
        UpdatePassWord = (Button) findViewById(R.id.ModifierMDPButton);
        Logout = (Button) findViewById(R.id.LogoutButton);

        Bundle data = getIntent().getExtras();
        User tempUser = (User) data.getParcelable("USER");
        currentUser = tempUser;
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);



        UpdateMail.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent NewMailIntent= new Intent(MyAccountActivity.this,NewMailActivity.class);
                NewMailIntent.putExtra("USER", currentUser);
                startActivity(NewMailIntent);
            }
        });
        UpdateName.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent NewNameIntent= new Intent(MyAccountActivity.this,NewNameActivity.class);
                NewNameIntent.putExtra("USER", currentUser);
                startActivity(NewNameIntent);
            }
        });
        UpdatePassWord.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent NewPasswordIntent= new Intent(MyAccountActivity.this,NewPasswordActivity.class);
                NewPasswordIntent.putExtra("USER", currentUser);
                startActivity(NewPasswordIntent);
            }
        });
        Logout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent LogOutIntent= new Intent(MyAccountActivity.this,LoginActivity.class);
                startActivity(LogOutIntent);
            }
        });

    }

}
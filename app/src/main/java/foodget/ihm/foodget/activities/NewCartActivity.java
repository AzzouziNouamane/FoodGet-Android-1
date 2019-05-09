package foodget.ihm.foodget.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import foodget.ihm.foodget.R;
import foodget.ihm.foodget.models.User;

public class NewCartActivity extends AppCompatActivity {
    User currentUser;
    Button createNewList;
    Button viewList;
    Button deleteList;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_accueil:
                    Intent MainMenuIntent = new Intent(NewCartActivity.this, MainMenu.class);
                    MainMenuIntent.putExtra("USER", currentUser);
                    startActivity(MainMenuIntent);
                    break;

                case R.id.navigation_compte:
                    Intent MyAccountIntent = new Intent(NewCartActivity.this, MyAccountActivity.class);
                    MyAccountIntent.putExtra("USER", currentUser);
                    startActivity(MyAccountIntent);
                    break;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cart);


        viewList = (Button) findViewById(R.id.viewList);
        createNewList = (Button) findViewById(R.id.createNewList);
        deleteList = (Button) findViewById(R.id.deleteList);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        viewList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent ViewListIntent= new Intent(NewCartActivity.this,ViewListActivity.class);
                ViewListIntent.putExtra("USER", currentUser);
                startActivity(ViewListIntent);
            }
        });

        createNewList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent CreateNewListIntent= new Intent(NewCartActivity.this,CreateNewListActivity.class);
                CreateNewListIntent.putExtra("USER", currentUser);
                startActivity(CreateNewListIntent);
            }
        });

        deleteList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent DeleteListIntent= new Intent(NewCartActivity.this,DeleteListActivity.class);
                DeleteListIntent.putExtra("USER", currentUser);
                startActivity(DeleteListIntent);
            }
        });
    }


}

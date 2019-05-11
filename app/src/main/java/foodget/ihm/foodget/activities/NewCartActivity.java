package foodget.ihm.foodget.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import foodget.ihm.foodget.R;
import foodget.ihm.foodget.models.User;

public class NewCartActivity extends AppCompatActivity {
    User currentUser;
    Button createNewList;
    Button viewList;
    Button deleteList;
    Button accueil;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_cart);
        Bundle data = getIntent().getExtras();
        User tempUser = (User) data.getParcelable("user");
        currentUser = tempUser;

        viewList = findViewById(R.id.viewList);
        createNewList = findViewById(R.id.createNewList);
        deleteList = findViewById(R.id.deleteList);
        accueil = (Button) findViewById(R.id.accueilButton);

        viewList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent ViewListIntent= new Intent(NewCartActivity.this,MyCartActivity.class);
                ViewListIntent.putExtra("user", currentUser);
                startActivity(ViewListIntent);
            }
        });

        createNewList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent CreateNewListIntent= new Intent(NewCartActivity.this,CreateNewListActivity.class);
                CreateNewListIntent.putExtra("user", currentUser);
                startActivity(CreateNewListIntent);
            }
        });

        deleteList.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent DeleteListIntent= new Intent(NewCartActivity.this,DeleteListActivity.class);
                DeleteListIntent.putExtra("user", currentUser);
                startActivity(DeleteListIntent);
            }
        });

        accueil.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                Intent MainMenu = new Intent(NewCartActivity.this, ManagementActivity.class);
                MainMenu.putExtra("user",currentUser);
                startActivity(MainMenu);
            }
        });
    }


}

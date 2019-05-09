package foodget.ihm.foodget.activities;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import foodget.ihm.foodget.DatabaseHelper;
import foodget.ihm.foodget.MyCartActivity;
import foodget.ihm.foodget.R;
import foodget.ihm.foodget.adapters.FoodListAdapter;
import foodget.ihm.foodget.models.Shopping;
import foodget.ihm.foodget.models.User;

public class MainMenu extends AppCompatActivity {

    String TAG = "MAINMENU";
    DatabaseHelper db;
    EditText add_food;
    EditText add_price;
    TextView welcomeView;
    Button add_data;
    Button btn_cart;
    Button btn_stats;
    ArrayList<Shopping> listItem;
    ArrayAdapter adapter;
    ListView shoppingView;
    User currentUser;
    Double total = 0.0;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_accueil:
                    Intent MainMenuIntent = new Intent(MainMenu.this,MainMenu.class);
                    MainMenuIntent.putExtra("USER", currentUser);
                    startActivity(MainMenuIntent);
                    break;

                case R.id.navigation_compte:
                    Intent MyAccountIntent = new Intent(MainMenu.this,MyAccountActivity.class);
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
        setContentView(R.layout.activity_main_menu);
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        db = new DatabaseHelper(this);
        welcomeView = findViewById(R.id.welcomeView);
        add_data = findViewById(R.id.add_data);
        btn_cart = findViewById(R.id.btn_cart);
        btn_stats = findViewById(R.id.btn_stats);
        add_food = findViewById(R.id.add_food);
        add_price = findViewById(R.id.add_price);
        listItem = new ArrayList<>();
        shoppingView = findViewById(R.id.shopping_list);
        Bundle data = getIntent().getExtras();
        User tempUser = data.getParcelable("USER");
        currentUser = tempUser;
        Log.d(TAG, "onCreate: " + tempUser);
        if(tempUser != null) {
            welcomeView.setText("Bonjour " + tempUser.getfName() + "\n" + "Vous avez dépensé " + total + " €" );
        }


        viewData();
        shoppingView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = shoppingView.getItemAtPosition(position).toString();
                Toast.makeText(MainMenu.this, ""+text, Toast.LENGTH_SHORT).show();
            }
        });

        btn_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent MyCartIntent = new Intent(MainMenu.this, MyCartActivity.class);
                MyCartIntent.putExtra("USER", currentUser);
                startActivity(MyCartIntent);
            }
        });

        add_data.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String food = add_food.getText().toString().trim();
                if(!add_price.getText().toString().trim().equals("")) {
                    Double price = Double.parseDouble(add_price.getText().toString().trim());
                    Shopping newShopping= new Shopping(food, price);
                    if ( (!food.equals("") ||  price.equals("")) && db.addFood(newShopping, currentUser) ) {
                        Toast.makeText(MainMenu.this, "data added", Toast.LENGTH_SHORT).show();
                        add_food.setText("");
                        add_price.setText("");
                        listItem.clear();
                        viewData();
                    } else {
                        Toast.makeText(MainMenu.this, "Data not added", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(MainMenu.this, "Ajoutez un prix !", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void viewData() {
        Cursor cursor = db.viewMenuData();
        total = 0.0;
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data to view", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                if(cursor.getString(3).equals(currentUser.getUsername())) {
                    Shopping newShopping = new Shopping(cursor.getString(1), cursor.getDouble(2));
                    listItem.add(newShopping);
                    total += newShopping.getPrice();
                    welcomeView.setText("Bonjour " + currentUser.getfName() + "\n" + "Vous avez dépensé " + total + " €" );
                    Log.d(TAG, "viewDataInMenu: total " + total + " getPrice() " + newShopping.getPrice());
                }

            }

            adapter = new ArrayAdapter<>(this, R.layout.da_food, listItem);
            FoodListAdapter adapterFood = new FoodListAdapter(this, R.layout.da_food, listItem);
            shoppingView.setAdapter(adapterFood);
        }
    }
}
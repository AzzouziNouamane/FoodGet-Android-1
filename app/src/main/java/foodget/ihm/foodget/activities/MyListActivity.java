package foodget.ihm.foodget.activities;

import android.app.Dialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import foodget.ihm.foodget.DatabaseHelper;
import foodget.ihm.foodget.R;
import foodget.ihm.foodget.adapters.FoodListAdapter;
import foodget.ihm.foodget.models.Alert;
import foodget.ihm.foodget.models.Alerts;
import foodget.ihm.foodget.models.Shopping;
import foodget.ihm.foodget.models.ShoppingList;
import foodget.ihm.foodget.models.User;

public class MyListActivity extends AppCompatActivity {

    DatabaseHelper db;
    EditText add_food;
    EditText add_price;
    TextView nameView;
    Button add_data;
    ArrayList<Shopping> listItem;
    ListView shoppingView;
    User currentUser;
    FoodListAdapter foodListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        db = new DatabaseHelper(this);
        nameView = findViewById(R.id.nameShoppingView);
        add_data = findViewById(R.id.addShoppingData);
        listItem = new ArrayList<>();
        shoppingView = findViewById(R.id.shopping_list);

        Bundle data = getIntent().getExtras();
        User tempUser = (User) data.getParcelable("USER");
        String tempName = data.getString("NAME");
        nameView.setText(tempName);
        listItem = data.getParcelableArrayList("SHOPPINGS");

        currentUser = tempUser;
        viewData();

        Dialog popupAddShoppingProduct = new Dialog(this);
        add_data.setOnClickListener((View v) -> {
            popupAddShoppingProduct.setContentView(R.layout.adding_data_popup);

            Button add_dataButton = popupAddShoppingProduct.findViewById(R.id.add_dataButton);
            Button cancelButton = popupAddShoppingProduct.findViewById(R.id.cancelButton);
            EditText priceInput = popupAddShoppingProduct.findViewById(R.id.priceInput);
            EditText productInput = popupAddShoppingProduct.findViewById(R.id.productInput);

            cancelButton.setOnClickListener((View v1) -> popupAddShoppingProduct.dismiss());

            add_dataButton.setOnClickListener((View v2) -> {
                String food = productInput.getText().toString().trim();
                String price = priceInput.getText().toString().trim();
                ArrayList<Shopping> newFoodList = new ArrayList<>();
                ShoppingList newShoppingList = null;
                if (!food.equals("") && !price.equals("")) {
                    Shopping newShopping = new Shopping(food, Double.parseDouble(price));
                    newFoodList.add(newShopping);
                    newShoppingList = new ShoppingList(tempName,newFoodList);
                } else {
                    popupAddShoppingProduct.dismiss();
                    return;
                }

//                if (db.addShoppingList(newShoppingList, currentUser)) {
//                    Toast.makeText(this, "data added", Toast.LENGTH_SHORT).show();
//                    db.addAlert(new Alert(Alerts.PRODUCT_LIST_ADDED.toString().replace("%product%", food)
//                            .replace("%price%", price).replace("%list%", newShoppingList.getName()),
//                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("d/MM/yy HH:mm", Locale.FRANCE))), currentUser);
//                    listItem.clear();
//                    viewData();
//
//                } else {
//                    Toast.makeText(this, "Data not added", Toast.LENGTH_SHORT).show();
//                }
                popupAddShoppingProduct.dismiss();
            });
            popupAddShoppingProduct.show();
        });

    }

    private void viewData() {
        foodListAdapter = new FoodListAdapter(this, R.layout.da_food, listItem);
        shoppingView.setAdapter(foodListAdapter);
    }
}

package foodget.ihm.foodget.activities;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Parcelable;
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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Locale;

import foodget.ihm.foodget.BluetoothActivity;
import foodget.ihm.foodget.DatabaseHelper;
import foodget.ihm.foodget.OnClickInMyAdapterListener;
import foodget.ihm.foodget.R;
import foodget.ihm.foodget.adapters.FoodListAdapter;
import foodget.ihm.foodget.fragments.TabMainMenu;
import foodget.ihm.foodget.models.Alert;
import foodget.ihm.foodget.models.Alerts;
import foodget.ihm.foodget.models.Shopping;
import foodget.ihm.foodget.models.ShoppingList;
import foodget.ihm.foodget.models.User;

public class MyListActivity extends AppCompatActivity implements OnClickInMyAdapterListener {

    private static final String TAG = "MyListActivity";
    DatabaseHelper db;
    EditText add_food;
    EditText add_price;
    TextView nameView;
    Button add_data;
    Button shareSoppingData;
    ArrayList<Shopping> listItem;
    ListView shoppingView;
    ShoppingList shoppingList;
    User currentUser;
    String tempName;
    FoodListAdapter foodListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        db = new DatabaseHelper(this);
        nameView = findViewById(R.id.nameShoppingView);
        add_data = findViewById(R.id.addShoppingData);
        shareSoppingData = findViewById(R.id.shareSoppingData);
        listItem = new ArrayList<>();
        shoppingView = findViewById(R.id.shopping_list);

        Bundle data = getIntent().getExtras();
        User tempUser = (User) data.getParcelable("USER");
        tempName = data.getString("NAME");
        shoppingList = data.getParcelable("SHOPPINGLIST");
        nameView.setText(tempName);
        listItem = new ArrayList<>();
//        listItem = data.getParcelableArrayList("SHOPPINGS");

        currentUser = tempUser;
        viewData(tempName);

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
                ShoppingList currentShoppingList = null;
                if (!food.equals("") && !price.equals("")) {
                    Shopping newShopping = new Shopping(food, Double.parseDouble(price));
                    listItem.add(newShopping);
                    currentShoppingList = new ShoppingList(tempName,listItem);
                } else {
                    popupAddShoppingProduct.dismiss();
                    return;
                }

                if (db.updateShoppingList(currentShoppingList) > 0) {
                    Toast.makeText(this, "data update", Toast.LENGTH_SHORT).show();
                    db.addAlert(new Alert(Alerts.PRODUCT_LIST_ADDED.toString().replace("%product%", food)
                            .replace("%price%", price).replace("%list%", currentShoppingList.getName()),
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("d/MM/yy HH:mm", Locale.FRANCE))), currentUser);
                    listItem.clear();
                    viewData(tempName);

                } else {
                    Toast.makeText(this, "Data not updated", Toast.LENGTH_SHORT).show();
                }
                popupAddShoppingProduct.dismiss();
            });
            popupAddShoppingProduct.show();
        });

        shareSoppingData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bluetoothIntent = new Intent(getApplicationContext(), BluetoothActivity.class);
                bluetoothIntent.putExtra("user",currentUser);
                bluetoothIntent.putExtra("SHOPPINGLIST", (Parcelable) shoppingList);
                startActivity(bluetoothIntent);
            }
        });

    }

    private void viewData( String nameList) {

        Cursor cursor = db.viewShoppingsOfList(currentUser, nameList);
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "No data to view", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                Log.d(TAG, "Test User : " + cursor.getString(3));
                Log.d(TAG, "Current User : " + currentUser);
                if (cursor.getString(3).equals(currentUser.getUsername())) {
                    Type listType = new TypeToken<ArrayList<Shopping>>() {
                    }.getType();
                    ArrayList<Shopping> shoppingList = new Gson().fromJson(cursor.getString(2), listType);
                    for (int i = 0; i<shoppingList.size(); i++){
                        String food = shoppingList.get(i).getFood();
                        Double price = shoppingList.get(i).getPrice();
                        listItem.add(new Shopping(food,price));
                    }

//                    Shopping newShopping = new Shopping(cursor.getString(1), cursor.getDouble(2));
//                    listItem.add(newShopping);
                } else {
                    Intent ToLoginPageIntent = new Intent(this, LoginActivity.class);
                    Toast.makeText(this, "Erreur de programme. Veuillez vous reconnecter", Toast.LENGTH_SHORT).show();
                    startActivity(ToLoginPageIntent);
                }
            }

            foodListAdapter = new FoodListAdapter(this, R.layout.da_food, listItem, (OnClickInMyAdapterListener) this);
            shoppingView.setAdapter(foodListAdapter);
        }

    }

    @Override
    public void onItemclicked() {
        Log.d(TAG, "Testing Interface....");
        listItem.clear();
        viewData(tempName);
    }

    @Override
    public ShoppingList getShoppingList() {
        return shoppingList;
    }
}

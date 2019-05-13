package foodget.ihm.foodget.activities;

import android.app.Dialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
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

import foodget.ihm.foodget.BluetoothConnectionService;
import foodget.ihm.foodget.DatabaseHelper;
import foodget.ihm.foodget.OnClickInMyShoppingAdapterListener;
import foodget.ihm.foodget.R;
import foodget.ihm.foodget.ReceiveBluetoothActivity;
import foodget.ihm.foodget.adapters.ShoppingListAdapter;
import foodget.ihm.foodget.adapters.ShoppingListExtraAdapter;
import foodget.ihm.foodget.models.Alert;
import foodget.ihm.foodget.models.Alerts;
import foodget.ihm.foodget.models.Shopping;
import foodget.ihm.foodget.models.ShoppingList;
import foodget.ihm.foodget.models.User;

public class MyCartActivity extends AppCompatActivity implements AdapterView.OnItemClickListener, OnClickInMyShoppingAdapterListener {

    User currentUser;
    TextView tv_listes;
    ListView shopping_lists;
    DatabaseHelper db;
    Button btn_add_list;
    ArrayList<ShoppingList> shoppingItem;
    Button shareBluetooth;
    ShoppingListAdapter myAdapter;
    ShoppingListExtraAdapter myExtraAdapter;
    BluetoothConnectionService bluetoothConnectionService;
//    int positionOfList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_mycart);
        db = new DatabaseHelper(this);
        tv_listes = findViewById(R.id.tv_listes);
        btn_add_list = findViewById(R.id.btn_add_list);
        shareBluetooth = findViewById(R.id.shareBluetooth);
        shoppingItem = new ArrayList<>();
        shopping_lists = findViewById(R.id.lists_list);
        shopping_lists.setOnItemClickListener(this);
        Bundle data = getIntent().getExtras();
        User tempUser = data.getParcelable("user");
        currentUser = tempUser;
        viewData();
//
//        shopping_lists.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                String text = shopping_lists.getItemAtPosition(position).toString();
//                Toast.makeText(MyCartActivity.this, "" + text, Toast.LENGTH_SHORT).show();
//            }
//        });

        shareBluetooth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent bluetoothIntent = new Intent(getApplicationContext(), ReceiveBluetoothActivity.class);
                bluetoothIntent.putExtra("user", currentUser);
                startActivity(bluetoothIntent);
            }
        });


        Dialog popupAddShoppingList = new Dialog(this);
        btn_add_list.setOnClickListener((View v) -> {
            popupAddShoppingList.setContentView(R.layout.adding_list_popup);

            Button add_dataButton = popupAddShoppingList.findViewById(R.id.addListButton);
            Button cancelButton = popupAddShoppingList.findViewById(R.id.cancelListButton);
            EditText nameInput = popupAddShoppingList.findViewById(R.id.nameListInput);

            cancelButton.setOnClickListener((View v1) -> popupAddShoppingList.dismiss());

            add_dataButton.setOnClickListener((View v2) -> {
                String nameList = nameInput.getText().toString().trim();
                ArrayList<Shopping> newFoodList = new ArrayList<>();
                //newFoodList.add(new Shopping("", Double.parseDouble("")));
                ShoppingList newShoppingList = null;
                if (!nameList.equals("")) {
                    newShoppingList = new ShoppingList(nameList,newFoodList);
                } else {
                    Toast.makeText(this, "Veuillez rentrer un nom pour votre liste !", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (db.addShoppingList(newShoppingList, currentUser)) {
                    Toast.makeText(this, "data added", Toast.LENGTH_SHORT).show();
                    db.addAlert(new Alert(Alerts.LIST_ADDED.toString().replace("%list%", nameList),
                            LocalDateTime.now().format(DateTimeFormatter.ofPattern("d/MM/yy HH:mm", Locale.FRANCE))), currentUser);
                    shoppingItem.clear();
                    viewData();

                } else {
                    Toast.makeText(this, "Data not added", Toast.LENGTH_SHORT).show();
                }
                popupAddShoppingList.dismiss();
            });
            popupAddShoppingList.show();
        });

    }

    public void viewData() {
        Cursor cursor = db.viewShoppingListData(currentUser);
        shoppingItem.clear();
        if (cursor.getCount() == 0) {
            Toast.makeText(this, "Votre liste est vide...", Toast.LENGTH_SHORT).show();
            tv_listes.setText("Vous n'avez aucune liste créée");
        } else {
            tv_listes.setText("Vos listes :");
            while (cursor.moveToNext()) {
                if (cursor.getString(3).equals(currentUser.getUsername())) {
                    Type listType = new TypeToken<ArrayList<Shopping>>() {
                    }.getType();
                    ArrayList<Shopping> shoppingList = new Gson().fromJson(cursor.getString(2), listType);
                    //System.out.print(shoppingList);
                    ShoppingList newShoppingList = new ShoppingList(cursor.getString(1), shoppingList);
                    shoppingItem.add(newShoppingList);
                } else {
                    Intent ToLoginPageIntent = new Intent(this, LoginActivity.class);
                    Toast.makeText(this, "Erreur de programme. Veuillez vous reconnecter", Toast.LENGTH_SHORT).show();
                    startActivity(ToLoginPageIntent);
                }
            }
            if (getResources().getConfiguration().orientation ==
                    Configuration.ORIENTATION_PORTRAIT) {
                myAdapter = new ShoppingListAdapter(this, R.layout.shopping, shoppingItem, this);
                shopping_lists.setAdapter(myAdapter);
            } else {
                myExtraAdapter = new ShoppingListExtraAdapter(this, R.layout.shopping_extra, shoppingItem, this);
                shopping_lists.setAdapter(myExtraAdapter);
            }
        }

    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//        positionOfList = position;
        Toast.makeText(MyCartActivity.this, "item clicked", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteclicked() {
        viewData();
    }

    @Override
    public void onNameclicked(ShoppingList shoppingList) {
        //    Toast.makeText(MyCartActivity.this, "name clicked",Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, MyListActivity.class);
        Bundle extras = new Bundle();
        if (getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_PORTRAIT) {
            extras.putString("NAME", shoppingList.getName());
            extras.putParcelable("SHOPPINGLIST", shoppingList);
            extras.putParcelable("USER", currentUser);
            intent.putExtras(extras);
            startActivity(intent);
        } else {
            extras.putString("NAME", shoppingList.getName());
            extras.putParcelable("SHOPPINGLIST", shoppingList);
            extras.putParcelable("USER", currentUser);
            intent.putExtras(extras);
            startActivity(intent);
        }
    }
}


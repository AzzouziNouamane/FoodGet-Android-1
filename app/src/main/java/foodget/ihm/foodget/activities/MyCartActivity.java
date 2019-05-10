package foodget.ihm.foodget.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import foodget.ihm.foodget.DatabaseHelper;
import foodget.ihm.foodget.R;
import foodget.ihm.foodget.models.Shopping;
import foodget.ihm.foodget.models.User;

public class MyCartActivity extends AppCompatActivity {

    User currentUser;
    TextView tv_listes;
    ListView lists_list;
    DatabaseHelper db;
    Button btn_add_list;
    ArrayList<ArrayList<Shopping>> listItem;
    ArrayAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycart);

        db = new DatabaseHelper(this);
        tv_listes = findViewById(R.id.tv_listes);
        btn_add_list = findViewById(R.id.btn_add_list);
        listItem = new ArrayList<>();
        lists_list = findViewById(R.id.lists_list);
        Bundle data = getIntent().getExtras();
        User tempUser = data.getParcelable("USER");
        currentUser = tempUser;
        //    Log.d(TAG, "onCreate: " + tempUser);

        viewData();
        lists_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String text = lists_list.getItemAtPosition(position).toString();
                Toast.makeText(MyCartActivity.this, "" + text, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void viewData() {
//        Cursor cursor = db.viewMenuData();
//        if (cursor.getCount() == 0) tv_listes.setText("Vous n'avez pas encore créé des listes");
//        else {
//            tv_listes.setText("Vos listes :");
//            adapter = new ArrayAdapter<>(this, R.layout.da_food, listItem);
//            //   FoodListAdapter adapterFood = new FoodListAdapter(this, R.layout.da_food, listItem);
//            lists_list.setAdapter(adapter);
//        }
    }
}

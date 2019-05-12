package foodget.ihm.foodget.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import foodget.ihm.foodget.DatabaseHelper;
import foodget.ihm.foodget.OnClickInMyAdapterListener;
import foodget.ihm.foodget.R;
import foodget.ihm.foodget.activities.ManagementActivity;
import foodget.ihm.foodget.activities.MyListActivity;
import foodget.ihm.foodget.fragments.TabMainMenu;
import foodget.ihm.foodget.models.Shopping;
import foodget.ihm.foodget.models.ShoppingList;

public class FoodListAdapter extends ArrayAdapter<Shopping> {

    private Context context;
    private int resource;
    private ArrayList<Shopping> objects;
    private static String TAG = "FoodListAdapter";
    private DatabaseHelper db;
    private OnClickInMyAdapterListener myActivityInterface;


    public FoodListAdapter(Context context, int resource, ArrayList<Shopping> objects, OnClickInMyAdapterListener  myActivityInterface) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.objects = objects;
        this.db = new DatabaseHelper(context);
        this.myActivityInterface= myActivityInterface;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //Set the shopping informations
        String food = getItem(position).getFood();
        Double price = getItem(position).getPrice();

        //Create the shopping object with the information
        Shopping shopping = new Shopping(food, price);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);

        TextView tvFood = convertView.findViewById(R.id.foodView);
        TextView tvPrice = convertView.findViewById(R.id.priceView);
        Button tvButton = convertView.findViewById(R.id.deleteFood);

        tvButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"" + context);
                if(context instanceof ManagementActivity) {
                    Log.d(TAG, "Deleting... TabMainMenu");
                    db.deleteFood(shopping);

                }

                if(context instanceof MyListActivity) {
                    Log.d(TAG, "Deleting... MyListActivity");
                    ShoppingList shoppingList = myActivityInterface.getShoppingList();
                    //db.deleteFoodFromList(shoppingList,shopping);
                }

                myActivityInterface.onItemclicked();
                notifyDataSetChanged();

            }
        });
        tvFood.setText(food);
        tvPrice.setText(price + "€");

        return convertView;
    }
}

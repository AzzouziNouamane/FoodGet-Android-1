package foodget.ihm.foodget.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import foodget.ihm.foodget.R;
import foodget.ihm.foodget.models.Shopping;
import foodget.ihm.foodget.models.ShoppingList;

public class ShoppingListAdapter extends ArrayAdapter<ShoppingList> {
    private Context context;
    private int resource;
    private ArrayList<ShoppingList> shoppings;

    public ShoppingListAdapter(Context context, int resource, ArrayList<ShoppingList> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.shoppings = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if(view == null)
            view = LayoutInflater.from(context).inflate(resource, parent, false);

        ShoppingList shoppingList = shoppings.get(position);

        //Create the shoppingList object with the information
        // ShoppingList shoppingList = new ShoppingList(name, shoppings);

        TextView tvName = view.findViewById(R.id.nameView);
//        TextView tvPrice = convertView.findViewById(R.id.priceView);
//
        tvName.setText(shoppingList.getName());
//        tvPrice.setText(price + "€");

        return view;
    }
}

package foodget.ihm.foodget.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import foodget.ihm.foodget.DatabaseHelper;
import foodget.ihm.foodget.OnClickInMyShoppingAdapterListener;
import foodget.ihm.foodget.R;
import foodget.ihm.foodget.models.ShoppingList;

public class ShoppingListExtraAdapter extends ArrayAdapter<ShoppingList> {
    private Context context;
    private int resource;
    private ArrayList<ShoppingList> shoppings;
    private DatabaseHelper db;
    private OnClickInMyShoppingAdapterListener myActivityInterface;


    public ShoppingListExtraAdapter(Context context, int resource, ArrayList<ShoppingList> objects, OnClickInMyShoppingAdapterListener myActivityInterface) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.shoppings = objects;
        this.db = new DatabaseHelper(context);
        this.myActivityInterface = myActivityInterface;

    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null)
            view = LayoutInflater.from(context).inflate(resource, parent, false);

        ShoppingList shoppingList = shoppings.get(position);

        //Create the shoppingList object with the information
        // ShoppingList shoppingList = new ShoppingList(name, shoppings);

        TextView tvName = view.findViewById(R.id.nameView);
        Button dButton = view.findViewById(R.id.deleteListe);
        TextView tvTotal = view.findViewById(R.id.totalView);
        TextView tvQuantity = view.findViewById(R.id.quantityView);

        tvName.setText(shoppingList.getName());
        tvName.setClickable(true);
        tvTotal.setText(shoppingList.getTotal() + " €");

        tvQuantity.setText(shoppingList.getShoppings().size() + " produits");

        dButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                db.deleteList(shoppingList);
                myActivityInterface.onDeleteclicked();
                notifyDataSetChanged();
            }
        });

        tvName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myActivityInterface.onNameclicked(shoppingList);
            }
        });


        return view;
    }
}

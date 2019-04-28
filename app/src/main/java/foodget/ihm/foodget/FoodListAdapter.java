package foodget.ihm.foodget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class FoodListAdapter extends ArrayAdapter<Shopping> {

    private Context context;
    private int resource;

    public FoodListAdapter(Context context, int resource, ArrayList<Shopping> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
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

        TextView tvFood = (TextView) convertView.findViewById(R.id.foodView);
        TextView tvPrice = (TextView) convertView.findViewById(R.id.priceView);

        tvFood.setText(food);
        tvPrice.setText(price + "€");

        return convertView;
    }
}

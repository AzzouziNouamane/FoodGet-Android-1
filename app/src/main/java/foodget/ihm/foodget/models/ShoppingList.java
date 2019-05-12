package foodget.ihm.foodget.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

public class ShoppingList implements Parcelable, Serializable {
    private String name;
    private ArrayList<Shopping> shoppings;

    public ShoppingList(String name, ArrayList<Shopping>shoppings){
        this.name = name;
        this.shoppings = shoppings;
    }


    protected ShoppingList(Parcel in) {
        name = in.readString();
        shoppings = in.createTypedArrayList(Shopping.CREATOR);
    }

    public static final Creator<ShoppingList> CREATOR = new Creator<ShoppingList>() {
        @Override
        public ShoppingList createFromParcel(Parcel in) {
            return new ShoppingList(in);
        }

        @Override
        public ShoppingList[] newArray(int size) {
            return new ShoppingList[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Shopping> getShoppings() {
        return shoppings;
    }

    public void setShoppings(ArrayList<Shopping> shoppings) {
        this.shoppings = shoppings;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeTypedList(shoppings);
    }

    public Double getTotal() {
        Double total = 0.0;
        for (int i = 0; i < this.shoppings.size(); i++){
            total = total + shoppings.get(i).getPrice();
        }
        return total;
    }
}

package foodget.ihm.foodget.models;

import java.util.ArrayList;

public class ShoppingList {
    private String name;
    private ArrayList<Shopping> shoppings;

    public ShoppingList(String name, ArrayList<Shopping>shoppings){
        this.name = name;
        this.shoppings = shoppings;
    }




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
}

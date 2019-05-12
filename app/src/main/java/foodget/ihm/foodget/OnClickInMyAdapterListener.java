package foodget.ihm.foodget;

import foodget.ihm.foodget.models.ShoppingList;

public interface OnClickInMyAdapterListener {
    public void onItemclicked();

    public ShoppingList getShoppingList();
}

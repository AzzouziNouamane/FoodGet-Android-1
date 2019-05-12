package foodget.ihm.foodget;

import foodget.ihm.foodget.models.ShoppingList;

public interface OnClickInMyAdapterListener {
    void onItemclicked();

    ShoppingList getShoppingList();
}

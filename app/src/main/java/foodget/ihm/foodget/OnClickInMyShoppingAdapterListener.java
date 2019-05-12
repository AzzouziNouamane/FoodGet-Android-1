package foodget.ihm.foodget;

import foodget.ihm.foodget.models.ShoppingList;

public interface OnClickInMyShoppingAdapterListener {
    void onDeleteclicked();

    void onNameclicked(ShoppingList shoppingList);
}

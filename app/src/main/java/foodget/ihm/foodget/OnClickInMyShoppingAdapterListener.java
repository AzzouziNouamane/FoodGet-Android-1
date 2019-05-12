package foodget.ihm.foodget;

import foodget.ihm.foodget.models.ShoppingList;

public interface OnClickInMyShoppingAdapterListener {
    public void onDeleteclicked();
    public void onNameclicked(ShoppingList shoppingList);
}

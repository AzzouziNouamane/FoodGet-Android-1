package foodget.ihm.foodget.models;

public enum Alerts {
    PRODUCT_ADDED("Le produit %product% à %price%€ ajouté"),
    LIST_ADDED("La liste %list% a été créée"),
    PRODUCT_LIST_ADDED("Produit %product% à %price%€ ajouté à la liste %list%"),
    THRESHOLD_OVER("Seuil de dépense dépassé de %over%€"),
    WELCOME("Bienvenue %username% sur Foodget");

    private final String name;

    Alerts(String str) {
        name = str;
    }

    public String toString() {
        return this.name;
    }
}
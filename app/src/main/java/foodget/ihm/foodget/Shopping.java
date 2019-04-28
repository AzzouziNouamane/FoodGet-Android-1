package foodget.ihm.foodget;

public class Shopping {

    private String food;
    private Double price;

    public Shopping(String food, Double price) {
        this.food = food;
        this.price = price;
    }

    public String getFood() {
        return food;
    }

    public void setFood(String food) {
        this.food = food;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}

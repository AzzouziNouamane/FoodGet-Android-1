package foodget.ihm.foodget.models;

public class Alert {
    private String date;
    private String alert;

    public Alert(String alert) {
        this.alert = alert;
    }

    public Alert(String alert, String date) {
        this.alert = alert;
        this.date = date;
    }

    public String getDate() {
        return date;
    }

    public String getAlert() {
        return alert;
    }

    @Override
    public String toString() {
        return this.alert + " " + this.date;
    }
}

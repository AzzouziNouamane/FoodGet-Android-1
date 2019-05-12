package foodget.ihm.foodget.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Shopping implements Parcelable, Serializable {

    private String food;
    private Double price;
    private String date;

    public Shopping(String food, Double price, String date) {
        this.food = food;
        this.price = price;
        this.date = date;
    }

    protected Shopping(Parcel in) {
        food = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
        date = in.readString();
    }

    public Shopping(byte[] buffer, int i, int bytes) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(food);
        if (price == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(price);
        }
        dest.writeString(date);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Shopping> CREATOR = new Creator<Shopping>() {
        @Override
        public Shopping createFromParcel(Parcel in) {
            return new Shopping(in);
        }

        @Override
        public Shopping[] newArray(int size) {
            return new Shopping[size];
        }
    };

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

    public String getDateAsString() {
        return date;
    }

    public LocalDateTime getDateAsDate() {
        return LocalDateTime.parse(date, DateTimeFormatter.ofPattern("dd/MM/yy HH:mm", Locale.FRANCE));
    }

    public byte[] serialize() throws IOException {
        ByteArrayOutputStream b = new ByteArrayOutputStream();
        ObjectOutputStream o = new ObjectOutputStream(b);
        o.writeObject(this);
        return b.toByteArray();
    }

    //AbstractMessage was actually the message type I used, but feel free to choose your own type
    public static Shopping deserialize(byte[] bytes) throws IOException, ClassNotFoundException {
        ByteArrayInputStream b = new ByteArrayInputStream(bytes);
        ObjectInputStream o = new ObjectInputStream(b);
        return (Shopping) o.readObject();
    }

    @Override
    public String toString() {
        return this.food + " " + this.price + "€ " + this.getDateAsString();
    }
}
package foodget.ihm.foodget.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Shopping implements Parcelable {

    private String food;
    private Double price;

    public Shopping(String food, Double price) {
        this.food = food;
        this.price = price;
    }

    protected Shopping(Parcel in) {
        food = in.readString();
        if (in.readByte() == 0) {
            price = null;
        } else {
            price = in.readDouble();
        }
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
}

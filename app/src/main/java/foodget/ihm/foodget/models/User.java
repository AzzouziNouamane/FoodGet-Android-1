package foodget.ihm.foodget.models;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String username;
    private String password;
    private String email;
    private String fName;
    private double threshold;

    public User(String username, String password, String email, String fName, double threshold) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.fName = fName;
        this.threshold = threshold;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setThreshold(int thresh){
        this.threshold = thresh;
    }

    public String getfName() {
        return fName;
    }

    public void setfName(String fName) {
        this.fName = fName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    protected User(Parcel in) {
        username = in.readString();
        password = in.readString();
        email = in.readString();
        fName = in.readString();
        threshold = in.readDouble();
    }

    public double getThreshold() {
        return threshold;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(password);
        dest.writeString(email);
        dest.writeString(fName);
        dest.writeDouble(threshold);
    }

    @SuppressWarnings("unused")
    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    @Override
    public String toString() {
        return this.fName + " " + this.password + " " + this.threshold;
    }
}
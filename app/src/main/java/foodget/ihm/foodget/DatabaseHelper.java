package foodget.ihm.foodget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;
import java.util.Arrays;

import static android.media.tv.TvContract.Programs.Genres.SHOPPING;

import foodget.ihm.foodget.models.Shopping;
import foodget.ihm.foodget.models.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static final String TAG = "DatabaseHelper";
    public static final String DATABASE_NAME = "register.db";
    public static final String TABLE_NAME = "registeruser";
    public static final String FOOD_TABLE = "fooduser";
    public static final String LIST_TABLE = "listsuser";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "username";
    public static final String COL_3 = "password";
    public static final String COL_4 = "email";
    public static final String COL_5 = "fName";
    public static final String FOOD = "food";
    public static final String PRICE = "price";
    public static User connectedUser;
    public static final ArrayList<User> listOfUsers = new ArrayList<>();

    public static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " (" +
            COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            COL_2 + " TEXT, " +
            COL_3 + " TEXT, " +
            COL_4 + " TEXT, " +
            COL_5 + " TEXT"  + ");";

    public static final String CREATE_TABLE_FOOD = "CREATE TABLE " + FOOD_TABLE + " (" +
            COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FOOD + " TEXT, " +
            PRICE + " INT, " +
            COL_2 + " TEXT"  + ");";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);

    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_FOOD);
        User user = new User("julie", "123", "julie@gmail.ocm", "julie");
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("email", user.getEmail());
        contentValues.put("fName", user.getfName());
        sqLiteDatabase.insert(TABLE_NAME, null, contentValues);

        Shopping shopping = new Shopping("Pomme", 2.0);
        ContentValues contentValuesShop = new ContentValues();
        contentValuesShop.put(FOOD, shopping.getFood());
        contentValuesShop.put(PRICE, shopping.getPrice());
        contentValuesShop.put(COL_2, user.getUsername());
        sqLiteDatabase.insert(FOOD_TABLE, null, contentValuesShop);

        Shopping shopping2 = new Shopping("Bananes", 4.0);
        ContentValues contentValuesShop2 = new ContentValues();
        contentValuesShop2.put(FOOD, shopping2.getFood());
        contentValuesShop2.put(PRICE, shopping2.getPrice());
        contentValuesShop2.put(COL_2, user.getUsername());
        sqLiteDatabase.insert(FOOD_TABLE, null, contentValuesShop2);

        Log.d(TAG, "DatabaseHelper: Created");
        listOfUsers.add(user);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        initializeUsers(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
    }

    private void initializeUsers(SQLiteDatabase db){
        String[] columns = {COL_1,COL_2,COL_3,COL_4,COL_5};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);
        while(cursor.moveToNext()){
            int id = cursor.getInt(0);
            String username = cursor.getString(1);
            String password = cursor.getString(2);
            String email = cursor.getString(3);
            String fname = cursor.getString(4);
            User user = new User(username,password,email,fname);
            listOfUsers.add(user);
        }
        cursor.close();
    }

    public long addUser(String username, String password, String email, String fName) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        contentValues.put("email", email);
        contentValues.put("fName", fName);
        long res = db.insert(TABLE_NAME, null, contentValues);
        listOfUsers.add(new User(username, password, email, fName));
        db.close();
        return res;
    }

    public boolean checkUser(String username, String password) {
        String[] columns = {COL_1};
        SQLiteDatabase db = getReadableDatabase();
        String selection = COL_2 + "=?" + " and " + COL_3 + "=?";
        String[] selectionArgs = { username, password};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if(count > 0) return true;
        else return false;
    }

    public Cursor viewData() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + FOOD_TABLE;
        Cursor cursor = db.rawQuery(query, null);
        return cursor;
    }

    public boolean insertData(String food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
            contentValues.put(COL_5, food);
        long result = db.insert(TABLE_NAME, null, contentValues);
        viewData();
        return result != -1;
    }

    public boolean addFood(Shopping shopping, User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FOOD, shopping.getFood());
        contentValues.put(PRICE, shopping.getPrice());
        contentValues.put(COL_2, user.getUsername());
        long result = db.insert(FOOD_TABLE, null, contentValues);
        return result != -1;
    }


    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("email", user.getEmail());
        contentValues.put("fName", user.getfName());
        long res = db.insert(TABLE_NAME, null, contentValues);
        listOfUsers.add(user);

        db.close();
        return res;
    }

    public User userLogged(String username, String password) {
        for(int i = 0; i < listOfUsers.size(); i++) {
            if(listOfUsers.get(i).getUsername().equalsIgnoreCase(username) && listOfUsers.get(i).getPassword().equalsIgnoreCase(password)) {
                return listOfUsers.get(i);
            }
            Log.d(TAG, "Utilisateur : "+ listOfUsers.get(i).getUsername() + " mot de passe : "+listOfUsers.get(i).getPassword());
        }
        Log.d(TAG, "Liste d'utilisateurs : "+ listOfUsers.size());
        return null;
    }

    public ArrayList<User> getListOfUsers() {
        return listOfUsers;
    }

    public int UpdatePassword(String OldPass,String NewPass){
        ContentValues contentValues = new ContentValues();
        contentValues.put("password",NewPass);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(TABLE_NAME, contentValues,"password",null);
        return 0;
    }
}

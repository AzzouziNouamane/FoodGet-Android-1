package foodget.ihm.foodget;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;

import foodget.ihm.foodget.models.Alert;
import foodget.ihm.foodget.models.Alerts;
import foodget.ihm.foodget.models.Shopping;
import foodget.ihm.foodget.models.ShoppingList;
import foodget.ihm.foodget.models.User;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String TAG = "DatabaseHelper";

    private static final String DATABASE_NAME = "register.db";

    private static final String USER_TABLE = "registeruser";
    private static final String FOOD_TABLE = "fooduser";
    private static final String SHOPPING_TABLE = "shoppinguser";
    private static final String ALERTS_TABLE = "alertsuser";

    private static final String ID = "ID";
    private static final String USER_NAME = "username";
    private static final String USER_PASSWORD = "password";
    private static final String USER_EMAIL = "email";
    private static final String USER_FIRSTNAME = "fName";
    private static final String USER_THRESHOLD = "threshold";


    private static final String FOOD_NAME = "food";
    private static final String FOOD_PRICE = "price";

    private static final String LIST_NAME = "nameList";
    private static final String LIST_FOOD = "foodList";

    private static final String ALERT_STRING = "alert";
    private static final String ALERT_DATE = "date";


    private static final String CREATE_TABLE = "CREATE TABLE " + USER_TABLE + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_NAME + " TEXT, " +
            USER_PASSWORD + " TEXT, " +
            USER_EMAIL + " TEXT, " +
            USER_FIRSTNAME + " TEXT, " +
            USER_THRESHOLD + " DOUBLE" + ");";

    private static final String CREATE_TABLE_FOOD = "CREATE TABLE " + FOOD_TABLE + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            FOOD_NAME + " TEXT, " +
            FOOD_PRICE + " INT, " +
            USER_NAME + " TEXT" + ");";

    private static final String CREATE_TABLE_SHOPPINGLIST = "CREATE TABLE " + SHOPPING_TABLE + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            LIST_NAME + " TEXT, " +
            LIST_FOOD + " TEXT, " +
            USER_NAME + " TEXT" + ");";

    private static final String CREATE_TABLE_ALERTS = "CREATE TABLE " + ALERTS_TABLE + " (" +
            ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            USER_NAME + " TEXT, " +
            ALERT_STRING + " DATE, " +
            ALERT_DATE + " TEXT" + ");";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2);
    }


    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(CREATE_TABLE);
        sqLiteDatabase.execSQL(CREATE_TABLE_FOOD);
        sqLiteDatabase.execSQL(CREATE_TABLE_SHOPPINGLIST);
        sqLiteDatabase.execSQL(CREATE_TABLE_ALERTS);

        //Utilisateur par défaut
        User user = new User("julie", "123", "julie@gmail.com", "julie", 48);
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("email", user.getEmail());
        contentValues.put("fName", user.getfName());
        contentValues.put("threshold", user.getThreshold());
        sqLiteDatabase.insert(USER_TABLE, null, contentValues);

        //Produits achetés par défaut
        Shopping shopping = new Shopping("Pomme", 2.0);
        ContentValues contentValuesShop = new ContentValues();
        contentValuesShop.put(FOOD_NAME, shopping.getFood());
        contentValuesShop.put(FOOD_PRICE, shopping.getPrice());
        contentValuesShop.put(USER_NAME, user.getUsername());
        sqLiteDatabase.insert(FOOD_TABLE, null, contentValuesShop);

        Shopping shopping2 = new Shopping("Bananes", 4.0);
        ContentValues contentValuesShop2 = new ContentValues();
        contentValuesShop2.put(FOOD_NAME, shopping2.getFood());
        contentValuesShop2.put(FOOD_PRICE, shopping2.getPrice());
        contentValuesShop2.put(USER_NAME, user.getUsername());
        sqLiteDatabase.insert(FOOD_TABLE, null, contentValuesShop2);

        //Listes créées par défaut
        Shopping shoppingA = new Shopping("Pain", 1.0);
        Shopping shoppingB = new Shopping("Poisson", 8.5);
        Shopping shoppingC = new Shopping("Riz", 2.0);
        Shopping shoppingD = new Shopping("Lait", 1.5);
        Shopping shoppingE = new Shopping("Poulet", 8.0);

        ArrayList<Shopping> shoppings1 = new ArrayList<>();
        shoppings1.add(shoppingA);
        shoppings1.add(shoppingB);
        shoppings1.add(shoppingC);

        ShoppingList shoppingList1 = new ShoppingList("Ma premiere liste", shoppings1);
        ContentValues contentValuesShopList1 = new ContentValues();
        contentValuesShopList1.put(LIST_NAME, shoppingList1.getName());
        Gson gson1 = new Gson();
        String foodList1 = gson1.toJson(shoppingList1.getShoppings());
        contentValuesShopList1.put(LIST_FOOD, foodList1);
        contentValuesShopList1.put(USER_NAME, user.getUsername());
        sqLiteDatabase.insert(SHOPPING_TABLE, null, contentValuesShopList1);

        ArrayList<Shopping> shoppings2 = new ArrayList<>();
        shoppings2.add(shoppingC);
        shoppings2.add(shoppingD);
        shoppings2.add(shoppingE);

        ShoppingList shoppingList2 = new ShoppingList("Ma deuxieme liste",shoppings2);
        ContentValues contentValuesShopList2 = new ContentValues();
        contentValuesShopList2.put(LIST_NAME, shoppingList2.getName());
        Gson gson2 = new Gson();
        String foodList2 = gson2.toJson(shoppingList2.getShoppings());
        contentValuesShopList2.put(LIST_FOOD, foodList2);
        contentValuesShopList2.put(USER_NAME, user.getUsername());
        sqLiteDatabase.insert(SHOPPING_TABLE, null, contentValuesShopList2);

        //Alerte de bienvenue
        Alert alertWelcome = new Alert(Alerts.WELCOME.toString().replace("%username%", user.getfName()), "19/01/19 18:52");
        ContentValues contentValuesAlertWelcome = new ContentValues();
        contentValuesAlertWelcome.put(ALERT_STRING, alertWelcome.getAlert());
        contentValuesAlertWelcome.put(ALERT_DATE, alertWelcome.getDate());
        contentValuesAlertWelcome.put(USER_NAME, user.getUsername());
        sqLiteDatabase.insert(ALERTS_TABLE, null, contentValuesAlertWelcome);


        Log.d(TAG, "DatabaseHelper: Created");
        StaticContentUsers.addUserFromDataBase(user);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        initializeUsers(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
    }

    private void initializeUsers(SQLiteDatabase db) {
        String[] columns = {ID, USER_NAME, USER_PASSWORD, USER_EMAIL, USER_FIRSTNAME, USER_THRESHOLD};
        Cursor cursor = db.query(USER_TABLE, columns, null, null, null, null, null);
        while (cursor.moveToNext()) {
            // int id = cursor.getInt(0);
            String username = cursor.getString(1);
            String password = cursor.getString(2);
            String email = cursor.getString(3);
            String fname = cursor.getString(4);
            double threshold = cursor.getDouble(5);
            User user = new User(username, password, email, fname, threshold);
            StaticContentUsers.addUserFromDataBase(user);
        }
        cursor.close();
    }


    //Vérification de la combinaison utilisateur / mot de passe
    public User checkUser(String username, String password) {
        String[] columns = {ID};
        SQLiteDatabase db = getReadableDatabase();
        String selection = USER_NAME + "=?" + " and " + USER_PASSWORD + "=?";
        String[] selectionArgs = {username, password};
        Cursor cursor = db.query(USER_TABLE, columns, selection, selectionArgs, null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        if (count > 0) {
            return StaticContentUsers.getUser(username);
        }
        return null;
    }

    public Cursor viewMenuData(User user) {
        String[] columns = {ID, FOOD_NAME, FOOD_PRICE, USER_NAME};
        SQLiteDatabase db = getReadableDatabase();
        String selection = USER_NAME + "=?";
        String[] selectionArgs = {user.getUsername()};
        return db.query(FOOD_TABLE, columns, selection, selectionArgs, null, null, null);
    }

    public Cursor viewShoppingListData(User user) {
        String[] columns = {ID, LIST_NAME, LIST_FOOD, USER_NAME};
        SQLiteDatabase db = getReadableDatabase();
        String selection = USER_NAME + "=?";
        String[] selectionArgs = {user.getUsername()};
        return db.query(SHOPPING_TABLE, columns, selection, selectionArgs, null, null, null);
    }

    public Cursor viewShoppingsOfList(User user, String listName) {
        String[] columns = {ID, LIST_NAME, LIST_FOOD, USER_NAME};
        SQLiteDatabase db = getReadableDatabase();
        String selection = LIST_NAME + "='" + listName +"'" + " AND " + USER_NAME + " = '" + user.getUsername() + "'";
        return db.query(SHOPPING_TABLE, columns, selection, null, null, null, null);
    }

    public Cursor viewAlertsData(User user) {
        String[] columns = {ID, USER_NAME, ALERT_STRING, ALERT_DATE};
        SQLiteDatabase db = getReadableDatabase();
        String selection = USER_NAME + "=?";
        String[] selectionArgs = {user.getUsername()};
        return db.query(ALERTS_TABLE, columns, selection, selectionArgs, null, null, null);
    }

    public boolean insertFood(String food) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(USER_FIRSTNAME, food);
        long result = db.insert(USER_TABLE, null, contentValues);
        return result != -1;
    }

    public boolean addFood(Shopping shopping, User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(FOOD_NAME, shopping.getFood());
        contentValues.put(FOOD_PRICE, shopping.getPrice());
        contentValues.put(USER_NAME, user.getUsername());
        long result = db.insert(FOOD_TABLE, null, contentValues);
        return result != -1;
    }

    public boolean addShoppingList(ShoppingList shoppingList, User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(LIST_NAME, shoppingList.getName());

        Gson gson = new Gson();
        String foodList = gson.toJson(shoppingList.getShoppings());
        contentValues.put(LIST_FOOD, foodList);
        contentValues.put(USER_NAME, user.getUsername());
        long result = db.insert(SHOPPING_TABLE, null, contentValues);
        return result != -1;
    }

    public boolean addAlert(Alert alert, User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(ALERT_STRING, alert.getAlert());
        contentValues.put(ALERT_DATE, alert.getDate());
        contentValues.put(USER_NAME, user.getUsername());
        long result = db.insert(ALERTS_TABLE, null, contentValues);
        return result != -1;
    }

    public long addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("username", user.getUsername());
        contentValues.put("password", user.getPassword());
        contentValues.put("email", user.getEmail());
        contentValues.put("fName", user.getfName());
        contentValues.put("threshold", user.getThreshold());
        long res = db.insert(USER_TABLE, null, contentValues);
        db.close();
        return res;
    }

    public void UpdatePassword(String OldPass, String NewPass) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("password", NewPass);
        SQLiteDatabase db = this.getWritableDatabase();
        db.update(USER_TABLE, contentValues, "password", null);
    }


    public int updateShoppingList(ShoppingList currentShoppingList, User currentUser) {
        SQLiteDatabase db = this.getWritableDatabase();
        String whereClause = LIST_NAME + "='" + currentShoppingList.getName() +"'";
        ContentValues contentValues = new ContentValues();

        Gson gson = new Gson();
        String foodList = gson.toJson(currentShoppingList.getShoppings());
        contentValues.put(LIST_FOOD, foodList);
//        contentValues.put(USER_NAME, currentUser.getUsername());
        return db.update(SHOPPING_TABLE, contentValues, whereClause, null);
    }

    public void deleteFood(Shopping shopping) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM " + FOOD_TABLE + " WHERE " + FOOD_NAME + " = \"" + shopping.getFood() + "\" AND " + FOOD_PRICE + " = " + shopping.getPrice();
        db.execSQL(sql);
    }

    public void deleteList(ShoppingList currentShoppingList) {
        SQLiteDatabase db = this.getWritableDatabase();
        String sql = "DELETE FROM " + SHOPPING_TABLE + " WHERE " + LIST_NAME +  "='" + currentShoppingList.getName() +"'";
        db.execSQL(sql);
    }
}

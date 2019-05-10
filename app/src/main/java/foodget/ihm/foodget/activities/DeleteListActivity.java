package foodget.ihm.foodget.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import foodget.ihm.foodget.DatabaseHelper;
import foodget.ihm.foodget.R;
import foodget.ihm.foodget.models.User;

public class DeleteListActivity extends AppCompatActivity {
    User currentUser;
    DatabaseHelper db;


    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_list);


        db= new DatabaseHelper(this);

        Bundle data = getIntent().getExtras();
        currentUser = data.getParcelable("USER");


    }

}

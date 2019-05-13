package foodget.ihm.foodget.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import foodget.ihm.foodget.R;
import foodget.ihm.foodget.models.User;

public class MultipleFragmentActivity extends AppCompatActivity {


    private User currentUser = new User("julie", "123", "julie", "Julie", 23.0);
    private Fragment fragmentLeft;
    private Fragment fragmentRight;

    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_fragment);

        fragmentLeft = new TabMainMenu();
        fragmentRight = new StatFragment();

        Bundle bundle = new Bundle();
        bundle.putParcelable("user", currentUser);
        fragmentLeft.setArguments(bundle);
        fragmentRight.setArguments(bundle);
        Toast.makeText(this, "" + currentUser , Toast.LENGTH_SHORT).show();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLeft, fragmentLeft).replace(R.id.fragmentRight, fragmentRight).commit();}


        //adapt the viewPager with the adapter

}

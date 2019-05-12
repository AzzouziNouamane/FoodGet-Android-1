package foodget.ihm.foodget.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import java.util.Objects;

import foodget.ihm.foodget.R;
import foodget.ihm.foodget.adapters.PageAdapter;
import foodget.ihm.foodget.adapters.PageAdapterNidhal;
import foodget.ihm.foodget.models.User;

public class MultipleFragmentActivity extends AppCompatActivity {


    private User currentUser = new User("julie", "123", "julie", "Julie", 23.0);
    private Fragment fragmentLeft;
    private Fragment fragmentRight;
    private PageAdapterNidhal pageAdapter;

    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_fragment);
        ViewPager viewPager = findViewById(R.id.container1);
        fragmentLeft = new TabMyAccount();
        fragmentRight = new TabMyAccount();
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", currentUser);
        fragmentLeft.setArguments(bundle);
        fragmentRight.setArguments(bundle);
        Toast.makeText(this, "" + currentUser , Toast.LENGTH_SHORT).show();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentLeft, fragmentLeft).replace(R.id.fragmentRight, fragmentRight).commit();


        //adapt the viewPager with the adapter
    }
}

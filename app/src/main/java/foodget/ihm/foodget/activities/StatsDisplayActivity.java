package foodget.ihm.foodget.activities;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import foodget.ihm.foodget.R;
import foodget.ihm.foodget.adapters.StatsAdapter;
import foodget.ihm.foodget.models.User;

public class StatsDisplayActivity extends AppCompatActivity {

    private StatsAdapter adapter;
    private User loggedUser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stats);

        final ViewPager viewPager = findViewById(R.id.stats_container);
        loggedUser = getIntent().getExtras().getParcelable("user");
        adapter = new StatsAdapter(getSupportFragmentManager(), loggedUser);
        viewPager.setAdapter(adapter);

    }
}

package foodget.ihm.foodget.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import java.util.Objects;

import foodget.ihm.foodget.R;
import foodget.ihm.foodget.adapters.PageAdapter;
import foodget.ihm.foodget.models.User;

public class MultipleFragmentActivity extends AppCompatActivity {
    private PageAdapter adapter;

    @Override
    protected void onCreate( Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_fragment);

        setTitle("Test");
    }
}

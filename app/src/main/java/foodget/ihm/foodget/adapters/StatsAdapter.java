package foodget.ihm.foodget.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import foodget.ihm.foodget.fragments.StatFragment;
import foodget.ihm.foodget.models.User;

public class StatsAdapter extends FragmentPagerAdapter {

    private User loggedUser;

    public StatsAdapter(FragmentManager fm, User loggedUser) {
        super(fm);
        this.loggedUser = loggedUser;
    }

    @Override
    public Fragment getItem(int i) {
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", loggedUser);
        Fragment frag = new StatFragment();
        frag.setArguments(bundle);
        return frag;
    }

    @Override
    public int getCount() {
        return 1;
    }
}

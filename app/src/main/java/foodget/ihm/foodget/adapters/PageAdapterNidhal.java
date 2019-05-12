package foodget.ihm.foodget.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import foodget.ihm.foodget.fragments.TabMainMenu;
import foodget.ihm.foodget.fragments.TabMyAccount;
import foodget.ihm.foodget.models.User;

public class PageAdapterNidhal extends FragmentStatePagerAdapter {

    private User loggedUser;
    private int tabCount;

    public PageAdapterNidhal(FragmentManager supportFragmentManager, User loggedUser) {
        super(supportFragmentManager);
        this.loggedUser = loggedUser;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        Bundle bundle = new Bundle();
        bundle.putParcelable("user", loggedUser);
        switch (i) {
            case 0:
                fragment = new TabMainMenu();
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new TabMyAccount();
                fragment.setArguments(bundle);
                break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }
}

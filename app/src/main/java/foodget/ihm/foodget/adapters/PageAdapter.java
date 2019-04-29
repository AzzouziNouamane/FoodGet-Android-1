package foodget.ihm.foodget.adapters;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import foodget.ihm.foodget.fragments.TabAlerts;
import foodget.ihm.foodget.fragments.TabMainMenu;
import foodget.ihm.foodget.fragments.TabMyAccount;

public class PageAdapter extends FragmentStatePagerAdapter {
    private int tabCount;

    public PageAdapter(FragmentManager supportFragmentManager, int tabCount) {
        super(supportFragmentManager);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int i) {
        Fragment fragment = null;
        switch (i){
            case 0 : fragment = new TabMainMenu(); break;
            case 1 : fragment = new TabAlerts(); break;
            case 2 : fragment = new TabMyAccount(); break;
        }
        return fragment;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}

package foodget.ihm.foodget.fragments;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import foodget.ihm.foodget.DatabaseHelper;
import foodget.ihm.foodget.R;
import foodget.ihm.foodget.activities.LoginActivity;
import foodget.ihm.foodget.adapters.AlertListAdapter;
import foodget.ihm.foodget.models.Alert;
import foodget.ihm.foodget.models.User;


public class TabAlerts extends Fragment {

    String TAG = "AlertsMenu";
    DatabaseHelper db;
    ArrayList<Alert> listAlerts;
    ListView alertsView;
    ArrayAdapter adapter;
    User currentUser;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        //inflate fragment_tab1
        final View view = inflater.inflate(R.layout.activity_alerts, container, false);
        db = new DatabaseHelper(getContext());
        currentUser = this.getArguments().getParcelable("user");
        listAlerts = new ArrayList<>();
        alertsView = view.findViewById(R.id.alerts_list);
        viewDataInAlerts();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        listAlerts.clear();
        viewDataInAlerts();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (this.isVisible()) {
            listAlerts.clear();
            viewDataInAlerts();
        }
    }

    private void viewDataInAlerts() {
        Cursor cursor = db.viewAlertsData();
        if (cursor.getCount() == 0) {
            Toast.makeText(getContext(), "No data to view", Toast.LENGTH_SHORT).show();
        } else {
            while (cursor.moveToNext()) {
                if (cursor.getString(1).equals(currentUser.getUsername())) {
                    Alert alert = new Alert(cursor.getString(2), cursor.getString(3));
                    listAlerts.add(alert);
                } else {
                    Intent ToLoginPageIntent = new Intent(getContext(), LoginActivity.class);
                    Toast.makeText(getContext(), "Erreur de programme. Veuillez vous reconnecter", Toast.LENGTH_SHORT).show();
                    startActivity(ToLoginPageIntent);
                }

            }

            adapter = new ArrayAdapter(getContext(), R.layout.alert_display, listAlerts);
            AlertListAdapter adapteralert = new AlertListAdapter(getContext(), R.layout.alert_display, listAlerts);
            alertsView.setAdapter(adapteralert);
        }
    }
}

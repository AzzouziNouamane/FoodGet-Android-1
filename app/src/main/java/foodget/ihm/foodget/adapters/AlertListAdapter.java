package foodget.ihm.foodget.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import foodget.ihm.foodget.R;
import foodget.ihm.foodget.models.Alert;

public class AlertListAdapter extends ArrayAdapter<Alert> {

    private Context context;
    private int resource;

    public AlertListAdapter(Context context, int resource, ArrayList<Alert> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String alert = getItem(position).getAlert();
        String date = getItem(position).getDate();

        Alert shopping = new Alert(alert, date);

        LayoutInflater layoutInflater = LayoutInflater.from(context);
        convertView = layoutInflater.inflate(resource, parent, false);

        TextView tvAlert = convertView.findViewById(R.id.alertView);
        TextView tvDate = convertView.findViewById(R.id.dateView);

        tvAlert.setText(alert);
        tvDate.setText(date);

        return convertView;
    }
}

package foodget.ihm.foodget.fragments;

import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import foodget.ihm.foodget.DatabaseHelper;
import foodget.ihm.foodget.R;
import foodget.ihm.foodget.models.Shopping;
import foodget.ihm.foodget.models.User;

public class StatFragment extends Fragment {

    List<Shopping> listItem;
    private View view;
    private Spinner spinner;
    private LineChart lineChart;
    private ArrayAdapter<CharSequence> choices;
    private int choice;
    private DatabaseHelper db;
    private User currentUser;
    private List<String> xAXES = new ArrayList<>();
    private List<Entry> yAXES = new ArrayList<>();
    private String[] xValues;
    private float[] values;
    private Description desc;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        db = new DatabaseHelper(getContext());
        currentUser = this.getArguments().getParcelable("user");
        listItem = new ArrayList<>();
        view = inflater.inflate(R.layout.stats_fragment, container, false);
        lineChart = view.findViewById(R.id.linechart);
        spinner = view.findViewById(R.id.spinner);
        choices = ArrayAdapter.createFromResource(getContext(),
                R.array.choice_array, android.R.layout.simple_spinner_item);
        choices.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(choices);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position >= 0 && position <= 2) {
                    choice = position;
                }
                initListOfShopppings();
                computeLineChart();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                choice = 0;
                initListOfShopppings();
                computeLineChart();
            }
        });

        return view;
    }

    private void computeLineChart() {
        switch (choice) {
            case 0:
                compute30days();
                break;
            case 1:
                compute7days();
                break;
            case 2:
                compute24hours();
                break;
        }


        ArrayList<ILineDataSet> lineDataSets = new ArrayList<>();

        LineDataSet lineDataSet1 = new LineDataSet(yAXES, "Dépenses");
        lineDataSet1.setDrawCircles(false);
        lineDataSet1.setColor(Color.parseColor("#ffbd4a"));
        lineDataSet1.setLineWidth(3f);
        lineChart.getXAxis().setValueFormatter(new XAxisFormatter(xValues));
        lineDataSet1.setValueFormatter(new YAxisformatter(values));


        lineDataSets.add(lineDataSet1);

        LimitLine threshold = new LimitLine((float) currentUser.getThreshold(), "Seuil : " + currentUser.getThreshold() + "€");
        threshold.setLineWidth(2f);
        threshold.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        threshold.setTextSize(14f);


        lineChart.getAxisLeft().addLimitLine(threshold);
        lineChart.getAxisLeft().setDrawLimitLinesBehindData(true);
        lineChart.getAxisLeft().setAxisMaximum(Math.max(lineChart.getYChartMax(), (float) currentUser.getThreshold()));
        lineChart.setData(new LineData(lineDataSets));
        lineChart.getAxisRight().setEnabled(false);
        lineChart.setDescription(desc);

        lineChart.invalidate();
    }

    private void compute30days() {
        LocalDateTime now = LocalDateTime.now();
        values = new float[30];
        xValues = new String[30];
        LocalDateTime[] daysValues = new LocalDateTime[30];
        for (int i = 0; i < 30; i++) {
            daysValues[i] = now.minusDays(30 - i);
            xValues[i] = (now.minusDays(30 - i).getDayOfMonth() + "/" + now.minusDays(30 - i).getMonthValue());
        }

        float total = 0;
        for (Shopping s : listItem) {
            total += s.getPrice();
            values[s.getDateAsDate().getDayOfYear() != now.getDayOfYear() ?
                    s.getDateAsDate().getDayOfYear() - daysValues[0].getDayOfYear() : 29] = total;
        }
        float max = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] > max) {
                max = values[i];
            }
            yAXES.add(new Entry(i, max));

        }
        desc = new Description();
        desc.setText("30 derniers jours");
    }

    private void compute7days() {
        values = new float[7];
        xValues = new String[7];
        LocalDateTime[] daysValues = new LocalDateTime[7];
        for (int i = 0; i < 7; i++) {
            daysValues[i] = LocalDateTime.now().minusDays(7 - i);
            xValues[i] = (LocalDateTime.now().minusDays(7 - i).getDayOfWeek() + "").substring(0, 3);
        }
        float total = 0;
        for (Shopping s : listItem) {
            total += s.getPrice();
            values[s.getDateAsDate().getDayOfMonth() != LocalDateTime.now().getDayOfMonth() ?
                    s.getDateAsDate().getDayOfWeek().compareTo(LocalDateTime.now().getDayOfWeek()) : 6] = total;
        }
        float max = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] > max) {
                max = values[i];

            }
            yAXES.add(new Entry(i, max));

        }
        desc = new Description();
        desc.setText("7 derniers jours");
    }

    private void compute24hours() {
        int nowHour = LocalDateTime.now().getHour();
        values = new float[24];
        xValues = new String[24];
        int a = nowHour == 24 ? 0 : nowHour + 1;
        xValues[0] = (nowHour) + "h";
        int b = 1;
        while (a != nowHour) {
            if (a == 24) {
                a = 0;
            }
            xValues[b] = (a) + "h";
            b++;
            a++;
        }

        float total = 0;
        for (Shopping s : listItem) {
            total += s.getPrice();
            values[s.getDateAsDate().getHour() >= nowHour ?
                    s.getDateAsDate().getHour() == nowHour ?
                            s.getDateAsDate().getDayOfWeek() != LocalDateTime.now().getDayOfWeek() ?
                                    0 : 23
                            : s.getDateAsDate().getHour() - nowHour :
                    24 - nowHour + s.getDateAsDate().getHour()] = total;
        }
        float max = 0;
        for (int i = 0; i < values.length; i++) {
            if (values[i] > max) {
                max = values[i];

            }
            yAXES.add(new Entry(i, max));

        }
        desc = new Description();
        desc.setText("24 dernières heures");

    }

    private void initListOfShopppings() {
        xAXES.clear();
        yAXES.clear();
        Cursor cursor = db.viewMenuData(currentUser);
        listItem.clear();
        if (cursor.getCount() == 0) {
            listItem.clear();
        } else {
            while (cursor.moveToNext()) {
                if (cursor.getString(3).equals(currentUser.getUsername())) {
                    Shopping newShopping = new Shopping(cursor.getString(1), cursor.getDouble(2), cursor.getString(4));
                    listItem.add(newShopping);
                }

            }

        }

        if (listItem != null && !listItem.isEmpty()) {
            listItem.sort(Comparator.comparing(Shopping::getDateAsDate).reversed());
            switch (choice) {
                case 0:
                    listItem = listItem.stream().filter(shopping -> shopping.getDateAsDate().isAfter(LocalDateTime.now().minusDays(30)))
                            .collect(Collectors.toList());
                    break;
                case 1:
                    listItem = listItem.stream().filter(shopping -> shopping.getDateAsDate().isAfter(LocalDateTime.now().minusDays(7)))
                            .collect(Collectors.toList());
                    break;
                case 2:
                    listItem = listItem.stream().filter(shopping -> shopping.getDateAsDate().isAfter(LocalDateTime.now().minusHours(24)))
                            .collect(Collectors.toList());
                    for (int i = 0; i < 24; i++) {
                        xAXES.add(i, i + "h");
                    }
                    break;
            }

            listItem.sort(Comparator.comparing(Shopping::getDateAsDate));
        }

    }

    private class YAxisformatter extends ValueFormatter {


        float precedentValue = 0;
        private float[] values;

        public YAxisformatter(float[] values) {
            this.values = values;
        }

        @Override
        public String getFormattedValue(float value) {
            if (value > 0) {
                if (value != precedentValue) {
                    precedentValue = value;
                    return String.valueOf(value);
                } else {
                    return "";
                }
            } else {
                return "";
            }
        }
    }

    private class XAxisFormatter extends ValueFormatter {

        String[] xValues;

        XAxisFormatter(String[] xValues) {
            this.xValues = xValues;
        }


        @Override
        public String getFormattedValue(float value) {
            return value < xValues.length ? xValues[(int) value] : String.valueOf(value);
        }
    }


}

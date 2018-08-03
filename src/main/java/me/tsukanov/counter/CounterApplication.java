package me.tsukanov.counter;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;

import java.util.LinkedHashMap;
import java.util.Map;

import me.tsukanov.counter.ui.CounterFragment;

public class CounterApplication extends Application {

    private static final String DATA_FILE_NAME = "counters";
    public LinkedHashMap<String, Integer> counters;
    private SharedPreferences data;

    @Override
    public void onCreate() {
        super.onCreate();
        counters = new LinkedHashMap<>();
        data = getBaseContext().getSharedPreferences(DATA_FILE_NAME, Context.MODE_PRIVATE);
        loadCounters();
    }

    public void loadCounters() {
        Map<String, ?> dataMap = data.getAll();
        if (dataMap.isEmpty()) {
            counters.put((String) getResources().getText(R.string.default_counter_name), CounterFragment.DEFAULT_VALUE);
        } else {
            for (Map.Entry<String, ?> entry : dataMap.entrySet()) {
                counters.put(entry.getKey(), (Integer) entry.getValue());
            }
        }
    }

    public void saveCounters() {
        SharedPreferences.Editor dataEditor = data.edit();
        dataEditor.clear();
        for (String name : counters.keySet()) {
            dataEditor.putInt(name, counters.get(name));
        }
        dataEditor.commit();
    }

    public void removeCounters() {
        counters.clear();
        counters.put((String) getResources().getText(R.string.default_counter_name), CounterFragment.DEFAULT_VALUE);
    }

}

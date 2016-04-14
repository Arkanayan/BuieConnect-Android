package me.arkanayan.buieconnect.adapters;

import android.databinding.BindingAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import java.util.ArrayList;

/**
 * Created by arka on 4/13/16.
 */
public class UserBindingAdapter {

    // sets current spinner value to user value
    @BindingAdapter("bind:currentValue")
    public static void setSpinnerCurrentValue(Spinner spinner, Object value) {
        value = (String) String.valueOf(value);
        ArrayList<String> spinnerEntries = new ArrayList<String>();
        SpinnerAdapter adapter = spinner.getAdapter();
        for (int i = 0 ; i < adapter.getCount(); i++) {
            spinnerEntries.add((String) adapter.getItem(i));
        }
        int position = spinnerEntries.indexOf(value);
        spinner.setSelection(position);
    }
}

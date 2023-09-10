package com.example.chordp.viewModels;

import java.util.ArrayList;

public class MainViewModel {
    private static MainViewModel viewModel;
    private ArrayList<Integer> checked;
    private int interval;

    private MainViewModel(){
        checked = new ArrayList<>();    // by default none is selected
        interval = 5000;                // by default interval of 5 seconds
    }

    public static MainViewModel getInstance() {
        if (viewModel != null)
            return viewModel;
        return viewModel = new MainViewModel();
    }

    public void addChord(int val) {
        if (!checked.contains(val))
            checked.add(val);
    }

    public void removeChord(int val) {
        if (checked.contains(val))
            checked.remove((Integer) val);
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getSize() {
        return checked.size();
    }

    public int getChord(int index) {
        return checked.get(index);
    }
}

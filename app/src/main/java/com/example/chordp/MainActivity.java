package com.example.chordp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Gravity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.chordp.viewModels.MainViewModel;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private CountDownTimer timer;
    private final long TIMER_RUN_TIME = 600000;
    private boolean isTimerRunning = false;
    private int currentIndex = -1;

    private final MainViewModel viewModel = MainViewModel.getInstance();
    private EditText interval;
    private Button btn;
    private TextView currentChord;


    private final HashMap<Integer, String> map = new HashMap<Integer, String>() {{
        put(0, "AM");
        put(1, "BM");
        put(2, "CM");
        put(3, "DM");
        put(4, "EM");
        put(5, "FM");
        put(6, "GM");
        put(7, "Am");
        put(8, "Bm");
        put(9, "Cm");
        put(10, "Dm");
        put(11, "Em");
        put(12, "Fm");
        put(13, "Gm");
    }};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        Toast toast = Toast.makeText(
                getApplicationContext(),
                "Select the chords to play",
                Toast.LENGTH_SHORT
        );
        interval = findViewById(R.id.IntervalTV);
        btn = findViewById(R.id.Action);
        currentChord = findViewById(R.id.CurrentChord);

        List<CheckBox> cb = Arrays.asList(
                // Major Chords
                findViewById(R.id.AM),
                findViewById(R.id.BM),
                findViewById(R.id.CM),
                findViewById(R.id.DM),
                findViewById(R.id.EM),
                findViewById(R.id.FM),
                findViewById(R.id.GM),
                // Minor Chords
                findViewById(R.id.Am),
                findViewById(R.id.Bm),
                findViewById(R.id.Cm),
                findViewById(R.id.Dm),
                findViewById(R.id.Em),
                findViewById(R.id.Fm),
                findViewById(R.id.Gm)
        );

        for (int i = 0; i < cb.size() ; i++) {
            int finalI = i;
            cb.get(finalI).setOnCheckedChangeListener((buttonView, isChecked) -> {
                if (isChecked) {
                    viewModel.addChord(finalI);
                } else {
                    viewModel.removeChord(finalI);
                }
            });
        }

        btn.setOnClickListener((a) -> {
            if (viewModel.getSize() == 0) {
                toast.show();
            }
            else if (isTimerRunning) {
                timer.cancel();
                currentChord.setText("-");
                btn.setText(R.string.start);
                currentIndex = -1;
                isTimerRunning = false;
            } else {
                timer = getTimer();
                timer.start();
                btn.setText(R.string.stop);
                isTimerRunning = true;
            }
        });
    }

    private CountDownTimer getTimer() {
        if (interval.getText().toString().isEmpty()) {
            viewModel.setInterval(5000);Toast.makeText(
                    getApplicationContext(),
                    "Interval set to 5 secs by default",
                    Toast.LENGTH_SHORT
            ).show();
            interval.setText("5");
        } else {
            viewModel.setInterval((int) (Float.parseFloat(interval.getText().toString()) * 1000));
        }
        return new CountDownTimer(TIMER_RUN_TIME, viewModel.getInterval()) {
            public void onTick(long millisUntilFinished) {
                int index = currentIndex;
                while ( viewModel.getSize() > 1 && index == currentIndex ) {
                    int checkedIndex = (int) (Math.random() * viewModel.getSize());
                    index = viewModel.getChord(checkedIndex);
                }
                currentChord.setText(map.get(index));
                currentIndex = index;
            }

            public void onFinish() {
                timer.start();
            }
        };
    }
}
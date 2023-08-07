package com.example.madlast;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.ToggleButton;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class triggeractivity1 extends AppCompatActivity {
    private static final String TAG = "triggeractivity1";
    private Button btnhomepage;
    private TimePicker alarmTimePicker;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;
    private String meddate;
    private String medtime;
    private String medname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_triggeractivity1);
        meddate = getIntent().getStringExtra("date");
        medtime = getIntent().getStringExtra("time");
        medname = getIntent().getStringExtra("name");
        btnhomepage = findViewById(R.id.homeButton);
        alarmTimePicker = findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);

        btnhomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent home = new Intent(triggeractivity1.this, MainActivity.class);
                startActivity(home);
            }
        });
    }

    public void OnToggleClicked(View view) {
        boolean isChecked = ((ToggleButton) view).isChecked();
        if (isChecked) {
            Toast.makeText(triggeractivity1.this, "ALARM ON", Toast.LENGTH_SHORT).show();
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
            calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());
            calendar.set(Calendar.SECOND, 0);
            long alarmTime = calendar.getTimeInMillis();

            Intent alarmIntent = new Intent(triggeractivity1.this, AlarmReceiver.class);
            alarmIntent.putExtra("date", meddate);
            alarmIntent.putExtra("time", medtime);
            alarmIntent.putExtra("name", medname);


            pendingIntent = PendingIntent.getBroadcast(this, 0, alarmIntent, PendingIntent.FLAG_IMMUTABLE);
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);
        }
        else {
            if (pendingIntent != null) {
                alarmManager.cancel(pendingIntent);
            }
            Toast.makeText(triggeractivity1.this, "ALARM OFF", Toast.LENGTH_SHORT).show();
        }
    }
}

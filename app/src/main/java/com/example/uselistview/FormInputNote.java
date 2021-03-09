package com.example.uselistview;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.*;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.TimeZone;

public class FormInputNote extends AppCompatActivity {
    TextView tvDate, tvTime;
    EditText edtTitle, edtVal;
    ImageButton btTime, btDate;
    long ltime = 0;
    int ld, lm, ly, lh, ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_input_data);

        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        actionBar.setTitle("Add New Note");
        actionBar.setDisplayHomeAsUpEnabled(true);
        tvDate = (TextView) findViewById(R.id.tvDate);
        tvTime = (TextView) findViewById(R.id.tvTime);
        edtTitle = (EditText) findViewById(R.id.edtTitle);
        edtVal = (EditText) findViewById(R.id.edtVal);
        btDate = (ImageButton) findViewById(R.id.btDate);
        btTime = (ImageButton) findViewById(R.id.btTime);
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+7"));
        Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(calendar.DAY_OF_MONTH);
        int thang = calendar.get(calendar.MONTH);
        int nam = calendar.get(calendar.YEAR);
        int gio = calendar.get(calendar.HOUR_OF_DAY);
        int phut = calendar.get(calendar.MINUTE);
        ld = ngay;
        lm = thang + 1;
        ly = nam;
        lh = gio;
        ls = phut;
        tvDate.setText(String.format("%02d / %02d / %d", ngay, thang + 1, nam));
        tvTime.setText(String.format("%02d : %02d", gio, phut));

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void doBtDate(final View view) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+7"));
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(calendar.DAY_OF_MONTH);
        int thang = calendar.get(calendar.MONTH);
        int nam = calendar.get(calendar.YEAR);

        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                ld = dayOfMonth;
                lm = month + 1;
                ly = year;
                tvDate.setText(String.format("%02d / %02d / %d", dayOfMonth, month + 1 , year));
            }
        };
        System.out.println(calendar.getTime());
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                android.R.style.ThemeOverlay_Material_Dialog_Alert,
                dateSetListener, nam, thang, ngay);
        datePickerDialog.show();


    }

    public void doBtTime(View view) {
        TimeZone.setDefault(TimeZone.getTimeZone("GMT+7"));
        Calendar calendar = Calendar.getInstance();
        int gio = calendar.get(calendar.HOUR_OF_DAY);
        int phut = calendar.get(calendar.MINUTE);
        TimePickerDialog.OnTimeSetListener timeSetListener = new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                lh = hourOfDay;
                ls = minute;
                tvTime.setText(String.format("%02d : %02d", hourOfDay, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                timeSetListener, gio, phut, true);
        timePickerDialog.show();
    }

    public void doBtThem(View view) {
        ConectDatabase conectDatabase = new ConectDatabase(this);
        Calendar calendar = Calendar.getInstance();
        calendar.set(ly, lm, ld, lh, ls);
        ltime = calendar.getTimeInMillis();
        String title, time, date, value;
        title = edtTitle.getText().toString();
        time = tvTime.getText().toString();
        date = tvDate.getText().toString();
        value = edtVal.getText().toString();

        Note note = new Note(conectDatabase.getCountNote() + 1, title, ltime, value);
        conectDatabase.addNote(note);

        finish();
    }

}
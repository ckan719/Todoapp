package com.example.uselistview;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class CustomNoteActivity extends AppCompatActivity {
    ConectDatabase conectDatabase;
    ArrayList<Note> arrayList;
    TextView tvDate, tvTime;
    EditText edtTitle, edtValue;
    int position;
    int ld, lm, ly, lh, ls;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_note);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setBackgroundDrawable(new ColorDrawable(Color.parseColor("#000000")));
        actionBar.setTitle("Edit Note");
        actionBar.setDisplayHomeAsUpEnabled(true);
        conectDatabase = new ConectDatabase(this);
        arrayList = conectDatabase.getAllNote();
        Intent intent = getIntent();
        position = intent.getIntExtra("INDEX", 0);

        tvDate = findViewById(R.id.tvDate);
        tvTime = findViewById(R.id.tvTime);
        edtTitle = findViewById(R.id.edtTitle);
        edtValue = findViewById(R.id.edtVal);

        Note note = arrayList.get(position);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(note.getTime());
        tvDate.setText(String.format("%02d/%02d/%d", calendar.get(Calendar.DAY_OF_MONTH), calendar.get(Calendar.MONTH)== 0 ? 12 :calendar.get(Calendar.MONTH) , calendar.get(Calendar.YEAR)));
        tvTime.setText(String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
        edtTitle.setText(note.getTitle());
        edtValue.setText(note.getValue());

        ld = calendar.get(Calendar.DAY_OF_MONTH);
        lm = calendar.get(Calendar.MONTH);
        ly = calendar.get(Calendar.YEAR);
        lh = calendar.get(Calendar.HOUR_OF_DAY);
        ls = calendar.get(Calendar.MINUTE);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(ly, lm, ld, lh, ls);
        arrayList.get(position).setTime(calendar.getTimeInMillis());
        arrayList.get(position).setTitle(edtTitle.getText().toString());
        arrayList.get(position).setValue(edtValue.getText().toString());
        Note note = arrayList.get(position);
        conectDatabase.updateNote(note);
        finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    public void doBtCalender(View view) {
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
                tvDate.setText(String.format("%02d/%02d/%d", dayOfMonth, month + 1, year));
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
                tvTime.setText(String.format("%02d:%02d", hourOfDay, minute));
            }
        };
        TimePickerDialog timePickerDialog = new TimePickerDialog(this,
                timeSetListener, gio, phut, true);
        timePickerDialog.show();
    }
}
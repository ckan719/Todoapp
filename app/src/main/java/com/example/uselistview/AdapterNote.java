package com.example.uselistview;

import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class AdapterNote extends BaseAdapter {

    ArrayList<Note> arrayList;

    public AdapterNote(ArrayList<Note> arrayList) {
        this.arrayList = arrayList;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return arrayList.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View view;

        if (convertView == null) view = View.inflate(parent.getContext(), R.layout.custom_layout_item, null);
        else view = convertView;

        Note note = arrayList.get(position);
        TextView tvTitle, tvTime;
        EditText edtVal;
        tvTime = (TextView) view.findViewById(R.id.tvTime);
        tvTitle = (TextView) view.findViewById(R.id.tvTitle);
        edtVal = (EditText) view.findViewById(R.id.edtVal);

        TimeZone.setDefault(TimeZone.getTimeZone("GMT+7"));
        Calendar calendar = Calendar.getInstance();
        Calendar calendar1 = Calendar.getInstance();
        calendar.setTimeInMillis(note.getTime());
        System.out.println("calendar = " + calendar.get(Calendar.MONTH));
        System.out.println("calendar1.get(Calendar.MONTH) = " + calendar1.get(Calendar.MONTH));
        int month =  calendar.get(Calendar.MONTH);
        int month1 =  calendar1.get(Calendar.MONTH);
        month1 += 1;
        month = month==0 ? 12 : month;
        System.out.println("month1 = " + month1);
        System.out.println("month = " + month);
        if (calendar1.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH) &&
                month1 == month &&
                calendar1.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)) {
            tvTime.setText(String.format("%02d:%02d", calendar.get(Calendar.HOUR_OF_DAY), calendar.get(Calendar.MINUTE)));
        } else
            tvTime.setText(String.format("%02d/%02d/%d", calendar.get(Calendar.DAY_OF_MONTH), month, calendar.get(Calendar.YEAR)));


        tvTitle.setText(note.getTitle());
        edtVal.setText(note.getValue());
        if(tvTitle.getText().toString().equalsIgnoreCase("")) tvTitle.setVisibility(View.GONE);
        if(edtVal.getText().toString().equalsIgnoreCase("")) edtVal.setVisibility(View.GONE);
        CheckBox checkBox = (CheckBox) view.findViewById(R.id.cbxChoose);
        checkBox.setVisibility(note.isVisible() ? View.VISIBLE : View.GONE);
        //checkBox.startAnimation(animation);
        checkBox.setChecked(note.isChoose());
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                arrayList.get(position).setChoose(isChecked);
            }
        });
        Animation animation = AnimationUtils.loadAnimation(view.getContext(),R.anim.anim_left);
        view.startAnimation(animation);
        return view;
    }
}

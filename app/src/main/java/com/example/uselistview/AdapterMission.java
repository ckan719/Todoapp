package com.example.uselistview;

import android.graphics.Paint;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import java.util.ArrayList;

public class AdapterMission extends BaseAdapter {
    ArrayList<Mission> arrayList;

    public AdapterMission(ArrayList<Mission> arrayList) {
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
        if (convertView == null) view = View.inflate(parent.getContext(), R.layout.custom_layout_item_1, null);
        else view = convertView;
        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_left);
        Mission mission = arrayList.get(position);
        TextView tvShowNv = view.findViewById(R.id.tvShowNv);
        tvShowNv.setText(mission.getValue());
        if (mission.isChoose()) {
            tvShowNv.setPaintFlags(tvShowNv.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        }
        ImageButton btDel = view.findViewById(R.id.btDel);
        btDel.setVisibility(mission.isVisible() ? View.VISIBLE : View.GONE);
        if (mission.isAnim()) view.startAnimation(animation);
        return view;
    }
}

package com.example.uselistview;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewPager;
    private TabLayout tabLayout;
    public int[] tabIcons = {
            R.drawable.baseline_article_black_18dp2x, R.drawable.baseline_bookmark_border_black_18dp2x
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        tabLayout = (TabLayout) findViewById((R.id.tabs));
        setupView();

        tabLayout.setupWithViewPager(viewPager);
        for (int i = 0; i < 2; ++i) {
            tabLayout.getTabAt(i).setIcon(tabIcons[i]);
        }
    }

    private void setupView() {
        ViewPagerApdapter apdapter = new ViewPagerApdapter(getSupportFragmentManager());
        apdapter.addFragment(new NoteFragment(), "Ghi chú");
        apdapter.addFragment(new MissionFragment(), "Nhiệm vụ");
        viewPager.setAdapter(apdapter);
    }
}
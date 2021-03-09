package com.example.uselistview;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NoteFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NoteFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private Button button;
    Context context;
    AdapterNote adapterNote;
    ArrayList<Note> arrayList;
    ListView listView;
    ConectDatabase conectDatabase;
    BottomNavigationView navigationView;

    //public boolean


    public NoteFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment today.
     */
    // TODO: Rename and change types and number of parameters
    public static NoteFragment newInstance(String param1, String param2) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_ghichu, container, false);
        // set Context
        context = view.getContext();
        // Conect database
        conectDatabase = new ConectDatabase(view.getContext());
        // get arr Note
        arrayList = conectDatabase.getAllNote();
        // find ID
        navigationView = (BottomNavigationView) view.findViewById(R.id.menuBottom);
        button = (Button) view.findViewById(R.id.btAdd);
        listView = view.findViewById(R.id.listView);
        // set action
        setMenuBottom();
        doClickButton();
        setListView();


        return view;

    }


    @Override
    public void onResume() {
        super.onResume();
        adapterNote.notifyDataSetChanged();
        arrayList = conectDatabase.getAllNote();
        for (Note ev : arrayList) {
            ev.setVisible(false);
        }
        adapterNote = new AdapterNote(arrayList);
        listView.setAdapter(adapterNote);
    }

    public void setListView() {
        listView.setLongClickable(true);
        adapterNote = new AdapterNote(arrayList);
        listView.setAdapter(adapterNote);
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                for (Note ev : arrayList) {
                    ev.setVisible(true);
                }
                arrayList.get(position).setChoose(true);
                navigationView.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.bottom_to_top);
                navigationView.startAnimation(animation);
                adapterNote.notifyDataSetChanged();
                listView.requestLayout();
                return true;
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(context, CustomNoteActivity.class) ;
                intent.putExtra("INDEX", position);
                System.out.println("callll");
                startActivity(intent);
            }
        });
    }

    public void doClickButton() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FormInputNote.class);
                startActivity(intent);
            }
        });
    }

    public void setMenuBottom() {
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull @NotNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.selectAll: {
                        for (Note ev : arrayList) {
                            ev.setChoose(true);
                        }
                        adapterNote.notifyDataSetChanged();
                        listView.requestLayout();
                        break;
                    }
                    case R.id.exit: {
                        for (Note ev : arrayList) {
                            ev.setVisible(false);
                            ev.setChoose(false);
                        }
                        button.setVisibility(View.VISIBLE);
                        navigationView.setVisibility(View.GONE);
                        adapterNote.notifyDataSetChanged();
                        listView.requestLayout();
                        break;
                    }
                    case R.id.delChoose: {
                        ArrayList<Note> temp = new ArrayList<>();
                        for (Note ev : arrayList) {
                            if (ev.isChoose()) {
                                conectDatabase.delNote(ev);
                                temp.add(ev);
                            } else ev.setVisible(false);
                        }
                        for (Note note : temp) {
                            arrayList.remove(note);
                        }
                        button.setVisibility(View.VISIBLE);
                        navigationView.setVisibility(View.GONE);
                        adapterNote.notifyDataSetChanged();
                        listView.requestLayout();
                        break;
                    }
                }
                return false;
            }
        });
    }
}
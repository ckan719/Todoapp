package com.example.uselistview;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;
import androidx.fragment.app.Fragment;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MissionFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MissionFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    Button btAdd, btTime;
    TextView tvFinish, btAddFinish;
    ListView listMission, finishList;
    EditText edtIpMission;
    Context context;
    ConectDatabase conectDatabase;
    AdapterMission adapterMission, myAdapter2;
    LinearLayout linearLayout;
    ArrayList<Mission> arrayList, arrM, arrF;
    BottomSheetBehavior sheetBehavior;
    LinearLayout bottomsheet;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MissionFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment future.
     */
    // TODO: Rename and change types and number of parameters
    public static MissionFragment newInstance(String param1, String param2) {
        MissionFragment fragment = new MissionFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_nhiemvu, container, false);
        // set Context
        context = view.getContext();
        // get connect database
        conectDatabase = new ConectDatabase(context);

        arrayList = conectDatabase.getAllMission();

        // find ID
        btAdd = (Button) view.findViewById(R.id.btAdd);
        listMission = (ListView) view.findViewById(R.id.listViewMission);
        finishList = (ListView) view.findViewById(R.id.finistList);
        linearLayout = view.findViewById(R.id.formIPMission);
        btAddFinish = view.findViewById(R.id.btAddMission);
        edtIpMission = view.findViewById(R.id.edtIPMission);
        btTime = view.findViewById(R.id.btTime);
        bottomsheet = view.findViewById(R.id.bottom_sheet);
        sheetBehavior = BottomSheetBehavior.from((bottomsheet));
        //set Layout
        setLayout();
        // set Action;
        setAction();

        return view;
    }

    public void setAction() {
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Animation animation = AnimationUtils.loadAnimation(context, R.anim.bottom_to_top);
                linearLayout.startAnimation(animation);
                linearLayout.setVisibility(View.VISIBLE);
                btAdd.setVisibility(View.GONE);
                bottomsheet.setVisibility(View.GONE);
            }
        });

        edtIpMission.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() != 0) btAddFinish.setEnabled(true);
                else btAddFinish.setEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        btAddFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int i = 0; i < arrM.size(); ++i) {
                    arrM.get(i).setAnim(false);
                    conectDatabase.updateMission(arrM.get(i));
                }
                linearLayout.setVisibility(View.GONE);
                btAdd.setVisibility(View.VISIBLE);
                bottomsheet.setVisibility(View.VISIBLE);
                Mission mission = new Mission(conectDatabase.getCountMission() + 1,
                        edtIpMission.getText().toString(),
                        false,
                        0, true);
                conectDatabase.addMission(mission);
                edtIpMission.setText("");
                arrayList.add(mission);
                arrM.add(mission);
                adapterMission.notifyDataSetChanged();
                listMission.requestLayout();
            }
        });

        listMission.setLongClickable(true);
        listMission.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                for (int i = 0; i < arrM.size(); ++i) {
                    arrM.get(i).setAnim(false);
                    arrM.get(i).setVisible(false);
                    conectDatabase.updateMission(arrM.get(i));
                }
                arrM.get(position).setVisible(true);
                adapterMission.notifyDataSetChanged();
                listMission.requestLayout();
                ImageButton bt = view.findViewById(R.id.btDel);
                Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_right);
                view.startAnimation(animation);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_erase);
                        view.startAnimation(animation);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                for (int i = 0; i < arrM.size(); ++i) {
                                    arrM.get(i).setAnim(false);
                                    arrM.get(i).setVisible(false);
                                    conectDatabase.updateMission(arrM.get(i));
                                }
                                conectDatabase.delMission(arrayList.get(position));
                                arrM.remove(arrM.get(position));
                                adapterMission.notifyDataSetChanged();
                                listMission.requestLayout();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                    }
                });

                return true;
            }
        });

        listMission.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                for (int i = 0; i < arrM.size(); ++i) {
                    arrM.get(i).setVisible(false);
                    conectDatabase.updateMission(arrM.get(i));
                }
                Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_erase);
                view.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        for (int i = 0; i < arrM.size(); ++i) {
                            arrM.get(i).setAnim(false);
                            conectDatabase.updateMission(arrM.get(i));
                        }
                        for (int i = 0; i < arrF.size(); ++i) {
                            arrF.get(i).setAnim(false);
                            conectDatabase.updateMission(arrF.get(i));
                        }
                        arrM.get(position).setChoose(true);
                        arrM.get(position).setAnim(true);
                        conectDatabase.updateMission(arrM.get(position));
                        arrF.add(arrM.get(position));
                        arrM.remove(arrM.get(position));
                        adapterMission.notifyDataSetChanged();
                        listMission.requestLayout();
                        myAdapter2.notifyDataSetChanged();
                        finishList.requestLayout();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });

            }
        });
        finishList.setLongClickable(true);
        finishList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                for (int i = 0; i < arrF.size(); ++i) {
                    arrF.get(i).setVisible(false);
                    conectDatabase.updateMission(arrF.get(i));
                }

                Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_erase);
                view.startAnimation(animation);
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        for (int i = 0; i < arrF.size(); ++i) {
                            arrF.get(i).setAnim(false);
                            conectDatabase.updateMission(arrF.get(i));
                        }
                        for (int i = 0; i < arrM.size(); ++i) {
                            arrM.get(i).setAnim(false);
                            conectDatabase.updateMission(arrM.get(i));
                        }
                        arrF.get(position).setChoose(false);
                        arrF.get(position).setAnim(true);
                        conectDatabase.updateMission(arrF.get(position));
                        arrM.add(arrF.get(position));
                        arrF.remove(arrF.get(position));
                        adapterMission.notifyDataSetChanged();
                        listMission.requestLayout();
                        myAdapter2.notifyDataSetChanged();
                        finishList.requestLayout();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
        finishList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, final View view, final int position, long id) {
                for (int i = 0; i < arrF.size(); ++i) {
                    arrF.get(i).setAnim(false);
                    arrF.get(i).setVisible(false);
                    conectDatabase.updateMission(arrF.get(i));
                }
                arrF.get(position).setVisible(true);
                myAdapter2.notifyDataSetChanged();
                finishList.requestLayout();
                ImageButton bt = view.findViewById(R.id.btDel);
                Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_right);
                view.startAnimation(animation);
                bt.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Animation animation = AnimationUtils.loadAnimation(view.getContext(), R.anim.anim_erase);
                        view.startAnimation(animation);
                        animation.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                for (int i = 0; i < arrF.size(); ++i) {
                                    arrF.get(i).setAnim(false);
                                    arrF.get(i).setVisible(false);
                                    conectDatabase.updateMission(arrF.get(i));
                                }
                                conectDatabase.delMission(arrayList.get(position));
                                arrF.remove(arrF.get(position));
                                myAdapter2.notifyDataSetChanged();
                                finishList.requestLayout();
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });

                    }
                });
                return true;
            }
        });
    }

    public void setLayout() {
        System.out.println("arrayList = " + arrayList);
        arrM = new ArrayList<>();
        arrF = new ArrayList<>();
        for (int i = 0; i < arrayList.size(); ++i) {
            arrayList.get(i).setAnim(false);
            arrayList.get(i).setVisible(false);
            conectDatabase.updateMission(arrayList.get(i));
        }
        for (Mission mission : arrayList) {
            if (!mission.isChoose()) arrM.add(mission);
            else arrF.add(mission);
        }

        adapterMission = new AdapterMission(arrM);
        listMission.setAdapter(adapterMission);
        myAdapter2 = new AdapterMission(arrF);
        finishList.setAdapter(myAdapter2);
    }

}
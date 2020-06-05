package com.example.asu.Fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asu.Adapters.recyclerAdapter;
import com.example.asu.DomainModel.DMLesson;
import com.example.asu.R;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

public class Tab1Fragment extends Fragment {

    public int day;

    public RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.tab1_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview1);


        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        Log.i("QWE", "Day: "+String.valueOf(day));
        List<DMLesson> list = DMLesson.findByDay(day);
        Collections.sort(list, new Comparator<DMLesson>() {
            @Override
            public int compare(DMLesson o1, DMLesson o2) {
                return o1.getTime()-o2.getTime();
            }
        });
        recyclerView.setAdapter(new recyclerAdapter(list, getContext()));
    }

    //    public Tab1Fragment() {
//        super();
//    }

    public static Tab1Fragment createFragment(int day){
        Tab1Fragment tab1Fragment = new Tab1Fragment();
        tab1Fragment.day = day;
        return tab1Fragment;
    }

//    @SuppressLint("ValidFragment")
//    public Tab1Fragment(int day){
//        super();
//        this.day = day;
//    }
}

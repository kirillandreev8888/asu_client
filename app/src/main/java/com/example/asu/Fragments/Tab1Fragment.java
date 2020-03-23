package com.example.asu.Fragments;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.asu.Adaprers.recyclerAdapter;
import com.example.asu.R;

import java.util.Random;

public class Tab1Fragment extends Fragment {

    public int day;

    public RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.tab1_fragment, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerview1);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        int num=(new Random()).nextInt(3)+1;
        int numpar=(new Random()).nextInt(3)+1;
        String[] test = new String[num+1];

        for (int i = 0; i<=num; i++){
            test[i] = "Day: "+day+"; position "+(i+1);

        }
        recyclerView.setAdapter(new recyclerAdapter(getContext(), test, numpar));

        return view;
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

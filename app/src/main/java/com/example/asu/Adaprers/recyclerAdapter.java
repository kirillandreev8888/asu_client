package com.example.asu.Adaprers;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asu.R;

public class recyclerAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    Context context;
    String[] items;
    int[] times;

    public recyclerAdapter(Context context, String[] items, int numpar){
        this.context = context;
        this.items = items;
        times = new int[items.length];
        for (int i=0; i<items.length; i++){
            times[i]=i+numpar;
        }
        this.times = times;
    }


    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.recycler_layout, viewGroup, false);
        Item item = new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((Item)viewHolder).textView1.setText(items[i]);
        ((Item)viewHolder).imageView.setImageResource(getTimeImageId(times[i]));
        ((Item)viewHolder).textView2.setText(""+times[i]);
    }

    @Override
    public int getItemCount() {
        return items.length;
    }

    public class Item extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        ImageView imageView;
        LinearLayout linearLayout;
        public Item(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.recyclerImage);
            textView1 = (TextView) itemView.findViewById(R.id.recyclerText1);
            textView2 = (TextView) itemView.findViewById(R.id.recyclerText2);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.recyclerlayout);
        }
    }

    private int getTimeImageId(int time){
        switch (time){
            case 1: return R.drawable.para1;
            case 2: return R.drawable.para2;
            case 3: return R.drawable.para3;
            case 4: return R.drawable.para4;
            case 5: return R.drawable.para5;
            case 6: return R.drawable.para6;
            case 7: return R.drawable.para7;
        }
        return R.drawable.para1;
    }


}

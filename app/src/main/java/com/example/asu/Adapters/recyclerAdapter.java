package com.example.asu.Adapters;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.asu.DomainModel.DMLesson;
import com.example.asu.R;

import java.util.List;

public class recyclerAdapter extends  RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DMLesson> lessons;
    private Context context;

    public recyclerAdapter(List<DMLesson> lessons, Context context) {
        this.lessons = lessons;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.recycler_layout, viewGroup, false);
        return new Item(row);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((Item)viewHolder).textView1.setText(lessons.get(i).name);
        if (lessons.get(i).pic!=0) {
            ((Item)viewHolder).imageView.setImageResource(lessons.get(i).pic);
        }
        ((Item)viewHolder).textView2.setText(lessons.get(i).classroom);
        ((Item)viewHolder).textView3.setText(lessons.get(i).type); //TODO сделать время до начала пары
        //TODO сделать изменение цвета типа пары
    }

    @Override
    public int getItemCount() {
        return lessons.size();
    }

    public class Item extends RecyclerView.ViewHolder {
        TextView textView1;
        TextView textView2;
        TextView textView3;
        ImageView imageView;
        LinearLayout linearLayout;
        Item(@NonNull View itemView) {
            super(itemView);
            imageView = (ImageView) itemView.findViewById(R.id.recyclerImage);
            textView1 = (TextView) itemView.findViewById(R.id.recyclerText1);
            textView2 = (TextView) itemView.findViewById(R.id.recyclerText2);
            textView3 = (TextView) itemView.findViewById(R.id.recyclerText3);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.recyclerlayout);
        }
    }


}

package com.example.asu.Adapters;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asu.DomainModel.DMGroup;
import com.example.asu.DomainModel.DMLesson;
import com.example.asu.MainActivity;
import com.example.asu.R;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.asu.MainActivity.BASE_URL;
import static com.example.asu.MainActivity.client;

public class recyclerSearchGroupsAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<DMGroup> data;
    private Context context;

    public recyclerSearchGroupsAdapter(List<DMGroup> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View row = inflater.inflate(R.layout.recycler_search_groups_layout, viewGroup, false);
        Item item = new Item(row);
        return item;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        final int ii = i;
        ((Item) viewHolder).Text.setText(data.get(i).name);
        ((Item) viewHolder).Item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(context, "id: " + data.get(ii).id, Toast.LENGTH_SHORT).show();
                Request request = new Request.Builder()
                        .url(BASE_URL + "api-searchforlessons/" + data.get(ii).id + "/")
                        .build();

                client.newCall(request).enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.d("QWE", "FAIL");
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String res = response.body().string();
                        Log.e("QWE", res);
                        List<DMLesson> list = new Gson().fromJson(res, new TypeToken<List<DMLesson>>() {
                        }.getType());
                        if (list.size() == 0) {
                            ((Activity) context).runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "Нет занятий", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        DMLesson.deleteAll();
                        for (DMLesson lesson : list) {
                           lesson.setILesson();
                           lesson.ilesson.save();
                        }
                        Instrumentation inst = new Instrumentation();
                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                        inst.sendKeyDownUpSync(KeyEvent.KEYCODE_BACK);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class Item extends RecyclerView.ViewHolder {
        LinearLayout Item;
        TextView Text;

        public Item(@NonNull View itemView) {
            super(itemView);
            Item = (LinearLayout) itemView.findViewById(R.id.recyclerSearchGroupsAdapterItem);
            Text = (TextView) itemView.findViewById(R.id.recyclerSearchGroupsAdapterText);
        }
    }
}

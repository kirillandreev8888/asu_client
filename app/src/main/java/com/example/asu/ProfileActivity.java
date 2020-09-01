package com.example.asu;

import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.asu.Adapters.recyclerSearchGroupsAdapter;
import com.example.asu.DomainModel.DMGroup;
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

public class ProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        final Context context = this;

        Button button = findViewById(R.id.searchGroupsButton);
        final TextView textView = findViewById(R.id.searchGroupsInput);
        final RecyclerView recyclerView = findViewById(R.id.searchGroupsRecycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = textView.getText().toString();

                //REQEST

                Request request = new Request.Builder()
                        .url(BASE_URL + "api-searchforgroups/" + name + "/")
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
                        List<DMGroup> list = new Gson().fromJson(res, new TypeToken<List<DMGroup>>() {
                        }.getType());
                        if (list.size() == 0) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "Ничего не найдено", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                        final List<DMGroup> liist = list;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                recyclerView.setAdapter(new recyclerSearchGroupsAdapter(liist, context));
                            }
                        });
                    }
                });

            }
        });
    }
}

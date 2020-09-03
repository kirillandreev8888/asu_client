package com.example.asu;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import com.example.asu.DomainModel.DMLesson;
import com.example.asu.DomainModel.DMSetting;
import com.google.android.material.tabs.TabLayout;

import androidx.viewpager.widget.ViewPager;

import com.google.android.material.navigation.NavigationView;

import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.asu.Adapters.SectionsPageAdapter;
import com.example.asu.RowDataGateway.DBinitter;
import com.example.asu.Fragments.Tab1Fragment;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.IsoFields;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    Toolbar toolbar;
    public static OkHttpClient client = new OkHttpClient();
    public static final String BASE_URL = "http://10.0.2.2:8000/groups/";
    Context context;
    SharedPreferences settings;
    int currentWeek;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = this;
        settings = getSharedPreferences("settings", MODE_PRIVATE);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //mSectionsPageAdapter = new SectionsPageAdapter(getSupportFragmentManager());
        //setupViewPager(mViewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);

        DBinitter dBinitter = new DBinitter(this);

        ImageButton weekSwitcher = findViewById(R.id.weekSwitcher);
        weekSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current = mViewPager.getCurrentItem();
                int count = mViewPager.getAdapter().getCount() / 2;
                if (current < count)
                    mViewPager.setCurrentItem(current + count);
                else
                    mViewPager.setCurrentItem(current - count);
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        //нечитаемый код: сначала к Date приводится настройка в Long
        Date startDate = new Date(settings.getLong("startDate", java.util.Date.from(LocalDate.of(2000, 1, 1).atStartOfDay() //у которой для задания default
                .atZone(ZoneId.systemDefault()) //идет сначала преобразование чисел в LocalDate, потом в Date и потом в Long
                .toInstant()).getTime()));
        currentWeek = java.time.LocalDate.now().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) - //потом это обратно переводится в LocalDate
                startDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().get(IsoFields.WEEK_OF_WEEK_BASED_YEAR) + 1; //зато работает
        //конец нечитаемого кода
        toolbar.setTitle("Неделя "+currentWeek+", "+isFirstWeekText());

        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout.setupWithViewPager(mViewPager);
        setupViewPager(mViewPager);

        int dayOfWeek = java.time.LocalDate.now().getDayOfWeek().getValue();
        DMSetting todaySetting = DMSetting.findOne("showday"+dayOfWeek);
        if (todaySetting.checkShowDaySetting())
            if(isFirstWeek())
                mViewPager.setCurrentItem(DMSetting.getAllShownDays().indexOf(dayOfWeek)+1);
            else
                mViewPager.setCurrentItem(DMSetting.getAllShownDays().indexOf(dayOfWeek)+1+DMSetting.getAllShownDays().size());
        if (!todaySetting.exists())
            if(isFirstWeek())
                mViewPager.setCurrentItem(dayOfWeek);
            else
                mViewPager.setCurrentItem(dayOfWeek+6);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_switch_week) {
            int current = mViewPager.getCurrentItem();
            int count = mViewPager.getAdapter().getCount() / 2;
            if (current < count)
                mViewPager.setCurrentItem(current + count);
            else
                mViewPager.setCurrentItem(current - count);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            //--->начало запроса расписания
            Request request = new Request.Builder()
                    .url("https://asu-firebase.firebaseio.com/schedule.json")
                    .build();
            client.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(@NotNull Call call, @NotNull IOException e) {
                    alert("ошибка соединения");
                }

                @Override
                public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                    String res = response.body().string();
                    List<DMLesson> list = new Gson().fromJson(res, new TypeToken<List<DMLesson>>() {
                    }.getType());
                    DMLesson.deleteAll();
                    for (DMLesson lesson : list) {
                        lesson.setILesson();
                        lesson.ilesson.save();
                    }
                    //<--- конец запроса расписания

                    //--->запрос даты
                    Request requestForDate = new Request.Builder()
                            .url("https://asu-firebase.firebaseio.com/startDate.json")
                            .build();
                    client.newCall(requestForDate).enqueue(new Callback() {
                        @Override
                        public void onFailure(@NotNull Call call, @NotNull IOException e) {

                        }

                        @Override
                        public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                            String res = response.body().string();
                            Date date = new GsonBuilder().setDateFormat("dd.MM.yyyy").create().fromJson(res, Date.class);
                            SharedPreferences.Editor editor = settings.edit();
                            editor.putLong("startDate", date.getTime());
                            editor.apply();

                            Intent intent = getIntent();
                            finish();
                            startActivity(intent);

                        }
                    });
                    //<--- конец запроса даты
                }
            });
        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void setupViewPager(ViewPager viewPager) {
        SectionsPageAdapter adapter = new SectionsPageAdapter(getSupportFragmentManager());

        adapter.addFragment(Tab1Fragment.createFragment(1), getString(R.string.tab_text_1));
        adapter.addFragment(Tab1Fragment.createFragment(2), getString(R.string.tab_text_2));
        adapter.addFragment(Tab1Fragment.createFragment(3), getString(R.string.tab_text_3));
        adapter.addFragment(Tab1Fragment.createFragment(4), getString(R.string.tab_text_4));
        adapter.addFragment(Tab1Fragment.createFragment(5), getString(R.string.tab_text_5));
        adapter.addFragment(Tab1Fragment.createFragment(6), getString(R.string.tab_text_6));
        adapter.addFragment(Tab1Fragment.createFragment(11), getString(R.string.tab_text_11));
        adapter.addFragment(Tab1Fragment.createFragment(12), getString(R.string.tab_text_12));
        adapter.addFragment(Tab1Fragment.createFragment(13), getString(R.string.tab_text_13));
        adapter.addFragment(Tab1Fragment.createFragment(14), getString(R.string.tab_text_14));
        adapter.addFragment(Tab1Fragment.createFragment(15), getString(R.string.tab_text_15));
        adapter.addFragment(Tab1Fragment.createFragment(16), getString(R.string.tab_text_16));

        viewPager.setAdapter(adapter);
    }

    private void alert(String text) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private boolean isFirstWeek(){
        if (currentWeek % 2 == 1)
            return true;
        else
            return false;
    }
    private String isFirstWeekText(){
        if (currentWeek % 2 == 1)
            return "ЧС";
        else
            return "ЗН";
    }
}

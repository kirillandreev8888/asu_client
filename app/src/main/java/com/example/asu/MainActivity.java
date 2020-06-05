package com.example.asu;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.asu.Adapters.SectionsPageAdapter;
import com.example.asu.DomainModel.DMGroup;
import com.example.asu.DomainModel.DMSetting;
import com.example.asu.RowDataGateway.DBinitter;
import com.example.asu.Fragments.Tab1Fragment;
import com.example.asu.RowDataGateway.Lesson;
import com.example.asu.RowDataGateway.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //private SectionsPageAdapter mSectionsPageAdapter;
    private ViewPager mViewPager;
    private TabLayout tabLayout;
    public static OkHttpClient client = new OkHttpClient();
    public static final String BASE_URL = "http://10.0.2.2:8000/groups/";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

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

    }

    @Override
    protected void onResume() {
        super.onResume();
        mViewPager = (ViewPager) findViewById(R.id.container);
        tabLayout.setupWithViewPager(mViewPager);
        setupViewPager(mViewPager);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

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
//            Toast.makeText(getApplicationContext(), ""+count, Toast.LENGTH_SHORT).show();
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
            Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
            startActivity(intent);
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
}

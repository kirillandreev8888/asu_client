package com.example.asu;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.widget.SwitchCompat;

import android.view.View;
import android.widget.Button;

import com.example.asu.DomainModel.DMSetting;

public class SettingsActivity extends AppCompatActivity {

    DMSetting s1;
    DMSetting s2;
    DMSetting s3;
    DMSetting s4;
    DMSetting s5;
    DMSetting s6;

    SwitchCompat sw1;
    SwitchCompat sw2;
    SwitchCompat sw3;
    SwitchCompat sw4;
    SwitchCompat sw5;
    SwitchCompat sw6;

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        context = this;

        sw1 = findViewById(R.id.settingSwitch1);
        sw2 = findViewById(R.id.settingSwitch2);
        sw3 = findViewById(R.id.settingSwitch3);
        sw4 = findViewById(R.id.settingSwitch4);
        sw5 = findViewById(R.id.settingSwitch5);
        sw6 = findViewById(R.id.settingSwitch6);

        Button saveButton = findViewById(R.id.settingButtonSave);
        Button backButton = findViewById(R.id.settingButtonBack);

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                s1.setDayStateSetting(sw1.isChecked());
                s1.save();
                s2.setDayStateSetting(sw2.isChecked());
                s2.save();
                s3.setDayStateSetting(sw3.isChecked());
                s3.save();
                s4.setDayStateSetting(sw4.isChecked());
                s4.save();
                s5.setDayStateSetting(sw5.isChecked());
                s5.save();
                s6.setDayStateSetting(sw6.isChecked());
                s6.save();
                onBackPressed();

                PackageManager packageManager = context.getPackageManager();
                Intent intent = packageManager.getLaunchIntentForPackage(context.getPackageName());
                ComponentName componentName = intent.getComponent();
                Intent mainIntent = Intent.makeRestartActivityTask(componentName);
                context.startActivity(mainIntent);
                Runtime.getRuntime().exit(0);
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             onBackPressed();
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        s1 = DMSetting.findOne("showday1");
        s2 = DMSetting.findOne("showday2");
        s3 = DMSetting.findOne("showday3");
        s4 = DMSetting.findOne("showday4");
        s5 = DMSetting.findOne("showday5");
        s6 = DMSetting.findOne("showday6");

        if (s1.exists()) {
            sw1.setChecked(s1.checkShowDaySetting());
        } else {
            sw1.setChecked(true);
        }

        if (s2.exists()) {
            sw2.setChecked(s2.checkShowDaySetting());
        } else {
            sw2.setChecked(true);
        }

        if (s3.exists()) {
            sw3.setChecked(s3.checkShowDaySetting());
        } else {
            sw3.setChecked(true);
        }

        if (s4.exists()) {
            sw4.setChecked(s4.checkShowDaySetting());
        } else {
            sw4.setChecked(true);
        }

        if (s5.exists()) {
            sw5.setChecked(s5.checkShowDaySetting());
        } else {
            sw5.setChecked(true);
        }

        if (s6.exists()) {
            sw6.setChecked(s6.checkShowDaySetting());
        } else {
            sw6.setChecked(true);
        }
    }
}

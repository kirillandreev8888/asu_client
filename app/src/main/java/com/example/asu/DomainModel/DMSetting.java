package com.example.asu.DomainModel;

import com.example.asu.RowDataGateway.Setting;

public class DMSetting {

    public int id;
    public String name;
    public String value;
    private boolean exists;

    public boolean exists() {
        return this.exists;
    }

    public boolean checkShowDaySetting() {
        if (this.value.equals("on"))
            return true;
        else return false;
    }

    public void setDayStateSetting(boolean val){
        if (val)
            this.value = "on";
        else
            this.value = "off";
    }

    public DMSetting() {
    }

    public DMSetting(Setting setting) {
        this.id = setting._id;
        this.name = setting.name;
        this.value = setting.value;
        this.exists = true;
    }

    public DMSetting(String name, String value) {
        this.name = name;
        this.value = value;
        this.exists = false;
    }

    public static DMSetting findOne(String name) {

        Setting temp = Setting.findOne(name);
        if (temp == null) {
            return new DMSetting(name, "default");
        } else
            return new DMSetting(temp);
    }

    public void save() {
        if (this.exists) {
            Setting temp = new Setting();
            temp._id = this.id;
            temp.name = this.name;
            temp.value = this.value;
            temp.update();
        } else {
            Setting temp = new Setting();
            temp.name = this.name;
            temp.value = this.value;
            temp.insert();
        }
    }
}

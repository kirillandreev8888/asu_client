package com.example.asu.Custom;

import com.example.asu.R;

public class Custom {

    public static int getTimeImageId(int time){
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

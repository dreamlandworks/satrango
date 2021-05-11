package com.satrangolimitless.User_UI.My_Job_Posts;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Job_Posts_Adapter extends FragmentPagerAdapter {

    int totalTabs;
    private Context context;
    String user_id;

    public Job_Posts_Adapter(FragmentManager fm, Context context, int totalTabs, String user_id) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
        this.user_id=user_id;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                User_Pending_Jobs fragment11 = new User_Pending_Jobs();
                return fragment11;
            case 1:
                User_Awarded_Jobs fragment22 = new User_Awarded_Jobs();
                return fragment22;
            case 2:
                User_Expired_Jobs fragment33 = new User_Expired_Jobs();
                return fragment33;
            default:
                return null;
        }
    }




    @Override
    public int getCount() {
        return totalTabs;
    }
}


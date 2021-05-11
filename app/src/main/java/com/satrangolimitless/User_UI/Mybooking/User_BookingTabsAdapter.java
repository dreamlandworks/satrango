package com.satrangolimitless.User_UI.Mybooking;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class User_BookingTabsAdapter extends FragmentPagerAdapter {

    int totalTabs;
    private Context context;
    String user_id;

    public User_BookingTabsAdapter(FragmentManager fm, Context context, int totalTabs, String user_id) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
        this.user_id=user_id;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                PendingBookingFragment fragment11 = new PendingBookingFragment();
                return fragment11;
            case 1:
                InprogressBookingFragment fragment22 = new InprogressBookingFragment();
                return fragment22;
            case 2:
                CompletedBookingFragment fragment33 = new CompletedBookingFragment();
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


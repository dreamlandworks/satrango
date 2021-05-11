package com.satrangolimitless.Vendor_UI.Vendor_MyBooking;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class BookingTabsAdapter_Vendor extends FragmentPagerAdapter {

    private final Context context;
    int totalTabs;
    String user_id;

    public BookingTabsAdapter_Vendor(FragmentManager fm, Context context, int totalTabs, String user_id) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
        this.user_id = user_id;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                Vendor_InprogressBookingFragment fragment22 = new Vendor_InprogressBookingFragment();
                return fragment22;
            case 1:
                Vendor_UpcomingBookingFragment fragment11 = new Vendor_UpcomingBookingFragment();
                return fragment11;


            case 2:
                Vendor_CompletedrejectedBookingFragment fragment33 = new Vendor_CompletedrejectedBookingFragment();
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


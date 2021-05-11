package com.satrangolimitless.Vendor_UI.My_Bids;

import android.content.Context;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class Vendor_MyBidsTabs_adapter extends FragmentPagerAdapter {

    private final Context context;
    int totalTabs;
    String user_id;

    public Vendor_MyBidsTabs_adapter(FragmentManager fm, Context context, int totalTabs, String user_id) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
        this.user_id = user_id;
    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                New_Jobs_Fragment fragment22 = new New_Jobs_Fragment();
                return fragment22;
            case 1:
                Bids_Placed_Fragment fragment11 = new Bids_Placed_Fragment();
                return fragment11;


            case 2:
                VendorAwarded_Expired_Fragment fragment33 = new VendorAwarded_Expired_Fragment();
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


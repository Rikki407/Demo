package com.kirayepay.KirayePay_Rikki.Navigation;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.LocalBroadcastManager;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.kirayepay.KirayePay_Rikki.Navigation.Home.HomeFragment;
import com.kirayepay.KirayePay_Rikki.Navigation.Menu.AllCategoriesFragment;
import com.kirayepay.KirayePay_Rikki.Fab.OnFabClickedActivity;
import com.kirayepay.KirayePay_Rikki.R;

/**
 * Created by rikki on 8/17/17.
 */

public class NavigationFragment extends Fragment {
    public BottomNavigationView navigation;
    private FloatingActionButton postButton;
    Fragment hf = HomeFragment.newInstance();
    Fragment alf = AllCategoriesFragment.newInstance();
    Fragment fragment = null;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.bottom_navigation_home:
                    fragment = hf;
                    break;
                case R.id.bottom_navigation_menu:
                    fragment = alf;
                    break;
            }
            if (fragment != null) {
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_contents, fragment);
                fragmentTransaction.commit();
            }
            return true;
        }

    };

    public static final String ACTION_INTENT = "DEMO_PLEASE";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_navigation, container, false);
        IntentFilter filter = new IntentFilter(ACTION_INTENT);
        LocalBroadcastManager.getInstance(getActivity()).registerReceiver(ActivityDataReceiver, filter);
        postButton = (FloatingActionButton) v.findViewById(R.id.float_post_action_button);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), OnFabClickedActivity.class);
                startActivity(intent);

            }
        });
        navigation = (BottomNavigationView) v.findViewById(R.id.navigation);

        navigation.setSelectedItemId(0);
        FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_contents, HomeFragment.newInstance());
        fragmentTransaction.commit();
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        return v;
    }

    @Override
    public void onDestroy() {
        LocalBroadcastManager.getInstance(getActivity()).unregisterReceiver(ActivityDataReceiver);
        super.onDestroy();
    }

    protected BroadcastReceiver ActivityDataReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

        }
    };

    public boolean navBackPressed() {
        if (getActivity() != null && isAdded()) {
            if (fragment == alf) {
                navigation.setSelectedItemId(0);
                navigation.getMenu().findItem(R.id.bottom_navigation_home).setChecked(true);
                FragmentTransaction fragmentTransaction = getChildFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.main_contents, HomeFragment.newInstance());
                fragmentTransaction.commit();
                fragment =hf;
                return true;
            }

        }
        return false;
    }

}

package com.hokagelab.www.cataloguemovie;

import android.app.ProgressDialog;
import android.content.Intent;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.hokagelab.www.cataloguemovie.adapter.MovieAdapter;
import com.hokagelab.www.cataloguemovie.adapter.MovieAdapterMulti;
import com.hokagelab.www.cataloguemovie.adapter.MovieAdapterSoon;
import com.hokagelab.www.cataloguemovie.api.ApiClient;
import com.hokagelab.www.cataloguemovie.api.MovieClient;
import com.hokagelab.www.cataloguemovie.model.MovieResponse;
import com.hokagelab.www.cataloguemovie.model.Result;
import com.takusemba.multisnaprecyclerview.MultiSnapRecyclerView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

//By Aris Kurniawan

public class MainActivity extends AppCompatActivity
        implements BottomNavigationView.OnNavigationItemSelectedListener {

    BottomNavigationView buttonNav;
    FrameLayout mFrame;
    private FragmentSearch fragmentSearch;
    private FragmentHome fragmentHome;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//  Kekinian

        mFrame = findViewById(R.id.frame_layout);
        buttonNav = findViewById(R.id.navigation);

        fragmentSearch = new FragmentSearch();
        fragmentHome = new FragmentHome();

        setFragment(fragmentHome);
        getSupportActionBar().setTitle("Home Movie");

        buttonNav.setOnNavigationItemSelectedListener(this);

    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.nav_search:
                setFragment(fragmentSearch);
                getSupportActionBar().setTitle("Search");
                item.setChecked(true);

                return true;
            case R.id.nav_home:
                setFragment(fragmentHome);
                getSupportActionBar().setTitle("Home Movie");
                item.setChecked(true);
                return true;

            default:
                return false;

        }
    }

    private void setFragment(FragmentHome fragmentHome) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragmentHome);
        fragmentTransaction.commit();

    }

    private void setFragment(FragmentSearch fragmentSearch) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout, fragmentSearch);
        fragmentTransaction.commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_change_settings){
            Intent mIntent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(mIntent);
        }
        return super.onOptionsItemSelected(item);
    }
}

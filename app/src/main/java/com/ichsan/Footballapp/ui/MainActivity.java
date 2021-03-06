package com.ichsan.Footballapp.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ichsan.Footballapp.R;
import com.ichsan.Footballapp.ui.competitions.CompetitionsActivity;
import com.ichsan.Footballapp.ui.matches.FixturesActivity;

public class MainActivity extends AppCompatActivity{

    private static final String LOG_TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar toolbar = getSupportActionBar();

        final int navCompetitions = R.id.navigation_competitions;
        final int navFixtures = R.id.navigation_fixtures;

        BottomNavigationView bottomNavigation = findViewById(R.id.bottom_navigation);
        bottomNavigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment fragment;
                int id = item.getItemId();
                assert toolbar != null;

                switch (id) {
                    case navCompetitions:
                        Toast.makeText(MainActivity.this, "Competitions clicked!",Toast.LENGTH_SHORT).show();
                        toolbar.setTitle(R.string.navigation_competitions);
                        fragment = new CompetitionsActivity();
                        loadFragment(fragment);
                        break;
                    case navFixtures:
                    default:
                        Toast.makeText(MainActivity.this, "Match clicked!",Toast.LENGTH_SHORT).show();
                        toolbar.setTitle(R.string.navigation_fixtures);
                        fragment = new FixturesActivity();
                        loadFragment(fragment);
                        break;
                }
                return true;
            }
        });
        bottomNavigation.setSelectedItemId(navFixtures);
    }

    private void loadFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu_main, menu);

//        final MenuItem searchItem = menu.findItem(R.id.action_search);
//        final SearchView searchView = (SearchView) searchItem.getActionView();
//        searchView.setOnQueryTextListener((SearchView.OnQueryTextListener) this);

        return true;
    }

}

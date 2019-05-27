package com.ichsan.Footballapp.util.competitions;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.ichsan.Footballapp.R;
import com.ichsan.Footballapp.ui.matches.MatchesFragment;
import com.ichsan.Footballapp.ui.standings.StandingsFragment;
import com.ichsan.Footballapp.ui.teams.TeamsFragment;

public class CompetitionAdapter extends FragmentPagerAdapter {

    private static final int TABS = 3;
    private Context context;

    public CompetitionAdapter(Context con, FragmentManager fm) {
        super(fm);
        context = con;
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            return new StandingsFragment();
        } else if (position == 1) {
            return new MatchesFragment();
        } else {
            return new TeamsFragment();
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position == 0) {
            return context.getString(R.string.category_standings);
        } else if (position == 1) {
            return context.getString(R.string.category_matches);
        } else {
            return context.getString(R.string.category_teams);
        }
    }

    @Override
    public int getCount() {
        return TABS;
    }
}

package com.ichsan.Footballapp.ui.competitions;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import com.ichsan.Footballapp.R;
import com.ichsan.Footballapp.data.model.competitions.Competition;
import com.ichsan.Footballapp.data.model.competitions.Competitions;
import com.ichsan.Footballapp.data.remote.ApiClient;
import com.ichsan.Footballapp.data.remote.ApiService;
import com.ichsan.Footballapp.util.ListItemClickListener;
import com.ichsan.Footballapp.util.competitions.CompetitionsAdapter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CompetitionsActivity extends Fragment implements ListItemClickListener {

    private static final String LOG_TAG = CompetitionsActivity.class.getSimpleName();
    private List<Competition> competitionList;
    private EditText search;

    public CompetitionsActivity() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_competitions, container, false);

        Log.d(LOG_TAG, LOG_TAG + " ACTIVE");

        RecyclerView list = view.findViewById(R.id.list_competitions);

        //search
//        final MenuItem searchItem = menu.findItem(R.id.action_search);
//        search = view.findViewById(R.id.action_search);
//        search.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (count != 0) {
//                    cariCompetions(search.getText().toString(), view);
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });


        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
        list.setLayoutManager(layoutManager);
        list.setHasFixedSize(true);
        list.setItemAnimator(new DefaultItemAnimator());

        ApiService apiService = ApiClient.getClient().create(ApiService.class);

        Call<Competitions> call = apiService.getCompetitions();
        call.enqueue(new Callback<Competitions>() {
            @Override
            public void onResponse(@NonNull Call<Competitions> call, @NonNull final Response<Competitions> response) {
                if (!response.isSuccessful()) {
                    Log.e(LOG_TAG, "FAILED CONNECTION with code " + response.code() + ": " + response.errorBody());
                    return;
                }

                Log.d(LOG_TAG, "SUCCESSFUL CONNECTION with code " + response.code());

                final Competitions res = response.body();

                if (res != null) {
                    Log.d(LOG_TAG, "Data received from " + response.body().toString());

                    competitionList = new ArrayList<>(res.getCompetitionList());
                    Log.d(LOG_TAG, "competitionList:" + competitionList.size());

                    CompetitionsAdapter adapter = new CompetitionsAdapter(competitionList, CompetitionsActivity.this);
                    list.setAdapter(adapter);

                    DividerItemDecoration divider = new DividerItemDecoration(list.getContext(), layoutManager.getOrientation());
                    list.addItemDecoration(divider);
                } else {
                    Log.w(LOG_TAG, "RESPONSE IS NULL!");
                }
            }

            @Override
            public void onFailure(@NonNull Call<Competitions> call, @NonNull Throwable t) {
                try {
                    Log.e(LOG_TAG, "QUERY FAILED: " + t.toString() + " >>> CAUSED BY: " + t.getCause());
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        return view;
    }

    @Override
    public void onListItemClick(int clickedItemIndex, int clickedItemId, String clickedItemName) {

        Intent intent = new Intent(this.getActivity(), CompetitionActivity.class);
        intent.putExtra("competitionId", clickedItemId);
        intent.putExtra("competitionName", clickedItemName);
        startActivity(intent);
    }


//    private void cariCompetions(String cari, View view) {
//        RecyclerView list = view.findViewById(R.id.list_competitions);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this.getActivity());
//        list.setLayoutManager(layoutManager);
//        list.setHasFixedSize(true);
//        list.setItemAnimator(new DefaultItemAnimator());
//
//        ApiService apiService = ApiClient.getClient().create(ApiService.class);
//
//        Call<Competitions> call = apiService.getCompetitions(cari);
//        call.enqueue(new Callback<Competitions>() {
//            @Override
//            public void onResponse(@NonNull Call<Competitions> call, @NonNull final Response<Competitions> response) {
//                if (!response.isSuccessful()) {
//                    Log.e(LOG_TAG, "FAILED CONNECTION with code " + response.code() + ": " + response.errorBody());
//                    return;
//                }
//
//                Log.d(LOG_TAG, "SUCCESSFUL CONNECTION with code " + response.code());
//
//                final Competitions res = response.body();
//
//                if (res != null) {
//                    Log.d(LOG_TAG, "Data received from " + response.body().toString());
//
//                    competitionList = new ArrayList<>(res.getCompetitionList());
//                    Log.d(LOG_TAG, "competitionList:" + competitionList.size());
//
//                    CompetitionsAdapter adapter = new CompetitionsAdapter(competitionList, CompetitionsActivity.this);
//                    list.setAdapter(adapter);
//
//                    DividerItemDecoration divider = new DividerItemDecoration(list.getContext(), layoutManager.getOrientation());
//                    list.addItemDecoration(divider);
//                } else {
//                    Log.w(LOG_TAG, "RESPONSE IS NULL!");
//                }
//            }
//
//            @Override
//            public void onFailure(@NonNull Call<Competitions> call, @NonNull Throwable t) {
//                try {
//                    Log.e(LOG_TAG, "QUERY FAILED: " + t.toString() + " >>> CAUSED BY: " + t.getCause());
//                    throw t;
//                } catch (Throwable throwable) {
//                    throwable.printStackTrace();
//                }
//            }
//        });
//    }

}


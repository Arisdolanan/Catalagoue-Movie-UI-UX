package com.hokagelab.www.cataloguemovie;


import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentHome extends Fragment {
    private final static String TAG = "MainActivity";
    private final static String API_KEY = "007c868395e80dc2e4a833416b24efa5";
    private RecyclerView.Adapter mAdapter;
    private MultiSnapRecyclerView playRecyclerView, soonRecyclerView;

    public FragmentHome() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_fragment_home, container, false);


        playRecyclerView = view.findViewById(R.id.multi);
        LinearLayoutManager layoutManager= new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        playRecyclerView.setNestedScrollingEnabled(false);
        playRecyclerView.setHasFixedSize(true);
        layoutManager.scrollToPositionWithOffset(0, 0);
        playRecyclerView.setLayoutManager(layoutManager);



        soonRecyclerView = view.findViewById(R.id.soon);
        LinearLayoutManager layoutManager2 = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        soonRecyclerView.setNestedScrollingEnabled(false);
        soonRecyclerView.setHasFixedSize(true);
        layoutManager2.scrollToPositionWithOffset(0, 0);
        soonRecyclerView.setLayoutManager(layoutManager2);

        getNowPlaying();
        getUpComing();

        return view;
    }

    private void getNowPlaying(){
        MovieClient apiService = ApiClient.getRetrofit().create(MovieClient.class);
        Call<MovieResponse> call = apiService.reposNowPlaying(API_KEY);

        // panggil progressdialog biar enak kayak nunggu jodoh
        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMax(100);
        progressDialog.setMessage("Harap Bersabar XD");
//        progressDialog.setTitle("Catalogue Movie");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();


        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progressDialog.dismiss();

                List<Result> MovieList = response.body().getResults();
                Log.d(TAG, "Jumlah data Movie: " + String.valueOf(MovieList.size()));
                //lempar data ke adapter
//                mRecyclerView.setAdapter(mAdapter);
                mAdapter = new MovieAdapterMulti(MovieList);
                playRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, t.toString());
                Toast.makeText(getActivity(), "koneksi error :(", Toast.LENGTH_SHORT).show();

            }
        });
    }

    private void getUpComing(){
        MovieClient apiService = ApiClient.getRetrofit().create(MovieClient.class);
        Call<MovieResponse> call = apiService.reposUpComing(API_KEY);

        final ProgressDialog progressDialog;
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMax(100);
        progressDialog.setMessage("Harap Bersabar XD");
//        progressDialog.setTitle("Catalogue Movie");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.show();


        call.enqueue(new Callback<MovieResponse>() {
            @Override
            public void onResponse(Call<MovieResponse> call, Response<MovieResponse> response) {
                progressDialog.dismiss();

                List<Result> MovieList = response.body().getResults();
                Log.d(TAG, "Jumlah data Movie: " + String.valueOf(MovieList.size()));
                //lempar data ke adapter
                mAdapter = new MovieAdapterSoon(MovieList);
                soonRecyclerView.setAdapter(mAdapter);

            }

            @Override
            public void onFailure(Call<MovieResponse> call, Throwable t) {
                progressDialog.dismiss();
                Log.e(TAG, t.toString());
                Toast.makeText(getActivity(), "koneksi error :(", Toast.LENGTH_SHORT).show();

            }
        });
    }

}
